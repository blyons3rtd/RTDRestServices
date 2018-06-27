package com.rtddenver.service.facade;

import java.io.InputStream;

import java.util.Date;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import java.util.Set;

import javax.annotation.PostConstruct;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Local;
import javax.ejb.Singleton;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


@Singleton(name="PropertiesService")
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Local(PropertiesServiceLocal.class)
public class PropertiesServiceBean implements PropertiesServiceLocal {
    
    private static final Logger LOGGER = LogManager.getLogger(PropertiesServiceBean.class.getName());
    private Properties props = null;
    private boolean reload = false;
    private long refreshLimit = 86400000; //Milliseconds to pass before refreshing cached info
    private Date lastRefreshDate = null;
    
    public PropertiesServiceBean() {
        super();
    }
    
    @PostConstruct
    public void initialize() {
        initializeProperties();
    }

    private void initializeProperties() {
        LOGGER.info("Initializing properties...");
        loadFromFile();
        lastRefreshDate = new Date();
    }

    public Properties getProperties() {
        if (this.props == null || this.props.isEmpty()) {
            LOGGER.info("Properties are null or empty!");
        }
        if (timeToRefresh()) {
            loadFromFile();
        }
        return this.props;
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private void loadFromFile() {
        InputStream inputStream = null;
        try {
            String fileName = "properties.xml";
            LOGGER.info("Loading properties from file: " + fileName);
            inputStream = this.getClass()
                              .getClassLoader()
                              .getResourceAsStream(fileName);
            props = new Properties();
            props.loadFromXML(inputStream);

            if (props == null || props.isEmpty()) {
                LOGGER.info("Properties empty! Please check file " + fileName);
                throw new Exception("Properties empty! Please check file " + fileName);
            }

            lastRefreshDate = new Date();

            LOGGER.info("Properties loaded");

        } catch (Exception e) {
            LOGGER.error("Properties failed to load!  Reason: " + e.toString());
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                LOGGER.warn("Failed to close inputStream.  Reason: " + e.toString());
            }
        }
    }


    /**
     * This method used primarily for test
     */
    void listProperties() {
        if (props != null) {
            Set<Entry<Object, Object>> set = props.entrySet();
            Iterator<Entry<Object, Object>> itr = set.iterator();
            LOGGER.info("Properties...");
            while (itr.hasNext()) {
                Entry<Object, Object> ent = itr.next();
                String key = (String) ent.getKey();
                String value = (String) ent.getValue();
                String aLine = key + "=" + value;
                LOGGER.info('\t' + aLine);
            }

        }
    }

    
    private boolean timeToRefresh() {
        boolean retVal = false;
        Date now = new Date();
        long diff = now.getTime() - lastRefreshDate.getTime();
        if (diff >= refreshLimit) {
            retVal = true;
        } else {
            Date future = new Date(now.getTime() + (refreshLimit - diff));
            LOGGER.info("Properties will be reloaded at " + future.toString());
        }
        return retVal;
    }
}

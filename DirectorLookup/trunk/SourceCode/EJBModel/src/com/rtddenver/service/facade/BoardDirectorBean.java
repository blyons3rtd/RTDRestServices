package com.rtddenver.service.facade;


import com.rtddenver.model.dto.DirectorDTO;
import com.rtddenver.service.BoardDirector;

import com.rtddenver.util.StackTraceUtil;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Local;
import javax.ejb.Singleton;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Local(BoardDirectorLocal.class)
public class BoardDirectorBean implements BoardDirectorLocal {

    private static final Logger LOGGER = LogManager.getLogger(BoardDirectorBean.class.getName());
    private ConcurrentHashMap<String, BoardDirector> directorMap = null;

    @PersistenceContext(unitName = "DirectorLookup")
    private EntityManager em;

    private boolean reload = false;
    private long refreshLimit = 86400000; //Milliseconds to pass before refreshing from db
    private Date lastRefreshDate = null;

    public BoardDirectorBean() {
        super();
    }

    @PostConstruct
    public void initialize() {
        initializeMap();
    }

    public DirectorDTO getDirectorByDistrict(String district) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Entered getDirectoryByDistrict(" + district + ")...");
        }
        DirectorDTO dto = null;
        // Provide a means to force a refresh of the directorMap via a svc call
        if (district == null || "".equals(district.trim())) {
            LOGGER.warn("District is null or empty");
            dto =
                new DirectorDTO(404, 1700, "No disrict code returned by DistrictLookup service",
                                "Address not found in RTD District", "");
        } else if (district.equalsIgnoreCase("refresh")) {
            initializeMap();
            dto = new DirectorDTO("Success", "directorMap initialized");
        } else {
            try {
                int cnt = 0;
                if (timeToRefresh()) {
                    directorMap = getAllDirectors();
                }
                while (cnt < 2) {
                    //Get director info from an existing cached map
                    //LOGGER.info("cnt:" + cnt);
                    BoardDirector bd = directorMap.get(district.toUpperCase());
                    if (bd != null) {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("Got director from cached directorMap");
                        }
                        dto = new DirectorDTO(bd.getDistrict(), bd.getDirectorFullName());
                        cnt = 2;
                    } else {
                        if (cnt >= 1) {
                            //Fail
                            LOGGER.error("Retry failed. Throwing exception...");
                            dto =
                                new DirectorDTO(500, 1999, "Could not find director for district " + district,
                                                "Director not found", "");
                        }
                        LOGGER.warn("BoardDirector returned null for district " + district +
                                    ".  Will try to reload the directorMap from the db.");
                        directorMap = getAllDirectors();
                    }
                    cnt++;
                }
            } catch (Exception e) {
                LOGGER.error(StackTraceUtil.getStackTrace(e));
                dto = new DirectorDTO(500, 1999, e.getMessage(), "Unexpected error occurred. Retry query.", "");
            }
        }
        return dto;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private ConcurrentHashMap<String, BoardDirector> getAllDirectors() {

        DirectorDTO dto = null;
        List<BoardDirector> bdL = null;

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Entered getAllDirectors...");
        }

        ConcurrentHashMap<String, BoardDirector> directorMap = new ConcurrentHashMap<String, BoardDirector>();
        try {
            bdL = em.createNamedQuery("getAllDirectors", BoardDirector.class).getResultList();

            if (bdL.size() == 0) {
                LOGGER.error("No directors retrieved from database");
                dto = new DirectorDTO(500, 1900, null, "No directors retrieved from database", "");
            } else {
                int sz = bdL.size();
                for (int x = 0; x < sz; x++) {
                    BoardDirector bd = bdL.get(x);
                    directorMap.put(bd.getDistrict(), bd);
                    LOGGER.info("Added to BoardDirector Map: district:" + bd.getDistrict() + "; " +
                                bd.getDirectorLastName() + ", " + bd.getDirectorFirstName());
                }
            }

        } catch (Exception ex) {
            LOGGER.error("Error querying and processing entity bean: " + ex);
            if (em != null) {
                em.clear();
                try {
                    em.close();
                } catch (Exception e) {
                    // do noting
                    LOGGER.error("Error closing EntityManager: " + e);
                } finally {
                    em = null;
                }
            }

            if (bdL != null) {
                bdL.clear();
                bdL = null;
            }

            dto = new DirectorDTO(500, 1999, ex.getClass().getName(), ex.getMessage(), "");
        }

        LOGGER.info("directorMap initialized");
        return directorMap;
    }

    private void initializeMap() {
        LOGGER.info("Initializing BoardDirectorBean directorMap from db...");
        directorMap = getAllDirectors();
        lastRefreshDate = new Date();
    }

    private boolean timeToRefresh() {
        boolean retVal = false;
        Date now = new Date();
        long diff = now.getTime() - lastRefreshDate.getTime();
        if (diff >= refreshLimit) {
            retVal = true;
        } else {
            Date future = new Date(now.getTime() + (refreshLimit - diff));
            LOGGER.info("directorMap will be refreshed from db at " + future.toString());
        }
        return retVal;
    }
}

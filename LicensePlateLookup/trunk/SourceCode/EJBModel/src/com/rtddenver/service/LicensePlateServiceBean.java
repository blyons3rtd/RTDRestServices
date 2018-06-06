package com.rtddenver.service;

import com.rtddenver.model.data.LicensePlate;
import com.rtddenver.model.dto.LicensePlateDTO;

import java.io.Serializable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


//***********************************************************
/* Description:
/*
/*
/* @author Luis Roche
/* @version 1.0, 11/17/2017
 */
//***********************************************************

@Stateless(name = "LicensePlateService")
@TransactionAttribute(value=TransactionAttributeType.NEVER)
public class LicensePlateServiceBean implements LicensePlateServiceLocal, Serializable {
    @SuppressWarnings("compatibility:-6964314926086926034")
    private static final long serialVersionUID = -8133779609484907020L;

    private static final Logger LOGGER = LogManager.getLogger(LicensePlateServiceBean.class.getName());
    
    @Resource
    private SessionContext sessionContext;

    @PersistenceContext(unitName = "licensePlateModel")
    private EntityManager em;

    /**
     * LicensePlateServiceBean
     */
    public LicensePlateServiceBean() {
        super();
    }

    /**
     * getLicensePlate
     * @param plateNumber String
     * @return LicensePlateDTO
     */
    @Override
    public LicensePlateDTO getLicensePlate(String plateNumber) throws Exception {
        //double start = System.currentTimeMillis();
        LicensePlateDTO dto = null;
        List<LicensePlate> lp = null;

        if (plateNumber == null || plateNumber.trim().isEmpty()) {
            return new LicensePlateDTO(400, "1600", "Required input missing", "License plate is null or empty");
        }

        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(plateNumber);

        if (m.find()) {
            return new LicensePlateDTO(400, "1610", "Invalid input", "Invalid characters found in license plate");
        }

        lp = em.createNamedQuery("findByLicensePlateNumber", LicensePlate.class)
               .setParameter("plateNumber", plateNumber.toUpperCase())
               .setMaxResults(1)
               .getResultList();

        if (lp.size() == 0) {
            // No rows returned
            //dto = new LicensePlateDTO(plateNumber, -1, -1);
            return new LicensePlateDTO(404, "1700", "Not found", "License Plate Not Found");
        } else {
            dto = new LicensePlateDTO(lp.get(0).getPlateNumber(), lp.get(0).getInDistrict(), lp.get(0).getGeocoded());
        }

        if (lp != null) {
            lp.clear();
            lp = null;
        }

        //LOGGER.info("Transaction - Plate:" + plateNumber + " Time(s):" + ((double) System.currentTimeMillis() - start) / (1000.0d));
        return dto;
    }
}

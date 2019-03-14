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
@TransactionAttribute(value = TransactionAttributeType.NEVER)
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
    public LicensePlateDTO getLicensePlate(String plate) throws Exception {
        double start = System.currentTimeMillis();
        LicensePlateDTO dto = null;
        List<LicensePlate> lp = null;
        String plateNumber = "";

        if (plate == null || plate.trim().isEmpty()) {
            dto = new LicensePlateDTO(400, 1600, "Required input missing", "License plate is null or empty", "");
        } else {
            plateNumber = plate;
            LOGGER.info("Plate received <" + plateNumber + ">");
            // Convert ascii space to space
            plateNumber = plateNumber.replaceAll("%20", " ");
            //LOGGER.info("Plate <" + plateNumber + ">");
            // Trim any trailing spaces
            plateNumber = plateNumber.trim();
            //LOGGER.info("Plate <" + plateNumber + ">");
            // Trim any leading spaces
            while (plateNumber.startsWith(" ")) {
                plateNumber = plateNumber.replaceFirst(" ", "");
            }
            LOGGER.info("Plate to checked <" + plateNumber + ">");
            // Check plate number for bad characters
            // Permitted chars are letters A-Z, upper or lower-case, digits 0-9, and embedded spaces
            //Pattern p = Pattern.compile("[^A-Za-z0-9 ]");
            //Matcher m = p.matcher(plateNumber);

            //if (m.find()) {
            //    dto = new LicensePlateDTO(400, 1610, "Invalid characters found in license plate", "Bad Request", "");
            //} else {
                lp = em.createNamedQuery("findByLicensePlateNumber", LicensePlate.class)
                       .setParameter("plateNumber", plateNumber.toUpperCase())
                       .setMaxResults(1)
                       .getResultList();

                if (lp.size() == 0) {
                    // No rows returned
                    dto = new LicensePlateDTO(404, 1700, "License plate not found - " + plateNumber, "Not Found", "");
                } else {
                    dto =
                        new LicensePlateDTO(lp.get(0).getPlateNumber(), lp.get(0).getInDistrict(),
                                            lp.get(0).getGeocoded());
                }

                if (lp != null) {
                    lp.clear();
                    lp = null;
                }
            //}

        }

        LOGGER.info("Transaction - Plate(orig):" + plate + ", Plate(edited):" + plateNumber + ", Time(s):" +
                    ((double) System.currentTimeMillis() - start) / (1000.0d) + ", Response... Plate:" +
                    dto.getPlateNumber() + ", InDistrict:" + dto.getInDistrict() + ", GeoCoded:" + dto.getGeocoded() +
                    ", Reason:" + dto.getReason() + ", Detail:" + dto.getDetail() + ", Message:" + dto.getMessage());

        return dto;
    }
}

package com.rtddenver.service;


import com.rtddenver.model.data.LicensePlate;
import com.rtddenver.model.dto.ErrorDTO;
import com.rtddenver.model.dto.LicensePlateDTO;


import java.util.List;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//***********************************************************
/* Description:
/*
/*
/* @author Luis Roche
/* @version 1.0, 11/17/2017
 */
//***********************************************************

@Stateless(name = "LicensePlateService")
public class LicensePlateServiceBean implements LicensePlateServiceLocal {

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
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public LicensePlateDTO getLicensePlate(String plateNumber) {
        //double start = System.currentTimeMillis();
        LicensePlateDTO dto = null;
        List<LicensePlate> lp = null;

        try {
            lp = em.createNamedQuery("findByLicensePlateNumber", LicensePlate.class)
                   .setParameter("plateNumber", plateNumber)
                   .setMaxResults(1)
                   .getResultList();

            if (lp.size() == 0) {
                dto = new LicensePlateDTO(plateNumber, 0, false);
            } else {
                LicensePlate p = lp.get(0);
                dto = new LicensePlateDTO(p.getPlateNumber(), p.getInDistrict());
            }
        } catch (Exception ex) {
            if (em != null) {
                em.clear();

                try {
                    em.close();
                } catch (Exception e) {
                    // do noting
                } finally {
                    em = null;
                }
            }

            if (lp != null) {
                lp.clear();
                lp = null;
            }

            ErrorDTO error = new ErrorDTO("500", ex.getClass().getName(), ex.getMessage());
            dto = new LicensePlateDTO(error);
        }
        //System.out.println("de " + ((double) System.currentTimeMillis() - start) / (1000.0d));
        return dto;
    }
}

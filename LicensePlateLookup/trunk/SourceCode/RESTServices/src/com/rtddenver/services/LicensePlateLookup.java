package com.rtddenver.services;

import com.rtddenver.model.dto.LicensePlateDTO;
import com.rtddenver.service.LicensePlateServiceLocal;

import javax.ejb.EJB;

import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


//***********************************************************
/* Description:
/*
/*
/* @author Luis Roche
/* @version 1.0, 11/17/2017
 */
//***********************************************************
//BUG in current version of JERSEY/weblogic 12.2.1 - https://github.com/jersey/jersey/issues/2962
//@Stateless
@javax.enterprise.context.RequestScoped
@Path("v1/plate")
public class LicensePlateLookup {

    //@EJB(name="LicensePlateService", beanInterface=com.rtddenver.model.facade.LicensePlateServiceLocal.class, beanName="EJBModel.jar#LicensePlateService")
    @EJB
    private LicensePlateServiceLocal licensePlateService;

    /**
     * LicensePlateLookup
     */
    public LicensePlateLookup() {
        super();
    }

    /**
     * getLicensePlate
     * @param plateNumber String
     * @return LicensePlateDTO
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("{plateNumber}")
    public LicensePlateDTO getLicensePlate(@Encoded @PathParam("plateNumber") String plateNumber) {
        return this.licensePlateService.getLicensePlate(plateNumber);
    }
}

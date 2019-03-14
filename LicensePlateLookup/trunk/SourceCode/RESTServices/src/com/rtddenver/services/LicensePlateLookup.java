package com.rtddenver.services;

import com.rtddenver.model.dto.LicensePlateDTO;
import com.rtddenver.service.LicensePlateServiceLocal;

import java.io.IOException;

import javax.ejb.EJB;

import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
//BUG in current version of JERSEY/weblogic 12.2.1 - https://github.com/jersey/jersey/issues/2962
//@Stateless
@javax.enterprise.context.RequestScoped
@Path("v1/plates")
public class LicensePlateLookup {
    private static final Logger LOGGER = LogManager.getLogger(LicensePlateLookup.class.getName());
    
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
    @Path("{plateNumber}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public LicensePlateDTO getLicensePlate(@Encoded @PathParam("plateNumber") String plateNumber, @Context final HttpServletResponse response) {
        LicensePlateDTO dto = null;
        
        try {
            dto = this.licensePlateService.getLicensePlate(plateNumber);
        } catch (Exception e) {
            dto = new LicensePlateDTO(500, 1950, e.getMessage(), "Internal Server Error","");
            LOGGER.error("Error in LicensePlateLookup.getLicensePlate - calling session ejb", e);
        }
    
        int status = 0;
        if (dto.isError()) {
            switch (dto.getStatusAsInt()) {
            case 400:
                status = Response.Status
                                 .BAD_REQUEST
                                 .getStatusCode();
                break;
            case 404:
                status = Response.Status
                                 .NOT_FOUND
                                 .getStatusCode();
                break;
            case 500:
                status = Response.Status
                                 .INTERNAL_SERVER_ERROR
                                 .getStatusCode();
                break;
            }

            try {
                response.setStatus(status);
                response.setContentType(MediaType.APPLICATION_JSON + ";charset=utf-8");
                response.getOutputStream().close();
                response.flushBuffer();
            } catch (IOException e) {
                LOGGER.error("Error in LicensePlateLookup.getLicensePlate - setting response", e);
            }
        }
        

        return dto;
    }
}

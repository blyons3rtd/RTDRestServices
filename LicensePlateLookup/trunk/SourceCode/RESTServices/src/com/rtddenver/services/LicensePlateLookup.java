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
        LicensePlateDTO dto = this.licensePlateService.getLicensePlate(plateNumber);
    
        int status = 0;
        if (dto.getStatus() != null) {
            // 400
            if (dto.getStatus() == 400) {
                status = Response.Status.BAD_REQUEST.getStatusCode();
            } else if (dto.getStatus() == 404) {
                status = Response.Status.NOT_FOUND.getStatusCode();
            } else if (dto.getStatus() == 500) {
                // 500 error
                status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
            }
            
            try {
                response.setStatus(status);
                response.setContentType(MediaType.APPLICATION_JSON + ";charset=utf-8");
                response.getOutputStream().close();
                response.flushBuffer();
            } catch (IOException e) {
                LOGGER.error("Error in LicensePlateLookup.getLicensePlate", e);
            }
        }

        return dto;
    }
}

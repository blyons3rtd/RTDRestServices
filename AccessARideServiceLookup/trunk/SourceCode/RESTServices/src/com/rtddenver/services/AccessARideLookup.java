package com.rtddenver.services;

import com.rtddenver.model.dto.DistrictDTO;

import com.rtddenver.service.facade.AdaRestServiceLocal;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ejb.AccessTimeout;
import javax.ejb.EJB;

import javax.ws.rs.Encoded;
import javax.ws.rs.PathParam;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


//***********************************************************
/* Description:
/*   RESTful service for determining RTD Access-A-Ride service
/*   availability for the provided address, city, zip,
/*   departureDay and departureTime
/*
/* @author Jay Butler
/* @version 1.0, 12/07/2017
 */
//***********************************************************
//BUG in current version of JERSEY/weblogic 12.2.1 - https://github.com/jersey/jersey/issues/2962
//@Stateless
@javax.enterprise.context.RequestScoped
@Path("v1/addresses")
@Produces("application/json")
public class AccessARideLookup {

    private static final Logger LOGGER = LogManager.getLogger(AccessARideLookup.class.getName());

    @EJB(name = "AdaRestService", beanInterface = com.rtddenver.service.facade.AdaRestServiceLocal.class,
         beanName = "EJBModel.jar#AdaRestService")
    //@EJB
    private AdaRestServiceLocal adaRestService;


    public AccessARideLookup() {
        super();
    }

    @AccessTimeout(value = 60, unit = TimeUnit.SECONDS)
    @GET
    @Produces("application/json")
    @Path("{street},{city},{zip},{departureDay},{departureTime}")
    public DistrictDTO getAccessARideInfo(@Encoded @PathParam("street") String street,
                                          @Encoded @PathParam("city") String city, 
                                          @PathParam("zip") String zip,
                                          @PathParam("departureDay") String departureDay,
                                          @PathParam("departureTime") String departureTime) {

        DistrictDTO districtDTO = null;

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Input received: " + street + " " + city + " " + zip + " " + departureDay + " " +
                         departureTime);
        }

        districtDTO = this.adaRestService.getAdaAvailability(street, city, zip, departureDay, departureTime);

        return districtDTO;
    }

}

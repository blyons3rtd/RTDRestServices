package com.rtddenver.services;

import com.rtddenver.model.dto.DistrictDTO;
import com.rtddenver.model.dto.GeoCodeAddressDTO;

import com.rtddenver.service.facade.DistrictServiceLocal;
import com.rtddenver.service.facade.GeoCoderServiceLocal;

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
@Path("v1/address")
@Produces("application/json")
public class AccessARideLookup {

    private static final Logger LOGGER = LogManager.getLogger(AccessARideLookup.class.getName());
    
    @EJB(name = "DistrictService", beanInterface = com.rtddenver.service.facade.DistrictServiceLocal.class,
         beanName = "EJBModel.jar#DistrictService")
    //@EJB
    private DistrictServiceLocal districtService;

    @EJB(name = "GeoCoderService", beanInterface = com.rtddenver.service.facade.GeoCoderServiceLocal.class,
         beanName = "EJBModel.jar#GeoCoderService")
    //@EJB
    private GeoCoderServiceLocal geocoderService;


    public AccessARideLookup() {
        super();
    }

    @AccessTimeout(value = 30, unit = TimeUnit.SECONDS)
    @GET
    @Produces("application/json")
    @Path("{street}/{city}/{zip}/{departureDay}/{departureTime}")
    public DistrictDTO getAccessARideInfo(@Encoded @PathParam("street") String street,
                                             @PathParam("city") String city, @PathParam("zip") String zip,
                                             @PathParam("departureDay") String departureDay,
                                             @PathParam("departureTime") String departureTime) {
        int options = 1;
        boolean returnInWGS84 = true;
        DistrictDTO districtDTO = null;

        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Input received: " + street + " " + city + " " + zip + " " + departureDay + " " +
                           departureTime);
        }

        GeoCodeAddressDTO geocodeDTO =
            this.geocoderService.getGeoCodeAddress(street, city, zip, options, returnInWGS84);

        if (geocodeDTO.isError()) {
            // Error handling
            districtDTO = new DistrictDTO(geocodeDTO.getError());
        } else {
            if (!geocodeDTO.isError()) {
                double lon = geocodeDTO.getX();
                double lat = geocodeDTO.getY();
                districtDTO = this.districtService.getAdaOnDayTimeLoc(departureDay, departureTime, lon, lat);
            } else {
                districtDTO = new DistrictDTO(geocodeDTO.getError());
            }
        }

        return districtDTO;
    }

}

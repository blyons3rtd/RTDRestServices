package com.rtddenver.services;

import com.rtddenver.model.dto.AccessARideDTO;
import com.rtddenver.model.dto.DistrictDTO;
import com.rtddenver.model.dto.ErrorDTO;
import com.rtddenver.model.dto.GeoCodeAddressDTO;

import com.rtddenver.service.facade.DistrictServiceLocal;
import com.rtddenver.service.facade.GeoCoderServiceLocal;

import java.util.concurrent.TimeUnit;

import javax.enterprise.context.RequestScoped;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ejb.AccessTimeout;
import javax.ejb.EJB;

import javax.ws.rs.Encoded;
import javax.ws.rs.PathParam;

import weblogic.logging.NonCatalogLogger;


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

    private NonCatalogLogger ncl = new NonCatalogLogger("AccessARideLookup");
    
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
        //AccessARideDTO aarDTO = null;
        DistrictDTO districtDTO = null;

        System.out.println("Input received: " + street + " " + city + " " + zip + " " + departureDay + " " +
                           departureTime);

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
                //aarDTO = evaluateResponseCode(districtDTO.getResponse(), departureDay, departureTime);
            } else {
                districtDTO = new DistrictDTO(geocodeDTO.getError());
            }
        }

        return districtDTO;
    }

    /**
     * Evaluates the response code returned from the GIS District service and assigns
     * the appropriate values for adaAvail and message in the AccessARideDTO
     * @param retVal
     * @param departureDay
     * @param departureTime
     * @return AccessARideDTO
     */
    /*
    private AccessARideDTO evaluateResponseCode(int retVal, String departureDay, String departureTime) {
        String message = "";
        boolean adaAvail = false;
        switch (retVal) {
        case -2:
            message = "-2: Timed out waiting for District Service response";
            break;
        case -1:
            message = "-1: General error in calling District Service";
            break;
        case 0:
            message = "0: Location is not in ADA service area at any time or day";
            adaAvail = false;
            break;
        case 1:
            message = "1: Service for location is available on " + departureDay + " at " + departureTime;
            adaAvail = true;
            break;
        case 3:
            message = "3: Service for location is available at a different time on " + departureDay;
            adaAvail = false;
            break;
        case 5:
            message = "5: Service for location is available on a different day at " + departureTime;
            adaAvail = false;
            break;
        case 7:
            message = "7: Service for location is available at a different time on " + departureDay;
            message += " and service for location is available on a different day at " + departureTime;
            adaAvail = false;
            break;
        case 8:
            message = "8: Service for location is only available on other days and at other times";
            adaAvail = false;
            break;
        case 10:
            message = "10: Service for location is available at a different time on " + departureDay;
            message += " and service for location is also available on other days at times other than " + departureTime;
            adaAvail = false;
            break;
        default:
            message = "Unexpected return value: " + retVal;
            adaAvail = false;
            break;
        }

        AccessARideDTO aarDTO = new AccessARideDTO(adaAvail, message);
        return aarDTO;
    }
    */
}

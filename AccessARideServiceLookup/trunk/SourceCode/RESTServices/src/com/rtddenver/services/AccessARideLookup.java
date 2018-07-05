package com.rtddenver.services;

import com.rtddenver.model.dto.DistrictDTO;

import com.rtddenver.model.dto.ErrorDTO;
import com.rtddenver.service.facade.AdaRestServiceLocal;

import java.io.IOException;

import java.util.concurrent.TimeUnit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ejb.AccessTimeout;
import javax.ejb.EJB;

import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Encoded;
import javax.ws.rs.QueryParam;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
@Path("v1")
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
    @Path("addresses")
    public DistrictDTO getAccessARideInfo(@Encoded @NotNull @QueryParam("street") String street,
                                          @Encoded @NotNull @QueryParam("city") String city, 
                                          @NotNull @QueryParam("zip") String zip,
                                          @NotNull @QueryParam("departureDay") String departureDay,
                                          @NotNull @QueryParam("departureTime") String departureTime,
                                          @Context final HttpServletResponse response) {

        DistrictDTO districtDTO = null;
        ErrorDTO err = null;

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Input received: " + street + " " + city + " " + zip + " " + departureDay + " " +
                         departureTime);
        }
        
        err = validateEntries(street, city, zip, departureDay, departureTime);

        if (err == null) {
            districtDTO = this.adaRestService.getAdaAvailability(street, city, zip, departureDay, departureTime);
        }
        else {
            districtDTO = new DistrictDTO(err);
        }
        
        int status = 0;
        if (districtDTO.isError()) {
            switch (districtDTO.getError().getStatus()) {
            case 400:
                status = Response.Status.BAD_REQUEST.getStatusCode();
                break;
            case 404:
                status = Response.Status.NOT_FOUND.getStatusCode();
                break;
            case 500:
                status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
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
        
        return districtDTO;
    }

    private ErrorDTO validateEntries(String street, String city, String zip, String departureDay,
                                     String departureTime) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Validating entries...");
        }
        ErrorDTO dto = null;
        String detail = "";
        String msg = "";
        boolean err = false;

        if (street == null || "".equals(street.trim())) {
            detail = "street cannot be empty/null ";
            err = true;
        }
        if (city == null || "".equals(city.trim())) {
            detail = detail + "city cannot be empty/null ";
            err = true;
        }
        if (zip == null || "".equals(zip.trim())) {
            detail = detail + "zip cannot be empty/null ";
            err = true;
        }
        if (departureDay == null || "".equals(departureDay.trim())) {
            detail = detail + "departureDay cannot be empty/null ";
            err = true;
        }
        if (departureTime == null || "".equals(departureTime.trim())) {
            detail = detail + "departureTime cannot be empty/null ";
            err = true;
        }
        if (!validateTime(departureTime)) {
            detail = detail + "Refer to API document for valid time format";
            err = true;
        }
        if (err) {
            msg = "Bad Request";
            dto = new ErrorDTO(400, 1610, detail, msg, "");
        }
        return dto;
    }

    private boolean validateTime(String time) {
        // Validates time is in a format expected by the GIS service.
        // 12hr format
        String TIMEHOURS_PATTERN = "(1[012]|[1-9]):[0-5][0-9](AM|PM)";;
        Pattern pattern = Pattern.compile(TIMEHOURS_PATTERN);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }
}

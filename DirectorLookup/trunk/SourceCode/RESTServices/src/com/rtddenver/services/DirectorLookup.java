package com.rtddenver.services;


import com.rtddenver.model.dto.DirectorDTO;
import com.rtddenver.model.dto.DistrictDTO;
import com.rtddenver.service.facade.BoardDirectorLocal;
import com.rtddenver.service.facade.GisDistrictServiceLocal;

import java.io.IOException;

import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.EJB;

import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.Context;

import javax.ws.rs.core.MediaType;

import javax.ws.rs.core.Response;

import javax.ws.rs.core.UriInfo;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


//***********************************************************
/* Description:
/*
/*
/* @author Jay Butler
/* @version 1.0, 11/30/2017
 */
//***********************************************************
//BUG in current version of JERSEY/weblogic 12.2.1 - https://github.com/jersey/jersey/issues/2962
//@Stateless
@javax.enterprise.context.RequestScoped
@Path("v1")
@Produces("application/json")
public class DirectorLookup {

    private static final Logger LOGGER = LogManager.getLogger(DirectorLookup.class.getName());

    @EJB
    private BoardDirectorLocal directorService;

    @EJB
    private GisDistrictServiceLocal gisDistrictService;

    /**
     * DirectorLookup
     */
    public DirectorLookup() {
        super();
    }

    /**
     *
     * @param street
     * @param city
     * @param zip
     * @param response
     * @return DirectorDTO
     */
    @AccessTimeout(value = 30, unit = TimeUnit.SECONDS)
    @GET
    @Produces("application/json")
    @Path("addresses")
    public DirectorDTO getDirector(@Encoded @NotNull @QueryParam("street") String street,
                                   @Encoded @NotNull @QueryParam("city") String city,
                                   @NotNull @QueryParam("zip") String zip,
                                   @Context final HttpServletResponse response) {

        DirectorDTO dirDto = null;
        DistrictDTO distDto = null;

        LOGGER.info("Input received: " + street + ", " + city + ", " + zip);

        dirDto = validateEntries(street, city, zip);

        if (dirDto == null) {
            if (street.equalsIgnoreCase("refresh") && city.equalsIgnoreCase("the") && zip.equalsIgnoreCase("map")) {
                dirDto = this.directorService.getDirectorByDistrict("refresh");
            } else {

                distDto = this.gisDistrictService.getDistrictForAddress(street, city, zip);

                if (distDto != null) {
                    if (distDto.isError()) {
                        dirDto = new DirectorDTO(distDto.getStatus(), distDto.getCode(), distDto.getDetail(), distDto.getMessage(), distDto.getMoreInfo());
                    } else {
                        String distr = distDto.getDistrict();
                        dirDto = this.directorService.getDirectorByDistrict(distr);
                    }
                } else {
                    LOGGER.warn("No reponse from GIS Service. Internal Service error. Address: " + street + ", " +
                                city + " " + zip);
                    dirDto = new DirectorDTO(500, 1999, "Internal Service error. Input received: " + street,
                                     "No reponse from GIS Service. Retry query.", "");
                }
            }
        } else {
            // Return error
        }

        int status = 0;
        if (dirDto.isError()) {
            switch (dirDto.getStatusAsInt()) {
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

        LOGGER.info("Returning...  District:" + dirDto.getDistrict() + "  Director:" + dirDto.getDirector() + "  Message:" + dirDto.getMessage());
        
        return dirDto;
    }

    private DirectorDTO validateEntries(String street, String city, String zip) {
        //LOGGER.info("Validating entries...");
        DirectorDTO dto = null;
        String detail = "";
        String msg = "";
        boolean err = false;

        if (street == null || "".equals(street.trim())) {
            detail = "Street cannot be empty/null ";
            err = true;
        }
        if (city == null || "".equals(city.trim())) {
            detail = detail + "City cannot be empty/null ";
            err = true;
        }
        if (zip == null || "".equals(zip.trim())) {
            detail = detail + "Zipcode cannot be empty/null ";
            err = true;
        }
        if (err) {
            msg = "Bad Request";
            dto = new DirectorDTO(400, 1610, detail, msg, "");
        }
        return dto;
    }
}

package com.rtddenver.services;


import com.rtddenver.model.dto.DirectorDTO;
import com.rtddenver.model.dto.DistrictDTO;
import com.rtddenver.model.dto.ErrorDTO;
import com.rtddenver.service.facade.BoardDirectorLocal;
import com.rtddenver.service.facade.GisDistrictServiceLocal;

import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.EJB;

import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

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
     * getDirectorForAddress
     * @param street
     * @param city
     * @param zip
     * @return Response
     */
    @AccessTimeout(value = 30, unit = TimeUnit.SECONDS)
    @GET
    @Produces("application/json")
    @Path("addresses/{street},{city},{zip}")
    public DirectorDTO getDirector(@Encoded @PathParam("street") String street, @PathParam("city") String city,
                                   @PathParam("zip") String zip) {
        DirectorDTO dirDto = null;
        DistrictDTO distDto = null;
        ErrorDTO err = null;

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Input received: " + street + ", " + city + ", " + zip);
        }

        if (street.equalsIgnoreCase("refresh") && city.equalsIgnoreCase("the") && zip.equalsIgnoreCase("map")) {
            dirDto = this.directorService.getDirectorByDistrict("refresh");
        } else {

            distDto = this.gisDistrictService.getDistrictForAddress(street, city, zip);

            if (distDto != null) {
                if (distDto.getMessage() != null && !"".equals(distDto.getMessage().trim())) {
                    if (distDto.getMessage().contains("error")) {
                        err = new ErrorDTO("500", distDto.getMessage(), "Internal GIS Service error");
                    } else if (distDto.getMessage().contains("Address Could Not Be Located")) {
                        err = new ErrorDTO("400", distDto.getMessage(), "Address not found in RTD service area");
                    }
                    dirDto = new DirectorDTO(err);
                } else {
                    String distr = distDto.getValue();
                    dirDto = this.directorService.getDirectorByDistrict(distr);
                }
            } else {
                LOGGER.warn("No reponse from GIS Service. Internal Service error.");
                err =
                    new ErrorDTO("500", "No reponse from GIS Service",
                                 "Internal Service error. Input received: " + street);
                dirDto = new DirectorDTO(err);
            }
        }

        return dirDto;
    }

}

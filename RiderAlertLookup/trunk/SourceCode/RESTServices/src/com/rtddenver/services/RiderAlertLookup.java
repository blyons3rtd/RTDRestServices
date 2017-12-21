package com.rtddenver.services;

import com.rtddenver.model.dto.ActiveAlertDTO;
import com.rtddenver.model.dto.RouteDTO;
import com.rtddenver.service.RiderAlertServiceLocal;

import javax.ejb.EJB;

import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@javax.enterprise.context.RequestScoped
//@Produces("application/json")
@Path("v1/allalerts")
public class RiderAlertLookup {

    @EJB
    private RiderAlertServiceLocal riderAlertService;

    /**
     * RiderAlertLookup
     */
    public RiderAlertLookup() {
        super();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    //@Produces("application/json")
    //@Path("allalerts")
    public RouteDTO getActiveAlertRoutes() {
        return this.riderAlertService.getActiveAlertRoutes();
    }
}

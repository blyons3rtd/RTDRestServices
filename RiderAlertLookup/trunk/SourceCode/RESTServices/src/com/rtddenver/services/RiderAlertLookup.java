package com.rtddenver.services;

import com.rtddenver.model.dto.ActiveAlertEventDTO;
import com.rtddenver.model.dto.AlertEventDTO;
import com.rtddenver.model.dto.AlertEventRouteDTO;
import com.rtddenver.model.dto.RouteActiveAlertEventDTO;
import com.rtddenver.service.RiderAlertServiceLocal;

import javax.ejb.EJB;

import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@javax.enterprise.context.RequestScoped
@Path("v1")
@Produces("application/json")
public class RiderAlertLookup {

    @EJB(name = "RiderAlertService", beanInterface = com.rtddenver.service.RiderAlertServiceLocal.class,
         beanName = "EJBModel.jar#RiderAlertService")
    private RiderAlertServiceLocal riderAlertService;

    
    /**
     * RiderAlertLookup
     */
    public RiderAlertLookup() {
        super();
    }

    /**
     * getActiveAlertEventList
     * @return ActiveAlertEventDTO
     */
    @GET
    @Produces("application/json")
    @Path("alerts")
    public ActiveAlertEventDTO getActiveAlertEventList() {
        return this.riderAlertService.getActiveAlertEventList();
    }
    
    /**
     * getAlertEventById
     * @return AlertEventDTO
     */
    @GET 
    @Produces("application/json")
    @Path("alerts/{alertEventId}")
    public AlertEventDTO getAlertEventById(@Encoded @PathParam("alertEventId") Integer alertEventId) {
        return this.riderAlertService.getAlertEventById(alertEventId);
    }
    
    /**
     * getRoutesWithActiveAlerts
     * @return RouteActiveAlertEventDTO
     */
    @GET
    @Produces("application/json")
    @Path("routes")
    public RouteActiveAlertEventDTO getRoutesWithActiveAlerts() {
        return this.riderAlertService.getRoutesActiveAlerts();
    }
    
    /**
     * getAlertEventRouteByID
     * @return AlertRouteDTO
     */
    @GET
    @Produces("application/json")
    @Path("routes/{masterRoute}")
    public AlertEventRouteDTO getAlertEventRouteByMasterRoute(@Encoded @PathParam("masterRoute") String masterRoute) {
        return this.riderAlertService.getAlertEventRouteByMasterRoute(masterRoute);
    }
}

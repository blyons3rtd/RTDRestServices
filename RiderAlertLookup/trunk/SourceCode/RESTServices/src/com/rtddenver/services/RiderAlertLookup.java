package com.rtddenver.services;

import com.rtddenver.model.dto.ActiveAlertDTO;
import com.rtddenver.model.dto.AlertRouteDTO;
import com.rtddenver.service.RiderAlertServiceLocal;

import com.rtddenver.service.RouteServiceLocal;
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

    @EJB(name = "RouteService", beanInterface = com.rtddenver.service.RouteServiceLocal.class,
         beanName = "EJBModel.jar#RouteService")
    private RouteServiceLocal geocoderService;


    /**
     * RiderAlertLookup
     */
    public RiderAlertLookup() {
        super();
    }

    /**
     * Get all active alerts
     * @return
     */
    @GET
    @Produces("application/json")
    @Path("alerts")
    public ActiveAlertDTO getActiveAlerts() {
        ActiveAlertDTO aa = null;
        try{
            aa = this.riderAlertService.getActiveAlertList();
        }catch(Exception ex){
            System.out.println("Exception returned from RiderAlertLookup() > getActiveAlerts(): " + ex); 
        }
        return aa;
    }
    
    /**
     * Get active alert by ID
     * @return
     */
    @GET
    @Produces("application/json")
    @Path("alerts/{alertEventId}")
    public ActiveAlertDTO getAlertByID(@Encoded @PathParam("alertEventId") String alertEventId) {
        ActiveAlertDTO ae = null;
        try{
            //System.out.println("alertEventId: " + alertEventId) ;
            ae = this.riderAlertService.getActiveAlertByID(alertEventId);
        }catch (Exception ex){
            System.out.println("Exception returned from RiderAlertLookup() > getAlertByID(): " + ex);
        }
        return ae;
    }
    
    /**
     * Get all routes associated to active alerts
     * @return
     */
    @GET
    @Produces("application/json")
    @Path("routes")
    public AlertRouteDTO getActiveAlertRoutes() {
        AlertRouteDTO ar = null;
        try{
            ar = this.riderAlertService.getActiveAlertRoutes();
        }catch(Exception ex){
            System.out.println("Exception returned from RiderAlertLookup() > getActiveAlertRoutes(): " + ex); 
        }
        return ar;
    }
    
    /**
     * Get alert route by ID
     * @return
     */
    @GET
    @Produces("application/json")
    @Path("routes/{routeId}")
    public AlertRouteDTO getAlertRouteByID(@Encoded @PathParam("routeId") String routeId) {
        AlertRouteDTO ar = null;
        try{
            ar = this.riderAlertService.getAlertRouteByID(routeId);
        }catch(Exception ex){
            System.out.println("Exception returned from RiderAlertLookup() > getAlertRouteByID(): " + ex); 
        }
        return ar;
    }
}

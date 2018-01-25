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
     *
     * @return
     */
    @GET
    @Produces("application/json")
    @Path("alerts")
    public ActiveAlertDTO getActiveAlerts() {
        return this.riderAlertService.getActiveAlertList();
    }
    
    /**
     *
     * @return
     */
    @GET
    @Produces("application/json")
    @Path("routes")
    public AlertRouteDTO getActiveAlertRoutes() {
        return this.riderAlertService.getActiveAlertRoutes();
    }
    
    /**
     *
     * @return
     */
    @GET
    @Produces("application/json")
    @Path("alerts/{alertURL}")
    public ActiveAlertDTO getAlertByID(@Encoded @PathParam("alertURL") String alertURL) {
        ActiveAlertDTO ae = null;
        try{
            //System.out.println("alertURL: " + alertURL);
            ae = this.riderAlertService.getActiveAlertByID(alertURL);
        }catch (Exception ex){
            System.out.println("Exception returned from RiderAlertLookup() > getAlertByID(): " + ex);
        }
        return ae;
    }
}

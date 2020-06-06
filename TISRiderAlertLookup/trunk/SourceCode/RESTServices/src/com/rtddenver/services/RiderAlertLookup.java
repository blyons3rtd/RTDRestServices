package com.rtddenver.services;

import com.rtddenver.model.dto.ActiveAlertEventDTO;
import com.rtddenver.model.dto.AlertEventDTO;
import com.rtddenver.model.dto.AlertEventRouteDTO;
import com.rtddenver.model.dto.RouteActiveAlertEventDTO;
import com.rtddenver.service.RiderAlertServiceLocal;

import java.io.IOException;

import javax.ejb.EJB;

import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@javax.enterprise.context.RequestScoped
@Path("v2")
public class RiderAlertLookup {
    private static final Logger LOGGER = LogManager.getLogger(RiderAlertLookup.class.getName());
    
    //@EJB(name = "RiderAlertService", beanInterface = com.rtddenver.service.RiderAlertServiceLocal.class, beanName = "EJBModel.jar#RiderAlertService")
    @EJB
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
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("alerts")
    public ActiveAlertEventDTO getActiveAlertEventList(@Context final HttpServletResponse response) {
        ActiveAlertEventDTO dto = null;
        
        LOGGER.info("getActiveAlertEventList...");
        
        try {
            dto = this.riderAlertService.getActiveAlertEventList();
        } catch (Exception e) {
            dto = new ActiveAlertEventDTO(500, "1950", "Unknown Server Error", e.getMessage());
            LOGGER.error("Error in RiderAlertLookup.getAlertEventById - calling session ejb", e);
        }
        
        if (dto.getErrorStatus() != null) {
            this.processErrorresponse(response, dto.getErrorStatus());
        }
        
        return dto;
    }
    
    /**
     * getAlertEventById
     * @return AlertEventDTO
     */
    @GET 
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("alerts/{alertEventId}")
    public AlertEventDTO getAlertEventById(@Encoded @PathParam("alertEventId") Integer alertEventId, @Context final HttpServletResponse response) {
        AlertEventDTO dto = null;
       
        LOGGER.info("getAlertEventById:" + alertEventId);

        try {
            dto = this.riderAlertService.getAlertEventById(alertEventId);
        } catch (Exception e) {
            dto = new AlertEventDTO(500, "1950", "Unknown Server Error", e.getMessage());
            LOGGER.error("Error in RiderAlertLookup.getAlertEventById - calling session ejb", e);
        }
       
        if (dto.getErrorStatus() != null) {
            this.processErrorresponse(response, dto.getErrorStatus());
        }
        
        return dto;
    }
    
    /**
     * getRoutesWithActiveAlerts
     * @return RouteActiveAlertEventDTO
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("routes")
    public RouteActiveAlertEventDTO getRoutesWithActiveAlerts(@Context final HttpServletResponse response) {
        RouteActiveAlertEventDTO dto = null;
        
        LOGGER.info("getRoutesWithActiveAlerts...");
        
        try {
            dto = this.riderAlertService.getRoutesActiveAlerts();
        } catch (Exception e) {
            dto = new RouteActiveAlertEventDTO(500, "1950", "Unknown Server Error", e.getMessage());
            LOGGER.error("Error in RiderAlertLookup.RouteActiveAlertEventDTO - calling session ejb", e);
        }
        
        if (dto.getErrorStatus() != null) {
            this.processErrorresponse(response, dto.getErrorStatus());
        }
        
        return dto;
    }
    
    /**
     * getAlertEventRouteByID
     * @return AlertRouteDTO
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("routes/{masterRoute}")
    public AlertEventRouteDTO getAlertEventRouteByMasterRoute(@Encoded @PathParam("masterRoute") String masterRoute, @Context final HttpServletResponse response) {
        AlertEventRouteDTO dto = null;
        
        LOGGER.info("getAlertEventRouteByMasterRoute:" + masterRoute);
        
        try {
            dto = this.riderAlertService.getAlertEventRouteByMasterRoute(masterRoute);
        } catch (Exception e) {
            dto = new AlertEventRouteDTO(500, "1950", "Unknown Server Error", e.getMessage());
            LOGGER.error("Error in RiderAlertLookup.RouteActiveAlertEventDTO - calling session ejb", e);
        }
        
        if (dto.getErrorStatus() != null) {
            this.processErrorresponse(response, dto.getErrorStatus());
        }
        
        return dto;
    }


    /**
     * processErrorresponse
     * @param response HttpServletResponse
     * @param errorStatus int
     */
    private void processErrorresponse(final HttpServletResponse response, int errorStatus) {
        int responseStatus = 0;

        switch (errorStatus) {
        case 400:
            responseStatus = Response.Status.BAD_REQUEST.getStatusCode();
            break;
        case 404:
            responseStatus = Response.Status.NOT_FOUND.getStatusCode();
            break;
        case 500:
            responseStatus = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
            break;
//        case 200:
//            responseStatus = Response.Status.OK.getStatusCode();
//            break;
        }

        try {
            response.setStatus(responseStatus);
            response.setContentType(MediaType.APPLICATION_JSON + ";charset=utf-8");
            response.getOutputStream().close();
            response.flushBuffer();
        } catch (IOException e) {
            LOGGER.error("Error in RiderAlertLookup.processErrorresponse - setting response code", e);
        }
    }
}

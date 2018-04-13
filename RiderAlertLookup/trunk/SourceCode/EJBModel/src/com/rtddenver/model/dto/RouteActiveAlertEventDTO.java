package com.rtddenver.model.dto;

import java.io.Serializable;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//***********************************************************
/* Description:
/*
/*
/* @author Van Tran
/* @version 1.0, 2/28/2018
*/
//***********************************************************
@XmlRootElement(name = "alertEventRoutes")
public class RouteActiveAlertEventDTO implements Serializable{
    @SuppressWarnings("compatibility:398784096148769172")
    private static final long serialVersionUID = 1L;   
    
    @XmlElement(name = "activeRoutesList")
    private List<AlertEventRouteDTO> activeRoutesList;

    @XmlElement(name = "Error")
    private ErrorDTO error = null;
    
    /**
     * RouteActiveAlertEventDTO
     */
    public RouteActiveAlertEventDTO() {
        super();
    }
    
    /**
     * RouteActiveAlertEventDTO
     * @param error ErrorDTO
     */
    public RouteActiveAlertEventDTO(ErrorDTO error) {
        this.error = error;
    }
    
    /**
     * setRoutesList
     * @param activeRoutesList List<AlertEventRouteDTO>
     */
    public void setRoutesList(List<AlertEventRouteDTO> activeRoutesList) {
        this.activeRoutesList = activeRoutesList;
    }

    /**
     * getActiveRoutesList
     * @return List<AlertEventRouteDTO>
     */
    public List<AlertEventRouteDTO> getActiveRoutesList() {
        return this.activeRoutesList;
    }
}

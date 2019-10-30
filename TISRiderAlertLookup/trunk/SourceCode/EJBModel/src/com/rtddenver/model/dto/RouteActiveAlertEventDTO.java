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
public class RouteActiveAlertEventDTO extends Error implements Serializable{
    @SuppressWarnings("compatibility:3914784784351126239")
    private static final long serialVersionUID = -4692786631415492754L;

    @XmlElement(name = "activeRoutesList")
    private List<AlertEventRouteDTO> activeRoutesList = null;

    /**
     * RouteActiveAlertEventDTO
     */
    public RouteActiveAlertEventDTO() {
        super();
    }
    
    /**
     * RouteActiveAlertEventDTO
     * param status Integer
     * @param code String
     * @param detail String
     * @param message String
     */
    public RouteActiveAlertEventDTO(Integer status, String code, String detail, String message) {
        super(status, code, detail, message);
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

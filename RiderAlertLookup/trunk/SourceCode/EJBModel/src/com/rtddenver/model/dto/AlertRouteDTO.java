package com.rtddenver.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "alertEventRoute")
public class AlertRouteDTO implements Serializable{
    @SuppressWarnings("compatibility:398784096148769172")
    private static final long serialVersionUID = 1L;

    @XmlElement(name = "routeType")
    private List<RouteTypeDTO> routeType;
    
    @XmlElement(name = "routeToAlertList")
    private List<StagingRouteDTO> routeToAlertList;
    
    @XmlElement(name = "alertRoutes")
    private List<RouteDTO> alertRoutes;
    
    @XmlElement(name = "routeToAlert")
    private List<RouteToAlertDTO> routeToAlert;

    @XmlElement(name = "Error")
    private ErrorDTO error = null;
    
    public AlertRouteDTO() {
        super();
    }
    
    public AlertRouteDTO(List<RouteDTO> routeDTO) {
        this.alertRoutes = routeDTO;
    }
    
    public void addRouteDTO(RouteDTO routeDTO) {
        if (this.alertRoutes == null) {
            this.alertRoutes = new ArrayList<RouteDTO>();
        }
        this.alertRoutes.add(routeDTO);
    }
    
    public List<RouteDTO> getRouteDTO() {
        return alertRoutes;
    }
    
    public void addRouteToAlertDTO(RouteToAlertDTO routeToAlertDTO) {
        if (this.routeToAlert == null) {
            this.routeToAlert = new ArrayList<RouteToAlertDTO>();
        }
        this.routeToAlert.add(routeToAlertDTO);
    }
    
    public void addStagingRouteDTO(StagingRouteDTO stagingRouteDTO) {
        if (this.routeToAlertList == null) {
            this.routeToAlertList = new ArrayList<StagingRouteDTO>();
        }
        this.routeToAlertList.add(stagingRouteDTO);
    }
    
    public void addBusRouteTypeDTO(RouteTypeDTO rteType) {
        if (this.routeType == null) {
            this.routeType = new ArrayList<RouteTypeDTO>();
        }
        this.routeType.add(rteType);
    }
    
    public AlertRouteDTO(ErrorDTO error) {
        this.error = error;
    }
    
    public void setError(ErrorDTO error) {
        this.error = error;
    } 
}

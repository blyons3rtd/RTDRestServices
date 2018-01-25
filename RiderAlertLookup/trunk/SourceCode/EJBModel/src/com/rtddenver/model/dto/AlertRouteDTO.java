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

    @XmlElement(name = "alertRoutes")
    private List<RouteDTO> alertRoutes;
    
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
    
    public AlertRouteDTO(ErrorDTO error) {
        this.error = error;
    }
    
    public void setError(ErrorDTO error) {
        this.error = error;
    }
}
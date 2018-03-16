package com.rtddenver.model.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "alertEventRoute")
public class AlertRouteDTO implements Serializable{
    @SuppressWarnings("compatibility:398784096148769172")
    private static final long serialVersionUID = 1L;   
    
    @XmlElement(name = "activeRoutesList")
    private List<AlertEventRouteDTO> activeRoutesList;

    @XmlElement(name = "Error")
    private ErrorDTO error = null;
    
    public AlertRouteDTO() {
        super();
    }
    
    public AlertRouteDTO(List<AlertEventRouteDTO> activeRoutesList) {
        this.activeRoutesList = activeRoutesList;
    }
    
    public void setRoutesList(List<AlertEventRouteDTO> activeRoutesList) {
        this.activeRoutesList = activeRoutesList;
    }

    public List<AlertEventRouteDTO> getActiveRoutesList() {
        return activeRoutesList;
    }
    
    public AlertRouteDTO(ErrorDTO error) {
        this.error = error;
    }
    
    public void setError(ErrorDTO error) {
        this.error = error;
    } 
}

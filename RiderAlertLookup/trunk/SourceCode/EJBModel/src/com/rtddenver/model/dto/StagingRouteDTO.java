package com.rtddenver.model.dto;

import com.rtddenver.model.data.AlertEventRoutes;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "stagingRouteList")
public class StagingRouteDTO implements Serializable {
    private static final long serialVersionUID = 1L;
        
    @XmlElement(name = "route")
    private String route;
    
    @XmlElement(name = "alertList")
    private List<AlertEventRoutes> alertList;
    
    @XmlElement(name = "Error")
    private ErrorDTO error = null;
    
    public StagingRouteDTO() {
        super();
    }
    
    public void setRouteId(String route) {
        this.route = route;
    }
    
    public void setAlertList(List<AlertEventRoutes> alertList) {
        this.alertList = alertList;
    }
    
    
    
    public StagingRouteDTO(ErrorDTO error) {
        this.error = error;
    }
    
    public void setError(ErrorDTO error) {
        this.error = error;
    }
}

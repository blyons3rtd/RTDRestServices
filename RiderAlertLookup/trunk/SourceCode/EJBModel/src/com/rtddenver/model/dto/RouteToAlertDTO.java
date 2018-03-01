package com.rtddenver.model.dto;

import com.rtddenver.model.data.AlertEventRoute;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "routeList")
public class RouteToAlertDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @XmlElement(name = "route")
    private Map<String, List<AlertEventRoute>> routeToAlertMap;
    
    @XmlElement(name = "Error")
    private ErrorDTO error = null;

    public RouteToAlertDTO() {
        super();
    }

    public void setRouteToAlertMap(Map<String, List<AlertEventRoute>> routeToAlertMap) {
        this.routeToAlertMap = routeToAlertMap;
    }
    
    public void setError(ErrorDTO error) {
        this.error = error;
    }
    
    public RouteToAlertDTO(ErrorDTO error) {
        this.error = error;
    }
}

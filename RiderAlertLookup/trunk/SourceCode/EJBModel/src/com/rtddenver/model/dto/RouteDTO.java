package com.rtddenver.model.dto;

import com.rtddenver.model.data.AlertEventRoutes;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "routes")
public class RouteDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @XmlElement(name = "alertEventId")
    private int alertEventId;
    
    @XmlElement(name = "routeList")
    private List<AlertEventRoutes> routeList;
        
    @XmlElement(name = "busList")
    private List<AlertEventRoutes> busList;
    
    @XmlElement(name = "railList")
    private List<AlertEventRoutes> railList;
    
    @XmlElement(name = "Error")
    private ErrorDTO error = null;
    
    public RouteDTO() {
        super();
    }
    public void setAlertEventId(int alertEventId) {
        this.alertEventId = alertEventId;
    }

    public void setRouteList(List<AlertEventRoutes> routeList) {
        this.routeList = routeList;
    }

    public List<AlertEventRoutes> getRouteList() {
        return routeList;
    }

    public void setBusList(List<AlertEventRoutes> busList) {
        this.busList = busList;
    }

    public void setRailList(List<AlertEventRoutes> railList) {
        this.railList = railList;
    }

    public List<AlertEventRoutes> getBusList() {
        return busList;
    }

    public List<AlertEventRoutes> getRailList() {
        return railList;
    }

    public void setError(ErrorDTO error) {
        this.error = error;
    }
    
    public RouteDTO(ErrorDTO error) {
        this.error = error;
    }
}

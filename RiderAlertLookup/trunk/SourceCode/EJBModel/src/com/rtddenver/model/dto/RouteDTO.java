package com.rtddenver.model.dto;

import com.rtddenver.model.data.AlertEventRoute;
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
    private List<AlertEventRoute> routeList;
        
    @XmlElement(name = "busList")
    private List<AlertEventRoute> busList;
    
    @XmlElement(name = "railList")
    private List<AlertEventRoute> railList;
    
    @XmlElement(name = "Error")
    private ErrorDTO error = null;
    
    public RouteDTO() {
        super();
    }
    public void setAlertEventId(int alertEventId) {
        this.alertEventId = alertEventId;
    }

    public void setRouteList(List<AlertEventRoute> routeList) {
        this.routeList = routeList;
    }

    public List<AlertEventRoute> getRouteList() {
        return routeList;
    }

    public void setBusList(List<AlertEventRoute> busList) {
        this.busList = busList;
    }

    public void setRailList(List<AlertEventRoute> railList) {
        this.railList = railList;
    }

    public List<AlertEventRoute> getBusList() {
        return busList;
    }

    public List<AlertEventRoute> getRailList() {
        return railList;
    }

    public void setError(ErrorDTO error) {
        this.error = error;
    }
    
    public RouteDTO(ErrorDTO error) {
        this.error = error;
    }
}

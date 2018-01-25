package com.rtddenver.model.dto;

import com.rtddenver.model.data.AlertEventRoutes;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "routes")	//@XmlRootElement(name = "alertEventRoutes")
public class RouteDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @XmlElement(name = "alertEventId")
    private int alertEventId;
        
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

    public void setBusList(List<AlertEventRoutes> busList) {
        this.busList = busList;
    }

    public void setRailList(List<AlertEventRoutes> railList) {
        this.railList = railList;
    }

    public void setError(ErrorDTO error) {
        this.error = error;
    }
    
    public RouteDTO(ErrorDTO error) {
        this.error = error;
    }
}

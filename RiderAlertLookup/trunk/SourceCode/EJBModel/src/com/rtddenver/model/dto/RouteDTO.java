package com.rtddenver.model.dto;

import com.rtddenver.model.data.AlertEventRoutes;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "alertEventRoutes")
public class RouteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    
    @XmlElement(name = "alertEventId")
    private int alertEventId;
        
    @XmlElement(name = "busRailList")
    private List<AlertEventRoutes> busRailList;
    
    @XmlElement(name = "Error")
    private ErrorDTO error = null;
    
    public RouteDTO() {
        super();
    }

    public RouteDTO(List<AlertEventRoutes> busRailList) {
        this.busRailList = busRailList;
    }
    
    public RouteDTO(ErrorDTO error) {
        this.error = error;
    }
}

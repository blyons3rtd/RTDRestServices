package com.rtddenver.model.dto;

import com.rtddenver.model.data.AlertEventRoute;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "routes")
public class RouteDTO implements Serializable {
    private static final long serialVersionUID = 1L;
        
    @XmlElement(name = "alertRoutes")
    private List<AlertEventRoute> alertRoutes;

    @XmlElement(name = "Error")
    private ErrorDTO error = null;
    
    public RouteDTO() {
        super();
    }

    public void setAlertRoutes(List<AlertEventRoute> alertRoutes) {
        this.alertRoutes = alertRoutes;
    }

    public List<AlertEventRoute> getAlertRoutes() {
        return alertRoutes;
    }

    public void setError(ErrorDTO error) {
        this.error = error;
    }
    
    public RouteDTO(ErrorDTO error) {
        this.error = error;
    }
}

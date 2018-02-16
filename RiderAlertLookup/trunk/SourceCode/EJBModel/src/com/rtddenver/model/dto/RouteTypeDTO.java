package com.rtddenver.model.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "routeTypeList")
public class RouteTypeDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @XmlElement(name = "busList")
    private String busList;
    
    @XmlElement(name = "busAlertURLList")
    private String busAlertURLList;
    
    @XmlElement(name = "railList")
    private String railList;

    @XmlElement(name = "railAlertURLList")
    private String railAlertURLList;
    
    @XmlElement(name = "Error")
    private ErrorDTO error = null;
    
    public RouteTypeDTO() {
        super();
    }

    public void setBusList(String busList) {
        this.busList = busList;
    }
    
    public void setRailList(String railList) {
        this.railList = railList;
    }

    public void setBusAlertURLList(String busAlertURLList) {
        this.busAlertURLList = busAlertURLList;
    }
    
    public void setRailAlertURLList(String railAlertURLList) {
        this.railAlertURLList = railAlertURLList;
    }

    public void setError(ErrorDTO error) {
        this.error = error;
    }
    
    public RouteTypeDTO(ErrorDTO error) {
        this.error = error;
    }
}

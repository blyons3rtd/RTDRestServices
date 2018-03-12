package com.rtddenver.model.dto;

import com.rtddenver.model.data.AlertEventRoutesDirection;
import com.rtddenver.model.data.AlertEvent;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "alertEventRoutesDirection")
public class RoutesDirectionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

//    @XmlElement(name = "activeAlertDetail")
//    private List<AlertEvents> activeAlertDetail;
        
    @XmlElement(name = "routesDirectionDetail")
    private List<AlertEventRoutesDirection> routesDirectionDetail;
    
    @XmlElement(name = "Error")
    private ErrorDTO error = null;
    
    public RoutesDirectionDTO() {
        super();
    }
    
//    public void setActiveAlertDetail(List<AlertEvents> activeAlertDetail) {
//        this.activeAlertDetail = activeAlertDetail;
//    }
//
//    public List<AlertEvents> getActiveAlertDetail() {
//        return activeAlertDetail;
//    }
    
    public void setRoutesDirectionDetail(List<AlertEventRoutesDirection> routesDirectionDetail) {
        this.routesDirectionDetail = routesDirectionDetail;
    }
    
    public List<AlertEventRoutesDirection> getRoutesDirectionDetail() {
        return routesDirectionDetail;
    }
    
    public RoutesDirectionDTO(ErrorDTO error) {
        this.error = error;
    }
    
//    public void setError(ErrorDTO error) {
//        this.error = error;
//    }
}

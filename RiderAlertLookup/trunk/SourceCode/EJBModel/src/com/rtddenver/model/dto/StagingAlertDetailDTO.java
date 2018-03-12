package com.rtddenver.model.dto;

import com.rtddenver.model.data.AlertEventRoutesDirection;
import com.rtddenver.model.data.AlertEvent;

import java.io.Serializable;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "stagingAlertDetailList")
public class StagingAlertDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @XmlElement(name = "alertDetail")
    private List<AlertEvent> alertDetail;
    
    @XmlElement(name = "routeDirectionDetail")
    private List<AlertEventRoutesDirection> routesDirectionList;
    
    @XmlElement(name = "Error")
    private ErrorDTO error = null;
    
    public StagingAlertDetailDTO() {
        super();
    }
    
    public void setAlertDetail(List<AlertEvent> alertDetail) {
        this.alertDetail = alertDetail;
    }

    public List<AlertEvent> getAlertDetail() {
        return alertDetail;
    }
    
    public void setRouteDirectionDetail(List<AlertEventRoutesDirection> routesDirectionDTO) {
        this.routesDirectionList = routesDirectionDTO;
    }

    public List<AlertEventRoutesDirection> getRoutesDirectionList() {
        return routesDirectionList;
    }

    public StagingAlertDetailDTO(ErrorDTO error) {
        this.error = error;
    }
}

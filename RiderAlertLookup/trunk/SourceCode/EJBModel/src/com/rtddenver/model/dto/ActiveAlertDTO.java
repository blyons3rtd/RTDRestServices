package com.rtddenver.model.dto;

import java.io.Serializable;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "alertEvents")
public class ActiveAlertDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @XmlElement(name = "activeAlertList")
    private List<AlertEventDTO> activeAlertList;
    
    @XmlElement(name = "activeStationPNRAlertsList")
    private List<AlertEventDTO> activeStationPNRAlertsList;
    
    @XmlElement(name = "Error")
    private ErrorDTO error = null;
    
    public ActiveAlertDTO() {
        super();
    }

    public ActiveAlertDTO(List<AlertEventDTO> activeAlertList) {
        this.activeAlertList = activeAlertList;
    }
    
    public ActiveAlertDTO(ErrorDTO error) {
        this.error = error;
    }
    
    public void setActiveAlertList(List<AlertEventDTO> activeAlertList) {
        this.activeAlertList = activeAlertList;
    }

    public List<AlertEventDTO> getActiveAlertList() {
        return activeAlertList;
    }
    
    public void setActiveStationPNRAlertsList(List<AlertEventDTO> activeStationPNRAlertsList) {
        this.activeStationPNRAlertsList = activeStationPNRAlertsList;
    }

    public List<AlertEventDTO> getActiveStationPNRAlertsList() {
        return activeStationPNRAlertsList;
    }
}

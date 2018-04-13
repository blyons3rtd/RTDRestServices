package com.rtddenver.model.dto;

import java.io.Serializable;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//***********************************************************
/* Description:
/*
/*
/* @author Van Tran
/* @version 1.0, 2/28/2018
*/
//***********************************************************
@XmlRootElement(name = "alertEvents")
public class ActiveAlertEventDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @XmlElement(name = "activeAlertList")
    private List<AlertEventDTO> activeAlertList;
    
    @XmlElement(name = "activeStationPNRAlertsList")
    private List<AlertEventDTO> activeStationPNRAlertsList;
    
    @XmlElement(name = "Error")
    private ErrorDTO error = null;
    
    /**
     * ActiveAlertEventDTO
     */
    public ActiveAlertEventDTO() {
        super();
    }

    /**
     * ActiveAlertEventDTO
     * @param error ErrorDTO
     */
    public ActiveAlertEventDTO(ErrorDTO error) {
        this.error = error;
    }
    
    /**
     * ActiveAlertEventDTO
     * @param activeAlertList List<AlertEventDTO>
     */
    public ActiveAlertEventDTO(List<AlertEventDTO> activeAlertList) {
        this.activeAlertList = activeAlertList;
    }
    
    /**
     * setActiveAlertList
     * @param activeAlertList List<AlertEventDTO>
     */
    public void setActiveAlertList(List<AlertEventDTO> activeAlertList) {
        this.activeAlertList = activeAlertList;
    }

    /**
     * getActiveAlertList
     * @return List<AlertEventDTO>
     */
    public List<AlertEventDTO> getActiveAlertList() {
        return activeAlertList;
    }
    
    /**
     * setActiveStationPNRAlertsList
     * @param activeStationPNRAlertsList List<AlertEventDTO>
     */
    public void setActiveStationPNRAlertsList(List<AlertEventDTO> activeStationPNRAlertsList) {
        this.activeStationPNRAlertsList = activeStationPNRAlertsList;
    }

    /**
     * getActiveStationPNRAlertsList
     * @return List<AlertEventDTO>
     */
    public List<AlertEventDTO> getActiveStationPNRAlertsList() {
        return this.activeStationPNRAlertsList;
    }
}

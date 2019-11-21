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
@XmlRootElement(name = "activeAlertEvent")
public class ActiveAlertEventDTO extends Error implements Serializable {
    @SuppressWarnings("compatibility:-6078465124647831274")
    private static final long serialVersionUID = 5386397361097295927L;

    @XmlElement(name = "activeAlertList")
    private List<AlertEventDTO> activeAlertList = null;
    
    @XmlElement(name = "activeStationPNRAlertsList")
    private List<AlertEventDTO> activeStationPNRAlertsList = null;
    
    @XmlElement(name = "activeSystemwideBusAlertsList")
    private List<AlertEventDTO> activeSystemwideBusAlertsList = null;
    
    @XmlElement(name = "activeSystemwideRailAlertsList")
    private List<AlertEventDTO> activeSystemwideRailAlertsList = null;
    
    /**
     * ActiveAlertEventDTO
     */
    public ActiveAlertEventDTO() {
        super();
    }

    /**
     * ActiveAlertEventDTO
     * param status Integer
     * @param code String
     * @param detail String
     * @param message String
     */
    public ActiveAlertEventDTO(Integer status, String code, String detail, String message) {
        super(status, code, detail, message);
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
    
    /**
     * setActiveSystemwideBusAlertsList
     * @param activeSystemwideBusAlertsList List<AlertEventDTO>
     */
    public void setActiveSystemwideBusAlertsList(List<AlertEventDTO> activeSystemwideBusAlertsList) {
        this.activeSystemwideBusAlertsList = activeSystemwideBusAlertsList;
    }

    /**
     * getActiveSystemwideBusAlertsList
     * @return List<AlertEventDTO>
     */
    public List<AlertEventDTO> getActiveSystemwideBusAlertsList() {
        return this.activeSystemwideBusAlertsList;
    }
    
    /**
     * setActiveSystemwideRailAlertsList
     * @param activeSystemwideRailAlertsList List<AlertEventDTO>
     */
    public void setActiveSystemwideRailAlertsList(List<AlertEventDTO> activeSystemwideRailAlertsList) {
        this.activeSystemwideRailAlertsList = activeSystemwideRailAlertsList;
    }

    /**
     * getActiveSystemwideRailAlertsList
     * @return List<AlertEventDTO>
     */
    public List<AlertEventDTO> getActiveSystemwideRailAlertsList() {
        return this.activeSystemwideRailAlertsList;
    }
}

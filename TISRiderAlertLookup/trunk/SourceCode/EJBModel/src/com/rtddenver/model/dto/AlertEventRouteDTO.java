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
@XmlRootElement(name = "alertEventRoute")
public class AlertEventRouteDTO extends Error implements Serializable {
    @SuppressWarnings("compatibility:-6777267865955981041")
    private static final long serialVersionUID = -2528409613740348776L;

    @XmlElement(name = "masterRoute")
    private String masterRoute = null;
    
    @XmlElement(name = "routeId")
    private String routeId = null;
    
    @XmlElement(name = "routeType")
    private String routeType = null;
        
    @XmlElement(name = "activeAlertList")
    private List<AlertEventDTO> activeAlertList = null;
    
    /**
     * AlertEventRouteDTO
     */
    public AlertEventRouteDTO() {
        super();
    }
    
    /**
     * AlertEventRouteDTO
     * param status Integer
     * @param code String
     * @param detail String
     * @param message String
     */
    public AlertEventRouteDTO(Integer status, String code, String detail, String message) {
        super(status, code, detail, message);
    }
 
    /**
     * setDTOValues
     * @param dto
     */
    public void setDTOValues(AlertEventRouteDTO dto) {
        this.masterRoute = dto.masterRoute;
        this.routeId = dto.routeId;
        this.routeType = dto.routeType;
    }
    
    /**
     * AlertEventRouteDTO
     * @param Builder builder
     */
    public AlertEventRouteDTO(Builder builder) {
        this.masterRoute = builder.masterRoute;
        this.routeId = builder.routeId;
        this.routeType = builder.routeType;
        this.activeAlertList = builder.activeAlertList;
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
        return this.activeAlertList;
    }

    /**
     * Builder
     */
    public static class Builder {
        private int alertEventId = 0;
        private int alertEventRoutesId = 0;
        private String masterRoute = null;
        private String routeId = null;
        private String routeType = null;
        private List<AlertEventDTO> activeAlertList;
        
        public Builder() {
        }
        
        /**
         * alertEventId
         * @param alertEventId int
         * @return Builder
         */
        public Builder alertEventId(int alertEventId) {
            this.alertEventId = alertEventId;
            return this;
        }
        
        /**
         * alertEventRoutesId
         * @param alertEventRoutesId int
         * @return Builder
         */
        public Builder alertEventRoutesId(int alertEventRoutesId) {
            this.alertEventRoutesId = alertEventRoutesId;
            return this;
        }
        
        /**
         * masterRoute
         * @param masterRoute String
         * @return Builder
         */
        public Builder masterRoute(String masterRoute) {
            this.masterRoute = masterRoute;
            return this;
        }
        
        /**
         * routeId
         * @param routeId String
         * @return Builder
         */
        public Builder routeId(String routeId) {
            this.routeId = routeId;
            return this;
        }
        
        /**
         * routeTypeName
         * @param routeTypeName String
         * @return Builder
         */
        public Builder routeType(String routeType) {
            this.routeType = routeType;
            return this;
        }
        
        /**
         * activeAlertList
         * @param activeAlertList List
         * @return Builder
         */
        public Builder activeAlertList(List<AlertEventDTO> activeAlertList) {
            this.activeAlertList = activeAlertList;
            return this;
        }     
        
        public AlertEventRouteDTO build() {
            return new AlertEventRouteDTO(this);
        }
    }
}

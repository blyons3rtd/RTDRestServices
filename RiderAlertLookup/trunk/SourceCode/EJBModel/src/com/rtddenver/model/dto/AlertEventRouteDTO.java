package com.rtddenver.model.dto;

import java.io.Serializable;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

//***********************************************************
/* Description:
/*
/*
/* @author Van Tran
/* @version 1.0, 2/28/2018
*/
//***********************************************************
public class AlertEventRouteDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @XmlElement(name = "masterRoute")
    private String masterRoute = null;
    @XmlElement(name = "routeId")
    private String routeId = null;
    @XmlElement(name = "routeType")
    private String routeType = null;
    @XmlElement(name = "routesDirectionList")
    private List<AlertEventRouteDirectionDTO> routesDirectionList;
    @XmlElement(name = "activeAlertList")
    private List<AlertEventDTO> activeAlertList;
    @XmlElement(name = "Error")
    private ErrorDTO error = null;
    
    /**
     * AlertEventRouteDTO
     */
    public AlertEventRouteDTO() {
        super();
    }
    
    /**
     * AlertEventRouteDTO
     * @param ErrorDTO error
     */
    public AlertEventRouteDTO(ErrorDTO error) {
        this.error = error;
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
        //this.alertEventRoutesId = builder.alertEventRoutesId;
        this.masterRoute = builder.masterRoute;
        this.routeId = builder.routeId;
        this.routeType = builder.routeType;
        this.routesDirectionList = builder.routesDirectionList;
        this.activeAlertList = builder.activeAlertList;
    }

    /**
     * setRoutesDirectionList
     * @param routesDirectionList List<AlertEventRouteDirectionDTO>
     */
    public void setRoutesDirectionList(List<AlertEventRouteDirectionDTO> routesDirectionList) {
        this.routesDirectionList = routesDirectionList;
    }

    /**
     * getRoutesDirectionList
     * @return List<AlertEventRouteDirectionDTO>
     */
    public List<AlertEventRouteDirectionDTO> getRoutesDirectionList() {
        return this.routesDirectionList;
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
        private List<AlertEventRouteDirectionDTO> routesDirectionList;
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
         * routesDirectionList
         * @param routesDirectionList List
         * @return Builder
         */
        public Builder routesDirectionList(List<AlertEventRouteDirectionDTO> routesDirectionList) {
            this.routesDirectionList = routesDirectionList;
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

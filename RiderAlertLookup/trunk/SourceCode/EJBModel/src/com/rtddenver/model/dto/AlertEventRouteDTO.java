package com.rtddenver.model.dto;

import java.io.Serializable;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class AlertEventRouteDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @XmlElement(name = "alertEventId")
    private int alertEventId = 0;
    @XmlElement(name = "alertEventRoutesId")
    private int alertEventRoutesId = 0;
    @XmlElement(name = "masterRoute")
    private String masterRoute = null;
    @XmlElement(name = "routeId")
    private String routeId = null;
    @XmlElement(name = "routeType")
    private String routeType = null;
    @XmlElement(name = "routesDirectionList")
    private List<AlertEventRoutesDirectionDTO> routesDirectionList;
    @XmlElement(name = "activeAlertList")
    private List<AlertEventDTO> activeAlertList;
    
    /**
     * AlertEventRouteDTO
     */
    public AlertEventRouteDTO() {
        super();
    }
    
    /**
     * AlertEventRouteDTO
     * @param Builder builder
     */
    public AlertEventRouteDTO(Builder builder) {
        this.alertEventId = builder.alertEventId;
        this.alertEventRoutesId = builder.alertEventRoutesId;
        this.masterRoute = builder.masterRoute;
        this.routeId = builder.routeId;
        this.routeType = builder.routeType;
        this.routesDirectionList = builder.routesDirectionList;
        this.activeAlertList = builder.activeAlertList;
    }
    
    public static class Builder {
        private int alertEventId = 0;
        private int alertEventRoutesId = 0;
        private String masterRoute = null;
        private String routeId = null;
        private String routeType = null;
        private List<AlertEventRoutesDirectionDTO> routesDirectionList;
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
        public Builder routesDirectionList(List<AlertEventRoutesDirectionDTO> routesDirectionList) {
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

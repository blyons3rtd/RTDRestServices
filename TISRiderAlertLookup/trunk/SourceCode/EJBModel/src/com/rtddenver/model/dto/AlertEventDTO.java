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
@XmlRootElement(name = "alertEvent")
public class AlertEventDTO extends Error implements Serializable {
    @SuppressWarnings("compatibility:-6957427777127909931")
    private static final long serialVersionUID = -4529906424785384877L;

    @XmlElement(name = "alertEventId")
    private Integer alertEventId = null;
    
    @XmlElement(name = "alertType")
    private String alertType = null;
    
    @XmlElement(name = "alertCategoryDetail")
    private String alertCategoryDetail = null;
    
    @XmlElement(name = "alertEventRouteLnAffected")
    private String alertEventRouteLnAffected = null;
    
    @XmlElement(name = "alertEventStartDate")
    private String alertEventStartDate = null;
    
    @XmlElement(name = "alertEventEndDate")
    private String alertEventEndDate = null;
    
    @XmlElement(name = "alertEventInfo")
    private String alertEventInfo = null;
    
    @XmlElement(name = "alertRoutesList")
    private List<AlertEventRouteDTO> alertRoutesList = null;
    
    @XmlElement(name = "otherRouteLnAffected")
    private String otherRouteLnAffected = null;
       
    /**
     * AlertEventDTO
     */
    public AlertEventDTO() {
        super();
    }

    /**
     * AlertEventDTO
     * param status Integer
     * @param code String
     * @param detail String
     * @param message String
     */
    public AlertEventDTO(Integer status, String code, String detail, String message) {
        super(status, code, detail, message);
    }

    /**
     * AlertEventDTO
     * @param builder Builder
     */
    public AlertEventDTO(Builder builder) {
        this.alertEventId = builder.alertEventId;
        this.alertType = builder.alertType;
        this.alertCategoryDetail = builder.alertCategoryDetail;
        this.alertEventRouteLnAffected = builder.alertEventRouteLnAffected;
        this.alertEventStartDate = builder.alertEventStartDate;
        this.alertEventEndDate = builder.alertEventEndDate;
        this.alertEventInfo = builder.alertEventInfo;
        this.alertRoutesList = builder.alertRoutesList;
        this.otherRouteLnAffected = builder.otherRouteLnAffected;
    }

    /**
     * setAlertRoutesList
     * @param alertRoutesList List<AlertEventRouteDTO>
     */
    public void setAlertRoutesList(List<AlertEventRouteDTO> alertRoutesList) {
        this.alertRoutesList = alertRoutesList;
    }

    /**
     * Builder
     */
    public static class Builder {
        private static final long serialVersionUID = 1L;
        private int alertEventId = 0;
        private String alertType = null;
        private String alertCategory = null;
        private String alertCategoryDetail = null;
        private String alertEventRouteLnAffected = null;
        private String alertEventStartDate = null;
        private String alertEventEndDate = null;
        private String alertEventInfo = null;
        private List<AlertEventRouteDTO> alertRoutesList;
        private String otherRouteLnAffected;

        public Builder() {
        }
        
        public Builder alertEventId(int alertEventId) {
            this.alertEventId = alertEventId;
            return this;
        }
        
        public Builder alertType(String alertType) {
            this.alertType = alertType;
            return this;
        }
        
        public Builder alertCategory(String alertCategory) {
            this.alertCategory = alertCategory;
            return this;
        }
        
        public Builder alertCategoryDetail(String alertCategoryDetail) {
            this.alertCategoryDetail = alertCategoryDetail;
            return this;
        }
        
        public Builder alertEventRouteLnAffected(String alertEventRouteLnAffected) {
            this.alertEventRouteLnAffected = alertEventRouteLnAffected;
            return this;
        }

        public Builder alertEventStartDate(String alertEventStartDate) {
            this.alertEventStartDate = alertEventStartDate;
            return this;
        }
        
        public Builder alertEventEndDate(String alertEventEndDate) {
            this.alertEventEndDate = alertEventEndDate;
            return this;
        }
        
        public Builder alertEventInfo(String alertEventInfo) {
            this.alertEventInfo = alertEventInfo;
            return this;
        }
        
        public Builder alertRoutesList(List<AlertEventRouteDTO> alertRoutesList) {
            this.alertRoutesList = alertRoutesList;
            return this;
        }
        
        public Builder otherRouteLnAffected(String otherRouteLnAffected) {
            this.otherRouteLnAffected = otherRouteLnAffected;
            return this;
        }
        
        public AlertEventDTO build() {
            return new AlertEventDTO(this);
        }
    }
}

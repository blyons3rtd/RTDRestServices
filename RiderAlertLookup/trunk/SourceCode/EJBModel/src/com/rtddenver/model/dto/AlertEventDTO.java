package com.rtddenver.model.dto;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class AlertEventDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @XmlElement(name = "alertEventId")
    private int alertEventId = 0;
    @XmlElement(name = "alertType")
    private String alertType = null;
    @XmlElement(name = "alertCategoryDetail")
    private String alertCategoryDetail = null;
    @XmlElement(name = "alertEventRouteLnAffected")
    private String alertEventRouteLnAffected = null;
    @XmlElement(name = "alertEventStartDate")
    private Date alertEventStartDate = null;
    @XmlElement(name = "alertEventStartTimeType")
    private int alertEventStartTimeType = 0;
    @XmlElement(name = "alertEventEndDate")
    private Date alertEventEndDate = null;
    @XmlElement(name = "alertEventEndTimeType")
    private int alertEventEndTimeType = 0;
    @XmlElement(name = "alertEventInfo")
    private String alertEventInfo = null;
    @XmlElement(name = "alertRoutesList")
    private List<AlertEventRouteDTO> alertRoutesList;

    /**
     * AlertEventDTO
     */
    public AlertEventDTO() {
        super();
    }

    /**
     * AlertEventDTO
     * @param Builder builder
     */
    public AlertEventDTO(Builder builder) {
        this.alertEventId = builder.alertEventId;
        this.alertType = builder.alertType;
        this.alertCategoryDetail = builder.alertCategoryDetail;
        this.alertEventRouteLnAffected = builder.alertEventRouteLnAffected;
        this.alertEventStartDate = builder.alertEventStartDate;
        this.alertEventStartTimeType = builder.alertEventStartTimeType;
        this.alertEventEndDate = builder.alertEventEndDate;
        this.alertEventEndTimeType = builder.alertEventEndTimeType;
        this.alertEventInfo = builder.alertEventInfo;
        this.alertRoutesList = builder.alertRoutesList;
    }

    public static class Builder {
        private static final long serialVersionUID = 1L;
        private int alertEventId = 0;
        private String alertType = null;
        private String alertCategoryDetail = null;
        private String alertEventRouteLnAffected = null;
        private Date alertEventStartDate = null;
        private int alertEventStartTimeType = 0;
        private Date alertEventEndDate = null;
        private int alertEventEndTimeType = 0;
        private String alertEventInfo = null;
        private List<AlertEventRouteDTO> alertRoutesList;

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
        
        public Builder alertCategoryDetail(String alertCategoryDetail) {
            this.alertCategoryDetail = alertCategoryDetail;
            return this;
        }
        
        public Builder alertEventRouteLnAffected(String alertEventRouteLnAffected) {
            this.alertEventRouteLnAffected = alertEventRouteLnAffected;
            return this;
        }

        public Builder alertEventStartDate(Date alertEventStartDate) {
            this.alertEventStartDate = alertEventStartDate;
            return this;
        }

        public Builder alertEventStartTimeType(int alertEventStartTimeType) {
            this.alertEventStartTimeType = alertEventStartTimeType;
            return this;
        }
        
        public Builder alertEventEndDate(Date alertEventEndDate) {
            this.alertEventEndDate = alertEventEndDate;
            return this;
        }

        public Builder alertEventEndTimeType(int alertEventEndTimeType) {
            this.alertEventEndTimeType = alertEventEndTimeType;
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
        
        public AlertEventDTO build() {
            return new AlertEventDTO(this);
        }
    }
}

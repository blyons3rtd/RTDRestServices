package com.rtddenver.model.dto;

import java.io.Serializable;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class AlertEventDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @XmlElement(name = "alertCategoryDetail")
    private String alertCategoryDetail = null;
    @XmlElement(name = "alertCategoryId")
    private int alertCategoryId = 0;
    @XmlElement(name = "alertEventCreatedBy")
    private String alertEventCreatedBy = null;
    @XmlElement(name = "alertEventCreatedDate")
    private Date alertEventCreatedDate = null;
    @XmlElement(name = "alertEventDisplayFlag")
    private int alertEventDisplayFlag = 0;
    @XmlElement(name = "alertEventEffEndDate")
    private Date alertEventEffEndDate = null;
    @XmlElement(name = "alertEventEffStartDate")
    private Date alertEventEffStartDate = null;
    @XmlElement(name = "alertEventEmailBlast")
    private int alertEventEmailBlast = 0;
    @XmlElement(name = "alertEventEmailIntFlag")
    private int alertEventEmailIntFlag = 0;
    @XmlElement(name = "alertEventEmailTopic")
    private String alertEventEmailTopic = null;
    @XmlElement(name = "alertEventEndDate")
    private Date alertEventEndDate = null;
    @XmlElement(name = "alertEventEndTimeType")
    private int alertEventEndTimeType = 0;
    @XmlElement(name = "alertEventGeneralDesc")
    private String alertEventGeneralDesc = null;
    @XmlElement(name = "alertEventId")
    private int alertEventId = 0;
    @XmlElement(name = "alertEventInfo")
    private String alertEventInfo = null;
    @XmlElement(name = "alertEventOperatorInfo")
    private String alertEventOperatorInfo = null;
    @XmlElement(name = "alertEventReason")
    private String alertEventReason = null;
    @XmlElement(name = "alertEventRouteLnAffected")
    private String alertEventRouteLnAffected = null;
    @XmlElement(name = "alertEventStartDate")
    private Date alertEventStartDate = null;
    @XmlElement(name = "alertEventStartTimeType")
    private int alertEventStartTimeType = 0;
    @XmlElement(name = "alertEventUpdatedBy")
    private String alertEventUpdatedBy = null;
    @XmlElement(name = "alertEventUpdatedDate")
    private Date alertEventUpdatedDate = null;
    @XmlElement(name = "alertEventUserNotes")
    private String alertEventUserNotes = null;
    @XmlElement(name = "alertTypeId")
    private int alertTypeId = 0;

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
        this.alertCategoryDetail = builder.alertCategoryDetail;
        this.alertCategoryId = builder.alertCategoryId;
        this.alertEventCreatedBy = builder.alertEventCreatedBy;
        this.alertEventCreatedDate = builder.alertEventCreatedDate;
        this.alertEventDisplayFlag = builder.alertEventDisplayFlag;
        this.alertEventEffEndDate = builder.alertEventEffEndDate;
        this.alertEventEffStartDate = builder.alertEventEffStartDate;
        this.alertEventEmailBlast = builder.alertEventEmailBlast;
        this.alertEventEmailIntFlag = builder.alertEventEmailIntFlag;
        this.alertEventEmailTopic = builder.alertEventEmailTopic;
        this.alertEventEndDate = builder.alertEventEndDate;
        this.alertEventEndTimeType = builder.alertEventEndTimeType;
        this.alertEventGeneralDesc = builder.alertEventGeneralDesc;
        this.alertEventId = builder.alertEventId;
        this.alertEventInfo = builder.alertEventInfo;
        this.alertEventOperatorInfo = builder.alertEventOperatorInfo;
        this.alertEventReason = builder.alertEventReason;
        this.alertEventRouteLnAffected = builder.alertEventRouteLnAffected;
        this.alertEventStartDate = builder.alertEventStartDate;
        this.alertEventStartTimeType = builder.alertEventStartTimeType;
        this.alertEventUpdatedBy = builder.alertEventUpdatedBy;
        this.alertEventUpdatedDate = builder.alertEventUpdatedDate;
        this.alertEventUserNotes = builder.alertEventUserNotes;
        this.alertTypeId = builder.alertTypeId;
    }

    public static class Builder {
        private static final long serialVersionUID = 1L;
        private String alertCategoryDetail = null;
        private int alertCategoryId = 0;
        private String alertEventCreatedBy = null;
        private Date alertEventCreatedDate = null;
        private int alertEventDisplayFlag = 0;
        private Date alertEventEffEndDate = null;
        private Date alertEventEffStartDate = null;
        private int alertEventEmailBlast = 0;
        private int alertEventEmailIntFlag = 0;
        private String alertEventEmailTopic = null;
        private Date alertEventEndDate = null;
        private int alertEventEndTimeType = 0;
        private String alertEventGeneralDesc = null;
        private int alertEventId = 0;
        private String alertEventInfo = null;
        private String alertEventOperatorInfo = null;
        private String alertEventReason = null;
        private String alertEventRouteLnAffected = null;
        private Date alertEventStartDate = null;
        private int alertEventStartTimeType = 0;
        private String alertEventUpdatedBy = null;
        private Date alertEventUpdatedDate = null;
        private String alertEventUserNotes = null;
        private int alertTypeId = 0;

        public Builder() {
        }

        public Builder alertCategoryDetail(String alertCategoryDetail) {
            this.alertCategoryDetail = alertCategoryDetail;
            return this;
        }

        public Builder alertCategoryId(int alertCategoryId) {
            this.alertCategoryId = alertCategoryId;
            return this;
        }

        public Builder alertEventCreatedBy(String alertEventCreatedBy) {
            this.alertEventCreatedBy = alertEventCreatedBy;
            return this;
        }

        public Builder alertEventCreatedDate(Date alertEventCreatedDate) {
            this.alertEventCreatedDate = alertEventCreatedDate;
            return this;
        }

        public Builder alertEventDisplayFlag(int alertEventDisplayFlag) {
            this.alertEventDisplayFlag = alertEventDisplayFlag;
            return this;
        }

        public Builder alertEventEffEndDate(Date alertEventEffEndDate) {
            this.alertEventEffEndDate = alertEventEffEndDate;
            return this;
        }

        public Builder alertEventEffStartDate(Date alertEventEffStartDate) {
            this.alertEventEffStartDate = alertEventEffStartDate;
            return this;
        }

        public Builder alertEventEmailBlast(int alertEventEmailBlast) {
            this.alertEventEmailBlast = alertEventEmailBlast;
            return this;
        }

        public Builder alertEventEmailIntFlag(int alertEventEmailIntFlag) {
            this.alertEventEmailIntFlag = alertEventEmailIntFlag;
            return this;
        }

        public Builder alertEventEmailTopic(String alertEventEmailTopic) {
            this.alertEventEmailTopic = alertEventEmailTopic;
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

        public Builder alertEventGeneralDesc(String alertEventGeneralDesc) {
            this.alertEventGeneralDesc = alertEventGeneralDesc;
            return this;
        }

        public Builder alertEventId(int alertEventId) {
            this.alertEventId = alertEventId;
            return this;
        }

        public Builder alertEventInfo(String alertEventInfo) {
            this.alertEventInfo = alertEventInfo;
            return this;
        }

        public Builder alertEventOperatorInfo(String alertEventOperatorInfo) {
            this.alertEventOperatorInfo = alertEventOperatorInfo;
            return this;
        }

        public Builder alertEventReason(String alertEventReason) {
            this.alertEventReason = alertEventReason;
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

        public Builder alertEventUpdatedBy(String alertEventUpdatedBy) {
            this.alertEventUpdatedBy = alertEventUpdatedBy;
            return this;
        }

        public Builder alertEventUpdatedDate(Date alertEventUpdatedDate) {
            this.alertEventUpdatedDate = alertEventUpdatedDate;
            return this;
        }

        public Builder alertEventUserNotes(String alertEventUserNotes) {
            this.alertEventUserNotes = alertEventUserNotes;
            return this;
        }

        public Builder alertTypeId(int alertTypeId) {
            this.alertTypeId = alertTypeId;
            return this;
        }
        
        public AlertEventDTO build() {
            return new AlertEventDTO(this);
        }
    }
}

package com.rtddenver.model.data;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({ @NamedQuery(name = "findAllActiveAlerts", query = "select o from AlertEvents o WHERE o.alertTypeId = 1 " +
                    "AND o.alertEventEffEndDate >= :alertDate " +
                    "AND o.alertEventEffStartDate <= :alertDate " +
                    "ORDER BY o.alertEventId DESC"),
                @NamedQuery(name = "findActiveAlertByID", query = "select o from AlertEvents o WHERE o.alertTypeId = 1 " +
                    "AND o.alertEventId = :alertID " +
                    "AND o.alertEventEffEndDate >= :alertDate " +
                    "AND o.alertEventEffStartDate <= :alertDate ") })
@Table(name = "ALERT_EVENTS", schema = "SCHEDLS")
public class AlertEvents implements Serializable {
    private static final long serialVersionUID = -6202385501726449589L;
    @Column(name = "ALERT_CATEGORY_DETAIL", length = 4000)
    private String alertCategoryDetail;
    @Column(name = "ALERT_CATEGORY_ID")
    private BigDecimal alertCategoryId;
    @Column(name = "ALERT_EVENT_CREATED_BY", length = 100)
    private String alertEventCreatedBy;
    @Temporal(TemporalType.DATE)
    @Column(name = "ALERT_EVENT_CREATED_DATE")
    private Date alertEventCreatedDate;
    @Column(name = "ALERT_EVENT_DISPLAY_FLAG")
    private BigDecimal alertEventDisplayFlag;
    @Temporal(TemporalType.DATE)
    @Column(name = "ALERT_EVENT_EFF_END_DATE")
    private Date alertEventEffEndDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "ALERT_EVENT_EFF_START_DATE")
    private Date alertEventEffStartDate;
    @Column(name = "ALERT_EVENT_EMAIL_BLAST")
    private BigDecimal alertEventEmailBlast;
    @Column(name = "ALERT_EVENT_EMAIL_INT_FLAG")
    private BigDecimal alertEventEmailIntFlag;
    @Column(name = "ALERT_EVENT_EMAIL_TOPIC", length = 4000)
    private String alertEventEmailTopic;
    @Temporal(TemporalType.DATE)
    @Column(name = "ALERT_EVENT_END_DATE")
    private Date alertEventEndDate;
    @Column(name = "ALERT_EVENT_END_TIME_TYPE")
    private BigDecimal alertEventEndTimeType;
    @Column(name = "ALERT_EVENT_GENERAL_DESC", length = 4000)
    private String alertEventGeneralDesc;
    @Id
    @Column(name = "ALERT_EVENT_ID", nullable = false)
    private BigDecimal alertEventId;
    @Column(name = "ALERT_EVENT_INFO")
    private String alertEventInfo;
    @Column(name = "ALERT_EVENT_OPERATOR_INFO")
    private String alertEventOperatorInfo;
    @Column(name = "ALERT_EVENT_REASON", length = 4000)
    private String alertEventReason;
    @Column(name = "ALERT_EVENT_ROUTE_LN_AFFECTED", length = 4000)
    private String alertEventRouteLnAffected;
    @Temporal(TemporalType.DATE)
    @Column(name = "ALERT_EVENT_START_DATE")
    private Date alertEventStartDate;
    @Column(name = "ALERT_EVENT_START_TIME_TYPE")
    private BigDecimal alertEventStartTimeType;
    @Column(name = "ALERT_EVENT_UPDATED_BY", length = 100)
    private String alertEventUpdatedBy;
    @Temporal(TemporalType.DATE)
    @Column(name = "ALERT_EVENT_UPDATED_DATE")
    private Date alertEventUpdatedDate;
    @Column(name = "ALERT_EVENT_USER_NOTES")
    private String alertEventUserNotes;
    @Column(name = "ALERT_TYPE_ID", nullable = false)
    private BigDecimal alertTypeId;

    public AlertEvents() {
    }

    public AlertEvents(String alertCategoryDetail, BigDecimal alertCategoryId, String alertEventCreatedBy,
                       Date alertEventCreatedDate, BigDecimal alertEventDisplayFlag, Date alertEventEffEndDate,
                       Date alertEventEffStartDate, BigDecimal alertEventEmailBlast, BigDecimal alertEventEmailIntFlag,
                       String alertEventEmailTopic, Date alertEventEndDate, BigDecimal alertEventEndTimeType,
                       String alertEventGeneralDesc, BigDecimal alertEventId, String alertEventInfo,
                       String alertEventOperatorInfo, String alertEventReason, String alertEventRouteLnAffected,
                       Date alertEventStartDate, BigDecimal alertEventStartTimeType, String alertEventUpdatedBy,
                       Date alertEventUpdatedDate, String alertEventUserNotes, BigDecimal alertTypeId) {
        this.alertCategoryDetail = alertCategoryDetail;
        this.alertCategoryId = alertCategoryId;
        this.alertEventCreatedBy = alertEventCreatedBy;
        this.alertEventCreatedDate = alertEventCreatedDate;
        this.alertEventDisplayFlag = alertEventDisplayFlag;
        this.alertEventEffEndDate = alertEventEffEndDate;
        this.alertEventEffStartDate = alertEventEffStartDate;
        this.alertEventEmailBlast = alertEventEmailBlast;
        this.alertEventEmailIntFlag = alertEventEmailIntFlag;
        this.alertEventEmailTopic = alertEventEmailTopic;
        this.alertEventEndDate = alertEventEndDate;
        this.alertEventEndTimeType = alertEventEndTimeType;
        this.alertEventGeneralDesc = alertEventGeneralDesc;
        this.alertEventId = alertEventId;
        this.alertEventInfo = alertEventInfo;
        this.alertEventOperatorInfo = alertEventOperatorInfo;
        this.alertEventReason = alertEventReason;
        this.alertEventRouteLnAffected = alertEventRouteLnAffected;
        this.alertEventStartDate = alertEventStartDate;
        this.alertEventStartTimeType = alertEventStartTimeType;
        this.alertEventUpdatedBy = alertEventUpdatedBy;
        this.alertEventUpdatedDate = alertEventUpdatedDate;
        this.alertEventUserNotes = alertEventUserNotes;
        this.alertTypeId = alertTypeId;
    }

    public String getAlertCategoryDetail() {
        return alertCategoryDetail;
    }

    public void setAlertCategoryDetail(String alertCategoryDetail) {
        this.alertCategoryDetail = alertCategoryDetail;
    }

    public BigDecimal getAlertCategoryId() {
        return alertCategoryId;
    }

    public void setAlertCategoryId(BigDecimal alertCategoryId) {
        this.alertCategoryId = alertCategoryId;
    }

    public String getAlertEventCreatedBy() {
        return alertEventCreatedBy;
    }

    public void setAlertEventCreatedBy(String alertEventCreatedBy) {
        this.alertEventCreatedBy = alertEventCreatedBy;
    }

    public Date getAlertEventCreatedDate() {
        return alertEventCreatedDate;
    }

    public void setAlertEventCreatedDate(Date alertEventCreatedDate) {
        this.alertEventCreatedDate = alertEventCreatedDate;
    }

    public BigDecimal getAlertEventDisplayFlag() {
        return alertEventDisplayFlag;
    }

    public void setAlertEventDisplayFlag(BigDecimal alertEventDisplayFlag) {
        this.alertEventDisplayFlag = alertEventDisplayFlag;
    }

    public Date getAlertEventEffEndDate() {
        return alertEventEffEndDate;
    }

    public void setAlertEventEffEndDate(Date alertEventEffEndDate) {
        this.alertEventEffEndDate = alertEventEffEndDate;
    }

    public Date getAlertEventEffStartDate() {
        return alertEventEffStartDate;
    }

    public void setAlertEventEffStartDate(Date alertEventEffStartDate) {
        this.alertEventEffStartDate = alertEventEffStartDate;
    }

    public BigDecimal getAlertEventEmailBlast() {
        return alertEventEmailBlast;
    }

    public void setAlertEventEmailBlast(BigDecimal alertEventEmailBlast) {
        this.alertEventEmailBlast = alertEventEmailBlast;
    }

    public BigDecimal getAlertEventEmailIntFlag() {
        return alertEventEmailIntFlag;
    }

    public void setAlertEventEmailIntFlag(BigDecimal alertEventEmailIntFlag) {
        this.alertEventEmailIntFlag = alertEventEmailIntFlag;
    }

    public String getAlertEventEmailTopic() {
        return alertEventEmailTopic;
    }

    public void setAlertEventEmailTopic(String alertEventEmailTopic) {
        this.alertEventEmailTopic = alertEventEmailTopic;
    }

    public Date getAlertEventEndDate() {
        return alertEventEndDate;
    }

    public void setAlertEventEndDate(Date alertEventEndDate) {
        this.alertEventEndDate = alertEventEndDate;
    }

    public BigDecimal getAlertEventEndTimeType() {
        return alertEventEndTimeType;
    }

    public void setAlertEventEndTimeType(BigDecimal alertEventEndTimeType) {
        this.alertEventEndTimeType = alertEventEndTimeType;
    }

    public String getAlertEventGeneralDesc() {
        return alertEventGeneralDesc;
    }

    public void setAlertEventGeneralDesc(String alertEventGeneralDesc) {
        this.alertEventGeneralDesc = alertEventGeneralDesc;
    }

    public BigDecimal getAlertEventId() {
        return alertEventId;
    }

    public void setAlertEventId(BigDecimal alertEventId) {
        this.alertEventId = alertEventId;
    }

    public String getAlertEventInfo() {
        return alertEventInfo;
    }

    public void setAlertEventInfo(String alertEventInfo) {
        this.alertEventInfo = alertEventInfo;
    }

    public String getAlertEventOperatorInfo() {
        return alertEventOperatorInfo;
    }

    public void setAlertEventOperatorInfo(String alertEventOperatorInfo) {
        this.alertEventOperatorInfo = alertEventOperatorInfo;
    }

    public String getAlertEventReason() {
        return alertEventReason;
    }

    public void setAlertEventReason(String alertEventReason) {
        this.alertEventReason = alertEventReason;
    }

    public String getAlertEventRouteLnAffected() {
        return alertEventRouteLnAffected;
    }

    public void setAlertEventRouteLnAffected(String alertEventRouteLnAffected) {
        this.alertEventRouteLnAffected = alertEventRouteLnAffected;
    }

    public Date getAlertEventStartDate() {
        return alertEventStartDate;
    }

    public void setAlertEventStartDate(Date alertEventStartDate) {
        this.alertEventStartDate = alertEventStartDate;
    }

    public BigDecimal getAlertEventStartTimeType() {
        return alertEventStartTimeType;
    }

    public void setAlertEventStartTimeType(BigDecimal alertEventStartTimeType) {
        this.alertEventStartTimeType = alertEventStartTimeType;
    }

    public String getAlertEventUpdatedBy() {
        return alertEventUpdatedBy;
    }

    public void setAlertEventUpdatedBy(String alertEventUpdatedBy) {
        this.alertEventUpdatedBy = alertEventUpdatedBy;
    }

    public Date getAlertEventUpdatedDate() {
        return alertEventUpdatedDate;
    }

    public void setAlertEventUpdatedDate(Date alertEventUpdatedDate) {
        this.alertEventUpdatedDate = alertEventUpdatedDate;
    }

    public String getAlertEventUserNotes() {
        return alertEventUserNotes;
    }

    public void setAlertEventUserNotes(String alertEventUserNotes) {
        this.alertEventUserNotes = alertEventUserNotes;
    }

    public BigDecimal getAlertTypeId() {
        return alertTypeId;
    }

    public void setAlertTypeId(BigDecimal alertTypeId) {
        this.alertTypeId = alertTypeId;
    }
}

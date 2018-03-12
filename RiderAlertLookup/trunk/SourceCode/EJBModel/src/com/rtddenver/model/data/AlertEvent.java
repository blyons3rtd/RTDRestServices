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
import javax.persistence.Transient;

//***********************************************************
/* Description:
/*
/*
/* @author Van Tran
/* @version 1.0, 2/28/2018
*/
//***********************************************************
@Entity
@NamedQueries({ @NamedQuery(name = "findAllActiveAlerts",
                            query =
                            "select o from AlertEvent o WHERE o.alertTypeId = 1 " +
                            "AND o.alertEventEffEndDate >= :alertDate AND o.alertEventEffStartDate <= :alertDate ORDER BY o.alertEventId DESC"),
                @NamedQuery(name = "findActiveAlertByID",
                            query =
                            "select o from AlertEvent o WHERE o.alertTypeId = 1 " +
                            "AND o.alertEventId in :alertEventId AND o.alertEventEffEndDate >= :alertDate AND o.alertEventEffStartDate <= :alertDate " +
                            "ORDER BY o.alertEventStartDate DESC"),
                @NamedQuery(name = "findAllActiveStationPNRAlerts",
                            query =
                            "select o from AlertEvent o WHERE o.alertTypeId = 3 " +
                            "AND o.alertEventEffEndDate >= :alertDate AND o.alertEventEffStartDate <= :alertDate ORDER BY o.alertEventStartDate DESC")
    })
@Table(name = "ALERT_EVENTS", schema = "SCHEDLS")
public class AlertEvent implements Serializable {
    private static final long serialVersionUID = -6202385501726449589L;
    @Column(name = "ALERT_CATEGORY_DETAIL")
    private String alertCategoryDetail = null;
    @Column(name = "ALERT_CATEGORY_ID")
    private int alertCategoryId = 0;
    @Column(name = "ALERT_EVENT_CREATED_BY")
    private String alertEventCreatedBy = null;
    @Temporal(TemporalType.DATE)
    @Column(name = "ALERT_EVENT_CREATED_DATE")
    private Date alertEventCreatedDate = null;
    @Column(name = "ALERT_EVENT_DISPLAY_FLAG")
    private int alertEventDisplayFlag = 0;
    @Temporal(TemporalType.DATE)
    @Column(name = "ALERT_EVENT_EFF_END_DATE")
    private Date alertEventEffEndDate = null;
    @Temporal(TemporalType.DATE)
    @Column(name = "ALERT_EVENT_EFF_START_DATE")
    private Date alertEventEffStartDate = null;
    @Column(name = "ALERT_EVENT_EMAIL_BLAST")
    private int alertEventEmailBlast = 0;
    @Column(name = "ALERT_EVENT_EMAIL_INT_FLAG")
    private int alertEventEmailIntFlag = 0;
    @Column(name = "ALERT_EVENT_EMAIL_TOPIC")
    private String alertEventEmailTopic = null;
    @Temporal(TemporalType.DATE)
    @Column(name = "ALERT_EVENT_END_DATE")
    private Date alertEventEndDate = null;
    @Column(name = "ALERT_EVENT_END_TIME_TYPE")
    private int alertEventEndTimeType = 0;
    @Column(name = "ALERT_EVENT_GENERAL_DESC")
    private String alertEventGeneralDesc = null;
    @Id
    @Column(name = "ALERT_EVENT_ID")
    private int alertEventId = 0;
    @Column(name = "ALERT_EVENT_INFO")
    private String alertEventInfo = null;
    @Column(name = "ALERT_EVENT_OPERATOR_INFO")
    private String alertEventOperatorInfo = null;
    @Column(name = "ALERT_EVENT_REASON")
    private String alertEventReason = null;
    @Column(name = "ALERT_EVENT_ROUTE_LN_AFFECTED")
    private String alertEventRouteLnAffected = null;
    @Temporal(TemporalType.DATE)
    @Column(name = "ALERT_EVENT_START_DATE")
    private Date alertEventStartDate = null;
    @Column(name = "ALERT_EVENT_START_TIME_TYPE")
    private int alertEventStartTimeType;
    @Column(name = "ALERT_EVENT_UPDATED_BY")
    private String alertEventUpdatedBy = null;
    @Temporal(TemporalType.DATE)
    @Column(name = "ALERT_EVENT_UPDATED_DATE")
    private Date alertEventUpdatedDate = null;
    @Column(name = "ALERT_EVENT_USER_NOTES")
    private String alertEventUserNotes = null;
    @Column(name = "ALERT_TYPE_ID")
    private int alertTypeId = 0;
    @Transient
    private int tmp_alertEventRoutesId = 0;
    @Transient
    private String routeDirectionDetail1 = null;
    @Transient
    private String routeDirectionDetail2 = null;

    /**
     * AlertEvent
     */
    public AlertEvent() {
        super();
    }

    /**
     * getAlertCategoryDetail
     * @return String
     */
    public String getAlertCategoryDetail() {
        return alertCategoryDetail;
    }

    /**
     * getAlertCategoryId
     * @return int
     */
    public int getAlertCategoryId() {
        return alertCategoryId;
    }

    /**
     * @return
     */
    public String getAlertEventCreatedBy() {
        return alertEventCreatedBy;
    }

    /**
     * getAlertEventCreatedDate
     * @return Date
     */
    public Date getAlertEventCreatedDate() {
        return alertEventCreatedDate;
    }

    /**
     * getAlertEventDisplayFlag
     * @return int
     */
    public int getAlertEventDisplayFlag() {
        return alertEventDisplayFlag;
    }

    /**
     * getAlertEventEffEndDate
     * @return Date
     */
    public Date getAlertEventEffEndDate() {
        return alertEventEffEndDate;
    }

    /**
     * getAlertEventEffStartDate
     * @return Date
     */
    public Date getAlertEventEffStartDate() {
        return alertEventEffStartDate;
    }

    /**
     * getAlertEventEmailBlast
     * @return int
     */
    public int getAlertEventEmailBlast() {
        return alertEventEmailBlast;
    }

    /**
     * getAlertEventEmailIntFlag
     * @return int
     */
    public int getAlertEventEmailIntFlag() {
        return alertEventEmailIntFlag;
    }

    /**
     * getAlertEventEmailTopic
     * @return String
     */
    public String getAlertEventEmailTopic() {
        return alertEventEmailTopic;
    }

    /**
     * getAlertEventEndDate
     * @return Date
     */
    public Date getAlertEventEndDate() {
        return alertEventEndDate;
    }

    /**
     * getAlertEventEndTimeType
     * @return int
     */
    public int getAlertEventEndTimeType() {
        return alertEventEndTimeType;
    }

    /**
     * @return
     */
    public String getAlertEventGeneralDesc() {
        return alertEventGeneralDesc;
    }

    /**
     * getAlertEventId
     * @return int
     */
    public int getAlertEventId() {
        return alertEventId;
    }

    /**
     * getAlertEventInfo
     * @return String
     */
    public String getAlertEventInfo() {
        return alertEventInfo;
    }

    /**
     * getAlertEventOperatorInfo
     * @return String
     */
    public String getAlertEventOperatorInfo() {
        return alertEventOperatorInfo;
    }

    /**
     * getAlertEventReason
     * @return String
     */
    public String getAlertEventReason() {
        return alertEventReason;
    }

    /**
     * getAlertEventRouteLnAffected
     * @return String
     */
    public String getAlertEventRouteLnAffected() {
        return alertEventRouteLnAffected;
    }

    /**
     * getAlertEventStartDate
     * @return Date
     */
    public Date getAlertEventStartDate() {
        return alertEventStartDate;
    }

    /**
     * getAlertEventStartTimeType
     * @return int
     */
    public int getAlertEventStartTimeType() {
        return alertEventStartTimeType;
    }

    /**
     * getAlertEventUpdatedBy
     * @return String
     */
    public String getAlertEventUpdatedBy() {
        return alertEventUpdatedBy;
    }

    /**
     * getAlertEventUpdatedDate
     * @return Date
     */
    public Date getAlertEventUpdatedDate() {
        return alertEventUpdatedDate;
    }

    /**
     * getAlertEventUserNotes
     * @return String
     */
    public String getAlertEventUserNotes() {
        return alertEventUserNotes;
    }

    /**
     * getAlertTypeId
     * @return int
     */
    public int getAlertTypeId() {
        return alertTypeId;
    }

    /**
     * getTmp_alertEventRoutesId
     * @return int
     */
    public int getTmp_alertEventRoutesId() {
        return tmp_alertEventRoutesId;
    }

    /**
     * getRouteDirectionDetail1
     * @return String
     */
    public String getRouteDirectionDetail1() {
        return routeDirectionDetail1;
    }

    /**
     * getRouteDirectionDetail2
     * @return String
     */
    public String getRouteDirectionDetail2() {
        return routeDirectionDetail2;
    }

    /**
     * setTmp_alertEventRoutesId
     * @param int tmp_alertEventRoutesId
     */
    public void setTmp_alertEventRoutesId(int tmp_alertEventRoutesId) {
        this.tmp_alertEventRoutesId = tmp_alertEventRoutesId;
    }

    /**
     * setRouteDirectionDetail1
     * @param String routeDirectionDetail1
     */
    public void setRouteDirectionDetail1(String routeDirectionDetail1) {
        this.routeDirectionDetail1 = routeDirectionDetail1;
    }

    /**
     * setRouteDirectionDetail2
     * @param String routeDirectionDetail2
     */
    public void setRouteDirectionDetail2(String routeDirectionDetail2) {
        this.routeDirectionDetail2 = routeDirectionDetail2;
    }
}

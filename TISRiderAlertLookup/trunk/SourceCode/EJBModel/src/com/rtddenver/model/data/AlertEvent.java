package com.rtddenver.model.data;

import com.rtddenver.model.dto.AlertEventRouteDTO;

import com.rtddenver.service.RiderAlertServiceBean;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import java.util.logging.Logger;
import java.util.logging.LogManager;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.eclipse.persistence.annotations.ReadOnly;

//***********************************************************
/* Description:
/*
/*
/* @author Van Tran
/* @version 1.0, 2/28/2018
*/
//***********************************************************
@Entity
@Cacheable(value=false)
@ReadOnly
@NamedQueries({ 
                //Get active alerts for bus & rail
                                @NamedQuery(name = "findActiveEventAlerts",
                            query =
                            "select o from AlertEvent o WHERE o.alertTypeId = 1 " + 
                            "ORDER BY o.alertEventStartDate DESC"),
                @NamedQuery(name = "findActiveStationsWithActiveEventtAlerts",
                            query =
                            "select o from AlertEvent o WHERE o.alertTypeId = 3 " +
                            "ORDER BY o.alertEventStartDate DESC")
    })
@Table(name = "ALERT_EVENTS", schema = "RIDER_ALERT")
public class AlertEvent implements Serializable {
    @SuppressWarnings("compatibility:9166475870670687228")
    private static final long serialVersionUID = 6980798237182890443L;
    
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.LogManager.getLogger(AlertEvent.class.getName());

    @Column(name = "ALERT_EVENT_ID")
    @Id
    private int alertEventId = 0;
    @Column(name = "ALERT_CATEGORY_DETAIL")
    private String alertCategoryDetail = null;
    @Transient
    private int alertCategoryId = 0;
    @Temporal(TemporalType.DATE)
    @Column(name = "ALERT_EVENT_EFF_END_DATE")
    private Date alertEventEffEndDate = null;
    @Temporal(TemporalType.DATE)
    @Column(name = "ALERT_EVENT_EFF_START_DATE")
    private Date alertEventEffStartDate = null;
    @Column(name = "ALERT_EVENT_END_DATE")
    private String alertEventEndDate = null;
    @Column(name = "ALERT_EVENT_END_TIME_TYPE")
    private int alertEventEndTimeType = 0;
    @Column(name = "ALERT_EVENT_INFO")
    private String alertEventInfo = null;
    @Column(name = "ALERT_EVENT_ROUTE_LN_AFFECTED")
    private String alertEventRouteLnAffected = null;
    @Column(name = "ALERT_EVENT_START_DATE")
    private String alertEventStartDate = null;
    @Column(name = "ALERT_EVENT_START_TIME_TYPE")
    private int alertEventStartTimeType;
    @Column(name = "ALERT_TYPE_ID")
    private int alertTypeId = 0;

    @OneToMany (fetch=FetchType.LAZY)
    @JoinColumn(name="ALERT_EVENT_ID", insertable=false, updatable=false)
    private List<AlertEventRoute> alertEventRoutes = null;

    @Transient
    private String customAlertType = null;
    @Transient
    private String customAlertCategoryDetail = null;
    @Transient
    private List<AlertEventRouteDTO> alertRoutesList = null;
    @Transient
    private String otherRouteLnAffected = null;
    @Transient
    private String customAlertCategoryShortDesc = null;
    
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
     * setAlertCategoryDetail
     * @param alertCategoryDetail String
     */
    public void setAlertCategoryDetail(String alertCategoryDetail) {
        this.alertCategoryDetail = alertCategoryDetail;
    }

    /**
     * getAlertEventEffEndDate
     * @return Date
     */
    public Date getAlertEventEffEndDate() {
        return this.alertEventEffEndDate;
    }

    /**
     * getAlertEventEffStartDate
     * @return Date
     */
    public Date getAlertEventEffStartDate() {
        return this.alertEventEffStartDate;
    }

    /**
     * getAlertEventEndDate
     * @return String
     */
    public String getAlertEventEndDate() {
        return this.parseDate(alertEventEndDate, alertEventEndTimeType);
    }

    /**
     * getAlertEventEndTimeType
     * @return int
     */
    public int getAlertEventEndTimeType() {
        return this.alertEventEndTimeType;
    }

    /**
     * getAlertEventId
     * @return int
     */
    public int getAlertEventId() {
        return this.alertEventId;
    }

    /**
     * getAlertEventInfo
     * @return String
     */
    public String getAlertEventInfo() {
        return this.alertEventInfo;
    }

    /**
     * getAlertEventRouteLnAffected
     * @return String
     */
    public String getAlertEventRouteLnAffected() {
        return this.alertEventRouteLnAffected;
    }
    
    /**
     * getAlertEventStartDate
     * @return String
     */
    public String getAlertEventStartDate() {
        return this.parseDate(alertEventStartDate, alertEventStartTimeType);
    }
    
    /**
     * parseDate
     * @param inputDate String, timeType int
     * @return String
     */
    public String parseDate(String inputDate, int timeType) {
        LOGGER.info("inputDate:" + inputDate + ", timeType:" + timeType);
        String outputDate = "";
        try {
            DateFormat outputDateFormat = new SimpleDateFormat("MMMMM dd, yyy");
            DateFormat outputDateTimeFormat = new SimpleDateFormat("MMMMM dd, yyyy h:mma");
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String tmpInputDate = inputDate;
            Date date = inputFormat.parse(tmpInputDate);
            
            switch(timeType){
                case 2:
                    outputDate = outputDateFormat.format(date) + " TBD";
                    break;
                case 3:
                    outputDate = outputDateTimeFormat.format(date);
                    break;
                default:
                    outputDate = outputDateFormat.format(date);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
        LOGGER.info("outputDate:" + outputDate);
        return outputDate;
    }

    /**
     * getAlertEventStartTimeType
     * @return int
     */
    public int getAlertEventStartTimeType() {
        return alertEventStartTimeType;
    }

    /**
     * getAlertTypeId
     * @return int
     */
    public int getAlertTypeId() {
        return alertTypeId;
    }
    
    /**
     * getCustomAlertType
     * @return String
     */
    public String getCustomAlertType() {
        switch(this.alertTypeId){
            case 1:
                this.customAlertType = "Rider Alert"; 
                break;
            case 3:
                this.customAlertType = "Station/Park-n-Ride"; 
                break;
            default:
                this.customAlertType = Integer.toString(this.alertTypeId);
        }
        return this.customAlertType;
    }

    /**
     * getAlertRoutesList
     * @return List<AlertEventRouteDTO>
     */
    public List<AlertEventRouteDTO> getAlertRoutesList() {
        return alertRoutesList;
    }
    
    /**
     * setAlertRoutesLis
     * @param alertRoutesList List<AlertEventRouteDTO>
     */
    public void setAlertRoutesList(List<AlertEventRouteDTO> alertRoutesList) {
        this.alertRoutesList = alertRoutesList;
    }
    
    /**
     * getOtherRouteLnAffected
     * @return String
     */
    public String getOtherRouteLnAffected() {
        return otherRouteLnAffected;
    }

    /**
     * setOtherRouteLnAffected
     * @param otherRouteLnAffected String
     */
    public void setOtherRouteLnAffected(String otherRouteLnAffected) {
        this.otherRouteLnAffected = otherRouteLnAffected;
    }
    
    /**
     * getAlertEventRoutes
     * @return List<AlertEventRoute>
     */
    public List<AlertEventRoute> getAlertEventRoutes() {
        return this.alertEventRoutes;
    }

    /**
     * getAlertCategoryId
     * @return int
     */
    public int getAlertCategoryId() {
        return this.alertCategoryId;
    }
}

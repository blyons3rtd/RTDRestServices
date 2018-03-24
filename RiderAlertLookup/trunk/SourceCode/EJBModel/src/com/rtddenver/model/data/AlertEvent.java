package com.rtddenver.model.data;

import com.rtddenver.model.dto.AlertCategoryDTO;
import com.rtddenver.model.dto.AlertEventRouteDTO;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
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
@NamedQueries({ 
                //Get active alerts for bus & rail
                @NamedQuery(name = "findAllActiveAlerts",
                            query =
                            "select o from AlertEvent o WHERE o.alertTypeId = 1 " + 
                            "AND o.alertEventEffEndDate >= :alertDate AND o.alertEventEffStartDate <= :alertDate ORDER BY o.alertEventStartDate DESC"),
                //Get active alert by ID
                @NamedQuery(name = "findActiveAlertByID",
                            query =
                            "select o from AlertEvent o WHERE o.alertTypeId = 1 " +
                            "AND o.alertEventId = :alertEventId AND o.alertEventEffEndDate >= :alertDate AND o.alertEventEffStartDate <= :alertDate " +
                            "ORDER BY o.alertEventStartDate DESC"),
                //Get active alerts for Station/PNR
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
    @Id
    @Column(name = "ALERT_EVENT_ID")
    private int alertEventId = 0;
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
    @Transient
    private String alertType;
    @Transient
    private List<AlertCategoryDTO> alertCategory;
    @Transient
    private List<AlertEventRouteDTO> alertRoutesList;
    @Transient
    private String otherRouteLnAffected;

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
        switch(this.alertTypeId){
            case 3:
                switch(this.alertCategoryId){
                    case 1:
                        alertCategoryDetail = "Elevator outage at " + this.alertCategoryDetail;
                        break;
                    default:
                        alertCategoryDetail = this.alertCategoryDetail;
                }
                break;
            default:
                alertCategoryDetail = this.alertCategoryDetail;
        }
        return alertCategoryDetail;
    }
    
    public void setAlertCategoryDetail(String alertCategoryDetail) {
        this.alertCategoryDetail = alertCategoryDetail;
    }

    /**
     * getAlertCategoryId
     * @return int
     */
    public int getAlertCategoryId() {
        return alertCategoryId;
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
     * getAlertEventEndDate
     * @return String
     */
    public String getAlertEventEndDate() {
        return parseDate(alertEventEndDate, alertEventEndTimeType);
    }

    /**
     * getAlertEventEndTimeType
     * @return int
     */
    public int getAlertEventEndTimeType() {
        return alertEventEndTimeType;
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
     * getAlertEventRouteLnAffected
     * @return String
     */
    public String getAlertEventRouteLnAffected() {
        return alertEventRouteLnAffected;
    }
    
    /**
     * getAlertEventStartDate
     * @return String
     */
    public String getAlertEventStartDate() {
        return parseDate(alertEventStartDate, alertEventStartTimeType);
    }
    
    /**
     * parseDate
     * @param inputDate String, timeType int
     * @return String
     */
    public String parseDate(String inputDate, int timeType) {
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
    @PostLoad
    public void findAlertType() {
        switch(this.alertTypeId){
            case 1:
                alertType = "Rider Alert"; 
                break;
            case 3:
                alertType = "Station/Park-n-Ride"; 
                break;
            default:
                alertType = Integer.toString(this.alertTypeId);
        }
    }
    
    /**
     * getAlertType
     * @return String
     */
    public String getAlertType() {
        return alertType;
    }

    /**
     * getAlertRoutesList
     * @return List
     */
    public List<AlertEventRouteDTO> getAlertRoutesList() {
        return alertRoutesList;
    }
    
    public void setAlertRoutesList(List<AlertEventRouteDTO> alertRoutesList) {
        this.alertRoutesList = alertRoutesList;
    }
    
    /**
     * getAlertCategory
     * @return List
     */
    public List<AlertCategoryDTO> getAlertCategory() {
        return alertCategory;
    }
    
    public void setAlertCategory(List<AlertCategoryDTO> alertCategory) {
        this.alertCategory = alertCategory;
    }

    /**
     * getOtherRouteLnAffected
     * @return String
     */
    public String getOtherRouteLnAffected() {
        return otherRouteLnAffected;
    }

    public void setOtherRouteLnAffected(String otherRouteLnAffected) {
        this.otherRouteLnAffected = otherRouteLnAffected;
    }
}

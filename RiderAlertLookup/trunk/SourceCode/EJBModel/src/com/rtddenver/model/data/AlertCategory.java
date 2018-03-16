package com.rtddenver.model.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

//***********************************************************
/* Description:
/*
/*
/* @author Van Tran
/* @version 1.0, 3/6/2018
*/
//***********************************************************
@Entity
@NamedQueries({ @NamedQuery(name = "findAlertCategoryByID", query = "select o from AlertCategory o where o.alertCategoryId = :alertCategoryId") })
@Table(name = "ALERT_CATEGORY", schema = "SCHEDLS") 
//schema = "REP_IP"
public class AlertCategory implements Serializable {
    private static final long serialVersionUID = -6710120066645450643L;
    @Column(name = "ALERT_CATEGORY_DESCRIPTION")
    private String alertCategoryDescription;
    @Id
    @Column(name = "ALERT_CATEGORY_ID", nullable = false)
    private int alertCategoryId;
    @Column(name = "ALERT_CATEGORY_NAME", nullable = false, length = 50)
    private String alertCategoryName;
    @Column(name = "ALERT_CATEGORY_SHORT_DESC")
    private String alertCategoryShortDesc;
    @Transient
    private String alertCategory;

    /**
     * AlertCategory
     */
    public AlertCategory() {
    }
    
    public AlertCategory(int alertCategoryId, String alertCategoryShortDesc) {
        this.alertCategoryId = alertCategoryId;
        this.alertCategoryShortDesc = alertCategoryShortDesc;
    }

    public int getAlertCategoryId() {
        return alertCategoryId;
    }

    public void setAlertCategoryId(int alertCategoryId) {
        this.alertCategoryId = alertCategoryId;
    }
    
    @PostLoad
    public void findAlertCategory() {
        switch(this.alertCategoryId){
            case 2:
            case 6:
                alertCategory = alertCategoryShortDesc + " for ";
                break;
            case 8:
            case 7:
                alertCategory = "";
                break;
            default:
                alertCategory = alertCategoryShortDesc;
        }
    }

    public String getAlertCategory() {
        return alertCategory;
    }

    public String getAlertCategoryShortDesc() {
        return alertCategoryShortDesc;
    }

    public void setAlertCategoryShortDesc(String alertCategoryShortDesc) {
        this.alertCategoryShortDesc = alertCategoryShortDesc;
    }

//    /**
//     * AlertCategory
//     * @param Builder
//     */
//    private AlertCategory(Builder builder){
//        this.alertCategoryId = builder.alertCategoryId;
//        this.alertCategoryShortDesc = builder.alertCategoryShortDesc;
//    }
//    
//    /**
//     * getAlertCategoryId
//     * @return int
//     */
//    public int getAlertCategoryId() {
//        return alertCategoryId;
//    }
//
//    /**
//     * setAlertCategoryId
//     * @param alertCategoryId int
//     */
//    public void setAlertCategoryId(int alertCategoryId) {
//        this.alertCategoryId = alertCategoryId;
//    }
//    
//    /**
//     * getAlertCategoryShortDesc
//     * @return String
//     */
//    public String getAlertCategoryShortDesc() {
//        return alertCategoryShortDesc;
//    }
//
//    /**
//     * setAlertCategoryShortDesc
//     * @param alertCategoryShortDesc String
//     */
//    public void setAlertCategoryShortDesc(String alertCategoryShortDesc) {
//        this.alertCategoryShortDesc = alertCategoryShortDesc;
//    }
//    
//    //***********************************************************
//    /* Description: Builder pattern
//    /* AlertCategory a = new AlertCategory.Builder().alertCategoryId(1).build();
//    /*
//    /* @author Van Tran
//    /* @version 1.0, 3/6/2018
//     */
//    //***********************************************************
//    public static class Builder {
//        private int alertCategoryId = 0;
//        private String alertCategoryShortDesc = null;
//        
//        /**
//         * Builder
//         */
//        public Builder() {
//        }
//        
//        /**
//         * alertCategoryId
//         * @param alertCategoryId int
//         * @return Builder
//         */
//        public Builder alertCategoryId(int alertCategoryId) {
//            this.alertCategoryId = alertCategoryId;
//            return this;
//        }
//
//        /**
//         * alertCategoryShortDesc
//         * @param alertCategoryShortDesc String
//         * @return Builder
//         */
//        public Builder alertCategoryShortDesc(String alertCategoryShortDesc) {
//            this.alertCategoryShortDesc = alertCategoryShortDesc;
//            return this;
//        }
//    }    
}

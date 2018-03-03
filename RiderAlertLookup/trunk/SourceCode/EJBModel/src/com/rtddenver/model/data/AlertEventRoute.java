package com.rtddenver.model.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

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
@NamedQueries({ @NamedQuery(name = "findAllRoutes",
                            query =
                            "select o from AlertEventRoute o WHERE o.alertEventId in :alertEventIDList ORDER BY o.routeSequence"),
                @NamedQuery(name = "findRouteByID",
                            query =
                            "select o from AlertEventRoute o WHERE o.alertEventId in :alertID AND o.masterRoute = :masterRoute ORDER BY o.routeSequence")
    })
@Table(name = "ALERT_EVENT_ROUTES", schema = "SCHEDLS")
//schema = "REP_IP"
public class AlertEventRoute implements Serializable {
    private static final long serialVersionUID = -5065925130746093504L;
    @Column(name = "ALERT_EVENT_ID")
    private int alertEventId = 0;
    @Id
    @Column(name = "ALERT_EVENT_ROUTES_ID")
    private int alertEventRoutesId = 0;
    @Column(name = "MASTER_ROUTE")
    private String masterRoute = null;
    @Column(name = "ROUTE_ID")
    private String routeId = null;
    @Column(name = "ROUTE_SEQUENCE")
    private int routeSequence = 0;
    @Column(name = "ROUTE_TYPE_NAME")
    private String routeTypeName = null;
    @Transient
    private String alertURL = null;

    /**
     * AlertEventRoute
     */
    public AlertEventRoute() {
        super();
    }
    
    /**
     * AlertEventRoute
     * @param Builder
     */
    private AlertEventRoute(Builder builder) {
        this.alertEventId = builder.alertEventId;
        this.alertEventRoutesId = builder.alertEventRoutesId;
        this.masterRoute = builder.masterRoute;
        this.routeId = builder.routeId;
        this.routeSequence = builder.routeSequence;
        this.routeTypeName = builder.routeTypeName;
        this.alertURL = builder.alertURL;
    }

    /**
     * getAlertEventId
     * @return int
     */
    public int getAlertEventId() {
        return alertEventId;
    }

    /**
     * setAlertEventId
     * @param alertEventId int
     */
    public void setAlertEventId(int alertEventId) {
        this.alertEventId = alertEventId;
    }

    /**
     * getAlertEventRoutesId
     * @return int
     */
    public int getAlertEventRoutesId() {
        return alertEventRoutesId;
    }

    /**
     * setAlertEventRoutesId
     * @param alertEventRoutesId int
     */
    public void setAlertEventRoutesId(int alertEventRoutesId) {
        this.alertEventRoutesId = alertEventRoutesId;
    }

    /**
     * getMasterRoute
     * @return String
     */
    public String getMasterRoute() {
        return masterRoute;
    }

    /**
     * setMasterRoute
     * @param masterRoute String
     */
    public void setMasterRoute(String masterRoute) {
        this.masterRoute = masterRoute;
    }

    /**
     * getRouteId
     * @return String
     */
    public String getRouteId() {
        return routeId;
    }

    /**
     * setRouteId
     * @param routeId String
     */
    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    /**
     * getRouteSequence
     * @return int
     */
    public int getRouteSequence() {
        return routeSequence;
    }

    /**
     * setRouteSequence
     * @param routeSequence int
     */
    public void setRouteSequence(int routeSequence) {
        this.routeSequence = routeSequence;
    }

    /**
     * getRouteTypeName
     * @return String
     */
    public String getRouteTypeName() {
        return routeTypeName;
    }

    /**
     * setRouteTypeName
     * @param routeTypeName String
     */
    public void setRouteTypeName(String routeTypeName) {
        this.routeTypeName = routeTypeName;
    }

    /**
     * setAlertURL
     * @param alertURL String
     */
    public void setAlertURL(String alertURL) {
        this.alertURL = alertURL;
    }

    /**
     * getAlertURL
     * @return String
     */
    public String getAlertURL() {
        return alertURL;
    }

    //***********************************************************
    /* Description: Builder pattern
    /* AlertEventRoute a = new AlertEventRoute.Builder().alertEventId(1).alertEventRoutesId(2).masterRoute("0L").build();
    /*
    /* @author Van Tran
    /* @version 1.0, 2/28/2018
     */
    //***********************************************************
    public static class Builder {
        private int alertEventId = 0;
        private int alertEventRoutesId = 0;
        private String masterRoute = null;
        private String routeId = null;
        private int routeSequence = 0;
        private String routeTypeName = null;
        private String alertURL = null;

        /**
         * Builder
         */
        public Builder() {
        }
        
        /**
         * alertEventRoutesId
         * @param alertEventRoutesId int
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
         * routeSequence
         * @param routeSequence int
         * @return Builder
         */
        public Builder routeSequence(int routeSequence) {
            this.routeSequence = routeSequence;
            return this;
        }

        /**
         * routeTypeName
         * @param routeTypeName String
         * @return Builder
         */
        public Builder routeTypeName(String routeTypeName) {
            this.routeTypeName = routeTypeName;
            return this;
        }

        /**
         * alertURL
         * @param alertURL String
         * @return Builder
         */
        public Builder alertURL(String alertURL) {
            this.alertURL = alertURL;
            return this;
        }

        public AlertEventRoute build() {
            return new AlertEventRoute(this);
        }
    }
}

package com.rtddenver.model.data;

import java.io.Serializable;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import javax.persistence.Transient;

@Entity
@NamedQueries({ @NamedQuery(name = "findAllRoutes", query = "select o from AlertEventRoutes o " +
                        "WHERE o.alertEventId in :alertEventIDList ORDER BY o.routeSequence") })
@Table(name = "ALERT_EVENT_ROUTES", schema = "REP_IP")
public class AlertEventRoutes implements Serializable {
    private static final long serialVersionUID = 6738713795768565884L;
    @Column(name = "ALERT_EVENT_ID", nullable = false)
    private Integer alertEventId;
    @Id
    @Column(name = "ALERT_EVENT_ROUTES_ID", nullable = false)
    private Integer alertEventRoutesId;
    @Column(name = "MASTER_ROUTE", length = 5)
    private String masterRoute;
    @Column(name = "ROUTE_ID", nullable = false, length = 100)
    private String routeId;
    @Column(name = "ROUTE_SEQUENCE", nullable = false)
    private Integer routeSequence;
    @Column(name = "ROUTE_TYPE_NAME", length = 50)
    private String routeTypeName;
    @Transient
    private String alertURL;
    
    public AlertEventRoutes() {
    }

    public AlertEventRoutes(Integer alertEventId, Integer alertEventRoutesId,
                            String masterRoute, String routeId, Integer routeSequence, String routeTypeName, String alertURL) {
        this.alertEventId = alertEventId;
        this.alertEventRoutesId = alertEventRoutesId;
        this.masterRoute = masterRoute;
        this.routeId = routeId;
        this.routeSequence = routeSequence;
        this.routeTypeName = routeTypeName;
        this.alertURL = alertURL;
    }

    public Integer getAlertEventId() {
        return alertEventId;
    }

    public void setAlertEventId(Integer alertEventId) {
        this.alertEventId = alertEventId;
    }

    public Integer getAlertEventRoutesId() {
        return alertEventRoutesId;
    }

    public void setAlertEventRoutesId(Integer alertEventRoutesId) {
        this.alertEventRoutesId = alertEventRoutesId;
    }

    public String getMasterRoute() {
        return masterRoute;
    }

    public void setMasterRoute(String masterRoute) {
        this.masterRoute = masterRoute;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public Integer getRouteSequence() {
        return routeSequence;
    }

    public void setRouteSequence(Integer routeSequence) {
        this.routeSequence = routeSequence;
    }

    public String getRouteTypeName() {
        return routeTypeName;
    }

    public void setRouteTypeName(String routeTypeName) {
        this.routeTypeName = routeTypeName;
    }

    public void setAlertURL(String alertURL) {
        this.alertURL = alertURL;
    }

    public String getAlertURL() {
        return alertURL;
    }
}

package com.rtddenver.model.data;

import com.rtddenver.model.dto.AlertEventDTO;
import com.rtddenver.model.dto.AlertEventRoutesDirectionDTO;

import java.io.Serializable;

import java.util.List;

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
/* @version 1.0, 2/28/2018
*/
//***********************************************************
@Entity
@NamedQueries({ @NamedQuery(name = "findAllRoutes",
                            query =
                            "select o from AlertEventRoute o WHERE o.alertEventId in :alertEventIDList ORDER BY o.routeSequence"),
                @NamedQuery(name = "findRouteByAlertEventID",
                            query =
                            "select o from AlertEventRoute o WHERE o.alertEventId = :alertEventId ORDER BY o.routeSequence"),
                @NamedQuery(name = "findRouteByRouteID",
                            query =
                            "select o from AlertEventRoute o WHERE o.alertEventId in :alertEventIDList AND (o.routeId = :masterRoute OR o.masterRoute = :masterRoute) ORDER BY o.routeSequence")
    })
@Table(name = "ALERT_EVENT_ROUTES", schema = "SCHEDLS")
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
    private String routeType = null;
    @Transient
    private List<AlertEventRoutesDirectionDTO> routesDirectionList;
    @Transient
    private List<AlertEventDTO> activeAlertList;

    /**
     * AlertEventRoute
     */
    public AlertEventRoute() {
        super();
    }

    /**
     * getAlertEventId
     * @return int
     */
    public int getAlertEventId() {
        return alertEventId;
    }

    /**
     * getAlertEventRoutesId
     * @return int
     */
    public int getAlertEventRoutesId() {
        return alertEventRoutesId;
    }

    /**
     * getMasterRoute
     * @return String
     */
    public String getMasterRoute() {
        return masterRoute;
    }

    /**
     * getRouteId
     * @return String
     */
    public String getRouteId() {
        return routeId;
    }

    /**
     * getRouteSequence
     * @return int
     */
    public int getRouteSequence() {
        return routeSequence;
    }

    /**
     * getRouteTypeName
     * @return String
     */
    public String getRouteTypeName() {
        return routeTypeName;
    }
    
    @PostLoad
    public void findRouteType() {
        switch(this.routeTypeName.toUpperCase()){
            case "LIGHT RAIL":
            case "COMMUTER RAIL":
                routeType = "Rail";
                break;
            default:
                routeType = "Bus";
        }
    }
    
    /**
     * getRouteType
     * @return String
     */
    public String getRouteType() {
        return routeType;
    }

    /**
     * getRoutesDirectionList
     * @return List
     */
    public List<AlertEventRoutesDirectionDTO> getRoutesDirectionList() {
        return routesDirectionList;
    }
    
    public void setRoutesDirectionList(List<AlertEventRoutesDirectionDTO> routesDirectionList) {
        this.routesDirectionList = routesDirectionList;
    }

    /**
     * getActiveAlertList
     * @return List
     */
    public List<AlertEventDTO> getActiveAlertList() {
        return activeAlertList;
    }
    
    public void setActiveAlertList(List<AlertEventDTO> activeAlertList) {
        this.activeAlertList = activeAlertList;
    }  
}

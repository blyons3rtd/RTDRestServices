package com.rtddenver.model.data;

import java.io.Serializable;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@ReadOnly
@NamedQueries({ @NamedQuery(name = "findRoutesWithActiveAlerts",
                            query =
                            "SELECT o from AlertEventRoute o WHERE o.alert.alertEventEffEndDate >= :alertDate AND o.alert.alertEventEffStartDate <= :alertDate " +
                            "ORDER BY o.routeSequence"),
                @NamedQuery(name = "findRoutesWithActiveAlertsGroupByMasterRoute",
                            query =
                            "SELECT o.masterRoute from AlertEventRoute o WHERE o.alert.alertEventEffEndDate >= :alertDate AND o.alert.alertEventEffStartDate <= :alertDate " +
                            "GROUP BY o.masterRoute"),
                @NamedQuery(name = "findRouteWithActiveAlertsByMasterRoute",
                            query =
                            "SELECT o from AlertEventRoute o WHERE o.masterRoute = :masterRoute " +
                            "AND o.alert.alertEventEffEndDate >= :alertDate AND o.alert.alertEventEffStartDate <= :alertDate " +
                            "ORDER BY o.routeSequence")
    })
@Table(name = "ALERT_EVENT_ROUTES", schema = "SCHEDLS")
public class AlertEventRoute implements Serializable {
    private static final long serialVersionUID = -5065925130746093504L;
    @Id
    @Column(name = "ALERT_EVENT_ROUTES_ID")
    private Integer alertEventRoutesId = null;
    @Column(name = "ALERT_EVENT_ID")
    private int alertEventId = 0;
    @Column(name = "MASTER_ROUTE")
    private String masterRoute = null;
    @Column(name = "ROUTE_ID")
    private String routeId = null;
    @Column(name = "ROUTE_SEQUENCE")
    private int routeSequence = 0;
    @Column(name = "ROUTE_TYPE_NAME")
    private String routeTypeName = null;

    @OneToMany (fetch=FetchType.LAZY)
    @JoinColumn(name="ALERT_EVENT_ROUTES_ID", insertable=false, updatable=false)
    private List<AlertEventRouteDirection> alertRoutesDirection;
    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn(name="ALERT_EVENT_ID", insertable=false, updatable=false)
    private AlertEvent alert;

    @Transient
    private String customRouteType = null;


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
        return this.alertEventId;
    }

    /**
     * getAlertEventRoutesId
     * @return Integer
     */
    public Integer getAlertEventRoutesId() {
        return this.alertEventRoutesId;
    }

    /**
     * getMasterRoute
     * @return String
     */
    public String getMasterRoute() {
        return this.masterRoute;
    }

    /**
     * getRouteId
     * @return String
     */
    public String getRouteId() {
        return this.routeId;
    }

    /**
     * getRouteSequence
     * @return int
     */
    public int getRouteSequence() {
        return this.routeSequence;
    }

    /**
     * getRouteTypeName
     * @return String
     */
    public String getRouteTypeName() {
        return this.routeTypeName;
    }
    
    /**
     * getAlertRoutesDirection
     * @return List<AlertEventRouteDirection>
     */
    public List<AlertEventRouteDirection> getAlertRoutesDirection() {
        return this.alertRoutesDirection;
    }

    /**
     * getCustomRouteType
     * @return String
     */
    public AlertEvent getAlertEvent() {
        return this.alert;
    }

    /**
     * getCustomRouteType
     * @return String
     */
    public String getCustomRouteType() {
        switch(this.routeTypeName.toUpperCase()){
            case "LIGHT RAIL":
            case "COMMUTER RAIL":
                this.customRouteType = "Rail";
                break;
            default:
                this.customRouteType = "Bus";
        }
        
        return this.customRouteType;
    }
}

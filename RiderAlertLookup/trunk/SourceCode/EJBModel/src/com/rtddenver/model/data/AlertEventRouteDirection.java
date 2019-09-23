package com.rtddenver.model.data;

import java.io.Serializable;

import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Cacheable(value=false)
@NamedQueries({ @NamedQuery(name = "findRoutesDirectionByID", query = "select o from AlertEventRouteDirection o " + 
                            "WHERE o.alertEventRoutesId = :alertEventRoutesId " +
                            //"AND LENGTH(o.directionAlert) > 0 " +
                            "ORDER BY o.directionName") })
@Table(name = "ALERT_EVENT_ROUTES_DIRECTION", schema = "SCHEDLS")
public class AlertEventRouteDirection implements Serializable {
    @SuppressWarnings("compatibility:1924203674063957519")
    private static final long serialVersionUID = 8097255506880411562L;

    @Column(name = "DIRECTION_ALERT")
    private String directionAlert = null;
    @Column(name = "DIRECTION_NAME")
    private String directionName = null;
    
    // 2019-06-12 jb21854 - Had to revise the key to include only direction_id and alert_event_routes_id, 
    // as defined in the table schema in Oracle.  Previously, all columns were included in the key in this object.
    // That became a problem when null values started to appear in direction_alert
    //@Id    
    @Column(name = "DIRECTION_ID")
    private BigDecimal directionId = null;
    @Column(name = "ALERT_EVENT_ROUTES_ID")
    private int alertEventRoutesId = 0;
    @EmbeddedId
    private AlertEvnetRouteDirectionId id = null;

    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn(name="ALERT_EVENT_ROUTES_ID", insertable=false, updatable=false)
    private AlertEventRoute alertEventRoute = null;

    @Transient
    private String customDirectionName = null;
    @Transient
    private String customDirectionAlert = null;


    /**
     * AlertEventRouteDirection
     */
    public AlertEventRouteDirection() {
        super();
    }

    /**
     * getAlertEventRoutesId
     * @return int
     */
    public int getAlertEventRoutesId() {
        return alertEventRoutesId;
    }

    /**
     * getDirectionAlert
     * @return String
     */
    public String getDirectionAlert() {
        return this.directionAlert;
    }

    /**
     * getDirectionId
     * @return BiDecimal
     */
    public BigDecimal getDirectionId() {
        return this.directionId;
    }

    /**
     * getDirectionName
     * @return String
     */
    public String getDirectionName() {
        return directionName;
    }

    /**
     * getAlertEventRoute
     * @return AlertEventRoute
     */
    public AlertEventRoute getAlertEventRoute() {
        return this.alertEventRoute;
    }

    /**
     * getCustomDirectionAlert
     * @return String
     */
    public String getCustomDirectionAlert() {
        if (this.alertEventRoute.getAlertEvent().getAlertCategoryId() != 7) {
            this.customDirectionAlert = this.getCustomDirectionName() + ": " + this.directionAlert;
        } else {
            this.customDirectionAlert = this.directionAlert;
        }
        
        return this.customDirectionAlert;
    }

    /**
     * getCustomDirectionName
     * @return String
     */
    public String getCustomDirectionName() {
        
        switch(this.directionName){
            case "N-Bound":
                this.customDirectionName = "Northbound";
                break;
            case "S-Bound":
                this.customDirectionName = "Southbound";
                break;
            case "E-Bound":
                this.customDirectionName = "Eastbound";
                break;
            case "W-Bound":
                this.customDirectionName = "Westbound";
                break;
            default:
                this.customDirectionName = this.directionName;
        }  
        
        return this.customDirectionName;
    }
}

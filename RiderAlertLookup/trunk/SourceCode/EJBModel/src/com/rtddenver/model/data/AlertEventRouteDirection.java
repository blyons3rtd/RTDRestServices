package com.rtddenver.model.data;

import java.io.Serializable;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
@NamedQueries({ @NamedQuery(name = "findRoutesDirectionByID", query = "select o from AlertEventRouteDirection o " + 
                            "WHERE o.alertEventRoutesId = :alertEventRoutesId " +
                            "AND LENGTH(o.directionAlert) > 0 " +
                            "ORDER BY o.directionName") })
@Table(name = "ALERT_EVENT_ROUTES_DIRECTION", schema = "SCHEDLS")
public class AlertEventRouteDirection implements Serializable {
    private static final long serialVersionUID = 7733162965875549370L;

    @Id    
    @Column(name = "DIRECTION_ALERT")
    private String directionAlert;
    @Column(name = "DIRECTION_ID", nullable = false)
    private BigDecimal directionId;
    @Column(name = "ALERT_EVENT_ROUTES_ID", nullable = false)
    private int alertEventRoutesId;
    @Column(name = "DIRECTION_NAME", nullable = false, length = 30)
    private String directionName;

    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn(name="ALERT_EVENT_ROUTES_ID", insertable=false, updatable=false)
    private AlertEventRoute alertEventRoute;

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

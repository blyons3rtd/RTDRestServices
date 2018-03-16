package com.rtddenver.model.data;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@NamedQueries({ @NamedQuery(name = "findRoutesDirectionByID", query = "select o from AlertEventRoutesDirection o " + 
                            "WHERE o.alertEventRoutesId = :alertEventRoutesId " +
                            "AND LENGTH(o.directionAlert) > 0 " +
                            "ORDER BY o.directionName") })
@Table(name = "ALERT_EVENT_ROUTES_DIRECTION", schema = "SCHEDLS") 
//schema = "REP_IP"
public class AlertEventRoutesDirection implements Serializable {
    private static final long serialVersionUID = 7733162965875549370L;
    @Id
    @Column(name = "ALERT_EVENT_ROUTES_ID", nullable = false)
    private int alertEventRoutesId;
    @Column(name = "DIRECTION_ALERT")
    private String directionAlert;
    @Id
    @Column(name = "DIRECTION_ID", nullable = false)
    private BigDecimal directionId;
    @Column(name = "DIRECTION_NAME", nullable = false, length = 30)
    private String directionName;

    /**
     * AlertEventRoutesDirection
     */
    public AlertEventRoutesDirection() {
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
        return directionAlert;
    }

    public void setDirectionAlert(String directionAlert) {
        this.directionAlert = directionAlert;
    }

    /**
     * getDirectionId
     * @return BiDecimal
     */
    public BigDecimal getDirectionId() {
        return directionId;
    }
    
    @PostLoad
    public void findDirectionName() {
        switch(this.directionName){
            case "N-Bound":
                directionName = "Northbound";
                break;
            case "S-Bound":
                directionName = "Southbound";
                break;
            case "E-Bound":
                directionName = "Eastbound";
                break;
            case "W-Bound":
                directionName = "Westbound";
                break;
            default:
                directionName = this.directionName;
        }
    }

    /**
     * getDirectionName
     * @return String
     */
    public String getDirectionName() {
        return directionName;
    }
}

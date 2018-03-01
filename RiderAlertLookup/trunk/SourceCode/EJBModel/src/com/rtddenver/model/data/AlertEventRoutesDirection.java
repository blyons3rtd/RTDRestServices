package com.rtddenver.model.data;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "findRoutesDirectionByID", query = "select o from AlertEventRoutesDirection o " +
                            "WHERE o.alertEventRoutesId in :alertEventRoutesId " +
                            "AND o.directionAlert IS NOT NULL " +
                            "ORDER BY o.directionName") })
@Table(name = "ALERT_EVENT_ROUTES_DIRECTION", schema = "SCHEDLS") 
//schema = "REP_IP"
public class AlertEventRoutesDirection implements Serializable {
    private static final long serialVersionUID = 7733162965875549370L;
    @Id
    @Column(name = "ALERT_EVENT_ROUTES_ID", nullable = false)
    private BigDecimal alertEventRoutesId;
    @Column(name = "DIRECTION_ALERT")
    private String directionAlert;
    @Id
    @Column(name = "DIRECTION_ID", nullable = false)
    private BigDecimal directionId;
    @Column(name = "DIRECTION_NAME", nullable = false, length = 30)
    private String directionName;

    public AlertEventRoutesDirection() {
    }

    public AlertEventRoutesDirection(BigDecimal alertEventRoutesId, String directionAlert, BigDecimal directionId,
                                     String directionName) {
        this.alertEventRoutesId = alertEventRoutesId;
        this.directionAlert = directionAlert;
        this.directionId = directionId;
        this.directionName = directionName;
    }

    public BigDecimal getAlertEventRoutesId() {
        return alertEventRoutesId;
    }

    public void setAlertEventRoutesId(BigDecimal alertEventRoutesId) {
        this.alertEventRoutesId = alertEventRoutesId;
    }

    public String getDirectionAlert() {
        return directionAlert;
    }

    public void setDirectionAlert(String directionAlert) {
        this.directionAlert = directionAlert;
    }

    public BigDecimal getDirectionId() {
        return directionId;
    }

    public void setDirectionId(BigDecimal directionId) {
        this.directionId = directionId;
    }

    public String getDirectionName() {
        return directionName;
    }

    public void setDirectionName(String directionName) {
        this.directionName = directionName;
    }
}

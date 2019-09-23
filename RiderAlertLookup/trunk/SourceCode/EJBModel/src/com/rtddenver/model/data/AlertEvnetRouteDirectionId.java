package com.rtddenver.model.data;

import java.io.Serializable;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AlertEvnetRouteDirectionId implements Serializable {
    @Column(name = "ALERT_EVENT_ROUTES_ID")
    private int id = 0;

    @Column(name = "DIRECTION_ID")
    private BigDecimal id2 = null;
    
    public AlertEvnetRouteDirectionId() {
        super();
    }


    public int getId() {
        return id;
    }

    public BigDecimal getId2() {
        return id2;
    }
}

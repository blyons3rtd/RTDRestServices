package com.rtddenver.model.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.ReadOnly;

//***********************************************************
/* Description:
/*
/*
/* @author Van Tran
/* @version 1.0, 3/6/2018
*/
//***********************************************************
@Entity
@ReadOnly
@NamedQueries({ @NamedQuery(name = "findAlertCategories",
                            query = "select o from AlertEventCategory o order by o.alertCategoryId"), 
                @NamedQuery(name = "findAlertEventCategoryByID",
                            query = "select o from AlertEventCategory o where o.alertCategoryId = :alertCategoryId")
                })
@Table(name = "ALERT_CATEGORY", schema = "SCHEDLS")
public class AlertEventCategory implements Serializable {
    private static final long serialVersionUID = -6710120066645450643L;
    @Id
    @Column(name = "ALERT_CATEGORY_ID", nullable = false)
    private int alertCategoryId;
    @Column(name = "ALERT_CATEGORY_SHORT_DESC")
    private String alertCategoryShortDesc;

    /**
     * AlertCategory
     */
    public AlertEventCategory() {
        super();
    }

    /**
     * getAlertCategoryId
     * @return int
     */
    public int getAlertCategoryId() {
        return alertCategoryId;
    }

    /**
     * getAlertCategoryShortDesc
     * @return String
     */
    public String getAlertCategoryShortDesc() {
        return alertCategoryShortDesc;
    }
}

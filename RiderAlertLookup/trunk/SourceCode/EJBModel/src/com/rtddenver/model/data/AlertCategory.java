package com.rtddenver.model.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.Table;

//***********************************************************
/* Description:
/*
/*
/* @author Van Tran
/* @version 1.0, 3/6/2018
*/
//***********************************************************
@Entity
@NamedQueries({ @NamedQuery(name = "findAlertCategories", query = "select o from AlertCategory o order by o.alertCategoryId"), 
                @NamedQuery(name = "findAlertCategoryByID", query = "select o from AlertCategory o where o.alertCategoryId = :alertCategoryId")
                })
@Table(name = "ALERT_CATEGORY", schema = "SCHEDLS")
public class AlertCategory implements Serializable {
    private static final long serialVersionUID = -6710120066645450643L;
    @Id
    @Column(name = "ALERT_CATEGORY_ID", nullable = false)
    private int alertCategoryId;
    @Column(name = "ALERT_CATEGORY_SHORT_DESC")
    private String alertCategoryShortDesc;

    /**
     * AlertCategory
     */
    public AlertCategory() {
        super();
    }

    /**
     * getAlertCategoryId
     * @return int
     */
    public int getAlertCategoryId() {
        return alertCategoryId;
    }
    
    @PostLoad
    public void findAlertCategory() {
        switch(this.alertCategoryId){
            case 2:
            case 6:
                alertCategoryShortDesc = this.alertCategoryShortDesc + " for ";
                break;
            case 8:
            case 7:
                alertCategoryShortDesc = "";
                break;
            default:
                alertCategoryShortDesc = this.alertCategoryShortDesc;
        }
    }

    /**
     * getAlertCategoryShortDesc
     * @return String
     */
    public String getAlertCategoryShortDesc() {
        return alertCategoryShortDesc;
    }
}

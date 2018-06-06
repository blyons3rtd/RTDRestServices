package com.rtddenver.model.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//***********************************************************
/* Description:
/*
/*
/* @author Van Tran
/* @version 1.0, 2/28/2018
*/
//***********************************************************
@XmlRootElement(name = "alertEventCategory")
public class AlertEventCategoryDTO implements Serializable {
    @SuppressWarnings("compatibility:6314655571106031074")
    private static final long serialVersionUID = -6862477427915516806L;

    @XmlElement(name = "alertCategory")
    private String alertCategoryShortDesc = null;
    
    /**
     * AlertCategoryDTO
     */
    public AlertEventCategoryDTO() {
        super();
    }
    
    /**
     * AlertCategoryDTO
     * @param Builder builder
     */
    public AlertEventCategoryDTO(Builder builder) {
        this.alertCategoryShortDesc = builder.alertCategoryShortDesc;
    }

    /**
     * Builder
     */
    public static class Builder {
        private String alertCategoryShortDesc = null;
        
        public Builder() {
        }
        
        /**
         * alertCategoryShortDesc
         * @param alertCategoryShortDesc String
         * @return Builder
         */
        public Builder alertCategoryShortDesc(String alertCategoryShortDesc) {
            this.alertCategoryShortDesc = alertCategoryShortDesc;
            return this;
        }
        
        public AlertEventCategoryDTO build() {
            return new AlertEventCategoryDTO(this);
        }
    }
}

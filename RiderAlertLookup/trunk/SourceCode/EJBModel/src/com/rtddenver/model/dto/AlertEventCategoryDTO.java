package com.rtddenver.model.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

//***********************************************************
/* Description:
/*
/*
/* @author Van Tran
/* @version 1.0, 2/28/2018
*/
//***********************************************************
public class AlertEventCategoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @XmlElement(name = "alertCategory")
    private String alertCategoryShortDesc;
    
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

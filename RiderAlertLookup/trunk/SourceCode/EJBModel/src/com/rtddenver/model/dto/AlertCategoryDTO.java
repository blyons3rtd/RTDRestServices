package com.rtddenver.model.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class AlertCategoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @XmlElement(name = "alertCategory")
    private String alertCategoryShortDesc;
    
    /**
     * AlertCategoryDTO
     */
    public AlertCategoryDTO() {
        super();
    }
    
    /**
     * AlertCategoryDTO
     * @param Builder builder
     */
    public AlertCategoryDTO(Builder builder) {
        this.alertCategoryShortDesc = builder.alertCategoryShortDesc;
    }

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
        
        public AlertCategoryDTO build() {
            return new AlertCategoryDTO(this);
        }
    }
}

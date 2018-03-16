package com.rtddenver.model.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class AlertEventRoutesDirectionDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @XmlElement(name = "directionAlert")
    private String directionAlert;
    
    /**
     * AlertEventRoutesDirectionDTO
     */
    public AlertEventRoutesDirectionDTO() {
        super();
    }
    
    /**
     * AlertEventRoutesDirectionDTO
     * @param Builder builder
     */
    public AlertEventRoutesDirectionDTO(Builder builder) {
        this.directionAlert = builder.directionAlert;
    }

    public static class Builder {
        private String directionAlert = null;
        
        public Builder() {
        }
        
        /**
         * directionAlert
         * @param directionAlert String
         * @return Builder
         */
        public Builder directionAlert(String directionAlert) {
            this.directionAlert = directionAlert;
            return this;
        }
        
        public AlertEventRoutesDirectionDTO build() {
            return new AlertEventRoutesDirectionDTO(this);
        }
    }
}

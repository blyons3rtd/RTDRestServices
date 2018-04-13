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
public class AlertEventRouteDirectionDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @XmlElement(name = "directionAlert")
    private String directionAlert;

    /**
     * AlertEventRouteDirectionDTO
     */
    public AlertEventRouteDirectionDTO() {
        super();
    }

    /**
     * AlertEventRouteDirectionDTO
     * @param builder Builder
     */
    public AlertEventRouteDirectionDTO(Builder builder) {
        this.directionAlert = builder.directionAlert;
    }

    /**
     * Builder
     */
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

        public AlertEventRouteDirectionDTO build() {
            return new AlertEventRouteDirectionDTO(this);
        }
    }
}

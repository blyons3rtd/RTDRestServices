package com.rtddenver.model.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//***********************************************************
/* Description:
/* 
/*
/* @author Jay Butler
/* @version 1.0, 12/07/2017
 */ 
//***********************************************************
@XmlRootElement(name="errorMessage")
public class AccessARideDTO implements Serializable{
    
    @SuppressWarnings("compatibility:-1775689515507726541")
    private static final long serialVersionUID = 1L;

    public AccessARideDTO() {
        super();
    }
    
    @XmlElement(name="Available")
    private boolean available = false;
    
    @XmlElement(name = "Message")
    private String message = "";
    
    @XmlElement(name = "Error")
    private ErrorDTO error = null;
    
    /**
     * AccessARideDTO
     * @param available
     * @param message
     */
    public AccessARideDTO(boolean available, String message) {
        this();
        this.available = available;
        this.message = message;
    }
    
    /**
     * AccessARideDTO
     * @param error
     */
    public AccessARideDTO(ErrorDTO error) {
        this();
        this.error = error;
    }


    public boolean isAvailable() {
        return available;
    }

    public String getMessage() {
        return message;
    }

    public ErrorDTO getError() {
        return error;
    }
    
    public boolean isError() {
        if (this.error != null) {
            return true;
        }
        return false;
    }

}

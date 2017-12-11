package com.rtddenver.model.dto;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Author: Jay Butler
 * 
 * Date: 2017-12-06
 * 
 * This is a Java object representation of the JSON object returned 
 * by the GIS RTDServiceGeocoder district service.
 */
@XmlRootElement(name = "district")
public class DistrictDTO implements Serializable {
    
    @SuppressWarnings("compatibility:-5871720097955122133")
    private static final long serialVersionUID = 1L;

    @XmlElement(name="Available")
    private boolean available = false;
    
    @XmlElement(name = "Message")
    private String message = "";
    
    @XmlElement(name = "Error")
    private ErrorDTO error = null;
    
    
    public DistrictDTO() {
        super();
    }
    
    public DistrictDTO(boolean available, String message) {
        this();
        this.available = available;
        this.message = message;
    }
    
    public DistrictDTO(ErrorDTO error) {
        this();
        this.error = error;
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

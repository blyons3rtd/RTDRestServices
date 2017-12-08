package com.rtddenver.model.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Author: Jay Butler
 * 
 * Date: 2017-12-01
 * 
 * This is a Java object representation of the JSON object returned 
 * by the GIS whoismydirector district service.
 */
@XmlRootElement(name = "district")
public class DistrictDTO implements Serializable {
    
    @XmlElement(name = "Value")
    private String value = null;
    
    @XmlElement(name = "Message")
    private String message = null;
    
    @XmlElement(name = "Address")
    private String address = null;
    
    
    public DistrictDTO() {
        super();
    }
    
    public DistrictDTO(String value, String message, String address) {
        this.value = value;
        this.message = message;
        this.address = address;
    }


    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}

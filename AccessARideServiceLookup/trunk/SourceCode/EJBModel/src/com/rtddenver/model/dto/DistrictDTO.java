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

    @XmlElement(name = "Response")
    private int responseCode = 0;
    
    @XmlElement(name = "Error")
    private ErrorDTO error = null;
    
    
    public DistrictDTO() {
        super();
    }
    
    public DistrictDTO(int response) {
        this();
        this.responseCode = response;
    }
    
    public DistrictDTO(ErrorDTO error) {
        this();
        this.error = error;
    }

    public int getResponse() {
        return responseCode;
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

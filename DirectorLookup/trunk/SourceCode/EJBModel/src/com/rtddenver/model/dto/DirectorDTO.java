package com.rtddenver.model.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


//***********************************************************
/* Description:
/*   This DTO is a representation of the object being 
/*   returned by this web service.
/*
/* @author Jay Butler
/* @version 1.0, 11/29/2017
 */
//***********************************************************
@XmlRootElement(name = "director")
public class DirectorDTO implements Serializable {

    @SuppressWarnings("compatibility:-4897277253218454610")
    private static final long serialVersionUID = 1L;

    @XmlElement(name = "District")
    private String district = null;

    @XmlElement(name = "Director")
    private String director = null;

    @XmlElement(name = "Error")
    private ErrorDTO error = null;
    
    /**
     * DirectorDTO
     */
    public DirectorDTO() {
        super();
    }

    public DirectorDTO(String district, String director) {
        this.district = district;
        this.director = director;
        if (null == director || "".equals(director.trim())) {
            
        }
    }
    
    /**
     * DirectorDTO
     * @param error ErrorDTO
     */
    public DirectorDTO(ErrorDTO error) {
        this.error = error;
    }


    public String getDistrict() {
        return district;
    }

    public String getDirector() {
        return director;
    }

    public ErrorDTO getError() {
        return error;
    }
}

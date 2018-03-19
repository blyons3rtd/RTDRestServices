package com.rtddenver.model.dto;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//***********************************************************
/* Description:
/*
/*
/* @author Luis Roche
/* @version 1.0, 11/17/2017
 */
//***********************************************************
@XmlRootElement(name = "licensePlate")
public class LicensePlateDTO implements Serializable {

    @SuppressWarnings("compatibility:-4897277253218454610")
    private static final long serialVersionUID = 1L;

    @XmlElement(name = "Plate")
    private String plateNumber = null;

    @XmlElement(name = "InDistrict")
    private Long inDistrict = null;

    @XmlElement(name = "Geocoded")
    private Long geocoded = null;
    
    @XmlElement(name = "Response")
    private String reason = null;

    @XmlElement(name = "Error")
    private ErrorDTO error = null;

    /**
     * LicensePlateDTO
     */
    public LicensePlateDTO() {
        super();
    }

    /**
     * LicensePlateDTO
     * @param plateNumber String
     * @param inDistrict long
     */
    public LicensePlateDTO(String plateNumber, long inDistrict, long geocoded) {
        this.plateNumber = plateNumber;
        this.inDistrict = inDistrict;
        this.geocoded = geocoded;
        if (inDistrict == 0) {
            this.reason = "Out of District";
        } else if (inDistrict == 1) {
            if (geocoded == 3) {
                this.reason = "Provisionally In District";
            } else {
                this.reason = "In District";
            }
        } else {
            this.reason = "Inconclusive. Plate could not be found. Call 303-299-2900";
        }
    }

    /**
     * LicensePlateDTO
     * @param error ErrorDTO
     */
    public LicensePlateDTO(ErrorDTO error) {
        this.error = error;
    }
}

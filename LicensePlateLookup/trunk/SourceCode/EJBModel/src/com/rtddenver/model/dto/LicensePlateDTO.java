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
     * @param inDistrict int
     * @param geocoded int
     */
    public LicensePlateDTO(String plateNumber, int inDistrict, int geocoded) {
        this.plateNumber = plateNumber;
        this.inDistrict = Long.valueOf(inDistrict);
        this.geocoded = Long.valueOf(geocoded);

        switch (inDistrict) {
        case 0:
            this.reason = "Out of District";
            break;
        case 1:
            {
                if (this.geocoded == 3) {
                    this.reason = "Provisionally In District";
                } else {
                    this.reason = "In District";
                }
            }
            break;
        case -1:
            this.reason = "License Plate Not Found";
            break;
        }
    }
    
    /**
     * LicensePlateDTO
     * @param plateNumber String
     * @param error ErrorDTO
     */
    public LicensePlateDTO(String plateNumber, ErrorDTO error) {
        this.plateNumber = plateNumber;
        this.error = error;
    }
}

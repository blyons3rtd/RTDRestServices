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
    private String inDistrict = null;

    @XmlElement(name = "Reason")
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
    public LicensePlateDTO(String plateNumber, long inDistrict) {
        this.plateNumber = plateNumber;
        this.inDistrict = Boolean.toString(inDistrict == 1);
        if (inDistrict != 1) {
            this.reason = "Not registered In-District";
        }
    }

    /**
     * LicensePlateDTO
     * @param plateNumber String
     * @param inDistrict long
     * @param licensePlateFound boolean
     */
    public LicensePlateDTO(String plateNumber, long inDistrict, boolean licensePlateFound) {
        this(plateNumber, inDistrict);
        if (!licensePlateFound) {
            this.reason = "Plate number not found";
        } else {
            this.reason = null;
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

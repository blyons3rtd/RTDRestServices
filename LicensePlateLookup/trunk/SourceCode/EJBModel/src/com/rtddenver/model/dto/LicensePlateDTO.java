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
    @SuppressWarnings("compatibility:3698552885640760980")
    private static final long serialVersionUID = 1L;

    @XmlElement(name = "Plate")
    private String plateNumber = null;

    @XmlElement(name = "InDistrict")
    private Long inDistrict = null;

    @XmlElement(name = "Geocoded")
    private Long geocoded = null;

    @XmlElement(name = "Response")
    private String reason = null;

    @XmlElement(name = "status")
    private Integer status = null;

    @XmlElement(name = "code")
    private String code = null;

    @XmlElement(name = "detail")
    private String detail = null;

    @XmlElement(name = "message")
    private String message = null;

    @XmlElement(name = "time")
    private String time = null;
    
    /**
     * LicensePlateDTO
     */
    public LicensePlateDTO() {
        super();
        java.text.SimpleDateFormat sdfOutput = new java.text.SimpleDateFormat();
        sdfOutput.applyPattern("M-d-yyyy hh:mm aaa");
        java.util.GregorianCalendar calendar = new java.util.GregorianCalendar();
        this.time = sdfOutput.format(new java.util.Date(calendar.getTime().getTime()));
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
     * @param status int
     * @param code String
     * @param detail String
     * @param message String
     */
    public LicensePlateDTO(int status, String code, String detail, String message) {
        this();
        this.status = status;
        this.code = code;
        this.detail = detail;
        this.message = message;
    }
    
    /**
     * getStatus
     * @return Integer
     */
    public Integer getStatus() {
        return this.status;
    }
}

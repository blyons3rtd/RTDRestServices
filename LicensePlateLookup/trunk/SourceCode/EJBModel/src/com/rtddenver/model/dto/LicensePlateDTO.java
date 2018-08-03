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
    @SuppressWarnings("compatibility:-642269197534760378")
    private static final long serialVersionUID = 4974752520351643708L;

    @XmlElement(name = "Plate")
    private String plateNumber = null;

    @XmlElement(name = "InDistrict")
    private Long inDistrict = null;

    @XmlElement(name = "Geocoded")
    private Long geocoded = null;

    @XmlElement(name = "Response")
    private String reason = null;
    
    @XmlElement(name="status")
    private Long status = null;

    @XmlElement(name="code")
    private Long code = null;
    
    @XmlElement(name="detail")
    private String detail = null;
    
    @XmlElement(name="message")
    private String message = null;
    
    @XmlElement(name="time")
    private String time = null;
    
    @XmlElement (name="more_info")
    private String moreInfo = null;

    private boolean isError = false;
    
    /**
     * LicensePlateDTO
     */
    public LicensePlateDTO() {
        super();
        java.text.SimpleDateFormat sdfOutput = new java.text.SimpleDateFormat();
        sdfOutput.applyPattern("M-d-yyyy hh:mm aaa");
        java.util.GregorianCalendar calendar = new java.util.GregorianCalendar();
        //this.errorTime = sdfOutput.format(new java.util.Date(calendar.getTime().getTime()));
    }

    /**
     * LicensePlateDTO. Instantiate with this method when no error has occurred.
     * @param plateNumber String
     * @param inDistrict int
     * @param geocoded int
     */
    public LicensePlateDTO(String plateNumber, int inDistrict, int geocoded) {
        this();
        
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
     * LicensePlateDTO. Instantiate with this method when an error has occurred.
     * @param status
     * @param code
     * @param detail
     * @param message
     * @param moreInfo
     */
    public LicensePlateDTO(int status, int code, String detail, String message, String moreInfo) {
        this();
        this.status = new Long(status);
        this.code = new Long(code);
        this.detail = detail;
        this.message = message;
        this.moreInfo = moreInfo;
        isError = true;
    }
    
    public boolean isError() {
        return this.isError;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public Long getInDistrict() {
        return inDistrict;
    }

    public Long getGeocoded() {
        return geocoded;
    }

    public String getReason() {
        return reason;
    }

    public Long getStatus() {
        return status;
    }
    
    public int getStatusAsInt() {
        int x = Math.toIntExact(status);
        return x;
    }

    public Long getCode() {
        return code;
    }
    
    public int getCodeAsInt() {
        int x = Math.toIntExact(code);
        return x;
    }

    public String getDetail() {
        return detail;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public String getMoreInfo() {
        return moreInfo;
    }
}

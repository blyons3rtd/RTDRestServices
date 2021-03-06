package com.rtddenver.model.dto;

import java.io.Serializable;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

//***********************************************************
/* Description:
/*
/*
/* @author Luis Roche
/* @version 1.0, 11/17/2017
 */ 
//***********************************************************
@XmlRootElement(name="errorMessage")
public class ErrorDTO implements Serializable {
   
    @SuppressWarnings("compatibility:-7713157847170658424")
    private static final long serialVersionUID = 1L;

    @XmlElement(name="status")
    private int status = 0;

    @XmlElement(name="code")
    private int code = 0;
   
    @XmlElement(name="detail")
    private String detail = null;
    
    @XmlElement(name="message")
    private String message = null;
    
    @XmlElement(name="time")
    private String time = null;
    
    @XmlElement (name="more_info")
    private String moreInfo = null;
   
    /**
     * ErrorDTO
     */
    public ErrorDTO() {
        super();
        java.text.SimpleDateFormat sdfOutput = new java.text.SimpleDateFormat();
        sdfOutput.applyPattern("M-d-yyyy hh:mm aaa");
        java.util.GregorianCalendar calendar = new java.util.GregorianCalendar();
        this.time = sdfOutput.format(new java.util.Date(calendar.getTime().getTime()));
    }

    /**
     * ErrorDTO
     * @param status
     * @param code
     * @param detail
     * @param message
     * @param moreInfo
     */
    public ErrorDTO(int status, int code, String detail, String message, String moreInfo) {
        this();
        this.status = status;
        this.code = code;
        this.detail = detail;
        this.message = message;
        this.moreInfo = moreInfo;
    }
    
   /**
     * ErrorDTO
     * @param status
     * @param code
     * @param detail
     * @param message
     * @param moreInfo
     * @param time
     */
    public ErrorDTO(int status, int code, String detail, String message, String moreInfo, String time) {
        this();
        this.status = status;
        this.code = code;
        this.detail = detail;
        this.message = message;
        this.moreInfo = moreInfo;
        this.time = time;
    }



    public int getStatus() {
        return status;
    }

    public int getCode() {
        return code;
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

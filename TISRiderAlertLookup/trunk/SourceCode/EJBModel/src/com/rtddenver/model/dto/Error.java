package com.rtddenver.model.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

//***********************************************************
/* Description:
/*
/*
/* @author Luis Roche
/* @version 1.0, 11/17/2017
 */ 
//***********************************************************
@XmlTransient
public abstract class Error implements Serializable {
    @SuppressWarnings("compatibility:-4687867958430822743")
    private static final long serialVersionUID = -1295817469579598103L;

    @XmlElement(name = "status")
    private Integer errorStatus = null;

    @XmlElement(name = "code")
    private String errorCode = null;

    @XmlElement(name = "detail")
    private String errorDetail = null;

    @XmlElement(name = "message")
    private String errorMessage = null;

    @XmlElement(name = "time")
    private String errorTime = null;
   
    /**
     * Error
     */
    public Error() {
        super();
    }

    /**
     * Error
     * @param errorStatus String
     * @param errorCode String
     * @param errorDetail String
     * @param errorMessage String
     */
    public Error(Integer status, String code, String detail, String message) {
        this();

        java.text.SimpleDateFormat sdfOutput = new java.text.SimpleDateFormat();
        sdfOutput.applyPattern("M-d-yyyy hh:mm aaa");
        java.util.GregorianCalendar calendar = new java.util.GregorianCalendar();

        this.errorStatus = status;
        this.errorCode = code;
        this.errorDetail = detail;
        this.errorMessage = message;
        this.errorTime = sdfOutput.format(new java.util.Date(calendar.getTime().getTime()));
    }
    
    /**
     * getErrorStatus
     * @return Integer
     */
    public Integer getErrorStatus() {
        return this.errorStatus;
    }
}

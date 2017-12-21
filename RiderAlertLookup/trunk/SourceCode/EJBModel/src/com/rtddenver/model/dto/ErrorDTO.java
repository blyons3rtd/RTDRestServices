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
    private String status = null;

    @XmlElement(name="code")
    private String code = null;
   
    @XmlElement(name="detail")
    private String detail = null;
    
    @XmlElement(name="message")
    private String message = null;
    
    @XmlElement(name="time")
    private String time = null;
   
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
     * @param code String
     * @param detail String
     * @param message String
     */
    public ErrorDTO(String code, String detail, String message) {
        this();
        this.code = code;
        this.detail = detail;
        this.message = message;
    }
}

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
     * DirectorDTO
     */
    public DirectorDTO() {
        super();
    }

    /**
     * DirectorDTO. Instantiate with this method when no error has occurred.
     * @param district
     * @param director
     */
    public DirectorDTO(String district, String director) {
        super();
        this.district = district;
        this.director = director;
    }
    
    /**
     * DirectorDTO.  Instantiate with this method when an error has occurred.
     * @param status
     * @param code
     * @param detail
     * @param message
     * @param moreInfo
     */
    public DirectorDTO(int status, int code, String detail, String message, String moreInfo) {
        super();
        java.text.SimpleDateFormat sdfOutput = new java.text.SimpleDateFormat();
        sdfOutput.applyPattern("M-d-yyyy hh:mm aaa");
        java.util.GregorianCalendar calendar = new java.util.GregorianCalendar();
        this.time = sdfOutput.format(new java.util.Date(calendar.getTime().getTime()));
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
    
    public String getDistrict() {
        return district;
    }

    public String getDirector() {
        return director;
    }

    public Long getStatus() {
        return status;
    }

    public int getStatusAsInt() {
        if (status == null) {
            return 0;
        }
        int x = Math.toIntExact(status);
        return x;
    }

    public Long getCode() {
        return code;
    }
    
    public int getCodeAsInt() {
        if (code == null) {
            return 0;
        }
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

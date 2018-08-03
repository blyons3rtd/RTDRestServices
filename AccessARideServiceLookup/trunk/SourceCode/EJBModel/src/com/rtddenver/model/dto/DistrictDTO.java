package com.rtddenver.model.dto;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Author: Jay Butler
 * 
 * Date: 2017-12-06
 * 
 * This is a Java object representation of the JSON object returned 
 * by the GIS RTDServiceGeocoder district service.
 */
@XmlRootElement(name = "district")
public class DistrictDTO implements Serializable {
    
    @SuppressWarnings("compatibility:-5871720097955122133")
    private static final long serialVersionUID = 1L;

    @XmlElement(name="available")
    private String available = null;
    
    @XmlElement(name="status")
    private Long status = null;

    @XmlElement(name="code")
    private Long code = null;
    
    @XmlElement(name="detail")
    private String detail = null;
    
    @XmlElement(name = "message")
    private String message = "";
    
    @XmlElement(name="time")
    private String time = null;
    
    @XmlElement (name="more_info")
    private String moreInfo = null;
    
    private boolean isError = false;
    
    
    
    public DistrictDTO() {
        super();
    }
    
    /**
     * DistrictDTO. Instantiate with this method when no error has occurred.
     * @param available
     * @param message
     */
    public DistrictDTO(String available, String message) {
        this();
        this.available = available;
        this.message = message;
    }
    
    /**
     * DistrictDTO. Instantiate with this method when an error has occurred.
     * @param status
     * @param code
     * @param detail
     * @param message
     * @param moreInfo
     */
    public DistrictDTO(int status, int code, String detail, String message, String moreInfo) {
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
        this.available = null;
    }

    /**
     * DistrictDTO. Instantiate with this method when an error has occurred.
     * @param status
     * @param code
     * @param detail
     * @param message
     * @param moreInfo
     */
    public DistrictDTO(int status, int code, String detail, String message, String moreInfo, String time) {
        super();
        this.time = time;
        this.status = new Long(status);
        this.code = new Long(code);
        this.detail = detail;
        this.message = message;
        this.moreInfo = moreInfo;
        isError = true;
    }
    
    public boolean isError() {
        return isError;
    }

    public String getMessage() {
        return message;
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

    public String getTime() {
        return time;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

}

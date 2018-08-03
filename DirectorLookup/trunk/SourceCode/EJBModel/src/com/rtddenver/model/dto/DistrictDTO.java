package com.rtddenver.model.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Author: Jay Butler
 * 
 * Date: 2017-12-01
 * 
 * This is a Java object representation of the JSON object returned 
 * by the GIS District Lookup service.
 */
@XmlRootElement(name = "district")
public class DistrictDTO implements Serializable {
    @SuppressWarnings("compatibility:5276935815740233750")
    private static final long serialVersionUID = 1L;

    @XmlElement(name = "address")
    private String address = null;
    
    @XmlElement(name = "city")
    private String city = null;
    
    @XmlElement(name = "district")
    private String district = null;
    
    @XmlElement(name = "desc")
    private String desc = null;
    
    @XmlElement(name = "lat")
    private String lat = null;
    
    @XmlElement(name = "lng")
    private String lng = null;
    
    @XmlElement(name = "zipcode")
    private String zipcode = null;
    
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
    
    private boolean isError = false;
    
    
    public DistrictDTO() {
        super();
    }
    
    /**
     *DistrictDTO. Instantiate with this method  when no error has occurred.
     * @param address
     * @param city
     * @param district
     * @param desc
     * @param lat
     * @param lng
     * @param zipcode
     */
    public DistrictDTO(String address, String city, String district, String desc, String lat, String lng, String zipcode) {
        this.address = address;
        this.city = city;
        this.district = district;
        this.desc = desc;
        this.lat = lat;
        this.lng = lng;
        this.zipcode = zipcode;
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
        this.status = status;
        this.code = code;
        this.detail = detail;
        this.message = message;
        this.moreInfo = moreInfo;
        isError = true;
    }

    /**
     * DistrictDTO. Instantiate with this method when an error has occurred and posting time from error source.
     * @param status
     * @param code
     * @param detail
     * @param message
     * @param moreInfo
     * @param time
     */
    public DistrictDTO(int status, int code, String detail, String message, String moreInfo, String time) {
        super();
        this.time = time;
        this.status = status;
        this.code = code;
        this.detail = detail;
        this.message = message;
        this.moreInfo = moreInfo;
        isError = true;
    }


    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getDesc() {
        return desc;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getZipcode() {
        return zipcode;
    }

    public boolean isError() {
        return isError;
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

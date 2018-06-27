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
    
    @XmlElement(name = "Error")
    private ErrorDTO errorDto = null;
    
    
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
     * @param ErrorDTO
     */
    public DistrictDTO(ErrorDTO errorDto) {
        this();
        this.errorDto = errorDto;
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

    public ErrorDTO getErrorDto() {
        return errorDto;
    }

}

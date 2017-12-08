package com.rtddenver.model.dto;

import java.io.Serializable;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Author: Jay Butler
 * 
 * Date: 2017-12-01
 * 
 * This is a Java object representation of the JSON object returned 
 * by the GIS whoismydirector district service.
 */
@XmlRootElement(name = "geocodeAddress")
public class GeoCodeAddressDTO implements Serializable {
    @SuppressWarnings("compatibility:7963433776354123924")
    private static final long serialVersionUID = 1L;

    @XmlElement(name = "InRTDDistrict")
    private boolean inRTDDistrict = false;
    
    @XmlElement(name = "X")
    private double x = 0;
    
    @XmlElement(name = "Y")
    private double y = 0;
    
    @XmlElement(name = "Error")
    private ErrorDTO error = null;
    
    
    public GeoCodeAddressDTO() {
        super();
    }
    
    /**
     * GeoCodeAddressDTO
     * @param inRTDDistrict
     * @param x
     * @param y
     */
    public GeoCodeAddressDTO(boolean inRTDDistrict, double x, double y) {
        this.inRTDDistrict = inRTDDistrict;
        this.x = x;
        this.y = y;
    }

    public boolean isInRTDDistrict() {
        return inRTDDistrict;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * GeoCodeAddressDTO
     * @param error ErrorDTO
     */
    public GeoCodeAddressDTO(ErrorDTO error) {
        this.error = error;
    }

    public ErrorDTO getError() {
        return error;
    }
    
    public boolean isError() {
        if (this.error != null) {
            return true;
        }
        return false;
    }


    /**  Example of JSON message returnd by the GeoCodeAddress service
    <?xml version="1.0" encoding="UTF-8"?><soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
        <soap:Body>
            <Json_GeocodeAddressWOptionsResponse xmlns="http://rtd-denver.com/geocoder">
                <JsonGeocode>{"Count":1,"Candidates":[{"InCallNRide":false,"CallNRide":null,"DirectorDistrict":"M","InRTDDistrict":true,"InCheckPoint":false,
                "CheckPoint":null,"Score":140,"CoordinateSystem":"GCS_WGS_1984","Y":39.759162954545687,"X":-105.15423763204174,
                "StandardAddress":{"HouseNumber":"13800","Street":{"Prefix":"W","Name":"30TH","Type":"AVE","Suffix":""},"City":"GOLDEN","Zipcode":"80401","State":"CO",
                "MatchAddr":"13800 W 30TH AVE, GOLDEN, CO, 80401"}}],"InputStandardAddress":{"HouseNumber":"13800","Street":{"Prefix":"W","Name":"30TH","Type":"AVE",
                "Suffix":""},"City":"GOLDEN","Zipcode":"80401","State":"CO","MatchAddr":"13800 W 30TH AVE, GOLDEN  80401"}}</JsonGeocode>
            </Json_GeocodeAddressWOptionsResponse>
        </soap:Body>
    </soap:Envelope>
     */
    
}

package com.rtddenver.service.facade;

import com.rtddenver.model.dto.GeoCodeAddressDTO;

import javax.ejb.Local;

@Local
public interface GeoCoderServiceLocal {
    
    /**
     * getGeoCodeAddress
     * @param street
     * @param city
     * @param zip
     * @param options
     * @param returnInWGS84
     * @return GeoCodeAddressDTO
     */
    public GeoCodeAddressDTO getGeoCodeAddress(String street, String city, String zip, int options, boolean returnInWGS84);
    
}

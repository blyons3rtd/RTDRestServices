package com.rtddenver.service.facade;

import com.rtddenver.model.dto.DistrictDTO;

import javax.ejb.Local;

@Local
public interface GisDistrictServiceLocal {
    
    /**
     * getDistrictForAddress
     * @param address
     * @return DistrictDTO
     */
    DistrictDTO getDistrictForAddress(String street, String city, String zip);
}

package com.rtddenver.service.facade;

import com.rtddenver.model.dto.DistrictDTO;

import javax.ejb.Local;

@Local
public interface AdaRestServiceLocal {
    
    /**
     * getAdaAvailability
     * @param street
     * @param city
     * @param zip
     * @param departureDay
     * @param departureTime
     * @return DistrictDTO
     */
    public DistrictDTO getAdaAvailability(String street, String city, String zip, String departureDay,
                                                String departureTime);
}

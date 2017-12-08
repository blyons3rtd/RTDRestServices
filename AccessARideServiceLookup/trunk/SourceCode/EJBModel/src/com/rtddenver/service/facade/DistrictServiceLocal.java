package com.rtddenver.service.facade;

import com.rtddenver.model.dto.DistrictDTO;

import javax.ejb.Local;

@Local
public interface DistrictServiceLocal {
    
    /**
     * getAdaOnDayTimeLoc
     * @param dayOfWeek
     * @param time
     * @param lon
     * @param lat
     * @return DistrictDTO
     */
    public DistrictDTO getAdaOnDayTimeLoc(String dayOfWeek, String time, double lon, double lat);
    
}

package com.rtddenver.service.facade;

import com.rtddenver.model.dto.DirectorDTO;

import javax.ejb.Local;


@Local
public interface DirectorServiceLocal {

    /**
     * getDirectorByDistrict
     * @param district
     * @return DirectorDTO
     */
    DirectorDTO getDirectorByDistrict(String district);
}

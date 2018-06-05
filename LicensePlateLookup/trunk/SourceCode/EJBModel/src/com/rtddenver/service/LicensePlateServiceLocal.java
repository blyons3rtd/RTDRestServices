package com.rtddenver.service;

import com.rtddenver.model.dto.LicensePlateDTO;

import javax.ejb.Local;

@Local
public interface LicensePlateServiceLocal {
    /**
     * getLicensePlate
     * @param plateNumber String
     * @return LicensePlateDTO
     */
    abstract LicensePlateDTO getLicensePlate(String plateNumber) throws Exception;
}

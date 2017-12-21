package com.rtddenver.service;

import com.rtddenver.model.dto.ActiveAlertDTO;
import com.rtddenver.model.dto.RouteDTO;

import javax.ejb.Local;

@Local
public interface RiderAlertServiceLocal {
    abstract ActiveAlertDTO getActiveAlertList();
    abstract RouteDTO getActiveAlertRoutes();
}

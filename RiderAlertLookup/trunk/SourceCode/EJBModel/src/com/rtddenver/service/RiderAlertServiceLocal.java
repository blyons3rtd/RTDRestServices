package com.rtddenver.service;

import com.rtddenver.model.dto.ActiveAlertDTO;
import com.rtddenver.model.dto.AlertRouteDTO;
import javax.ejb.Local;

@Local
public interface RiderAlertServiceLocal {
    abstract ActiveAlertDTO getActiveAlertList();
    abstract ActiveAlertDTO getActiveAlertByID(String alertEventId);
    abstract AlertRouteDTO getActiveAlertRoutes();
    abstract AlertRouteDTO getAlertRouteByID(String masterRoute);
}
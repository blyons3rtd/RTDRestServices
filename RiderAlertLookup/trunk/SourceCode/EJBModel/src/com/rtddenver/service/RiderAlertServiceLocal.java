package com.rtddenver.service;

import com.rtddenver.model.dto.ActiveAlertEventDTO;
import com.rtddenver.model.dto.AlertEventDTO;
import com.rtddenver.model.dto.AlertEventRouteDTO;
import com.rtddenver.model.dto.RouteActiveAlertEventDTO;

import javax.ejb.Local;

@Local
public interface RiderAlertServiceLocal {
    abstract ActiveAlertEventDTO getActiveAlertEventList() throws Exception;
    abstract AlertEventDTO getAlertEventById(Integer alertEventId) throws Exception;
    abstract RouteActiveAlertEventDTO getRoutesActiveAlerts() throws Exception;
    abstract AlertEventRouteDTO getAlertEventRouteByMasterRoute(String masterRoute) throws Exception;
}
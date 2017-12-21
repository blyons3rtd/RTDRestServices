package com.rtddenver.service;

import com.rtddenver.model.dto.RouteDTO;
import javax.ejb.Local;

@Local
public interface RouteServiceLocal {
    abstract RouteDTO getRouteList();
}

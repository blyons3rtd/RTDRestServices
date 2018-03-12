package com.rtddenver.service;

import com.rtddenver.model.data.AlertEventRoute;

import com.rtddenver.model.dto.ErrorDTO;
import com.rtddenver.model.dto.RouteDTO;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//***********************************************************
/* Description:
/*
/*
/* @author Van Tran
/* @version 1.0, 2/28/2018
*/
//***********************************************************
@Stateless(name = "RouteService", mappedName = "RiderAlertService-EJBModel-RouteService")
public class RouteServiceBean implements RouteServiceLocal {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "riderAlertModel")
    private EntityManager em;

    /**
     * RouteServiceBean
     */
    public RouteServiceBean() {
        super();
    }

    /**
     * getRouteList
     * @return RouteDTO
     */
    public RouteDTO getRouteList() {
        RouteDTO dto = null;
        List<AlertEventRoute> aer = null;
        ArrayList<AlertEventRoute> busRailList = new ArrayList<AlertEventRoute>();

        aer = em.createNamedQuery("findAllRoutes", AlertEventRoute.class).getResultList();

        if (aer.size() == 0) {
            ErrorDTO error = new ErrorDTO("404", "No records found", "Currently there are no records found.");
            dto = new RouteDTO(error);
        } else {
            aer.forEach(route -> { busRailList.add(route); });
            dto = new RouteDTO();
            dto.setBusList(busRailList);
        }

        if (aer != null) {
            aer.clear();
            aer = null;
        }

        return dto;
    }
}

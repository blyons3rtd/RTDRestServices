package com.rtddenver.service;

import com.rtddenver.model.data.AlertEvent;
import com.rtddenver.model.data.AlertEventCategory;
import com.rtddenver.model.data.AlertEventRoute;
import com.rtddenver.model.data.AlertEventRouteDirection;
import com.rtddenver.model.dto.ActiveAlertEventDTO;
import com.rtddenver.model.dto.AlertEventCategoryDTO;
import com.rtddenver.model.dto.AlertEventDTO;
import com.rtddenver.model.dto.AlertEventRouteDTO;
import com.rtddenver.model.dto.AlertEventRouteDirectionDTO;
import com.rtddenver.model.dto.ErrorDTO;
import com.rtddenver.model.dto.RouteActiveAlertEventDTO;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

//***********************************************************
/* Description:
/*
/*
/* @author Van Tran
/* @version 1.0, 2/28/2018
*/
//***********************************************************
@Stateless(name = "RiderAlertService")
@TransactionAttribute(value = TransactionAttributeType.NEVER)
public class RiderAlertServiceBean implements RiderAlertServiceLocal {

    @Resource
    private SessionContext sessionContext;

    @PersistenceContext(unitName = "riderAlertModel")
    private EntityManager em;

    /**
     * RiderAlertServiceBean
     */
    public RiderAlertServiceBean() {
        super();
    }

    /**
     * getActiveAlertEventList - Get all active alerts
     * @return ActiveAlertEventDTO
     */
    public ActiveAlertEventDTO getActiveAlertEventList() {
        ActiveAlertEventDTO dtoAlert = new ActiveAlertEventDTO();
        List<AlertEvent> stationPNRList = null;
        List<AlertEventDTO> stations = new ArrayList<AlertEventDTO>();

        List<AlertEvent> activeAlerts = this.findActiveEventAlerts();
        List<AlertEventDTO> alerts = new ArrayList<AlertEventDTO>(activeAlerts.size());
        boolean noAlertsFound = false;

        if (activeAlerts.size() == 0) {
            noAlertsFound = true;
        } else {
            activeAlerts.forEach(alert -> {
                AlertEventDTO alertDTO = this.createAlertEventDTO(alert);
                List<AlertEventRouteDTO> routes = new ArrayList<AlertEventRouteDTO>(alert.getAlertEventRoutes().size());
                alert.getAlertEventRoutes().forEach(route -> {
                    List<AlertEventRouteDirectionDTO> directions =
                        new ArrayList<AlertEventRouteDirectionDTO>(route.getAlertRoutesDirection().size());
                    route.getAlertRoutesDirection()
                        .forEach(direction -> { directions.add(this.createAlertEventRoutesDirectionDTO(direction)); });
                    AlertEventRouteDTO routeDTO = this.createAlertEventRouteDTO(route);
                    routeDTO.setRoutesDirectionList(directions);
                    routes.add(routeDTO);
                    ;
                });

                alertDTO.setAlertRoutesList(routes);
                alerts.add(alertDTO);
            });
            dtoAlert.setActiveAlertList(alerts);
        }

        if (activeAlerts != null) {
            activeAlerts.clear();
            activeAlerts = null;
        }

        // Next, get active alerts for Station/PNR...
        stationPNRList = this.findStationsWithActiveEventAlerts();

        if (stationPNRList.size() == 0 && noAlertsFound) {
            ErrorDTO error = new ErrorDTO("404", "No records found", "Currently there are no active alerts.");
            dtoAlert = new ActiveAlertEventDTO(error);
        } else {
            stationPNRList.forEach(station_pnr -> { stations.add(this.createAlertEventDTO(station_pnr)); });
            dtoAlert.setActiveStationPNRAlertsList(stations);
        }

        if (stationPNRList != null) {
            stationPNRList.clear();
            stationPNRList = null;
        }

        return dtoAlert;
    }

    /**
     * getAlertEventById - Get active alert by ID
     * @param Integer alertEventId
     * @return AlertEventDTO
     */
    public AlertEventDTO getAlertEventById(Integer alertEventId) {
        AlertEventDTO alertDTO = null;
        AlertEvent alert = null;

        // First, get active alert by ID
        alert = this.findAlertEventById(alertEventId);
        if (alert == null) {
            ErrorDTO error = new ErrorDTO("404", "No record found", "Alert " + alertEventId + " not found.");
            alertDTO = new AlertEventDTO(error);
        } else {
            alertDTO = this.createAlertEventDTO(alert);
            List<AlertEventRouteDTO> routes = new ArrayList<AlertEventRouteDTO>(alert.getAlertEventRoutes().size());
            alert.getAlertEventRoutes().forEach(route -> {
                List<AlertEventRouteDirectionDTO> directions =
                    new ArrayList<AlertEventRouteDirectionDTO>(route.getAlertRoutesDirection().size());
                route.getAlertRoutesDirection()
                    .forEach(direction -> { directions.add(this.createAlertEventRoutesDirectionDTO(direction)); });
                AlertEventRouteDTO routeDTO = this.createAlertEventRouteDTO(route);
                routeDTO.setRoutesDirectionList(directions);
                routes.add(routeDTO);
                ;
            });

            // stations are not tied to a route, so the list will be blank
            if (routes.size() > 0) {
                alertDTO.setAlertRoutesList(routes);
            }
        }

        return alertDTO;
    }
    
    /**
     * getRoutesActiveAlerts
     * @return RouteActiveAlertEventDTO
     */
    public RouteActiveAlertEventDTO getRoutesActiveAlerts() {

        RouteActiveAlertEventDTO routeActiveAlertEventDTO = null;
        List<AlertEventRouteDTO> routeList = new ArrayList<AlertEventRouteDTO>();
        
        
        List<String> routesWithActiveAlertsGroupByMasetrRouteList = this.findRoutesWithActiveAlertsGroupByMasterRoute();


        if (routesWithActiveAlertsGroupByMasetrRouteList.isEmpty()) {
            ErrorDTO error = new ErrorDTO("404", "No records found", "No routes with active alerts were found.");
            routeActiveAlertEventDTO = new RouteActiveAlertEventDTO(error);
        } else {
            routeActiveAlertEventDTO = new RouteActiveAlertEventDTO();
            routesWithActiveAlertsGroupByMasetrRouteList.forEach(masterRoute -> {

                List<AlertEventRoute> routeWithAlerts = this.findRouteWithActiveAlertsByMasterRoute(masterRoute);
                List<AlertEventDTO> alertEventsDTO = new ArrayList<AlertEventDTO>();


                AlertEventRouteDTO alertEventRouteDTO = new AlertEventRouteDTO();

                routeWithAlerts.forEach(route -> {
                    AlertEventDTO alert = this.createAlertEventDTO(route.getAlertEvent());
                    alertEventRouteDTO.setDTOValues(this.createAlertEventRouteDTO(route));
                    // get direction
                    List<AlertEventRouteDirectionDTO> directions =
                        new ArrayList<AlertEventRouteDirectionDTO>(route.getAlertRoutesDirection().size());
                    route.getAlertRoutesDirection()
                        .forEach(direction -> { directions.add(this.createAlertEventRoutesDirectionDTO(direction)); });
                    alert.setRoutesDirectionList(directions);
                    alertEventsDTO.add(alert);
                });

                alertEventRouteDTO.setActiveAlertList(alertEventsDTO);

                routeList.add(alertEventRouteDTO);
            });


        }


        routeActiveAlertEventDTO.setRoutesList(routeList);

        return routeActiveAlertEventDTO;
    }

    /**
     * getAlertEventRouteByMasterRoute
     * @param masterRoute String
     * @return AlertEventRouteDTO
     */
    public AlertEventRouteDTO getAlertEventRouteByMasterRoute(String masterRoute) {
        AlertEventRouteDTO alertEventRouteDTO = null;
        List<AlertEventRoute> alertEventRouteList = null;

        // get active alerts for specific master route
        alertEventRouteList = this.findRouteWithActiveAlertsByMasterRoute(masterRoute);

        if (alertEventRouteList.isEmpty()) {
            ErrorDTO error = new ErrorDTO("404", "No record found", "Route " + masterRoute + " not found.");
            alertEventRouteDTO = new AlertEventRouteDTO(error);
        } else {
            // get route info from 1st record
            alertEventRouteDTO = this.createAlertEventRouteDTO(alertEventRouteList.get(0));
            List<AlertEventDTO> alerts = new ArrayList<AlertEventDTO>(alertEventRouteList.size());
            alertEventRouteList.forEach(route -> {
                AlertEventDTO alert = this.createAlertEventDTO(route.getAlertEvent());

                // get direction
                List<AlertEventRouteDirectionDTO> directions =
                    new ArrayList<AlertEventRouteDirectionDTO>(route.getAlertRoutesDirection().size());
                route.getAlertRoutesDirection()
                    .forEach(direction -> { directions.add(this.createAlertEventRoutesDirectionDTO(direction)); });

                alert.setRoutesDirectionList(directions);
                alerts.add(alert);

            });

            alertEventRouteDTO.setActiveAlertList(alerts);
        }

        return alertEventRouteDTO;
    }

    /**
     *  findActiveEventAlerts
     *  @return List<AlertEvent>
     */
    private List<AlertEvent> findActiveEventAlerts() {
        return em.createNamedQuery("findActiveEventAlerts", AlertEvent.class)
                 .setParameter("alertDate", java.sql
                                                .Date
                                                .valueOf(LocalDate.now()))
                 .getResultList();
    }

    /**
     *  findStationsWithActiveEventAlerts
     *  @return List<AlertEvent>
     */
    private List<AlertEvent> findStationsWithActiveEventAlerts() {
        return em.createNamedQuery("findActiveStationsWithActiveEventtAlerts", AlertEvent.class)
                 .setParameter("alertDate", java.sql
                                                .Date
                                                .valueOf(LocalDate.now()))
                 .getResultList();
    }

    /**
     *  findAlertEventById
     *  @param int alertEventId
     *  @return AlertEvent
     */
    private AlertEvent findAlertEventById(int alertEventId) {
        return em.find(AlertEvent.class, alertEventId);
    }

    /**
     *  findRouteWithActiveAlertsByMasterRoute
     *  @param String masterRoute
     *  @return List<AlertEventRoute>
     */
    private List<AlertEventRoute> findRouteWithActiveAlertsByMasterRoute(String masterRoute) {
        return em.createNamedQuery("findRouteWithActiveAlertsByMasterRoute", AlertEventRoute.class)
                 .setParameter("masterRoute", masterRoute)
                 .setParameter("alertDate", java.sql
                                                .Date
                                                .valueOf(LocalDate.now()))
                 .getResultList();
    }

    /**
     *  findRoutesWithActiveAlerts
     *  @return List<AlertEventRoute>
     */
    private List<AlertEventRoute> findRoutesWithActiveAlerts() {
        return em.createNamedQuery("findRoutesWithActiveAlerts", AlertEventRoute.class)
                 .setParameter("alertDate", java.sql
                                                .Date
                                                .valueOf(LocalDate.now()))
                 .getResultList();
    }

    /**
     *  findRoutesWithActiveEventAlertsGroupByMasetrRoute
     *  @return List<AlertEventRoute>
     */
    private List findRoutesWithActiveAlertsGroupByMasterRoute() {
        TypedQuery query =
            em.createQuery("SELECT o.masterRoute from AlertEventRoute o WHERE o.alert.alertEventEffEndDate >= :alertDate AND o.alert.alertEventEffStartDate <= :alertDate GROUP BY o.masterRoute",
                           java.lang.String.class);
        query.setParameter("alertDate", java.sql
                                            .Date
                                            .valueOf(LocalDate.now()));
        return query.getResultList();
    }

   /**
     * createAlertEventDTO
     * @param alert AlertEvent
     * @return AlertEventDTO
     */
    private AlertEventDTO createAlertEventDTO(AlertEvent alert) {
        return new AlertEventDTO.Builder().alertEventId(alert.getAlertEventId())
                                          .alertType(alert.getCustomAlertType())
                                          .alertCategoryDetail(alert.getCustomAlertCategoryDetail())
                                          .alertEventRouteLnAffected(alert.getAlertEventRouteLnAffected())
                                          .alertEventStartDate(alert.getAlertEventStartDate())
                                          .alertEventEndDate(alert.getAlertEventEndDate())
                                          .alertEventInfo(alert.getAlertEventInfo())
                                          .alertRoutesList(alert.getAlertRoutesList())
                                          .otherRouteLnAffected(alert.getOtherRouteLnAffected())
                                          .build();
    }

    /**
     * createAlertEventRouteDTO
     * @param alertRoute AlertEventRoute
     * @return AlertEventRouteDTO
     */
    private AlertEventRouteDTO createAlertEventRouteDTO(AlertEventRoute alertRoute) {
        return new AlertEventRouteDTO.Builder()
               .masterRoute(alertRoute.getMasterRoute())
               .routeId(alertRoute.getRouteId())
               .routeType(alertRoute.getCustomRouteType())
               .build();
    }

    /**
     * createAlertEventRoutesDirectionDTO
     * @param alertRouteDirection AlertEventRouteDirection
     * @return AlertEventRouteDirectionDTO
     */
    private AlertEventRouteDirectionDTO createAlertEventRoutesDirectionDTO(AlertEventRouteDirection alertRouteDirection) {
        return new AlertEventRouteDirectionDTO.Builder().directionAlert(alertRouteDirection.getCustomDirectionAlert())
               .build();
    }

    /**
     * AlertEventCategoryDTO
     * @param alertCategory AlertEventCategory
     * @return AlertEventCategoryDTO
     */
    private AlertEventCategoryDTO createAlertCategoryDTO(AlertEventCategory alertCategory) {
        return new AlertEventCategoryDTO.Builder().alertCategoryShortDesc(alertCategory.getAlertCategoryShortDesc())
               .build();
    }
}

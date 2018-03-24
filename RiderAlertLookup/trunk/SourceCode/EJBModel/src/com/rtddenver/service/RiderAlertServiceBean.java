package com.rtddenver.service;

import com.rtddenver.model.data.AlertCategory;
import com.rtddenver.model.data.AlertEventRoute;
import com.rtddenver.model.data.AlertEventRoutesDirection;
import com.rtddenver.model.dto.ErrorDTO;
import com.rtddenver.model.data.AlertEvent;
import com.rtddenver.model.dto.ActiveAlertDTO;
import com.rtddenver.model.dto.AlertCategoryDTO;
import com.rtddenver.model.dto.AlertEventDTO;
import com.rtddenver.model.dto.AlertEventRouteDTO;
import com.rtddenver.model.dto.AlertEventRoutesDirectionDTO;
import com.rtddenver.model.dto.AlertRouteDTO;

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

@Stateless(name = "RiderAlertService")
public class RiderAlertServiceBean implements RiderAlertServiceLocal {

    @Resource
    private SessionContext sessionContext;

    @PersistenceContext(unitName = "riderAlertModel")
    private EntityManager em;

    List<AlertEvent> alertList = null;
    List<AlertCategory> acList = null;
    List<AlertEventRoute> aerList = null;
    List<AlertEventRoutesDirection> aerdList = null;
    
    public RiderAlertServiceBean() {
        super();
    }

    /*
     * Get all active alerts
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ActiveAlertDTO getActiveAlertList() {
        ActiveAlertDTO dtoAlert = null;
        List<AlertEvent> aeStationPNRList = null;
        List<AlertEventDTO> alerts = new ArrayList<AlertEventDTO>();
        List<AlertEventDTO> station_alerts = new ArrayList<AlertEventDTO>();

        try {
            // First, get active alerts for bus & rail...
            alertList = this.getActiveAlerts();
            if (alertList.size() == 0) {
                ErrorDTO error = new ErrorDTO("404", "No records found", "Currently there are no active alerts.");
                dtoAlert = new ActiveAlertDTO(error);
            } else {
                //Get all alert categories & set resultset into categories list
                acList = this.getAlertCategory();
                List<AlertCategory> categories = new ArrayList<AlertCategory>();
                acList.forEach(cat -> {
                        categories.add(cat);
                    });

                //Get alert detail of active alert
                alertList.forEach(alert -> {
                    
                    //When alert type is 1 which is Rider Alert (bus & rail), get Alert Category Description based on alert category id
                    if("1".equals(Integer.toString(alert.getAlertTypeId()))){
                        //iterate through categories list to find a match alertCategoryId, pull alertCategoryShortDesc & append it to alert category detail of each alert
                        categories.forEach(alertCat -> {
                                String tmpAlertCategoryId = Integer.toString(alertCat.getAlertCategoryId());
                                if(tmpAlertCategoryId.equals(Integer.toString(alert.getAlertCategoryId()))){
                                    alert.setAlertCategoryDetail(alertCat.getAlertCategoryShortDesc() + (alert.getAlertCategoryDetail() != null ? alert.getAlertCategoryDetail() : ""));
                                }
                            });
                    }
                    // Iterate through the alert_event_id value and fetch routes for each
                    aerList = this.getRouteByAlertEventID(alert.getAlertEventId());
                    List<AlertEventRouteDTO> routes = new ArrayList<AlertEventRouteDTO>();
                    aerList.forEach(route -> {
                            //Get direction details of each Route/Line affected based on alertEventRoutesId
                            aerdList = getRoutesDirectionByID(route.getAlertEventRoutesId());
                            List<AlertEventRoutesDirectionDTO> directions = new ArrayList<AlertEventRoutesDirectionDTO>();
                            aerdList.forEach(direction -> {
                                    //if alert category ID is NOT 7 (Service Changes), show direction name, else don't show direction name. 
                                    direction.setDirectionAlert((!"7".equals(Integer.toString(alert.getAlertCategoryId())) ? direction.getDirectionName() + ": " : "") + direction.getDirectionAlert());
                                    directions.add(this.createAlertEventRoutesDirectionDTO(direction));
                                });
                        route.setRoutesDirectionList(directions);
                        routes.add(this.createAlertEventRouteDTO(route));
                        });
                    alert.setAlertRoutesList(routes);
                    alerts.add(this.createAlertEventDTO(alert));
                });

                // Next, get active alerts for Station/PNR...
                aeStationPNRList = this.getActiveStationPNRAlerts();
                aeStationPNRList.forEach(station_pnr -> {
                    station_alerts.add(this.createAlertEventDTO(station_pnr));
                });              
                
                dtoAlert = new ActiveAlertDTO();
                dtoAlert.setActiveAlertList(alerts);
                dtoAlert.setActiveStationPNRAlertsList(station_alerts);
            }
        } catch (Exception ex) {
            System.out.println(ex);
            if (em != null) {
                em.clear();

                try {
                    em.close();
                } catch (Exception e) {
                    // do nothing
                } finally {
                    em = null;
                }
            }

            if (alertList != null) {
                alertList.clear();
                alertList = null;
            }

            ErrorDTO error = new ErrorDTO("500", ex.getClass().getName(), ex.getMessage());
            dtoAlert = new ActiveAlertDTO(error);
        }
        return dtoAlert;
    }
    
    /*
     * Get active alert by ID
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ActiveAlertDTO getActiveAlertByID(String alertEventId) {
        ActiveAlertDTO dtoAlertByID = null;
        List<AlertEventDTO> alerts = new ArrayList<AlertEventDTO>();

        try {
            // First, get active alert by ID
            alertList = this.getAlertByID(Integer.parseInt(alertEventId));
            if (alertList.size() == 0) {
                ErrorDTO error = new ErrorDTO("404", "No records found", "Currently there are no active alerts.");
                dtoAlertByID = new ActiveAlertDTO(error);
            } else {
                //Get all alert categories & set resultset into categories list
                acList = this.getAlertCategory();
                List<AlertCategory> categories = new ArrayList<AlertCategory>();
                acList.forEach(cat -> {
                        categories.add(cat);
                    });

                //Get alert detail of active alert
                alertList.forEach(alert -> { 
                    //When alert type is 1 which is Rider Alert (bus & rail), get Alert Category Description based on alert category id
                    if("1".equals(Integer.toString(alert.getAlertTypeId()))){
                        //iterate through categories list to find a match alertCategoryId, pull alertCategoryShortDesc & append it to alert category detail of each alert
                        categories.forEach(alertCat -> {
                                String tmpAlertCategoryId = Integer.toString(alertCat.getAlertCategoryId());
                                if(tmpAlertCategoryId.equals(Integer.toString(alert.getAlertCategoryId()))){
                                    alert.setAlertCategoryDetail(alertCat.getAlertCategoryShortDesc() + (alert.getAlertCategoryDetail() != null ? alert.getAlertCategoryDetail() : ""));
                                }
                            });
                    }
                    // Iterate through the alert_event_id value and fetch routes for each
                    aerList = this.getRouteByAlertEventID(alert.getAlertEventId());
                    List<AlertEventRouteDTO> routes = new ArrayList<AlertEventRouteDTO>();
                    aerList.forEach(route -> {
                            //Get direction details of each Route/Line affected based on alertEventRoutesId
                            aerdList = getRoutesDirectionByID(route.getAlertEventRoutesId());
                            List<AlertEventRoutesDirectionDTO> directions = new ArrayList<AlertEventRoutesDirectionDTO>();
                            aerdList.forEach(direction -> {
                                    //if alert category ID is NOT 7 (Service Changes), show direction name, else don't show direction name. 
                                    direction.setDirectionAlert((!"7".equals(Integer.toString(alert.getAlertCategoryId())) ? direction.getDirectionName() + ": " : "") + direction.getDirectionAlert());
                                    directions.add(this.createAlertEventRoutesDirectionDTO(direction));
                                });
                        route.setRoutesDirectionList(directions);
                        routes.add(this.createAlertEventRouteDTO(route));
                        });
                    alert.setAlertRoutesList(routes);
                    alerts.add(this.createAlertEventDTO(alert));
                });             
                
                dtoAlertByID = new ActiveAlertDTO();
                dtoAlertByID.setActiveAlertList(alerts);
            }
        } catch (Exception ex) {
            System.out.println(ex);
            if (em != null) {
                em.clear();

                try {
                    em.close();
                } catch (Exception e) {
                    // do nothing
                } finally {
                    em = null;
                }
            }

            if (alertList != null) {
                alertList.clear();
                alertList = null;
            }

            ErrorDTO error = new ErrorDTO("500", ex.getClass().getName(), ex.getMessage());
            dtoAlertByID = new ActiveAlertDTO(error);
        }
        return dtoAlertByID;
    }
    
    /*
    * Get all routes associated to active alerts
    */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public AlertRouteDTO getActiveAlertRoutes() {
        AlertRouteDTO alertRouteDTO = null;
        List<AlertEventRoute> aerList = null;
        List<AlertEventRouteDTO> routes = new ArrayList<AlertEventRouteDTO>();
    
        try {
            // First, get active alerts for bus & rail...
            alertList = this.getActiveAlerts();
            if (alertList.size() == 0) {
                ErrorDTO error = new ErrorDTO("404", "No records found", "Currently there are no active alerts.");
                alertRouteDTO = new AlertRouteDTO(error);
            } else {
                //Get all alert categories & set resultset into categories list
                acList = this.getAlertCategory();
                List<AlertCategory> categories = new ArrayList<AlertCategory>();
                acList.forEach(cat -> {
                        categories.add(cat);
                    });

                //Get all active alerts & set resultset into aeList list
                List<Integer> aeIdList = new ArrayList<Integer>();
                List<AlertEvent> aeList = new ArrayList<AlertEvent>();
                alertList.forEach(alert -> {
                        // iterate through the alert_event_id values & set these as arrays of integer aeIdList 
                        aeIdList.add(alert.getAlertEventId());
                        //iterate through categories list to find a match alertCategoryId, pull alertCategoryShortDesc & append it to alert category detail of each alert
                        categories.forEach(alertCat -> {
                                String tmpAlertCategoryId = Integer.toString(alertCat.getAlertCategoryId());
                                if(tmpAlertCategoryId.equals(Integer.toString(alert.getAlertCategoryId()))){
                                    alert.setAlertCategoryDetail(alertCat.getAlertCategoryShortDesc() + (alert.getAlertCategoryDetail() != null ? alert.getAlertCategoryDetail() : ""));
                                }
                            });
                        aeList.add(alert);
                    });
                // Next, iterate through the alert_event_id values and fetch routes for each
                aerList = this.getAllRoutes(aeIdList);
                aerList.forEach(route -> {
                        //Get direction details of each Route/Line affected based on alertEventRoutesId
                        aerdList = this.getRoutesDirectionByID(route.getAlertEventRoutesId());
                        List<AlertEventRoutesDirectionDTO> directions = new ArrayList<AlertEventRoutesDirectionDTO>();
                        aerdList.forEach(direction -> {
                                //if alert category ID is NOT 7 (Service Changes), show direction name, else don't show direction name. 
                                direction.setDirectionAlert((aerdList.size() > 1 ? direction.getDirectionName() + ": " : "") + direction.getDirectionAlert());
                                directions.add(this.createAlertEventRoutesDirectionDTO(direction));
                            });
                        
                        //iterate through aeList list to find a match alertEventId & add object to AlertEventDTO
                        String tmpAlertEventId = Integer.toString(route.getAlertEventId());
                        List<AlertEventDTO> alerts = new ArrayList<AlertEventDTO>();
                        aeList.forEach(alert -> {
                                if(tmpAlertEventId.equals(Integer.toString(alert.getAlertEventId()))){
                                    alerts.add(this.createAlertEventDTO(alert));
                                }
                            });
                       
                        route.setActiveAlertList(alerts);
                        route.setRoutesDirectionList(directions);
                        routes.add(this.createAlertEventRouteDTO(route));
                    });
                alertRouteDTO = new AlertRouteDTO();
                alertRouteDTO.setRoutesList(routes);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
            if (em != null) {
                em.clear();
        
                try {
                    em.close();
                } catch (Exception e) {
                    // do nothing
                } finally {
                    em = null;
                }
            }
        
            if (aerList != null) {
                aerList.clear();
                aerList = null;
            }
        
            ErrorDTO error = new ErrorDTO("500", ex.getClass().getName(), ex.getMessage());
            alertRouteDTO.setError(error);
        }
    
        return alertRouteDTO;
    }

    /*
     * Get alert route by ID
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public AlertRouteDTO getAlertRouteByID(String masterRoute) {
        AlertRouteDTO alertRouteDTO = null;
        List<AlertEventRoute> aerList = null;
        List<AlertEventRouteDTO> routes = new ArrayList<AlertEventRouteDTO>();
    
        try {
            // First, get active alerts for bus & rail...
            alertList = this.getActiveAlerts();
            if (alertList.size() == 0) {
                ErrorDTO error = new ErrorDTO("404", "No records found", "Currently there are no active alerts.");
                alertRouteDTO = new AlertRouteDTO(error);
            } else {
                //Get all alert categories & set resultset into categories list
                acList = this.getAlertCategory();
                List<AlertCategory> categories = new ArrayList<AlertCategory>();
                acList.forEach(cat -> {
                        categories.add(cat);
                    });

                List<Integer> aeIdList = new ArrayList<Integer>();
                List<AlertEvent> aeList = new ArrayList<AlertEvent>();
                alertList.forEach(alert -> {
                        // iterate through the alert_event_id values & set these as arrays of integer aeIdList 
                        aeIdList.add(alert.getAlertEventId());
                        //iterate through categories list to find a match alertCategoryId, pull alertCategoryShortDesc & append it to alert category detail of each alert
                        categories.forEach(alertCat -> {
                                String tmpAlertCategoryId = Integer.toString(alertCat.getAlertCategoryId());
                                if(tmpAlertCategoryId.equals(Integer.toString(alert.getAlertCategoryId()))){
                                    alert.setAlertCategoryDetail(alertCat.getAlertCategoryShortDesc() + (alert.getAlertCategoryDetail() != null ? alert.getAlertCategoryDetail() : ""));
                                }
                            });
                        aeList.add(alert);
                    });

                // Next, iterate through the alert_event_id values and fetch routes for each
                aerList = this.getRouteByRouteID(aeIdList, masterRoute.toUpperCase());
                aerList.forEach(route -> {
                        //Get direction details of each Route/Line affected based on alertEventRoutesId
                        aerdList = getRoutesDirectionByID(route.getAlertEventRoutesId());
                        List<AlertEventRoutesDirectionDTO> directions = new ArrayList<AlertEventRoutesDirectionDTO>();
                        aerdList.forEach(direction -> {
                                //if alert category ID is NOT 7 (Service Changes), show direction name, else don't show direction name. 
                                direction.setDirectionAlert((aerdList.size() > 1 ? direction.getDirectionName() + ": " : "") + direction.getDirectionAlert());
                                directions.add(this.createAlertEventRoutesDirectionDTO(direction));
                            });
                        
                        //iterate through aeList list to find a match alertEventId & add object to AlertEventDTO
                        String tmpAlertEventId = Integer.toString(route.getAlertEventId());
                        List<AlertEventDTO> alerts = new ArrayList<AlertEventDTO>();
                        aeList.forEach(alert -> {
                                if(tmpAlertEventId.equals(Integer.toString(alert.getAlertEventId()))){
                                    //Iterate thru list of routes/lines affected, set the list for other routes/lines affected except the current route/line being requested
                                    String[] alertEventRouteLnAffectedArray = alert.getAlertEventRouteLnAffected().split(",");
                                    String otherRouteLnAffectedList = "";
                                    for (int i = 0; i < alertEventRouteLnAffectedArray.length; i++) {
                                        if(!route.getRouteId().equalsIgnoreCase(alertEventRouteLnAffectedArray[i])){
                                            otherRouteLnAffectedList += (otherRouteLnAffectedList.length() > 0 ? "," : "") + alertEventRouteLnAffectedArray[i];
                                        }  
                                    }
                                    alert.setOtherRouteLnAffected(otherRouteLnAffectedList);
                                    alerts.add(this.createAlertEventDTO(alert));
                                }
                            });
                        
                        route.setActiveAlertList(alerts);
                        route.setRoutesDirectionList(directions);
                        routes.add(this.createAlertEventRouteDTO(route));
                    });
                alertRouteDTO = new AlertRouteDTO();
                alertRouteDTO.setRoutesList(routes);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
            if (em != null) {
                em.clear();
        
                try {
                    em.close();
                } catch (Exception e) {
                    // do nothing
                } finally {
                    em = null;
                }
            }
        
            if (aerList != null) {
                aerList.clear();
                aerList = null;
            }
        
            ErrorDTO error = new ErrorDTO("500", ex.getClass().getName(), ex.getMessage());
            alertRouteDTO.setError(error);
        }
    
        return alertRouteDTO;
    }
    
    /**
     *  getActiveAlerts
     *  @return List<AlertEvent>
     */
    private List<AlertEvent> getActiveAlerts() {
        return em.createNamedQuery("findAllActiveAlerts", AlertEvent.class)
                 .setParameter("alertDate", java.sql.Date.valueOf(LocalDate.now())).getResultList();
    }

    /**
     *  getActiveStationPNRAlerts
     *  @return List<AlertEvent>
     */
    private List<AlertEvent> getActiveStationPNRAlerts() {
        return em.createNamedQuery("findAllActiveStationPNRAlerts", AlertEvent.class)
                 .setParameter("alertDate", java.sql
                                                .Date
                                                .valueOf(LocalDate.now()))
                 .getResultList();
    }

    /**
     *  getAlertByID
     *  @param String alertEventId
     *  @return List<AlertEvent>
     */
    private List<AlertEvent> getAlertByID(int alertEventId) {
        return em.createNamedQuery("findActiveAlertByID", AlertEvent.class)
                 .setParameter("alertDate", java.sql.Date.valueOf(LocalDate.now()))
                 .setParameter("alertEventId", alertEventId).getResultList();
    }
    
    /**
     *  getAllRoutes
     *  @param List alertEventIDList
     *  @return List<AlertEventRoute>
     */
    private List<AlertEventRoute> getAllRoutes(List<Integer> alertEventIDList) {
        return em.createNamedQuery("findAllRoutes", AlertEventRoute.class)
               .setParameter("alertEventIDList", alertEventIDList).getResultList();
    }
    
    /**
     *  getRouteByRouteID
     *  @param List alertEventIDList
     *  @param String masterRoute
     *  @return List<AlertEventRoute>
     */
    private List<AlertEventRoute> getRouteByRouteID(List<Integer> alertEventIDList, String masterRoute) {
        return em.createNamedQuery("findRouteByRouteID", AlertEventRoute.class)
               .setParameter("alertEventIDList", alertEventIDList)
               .setParameter("masterRoute", masterRoute).getResultList();
    }
    
    /**
     *  getRouteByAlertEventID
     *  @param int alertEventId
     *  @return List<AlertEventRoute>
     */
    private List<AlertEventRoute> getRouteByAlertEventID(int alertEventId) {
        return em.createNamedQuery("findRouteByAlertEventID", AlertEventRoute.class)
               .setParameter("alertEventId", alertEventId).getResultList();
    }

    /**
     *  getRoutesDirectionByID
     *  @param List<Integer> alertEventRoutesId
     *  @return List<AlertEventRoutesDirection>
     */
    private List<AlertEventRoutesDirection> getRoutesDirectionByID(int alertEventRoutesId) {
        return em.createNamedQuery("findRoutesDirectionByID", AlertEventRoutesDirection.class)
                 .setParameter("alertEventRoutesId", alertEventRoutesId).getResultList();
    }
    
    /**
     *  getAlertCategoryByID
     *  @param int alertCategoryId
     *  @return List<AlertCategory>
     */
    private List<AlertCategory> getAlertCategoryByID(int alertCategoryId) {
        return em.createNamedQuery("findAlertCategoryByID", AlertCategory.class)
                 .setParameter("alertCategoryId", alertCategoryId).getResultList();
    }
    
    /**
     *  getAlertCategory
     *  @return List<AlertCategory>
     */
    private List<AlertCategory> getAlertCategory() {
        return em.createNamedQuery("findAlertCategories", AlertCategory.class).getResultList();
    }  

    /**
     *  getActiveAlerts
     *  @param int year
     *  @param int month
     *  @param int day
     *  @return List<AlertEvent>
     */
    private List<AlertEvent> getActiveAlerts(int year, int month, int day) throws Exception {
        return em.createNamedQuery("findAllActiveAlerts", AlertEvent.class)
                 .setParameter("alertDate", java.sql
                                                .Date
                                                .valueOf(LocalDate.of(year, month, day)))
                 .getResultList();
    }

    private AlertEventDTO createAlertEventDTO(AlertEvent alert) {
        return new AlertEventDTO.Builder().alertEventId(alert.getAlertEventId())
                                          .alertType(alert.getAlertType())
                                          .alertCategory(alert.getAlertCategory())
                                          .alertCategoryDetail(alert.getAlertCategoryDetail())
                                          .alertEventRouteLnAffected(alert.getAlertEventRouteLnAffected())
                                          .alertEventStartDate(alert.getAlertEventStartDate())
                                          .alertEventEndDate(alert.getAlertEventEndDate())
                                          .alertEventInfo(alert.getAlertEventInfo())
                                          .alertRoutesList(alert.getAlertRoutesList())
                                          .otherRouteLnAffected(alert.getOtherRouteLnAffected())
                                          .build();
    }
    
    private AlertEventRouteDTO createAlertEventRouteDTO(AlertEventRoute alertRoute) {
            return new AlertEventRouteDTO.Builder().alertEventId(alertRoute.getAlertEventId())
                                                   .alertEventRoutesId(alertRoute.getAlertEventRoutesId())
                                                   .masterRoute(alertRoute.getMasterRoute())
                                                   .routeId(alertRoute.getRouteId())
                                                   .routeType(alertRoute.getRouteType())
                                                   .routesDirectionList(alertRoute.getRoutesDirectionList())
                                                   .activeAlertList(alertRoute.getActiveAlertList())
                                                   .build();
    }
    
    private AlertEventRoutesDirectionDTO createAlertEventRoutesDirectionDTO(AlertEventRoutesDirection alertRouteDirection) {
            return new AlertEventRoutesDirectionDTO.Builder().directionAlert(alertRouteDirection.getDirectionAlert()).build();
    }
    
    private AlertCategoryDTO createAlertCategoryDTO(AlertCategory alertCategory) {
        return new AlertCategoryDTO.Builder().alertCategoryShortDesc(alertCategory.getAlertCategoryShortDesc()).build();
    }
}

package com.rtddenver.service;

import com.rtddenver.model.data.AlertEventRoutes;
import com.rtddenver.model.dto.ErrorDTO;
import com.rtddenver.model.data.AlertEvents;
import com.rtddenver.model.dto.ActiveAlertDTO;

import com.rtddenver.model.dto.AlertRouteDTO;
import com.rtddenver.model.dto.RouteDTO;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

    public RiderAlertServiceBean() {
        super();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ActiveAlertDTO getActiveAlertList() {
        ActiveAlertDTO dtoAlert = null;
        List<AlertEvents> ae = null;

        try {
            ae = getActiveAlerts();
            if (ae.size() == 0) {
                ErrorDTO error = new ErrorDTO("", null, "Currently there are no active alerts.");
                dtoAlert = new ActiveAlertDTO(error);
            } else {
                dtoAlert = new ActiveAlertDTO(ae);
            }
        } catch (Exception ex) {
            System.out.println(ex);
            if (em != null) {
                em.clear();

                try {
                    em.close();
                } catch (Exception e) {
                    // do noting
                } finally {
                    em = null;
                }
            }

            if (ae != null) {
                ae.clear();
                ae = null;
            }

            ErrorDTO error = new ErrorDTO("500", ex.getClass().getName(), ex.getMessage());
            dtoAlert = new ActiveAlertDTO(error);
        }

        return dtoAlert;
    }


    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public AlertRouteDTO getActiveAlertRoutes() {
        AlertRouteDTO alertRouteDTO = new AlertRouteDTO();
        List<AlertEvents> ae = null;
        List<BigDecimal> alertEventIDList = null;
        RouteDTO routeDTO = null;

        try {
            // First, get active alerts...
            ae = getActiveAlerts();
            if (ae.size() == 0) {
                ErrorDTO error = new ErrorDTO("", null, "Currently there are no active alerts.");
                alertRouteDTO.setError(error);
            } else {
                // Next, iterate through the alert_event_id values and fetch routes for each
                for (int i = 0; i < ae.size(); i++) {
                    AlertEvents a = ae.get(i);
                    routeDTO = new RouteDTO();
                    alertEventIDList = new ArrayList<BigDecimal>();
                    alertEventIDList.add(a.getAlertEventId());
                    List<AlertEventRoutes> aerList = null;
                    List<AlertEventRoutes> aerBusList = new ArrayList<AlertEventRoutes>();
                    List<AlertEventRoutes> aerRailList = new ArrayList<AlertEventRoutes>();
                    aerList = em.createNamedQuery("findAllRoutes", AlertEventRoutes.class)
                            .setParameter("alertEventIDList", alertEventIDList)
                            .getResultList();
                    
                    // Create a RouteDTO object for the alert event and its effected routes
                    routeDTO.setAlertEventId(a.getAlertEventId().intValue());
                    
                    Iterator<AlertEventRoutes> itr = aerList.iterator();
                    while (itr.hasNext()) {
                        AlertEventRoutes aer = itr.next(); 
                        aer.setAlertURL("APIWSGoesHere/alerts/" + aer.getAlertEventId() + ":" + aer.getRouteId());
                        if( "LIGHT RAIL".equalsIgnoreCase(aer.getRouteTypeName()) || "COMMUTER RAIL".equalsIgnoreCase(aer.getRouteTypeName()) ) {
                            aerRailList.add(aer);
                        }else{
                            aerBusList.add(aer);
                        }
                    }
                    
                    routeDTO.setBusList(aerBusList);
                    routeDTO.setRailList(aerRailList);
                    
                    // Add the RouteDTO to the AlertRouteDTO
                    alertRouteDTO.addRouteDTO(routeDTO);
                }
                
            }
        } catch (Exception ex) {
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

            if (ae != null) {
                ae.clear();
                ae = null;
            }

            ErrorDTO error = new ErrorDTO("500", ex.getClass().getName(), ex.getMessage());
            alertRouteDTO.setError(error);
        }

        return alertRouteDTO;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ActiveAlertDTO getActiveAlertByID(String alertURL) {
        ActiveAlertDTO dtoAlertByID = null;
        List<AlertEvents> aeID = null;

        try {
            aeID = getAlertByID(alertURL);
            if (aeID.size() == 0) {
                ErrorDTO error = new ErrorDTO("", null, "The route name you entered is not valid. See a complete list of Rider Alerts.");
                dtoAlertByID = new ActiveAlertDTO(error);
            }else{
                dtoAlertByID = new ActiveAlertDTO(aeID);
            }    
        }catch (Exception ex) {
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

            if (aeID != null) {
                aeID.clear();
                aeID = null;
            }

            ErrorDTO error = new ErrorDTO("500", ex.getClass().getName(), ex.getMessage());
            dtoAlertByID = new ActiveAlertDTO(error);
        }
        
        return dtoAlertByID;
    }   

    private List<AlertEvents> getAlertByID(String alertID) throws Exception {
        List<AlertEvents> ae = null;
        Date date = new Date();
        BigDecimal alertEventID =  new BigDecimal(alertID);
        ae = em.createNamedQuery("findActiveAlertByID", AlertEvents.class)
               .setParameter("alertDate", date)
               .setParameter("alertID", alertEventID)
               .getResultList();
        return ae;
    }
    
    private List<AlertEvents> getActiveAlerts() throws Exception {
        List<AlertEvents> ae = null;
        Date date = new Date();
        //System.out.println("Test alertDate:" + date.toString());
        ae = em.createNamedQuery("findAllActiveAlerts", AlertEvents.class)
               .setParameter("alertDate", date)
               .getResultList();
        return ae;
    }

    private List<AlertEvents> getActiveAlerts(int year, int month, int day) throws Exception {
        List<AlertEvents> ae = null;
        Date date = new Date((year - 1900), month, day);
        //System.out.println("Test alertDate:" + date.toString());
        ae = em.createNamedQuery("findAllActiveAlerts", AlertEvents.class)
               .setParameter("alertDate", date)
               .getResultList();
        return ae;
    }
}
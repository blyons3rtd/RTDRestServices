package com.rtddenver.service;

import com.rtddenver.model.data.AlertEventRoutes;
import com.rtddenver.model.dto.ErrorDTO;
import com.rtddenver.model.data.AlertEvents;
import com.rtddenver.model.dto.ActiveAlertDTO;

import com.rtddenver.model.dto.AlertRouteDTO;
import com.rtddenver.model.dto.RouteDTO;

import com.rtddenver.model.dto.RouteToAlertDTO;

import com.rtddenver.model.dto.RouteTypeDTO;
import com.rtddenver.model.dto.StagingRouteDTO;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

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
    @SuppressWarnings("unchecked")
    public AlertRouteDTO getActiveAlertRoutes() {
        AlertRouteDTO alertRouteDTO = new AlertRouteDTO();
        List<AlertEvents> alertEventsList = null;
        List<BigDecimal> alertEventIDList = null;
        RouteDTO routeDTO = null;
        RouteToAlertDTO routeToAlertDTO = null;
        StagingRouteDTO stagingRouteDTO = null;
        RouteTypeDTO routeTypeDTO = null;
        String tmpRouteId = "";
        String tmpAlertURL = "";
        int iCntRt = 0;
        int iCnt = 0;
        String commaStr = "";
        String colonStr = "";

        try {
            routeToAlertDTO = new RouteToAlertDTO();
            // First, get active alerts...
            alertEventsList = getActiveAlerts();
            if (alertEventsList.size() == 0) {
                ErrorDTO error = new ErrorDTO("", null, "Currently there are no active alerts.");
                alertRouteDTO.setError(error);
            } else {
                Map<String, List<AlertEventRoutes>> routeToAlertMap = new HashMap<String, List<AlertEventRoutes>>();
                Map<String, String> busTypeMap = new HashMap<String, String>();
                Map<String, String> railTypeMap = new HashMap<String, String>();
                
                // Next, iterate through the alert_event_id values and fetch routes for each
                for (int i = 0; i < alertEventsList.size(); i++) {
                    AlertEvents alertEvents = alertEventsList.get(i);
                    routeDTO = new RouteDTO();
                    alertEventIDList = new ArrayList<BigDecimal>();
                    alertEventIDList.add(alertEvents.getAlertEventId());
                    List<AlertEventRoutes> aerList = null;
                    List<AlertEventRoutes> aerRouteList = new ArrayList<AlertEventRoutes>();
                    aerList = em.createNamedQuery("findAllRoutes", AlertEventRoutes.class)
                            .setParameter("alertEventIDList", alertEventIDList)
                            .getResultList();
                    
                    // Create a RouteDTO object for the alert event and its effected routes
                    routeDTO.setAlertEventId(alertEvents.getAlertEventId().intValue());
                    
                    Iterator<AlertEventRoutes> itr = aerList.iterator();
                    while (itr.hasNext()) {
                        AlertEventRoutes aer = itr.next(); 
                        aer.setAlertURL("APIWSGoesHere/alerts/" + aer.getAlertEventId() + "/" + aer.getMasterRoute());
                        aerRouteList.add(aer);
                        if( "LIGHT RAIL".equalsIgnoreCase(aer.getRouteTypeName()) || "COMMUTER RAIL".equalsIgnoreCase(aer.getRouteTypeName()) ) {
                            // Create a map of unique routeIds for Rail
                            railTypeMap.put(aer.getRouteId(),null);
                        }else{
                            // Create a map of unique routeIds for Bus
                            busTypeMap.put(aer.getRouteId(),null);
                        }
                        // Create a map of unique routeIds
                        routeToAlertMap.put(aer.getRouteId(),null);
                        
                    }
                    
                    routeDTO.setRouteList(aerRouteList);                    
                    // Add the RouteDTO to the AlertRouteDTO
                    alertRouteDTO.addRouteDTO(routeDTO);   
                    
                }  // End of master list of alertRoutes
                
                
                //Loop through the map of routeIds and associate each routeId with related alerts
                List<AlertEventRoutes> routeListNew = null;
                Iterator<String> routeIdItr = routeToAlertMap.keySet().iterator();
                while (routeIdItr.hasNext()) {
                    String routeId = routeIdItr.next();
                    System.out.println("== RouteId ====================================================");
                    System.out.println("Map routeId: " + routeId);
                    
                    //Iterate through rDTO to id RouteDTO pertinant to the current routeId
                    List<RouteDTO> routeDTOList = alertRouteDTO.getRouteDTO();
                    Iterator<RouteDTO> routeDTOItr = routeDTOList.iterator();
                    
                    //While
                    routeListNew = new ArrayList<AlertEventRoutes>();
                    System.out.println("Route List contains " + routeDTOList.size() + " records");
                    System.out.println("Iterating through RouteDTO list. Contains " + routeDTOList.size() + " records");
                    while (routeDTOItr.hasNext()) {
                        RouteDTO rteDTO = routeDTOItr.next();
                        System.out.println("=======================================");
                        if(rteDTO.getRouteList() != null) {
                            //iterate through rteDTO.getRouteList() to get elements in this list
                            System.out.println("Iterating through the RouteList... Records: " + rteDTO.getRouteList().size());
                            Iterator<AlertEventRoutes> routeListItr = rteDTO.getRouteList().iterator();
                            while (routeListItr.hasNext()) {
                                AlertEventRoutes aerRoute = routeListItr.next();
                                System.out.println("Looking at Route List RouteId: " + aerRoute.getRouteId() + ", AlertEventId: " + aerRoute.getAlertEventId());
                                //if routeId matches save RouteDTO to new list
                                if(routeId.equalsIgnoreCase(aerRoute.getRouteId())) {
                                    //Add to new list
                                    routeListNew.add(aerRoute);
                                    System.out.println("Assigning AlertEventId: " + aerRoute.getAlertEventId());
                                }
                            }
                            System.out.println("End of RouteList");
                        }  
                    }
                    
                    System.out.println("\nDone processing routeId: " + routeId);
                    
                    if (routeListNew != null) {
                        routeToAlertMap.put(routeId, routeListNew);
                        System.out.println("Adding route to routeToAlertMap...");
                    }
                    
                    // end While
                    System.out.println();
                }
                
                routeToAlertDTO.setRouteToAlertMap(routeToAlertMap);
                
                List<AlertEventRoutes> aerBusList = new ArrayList<AlertEventRoutes>();
                routeTypeDTO = new RouteTypeDTO();
            
                //Setup new DTO to define list of a single route and one-to-many alerts
                //iterate through routeToAlertMap object containing List in a Map of route and list of alerts
                Iterator<Map.Entry<String, List<AlertEventRoutes>>> routeMapItr = routeToAlertMap.entrySet().iterator();
                String tmpAlertEventId = "";
                while(routeMapItr.hasNext()) {
                    Map.Entry<String, List<AlertEventRoutes>> aer = routeMapItr.next();
                    
                    //iterate over busTypeMapItr, find a match key with aer.key, set the alertURL to combine all possible alertEventId(s):alertEventRoutesId                    
                    Iterator<String> rIdItr = busTypeMap.keySet().iterator();
                    while (rIdItr.hasNext()) {
                        String routeId = rIdItr.next();
                        if(routeId.equalsIgnoreCase(aer.getKey())){
                            Iterator<AlertEventRoutes> alertListItr = aer.getValue().iterator();
                            while (alertListItr.hasNext()) {
                                AlertEventRoutes aerL = alertListItr.next();
                                if (iCntRt != 0 && iCntRt < aer.getValue().size()) {
                                    colonStr = ":";  
                                }
                                tmpAlertEventId += colonStr + aerL.getAlertEventId();
                                iCntRt++;
                            }  
                        }
                        busTypeMap.replace(routeId, tmpAlertEventId);
                    }

                    //Add to new list
                    stagingRouteDTO = new StagingRouteDTO();
                    stagingRouteDTO.setRouteId(findRouteId(aer.getKey()));
                    stagingRouteDTO.setAlertList(aer.getValue());
                    alertRouteDTO.addStagingRouteDTO(stagingRouteDTO);
                }
                
                Iterator<Map.Entry<String, String>> busTypeMapItr = busTypeMap.entrySet().iterator();
                while(busTypeMapItr.hasNext()) {
                    Map.Entry<String, String> rteType = busTypeMapItr.next();
                    System.out.println("RouteId: " + rteType.getKey() + " | AlertURL: " + rteType.getValue());
                    if (iCnt != 0 && iCnt < busTypeMap.size()) {
                        commaStr = ",";
                    }                    
                    tmpRouteId += commaStr + rteType.getKey();
                    tmpAlertURL += commaStr + rteType.getValue();
                    iCnt++;
                }
                
                if(tmpRouteId != null){
                    routeTypeDTO.setBusList(tmpRouteId);
                }
                if(tmpAlertURL != null){
                    routeTypeDTO.setAlertURLList(tmpAlertURL);
                }
                
                alertRouteDTO.addBusRouteTypeDTO(routeTypeDTO);
                
                /*Comment out the line below - no need to create the object 'List<RouteToAlertDTO> routeToAlert' but still need the logic to create the Map<String, List<AlertEventRoutes>>
                alertRouteDTO.addRouteToAlertDTO(routeToAlertDTO);
                */
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

            if (alertEventsList != null) {
                alertEventsList.clear();
                alertEventsList = null;
            }

            ErrorDTO error = new ErrorDTO("500", ex.getClass().getName(), ex.getMessage());
            alertRouteDTO.setError(error);
        }

        return alertRouteDTO;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ActiveAlertDTO getActiveAlertByID(String alertID, String route) {
        ActiveAlertDTO dtoAlertByID = null;
        List<AlertEvents> aeID = null;
        //RouteDTO routeDTO = null;

        try {
            aeID = getAlertByID(alertID);
            if (aeID.size() == 0) {
                ErrorDTO error = new ErrorDTO("", null, "The route name you entered is not valid. See a complete list of Rider Alerts.");
                dtoAlertByID = new ActiveAlertDTO(error);
            }else{
//                List<AlertEventRoutes> aer = null;
//                routeDTO = new RouteDTO();
//                aer = em.createNamedQuery("findRouteByID", AlertEventRoutes.class)
//                        .setParameter("alertID", alertID)
//                        .setParameter("masterRoute", route)
//                        .getResultList();
//                
//                routeDTO.setAlertEventList(aer);
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
    
    private String findRouteId(String routeId) throws Exception {
        String rteId = routeId;
        if(routeId.indexOf(':') > 0){
            rteId = routeId.substring(0, routeId.indexOf(':'));
        }
        return rteId;
    }
    
    private String findRouteType(String routeId) throws Exception {
        String rteType = null;
        if(routeId.indexOf(':') > 0){
            rteType = routeId.substring(routeId.indexOf(':') + 1);
        }
        return rteType;
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
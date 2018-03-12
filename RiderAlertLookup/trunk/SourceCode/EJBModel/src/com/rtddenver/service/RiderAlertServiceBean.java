package com.rtddenver.service;

import com.rtddenver.model.data.AlertEventRoute;
import com.rtddenver.model.data.AlertEventRoutesDirection;
import com.rtddenver.model.dto.ErrorDTO;
import com.rtddenver.model.data.AlertEvent;
import com.rtddenver.model.dto.ActiveAlertDTO;

import com.rtddenver.model.dto.AlertEventDTO;
import com.rtddenver.model.dto.AlertRouteDTO;
import com.rtddenver.model.dto.RouteDTO;

import com.rtddenver.model.dto.RouteToAlertDTO;

import com.rtddenver.model.dto.RouteTypeDTO;
import com.rtddenver.model.dto.StagingAlertDetailDTO;
import com.rtddenver.model.dto.StagingRouteDTO;

import java.math.BigDecimal;

import java.time.LocalDate;

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
        List<AlertEvent> ae = null;
        List<AlertEventDTO> alerts = new ArrayList<AlertEventDTO>();

        ae = this.getActiveAlerts();
               if (ae.size() == 0) {
                   ErrorDTO error = new ErrorDTO("404", "No records found", "Currently there are no active alerts.");
                   dtoAlert = new ActiveAlertDTO(error);
               } else {
                   ae.forEach(alert -> { alerts.add(this.createAlertEventDTO(alert)); });
                   dtoAlert = new ActiveAlertDTO(alerts);
               }

            if (ae != null) {
                ae.clear();
                ae = null;
            }

        return dtoAlert;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @SuppressWarnings("unchecked")
    public AlertRouteDTO getActiveAlertRoutes() {
        AlertRouteDTO alertRouteDTO = new AlertRouteDTO();
        StagingAlertDetailDTO stagingAlertDetailDTO = null;
        List<AlertEvent> alertEventsList = null;
        List<AlertEvent> stationPNRAlertsList = null;
        List<Integer> alertEventIDList = null;
        RouteDTO routeDTO = null;
        RouteToAlertDTO routeToAlertDTO = null;
        StagingRouteDTO stagingRouteDTO = null;
        RouteTypeDTO routeTypeDTO = null;
        String alertAPILink = "http://APIWSGoesHere/alerts/";
        String bus_tmpRouteId = "";
        String bus_tmpAlertURL = "";
        String bus_separator = "";
        int iCnt_bus = 0;
        int iCntValue_bus = 0;
        String rail_tmpRouteId = "";
        String rail_tmpAlertURL = "";
        String rail_separator = "";
        int iCnt_rail = 0;
        int iCntValue_rail = 0;
        String commaStr = ",";
        String colonStr = ":";
        String charStr = "/";

        try {
            routeToAlertDTO = new RouteToAlertDTO();
            // First, get active alerts for bus & rail...
            alertEventsList = getActiveAlerts();
            if (alertEventsList.size() == 0) {
                ErrorDTO error = new ErrorDTO("", null, "Currently there are no active alerts.");
                alertRouteDTO.setError(error);
            } else {
                Map<String, List<AlertEventRoute>> routeToAlertMap = new HashMap<String, List<AlertEventRoute>>();
                Map<String, String> busTypeMap = new HashMap<String, String>();
                Map<String, String> railTypeMap = new HashMap<String, String>();

                // Next, iterate through the alert_event_id values and fetch routes for each
                for (int i = 0; i < alertEventsList.size(); i++) {
                    AlertEvent alertEvent = alertEventsList.get(i);
                    routeDTO = new RouteDTO();
                    alertEventIDList = new ArrayList<Integer>();
                    alertEventIDList.add(alertEvent.getAlertEventId());
                    List<AlertEventRoute> aerList = null;
                    List<AlertEventRoute> aerRouteList = new ArrayList<AlertEventRoute>();
                    aerList = em.createNamedQuery("findAllRoutes", AlertEventRoute.class)
                                .setParameter("alertEventIDList", alertEventIDList)
                                .getResultList();

                    // Create a RouteDTO object for the alert event and its effected routes
                    routeDTO.setAlertEventId(alertEvent.getAlertEventId());

                    Iterator<AlertEventRoute> itr = aerList.iterator();
                    while (itr.hasNext()) {
                        AlertEventRoute aer = itr.next();
                        aer.setAlertURL(alertAPILink + aer.getAlertEventId() + charStr + aer.getMasterRoute());
                        aerRouteList.add(aer);
                        if ("LIGHT RAIL".equalsIgnoreCase(aer.getRouteTypeName()) ||
                            "COMMUTER RAIL".equalsIgnoreCase(aer.getRouteTypeName())) {
                            // Create a map of unique routeIds for Rail
                            railTypeMap.put(aer.getRouteId(), null);
                        } else {
                            // Create a map of unique routeIds for Bus
                            busTypeMap.put(aer.getRouteId(), null);
                        }
                        // Create a map of unique routeIds
                        routeToAlertMap.put(aer.getRouteId(), null);

                    }

                    routeDTO.setRouteList(aerRouteList);
                    // Add the RouteDTO to the AlertRouteDTO
                    alertRouteDTO.addRouteDTO(routeDTO);

                } // End of master list of alertRoutes


                //Loop through the map of routeIds and associate each routeId with related alerts
                List<AlertEventRoute> routeListNew = null;
                Iterator<String> routeIdItr = routeToAlertMap.keySet().iterator();
                while (routeIdItr.hasNext()) {
                    String routeId = routeIdItr.next();
                    //System.out.println("== RouteId ====================================================");
                    //System.out.println("Map routeId: " + routeId);

                    //Iterate through rDTO to id RouteDTO pertinant to the current routeId
                    List<RouteDTO> routeDTOList = alertRouteDTO.getRouteDTO();
                    Iterator<RouteDTO> routeDTOItr = routeDTOList.iterator();

                    //While
                    routeListNew = new ArrayList<AlertEventRoute>();
                    //System.out.println("Route List contains " + routeDTOList.size() + " records");
                    //System.out.println("Iterating through RouteDTO list. Contains " + routeDTOList.size() + " records");
                    while (routeDTOItr.hasNext()) {
                        RouteDTO rteDTO = routeDTOItr.next();
                        //System.out.println("=======================================");
                        if (rteDTO.getRouteList() != null) {
                            //iterate through rteDTO.getRouteList() to get elements in this list
                            //System.out.println("Iterating through the RouteList... Records: " + rteDTO.getRouteList().size());
                            Iterator<AlertEventRoute> routeListItr = rteDTO.getRouteList().iterator();
                            while (routeListItr.hasNext()) {
                                AlertEventRoute aerRoute = routeListItr.next();
                                //System.out.println("Looking at Route List RouteId: " + aerRoute.getRouteId() + ", AlertEventId: " + aerRoute.getAlertEventId());
                                //if routeId matches save RouteDTO to new list
                                if (routeId.equalsIgnoreCase(aerRoute.getRouteId())) {
                                    //Add to new list
                                    routeListNew.add(aerRoute);
                                    //System.out.println("Assigning AlertEventId: " + aerRoute.getAlertEventId());
                                }
                            }
                            //System.out.println("End of RouteList");
                        }
                    }

                    //System.out.println("\nDone processing routeId: " + routeId);

                    if (routeListNew != null) {
                        routeToAlertMap.put(routeId, routeListNew);
                        //System.out.println("Adding route to routeToAlertMap...");
                    }

                    // end While
                }

                routeToAlertDTO.setRouteToAlertMap(routeToAlertMap);

                routeTypeDTO = new RouteTypeDTO();

                //Setup new DTO to define list of a single route and one-to-many alerts
                //iterate through routeToAlertMap object containing List in a Map of route and list of alerts
                Iterator<Map.Entry<String, List<AlertEventRoute>>> routeMapItr = routeToAlertMap.entrySet().iterator();
                String bus_tmpAlertEventId = "";
                String bus_tmpAlertEventRouteId = "";
                String b_separator = "";
                String bus_tmpRte = "";
                String rail_tmpAlertEventId = "";
                String rail_tmpAlertEventRouteId = "";
                String r_separator = "";
                String rail_tmpRte = "";

                while (routeMapItr.hasNext()) {
                    Map.Entry<String, List<AlertEventRoute>> aer = routeMapItr.next();

                    //iterate over busTypeMapItr, find a match key with aer.key, set the alertURL to combine all possible alertEventId(s) & append with AlertEventRouteId(s)
                    if (!aer.getKey().equalsIgnoreCase(bus_tmpRte)) {
                        bus_tmpAlertEventId = "";
                        bus_tmpAlertEventRouteId = "";
                        b_separator = "";
                        System.out.println("aer.getKey(): " + aer.getKey() + " | bus_tmpRte: " + bus_tmpRte);
                    }
                    Iterator<String> rIdItr = busTypeMap.keySet().iterator();
                    while (rIdItr.hasNext()) {
                        String routeId = rIdItr.next();
                        if (routeId.equalsIgnoreCase(aer.getKey())) {
                            Iterator<AlertEventRoute> alertListItr = aer.getValue().iterator();
                            while (alertListItr.hasNext()) {
                                AlertEventRoute aerL = alertListItr.next();
                                if (iCntValue_bus != 0 && iCntValue_bus < aer.getValue().size()) {
                                    b_separator = colonStr;
                                }
                                bus_tmpAlertEventId += b_separator + aerL.getAlertEventId();
                                bus_tmpAlertEventRouteId += b_separator + aerL.getAlertEventRoutesId();
                                System.out.println("bus_tmpAlertEventId: " + bus_tmpAlertEventId +
                                                   " | bus_tmpAlertEventRouteId: " + bus_tmpAlertEventRouteId);
                                iCntValue_bus++;
                            }
                            bus_tmpRte = routeId;
                        }
                    }
                    busTypeMap.replace(aer.getKey(),
                                       alertAPILink + bus_tmpAlertEventId + charStr + bus_tmpAlertEventRouteId);

                    //iterate over railTypeMapItr, find a match key with aer.key, set the alertURL to combine all possible alertEventId(s) & append with AlertEventRouteId(s)
                    if (!aer.getKey().equalsIgnoreCase(rail_tmpRte)) {
                        rail_tmpAlertEventId = "";
                        rail_tmpAlertEventRouteId = "";
                        r_separator = "";
                    }
                    Iterator<String> rIdItr2 = railTypeMap.keySet().iterator();
                    while (rIdItr2.hasNext()) {
                        String routeId = rIdItr2.next();
                        if (routeId.equalsIgnoreCase(aer.getKey())) {
                            Iterator<AlertEventRoute> alertListItr = aer.getValue().iterator();
                            while (alertListItr.hasNext()) {
                                AlertEventRoute aerL = alertListItr.next();
                                if (iCntValue_rail != 0 && iCntValue_rail < aer.getValue().size()) {
                                    r_separator = colonStr;
                                }
                                rail_tmpAlertEventId += r_separator + aerL.getAlertEventId();
                                rail_tmpAlertEventRouteId += r_separator + aerL.getAlertEventRoutesId();
                                iCntValue_rail++;
                            }
                            rail_tmpRte = routeId;
                        }
                    }
                    railTypeMap.replace(aer.getKey(),
                                        alertAPILink + rail_tmpAlertEventId + charStr + rail_tmpAlertEventRouteId);

                    //Add to new list
                    stagingRouteDTO = new StagingRouteDTO();
                    stagingRouteDTO.setRouteId(aer.getKey());
                    stagingRouteDTO.setAlertList(aer.getValue());
                    alertRouteDTO.addStagingRouteDTO(stagingRouteDTO);
                }

                //Bus List
                //iterate through busTypeMap to get elements in this list
                Iterator<Map.Entry<String, String>> busTypeMapItr = busTypeMap.entrySet().iterator();
                while (busTypeMapItr.hasNext()) {
                    Map.Entry<String, String> rteType = busTypeMapItr.next();
                    //System.out.println("RouteId: " + rteType.getKey() + " | AlertURL: " + rteType.getValue());
                    if (iCnt_bus != 0 && iCnt_bus < busTypeMap.size()) {
                        bus_separator = commaStr;
                    }
                    bus_tmpRouteId += bus_separator + rteType.getKey();
                    bus_tmpAlertURL += bus_separator + rteType.getValue();
                    iCnt_bus++;
                }

                if (bus_tmpRouteId != null) {
                    routeTypeDTO.setBusList(bus_tmpRouteId);
                }
                if (bus_tmpAlertURL != null) {
                    routeTypeDTO.setBusAlertURLList(bus_tmpAlertURL);
                }

                //Rail List
                //iterate through railTypeMap to get elements in this list
                Iterator<Map.Entry<String, String>> railTypeMapItr = railTypeMap.entrySet().iterator();
                while (railTypeMapItr.hasNext()) {
                    Map.Entry<String, String> rteType = railTypeMapItr.next();
                    //System.out.println("RouteId: " + rteType.getKey() + " | AlertURL: " + rteType.getValue());
                    if (iCnt_rail != 0 && iCnt_rail < railTypeMap.size()) {
                        rail_separator = commaStr;
                    }
                    rail_tmpRouteId += rail_separator + rteType.getKey();
                    rail_tmpAlertURL += rail_separator + rteType.getValue();
                    iCnt_rail++;
                }

                if (rail_tmpRouteId != null) {
                    routeTypeDTO.setRailList(rail_tmpRouteId);
                }
                if (rail_tmpAlertURL != null) {
                    routeTypeDTO.setRailAlertURLList(rail_tmpAlertURL);
                }

                alertRouteDTO.addRouteTypeDTO(routeTypeDTO);

                /*Comment out the line below - no need to create the object 'List<RouteToAlertDTO> routeToAlert' but still need the logic to create the Map<String, List<AlertEventRoute>>
                alertRouteDTO.addRouteToAlertDTO(routeToAlertDTO);
                */
            }

            // Second, get active alerts for Station/PNR...
            List<AlertEvent> aeStationPNR = new ArrayList<AlertEvent>();
            stationPNRAlertsList = getActiveStationPNRAlerts();
            if (stationPNRAlertsList.size() == 0) {
                ErrorDTO error = new ErrorDTO("", null, "Currently there are no active Station/park-n-Ride Alerts.");
                alertRouteDTO.setError(error);
            } else {
                Iterator<AlertEvent> stationPNRItr = stationPNRAlertsList.iterator();
                while (stationPNRItr.hasNext()) {
                    AlertEvent ae = stationPNRItr.next();
                    aeStationPNR.add(ae);
                }
                stagingAlertDetailDTO = new StagingAlertDetailDTO();
                stagingAlertDetailDTO.setAlertDetail(aeStationPNR);
                alertRouteDTO.addStagingAlertDetailDTO(stagingAlertDetailDTO);
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
    public ActiveAlertDTO getActiveAlertByID(String alertEventId, String alertEventRoutesId) {
        ActiveAlertDTO dtoAlertByID = null;
        StagingAlertDetailDTO stagingAlertDetailDTO = null;
        List<AlertEvent> aeList = null;
        List<AlertEventRoutesDirection> aerdList = null;
        List<Integer> alertEventIdList = null;
        List<Integer> alertEventRoutesIdList = null;
        try {
            /*
             * Capture alertEventId & alertEventRoutesId param and store them in arrays.
             * Example URL with these params: http://.../RiderAlert/resources/v1/alerts/18397:14261/27035:20499
             * '18397:14261' is the alertEventId where mulitple alertEventIds are appended with a colon.
             * '27035:20499' is the alertEventRoutesId where mulitple alertEventRoutesIds are appended with a colon.
             */
            String[] alertEventIdArray = alertEventId.split(":");
            String[] alertEventRoutesIdArray = alertEventRoutesId.split(":");
            alertEventIdList = new ArrayList<Integer>();
            alertEventRoutesIdList = new ArrayList<Integer>();
            for (int i = 0; i < alertEventIdArray.length; i++) {
                dtoAlertByID = new ActiveAlertDTO();
                //Get alert detail by alertEventId param
                alertEventIdList.add(new Integer(alertEventIdArray[i]));
                aeList = this.getAlertByID(alertEventIdList);
                System.out.println("i: " + i + " alertEventIDArray: " + alertEventIdArray[i] +
                                   "alertEventRoutesIdArray: " + alertEventRoutesIdArray[i]);

                //Get route direction detail by alertEventRoutesId param
                alertEventRoutesIdList.add(new Integer(alertEventRoutesIdArray[i]));
                aerdList = getRoutesDirectionByID(alertEventRoutesIdList);

                List<AlertEvent> aeByID = new ArrayList<AlertEvent>();
                Iterator<AlertEvent> alertEventsItr = aeList.iterator();
                while (alertEventsItr.hasNext()) {
                    AlertEvent ae = alertEventsItr.next();
                    ae.setTmp_alertEventRoutesId(new Integer(alertEventRoutesIdArray[i]));

                    List<AlertEventRoutesDirection> aerdID = new ArrayList<AlertEventRoutesDirection>();
                    Iterator<AlertEventRoutesDirection> aerdItr = aerdList.iterator();
                    while (aerdItr.hasNext()) {
                        AlertEventRoutesDirection aerd = aerdItr.next();
                        ae.setRouteDirectionDetail1(aerd.getDirectionId() + ": " + aerd.getDirectionAlert());
                        System.out.println("aerd.getAlertEventRoutesId: " + aerd.getAlertEventRoutesId());
                        aerdID.add(aerd);
                    }
                    aeByID.add(ae);

                    //dtoAlertByID.setActiveAlertList(aeByID);
                    stagingAlertDetailDTO = new StagingAlertDetailDTO();
                    stagingAlertDetailDTO.setAlertDetail(aeByID);
                    stagingAlertDetailDTO.setRouteDirectionDetail(aerdID);
                }

                dtoAlertByID.addStagingAlertDetailDTO(stagingAlertDetailDTO);
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

            if (aeList != null) {
                aeList.clear();
                aeList = null;
            }

            ErrorDTO error = new ErrorDTO("500", ex.getClass().getName(), ex.getMessage());
            dtoAlertByID = new ActiveAlertDTO(error);
        }

        return dtoAlertByID;
    }

    /**
     *  findRouteType
     *  @return String
     */
    private String findRouteId(String routeId) throws Exception {
        String rteType = null;
        if (routeId != null || routeId.indexOf(':') > 0) {
            rteType = routeId.substring(routeId.indexOf(':') + 1);
        }
        return rteType;
    }

    /**
     *  getAlertByID
     *  @param String alertID
     *  @return List<AlertEvents>
     */
    private List<AlertEvent> getAlertByID(List<Integer> alertID) {
        return em.createNamedQuery("findActiveAlertByID", AlertEvent.class)
                 .setParameter("alertDate", java.sql.Date.valueOf(LocalDate.now()))
                 .setParameter("alertEventId", alertID).getResultList();
    }

    /**
     *  getRoutesDirectionByID
     *  @param List<Integer> alertEventRoutesId
     *  @return List<AlertEventRoutesDirection>
     */
    private List<AlertEventRoutesDirection> getRoutesDirectionByID(List<Integer> alertEventRoutesId) throws Exception {
        return em.createNamedQuery("findRoutesDirectionByID", AlertEventRoutesDirection.class)
                 .setParameter("alertEventRoutesId", alertEventRoutesId).getResultList();
    }

    /**
     *  getActiveAlerts
     *  @return List<AlertEvents>
     */
    private List<AlertEvent> getActiveAlerts() {
        return em.createNamedQuery("findAllActiveAlerts", AlertEvent.class)
                 .setParameter("alertDate", java.sql.Date.valueOf(LocalDate.now())).getResultList();
    }

    /**
     *  getActiveStationPNRAlerts
     *  @return List<AlertEvents>
     */
    private List<AlertEvent> getActiveStationPNRAlerts() {
        return em.createNamedQuery("findAllActiveStationPNRAlerts", AlertEvent.class)
                 .setParameter("alertDate", java.sql
                                                .Date
                                                .valueOf(LocalDate.now()))
                 .getResultList();
    }

    /**
     *  getActiveAlerts
     *  @param int year
     *  @param int month
     *  @param int day
     *  @return List<AlertEvents>
     */
    private List<AlertEvent> getActiveAlerts(int year, int month, int day) throws Exception {
        return em.createNamedQuery("findAllActiveAlerts", AlertEvent.class)
                 .setParameter("alertDate", java.sql
                                                .Date
                                                .valueOf(LocalDate.of(year, month, day)))
                 .getResultList();
    }

    private AlertEventDTO createAlertEventDTO(AlertEvent alert) {
        return new AlertEventDTO.Builder().alertCategoryDetail(alert.getAlertCategoryDetail())
                                          .alertCategoryId(alert.getAlertCategoryId())
                                          .alertEventCreatedBy(alert.getAlertEventCreatedBy())
                                          .alertEventCreatedDate(alert.getAlertEventCreatedDate())
                                          .alertEventDisplayFlag(alert.getAlertEventDisplayFlag())
                                          .alertEventEffEndDate(alert.getAlertEventEffEndDate())
                                          .alertEventEffStartDate(alert.getAlertEventEffStartDate())
                                          .alertEventEmailBlast(alert.getAlertEventEmailBlast())
                                          .alertEventEmailIntFlag(alert.getAlertEventEmailIntFlag())
                                          .alertEventEmailTopic(alert.getAlertEventEmailTopic())
                                          .alertEventEndDate(alert.getAlertEventEndDate())
                                          .alertEventEndTimeType(alert.getAlertEventEndTimeType())
                                          .alertEventGeneralDesc(alert.getAlertEventGeneralDesc())
                                          .alertEventId(alert.getAlertEventId())
                                          .alertEventInfo(alert.getAlertEventInfo())
                                          .alertEventOperatorInfo(alert.getAlertEventOperatorInfo())
                                          .alertEventReason(alert.getAlertEventReason())
                                          .alertEventRouteLnAffected(alert.getAlertEventRouteLnAffected())
                                          .alertEventStartDate(alert.getAlertEventStartDate())
                                          .alertEventStartTimeType(alert.getAlertEventStartTimeType())
                                          .alertEventUpdatedBy(alert.getAlertEventUpdatedBy())
                                          .alertEventUpdatedDate(alert.getAlertEventUpdatedDate())
                                          .alertEventUserNotes(alert.getAlertEventUserNotes())
                                          .alertTypeId(alert.getAlertTypeId())
                                          .build();
    }
}

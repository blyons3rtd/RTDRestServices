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

@Stateless(name = "RouteService", mappedName = "RiderAlertService-EJBModel-RouteService")
public class RouteServiceBean implements RouteServiceLocal {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "riderAlertModel")
    private EntityManager em;

    public RouteServiceBean() {
    }
    
    public RouteDTO getRouteList() {
        RouteDTO dto = null;
        List<AlertEventRoute> aer = null;
        ArrayList<AlertEventRoute> busRailList = new ArrayList<AlertEventRoute>();
        
        try {
          aer = em.createNamedQuery("findAllRoutes", AlertEventRoute.class)
                                    .getResultList();
            if (aer.size() == 0) {
                ErrorDTO error = new ErrorDTO("", null, "Currently there are no records found.");
                dto = new RouteDTO(error);
            } else {
                for (int i=0; i<aer.size(); i++){
                    AlertEventRoute a = aer.get(i);
                    busRailList.add(a);
                    //System.out.println("Count: " + i);
                }
                //AlertEventRoutes a = aer.get(0);
                //dto = new RiderAlertDTO(a.getAlertEventId(), a.getAlertEventRoutesId(), a.getRouteId(), a.getMasterRoute(), a.getRouteTypeName(), busRailList);
                dto = new RouteDTO();
                dto.setBusList(busRailList);
            } 
        } catch (Exception ex) {
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
            
            if (aer != null) {
                aer.clear();
                aer = null;
            }
            
            ErrorDTO error = new ErrorDTO("500", ex.getClass().getName(), ex.getMessage());
            dto = new RouteDTO(error);
        }
        //System.out.println("this is a test!");
        return dto;
    }

//    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
//    public Object queryByRange(String jpqlStmt, int firstResult, int maxResults) {
//        Query query = em.createQuery(jpqlStmt);
//        if (firstResult > 0) {
//            query = query.setFirstResult(firstResult);
//        }
//        if (maxResults > 0) {
//            query = query.setMaxResults(maxResults);
//        }
//        return query.getResultList();
//    }
//
//    public <T> T persistEntity(T entity) {
//        em.persist(entity);
//        return entity;
//    }
//
//    public <T> T mergeEntity(T entity) {
//        return em.merge(entity);
//    }
//
//    public void removeAlertEventRoutes(AlertEventRoutes alertEventRoutes) {
//        alertEventRoutes = em.find(AlertEventRoutes.class, alertEventRoutes.getAlertEventRoutesId());
//        em.remove(alertEventRoutes);
//    }
//
//    /** <code>select o from AlertEventRoutes o WHERE o.alertEventId in (18641, 18601, 18585) ORDER BY o.routeSequence</code> */
//    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
//    public List<AlertEventRoutes> getFindAllRoutes() {
//        return em.createNamedQuery("findAllRoutes", AlertEventRoutes.class).getResultList();
//    }
}

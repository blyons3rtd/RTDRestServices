package com.rtddenver.service;

import com.rtddenver.model.data.AlertEventRoutes;
import com.rtddenver.model.dto.ErrorDTO;
import com.rtddenver.model.data.AlertEvents;
import com.rtddenver.model.dto.ActiveAlertDTO;

import com.rtddenver.model.dto.RouteDTO;

import java.math.BigDecimal;

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
    public RouteDTO getActiveAlertRoutes() {
        RouteDTO dtoRoute = null;
        List<AlertEvents> ae = null;
        
        try {
            //List<Integer> inClause = new ArrayList<Integer>();
            String inClause = "";
            String commaStr = ",";
            int iCnt = 0;
            ae = getActiveAlerts();
            if (ae.size() == 0) {
                ErrorDTO error = new ErrorDTO("", null, "Currently there are no active alerts.");
                dtoRoute = new RouteDTO(error);
            } else {
                //inClause = "(";
                for (int i=0; i<ae.size(); i++){
                    AlertEvents a = ae.get(i);
                    iCnt++;
                    //inClause.add(a.getAlertEventId().intValue());
                    inClause = inClause + a.getAlertEventId() + (iCnt < ae.size() ? commaStr : "");
                }
                //inClause = inClause + ")";
                
                //System.out.println("inClause: " + inClause);

                //Integer aeID = Integer.valueOf(inClause);
                List<AlertEventRoutes> aer = null;
                aer = em.createNamedQuery("findAllRoutes", AlertEventRoutes.class)
                        //.setParameter("alertEventIDList", inClause)
                        .getResultList();
                dtoRoute = new RouteDTO(aer);
                
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
            
            if (ae != null) {
                ae.clear();
                ae = null;
            }
            
            ErrorDTO error = new ErrorDTO("500", ex.getClass().getName(), ex.getMessage());
            dtoRoute = new RouteDTO(error);
        }
        
        return dtoRoute;
    }
    
    

    private List<AlertEvents> getActiveAlerts() throws Exception{
        List<AlertEvents> ae = null;
        ae = em.createNamedQuery("findAllActiveAlerts", AlertEvents.class)
               .getResultList();
        return ae;
    }
    
}

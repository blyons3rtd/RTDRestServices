package com.rtddenver.service.facade;

import com.rtddenver.model.dto.DirectorDTO;
import com.rtddenver.model.dto.ErrorDTO;
import com.rtddenver.service.BoardDirector;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import weblogic.logging.NonCatalogLogger;


@Stateless(name = "DirectorService")
public class DirectorServiceBean implements DirectorServiceLocal {
    
    private NonCatalogLogger ncl = new NonCatalogLogger("DirectorServiceBean");
    
    @Resource
    SessionContext sessionContext;
    
    @PersistenceContext(unitName = "DirectorLookup")
    private EntityManager em;
    
    public DirectorServiceBean() {
    }

    /** <code>select o from BoardDirector o WHERE UPPER(o.district) = UPPER(:district) AND UPPER(o.active) = 'Y' </code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DirectorDTO getDirectorByDistrict(String district) {

        DirectorDTO dto = null;
        List<BoardDirector> bdL = null;

        try {
            bdL = em.createNamedQuery("findDirectorByDistrict", BoardDirector.class)
                    .setParameter("district", district)
                    .getResultList();

            if (bdL.size() == 0) {
                ErrorDTO error = new ErrorDTO("400",null, "No director found for district " + district);
                dto = new DirectorDTO(error);
            } else {
                BoardDirector bd = bdL.get(0);
                dto = new DirectorDTO(bd.getDistrict(), bd.getDirectorFullName());
            }

        } catch (Exception ex) {
            ncl.error("Error querying and processing entity bean", ex);
            if (em != null) {
                em.clear();
                try {
                    em.close();
                } catch (Exception e) {
                    // do noting 
                    ncl.error("Error closing EntityManager", e);
                } finally {
                    em = null;
                }
            }
            
            if (bdL != null) {
                bdL.clear();
                bdL = null;
            }
            
            ErrorDTO error = new ErrorDTO("500", ex.getClass().getName(), ex.getMessage());
            dto = new DirectorDTO(error);
        }

        return dto;
    }
    
    /** <code>select o from BoardDirector o WHERE UPPER(o.active) = 'Y' order by DISTRICT</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<String, BoardDirector> getAllDirectors() {

        DirectorDTO dto = null;
        List<BoardDirector> bdL = null;
        Map<String, BoardDirector> directorMap = new HashMap<String, BoardDirector>();
        try {
            bdL = em.createNamedQuery("getAllDirectors", BoardDirector.class)
                    .getResultList();

            if (bdL.size() == 0) {
                ErrorDTO error = new ErrorDTO("500",null, "No directors retrieved from database");
                dto = new DirectorDTO(error);
            } else {
                int sz = bdL.size();
                for (int x=0; x < sz;x++) {
                    BoardDirector bd = bdL.get(0);
                    directorMap.put(bd.getDistrict(), bd);
                }
            }

        } catch (Exception ex) {
            ncl.error("Error querying and processing entity bean", ex);
            if (em != null) {
                em.clear();
                try {
                    em.close();
                } catch (Exception e) {
                    // do noting 
                    ncl.error("Error closing EntityManager", e);
                } finally {
                    em = null;
                }
            }
            
            if (bdL != null) {
                bdL.clear();
                bdL = null;
            }
            
            ErrorDTO error = new ErrorDTO("500", ex.getClass().getName(), ex.getMessage());
            dto = new DirectorDTO(error);
        }

        return directorMap;
    }
}

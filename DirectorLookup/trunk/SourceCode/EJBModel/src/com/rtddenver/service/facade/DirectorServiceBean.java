package com.rtddenver.service.facade;

import com.rtddenver.cache.DirectorNameSingleton;
import com.rtddenver.model.dto.DirectorDTO;
import com.rtddenver.model.dto.ErrorDTO;
import com.rtddenver.service.BoardDirector;

import com.rtddenver.util.StackTraceUtil;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless(name = "DirectorService")
public class DirectorServiceBean implements DirectorServiceLocal {

    private static final Logger LOGGER = LogManager.getLogger(DirectorServiceBean.class.getName());

    @Resource
    SessionContext sessionContext;

    @PersistenceContext(unitName = "DirectorLookup")
    private EntityManager em;

    public DirectorServiceBean() {
    }

    public DirectorDTO getDirectorByDistrict(String district) {
        DirectorDTO dto = null;
        try {
            if (DirectorNameSingleton.hasLoaded()) {
                if (district.equalsIgnoreCase("reload")) {
                    LOGGER.info("Reloading BoardDirector Map");
                    Map<String, BoardDirector> bdMap = getAllDirectors();
                    DirectorNameSingleton.initialize(bdMap);
                    throw new Exception("BoardDirector cache reloaded");
                } else {
                    //Get director info from an existing cached map
                    BoardDirector bd = DirectorNameSingleton.getDirectorByDistrict(district);
                    if (bd != null) {
                        dto = new DirectorDTO(bd.getDistrict(), bd.getDirectorFullName());
                    } else {
                        LOGGER.error("BoardDirector returned null");
                        ErrorDTO error = new ErrorDTO("500", "BoardDirector null", "BoardDirector null");
                        dto = new DirectorDTO(error);
                    }
                }
            } else {
                //dto = getDirectorByDistrict(district, true);
                Map<String, BoardDirector> bdMap = getAllDirectors();
                try {
                    DirectorNameSingleton.initialize(bdMap);
                    BoardDirector bd = DirectorNameSingleton.getDirectorByDistrict(district);
                    if (bd != null) {
                        dto = new DirectorDTO(bd.getDistrict(), bd.getDirectorFullName());
                    } else {
                        throw new Exception("Failed to find Director for District:" + district);
                    }

                } catch (Exception e) {
                    LOGGER.error(StackTraceUtil.getStackTrace(e));
                    ErrorDTO error = new ErrorDTO("500", e.getClass().getName(), e.getMessage());
                    dto = new DirectorDTO(error);
                }
            }
        } catch (Exception e) {
            LOGGER.error(StackTraceUtil.getStackTrace(e));
            ErrorDTO error = new ErrorDTO("500", e.getClass().getName(), e.getMessage());
            dto = new DirectorDTO(error);
        }
        return dto;
    }

    /** <code>select o from BoardDirector o WHERE UPPER(o.district) = UPPER(:district) AND UPPER(o.active) = 'Y' </code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DirectorDTO getDirectorByDistrict(String district, boolean dbCall) {

        DirectorDTO dto = null;
        List<BoardDirector> bdL = null;

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Entered getDirectorByDistrict... district:" + district);
        }

        try {
            bdL = em.createNamedQuery("findDirectorByDistrict", BoardDirector.class)
                    .setParameter("district", district)
                    .getResultList();

            if (bdL.size() == 0) {
                LOGGER.warn("No director found for district " + district);
                ErrorDTO error = new ErrorDTO("400", null, "No director found for district " + district);
                dto = new DirectorDTO(error);
            } else {
                BoardDirector bd = bdL.get(0);
                dto = new DirectorDTO(bd.getDistrict(), bd.getDirectorFullName());
            }

        } catch (Exception ex) {
            LOGGER.error("Error querying and processing entity bean: " + ex);
            if (em != null) {
                em.clear();
                try {
                    em.close();
                } catch (Exception e) {
                    // do noting
                    LOGGER.error("Error closing EntityManager: " + e);
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

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Entered getAllDirectors...");
        }

        Map<String, BoardDirector> directorMap = new HashMap<String, BoardDirector>();
        try {
            bdL = em.createNamedQuery("getAllDirectors", BoardDirector.class).getResultList();

            if (bdL.size() == 0) {
                LOGGER.error("No directors retrieved from database");
                ErrorDTO error = new ErrorDTO("500", null, "No directors retrieved from database");
                dto = new DirectorDTO(error);
            } else {
                int sz = bdL.size();
                for (int x = 0; x < sz; x++) {
                    BoardDirector bd = bdL.get(x);
                    directorMap.put(bd.getDistrict(), bd);
                    LOGGER.info("Added to BoardDirector Map: district:" + bd.getDistrict() + "; " +
                                bd.getDirectorLastName() + ", " + bd.getDirectorFirstName());
                }
            }

        } catch (Exception ex) {
            LOGGER.error("Error querying and processing entity bean: " + ex);
            if (em != null) {
                em.clear();
                try {
                    em.close();
                } catch (Exception e) {
                    // do noting
                    LOGGER.error("Error closing EntityManager: " + e);
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

package com.rtddenver.cache;

/**
 * This singleton class holds a map of board of director names.
 * This class is intended to reduce the number of database calls to retrieve this
 * rather static information.
 */
import com.rtddenver.service.BoardDirector;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class DirectorNameSingleton {

    private static final Logger LOGGER = LogManager.getLogger(DirectorNameSingleton.class.getName());

    private static DirectorNameSingleton _instance;

    private static Map<String, BoardDirector> directorMap = new HashMap<String, BoardDirector>();

    private static boolean hasLoaded = false;

    /**
     * Initialize DirectorNameSingleton with a Map of Board Director names
     * @param directorMap
     * @return
     * @throws Exception
     */
    public static DirectorNameSingleton initialize(Map<String, BoardDirector> directorMap) throws Exception {
        LOGGER.info("Initializing DirectorNameSingleton");
        if (!hasLoaded) {
            doInitialize(directorMap);
        }

        return _instance;
    }

    /**
     * This method permits forcing a re-loading of a Director Map
     * @param doInitialize
     * @return
     */
    public static DirectorNameSingleton initialize(boolean doInitialize,
                                                   Map<String, BoardDirector> newDirectorMap) throws Exception {
        if (doInitialize) {
            hasLoaded = false;
            doInitialize(newDirectorMap);
        }
        return _instance;
    }

    public static boolean hasLoaded() {
        return hasLoaded;
    }

    public static BoardDirector getDirectorByDistrict(String district) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Getting director name from cache... District: " + district);
        }
        BoardDirector bd = directorMap.get(district.toUpperCase());
        return bd;
    }

    private static synchronized void doInitialize(Map<String, BoardDirector> newDirectorMap) throws Exception {
        if (!hasLoaded) {
            LOGGER.info("Creating instance of DirectorNameSingleton");
            _instance = new DirectorNameSingleton();
            directorMap = newDirectorMap;
            hasLoaded = true;
            LOGGER.info("District:Director info has been cached");
        }
    }

}

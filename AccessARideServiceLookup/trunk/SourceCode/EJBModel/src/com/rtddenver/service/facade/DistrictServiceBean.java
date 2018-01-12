package com.rtddenver.service.facade;

import com.google.gson.Gson;

import com.rtd_denver.gis.District;

import com.rtddenver.model.dto.AccessARideDTO;
import com.rtddenver.model.dto.DistrictDTO;

import com.rtddenver.model.dto.ErrorDTO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.xml.ws.WebServiceRef;

import weblogic.logging.NonCatalogLogger;

@Stateless(name = "DistrictService")
public class DistrictServiceBean implements DistrictServiceLocal {

    private NonCatalogLogger ncl = new NonCatalogLogger("DistrictServiceBean");

    @Resource
    SessionContext sessionContext;

    @WebServiceRef(wsdlLocation =
                   "district.wsdl") //"http://maps.rtd-denver.com/services/whoismydirector/district.asmx?WSDL")
    private District district;

    public DistrictServiceBean() {
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DistrictDTO getAdaOnDayTimeLoc(String dayOfWeek, String time, double lon, double lat) {

        ncl.debug("Entered getAdaOnDayTimeLoc(" + dayOfWeek + ", " + time + ", " + lon + ", " + lat + ")");
        System.out.println("Entered getAdaOnDayTimeLoc(" + dayOfWeek + ", " + time + ", " + lon + ", " + lat + ")");

        DistrictDTO dto = null;
        boolean IsInWGS84 = true; //default
        boolean userError = false;
        String errorCode = "500";
        int retVal = -1;
        try {
            // Need to add basic data checks. Otherwise, the GIS service can fail in a way that does not readily
            // communicate back as a user error.
            if (dayOfWeek == null || "".equals(dayOfWeek.trim())) {
                userError = true;
                throw new Exception("Day of week not provided");
            }
            if (time == null || "".equals(time.trim())) {
                userError = true;
                throw new Exception("Time not provided");
            }
            if (!timeFormatValidator(time)) {
                userError = true;
                throw new Exception("Unexpected/unexcepted time value:" + time);
            }
            retVal = district.getDistrictSoap().getADAOnDayTimeLoc(dayOfWeek, time, lon, lat, IsInWGS84);
            dto = evaluateResponseCode(retVal, dayOfWeek, time);
        } catch (Exception e) {
            ncl.error("Error querying and processing entity bean", e);
            e.printStackTrace();
            if (userError) {
                errorCode = "400";
            }
            ErrorDTO error = new ErrorDTO(errorCode, e.toString(), e.getMessage());
            dto = new DistrictDTO(error);
        }

        return dto;
    }

    /**
     * Evaluates the response code returned from the GIS District service and assigns
     * the appropriate values for adaAvail and message in the AccessARideDTO
     * @param retVal
     * @param departureDay
     * @param departureTime
     * @return AccessARideDTO
     */
    private DistrictDTO evaluateResponseCode(int retVal, String departureDay, String departureTime) {
        String message = "";
        boolean adaAvail = false;
        switch (retVal) {
        case -2:
            message = "-2: Timed out waiting for District Service response";
            break;
        case -1:
            message = "-1: General error in calling District Service";
            break;
        case 0:
            message = "0: Location is not in ADA service area at any time or day";
            adaAvail = false;
            break;
        case 1:
            message = "1: Service for location is available on " + departureDay + " at " + departureTime;
            adaAvail = true;
            break;
        case 3:
            message = "3: Service for location is available at a different time on " + departureDay;
            adaAvail = false;
            break;
        case 5:
            message = "5: Service for location is available on a different day at " + departureTime;
            adaAvail = false;
            break;
        case 7:
            message = "7: Service for location is available at a different time on " + departureDay;
            message += " and service for location is available on a different day at " + departureTime;
            adaAvail = false;
            break;
        case 8:
            message = "8: Service for location is only available on other days and at other times";
            adaAvail = false;
            break;
        case 10:
            message = "10: Service for location is available at a different time on " + departureDay;
            message += " and service for location is also available on other days at times other than " + departureTime;
            adaAvail = false;
            break;
        default:
            message = "Unexpected return value: " + retVal;
            adaAvail = false;
            break;
        }

        DistrictDTO dto = new DistrictDTO(adaAvail, message);
        return dto;
    }

    /**
     * Validate time in format with regular expression
     * @param time
     * @return true valid time format, false invalid time format
     */
    private boolean timeFormatValidator(String time) {
        if (time == null) return false;
        Pattern pattern;
        Matcher matcher;
        String TIME_PATTERN = "(0[1-9]:[0-5][0-9]((AM)|(PM)|(am)|(pm)))|([1-9]:[0-5][0-9]((AM)|(PM)|(am)|(pm)))|(1[0-2]:[0-5][0-9]((AM)|(PM)|(am)|(pm)))|(([01]?[0-9]|2[0-3]):[0-5][0-9])";
        pattern = Pattern.compile(TIME_PATTERN);
        matcher = pattern.matcher(time.toUpperCase());
        return matcher.matches();
    }
}

package com.rtddenver.service.facade;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.rtddenver.model.dto.DistrictDTO;
import com.rtddenver.model.dto.ErrorDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


@Stateless(name = "AdaRestService", mappedName = "AccessARideLookup-EJBModel-AdaRestService")
public class AdaRestServiceBean implements AdaRestServiceLocal {

    private static final Logger LOGGER = LogManager.getLogger(AdaRestServiceBean.class.getName());

    @Resource
    SessionContext sessionContext;
    
    @EJB
    private PropertiesServiceLocal propertiesBean;

    // The URL assigned is a default. The build process will assign the appropriate URL for dev/cert and prod.
    // This assigned value gets set in the properties.xml file.
    private String adaRestUrl = "https://gis-test.rtd-denver.com/ADARESTful/rest/ada/adaAddressOnDayTimeLoc";

    public AdaRestServiceBean() {
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DistrictDTO getAdaAvailability(String street, String city, String zip, String departureDay,
                                          String departureTime) {
        DistrictDTO dto = null;
        HttpURLConnection conn = null;
        LOGGER.info("Entered getAdaAvailability... street:" + street + ", city:" + city + ", zip:" + zip +
                    ", departureDay:" + departureDay + ", departureTime:" + departureTime);
        try {
            departureTime = departureTime.toUpperCase(); // convert am/pm to AM/PM
            adaRestUrl = propertiesBean.getProperties().getProperty("adaRestSvcURL");
            ErrorDTO errorDTO = validateEntries(street, city, zip, departureDay, departureTime);

            if (errorDTO == null) {
                String params = assembleParameters(street, city, zip, departureDay, departureTime);
                URL url = new URL((adaRestUrl + params));
                LOGGER.info("Assembled service URL: " + url.toString());
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                    LOGGER.error(conn.getResponseCode());
                    errorDTO =
                        new ErrorDTO(conn.getResponseCode(), 1999, "Error calling ADA Service",
                                     "Unexpected error occurred. Retry query.", "");
                    dto = new DistrictDTO(errorDTO);
                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                    String jsonStr = "";
                    String aLine;
                    while ((aLine = br.readLine()) != null) {
                        jsonStr = jsonStr + aLine;
                    }
                    dto = parseJsonMessage(jsonStr);
                }
            } else {
                dto = new DistrictDTO(errorDTO);
            }

        } catch (IOException e) {
            LOGGER.error(e.toString());
            ErrorDTO errorDTO = new ErrorDTO(500, 1999, e.toString(), "Unexpected error occurred. Retry query.", "");
            dto = new DistrictDTO(errorDTO);

        } finally {
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Exception e) {
                    LOGGER.warn("Failed to disconnect connection. Reason: " + e.toString());
                }
            }
        }

        return dto;
    }


    /**
     * Assembles the parameters to be passed to the service
     * @param street
     * @param city
     * @param zip
     * @param serviceDay
     * @param serviceTime
     * @return String
     */
    private String assembleParameters(String street, String city, String zip, String serviceDay, String serviceTime) {
        String paramStr =
            "?address=" + street + "&city=" + city + "&zipcode=" + zip + "&serviceday=" + serviceDay + "&servicetime=" +
            serviceTime;
        return paramStr;
    }

    /**
     * Evaluates the value of "code" returned by the ADA service
     * @param code
     * @return boolean
     */
    private boolean evaluateCode(int code) {
        boolean adaAvail = false;
        // Current only need to set to true when needed
        switch (code) {
        case -2:
            break;
        case -1:
            break;
        case 0:
            break;
        case 1:
            adaAvail = true;
            break;
        case 3:
            break;
        case 5:
            break;
        case 7:
            break;
        case 8:
            break;
        case 10:
            break;
        default:
            break;
        }

        return adaAvail;
    }


    private DistrictDTO parseJsonMessage(String jsonStr) {
        LOGGER.info("Parsing JSON string... \n" + jsonStr);
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject) jsonParser.parse(jsonStr);
        DistrictDTO dto = null;
        /* Example of message returned by the ADA Service
        {"address":"3126 e 134th circle","city":"thornton","code":1,"departureDay":"Weekday","departureTime":"5:00AM",
         "desc":"Service for location is available on  at 5:00AM.","error":null,"lat":null,"lng":null,"zipcode":"80241"}
         */
        if (jo != null && !jo.isJsonNull()) {
            try {
                int code = 0;
                String desc = "";
                if (jo.get("code") != null && !jo.get("code").isJsonNull()) code = jo.get("code").getAsInt();
                if (jo.get("desc") != null && !jo.get("desc").isJsonNull()) desc = jo.get("desc").getAsString();
                boolean adaAvail = evaluateCode(code);
                dto = new DistrictDTO(adaAvail, desc);
            } catch (Exception e) {
                LOGGER.error(e);
                ErrorDTO err =
                    new ErrorDTO(500, 1502, "No information returned from service",
                                 "Failure. Please retry.", "");
                dto = new DistrictDTO(err);
            }
        } else {
            ErrorDTO err =
                new ErrorDTO(400, 1502, "No address returned from service", "Address not found in RTD service area", "");
            dto = new DistrictDTO(err);
        }

        return dto;
    }

    private ErrorDTO validateEntries(String street, String city, String zip, String departureDay,
                                     String departureTime) {
        LOGGER.info("Validating entries...");
        ErrorDTO dto = null;
        String detail = "";
        String msg = "";
        boolean err = false;

        if (street == null || "".equals(street.trim())) {
            msg = "Street missing. ";
            detail = "Street is a required entry. ";
            err = true;
        }
        if (city == null || "".equals(city.trim())) {
            msg = "City missing. ";
            detail = detail + "City is a required entry. ";
            err = true;
        }
        if (zip == null || "".equals(zip.trim())) {
            msg = "Zipcode missing. ";
            detail = detail + "Zipcode is a required entry. ";
            err = true;
        }
        if (departureDay == null || "".equals(departureDay.trim())) {
            msg = "Departure day missing. ";
            detail = detail + "Departure day is a required entry. ";
            err = true;
        }
        if (departureTime == null || "".equals(departureTime.trim())) {
            msg = "Departure time missing. ";
            detail = detail + "Departure time is a required entry. ";
            err = true;
        }
        if (!validateTime(departureTime)) {
            msg = "Departure time invalid. ";
            detail = detail + "Refer to API document for valid time format.";
            err = true;
        }
        if (err) {
            dto = new ErrorDTO(400, 1610, detail, msg, "", "");
        }
        return dto;
    }

    private boolean validateTime(String time) {
        // Validates time is in a format expected by the GIS service.
        // 12hr format
        String TIMEHOURS_PATTERN = "(1[012]|[1-9]):[0-5][0-9](AM|PM)";;
        Pattern pattern = Pattern.compile(TIMEHOURS_PATTERN);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }
}

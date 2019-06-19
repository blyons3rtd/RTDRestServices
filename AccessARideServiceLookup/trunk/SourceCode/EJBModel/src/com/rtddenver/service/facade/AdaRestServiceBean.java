package com.rtddenver.service.facade;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.rtddenver.model.dto.DistrictDTO;

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
    private String adaRestUrl = "http://localhost:7001/ADALookup/api/v1/addresses";

    public AdaRestServiceBean() {
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DistrictDTO getAdaAvailability(String street, String city, String zip, String departureDay,
                                          String departureTime) {
        DistrictDTO dto = null;
        HttpURLConnection conn = null;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Entered getAdaAvailability... street:" + street + ", city:" + city + ", zip:" + zip +
                         ", departureDay:" + departureDay + ", departureTime:" + departureTime);
        }
        try {
            departureTime = departureTime.toUpperCase(); // convert am/pm to AM/PM
            adaRestUrl = propertiesBean.getProperties().getProperty("adaRestSvcURL");
            String params = assembleParameters(street, city, zip, departureDay, departureTime);
            URL url = new URL((adaRestUrl + params));
            //if (LOGGER.isDebugEnabled()) {
                LOGGER.info("Assembled service URL: " + url.toString());
            //}
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                LOGGER.error(conn.getResponseCode());
                dto =
                    new DistrictDTO(conn.getResponseCode(), 1999, "Error calling ADA Service",
                                    "Unexpected error occurred. Retry query.", "");
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                String jsonStr = "";
                String aLine;
                while ((aLine = br.readLine()) != null) {
                    jsonStr = jsonStr + aLine;
                }
                dto = parseJsonMessage(jsonStr);
            }

        } catch (Exception e) {
            LOGGER.error(e.toString());
            dto = new DistrictDTO(500, 1999, e.toString(), "Unexpected error occurred. Retry query.", "");

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
    private String evaluateCode(int code) {
        String adaAvail = "false";
        // Current only need to set to true when needed
        switch (code) {
        case -2:
            break;
        case -1:
            break;
        case 0:
            break;
        case 1:
            adaAvail = "true";
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
        //if (LOGGER.isDebugEnabled()) {
            LOGGER.info("Parsing JSON string... \n" + jsonStr);
        //}
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject) jsonParser.parse(jsonStr);
        DistrictDTO dto = null;
        /* Example of a good message returned by the ADA Service
        {"address":"3126 e 134th circle","city":"thornton","code":1,"departureDay":"Weekday","departureTime":"5:00AM",
         "desc":"Service for location is available on  at 5:00AM.","error":null,"lat":null,"lng":null,"zipcode":"80241"}

        Example of an error message:
        {"detail":"Invalid Address. Couldn't geocode this address.","errorCode":"1502","errorStatus":400,"message":"Invalid Address. Couldn't geocode this address.","time":"2018-07-16T13:39:30.036-06:00","code":0}
         */
        if (jo != null && !jo.isJsonNull()) {
            try {
                int code = 0, status = 0;
                ;
                String desc = "", detail = "", message = "", time = "", moreInfo = "";

                if (jo.has("errorCode")) {
                    if (jo.get("errorCode") != null && !jo.get("errorCode").isJsonNull()) {
                        code = jo.get("errorCode").getAsInt();
                    }
                    if (jo.get("errorStatus") != null && !jo.get("errorStatus").isJsonNull()) {
                        status = jo.get("errorStatus").getAsInt();
                    }
                    if (jo.get("detail") != null && !jo.get("detail").isJsonNull()) {
                        detail = jo.get("detail").getAsString();
                    }
                    if (jo.get("message") != null && !jo.get("message").isJsonNull()) {
                        message = jo.get("message").getAsString();
                    }
                    //if (jo.get("more_info") != null && !jo.get("more_info").isJsonNull())
                    //    moreInfo = jo.get("moreInfo").getAsString();
                    if (jo.get("time") != null && !jo.get("time").isJsonNull()) {
                        time = jo.get("time").getAsString();
                    }
                    dto = new DistrictDTO(status, code, detail, message, moreInfo, time);
                } else {
                    if (jo.get("code") != null && !jo.get("code").isJsonNull()) {
                        code = jo.get("code").getAsInt();
                    }
                    if (jo.get("desc") != null && !jo.get("desc").isJsonNull()) {
                        desc = jo.get("desc").getAsString();
                    }
                    String adaAvail = evaluateCode(code);
                    dto = new DistrictDTO(adaAvail, desc);
                }
            } catch (Exception e) {
                LOGGER.error(e);
                dto = new DistrictDTO(500, 1502, "No information returned from service", "Failure. Please retry.", "");
            }
        } else {
            dto =
                new DistrictDTO(400, 1502, "No address returned from service", "Address not found in RTD service area",
                                "");
        }

        return dto;
    }

}

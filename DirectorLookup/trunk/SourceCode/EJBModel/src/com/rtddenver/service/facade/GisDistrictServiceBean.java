package com.rtddenver.service.facade;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.rtddenver.model.dto.DistrictDTO;
import com.rtddenver.util.StackTraceUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.annotation.Resource;

import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


@Stateless(name = "GisDistrictService")
public class GisDistrictServiceBean implements GisDistrictServiceLocal {

    private static final Logger LOGGER = LogManager.getLogger(GisDistrictServiceBean.class.getName());

    @Resource
    SessionContext sessionContext;

    @EJB
    private PropertiesServiceLocal propertiesBean;

    // The assigned value gets set in the properties.xml file.
    private String districtRestUrl = "";

    public GisDistrictServiceBean() {
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DistrictDTO getDistrictForAddress(String street, String city, String zip) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("GisDistrictServiceBean.getDistrictForAddress() address:" + street + ", " + city + ", " + zip);
        }

        DistrictDTO dto = null;
        HttpURLConnection conn = null;

        try {

            districtRestUrl = propertiesBean.getProperties().getProperty("DistrictSvcURL");

            if (dto == null) {
                String params = assembleParameters(street, city, zip);
                URL url = new URL((districtRestUrl + params));
                LOGGER.info("Assembled service URL: " + url.toString());
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                    LOGGER.error("GIS Get District service call returned: " + conn.getResponseCode() + ", " + conn.getResponseMessage());
                    dto = new DistrictDTO(conn.getResponseCode(), 1999, "Error calling GIS Get District service",
                                     "Unexpected error occurred. Retry query. " + conn.getResponseMessage(), "");
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
                // Will return an error
            }

        } catch (Exception e) {
            LOGGER.error("Error querying and processing entity bean: " + e);
            e.printStackTrace();
            dto = new DistrictDTO(500, 1502, e.getMessage(), "Error calling GISDistrictService", "");
        }

        return dto;
    }

    /**
     * Assembles the parameters to be passed to the GIS service
     * @param street
     * @param city
     * @param zip
     * @return String
     */
    private String assembleParameters(String street, String city, String zip) {
        String paramStr = "?address=" + street + "&city=" + city + "&zipcode=" + zip;
        return paramStr;
    }

    private DistrictDTO parseJsonMessage(String jsonStr) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Parsing JSON string... \n" + jsonStr);
        }
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject) jsonParser.parse(jsonStr);
        DistrictDTO dto = null;
        /* Example of message returned by the GIS Get District Service
        {"address":"3126 e 134th circle","city":"thornton","desc":null,"district":"K","lat":"39.9355078","lng":"-104.9479889","zipcode":"80241"}
         */
        if (jo != null && !jo.isJsonNull()) {
            if (jo.get("errorCode") != null && !jo.get("errorCode").isJsonNull()) {
                LOGGER.info("Received an error from the GIS service");
                int status = 0, code = 0;
                String detail = "", message = "", moreInfo = "", time = "";
                try {
                    //LOGGER.info("JsonObject:" + jo.toString());
                    // DistrictDTO(String address, String city, String district, String desc, String lat, String lng, String zipcode)
                    if (jo.get("errorCode") != null && !jo.get("errorCode").isJsonNull())
                        code = jo.get("errorCode").getAsInt();
                    if (jo.get("errorStatus") != null && !jo.get("errorStatus").isJsonNull())
                        status = jo.get("errorStatus").getAsInt();
                    if (jo.get("detail") != null && !jo.get("detail").isJsonNull())
                        detail = jo.get("detail").getAsString();
                    if (jo.get("message") != null && !jo.get("message").isJsonNull())
                        message = jo.get("message").getAsString();
                    //if (jo.get("more_info") != null && !jo.get("more_info").isJsonNull())
                    //    moreInfo = jo.get("moreInfo").getAsString();
                    if (jo.get("time") != null && !jo.get("time").isJsonNull())
                        time = jo.get("time").getAsString();
                    dto = new DistrictDTO(status, code, detail, message, moreInfo, time);
                } catch (Exception e) {
                    LOGGER.error(StackTraceUtil.getStackTrace(e));
                    dto = new DistrictDTO(500, 1502, "No information returned from service", "Failure. Please retry.", "");
                }
            } else {
                String address = "", city = "", district = "", desc = "", lat = "", lng = "", zipcode = "";
                try {
                    //LOGGER.info("JsonObject:" + jo.toString());
                    // DistrictDTO(String address, String city, String district, String desc, String lat, String lng, String zipcode)
                    if (jo.get("address") != null && !jo.get("address").isJsonNull())
                        address = jo.get("address").getAsString();
                    if (jo.get("city") != null && !jo.get("city").isJsonNull())
                        city = jo.get("city").getAsString();
                    if (jo.get("district") != null && !jo.get("district").isJsonNull())
                        district = jo.get("district").getAsString();
                    if (jo.get("desc") != null && !jo.get("desc").isJsonNull())
                        desc = jo.get("desc").getAsString();
                    if (jo.get("lat") != null && !jo.get("lat").isJsonNull())
                        lat = jo.get("lat").getAsString();
                    if (jo.get("lng") != null && !jo.get("lng").isJsonNull())
                        lng = jo.get("lng").getAsString();
                    if (jo.get("zipcode") != null && !jo.get("zipcode").isJsonNull())
                        zipcode = jo.get("zipcode").getAsString();

                    dto = new DistrictDTO(address, city, district, desc, lat, lng, zipcode);
                } catch (Exception e) {
                    LOGGER.error(StackTraceUtil.getStackTrace(e));
                    dto = new DistrictDTO(500, 1502, "No information returned from service", "Failure. Please retry.", "");
                }
            }
        } else {
            dto = new DistrictDTO(400, 1502, "No address returned from service", "Address not found in RTD service area", "");
        }

        return dto;
    }

}

package com.rtddenver.service.facade;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.rtddenver.model.dto.DistrictDTO;
import com.rtddenver.model.dto.ErrorDTO;
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

    // The URL assigned is a default. The build process will assign the appropriate URL for dev/cert and prod.
    // This assigned value gets set in the properties.xml file.
    private String gisRestUrl = "http://gis-app-c01.rtd-denver.com/:8080/DistrictRESTful/api/v1/district/getDistrict";

    //private District district;

    //private static final String districtSvc = "#%7Bhttp%3A%2F%2Fgis.rtd-denver.com%7DDistrict";

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

            gisRestUrl = propertiesBean.getProperties().getProperty("gisRestSvcURL");
            ErrorDTO errorDTO = validateEntries(street, city, zip);

            if (errorDTO == null) {
                street = street.replaceAll("%20", " "); // Convert HTML encoded space characters
                String params = assembleParameters(street, city, zip);
                URL url = new URL((gisRestUrl + params));
                LOGGER.info("Assembled service URL: " + url.toString());
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                    LOGGER.error(conn.getResponseCode());
                    errorDTO =
                        new ErrorDTO(conn.getResponseCode(), 1999, "Error calling GIS Get District service",
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

        } catch (Exception e) {
            LOGGER.error("Error querying and processing entity bean: " + e);
            e.printStackTrace();
            //int status, String code, String detail, String message, String moreInfo
            ErrorDTO error = new ErrorDTO(500, 1502, e.getMessage(), "Error calling GISDistrictService", "");
            dto = new DistrictDTO(error);
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
        street = street.replaceAll(" ", "%20");
        city = city.replaceAll(" ", "%20");
        String paramStr = "?address=" + street + "&city=" + city + "&zipcode=" + zip;
        return paramStr;
    }

    private ErrorDTO validateEntries(String street, String city, String zip) {
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
        if (err) {
            dto = new ErrorDTO(400, 1610, detail, msg, "");
        }
        return dto;
    }

    private DistrictDTO parseJsonMessage(String jsonStr) {
        LOGGER.info("Parsing JSON string... \n" + jsonStr);
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject) jsonParser.parse(jsonStr);
        DistrictDTO dto = null;
        /* Example of message returned by the GIS Get District Service
        {"address":"3126 e 134th circle","city":"thornton","desc":null,"district":"K","lat":"39.9355078","lng":"-104.9479889","zipcode":"80241"}
         */
        if (jo != null && !jo.isJsonNull()) {
            if (jo.get("status") != null && !jo.get("status").isJsonNull()) {
                LOGGER.info("Received an error from the GIS service");
                int status = 0, code = 0;
                String detail = "", message = "", moreInfo = "", time = "";
                try {
                    LOGGER.info("JsonObject:" + jo.toString());
                    // DistrictDTO(String address, String city, String district, String desc, String lat, String lng, String zipcode)
                    if (jo.get("code") != null && !jo.get("code").isJsonNull())
                        code = jo.get("code").getAsInt();
                    if (jo.get("status") != null && !jo.get("status").isJsonNull())
                        status = jo.get("status").getAsInt();
                    if (jo.get("detail") != null && !jo.get("detail").isJsonNull())
                        detail = jo.get("detail").getAsString();
                    if (jo.get("message") != null && !jo.get("message").isJsonNull())
                        message = jo.get("message").getAsString();
                    if (jo.get("more_info") != null && !jo.get("more_info").isJsonNull())
                        moreInfo = jo.get("moreInfo").getAsString();
                    if (jo.get("time") != null && !jo.get("time").isJsonNull())
                        time = jo.get("time").getAsString();
                    ErrorDTO err = new ErrorDTO(status, code, detail, message, moreInfo, time);
                    dto = new DistrictDTO(err);
                } catch (Exception e) {
                    LOGGER.error(StackTraceUtil.getStackTrace(e));
                    ErrorDTO err =
                        new ErrorDTO(500, 1502, "No information returned from service", "Failure. Please retry.", "");
                    dto = new DistrictDTO(err);
                }
            } else {
                String address = "", city = "", district = "", desc = "", lat = "", lng = "", zipcode = "";
                try {
                    LOGGER.info("JsonObject:" + jo.toString());
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
                    ErrorDTO err =
                        new ErrorDTO(500, 1502, "No information returned from service", "Failure. Please retry.", "");
                    dto = new DistrictDTO(err);
                }
            }
        } else {
            ErrorDTO err =
                new ErrorDTO(400, 1502, "No address returned from service", "Address not found in RTD service area",
                             "");
            dto = new DistrictDTO(err);
        }

        return dto;
    }

}

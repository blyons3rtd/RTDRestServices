package com.rtddenver.service.facade;

import com.google.gson.Gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.rtd_denver.geocoder.GeocoderService;

import com.rtddenver.model.dto.ErrorDTO;
import com.rtddenver.model.dto.GeoCodeAddressDTO;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.xml.ws.WebServiceRef;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


@Stateless(name = "GeoCoderService")
public class GeoCoderServiceBean implements GeoCoderServiceLocal {


    private static final Logger LOGGER = LogManager.getLogger(GeoCoderServiceBean.class.getName());
    
    
    @Resource
    SessionContext sessionContext;

    @WebServiceRef(wsdlLocation = "geocoder.wsdl") 
        //"http://maps.rtd-denver.com/RTDGeocoder3A/GeocoderService.asmx?WSDL#%7Bhttp%3A%2F%2Frtd-denver.com%2Fgeocoder%7DGeocoderService")
    private GeocoderService geocoderService;

    public GeoCoderServiceBean() {
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public GeoCodeAddressDTO getGeoCodeAddress(String street, String city, String zip, int options,
                                               boolean returnInWGS84) {
        GeoCodeAddressDTO dto = null;

        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("GeoCoderServiceBean.getGeoCodeAddress() street:" + street + ", city:" + city + ", zip:" + zip +
                  ", returnInWGS84:" + returnInWGS84);
        }
        //System.out.println("GeoCoderServiceBean.getGeoCodeAddress() street:" + street + ", city:" + city + ", zip:" +
        //                   zip + ", returnInWGS84:" + returnInWGS84);

        try {
            street = street.replaceAll("%20", " ");

            String str = geocoderService.getGeocoderInterface().jsonGeocodeAddress(street, city, zip, returnInWGS84);

            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("GeoCoderServiceBean.getGeoCodeAddress() Geocoder Service Response: " + str);
            }
            //System.out.println("GeoCoderServiceBean.getGeoCodeAddress() Geocoder Service Response: " + str);

            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject) jsonParser.parse(str);
            if (jo != null) {
                JsonElement count = jo.get("Count");
                if (count != null) {
                    if (count.getAsInt() > 0) {
                        JsonArray jsonArr = jo.getAsJsonArray("Candidates");
                        if (jsonArr != null && jsonArr.size() > 0) {
                            JsonObject candidate = jsonArr.get(0).getAsJsonObject(); //Only need the first item
                            JsonElement inRtdDistrictE = candidate.get("InRTDDistrict");
                            JsonElement xE = candidate.get("X");
                            JsonElement yE = candidate.get("Y");
                            Gson googleJson = new Gson();
                            boolean inRtdDistrict = googleJson.fromJson(inRtdDistrictE, Boolean.class);
                            double x = googleJson.fromJson(xE, Double.class);
                            double y = googleJson.fromJson(yE, Double.class);
                            dto = new GeoCodeAddressDTO(inRtdDistrict, x, y);
                        } else {
                            ErrorDTO err =
                                new ErrorDTO("400", "No address returned from GeocodeAddress service",
                                             "Address not found in RTD service area");
                            dto = new GeoCodeAddressDTO(err);
                        }
                    } else {
                        ErrorDTO err =
                            new ErrorDTO("400", "No address returned from GeocodeAddress service",
                                         "Address not found in RTD service area");
                        dto = new GeoCodeAddressDTO(err);
                    }
                } else {
                    ErrorDTO err =
                        new ErrorDTO("400", "No address returned from GeocodeAddress service",
                                     "Address not found in RTD service area");
                    dto = new GeoCodeAddressDTO(err);
                }
            } else {
                ErrorDTO err =
                    new ErrorDTO("400", "No address returned from GeocodeAddress service",
                                 "Address not found in RTD service area");
                dto = new GeoCodeAddressDTO(err);
            }

        } catch (Exception e) {
            LOGGER.error("Error querying and processing entity bean", e);
            e.printStackTrace();
            ErrorDTO err = new ErrorDTO("500", e.toString(), "Error querying and processing GeoCoder service");
            dto = new GeoCodeAddressDTO(err);
        }

        return dto;
    }

}

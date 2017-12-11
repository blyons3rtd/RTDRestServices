package com.rtddenver.service.facade;

import com.google.gson.Gson;

import com.rtd_denver.maps.District;

import com.rtddenver.model.dto.DistrictDTO;

import com.rtddenver.model.dto.ErrorDTO;

import java.net.URL;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.xml.ws.WebServiceRef;

import weblogic.logging.NonCatalogLogger;

@Stateless(name = "GisDistrictService")
public class GisDistrictServiceBean implements GisDistrictServiceLocal {
    
    private NonCatalogLogger ncl = new NonCatalogLogger("GisDistrictServiceBean");
    
    @Resource
    SessionContext sessionContext;

    @WebServiceRef(wsdlLocation = "whoismydirector.wsdl") //"http://maps.rtd-denver.com/services/whoismydirector/district.asmx?WSDL")
    private District district;

    private static final String districtSvc = "#%7Bhttp%3A%2F%2Fgis.rtd-denver.com%7DDistrict";

    public GisDistrictServiceBean() {
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DistrictDTO getDistrictForAddress(String street, String city, String zip) {
        
        ncl.debug("GisDistrictServiceBean.getDistrictForAddress() street:" + street + ", city:" + city + ", zip:" + zip);
        DistrictDTO dto = null;
        try {
            street = street.replaceAll("%20", " ");
            String json = district.getDistrictSoap().getDistrict(street, city, zip);
            dto = new Gson().fromJson(json, DistrictDTO.class);
        } catch (Exception e) {
            ncl.error("Error querying and processing entity bean", e);
            e.printStackTrace();
            dto = new DistrictDTO("500", e.getMessage(), "Error calling GISDistrictService");
        }

        return dto;
    }
    
}

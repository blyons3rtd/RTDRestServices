package com.rtddenver.service.facade;

import com.google.gson.Gson;

import com.rtd_denver.gis.District;

import com.rtddenver.model.dto.DistrictDTO;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.xml.ws.WebServiceRef;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


@Stateless(name = "GisDistrictService")
public class GisDistrictServiceBean implements GisDistrictServiceLocal {

    private static final Logger LOGGER = LogManager.getLogger(GisDistrictServiceBean.class.getName());

    @Resource
    SessionContext sessionContext;

    @WebServiceRef(wsdlLocation = "whoismydirector.wsdl")
    
    private District district;

    //private static final String districtSvc = "#%7Bhttp%3A%2F%2Fgis.rtd-denver.com%7DDistrict";

    public GisDistrictServiceBean() {
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DistrictDTO getDistrictForAddress(String street, String city, String zip) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("GisDistrictServiceBean.getDistrictForAddress() address:" + street + ", " + city + ", " + zip);
        }
        
        DistrictDTO dto = null;
        try {
            street = street.replaceAll("%20", " ");  // Convert HTML encoded space characters
            //dto = testDto(street, city, zip);  // Used to bypass the GIS service call for test purposes
            district = new District();
            
            // TODO - Look for GIS to replace the current Soap svc with a new REST svc
            String json = district.getDistrictSoap().getDistrict(street, city, zip);
            dto = new Gson().fromJson(json, DistrictDTO.class);
            
        } catch (Exception e) {
            LOGGER.error("Error querying and processing entity bean: " + e);
            e.printStackTrace();
            dto = new DistrictDTO("500", e.getMessage(), "Error calling GISDistrictService");
        }

        return dto;
    }
    
    // Test method
    private DistrictDTO testDto(String street, String city, String zip) {
        DistrictDTO dto = new DistrictDTO();
        dto.setAddress(street + ", " + city + ", " + zip);
        dto.setMessage("");
        dto.setValue("Z");
        return dto;
    }
    

}

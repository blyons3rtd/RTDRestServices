package com.rtddenver.service.facade;

import com.google.gson.Gson;

import com.rtd_denver.gis.District;

import com.rtddenver.model.dto.DistrictDTO;

import com.rtddenver.model.dto.ErrorDTO;

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

    @WebServiceRef(wsdlLocation = "district.wsdl") //"http://maps.rtd-denver.com/services/whoismydirector/district.asmx?WSDL")
    private District district;

    public DistrictServiceBean() {
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public DistrictDTO getAdaOnDayTimeLoc(String dayOfWeek, String time, double lon, double lat) {
        
        System.out.println("Entered getAdaOnDayTimeLoc(" + dayOfWeek + ", " + time + ", " + lon + ", " + lat + ")");
        
        DistrictDTO dto = null;
        boolean IsInWGS84 = true; //default
        int retVal = -1;
        try {
            System.out.println("District Service WSDL location: " + district.getWSDLDocumentLocation().toString());
            retVal = district.getDistrictSoap().getADAOnDayTimeLoc(dayOfWeek, time, lon, lat, IsInWGS84);
            dto = new DistrictDTO(retVal);
        } catch (Exception e) {
            ncl.error("Error querying and processing entity bean", e);
            e.printStackTrace();
            ErrorDTO error = new ErrorDTO("500", e.toString(), e.getMessage());
            dto = new DistrictDTO(error);
        }
        
        return dto;
    }
    
}

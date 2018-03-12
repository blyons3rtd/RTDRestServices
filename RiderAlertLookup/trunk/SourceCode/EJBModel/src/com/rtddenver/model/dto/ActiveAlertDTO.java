package com.rtddenver.model.dto;

import com.rtddenver.model.data.AlertEventRoutesDirection;

import com.rtddenver.model.data.AlertEvent;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "alertEvents")
public class ActiveAlertDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @XmlElement(name = "alertDetailList")
    private List<StagingAlertDetailDTO> stagingAlertDetailList;
    
    @XmlElement(name = "activeAlertList")
    private List<AlertEventDTO> activeAlertList;

//    @XmlElement(name = "routesDirectionDetail")
//    private List<AlertEventRoutesDirection> routesDirectionDetail;
    
    @XmlElement(name = "Error")
    private ErrorDTO error = null;
    
    public ActiveAlertDTO() {
        super();
    }

    public ActiveAlertDTO(List<AlertEventDTO> activeAlertList) {
        this.activeAlertList = activeAlertList;
    }
   
//    public ActiveAlertDTO(List<StagingAlertDetailDTO> stagingAlertDetailDTO) {
//        this.stagingAlertDetailList = stagingAlertDetailDTO;
//    }
    
    public ActiveAlertDTO(ErrorDTO error) {
        this.error = error;
    }

    public void addStagingAlertDetailDTO(StagingAlertDetailDTO stagingAlertDetailDTO) {
        if (this.stagingAlertDetailList == null) {
            this.stagingAlertDetailList = new ArrayList<StagingAlertDetailDTO>();
        }
        this.stagingAlertDetailList.add(stagingAlertDetailDTO);
    }
    
    public List<StagingAlertDetailDTO> getStagingAlertDetailDTO() {
        return stagingAlertDetailList;
    }
    
    public void setActiveAlertList(List<AlertEventDTO> activeAlertList) {
        this.activeAlertList = activeAlertList;
    }

    public List<AlertEventDTO> getActiveAlertList() {
        return activeAlertList;
    }
    

//    public void addRoutesDirectionDTO(List<AlertEventRoutesDirection> rteDir) {
//        this.routesDirectionDetail = rteDir;
//    }
}

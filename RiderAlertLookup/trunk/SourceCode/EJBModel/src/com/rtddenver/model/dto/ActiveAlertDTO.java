package com.rtddenver.model.dto;

import com.rtddenver.model.data.AlertEventRoutesDirection;

import com.rtddenver.model.data.AlertEvents;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "alertEvents")
public class ActiveAlertDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @XmlElement(name = "alertDetailList")
    private List<StagingAlertDetailDTO> alertDetailList;
    
    @XmlElement(name = "activeAlertList")
    private List<AlertEvents> activeAlertList;

//    @XmlElement(name = "routesDirectionDetail")
//    private List<AlertEventRoutesDirection> routesDirectionDetail;
    
    @XmlElement(name = "Error")
    private ErrorDTO error = null;
    
    public ActiveAlertDTO() {
        super();
    }
    
    public ActiveAlertDTO(List<StagingAlertDetailDTO> stagingAlertDetailDTO) {
        this.alertDetailList = stagingAlertDetailDTO;
    }
    
    public void addStagingAlertDetailDTO(StagingAlertDetailDTO stagingAlertDetailDTO) {
        if (this.alertDetailList == null) {
            this.alertDetailList = new ArrayList<StagingAlertDetailDTO>();
        }
        this.alertDetailList.add(stagingAlertDetailDTO);
    }
    
    public List<StagingAlertDetailDTO> getStagingAlertDetailDTO() {
        return alertDetailList;
    }
    
    public void setActiveAlertList(List<AlertEvents> activeAlertList) {
        this.activeAlertList = activeAlertList;
    }

    public List<AlertEvents> getActiveAlertList() {
        return activeAlertList;
    }
    
//    public ActiveAlertDTO(List<AlertEvents> activeAlertList) {
//        this.activeAlertList = activeAlertList;
//    }
    

    
    public ActiveAlertDTO(ErrorDTO error) {
        this.error = error;
    }

//    public void addRoutesDirectionDTO(List<AlertEventRoutesDirection> rteDir) {
//        this.routesDirectionDetail = rteDir;
//    }
}

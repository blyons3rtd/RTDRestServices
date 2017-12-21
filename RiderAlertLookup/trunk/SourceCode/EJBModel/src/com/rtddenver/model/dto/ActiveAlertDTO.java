package com.rtddenver.model.dto;

import com.rtddenver.model.data.AlertEvents;
import java.io.Serializable;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "alertEvents")
public class ActiveAlertDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @XmlElement(name = "activeAlertList")
    private List<AlertEvents> activeAlertList;
    
    @XmlElement(name = "Error")
    private ErrorDTO error = null;
    
    public ActiveAlertDTO() {
        super();
    }
    
    public ActiveAlertDTO(List<AlertEvents> activeAlertList) {
        this.activeAlertList = activeAlertList;
    }
    
    public ActiveAlertDTO(ErrorDTO error) {
        this.error = error;
    }
}

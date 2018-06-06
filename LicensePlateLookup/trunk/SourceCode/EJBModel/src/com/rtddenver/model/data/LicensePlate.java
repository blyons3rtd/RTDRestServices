package com.rtddenver.model.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.ReadOnly;

//***********************************************************
/* Description:
/*
/*
/* @author Luis Roche
/* @version 1.0, 11/17/2017
 */ 
//***********************************************************
@Entity
@ReadOnly
@NamedQueries({ @NamedQuery(name = "findByLicensePlateNumber",
                            query = "SELECT o FROM LicensePlate o WHERE o.plateNumber = :plateNumber")
                })
@Table(name = "LICENSE_PLATE_MASTER", schema = "CHK_PLT")
public class LicensePlate implements Serializable {
    @SuppressWarnings("compatibility:-45716201554151264")
    private static final long serialVersionUID = -6476394450503874613L;

    @Column(name = "PLATE_NUMBER")
    @Id 
    private String plateNumber = null;
    @Column(name = "IN_DISTRICT")
    private int inDistrict = 0;
    @Column(name = "GEOCODED")
    private int geocoded = 0;

    /**
     * LicensePlate
     */
    public LicensePlate() {
        super();
    }

    /**
     * getInDistrict
     * @return int
     */
    public int getInDistrict() {
        return this.inDistrict;
    }

    /**
     * getPlateNumber
     * @return String
     */
    public String getPlateNumber() {
        return this.plateNumber;
    }
    
    /**
     * getGeocoded
     * @return int
     */
    public int getGeocoded() {
        return this.geocoded;
    }
}
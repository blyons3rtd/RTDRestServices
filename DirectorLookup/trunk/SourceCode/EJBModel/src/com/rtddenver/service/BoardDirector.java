package com.rtddenver.service;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * To create ID generator sequence BOARDDIRECTOR_ID_SEQ_GEN:
 * CREATE SEQUENCE BOARDDIRECTOR_ID_SEQ_GEN INCREMENT BY 50 START WITH 50;
 */
@Entity
@NamedQueries({ @NamedQuery(name = "findDirectorByDistrict", query = "select o from BoardDirector o " +
    "WHERE UPPER(o.district) = UPPER(:district) AND UPPER(o.active) = 'Y' "),
                @NamedQuery(name = "getAllDirectors", query = "select o from BoardDirector o " +
    "WHERE UPPER(o.active) = 'Y'") })
@Table(name = "BOARD_DIRECTOR")
@SequenceGenerator(name = "BoardDirector_Id_Seq_Gen", sequenceName = "BOARDDIRECTOR_ID_SEQ_GEN", allocationSize = 50,
                   initialValue = 50)

public class BoardDirector implements Serializable {
    @SuppressWarnings("compatibility:3809877802474394801")
    private static final long serialVersionUID = -5565086281767852537L;
    @Column(name = "ACTIVE", nullable = false, length = 1)
    private String active;
    @Id
    @Column(name = "DIRECTOR_FIRST_NAME", nullable = false, length = 30)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BoardDirector_Id_Seq_Gen")
    private String directorFirstName;
    @Id
    @Column(name = "DIRECTOR_LAST_NAME", nullable = false, length = 30)
    private String directorLastName;
    @Id
    @Column(name = "DISTRICT", nullable = false, length = 10)
    private String district;

    public BoardDirector() {
    }

    public BoardDirector(String active, String directorFirstName, String directorLastName, String district) {
        this.active = active;
        this.directorFirstName = directorFirstName;
        this.directorLastName = directorLastName;
        this.district = district;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
    
    /**
     * Returns a concatenation of lastname, firstname
     * @return
     */
    public String getDirectorFullName() {
        return directorLastName + ", " + directorFirstName;    
    }
    
    public String getDirectorFirstName() {
        return directorFirstName;
    }

    public void setDirectorFirstName(String directorFirstName) {
        this.directorFirstName = directorFirstName;
    }

    public String getDirectorLastName() {
        return directorLastName;
    }

    public void setDirectorLastName(String directorLastName) {
        this.directorLastName = directorLastName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

}

package com.rtddenver.service;

import java.io.Serializable;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "findDirectorByDistrict", query = "select o from BoardDirector o " +
    "WHERE UPPER(o.district) = UPPER(:district) AND UPPER(o.active) = 'Y' "),
                @NamedQuery(name = "getAllDirectors", query = "select o from BoardDirector o " +
    "WHERE UPPER(o.active) = 'Y' order by DISTRICT") })
@Table(name = "BOARD_DIRECTOR", schema = "BOARD_DIR")

public class BoardDirector implements Serializable {
    private static final long serialVersionUID = -5565086281767852537L;
    @Column(name = "ACTIVE", nullable = false, length = 1)
    private String active;
    @Id
    @Column(name = "DIRECTOR_FIRST_NAME", nullable = false, length = 30)
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

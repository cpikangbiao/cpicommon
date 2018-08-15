package com.cpi.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A VesselNameHistory.
 */
@Entity
@Table(name = "vessel_name_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VesselNameHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "update_time")
    private Instant updateTime;

    @Column(name = "update_user")
    private Long updateUser;

    @Column(name = "vessel_name")
    private String vesselName;

    @Column(name = "vessel_chinese_name")
    private String vesselChineseName;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Vessel vessel;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public VesselNameHistory updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public VesselNameHistory updateUser(Long updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public String getVesselName() {
        return vesselName;
    }

    public VesselNameHistory vesselName(String vesselName) {
        this.vesselName = vesselName;
        return this;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public String getVesselChineseName() {
        return vesselChineseName;
    }

    public VesselNameHistory vesselChineseName(String vesselChineseName) {
        this.vesselChineseName = vesselChineseName;
        return this;
    }

    public void setVesselChineseName(String vesselChineseName) {
        this.vesselChineseName = vesselChineseName;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public VesselNameHistory startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public VesselNameHistory endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Vessel getVessel() {
        return vessel;
    }

    public VesselNameHistory vessel(Vessel vessel) {
        this.vessel = vessel;
        return this;
    }

    public void setVessel(Vessel vessel) {
        this.vessel = vessel;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VesselNameHistory vesselNameHistory = (VesselNameHistory) o;
        if (vesselNameHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vesselNameHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VesselNameHistory{" +
            "id=" + getId() +
            ", updateTime='" + getUpdateTime() + "'" +
            ", updateUser=" + getUpdateUser() +
            ", vesselName='" + getVesselName() + "'" +
            ", vesselChineseName='" + getVesselChineseName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}

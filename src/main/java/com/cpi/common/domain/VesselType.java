package com.cpi.common.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A VesselType.
 */
@Entity
@Table(name = "vessel_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VesselType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "vessel_type_name", nullable = false)
    private String vesselTypeName;

    @NotNull
    @Column(name = "sort_num", nullable = false)
    private Integer sortNum;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVesselTypeName() {
        return vesselTypeName;
    }

    public VesselType vesselTypeName(String vesselTypeName) {
        this.vesselTypeName = vesselTypeName;
        return this;
    }

    public void setVesselTypeName(String vesselTypeName) {
        this.vesselTypeName = vesselTypeName;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public VesselType sortNum(Integer sortNum) {
        this.sortNum = sortNum;
        return this;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VesselType)) {
            return false;
        }
        return id != null && id.equals(((VesselType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "VesselType{" +
            "id=" + getId() +
            ", vesselTypeName='" + getVesselTypeName() + "'" +
            ", sortNum=" + getSortNum() +
            "}";
    }
}

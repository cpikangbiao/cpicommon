package com.cpi.common.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cpi.common.domain.VesselType} entity.
 */
public class VesselTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String vesselTypeName;

    @NotNull
    private Integer sortNum;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVesselTypeName() {
        return vesselTypeName;
    }

    public void setVesselTypeName(String vesselTypeName) {
        this.vesselTypeName = vesselTypeName;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VesselTypeDTO vesselTypeDTO = (VesselTypeDTO) o;
        if (vesselTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vesselTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VesselTypeDTO{" +
            "id=" + getId() +
            ", vesselTypeName='" + getVesselTypeName() + "'" +
            ", sortNum=" + getSortNum() +
            "}";
    }
}

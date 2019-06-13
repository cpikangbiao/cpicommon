package com.cpi.common.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cpi.common.domain.VesselNameHistory} entity.
 */
public class VesselNameHistoryDTO implements Serializable {

    private Long id;

    private Instant updateTime;

    private Long updateUser;

    private String vesselName;

    private String vesselChineseName;

    private Instant startDate;

    private Instant endDate;


    private Long vesselId;

    private String vesselVesselName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public String getVesselChineseName() {
        return vesselChineseName;
    }

    public void setVesselChineseName(String vesselChineseName) {
        this.vesselChineseName = vesselChineseName;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Long getVesselId() {
        return vesselId;
    }

    public void setVesselId(Long vesselId) {
        this.vesselId = vesselId;
    }

    public String getVesselVesselName() {
        return vesselVesselName;
    }

    public void setVesselVesselName(String vesselVesselName) {
        this.vesselVesselName = vesselVesselName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VesselNameHistoryDTO vesselNameHistoryDTO = (VesselNameHistoryDTO) o;
        if (vesselNameHistoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vesselNameHistoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VesselNameHistoryDTO{" +
            "id=" + getId() +
            ", updateTime='" + getUpdateTime() + "'" +
            ", updateUser=" + getUpdateUser() +
            ", vesselName='" + getVesselName() + "'" +
            ", vesselChineseName='" + getVesselChineseName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", vessel=" + getVesselId() +
            ", vessel='" + getVesselVesselName() + "'" +
            "}";
    }
}

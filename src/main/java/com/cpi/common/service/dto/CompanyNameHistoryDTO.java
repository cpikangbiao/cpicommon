package com.cpi.common.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cpi.common.domain.CompanyNameHistory} entity.
 */
public class CompanyNameHistoryDTO implements Serializable {

    private Long id;

    private Instant updateTime;

    private Long updateUser;

    private String companyName;

    private String companyChineseName;

    private Instant startDate;

    private Instant endDate;


    private Long companyId;

    private String companyCompanyName;

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyChineseName() {
        return companyChineseName;
    }

    public void setCompanyChineseName(String companyChineseName) {
        this.companyChineseName = companyChineseName;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyCompanyName() {
        return companyCompanyName;
    }

    public void setCompanyCompanyName(String companyCompanyName) {
        this.companyCompanyName = companyCompanyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyNameHistoryDTO companyNameHistoryDTO = (CompanyNameHistoryDTO) o;
        if (companyNameHistoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyNameHistoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyNameHistoryDTO{" +
            "id=" + getId() +
            ", updateTime='" + getUpdateTime() + "'" +
            ", updateUser=" + getUpdateUser() +
            ", companyName='" + getCompanyName() + "'" +
            ", companyChineseName='" + getCompanyChineseName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", company=" + getCompanyId() +
            ", company='" + getCompanyCompanyName() + "'" +
            "}";
    }
}

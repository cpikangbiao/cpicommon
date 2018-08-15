package com.cpi.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A CompanyNameHistory.
 */
@Entity
@Table(name = "company_name_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompanyNameHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "update_time")
    private Instant updateTime;

    @Column(name = "update_user")
    private Long updateUser;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_chinese_name")
    private String companyChineseName;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Company company;

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

    public CompanyNameHistory updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public CompanyNameHistory updateUser(Long updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public String getCompanyName() {
        return companyName;
    }

    public CompanyNameHistory companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyChineseName() {
        return companyChineseName;
    }

    public CompanyNameHistory companyChineseName(String companyChineseName) {
        this.companyChineseName = companyChineseName;
        return this;
    }

    public void setCompanyChineseName(String companyChineseName) {
        this.companyChineseName = companyChineseName;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public CompanyNameHistory startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public CompanyNameHistory endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Company getCompany() {
        return company;
    }

    public CompanyNameHistory company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
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
        CompanyNameHistory companyNameHistory = (CompanyNameHistory) o;
        if (companyNameHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyNameHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyNameHistory{" +
            "id=" + getId() +
            ", updateTime='" + getUpdateTime() + "'" +
            ", updateUser=" + getUpdateUser() +
            ", companyName='" + getCompanyName() + "'" +
            ", companyChineseName='" + getCompanyChineseName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}

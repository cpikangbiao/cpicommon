package com.cpi.common.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "company_code", length = 10, nullable = false)
    private String companyCode;

    @NotNull
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "company_name_chinese")
    private String companyNameChinese;

    @Lob
    @Column(name = "remark")
    private String remark;

    @OneToOne    @JoinColumn(unique = true)
    private Country country;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public Company companyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Company companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNameChinese() {
        return companyNameChinese;
    }

    public Company companyNameChinese(String companyNameChinese) {
        this.companyNameChinese = companyNameChinese;
        return this;
    }

    public void setCompanyNameChinese(String companyNameChinese) {
        this.companyNameChinese = companyNameChinese;
    }

    public String getRemark() {
        return remark;
    }

    public Company remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Country getCountry() {
        return country;
    }

    public Company country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
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
        Company company = (Company) o;
        if (company.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), company.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", companyCode='" + getCompanyCode() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", companyNameChinese='" + getCompanyNameChinese() + "'" +
            ", remark='" + getRemark() + "'" +
            "}";
    }
}

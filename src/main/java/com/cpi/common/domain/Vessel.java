package com.cpi.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Vessel.
 */
@Entity
@Table(name = "vessel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vessel extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vessel_code")
    private String vesselCode;

    @Column(name = "vessel_name")
    private String vesselName;

    @Column(name = "vessel_chinese_name")
    private String vesselChineseName;

    @NotNull
    @Column(name = "gtr", nullable = false)
    private Double gtr;

    @Column(name = "wrt")
    private Double wrt;

    @Column(name = "build_location")
    private String buildLocation;

    @Size(min = 4, max = 4)
    @Column(name = "build_year", length = 4)
    private String buildYear;

    @Size(max = 20)
    @Column(name = "foc", length = 20)
    private String foc;

    @Column(name = "dwt")
    private Double dwt;

    @Column(name = "hull_hm_amount")
    private Double hullHmAmount;

    @Column(name = "hull_hm_value")
    private Double hullHmValue;

    @Column(name = "hull_iv_amount")
    private Double hullIvAmount;

    @Column(name = "hull_iv_value")
    private Double hullIvValue;

    @Column(name = "hull_war_amount")
    private Double hullWarAmount;

    @Column(name = "hull_war_value")
    private Double hullWarValue;

    @Column(name = "vessel_size")
    private String vesselSize;

    @Size(max = 20)
    @Column(name = "line", length = 20)
    private String line;

    @Column(name = "deeper")
    private Double deeper;

    @Size(max = 15)
    @Column(name = "call_sign", length = 15)
    private String callSign;

    @Size(max = 10)
    @Column(name = "imo_number", length = 10)
    private String imoNumber;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Country vesselCountry;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Currency vesselCurrency;

    @ManyToOne
    @JsonIgnoreProperties("")
    private VesselType vesselType;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Company vesselOwnerCompany;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Port registedPort;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVesselCode() {
        return vesselCode;
    }

    public Vessel vesselCode(String vesselCode) {
        this.vesselCode = vesselCode;
        return this;
    }

    public void setVesselCode(String vesselCode) {
        this.vesselCode = vesselCode;
    }

    public String getVesselName() {
        return vesselName;
    }

    public Vessel vesselName(String vesselName) {
        this.vesselName = vesselName;
        return this;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public String getVesselChineseName() {
        return vesselChineseName;
    }

    public Vessel vesselChineseName(String vesselChineseName) {
        this.vesselChineseName = vesselChineseName;
        return this;
    }

    public void setVesselChineseName(String vesselChineseName) {
        this.vesselChineseName = vesselChineseName;
    }

    public Double getGtr() {
        return gtr;
    }

    public Vessel gtr(Double gtr) {
        this.gtr = gtr;
        return this;
    }

    public void setGtr(Double gtr) {
        this.gtr = gtr;
    }

    public Double getWrt() {
        return wrt;
    }

    public Vessel wrt(Double wrt) {
        this.wrt = wrt;
        return this;
    }

    public void setWrt(Double wrt) {
        this.wrt = wrt;
    }

    public String getBuildLocation() {
        return buildLocation;
    }

    public Vessel buildLocation(String buildLocation) {
        this.buildLocation = buildLocation;
        return this;
    }

    public void setBuildLocation(String buildLocation) {
        this.buildLocation = buildLocation;
    }

    public String getBuildYear() {
        return buildYear;
    }

    public Vessel buildYear(String buildYear) {
        this.buildYear = buildYear;
        return this;
    }

    public void setBuildYear(String buildYear) {
        this.buildYear = buildYear;
    }

    public String getFoc() {
        return foc;
    }

    public Vessel foc(String foc) {
        this.foc = foc;
        return this;
    }

    public void setFoc(String foc) {
        this.foc = foc;
    }

    public Double getDwt() {
        return dwt;
    }

    public Vessel dwt(Double dwt) {
        this.dwt = dwt;
        return this;
    }

    public void setDwt(Double dwt) {
        this.dwt = dwt;
    }

    public Double getHullHmAmount() {
        return hullHmAmount;
    }

    public Vessel hullHmAmount(Double hullHmAmount) {
        this.hullHmAmount = hullHmAmount;
        return this;
    }

    public void setHullHmAmount(Double hullHmAmount) {
        this.hullHmAmount = hullHmAmount;
    }

    public Double getHullHmValue() {
        return hullHmValue;
    }

    public Vessel hullHmValue(Double hullHmValue) {
        this.hullHmValue = hullHmValue;
        return this;
    }

    public void setHullHmValue(Double hullHmValue) {
        this.hullHmValue = hullHmValue;
    }

    public Double getHullIvAmount() {
        return hullIvAmount;
    }

    public Vessel hullIvAmount(Double hullIvAmount) {
        this.hullIvAmount = hullIvAmount;
        return this;
    }

    public void setHullIvAmount(Double hullIvAmount) {
        this.hullIvAmount = hullIvAmount;
    }

    public Double getHullIvValue() {
        return hullIvValue;
    }

    public Vessel hullIvValue(Double hullIvValue) {
        this.hullIvValue = hullIvValue;
        return this;
    }

    public void setHullIvValue(Double hullIvValue) {
        this.hullIvValue = hullIvValue;
    }

    public Double getHullWarAmount() {
        return hullWarAmount;
    }

    public Vessel hullWarAmount(Double hullWarAmount) {
        this.hullWarAmount = hullWarAmount;
        return this;
    }

    public void setHullWarAmount(Double hullWarAmount) {
        this.hullWarAmount = hullWarAmount;
    }

    public Double getHullWarValue() {
        return hullWarValue;
    }

    public Vessel hullWarValue(Double hullWarValue) {
        this.hullWarValue = hullWarValue;
        return this;
    }

    public void setHullWarValue(Double hullWarValue) {
        this.hullWarValue = hullWarValue;
    }

    public String getVesselSize() {
        return vesselSize;
    }

    public Vessel vesselSize(String vesselSize) {
        this.vesselSize = vesselSize;
        return this;
    }

    public void setVesselSize(String vesselSize) {
        this.vesselSize = vesselSize;
    }

    public String getLine() {
        return line;
    }

    public Vessel line(String line) {
        this.line = line;
        return this;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Double getDeeper() {
        return deeper;
    }

    public Vessel deeper(Double deeper) {
        this.deeper = deeper;
        return this;
    }

    public void setDeeper(Double deeper) {
        this.deeper = deeper;
    }

    public String getCallSign() {
        return callSign;
    }

    public Vessel callSign(String callSign) {
        this.callSign = callSign;
        return this;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public String getImoNumber() {
        return imoNumber;
    }

    public Vessel imoNumber(String imoNumber) {
        this.imoNumber = imoNumber;
        return this;
    }

    public void setImoNumber(String imoNumber) {
        this.imoNumber = imoNumber;
    }

    public Country getVesselCountry() {
        return vesselCountry;
    }

    public Vessel vesselCountry(Country country) {
        this.vesselCountry = country;
        return this;
    }

    public void setVesselCountry(Country country) {
        this.vesselCountry = country;
    }

    public Currency getVesselCurrency() {
        return vesselCurrency;
    }

    public Vessel vesselCurrency(Currency currency) {
        this.vesselCurrency = currency;
        return this;
    }

    public void setVesselCurrency(Currency currency) {
        this.vesselCurrency = currency;
    }

    public VesselType getVesselType() {
        return vesselType;
    }

    public Vessel vesselType(VesselType vesselType) {
        this.vesselType = vesselType;
        return this;
    }

    public void setVesselType(VesselType vesselType) {
        this.vesselType = vesselType;
    }

    public Company getVesselOwnerCompany() {
        return vesselOwnerCompany;
    }

    public Vessel vesselOwnerCompany(Company company) {
        this.vesselOwnerCompany = company;
        return this;
    }

    public void setVesselOwnerCompany(Company company) {
        this.vesselOwnerCompany = company;
    }

    public Port getRegistedPort() {
        return registedPort;
    }

    public Vessel registedPort(Port port) {
        this.registedPort = port;
        return this;
    }

    public void setRegistedPort(Port port) {
        this.registedPort = port;
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
        Vessel vessel = (Vessel) o;
        if (vessel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vessel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vessel{" +
            "id=" + getId() +
            ", vesselCode='" + getVesselCode() + "'" +
            ", vesselName='" + getVesselName() + "'" +
            ", vesselChineseName='" + getVesselChineseName() + "'" +
            ", gtr=" + getGtr() +
            ", wrt=" + getWrt() +
            ", buildLocation='" + getBuildLocation() + "'" +
            ", buildYear='" + getBuildYear() + "'" +
            ", foc='" + getFoc() + "'" +
            ", dwt=" + getDwt() +
            ", hullHmAmount=" + getHullHmAmount() +
            ", hullHmValue=" + getHullHmValue() +
            ", hullIvAmount=" + getHullIvAmount() +
            ", hullIvValue=" + getHullIvValue() +
            ", hullWarAmount=" + getHullWarAmount() +
            ", hullWarValue=" + getHullWarValue() +
            ", vesselSize='" + getVesselSize() + "'" +
            ", line='" + getLine() + "'" +
            ", deeper=" + getDeeper() +
            ", callSign='" + getCallSign() + "'" +
            ", imoNumber='" + getImoNumber() + "'" +
            "}";
    }
}

package com.cpi.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Country.
 */
@Entity
@Table(name = "country")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "country_name", nullable = false)
    private String countryName;

    @NotNull
    @Column(name = "country_name_abbr", nullable = false)
    private String countryNameAbbr;

    @Column(name = "country_name_chinese")
    private String countryNameChinese;

    @Column(name = "dial_code")
    private String dialCode;

    @OneToMany(mappedBy = "country")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Port> ports = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public Country countryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryNameAbbr() {
        return countryNameAbbr;
    }

    public Country countryNameAbbr(String countryNameAbbr) {
        this.countryNameAbbr = countryNameAbbr;
        return this;
    }

    public void setCountryNameAbbr(String countryNameAbbr) {
        this.countryNameAbbr = countryNameAbbr;
    }

    public String getCountryNameChinese() {
        return countryNameChinese;
    }

    public Country countryNameChinese(String countryNameChinese) {
        this.countryNameChinese = countryNameChinese;
        return this;
    }

    public void setCountryNameChinese(String countryNameChinese) {
        this.countryNameChinese = countryNameChinese;
    }

    public String getDialCode() {
        return dialCode;
    }

    public Country dialCode(String dialCode) {
        this.dialCode = dialCode;
        return this;
    }

    public void setDialCode(String dialCode) {
        this.dialCode = dialCode;
    }

    public Set<Port> getPorts() {
        return ports;
    }

    public Country ports(Set<Port> ports) {
        this.ports = ports;
        return this;
    }

    public Country addPorts(Port port) {
        this.ports.add(port);
        port.setCountry(this);
        return this;
    }

    public Country removePorts(Port port) {
        this.ports.remove(port);
        port.setCountry(null);
        return this;
    }

    public void setPorts(Set<Port> ports) {
        this.ports = ports;
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
        Country country = (Country) o;
        if (country.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), country.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", countryName='" + getCountryName() + "'" +
            ", countryNameAbbr='" + getCountryNameAbbr() + "'" +
            ", countryNameChinese='" + getCountryNameChinese() + "'" +
            ", dialCode='" + getDialCode() + "'" +
            "}";
    }
}

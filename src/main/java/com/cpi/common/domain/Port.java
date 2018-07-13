package com.cpi.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Port.
 */
@Entity
@Table(name = "port")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Port implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "port_code")
    private String portCode;

    @Column(name = "port_name")
    private String portName;

    @Column(name = "port_name_chinese")
    private String portNameChinese;

    @OneToMany(mappedBy = "port")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Correspondent> correspondents = new HashSet<>();

    @ManyToOne
    private Country country;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPortCode() {
        return portCode;
    }

    public Port portCode(String portCode) {
        this.portCode = portCode;
        return this;
    }

    public void setPortCode(String portCode) {
        this.portCode = portCode;
    }

    public String getPortName() {
        return portName;
    }

    public Port portName(String portName) {
        this.portName = portName;
        return this;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getPortNameChinese() {
        return portNameChinese;
    }

    public Port portNameChinese(String portNameChinese) {
        this.portNameChinese = portNameChinese;
        return this;
    }

    public void setPortNameChinese(String portNameChinese) {
        this.portNameChinese = portNameChinese;
    }

    public Set<Correspondent> getCorrespondents() {
        return correspondents;
    }

    public Port correspondents(Set<Correspondent> correspondents) {
        this.correspondents = correspondents;
        return this;
    }

    public Port addCorrespondents(Correspondent correspondent) {
        this.correspondents.add(correspondent);
        correspondent.setPort(this);
        return this;
    }

    public Port removeCorrespondents(Correspondent correspondent) {
        this.correspondents.remove(correspondent);
        correspondent.setPort(null);
        return this;
    }

    public void setCorrespondents(Set<Correspondent> correspondents) {
        this.correspondents = correspondents;
    }

    public Country getCountry() {
        return country;
    }

    public Port country(Country country) {
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
        Port port = (Port) o;
        if (port.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), port.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Port{" +
            "id=" + getId() +
            ", portCode='" + getPortCode() + "'" +
            ", portName='" + getPortName() + "'" +
            ", portNameChinese='" + getPortNameChinese() + "'" +
            "}";
    }
}

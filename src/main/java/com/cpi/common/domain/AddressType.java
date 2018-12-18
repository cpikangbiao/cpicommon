package com.cpi.common.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AddressType.
 */
@Entity
@Table(name = "address_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AddressType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sort_num")
    private Integer sortNum;

    @NotNull
    @Column(name = "address_type_name", nullable = false)
    private String addressTypeName;

    @Column(name = "address_type_name_chinese")
    private String addressTypeNameChinese;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public AddressType sortNum(Integer sortNum) {
        this.sortNum = sortNum;
        return this;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getAddressTypeName() {
        return addressTypeName;
    }

    public AddressType addressTypeName(String addressTypeName) {
        this.addressTypeName = addressTypeName;
        return this;
    }

    public void setAddressTypeName(String addressTypeName) {
        this.addressTypeName = addressTypeName;
    }

    public String getAddressTypeNameChinese() {
        return addressTypeNameChinese;
    }

    public AddressType addressTypeNameChinese(String addressTypeNameChinese) {
        this.addressTypeNameChinese = addressTypeNameChinese;
        return this;
    }

    public void setAddressTypeNameChinese(String addressTypeNameChinese) {
        this.addressTypeNameChinese = addressTypeNameChinese;
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
        AddressType addressType = (AddressType) o;
        if (addressType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addressType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddressType{" +
            "id=" + getId() +
            ", sortNum=" + getSortNum() +
            ", addressTypeName='" + getAddressTypeName() + "'" +
            ", addressTypeNameChinese='" + getAddressTypeNameChinese() + "'" +
            "}";
    }
}

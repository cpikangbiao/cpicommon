package com.cpi.common.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the AddressType entity.
 */
public class AddressTypeDTO implements Serializable {

    private Long id;

    private Integer sortNum;

    @NotNull
    private String addressTypeName;

    private String addressTypeNameChinese;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getAddressTypeName() {
        return addressTypeName;
    }

    public void setAddressTypeName(String addressTypeName) {
        this.addressTypeName = addressTypeName;
    }

    public String getAddressTypeNameChinese() {
        return addressTypeNameChinese;
    }

    public void setAddressTypeNameChinese(String addressTypeNameChinese) {
        this.addressTypeNameChinese = addressTypeNameChinese;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddressTypeDTO addressTypeDTO = (AddressTypeDTO) o;
        if (addressTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addressTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddressTypeDTO{" +
            "id=" + getId() +
            ", sortNum=" + getSortNum() +
            ", addressTypeName='" + getAddressTypeName() + "'" +
            ", addressTypeNameChinese='" + getAddressTypeNameChinese() + "'" +
            "}";
    }
}

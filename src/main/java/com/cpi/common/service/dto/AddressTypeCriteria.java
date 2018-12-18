package com.cpi.common.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the AddressType entity. This class is used in AddressTypeResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /address-types?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AddressTypeCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter sortNum;

    private StringFilter addressTypeName;

    private StringFilter addressTypeNameChinese;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getSortNum() {
        return sortNum;
    }

    public void setSortNum(IntegerFilter sortNum) {
        this.sortNum = sortNum;
    }

    public StringFilter getAddressTypeName() {
        return addressTypeName;
    }

    public void setAddressTypeName(StringFilter addressTypeName) {
        this.addressTypeName = addressTypeName;
    }

    public StringFilter getAddressTypeNameChinese() {
        return addressTypeNameChinese;
    }

    public void setAddressTypeNameChinese(StringFilter addressTypeNameChinese) {
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
        final AddressTypeCriteria that = (AddressTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(sortNum, that.sortNum) &&
            Objects.equals(addressTypeName, that.addressTypeName) &&
            Objects.equals(addressTypeNameChinese, that.addressTypeNameChinese);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        sortNum,
        addressTypeName,
        addressTypeNameChinese
        );
    }

    @Override
    public String toString() {
        return "AddressTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (sortNum != null ? "sortNum=" + sortNum + ", " : "") +
                (addressTypeName != null ? "addressTypeName=" + addressTypeName + ", " : "") +
                (addressTypeNameChinese != null ? "addressTypeNameChinese=" + addressTypeNameChinese + ", " : "") +
            "}";
    }

}

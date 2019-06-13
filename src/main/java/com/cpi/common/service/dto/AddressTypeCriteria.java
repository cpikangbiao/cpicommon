package com.cpi.common.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.cpi.common.domain.AddressType} entity. This class is used
 * in {@link com.cpi.common.web.rest.AddressTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /address-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AddressTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter sortNum;

    private StringFilter addressTypeName;

    private StringFilter addressTypeNameChinese;

    public AddressTypeCriteria(){
    }

    public AddressTypeCriteria(AddressTypeCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.sortNum = other.sortNum == null ? null : other.sortNum.copy();
        this.addressTypeName = other.addressTypeName == null ? null : other.addressTypeName.copy();
        this.addressTypeNameChinese = other.addressTypeNameChinese == null ? null : other.addressTypeNameChinese.copy();
    }

    @Override
    public AddressTypeCriteria copy() {
        return new AddressTypeCriteria(this);
    }

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

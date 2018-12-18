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
 * Criteria class for the Address entity. This class is used in AddressResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /addresses?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AddressCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter building;

    private StringFilter street;

    private StringFilter postbox;

    private StringFilter zip;

    private StringFilter city;

    private LongFilter companyId;

    private LongFilter addressTypeId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getBuilding() {
        return building;
    }

    public void setBuilding(StringFilter building) {
        this.building = building;
    }

    public StringFilter getStreet() {
        return street;
    }

    public void setStreet(StringFilter street) {
        this.street = street;
    }

    public StringFilter getPostbox() {
        return postbox;
    }

    public void setPostbox(StringFilter postbox) {
        this.postbox = postbox;
    }

    public StringFilter getZip() {
        return zip;
    }

    public void setZip(StringFilter zip) {
        this.zip = zip;
    }

    public StringFilter getCity() {
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
    }

    public LongFilter getAddressTypeId() {
        return addressTypeId;
    }

    public void setAddressTypeId(LongFilter addressTypeId) {
        this.addressTypeId = addressTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AddressCriteria that = (AddressCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(building, that.building) &&
            Objects.equals(street, that.street) &&
            Objects.equals(postbox, that.postbox) &&
            Objects.equals(zip, that.zip) &&
            Objects.equals(city, that.city) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(addressTypeId, that.addressTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        building,
        street,
        postbox,
        zip,
        city,
        companyId,
        addressTypeId
        );
    }

    @Override
    public String toString() {
        return "AddressCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (building != null ? "building=" + building + ", " : "") +
                (street != null ? "street=" + street + ", " : "") +
                (postbox != null ? "postbox=" + postbox + ", " : "") +
                (zip != null ? "zip=" + zip + ", " : "") +
                (city != null ? "city=" + city + ", " : "") +
                (companyId != null ? "companyId=" + companyId + ", " : "") +
                (addressTypeId != null ? "addressTypeId=" + addressTypeId + ", " : "") +
            "}";
    }

}

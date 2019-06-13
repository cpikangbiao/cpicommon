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
 * Criteria class for the {@link com.cpi.common.domain.VesselType} entity. This class is used
 * in {@link com.cpi.common.web.rest.VesselTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vessel-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VesselTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter vesselTypeName;

    private IntegerFilter sortNum;

    public VesselTypeCriteria(){
    }

    public VesselTypeCriteria(VesselTypeCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.vesselTypeName = other.vesselTypeName == null ? null : other.vesselTypeName.copy();
        this.sortNum = other.sortNum == null ? null : other.sortNum.copy();
    }

    @Override
    public VesselTypeCriteria copy() {
        return new VesselTypeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getVesselTypeName() {
        return vesselTypeName;
    }

    public void setVesselTypeName(StringFilter vesselTypeName) {
        this.vesselTypeName = vesselTypeName;
    }

    public IntegerFilter getSortNum() {
        return sortNum;
    }

    public void setSortNum(IntegerFilter sortNum) {
        this.sortNum = sortNum;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VesselTypeCriteria that = (VesselTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(vesselTypeName, that.vesselTypeName) &&
            Objects.equals(sortNum, that.sortNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        vesselTypeName,
        sortNum
        );
    }

    @Override
    public String toString() {
        return "VesselTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (vesselTypeName != null ? "vesselTypeName=" + vesselTypeName + ", " : "") +
                (sortNum != null ? "sortNum=" + sortNum + ", " : "") +
            "}";
    }

}

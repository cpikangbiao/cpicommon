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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.cpi.common.domain.VesselNameHistory} entity. This class is used
 * in {@link com.cpi.common.web.rest.VesselNameHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vessel-name-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VesselNameHistoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter updateTime;

    private LongFilter updateUser;

    private StringFilter vesselName;

    private StringFilter vesselChineseName;

    private InstantFilter startDate;

    private InstantFilter endDate;

    private LongFilter vesselId;

    public VesselNameHistoryCriteria(){
    }

    public VesselNameHistoryCriteria(VesselNameHistoryCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.updateTime = other.updateTime == null ? null : other.updateTime.copy();
        this.updateUser = other.updateUser == null ? null : other.updateUser.copy();
        this.vesselName = other.vesselName == null ? null : other.vesselName.copy();
        this.vesselChineseName = other.vesselChineseName == null ? null : other.vesselChineseName.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.vesselId = other.vesselId == null ? null : other.vesselId.copy();
    }

    @Override
    public VesselNameHistoryCriteria copy() {
        return new VesselNameHistoryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(InstantFilter updateTime) {
        this.updateTime = updateTime;
    }

    public LongFilter getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(LongFilter updateUser) {
        this.updateUser = updateUser;
    }

    public StringFilter getVesselName() {
        return vesselName;
    }

    public void setVesselName(StringFilter vesselName) {
        this.vesselName = vesselName;
    }

    public StringFilter getVesselChineseName() {
        return vesselChineseName;
    }

    public void setVesselChineseName(StringFilter vesselChineseName) {
        this.vesselChineseName = vesselChineseName;
    }

    public InstantFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(InstantFilter startDate) {
        this.startDate = startDate;
    }

    public InstantFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(InstantFilter endDate) {
        this.endDate = endDate;
    }

    public LongFilter getVesselId() {
        return vesselId;
    }

    public void setVesselId(LongFilter vesselId) {
        this.vesselId = vesselId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VesselNameHistoryCriteria that = (VesselNameHistoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(updateTime, that.updateTime) &&
            Objects.equals(updateUser, that.updateUser) &&
            Objects.equals(vesselName, that.vesselName) &&
            Objects.equals(vesselChineseName, that.vesselChineseName) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(vesselId, that.vesselId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        updateTime,
        updateUser,
        vesselName,
        vesselChineseName,
        startDate,
        endDate,
        vesselId
        );
    }

    @Override
    public String toString() {
        return "VesselNameHistoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (updateTime != null ? "updateTime=" + updateTime + ", " : "") +
                (updateUser != null ? "updateUser=" + updateUser + ", " : "") +
                (vesselName != null ? "vesselName=" + vesselName + ", " : "") +
                (vesselChineseName != null ? "vesselChineseName=" + vesselChineseName + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (vesselId != null ? "vesselId=" + vesselId + ", " : "") +
            "}";
    }

}

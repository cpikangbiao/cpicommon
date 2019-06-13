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
 * Criteria class for the {@link com.cpi.common.domain.CompanyNameHistory} entity. This class is used
 * in {@link com.cpi.common.web.rest.CompanyNameHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /company-name-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompanyNameHistoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter updateTime;

    private LongFilter updateUser;

    private StringFilter companyName;

    private StringFilter companyChineseName;

    private InstantFilter startDate;

    private InstantFilter endDate;

    private LongFilter companyId;

    public CompanyNameHistoryCriteria(){
    }

    public CompanyNameHistoryCriteria(CompanyNameHistoryCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.updateTime = other.updateTime == null ? null : other.updateTime.copy();
        this.updateUser = other.updateUser == null ? null : other.updateUser.copy();
        this.companyName = other.companyName == null ? null : other.companyName.copy();
        this.companyChineseName = other.companyChineseName == null ? null : other.companyChineseName.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
    }

    @Override
    public CompanyNameHistoryCriteria copy() {
        return new CompanyNameHistoryCriteria(this);
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

    public StringFilter getCompanyName() {
        return companyName;
    }

    public void setCompanyName(StringFilter companyName) {
        this.companyName = companyName;
    }

    public StringFilter getCompanyChineseName() {
        return companyChineseName;
    }

    public void setCompanyChineseName(StringFilter companyChineseName) {
        this.companyChineseName = companyChineseName;
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

    public LongFilter getCompanyId() {
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CompanyNameHistoryCriteria that = (CompanyNameHistoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(updateTime, that.updateTime) &&
            Objects.equals(updateUser, that.updateUser) &&
            Objects.equals(companyName, that.companyName) &&
            Objects.equals(companyChineseName, that.companyChineseName) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(companyId, that.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        updateTime,
        updateUser,
        companyName,
        companyChineseName,
        startDate,
        endDate,
        companyId
        );
    }

    @Override
    public String toString() {
        return "CompanyNameHistoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (updateTime != null ? "updateTime=" + updateTime + ", " : "") +
                (updateUser != null ? "updateUser=" + updateUser + ", " : "") +
                (companyName != null ? "companyName=" + companyName + ", " : "") +
                (companyChineseName != null ? "companyChineseName=" + companyChineseName + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (companyId != null ? "companyId=" + companyId + ", " : "") +
            "}";
    }

}

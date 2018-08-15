package com.cpi.common.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import io.github.jhipster.service.filter.InstantFilter;




/**
 * Criteria class for the CompanyNameHistory entity. This class is used in CompanyNameHistoryResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /company-name-histories?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompanyNameHistoryCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private InstantFilter updateTime;

    private LongFilter updateUser;

    private StringFilter companyName;

    private StringFilter companyChineseName;

    private InstantFilter startDate;

    private InstantFilter endDate;

    private LongFilter companyId;

    public CompanyNameHistoryCriteria() {
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

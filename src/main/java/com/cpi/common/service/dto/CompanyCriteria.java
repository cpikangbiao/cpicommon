package com.cpi.common.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Company entity. This class is used in CompanyResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /companies?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompanyCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter companyCode;

    private StringFilter companyName;

    private StringFilter companyNameChinese;

    private LongFilter countryId;

    public CompanyCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(StringFilter companyCode) {
        this.companyCode = companyCode;
    }

    public StringFilter getCompanyName() {
        return companyName;
    }

    public void setCompanyName(StringFilter companyName) {
        this.companyName = companyName;
    }

    public StringFilter getCompanyNameChinese() {
        return companyNameChinese;
    }

    public void setCompanyNameChinese(StringFilter companyNameChinese) {
        this.companyNameChinese = companyNameChinese;
    }

    public LongFilter getCountryId() {
        return countryId;
    }

    public void setCountryId(LongFilter countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString() {
        return "CompanyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (companyCode != null ? "companyCode=" + companyCode + ", " : "") +
                (companyName != null ? "companyName=" + companyName + ", " : "") +
                (companyNameChinese != null ? "companyNameChinese=" + companyNameChinese + ", " : "") +
                (countryId != null ? "countryId=" + countryId + ", " : "") +
            "}";
    }

}

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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.cpi.common.domain.CurrencyRate} entity. This class is used
 * in {@link com.cpi.common.web.rest.CurrencyRateResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /currency-rates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CurrencyRateCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter rateDate;

    private DoubleFilter currencyRate;

    private LongFilter currencyId;

    public CurrencyRateCriteria(){
    }

    public CurrencyRateCriteria(CurrencyRateCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.rateDate = other.rateDate == null ? null : other.rateDate.copy();
        this.currencyRate = other.currencyRate == null ? null : other.currencyRate.copy();
        this.currencyId = other.currencyId == null ? null : other.currencyId.copy();
    }

    @Override
    public CurrencyRateCriteria copy() {
        return new CurrencyRateCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getRateDate() {
        return rateDate;
    }

    public void setRateDate(LocalDateFilter rateDate) {
        this.rateDate = rateDate;
    }

    public DoubleFilter getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(DoubleFilter currencyRate) {
        this.currencyRate = currencyRate;
    }

    public LongFilter getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(LongFilter currencyId) {
        this.currencyId = currencyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CurrencyRateCriteria that = (CurrencyRateCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(rateDate, that.rateDate) &&
            Objects.equals(currencyRate, that.currencyRate) &&
            Objects.equals(currencyId, that.currencyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        rateDate,
        currencyRate,
        currencyId
        );
    }

    @Override
    public String toString() {
        return "CurrencyRateCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (rateDate != null ? "rateDate=" + rateDate + ", " : "") +
                (currencyRate != null ? "currencyRate=" + currencyRate + ", " : "") +
                (currencyId != null ? "currencyId=" + currencyId + ", " : "") +
            "}";
    }

}

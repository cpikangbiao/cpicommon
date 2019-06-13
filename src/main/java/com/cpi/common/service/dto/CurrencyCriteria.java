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
 * Criteria class for the {@link com.cpi.common.domain.Currency} entity. This class is used
 * in {@link com.cpi.common.web.rest.CurrencyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /currencies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CurrencyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fullNameEnglish;

    private StringFilter fullNameChinese;

    private StringFilter countryNameEnglish;

    private StringFilter countryNameChinese;

    private StringFilter nameAbbre;

    private IntegerFilter currencySort;

    public CurrencyCriteria(){
    }

    public CurrencyCriteria(CurrencyCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.fullNameEnglish = other.fullNameEnglish == null ? null : other.fullNameEnglish.copy();
        this.fullNameChinese = other.fullNameChinese == null ? null : other.fullNameChinese.copy();
        this.countryNameEnglish = other.countryNameEnglish == null ? null : other.countryNameEnglish.copy();
        this.countryNameChinese = other.countryNameChinese == null ? null : other.countryNameChinese.copy();
        this.nameAbbre = other.nameAbbre == null ? null : other.nameAbbre.copy();
        this.currencySort = other.currencySort == null ? null : other.currencySort.copy();
    }

    @Override
    public CurrencyCriteria copy() {
        return new CurrencyCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFullNameEnglish() {
        return fullNameEnglish;
    }

    public void setFullNameEnglish(StringFilter fullNameEnglish) {
        this.fullNameEnglish = fullNameEnglish;
    }

    public StringFilter getFullNameChinese() {
        return fullNameChinese;
    }

    public void setFullNameChinese(StringFilter fullNameChinese) {
        this.fullNameChinese = fullNameChinese;
    }

    public StringFilter getCountryNameEnglish() {
        return countryNameEnglish;
    }

    public void setCountryNameEnglish(StringFilter countryNameEnglish) {
        this.countryNameEnglish = countryNameEnglish;
    }

    public StringFilter getCountryNameChinese() {
        return countryNameChinese;
    }

    public void setCountryNameChinese(StringFilter countryNameChinese) {
        this.countryNameChinese = countryNameChinese;
    }

    public StringFilter getNameAbbre() {
        return nameAbbre;
    }

    public void setNameAbbre(StringFilter nameAbbre) {
        this.nameAbbre = nameAbbre;
    }

    public IntegerFilter getCurrencySort() {
        return currencySort;
    }

    public void setCurrencySort(IntegerFilter currencySort) {
        this.currencySort = currencySort;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CurrencyCriteria that = (CurrencyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fullNameEnglish, that.fullNameEnglish) &&
            Objects.equals(fullNameChinese, that.fullNameChinese) &&
            Objects.equals(countryNameEnglish, that.countryNameEnglish) &&
            Objects.equals(countryNameChinese, that.countryNameChinese) &&
            Objects.equals(nameAbbre, that.nameAbbre) &&
            Objects.equals(currencySort, that.currencySort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fullNameEnglish,
        fullNameChinese,
        countryNameEnglish,
        countryNameChinese,
        nameAbbre,
        currencySort
        );
    }

    @Override
    public String toString() {
        return "CurrencyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fullNameEnglish != null ? "fullNameEnglish=" + fullNameEnglish + ", " : "") +
                (fullNameChinese != null ? "fullNameChinese=" + fullNameChinese + ", " : "") +
                (countryNameEnglish != null ? "countryNameEnglish=" + countryNameEnglish + ", " : "") +
                (countryNameChinese != null ? "countryNameChinese=" + countryNameChinese + ", " : "") +
                (nameAbbre != null ? "nameAbbre=" + nameAbbre + ", " : "") +
                (currencySort != null ? "currencySort=" + currencySort + ", " : "") +
            "}";
    }

}

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
 * Criteria class for the ClassificationSociety entity. This class is used in ClassificationSocietyResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /classification-societies?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClassificationSocietyCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter classificationSocietyName;

    private StringFilter classificationSocietyNameAbbr;

    private IntegerFilter sortNum;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getClassificationSocietyName() {
        return classificationSocietyName;
    }

    public void setClassificationSocietyName(StringFilter classificationSocietyName) {
        this.classificationSocietyName = classificationSocietyName;
    }

    public StringFilter getClassificationSocietyNameAbbr() {
        return classificationSocietyNameAbbr;
    }

    public void setClassificationSocietyNameAbbr(StringFilter classificationSocietyNameAbbr) {
        this.classificationSocietyNameAbbr = classificationSocietyNameAbbr;
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
        final ClassificationSocietyCriteria that = (ClassificationSocietyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(classificationSocietyName, that.classificationSocietyName) &&
            Objects.equals(classificationSocietyNameAbbr, that.classificationSocietyNameAbbr) &&
            Objects.equals(sortNum, that.sortNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        classificationSocietyName,
        classificationSocietyNameAbbr,
        sortNum
        );
    }

    @Override
    public String toString() {
        return "ClassificationSocietyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (classificationSocietyName != null ? "classificationSocietyName=" + classificationSocietyName + ", " : "") +
                (classificationSocietyNameAbbr != null ? "classificationSocietyNameAbbr=" + classificationSocietyNameAbbr + ", " : "") +
                (sortNum != null ? "sortNum=" + sortNum + ", " : "") +
            "}";
    }

}

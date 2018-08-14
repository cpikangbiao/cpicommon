package com.cpi.common.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ClassificationSociety entity.
 */
public class ClassificationSocietyDTO implements Serializable {

    private Long id;

    @NotNull
    private String classificationSocietyName;

    @NotNull
    private String classificationSocietyNameAbbr;

    @NotNull
    private Integer sortNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassificationSocietyName() {
        return classificationSocietyName;
    }

    public void setClassificationSocietyName(String classificationSocietyName) {
        this.classificationSocietyName = classificationSocietyName;
    }

    public String getClassificationSocietyNameAbbr() {
        return classificationSocietyNameAbbr;
    }

    public void setClassificationSocietyNameAbbr(String classificationSocietyNameAbbr) {
        this.classificationSocietyNameAbbr = classificationSocietyNameAbbr;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
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

        ClassificationSocietyDTO classificationSocietyDTO = (ClassificationSocietyDTO) o;
        if (classificationSocietyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classificationSocietyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassificationSocietyDTO{" +
            "id=" + getId() +
            ", classificationSocietyName='" + getClassificationSocietyName() + "'" +
            ", classificationSocietyNameAbbr='" + getClassificationSocietyNameAbbr() + "'" +
            ", sortNum=" + getSortNum() +
            "}";
    }
}

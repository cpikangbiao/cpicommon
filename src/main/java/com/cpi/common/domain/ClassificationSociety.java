package com.cpi.common.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ClassificationSociety.
 */
@Entity
@Table(name = "classification_society")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClassificationSociety implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "classification_society_name", nullable = false)
    private String classificationSocietyName;

    @NotNull
    @Column(name = "classification_society_name_abbr", nullable = false)
    private String classificationSocietyNameAbbr;

    @NotNull
    @Column(name = "sort_num", nullable = false)
    private Integer sortNum;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassificationSocietyName() {
        return classificationSocietyName;
    }

    public ClassificationSociety classificationSocietyName(String classificationSocietyName) {
        this.classificationSocietyName = classificationSocietyName;
        return this;
    }

    public void setClassificationSocietyName(String classificationSocietyName) {
        this.classificationSocietyName = classificationSocietyName;
    }

    public String getClassificationSocietyNameAbbr() {
        return classificationSocietyNameAbbr;
    }

    public ClassificationSociety classificationSocietyNameAbbr(String classificationSocietyNameAbbr) {
        this.classificationSocietyNameAbbr = classificationSocietyNameAbbr;
        return this;
    }

    public void setClassificationSocietyNameAbbr(String classificationSocietyNameAbbr) {
        this.classificationSocietyNameAbbr = classificationSocietyNameAbbr;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public ClassificationSociety sortNum(Integer sortNum) {
        this.sortNum = sortNum;
        return this;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClassificationSociety classificationSociety = (ClassificationSociety) o;
        if (classificationSociety.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classificationSociety.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassificationSociety{" +
            "id=" + getId() +
            ", classificationSocietyName='" + getClassificationSocietyName() + "'" +
            ", classificationSocietyNameAbbr='" + getClassificationSocietyNameAbbr() + "'" +
            ", sortNum=" + getSortNum() +
            "}";
    }
}

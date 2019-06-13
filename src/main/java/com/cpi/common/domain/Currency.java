package com.cpi.common.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Currency.
 */
@Entity
@Table(name = "currency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "full_name_english", length = 100, nullable = false)
    private String fullNameEnglish;

    @Size(max = 255)
    @Column(name = "full_name_chinese", length = 255)
    private String fullNameChinese;

    @NotNull
    @Size(max = 255)
    @Column(name = "country_name_english", length = 255, nullable = false)
    private String countryNameEnglish;

    @Size(max = 255)
    @Column(name = "country_name_chinese", length = 255)
    private String countryNameChinese;

    @NotNull
    @Size(max = 100)
    @Column(name = "name_abbre", length = 100, nullable = false)
    private String nameAbbre;

    @Column(name = "currency_sort")
    private Integer currencySort;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullNameEnglish() {
        return fullNameEnglish;
    }

    public Currency fullNameEnglish(String fullNameEnglish) {
        this.fullNameEnglish = fullNameEnglish;
        return this;
    }

    public void setFullNameEnglish(String fullNameEnglish) {
        this.fullNameEnglish = fullNameEnglish;
    }

    public String getFullNameChinese() {
        return fullNameChinese;
    }

    public Currency fullNameChinese(String fullNameChinese) {
        this.fullNameChinese = fullNameChinese;
        return this;
    }

    public void setFullNameChinese(String fullNameChinese) {
        this.fullNameChinese = fullNameChinese;
    }

    public String getCountryNameEnglish() {
        return countryNameEnglish;
    }

    public Currency countryNameEnglish(String countryNameEnglish) {
        this.countryNameEnglish = countryNameEnglish;
        return this;
    }

    public void setCountryNameEnglish(String countryNameEnglish) {
        this.countryNameEnglish = countryNameEnglish;
    }

    public String getCountryNameChinese() {
        return countryNameChinese;
    }

    public Currency countryNameChinese(String countryNameChinese) {
        this.countryNameChinese = countryNameChinese;
        return this;
    }

    public void setCountryNameChinese(String countryNameChinese) {
        this.countryNameChinese = countryNameChinese;
    }

    public String getNameAbbre() {
        return nameAbbre;
    }

    public Currency nameAbbre(String nameAbbre) {
        this.nameAbbre = nameAbbre;
        return this;
    }

    public void setNameAbbre(String nameAbbre) {
        this.nameAbbre = nameAbbre;
    }

    public Integer getCurrencySort() {
        return currencySort;
    }

    public Currency currencySort(Integer currencySort) {
        this.currencySort = currencySort;
        return this;
    }

    public void setCurrencySort(Integer currencySort) {
        this.currencySort = currencySort;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Currency)) {
            return false;
        }
        return id != null && id.equals(((Currency) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Currency{" +
            "id=" + getId() +
            ", fullNameEnglish='" + getFullNameEnglish() + "'" +
            ", fullNameChinese='" + getFullNameChinese() + "'" +
            ", countryNameEnglish='" + getCountryNameEnglish() + "'" +
            ", countryNameChinese='" + getCountryNameChinese() + "'" +
            ", nameAbbre='" + getNameAbbre() + "'" +
            ", currencySort=" + getCurrencySort() +
            "}";
    }
}

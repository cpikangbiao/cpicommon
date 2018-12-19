/*
 * Copyright (c)  2015-2018, All rights Reserved, Designed By Kang Biao
 * Email: alex.kangbiao@gmail.com
 * Create by Alex Kang on 18-12-18 上午9:40
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE
 */

package com.cpi.common.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Currency currency = (Currency) o;
        if (currency.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), currency.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
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

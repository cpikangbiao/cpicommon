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

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
 * A DTO for the Correspondent entity.
 */
public class CorrespondentDTO implements Serializable {

    private Long id;

    @NotNull
    private String correspondentName;

    private String faxNumber;

    private String address;

    private String telephoneOffice;

    private String telephoneAlternate;

    private String webSite;

    private Long portId;

    private String portPortName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorrespondentName() {
        return correspondentName;
    }

    public void setCorrespondentName(String correspondentName) {
        this.correspondentName = correspondentName;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephoneOffice() {
        return telephoneOffice;
    }

    public void setTelephoneOffice(String telephoneOffice) {
        this.telephoneOffice = telephoneOffice;
    }

    public String getTelephoneAlternate() {
        return telephoneAlternate;
    }

    public void setTelephoneAlternate(String telephoneAlternate) {
        this.telephoneAlternate = telephoneAlternate;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public Long getPortId() {
        return portId;
    }

    public void setPortId(Long portId) {
        this.portId = portId;
    }

    public String getPortPortName() {
        return portPortName;
    }

    public void setPortPortName(String portPortName) {
        this.portPortName = portPortName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CorrespondentDTO correspondentDTO = (CorrespondentDTO) o;
        if (correspondentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), correspondentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CorrespondentDTO{" +
            "id=" + getId() +
            ", correspondentName='" + getCorrespondentName() + "'" +
            ", faxNumber='" + getFaxNumber() + "'" +
            ", address='" + getAddress() + "'" +
            ", telephoneOffice='" + getTelephoneOffice() + "'" +
            ", telephoneAlternate='" + getTelephoneAlternate() + "'" +
            ", webSite='" + getWebSite() + "'" +
            ", port=" + getPortId() +
            ", port='" + getPortPortName() + "'" +
            "}";
    }
}

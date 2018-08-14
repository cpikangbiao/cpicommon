package com.cpi.common.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CorrespondentContact entity.
 */
public class CorrespondentContactDTO implements Serializable {

    private Long id;

    @NotNull
    private String correspondentContactName;

    private String telephoneOffice;

    private String telephone;

    private String eMail;

    private String webSite;

    private Long correspondentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorrespondentContactName() {
        return correspondentContactName;
    }

    public void setCorrespondentContactName(String correspondentContactName) {
        this.correspondentContactName = correspondentContactName;
    }

    public String getTelephoneOffice() {
        return telephoneOffice;
    }

    public void setTelephoneOffice(String telephoneOffice) {
        this.telephoneOffice = telephoneOffice;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public Long getCorrespondentId() {
        return correspondentId;
    }

    public void setCorrespondentId(Long correspondentId) {
        this.correspondentId = correspondentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CorrespondentContactDTO correspondentContactDTO = (CorrespondentContactDTO) o;
        if (correspondentContactDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), correspondentContactDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CorrespondentContactDTO{" +
            "id=" + getId() +
            ", correspondentContactName='" + getCorrespondentContactName() + "'" +
            ", telephoneOffice='" + getTelephoneOffice() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", eMail='" + geteMail() + "'" +
            ", webSite='" + getWebSite() + "'" +
            ", correspondent=" + getCorrespondentId() +
            "}";
    }
}

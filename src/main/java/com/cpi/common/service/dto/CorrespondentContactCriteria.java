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
 * Criteria class for the CorrespondentContact entity. This class is used in CorrespondentContactResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /correspondent-contacts?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CorrespondentContactCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter correspondentContactName;

    private StringFilter telephoneOffice;

    private StringFilter telephone;

    private StringFilter eMail;

    private StringFilter webSite;

    private LongFilter correspondentId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCorrespondentContactName() {
        return correspondentContactName;
    }

    public void setCorrespondentContactName(StringFilter correspondentContactName) {
        this.correspondentContactName = correspondentContactName;
    }

    public StringFilter getTelephoneOffice() {
        return telephoneOffice;
    }

    public void setTelephoneOffice(StringFilter telephoneOffice) {
        this.telephoneOffice = telephoneOffice;
    }

    public StringFilter getTelephone() {
        return telephone;
    }

    public void setTelephone(StringFilter telephone) {
        this.telephone = telephone;
    }

    public StringFilter geteMail() {
        return eMail;
    }

    public void seteMail(StringFilter eMail) {
        this.eMail = eMail;
    }

    public StringFilter getWebSite() {
        return webSite;
    }

    public void setWebSite(StringFilter webSite) {
        this.webSite = webSite;
    }

    public LongFilter getCorrespondentId() {
        return correspondentId;
    }

    public void setCorrespondentId(LongFilter correspondentId) {
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
        final CorrespondentContactCriteria that = (CorrespondentContactCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(correspondentContactName, that.correspondentContactName) &&
            Objects.equals(telephoneOffice, that.telephoneOffice) &&
            Objects.equals(telephone, that.telephone) &&
            Objects.equals(eMail, that.eMail) &&
            Objects.equals(webSite, that.webSite) &&
            Objects.equals(correspondentId, that.correspondentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        correspondentContactName,
        telephoneOffice,
        telephone,
        eMail,
        webSite,
        correspondentId
        );
    }

    @Override
    public String toString() {
        return "CorrespondentContactCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (correspondentContactName != null ? "correspondentContactName=" + correspondentContactName + ", " : "") +
                (telephoneOffice != null ? "telephoneOffice=" + telephoneOffice + ", " : "") +
                (telephone != null ? "telephone=" + telephone + ", " : "") +
                (eMail != null ? "eMail=" + eMail + ", " : "") +
                (webSite != null ? "webSite=" + webSite + ", " : "") +
                (correspondentId != null ? "correspondentId=" + correspondentId + ", " : "") +
            "}";
    }

}

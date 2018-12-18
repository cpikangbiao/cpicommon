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
 * Criteria class for the Vessel entity. This class is used in VesselResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /vessels?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VesselCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter vesselCode;

    private StringFilter vesselName;

    private StringFilter vesselChineseName;

    private DoubleFilter gtr;

    private DoubleFilter wrt;

    private StringFilter buildLocation;

    private StringFilter buildYear;

    private StringFilter foc;

    private DoubleFilter dwt;

    private DoubleFilter hullHmAmount;

    private DoubleFilter hullHmValue;

    private DoubleFilter hullIvAmount;

    private DoubleFilter hullIvValue;

    private DoubleFilter hullWarAmount;

    private DoubleFilter hullWarValue;

    private StringFilter vesselSize;

    private StringFilter line;

    private DoubleFilter deeper;

    private StringFilter callSign;

    private StringFilter imoNumber;

    private LongFilter vesselCountryId;

    private LongFilter vesselCurrencyId;

    private LongFilter vesselTypeId;

    private LongFilter vesselOwnerCompanyId;

    private LongFilter registedPortId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getVesselCode() {
        return vesselCode;
    }

    public void setVesselCode(StringFilter vesselCode) {
        this.vesselCode = vesselCode;
    }

    public StringFilter getVesselName() {
        return vesselName;
    }

    public void setVesselName(StringFilter vesselName) {
        this.vesselName = vesselName;
    }

    public StringFilter getVesselChineseName() {
        return vesselChineseName;
    }

    public void setVesselChineseName(StringFilter vesselChineseName) {
        this.vesselChineseName = vesselChineseName;
    }

    public DoubleFilter getGtr() {
        return gtr;
    }

    public void setGtr(DoubleFilter gtr) {
        this.gtr = gtr;
    }

    public DoubleFilter getWrt() {
        return wrt;
    }

    public void setWrt(DoubleFilter wrt) {
        this.wrt = wrt;
    }

    public StringFilter getBuildLocation() {
        return buildLocation;
    }

    public void setBuildLocation(StringFilter buildLocation) {
        this.buildLocation = buildLocation;
    }

    public StringFilter getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(StringFilter buildYear) {
        this.buildYear = buildYear;
    }

    public StringFilter getFoc() {
        return foc;
    }

    public void setFoc(StringFilter foc) {
        this.foc = foc;
    }

    public DoubleFilter getDwt() {
        return dwt;
    }

    public void setDwt(DoubleFilter dwt) {
        this.dwt = dwt;
    }

    public DoubleFilter getHullHmAmount() {
        return hullHmAmount;
    }

    public void setHullHmAmount(DoubleFilter hullHmAmount) {
        this.hullHmAmount = hullHmAmount;
    }

    public DoubleFilter getHullHmValue() {
        return hullHmValue;
    }

    public void setHullHmValue(DoubleFilter hullHmValue) {
        this.hullHmValue = hullHmValue;
    }

    public DoubleFilter getHullIvAmount() {
        return hullIvAmount;
    }

    public void setHullIvAmount(DoubleFilter hullIvAmount) {
        this.hullIvAmount = hullIvAmount;
    }

    public DoubleFilter getHullIvValue() {
        return hullIvValue;
    }

    public void setHullIvValue(DoubleFilter hullIvValue) {
        this.hullIvValue = hullIvValue;
    }

    public DoubleFilter getHullWarAmount() {
        return hullWarAmount;
    }

    public void setHullWarAmount(DoubleFilter hullWarAmount) {
        this.hullWarAmount = hullWarAmount;
    }

    public DoubleFilter getHullWarValue() {
        return hullWarValue;
    }

    public void setHullWarValue(DoubleFilter hullWarValue) {
        this.hullWarValue = hullWarValue;
    }

    public StringFilter getVesselSize() {
        return vesselSize;
    }

    public void setVesselSize(StringFilter vesselSize) {
        this.vesselSize = vesselSize;
    }

    public StringFilter getLine() {
        return line;
    }

    public void setLine(StringFilter line) {
        this.line = line;
    }

    public DoubleFilter getDeeper() {
        return deeper;
    }

    public void setDeeper(DoubleFilter deeper) {
        this.deeper = deeper;
    }

    public StringFilter getCallSign() {
        return callSign;
    }

    public void setCallSign(StringFilter callSign) {
        this.callSign = callSign;
    }

    public StringFilter getImoNumber() {
        return imoNumber;
    }

    public void setImoNumber(StringFilter imoNumber) {
        this.imoNumber = imoNumber;
    }

    public LongFilter getVesselCountryId() {
        return vesselCountryId;
    }

    public void setVesselCountryId(LongFilter vesselCountryId) {
        this.vesselCountryId = vesselCountryId;
    }

    public LongFilter getVesselCurrencyId() {
        return vesselCurrencyId;
    }

    public void setVesselCurrencyId(LongFilter vesselCurrencyId) {
        this.vesselCurrencyId = vesselCurrencyId;
    }

    public LongFilter getVesselTypeId() {
        return vesselTypeId;
    }

    public void setVesselTypeId(LongFilter vesselTypeId) {
        this.vesselTypeId = vesselTypeId;
    }

    public LongFilter getVesselOwnerCompanyId() {
        return vesselOwnerCompanyId;
    }

    public void setVesselOwnerCompanyId(LongFilter vesselOwnerCompanyId) {
        this.vesselOwnerCompanyId = vesselOwnerCompanyId;
    }

    public LongFilter getRegistedPortId() {
        return registedPortId;
    }

    public void setRegistedPortId(LongFilter registedPortId) {
        this.registedPortId = registedPortId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VesselCriteria that = (VesselCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(vesselCode, that.vesselCode) &&
            Objects.equals(vesselName, that.vesselName) &&
            Objects.equals(vesselChineseName, that.vesselChineseName) &&
            Objects.equals(gtr, that.gtr) &&
            Objects.equals(wrt, that.wrt) &&
            Objects.equals(buildLocation, that.buildLocation) &&
            Objects.equals(buildYear, that.buildYear) &&
            Objects.equals(foc, that.foc) &&
            Objects.equals(dwt, that.dwt) &&
            Objects.equals(hullHmAmount, that.hullHmAmount) &&
            Objects.equals(hullHmValue, that.hullHmValue) &&
            Objects.equals(hullIvAmount, that.hullIvAmount) &&
            Objects.equals(hullIvValue, that.hullIvValue) &&
            Objects.equals(hullWarAmount, that.hullWarAmount) &&
            Objects.equals(hullWarValue, that.hullWarValue) &&
            Objects.equals(vesselSize, that.vesselSize) &&
            Objects.equals(line, that.line) &&
            Objects.equals(deeper, that.deeper) &&
            Objects.equals(callSign, that.callSign) &&
            Objects.equals(imoNumber, that.imoNumber) &&
            Objects.equals(vesselCountryId, that.vesselCountryId) &&
            Objects.equals(vesselCurrencyId, that.vesselCurrencyId) &&
            Objects.equals(vesselTypeId, that.vesselTypeId) &&
            Objects.equals(vesselOwnerCompanyId, that.vesselOwnerCompanyId) &&
            Objects.equals(registedPortId, that.registedPortId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        vesselCode,
        vesselName,
        vesselChineseName,
        gtr,
        wrt,
        buildLocation,
        buildYear,
        foc,
        dwt,
        hullHmAmount,
        hullHmValue,
        hullIvAmount,
        hullIvValue,
        hullWarAmount,
        hullWarValue,
        vesselSize,
        line,
        deeper,
        callSign,
        imoNumber,
        vesselCountryId,
        vesselCurrencyId,
        vesselTypeId,
        vesselOwnerCompanyId,
        registedPortId
        );
    }

    @Override
    public String toString() {
        return "VesselCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (vesselCode != null ? "vesselCode=" + vesselCode + ", " : "") +
                (vesselName != null ? "vesselName=" + vesselName + ", " : "") +
                (vesselChineseName != null ? "vesselChineseName=" + vesselChineseName + ", " : "") +
                (gtr != null ? "gtr=" + gtr + ", " : "") +
                (wrt != null ? "wrt=" + wrt + ", " : "") +
                (buildLocation != null ? "buildLocation=" + buildLocation + ", " : "") +
                (buildYear != null ? "buildYear=" + buildYear + ", " : "") +
                (foc != null ? "foc=" + foc + ", " : "") +
                (dwt != null ? "dwt=" + dwt + ", " : "") +
                (hullHmAmount != null ? "hullHmAmount=" + hullHmAmount + ", " : "") +
                (hullHmValue != null ? "hullHmValue=" + hullHmValue + ", " : "") +
                (hullIvAmount != null ? "hullIvAmount=" + hullIvAmount + ", " : "") +
                (hullIvValue != null ? "hullIvValue=" + hullIvValue + ", " : "") +
                (hullWarAmount != null ? "hullWarAmount=" + hullWarAmount + ", " : "") +
                (hullWarValue != null ? "hullWarValue=" + hullWarValue + ", " : "") +
                (vesselSize != null ? "vesselSize=" + vesselSize + ", " : "") +
                (line != null ? "line=" + line + ", " : "") +
                (deeper != null ? "deeper=" + deeper + ", " : "") +
                (callSign != null ? "callSign=" + callSign + ", " : "") +
                (imoNumber != null ? "imoNumber=" + imoNumber + ", " : "") +
                (vesselCountryId != null ? "vesselCountryId=" + vesselCountryId + ", " : "") +
                (vesselCurrencyId != null ? "vesselCurrencyId=" + vesselCurrencyId + ", " : "") +
                (vesselTypeId != null ? "vesselTypeId=" + vesselTypeId + ", " : "") +
                (vesselOwnerCompanyId != null ? "vesselOwnerCompanyId=" + vesselOwnerCompanyId + ", " : "") +
                (registedPortId != null ? "registedPortId=" + registedPortId + ", " : "") +
            "}";
    }

}

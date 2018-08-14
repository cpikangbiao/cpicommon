package com.cpi.common.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CurrencyRate entity.
 */
public class CurrencyRateDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate rateDate;

    @NotNull
    private Double currencyRate;

    private Long currencyId;

    private String currencyNameAbbre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRateDate() {
        return rateDate;
    }

    public void setRateDate(LocalDate rateDate) {
        this.rateDate = rateDate;
    }

    public Double getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(Double currencyRate) {
        this.currencyRate = currencyRate;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyNameAbbre() {
        return currencyNameAbbre;
    }

    public void setCurrencyNameAbbre(String currencyNameAbbre) {
        this.currencyNameAbbre = currencyNameAbbre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CurrencyRateDTO currencyRateDTO = (CurrencyRateDTO) o;
        if (currencyRateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), currencyRateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CurrencyRateDTO{" +
            "id=" + getId() +
            ", rateDate='" + getRateDate() + "'" +
            ", currencyRate=" + getCurrencyRate() +
            ", currency=" + getCurrencyId() +
            ", currency='" + getCurrencyNameAbbre() + "'" +
            "}";
    }
}

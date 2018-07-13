package com.cpi.common.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CurrencyRate.
 */
@Entity
@Table(name = "currency_rate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CurrencyRate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "rate_date", nullable = false)
    private LocalDate rateDate;

    @NotNull
    @Column(name = "currency_rate", nullable = false)
    private Double currencyRate;

    @OneToOne
    @JoinColumn(unique = true)
    private Currency currency;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRateDate() {
        return rateDate;
    }

    public CurrencyRate rateDate(LocalDate rateDate) {
        this.rateDate = rateDate;
        return this;
    }

    public void setRateDate(LocalDate rateDate) {
        this.rateDate = rateDate;
    }

    public Double getCurrencyRate() {
        return currencyRate;
    }

    public CurrencyRate currencyRate(Double currencyRate) {
        this.currencyRate = currencyRate;
        return this;
    }

    public void setCurrencyRate(Double currencyRate) {
        this.currencyRate = currencyRate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public CurrencyRate currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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
        CurrencyRate currencyRate = (CurrencyRate) o;
        if (currencyRate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), currencyRate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CurrencyRate{" +
            "id=" + getId() +
            ", rateDate='" + getRateDate() + "'" +
            ", currencyRate=" + getCurrencyRate() +
            "}";
    }
}

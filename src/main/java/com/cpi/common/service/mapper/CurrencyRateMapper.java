package com.cpi.common.service.mapper;

import com.cpi.common.domain.*;
import com.cpi.common.service.dto.CurrencyRateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CurrencyRate and its DTO CurrencyRateDTO.
 */
@Mapper(componentModel = "spring", uses = {CurrencyMapper.class})
public interface CurrencyRateMapper extends EntityMapper<CurrencyRateDTO, CurrencyRate> {

    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "currency.nameAbbre", target = "currencyNameAbbre")
    CurrencyRateDTO toDto(CurrencyRate currencyRate);

    @Mapping(source = "currencyId", target = "currency")
    CurrencyRate toEntity(CurrencyRateDTO currencyRateDTO);

    default CurrencyRate fromId(Long id) {
        if (id == null) {
            return null;
        }
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setId(id);
        return currencyRate;
    }
}

package com.cpi.common.service.mapper;

import com.cpi.common.domain.*;
import com.cpi.common.service.dto.VesselDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Vessel and its DTO VesselDTO.
 */
@Mapper(componentModel = "spring", uses = {CountryMapper.class, CurrencyMapper.class, VesselTypeMapper.class, CompanyMapper.class, PortMapper.class})
public interface VesselMapper extends EntityMapper<VesselDTO, Vessel> {

    @Mapping(source = "vesselCountry.id", target = "vesselCountryId")
    @Mapping(source = "vesselCountry.countryName", target = "vesselCountryCountryName")
    @Mapping(source = "vesselCurrency.id", target = "vesselCurrencyId")
    @Mapping(source = "vesselCurrency.nameAbbre", target = "vesselCurrencyNameAbbre")
    @Mapping(source = "vesselType.id", target = "vesselTypeId")
    @Mapping(source = "vesselType.vesselTypeName", target = "vesselTypeVesselTypeName")
    @Mapping(source = "vesselOwnerCompany.id", target = "vesselOwnerCompanyId")
    @Mapping(source = "vesselOwnerCompany.companyName", target = "vesselOwnerCompanyCompanyName")
    @Mapping(source = "registedPort.id", target = "registedPortId")
    @Mapping(source = "registedPort.portName", target = "registedPortPortName")
    VesselDTO toDto(Vessel vessel);

    @Mapping(source = "vesselCountryId", target = "vesselCountry")
    @Mapping(source = "vesselCurrencyId", target = "vesselCurrency")
    @Mapping(source = "vesselTypeId", target = "vesselType")
    @Mapping(source = "vesselOwnerCompanyId", target = "vesselOwnerCompany")
    @Mapping(source = "registedPortId", target = "registedPort")
    Vessel toEntity(VesselDTO vesselDTO);

    default Vessel fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vessel vessel = new Vessel();
        vessel.setId(id);
        return vessel;
    }
}

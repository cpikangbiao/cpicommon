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

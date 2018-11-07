package com.cpi.common.service.mapper;

import com.cpi.common.domain.*;
import com.cpi.common.service.dto.AddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Address and its DTO AddressDTO.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class, AddressTypeMapper.class})
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.companyName", target = "companyCompanyName")
    @Mapping(source = "addressType.id", target = "addressTypeId")
    @Mapping(source = "addressType.addressTypeName", target = "addressTypeAddressTypeName")
    AddressDTO toDto(Address address);

    @Mapping(source = "companyId", target = "company")
    @Mapping(source = "addressTypeId", target = "addressType")
    Address toEntity(AddressDTO addressDTO);

    default Address fromId(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }
}

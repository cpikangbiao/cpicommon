package com.cpi.common.service.mapper;

import com.cpi.common.domain.*;
import com.cpi.common.service.dto.AddressTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AddressType} and its DTO {@link AddressTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AddressTypeMapper extends EntityMapper<AddressTypeDTO, AddressType> {



    default AddressType fromId(Long id) {
        if (id == null) {
            return null;
        }
        AddressType addressType = new AddressType();
        addressType.setId(id);
        return addressType;
    }
}

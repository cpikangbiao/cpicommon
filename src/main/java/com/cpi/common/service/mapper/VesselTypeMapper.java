package com.cpi.common.service.mapper;

import com.cpi.common.domain.*;
import com.cpi.common.service.dto.VesselTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VesselType and its DTO VesselTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VesselTypeMapper extends EntityMapper<VesselTypeDTO, VesselType> {



    default VesselType fromId(Long id) {
        if (id == null) {
            return null;
        }
        VesselType vesselType = new VesselType();
        vesselType.setId(id);
        return vesselType;
    }
}

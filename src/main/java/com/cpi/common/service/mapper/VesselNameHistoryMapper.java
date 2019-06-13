package com.cpi.common.service.mapper;

import com.cpi.common.domain.*;
import com.cpi.common.service.dto.VesselNameHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link VesselNameHistory} and its DTO {@link VesselNameHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {VesselMapper.class})
public interface VesselNameHistoryMapper extends EntityMapper<VesselNameHistoryDTO, VesselNameHistory> {

    @Mapping(source = "vessel.id", target = "vesselId")
    @Mapping(source = "vessel.vesselName", target = "vesselVesselName")
    VesselNameHistoryDTO toDto(VesselNameHistory vesselNameHistory);

    @Mapping(source = "vesselId", target = "vessel")
    VesselNameHistory toEntity(VesselNameHistoryDTO vesselNameHistoryDTO);

    default VesselNameHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        VesselNameHistory vesselNameHistory = new VesselNameHistory();
        vesselNameHistory.setId(id);
        return vesselNameHistory;
    }
}

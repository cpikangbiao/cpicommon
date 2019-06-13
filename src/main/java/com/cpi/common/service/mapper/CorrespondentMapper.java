package com.cpi.common.service.mapper;

import com.cpi.common.domain.*;
import com.cpi.common.service.dto.CorrespondentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Correspondent} and its DTO {@link CorrespondentDTO}.
 */
@Mapper(componentModel = "spring", uses = {PortMapper.class})
public interface CorrespondentMapper extends EntityMapper<CorrespondentDTO, Correspondent> {

    @Mapping(source = "port.id", target = "portId")
    @Mapping(source = "port.portName", target = "portPortName")
    CorrespondentDTO toDto(Correspondent correspondent);

    @Mapping(target = "contacts", ignore = true)
    @Mapping(target = "removeContacts", ignore = true)
    @Mapping(source = "portId", target = "port")
    Correspondent toEntity(CorrespondentDTO correspondentDTO);

    default Correspondent fromId(Long id) {
        if (id == null) {
            return null;
        }
        Correspondent correspondent = new Correspondent();
        correspondent.setId(id);
        return correspondent;
    }
}

package com.cpi.common.service.mapper;

import com.cpi.common.domain.*;
import com.cpi.common.service.dto.CorrespondentContactDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CorrespondentContact} and its DTO {@link CorrespondentContactDTO}.
 */
@Mapper(componentModel = "spring", uses = {CorrespondentMapper.class})
public interface CorrespondentContactMapper extends EntityMapper<CorrespondentContactDTO, CorrespondentContact> {

    @Mapping(source = "correspondent.id", target = "correspondentId")
    CorrespondentContactDTO toDto(CorrespondentContact correspondentContact);

    @Mapping(source = "correspondentId", target = "correspondent")
    CorrespondentContact toEntity(CorrespondentContactDTO correspondentContactDTO);

    default CorrespondentContact fromId(Long id) {
        if (id == null) {
            return null;
        }
        CorrespondentContact correspondentContact = new CorrespondentContact();
        correspondentContact.setId(id);
        return correspondentContact;
    }
}

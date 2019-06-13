package com.cpi.common.service.mapper;

import com.cpi.common.domain.*;
import com.cpi.common.service.dto.ClassificationSocietyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClassificationSociety} and its DTO {@link ClassificationSocietyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClassificationSocietyMapper extends EntityMapper<ClassificationSocietyDTO, ClassificationSociety> {



    default ClassificationSociety fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClassificationSociety classificationSociety = new ClassificationSociety();
        classificationSociety.setId(id);
        return classificationSociety;
    }
}

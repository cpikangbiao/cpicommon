package com.cpi.common.service.mapper;

import com.cpi.common.domain.*;
import com.cpi.common.service.dto.CompanyNameHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompanyNameHistory} and its DTO {@link CompanyNameHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface CompanyNameHistoryMapper extends EntityMapper<CompanyNameHistoryDTO, CompanyNameHistory> {

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.companyName", target = "companyCompanyName")
    CompanyNameHistoryDTO toDto(CompanyNameHistory companyNameHistory);

    @Mapping(source = "companyId", target = "company")
    CompanyNameHistory toEntity(CompanyNameHistoryDTO companyNameHistoryDTO);

    default CompanyNameHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanyNameHistory companyNameHistory = new CompanyNameHistory();
        companyNameHistory.setId(id);
        return companyNameHistory;
    }
}

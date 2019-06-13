package com.cpi.common.service.impl;

import com.cpi.common.service.CompanyNameHistoryService;
import com.cpi.common.domain.CompanyNameHistory;
import com.cpi.common.repository.CompanyNameHistoryRepository;
import com.cpi.common.service.dto.CompanyNameHistoryDTO;
import com.cpi.common.service.mapper.CompanyNameHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CompanyNameHistory}.
 */
@Service
@Transactional
public class CompanyNameHistoryServiceImpl implements CompanyNameHistoryService {

    private final Logger log = LoggerFactory.getLogger(CompanyNameHistoryServiceImpl.class);

    private final CompanyNameHistoryRepository companyNameHistoryRepository;

    private final CompanyNameHistoryMapper companyNameHistoryMapper;

    public CompanyNameHistoryServiceImpl(CompanyNameHistoryRepository companyNameHistoryRepository, CompanyNameHistoryMapper companyNameHistoryMapper) {
        this.companyNameHistoryRepository = companyNameHistoryRepository;
        this.companyNameHistoryMapper = companyNameHistoryMapper;
    }

    /**
     * Save a companyNameHistory.
     *
     * @param companyNameHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CompanyNameHistoryDTO save(CompanyNameHistoryDTO companyNameHistoryDTO) {
        log.debug("Request to save CompanyNameHistory : {}", companyNameHistoryDTO);
        CompanyNameHistory companyNameHistory = companyNameHistoryMapper.toEntity(companyNameHistoryDTO);
        companyNameHistory = companyNameHistoryRepository.save(companyNameHistory);
        return companyNameHistoryMapper.toDto(companyNameHistory);
    }

    /**
     * Get all the companyNameHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompanyNameHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyNameHistories");
        return companyNameHistoryRepository.findAll(pageable)
            .map(companyNameHistoryMapper::toDto);
    }


    /**
     * Get one companyNameHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyNameHistoryDTO> findOne(Long id) {
        log.debug("Request to get CompanyNameHistory : {}", id);
        return companyNameHistoryRepository.findById(id)
            .map(companyNameHistoryMapper::toDto);
    }

    /**
     * Delete the companyNameHistory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyNameHistory : {}", id);
        companyNameHistoryRepository.deleteById(id);
    }
}

package com.cpi.common.service.impl;

import com.cpi.common.service.VesselNameHistoryService;
import com.cpi.common.domain.VesselNameHistory;
import com.cpi.common.repository.VesselNameHistoryRepository;
import com.cpi.common.service.dto.VesselNameHistoryDTO;
import com.cpi.common.service.mapper.VesselNameHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing VesselNameHistory.
 */
@Service
@Transactional
public class VesselNameHistoryServiceImpl implements VesselNameHistoryService {

    private final Logger log = LoggerFactory.getLogger(VesselNameHistoryServiceImpl.class);

    private final VesselNameHistoryRepository vesselNameHistoryRepository;

    private final VesselNameHistoryMapper vesselNameHistoryMapper;

    public VesselNameHistoryServiceImpl(VesselNameHistoryRepository vesselNameHistoryRepository, VesselNameHistoryMapper vesselNameHistoryMapper) {
        this.vesselNameHistoryRepository = vesselNameHistoryRepository;
        this.vesselNameHistoryMapper = vesselNameHistoryMapper;
    }

    /**
     * Save a vesselNameHistory.
     *
     * @param vesselNameHistoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VesselNameHistoryDTO save(VesselNameHistoryDTO vesselNameHistoryDTO) {
        log.debug("Request to save VesselNameHistory : {}", vesselNameHistoryDTO);

        VesselNameHistory vesselNameHistory = vesselNameHistoryMapper.toEntity(vesselNameHistoryDTO);
        vesselNameHistory = vesselNameHistoryRepository.save(vesselNameHistory);
        return vesselNameHistoryMapper.toDto(vesselNameHistory);
    }

    /**
     * Get all the vesselNameHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VesselNameHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VesselNameHistories");
        return vesselNameHistoryRepository.findAll(pageable)
            .map(vesselNameHistoryMapper::toDto);
    }


    /**
     * Get one vesselNameHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VesselNameHistoryDTO> findOne(Long id) {
        log.debug("Request to get VesselNameHistory : {}", id);
        return vesselNameHistoryRepository.findById(id)
            .map(vesselNameHistoryMapper::toDto);
    }

    /**
     * Delete the vesselNameHistory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VesselNameHistory : {}", id);
        vesselNameHistoryRepository.deleteById(id);
    }
}

package com.cpi.common.service.impl;

import com.cpi.common.service.VesselService;
import com.cpi.common.domain.Vessel;
import com.cpi.common.repository.VesselRepository;
import com.cpi.common.service.dto.VesselDTO;
import com.cpi.common.service.mapper.VesselMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Vessel.
 */
@Service
@Transactional
public class VesselServiceImpl implements VesselService {

    private final Logger log = LoggerFactory.getLogger(VesselServiceImpl.class);

    private final VesselRepository vesselRepository;

    private final VesselMapper vesselMapper;

    public VesselServiceImpl(VesselRepository vesselRepository, VesselMapper vesselMapper) {
        this.vesselRepository = vesselRepository;
        this.vesselMapper = vesselMapper;
    }

    /**
     * Save a vessel.
     *
     * @param vesselDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VesselDTO save(VesselDTO vesselDTO) {
        log.debug("Request to save Vessel : {}", vesselDTO);
        Vessel vessel = vesselMapper.toEntity(vesselDTO);
        vessel = vesselRepository.save(vessel);
        return vesselMapper.toDto(vessel);
    }

    /**
     * Get all the vessels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VesselDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Vessels");
        return vesselRepository.findAll(pageable)
            .map(vesselMapper::toDto);
    }


    /**
     * Get one vessel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VesselDTO> findOne(Long id) {
        log.debug("Request to get Vessel : {}", id);
        return vesselRepository.findById(id)
            .map(vesselMapper::toDto);
    }

    /**
     * Delete the vessel by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vessel : {}", id);
        vesselRepository.deleteById(id);
    }
}

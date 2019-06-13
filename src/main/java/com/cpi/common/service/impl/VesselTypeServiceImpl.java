package com.cpi.common.service.impl;

import com.cpi.common.service.VesselTypeService;
import com.cpi.common.domain.VesselType;
import com.cpi.common.repository.VesselTypeRepository;
import com.cpi.common.service.dto.VesselTypeDTO;
import com.cpi.common.service.mapper.VesselTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link VesselType}.
 */
@Service
@Transactional
public class VesselTypeServiceImpl implements VesselTypeService {

    private final Logger log = LoggerFactory.getLogger(VesselTypeServiceImpl.class);

    private final VesselTypeRepository vesselTypeRepository;

    private final VesselTypeMapper vesselTypeMapper;

    public VesselTypeServiceImpl(VesselTypeRepository vesselTypeRepository, VesselTypeMapper vesselTypeMapper) {
        this.vesselTypeRepository = vesselTypeRepository;
        this.vesselTypeMapper = vesselTypeMapper;
    }

    /**
     * Save a vesselType.
     *
     * @param vesselTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public VesselTypeDTO save(VesselTypeDTO vesselTypeDTO) {
        log.debug("Request to save VesselType : {}", vesselTypeDTO);
        VesselType vesselType = vesselTypeMapper.toEntity(vesselTypeDTO);
        vesselType = vesselTypeRepository.save(vesselType);
        return vesselTypeMapper.toDto(vesselType);
    }

    /**
     * Get all the vesselTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VesselTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VesselTypes");
        return vesselTypeRepository.findAll(pageable)
            .map(vesselTypeMapper::toDto);
    }


    /**
     * Get one vesselType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VesselTypeDTO> findOne(Long id) {
        log.debug("Request to get VesselType : {}", id);
        return vesselTypeRepository.findById(id)
            .map(vesselTypeMapper::toDto);
    }

    /**
     * Delete the vesselType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VesselType : {}", id);
        vesselTypeRepository.deleteById(id);
    }
}

package com.cpi.common.service.impl;

import com.cpi.common.service.AddressTypeService;
import com.cpi.common.domain.AddressType;
import com.cpi.common.repository.AddressTypeRepository;
import com.cpi.common.service.dto.AddressTypeDTO;
import com.cpi.common.service.mapper.AddressTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AddressType}.
 */
@Service
@Transactional
public class AddressTypeServiceImpl implements AddressTypeService {

    private final Logger log = LoggerFactory.getLogger(AddressTypeServiceImpl.class);

    private final AddressTypeRepository addressTypeRepository;

    private final AddressTypeMapper addressTypeMapper;

    public AddressTypeServiceImpl(AddressTypeRepository addressTypeRepository, AddressTypeMapper addressTypeMapper) {
        this.addressTypeRepository = addressTypeRepository;
        this.addressTypeMapper = addressTypeMapper;
    }

    /**
     * Save a addressType.
     *
     * @param addressTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AddressTypeDTO save(AddressTypeDTO addressTypeDTO) {
        log.debug("Request to save AddressType : {}", addressTypeDTO);
        AddressType addressType = addressTypeMapper.toEntity(addressTypeDTO);
        addressType = addressTypeRepository.save(addressType);
        return addressTypeMapper.toDto(addressType);
    }

    /**
     * Get all the addressTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AddressTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AddressTypes");
        return addressTypeRepository.findAll(pageable)
            .map(addressTypeMapper::toDto);
    }


    /**
     * Get one addressType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AddressTypeDTO> findOne(Long id) {
        log.debug("Request to get AddressType : {}", id);
        return addressTypeRepository.findById(id)
            .map(addressTypeMapper::toDto);
    }

    /**
     * Delete the addressType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AddressType : {}", id);
        addressTypeRepository.deleteById(id);
    }
}

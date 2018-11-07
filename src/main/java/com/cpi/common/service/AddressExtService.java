package com.cpi.common.service;

import com.cpi.common.domain.*;
import com.cpi.common.repository.AddressRepository;
import com.cpi.common.service.dto.AddressCriteria;
import com.cpi.common.service.dto.AddressDTO;
import com.cpi.common.service.mapper.AddressMapper;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for executing complex queries for Address entities in the database.
 * The main input is a {@link AddressCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AddressDTO} or a {@link Page} of {@link AddressDTO} which fulfills the criteria.
 */
@Service
@Transactional
public class AddressExtService extends QueryService<Address> {

    private final Logger log = LoggerFactory.getLogger(AddressExtService.class);

    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;

    public AddressExtService(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Transactional(readOnly = true)
    public AddressDTO findRegisterAddress(Long companyId) {
        log.debug("find by companyId : {}", companyId);
        final Address address = addressRepository.findTopByAddressTypeIdAndCompanyId(AddressType.ADDRESS_TYPE_REGISTER, companyId);
        return addressMapper.toDto(address);
    }

    @Transactional(readOnly = true)
    public AddressDTO findEnglishAddress(Long companyId) {
        log.debug("find by companyId : {}", companyId);
        final Address address = addressRepository.findTopByAddressTypeIdAndCompanyId(AddressType.ADDRESS_TYPE_ENGLISH, companyId);
        return addressMapper.toDto(address);
    }

    @Transactional(readOnly = true)
    public AddressDTO findChineseAddress(Long companyId) {
        log.debug("find by companyId : {}", companyId);
        final Address address = addressRepository.findTopByAddressTypeIdAndCompanyId(AddressType.ADDRESS_TYPE_CHINESE, companyId);
        return addressMapper.toDto(address);
    }


}

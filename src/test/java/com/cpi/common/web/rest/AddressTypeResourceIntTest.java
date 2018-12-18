package com.cpi.common.web.rest;

import com.cpi.common.CpicommonApp;

import com.cpi.common.config.SecurityBeanOverrideConfiguration;

import com.cpi.common.domain.AddressType;
import com.cpi.common.repository.AddressTypeRepository;
import com.cpi.common.service.AddressTypeService;
import com.cpi.common.service.dto.AddressTypeDTO;
import com.cpi.common.service.mapper.AddressTypeMapper;
import com.cpi.common.web.rest.errors.ExceptionTranslator;
import com.cpi.common.service.dto.AddressTypeCriteria;
import com.cpi.common.service.AddressTypeQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.cpi.common.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AddressTypeResource REST controller.
 *
 * @see AddressTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, CpicommonApp.class})
public class AddressTypeResourceIntTest {

    private static final Integer DEFAULT_SORT_NUM = 1;
    private static final Integer UPDATED_SORT_NUM = 2;

    private static final String DEFAULT_ADDRESS_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_TYPE_NAME_CHINESE = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_TYPE_NAME_CHINESE = "BBBBBBBBBB";

    @Autowired
    private AddressTypeRepository addressTypeRepository;

    @Autowired
    private AddressTypeMapper addressTypeMapper;

    @Autowired
    private AddressTypeService addressTypeService;

    @Autowired
    private AddressTypeQueryService addressTypeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAddressTypeMockMvc;

    private AddressType addressType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AddressTypeResource addressTypeResource = new AddressTypeResource(addressTypeService, addressTypeQueryService);
        this.restAddressTypeMockMvc = MockMvcBuilders.standaloneSetup(addressTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AddressType createEntity(EntityManager em) {
        AddressType addressType = new AddressType()
            .sortNum(DEFAULT_SORT_NUM)
            .addressTypeName(DEFAULT_ADDRESS_TYPE_NAME)
            .addressTypeNameChinese(DEFAULT_ADDRESS_TYPE_NAME_CHINESE);
        return addressType;
    }

    @Before
    public void initTest() {
        addressType = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddressType() throws Exception {
        int databaseSizeBeforeCreate = addressTypeRepository.findAll().size();

        // Create the AddressType
        AddressTypeDTO addressTypeDTO = addressTypeMapper.toDto(addressType);
        restAddressTypeMockMvc.perform(post("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the AddressType in the database
        List<AddressType> addressTypeList = addressTypeRepository.findAll();
        assertThat(addressTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AddressType testAddressType = addressTypeList.get(addressTypeList.size() - 1);
        assertThat(testAddressType.getSortNum()).isEqualTo(DEFAULT_SORT_NUM);
        assertThat(testAddressType.getAddressTypeName()).isEqualTo(DEFAULT_ADDRESS_TYPE_NAME);
        assertThat(testAddressType.getAddressTypeNameChinese()).isEqualTo(DEFAULT_ADDRESS_TYPE_NAME_CHINESE);
    }

    @Test
    @Transactional
    public void createAddressTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressTypeRepository.findAll().size();

        // Create the AddressType with an existing ID
        addressType.setId(1L);
        AddressTypeDTO addressTypeDTO = addressTypeMapper.toDto(addressType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressTypeMockMvc.perform(post("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AddressType in the database
        List<AddressType> addressTypeList = addressTypeRepository.findAll();
        assertThat(addressTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAddressTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressTypeRepository.findAll().size();
        // set the field null
        addressType.setAddressTypeName(null);

        // Create the AddressType, which fails.
        AddressTypeDTO addressTypeDTO = addressTypeMapper.toDto(addressType);

        restAddressTypeMockMvc.perform(post("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressTypeDTO)))
            .andExpect(status().isBadRequest());

        List<AddressType> addressTypeList = addressTypeRepository.findAll();
        assertThat(addressTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAddressTypes() throws Exception {
        // Initialize the database
        addressTypeRepository.saveAndFlush(addressType);

        // Get all the addressTypeList
        restAddressTypeMockMvc.perform(get("/api/address-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addressType.getId().intValue())))
            .andExpect(jsonPath("$.[*].sortNum").value(hasItem(DEFAULT_SORT_NUM)))
            .andExpect(jsonPath("$.[*].addressTypeName").value(hasItem(DEFAULT_ADDRESS_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].addressTypeNameChinese").value(hasItem(DEFAULT_ADDRESS_TYPE_NAME_CHINESE.toString())));
    }
    
    @Test
    @Transactional
    public void getAddressType() throws Exception {
        // Initialize the database
        addressTypeRepository.saveAndFlush(addressType);

        // Get the addressType
        restAddressTypeMockMvc.perform(get("/api/address-types/{id}", addressType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(addressType.getId().intValue()))
            .andExpect(jsonPath("$.sortNum").value(DEFAULT_SORT_NUM))
            .andExpect(jsonPath("$.addressTypeName").value(DEFAULT_ADDRESS_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.addressTypeNameChinese").value(DEFAULT_ADDRESS_TYPE_NAME_CHINESE.toString()));
    }

    @Test
    @Transactional
    public void getAllAddressTypesBySortNumIsEqualToSomething() throws Exception {
        // Initialize the database
        addressTypeRepository.saveAndFlush(addressType);

        // Get all the addressTypeList where sortNum equals to DEFAULT_SORT_NUM
        defaultAddressTypeShouldBeFound("sortNum.equals=" + DEFAULT_SORT_NUM);

        // Get all the addressTypeList where sortNum equals to UPDATED_SORT_NUM
        defaultAddressTypeShouldNotBeFound("sortNum.equals=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllAddressTypesBySortNumIsInShouldWork() throws Exception {
        // Initialize the database
        addressTypeRepository.saveAndFlush(addressType);

        // Get all the addressTypeList where sortNum in DEFAULT_SORT_NUM or UPDATED_SORT_NUM
        defaultAddressTypeShouldBeFound("sortNum.in=" + DEFAULT_SORT_NUM + "," + UPDATED_SORT_NUM);

        // Get all the addressTypeList where sortNum equals to UPDATED_SORT_NUM
        defaultAddressTypeShouldNotBeFound("sortNum.in=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllAddressTypesBySortNumIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressTypeRepository.saveAndFlush(addressType);

        // Get all the addressTypeList where sortNum is not null
        defaultAddressTypeShouldBeFound("sortNum.specified=true");

        // Get all the addressTypeList where sortNum is null
        defaultAddressTypeShouldNotBeFound("sortNum.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressTypesBySortNumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressTypeRepository.saveAndFlush(addressType);

        // Get all the addressTypeList where sortNum greater than or equals to DEFAULT_SORT_NUM
        defaultAddressTypeShouldBeFound("sortNum.greaterOrEqualThan=" + DEFAULT_SORT_NUM);

        // Get all the addressTypeList where sortNum greater than or equals to UPDATED_SORT_NUM
        defaultAddressTypeShouldNotBeFound("sortNum.greaterOrEqualThan=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllAddressTypesBySortNumIsLessThanSomething() throws Exception {
        // Initialize the database
        addressTypeRepository.saveAndFlush(addressType);

        // Get all the addressTypeList where sortNum less than or equals to DEFAULT_SORT_NUM
        defaultAddressTypeShouldNotBeFound("sortNum.lessThan=" + DEFAULT_SORT_NUM);

        // Get all the addressTypeList where sortNum less than or equals to UPDATED_SORT_NUM
        defaultAddressTypeShouldBeFound("sortNum.lessThan=" + UPDATED_SORT_NUM);
    }


    @Test
    @Transactional
    public void getAllAddressTypesByAddressTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        addressTypeRepository.saveAndFlush(addressType);

        // Get all the addressTypeList where addressTypeName equals to DEFAULT_ADDRESS_TYPE_NAME
        defaultAddressTypeShouldBeFound("addressTypeName.equals=" + DEFAULT_ADDRESS_TYPE_NAME);

        // Get all the addressTypeList where addressTypeName equals to UPDATED_ADDRESS_TYPE_NAME
        defaultAddressTypeShouldNotBeFound("addressTypeName.equals=" + UPDATED_ADDRESS_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllAddressTypesByAddressTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        addressTypeRepository.saveAndFlush(addressType);

        // Get all the addressTypeList where addressTypeName in DEFAULT_ADDRESS_TYPE_NAME or UPDATED_ADDRESS_TYPE_NAME
        defaultAddressTypeShouldBeFound("addressTypeName.in=" + DEFAULT_ADDRESS_TYPE_NAME + "," + UPDATED_ADDRESS_TYPE_NAME);

        // Get all the addressTypeList where addressTypeName equals to UPDATED_ADDRESS_TYPE_NAME
        defaultAddressTypeShouldNotBeFound("addressTypeName.in=" + UPDATED_ADDRESS_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllAddressTypesByAddressTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressTypeRepository.saveAndFlush(addressType);

        // Get all the addressTypeList where addressTypeName is not null
        defaultAddressTypeShouldBeFound("addressTypeName.specified=true");

        // Get all the addressTypeList where addressTypeName is null
        defaultAddressTypeShouldNotBeFound("addressTypeName.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressTypesByAddressTypeNameChineseIsEqualToSomething() throws Exception {
        // Initialize the database
        addressTypeRepository.saveAndFlush(addressType);

        // Get all the addressTypeList where addressTypeNameChinese equals to DEFAULT_ADDRESS_TYPE_NAME_CHINESE
        defaultAddressTypeShouldBeFound("addressTypeNameChinese.equals=" + DEFAULT_ADDRESS_TYPE_NAME_CHINESE);

        // Get all the addressTypeList where addressTypeNameChinese equals to UPDATED_ADDRESS_TYPE_NAME_CHINESE
        defaultAddressTypeShouldNotBeFound("addressTypeNameChinese.equals=" + UPDATED_ADDRESS_TYPE_NAME_CHINESE);
    }

    @Test
    @Transactional
    public void getAllAddressTypesByAddressTypeNameChineseIsInShouldWork() throws Exception {
        // Initialize the database
        addressTypeRepository.saveAndFlush(addressType);

        // Get all the addressTypeList where addressTypeNameChinese in DEFAULT_ADDRESS_TYPE_NAME_CHINESE or UPDATED_ADDRESS_TYPE_NAME_CHINESE
        defaultAddressTypeShouldBeFound("addressTypeNameChinese.in=" + DEFAULT_ADDRESS_TYPE_NAME_CHINESE + "," + UPDATED_ADDRESS_TYPE_NAME_CHINESE);

        // Get all the addressTypeList where addressTypeNameChinese equals to UPDATED_ADDRESS_TYPE_NAME_CHINESE
        defaultAddressTypeShouldNotBeFound("addressTypeNameChinese.in=" + UPDATED_ADDRESS_TYPE_NAME_CHINESE);
    }

    @Test
    @Transactional
    public void getAllAddressTypesByAddressTypeNameChineseIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressTypeRepository.saveAndFlush(addressType);

        // Get all the addressTypeList where addressTypeNameChinese is not null
        defaultAddressTypeShouldBeFound("addressTypeNameChinese.specified=true");

        // Get all the addressTypeList where addressTypeNameChinese is null
        defaultAddressTypeShouldNotBeFound("addressTypeNameChinese.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAddressTypeShouldBeFound(String filter) throws Exception {
        restAddressTypeMockMvc.perform(get("/api/address-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addressType.getId().intValue())))
            .andExpect(jsonPath("$.[*].sortNum").value(hasItem(DEFAULT_SORT_NUM)))
            .andExpect(jsonPath("$.[*].addressTypeName").value(hasItem(DEFAULT_ADDRESS_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].addressTypeNameChinese").value(hasItem(DEFAULT_ADDRESS_TYPE_NAME_CHINESE.toString())));

        // Check, that the count call also returns 1
        restAddressTypeMockMvc.perform(get("/api/address-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultAddressTypeShouldNotBeFound(String filter) throws Exception {
        restAddressTypeMockMvc.perform(get("/api/address-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAddressTypeMockMvc.perform(get("/api/address-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAddressType() throws Exception {
        // Get the addressType
        restAddressTypeMockMvc.perform(get("/api/address-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddressType() throws Exception {
        // Initialize the database
        addressTypeRepository.saveAndFlush(addressType);

        int databaseSizeBeforeUpdate = addressTypeRepository.findAll().size();

        // Update the addressType
        AddressType updatedAddressType = addressTypeRepository.findById(addressType.getId()).get();
        // Disconnect from session so that the updates on updatedAddressType are not directly saved in db
        em.detach(updatedAddressType);
        updatedAddressType
            .sortNum(UPDATED_SORT_NUM)
            .addressTypeName(UPDATED_ADDRESS_TYPE_NAME)
            .addressTypeNameChinese(UPDATED_ADDRESS_TYPE_NAME_CHINESE);
        AddressTypeDTO addressTypeDTO = addressTypeMapper.toDto(updatedAddressType);

        restAddressTypeMockMvc.perform(put("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressTypeDTO)))
            .andExpect(status().isOk());

        // Validate the AddressType in the database
        List<AddressType> addressTypeList = addressTypeRepository.findAll();
        assertThat(addressTypeList).hasSize(databaseSizeBeforeUpdate);
        AddressType testAddressType = addressTypeList.get(addressTypeList.size() - 1);
        assertThat(testAddressType.getSortNum()).isEqualTo(UPDATED_SORT_NUM);
        assertThat(testAddressType.getAddressTypeName()).isEqualTo(UPDATED_ADDRESS_TYPE_NAME);
        assertThat(testAddressType.getAddressTypeNameChinese()).isEqualTo(UPDATED_ADDRESS_TYPE_NAME_CHINESE);
    }

    @Test
    @Transactional
    public void updateNonExistingAddressType() throws Exception {
        int databaseSizeBeforeUpdate = addressTypeRepository.findAll().size();

        // Create the AddressType
        AddressTypeDTO addressTypeDTO = addressTypeMapper.toDto(addressType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressTypeMockMvc.perform(put("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AddressType in the database
        List<AddressType> addressTypeList = addressTypeRepository.findAll();
        assertThat(addressTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAddressType() throws Exception {
        // Initialize the database
        addressTypeRepository.saveAndFlush(addressType);

        int databaseSizeBeforeDelete = addressTypeRepository.findAll().size();

        // Get the addressType
        restAddressTypeMockMvc.perform(delete("/api/address-types/{id}", addressType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AddressType> addressTypeList = addressTypeRepository.findAll();
        assertThat(addressTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressType.class);
        AddressType addressType1 = new AddressType();
        addressType1.setId(1L);
        AddressType addressType2 = new AddressType();
        addressType2.setId(addressType1.getId());
        assertThat(addressType1).isEqualTo(addressType2);
        addressType2.setId(2L);
        assertThat(addressType1).isNotEqualTo(addressType2);
        addressType1.setId(null);
        assertThat(addressType1).isNotEqualTo(addressType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressTypeDTO.class);
        AddressTypeDTO addressTypeDTO1 = new AddressTypeDTO();
        addressTypeDTO1.setId(1L);
        AddressTypeDTO addressTypeDTO2 = new AddressTypeDTO();
        assertThat(addressTypeDTO1).isNotEqualTo(addressTypeDTO2);
        addressTypeDTO2.setId(addressTypeDTO1.getId());
        assertThat(addressTypeDTO1).isEqualTo(addressTypeDTO2);
        addressTypeDTO2.setId(2L);
        assertThat(addressTypeDTO1).isNotEqualTo(addressTypeDTO2);
        addressTypeDTO1.setId(null);
        assertThat(addressTypeDTO1).isNotEqualTo(addressTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(addressTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(addressTypeMapper.fromId(null)).isNull();
    }
}

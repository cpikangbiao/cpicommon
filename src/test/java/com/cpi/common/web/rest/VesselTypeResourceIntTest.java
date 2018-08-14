package com.cpi.common.web.rest;

import com.cpi.common.CpicommonApp;

import com.cpi.common.config.SecurityBeanOverrideConfiguration;

import com.cpi.common.domain.VesselType;
import com.cpi.common.repository.VesselTypeRepository;
import com.cpi.common.service.VesselTypeService;
import com.cpi.common.service.dto.VesselTypeDTO;
import com.cpi.common.service.mapper.VesselTypeMapper;
import com.cpi.common.web.rest.errors.ExceptionTranslator;
import com.cpi.common.service.dto.VesselTypeCriteria;
import com.cpi.common.service.VesselTypeQueryService;

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

import javax.persistence.EntityManager;
import java.util.List;


import static com.cpi.common.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VesselTypeResource REST controller.
 *
 * @see VesselTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, CpicommonApp.class})
public class VesselTypeResourceIntTest {

    private static final String DEFAULT_VESSEL_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VESSEL_TYPE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SORT_NUM = 1;
    private static final Integer UPDATED_SORT_NUM = 2;

    @Autowired
    private VesselTypeRepository vesselTypeRepository;


    @Autowired
    private VesselTypeMapper vesselTypeMapper;
    

    @Autowired
    private VesselTypeService vesselTypeService;

    @Autowired
    private VesselTypeQueryService vesselTypeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVesselTypeMockMvc;

    private VesselType vesselType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VesselTypeResource vesselTypeResource = new VesselTypeResource(vesselTypeService, vesselTypeQueryService);
        this.restVesselTypeMockMvc = MockMvcBuilders.standaloneSetup(vesselTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VesselType createEntity(EntityManager em) {
        VesselType vesselType = new VesselType()
            .vesselTypeName(DEFAULT_VESSEL_TYPE_NAME)
            .sortNum(DEFAULT_SORT_NUM);
        return vesselType;
    }

    @Before
    public void initTest() {
        vesselType = createEntity(em);
    }

    @Test
    @Transactional
    public void createVesselType() throws Exception {
        int databaseSizeBeforeCreate = vesselTypeRepository.findAll().size();

        // Create the VesselType
        VesselTypeDTO vesselTypeDTO = vesselTypeMapper.toDto(vesselType);
        restVesselTypeMockMvc.perform(post("/api/vessel-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vesselTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the VesselType in the database
        List<VesselType> vesselTypeList = vesselTypeRepository.findAll();
        assertThat(vesselTypeList).hasSize(databaseSizeBeforeCreate + 1);
        VesselType testVesselType = vesselTypeList.get(vesselTypeList.size() - 1);
        assertThat(testVesselType.getVesselTypeName()).isEqualTo(DEFAULT_VESSEL_TYPE_NAME);
        assertThat(testVesselType.getSortNum()).isEqualTo(DEFAULT_SORT_NUM);
    }

    @Test
    @Transactional
    public void createVesselTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vesselTypeRepository.findAll().size();

        // Create the VesselType with an existing ID
        vesselType.setId(1L);
        VesselTypeDTO vesselTypeDTO = vesselTypeMapper.toDto(vesselType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVesselTypeMockMvc.perform(post("/api/vessel-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vesselTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VesselType in the database
        List<VesselType> vesselTypeList = vesselTypeRepository.findAll();
        assertThat(vesselTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkVesselTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = vesselTypeRepository.findAll().size();
        // set the field null
        vesselType.setVesselTypeName(null);

        // Create the VesselType, which fails.
        VesselTypeDTO vesselTypeDTO = vesselTypeMapper.toDto(vesselType);

        restVesselTypeMockMvc.perform(post("/api/vessel-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vesselTypeDTO)))
            .andExpect(status().isBadRequest());

        List<VesselType> vesselTypeList = vesselTypeRepository.findAll();
        assertThat(vesselTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSortNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = vesselTypeRepository.findAll().size();
        // set the field null
        vesselType.setSortNum(null);

        // Create the VesselType, which fails.
        VesselTypeDTO vesselTypeDTO = vesselTypeMapper.toDto(vesselType);

        restVesselTypeMockMvc.perform(post("/api/vessel-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vesselTypeDTO)))
            .andExpect(status().isBadRequest());

        List<VesselType> vesselTypeList = vesselTypeRepository.findAll();
        assertThat(vesselTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVesselTypes() throws Exception {
        // Initialize the database
        vesselTypeRepository.saveAndFlush(vesselType);

        // Get all the vesselTypeList
        restVesselTypeMockMvc.perform(get("/api/vessel-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vesselType.getId().intValue())))
            .andExpect(jsonPath("$.[*].vesselTypeName").value(hasItem(DEFAULT_VESSEL_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].sortNum").value(hasItem(DEFAULT_SORT_NUM)));
    }
    

    @Test
    @Transactional
    public void getVesselType() throws Exception {
        // Initialize the database
        vesselTypeRepository.saveAndFlush(vesselType);

        // Get the vesselType
        restVesselTypeMockMvc.perform(get("/api/vessel-types/{id}", vesselType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vesselType.getId().intValue()))
            .andExpect(jsonPath("$.vesselTypeName").value(DEFAULT_VESSEL_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.sortNum").value(DEFAULT_SORT_NUM));
    }

    @Test
    @Transactional
    public void getAllVesselTypesByVesselTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselTypeRepository.saveAndFlush(vesselType);

        // Get all the vesselTypeList where vesselTypeName equals to DEFAULT_VESSEL_TYPE_NAME
        defaultVesselTypeShouldBeFound("vesselTypeName.equals=" + DEFAULT_VESSEL_TYPE_NAME);

        // Get all the vesselTypeList where vesselTypeName equals to UPDATED_VESSEL_TYPE_NAME
        defaultVesselTypeShouldNotBeFound("vesselTypeName.equals=" + UPDATED_VESSEL_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllVesselTypesByVesselTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        vesselTypeRepository.saveAndFlush(vesselType);

        // Get all the vesselTypeList where vesselTypeName in DEFAULT_VESSEL_TYPE_NAME or UPDATED_VESSEL_TYPE_NAME
        defaultVesselTypeShouldBeFound("vesselTypeName.in=" + DEFAULT_VESSEL_TYPE_NAME + "," + UPDATED_VESSEL_TYPE_NAME);

        // Get all the vesselTypeList where vesselTypeName equals to UPDATED_VESSEL_TYPE_NAME
        defaultVesselTypeShouldNotBeFound("vesselTypeName.in=" + UPDATED_VESSEL_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllVesselTypesByVesselTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselTypeRepository.saveAndFlush(vesselType);

        // Get all the vesselTypeList where vesselTypeName is not null
        defaultVesselTypeShouldBeFound("vesselTypeName.specified=true");

        // Get all the vesselTypeList where vesselTypeName is null
        defaultVesselTypeShouldNotBeFound("vesselTypeName.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselTypesBySortNumIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselTypeRepository.saveAndFlush(vesselType);

        // Get all the vesselTypeList where sortNum equals to DEFAULT_SORT_NUM
        defaultVesselTypeShouldBeFound("sortNum.equals=" + DEFAULT_SORT_NUM);

        // Get all the vesselTypeList where sortNum equals to UPDATED_SORT_NUM
        defaultVesselTypeShouldNotBeFound("sortNum.equals=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllVesselTypesBySortNumIsInShouldWork() throws Exception {
        // Initialize the database
        vesselTypeRepository.saveAndFlush(vesselType);

        // Get all the vesselTypeList where sortNum in DEFAULT_SORT_NUM or UPDATED_SORT_NUM
        defaultVesselTypeShouldBeFound("sortNum.in=" + DEFAULT_SORT_NUM + "," + UPDATED_SORT_NUM);

        // Get all the vesselTypeList where sortNum equals to UPDATED_SORT_NUM
        defaultVesselTypeShouldNotBeFound("sortNum.in=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllVesselTypesBySortNumIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselTypeRepository.saveAndFlush(vesselType);

        // Get all the vesselTypeList where sortNum is not null
        defaultVesselTypeShouldBeFound("sortNum.specified=true");

        // Get all the vesselTypeList where sortNum is null
        defaultVesselTypeShouldNotBeFound("sortNum.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselTypesBySortNumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vesselTypeRepository.saveAndFlush(vesselType);

        // Get all the vesselTypeList where sortNum greater than or equals to DEFAULT_SORT_NUM
        defaultVesselTypeShouldBeFound("sortNum.greaterOrEqualThan=" + DEFAULT_SORT_NUM);

        // Get all the vesselTypeList where sortNum greater than or equals to UPDATED_SORT_NUM
        defaultVesselTypeShouldNotBeFound("sortNum.greaterOrEqualThan=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllVesselTypesBySortNumIsLessThanSomething() throws Exception {
        // Initialize the database
        vesselTypeRepository.saveAndFlush(vesselType);

        // Get all the vesselTypeList where sortNum less than or equals to DEFAULT_SORT_NUM
        defaultVesselTypeShouldNotBeFound("sortNum.lessThan=" + DEFAULT_SORT_NUM);

        // Get all the vesselTypeList where sortNum less than or equals to UPDATED_SORT_NUM
        defaultVesselTypeShouldBeFound("sortNum.lessThan=" + UPDATED_SORT_NUM);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultVesselTypeShouldBeFound(String filter) throws Exception {
        restVesselTypeMockMvc.perform(get("/api/vessel-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vesselType.getId().intValue())))
            .andExpect(jsonPath("$.[*].vesselTypeName").value(hasItem(DEFAULT_VESSEL_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].sortNum").value(hasItem(DEFAULT_SORT_NUM)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultVesselTypeShouldNotBeFound(String filter) throws Exception {
        restVesselTypeMockMvc.perform(get("/api/vessel-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingVesselType() throws Exception {
        // Get the vesselType
        restVesselTypeMockMvc.perform(get("/api/vessel-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVesselType() throws Exception {
        // Initialize the database
        vesselTypeRepository.saveAndFlush(vesselType);

        int databaseSizeBeforeUpdate = vesselTypeRepository.findAll().size();

        // Update the vesselType
        VesselType updatedVesselType = vesselTypeRepository.findById(vesselType.getId()).get();
        // Disconnect from session so that the updates on updatedVesselType are not directly saved in db
        em.detach(updatedVesselType);
        updatedVesselType
            .vesselTypeName(UPDATED_VESSEL_TYPE_NAME)
            .sortNum(UPDATED_SORT_NUM);
        VesselTypeDTO vesselTypeDTO = vesselTypeMapper.toDto(updatedVesselType);

        restVesselTypeMockMvc.perform(put("/api/vessel-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vesselTypeDTO)))
            .andExpect(status().isOk());

        // Validate the VesselType in the database
        List<VesselType> vesselTypeList = vesselTypeRepository.findAll();
        assertThat(vesselTypeList).hasSize(databaseSizeBeforeUpdate);
        VesselType testVesselType = vesselTypeList.get(vesselTypeList.size() - 1);
        assertThat(testVesselType.getVesselTypeName()).isEqualTo(UPDATED_VESSEL_TYPE_NAME);
        assertThat(testVesselType.getSortNum()).isEqualTo(UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void updateNonExistingVesselType() throws Exception {
        int databaseSizeBeforeUpdate = vesselTypeRepository.findAll().size();

        // Create the VesselType
        VesselTypeDTO vesselTypeDTO = vesselTypeMapper.toDto(vesselType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restVesselTypeMockMvc.perform(put("/api/vessel-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vesselTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VesselType in the database
        List<VesselType> vesselTypeList = vesselTypeRepository.findAll();
        assertThat(vesselTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVesselType() throws Exception {
        // Initialize the database
        vesselTypeRepository.saveAndFlush(vesselType);

        int databaseSizeBeforeDelete = vesselTypeRepository.findAll().size();

        // Get the vesselType
        restVesselTypeMockMvc.perform(delete("/api/vessel-types/{id}", vesselType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VesselType> vesselTypeList = vesselTypeRepository.findAll();
        assertThat(vesselTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VesselType.class);
        VesselType vesselType1 = new VesselType();
        vesselType1.setId(1L);
        VesselType vesselType2 = new VesselType();
        vesselType2.setId(vesselType1.getId());
        assertThat(vesselType1).isEqualTo(vesselType2);
        vesselType2.setId(2L);
        assertThat(vesselType1).isNotEqualTo(vesselType2);
        vesselType1.setId(null);
        assertThat(vesselType1).isNotEqualTo(vesselType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VesselTypeDTO.class);
        VesselTypeDTO vesselTypeDTO1 = new VesselTypeDTO();
        vesselTypeDTO1.setId(1L);
        VesselTypeDTO vesselTypeDTO2 = new VesselTypeDTO();
        assertThat(vesselTypeDTO1).isNotEqualTo(vesselTypeDTO2);
        vesselTypeDTO2.setId(vesselTypeDTO1.getId());
        assertThat(vesselTypeDTO1).isEqualTo(vesselTypeDTO2);
        vesselTypeDTO2.setId(2L);
        assertThat(vesselTypeDTO1).isNotEqualTo(vesselTypeDTO2);
        vesselTypeDTO1.setId(null);
        assertThat(vesselTypeDTO1).isNotEqualTo(vesselTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vesselTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vesselTypeMapper.fromId(null)).isNull();
    }
}

package com.cpi.common.web.rest;

import com.cpi.common.CpicommonApp;

import com.cpi.common.config.SecurityBeanOverrideConfiguration;

import com.cpi.common.domain.VesselNameHistory;
import com.cpi.common.domain.Vessel;
import com.cpi.common.repository.VesselNameHistoryRepository;
import com.cpi.common.service.VesselNameHistoryService;
import com.cpi.common.service.dto.VesselNameHistoryDTO;
import com.cpi.common.service.mapper.VesselNameHistoryMapper;
import com.cpi.common.web.rest.errors.ExceptionTranslator;
import com.cpi.common.service.dto.VesselNameHistoryCriteria;
import com.cpi.common.service.VesselNameHistoryQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.cpi.common.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VesselNameHistoryResource REST controller.
 *
 * @see VesselNameHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, CpicommonApp.class})
public class VesselNameHistoryResourceIntTest {

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_UPDATE_USER = 1L;
    private static final Long UPDATED_UPDATE_USER = 2L;

    private static final String DEFAULT_VESSEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VESSEL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VESSEL_CHINESE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VESSEL_CHINESE_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private VesselNameHistoryRepository vesselNameHistoryRepository;


    @Autowired
    private VesselNameHistoryMapper vesselNameHistoryMapper;
    

    @Autowired
    private VesselNameHistoryService vesselNameHistoryService;

    @Autowired
    private VesselNameHistoryQueryService vesselNameHistoryQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVesselNameHistoryMockMvc;

    private VesselNameHistory vesselNameHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VesselNameHistoryResource vesselNameHistoryResource = new VesselNameHistoryResource(vesselNameHistoryService, vesselNameHistoryQueryService);
        this.restVesselNameHistoryMockMvc = MockMvcBuilders.standaloneSetup(vesselNameHistoryResource)
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
    public static VesselNameHistory createEntity(EntityManager em) {
        VesselNameHistory vesselNameHistory = new VesselNameHistory()
            .updateTime(DEFAULT_UPDATE_TIME)
            .updateUser(DEFAULT_UPDATE_USER)
            .vesselName(DEFAULT_VESSEL_NAME)
            .vesselChineseName(DEFAULT_VESSEL_CHINESE_NAME)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return vesselNameHistory;
    }

    @Before
    public void initTest() {
        vesselNameHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createVesselNameHistory() throws Exception {
        int databaseSizeBeforeCreate = vesselNameHistoryRepository.findAll().size();

        // Create the VesselNameHistory
        VesselNameHistoryDTO vesselNameHistoryDTO = vesselNameHistoryMapper.toDto(vesselNameHistory);
        restVesselNameHistoryMockMvc.perform(post("/api/vessel-name-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vesselNameHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the VesselNameHistory in the database
        List<VesselNameHistory> vesselNameHistoryList = vesselNameHistoryRepository.findAll();
        assertThat(vesselNameHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        VesselNameHistory testVesselNameHistory = vesselNameHistoryList.get(vesselNameHistoryList.size() - 1);
        assertThat(testVesselNameHistory.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testVesselNameHistory.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testVesselNameHistory.getVesselName()).isEqualTo(DEFAULT_VESSEL_NAME);
        assertThat(testVesselNameHistory.getVesselChineseName()).isEqualTo(DEFAULT_VESSEL_CHINESE_NAME);
        assertThat(testVesselNameHistory.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testVesselNameHistory.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createVesselNameHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vesselNameHistoryRepository.findAll().size();

        // Create the VesselNameHistory with an existing ID
        vesselNameHistory.setId(1L);
        VesselNameHistoryDTO vesselNameHistoryDTO = vesselNameHistoryMapper.toDto(vesselNameHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVesselNameHistoryMockMvc.perform(post("/api/vessel-name-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vesselNameHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VesselNameHistory in the database
        List<VesselNameHistory> vesselNameHistoryList = vesselNameHistoryRepository.findAll();
        assertThat(vesselNameHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVesselNameHistories() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        // Get all the vesselNameHistoryList
        restVesselNameHistoryMockMvc.perform(get("/api/vessel-name-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vesselNameHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER.intValue())))
            .andExpect(jsonPath("$.[*].vesselName").value(hasItem(DEFAULT_VESSEL_NAME.toString())))
            .andExpect(jsonPath("$.[*].vesselChineseName").value(hasItem(DEFAULT_VESSEL_CHINESE_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    

    @Test
    @Transactional
    public void getVesselNameHistory() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        // Get the vesselNameHistory
        restVesselNameHistoryMockMvc.perform(get("/api/vessel-name-histories/{id}", vesselNameHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vesselNameHistory.getId().intValue()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER.intValue()))
            .andExpect(jsonPath("$.vesselName").value(DEFAULT_VESSEL_NAME.toString()))
            .andExpect(jsonPath("$.vesselChineseName").value(DEFAULT_VESSEL_CHINESE_NAME.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllVesselNameHistoriesByUpdateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        // Get all the vesselNameHistoryList where updateTime equals to DEFAULT_UPDATE_TIME
        defaultVesselNameHistoryShouldBeFound("updateTime.equals=" + DEFAULT_UPDATE_TIME);

        // Get all the vesselNameHistoryList where updateTime equals to UPDATED_UPDATE_TIME
        defaultVesselNameHistoryShouldNotBeFound("updateTime.equals=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getAllVesselNameHistoriesByUpdateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        // Get all the vesselNameHistoryList where updateTime in DEFAULT_UPDATE_TIME or UPDATED_UPDATE_TIME
        defaultVesselNameHistoryShouldBeFound("updateTime.in=" + DEFAULT_UPDATE_TIME + "," + UPDATED_UPDATE_TIME);

        // Get all the vesselNameHistoryList where updateTime equals to UPDATED_UPDATE_TIME
        defaultVesselNameHistoryShouldNotBeFound("updateTime.in=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getAllVesselNameHistoriesByUpdateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        // Get all the vesselNameHistoryList where updateTime is not null
        defaultVesselNameHistoryShouldBeFound("updateTime.specified=true");

        // Get all the vesselNameHistoryList where updateTime is null
        defaultVesselNameHistoryShouldNotBeFound("updateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselNameHistoriesByUpdateUserIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        // Get all the vesselNameHistoryList where updateUser equals to DEFAULT_UPDATE_USER
        defaultVesselNameHistoryShouldBeFound("updateUser.equals=" + DEFAULT_UPDATE_USER);

        // Get all the vesselNameHistoryList where updateUser equals to UPDATED_UPDATE_USER
        defaultVesselNameHistoryShouldNotBeFound("updateUser.equals=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllVesselNameHistoriesByUpdateUserIsInShouldWork() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        // Get all the vesselNameHistoryList where updateUser in DEFAULT_UPDATE_USER or UPDATED_UPDATE_USER
        defaultVesselNameHistoryShouldBeFound("updateUser.in=" + DEFAULT_UPDATE_USER + "," + UPDATED_UPDATE_USER);

        // Get all the vesselNameHistoryList where updateUser equals to UPDATED_UPDATE_USER
        defaultVesselNameHistoryShouldNotBeFound("updateUser.in=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllVesselNameHistoriesByUpdateUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        // Get all the vesselNameHistoryList where updateUser is not null
        defaultVesselNameHistoryShouldBeFound("updateUser.specified=true");

        // Get all the vesselNameHistoryList where updateUser is null
        defaultVesselNameHistoryShouldNotBeFound("updateUser.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselNameHistoriesByUpdateUserIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        // Get all the vesselNameHistoryList where updateUser greater than or equals to DEFAULT_UPDATE_USER
        defaultVesselNameHistoryShouldBeFound("updateUser.greaterOrEqualThan=" + DEFAULT_UPDATE_USER);

        // Get all the vesselNameHistoryList where updateUser greater than or equals to UPDATED_UPDATE_USER
        defaultVesselNameHistoryShouldNotBeFound("updateUser.greaterOrEqualThan=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllVesselNameHistoriesByUpdateUserIsLessThanSomething() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        // Get all the vesselNameHistoryList where updateUser less than or equals to DEFAULT_UPDATE_USER
        defaultVesselNameHistoryShouldNotBeFound("updateUser.lessThan=" + DEFAULT_UPDATE_USER);

        // Get all the vesselNameHistoryList where updateUser less than or equals to UPDATED_UPDATE_USER
        defaultVesselNameHistoryShouldBeFound("updateUser.lessThan=" + UPDATED_UPDATE_USER);
    }


    @Test
    @Transactional
    public void getAllVesselNameHistoriesByVesselNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        // Get all the vesselNameHistoryList where vesselName equals to DEFAULT_VESSEL_NAME
        defaultVesselNameHistoryShouldBeFound("vesselName.equals=" + DEFAULT_VESSEL_NAME);

        // Get all the vesselNameHistoryList where vesselName equals to UPDATED_VESSEL_NAME
        defaultVesselNameHistoryShouldNotBeFound("vesselName.equals=" + UPDATED_VESSEL_NAME);
    }

    @Test
    @Transactional
    public void getAllVesselNameHistoriesByVesselNameIsInShouldWork() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        // Get all the vesselNameHistoryList where vesselName in DEFAULT_VESSEL_NAME or UPDATED_VESSEL_NAME
        defaultVesselNameHistoryShouldBeFound("vesselName.in=" + DEFAULT_VESSEL_NAME + "," + UPDATED_VESSEL_NAME);

        // Get all the vesselNameHistoryList where vesselName equals to UPDATED_VESSEL_NAME
        defaultVesselNameHistoryShouldNotBeFound("vesselName.in=" + UPDATED_VESSEL_NAME);
    }

    @Test
    @Transactional
    public void getAllVesselNameHistoriesByVesselNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        // Get all the vesselNameHistoryList where vesselName is not null
        defaultVesselNameHistoryShouldBeFound("vesselName.specified=true");

        // Get all the vesselNameHistoryList where vesselName is null
        defaultVesselNameHistoryShouldNotBeFound("vesselName.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselNameHistoriesByVesselChineseNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        // Get all the vesselNameHistoryList where vesselChineseName equals to DEFAULT_VESSEL_CHINESE_NAME
        defaultVesselNameHistoryShouldBeFound("vesselChineseName.equals=" + DEFAULT_VESSEL_CHINESE_NAME);

        // Get all the vesselNameHistoryList where vesselChineseName equals to UPDATED_VESSEL_CHINESE_NAME
        defaultVesselNameHistoryShouldNotBeFound("vesselChineseName.equals=" + UPDATED_VESSEL_CHINESE_NAME);
    }

    @Test
    @Transactional
    public void getAllVesselNameHistoriesByVesselChineseNameIsInShouldWork() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        // Get all the vesselNameHistoryList where vesselChineseName in DEFAULT_VESSEL_CHINESE_NAME or UPDATED_VESSEL_CHINESE_NAME
        defaultVesselNameHistoryShouldBeFound("vesselChineseName.in=" + DEFAULT_VESSEL_CHINESE_NAME + "," + UPDATED_VESSEL_CHINESE_NAME);

        // Get all the vesselNameHistoryList where vesselChineseName equals to UPDATED_VESSEL_CHINESE_NAME
        defaultVesselNameHistoryShouldNotBeFound("vesselChineseName.in=" + UPDATED_VESSEL_CHINESE_NAME);
    }

    @Test
    @Transactional
    public void getAllVesselNameHistoriesByVesselChineseNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        // Get all the vesselNameHistoryList where vesselChineseName is not null
        defaultVesselNameHistoryShouldBeFound("vesselChineseName.specified=true");

        // Get all the vesselNameHistoryList where vesselChineseName is null
        defaultVesselNameHistoryShouldNotBeFound("vesselChineseName.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselNameHistoriesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        // Get all the vesselNameHistoryList where startDate equals to DEFAULT_START_DATE
        defaultVesselNameHistoryShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the vesselNameHistoryList where startDate equals to UPDATED_START_DATE
        defaultVesselNameHistoryShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllVesselNameHistoriesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        // Get all the vesselNameHistoryList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultVesselNameHistoryShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the vesselNameHistoryList where startDate equals to UPDATED_START_DATE
        defaultVesselNameHistoryShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllVesselNameHistoriesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        // Get all the vesselNameHistoryList where startDate is not null
        defaultVesselNameHistoryShouldBeFound("startDate.specified=true");

        // Get all the vesselNameHistoryList where startDate is null
        defaultVesselNameHistoryShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselNameHistoriesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        // Get all the vesselNameHistoryList where endDate equals to DEFAULT_END_DATE
        defaultVesselNameHistoryShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the vesselNameHistoryList where endDate equals to UPDATED_END_DATE
        defaultVesselNameHistoryShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllVesselNameHistoriesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        // Get all the vesselNameHistoryList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultVesselNameHistoryShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the vesselNameHistoryList where endDate equals to UPDATED_END_DATE
        defaultVesselNameHistoryShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllVesselNameHistoriesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        // Get all the vesselNameHistoryList where endDate is not null
        defaultVesselNameHistoryShouldBeFound("endDate.specified=true");

        // Get all the vesselNameHistoryList where endDate is null
        defaultVesselNameHistoryShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselNameHistoriesByVesselIsEqualToSomething() throws Exception {
        // Initialize the database
        Vessel vessel = VesselResourceIntTest.createEntity(em);
        em.persist(vessel);
        em.flush();
        vesselNameHistory.setVessel(vessel);
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);
        Long vesselId = vessel.getId();

        // Get all the vesselNameHistoryList where vessel equals to vesselId
        defaultVesselNameHistoryShouldBeFound("vesselId.equals=" + vesselId);

        // Get all the vesselNameHistoryList where vessel equals to vesselId + 1
        defaultVesselNameHistoryShouldNotBeFound("vesselId.equals=" + (vesselId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultVesselNameHistoryShouldBeFound(String filter) throws Exception {
        restVesselNameHistoryMockMvc.perform(get("/api/vessel-name-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vesselNameHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER.intValue())))
            .andExpect(jsonPath("$.[*].vesselName").value(hasItem(DEFAULT_VESSEL_NAME.toString())))
            .andExpect(jsonPath("$.[*].vesselChineseName").value(hasItem(DEFAULT_VESSEL_CHINESE_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultVesselNameHistoryShouldNotBeFound(String filter) throws Exception {
        restVesselNameHistoryMockMvc.perform(get("/api/vessel-name-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingVesselNameHistory() throws Exception {
        // Get the vesselNameHistory
        restVesselNameHistoryMockMvc.perform(get("/api/vessel-name-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVesselNameHistory() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        int databaseSizeBeforeUpdate = vesselNameHistoryRepository.findAll().size();

        // Update the vesselNameHistory
        VesselNameHistory updatedVesselNameHistory = vesselNameHistoryRepository.findById(vesselNameHistory.getId()).get();
        // Disconnect from session so that the updates on updatedVesselNameHistory are not directly saved in db
        em.detach(updatedVesselNameHistory);
        updatedVesselNameHistory
            .updateTime(UPDATED_UPDATE_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .vesselName(UPDATED_VESSEL_NAME)
            .vesselChineseName(UPDATED_VESSEL_CHINESE_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        VesselNameHistoryDTO vesselNameHistoryDTO = vesselNameHistoryMapper.toDto(updatedVesselNameHistory);

        restVesselNameHistoryMockMvc.perform(put("/api/vessel-name-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vesselNameHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the VesselNameHistory in the database
        List<VesselNameHistory> vesselNameHistoryList = vesselNameHistoryRepository.findAll();
        assertThat(vesselNameHistoryList).hasSize(databaseSizeBeforeUpdate);
        VesselNameHistory testVesselNameHistory = vesselNameHistoryList.get(vesselNameHistoryList.size() - 1);
        assertThat(testVesselNameHistory.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testVesselNameHistory.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testVesselNameHistory.getVesselName()).isEqualTo(UPDATED_VESSEL_NAME);
        assertThat(testVesselNameHistory.getVesselChineseName()).isEqualTo(UPDATED_VESSEL_CHINESE_NAME);
        assertThat(testVesselNameHistory.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testVesselNameHistory.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingVesselNameHistory() throws Exception {
        int databaseSizeBeforeUpdate = vesselNameHistoryRepository.findAll().size();

        // Create the VesselNameHistory
        VesselNameHistoryDTO vesselNameHistoryDTO = vesselNameHistoryMapper.toDto(vesselNameHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restVesselNameHistoryMockMvc.perform(put("/api/vessel-name-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vesselNameHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VesselNameHistory in the database
        List<VesselNameHistory> vesselNameHistoryList = vesselNameHistoryRepository.findAll();
        assertThat(vesselNameHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVesselNameHistory() throws Exception {
        // Initialize the database
        vesselNameHistoryRepository.saveAndFlush(vesselNameHistory);

        int databaseSizeBeforeDelete = vesselNameHistoryRepository.findAll().size();

        // Get the vesselNameHistory
        restVesselNameHistoryMockMvc.perform(delete("/api/vessel-name-histories/{id}", vesselNameHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VesselNameHistory> vesselNameHistoryList = vesselNameHistoryRepository.findAll();
        assertThat(vesselNameHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VesselNameHistory.class);
        VesselNameHistory vesselNameHistory1 = new VesselNameHistory();
        vesselNameHistory1.setId(1L);
        VesselNameHistory vesselNameHistory2 = new VesselNameHistory();
        vesselNameHistory2.setId(vesselNameHistory1.getId());
        assertThat(vesselNameHistory1).isEqualTo(vesselNameHistory2);
        vesselNameHistory2.setId(2L);
        assertThat(vesselNameHistory1).isNotEqualTo(vesselNameHistory2);
        vesselNameHistory1.setId(null);
        assertThat(vesselNameHistory1).isNotEqualTo(vesselNameHistory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VesselNameHistoryDTO.class);
        VesselNameHistoryDTO vesselNameHistoryDTO1 = new VesselNameHistoryDTO();
        vesselNameHistoryDTO1.setId(1L);
        VesselNameHistoryDTO vesselNameHistoryDTO2 = new VesselNameHistoryDTO();
        assertThat(vesselNameHistoryDTO1).isNotEqualTo(vesselNameHistoryDTO2);
        vesselNameHistoryDTO2.setId(vesselNameHistoryDTO1.getId());
        assertThat(vesselNameHistoryDTO1).isEqualTo(vesselNameHistoryDTO2);
        vesselNameHistoryDTO2.setId(2L);
        assertThat(vesselNameHistoryDTO1).isNotEqualTo(vesselNameHistoryDTO2);
        vesselNameHistoryDTO1.setId(null);
        assertThat(vesselNameHistoryDTO1).isNotEqualTo(vesselNameHistoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vesselNameHistoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vesselNameHistoryMapper.fromId(null)).isNull();
    }
}

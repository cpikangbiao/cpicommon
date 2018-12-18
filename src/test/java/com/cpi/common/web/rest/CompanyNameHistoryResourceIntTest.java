package com.cpi.common.web.rest;

import com.cpi.common.CpicommonApp;

import com.cpi.common.config.SecurityBeanOverrideConfiguration;

import com.cpi.common.domain.CompanyNameHistory;
import com.cpi.common.domain.Company;
import com.cpi.common.repository.CompanyNameHistoryRepository;
import com.cpi.common.service.CompanyNameHistoryService;
import com.cpi.common.service.dto.CompanyNameHistoryDTO;
import com.cpi.common.service.mapper.CompanyNameHistoryMapper;
import com.cpi.common.web.rest.errors.ExceptionTranslator;
import com.cpi.common.service.dto.CompanyNameHistoryCriteria;
import com.cpi.common.service.CompanyNameHistoryQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.cpi.common.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CompanyNameHistoryResource REST controller.
 *
 * @see CompanyNameHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, CpicommonApp.class})
public class CompanyNameHistoryResourceIntTest {

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_UPDATE_USER = 1L;
    private static final Long UPDATED_UPDATE_USER = 2L;

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_CHINESE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CHINESE_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CompanyNameHistoryRepository companyNameHistoryRepository;

    @Autowired
    private CompanyNameHistoryMapper companyNameHistoryMapper;

    @Autowired
    private CompanyNameHistoryService companyNameHistoryService;

    @Autowired
    private CompanyNameHistoryQueryService companyNameHistoryQueryService;

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

    private MockMvc restCompanyNameHistoryMockMvc;

    private CompanyNameHistory companyNameHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyNameHistoryResource companyNameHistoryResource = new CompanyNameHistoryResource(companyNameHistoryService, companyNameHistoryQueryService);
        this.restCompanyNameHistoryMockMvc = MockMvcBuilders.standaloneSetup(companyNameHistoryResource)
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
    public static CompanyNameHistory createEntity(EntityManager em) {
        CompanyNameHistory companyNameHistory = new CompanyNameHistory()
            .updateTime(DEFAULT_UPDATE_TIME)
            .updateUser(DEFAULT_UPDATE_USER)
            .companyName(DEFAULT_COMPANY_NAME)
            .companyChineseName(DEFAULT_COMPANY_CHINESE_NAME)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return companyNameHistory;
    }

    @Before
    public void initTest() {
        companyNameHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyNameHistory() throws Exception {
        int databaseSizeBeforeCreate = companyNameHistoryRepository.findAll().size();

        // Create the CompanyNameHistory
        CompanyNameHistoryDTO companyNameHistoryDTO = companyNameHistoryMapper.toDto(companyNameHistory);
        restCompanyNameHistoryMockMvc.perform(post("/api/company-name-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyNameHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyNameHistory in the database
        List<CompanyNameHistory> companyNameHistoryList = companyNameHistoryRepository.findAll();
        assertThat(companyNameHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyNameHistory testCompanyNameHistory = companyNameHistoryList.get(companyNameHistoryList.size() - 1);
        assertThat(testCompanyNameHistory.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testCompanyNameHistory.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testCompanyNameHistory.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testCompanyNameHistory.getCompanyChineseName()).isEqualTo(DEFAULT_COMPANY_CHINESE_NAME);
        assertThat(testCompanyNameHistory.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testCompanyNameHistory.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createCompanyNameHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyNameHistoryRepository.findAll().size();

        // Create the CompanyNameHistory with an existing ID
        companyNameHistory.setId(1L);
        CompanyNameHistoryDTO companyNameHistoryDTO = companyNameHistoryMapper.toDto(companyNameHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyNameHistoryMockMvc.perform(post("/api/company-name-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyNameHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyNameHistory in the database
        List<CompanyNameHistory> companyNameHistoryList = companyNameHistoryRepository.findAll();
        assertThat(companyNameHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanyNameHistories() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        // Get all the companyNameHistoryList
        restCompanyNameHistoryMockMvc.perform(get("/api/company-name-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyNameHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER.intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].companyChineseName").value(hasItem(DEFAULT_COMPANY_CHINESE_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getCompanyNameHistory() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        // Get the companyNameHistory
        restCompanyNameHistoryMockMvc.perform(get("/api/company-name-histories/{id}", companyNameHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyNameHistory.getId().intValue()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER.intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.companyChineseName").value(DEFAULT_COMPANY_CHINESE_NAME.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllCompanyNameHistoriesByUpdateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        // Get all the companyNameHistoryList where updateTime equals to DEFAULT_UPDATE_TIME
        defaultCompanyNameHistoryShouldBeFound("updateTime.equals=" + DEFAULT_UPDATE_TIME);

        // Get all the companyNameHistoryList where updateTime equals to UPDATED_UPDATE_TIME
        defaultCompanyNameHistoryShouldNotBeFound("updateTime.equals=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCompanyNameHistoriesByUpdateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        // Get all the companyNameHistoryList where updateTime in DEFAULT_UPDATE_TIME or UPDATED_UPDATE_TIME
        defaultCompanyNameHistoryShouldBeFound("updateTime.in=" + DEFAULT_UPDATE_TIME + "," + UPDATED_UPDATE_TIME);

        // Get all the companyNameHistoryList where updateTime equals to UPDATED_UPDATE_TIME
        defaultCompanyNameHistoryShouldNotBeFound("updateTime.in=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCompanyNameHistoriesByUpdateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        // Get all the companyNameHistoryList where updateTime is not null
        defaultCompanyNameHistoryShouldBeFound("updateTime.specified=true");

        // Get all the companyNameHistoryList where updateTime is null
        defaultCompanyNameHistoryShouldNotBeFound("updateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompanyNameHistoriesByUpdateUserIsEqualToSomething() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        // Get all the companyNameHistoryList where updateUser equals to DEFAULT_UPDATE_USER
        defaultCompanyNameHistoryShouldBeFound("updateUser.equals=" + DEFAULT_UPDATE_USER);

        // Get all the companyNameHistoryList where updateUser equals to UPDATED_UPDATE_USER
        defaultCompanyNameHistoryShouldNotBeFound("updateUser.equals=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllCompanyNameHistoriesByUpdateUserIsInShouldWork() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        // Get all the companyNameHistoryList where updateUser in DEFAULT_UPDATE_USER or UPDATED_UPDATE_USER
        defaultCompanyNameHistoryShouldBeFound("updateUser.in=" + DEFAULT_UPDATE_USER + "," + UPDATED_UPDATE_USER);

        // Get all the companyNameHistoryList where updateUser equals to UPDATED_UPDATE_USER
        defaultCompanyNameHistoryShouldNotBeFound("updateUser.in=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllCompanyNameHistoriesByUpdateUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        // Get all the companyNameHistoryList where updateUser is not null
        defaultCompanyNameHistoryShouldBeFound("updateUser.specified=true");

        // Get all the companyNameHistoryList where updateUser is null
        defaultCompanyNameHistoryShouldNotBeFound("updateUser.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompanyNameHistoriesByUpdateUserIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        // Get all the companyNameHistoryList where updateUser greater than or equals to DEFAULT_UPDATE_USER
        defaultCompanyNameHistoryShouldBeFound("updateUser.greaterOrEqualThan=" + DEFAULT_UPDATE_USER);

        // Get all the companyNameHistoryList where updateUser greater than or equals to UPDATED_UPDATE_USER
        defaultCompanyNameHistoryShouldNotBeFound("updateUser.greaterOrEqualThan=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllCompanyNameHistoriesByUpdateUserIsLessThanSomething() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        // Get all the companyNameHistoryList where updateUser less than or equals to DEFAULT_UPDATE_USER
        defaultCompanyNameHistoryShouldNotBeFound("updateUser.lessThan=" + DEFAULT_UPDATE_USER);

        // Get all the companyNameHistoryList where updateUser less than or equals to UPDATED_UPDATE_USER
        defaultCompanyNameHistoryShouldBeFound("updateUser.lessThan=" + UPDATED_UPDATE_USER);
    }


    @Test
    @Transactional
    public void getAllCompanyNameHistoriesByCompanyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        // Get all the companyNameHistoryList where companyName equals to DEFAULT_COMPANY_NAME
        defaultCompanyNameHistoryShouldBeFound("companyName.equals=" + DEFAULT_COMPANY_NAME);

        // Get all the companyNameHistoryList where companyName equals to UPDATED_COMPANY_NAME
        defaultCompanyNameHistoryShouldNotBeFound("companyName.equals=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyNameHistoriesByCompanyNameIsInShouldWork() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        // Get all the companyNameHistoryList where companyName in DEFAULT_COMPANY_NAME or UPDATED_COMPANY_NAME
        defaultCompanyNameHistoryShouldBeFound("companyName.in=" + DEFAULT_COMPANY_NAME + "," + UPDATED_COMPANY_NAME);

        // Get all the companyNameHistoryList where companyName equals to UPDATED_COMPANY_NAME
        defaultCompanyNameHistoryShouldNotBeFound("companyName.in=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyNameHistoriesByCompanyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        // Get all the companyNameHistoryList where companyName is not null
        defaultCompanyNameHistoryShouldBeFound("companyName.specified=true");

        // Get all the companyNameHistoryList where companyName is null
        defaultCompanyNameHistoryShouldNotBeFound("companyName.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompanyNameHistoriesByCompanyChineseNameIsEqualToSomething() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        // Get all the companyNameHistoryList where companyChineseName equals to DEFAULT_COMPANY_CHINESE_NAME
        defaultCompanyNameHistoryShouldBeFound("companyChineseName.equals=" + DEFAULT_COMPANY_CHINESE_NAME);

        // Get all the companyNameHistoryList where companyChineseName equals to UPDATED_COMPANY_CHINESE_NAME
        defaultCompanyNameHistoryShouldNotBeFound("companyChineseName.equals=" + UPDATED_COMPANY_CHINESE_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyNameHistoriesByCompanyChineseNameIsInShouldWork() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        // Get all the companyNameHistoryList where companyChineseName in DEFAULT_COMPANY_CHINESE_NAME or UPDATED_COMPANY_CHINESE_NAME
        defaultCompanyNameHistoryShouldBeFound("companyChineseName.in=" + DEFAULT_COMPANY_CHINESE_NAME + "," + UPDATED_COMPANY_CHINESE_NAME);

        // Get all the companyNameHistoryList where companyChineseName equals to UPDATED_COMPANY_CHINESE_NAME
        defaultCompanyNameHistoryShouldNotBeFound("companyChineseName.in=" + UPDATED_COMPANY_CHINESE_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyNameHistoriesByCompanyChineseNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        // Get all the companyNameHistoryList where companyChineseName is not null
        defaultCompanyNameHistoryShouldBeFound("companyChineseName.specified=true");

        // Get all the companyNameHistoryList where companyChineseName is null
        defaultCompanyNameHistoryShouldNotBeFound("companyChineseName.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompanyNameHistoriesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        // Get all the companyNameHistoryList where startDate equals to DEFAULT_START_DATE
        defaultCompanyNameHistoryShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the companyNameHistoryList where startDate equals to UPDATED_START_DATE
        defaultCompanyNameHistoryShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllCompanyNameHistoriesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        // Get all the companyNameHistoryList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultCompanyNameHistoryShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the companyNameHistoryList where startDate equals to UPDATED_START_DATE
        defaultCompanyNameHistoryShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllCompanyNameHistoriesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        // Get all the companyNameHistoryList where startDate is not null
        defaultCompanyNameHistoryShouldBeFound("startDate.specified=true");

        // Get all the companyNameHistoryList where startDate is null
        defaultCompanyNameHistoryShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompanyNameHistoriesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        // Get all the companyNameHistoryList where endDate equals to DEFAULT_END_DATE
        defaultCompanyNameHistoryShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the companyNameHistoryList where endDate equals to UPDATED_END_DATE
        defaultCompanyNameHistoryShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllCompanyNameHistoriesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        // Get all the companyNameHistoryList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultCompanyNameHistoryShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the companyNameHistoryList where endDate equals to UPDATED_END_DATE
        defaultCompanyNameHistoryShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllCompanyNameHistoriesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        // Get all the companyNameHistoryList where endDate is not null
        defaultCompanyNameHistoryShouldBeFound("endDate.specified=true");

        // Get all the companyNameHistoryList where endDate is null
        defaultCompanyNameHistoryShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompanyNameHistoriesByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        Company company = CompanyResourceIntTest.createEntity(em);
        em.persist(company);
        em.flush();
        companyNameHistory.setCompany(company);
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);
        Long companyId = company.getId();

        // Get all the companyNameHistoryList where company equals to companyId
        defaultCompanyNameHistoryShouldBeFound("companyId.equals=" + companyId);

        // Get all the companyNameHistoryList where company equals to companyId + 1
        defaultCompanyNameHistoryShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCompanyNameHistoryShouldBeFound(String filter) throws Exception {
        restCompanyNameHistoryMockMvc.perform(get("/api/company-name-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyNameHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER.intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].companyChineseName").value(hasItem(DEFAULT_COMPANY_CHINESE_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));

        // Check, that the count call also returns 1
        restCompanyNameHistoryMockMvc.perform(get("/api/company-name-histories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCompanyNameHistoryShouldNotBeFound(String filter) throws Exception {
        restCompanyNameHistoryMockMvc.perform(get("/api/company-name-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompanyNameHistoryMockMvc.perform(get("/api/company-name-histories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCompanyNameHistory() throws Exception {
        // Get the companyNameHistory
        restCompanyNameHistoryMockMvc.perform(get("/api/company-name-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyNameHistory() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        int databaseSizeBeforeUpdate = companyNameHistoryRepository.findAll().size();

        // Update the companyNameHistory
        CompanyNameHistory updatedCompanyNameHistory = companyNameHistoryRepository.findById(companyNameHistory.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyNameHistory are not directly saved in db
        em.detach(updatedCompanyNameHistory);
        updatedCompanyNameHistory
            .updateTime(UPDATED_UPDATE_TIME)
            .updateUser(UPDATED_UPDATE_USER)
            .companyName(UPDATED_COMPANY_NAME)
            .companyChineseName(UPDATED_COMPANY_CHINESE_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        CompanyNameHistoryDTO companyNameHistoryDTO = companyNameHistoryMapper.toDto(updatedCompanyNameHistory);

        restCompanyNameHistoryMockMvc.perform(put("/api/company-name-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyNameHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the CompanyNameHistory in the database
        List<CompanyNameHistory> companyNameHistoryList = companyNameHistoryRepository.findAll();
        assertThat(companyNameHistoryList).hasSize(databaseSizeBeforeUpdate);
        CompanyNameHistory testCompanyNameHistory = companyNameHistoryList.get(companyNameHistoryList.size() - 1);
        assertThat(testCompanyNameHistory.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testCompanyNameHistory.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testCompanyNameHistory.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCompanyNameHistory.getCompanyChineseName()).isEqualTo(UPDATED_COMPANY_CHINESE_NAME);
        assertThat(testCompanyNameHistory.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testCompanyNameHistory.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyNameHistory() throws Exception {
        int databaseSizeBeforeUpdate = companyNameHistoryRepository.findAll().size();

        // Create the CompanyNameHistory
        CompanyNameHistoryDTO companyNameHistoryDTO = companyNameHistoryMapper.toDto(companyNameHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyNameHistoryMockMvc.perform(put("/api/company-name-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyNameHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyNameHistory in the database
        List<CompanyNameHistory> companyNameHistoryList = companyNameHistoryRepository.findAll();
        assertThat(companyNameHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompanyNameHistory() throws Exception {
        // Initialize the database
        companyNameHistoryRepository.saveAndFlush(companyNameHistory);

        int databaseSizeBeforeDelete = companyNameHistoryRepository.findAll().size();

        // Get the companyNameHistory
        restCompanyNameHistoryMockMvc.perform(delete("/api/company-name-histories/{id}", companyNameHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyNameHistory> companyNameHistoryList = companyNameHistoryRepository.findAll();
        assertThat(companyNameHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyNameHistory.class);
        CompanyNameHistory companyNameHistory1 = new CompanyNameHistory();
        companyNameHistory1.setId(1L);
        CompanyNameHistory companyNameHistory2 = new CompanyNameHistory();
        companyNameHistory2.setId(companyNameHistory1.getId());
        assertThat(companyNameHistory1).isEqualTo(companyNameHistory2);
        companyNameHistory2.setId(2L);
        assertThat(companyNameHistory1).isNotEqualTo(companyNameHistory2);
        companyNameHistory1.setId(null);
        assertThat(companyNameHistory1).isNotEqualTo(companyNameHistory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyNameHistoryDTO.class);
        CompanyNameHistoryDTO companyNameHistoryDTO1 = new CompanyNameHistoryDTO();
        companyNameHistoryDTO1.setId(1L);
        CompanyNameHistoryDTO companyNameHistoryDTO2 = new CompanyNameHistoryDTO();
        assertThat(companyNameHistoryDTO1).isNotEqualTo(companyNameHistoryDTO2);
        companyNameHistoryDTO2.setId(companyNameHistoryDTO1.getId());
        assertThat(companyNameHistoryDTO1).isEqualTo(companyNameHistoryDTO2);
        companyNameHistoryDTO2.setId(2L);
        assertThat(companyNameHistoryDTO1).isNotEqualTo(companyNameHistoryDTO2);
        companyNameHistoryDTO1.setId(null);
        assertThat(companyNameHistoryDTO1).isNotEqualTo(companyNameHistoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(companyNameHistoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(companyNameHistoryMapper.fromId(null)).isNull();
    }
}

package com.cpi.common.web.rest;

import com.cpi.common.CpicommonApp;

import com.cpi.common.config.SecurityBeanOverrideConfiguration;

import com.cpi.common.domain.CurrencyRate;
import com.cpi.common.domain.Currency;
import com.cpi.common.repository.CurrencyRateRepository;
import com.cpi.common.service.CurrencyRateService;
import com.cpi.common.service.dto.CurrencyRateDTO;
import com.cpi.common.service.mapper.CurrencyRateMapper;
import com.cpi.common.web.rest.errors.ExceptionTranslator;
import com.cpi.common.service.dto.CurrencyRateCriteria;
import com.cpi.common.service.CurrencyRateQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.cpi.common.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CurrencyRateResource REST controller.
 *
 * @see CurrencyRateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, CpicommonApp.class})
public class CurrencyRateResourceIntTest {

    private static final LocalDate DEFAULT_RATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_CURRENCY_RATE = 1D;
    private static final Double UPDATED_CURRENCY_RATE = 2D;

    @Autowired
    private CurrencyRateRepository currencyRateRepository;


    @Autowired
    private CurrencyRateMapper currencyRateMapper;
    

    @Autowired
    private CurrencyRateService currencyRateService;

    @Autowired
    private CurrencyRateQueryService currencyRateQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCurrencyRateMockMvc;

    private CurrencyRate currencyRate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CurrencyRateResource currencyRateResource = new CurrencyRateResource(currencyRateService, currencyRateQueryService);
        this.restCurrencyRateMockMvc = MockMvcBuilders.standaloneSetup(currencyRateResource)
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
    public static CurrencyRate createEntity(EntityManager em) {
        CurrencyRate currencyRate = new CurrencyRate()
            .rateDate(DEFAULT_RATE_DATE)
            .currencyRate(DEFAULT_CURRENCY_RATE);
        return currencyRate;
    }

    @Before
    public void initTest() {
        currencyRate = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurrencyRate() throws Exception {
        int databaseSizeBeforeCreate = currencyRateRepository.findAll().size();

        // Create the CurrencyRate
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(currencyRate);
        restCurrencyRateMockMvc.perform(post("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isCreated());

        // Validate the CurrencyRate in the database
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeCreate + 1);
        CurrencyRate testCurrencyRate = currencyRateList.get(currencyRateList.size() - 1);
        assertThat(testCurrencyRate.getRateDate()).isEqualTo(DEFAULT_RATE_DATE);
        assertThat(testCurrencyRate.getCurrencyRate()).isEqualTo(DEFAULT_CURRENCY_RATE);
    }

    @Test
    @Transactional
    public void createCurrencyRateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = currencyRateRepository.findAll().size();

        // Create the CurrencyRate with an existing ID
        currencyRate.setId(1L);
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(currencyRate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrencyRateMockMvc.perform(post("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CurrencyRate in the database
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRateRepository.findAll().size();
        // set the field null
        currencyRate.setRateDate(null);

        // Create the CurrencyRate, which fails.
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(currencyRate);

        restCurrencyRateMockMvc.perform(post("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isBadRequest());

        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrencyRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRateRepository.findAll().size();
        // set the field null
        currencyRate.setCurrencyRate(null);

        // Create the CurrencyRate, which fails.
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(currencyRate);

        restCurrencyRateMockMvc.perform(post("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isBadRequest());

        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCurrencyRates() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList
        restCurrencyRateMockMvc.perform(get("/api/currency-rates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currencyRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].rateDate").value(hasItem(DEFAULT_RATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].currencyRate").value(hasItem(DEFAULT_CURRENCY_RATE.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getCurrencyRate() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get the currencyRate
        restCurrencyRateMockMvc.perform(get("/api/currency-rates/{id}", currencyRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(currencyRate.getId().intValue()))
            .andExpect(jsonPath("$.rateDate").value(DEFAULT_RATE_DATE.toString()))
            .andExpect(jsonPath("$.currencyRate").value(DEFAULT_CURRENCY_RATE.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByRateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where rateDate equals to DEFAULT_RATE_DATE
        defaultCurrencyRateShouldBeFound("rateDate.equals=" + DEFAULT_RATE_DATE);

        // Get all the currencyRateList where rateDate equals to UPDATED_RATE_DATE
        defaultCurrencyRateShouldNotBeFound("rateDate.equals=" + UPDATED_RATE_DATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByRateDateIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where rateDate in DEFAULT_RATE_DATE or UPDATED_RATE_DATE
        defaultCurrencyRateShouldBeFound("rateDate.in=" + DEFAULT_RATE_DATE + "," + UPDATED_RATE_DATE);

        // Get all the currencyRateList where rateDate equals to UPDATED_RATE_DATE
        defaultCurrencyRateShouldNotBeFound("rateDate.in=" + UPDATED_RATE_DATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByRateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where rateDate is not null
        defaultCurrencyRateShouldBeFound("rateDate.specified=true");

        // Get all the currencyRateList where rateDate is null
        defaultCurrencyRateShouldNotBeFound("rateDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByRateDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where rateDate greater than or equals to DEFAULT_RATE_DATE
        defaultCurrencyRateShouldBeFound("rateDate.greaterOrEqualThan=" + DEFAULT_RATE_DATE);

        // Get all the currencyRateList where rateDate greater than or equals to UPDATED_RATE_DATE
        defaultCurrencyRateShouldNotBeFound("rateDate.greaterOrEqualThan=" + UPDATED_RATE_DATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByRateDateIsLessThanSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where rateDate less than or equals to DEFAULT_RATE_DATE
        defaultCurrencyRateShouldNotBeFound("rateDate.lessThan=" + DEFAULT_RATE_DATE);

        // Get all the currencyRateList where rateDate less than or equals to UPDATED_RATE_DATE
        defaultCurrencyRateShouldBeFound("rateDate.lessThan=" + UPDATED_RATE_DATE);
    }


    @Test
    @Transactional
    public void getAllCurrencyRatesByCurrencyRateIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where currencyRate equals to DEFAULT_CURRENCY_RATE
        defaultCurrencyRateShouldBeFound("currencyRate.equals=" + DEFAULT_CURRENCY_RATE);

        // Get all the currencyRateList where currencyRate equals to UPDATED_CURRENCY_RATE
        defaultCurrencyRateShouldNotBeFound("currencyRate.equals=" + UPDATED_CURRENCY_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByCurrencyRateIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where currencyRate in DEFAULT_CURRENCY_RATE or UPDATED_CURRENCY_RATE
        defaultCurrencyRateShouldBeFound("currencyRate.in=" + DEFAULT_CURRENCY_RATE + "," + UPDATED_CURRENCY_RATE);

        // Get all the currencyRateList where currencyRate equals to UPDATED_CURRENCY_RATE
        defaultCurrencyRateShouldNotBeFound("currencyRate.in=" + UPDATED_CURRENCY_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByCurrencyRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where currencyRate is not null
        defaultCurrencyRateShouldBeFound("currencyRate.specified=true");

        // Get all the currencyRateList where currencyRate is null
        defaultCurrencyRateShouldNotBeFound("currencyRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        Currency currency = CurrencyResourceIntTest.createEntity(em);
        em.persist(currency);
        em.flush();
        currencyRate.setCurrency(currency);
        currencyRateRepository.saveAndFlush(currencyRate);
        Long currencyId = currency.getId();

        // Get all the currencyRateList where currency equals to currencyId
        defaultCurrencyRateShouldBeFound("currencyId.equals=" + currencyId);

        // Get all the currencyRateList where currency equals to currencyId + 1
        defaultCurrencyRateShouldNotBeFound("currencyId.equals=" + (currencyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCurrencyRateShouldBeFound(String filter) throws Exception {
        restCurrencyRateMockMvc.perform(get("/api/currency-rates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currencyRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].rateDate").value(hasItem(DEFAULT_RATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].currencyRate").value(hasItem(DEFAULT_CURRENCY_RATE.doubleValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCurrencyRateShouldNotBeFound(String filter) throws Exception {
        restCurrencyRateMockMvc.perform(get("/api/currency-rates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingCurrencyRate() throws Exception {
        // Get the currencyRate
        restCurrencyRateMockMvc.perform(get("/api/currency-rates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurrencyRate() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        int databaseSizeBeforeUpdate = currencyRateRepository.findAll().size();

        // Update the currencyRate
        CurrencyRate updatedCurrencyRate = currencyRateRepository.findById(currencyRate.getId()).get();
        // Disconnect from session so that the updates on updatedCurrencyRate are not directly saved in db
        em.detach(updatedCurrencyRate);
        updatedCurrencyRate
            .rateDate(UPDATED_RATE_DATE)
            .currencyRate(UPDATED_CURRENCY_RATE);
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(updatedCurrencyRate);

        restCurrencyRateMockMvc.perform(put("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isOk());

        // Validate the CurrencyRate in the database
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeUpdate);
        CurrencyRate testCurrencyRate = currencyRateList.get(currencyRateList.size() - 1);
        assertThat(testCurrencyRate.getRateDate()).isEqualTo(UPDATED_RATE_DATE);
        assertThat(testCurrencyRate.getCurrencyRate()).isEqualTo(UPDATED_CURRENCY_RATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCurrencyRate() throws Exception {
        int databaseSizeBeforeUpdate = currencyRateRepository.findAll().size();

        // Create the CurrencyRate
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(currencyRate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restCurrencyRateMockMvc.perform(put("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CurrencyRate in the database
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCurrencyRate() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        int databaseSizeBeforeDelete = currencyRateRepository.findAll().size();

        // Get the currencyRate
        restCurrencyRateMockMvc.perform(delete("/api/currency-rates/{id}", currencyRate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrencyRate.class);
        CurrencyRate currencyRate1 = new CurrencyRate();
        currencyRate1.setId(1L);
        CurrencyRate currencyRate2 = new CurrencyRate();
        currencyRate2.setId(currencyRate1.getId());
        assertThat(currencyRate1).isEqualTo(currencyRate2);
        currencyRate2.setId(2L);
        assertThat(currencyRate1).isNotEqualTo(currencyRate2);
        currencyRate1.setId(null);
        assertThat(currencyRate1).isNotEqualTo(currencyRate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrencyRateDTO.class);
        CurrencyRateDTO currencyRateDTO1 = new CurrencyRateDTO();
        currencyRateDTO1.setId(1L);
        CurrencyRateDTO currencyRateDTO2 = new CurrencyRateDTO();
        assertThat(currencyRateDTO1).isNotEqualTo(currencyRateDTO2);
        currencyRateDTO2.setId(currencyRateDTO1.getId());
        assertThat(currencyRateDTO1).isEqualTo(currencyRateDTO2);
        currencyRateDTO2.setId(2L);
        assertThat(currencyRateDTO1).isNotEqualTo(currencyRateDTO2);
        currencyRateDTO1.setId(null);
        assertThat(currencyRateDTO1).isNotEqualTo(currencyRateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(currencyRateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(currencyRateMapper.fromId(null)).isNull();
    }
}

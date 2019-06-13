package com.cpi.common.web.rest;

import com.cpi.common.CpicommonApp;
import com.cpi.common.config.SecurityBeanOverrideConfiguration;
import com.cpi.common.domain.Currency;
import com.cpi.common.repository.CurrencyRepository;
import com.cpi.common.service.CurrencyService;
import com.cpi.common.service.dto.CurrencyDTO;
import com.cpi.common.service.mapper.CurrencyMapper;
import com.cpi.common.web.rest.errors.ExceptionTranslator;
import com.cpi.common.service.dto.CurrencyCriteria;
import com.cpi.common.service.CurrencyQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
 * Integration tests for the {@Link CurrencyResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, CpicommonApp.class})
public class CurrencyResourceIT {

    private static final String DEFAULT_FULL_NAME_ENGLISH = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME_ENGLISH = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME_CHINESE = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME_CHINESE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_NAME_ENGLISH = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME_ENGLISH = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_NAME_CHINESE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME_CHINESE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_ABBRE = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ABBRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CURRENCY_SORT = 1;
    private static final Integer UPDATED_CURRENCY_SORT = 2;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyMapper currencyMapper;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyQueryService currencyQueryService;

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

    private MockMvc restCurrencyMockMvc;

    private Currency currency;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CurrencyResource currencyResource = new CurrencyResource(currencyService, currencyQueryService);
        this.restCurrencyMockMvc = MockMvcBuilders.standaloneSetup(currencyResource)
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
    public static Currency createEntity(EntityManager em) {
        Currency currency = new Currency()
            .fullNameEnglish(DEFAULT_FULL_NAME_ENGLISH)
            .fullNameChinese(DEFAULT_FULL_NAME_CHINESE)
            .countryNameEnglish(DEFAULT_COUNTRY_NAME_ENGLISH)
            .countryNameChinese(DEFAULT_COUNTRY_NAME_CHINESE)
            .nameAbbre(DEFAULT_NAME_ABBRE)
            .currencySort(DEFAULT_CURRENCY_SORT);
        return currency;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Currency createUpdatedEntity(EntityManager em) {
        Currency currency = new Currency()
            .fullNameEnglish(UPDATED_FULL_NAME_ENGLISH)
            .fullNameChinese(UPDATED_FULL_NAME_CHINESE)
            .countryNameEnglish(UPDATED_COUNTRY_NAME_ENGLISH)
            .countryNameChinese(UPDATED_COUNTRY_NAME_CHINESE)
            .nameAbbre(UPDATED_NAME_ABBRE)
            .currencySort(UPDATED_CURRENCY_SORT);
        return currency;
    }

    @BeforeEach
    public void initTest() {
        currency = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurrency() throws Exception {
        int databaseSizeBeforeCreate = currencyRepository.findAll().size();

        // Create the Currency
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);
        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isCreated());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeCreate + 1);
        Currency testCurrency = currencyList.get(currencyList.size() - 1);
        assertThat(testCurrency.getFullNameEnglish()).isEqualTo(DEFAULT_FULL_NAME_ENGLISH);
        assertThat(testCurrency.getFullNameChinese()).isEqualTo(DEFAULT_FULL_NAME_CHINESE);
        assertThat(testCurrency.getCountryNameEnglish()).isEqualTo(DEFAULT_COUNTRY_NAME_ENGLISH);
        assertThat(testCurrency.getCountryNameChinese()).isEqualTo(DEFAULT_COUNTRY_NAME_CHINESE);
        assertThat(testCurrency.getNameAbbre()).isEqualTo(DEFAULT_NAME_ABBRE);
        assertThat(testCurrency.getCurrencySort()).isEqualTo(DEFAULT_CURRENCY_SORT);
    }

    @Test
    @Transactional
    public void createCurrencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = currencyRepository.findAll().size();

        // Create the Currency with an existing ID
        currency.setId(1L);
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFullNameEnglishIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRepository.findAll().size();
        // set the field null
        currency.setFullNameEnglish(null);

        // Create the Currency, which fails.
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);

        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isBadRequest());

        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryNameEnglishIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRepository.findAll().size();
        // set the field null
        currency.setCountryNameEnglish(null);

        // Create the Currency, which fails.
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);

        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isBadRequest());

        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameAbbreIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRepository.findAll().size();
        // set the field null
        currency.setNameAbbre(null);

        // Create the Currency, which fails.
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);

        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isBadRequest());

        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCurrencies() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList
        restCurrencyMockMvc.perform(get("/api/currencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currency.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullNameEnglish").value(hasItem(DEFAULT_FULL_NAME_ENGLISH.toString())))
            .andExpect(jsonPath("$.[*].fullNameChinese").value(hasItem(DEFAULT_FULL_NAME_CHINESE.toString())))
            .andExpect(jsonPath("$.[*].countryNameEnglish").value(hasItem(DEFAULT_COUNTRY_NAME_ENGLISH.toString())))
            .andExpect(jsonPath("$.[*].countryNameChinese").value(hasItem(DEFAULT_COUNTRY_NAME_CHINESE.toString())))
            .andExpect(jsonPath("$.[*].nameAbbre").value(hasItem(DEFAULT_NAME_ABBRE.toString())))
            .andExpect(jsonPath("$.[*].currencySort").value(hasItem(DEFAULT_CURRENCY_SORT)));
    }
    
    @Test
    @Transactional
    public void getCurrency() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get the currency
        restCurrencyMockMvc.perform(get("/api/currencies/{id}", currency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(currency.getId().intValue()))
            .andExpect(jsonPath("$.fullNameEnglish").value(DEFAULT_FULL_NAME_ENGLISH.toString()))
            .andExpect(jsonPath("$.fullNameChinese").value(DEFAULT_FULL_NAME_CHINESE.toString()))
            .andExpect(jsonPath("$.countryNameEnglish").value(DEFAULT_COUNTRY_NAME_ENGLISH.toString()))
            .andExpect(jsonPath("$.countryNameChinese").value(DEFAULT_COUNTRY_NAME_CHINESE.toString()))
            .andExpect(jsonPath("$.nameAbbre").value(DEFAULT_NAME_ABBRE.toString()))
            .andExpect(jsonPath("$.currencySort").value(DEFAULT_CURRENCY_SORT));
    }

    @Test
    @Transactional
    public void getAllCurrenciesByFullNameEnglishIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where fullNameEnglish equals to DEFAULT_FULL_NAME_ENGLISH
        defaultCurrencyShouldBeFound("fullNameEnglish.equals=" + DEFAULT_FULL_NAME_ENGLISH);

        // Get all the currencyList where fullNameEnglish equals to UPDATED_FULL_NAME_ENGLISH
        defaultCurrencyShouldNotBeFound("fullNameEnglish.equals=" + UPDATED_FULL_NAME_ENGLISH);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByFullNameEnglishIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where fullNameEnglish in DEFAULT_FULL_NAME_ENGLISH or UPDATED_FULL_NAME_ENGLISH
        defaultCurrencyShouldBeFound("fullNameEnglish.in=" + DEFAULT_FULL_NAME_ENGLISH + "," + UPDATED_FULL_NAME_ENGLISH);

        // Get all the currencyList where fullNameEnglish equals to UPDATED_FULL_NAME_ENGLISH
        defaultCurrencyShouldNotBeFound("fullNameEnglish.in=" + UPDATED_FULL_NAME_ENGLISH);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByFullNameEnglishIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where fullNameEnglish is not null
        defaultCurrencyShouldBeFound("fullNameEnglish.specified=true");

        // Get all the currencyList where fullNameEnglish is null
        defaultCurrencyShouldNotBeFound("fullNameEnglish.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurrenciesByFullNameChineseIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where fullNameChinese equals to DEFAULT_FULL_NAME_CHINESE
        defaultCurrencyShouldBeFound("fullNameChinese.equals=" + DEFAULT_FULL_NAME_CHINESE);

        // Get all the currencyList where fullNameChinese equals to UPDATED_FULL_NAME_CHINESE
        defaultCurrencyShouldNotBeFound("fullNameChinese.equals=" + UPDATED_FULL_NAME_CHINESE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByFullNameChineseIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where fullNameChinese in DEFAULT_FULL_NAME_CHINESE or UPDATED_FULL_NAME_CHINESE
        defaultCurrencyShouldBeFound("fullNameChinese.in=" + DEFAULT_FULL_NAME_CHINESE + "," + UPDATED_FULL_NAME_CHINESE);

        // Get all the currencyList where fullNameChinese equals to UPDATED_FULL_NAME_CHINESE
        defaultCurrencyShouldNotBeFound("fullNameChinese.in=" + UPDATED_FULL_NAME_CHINESE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByFullNameChineseIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where fullNameChinese is not null
        defaultCurrencyShouldBeFound("fullNameChinese.specified=true");

        // Get all the currencyList where fullNameChinese is null
        defaultCurrencyShouldNotBeFound("fullNameChinese.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCountryNameEnglishIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where countryNameEnglish equals to DEFAULT_COUNTRY_NAME_ENGLISH
        defaultCurrencyShouldBeFound("countryNameEnglish.equals=" + DEFAULT_COUNTRY_NAME_ENGLISH);

        // Get all the currencyList where countryNameEnglish equals to UPDATED_COUNTRY_NAME_ENGLISH
        defaultCurrencyShouldNotBeFound("countryNameEnglish.equals=" + UPDATED_COUNTRY_NAME_ENGLISH);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCountryNameEnglishIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where countryNameEnglish in DEFAULT_COUNTRY_NAME_ENGLISH or UPDATED_COUNTRY_NAME_ENGLISH
        defaultCurrencyShouldBeFound("countryNameEnglish.in=" + DEFAULT_COUNTRY_NAME_ENGLISH + "," + UPDATED_COUNTRY_NAME_ENGLISH);

        // Get all the currencyList where countryNameEnglish equals to UPDATED_COUNTRY_NAME_ENGLISH
        defaultCurrencyShouldNotBeFound("countryNameEnglish.in=" + UPDATED_COUNTRY_NAME_ENGLISH);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCountryNameEnglishIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where countryNameEnglish is not null
        defaultCurrencyShouldBeFound("countryNameEnglish.specified=true");

        // Get all the currencyList where countryNameEnglish is null
        defaultCurrencyShouldNotBeFound("countryNameEnglish.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCountryNameChineseIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where countryNameChinese equals to DEFAULT_COUNTRY_NAME_CHINESE
        defaultCurrencyShouldBeFound("countryNameChinese.equals=" + DEFAULT_COUNTRY_NAME_CHINESE);

        // Get all the currencyList where countryNameChinese equals to UPDATED_COUNTRY_NAME_CHINESE
        defaultCurrencyShouldNotBeFound("countryNameChinese.equals=" + UPDATED_COUNTRY_NAME_CHINESE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCountryNameChineseIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where countryNameChinese in DEFAULT_COUNTRY_NAME_CHINESE or UPDATED_COUNTRY_NAME_CHINESE
        defaultCurrencyShouldBeFound("countryNameChinese.in=" + DEFAULT_COUNTRY_NAME_CHINESE + "," + UPDATED_COUNTRY_NAME_CHINESE);

        // Get all the currencyList where countryNameChinese equals to UPDATED_COUNTRY_NAME_CHINESE
        defaultCurrencyShouldNotBeFound("countryNameChinese.in=" + UPDATED_COUNTRY_NAME_CHINESE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCountryNameChineseIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where countryNameChinese is not null
        defaultCurrencyShouldBeFound("countryNameChinese.specified=true");

        // Get all the currencyList where countryNameChinese is null
        defaultCurrencyShouldNotBeFound("countryNameChinese.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurrenciesByNameAbbreIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where nameAbbre equals to DEFAULT_NAME_ABBRE
        defaultCurrencyShouldBeFound("nameAbbre.equals=" + DEFAULT_NAME_ABBRE);

        // Get all the currencyList where nameAbbre equals to UPDATED_NAME_ABBRE
        defaultCurrencyShouldNotBeFound("nameAbbre.equals=" + UPDATED_NAME_ABBRE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByNameAbbreIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where nameAbbre in DEFAULT_NAME_ABBRE or UPDATED_NAME_ABBRE
        defaultCurrencyShouldBeFound("nameAbbre.in=" + DEFAULT_NAME_ABBRE + "," + UPDATED_NAME_ABBRE);

        // Get all the currencyList where nameAbbre equals to UPDATED_NAME_ABBRE
        defaultCurrencyShouldNotBeFound("nameAbbre.in=" + UPDATED_NAME_ABBRE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByNameAbbreIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where nameAbbre is not null
        defaultCurrencyShouldBeFound("nameAbbre.specified=true");

        // Get all the currencyList where nameAbbre is null
        defaultCurrencyShouldNotBeFound("nameAbbre.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencySortIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencySort equals to DEFAULT_CURRENCY_SORT
        defaultCurrencyShouldBeFound("currencySort.equals=" + DEFAULT_CURRENCY_SORT);

        // Get all the currencyList where currencySort equals to UPDATED_CURRENCY_SORT
        defaultCurrencyShouldNotBeFound("currencySort.equals=" + UPDATED_CURRENCY_SORT);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencySortIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencySort in DEFAULT_CURRENCY_SORT or UPDATED_CURRENCY_SORT
        defaultCurrencyShouldBeFound("currencySort.in=" + DEFAULT_CURRENCY_SORT + "," + UPDATED_CURRENCY_SORT);

        // Get all the currencyList where currencySort equals to UPDATED_CURRENCY_SORT
        defaultCurrencyShouldNotBeFound("currencySort.in=" + UPDATED_CURRENCY_SORT);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencySortIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencySort is not null
        defaultCurrencyShouldBeFound("currencySort.specified=true");

        // Get all the currencyList where currencySort is null
        defaultCurrencyShouldNotBeFound("currencySort.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencySortIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencySort greater than or equals to DEFAULT_CURRENCY_SORT
        defaultCurrencyShouldBeFound("currencySort.greaterOrEqualThan=" + DEFAULT_CURRENCY_SORT);

        // Get all the currencyList where currencySort greater than or equals to UPDATED_CURRENCY_SORT
        defaultCurrencyShouldNotBeFound("currencySort.greaterOrEqualThan=" + UPDATED_CURRENCY_SORT);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencySortIsLessThanSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencySort less than or equals to DEFAULT_CURRENCY_SORT
        defaultCurrencyShouldNotBeFound("currencySort.lessThan=" + DEFAULT_CURRENCY_SORT);

        // Get all the currencyList where currencySort less than or equals to UPDATED_CURRENCY_SORT
        defaultCurrencyShouldBeFound("currencySort.lessThan=" + UPDATED_CURRENCY_SORT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCurrencyShouldBeFound(String filter) throws Exception {
        restCurrencyMockMvc.perform(get("/api/currencies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currency.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullNameEnglish").value(hasItem(DEFAULT_FULL_NAME_ENGLISH)))
            .andExpect(jsonPath("$.[*].fullNameChinese").value(hasItem(DEFAULT_FULL_NAME_CHINESE)))
            .andExpect(jsonPath("$.[*].countryNameEnglish").value(hasItem(DEFAULT_COUNTRY_NAME_ENGLISH)))
            .andExpect(jsonPath("$.[*].countryNameChinese").value(hasItem(DEFAULT_COUNTRY_NAME_CHINESE)))
            .andExpect(jsonPath("$.[*].nameAbbre").value(hasItem(DEFAULT_NAME_ABBRE)))
            .andExpect(jsonPath("$.[*].currencySort").value(hasItem(DEFAULT_CURRENCY_SORT)));

        // Check, that the count call also returns 1
        restCurrencyMockMvc.perform(get("/api/currencies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCurrencyShouldNotBeFound(String filter) throws Exception {
        restCurrencyMockMvc.perform(get("/api/currencies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCurrencyMockMvc.perform(get("/api/currencies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCurrency() throws Exception {
        // Get the currency
        restCurrencyMockMvc.perform(get("/api/currencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurrency() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();

        // Update the currency
        Currency updatedCurrency = currencyRepository.findById(currency.getId()).get();
        // Disconnect from session so that the updates on updatedCurrency are not directly saved in db
        em.detach(updatedCurrency);
        updatedCurrency
            .fullNameEnglish(UPDATED_FULL_NAME_ENGLISH)
            .fullNameChinese(UPDATED_FULL_NAME_CHINESE)
            .countryNameEnglish(UPDATED_COUNTRY_NAME_ENGLISH)
            .countryNameChinese(UPDATED_COUNTRY_NAME_CHINESE)
            .nameAbbre(UPDATED_NAME_ABBRE)
            .currencySort(UPDATED_CURRENCY_SORT);
        CurrencyDTO currencyDTO = currencyMapper.toDto(updatedCurrency);

        restCurrencyMockMvc.perform(put("/api/currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isOk());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeUpdate);
        Currency testCurrency = currencyList.get(currencyList.size() - 1);
        assertThat(testCurrency.getFullNameEnglish()).isEqualTo(UPDATED_FULL_NAME_ENGLISH);
        assertThat(testCurrency.getFullNameChinese()).isEqualTo(UPDATED_FULL_NAME_CHINESE);
        assertThat(testCurrency.getCountryNameEnglish()).isEqualTo(UPDATED_COUNTRY_NAME_ENGLISH);
        assertThat(testCurrency.getCountryNameChinese()).isEqualTo(UPDATED_COUNTRY_NAME_CHINESE);
        assertThat(testCurrency.getNameAbbre()).isEqualTo(UPDATED_NAME_ABBRE);
        assertThat(testCurrency.getCurrencySort()).isEqualTo(UPDATED_CURRENCY_SORT);
    }

    @Test
    @Transactional
    public void updateNonExistingCurrency() throws Exception {
        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();

        // Create the Currency
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrencyMockMvc.perform(put("/api/currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCurrency() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        int databaseSizeBeforeDelete = currencyRepository.findAll().size();

        // Delete the currency
        restCurrencyMockMvc.perform(delete("/api/currencies/{id}", currency.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Currency.class);
        Currency currency1 = new Currency();
        currency1.setId(1L);
        Currency currency2 = new Currency();
        currency2.setId(currency1.getId());
        assertThat(currency1).isEqualTo(currency2);
        currency2.setId(2L);
        assertThat(currency1).isNotEqualTo(currency2);
        currency1.setId(null);
        assertThat(currency1).isNotEqualTo(currency2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrencyDTO.class);
        CurrencyDTO currencyDTO1 = new CurrencyDTO();
        currencyDTO1.setId(1L);
        CurrencyDTO currencyDTO2 = new CurrencyDTO();
        assertThat(currencyDTO1).isNotEqualTo(currencyDTO2);
        currencyDTO2.setId(currencyDTO1.getId());
        assertThat(currencyDTO1).isEqualTo(currencyDTO2);
        currencyDTO2.setId(2L);
        assertThat(currencyDTO1).isNotEqualTo(currencyDTO2);
        currencyDTO1.setId(null);
        assertThat(currencyDTO1).isNotEqualTo(currencyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(currencyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(currencyMapper.fromId(null)).isNull();
    }
}

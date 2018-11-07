package com.cpi.common.web.rest;

import com.cpi.common.CpicommonApp;

import com.cpi.common.config.SecurityBeanOverrideConfiguration;

import com.cpi.common.domain.Vessel;
import com.cpi.common.domain.Country;
import com.cpi.common.domain.Currency;
import com.cpi.common.domain.VesselType;
import com.cpi.common.domain.Company;
import com.cpi.common.domain.Port;
import com.cpi.common.repository.VesselRepository;
import com.cpi.common.service.VesselService;
import com.cpi.common.service.dto.VesselDTO;
import com.cpi.common.service.mapper.VesselMapper;
import com.cpi.common.web.rest.errors.ExceptionTranslator;
import com.cpi.common.service.dto.VesselCriteria;
import com.cpi.common.service.VesselQueryService;

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
 * Test class for the VesselResource REST controller.
 *
 * @see VesselResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, CpicommonApp.class})
public class VesselResourceIntTest {

    private static final String DEFAULT_VESSEL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_VESSEL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_VESSEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VESSEL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VESSEL_CHINESE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VESSEL_CHINESE_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_GTR = 1D;
    private static final Double UPDATED_GTR = 2D;

    private static final Double DEFAULT_WRT = 1D;
    private static final Double UPDATED_WRT = 2D;

    private static final String DEFAULT_BUILD_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_BUILD_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_BUILD_YEAR = "AAAA";
    private static final String UPDATED_BUILD_YEAR = "BBBB";

    private static final String DEFAULT_FOC = "AAAAAAAAAA";
    private static final String UPDATED_FOC = "BBBBBBBBBB";

    private static final Double DEFAULT_DWT = 1D;
    private static final Double UPDATED_DWT = 2D;

    private static final Double DEFAULT_HULL_HM_AMOUNT = 1D;
    private static final Double UPDATED_HULL_HM_AMOUNT = 2D;

    private static final Double DEFAULT_HULL_HM_VALUE = 1D;
    private static final Double UPDATED_HULL_HM_VALUE = 2D;

    private static final Double DEFAULT_HULL_IV_AMOUNT = 1D;
    private static final Double UPDATED_HULL_IV_AMOUNT = 2D;

    private static final Double DEFAULT_HULL_IV_VALUE = 1D;
    private static final Double UPDATED_HULL_IV_VALUE = 2D;

    private static final Double DEFAULT_HULL_WAR_AMOUNT = 1D;
    private static final Double UPDATED_HULL_WAR_AMOUNT = 2D;

    private static final Double DEFAULT_HULL_WAR_VALUE = 1D;
    private static final Double UPDATED_HULL_WAR_VALUE = 2D;

    private static final String DEFAULT_VESSEL_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_VESSEL_SIZE = "BBBBBBBBBB";

    private static final String DEFAULT_LINE = "AAAAAAAAAA";
    private static final String UPDATED_LINE = "BBBBBBBBBB";

    private static final Double DEFAULT_DEEPER = 1D;
    private static final Double UPDATED_DEEPER = 2D;

    private static final String DEFAULT_CALL_SIGN = "AAAAAAAAAA";
    private static final String UPDATED_CALL_SIGN = "BBBBBBBBBB";

    private static final String DEFAULT_IMO_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_IMO_NUMBER = "BBBBBBBBBB";

    @Autowired
    private VesselRepository vesselRepository;


    @Autowired
    private VesselMapper vesselMapper;
    

    @Autowired
    private VesselService vesselService;

    @Autowired
    private VesselQueryService vesselQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVesselMockMvc;

    private Vessel vessel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VesselResource vesselResource = new VesselResource(vesselService, vesselQueryService);
        this.restVesselMockMvc = MockMvcBuilders.standaloneSetup(vesselResource)
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
    public static Vessel createEntity(EntityManager em) {
        Vessel vessel = new Vessel()
            .vesselCode(DEFAULT_VESSEL_CODE)
            .vesselName(DEFAULT_VESSEL_NAME)
            .vesselChineseName(DEFAULT_VESSEL_CHINESE_NAME)
            .gtr(DEFAULT_GTR)
            .wrt(DEFAULT_WRT)
            .buildLocation(DEFAULT_BUILD_LOCATION)
            .buildYear(DEFAULT_BUILD_YEAR)
            .foc(DEFAULT_FOC)
            .dwt(DEFAULT_DWT)
            .hullHmAmount(DEFAULT_HULL_HM_AMOUNT)
            .hullHmValue(DEFAULT_HULL_HM_VALUE)
            .hullIvAmount(DEFAULT_HULL_IV_AMOUNT)
            .hullIvValue(DEFAULT_HULL_IV_VALUE)
            .hullWarAmount(DEFAULT_HULL_WAR_AMOUNT)
            .hullWarValue(DEFAULT_HULL_WAR_VALUE)
            .vesselSize(DEFAULT_VESSEL_SIZE)
            .line(DEFAULT_LINE)
            .deeper(DEFAULT_DEEPER)
            .callSign(DEFAULT_CALL_SIGN)
            .imoNumber(DEFAULT_IMO_NUMBER);
        return vessel;
    }

    @Before
    public void initTest() {
        vessel = createEntity(em);
    }

    @Test
    @Transactional
    public void createVessel() throws Exception {
        int databaseSizeBeforeCreate = vesselRepository.findAll().size();

        // Create the Vessel
        VesselDTO vesselDTO = vesselMapper.toDto(vessel);
        restVesselMockMvc.perform(post("/api/vessels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vesselDTO)))
            .andExpect(status().isCreated());

        // Validate the Vessel in the database
        List<Vessel> vesselList = vesselRepository.findAll();
        assertThat(vesselList).hasSize(databaseSizeBeforeCreate + 1);
        Vessel testVessel = vesselList.get(vesselList.size() - 1);
        assertThat(testVessel.getVesselCode()).isEqualTo(DEFAULT_VESSEL_CODE);
        assertThat(testVessel.getVesselName()).isEqualTo(DEFAULT_VESSEL_NAME);
        assertThat(testVessel.getVesselChineseName()).isEqualTo(DEFAULT_VESSEL_CHINESE_NAME);
        assertThat(testVessel.getGtr()).isEqualTo(DEFAULT_GTR);
        assertThat(testVessel.getWrt()).isEqualTo(DEFAULT_WRT);
        assertThat(testVessel.getBuildLocation()).isEqualTo(DEFAULT_BUILD_LOCATION);
        assertThat(testVessel.getBuildYear()).isEqualTo(DEFAULT_BUILD_YEAR);
        assertThat(testVessel.getFoc()).isEqualTo(DEFAULT_FOC);
        assertThat(testVessel.getDwt()).isEqualTo(DEFAULT_DWT);
        assertThat(testVessel.getHullHmAmount()).isEqualTo(DEFAULT_HULL_HM_AMOUNT);
        assertThat(testVessel.getHullHmValue()).isEqualTo(DEFAULT_HULL_HM_VALUE);
        assertThat(testVessel.getHullIvAmount()).isEqualTo(DEFAULT_HULL_IV_AMOUNT);
        assertThat(testVessel.getHullIvValue()).isEqualTo(DEFAULT_HULL_IV_VALUE);
        assertThat(testVessel.getHullWarAmount()).isEqualTo(DEFAULT_HULL_WAR_AMOUNT);
        assertThat(testVessel.getHullWarValue()).isEqualTo(DEFAULT_HULL_WAR_VALUE);
        assertThat(testVessel.getVesselSize()).isEqualTo(DEFAULT_VESSEL_SIZE);
        assertThat(testVessel.getLine()).isEqualTo(DEFAULT_LINE);
        assertThat(testVessel.getDeeper()).isEqualTo(DEFAULT_DEEPER);
        assertThat(testVessel.getCallSign()).isEqualTo(DEFAULT_CALL_SIGN);
        assertThat(testVessel.getImoNumber()).isEqualTo(DEFAULT_IMO_NUMBER);
    }

    @Test
    @Transactional
    public void createVesselWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vesselRepository.findAll().size();

        // Create the Vessel with an existing ID
        vessel.setId(1L);
        VesselDTO vesselDTO = vesselMapper.toDto(vessel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVesselMockMvc.perform(post("/api/vessels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vesselDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vessel in the database
        List<Vessel> vesselList = vesselRepository.findAll();
        assertThat(vesselList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkGtrIsRequired() throws Exception {
        int databaseSizeBeforeTest = vesselRepository.findAll().size();
        // set the field null
        vessel.setGtr(null);

        // Create the Vessel, which fails.
        VesselDTO vesselDTO = vesselMapper.toDto(vessel);

        restVesselMockMvc.perform(post("/api/vessels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vesselDTO)))
            .andExpect(status().isBadRequest());

        List<Vessel> vesselList = vesselRepository.findAll();
        assertThat(vesselList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVessels() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList
        restVesselMockMvc.perform(get("/api/vessels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vessel.getId().intValue())))
            .andExpect(jsonPath("$.[*].vesselCode").value(hasItem(DEFAULT_VESSEL_CODE.toString())))
            .andExpect(jsonPath("$.[*].vesselName").value(hasItem(DEFAULT_VESSEL_NAME.toString())))
            .andExpect(jsonPath("$.[*].vesselChineseName").value(hasItem(DEFAULT_VESSEL_CHINESE_NAME.toString())))
            .andExpect(jsonPath("$.[*].gtr").value(hasItem(DEFAULT_GTR.doubleValue())))
            .andExpect(jsonPath("$.[*].wrt").value(hasItem(DEFAULT_WRT.doubleValue())))
            .andExpect(jsonPath("$.[*].buildLocation").value(hasItem(DEFAULT_BUILD_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].buildYear").value(hasItem(DEFAULT_BUILD_YEAR.toString())))
            .andExpect(jsonPath("$.[*].foc").value(hasItem(DEFAULT_FOC.toString())))
            .andExpect(jsonPath("$.[*].dwt").value(hasItem(DEFAULT_DWT.doubleValue())))
            .andExpect(jsonPath("$.[*].hullHmAmount").value(hasItem(DEFAULT_HULL_HM_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].hullHmValue").value(hasItem(DEFAULT_HULL_HM_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].hullIvAmount").value(hasItem(DEFAULT_HULL_IV_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].hullIvValue").value(hasItem(DEFAULT_HULL_IV_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].hullWarAmount").value(hasItem(DEFAULT_HULL_WAR_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].hullWarValue").value(hasItem(DEFAULT_HULL_WAR_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].vesselSize").value(hasItem(DEFAULT_VESSEL_SIZE.toString())))
            .andExpect(jsonPath("$.[*].line").value(hasItem(DEFAULT_LINE.toString())))
            .andExpect(jsonPath("$.[*].deeper").value(hasItem(DEFAULT_DEEPER.doubleValue())))
            .andExpect(jsonPath("$.[*].callSign").value(hasItem(DEFAULT_CALL_SIGN.toString())))
            .andExpect(jsonPath("$.[*].imoNumber").value(hasItem(DEFAULT_IMO_NUMBER.toString())));
    }
    

    @Test
    @Transactional
    public void getVessel() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get the vessel
        restVesselMockMvc.perform(get("/api/vessels/{id}", vessel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vessel.getId().intValue()))
            .andExpect(jsonPath("$.vesselCode").value(DEFAULT_VESSEL_CODE.toString()))
            .andExpect(jsonPath("$.vesselName").value(DEFAULT_VESSEL_NAME.toString()))
            .andExpect(jsonPath("$.vesselChineseName").value(DEFAULT_VESSEL_CHINESE_NAME.toString()))
            .andExpect(jsonPath("$.gtr").value(DEFAULT_GTR.doubleValue()))
            .andExpect(jsonPath("$.wrt").value(DEFAULT_WRT.doubleValue()))
            .andExpect(jsonPath("$.buildLocation").value(DEFAULT_BUILD_LOCATION.toString()))
            .andExpect(jsonPath("$.buildYear").value(DEFAULT_BUILD_YEAR.toString()))
            .andExpect(jsonPath("$.foc").value(DEFAULT_FOC.toString()))
            .andExpect(jsonPath("$.dwt").value(DEFAULT_DWT.doubleValue()))
            .andExpect(jsonPath("$.hullHmAmount").value(DEFAULT_HULL_HM_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.hullHmValue").value(DEFAULT_HULL_HM_VALUE.doubleValue()))
            .andExpect(jsonPath("$.hullIvAmount").value(DEFAULT_HULL_IV_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.hullIvValue").value(DEFAULT_HULL_IV_VALUE.doubleValue()))
            .andExpect(jsonPath("$.hullWarAmount").value(DEFAULT_HULL_WAR_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.hullWarValue").value(DEFAULT_HULL_WAR_VALUE.doubleValue()))
            .andExpect(jsonPath("$.vesselSize").value(DEFAULT_VESSEL_SIZE.toString()))
            .andExpect(jsonPath("$.line").value(DEFAULT_LINE.toString()))
            .andExpect(jsonPath("$.deeper").value(DEFAULT_DEEPER.doubleValue()))
            .andExpect(jsonPath("$.callSign").value(DEFAULT_CALL_SIGN.toString()))
            .andExpect(jsonPath("$.imoNumber").value(DEFAULT_IMO_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getAllVesselsByVesselCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where vesselCode equals to DEFAULT_VESSEL_CODE
        defaultVesselShouldBeFound("vesselCode.equals=" + DEFAULT_VESSEL_CODE);

        // Get all the vesselList where vesselCode equals to UPDATED_VESSEL_CODE
        defaultVesselShouldNotBeFound("vesselCode.equals=" + UPDATED_VESSEL_CODE);
    }

    @Test
    @Transactional
    public void getAllVesselsByVesselCodeIsInShouldWork() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where vesselCode in DEFAULT_VESSEL_CODE or UPDATED_VESSEL_CODE
        defaultVesselShouldBeFound("vesselCode.in=" + DEFAULT_VESSEL_CODE + "," + UPDATED_VESSEL_CODE);

        // Get all the vesselList where vesselCode equals to UPDATED_VESSEL_CODE
        defaultVesselShouldNotBeFound("vesselCode.in=" + UPDATED_VESSEL_CODE);
    }

    @Test
    @Transactional
    public void getAllVesselsByVesselCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where vesselCode is not null
        defaultVesselShouldBeFound("vesselCode.specified=true");

        // Get all the vesselList where vesselCode is null
        defaultVesselShouldNotBeFound("vesselCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselsByVesselNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where vesselName equals to DEFAULT_VESSEL_NAME
        defaultVesselShouldBeFound("vesselName.equals=" + DEFAULT_VESSEL_NAME);

        // Get all the vesselList where vesselName equals to UPDATED_VESSEL_NAME
        defaultVesselShouldNotBeFound("vesselName.equals=" + UPDATED_VESSEL_NAME);
    }

    @Test
    @Transactional
    public void getAllVesselsByVesselNameIsInShouldWork() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where vesselName in DEFAULT_VESSEL_NAME or UPDATED_VESSEL_NAME
        defaultVesselShouldBeFound("vesselName.in=" + DEFAULT_VESSEL_NAME + "," + UPDATED_VESSEL_NAME);

        // Get all the vesselList where vesselName equals to UPDATED_VESSEL_NAME
        defaultVesselShouldNotBeFound("vesselName.in=" + UPDATED_VESSEL_NAME);
    }

    @Test
    @Transactional
    public void getAllVesselsByVesselNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where vesselName is not null
        defaultVesselShouldBeFound("vesselName.specified=true");

        // Get all the vesselList where vesselName is null
        defaultVesselShouldNotBeFound("vesselName.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselsByVesselChineseNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where vesselChineseName equals to DEFAULT_VESSEL_CHINESE_NAME
        defaultVesselShouldBeFound("vesselChineseName.equals=" + DEFAULT_VESSEL_CHINESE_NAME);

        // Get all the vesselList where vesselChineseName equals to UPDATED_VESSEL_CHINESE_NAME
        defaultVesselShouldNotBeFound("vesselChineseName.equals=" + UPDATED_VESSEL_CHINESE_NAME);
    }

    @Test
    @Transactional
    public void getAllVesselsByVesselChineseNameIsInShouldWork() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where vesselChineseName in DEFAULT_VESSEL_CHINESE_NAME or UPDATED_VESSEL_CHINESE_NAME
        defaultVesselShouldBeFound("vesselChineseName.in=" + DEFAULT_VESSEL_CHINESE_NAME + "," + UPDATED_VESSEL_CHINESE_NAME);

        // Get all the vesselList where vesselChineseName equals to UPDATED_VESSEL_CHINESE_NAME
        defaultVesselShouldNotBeFound("vesselChineseName.in=" + UPDATED_VESSEL_CHINESE_NAME);
    }

    @Test
    @Transactional
    public void getAllVesselsByVesselChineseNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where vesselChineseName is not null
        defaultVesselShouldBeFound("vesselChineseName.specified=true");

        // Get all the vesselList where vesselChineseName is null
        defaultVesselShouldNotBeFound("vesselChineseName.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselsByGtrIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where gtr equals to DEFAULT_GTR
        defaultVesselShouldBeFound("gtr.equals=" + DEFAULT_GTR);

        // Get all the vesselList where gtr equals to UPDATED_GTR
        defaultVesselShouldNotBeFound("gtr.equals=" + UPDATED_GTR);
    }

    @Test
    @Transactional
    public void getAllVesselsByGtrIsInShouldWork() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where gtr in DEFAULT_GTR or UPDATED_GTR
        defaultVesselShouldBeFound("gtr.in=" + DEFAULT_GTR + "," + UPDATED_GTR);

        // Get all the vesselList where gtr equals to UPDATED_GTR
        defaultVesselShouldNotBeFound("gtr.in=" + UPDATED_GTR);
    }

    @Test
    @Transactional
    public void getAllVesselsByGtrIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where gtr is not null
        defaultVesselShouldBeFound("gtr.specified=true");

        // Get all the vesselList where gtr is null
        defaultVesselShouldNotBeFound("gtr.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselsByWrtIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where wrt equals to DEFAULT_WRT
        defaultVesselShouldBeFound("wrt.equals=" + DEFAULT_WRT);

        // Get all the vesselList where wrt equals to UPDATED_WRT
        defaultVesselShouldNotBeFound("wrt.equals=" + UPDATED_WRT);
    }

    @Test
    @Transactional
    public void getAllVesselsByWrtIsInShouldWork() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where wrt in DEFAULT_WRT or UPDATED_WRT
        defaultVesselShouldBeFound("wrt.in=" + DEFAULT_WRT + "," + UPDATED_WRT);

        // Get all the vesselList where wrt equals to UPDATED_WRT
        defaultVesselShouldNotBeFound("wrt.in=" + UPDATED_WRT);
    }

    @Test
    @Transactional
    public void getAllVesselsByWrtIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where wrt is not null
        defaultVesselShouldBeFound("wrt.specified=true");

        // Get all the vesselList where wrt is null
        defaultVesselShouldNotBeFound("wrt.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselsByBuildLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where buildLocation equals to DEFAULT_BUILD_LOCATION
        defaultVesselShouldBeFound("buildLocation.equals=" + DEFAULT_BUILD_LOCATION);

        // Get all the vesselList where buildLocation equals to UPDATED_BUILD_LOCATION
        defaultVesselShouldNotBeFound("buildLocation.equals=" + UPDATED_BUILD_LOCATION);
    }

    @Test
    @Transactional
    public void getAllVesselsByBuildLocationIsInShouldWork() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where buildLocation in DEFAULT_BUILD_LOCATION or UPDATED_BUILD_LOCATION
        defaultVesselShouldBeFound("buildLocation.in=" + DEFAULT_BUILD_LOCATION + "," + UPDATED_BUILD_LOCATION);

        // Get all the vesselList where buildLocation equals to UPDATED_BUILD_LOCATION
        defaultVesselShouldNotBeFound("buildLocation.in=" + UPDATED_BUILD_LOCATION);
    }

    @Test
    @Transactional
    public void getAllVesselsByBuildLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where buildLocation is not null
        defaultVesselShouldBeFound("buildLocation.specified=true");

        // Get all the vesselList where buildLocation is null
        defaultVesselShouldNotBeFound("buildLocation.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselsByBuildYearIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where buildYear equals to DEFAULT_BUILD_YEAR
        defaultVesselShouldBeFound("buildYear.equals=" + DEFAULT_BUILD_YEAR);

        // Get all the vesselList where buildYear equals to UPDATED_BUILD_YEAR
        defaultVesselShouldNotBeFound("buildYear.equals=" + UPDATED_BUILD_YEAR);
    }

    @Test
    @Transactional
    public void getAllVesselsByBuildYearIsInShouldWork() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where buildYear in DEFAULT_BUILD_YEAR or UPDATED_BUILD_YEAR
        defaultVesselShouldBeFound("buildYear.in=" + DEFAULT_BUILD_YEAR + "," + UPDATED_BUILD_YEAR);

        // Get all the vesselList where buildYear equals to UPDATED_BUILD_YEAR
        defaultVesselShouldNotBeFound("buildYear.in=" + UPDATED_BUILD_YEAR);
    }

    @Test
    @Transactional
    public void getAllVesselsByBuildYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where buildYear is not null
        defaultVesselShouldBeFound("buildYear.specified=true");

        // Get all the vesselList where buildYear is null
        defaultVesselShouldNotBeFound("buildYear.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselsByFocIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where foc equals to DEFAULT_FOC
        defaultVesselShouldBeFound("foc.equals=" + DEFAULT_FOC);

        // Get all the vesselList where foc equals to UPDATED_FOC
        defaultVesselShouldNotBeFound("foc.equals=" + UPDATED_FOC);
    }

    @Test
    @Transactional
    public void getAllVesselsByFocIsInShouldWork() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where foc in DEFAULT_FOC or UPDATED_FOC
        defaultVesselShouldBeFound("foc.in=" + DEFAULT_FOC + "," + UPDATED_FOC);

        // Get all the vesselList where foc equals to UPDATED_FOC
        defaultVesselShouldNotBeFound("foc.in=" + UPDATED_FOC);
    }

    @Test
    @Transactional
    public void getAllVesselsByFocIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where foc is not null
        defaultVesselShouldBeFound("foc.specified=true");

        // Get all the vesselList where foc is null
        defaultVesselShouldNotBeFound("foc.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselsByDwtIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where dwt equals to DEFAULT_DWT
        defaultVesselShouldBeFound("dwt.equals=" + DEFAULT_DWT);

        // Get all the vesselList where dwt equals to UPDATED_DWT
        defaultVesselShouldNotBeFound("dwt.equals=" + UPDATED_DWT);
    }

    @Test
    @Transactional
    public void getAllVesselsByDwtIsInShouldWork() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where dwt in DEFAULT_DWT or UPDATED_DWT
        defaultVesselShouldBeFound("dwt.in=" + DEFAULT_DWT + "," + UPDATED_DWT);

        // Get all the vesselList where dwt equals to UPDATED_DWT
        defaultVesselShouldNotBeFound("dwt.in=" + UPDATED_DWT);
    }

    @Test
    @Transactional
    public void getAllVesselsByDwtIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where dwt is not null
        defaultVesselShouldBeFound("dwt.specified=true");

        // Get all the vesselList where dwt is null
        defaultVesselShouldNotBeFound("dwt.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselsByHullHmAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where hullHmAmount equals to DEFAULT_HULL_HM_AMOUNT
        defaultVesselShouldBeFound("hullHmAmount.equals=" + DEFAULT_HULL_HM_AMOUNT);

        // Get all the vesselList where hullHmAmount equals to UPDATED_HULL_HM_AMOUNT
        defaultVesselShouldNotBeFound("hullHmAmount.equals=" + UPDATED_HULL_HM_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllVesselsByHullHmAmountIsInShouldWork() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where hullHmAmount in DEFAULT_HULL_HM_AMOUNT or UPDATED_HULL_HM_AMOUNT
        defaultVesselShouldBeFound("hullHmAmount.in=" + DEFAULT_HULL_HM_AMOUNT + "," + UPDATED_HULL_HM_AMOUNT);

        // Get all the vesselList where hullHmAmount equals to UPDATED_HULL_HM_AMOUNT
        defaultVesselShouldNotBeFound("hullHmAmount.in=" + UPDATED_HULL_HM_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllVesselsByHullHmAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where hullHmAmount is not null
        defaultVesselShouldBeFound("hullHmAmount.specified=true");

        // Get all the vesselList where hullHmAmount is null
        defaultVesselShouldNotBeFound("hullHmAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselsByHullHmValueIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where hullHmValue equals to DEFAULT_HULL_HM_VALUE
        defaultVesselShouldBeFound("hullHmValue.equals=" + DEFAULT_HULL_HM_VALUE);

        // Get all the vesselList where hullHmValue equals to UPDATED_HULL_HM_VALUE
        defaultVesselShouldNotBeFound("hullHmValue.equals=" + UPDATED_HULL_HM_VALUE);
    }

    @Test
    @Transactional
    public void getAllVesselsByHullHmValueIsInShouldWork() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where hullHmValue in DEFAULT_HULL_HM_VALUE or UPDATED_HULL_HM_VALUE
        defaultVesselShouldBeFound("hullHmValue.in=" + DEFAULT_HULL_HM_VALUE + "," + UPDATED_HULL_HM_VALUE);

        // Get all the vesselList where hullHmValue equals to UPDATED_HULL_HM_VALUE
        defaultVesselShouldNotBeFound("hullHmValue.in=" + UPDATED_HULL_HM_VALUE);
    }

    @Test
    @Transactional
    public void getAllVesselsByHullHmValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where hullHmValue is not null
        defaultVesselShouldBeFound("hullHmValue.specified=true");

        // Get all the vesselList where hullHmValue is null
        defaultVesselShouldNotBeFound("hullHmValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselsByHullIvAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where hullIvAmount equals to DEFAULT_HULL_IV_AMOUNT
        defaultVesselShouldBeFound("hullIvAmount.equals=" + DEFAULT_HULL_IV_AMOUNT);

        // Get all the vesselList where hullIvAmount equals to UPDATED_HULL_IV_AMOUNT
        defaultVesselShouldNotBeFound("hullIvAmount.equals=" + UPDATED_HULL_IV_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllVesselsByHullIvAmountIsInShouldWork() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where hullIvAmount in DEFAULT_HULL_IV_AMOUNT or UPDATED_HULL_IV_AMOUNT
        defaultVesselShouldBeFound("hullIvAmount.in=" + DEFAULT_HULL_IV_AMOUNT + "," + UPDATED_HULL_IV_AMOUNT);

        // Get all the vesselList where hullIvAmount equals to UPDATED_HULL_IV_AMOUNT
        defaultVesselShouldNotBeFound("hullIvAmount.in=" + UPDATED_HULL_IV_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllVesselsByHullIvAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where hullIvAmount is not null
        defaultVesselShouldBeFound("hullIvAmount.specified=true");

        // Get all the vesselList where hullIvAmount is null
        defaultVesselShouldNotBeFound("hullIvAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselsByHullIvValueIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where hullIvValue equals to DEFAULT_HULL_IV_VALUE
        defaultVesselShouldBeFound("hullIvValue.equals=" + DEFAULT_HULL_IV_VALUE);

        // Get all the vesselList where hullIvValue equals to UPDATED_HULL_IV_VALUE
        defaultVesselShouldNotBeFound("hullIvValue.equals=" + UPDATED_HULL_IV_VALUE);
    }

    @Test
    @Transactional
    public void getAllVesselsByHullIvValueIsInShouldWork() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where hullIvValue in DEFAULT_HULL_IV_VALUE or UPDATED_HULL_IV_VALUE
        defaultVesselShouldBeFound("hullIvValue.in=" + DEFAULT_HULL_IV_VALUE + "," + UPDATED_HULL_IV_VALUE);

        // Get all the vesselList where hullIvValue equals to UPDATED_HULL_IV_VALUE
        defaultVesselShouldNotBeFound("hullIvValue.in=" + UPDATED_HULL_IV_VALUE);
    }

    @Test
    @Transactional
    public void getAllVesselsByHullIvValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where hullIvValue is not null
        defaultVesselShouldBeFound("hullIvValue.specified=true");

        // Get all the vesselList where hullIvValue is null
        defaultVesselShouldNotBeFound("hullIvValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselsByHullWarAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where hullWarAmount equals to DEFAULT_HULL_WAR_AMOUNT
        defaultVesselShouldBeFound("hullWarAmount.equals=" + DEFAULT_HULL_WAR_AMOUNT);

        // Get all the vesselList where hullWarAmount equals to UPDATED_HULL_WAR_AMOUNT
        defaultVesselShouldNotBeFound("hullWarAmount.equals=" + UPDATED_HULL_WAR_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllVesselsByHullWarAmountIsInShouldWork() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where hullWarAmount in DEFAULT_HULL_WAR_AMOUNT or UPDATED_HULL_WAR_AMOUNT
        defaultVesselShouldBeFound("hullWarAmount.in=" + DEFAULT_HULL_WAR_AMOUNT + "," + UPDATED_HULL_WAR_AMOUNT);

        // Get all the vesselList where hullWarAmount equals to UPDATED_HULL_WAR_AMOUNT
        defaultVesselShouldNotBeFound("hullWarAmount.in=" + UPDATED_HULL_WAR_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllVesselsByHullWarAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where hullWarAmount is not null
        defaultVesselShouldBeFound("hullWarAmount.specified=true");

        // Get all the vesselList where hullWarAmount is null
        defaultVesselShouldNotBeFound("hullWarAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselsByHullWarValueIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where hullWarValue equals to DEFAULT_HULL_WAR_VALUE
        defaultVesselShouldBeFound("hullWarValue.equals=" + DEFAULT_HULL_WAR_VALUE);

        // Get all the vesselList where hullWarValue equals to UPDATED_HULL_WAR_VALUE
        defaultVesselShouldNotBeFound("hullWarValue.equals=" + UPDATED_HULL_WAR_VALUE);
    }

    @Test
    @Transactional
    public void getAllVesselsByHullWarValueIsInShouldWork() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where hullWarValue in DEFAULT_HULL_WAR_VALUE or UPDATED_HULL_WAR_VALUE
        defaultVesselShouldBeFound("hullWarValue.in=" + DEFAULT_HULL_WAR_VALUE + "," + UPDATED_HULL_WAR_VALUE);

        // Get all the vesselList where hullWarValue equals to UPDATED_HULL_WAR_VALUE
        defaultVesselShouldNotBeFound("hullWarValue.in=" + UPDATED_HULL_WAR_VALUE);
    }

    @Test
    @Transactional
    public void getAllVesselsByHullWarValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where hullWarValue is not null
        defaultVesselShouldBeFound("hullWarValue.specified=true");

        // Get all the vesselList where hullWarValue is null
        defaultVesselShouldNotBeFound("hullWarValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselsByVesselSizeIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where vesselSize equals to DEFAULT_VESSEL_SIZE
        defaultVesselShouldBeFound("vesselSize.equals=" + DEFAULT_VESSEL_SIZE);

        // Get all the vesselList where vesselSize equals to UPDATED_VESSEL_SIZE
        defaultVesselShouldNotBeFound("vesselSize.equals=" + UPDATED_VESSEL_SIZE);
    }

    @Test
    @Transactional
    public void getAllVesselsByVesselSizeIsInShouldWork() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where vesselSize in DEFAULT_VESSEL_SIZE or UPDATED_VESSEL_SIZE
        defaultVesselShouldBeFound("vesselSize.in=" + DEFAULT_VESSEL_SIZE + "," + UPDATED_VESSEL_SIZE);

        // Get all the vesselList where vesselSize equals to UPDATED_VESSEL_SIZE
        defaultVesselShouldNotBeFound("vesselSize.in=" + UPDATED_VESSEL_SIZE);
    }

    @Test
    @Transactional
    public void getAllVesselsByVesselSizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where vesselSize is not null
        defaultVesselShouldBeFound("vesselSize.specified=true");

        // Get all the vesselList where vesselSize is null
        defaultVesselShouldNotBeFound("vesselSize.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselsByLineIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where line equals to DEFAULT_LINE
        defaultVesselShouldBeFound("line.equals=" + DEFAULT_LINE);

        // Get all the vesselList where line equals to UPDATED_LINE
        defaultVesselShouldNotBeFound("line.equals=" + UPDATED_LINE);
    }

    @Test
    @Transactional
    public void getAllVesselsByLineIsInShouldWork() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where line in DEFAULT_LINE or UPDATED_LINE
        defaultVesselShouldBeFound("line.in=" + DEFAULT_LINE + "," + UPDATED_LINE);

        // Get all the vesselList where line equals to UPDATED_LINE
        defaultVesselShouldNotBeFound("line.in=" + UPDATED_LINE);
    }

    @Test
    @Transactional
    public void getAllVesselsByLineIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where line is not null
        defaultVesselShouldBeFound("line.specified=true");

        // Get all the vesselList where line is null
        defaultVesselShouldNotBeFound("line.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselsByDeeperIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where deeper equals to DEFAULT_DEEPER
        defaultVesselShouldBeFound("deeper.equals=" + DEFAULT_DEEPER);

        // Get all the vesselList where deeper equals to UPDATED_DEEPER
        defaultVesselShouldNotBeFound("deeper.equals=" + UPDATED_DEEPER);
    }

    @Test
    @Transactional
    public void getAllVesselsByDeeperIsInShouldWork() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where deeper in DEFAULT_DEEPER or UPDATED_DEEPER
        defaultVesselShouldBeFound("deeper.in=" + DEFAULT_DEEPER + "," + UPDATED_DEEPER);

        // Get all the vesselList where deeper equals to UPDATED_DEEPER
        defaultVesselShouldNotBeFound("deeper.in=" + UPDATED_DEEPER);
    }

    @Test
    @Transactional
    public void getAllVesselsByDeeperIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where deeper is not null
        defaultVesselShouldBeFound("deeper.specified=true");

        // Get all the vesselList where deeper is null
        defaultVesselShouldNotBeFound("deeper.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselsByCallSignIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where callSign equals to DEFAULT_CALL_SIGN
        defaultVesselShouldBeFound("callSign.equals=" + DEFAULT_CALL_SIGN);

        // Get all the vesselList where callSign equals to UPDATED_CALL_SIGN
        defaultVesselShouldNotBeFound("callSign.equals=" + UPDATED_CALL_SIGN);
    }

    @Test
    @Transactional
    public void getAllVesselsByCallSignIsInShouldWork() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where callSign in DEFAULT_CALL_SIGN or UPDATED_CALL_SIGN
        defaultVesselShouldBeFound("callSign.in=" + DEFAULT_CALL_SIGN + "," + UPDATED_CALL_SIGN);

        // Get all the vesselList where callSign equals to UPDATED_CALL_SIGN
        defaultVesselShouldNotBeFound("callSign.in=" + UPDATED_CALL_SIGN);
    }

    @Test
    @Transactional
    public void getAllVesselsByCallSignIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where callSign is not null
        defaultVesselShouldBeFound("callSign.specified=true");

        // Get all the vesselList where callSign is null
        defaultVesselShouldNotBeFound("callSign.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselsByImoNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where imoNumber equals to DEFAULT_IMO_NUMBER
        defaultVesselShouldBeFound("imoNumber.equals=" + DEFAULT_IMO_NUMBER);

        // Get all the vesselList where imoNumber equals to UPDATED_IMO_NUMBER
        defaultVesselShouldNotBeFound("imoNumber.equals=" + UPDATED_IMO_NUMBER);
    }

    @Test
    @Transactional
    public void getAllVesselsByImoNumberIsInShouldWork() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where imoNumber in DEFAULT_IMO_NUMBER or UPDATED_IMO_NUMBER
        defaultVesselShouldBeFound("imoNumber.in=" + DEFAULT_IMO_NUMBER + "," + UPDATED_IMO_NUMBER);

        // Get all the vesselList where imoNumber equals to UPDATED_IMO_NUMBER
        defaultVesselShouldNotBeFound("imoNumber.in=" + UPDATED_IMO_NUMBER);
    }

    @Test
    @Transactional
    public void getAllVesselsByImoNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        // Get all the vesselList where imoNumber is not null
        defaultVesselShouldBeFound("imoNumber.specified=true");

        // Get all the vesselList where imoNumber is null
        defaultVesselShouldNotBeFound("imoNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllVesselsByVesselCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        Country vesselCountry = CountryResourceIntTest.createEntity(em);
        em.persist(vesselCountry);
        em.flush();
        vessel.setVesselCountry(vesselCountry);
        vesselRepository.saveAndFlush(vessel);
        Long vesselCountryId = vesselCountry.getId();

        // Get all the vesselList where vesselCountry equals to vesselCountryId
        defaultVesselShouldBeFound("vesselCountryId.equals=" + vesselCountryId);

        // Get all the vesselList where vesselCountry equals to vesselCountryId + 1
        defaultVesselShouldNotBeFound("vesselCountryId.equals=" + (vesselCountryId + 1));
    }


    @Test
    @Transactional
    public void getAllVesselsByVesselCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        Currency vesselCurrency = CurrencyResourceIntTest.createEntity(em);
        em.persist(vesselCurrency);
        em.flush();
        vessel.setVesselCurrency(vesselCurrency);
        vesselRepository.saveAndFlush(vessel);
        Long vesselCurrencyId = vesselCurrency.getId();

        // Get all the vesselList where vesselCurrency equals to vesselCurrencyId
        defaultVesselShouldBeFound("vesselCurrencyId.equals=" + vesselCurrencyId);

        // Get all the vesselList where vesselCurrency equals to vesselCurrencyId + 1
        defaultVesselShouldNotBeFound("vesselCurrencyId.equals=" + (vesselCurrencyId + 1));
    }


    @Test
    @Transactional
    public void getAllVesselsByVesselTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        VesselType vesselType = VesselTypeResourceIntTest.createEntity(em);
        em.persist(vesselType);
        em.flush();
        vessel.setVesselType(vesselType);
        vesselRepository.saveAndFlush(vessel);
        Long vesselTypeId = vesselType.getId();

        // Get all the vesselList where vesselType equals to vesselTypeId
        defaultVesselShouldBeFound("vesselTypeId.equals=" + vesselTypeId);

        // Get all the vesselList where vesselType equals to vesselTypeId + 1
        defaultVesselShouldNotBeFound("vesselTypeId.equals=" + (vesselTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllVesselsByVesselOwnerCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        Company vesselOwnerCompany = CompanyResourceIntTest.createEntity(em);
        em.persist(vesselOwnerCompany);
        em.flush();
        vessel.setVesselOwnerCompany(vesselOwnerCompany);
        vesselRepository.saveAndFlush(vessel);
        Long vesselOwnerCompanyId = vesselOwnerCompany.getId();

        // Get all the vesselList where vesselOwnerCompany equals to vesselOwnerCompanyId
        defaultVesselShouldBeFound("vesselOwnerCompanyId.equals=" + vesselOwnerCompanyId);

        // Get all the vesselList where vesselOwnerCompany equals to vesselOwnerCompanyId + 1
        defaultVesselShouldNotBeFound("vesselOwnerCompanyId.equals=" + (vesselOwnerCompanyId + 1));
    }


    @Test
    @Transactional
    public void getAllVesselsByRegistedPortIsEqualToSomething() throws Exception {
        // Initialize the database
        Port registedPort = PortResourceIntTest.createEntity(em);
        em.persist(registedPort);
        em.flush();
        vessel.setRegistedPort(registedPort);
        vesselRepository.saveAndFlush(vessel);
        Long registedPortId = registedPort.getId();

        // Get all the vesselList where registedPort equals to registedPortId
        defaultVesselShouldBeFound("registedPortId.equals=" + registedPortId);

        // Get all the vesselList where registedPort equals to registedPortId + 1
        defaultVesselShouldNotBeFound("registedPortId.equals=" + (registedPortId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultVesselShouldBeFound(String filter) throws Exception {
        restVesselMockMvc.perform(get("/api/vessels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vessel.getId().intValue())))
            .andExpect(jsonPath("$.[*].vesselCode").value(hasItem(DEFAULT_VESSEL_CODE.toString())))
            .andExpect(jsonPath("$.[*].vesselName").value(hasItem(DEFAULT_VESSEL_NAME.toString())))
            .andExpect(jsonPath("$.[*].vesselChineseName").value(hasItem(DEFAULT_VESSEL_CHINESE_NAME.toString())))
            .andExpect(jsonPath("$.[*].gtr").value(hasItem(DEFAULT_GTR.doubleValue())))
            .andExpect(jsonPath("$.[*].wrt").value(hasItem(DEFAULT_WRT.doubleValue())))
            .andExpect(jsonPath("$.[*].buildLocation").value(hasItem(DEFAULT_BUILD_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].buildYear").value(hasItem(DEFAULT_BUILD_YEAR.toString())))
            .andExpect(jsonPath("$.[*].foc").value(hasItem(DEFAULT_FOC.toString())))
            .andExpect(jsonPath("$.[*].dwt").value(hasItem(DEFAULT_DWT.doubleValue())))
            .andExpect(jsonPath("$.[*].hullHmAmount").value(hasItem(DEFAULT_HULL_HM_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].hullHmValue").value(hasItem(DEFAULT_HULL_HM_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].hullIvAmount").value(hasItem(DEFAULT_HULL_IV_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].hullIvValue").value(hasItem(DEFAULT_HULL_IV_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].hullWarAmount").value(hasItem(DEFAULT_HULL_WAR_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].hullWarValue").value(hasItem(DEFAULT_HULL_WAR_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].vesselSize").value(hasItem(DEFAULT_VESSEL_SIZE.toString())))
            .andExpect(jsonPath("$.[*].line").value(hasItem(DEFAULT_LINE.toString())))
            .andExpect(jsonPath("$.[*].deeper").value(hasItem(DEFAULT_DEEPER.doubleValue())))
            .andExpect(jsonPath("$.[*].callSign").value(hasItem(DEFAULT_CALL_SIGN.toString())))
            .andExpect(jsonPath("$.[*].imoNumber").value(hasItem(DEFAULT_IMO_NUMBER.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultVesselShouldNotBeFound(String filter) throws Exception {
        restVesselMockMvc.perform(get("/api/vessels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingVessel() throws Exception {
        // Get the vessel
        restVesselMockMvc.perform(get("/api/vessels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVessel() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        int databaseSizeBeforeUpdate = vesselRepository.findAll().size();

        // Update the vessel
        Vessel updatedVessel = vesselRepository.findById(vessel.getId()).get();
        // Disconnect from session so that the updates on updatedVessel are not directly saved in db
        em.detach(updatedVessel);
        updatedVessel
            .vesselCode(UPDATED_VESSEL_CODE)
            .vesselName(UPDATED_VESSEL_NAME)
            .vesselChineseName(UPDATED_VESSEL_CHINESE_NAME)
            .gtr(UPDATED_GTR)
            .wrt(UPDATED_WRT)
            .buildLocation(UPDATED_BUILD_LOCATION)
            .buildYear(UPDATED_BUILD_YEAR)
            .foc(UPDATED_FOC)
            .dwt(UPDATED_DWT)
            .hullHmAmount(UPDATED_HULL_HM_AMOUNT)
            .hullHmValue(UPDATED_HULL_HM_VALUE)
            .hullIvAmount(UPDATED_HULL_IV_AMOUNT)
            .hullIvValue(UPDATED_HULL_IV_VALUE)
            .hullWarAmount(UPDATED_HULL_WAR_AMOUNT)
            .hullWarValue(UPDATED_HULL_WAR_VALUE)
            .vesselSize(UPDATED_VESSEL_SIZE)
            .line(UPDATED_LINE)
            .deeper(UPDATED_DEEPER)
            .callSign(UPDATED_CALL_SIGN)
            .imoNumber(UPDATED_IMO_NUMBER);
        VesselDTO vesselDTO = vesselMapper.toDto(updatedVessel);

        restVesselMockMvc.perform(put("/api/vessels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vesselDTO)))
            .andExpect(status().isOk());

        // Validate the Vessel in the database
        List<Vessel> vesselList = vesselRepository.findAll();
        assertThat(vesselList).hasSize(databaseSizeBeforeUpdate);
        Vessel testVessel = vesselList.get(vesselList.size() - 1);
        assertThat(testVessel.getVesselCode()).isEqualTo(UPDATED_VESSEL_CODE);
        assertThat(testVessel.getVesselName()).isEqualTo(UPDATED_VESSEL_NAME);
        assertThat(testVessel.getVesselChineseName()).isEqualTo(UPDATED_VESSEL_CHINESE_NAME);
        assertThat(testVessel.getGtr()).isEqualTo(UPDATED_GTR);
        assertThat(testVessel.getWrt()).isEqualTo(UPDATED_WRT);
        assertThat(testVessel.getBuildLocation()).isEqualTo(UPDATED_BUILD_LOCATION);
        assertThat(testVessel.getBuildYear()).isEqualTo(UPDATED_BUILD_YEAR);
        assertThat(testVessel.getFoc()).isEqualTo(UPDATED_FOC);
        assertThat(testVessel.getDwt()).isEqualTo(UPDATED_DWT);
        assertThat(testVessel.getHullHmAmount()).isEqualTo(UPDATED_HULL_HM_AMOUNT);
        assertThat(testVessel.getHullHmValue()).isEqualTo(UPDATED_HULL_HM_VALUE);
        assertThat(testVessel.getHullIvAmount()).isEqualTo(UPDATED_HULL_IV_AMOUNT);
        assertThat(testVessel.getHullIvValue()).isEqualTo(UPDATED_HULL_IV_VALUE);
        assertThat(testVessel.getHullWarAmount()).isEqualTo(UPDATED_HULL_WAR_AMOUNT);
        assertThat(testVessel.getHullWarValue()).isEqualTo(UPDATED_HULL_WAR_VALUE);
        assertThat(testVessel.getVesselSize()).isEqualTo(UPDATED_VESSEL_SIZE);
        assertThat(testVessel.getLine()).isEqualTo(UPDATED_LINE);
        assertThat(testVessel.getDeeper()).isEqualTo(UPDATED_DEEPER);
        assertThat(testVessel.getCallSign()).isEqualTo(UPDATED_CALL_SIGN);
        assertThat(testVessel.getImoNumber()).isEqualTo(UPDATED_IMO_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingVessel() throws Exception {
        int databaseSizeBeforeUpdate = vesselRepository.findAll().size();

        // Create the Vessel
        VesselDTO vesselDTO = vesselMapper.toDto(vessel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restVesselMockMvc.perform(put("/api/vessels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vesselDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vessel in the database
        List<Vessel> vesselList = vesselRepository.findAll();
        assertThat(vesselList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVessel() throws Exception {
        // Initialize the database
        vesselRepository.saveAndFlush(vessel);

        int databaseSizeBeforeDelete = vesselRepository.findAll().size();

        // Get the vessel
        restVesselMockMvc.perform(delete("/api/vessels/{id}", vessel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Vessel> vesselList = vesselRepository.findAll();
        assertThat(vesselList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vessel.class);
        Vessel vessel1 = new Vessel();
        vessel1.setId(1L);
        Vessel vessel2 = new Vessel();
        vessel2.setId(vessel1.getId());
        assertThat(vessel1).isEqualTo(vessel2);
        vessel2.setId(2L);
        assertThat(vessel1).isNotEqualTo(vessel2);
        vessel1.setId(null);
        assertThat(vessel1).isNotEqualTo(vessel2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VesselDTO.class);
        VesselDTO vesselDTO1 = new VesselDTO();
        vesselDTO1.setId(1L);
        VesselDTO vesselDTO2 = new VesselDTO();
        assertThat(vesselDTO1).isNotEqualTo(vesselDTO2);
        vesselDTO2.setId(vesselDTO1.getId());
        assertThat(vesselDTO1).isEqualTo(vesselDTO2);
        vesselDTO2.setId(2L);
        assertThat(vesselDTO1).isNotEqualTo(vesselDTO2);
        vesselDTO1.setId(null);
        assertThat(vesselDTO1).isNotEqualTo(vesselDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vesselMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vesselMapper.fromId(null)).isNull();
    }
}

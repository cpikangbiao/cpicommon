package com.cpi.common.web.rest;

import com.cpi.common.CpicommonApp;

import com.cpi.common.config.SecurityBeanOverrideConfiguration;

import com.cpi.common.domain.Port;
import com.cpi.common.domain.Correspondent;
import com.cpi.common.domain.Country;
import com.cpi.common.repository.PortRepository;
import com.cpi.common.service.PortService;
import com.cpi.common.service.dto.PortDTO;
import com.cpi.common.service.mapper.PortMapper;
import com.cpi.common.web.rest.errors.ExceptionTranslator;
import com.cpi.common.service.dto.PortCriteria;
import com.cpi.common.service.PortQueryService;

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
 * Test class for the PortResource REST controller.
 *
 * @see PortResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CpicommonApp.class, SecurityBeanOverrideConfiguration.class})
public class PortResourceIntTest {

    private static final String DEFAULT_PORT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PORT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PORT_NAME_CHINESE = "AAAAAAAAAA";
    private static final String UPDATED_PORT_NAME_CHINESE = "BBBBBBBBBB";

    @Autowired
    private PortRepository portRepository;

    @Autowired
    private PortMapper portMapper;

    @Autowired
    private PortService portService;

    @Autowired
    private PortQueryService portQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPortMockMvc;

    private Port port;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PortResource portResource = new PortResource(portService, portQueryService);
        this.restPortMockMvc = MockMvcBuilders.standaloneSetup(portResource)
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
    public static Port createEntity(EntityManager em) {
        Port port = new Port()
            .portCode(DEFAULT_PORT_CODE)
            .portName(DEFAULT_PORT_NAME)
            .portNameChinese(DEFAULT_PORT_NAME_CHINESE);
        return port;
    }

    @Before
    public void initTest() {
        port = createEntity(em);
    }

    @Test
    @Transactional
    public void createPort() throws Exception {
        int databaseSizeBeforeCreate = portRepository.findAll().size();

        // Create the Port
        PortDTO portDTO = portMapper.toDto(port);
        restPortMockMvc.perform(post("/api/ports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portDTO)))
            .andExpect(status().isCreated());

        // Validate the Port in the database
        List<Port> portList = portRepository.findAll();
        assertThat(portList).hasSize(databaseSizeBeforeCreate + 1);
        Port testPort = portList.get(portList.size() - 1);
        assertThat(testPort.getPortCode()).isEqualTo(DEFAULT_PORT_CODE);
        assertThat(testPort.getPortName()).isEqualTo(DEFAULT_PORT_NAME);
        assertThat(testPort.getPortNameChinese()).isEqualTo(DEFAULT_PORT_NAME_CHINESE);
    }

    @Test
    @Transactional
    public void createPortWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = portRepository.findAll().size();

        // Create the Port with an existing ID
        port.setId(1L);
        PortDTO portDTO = portMapper.toDto(port);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPortMockMvc.perform(post("/api/ports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Port in the database
        List<Port> portList = portRepository.findAll();
        assertThat(portList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPorts() throws Exception {
        // Initialize the database
        portRepository.saveAndFlush(port);

        // Get all the portList
        restPortMockMvc.perform(get("/api/ports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(port.getId().intValue())))
            .andExpect(jsonPath("$.[*].portCode").value(hasItem(DEFAULT_PORT_CODE.toString())))
            .andExpect(jsonPath("$.[*].portName").value(hasItem(DEFAULT_PORT_NAME.toString())))
            .andExpect(jsonPath("$.[*].portNameChinese").value(hasItem(DEFAULT_PORT_NAME_CHINESE.toString())));
    }

    @Test
    @Transactional
    public void getPort() throws Exception {
        // Initialize the database
        portRepository.saveAndFlush(port);

        // Get the port
        restPortMockMvc.perform(get("/api/ports/{id}", port.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(port.getId().intValue()))
            .andExpect(jsonPath("$.portCode").value(DEFAULT_PORT_CODE.toString()))
            .andExpect(jsonPath("$.portName").value(DEFAULT_PORT_NAME.toString()))
            .andExpect(jsonPath("$.portNameChinese").value(DEFAULT_PORT_NAME_CHINESE.toString()));
    }

    @Test
    @Transactional
    public void getAllPortsByPortCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        portRepository.saveAndFlush(port);

        // Get all the portList where portCode equals to DEFAULT_PORT_CODE
        defaultPortShouldBeFound("portCode.equals=" + DEFAULT_PORT_CODE);

        // Get all the portList where portCode equals to UPDATED_PORT_CODE
        defaultPortShouldNotBeFound("portCode.equals=" + UPDATED_PORT_CODE);
    }

    @Test
    @Transactional
    public void getAllPortsByPortCodeIsInShouldWork() throws Exception {
        // Initialize the database
        portRepository.saveAndFlush(port);

        // Get all the portList where portCode in DEFAULT_PORT_CODE or UPDATED_PORT_CODE
        defaultPortShouldBeFound("portCode.in=" + DEFAULT_PORT_CODE + "," + UPDATED_PORT_CODE);

        // Get all the portList where portCode equals to UPDATED_PORT_CODE
        defaultPortShouldNotBeFound("portCode.in=" + UPDATED_PORT_CODE);
    }

    @Test
    @Transactional
    public void getAllPortsByPortCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        portRepository.saveAndFlush(port);

        // Get all the portList where portCode is not null
        defaultPortShouldBeFound("portCode.specified=true");

        // Get all the portList where portCode is null
        defaultPortShouldNotBeFound("portCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllPortsByPortNameIsEqualToSomething() throws Exception {
        // Initialize the database
        portRepository.saveAndFlush(port);

        // Get all the portList where portName equals to DEFAULT_PORT_NAME
        defaultPortShouldBeFound("portName.equals=" + DEFAULT_PORT_NAME);

        // Get all the portList where portName equals to UPDATED_PORT_NAME
        defaultPortShouldNotBeFound("portName.equals=" + UPDATED_PORT_NAME);
    }

    @Test
    @Transactional
    public void getAllPortsByPortNameIsInShouldWork() throws Exception {
        // Initialize the database
        portRepository.saveAndFlush(port);

        // Get all the portList where portName in DEFAULT_PORT_NAME or UPDATED_PORT_NAME
        defaultPortShouldBeFound("portName.in=" + DEFAULT_PORT_NAME + "," + UPDATED_PORT_NAME);

        // Get all the portList where portName equals to UPDATED_PORT_NAME
        defaultPortShouldNotBeFound("portName.in=" + UPDATED_PORT_NAME);
    }

    @Test
    @Transactional
    public void getAllPortsByPortNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        portRepository.saveAndFlush(port);

        // Get all the portList where portName is not null
        defaultPortShouldBeFound("portName.specified=true");

        // Get all the portList where portName is null
        defaultPortShouldNotBeFound("portName.specified=false");
    }

    @Test
    @Transactional
    public void getAllPortsByPortNameChineseIsEqualToSomething() throws Exception {
        // Initialize the database
        portRepository.saveAndFlush(port);

        // Get all the portList where portNameChinese equals to DEFAULT_PORT_NAME_CHINESE
        defaultPortShouldBeFound("portNameChinese.equals=" + DEFAULT_PORT_NAME_CHINESE);

        // Get all the portList where portNameChinese equals to UPDATED_PORT_NAME_CHINESE
        defaultPortShouldNotBeFound("portNameChinese.equals=" + UPDATED_PORT_NAME_CHINESE);
    }

    @Test
    @Transactional
    public void getAllPortsByPortNameChineseIsInShouldWork() throws Exception {
        // Initialize the database
        portRepository.saveAndFlush(port);

        // Get all the portList where portNameChinese in DEFAULT_PORT_NAME_CHINESE or UPDATED_PORT_NAME_CHINESE
        defaultPortShouldBeFound("portNameChinese.in=" + DEFAULT_PORT_NAME_CHINESE + "," + UPDATED_PORT_NAME_CHINESE);

        // Get all the portList where portNameChinese equals to UPDATED_PORT_NAME_CHINESE
        defaultPortShouldNotBeFound("portNameChinese.in=" + UPDATED_PORT_NAME_CHINESE);
    }

    @Test
    @Transactional
    public void getAllPortsByPortNameChineseIsNullOrNotNull() throws Exception {
        // Initialize the database
        portRepository.saveAndFlush(port);

        // Get all the portList where portNameChinese is not null
        defaultPortShouldBeFound("portNameChinese.specified=true");

        // Get all the portList where portNameChinese is null
        defaultPortShouldNotBeFound("portNameChinese.specified=false");
    }

    @Test
    @Transactional
    public void getAllPortsByCorrespondentsIsEqualToSomething() throws Exception {
        // Initialize the database
        Correspondent correspondents = CorrespondentResourceIntTest.createEntity(em);
        em.persist(correspondents);
        em.flush();
        port.addCorrespondents(correspondents);
        portRepository.saveAndFlush(port);
        Long correspondentsId = correspondents.getId();

        // Get all the portList where correspondents equals to correspondentsId
        defaultPortShouldBeFound("correspondentsId.equals=" + correspondentsId);

        // Get all the portList where correspondents equals to correspondentsId + 1
        defaultPortShouldNotBeFound("correspondentsId.equals=" + (correspondentsId + 1));
    }


    @Test
    @Transactional
    public void getAllPortsByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        Country country = CountryResourceIntTest.createEntity(em);
        em.persist(country);
        em.flush();
        port.setCountry(country);
        portRepository.saveAndFlush(port);
        Long countryId = country.getId();

        // Get all the portList where country equals to countryId
        defaultPortShouldBeFound("countryId.equals=" + countryId);

        // Get all the portList where country equals to countryId + 1
        defaultPortShouldNotBeFound("countryId.equals=" + (countryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPortShouldBeFound(String filter) throws Exception {
        restPortMockMvc.perform(get("/api/ports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(port.getId().intValue())))
            .andExpect(jsonPath("$.[*].portCode").value(hasItem(DEFAULT_PORT_CODE.toString())))
            .andExpect(jsonPath("$.[*].portName").value(hasItem(DEFAULT_PORT_NAME.toString())))
            .andExpect(jsonPath("$.[*].portNameChinese").value(hasItem(DEFAULT_PORT_NAME_CHINESE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPortShouldNotBeFound(String filter) throws Exception {
        restPortMockMvc.perform(get("/api/ports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingPort() throws Exception {
        // Get the port
        restPortMockMvc.perform(get("/api/ports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePort() throws Exception {
        // Initialize the database
        portRepository.saveAndFlush(port);
        int databaseSizeBeforeUpdate = portRepository.findAll().size();

        // Update the port
        Port updatedPort = portRepository.findOne(port.getId());
        // Disconnect from session so that the updates on updatedPort are not directly saved in db
        em.detach(updatedPort);
        updatedPort
            .portCode(UPDATED_PORT_CODE)
            .portName(UPDATED_PORT_NAME)
            .portNameChinese(UPDATED_PORT_NAME_CHINESE);
        PortDTO portDTO = portMapper.toDto(updatedPort);

        restPortMockMvc.perform(put("/api/ports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portDTO)))
            .andExpect(status().isOk());

        // Validate the Port in the database
        List<Port> portList = portRepository.findAll();
        assertThat(portList).hasSize(databaseSizeBeforeUpdate);
        Port testPort = portList.get(portList.size() - 1);
        assertThat(testPort.getPortCode()).isEqualTo(UPDATED_PORT_CODE);
        assertThat(testPort.getPortName()).isEqualTo(UPDATED_PORT_NAME);
        assertThat(testPort.getPortNameChinese()).isEqualTo(UPDATED_PORT_NAME_CHINESE);
    }

    @Test
    @Transactional
    public void updateNonExistingPort() throws Exception {
        int databaseSizeBeforeUpdate = portRepository.findAll().size();

        // Create the Port
        PortDTO portDTO = portMapper.toDto(port);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPortMockMvc.perform(put("/api/ports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portDTO)))
            .andExpect(status().isCreated());

        // Validate the Port in the database
        List<Port> portList = portRepository.findAll();
        assertThat(portList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePort() throws Exception {
        // Initialize the database
        portRepository.saveAndFlush(port);
        int databaseSizeBeforeDelete = portRepository.findAll().size();

        // Get the port
        restPortMockMvc.perform(delete("/api/ports/{id}", port.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Port> portList = portRepository.findAll();
        assertThat(portList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Port.class);
        Port port1 = new Port();
        port1.setId(1L);
        Port port2 = new Port();
        port2.setId(port1.getId());
        assertThat(port1).isEqualTo(port2);
        port2.setId(2L);
        assertThat(port1).isNotEqualTo(port2);
        port1.setId(null);
        assertThat(port1).isNotEqualTo(port2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PortDTO.class);
        PortDTO portDTO1 = new PortDTO();
        portDTO1.setId(1L);
        PortDTO portDTO2 = new PortDTO();
        assertThat(portDTO1).isNotEqualTo(portDTO2);
        portDTO2.setId(portDTO1.getId());
        assertThat(portDTO1).isEqualTo(portDTO2);
        portDTO2.setId(2L);
        assertThat(portDTO1).isNotEqualTo(portDTO2);
        portDTO1.setId(null);
        assertThat(portDTO1).isNotEqualTo(portDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(portMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(portMapper.fromId(null)).isNull();
    }
}

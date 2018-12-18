package com.cpi.common.web.rest;

import com.cpi.common.CpicommonApp;

import com.cpi.common.config.SecurityBeanOverrideConfiguration;

import com.cpi.common.domain.ClassificationSociety;
import com.cpi.common.repository.ClassificationSocietyRepository;
import com.cpi.common.service.ClassificationSocietyService;
import com.cpi.common.service.dto.ClassificationSocietyDTO;
import com.cpi.common.service.mapper.ClassificationSocietyMapper;
import com.cpi.common.web.rest.errors.ExceptionTranslator;
import com.cpi.common.service.dto.ClassificationSocietyCriteria;
import com.cpi.common.service.ClassificationSocietyQueryService;

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
 * Test class for the ClassificationSocietyResource REST controller.
 *
 * @see ClassificationSocietyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, CpicommonApp.class})
public class ClassificationSocietyResourceIntTest {

    private static final String DEFAULT_CLASSIFICATION_SOCIETY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLASSIFICATION_SOCIETY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLASSIFICATION_SOCIETY_NAME_ABBR = "AAAAAAAAAA";
    private static final String UPDATED_CLASSIFICATION_SOCIETY_NAME_ABBR = "BBBBBBBBBB";

    private static final Integer DEFAULT_SORT_NUM = 1;
    private static final Integer UPDATED_SORT_NUM = 2;

    @Autowired
    private ClassificationSocietyRepository classificationSocietyRepository;

    @Autowired
    private ClassificationSocietyMapper classificationSocietyMapper;

    @Autowired
    private ClassificationSocietyService classificationSocietyService;

    @Autowired
    private ClassificationSocietyQueryService classificationSocietyQueryService;

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

    private MockMvc restClassificationSocietyMockMvc;

    private ClassificationSociety classificationSociety;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClassificationSocietyResource classificationSocietyResource = new ClassificationSocietyResource(classificationSocietyService, classificationSocietyQueryService);
        this.restClassificationSocietyMockMvc = MockMvcBuilders.standaloneSetup(classificationSocietyResource)
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
    public static ClassificationSociety createEntity(EntityManager em) {
        ClassificationSociety classificationSociety = new ClassificationSociety()
            .classificationSocietyName(DEFAULT_CLASSIFICATION_SOCIETY_NAME)
            .classificationSocietyNameAbbr(DEFAULT_CLASSIFICATION_SOCIETY_NAME_ABBR)
            .sortNum(DEFAULT_SORT_NUM);
        return classificationSociety;
    }

    @Before
    public void initTest() {
        classificationSociety = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassificationSociety() throws Exception {
        int databaseSizeBeforeCreate = classificationSocietyRepository.findAll().size();

        // Create the ClassificationSociety
        ClassificationSocietyDTO classificationSocietyDTO = classificationSocietyMapper.toDto(classificationSociety);
        restClassificationSocietyMockMvc.perform(post("/api/classification-societies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classificationSocietyDTO)))
            .andExpect(status().isCreated());

        // Validate the ClassificationSociety in the database
        List<ClassificationSociety> classificationSocietyList = classificationSocietyRepository.findAll();
        assertThat(classificationSocietyList).hasSize(databaseSizeBeforeCreate + 1);
        ClassificationSociety testClassificationSociety = classificationSocietyList.get(classificationSocietyList.size() - 1);
        assertThat(testClassificationSociety.getClassificationSocietyName()).isEqualTo(DEFAULT_CLASSIFICATION_SOCIETY_NAME);
        assertThat(testClassificationSociety.getClassificationSocietyNameAbbr()).isEqualTo(DEFAULT_CLASSIFICATION_SOCIETY_NAME_ABBR);
        assertThat(testClassificationSociety.getSortNum()).isEqualTo(DEFAULT_SORT_NUM);
    }

    @Test
    @Transactional
    public void createClassificationSocietyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classificationSocietyRepository.findAll().size();

        // Create the ClassificationSociety with an existing ID
        classificationSociety.setId(1L);
        ClassificationSocietyDTO classificationSocietyDTO = classificationSocietyMapper.toDto(classificationSociety);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassificationSocietyMockMvc.perform(post("/api/classification-societies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classificationSocietyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassificationSociety in the database
        List<ClassificationSociety> classificationSocietyList = classificationSocietyRepository.findAll();
        assertThat(classificationSocietyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkClassificationSocietyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = classificationSocietyRepository.findAll().size();
        // set the field null
        classificationSociety.setClassificationSocietyName(null);

        // Create the ClassificationSociety, which fails.
        ClassificationSocietyDTO classificationSocietyDTO = classificationSocietyMapper.toDto(classificationSociety);

        restClassificationSocietyMockMvc.perform(post("/api/classification-societies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classificationSocietyDTO)))
            .andExpect(status().isBadRequest());

        List<ClassificationSociety> classificationSocietyList = classificationSocietyRepository.findAll();
        assertThat(classificationSocietyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClassificationSocietyNameAbbrIsRequired() throws Exception {
        int databaseSizeBeforeTest = classificationSocietyRepository.findAll().size();
        // set the field null
        classificationSociety.setClassificationSocietyNameAbbr(null);

        // Create the ClassificationSociety, which fails.
        ClassificationSocietyDTO classificationSocietyDTO = classificationSocietyMapper.toDto(classificationSociety);

        restClassificationSocietyMockMvc.perform(post("/api/classification-societies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classificationSocietyDTO)))
            .andExpect(status().isBadRequest());

        List<ClassificationSociety> classificationSocietyList = classificationSocietyRepository.findAll();
        assertThat(classificationSocietyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSortNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = classificationSocietyRepository.findAll().size();
        // set the field null
        classificationSociety.setSortNum(null);

        // Create the ClassificationSociety, which fails.
        ClassificationSocietyDTO classificationSocietyDTO = classificationSocietyMapper.toDto(classificationSociety);

        restClassificationSocietyMockMvc.perform(post("/api/classification-societies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classificationSocietyDTO)))
            .andExpect(status().isBadRequest());

        List<ClassificationSociety> classificationSocietyList = classificationSocietyRepository.findAll();
        assertThat(classificationSocietyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClassificationSocieties() throws Exception {
        // Initialize the database
        classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get all the classificationSocietyList
        restClassificationSocietyMockMvc.perform(get("/api/classification-societies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classificationSociety.getId().intValue())))
            .andExpect(jsonPath("$.[*].classificationSocietyName").value(hasItem(DEFAULT_CLASSIFICATION_SOCIETY_NAME.toString())))
            .andExpect(jsonPath("$.[*].classificationSocietyNameAbbr").value(hasItem(DEFAULT_CLASSIFICATION_SOCIETY_NAME_ABBR.toString())))
            .andExpect(jsonPath("$.[*].sortNum").value(hasItem(DEFAULT_SORT_NUM)));
    }
    
    @Test
    @Transactional
    public void getClassificationSociety() throws Exception {
        // Initialize the database
        classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get the classificationSociety
        restClassificationSocietyMockMvc.perform(get("/api/classification-societies/{id}", classificationSociety.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classificationSociety.getId().intValue()))
            .andExpect(jsonPath("$.classificationSocietyName").value(DEFAULT_CLASSIFICATION_SOCIETY_NAME.toString()))
            .andExpect(jsonPath("$.classificationSocietyNameAbbr").value(DEFAULT_CLASSIFICATION_SOCIETY_NAME_ABBR.toString()))
            .andExpect(jsonPath("$.sortNum").value(DEFAULT_SORT_NUM));
    }

    @Test
    @Transactional
    public void getAllClassificationSocietiesByClassificationSocietyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get all the classificationSocietyList where classificationSocietyName equals to DEFAULT_CLASSIFICATION_SOCIETY_NAME
        defaultClassificationSocietyShouldBeFound("classificationSocietyName.equals=" + DEFAULT_CLASSIFICATION_SOCIETY_NAME);

        // Get all the classificationSocietyList where classificationSocietyName equals to UPDATED_CLASSIFICATION_SOCIETY_NAME
        defaultClassificationSocietyShouldNotBeFound("classificationSocietyName.equals=" + UPDATED_CLASSIFICATION_SOCIETY_NAME);
    }

    @Test
    @Transactional
    public void getAllClassificationSocietiesByClassificationSocietyNameIsInShouldWork() throws Exception {
        // Initialize the database
        classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get all the classificationSocietyList where classificationSocietyName in DEFAULT_CLASSIFICATION_SOCIETY_NAME or UPDATED_CLASSIFICATION_SOCIETY_NAME
        defaultClassificationSocietyShouldBeFound("classificationSocietyName.in=" + DEFAULT_CLASSIFICATION_SOCIETY_NAME + "," + UPDATED_CLASSIFICATION_SOCIETY_NAME);

        // Get all the classificationSocietyList where classificationSocietyName equals to UPDATED_CLASSIFICATION_SOCIETY_NAME
        defaultClassificationSocietyShouldNotBeFound("classificationSocietyName.in=" + UPDATED_CLASSIFICATION_SOCIETY_NAME);
    }

    @Test
    @Transactional
    public void getAllClassificationSocietiesByClassificationSocietyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get all the classificationSocietyList where classificationSocietyName is not null
        defaultClassificationSocietyShouldBeFound("classificationSocietyName.specified=true");

        // Get all the classificationSocietyList where classificationSocietyName is null
        defaultClassificationSocietyShouldNotBeFound("classificationSocietyName.specified=false");
    }

    @Test
    @Transactional
    public void getAllClassificationSocietiesByClassificationSocietyNameAbbrIsEqualToSomething() throws Exception {
        // Initialize the database
        classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get all the classificationSocietyList where classificationSocietyNameAbbr equals to DEFAULT_CLASSIFICATION_SOCIETY_NAME_ABBR
        defaultClassificationSocietyShouldBeFound("classificationSocietyNameAbbr.equals=" + DEFAULT_CLASSIFICATION_SOCIETY_NAME_ABBR);

        // Get all the classificationSocietyList where classificationSocietyNameAbbr equals to UPDATED_CLASSIFICATION_SOCIETY_NAME_ABBR
        defaultClassificationSocietyShouldNotBeFound("classificationSocietyNameAbbr.equals=" + UPDATED_CLASSIFICATION_SOCIETY_NAME_ABBR);
    }

    @Test
    @Transactional
    public void getAllClassificationSocietiesByClassificationSocietyNameAbbrIsInShouldWork() throws Exception {
        // Initialize the database
        classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get all the classificationSocietyList where classificationSocietyNameAbbr in DEFAULT_CLASSIFICATION_SOCIETY_NAME_ABBR or UPDATED_CLASSIFICATION_SOCIETY_NAME_ABBR
        defaultClassificationSocietyShouldBeFound("classificationSocietyNameAbbr.in=" + DEFAULT_CLASSIFICATION_SOCIETY_NAME_ABBR + "," + UPDATED_CLASSIFICATION_SOCIETY_NAME_ABBR);

        // Get all the classificationSocietyList where classificationSocietyNameAbbr equals to UPDATED_CLASSIFICATION_SOCIETY_NAME_ABBR
        defaultClassificationSocietyShouldNotBeFound("classificationSocietyNameAbbr.in=" + UPDATED_CLASSIFICATION_SOCIETY_NAME_ABBR);
    }

    @Test
    @Transactional
    public void getAllClassificationSocietiesByClassificationSocietyNameAbbrIsNullOrNotNull() throws Exception {
        // Initialize the database
        classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get all the classificationSocietyList where classificationSocietyNameAbbr is not null
        defaultClassificationSocietyShouldBeFound("classificationSocietyNameAbbr.specified=true");

        // Get all the classificationSocietyList where classificationSocietyNameAbbr is null
        defaultClassificationSocietyShouldNotBeFound("classificationSocietyNameAbbr.specified=false");
    }

    @Test
    @Transactional
    public void getAllClassificationSocietiesBySortNumIsEqualToSomething() throws Exception {
        // Initialize the database
        classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get all the classificationSocietyList where sortNum equals to DEFAULT_SORT_NUM
        defaultClassificationSocietyShouldBeFound("sortNum.equals=" + DEFAULT_SORT_NUM);

        // Get all the classificationSocietyList where sortNum equals to UPDATED_SORT_NUM
        defaultClassificationSocietyShouldNotBeFound("sortNum.equals=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllClassificationSocietiesBySortNumIsInShouldWork() throws Exception {
        // Initialize the database
        classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get all the classificationSocietyList where sortNum in DEFAULT_SORT_NUM or UPDATED_SORT_NUM
        defaultClassificationSocietyShouldBeFound("sortNum.in=" + DEFAULT_SORT_NUM + "," + UPDATED_SORT_NUM);

        // Get all the classificationSocietyList where sortNum equals to UPDATED_SORT_NUM
        defaultClassificationSocietyShouldNotBeFound("sortNum.in=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllClassificationSocietiesBySortNumIsNullOrNotNull() throws Exception {
        // Initialize the database
        classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get all the classificationSocietyList where sortNum is not null
        defaultClassificationSocietyShouldBeFound("sortNum.specified=true");

        // Get all the classificationSocietyList where sortNum is null
        defaultClassificationSocietyShouldNotBeFound("sortNum.specified=false");
    }

    @Test
    @Transactional
    public void getAllClassificationSocietiesBySortNumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get all the classificationSocietyList where sortNum greater than or equals to DEFAULT_SORT_NUM
        defaultClassificationSocietyShouldBeFound("sortNum.greaterOrEqualThan=" + DEFAULT_SORT_NUM);

        // Get all the classificationSocietyList where sortNum greater than or equals to UPDATED_SORT_NUM
        defaultClassificationSocietyShouldNotBeFound("sortNum.greaterOrEqualThan=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllClassificationSocietiesBySortNumIsLessThanSomething() throws Exception {
        // Initialize the database
        classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get all the classificationSocietyList where sortNum less than or equals to DEFAULT_SORT_NUM
        defaultClassificationSocietyShouldNotBeFound("sortNum.lessThan=" + DEFAULT_SORT_NUM);

        // Get all the classificationSocietyList where sortNum less than or equals to UPDATED_SORT_NUM
        defaultClassificationSocietyShouldBeFound("sortNum.lessThan=" + UPDATED_SORT_NUM);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultClassificationSocietyShouldBeFound(String filter) throws Exception {
        restClassificationSocietyMockMvc.perform(get("/api/classification-societies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classificationSociety.getId().intValue())))
            .andExpect(jsonPath("$.[*].classificationSocietyName").value(hasItem(DEFAULT_CLASSIFICATION_SOCIETY_NAME.toString())))
            .andExpect(jsonPath("$.[*].classificationSocietyNameAbbr").value(hasItem(DEFAULT_CLASSIFICATION_SOCIETY_NAME_ABBR.toString())))
            .andExpect(jsonPath("$.[*].sortNum").value(hasItem(DEFAULT_SORT_NUM)));

        // Check, that the count call also returns 1
        restClassificationSocietyMockMvc.perform(get("/api/classification-societies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultClassificationSocietyShouldNotBeFound(String filter) throws Exception {
        restClassificationSocietyMockMvc.perform(get("/api/classification-societies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClassificationSocietyMockMvc.perform(get("/api/classification-societies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingClassificationSociety() throws Exception {
        // Get the classificationSociety
        restClassificationSocietyMockMvc.perform(get("/api/classification-societies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassificationSociety() throws Exception {
        // Initialize the database
        classificationSocietyRepository.saveAndFlush(classificationSociety);

        int databaseSizeBeforeUpdate = classificationSocietyRepository.findAll().size();

        // Update the classificationSociety
        ClassificationSociety updatedClassificationSociety = classificationSocietyRepository.findById(classificationSociety.getId()).get();
        // Disconnect from session so that the updates on updatedClassificationSociety are not directly saved in db
        em.detach(updatedClassificationSociety);
        updatedClassificationSociety
            .classificationSocietyName(UPDATED_CLASSIFICATION_SOCIETY_NAME)
            .classificationSocietyNameAbbr(UPDATED_CLASSIFICATION_SOCIETY_NAME_ABBR)
            .sortNum(UPDATED_SORT_NUM);
        ClassificationSocietyDTO classificationSocietyDTO = classificationSocietyMapper.toDto(updatedClassificationSociety);

        restClassificationSocietyMockMvc.perform(put("/api/classification-societies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classificationSocietyDTO)))
            .andExpect(status().isOk());

        // Validate the ClassificationSociety in the database
        List<ClassificationSociety> classificationSocietyList = classificationSocietyRepository.findAll();
        assertThat(classificationSocietyList).hasSize(databaseSizeBeforeUpdate);
        ClassificationSociety testClassificationSociety = classificationSocietyList.get(classificationSocietyList.size() - 1);
        assertThat(testClassificationSociety.getClassificationSocietyName()).isEqualTo(UPDATED_CLASSIFICATION_SOCIETY_NAME);
        assertThat(testClassificationSociety.getClassificationSocietyNameAbbr()).isEqualTo(UPDATED_CLASSIFICATION_SOCIETY_NAME_ABBR);
        assertThat(testClassificationSociety.getSortNum()).isEqualTo(UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void updateNonExistingClassificationSociety() throws Exception {
        int databaseSizeBeforeUpdate = classificationSocietyRepository.findAll().size();

        // Create the ClassificationSociety
        ClassificationSocietyDTO classificationSocietyDTO = classificationSocietyMapper.toDto(classificationSociety);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassificationSocietyMockMvc.perform(put("/api/classification-societies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classificationSocietyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassificationSociety in the database
        List<ClassificationSociety> classificationSocietyList = classificationSocietyRepository.findAll();
        assertThat(classificationSocietyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClassificationSociety() throws Exception {
        // Initialize the database
        classificationSocietyRepository.saveAndFlush(classificationSociety);

        int databaseSizeBeforeDelete = classificationSocietyRepository.findAll().size();

        // Get the classificationSociety
        restClassificationSocietyMockMvc.perform(delete("/api/classification-societies/{id}", classificationSociety.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClassificationSociety> classificationSocietyList = classificationSocietyRepository.findAll();
        assertThat(classificationSocietyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassificationSociety.class);
        ClassificationSociety classificationSociety1 = new ClassificationSociety();
        classificationSociety1.setId(1L);
        ClassificationSociety classificationSociety2 = new ClassificationSociety();
        classificationSociety2.setId(classificationSociety1.getId());
        assertThat(classificationSociety1).isEqualTo(classificationSociety2);
        classificationSociety2.setId(2L);
        assertThat(classificationSociety1).isNotEqualTo(classificationSociety2);
        classificationSociety1.setId(null);
        assertThat(classificationSociety1).isNotEqualTo(classificationSociety2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassificationSocietyDTO.class);
        ClassificationSocietyDTO classificationSocietyDTO1 = new ClassificationSocietyDTO();
        classificationSocietyDTO1.setId(1L);
        ClassificationSocietyDTO classificationSocietyDTO2 = new ClassificationSocietyDTO();
        assertThat(classificationSocietyDTO1).isNotEqualTo(classificationSocietyDTO2);
        classificationSocietyDTO2.setId(classificationSocietyDTO1.getId());
        assertThat(classificationSocietyDTO1).isEqualTo(classificationSocietyDTO2);
        classificationSocietyDTO2.setId(2L);
        assertThat(classificationSocietyDTO1).isNotEqualTo(classificationSocietyDTO2);
        classificationSocietyDTO1.setId(null);
        assertThat(classificationSocietyDTO1).isNotEqualTo(classificationSocietyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(classificationSocietyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(classificationSocietyMapper.fromId(null)).isNull();
    }
}

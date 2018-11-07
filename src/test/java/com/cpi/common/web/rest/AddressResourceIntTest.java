package com.cpi.common.web.rest;

import com.cpi.common.CpicommonApp;

import com.cpi.common.config.SecurityBeanOverrideConfiguration;

import com.cpi.common.domain.Address;
import com.cpi.common.domain.Company;
import com.cpi.common.domain.AddressType;
import com.cpi.common.repository.AddressRepository;
import com.cpi.common.service.AddressService;
import com.cpi.common.service.dto.AddressDTO;
import com.cpi.common.service.mapper.AddressMapper;
import com.cpi.common.web.rest.errors.ExceptionTranslator;
import com.cpi.common.service.dto.AddressCriteria;
import com.cpi.common.service.AddressQueryService;

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
 * Test class for the AddressResource REST controller.
 *
 * @see AddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, CpicommonApp.class})
public class AddressResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BUILDING = "AAAAAAAAAA";
    private static final String UPDATED_BUILDING = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_POSTBOX = "AAAAAAAAAA";
    private static final String UPDATED_POSTBOX = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP = "AAAAAAAAAA";
    private static final String UPDATED_ZIP = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    @Autowired
    private AddressRepository addressRepository;


    @Autowired
    private AddressMapper addressMapper;
    

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressQueryService addressQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAddressMockMvc;

    private Address address;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AddressResource addressResource = new AddressResource(addressService, addressQueryService);
        this.restAddressMockMvc = MockMvcBuilders.standaloneSetup(addressResource)
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
    public static Address createEntity(EntityManager em) {
        Address address = new Address()
            .name(DEFAULT_NAME)
            .building(DEFAULT_BUILDING)
            .street(DEFAULT_STREET)
            .postbox(DEFAULT_POSTBOX)
            .zip(DEFAULT_ZIP)
            .city(DEFAULT_CITY);
        return address;
    }

    @Before
    public void initTest() {
        address = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddress() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);
        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isCreated());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate + 1);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAddress.getBuilding()).isEqualTo(DEFAULT_BUILDING);
        assertThat(testAddress.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testAddress.getPostbox()).isEqualTo(DEFAULT_POSTBOX);
        assertThat(testAddress.getZip()).isEqualTo(DEFAULT_ZIP);
        assertThat(testAddress.getCity()).isEqualTo(DEFAULT_CITY);
    }

    @Test
    @Transactional
    public void createAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();

        // Create the Address with an existing ID
        address.setId(1L);
        AddressDTO addressDTO = addressMapper.toDto(address);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAddresses() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList
        restAddressMockMvc.perform(get("/api/addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].building").value(hasItem(DEFAULT_BUILDING.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].postbox").value(hasItem(DEFAULT_POSTBOX.toString())))
            .andExpect(jsonPath("$.[*].zip").value(hasItem(DEFAULT_ZIP.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())));
    }
    

    @Test
    @Transactional
    public void getAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get the address
        restAddressMockMvc.perform(get("/api/addresses/{id}", address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(address.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.building").value(DEFAULT_BUILDING.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.postbox").value(DEFAULT_POSTBOX.toString()))
            .andExpect(jsonPath("$.zip").value(DEFAULT_ZIP.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()));
    }

    @Test
    @Transactional
    public void getAllAddressesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where name equals to DEFAULT_NAME
        defaultAddressShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the addressList where name equals to UPDATED_NAME
        defaultAddressShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAddressesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAddressShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the addressList where name equals to UPDATED_NAME
        defaultAddressShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAddressesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where name is not null
        defaultAddressShouldBeFound("name.specified=true");

        // Get all the addressList where name is null
        defaultAddressShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressesByBuildingIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where building equals to DEFAULT_BUILDING
        defaultAddressShouldBeFound("building.equals=" + DEFAULT_BUILDING);

        // Get all the addressList where building equals to UPDATED_BUILDING
        defaultAddressShouldNotBeFound("building.equals=" + UPDATED_BUILDING);
    }

    @Test
    @Transactional
    public void getAllAddressesByBuildingIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where building in DEFAULT_BUILDING or UPDATED_BUILDING
        defaultAddressShouldBeFound("building.in=" + DEFAULT_BUILDING + "," + UPDATED_BUILDING);

        // Get all the addressList where building equals to UPDATED_BUILDING
        defaultAddressShouldNotBeFound("building.in=" + UPDATED_BUILDING);
    }

    @Test
    @Transactional
    public void getAllAddressesByBuildingIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where building is not null
        defaultAddressShouldBeFound("building.specified=true");

        // Get all the addressList where building is null
        defaultAddressShouldNotBeFound("building.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressesByStreetIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where street equals to DEFAULT_STREET
        defaultAddressShouldBeFound("street.equals=" + DEFAULT_STREET);

        // Get all the addressList where street equals to UPDATED_STREET
        defaultAddressShouldNotBeFound("street.equals=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllAddressesByStreetIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where street in DEFAULT_STREET or UPDATED_STREET
        defaultAddressShouldBeFound("street.in=" + DEFAULT_STREET + "," + UPDATED_STREET);

        // Get all the addressList where street equals to UPDATED_STREET
        defaultAddressShouldNotBeFound("street.in=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllAddressesByStreetIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where street is not null
        defaultAddressShouldBeFound("street.specified=true");

        // Get all the addressList where street is null
        defaultAddressShouldNotBeFound("street.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressesByPostboxIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where postbox equals to DEFAULT_POSTBOX
        defaultAddressShouldBeFound("postbox.equals=" + DEFAULT_POSTBOX);

        // Get all the addressList where postbox equals to UPDATED_POSTBOX
        defaultAddressShouldNotBeFound("postbox.equals=" + UPDATED_POSTBOX);
    }

    @Test
    @Transactional
    public void getAllAddressesByPostboxIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where postbox in DEFAULT_POSTBOX or UPDATED_POSTBOX
        defaultAddressShouldBeFound("postbox.in=" + DEFAULT_POSTBOX + "," + UPDATED_POSTBOX);

        // Get all the addressList where postbox equals to UPDATED_POSTBOX
        defaultAddressShouldNotBeFound("postbox.in=" + UPDATED_POSTBOX);
    }

    @Test
    @Transactional
    public void getAllAddressesByPostboxIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where postbox is not null
        defaultAddressShouldBeFound("postbox.specified=true");

        // Get all the addressList where postbox is null
        defaultAddressShouldNotBeFound("postbox.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressesByZipIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where zip equals to DEFAULT_ZIP
        defaultAddressShouldBeFound("zip.equals=" + DEFAULT_ZIP);

        // Get all the addressList where zip equals to UPDATED_ZIP
        defaultAddressShouldNotBeFound("zip.equals=" + UPDATED_ZIP);
    }

    @Test
    @Transactional
    public void getAllAddressesByZipIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where zip in DEFAULT_ZIP or UPDATED_ZIP
        defaultAddressShouldBeFound("zip.in=" + DEFAULT_ZIP + "," + UPDATED_ZIP);

        // Get all the addressList where zip equals to UPDATED_ZIP
        defaultAddressShouldNotBeFound("zip.in=" + UPDATED_ZIP);
    }

    @Test
    @Transactional
    public void getAllAddressesByZipIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where zip is not null
        defaultAddressShouldBeFound("zip.specified=true");

        // Get all the addressList where zip is null
        defaultAddressShouldNotBeFound("zip.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where city equals to DEFAULT_CITY
        defaultAddressShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the addressList where city equals to UPDATED_CITY
        defaultAddressShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllAddressesByCityIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where city in DEFAULT_CITY or UPDATED_CITY
        defaultAddressShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the addressList where city equals to UPDATED_CITY
        defaultAddressShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllAddressesByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where city is not null
        defaultAddressShouldBeFound("city.specified=true");

        // Get all the addressList where city is null
        defaultAddressShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressesByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        Company company = CompanyResourceIntTest.createEntity(em);
        em.persist(company);
        em.flush();
        address.setCompany(company);
        addressRepository.saveAndFlush(address);
        Long companyId = company.getId();

        // Get all the addressList where company equals to companyId
        defaultAddressShouldBeFound("companyId.equals=" + companyId);

        // Get all the addressList where company equals to companyId + 1
        defaultAddressShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }


    @Test
    @Transactional
    public void getAllAddressesByAddressTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        AddressType addressType = AddressTypeResourceIntTest.createEntity(em);
        em.persist(addressType);
        em.flush();
        address.setAddressType(addressType);
        addressRepository.saveAndFlush(address);
        Long addressTypeId = addressType.getId();

        // Get all the addressList where addressType equals to addressTypeId
        defaultAddressShouldBeFound("addressTypeId.equals=" + addressTypeId);

        // Get all the addressList where addressType equals to addressTypeId + 1
        defaultAddressShouldNotBeFound("addressTypeId.equals=" + (addressTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAddressShouldBeFound(String filter) throws Exception {
        restAddressMockMvc.perform(get("/api/addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].building").value(hasItem(DEFAULT_BUILDING.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].postbox").value(hasItem(DEFAULT_POSTBOX.toString())))
            .andExpect(jsonPath("$.[*].zip").value(hasItem(DEFAULT_ZIP.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultAddressShouldNotBeFound(String filter) throws Exception {
        restAddressMockMvc.perform(get("/api/addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingAddress() throws Exception {
        // Get the address
        restAddressMockMvc.perform(get("/api/addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address
        Address updatedAddress = addressRepository.findById(address.getId()).get();
        // Disconnect from session so that the updates on updatedAddress are not directly saved in db
        em.detach(updatedAddress);
        updatedAddress
            .name(UPDATED_NAME)
            .building(UPDATED_BUILDING)
            .street(UPDATED_STREET)
            .postbox(UPDATED_POSTBOX)
            .zip(UPDATED_ZIP)
            .city(UPDATED_CITY);
        AddressDTO addressDTO = addressMapper.toDto(updatedAddress);

        restAddressMockMvc.perform(put("/api/addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAddress.getBuilding()).isEqualTo(UPDATED_BUILDING);
        assertThat(testAddress.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testAddress.getPostbox()).isEqualTo(UPDATED_POSTBOX);
        assertThat(testAddress.getZip()).isEqualTo(UPDATED_ZIP);
        assertThat(testAddress.getCity()).isEqualTo(UPDATED_CITY);
    }

    @Test
    @Transactional
    public void updateNonExistingAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restAddressMockMvc.perform(put("/api/addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeDelete = addressRepository.findAll().size();

        // Get the address
        restAddressMockMvc.perform(delete("/api/addresses/{id}", address.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Address.class);
        Address address1 = new Address();
        address1.setId(1L);
        Address address2 = new Address();
        address2.setId(address1.getId());
        assertThat(address1).isEqualTo(address2);
        address2.setId(2L);
        assertThat(address1).isNotEqualTo(address2);
        address1.setId(null);
        assertThat(address1).isNotEqualTo(address2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressDTO.class);
        AddressDTO addressDTO1 = new AddressDTO();
        addressDTO1.setId(1L);
        AddressDTO addressDTO2 = new AddressDTO();
        assertThat(addressDTO1).isNotEqualTo(addressDTO2);
        addressDTO2.setId(addressDTO1.getId());
        assertThat(addressDTO1).isEqualTo(addressDTO2);
        addressDTO2.setId(2L);
        assertThat(addressDTO1).isNotEqualTo(addressDTO2);
        addressDTO1.setId(null);
        assertThat(addressDTO1).isNotEqualTo(addressDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(addressMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(addressMapper.fromId(null)).isNull();
    }
}

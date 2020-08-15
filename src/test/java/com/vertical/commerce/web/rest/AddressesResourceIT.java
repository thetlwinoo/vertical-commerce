package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.Addresses;
import com.vertical.commerce.domain.Regions;
import com.vertical.commerce.domain.Cities;
import com.vertical.commerce.domain.Townships;
import com.vertical.commerce.domain.AddressTypes;
import com.vertical.commerce.domain.Customers;
import com.vertical.commerce.domain.Suppliers;
import com.vertical.commerce.repository.AddressesRepository;
import com.vertical.commerce.service.AddressesService;
import com.vertical.commerce.service.dto.AddressesDTO;
import com.vertical.commerce.service.mapper.AddressesMapper;
import com.vertical.commerce.service.dto.AddressesCriteria;
import com.vertical.commerce.service.AddressesQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AddressesResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class AddressesResourceIT {

    private static final String DEFAULT_CONTACT_PERSON = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PERSON = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_EMAIL_ADDRESS = "}e74,d@\"_~.z`";
    private static final String UPDATED_CONTACT_EMAIL_ADDRESS = "T[@a.I]fy";

    private static final String DEFAULT_ADDRESS_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AddressesRepository addressesRepository;

    @Autowired
    private AddressesMapper addressesMapper;

    @Autowired
    private AddressesService addressesService;

    @Autowired
    private AddressesQueryService addressesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAddressesMockMvc;

    private Addresses addresses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Addresses createEntity(EntityManager em) {
        Addresses addresses = new Addresses()
            .contactPerson(DEFAULT_CONTACT_PERSON)
            .contactNumber(DEFAULT_CONTACT_NUMBER)
            .contactEmailAddress(DEFAULT_CONTACT_EMAIL_ADDRESS)
            .addressLine1(DEFAULT_ADDRESS_LINE_1)
            .addressLine2(DEFAULT_ADDRESS_LINE_2)
            .postalCode(DEFAULT_POSTAL_CODE)
            .description(DEFAULT_DESCRIPTION)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return addresses;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Addresses createUpdatedEntity(EntityManager em) {
        Addresses addresses = new Addresses()
            .contactPerson(UPDATED_CONTACT_PERSON)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .contactEmailAddress(UPDATED_CONTACT_EMAIL_ADDRESS)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .postalCode(UPDATED_POSTAL_CODE)
            .description(UPDATED_DESCRIPTION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return addresses;
    }

    @BeforeEach
    public void initTest() {
        addresses = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddresses() throws Exception {
        int databaseSizeBeforeCreate = addressesRepository.findAll().size();
        // Create the Addresses
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);
        restAddressesMockMvc.perform(post("/api/addresses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressesDTO)))
            .andExpect(status().isCreated());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeCreate + 1);
        Addresses testAddresses = addressesList.get(addressesList.size() - 1);
        assertThat(testAddresses.getContactPerson()).isEqualTo(DEFAULT_CONTACT_PERSON);
        assertThat(testAddresses.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testAddresses.getContactEmailAddress()).isEqualTo(DEFAULT_CONTACT_EMAIL_ADDRESS);
        assertThat(testAddresses.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE_1);
        assertThat(testAddresses.getAddressLine2()).isEqualTo(DEFAULT_ADDRESS_LINE_2);
        assertThat(testAddresses.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testAddresses.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAddresses.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testAddresses.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createAddressesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressesRepository.findAll().size();

        // Create the Addresses with an existing ID
        addresses.setId(1L);
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressesMockMvc.perform(post("/api/addresses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAddressLine1IsRequired() throws Exception {
        int databaseSizeBeforeTest = addressesRepository.findAll().size();
        // set the field null
        addresses.setAddressLine1(null);

        // Create the Addresses, which fails.
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);


        restAddressesMockMvc.perform(post("/api/addresses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressesDTO)))
            .andExpect(status().isBadRequest());

        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressesRepository.findAll().size();
        // set the field null
        addresses.setValidFrom(null);

        // Create the Addresses, which fails.
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);


        restAddressesMockMvc.perform(post("/api/addresses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressesDTO)))
            .andExpect(status().isBadRequest());

        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAddresses() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList
        restAddressesMockMvc.perform(get("/api/addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addresses.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactPerson").value(hasItem(DEFAULT_CONTACT_PERSON)))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER)))
            .andExpect(jsonPath("$.[*].contactEmailAddress").value(hasItem(DEFAULT_CONTACT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1)))
            .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getAddresses() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get the addresses
        restAddressesMockMvc.perform(get("/api/addresses/{id}", addresses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(addresses.getId().intValue()))
            .andExpect(jsonPath("$.contactPerson").value(DEFAULT_CONTACT_PERSON))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER))
            .andExpect(jsonPath("$.contactEmailAddress").value(DEFAULT_CONTACT_EMAIL_ADDRESS))
            .andExpect(jsonPath("$.addressLine1").value(DEFAULT_ADDRESS_LINE_1))
            .andExpect(jsonPath("$.addressLine2").value(DEFAULT_ADDRESS_LINE_2))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getAddressesByIdFiltering() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        Long id = addresses.getId();

        defaultAddressesShouldBeFound("id.equals=" + id);
        defaultAddressesShouldNotBeFound("id.notEquals=" + id);

        defaultAddressesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAddressesShouldNotBeFound("id.greaterThan=" + id);

        defaultAddressesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAddressesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAddressesByContactPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactPerson equals to DEFAULT_CONTACT_PERSON
        defaultAddressesShouldBeFound("contactPerson.equals=" + DEFAULT_CONTACT_PERSON);

        // Get all the addressesList where contactPerson equals to UPDATED_CONTACT_PERSON
        defaultAddressesShouldNotBeFound("contactPerson.equals=" + UPDATED_CONTACT_PERSON);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactPersonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactPerson not equals to DEFAULT_CONTACT_PERSON
        defaultAddressesShouldNotBeFound("contactPerson.notEquals=" + DEFAULT_CONTACT_PERSON);

        // Get all the addressesList where contactPerson not equals to UPDATED_CONTACT_PERSON
        defaultAddressesShouldBeFound("contactPerson.notEquals=" + UPDATED_CONTACT_PERSON);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactPersonIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactPerson in DEFAULT_CONTACT_PERSON or UPDATED_CONTACT_PERSON
        defaultAddressesShouldBeFound("contactPerson.in=" + DEFAULT_CONTACT_PERSON + "," + UPDATED_CONTACT_PERSON);

        // Get all the addressesList where contactPerson equals to UPDATED_CONTACT_PERSON
        defaultAddressesShouldNotBeFound("contactPerson.in=" + UPDATED_CONTACT_PERSON);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactPersonIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactPerson is not null
        defaultAddressesShouldBeFound("contactPerson.specified=true");

        // Get all the addressesList where contactPerson is null
        defaultAddressesShouldNotBeFound("contactPerson.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByContactPersonContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactPerson contains DEFAULT_CONTACT_PERSON
        defaultAddressesShouldBeFound("contactPerson.contains=" + DEFAULT_CONTACT_PERSON);

        // Get all the addressesList where contactPerson contains UPDATED_CONTACT_PERSON
        defaultAddressesShouldNotBeFound("contactPerson.contains=" + UPDATED_CONTACT_PERSON);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactPersonNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactPerson does not contain DEFAULT_CONTACT_PERSON
        defaultAddressesShouldNotBeFound("contactPerson.doesNotContain=" + DEFAULT_CONTACT_PERSON);

        // Get all the addressesList where contactPerson does not contain UPDATED_CONTACT_PERSON
        defaultAddressesShouldBeFound("contactPerson.doesNotContain=" + UPDATED_CONTACT_PERSON);
    }


    @Test
    @Transactional
    public void getAllAddressesByContactNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactNumber equals to DEFAULT_CONTACT_NUMBER
        defaultAddressesShouldBeFound("contactNumber.equals=" + DEFAULT_CONTACT_NUMBER);

        // Get all the addressesList where contactNumber equals to UPDATED_CONTACT_NUMBER
        defaultAddressesShouldNotBeFound("contactNumber.equals=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactNumber not equals to DEFAULT_CONTACT_NUMBER
        defaultAddressesShouldNotBeFound("contactNumber.notEquals=" + DEFAULT_CONTACT_NUMBER);

        // Get all the addressesList where contactNumber not equals to UPDATED_CONTACT_NUMBER
        defaultAddressesShouldBeFound("contactNumber.notEquals=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactNumberIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactNumber in DEFAULT_CONTACT_NUMBER or UPDATED_CONTACT_NUMBER
        defaultAddressesShouldBeFound("contactNumber.in=" + DEFAULT_CONTACT_NUMBER + "," + UPDATED_CONTACT_NUMBER);

        // Get all the addressesList where contactNumber equals to UPDATED_CONTACT_NUMBER
        defaultAddressesShouldNotBeFound("contactNumber.in=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactNumber is not null
        defaultAddressesShouldBeFound("contactNumber.specified=true");

        // Get all the addressesList where contactNumber is null
        defaultAddressesShouldNotBeFound("contactNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByContactNumberContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactNumber contains DEFAULT_CONTACT_NUMBER
        defaultAddressesShouldBeFound("contactNumber.contains=" + DEFAULT_CONTACT_NUMBER);

        // Get all the addressesList where contactNumber contains UPDATED_CONTACT_NUMBER
        defaultAddressesShouldNotBeFound("contactNumber.contains=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactNumberNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactNumber does not contain DEFAULT_CONTACT_NUMBER
        defaultAddressesShouldNotBeFound("contactNumber.doesNotContain=" + DEFAULT_CONTACT_NUMBER);

        // Get all the addressesList where contactNumber does not contain UPDATED_CONTACT_NUMBER
        defaultAddressesShouldBeFound("contactNumber.doesNotContain=" + UPDATED_CONTACT_NUMBER);
    }


    @Test
    @Transactional
    public void getAllAddressesByContactEmailAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactEmailAddress equals to DEFAULT_CONTACT_EMAIL_ADDRESS
        defaultAddressesShouldBeFound("contactEmailAddress.equals=" + DEFAULT_CONTACT_EMAIL_ADDRESS);

        // Get all the addressesList where contactEmailAddress equals to UPDATED_CONTACT_EMAIL_ADDRESS
        defaultAddressesShouldNotBeFound("contactEmailAddress.equals=" + UPDATED_CONTACT_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactEmailAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactEmailAddress not equals to DEFAULT_CONTACT_EMAIL_ADDRESS
        defaultAddressesShouldNotBeFound("contactEmailAddress.notEquals=" + DEFAULT_CONTACT_EMAIL_ADDRESS);

        // Get all the addressesList where contactEmailAddress not equals to UPDATED_CONTACT_EMAIL_ADDRESS
        defaultAddressesShouldBeFound("contactEmailAddress.notEquals=" + UPDATED_CONTACT_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactEmailAddressIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactEmailAddress in DEFAULT_CONTACT_EMAIL_ADDRESS or UPDATED_CONTACT_EMAIL_ADDRESS
        defaultAddressesShouldBeFound("contactEmailAddress.in=" + DEFAULT_CONTACT_EMAIL_ADDRESS + "," + UPDATED_CONTACT_EMAIL_ADDRESS);

        // Get all the addressesList where contactEmailAddress equals to UPDATED_CONTACT_EMAIL_ADDRESS
        defaultAddressesShouldNotBeFound("contactEmailAddress.in=" + UPDATED_CONTACT_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactEmailAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactEmailAddress is not null
        defaultAddressesShouldBeFound("contactEmailAddress.specified=true");

        // Get all the addressesList where contactEmailAddress is null
        defaultAddressesShouldNotBeFound("contactEmailAddress.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByContactEmailAddressContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactEmailAddress contains DEFAULT_CONTACT_EMAIL_ADDRESS
        defaultAddressesShouldBeFound("contactEmailAddress.contains=" + DEFAULT_CONTACT_EMAIL_ADDRESS);

        // Get all the addressesList where contactEmailAddress contains UPDATED_CONTACT_EMAIL_ADDRESS
        defaultAddressesShouldNotBeFound("contactEmailAddress.contains=" + UPDATED_CONTACT_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactEmailAddressNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactEmailAddress does not contain DEFAULT_CONTACT_EMAIL_ADDRESS
        defaultAddressesShouldNotBeFound("contactEmailAddress.doesNotContain=" + DEFAULT_CONTACT_EMAIL_ADDRESS);

        // Get all the addressesList where contactEmailAddress does not contain UPDATED_CONTACT_EMAIL_ADDRESS
        defaultAddressesShouldBeFound("contactEmailAddress.doesNotContain=" + UPDATED_CONTACT_EMAIL_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllAddressesByAddressLine1IsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine1 equals to DEFAULT_ADDRESS_LINE_1
        defaultAddressesShouldBeFound("addressLine1.equals=" + DEFAULT_ADDRESS_LINE_1);

        // Get all the addressesList where addressLine1 equals to UPDATED_ADDRESS_LINE_1
        defaultAddressesShouldNotBeFound("addressLine1.equals=" + UPDATED_ADDRESS_LINE_1);
    }

    @Test
    @Transactional
    public void getAllAddressesByAddressLine1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine1 not equals to DEFAULT_ADDRESS_LINE_1
        defaultAddressesShouldNotBeFound("addressLine1.notEquals=" + DEFAULT_ADDRESS_LINE_1);

        // Get all the addressesList where addressLine1 not equals to UPDATED_ADDRESS_LINE_1
        defaultAddressesShouldBeFound("addressLine1.notEquals=" + UPDATED_ADDRESS_LINE_1);
    }

    @Test
    @Transactional
    public void getAllAddressesByAddressLine1IsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine1 in DEFAULT_ADDRESS_LINE_1 or UPDATED_ADDRESS_LINE_1
        defaultAddressesShouldBeFound("addressLine1.in=" + DEFAULT_ADDRESS_LINE_1 + "," + UPDATED_ADDRESS_LINE_1);

        // Get all the addressesList where addressLine1 equals to UPDATED_ADDRESS_LINE_1
        defaultAddressesShouldNotBeFound("addressLine1.in=" + UPDATED_ADDRESS_LINE_1);
    }

    @Test
    @Transactional
    public void getAllAddressesByAddressLine1IsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine1 is not null
        defaultAddressesShouldBeFound("addressLine1.specified=true");

        // Get all the addressesList where addressLine1 is null
        defaultAddressesShouldNotBeFound("addressLine1.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByAddressLine1ContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine1 contains DEFAULT_ADDRESS_LINE_1
        defaultAddressesShouldBeFound("addressLine1.contains=" + DEFAULT_ADDRESS_LINE_1);

        // Get all the addressesList where addressLine1 contains UPDATED_ADDRESS_LINE_1
        defaultAddressesShouldNotBeFound("addressLine1.contains=" + UPDATED_ADDRESS_LINE_1);
    }

    @Test
    @Transactional
    public void getAllAddressesByAddressLine1NotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine1 does not contain DEFAULT_ADDRESS_LINE_1
        defaultAddressesShouldNotBeFound("addressLine1.doesNotContain=" + DEFAULT_ADDRESS_LINE_1);

        // Get all the addressesList where addressLine1 does not contain UPDATED_ADDRESS_LINE_1
        defaultAddressesShouldBeFound("addressLine1.doesNotContain=" + UPDATED_ADDRESS_LINE_1);
    }


    @Test
    @Transactional
    public void getAllAddressesByAddressLine2IsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine2 equals to DEFAULT_ADDRESS_LINE_2
        defaultAddressesShouldBeFound("addressLine2.equals=" + DEFAULT_ADDRESS_LINE_2);

        // Get all the addressesList where addressLine2 equals to UPDATED_ADDRESS_LINE_2
        defaultAddressesShouldNotBeFound("addressLine2.equals=" + UPDATED_ADDRESS_LINE_2);
    }

    @Test
    @Transactional
    public void getAllAddressesByAddressLine2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine2 not equals to DEFAULT_ADDRESS_LINE_2
        defaultAddressesShouldNotBeFound("addressLine2.notEquals=" + DEFAULT_ADDRESS_LINE_2);

        // Get all the addressesList where addressLine2 not equals to UPDATED_ADDRESS_LINE_2
        defaultAddressesShouldBeFound("addressLine2.notEquals=" + UPDATED_ADDRESS_LINE_2);
    }

    @Test
    @Transactional
    public void getAllAddressesByAddressLine2IsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine2 in DEFAULT_ADDRESS_LINE_2 or UPDATED_ADDRESS_LINE_2
        defaultAddressesShouldBeFound("addressLine2.in=" + DEFAULT_ADDRESS_LINE_2 + "," + UPDATED_ADDRESS_LINE_2);

        // Get all the addressesList where addressLine2 equals to UPDATED_ADDRESS_LINE_2
        defaultAddressesShouldNotBeFound("addressLine2.in=" + UPDATED_ADDRESS_LINE_2);
    }

    @Test
    @Transactional
    public void getAllAddressesByAddressLine2IsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine2 is not null
        defaultAddressesShouldBeFound("addressLine2.specified=true");

        // Get all the addressesList where addressLine2 is null
        defaultAddressesShouldNotBeFound("addressLine2.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByAddressLine2ContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine2 contains DEFAULT_ADDRESS_LINE_2
        defaultAddressesShouldBeFound("addressLine2.contains=" + DEFAULT_ADDRESS_LINE_2);

        // Get all the addressesList where addressLine2 contains UPDATED_ADDRESS_LINE_2
        defaultAddressesShouldNotBeFound("addressLine2.contains=" + UPDATED_ADDRESS_LINE_2);
    }

    @Test
    @Transactional
    public void getAllAddressesByAddressLine2NotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine2 does not contain DEFAULT_ADDRESS_LINE_2
        defaultAddressesShouldNotBeFound("addressLine2.doesNotContain=" + DEFAULT_ADDRESS_LINE_2);

        // Get all the addressesList where addressLine2 does not contain UPDATED_ADDRESS_LINE_2
        defaultAddressesShouldBeFound("addressLine2.doesNotContain=" + UPDATED_ADDRESS_LINE_2);
    }


    @Test
    @Transactional
    public void getAllAddressesByPostalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where postalCode equals to DEFAULT_POSTAL_CODE
        defaultAddressesShouldBeFound("postalCode.equals=" + DEFAULT_POSTAL_CODE);

        // Get all the addressesList where postalCode equals to UPDATED_POSTAL_CODE
        defaultAddressesShouldNotBeFound("postalCode.equals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPostalCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where postalCode not equals to DEFAULT_POSTAL_CODE
        defaultAddressesShouldNotBeFound("postalCode.notEquals=" + DEFAULT_POSTAL_CODE);

        // Get all the addressesList where postalCode not equals to UPDATED_POSTAL_CODE
        defaultAddressesShouldBeFound("postalCode.notEquals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPostalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where postalCode in DEFAULT_POSTAL_CODE or UPDATED_POSTAL_CODE
        defaultAddressesShouldBeFound("postalCode.in=" + DEFAULT_POSTAL_CODE + "," + UPDATED_POSTAL_CODE);

        // Get all the addressesList where postalCode equals to UPDATED_POSTAL_CODE
        defaultAddressesShouldNotBeFound("postalCode.in=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPostalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where postalCode is not null
        defaultAddressesShouldBeFound("postalCode.specified=true");

        // Get all the addressesList where postalCode is null
        defaultAddressesShouldNotBeFound("postalCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByPostalCodeContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where postalCode contains DEFAULT_POSTAL_CODE
        defaultAddressesShouldBeFound("postalCode.contains=" + DEFAULT_POSTAL_CODE);

        // Get all the addressesList where postalCode contains UPDATED_POSTAL_CODE
        defaultAddressesShouldNotBeFound("postalCode.contains=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPostalCodeNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where postalCode does not contain DEFAULT_POSTAL_CODE
        defaultAddressesShouldNotBeFound("postalCode.doesNotContain=" + DEFAULT_POSTAL_CODE);

        // Get all the addressesList where postalCode does not contain UPDATED_POSTAL_CODE
        defaultAddressesShouldBeFound("postalCode.doesNotContain=" + UPDATED_POSTAL_CODE);
    }


    @Test
    @Transactional
    public void getAllAddressesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where description equals to DEFAULT_DESCRIPTION
        defaultAddressesShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the addressesList where description equals to UPDATED_DESCRIPTION
        defaultAddressesShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAddressesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where description not equals to DEFAULT_DESCRIPTION
        defaultAddressesShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the addressesList where description not equals to UPDATED_DESCRIPTION
        defaultAddressesShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAddressesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultAddressesShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the addressesList where description equals to UPDATED_DESCRIPTION
        defaultAddressesShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAddressesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where description is not null
        defaultAddressesShouldBeFound("description.specified=true");

        // Get all the addressesList where description is null
        defaultAddressesShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where description contains DEFAULT_DESCRIPTION
        defaultAddressesShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the addressesList where description contains UPDATED_DESCRIPTION
        defaultAddressesShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAddressesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where description does not contain DEFAULT_DESCRIPTION
        defaultAddressesShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the addressesList where description does not contain UPDATED_DESCRIPTION
        defaultAddressesShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllAddressesByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where validFrom equals to DEFAULT_VALID_FROM
        defaultAddressesShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the addressesList where validFrom equals to UPDATED_VALID_FROM
        defaultAddressesShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllAddressesByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where validFrom not equals to DEFAULT_VALID_FROM
        defaultAddressesShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the addressesList where validFrom not equals to UPDATED_VALID_FROM
        defaultAddressesShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllAddressesByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultAddressesShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the addressesList where validFrom equals to UPDATED_VALID_FROM
        defaultAddressesShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllAddressesByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where validFrom is not null
        defaultAddressesShouldBeFound("validFrom.specified=true");

        // Get all the addressesList where validFrom is null
        defaultAddressesShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressesByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where validTo equals to DEFAULT_VALID_TO
        defaultAddressesShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the addressesList where validTo equals to UPDATED_VALID_TO
        defaultAddressesShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllAddressesByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where validTo not equals to DEFAULT_VALID_TO
        defaultAddressesShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the addressesList where validTo not equals to UPDATED_VALID_TO
        defaultAddressesShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllAddressesByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultAddressesShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the addressesList where validTo equals to UPDATED_VALID_TO
        defaultAddressesShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllAddressesByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where validTo is not null
        defaultAddressesShouldBeFound("validTo.specified=true");

        // Get all the addressesList where validTo is null
        defaultAddressesShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressesByRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);
        Regions region = RegionsResourceIT.createEntity(em);
        em.persist(region);
        em.flush();
        addresses.setRegion(region);
        addressesRepository.saveAndFlush(addresses);
        Long regionId = region.getId();

        // Get all the addressesList where region equals to regionId
        defaultAddressesShouldBeFound("regionId.equals=" + regionId);

        // Get all the addressesList where region equals to regionId + 1
        defaultAddressesShouldNotBeFound("regionId.equals=" + (regionId + 1));
    }


    @Test
    @Transactional
    public void getAllAddressesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);
        Cities city = CitiesResourceIT.createEntity(em);
        em.persist(city);
        em.flush();
        addresses.setCity(city);
        addressesRepository.saveAndFlush(addresses);
        Long cityId = city.getId();

        // Get all the addressesList where city equals to cityId
        defaultAddressesShouldBeFound("cityId.equals=" + cityId);

        // Get all the addressesList where city equals to cityId + 1
        defaultAddressesShouldNotBeFound("cityId.equals=" + (cityId + 1));
    }


    @Test
    @Transactional
    public void getAllAddressesByTownshipIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);
        Townships township = TownshipsResourceIT.createEntity(em);
        em.persist(township);
        em.flush();
        addresses.setTownship(township);
        addressesRepository.saveAndFlush(addresses);
        Long townshipId = township.getId();

        // Get all the addressesList where township equals to townshipId
        defaultAddressesShouldBeFound("townshipId.equals=" + townshipId);

        // Get all the addressesList where township equals to townshipId + 1
        defaultAddressesShouldNotBeFound("townshipId.equals=" + (townshipId + 1));
    }


    @Test
    @Transactional
    public void getAllAddressesByAddressTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);
        AddressTypes addressType = AddressTypesResourceIT.createEntity(em);
        em.persist(addressType);
        em.flush();
        addresses.setAddressType(addressType);
        addressesRepository.saveAndFlush(addresses);
        Long addressTypeId = addressType.getId();

        // Get all the addressesList where addressType equals to addressTypeId
        defaultAddressesShouldBeFound("addressTypeId.equals=" + addressTypeId);

        // Get all the addressesList where addressType equals to addressTypeId + 1
        defaultAddressesShouldNotBeFound("addressTypeId.equals=" + (addressTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllAddressesByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);
        Customers customer = CustomersResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        addresses.setCustomer(customer);
        addressesRepository.saveAndFlush(addresses);
        Long customerId = customer.getId();

        // Get all the addressesList where customer equals to customerId
        defaultAddressesShouldBeFound("customerId.equals=" + customerId);

        // Get all the addressesList where customer equals to customerId + 1
        defaultAddressesShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }


    @Test
    @Transactional
    public void getAllAddressesBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);
        Suppliers supplier = SuppliersResourceIT.createEntity(em);
        em.persist(supplier);
        em.flush();
        addresses.setSupplier(supplier);
        addressesRepository.saveAndFlush(addresses);
        Long supplierId = supplier.getId();

        // Get all the addressesList where supplier equals to supplierId
        defaultAddressesShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the addressesList where supplier equals to supplierId + 1
        defaultAddressesShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAddressesShouldBeFound(String filter) throws Exception {
        restAddressesMockMvc.perform(get("/api/addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addresses.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactPerson").value(hasItem(DEFAULT_CONTACT_PERSON)))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER)))
            .andExpect(jsonPath("$.[*].contactEmailAddress").value(hasItem(DEFAULT_CONTACT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1)))
            .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restAddressesMockMvc.perform(get("/api/addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAddressesShouldNotBeFound(String filter) throws Exception {
        restAddressesMockMvc.perform(get("/api/addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAddressesMockMvc.perform(get("/api/addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAddresses() throws Exception {
        // Get the addresses
        restAddressesMockMvc.perform(get("/api/addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddresses() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();

        // Update the addresses
        Addresses updatedAddresses = addressesRepository.findById(addresses.getId()).get();
        // Disconnect from session so that the updates on updatedAddresses are not directly saved in db
        em.detach(updatedAddresses);
        updatedAddresses
            .contactPerson(UPDATED_CONTACT_PERSON)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .contactEmailAddress(UPDATED_CONTACT_EMAIL_ADDRESS)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .postalCode(UPDATED_POSTAL_CODE)
            .description(UPDATED_DESCRIPTION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        AddressesDTO addressesDTO = addressesMapper.toDto(updatedAddresses);

        restAddressesMockMvc.perform(put("/api/addresses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressesDTO)))
            .andExpect(status().isOk());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
        Addresses testAddresses = addressesList.get(addressesList.size() - 1);
        assertThat(testAddresses.getContactPerson()).isEqualTo(UPDATED_CONTACT_PERSON);
        assertThat(testAddresses.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testAddresses.getContactEmailAddress()).isEqualTo(UPDATED_CONTACT_EMAIL_ADDRESS);
        assertThat(testAddresses.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testAddresses.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testAddresses.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testAddresses.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAddresses.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testAddresses.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingAddresses() throws Exception {
        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();

        // Create the Addresses
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressesMockMvc.perform(put("/api/addresses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAddresses() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        int databaseSizeBeforeDelete = addressesRepository.findAll().size();

        // Delete the addresses
        restAddressesMockMvc.perform(delete("/api/addresses/{id}", addresses.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

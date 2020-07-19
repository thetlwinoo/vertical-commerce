package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.PersonEmailAddress;
import com.vertical.commerce.domain.People;
import com.vertical.commerce.repository.PersonEmailAddressRepository;
import com.vertical.commerce.service.PersonEmailAddressService;
import com.vertical.commerce.service.dto.PersonEmailAddressDTO;
import com.vertical.commerce.service.mapper.PersonEmailAddressMapper;
import com.vertical.commerce.service.dto.PersonEmailAddressCriteria;
import com.vertical.commerce.service.PersonEmailAddressQueryService;

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
 * Integration tests for the {@link PersonEmailAddressResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PersonEmailAddressResourceIT {

    private static final String DEFAULT_EMAIL_ADDRESS = "a@J??Z.f;k;";
    private static final String UPDATED_EMAIL_ADDRESS = "R5\"@|*.s(;B";

    private static final Boolean DEFAULT_DEFAULT_IND = false;
    private static final Boolean UPDATED_DEFAULT_IND = true;

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PersonEmailAddressRepository personEmailAddressRepository;

    @Autowired
    private PersonEmailAddressMapper personEmailAddressMapper;

    @Autowired
    private PersonEmailAddressService personEmailAddressService;

    @Autowired
    private PersonEmailAddressQueryService personEmailAddressQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonEmailAddressMockMvc;

    private PersonEmailAddress personEmailAddress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonEmailAddress createEntity(EntityManager em) {
        PersonEmailAddress personEmailAddress = new PersonEmailAddress()
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .defaultInd(DEFAULT_DEFAULT_IND)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return personEmailAddress;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonEmailAddress createUpdatedEntity(EntityManager em) {
        PersonEmailAddress personEmailAddress = new PersonEmailAddress()
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .defaultInd(UPDATED_DEFAULT_IND)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return personEmailAddress;
    }

    @BeforeEach
    public void initTest() {
        personEmailAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonEmailAddress() throws Exception {
        int databaseSizeBeforeCreate = personEmailAddressRepository.findAll().size();
        // Create the PersonEmailAddress
        PersonEmailAddressDTO personEmailAddressDTO = personEmailAddressMapper.toDto(personEmailAddress);
        restPersonEmailAddressMockMvc.perform(post("/api/person-email-addresses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personEmailAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonEmailAddress in the database
        List<PersonEmailAddress> personEmailAddressList = personEmailAddressRepository.findAll();
        assertThat(personEmailAddressList).hasSize(databaseSizeBeforeCreate + 1);
        PersonEmailAddress testPersonEmailAddress = personEmailAddressList.get(personEmailAddressList.size() - 1);
        assertThat(testPersonEmailAddress.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testPersonEmailAddress.isDefaultInd()).isEqualTo(DEFAULT_DEFAULT_IND);
        assertThat(testPersonEmailAddress.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testPersonEmailAddress.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createPersonEmailAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personEmailAddressRepository.findAll().size();

        // Create the PersonEmailAddress with an existing ID
        personEmailAddress.setId(1L);
        PersonEmailAddressDTO personEmailAddressDTO = personEmailAddressMapper.toDto(personEmailAddress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonEmailAddressMockMvc.perform(post("/api/person-email-addresses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personEmailAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonEmailAddress in the database
        List<PersonEmailAddress> personEmailAddressList = personEmailAddressRepository.findAll();
        assertThat(personEmailAddressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEmailAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = personEmailAddressRepository.findAll().size();
        // set the field null
        personEmailAddress.setEmailAddress(null);

        // Create the PersonEmailAddress, which fails.
        PersonEmailAddressDTO personEmailAddressDTO = personEmailAddressMapper.toDto(personEmailAddress);


        restPersonEmailAddressMockMvc.perform(post("/api/person-email-addresses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personEmailAddressDTO)))
            .andExpect(status().isBadRequest());

        List<PersonEmailAddress> personEmailAddressList = personEmailAddressRepository.findAll();
        assertThat(personEmailAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = personEmailAddressRepository.findAll().size();
        // set the field null
        personEmailAddress.setValidFrom(null);

        // Create the PersonEmailAddress, which fails.
        PersonEmailAddressDTO personEmailAddressDTO = personEmailAddressMapper.toDto(personEmailAddress);


        restPersonEmailAddressMockMvc.perform(post("/api/person-email-addresses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personEmailAddressDTO)))
            .andExpect(status().isBadRequest());

        List<PersonEmailAddress> personEmailAddressList = personEmailAddressRepository.findAll();
        assertThat(personEmailAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddresses() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList
        restPersonEmailAddressMockMvc.perform(get("/api/person-email-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personEmailAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].defaultInd").value(hasItem(DEFAULT_DEFAULT_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getPersonEmailAddress() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get the personEmailAddress
        restPersonEmailAddressMockMvc.perform(get("/api/person-email-addresses/{id}", personEmailAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personEmailAddress.getId().intValue()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS))
            .andExpect(jsonPath("$.defaultInd").value(DEFAULT_DEFAULT_IND.booleanValue()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getPersonEmailAddressesByIdFiltering() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        Long id = personEmailAddress.getId();

        defaultPersonEmailAddressShouldBeFound("id.equals=" + id);
        defaultPersonEmailAddressShouldNotBeFound("id.notEquals=" + id);

        defaultPersonEmailAddressShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPersonEmailAddressShouldNotBeFound("id.greaterThan=" + id);

        defaultPersonEmailAddressShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPersonEmailAddressShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPersonEmailAddressesByEmailAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where emailAddress equals to DEFAULT_EMAIL_ADDRESS
        defaultPersonEmailAddressShouldBeFound("emailAddress.equals=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the personEmailAddressList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultPersonEmailAddressShouldNotBeFound("emailAddress.equals=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByEmailAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where emailAddress not equals to DEFAULT_EMAIL_ADDRESS
        defaultPersonEmailAddressShouldNotBeFound("emailAddress.notEquals=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the personEmailAddressList where emailAddress not equals to UPDATED_EMAIL_ADDRESS
        defaultPersonEmailAddressShouldBeFound("emailAddress.notEquals=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByEmailAddressIsInShouldWork() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where emailAddress in DEFAULT_EMAIL_ADDRESS or UPDATED_EMAIL_ADDRESS
        defaultPersonEmailAddressShouldBeFound("emailAddress.in=" + DEFAULT_EMAIL_ADDRESS + "," + UPDATED_EMAIL_ADDRESS);

        // Get all the personEmailAddressList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultPersonEmailAddressShouldNotBeFound("emailAddress.in=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByEmailAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where emailAddress is not null
        defaultPersonEmailAddressShouldBeFound("emailAddress.specified=true");

        // Get all the personEmailAddressList where emailAddress is null
        defaultPersonEmailAddressShouldNotBeFound("emailAddress.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonEmailAddressesByEmailAddressContainsSomething() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where emailAddress contains DEFAULT_EMAIL_ADDRESS
        defaultPersonEmailAddressShouldBeFound("emailAddress.contains=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the personEmailAddressList where emailAddress contains UPDATED_EMAIL_ADDRESS
        defaultPersonEmailAddressShouldNotBeFound("emailAddress.contains=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByEmailAddressNotContainsSomething() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where emailAddress does not contain DEFAULT_EMAIL_ADDRESS
        defaultPersonEmailAddressShouldNotBeFound("emailAddress.doesNotContain=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the personEmailAddressList where emailAddress does not contain UPDATED_EMAIL_ADDRESS
        defaultPersonEmailAddressShouldBeFound("emailAddress.doesNotContain=" + UPDATED_EMAIL_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllPersonEmailAddressesByDefaultIndIsEqualToSomething() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where defaultInd equals to DEFAULT_DEFAULT_IND
        defaultPersonEmailAddressShouldBeFound("defaultInd.equals=" + DEFAULT_DEFAULT_IND);

        // Get all the personEmailAddressList where defaultInd equals to UPDATED_DEFAULT_IND
        defaultPersonEmailAddressShouldNotBeFound("defaultInd.equals=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByDefaultIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where defaultInd not equals to DEFAULT_DEFAULT_IND
        defaultPersonEmailAddressShouldNotBeFound("defaultInd.notEquals=" + DEFAULT_DEFAULT_IND);

        // Get all the personEmailAddressList where defaultInd not equals to UPDATED_DEFAULT_IND
        defaultPersonEmailAddressShouldBeFound("defaultInd.notEquals=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByDefaultIndIsInShouldWork() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where defaultInd in DEFAULT_DEFAULT_IND or UPDATED_DEFAULT_IND
        defaultPersonEmailAddressShouldBeFound("defaultInd.in=" + DEFAULT_DEFAULT_IND + "," + UPDATED_DEFAULT_IND);

        // Get all the personEmailAddressList where defaultInd equals to UPDATED_DEFAULT_IND
        defaultPersonEmailAddressShouldNotBeFound("defaultInd.in=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByDefaultIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where defaultInd is not null
        defaultPersonEmailAddressShouldBeFound("defaultInd.specified=true");

        // Get all the personEmailAddressList where defaultInd is null
        defaultPersonEmailAddressShouldNotBeFound("defaultInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where validFrom equals to DEFAULT_VALID_FROM
        defaultPersonEmailAddressShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the personEmailAddressList where validFrom equals to UPDATED_VALID_FROM
        defaultPersonEmailAddressShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where validFrom not equals to DEFAULT_VALID_FROM
        defaultPersonEmailAddressShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the personEmailAddressList where validFrom not equals to UPDATED_VALID_FROM
        defaultPersonEmailAddressShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultPersonEmailAddressShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the personEmailAddressList where validFrom equals to UPDATED_VALID_FROM
        defaultPersonEmailAddressShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where validFrom is not null
        defaultPersonEmailAddressShouldBeFound("validFrom.specified=true");

        // Get all the personEmailAddressList where validFrom is null
        defaultPersonEmailAddressShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where validTo equals to DEFAULT_VALID_TO
        defaultPersonEmailAddressShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the personEmailAddressList where validTo equals to UPDATED_VALID_TO
        defaultPersonEmailAddressShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where validTo not equals to DEFAULT_VALID_TO
        defaultPersonEmailAddressShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the personEmailAddressList where validTo not equals to UPDATED_VALID_TO
        defaultPersonEmailAddressShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultPersonEmailAddressShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the personEmailAddressList where validTo equals to UPDATED_VALID_TO
        defaultPersonEmailAddressShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where validTo is not null
        defaultPersonEmailAddressShouldBeFound("validTo.specified=true");

        // Get all the personEmailAddressList where validTo is null
        defaultPersonEmailAddressShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);
        People person = PeopleResourceIT.createEntity(em);
        em.persist(person);
        em.flush();
        personEmailAddress.setPerson(person);
        personEmailAddressRepository.saveAndFlush(personEmailAddress);
        Long personId = person.getId();

        // Get all the personEmailAddressList where person equals to personId
        defaultPersonEmailAddressShouldBeFound("personId.equals=" + personId);

        // Get all the personEmailAddressList where person equals to personId + 1
        defaultPersonEmailAddressShouldNotBeFound("personId.equals=" + (personId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPersonEmailAddressShouldBeFound(String filter) throws Exception {
        restPersonEmailAddressMockMvc.perform(get("/api/person-email-addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personEmailAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].defaultInd").value(hasItem(DEFAULT_DEFAULT_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restPersonEmailAddressMockMvc.perform(get("/api/person-email-addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPersonEmailAddressShouldNotBeFound(String filter) throws Exception {
        restPersonEmailAddressMockMvc.perform(get("/api/person-email-addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPersonEmailAddressMockMvc.perform(get("/api/person-email-addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPersonEmailAddress() throws Exception {
        // Get the personEmailAddress
        restPersonEmailAddressMockMvc.perform(get("/api/person-email-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonEmailAddress() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        int databaseSizeBeforeUpdate = personEmailAddressRepository.findAll().size();

        // Update the personEmailAddress
        PersonEmailAddress updatedPersonEmailAddress = personEmailAddressRepository.findById(personEmailAddress.getId()).get();
        // Disconnect from session so that the updates on updatedPersonEmailAddress are not directly saved in db
        em.detach(updatedPersonEmailAddress);
        updatedPersonEmailAddress
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .defaultInd(UPDATED_DEFAULT_IND)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        PersonEmailAddressDTO personEmailAddressDTO = personEmailAddressMapper.toDto(updatedPersonEmailAddress);

        restPersonEmailAddressMockMvc.perform(put("/api/person-email-addresses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personEmailAddressDTO)))
            .andExpect(status().isOk());

        // Validate the PersonEmailAddress in the database
        List<PersonEmailAddress> personEmailAddressList = personEmailAddressRepository.findAll();
        assertThat(personEmailAddressList).hasSize(databaseSizeBeforeUpdate);
        PersonEmailAddress testPersonEmailAddress = personEmailAddressList.get(personEmailAddressList.size() - 1);
        assertThat(testPersonEmailAddress.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testPersonEmailAddress.isDefaultInd()).isEqualTo(UPDATED_DEFAULT_IND);
        assertThat(testPersonEmailAddress.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testPersonEmailAddress.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonEmailAddress() throws Exception {
        int databaseSizeBeforeUpdate = personEmailAddressRepository.findAll().size();

        // Create the PersonEmailAddress
        PersonEmailAddressDTO personEmailAddressDTO = personEmailAddressMapper.toDto(personEmailAddress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonEmailAddressMockMvc.perform(put("/api/person-email-addresses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personEmailAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonEmailAddress in the database
        List<PersonEmailAddress> personEmailAddressList = personEmailAddressRepository.findAll();
        assertThat(personEmailAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonEmailAddress() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        int databaseSizeBeforeDelete = personEmailAddressRepository.findAll().size();

        // Delete the personEmailAddress
        restPersonEmailAddressMockMvc.perform(delete("/api/person-email-addresses/{id}", personEmailAddress.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonEmailAddress> personEmailAddressList = personEmailAddressRepository.findAll();
        assertThat(personEmailAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

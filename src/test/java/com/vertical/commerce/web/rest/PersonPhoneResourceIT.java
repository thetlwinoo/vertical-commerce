package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.PersonPhone;
import com.vertical.commerce.domain.People;
import com.vertical.commerce.domain.PhoneNumberType;
import com.vertical.commerce.repository.PersonPhoneRepository;
import com.vertical.commerce.service.PersonPhoneService;
import com.vertical.commerce.service.dto.PersonPhoneDTO;
import com.vertical.commerce.service.mapper.PersonPhoneMapper;
import com.vertical.commerce.service.dto.PersonPhoneCriteria;
import com.vertical.commerce.service.PersonPhoneQueryService;

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
 * Integration tests for the {@link PersonPhoneResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PersonPhoneResourceIT {

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEFAULT_IND = false;
    private static final Boolean UPDATED_DEFAULT_IND = true;

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PersonPhoneRepository personPhoneRepository;

    @Autowired
    private PersonPhoneMapper personPhoneMapper;

    @Autowired
    private PersonPhoneService personPhoneService;

    @Autowired
    private PersonPhoneQueryService personPhoneQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonPhoneMockMvc;

    private PersonPhone personPhone;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonPhone createEntity(EntityManager em) {
        PersonPhone personPhone = new PersonPhone()
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .defaultInd(DEFAULT_DEFAULT_IND)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return personPhone;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonPhone createUpdatedEntity(EntityManager em) {
        PersonPhone personPhone = new PersonPhone()
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .defaultInd(UPDATED_DEFAULT_IND)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return personPhone;
    }

    @BeforeEach
    public void initTest() {
        personPhone = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonPhone() throws Exception {
        int databaseSizeBeforeCreate = personPhoneRepository.findAll().size();
        // Create the PersonPhone
        PersonPhoneDTO personPhoneDTO = personPhoneMapper.toDto(personPhone);
        restPersonPhoneMockMvc.perform(post("/api/person-phones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personPhoneDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonPhone in the database
        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeCreate + 1);
        PersonPhone testPersonPhone = personPhoneList.get(personPhoneList.size() - 1);
        assertThat(testPersonPhone.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testPersonPhone.isDefaultInd()).isEqualTo(DEFAULT_DEFAULT_IND);
        assertThat(testPersonPhone.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testPersonPhone.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createPersonPhoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personPhoneRepository.findAll().size();

        // Create the PersonPhone with an existing ID
        personPhone.setId(1L);
        PersonPhoneDTO personPhoneDTO = personPhoneMapper.toDto(personPhone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonPhoneMockMvc.perform(post("/api/person-phones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personPhoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonPhone in the database
        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = personPhoneRepository.findAll().size();
        // set the field null
        personPhone.setPhoneNumber(null);

        // Create the PersonPhone, which fails.
        PersonPhoneDTO personPhoneDTO = personPhoneMapper.toDto(personPhone);


        restPersonPhoneMockMvc.perform(post("/api/person-phones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personPhoneDTO)))
            .andExpect(status().isBadRequest());

        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = personPhoneRepository.findAll().size();
        // set the field null
        personPhone.setValidFrom(null);

        // Create the PersonPhone, which fails.
        PersonPhoneDTO personPhoneDTO = personPhoneMapper.toDto(personPhone);


        restPersonPhoneMockMvc.perform(post("/api/person-phones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personPhoneDTO)))
            .andExpect(status().isBadRequest());

        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonPhones() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList
        restPersonPhoneMockMvc.perform(get("/api/person-phones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personPhone.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].defaultInd").value(hasItem(DEFAULT_DEFAULT_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getPersonPhone() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get the personPhone
        restPersonPhoneMockMvc.perform(get("/api/person-phones/{id}", personPhone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personPhone.getId().intValue()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.defaultInd").value(DEFAULT_DEFAULT_IND.booleanValue()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getPersonPhonesByIdFiltering() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        Long id = personPhone.getId();

        defaultPersonPhoneShouldBeFound("id.equals=" + id);
        defaultPersonPhoneShouldNotBeFound("id.notEquals=" + id);

        defaultPersonPhoneShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPersonPhoneShouldNotBeFound("id.greaterThan=" + id);

        defaultPersonPhoneShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPersonPhoneShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPersonPhonesByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultPersonPhoneShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the personPhoneList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultPersonPhoneShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where phoneNumber not equals to DEFAULT_PHONE_NUMBER
        defaultPersonPhoneShouldNotBeFound("phoneNumber.notEquals=" + DEFAULT_PHONE_NUMBER);

        // Get all the personPhoneList where phoneNumber not equals to UPDATED_PHONE_NUMBER
        defaultPersonPhoneShouldBeFound("phoneNumber.notEquals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultPersonPhoneShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the personPhoneList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultPersonPhoneShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where phoneNumber is not null
        defaultPersonPhoneShouldBeFound("phoneNumber.specified=true");

        // Get all the personPhoneList where phoneNumber is null
        defaultPersonPhoneShouldNotBeFound("phoneNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonPhonesByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultPersonPhoneShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the personPhoneList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultPersonPhoneShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultPersonPhoneShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the personPhoneList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultPersonPhoneShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllPersonPhonesByDefaultIndIsEqualToSomething() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where defaultInd equals to DEFAULT_DEFAULT_IND
        defaultPersonPhoneShouldBeFound("defaultInd.equals=" + DEFAULT_DEFAULT_IND);

        // Get all the personPhoneList where defaultInd equals to UPDATED_DEFAULT_IND
        defaultPersonPhoneShouldNotBeFound("defaultInd.equals=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByDefaultIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where defaultInd not equals to DEFAULT_DEFAULT_IND
        defaultPersonPhoneShouldNotBeFound("defaultInd.notEquals=" + DEFAULT_DEFAULT_IND);

        // Get all the personPhoneList where defaultInd not equals to UPDATED_DEFAULT_IND
        defaultPersonPhoneShouldBeFound("defaultInd.notEquals=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByDefaultIndIsInShouldWork() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where defaultInd in DEFAULT_DEFAULT_IND or UPDATED_DEFAULT_IND
        defaultPersonPhoneShouldBeFound("defaultInd.in=" + DEFAULT_DEFAULT_IND + "," + UPDATED_DEFAULT_IND);

        // Get all the personPhoneList where defaultInd equals to UPDATED_DEFAULT_IND
        defaultPersonPhoneShouldNotBeFound("defaultInd.in=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByDefaultIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where defaultInd is not null
        defaultPersonPhoneShouldBeFound("defaultInd.specified=true");

        // Get all the personPhoneList where defaultInd is null
        defaultPersonPhoneShouldNotBeFound("defaultInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where validFrom equals to DEFAULT_VALID_FROM
        defaultPersonPhoneShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the personPhoneList where validFrom equals to UPDATED_VALID_FROM
        defaultPersonPhoneShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where validFrom not equals to DEFAULT_VALID_FROM
        defaultPersonPhoneShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the personPhoneList where validFrom not equals to UPDATED_VALID_FROM
        defaultPersonPhoneShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultPersonPhoneShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the personPhoneList where validFrom equals to UPDATED_VALID_FROM
        defaultPersonPhoneShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where validFrom is not null
        defaultPersonPhoneShouldBeFound("validFrom.specified=true");

        // Get all the personPhoneList where validFrom is null
        defaultPersonPhoneShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where validTo equals to DEFAULT_VALID_TO
        defaultPersonPhoneShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the personPhoneList where validTo equals to UPDATED_VALID_TO
        defaultPersonPhoneShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where validTo not equals to DEFAULT_VALID_TO
        defaultPersonPhoneShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the personPhoneList where validTo not equals to UPDATED_VALID_TO
        defaultPersonPhoneShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultPersonPhoneShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the personPhoneList where validTo equals to UPDATED_VALID_TO
        defaultPersonPhoneShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where validTo is not null
        defaultPersonPhoneShouldBeFound("validTo.specified=true");

        // Get all the personPhoneList where validTo is null
        defaultPersonPhoneShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);
        People person = PeopleResourceIT.createEntity(em);
        em.persist(person);
        em.flush();
        personPhone.setPerson(person);
        personPhoneRepository.saveAndFlush(personPhone);
        Long personId = person.getId();

        // Get all the personPhoneList where person equals to personId
        defaultPersonPhoneShouldBeFound("personId.equals=" + personId);

        // Get all the personPhoneList where person equals to personId + 1
        defaultPersonPhoneShouldNotBeFound("personId.equals=" + (personId + 1));
    }


    @Test
    @Transactional
    public void getAllPersonPhonesByPhoneNumberTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);
        PhoneNumberType phoneNumberType = PhoneNumberTypeResourceIT.createEntity(em);
        em.persist(phoneNumberType);
        em.flush();
        personPhone.setPhoneNumberType(phoneNumberType);
        personPhoneRepository.saveAndFlush(personPhone);
        Long phoneNumberTypeId = phoneNumberType.getId();

        // Get all the personPhoneList where phoneNumberType equals to phoneNumberTypeId
        defaultPersonPhoneShouldBeFound("phoneNumberTypeId.equals=" + phoneNumberTypeId);

        // Get all the personPhoneList where phoneNumberType equals to phoneNumberTypeId + 1
        defaultPersonPhoneShouldNotBeFound("phoneNumberTypeId.equals=" + (phoneNumberTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPersonPhoneShouldBeFound(String filter) throws Exception {
        restPersonPhoneMockMvc.perform(get("/api/person-phones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personPhone.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].defaultInd").value(hasItem(DEFAULT_DEFAULT_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restPersonPhoneMockMvc.perform(get("/api/person-phones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPersonPhoneShouldNotBeFound(String filter) throws Exception {
        restPersonPhoneMockMvc.perform(get("/api/person-phones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPersonPhoneMockMvc.perform(get("/api/person-phones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPersonPhone() throws Exception {
        // Get the personPhone
        restPersonPhoneMockMvc.perform(get("/api/person-phones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonPhone() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        int databaseSizeBeforeUpdate = personPhoneRepository.findAll().size();

        // Update the personPhone
        PersonPhone updatedPersonPhone = personPhoneRepository.findById(personPhone.getId()).get();
        // Disconnect from session so that the updates on updatedPersonPhone are not directly saved in db
        em.detach(updatedPersonPhone);
        updatedPersonPhone
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .defaultInd(UPDATED_DEFAULT_IND)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        PersonPhoneDTO personPhoneDTO = personPhoneMapper.toDto(updatedPersonPhone);

        restPersonPhoneMockMvc.perform(put("/api/person-phones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personPhoneDTO)))
            .andExpect(status().isOk());

        // Validate the PersonPhone in the database
        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeUpdate);
        PersonPhone testPersonPhone = personPhoneList.get(personPhoneList.size() - 1);
        assertThat(testPersonPhone.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPersonPhone.isDefaultInd()).isEqualTo(UPDATED_DEFAULT_IND);
        assertThat(testPersonPhone.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testPersonPhone.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonPhone() throws Exception {
        int databaseSizeBeforeUpdate = personPhoneRepository.findAll().size();

        // Create the PersonPhone
        PersonPhoneDTO personPhoneDTO = personPhoneMapper.toDto(personPhone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonPhoneMockMvc.perform(put("/api/person-phones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personPhoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonPhone in the database
        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonPhone() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        int databaseSizeBeforeDelete = personPhoneRepository.findAll().size();

        // Delete the personPhone
        restPersonPhoneMockMvc.perform(delete("/api/person-phones/{id}", personPhone.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

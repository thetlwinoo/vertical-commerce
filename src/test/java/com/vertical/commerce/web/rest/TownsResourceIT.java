package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.Towns;
import com.vertical.commerce.domain.Townships;
import com.vertical.commerce.repository.TownsRepository;
import com.vertical.commerce.service.TownsService;
import com.vertical.commerce.service.dto.TownsDTO;
import com.vertical.commerce.service.mapper.TownsMapper;
import com.vertical.commerce.service.dto.TownsCriteria;
import com.vertical.commerce.service.TownsQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@link TownsResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TownsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CULTURE_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_CULTURE_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TownsRepository townsRepository;

    @Autowired
    private TownsMapper townsMapper;

    @Autowired
    private TownsService townsService;

    @Autowired
    private TownsQueryService townsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTownsMockMvc;

    private Towns towns;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Towns createEntity(EntityManager em) {
        Towns towns = new Towns()
            .name(DEFAULT_NAME)
            .postalCode(DEFAULT_POSTAL_CODE)
            .cultureDetails(DEFAULT_CULTURE_DETAILS)
            .description(DEFAULT_DESCRIPTION)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return towns;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Towns createUpdatedEntity(EntityManager em) {
        Towns towns = new Towns()
            .name(UPDATED_NAME)
            .postalCode(UPDATED_POSTAL_CODE)
            .cultureDetails(UPDATED_CULTURE_DETAILS)
            .description(UPDATED_DESCRIPTION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return towns;
    }

    @BeforeEach
    public void initTest() {
        towns = createEntity(em);
    }

    @Test
    @Transactional
    public void createTowns() throws Exception {
        int databaseSizeBeforeCreate = townsRepository.findAll().size();
        // Create the Towns
        TownsDTO townsDTO = townsMapper.toDto(towns);
        restTownsMockMvc.perform(post("/api/towns").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townsDTO)))
            .andExpect(status().isCreated());

        // Validate the Towns in the database
        List<Towns> townsList = townsRepository.findAll();
        assertThat(townsList).hasSize(databaseSizeBeforeCreate + 1);
        Towns testTowns = townsList.get(townsList.size() - 1);
        assertThat(testTowns.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTowns.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testTowns.getCultureDetails()).isEqualTo(DEFAULT_CULTURE_DETAILS);
        assertThat(testTowns.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTowns.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testTowns.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createTownsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = townsRepository.findAll().size();

        // Create the Towns with an existing ID
        towns.setId(1L);
        TownsDTO townsDTO = townsMapper.toDto(towns);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTownsMockMvc.perform(post("/api/towns").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Towns in the database
        List<Towns> townsList = townsRepository.findAll();
        assertThat(townsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = townsRepository.findAll().size();
        // set the field null
        towns.setName(null);

        // Create the Towns, which fails.
        TownsDTO townsDTO = townsMapper.toDto(towns);


        restTownsMockMvc.perform(post("/api/towns").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townsDTO)))
            .andExpect(status().isBadRequest());

        List<Towns> townsList = townsRepository.findAll();
        assertThat(townsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = townsRepository.findAll().size();
        // set the field null
        towns.setValidFrom(null);

        // Create the Towns, which fails.
        TownsDTO townsDTO = townsMapper.toDto(towns);


        restTownsMockMvc.perform(post("/api/towns").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townsDTO)))
            .andExpect(status().isBadRequest());

        List<Towns> townsList = townsRepository.findAll();
        assertThat(townsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTowns() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList
        restTownsMockMvc.perform(get("/api/towns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(towns.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].cultureDetails").value(hasItem(DEFAULT_CULTURE_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getTowns() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get the towns
        restTownsMockMvc.perform(get("/api/towns/{id}", towns.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(towns.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.cultureDetails").value(DEFAULT_CULTURE_DETAILS.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getTownsByIdFiltering() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        Long id = towns.getId();

        defaultTownsShouldBeFound("id.equals=" + id);
        defaultTownsShouldNotBeFound("id.notEquals=" + id);

        defaultTownsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTownsShouldNotBeFound("id.greaterThan=" + id);

        defaultTownsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTownsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTownsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where name equals to DEFAULT_NAME
        defaultTownsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the townsList where name equals to UPDATED_NAME
        defaultTownsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where name not equals to DEFAULT_NAME
        defaultTownsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the townsList where name not equals to UPDATED_NAME
        defaultTownsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTownsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the townsList where name equals to UPDATED_NAME
        defaultTownsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where name is not null
        defaultTownsShouldBeFound("name.specified=true");

        // Get all the townsList where name is null
        defaultTownsShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTownsByNameContainsSomething() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where name contains DEFAULT_NAME
        defaultTownsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the townsList where name contains UPDATED_NAME
        defaultTownsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where name does not contain DEFAULT_NAME
        defaultTownsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the townsList where name does not contain UPDATED_NAME
        defaultTownsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTownsByPostalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where postalCode equals to DEFAULT_POSTAL_CODE
        defaultTownsShouldBeFound("postalCode.equals=" + DEFAULT_POSTAL_CODE);

        // Get all the townsList where postalCode equals to UPDATED_POSTAL_CODE
        defaultTownsShouldNotBeFound("postalCode.equals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllTownsByPostalCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where postalCode not equals to DEFAULT_POSTAL_CODE
        defaultTownsShouldNotBeFound("postalCode.notEquals=" + DEFAULT_POSTAL_CODE);

        // Get all the townsList where postalCode not equals to UPDATED_POSTAL_CODE
        defaultTownsShouldBeFound("postalCode.notEquals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllTownsByPostalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where postalCode in DEFAULT_POSTAL_CODE or UPDATED_POSTAL_CODE
        defaultTownsShouldBeFound("postalCode.in=" + DEFAULT_POSTAL_CODE + "," + UPDATED_POSTAL_CODE);

        // Get all the townsList where postalCode equals to UPDATED_POSTAL_CODE
        defaultTownsShouldNotBeFound("postalCode.in=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllTownsByPostalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where postalCode is not null
        defaultTownsShouldBeFound("postalCode.specified=true");

        // Get all the townsList where postalCode is null
        defaultTownsShouldNotBeFound("postalCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllTownsByPostalCodeContainsSomething() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where postalCode contains DEFAULT_POSTAL_CODE
        defaultTownsShouldBeFound("postalCode.contains=" + DEFAULT_POSTAL_CODE);

        // Get all the townsList where postalCode contains UPDATED_POSTAL_CODE
        defaultTownsShouldNotBeFound("postalCode.contains=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllTownsByPostalCodeNotContainsSomething() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where postalCode does not contain DEFAULT_POSTAL_CODE
        defaultTownsShouldNotBeFound("postalCode.doesNotContain=" + DEFAULT_POSTAL_CODE);

        // Get all the townsList where postalCode does not contain UPDATED_POSTAL_CODE
        defaultTownsShouldBeFound("postalCode.doesNotContain=" + UPDATED_POSTAL_CODE);
    }


    @Test
    @Transactional
    public void getAllTownsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where description equals to DEFAULT_DESCRIPTION
        defaultTownsShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the townsList where description equals to UPDATED_DESCRIPTION
        defaultTownsShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTownsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where description not equals to DEFAULT_DESCRIPTION
        defaultTownsShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the townsList where description not equals to UPDATED_DESCRIPTION
        defaultTownsShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTownsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultTownsShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the townsList where description equals to UPDATED_DESCRIPTION
        defaultTownsShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTownsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where description is not null
        defaultTownsShouldBeFound("description.specified=true");

        // Get all the townsList where description is null
        defaultTownsShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllTownsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where description contains DEFAULT_DESCRIPTION
        defaultTownsShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the townsList where description contains UPDATED_DESCRIPTION
        defaultTownsShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTownsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where description does not contain DEFAULT_DESCRIPTION
        defaultTownsShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the townsList where description does not contain UPDATED_DESCRIPTION
        defaultTownsShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllTownsByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where validFrom equals to DEFAULT_VALID_FROM
        defaultTownsShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the townsList where validFrom equals to UPDATED_VALID_FROM
        defaultTownsShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllTownsByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where validFrom not equals to DEFAULT_VALID_FROM
        defaultTownsShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the townsList where validFrom not equals to UPDATED_VALID_FROM
        defaultTownsShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllTownsByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultTownsShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the townsList where validFrom equals to UPDATED_VALID_FROM
        defaultTownsShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllTownsByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where validFrom is not null
        defaultTownsShouldBeFound("validFrom.specified=true");

        // Get all the townsList where validFrom is null
        defaultTownsShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllTownsByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where validTo equals to DEFAULT_VALID_TO
        defaultTownsShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the townsList where validTo equals to UPDATED_VALID_TO
        defaultTownsShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllTownsByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where validTo not equals to DEFAULT_VALID_TO
        defaultTownsShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the townsList where validTo not equals to UPDATED_VALID_TO
        defaultTownsShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllTownsByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultTownsShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the townsList where validTo equals to UPDATED_VALID_TO
        defaultTownsShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllTownsByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        // Get all the townsList where validTo is not null
        defaultTownsShouldBeFound("validTo.specified=true");

        // Get all the townsList where validTo is null
        defaultTownsShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllTownsByTownshipIsEqualToSomething() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);
        Townships township = TownshipsResourceIT.createEntity(em);
        em.persist(township);
        em.flush();
        towns.setTownship(township);
        townsRepository.saveAndFlush(towns);
        Long townshipId = township.getId();

        // Get all the townsList where township equals to townshipId
        defaultTownsShouldBeFound("townshipId.equals=" + townshipId);

        // Get all the townsList where township equals to townshipId + 1
        defaultTownsShouldNotBeFound("townshipId.equals=" + (townshipId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTownsShouldBeFound(String filter) throws Exception {
        restTownsMockMvc.perform(get("/api/towns?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(towns.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].cultureDetails").value(hasItem(DEFAULT_CULTURE_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restTownsMockMvc.perform(get("/api/towns/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTownsShouldNotBeFound(String filter) throws Exception {
        restTownsMockMvc.perform(get("/api/towns?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTownsMockMvc.perform(get("/api/towns/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTowns() throws Exception {
        // Get the towns
        restTownsMockMvc.perform(get("/api/towns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTowns() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        int databaseSizeBeforeUpdate = townsRepository.findAll().size();

        // Update the towns
        Towns updatedTowns = townsRepository.findById(towns.getId()).get();
        // Disconnect from session so that the updates on updatedTowns are not directly saved in db
        em.detach(updatedTowns);
        updatedTowns
            .name(UPDATED_NAME)
            .postalCode(UPDATED_POSTAL_CODE)
            .cultureDetails(UPDATED_CULTURE_DETAILS)
            .description(UPDATED_DESCRIPTION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        TownsDTO townsDTO = townsMapper.toDto(updatedTowns);

        restTownsMockMvc.perform(put("/api/towns").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townsDTO)))
            .andExpect(status().isOk());

        // Validate the Towns in the database
        List<Towns> townsList = townsRepository.findAll();
        assertThat(townsList).hasSize(databaseSizeBeforeUpdate);
        Towns testTowns = townsList.get(townsList.size() - 1);
        assertThat(testTowns.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTowns.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testTowns.getCultureDetails()).isEqualTo(UPDATED_CULTURE_DETAILS);
        assertThat(testTowns.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTowns.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testTowns.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingTowns() throws Exception {
        int databaseSizeBeforeUpdate = townsRepository.findAll().size();

        // Create the Towns
        TownsDTO townsDTO = townsMapper.toDto(towns);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTownsMockMvc.perform(put("/api/towns").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Towns in the database
        List<Towns> townsList = townsRepository.findAll();
        assertThat(townsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTowns() throws Exception {
        // Initialize the database
        townsRepository.saveAndFlush(towns);

        int databaseSizeBeforeDelete = townsRepository.findAll().size();

        // Delete the towns
        restTownsMockMvc.perform(delete("/api/towns/{id}", towns.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Towns> townsList = townsRepository.findAll();
        assertThat(townsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

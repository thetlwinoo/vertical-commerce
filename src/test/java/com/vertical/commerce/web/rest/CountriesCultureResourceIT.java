package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.CountriesCulture;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.Countries;
import com.vertical.commerce.repository.CountriesCultureRepository;
import com.vertical.commerce.service.CountriesCultureService;
import com.vertical.commerce.service.dto.CountriesCultureDTO;
import com.vertical.commerce.service.mapper.CountriesCultureMapper;
import com.vertical.commerce.service.dto.CountriesCultureCriteria;
import com.vertical.commerce.service.CountriesCultureQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CountriesCultureResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CountriesCultureResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CountriesCultureRepository countriesCultureRepository;

    @Autowired
    private CountriesCultureMapper countriesCultureMapper;

    @Autowired
    private CountriesCultureService countriesCultureService;

    @Autowired
    private CountriesCultureQueryService countriesCultureQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCountriesCultureMockMvc;

    private CountriesCulture countriesCulture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CountriesCulture createEntity(EntityManager em) {
        CountriesCulture countriesCulture = new CountriesCulture()
            .name(DEFAULT_NAME);
        return countriesCulture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CountriesCulture createUpdatedEntity(EntityManager em) {
        CountriesCulture countriesCulture = new CountriesCulture()
            .name(UPDATED_NAME);
        return countriesCulture;
    }

    @BeforeEach
    public void initTest() {
        countriesCulture = createEntity(em);
    }

    @Test
    @Transactional
    public void createCountriesCulture() throws Exception {
        int databaseSizeBeforeCreate = countriesCultureRepository.findAll().size();
        // Create the CountriesCulture
        CountriesCultureDTO countriesCultureDTO = countriesCultureMapper.toDto(countriesCulture);
        restCountriesCultureMockMvc.perform(post("/api/countries-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countriesCultureDTO)))
            .andExpect(status().isCreated());

        // Validate the CountriesCulture in the database
        List<CountriesCulture> countriesCultureList = countriesCultureRepository.findAll();
        assertThat(countriesCultureList).hasSize(databaseSizeBeforeCreate + 1);
        CountriesCulture testCountriesCulture = countriesCultureList.get(countriesCultureList.size() - 1);
        assertThat(testCountriesCulture.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCountriesCultureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = countriesCultureRepository.findAll().size();

        // Create the CountriesCulture with an existing ID
        countriesCulture.setId(1L);
        CountriesCultureDTO countriesCultureDTO = countriesCultureMapper.toDto(countriesCulture);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountriesCultureMockMvc.perform(post("/api/countries-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countriesCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CountriesCulture in the database
        List<CountriesCulture> countriesCultureList = countriesCultureRepository.findAll();
        assertThat(countriesCultureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = countriesCultureRepository.findAll().size();
        // set the field null
        countriesCulture.setName(null);

        // Create the CountriesCulture, which fails.
        CountriesCultureDTO countriesCultureDTO = countriesCultureMapper.toDto(countriesCulture);


        restCountriesCultureMockMvc.perform(post("/api/countries-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countriesCultureDTO)))
            .andExpect(status().isBadRequest());

        List<CountriesCulture> countriesCultureList = countriesCultureRepository.findAll();
        assertThat(countriesCultureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCountriesCultures() throws Exception {
        // Initialize the database
        countriesCultureRepository.saveAndFlush(countriesCulture);

        // Get all the countriesCultureList
        restCountriesCultureMockMvc.perform(get("/api/countries-cultures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countriesCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getCountriesCulture() throws Exception {
        // Initialize the database
        countriesCultureRepository.saveAndFlush(countriesCulture);

        // Get the countriesCulture
        restCountriesCultureMockMvc.perform(get("/api/countries-cultures/{id}", countriesCulture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(countriesCulture.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getCountriesCulturesByIdFiltering() throws Exception {
        // Initialize the database
        countriesCultureRepository.saveAndFlush(countriesCulture);

        Long id = countriesCulture.getId();

        defaultCountriesCultureShouldBeFound("id.equals=" + id);
        defaultCountriesCultureShouldNotBeFound("id.notEquals=" + id);

        defaultCountriesCultureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCountriesCultureShouldNotBeFound("id.greaterThan=" + id);

        defaultCountriesCultureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCountriesCultureShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCountriesCulturesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesCultureRepository.saveAndFlush(countriesCulture);

        // Get all the countriesCultureList where name equals to DEFAULT_NAME
        defaultCountriesCultureShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the countriesCultureList where name equals to UPDATED_NAME
        defaultCountriesCultureShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesCulturesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countriesCultureRepository.saveAndFlush(countriesCulture);

        // Get all the countriesCultureList where name not equals to DEFAULT_NAME
        defaultCountriesCultureShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the countriesCultureList where name not equals to UPDATED_NAME
        defaultCountriesCultureShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesCulturesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        countriesCultureRepository.saveAndFlush(countriesCulture);

        // Get all the countriesCultureList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCountriesCultureShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the countriesCultureList where name equals to UPDATED_NAME
        defaultCountriesCultureShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesCulturesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        countriesCultureRepository.saveAndFlush(countriesCulture);

        // Get all the countriesCultureList where name is not null
        defaultCountriesCultureShouldBeFound("name.specified=true");

        // Get all the countriesCultureList where name is null
        defaultCountriesCultureShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCountriesCulturesByNameContainsSomething() throws Exception {
        // Initialize the database
        countriesCultureRepository.saveAndFlush(countriesCulture);

        // Get all the countriesCultureList where name contains DEFAULT_NAME
        defaultCountriesCultureShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the countriesCultureList where name contains UPDATED_NAME
        defaultCountriesCultureShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesCulturesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        countriesCultureRepository.saveAndFlush(countriesCulture);

        // Get all the countriesCultureList where name does not contain DEFAULT_NAME
        defaultCountriesCultureShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the countriesCultureList where name does not contain UPDATED_NAME
        defaultCountriesCultureShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCountriesCulturesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesCultureRepository.saveAndFlush(countriesCulture);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        countriesCulture.setCulture(culture);
        countriesCultureRepository.saveAndFlush(countriesCulture);
        Long cultureId = culture.getId();

        // Get all the countriesCultureList where culture equals to cultureId
        defaultCountriesCultureShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the countriesCultureList where culture equals to cultureId + 1
        defaultCountriesCultureShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllCountriesCulturesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesCultureRepository.saveAndFlush(countriesCulture);
        Countries country = CountriesResourceIT.createEntity(em);
        em.persist(country);
        em.flush();
        countriesCulture.setCountry(country);
        countriesCultureRepository.saveAndFlush(countriesCulture);
        Long countryId = country.getId();

        // Get all the countriesCultureList where country equals to countryId
        defaultCountriesCultureShouldBeFound("countryId.equals=" + countryId);

        // Get all the countriesCultureList where country equals to countryId + 1
        defaultCountriesCultureShouldNotBeFound("countryId.equals=" + (countryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCountriesCultureShouldBeFound(String filter) throws Exception {
        restCountriesCultureMockMvc.perform(get("/api/countries-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countriesCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restCountriesCultureMockMvc.perform(get("/api/countries-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCountriesCultureShouldNotBeFound(String filter) throws Exception {
        restCountriesCultureMockMvc.perform(get("/api/countries-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCountriesCultureMockMvc.perform(get("/api/countries-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCountriesCulture() throws Exception {
        // Get the countriesCulture
        restCountriesCultureMockMvc.perform(get("/api/countries-cultures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCountriesCulture() throws Exception {
        // Initialize the database
        countriesCultureRepository.saveAndFlush(countriesCulture);

        int databaseSizeBeforeUpdate = countriesCultureRepository.findAll().size();

        // Update the countriesCulture
        CountriesCulture updatedCountriesCulture = countriesCultureRepository.findById(countriesCulture.getId()).get();
        // Disconnect from session so that the updates on updatedCountriesCulture are not directly saved in db
        em.detach(updatedCountriesCulture);
        updatedCountriesCulture
            .name(UPDATED_NAME);
        CountriesCultureDTO countriesCultureDTO = countriesCultureMapper.toDto(updatedCountriesCulture);

        restCountriesCultureMockMvc.perform(put("/api/countries-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countriesCultureDTO)))
            .andExpect(status().isOk());

        // Validate the CountriesCulture in the database
        List<CountriesCulture> countriesCultureList = countriesCultureRepository.findAll();
        assertThat(countriesCultureList).hasSize(databaseSizeBeforeUpdate);
        CountriesCulture testCountriesCulture = countriesCultureList.get(countriesCultureList.size() - 1);
        assertThat(testCountriesCulture.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCountriesCulture() throws Exception {
        int databaseSizeBeforeUpdate = countriesCultureRepository.findAll().size();

        // Create the CountriesCulture
        CountriesCultureDTO countriesCultureDTO = countriesCultureMapper.toDto(countriesCulture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountriesCultureMockMvc.perform(put("/api/countries-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countriesCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CountriesCulture in the database
        List<CountriesCulture> countriesCultureList = countriesCultureRepository.findAll();
        assertThat(countriesCultureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCountriesCulture() throws Exception {
        // Initialize the database
        countriesCultureRepository.saveAndFlush(countriesCulture);

        int databaseSizeBeforeDelete = countriesCultureRepository.findAll().size();

        // Delete the countriesCulture
        restCountriesCultureMockMvc.perform(delete("/api/countries-cultures/{id}", countriesCulture.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CountriesCulture> countriesCultureList = countriesCultureRepository.findAll();
        assertThat(countriesCultureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

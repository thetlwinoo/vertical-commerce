package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.CountriesLocalize;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.Countries;
import com.vertical.commerce.repository.CountriesLocalizeRepository;
import com.vertical.commerce.service.CountriesLocalizeService;
import com.vertical.commerce.service.dto.CountriesLocalizeDTO;
import com.vertical.commerce.service.mapper.CountriesLocalizeMapper;
import com.vertical.commerce.service.dto.CountriesLocalizeCriteria;
import com.vertical.commerce.service.CountriesLocalizeQueryService;

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
 * Integration tests for the {@link CountriesLocalizeResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CountriesLocalizeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CountriesLocalizeRepository countriesLocalizeRepository;

    @Autowired
    private CountriesLocalizeMapper countriesLocalizeMapper;

    @Autowired
    private CountriesLocalizeService countriesLocalizeService;

    @Autowired
    private CountriesLocalizeQueryService countriesLocalizeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCountriesLocalizeMockMvc;

    private CountriesLocalize countriesLocalize;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CountriesLocalize createEntity(EntityManager em) {
        CountriesLocalize countriesLocalize = new CountriesLocalize()
            .name(DEFAULT_NAME);
        return countriesLocalize;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CountriesLocalize createUpdatedEntity(EntityManager em) {
        CountriesLocalize countriesLocalize = new CountriesLocalize()
            .name(UPDATED_NAME);
        return countriesLocalize;
    }

    @BeforeEach
    public void initTest() {
        countriesLocalize = createEntity(em);
    }

    @Test
    @Transactional
    public void createCountriesLocalize() throws Exception {
        int databaseSizeBeforeCreate = countriesLocalizeRepository.findAll().size();
        // Create the CountriesLocalize
        CountriesLocalizeDTO countriesLocalizeDTO = countriesLocalizeMapper.toDto(countriesLocalize);
        restCountriesLocalizeMockMvc.perform(post("/api/countries-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countriesLocalizeDTO)))
            .andExpect(status().isCreated());

        // Validate the CountriesLocalize in the database
        List<CountriesLocalize> countriesLocalizeList = countriesLocalizeRepository.findAll();
        assertThat(countriesLocalizeList).hasSize(databaseSizeBeforeCreate + 1);
        CountriesLocalize testCountriesLocalize = countriesLocalizeList.get(countriesLocalizeList.size() - 1);
        assertThat(testCountriesLocalize.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCountriesLocalizeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = countriesLocalizeRepository.findAll().size();

        // Create the CountriesLocalize with an existing ID
        countriesLocalize.setId(1L);
        CountriesLocalizeDTO countriesLocalizeDTO = countriesLocalizeMapper.toDto(countriesLocalize);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountriesLocalizeMockMvc.perform(post("/api/countries-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countriesLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CountriesLocalize in the database
        List<CountriesLocalize> countriesLocalizeList = countriesLocalizeRepository.findAll();
        assertThat(countriesLocalizeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = countriesLocalizeRepository.findAll().size();
        // set the field null
        countriesLocalize.setName(null);

        // Create the CountriesLocalize, which fails.
        CountriesLocalizeDTO countriesLocalizeDTO = countriesLocalizeMapper.toDto(countriesLocalize);


        restCountriesLocalizeMockMvc.perform(post("/api/countries-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countriesLocalizeDTO)))
            .andExpect(status().isBadRequest());

        List<CountriesLocalize> countriesLocalizeList = countriesLocalizeRepository.findAll();
        assertThat(countriesLocalizeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCountriesLocalizes() throws Exception {
        // Initialize the database
        countriesLocalizeRepository.saveAndFlush(countriesLocalize);

        // Get all the countriesLocalizeList
        restCountriesLocalizeMockMvc.perform(get("/api/countries-localizes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countriesLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getCountriesLocalize() throws Exception {
        // Initialize the database
        countriesLocalizeRepository.saveAndFlush(countriesLocalize);

        // Get the countriesLocalize
        restCountriesLocalizeMockMvc.perform(get("/api/countries-localizes/{id}", countriesLocalize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(countriesLocalize.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getCountriesLocalizesByIdFiltering() throws Exception {
        // Initialize the database
        countriesLocalizeRepository.saveAndFlush(countriesLocalize);

        Long id = countriesLocalize.getId();

        defaultCountriesLocalizeShouldBeFound("id.equals=" + id);
        defaultCountriesLocalizeShouldNotBeFound("id.notEquals=" + id);

        defaultCountriesLocalizeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCountriesLocalizeShouldNotBeFound("id.greaterThan=" + id);

        defaultCountriesLocalizeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCountriesLocalizeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCountriesLocalizesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesLocalizeRepository.saveAndFlush(countriesLocalize);

        // Get all the countriesLocalizeList where name equals to DEFAULT_NAME
        defaultCountriesLocalizeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the countriesLocalizeList where name equals to UPDATED_NAME
        defaultCountriesLocalizeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesLocalizesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countriesLocalizeRepository.saveAndFlush(countriesLocalize);

        // Get all the countriesLocalizeList where name not equals to DEFAULT_NAME
        defaultCountriesLocalizeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the countriesLocalizeList where name not equals to UPDATED_NAME
        defaultCountriesLocalizeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesLocalizesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        countriesLocalizeRepository.saveAndFlush(countriesLocalize);

        // Get all the countriesLocalizeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCountriesLocalizeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the countriesLocalizeList where name equals to UPDATED_NAME
        defaultCountriesLocalizeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesLocalizesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        countriesLocalizeRepository.saveAndFlush(countriesLocalize);

        // Get all the countriesLocalizeList where name is not null
        defaultCountriesLocalizeShouldBeFound("name.specified=true");

        // Get all the countriesLocalizeList where name is null
        defaultCountriesLocalizeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCountriesLocalizesByNameContainsSomething() throws Exception {
        // Initialize the database
        countriesLocalizeRepository.saveAndFlush(countriesLocalize);

        // Get all the countriesLocalizeList where name contains DEFAULT_NAME
        defaultCountriesLocalizeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the countriesLocalizeList where name contains UPDATED_NAME
        defaultCountriesLocalizeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesLocalizesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        countriesLocalizeRepository.saveAndFlush(countriesLocalize);

        // Get all the countriesLocalizeList where name does not contain DEFAULT_NAME
        defaultCountriesLocalizeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the countriesLocalizeList where name does not contain UPDATED_NAME
        defaultCountriesLocalizeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCountriesLocalizesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesLocalizeRepository.saveAndFlush(countriesLocalize);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        countriesLocalize.setCulture(culture);
        countriesLocalizeRepository.saveAndFlush(countriesLocalize);
        Long cultureId = culture.getId();

        // Get all the countriesLocalizeList where culture equals to cultureId
        defaultCountriesLocalizeShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the countriesLocalizeList where culture equals to cultureId + 1
        defaultCountriesLocalizeShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllCountriesLocalizesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesLocalizeRepository.saveAndFlush(countriesLocalize);
        Countries country = CountriesResourceIT.createEntity(em);
        em.persist(country);
        em.flush();
        countriesLocalize.setCountry(country);
        countriesLocalizeRepository.saveAndFlush(countriesLocalize);
        Long countryId = country.getId();

        // Get all the countriesLocalizeList where country equals to countryId
        defaultCountriesLocalizeShouldBeFound("countryId.equals=" + countryId);

        // Get all the countriesLocalizeList where country equals to countryId + 1
        defaultCountriesLocalizeShouldNotBeFound("countryId.equals=" + (countryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCountriesLocalizeShouldBeFound(String filter) throws Exception {
        restCountriesLocalizeMockMvc.perform(get("/api/countries-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countriesLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restCountriesLocalizeMockMvc.perform(get("/api/countries-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCountriesLocalizeShouldNotBeFound(String filter) throws Exception {
        restCountriesLocalizeMockMvc.perform(get("/api/countries-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCountriesLocalizeMockMvc.perform(get("/api/countries-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCountriesLocalize() throws Exception {
        // Get the countriesLocalize
        restCountriesLocalizeMockMvc.perform(get("/api/countries-localizes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCountriesLocalize() throws Exception {
        // Initialize the database
        countriesLocalizeRepository.saveAndFlush(countriesLocalize);

        int databaseSizeBeforeUpdate = countriesLocalizeRepository.findAll().size();

        // Update the countriesLocalize
        CountriesLocalize updatedCountriesLocalize = countriesLocalizeRepository.findById(countriesLocalize.getId()).get();
        // Disconnect from session so that the updates on updatedCountriesLocalize are not directly saved in db
        em.detach(updatedCountriesLocalize);
        updatedCountriesLocalize
            .name(UPDATED_NAME);
        CountriesLocalizeDTO countriesLocalizeDTO = countriesLocalizeMapper.toDto(updatedCountriesLocalize);

        restCountriesLocalizeMockMvc.perform(put("/api/countries-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countriesLocalizeDTO)))
            .andExpect(status().isOk());

        // Validate the CountriesLocalize in the database
        List<CountriesLocalize> countriesLocalizeList = countriesLocalizeRepository.findAll();
        assertThat(countriesLocalizeList).hasSize(databaseSizeBeforeUpdate);
        CountriesLocalize testCountriesLocalize = countriesLocalizeList.get(countriesLocalizeList.size() - 1);
        assertThat(testCountriesLocalize.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCountriesLocalize() throws Exception {
        int databaseSizeBeforeUpdate = countriesLocalizeRepository.findAll().size();

        // Create the CountriesLocalize
        CountriesLocalizeDTO countriesLocalizeDTO = countriesLocalizeMapper.toDto(countriesLocalize);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountriesLocalizeMockMvc.perform(put("/api/countries-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countriesLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CountriesLocalize in the database
        List<CountriesLocalize> countriesLocalizeList = countriesLocalizeRepository.findAll();
        assertThat(countriesLocalizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCountriesLocalize() throws Exception {
        // Initialize the database
        countriesLocalizeRepository.saveAndFlush(countriesLocalize);

        int databaseSizeBeforeDelete = countriesLocalizeRepository.findAll().size();

        // Delete the countriesLocalize
        restCountriesLocalizeMockMvc.perform(delete("/api/countries-localizes/{id}", countriesLocalize.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CountriesLocalize> countriesLocalizeList = countriesLocalizeRepository.findAll();
        assertThat(countriesLocalizeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

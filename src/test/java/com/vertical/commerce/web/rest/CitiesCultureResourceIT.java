package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.CitiesCulture;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.Cities;
import com.vertical.commerce.repository.CitiesCultureRepository;
import com.vertical.commerce.service.CitiesCultureService;
import com.vertical.commerce.service.dto.CitiesCultureDTO;
import com.vertical.commerce.service.mapper.CitiesCultureMapper;
import com.vertical.commerce.service.dto.CitiesCultureCriteria;
import com.vertical.commerce.service.CitiesCultureQueryService;

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
 * Integration tests for the {@link CitiesCultureResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CitiesCultureResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CitiesCultureRepository citiesCultureRepository;

    @Autowired
    private CitiesCultureMapper citiesCultureMapper;

    @Autowired
    private CitiesCultureService citiesCultureService;

    @Autowired
    private CitiesCultureQueryService citiesCultureQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCitiesCultureMockMvc;

    private CitiesCulture citiesCulture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CitiesCulture createEntity(EntityManager em) {
        CitiesCulture citiesCulture = new CitiesCulture()
            .name(DEFAULT_NAME);
        return citiesCulture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CitiesCulture createUpdatedEntity(EntityManager em) {
        CitiesCulture citiesCulture = new CitiesCulture()
            .name(UPDATED_NAME);
        return citiesCulture;
    }

    @BeforeEach
    public void initTest() {
        citiesCulture = createEntity(em);
    }

    @Test
    @Transactional
    public void createCitiesCulture() throws Exception {
        int databaseSizeBeforeCreate = citiesCultureRepository.findAll().size();
        // Create the CitiesCulture
        CitiesCultureDTO citiesCultureDTO = citiesCultureMapper.toDto(citiesCulture);
        restCitiesCultureMockMvc.perform(post("/api/cities-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(citiesCultureDTO)))
            .andExpect(status().isCreated());

        // Validate the CitiesCulture in the database
        List<CitiesCulture> citiesCultureList = citiesCultureRepository.findAll();
        assertThat(citiesCultureList).hasSize(databaseSizeBeforeCreate + 1);
        CitiesCulture testCitiesCulture = citiesCultureList.get(citiesCultureList.size() - 1);
        assertThat(testCitiesCulture.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCitiesCultureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = citiesCultureRepository.findAll().size();

        // Create the CitiesCulture with an existing ID
        citiesCulture.setId(1L);
        CitiesCultureDTO citiesCultureDTO = citiesCultureMapper.toDto(citiesCulture);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCitiesCultureMockMvc.perform(post("/api/cities-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(citiesCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CitiesCulture in the database
        List<CitiesCulture> citiesCultureList = citiesCultureRepository.findAll();
        assertThat(citiesCultureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = citiesCultureRepository.findAll().size();
        // set the field null
        citiesCulture.setName(null);

        // Create the CitiesCulture, which fails.
        CitiesCultureDTO citiesCultureDTO = citiesCultureMapper.toDto(citiesCulture);


        restCitiesCultureMockMvc.perform(post("/api/cities-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(citiesCultureDTO)))
            .andExpect(status().isBadRequest());

        List<CitiesCulture> citiesCultureList = citiesCultureRepository.findAll();
        assertThat(citiesCultureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCitiesCultures() throws Exception {
        // Initialize the database
        citiesCultureRepository.saveAndFlush(citiesCulture);

        // Get all the citiesCultureList
        restCitiesCultureMockMvc.perform(get("/api/cities-cultures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(citiesCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getCitiesCulture() throws Exception {
        // Initialize the database
        citiesCultureRepository.saveAndFlush(citiesCulture);

        // Get the citiesCulture
        restCitiesCultureMockMvc.perform(get("/api/cities-cultures/{id}", citiesCulture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(citiesCulture.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getCitiesCulturesByIdFiltering() throws Exception {
        // Initialize the database
        citiesCultureRepository.saveAndFlush(citiesCulture);

        Long id = citiesCulture.getId();

        defaultCitiesCultureShouldBeFound("id.equals=" + id);
        defaultCitiesCultureShouldNotBeFound("id.notEquals=" + id);

        defaultCitiesCultureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCitiesCultureShouldNotBeFound("id.greaterThan=" + id);

        defaultCitiesCultureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCitiesCultureShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCitiesCulturesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesCultureRepository.saveAndFlush(citiesCulture);

        // Get all the citiesCultureList where name equals to DEFAULT_NAME
        defaultCitiesCultureShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the citiesCultureList where name equals to UPDATED_NAME
        defaultCitiesCultureShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCitiesCulturesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citiesCultureRepository.saveAndFlush(citiesCulture);

        // Get all the citiesCultureList where name not equals to DEFAULT_NAME
        defaultCitiesCultureShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the citiesCultureList where name not equals to UPDATED_NAME
        defaultCitiesCultureShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCitiesCulturesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        citiesCultureRepository.saveAndFlush(citiesCulture);

        // Get all the citiesCultureList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCitiesCultureShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the citiesCultureList where name equals to UPDATED_NAME
        defaultCitiesCultureShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCitiesCulturesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesCultureRepository.saveAndFlush(citiesCulture);

        // Get all the citiesCultureList where name is not null
        defaultCitiesCultureShouldBeFound("name.specified=true");

        // Get all the citiesCultureList where name is null
        defaultCitiesCultureShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCitiesCulturesByNameContainsSomething() throws Exception {
        // Initialize the database
        citiesCultureRepository.saveAndFlush(citiesCulture);

        // Get all the citiesCultureList where name contains DEFAULT_NAME
        defaultCitiesCultureShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the citiesCultureList where name contains UPDATED_NAME
        defaultCitiesCultureShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCitiesCulturesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        citiesCultureRepository.saveAndFlush(citiesCulture);

        // Get all the citiesCultureList where name does not contain DEFAULT_NAME
        defaultCitiesCultureShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the citiesCultureList where name does not contain UPDATED_NAME
        defaultCitiesCultureShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCitiesCulturesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesCultureRepository.saveAndFlush(citiesCulture);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        citiesCulture.setCulture(culture);
        citiesCultureRepository.saveAndFlush(citiesCulture);
        Long cultureId = culture.getId();

        // Get all the citiesCultureList where culture equals to cultureId
        defaultCitiesCultureShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the citiesCultureList where culture equals to cultureId + 1
        defaultCitiesCultureShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllCitiesCulturesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesCultureRepository.saveAndFlush(citiesCulture);
        Cities city = CitiesResourceIT.createEntity(em);
        em.persist(city);
        em.flush();
        citiesCulture.setCity(city);
        citiesCultureRepository.saveAndFlush(citiesCulture);
        Long cityId = city.getId();

        // Get all the citiesCultureList where city equals to cityId
        defaultCitiesCultureShouldBeFound("cityId.equals=" + cityId);

        // Get all the citiesCultureList where city equals to cityId + 1
        defaultCitiesCultureShouldNotBeFound("cityId.equals=" + (cityId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCitiesCultureShouldBeFound(String filter) throws Exception {
        restCitiesCultureMockMvc.perform(get("/api/cities-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(citiesCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restCitiesCultureMockMvc.perform(get("/api/cities-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCitiesCultureShouldNotBeFound(String filter) throws Exception {
        restCitiesCultureMockMvc.perform(get("/api/cities-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCitiesCultureMockMvc.perform(get("/api/cities-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCitiesCulture() throws Exception {
        // Get the citiesCulture
        restCitiesCultureMockMvc.perform(get("/api/cities-cultures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCitiesCulture() throws Exception {
        // Initialize the database
        citiesCultureRepository.saveAndFlush(citiesCulture);

        int databaseSizeBeforeUpdate = citiesCultureRepository.findAll().size();

        // Update the citiesCulture
        CitiesCulture updatedCitiesCulture = citiesCultureRepository.findById(citiesCulture.getId()).get();
        // Disconnect from session so that the updates on updatedCitiesCulture are not directly saved in db
        em.detach(updatedCitiesCulture);
        updatedCitiesCulture
            .name(UPDATED_NAME);
        CitiesCultureDTO citiesCultureDTO = citiesCultureMapper.toDto(updatedCitiesCulture);

        restCitiesCultureMockMvc.perform(put("/api/cities-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(citiesCultureDTO)))
            .andExpect(status().isOk());

        // Validate the CitiesCulture in the database
        List<CitiesCulture> citiesCultureList = citiesCultureRepository.findAll();
        assertThat(citiesCultureList).hasSize(databaseSizeBeforeUpdate);
        CitiesCulture testCitiesCulture = citiesCultureList.get(citiesCultureList.size() - 1);
        assertThat(testCitiesCulture.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCitiesCulture() throws Exception {
        int databaseSizeBeforeUpdate = citiesCultureRepository.findAll().size();

        // Create the CitiesCulture
        CitiesCultureDTO citiesCultureDTO = citiesCultureMapper.toDto(citiesCulture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitiesCultureMockMvc.perform(put("/api/cities-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(citiesCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CitiesCulture in the database
        List<CitiesCulture> citiesCultureList = citiesCultureRepository.findAll();
        assertThat(citiesCultureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCitiesCulture() throws Exception {
        // Initialize the database
        citiesCultureRepository.saveAndFlush(citiesCulture);

        int databaseSizeBeforeDelete = citiesCultureRepository.findAll().size();

        // Delete the citiesCulture
        restCitiesCultureMockMvc.perform(delete("/api/cities-cultures/{id}", citiesCulture.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CitiesCulture> citiesCultureList = citiesCultureRepository.findAll();
        assertThat(citiesCultureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

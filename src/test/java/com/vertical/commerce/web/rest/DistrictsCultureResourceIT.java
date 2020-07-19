package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.DistrictsCulture;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.Districts;
import com.vertical.commerce.repository.DistrictsCultureRepository;
import com.vertical.commerce.service.DistrictsCultureService;
import com.vertical.commerce.service.dto.DistrictsCultureDTO;
import com.vertical.commerce.service.mapper.DistrictsCultureMapper;
import com.vertical.commerce.service.dto.DistrictsCultureCriteria;
import com.vertical.commerce.service.DistrictsCultureQueryService;

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
 * Integration tests for the {@link DistrictsCultureResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class DistrictsCultureResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DistrictsCultureRepository districtsCultureRepository;

    @Autowired
    private DistrictsCultureMapper districtsCultureMapper;

    @Autowired
    private DistrictsCultureService districtsCultureService;

    @Autowired
    private DistrictsCultureQueryService districtsCultureQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDistrictsCultureMockMvc;

    private DistrictsCulture districtsCulture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DistrictsCulture createEntity(EntityManager em) {
        DistrictsCulture districtsCulture = new DistrictsCulture()
            .name(DEFAULT_NAME);
        return districtsCulture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DistrictsCulture createUpdatedEntity(EntityManager em) {
        DistrictsCulture districtsCulture = new DistrictsCulture()
            .name(UPDATED_NAME);
        return districtsCulture;
    }

    @BeforeEach
    public void initTest() {
        districtsCulture = createEntity(em);
    }

    @Test
    @Transactional
    public void createDistrictsCulture() throws Exception {
        int databaseSizeBeforeCreate = districtsCultureRepository.findAll().size();
        // Create the DistrictsCulture
        DistrictsCultureDTO districtsCultureDTO = districtsCultureMapper.toDto(districtsCulture);
        restDistrictsCultureMockMvc.perform(post("/api/districts-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(districtsCultureDTO)))
            .andExpect(status().isCreated());

        // Validate the DistrictsCulture in the database
        List<DistrictsCulture> districtsCultureList = districtsCultureRepository.findAll();
        assertThat(districtsCultureList).hasSize(databaseSizeBeforeCreate + 1);
        DistrictsCulture testDistrictsCulture = districtsCultureList.get(districtsCultureList.size() - 1);
        assertThat(testDistrictsCulture.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDistrictsCultureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = districtsCultureRepository.findAll().size();

        // Create the DistrictsCulture with an existing ID
        districtsCulture.setId(1L);
        DistrictsCultureDTO districtsCultureDTO = districtsCultureMapper.toDto(districtsCulture);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistrictsCultureMockMvc.perform(post("/api/districts-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(districtsCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DistrictsCulture in the database
        List<DistrictsCulture> districtsCultureList = districtsCultureRepository.findAll();
        assertThat(districtsCultureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = districtsCultureRepository.findAll().size();
        // set the field null
        districtsCulture.setName(null);

        // Create the DistrictsCulture, which fails.
        DistrictsCultureDTO districtsCultureDTO = districtsCultureMapper.toDto(districtsCulture);


        restDistrictsCultureMockMvc.perform(post("/api/districts-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(districtsCultureDTO)))
            .andExpect(status().isBadRequest());

        List<DistrictsCulture> districtsCultureList = districtsCultureRepository.findAll();
        assertThat(districtsCultureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDistrictsCultures() throws Exception {
        // Initialize the database
        districtsCultureRepository.saveAndFlush(districtsCulture);

        // Get all the districtsCultureList
        restDistrictsCultureMockMvc.perform(get("/api/districts-cultures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(districtsCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getDistrictsCulture() throws Exception {
        // Initialize the database
        districtsCultureRepository.saveAndFlush(districtsCulture);

        // Get the districtsCulture
        restDistrictsCultureMockMvc.perform(get("/api/districts-cultures/{id}", districtsCulture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(districtsCulture.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getDistrictsCulturesByIdFiltering() throws Exception {
        // Initialize the database
        districtsCultureRepository.saveAndFlush(districtsCulture);

        Long id = districtsCulture.getId();

        defaultDistrictsCultureShouldBeFound("id.equals=" + id);
        defaultDistrictsCultureShouldNotBeFound("id.notEquals=" + id);

        defaultDistrictsCultureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDistrictsCultureShouldNotBeFound("id.greaterThan=" + id);

        defaultDistrictsCultureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDistrictsCultureShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDistrictsCulturesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        districtsCultureRepository.saveAndFlush(districtsCulture);

        // Get all the districtsCultureList where name equals to DEFAULT_NAME
        defaultDistrictsCultureShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the districtsCultureList where name equals to UPDATED_NAME
        defaultDistrictsCultureShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsCulturesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        districtsCultureRepository.saveAndFlush(districtsCulture);

        // Get all the districtsCultureList where name not equals to DEFAULT_NAME
        defaultDistrictsCultureShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the districtsCultureList where name not equals to UPDATED_NAME
        defaultDistrictsCultureShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsCulturesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        districtsCultureRepository.saveAndFlush(districtsCulture);

        // Get all the districtsCultureList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDistrictsCultureShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the districtsCultureList where name equals to UPDATED_NAME
        defaultDistrictsCultureShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsCulturesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        districtsCultureRepository.saveAndFlush(districtsCulture);

        // Get all the districtsCultureList where name is not null
        defaultDistrictsCultureShouldBeFound("name.specified=true");

        // Get all the districtsCultureList where name is null
        defaultDistrictsCultureShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDistrictsCulturesByNameContainsSomething() throws Exception {
        // Initialize the database
        districtsCultureRepository.saveAndFlush(districtsCulture);

        // Get all the districtsCultureList where name contains DEFAULT_NAME
        defaultDistrictsCultureShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the districtsCultureList where name contains UPDATED_NAME
        defaultDistrictsCultureShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsCulturesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        districtsCultureRepository.saveAndFlush(districtsCulture);

        // Get all the districtsCultureList where name does not contain DEFAULT_NAME
        defaultDistrictsCultureShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the districtsCultureList where name does not contain UPDATED_NAME
        defaultDistrictsCultureShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDistrictsCulturesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        districtsCultureRepository.saveAndFlush(districtsCulture);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        districtsCulture.setCulture(culture);
        districtsCultureRepository.saveAndFlush(districtsCulture);
        Long cultureId = culture.getId();

        // Get all the districtsCultureList where culture equals to cultureId
        defaultDistrictsCultureShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the districtsCultureList where culture equals to cultureId + 1
        defaultDistrictsCultureShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllDistrictsCulturesByDistinctIsEqualToSomething() throws Exception {
        // Initialize the database
        districtsCultureRepository.saveAndFlush(districtsCulture);
        Districts distinct = DistrictsResourceIT.createEntity(em);
        em.persist(distinct);
        em.flush();
        districtsCulture.setDistinct(distinct);
        districtsCultureRepository.saveAndFlush(districtsCulture);
        Long distinctId = distinct.getId();

        // Get all the districtsCultureList where distinct equals to distinctId
        defaultDistrictsCultureShouldBeFound("distinctId.equals=" + distinctId);

        // Get all the districtsCultureList where distinct equals to distinctId + 1
        defaultDistrictsCultureShouldNotBeFound("distinctId.equals=" + (distinctId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDistrictsCultureShouldBeFound(String filter) throws Exception {
        restDistrictsCultureMockMvc.perform(get("/api/districts-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(districtsCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restDistrictsCultureMockMvc.perform(get("/api/districts-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDistrictsCultureShouldNotBeFound(String filter) throws Exception {
        restDistrictsCultureMockMvc.perform(get("/api/districts-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDistrictsCultureMockMvc.perform(get("/api/districts-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDistrictsCulture() throws Exception {
        // Get the districtsCulture
        restDistrictsCultureMockMvc.perform(get("/api/districts-cultures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDistrictsCulture() throws Exception {
        // Initialize the database
        districtsCultureRepository.saveAndFlush(districtsCulture);

        int databaseSizeBeforeUpdate = districtsCultureRepository.findAll().size();

        // Update the districtsCulture
        DistrictsCulture updatedDistrictsCulture = districtsCultureRepository.findById(districtsCulture.getId()).get();
        // Disconnect from session so that the updates on updatedDistrictsCulture are not directly saved in db
        em.detach(updatedDistrictsCulture);
        updatedDistrictsCulture
            .name(UPDATED_NAME);
        DistrictsCultureDTO districtsCultureDTO = districtsCultureMapper.toDto(updatedDistrictsCulture);

        restDistrictsCultureMockMvc.perform(put("/api/districts-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(districtsCultureDTO)))
            .andExpect(status().isOk());

        // Validate the DistrictsCulture in the database
        List<DistrictsCulture> districtsCultureList = districtsCultureRepository.findAll();
        assertThat(districtsCultureList).hasSize(databaseSizeBeforeUpdate);
        DistrictsCulture testDistrictsCulture = districtsCultureList.get(districtsCultureList.size() - 1);
        assertThat(testDistrictsCulture.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDistrictsCulture() throws Exception {
        int databaseSizeBeforeUpdate = districtsCultureRepository.findAll().size();

        // Create the DistrictsCulture
        DistrictsCultureDTO districtsCultureDTO = districtsCultureMapper.toDto(districtsCulture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictsCultureMockMvc.perform(put("/api/districts-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(districtsCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DistrictsCulture in the database
        List<DistrictsCulture> districtsCultureList = districtsCultureRepository.findAll();
        assertThat(districtsCultureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDistrictsCulture() throws Exception {
        // Initialize the database
        districtsCultureRepository.saveAndFlush(districtsCulture);

        int databaseSizeBeforeDelete = districtsCultureRepository.findAll().size();

        // Delete the districtsCulture
        restDistrictsCultureMockMvc.perform(delete("/api/districts-cultures/{id}", districtsCulture.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DistrictsCulture> districtsCultureList = districtsCultureRepository.findAll();
        assertThat(districtsCultureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

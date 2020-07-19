package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.RegionsCulture;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.Regions;
import com.vertical.commerce.repository.RegionsCultureRepository;
import com.vertical.commerce.service.RegionsCultureService;
import com.vertical.commerce.service.dto.RegionsCultureDTO;
import com.vertical.commerce.service.mapper.RegionsCultureMapper;
import com.vertical.commerce.service.dto.RegionsCultureCriteria;
import com.vertical.commerce.service.RegionsCultureQueryService;

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
 * Integration tests for the {@link RegionsCultureResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class RegionsCultureResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private RegionsCultureRepository regionsCultureRepository;

    @Autowired
    private RegionsCultureMapper regionsCultureMapper;

    @Autowired
    private RegionsCultureService regionsCultureService;

    @Autowired
    private RegionsCultureQueryService regionsCultureQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegionsCultureMockMvc;

    private RegionsCulture regionsCulture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegionsCulture createEntity(EntityManager em) {
        RegionsCulture regionsCulture = new RegionsCulture()
            .name(DEFAULT_NAME);
        return regionsCulture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegionsCulture createUpdatedEntity(EntityManager em) {
        RegionsCulture regionsCulture = new RegionsCulture()
            .name(UPDATED_NAME);
        return regionsCulture;
    }

    @BeforeEach
    public void initTest() {
        regionsCulture = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegionsCulture() throws Exception {
        int databaseSizeBeforeCreate = regionsCultureRepository.findAll().size();
        // Create the RegionsCulture
        RegionsCultureDTO regionsCultureDTO = regionsCultureMapper.toDto(regionsCulture);
        restRegionsCultureMockMvc.perform(post("/api/regions-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regionsCultureDTO)))
            .andExpect(status().isCreated());

        // Validate the RegionsCulture in the database
        List<RegionsCulture> regionsCultureList = regionsCultureRepository.findAll();
        assertThat(regionsCultureList).hasSize(databaseSizeBeforeCreate + 1);
        RegionsCulture testRegionsCulture = regionsCultureList.get(regionsCultureList.size() - 1);
        assertThat(testRegionsCulture.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createRegionsCultureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regionsCultureRepository.findAll().size();

        // Create the RegionsCulture with an existing ID
        regionsCulture.setId(1L);
        RegionsCultureDTO regionsCultureDTO = regionsCultureMapper.toDto(regionsCulture);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegionsCultureMockMvc.perform(post("/api/regions-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regionsCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegionsCulture in the database
        List<RegionsCulture> regionsCultureList = regionsCultureRepository.findAll();
        assertThat(regionsCultureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = regionsCultureRepository.findAll().size();
        // set the field null
        regionsCulture.setName(null);

        // Create the RegionsCulture, which fails.
        RegionsCultureDTO regionsCultureDTO = regionsCultureMapper.toDto(regionsCulture);


        restRegionsCultureMockMvc.perform(post("/api/regions-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regionsCultureDTO)))
            .andExpect(status().isBadRequest());

        List<RegionsCulture> regionsCultureList = regionsCultureRepository.findAll();
        assertThat(regionsCultureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegionsCultures() throws Exception {
        // Initialize the database
        regionsCultureRepository.saveAndFlush(regionsCulture);

        // Get all the regionsCultureList
        restRegionsCultureMockMvc.perform(get("/api/regions-cultures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regionsCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getRegionsCulture() throws Exception {
        // Initialize the database
        regionsCultureRepository.saveAndFlush(regionsCulture);

        // Get the regionsCulture
        restRegionsCultureMockMvc.perform(get("/api/regions-cultures/{id}", regionsCulture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(regionsCulture.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getRegionsCulturesByIdFiltering() throws Exception {
        // Initialize the database
        regionsCultureRepository.saveAndFlush(regionsCulture);

        Long id = regionsCulture.getId();

        defaultRegionsCultureShouldBeFound("id.equals=" + id);
        defaultRegionsCultureShouldNotBeFound("id.notEquals=" + id);

        defaultRegionsCultureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRegionsCultureShouldNotBeFound("id.greaterThan=" + id);

        defaultRegionsCultureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRegionsCultureShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRegionsCulturesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        regionsCultureRepository.saveAndFlush(regionsCulture);

        // Get all the regionsCultureList where name equals to DEFAULT_NAME
        defaultRegionsCultureShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the regionsCultureList where name equals to UPDATED_NAME
        defaultRegionsCultureShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegionsCulturesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regionsCultureRepository.saveAndFlush(regionsCulture);

        // Get all the regionsCultureList where name not equals to DEFAULT_NAME
        defaultRegionsCultureShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the regionsCultureList where name not equals to UPDATED_NAME
        defaultRegionsCultureShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegionsCulturesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        regionsCultureRepository.saveAndFlush(regionsCulture);

        // Get all the regionsCultureList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRegionsCultureShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the regionsCultureList where name equals to UPDATED_NAME
        defaultRegionsCultureShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegionsCulturesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionsCultureRepository.saveAndFlush(regionsCulture);

        // Get all the regionsCultureList where name is not null
        defaultRegionsCultureShouldBeFound("name.specified=true");

        // Get all the regionsCultureList where name is null
        defaultRegionsCultureShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllRegionsCulturesByNameContainsSomething() throws Exception {
        // Initialize the database
        regionsCultureRepository.saveAndFlush(regionsCulture);

        // Get all the regionsCultureList where name contains DEFAULT_NAME
        defaultRegionsCultureShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the regionsCultureList where name contains UPDATED_NAME
        defaultRegionsCultureShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegionsCulturesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        regionsCultureRepository.saveAndFlush(regionsCulture);

        // Get all the regionsCultureList where name does not contain DEFAULT_NAME
        defaultRegionsCultureShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the regionsCultureList where name does not contain UPDATED_NAME
        defaultRegionsCultureShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllRegionsCulturesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        regionsCultureRepository.saveAndFlush(regionsCulture);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        regionsCulture.setCulture(culture);
        regionsCultureRepository.saveAndFlush(regionsCulture);
        Long cultureId = culture.getId();

        // Get all the regionsCultureList where culture equals to cultureId
        defaultRegionsCultureShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the regionsCultureList where culture equals to cultureId + 1
        defaultRegionsCultureShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllRegionsCulturesByRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        regionsCultureRepository.saveAndFlush(regionsCulture);
        Regions region = RegionsResourceIT.createEntity(em);
        em.persist(region);
        em.flush();
        regionsCulture.setRegion(region);
        regionsCultureRepository.saveAndFlush(regionsCulture);
        Long regionId = region.getId();

        // Get all the regionsCultureList where region equals to regionId
        defaultRegionsCultureShouldBeFound("regionId.equals=" + regionId);

        // Get all the regionsCultureList where region equals to regionId + 1
        defaultRegionsCultureShouldNotBeFound("regionId.equals=" + (regionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRegionsCultureShouldBeFound(String filter) throws Exception {
        restRegionsCultureMockMvc.perform(get("/api/regions-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regionsCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restRegionsCultureMockMvc.perform(get("/api/regions-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRegionsCultureShouldNotBeFound(String filter) throws Exception {
        restRegionsCultureMockMvc.perform(get("/api/regions-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRegionsCultureMockMvc.perform(get("/api/regions-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRegionsCulture() throws Exception {
        // Get the regionsCulture
        restRegionsCultureMockMvc.perform(get("/api/regions-cultures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegionsCulture() throws Exception {
        // Initialize the database
        regionsCultureRepository.saveAndFlush(regionsCulture);

        int databaseSizeBeforeUpdate = regionsCultureRepository.findAll().size();

        // Update the regionsCulture
        RegionsCulture updatedRegionsCulture = regionsCultureRepository.findById(regionsCulture.getId()).get();
        // Disconnect from session so that the updates on updatedRegionsCulture are not directly saved in db
        em.detach(updatedRegionsCulture);
        updatedRegionsCulture
            .name(UPDATED_NAME);
        RegionsCultureDTO regionsCultureDTO = regionsCultureMapper.toDto(updatedRegionsCulture);

        restRegionsCultureMockMvc.perform(put("/api/regions-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regionsCultureDTO)))
            .andExpect(status().isOk());

        // Validate the RegionsCulture in the database
        List<RegionsCulture> regionsCultureList = regionsCultureRepository.findAll();
        assertThat(regionsCultureList).hasSize(databaseSizeBeforeUpdate);
        RegionsCulture testRegionsCulture = regionsCultureList.get(regionsCultureList.size() - 1);
        assertThat(testRegionsCulture.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingRegionsCulture() throws Exception {
        int databaseSizeBeforeUpdate = regionsCultureRepository.findAll().size();

        // Create the RegionsCulture
        RegionsCultureDTO regionsCultureDTO = regionsCultureMapper.toDto(regionsCulture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegionsCultureMockMvc.perform(put("/api/regions-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regionsCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegionsCulture in the database
        List<RegionsCulture> regionsCultureList = regionsCultureRepository.findAll();
        assertThat(regionsCultureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegionsCulture() throws Exception {
        // Initialize the database
        regionsCultureRepository.saveAndFlush(regionsCulture);

        int databaseSizeBeforeDelete = regionsCultureRepository.findAll().size();

        // Delete the regionsCulture
        restRegionsCultureMockMvc.perform(delete("/api/regions-cultures/{id}", regionsCulture.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegionsCulture> regionsCultureList = regionsCultureRepository.findAll();
        assertThat(regionsCultureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

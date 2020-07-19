package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.TownshipsCulture;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.Townships;
import com.vertical.commerce.repository.TownshipsCultureRepository;
import com.vertical.commerce.service.TownshipsCultureService;
import com.vertical.commerce.service.dto.TownshipsCultureDTO;
import com.vertical.commerce.service.mapper.TownshipsCultureMapper;
import com.vertical.commerce.service.dto.TownshipsCultureCriteria;
import com.vertical.commerce.service.TownshipsCultureQueryService;

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
 * Integration tests for the {@link TownshipsCultureResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TownshipsCultureResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TownshipsCultureRepository townshipsCultureRepository;

    @Autowired
    private TownshipsCultureMapper townshipsCultureMapper;

    @Autowired
    private TownshipsCultureService townshipsCultureService;

    @Autowired
    private TownshipsCultureQueryService townshipsCultureQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTownshipsCultureMockMvc;

    private TownshipsCulture townshipsCulture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TownshipsCulture createEntity(EntityManager em) {
        TownshipsCulture townshipsCulture = new TownshipsCulture()
            .name(DEFAULT_NAME);
        return townshipsCulture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TownshipsCulture createUpdatedEntity(EntityManager em) {
        TownshipsCulture townshipsCulture = new TownshipsCulture()
            .name(UPDATED_NAME);
        return townshipsCulture;
    }

    @BeforeEach
    public void initTest() {
        townshipsCulture = createEntity(em);
    }

    @Test
    @Transactional
    public void createTownshipsCulture() throws Exception {
        int databaseSizeBeforeCreate = townshipsCultureRepository.findAll().size();
        // Create the TownshipsCulture
        TownshipsCultureDTO townshipsCultureDTO = townshipsCultureMapper.toDto(townshipsCulture);
        restTownshipsCultureMockMvc.perform(post("/api/townships-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townshipsCultureDTO)))
            .andExpect(status().isCreated());

        // Validate the TownshipsCulture in the database
        List<TownshipsCulture> townshipsCultureList = townshipsCultureRepository.findAll();
        assertThat(townshipsCultureList).hasSize(databaseSizeBeforeCreate + 1);
        TownshipsCulture testTownshipsCulture = townshipsCultureList.get(townshipsCultureList.size() - 1);
        assertThat(testTownshipsCulture.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTownshipsCultureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = townshipsCultureRepository.findAll().size();

        // Create the TownshipsCulture with an existing ID
        townshipsCulture.setId(1L);
        TownshipsCultureDTO townshipsCultureDTO = townshipsCultureMapper.toDto(townshipsCulture);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTownshipsCultureMockMvc.perform(post("/api/townships-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townshipsCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TownshipsCulture in the database
        List<TownshipsCulture> townshipsCultureList = townshipsCultureRepository.findAll();
        assertThat(townshipsCultureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = townshipsCultureRepository.findAll().size();
        // set the field null
        townshipsCulture.setName(null);

        // Create the TownshipsCulture, which fails.
        TownshipsCultureDTO townshipsCultureDTO = townshipsCultureMapper.toDto(townshipsCulture);


        restTownshipsCultureMockMvc.perform(post("/api/townships-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townshipsCultureDTO)))
            .andExpect(status().isBadRequest());

        List<TownshipsCulture> townshipsCultureList = townshipsCultureRepository.findAll();
        assertThat(townshipsCultureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTownshipsCultures() throws Exception {
        // Initialize the database
        townshipsCultureRepository.saveAndFlush(townshipsCulture);

        // Get all the townshipsCultureList
        restTownshipsCultureMockMvc.perform(get("/api/townships-cultures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(townshipsCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getTownshipsCulture() throws Exception {
        // Initialize the database
        townshipsCultureRepository.saveAndFlush(townshipsCulture);

        // Get the townshipsCulture
        restTownshipsCultureMockMvc.perform(get("/api/townships-cultures/{id}", townshipsCulture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(townshipsCulture.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getTownshipsCulturesByIdFiltering() throws Exception {
        // Initialize the database
        townshipsCultureRepository.saveAndFlush(townshipsCulture);

        Long id = townshipsCulture.getId();

        defaultTownshipsCultureShouldBeFound("id.equals=" + id);
        defaultTownshipsCultureShouldNotBeFound("id.notEquals=" + id);

        defaultTownshipsCultureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTownshipsCultureShouldNotBeFound("id.greaterThan=" + id);

        defaultTownshipsCultureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTownshipsCultureShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTownshipsCulturesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        townshipsCultureRepository.saveAndFlush(townshipsCulture);

        // Get all the townshipsCultureList where name equals to DEFAULT_NAME
        defaultTownshipsCultureShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the townshipsCultureList where name equals to UPDATED_NAME
        defaultTownshipsCultureShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownshipsCulturesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        townshipsCultureRepository.saveAndFlush(townshipsCulture);

        // Get all the townshipsCultureList where name not equals to DEFAULT_NAME
        defaultTownshipsCultureShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the townshipsCultureList where name not equals to UPDATED_NAME
        defaultTownshipsCultureShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownshipsCulturesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        townshipsCultureRepository.saveAndFlush(townshipsCulture);

        // Get all the townshipsCultureList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTownshipsCultureShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the townshipsCultureList where name equals to UPDATED_NAME
        defaultTownshipsCultureShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownshipsCulturesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        townshipsCultureRepository.saveAndFlush(townshipsCulture);

        // Get all the townshipsCultureList where name is not null
        defaultTownshipsCultureShouldBeFound("name.specified=true");

        // Get all the townshipsCultureList where name is null
        defaultTownshipsCultureShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTownshipsCulturesByNameContainsSomething() throws Exception {
        // Initialize the database
        townshipsCultureRepository.saveAndFlush(townshipsCulture);

        // Get all the townshipsCultureList where name contains DEFAULT_NAME
        defaultTownshipsCultureShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the townshipsCultureList where name contains UPDATED_NAME
        defaultTownshipsCultureShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownshipsCulturesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        townshipsCultureRepository.saveAndFlush(townshipsCulture);

        // Get all the townshipsCultureList where name does not contain DEFAULT_NAME
        defaultTownshipsCultureShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the townshipsCultureList where name does not contain UPDATED_NAME
        defaultTownshipsCultureShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTownshipsCulturesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        townshipsCultureRepository.saveAndFlush(townshipsCulture);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        townshipsCulture.setCulture(culture);
        townshipsCultureRepository.saveAndFlush(townshipsCulture);
        Long cultureId = culture.getId();

        // Get all the townshipsCultureList where culture equals to cultureId
        defaultTownshipsCultureShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the townshipsCultureList where culture equals to cultureId + 1
        defaultTownshipsCultureShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllTownshipsCulturesByTownshipIsEqualToSomething() throws Exception {
        // Initialize the database
        townshipsCultureRepository.saveAndFlush(townshipsCulture);
        Townships township = TownshipsResourceIT.createEntity(em);
        em.persist(township);
        em.flush();
        townshipsCulture.setTownship(township);
        townshipsCultureRepository.saveAndFlush(townshipsCulture);
        Long townshipId = township.getId();

        // Get all the townshipsCultureList where township equals to townshipId
        defaultTownshipsCultureShouldBeFound("townshipId.equals=" + townshipId);

        // Get all the townshipsCultureList where township equals to townshipId + 1
        defaultTownshipsCultureShouldNotBeFound("townshipId.equals=" + (townshipId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTownshipsCultureShouldBeFound(String filter) throws Exception {
        restTownshipsCultureMockMvc.perform(get("/api/townships-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(townshipsCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restTownshipsCultureMockMvc.perform(get("/api/townships-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTownshipsCultureShouldNotBeFound(String filter) throws Exception {
        restTownshipsCultureMockMvc.perform(get("/api/townships-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTownshipsCultureMockMvc.perform(get("/api/townships-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTownshipsCulture() throws Exception {
        // Get the townshipsCulture
        restTownshipsCultureMockMvc.perform(get("/api/townships-cultures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTownshipsCulture() throws Exception {
        // Initialize the database
        townshipsCultureRepository.saveAndFlush(townshipsCulture);

        int databaseSizeBeforeUpdate = townshipsCultureRepository.findAll().size();

        // Update the townshipsCulture
        TownshipsCulture updatedTownshipsCulture = townshipsCultureRepository.findById(townshipsCulture.getId()).get();
        // Disconnect from session so that the updates on updatedTownshipsCulture are not directly saved in db
        em.detach(updatedTownshipsCulture);
        updatedTownshipsCulture
            .name(UPDATED_NAME);
        TownshipsCultureDTO townshipsCultureDTO = townshipsCultureMapper.toDto(updatedTownshipsCulture);

        restTownshipsCultureMockMvc.perform(put("/api/townships-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townshipsCultureDTO)))
            .andExpect(status().isOk());

        // Validate the TownshipsCulture in the database
        List<TownshipsCulture> townshipsCultureList = townshipsCultureRepository.findAll();
        assertThat(townshipsCultureList).hasSize(databaseSizeBeforeUpdate);
        TownshipsCulture testTownshipsCulture = townshipsCultureList.get(townshipsCultureList.size() - 1);
        assertThat(testTownshipsCulture.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTownshipsCulture() throws Exception {
        int databaseSizeBeforeUpdate = townshipsCultureRepository.findAll().size();

        // Create the TownshipsCulture
        TownshipsCultureDTO townshipsCultureDTO = townshipsCultureMapper.toDto(townshipsCulture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTownshipsCultureMockMvc.perform(put("/api/townships-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townshipsCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TownshipsCulture in the database
        List<TownshipsCulture> townshipsCultureList = townshipsCultureRepository.findAll();
        assertThat(townshipsCultureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTownshipsCulture() throws Exception {
        // Initialize the database
        townshipsCultureRepository.saveAndFlush(townshipsCulture);

        int databaseSizeBeforeDelete = townshipsCultureRepository.findAll().size();

        // Delete the townshipsCulture
        restTownshipsCultureMockMvc.perform(delete("/api/townships-cultures/{id}", townshipsCulture.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TownshipsCulture> townshipsCultureList = townshipsCultureRepository.findAll();
        assertThat(townshipsCultureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

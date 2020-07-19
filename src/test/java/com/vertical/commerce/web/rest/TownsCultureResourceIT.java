package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.TownsCulture;
import com.vertical.commerce.repository.TownsCultureRepository;
import com.vertical.commerce.service.TownsCultureService;
import com.vertical.commerce.service.dto.TownsCultureDTO;
import com.vertical.commerce.service.mapper.TownsCultureMapper;
import com.vertical.commerce.service.dto.TownsCultureCriteria;
import com.vertical.commerce.service.TownsCultureQueryService;

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
 * Integration tests for the {@link TownsCultureResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TownsCultureResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TownsCultureRepository townsCultureRepository;

    @Autowired
    private TownsCultureMapper townsCultureMapper;

    @Autowired
    private TownsCultureService townsCultureService;

    @Autowired
    private TownsCultureQueryService townsCultureQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTownsCultureMockMvc;

    private TownsCulture townsCulture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TownsCulture createEntity(EntityManager em) {
        TownsCulture townsCulture = new TownsCulture()
            .name(DEFAULT_NAME);
        return townsCulture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TownsCulture createUpdatedEntity(EntityManager em) {
        TownsCulture townsCulture = new TownsCulture()
            .name(UPDATED_NAME);
        return townsCulture;
    }

    @BeforeEach
    public void initTest() {
        townsCulture = createEntity(em);
    }

    @Test
    @Transactional
    public void createTownsCulture() throws Exception {
        int databaseSizeBeforeCreate = townsCultureRepository.findAll().size();
        // Create the TownsCulture
        TownsCultureDTO townsCultureDTO = townsCultureMapper.toDto(townsCulture);
        restTownsCultureMockMvc.perform(post("/api/towns-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townsCultureDTO)))
            .andExpect(status().isCreated());

        // Validate the TownsCulture in the database
        List<TownsCulture> townsCultureList = townsCultureRepository.findAll();
        assertThat(townsCultureList).hasSize(databaseSizeBeforeCreate + 1);
        TownsCulture testTownsCulture = townsCultureList.get(townsCultureList.size() - 1);
        assertThat(testTownsCulture.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTownsCultureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = townsCultureRepository.findAll().size();

        // Create the TownsCulture with an existing ID
        townsCulture.setId(1L);
        TownsCultureDTO townsCultureDTO = townsCultureMapper.toDto(townsCulture);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTownsCultureMockMvc.perform(post("/api/towns-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townsCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TownsCulture in the database
        List<TownsCulture> townsCultureList = townsCultureRepository.findAll();
        assertThat(townsCultureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = townsCultureRepository.findAll().size();
        // set the field null
        townsCulture.setName(null);

        // Create the TownsCulture, which fails.
        TownsCultureDTO townsCultureDTO = townsCultureMapper.toDto(townsCulture);


        restTownsCultureMockMvc.perform(post("/api/towns-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townsCultureDTO)))
            .andExpect(status().isBadRequest());

        List<TownsCulture> townsCultureList = townsCultureRepository.findAll();
        assertThat(townsCultureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTownsCultures() throws Exception {
        // Initialize the database
        townsCultureRepository.saveAndFlush(townsCulture);

        // Get all the townsCultureList
        restTownsCultureMockMvc.perform(get("/api/towns-cultures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(townsCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getTownsCulture() throws Exception {
        // Initialize the database
        townsCultureRepository.saveAndFlush(townsCulture);

        // Get the townsCulture
        restTownsCultureMockMvc.perform(get("/api/towns-cultures/{id}", townsCulture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(townsCulture.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getTownsCulturesByIdFiltering() throws Exception {
        // Initialize the database
        townsCultureRepository.saveAndFlush(townsCulture);

        Long id = townsCulture.getId();

        defaultTownsCultureShouldBeFound("id.equals=" + id);
        defaultTownsCultureShouldNotBeFound("id.notEquals=" + id);

        defaultTownsCultureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTownsCultureShouldNotBeFound("id.greaterThan=" + id);

        defaultTownsCultureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTownsCultureShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTownsCulturesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        townsCultureRepository.saveAndFlush(townsCulture);

        // Get all the townsCultureList where name equals to DEFAULT_NAME
        defaultTownsCultureShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the townsCultureList where name equals to UPDATED_NAME
        defaultTownsCultureShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownsCulturesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        townsCultureRepository.saveAndFlush(townsCulture);

        // Get all the townsCultureList where name not equals to DEFAULT_NAME
        defaultTownsCultureShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the townsCultureList where name not equals to UPDATED_NAME
        defaultTownsCultureShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownsCulturesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        townsCultureRepository.saveAndFlush(townsCulture);

        // Get all the townsCultureList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTownsCultureShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the townsCultureList where name equals to UPDATED_NAME
        defaultTownsCultureShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownsCulturesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        townsCultureRepository.saveAndFlush(townsCulture);

        // Get all the townsCultureList where name is not null
        defaultTownsCultureShouldBeFound("name.specified=true");

        // Get all the townsCultureList where name is null
        defaultTownsCultureShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTownsCulturesByNameContainsSomething() throws Exception {
        // Initialize the database
        townsCultureRepository.saveAndFlush(townsCulture);

        // Get all the townsCultureList where name contains DEFAULT_NAME
        defaultTownsCultureShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the townsCultureList where name contains UPDATED_NAME
        defaultTownsCultureShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownsCulturesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        townsCultureRepository.saveAndFlush(townsCulture);

        // Get all the townsCultureList where name does not contain DEFAULT_NAME
        defaultTownsCultureShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the townsCultureList where name does not contain UPDATED_NAME
        defaultTownsCultureShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTownsCultureShouldBeFound(String filter) throws Exception {
        restTownsCultureMockMvc.perform(get("/api/towns-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(townsCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restTownsCultureMockMvc.perform(get("/api/towns-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTownsCultureShouldNotBeFound(String filter) throws Exception {
        restTownsCultureMockMvc.perform(get("/api/towns-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTownsCultureMockMvc.perform(get("/api/towns-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTownsCulture() throws Exception {
        // Get the townsCulture
        restTownsCultureMockMvc.perform(get("/api/towns-cultures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTownsCulture() throws Exception {
        // Initialize the database
        townsCultureRepository.saveAndFlush(townsCulture);

        int databaseSizeBeforeUpdate = townsCultureRepository.findAll().size();

        // Update the townsCulture
        TownsCulture updatedTownsCulture = townsCultureRepository.findById(townsCulture.getId()).get();
        // Disconnect from session so that the updates on updatedTownsCulture are not directly saved in db
        em.detach(updatedTownsCulture);
        updatedTownsCulture
            .name(UPDATED_NAME);
        TownsCultureDTO townsCultureDTO = townsCultureMapper.toDto(updatedTownsCulture);

        restTownsCultureMockMvc.perform(put("/api/towns-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townsCultureDTO)))
            .andExpect(status().isOk());

        // Validate the TownsCulture in the database
        List<TownsCulture> townsCultureList = townsCultureRepository.findAll();
        assertThat(townsCultureList).hasSize(databaseSizeBeforeUpdate);
        TownsCulture testTownsCulture = townsCultureList.get(townsCultureList.size() - 1);
        assertThat(testTownsCulture.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTownsCulture() throws Exception {
        int databaseSizeBeforeUpdate = townsCultureRepository.findAll().size();

        // Create the TownsCulture
        TownsCultureDTO townsCultureDTO = townsCultureMapper.toDto(townsCulture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTownsCultureMockMvc.perform(put("/api/towns-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townsCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TownsCulture in the database
        List<TownsCulture> townsCultureList = townsCultureRepository.findAll();
        assertThat(townsCultureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTownsCulture() throws Exception {
        // Initialize the database
        townsCultureRepository.saveAndFlush(townsCulture);

        int databaseSizeBeforeDelete = townsCultureRepository.findAll().size();

        // Delete the townsCulture
        restTownsCultureMockMvc.perform(delete("/api/towns-cultures/{id}", townsCulture.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TownsCulture> townsCultureList = townsCultureRepository.findAll();
        assertThat(townsCultureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.TownsLocalize;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.Towns;
import com.vertical.commerce.repository.TownsLocalizeRepository;
import com.vertical.commerce.service.TownsLocalizeService;
import com.vertical.commerce.service.dto.TownsLocalizeDTO;
import com.vertical.commerce.service.mapper.TownsLocalizeMapper;
import com.vertical.commerce.service.dto.TownsLocalizeCriteria;
import com.vertical.commerce.service.TownsLocalizeQueryService;

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
 * Integration tests for the {@link TownsLocalizeResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TownsLocalizeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TownsLocalizeRepository townsLocalizeRepository;

    @Autowired
    private TownsLocalizeMapper townsLocalizeMapper;

    @Autowired
    private TownsLocalizeService townsLocalizeService;

    @Autowired
    private TownsLocalizeQueryService townsLocalizeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTownsLocalizeMockMvc;

    private TownsLocalize townsLocalize;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TownsLocalize createEntity(EntityManager em) {
        TownsLocalize townsLocalize = new TownsLocalize()
            .name(DEFAULT_NAME);
        return townsLocalize;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TownsLocalize createUpdatedEntity(EntityManager em) {
        TownsLocalize townsLocalize = new TownsLocalize()
            .name(UPDATED_NAME);
        return townsLocalize;
    }

    @BeforeEach
    public void initTest() {
        townsLocalize = createEntity(em);
    }

    @Test
    @Transactional
    public void createTownsLocalize() throws Exception {
        int databaseSizeBeforeCreate = townsLocalizeRepository.findAll().size();
        // Create the TownsLocalize
        TownsLocalizeDTO townsLocalizeDTO = townsLocalizeMapper.toDto(townsLocalize);
        restTownsLocalizeMockMvc.perform(post("/api/towns-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townsLocalizeDTO)))
            .andExpect(status().isCreated());

        // Validate the TownsLocalize in the database
        List<TownsLocalize> townsLocalizeList = townsLocalizeRepository.findAll();
        assertThat(townsLocalizeList).hasSize(databaseSizeBeforeCreate + 1);
        TownsLocalize testTownsLocalize = townsLocalizeList.get(townsLocalizeList.size() - 1);
        assertThat(testTownsLocalize.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTownsLocalizeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = townsLocalizeRepository.findAll().size();

        // Create the TownsLocalize with an existing ID
        townsLocalize.setId(1L);
        TownsLocalizeDTO townsLocalizeDTO = townsLocalizeMapper.toDto(townsLocalize);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTownsLocalizeMockMvc.perform(post("/api/towns-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townsLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TownsLocalize in the database
        List<TownsLocalize> townsLocalizeList = townsLocalizeRepository.findAll();
        assertThat(townsLocalizeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = townsLocalizeRepository.findAll().size();
        // set the field null
        townsLocalize.setName(null);

        // Create the TownsLocalize, which fails.
        TownsLocalizeDTO townsLocalizeDTO = townsLocalizeMapper.toDto(townsLocalize);


        restTownsLocalizeMockMvc.perform(post("/api/towns-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townsLocalizeDTO)))
            .andExpect(status().isBadRequest());

        List<TownsLocalize> townsLocalizeList = townsLocalizeRepository.findAll();
        assertThat(townsLocalizeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTownsLocalizes() throws Exception {
        // Initialize the database
        townsLocalizeRepository.saveAndFlush(townsLocalize);

        // Get all the townsLocalizeList
        restTownsLocalizeMockMvc.perform(get("/api/towns-localizes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(townsLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getTownsLocalize() throws Exception {
        // Initialize the database
        townsLocalizeRepository.saveAndFlush(townsLocalize);

        // Get the townsLocalize
        restTownsLocalizeMockMvc.perform(get("/api/towns-localizes/{id}", townsLocalize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(townsLocalize.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getTownsLocalizesByIdFiltering() throws Exception {
        // Initialize the database
        townsLocalizeRepository.saveAndFlush(townsLocalize);

        Long id = townsLocalize.getId();

        defaultTownsLocalizeShouldBeFound("id.equals=" + id);
        defaultTownsLocalizeShouldNotBeFound("id.notEquals=" + id);

        defaultTownsLocalizeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTownsLocalizeShouldNotBeFound("id.greaterThan=" + id);

        defaultTownsLocalizeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTownsLocalizeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTownsLocalizesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        townsLocalizeRepository.saveAndFlush(townsLocalize);

        // Get all the townsLocalizeList where name equals to DEFAULT_NAME
        defaultTownsLocalizeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the townsLocalizeList where name equals to UPDATED_NAME
        defaultTownsLocalizeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownsLocalizesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        townsLocalizeRepository.saveAndFlush(townsLocalize);

        // Get all the townsLocalizeList where name not equals to DEFAULT_NAME
        defaultTownsLocalizeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the townsLocalizeList where name not equals to UPDATED_NAME
        defaultTownsLocalizeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownsLocalizesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        townsLocalizeRepository.saveAndFlush(townsLocalize);

        // Get all the townsLocalizeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTownsLocalizeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the townsLocalizeList where name equals to UPDATED_NAME
        defaultTownsLocalizeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownsLocalizesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        townsLocalizeRepository.saveAndFlush(townsLocalize);

        // Get all the townsLocalizeList where name is not null
        defaultTownsLocalizeShouldBeFound("name.specified=true");

        // Get all the townsLocalizeList where name is null
        defaultTownsLocalizeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTownsLocalizesByNameContainsSomething() throws Exception {
        // Initialize the database
        townsLocalizeRepository.saveAndFlush(townsLocalize);

        // Get all the townsLocalizeList where name contains DEFAULT_NAME
        defaultTownsLocalizeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the townsLocalizeList where name contains UPDATED_NAME
        defaultTownsLocalizeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownsLocalizesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        townsLocalizeRepository.saveAndFlush(townsLocalize);

        // Get all the townsLocalizeList where name does not contain DEFAULT_NAME
        defaultTownsLocalizeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the townsLocalizeList where name does not contain UPDATED_NAME
        defaultTownsLocalizeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTownsLocalizesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        townsLocalizeRepository.saveAndFlush(townsLocalize);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        townsLocalize.setCulture(culture);
        townsLocalizeRepository.saveAndFlush(townsLocalize);
        Long cultureId = culture.getId();

        // Get all the townsLocalizeList where culture equals to cultureId
        defaultTownsLocalizeShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the townsLocalizeList where culture equals to cultureId + 1
        defaultTownsLocalizeShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllTownsLocalizesByTownIsEqualToSomething() throws Exception {
        // Initialize the database
        townsLocalizeRepository.saveAndFlush(townsLocalize);
        Towns town = TownsResourceIT.createEntity(em);
        em.persist(town);
        em.flush();
        townsLocalize.setTown(town);
        townsLocalizeRepository.saveAndFlush(townsLocalize);
        Long townId = town.getId();

        // Get all the townsLocalizeList where town equals to townId
        defaultTownsLocalizeShouldBeFound("townId.equals=" + townId);

        // Get all the townsLocalizeList where town equals to townId + 1
        defaultTownsLocalizeShouldNotBeFound("townId.equals=" + (townId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTownsLocalizeShouldBeFound(String filter) throws Exception {
        restTownsLocalizeMockMvc.perform(get("/api/towns-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(townsLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restTownsLocalizeMockMvc.perform(get("/api/towns-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTownsLocalizeShouldNotBeFound(String filter) throws Exception {
        restTownsLocalizeMockMvc.perform(get("/api/towns-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTownsLocalizeMockMvc.perform(get("/api/towns-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTownsLocalize() throws Exception {
        // Get the townsLocalize
        restTownsLocalizeMockMvc.perform(get("/api/towns-localizes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTownsLocalize() throws Exception {
        // Initialize the database
        townsLocalizeRepository.saveAndFlush(townsLocalize);

        int databaseSizeBeforeUpdate = townsLocalizeRepository.findAll().size();

        // Update the townsLocalize
        TownsLocalize updatedTownsLocalize = townsLocalizeRepository.findById(townsLocalize.getId()).get();
        // Disconnect from session so that the updates on updatedTownsLocalize are not directly saved in db
        em.detach(updatedTownsLocalize);
        updatedTownsLocalize
            .name(UPDATED_NAME);
        TownsLocalizeDTO townsLocalizeDTO = townsLocalizeMapper.toDto(updatedTownsLocalize);

        restTownsLocalizeMockMvc.perform(put("/api/towns-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townsLocalizeDTO)))
            .andExpect(status().isOk());

        // Validate the TownsLocalize in the database
        List<TownsLocalize> townsLocalizeList = townsLocalizeRepository.findAll();
        assertThat(townsLocalizeList).hasSize(databaseSizeBeforeUpdate);
        TownsLocalize testTownsLocalize = townsLocalizeList.get(townsLocalizeList.size() - 1);
        assertThat(testTownsLocalize.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTownsLocalize() throws Exception {
        int databaseSizeBeforeUpdate = townsLocalizeRepository.findAll().size();

        // Create the TownsLocalize
        TownsLocalizeDTO townsLocalizeDTO = townsLocalizeMapper.toDto(townsLocalize);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTownsLocalizeMockMvc.perform(put("/api/towns-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townsLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TownsLocalize in the database
        List<TownsLocalize> townsLocalizeList = townsLocalizeRepository.findAll();
        assertThat(townsLocalizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTownsLocalize() throws Exception {
        // Initialize the database
        townsLocalizeRepository.saveAndFlush(townsLocalize);

        int databaseSizeBeforeDelete = townsLocalizeRepository.findAll().size();

        // Delete the townsLocalize
        restTownsLocalizeMockMvc.perform(delete("/api/towns-localizes/{id}", townsLocalize.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TownsLocalize> townsLocalizeList = townsLocalizeRepository.findAll();
        assertThat(townsLocalizeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

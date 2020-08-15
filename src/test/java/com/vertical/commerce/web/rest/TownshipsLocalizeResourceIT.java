package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.TownshipsLocalize;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.Townships;
import com.vertical.commerce.repository.TownshipsLocalizeRepository;
import com.vertical.commerce.service.TownshipsLocalizeService;
import com.vertical.commerce.service.dto.TownshipsLocalizeDTO;
import com.vertical.commerce.service.mapper.TownshipsLocalizeMapper;
import com.vertical.commerce.service.dto.TownshipsLocalizeCriteria;
import com.vertical.commerce.service.TownshipsLocalizeQueryService;

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
 * Integration tests for the {@link TownshipsLocalizeResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TownshipsLocalizeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TownshipsLocalizeRepository townshipsLocalizeRepository;

    @Autowired
    private TownshipsLocalizeMapper townshipsLocalizeMapper;

    @Autowired
    private TownshipsLocalizeService townshipsLocalizeService;

    @Autowired
    private TownshipsLocalizeQueryService townshipsLocalizeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTownshipsLocalizeMockMvc;

    private TownshipsLocalize townshipsLocalize;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TownshipsLocalize createEntity(EntityManager em) {
        TownshipsLocalize townshipsLocalize = new TownshipsLocalize()
            .name(DEFAULT_NAME);
        return townshipsLocalize;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TownshipsLocalize createUpdatedEntity(EntityManager em) {
        TownshipsLocalize townshipsLocalize = new TownshipsLocalize()
            .name(UPDATED_NAME);
        return townshipsLocalize;
    }

    @BeforeEach
    public void initTest() {
        townshipsLocalize = createEntity(em);
    }

    @Test
    @Transactional
    public void createTownshipsLocalize() throws Exception {
        int databaseSizeBeforeCreate = townshipsLocalizeRepository.findAll().size();
        // Create the TownshipsLocalize
        TownshipsLocalizeDTO townshipsLocalizeDTO = townshipsLocalizeMapper.toDto(townshipsLocalize);
        restTownshipsLocalizeMockMvc.perform(post("/api/townships-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townshipsLocalizeDTO)))
            .andExpect(status().isCreated());

        // Validate the TownshipsLocalize in the database
        List<TownshipsLocalize> townshipsLocalizeList = townshipsLocalizeRepository.findAll();
        assertThat(townshipsLocalizeList).hasSize(databaseSizeBeforeCreate + 1);
        TownshipsLocalize testTownshipsLocalize = townshipsLocalizeList.get(townshipsLocalizeList.size() - 1);
        assertThat(testTownshipsLocalize.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTownshipsLocalizeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = townshipsLocalizeRepository.findAll().size();

        // Create the TownshipsLocalize with an existing ID
        townshipsLocalize.setId(1L);
        TownshipsLocalizeDTO townshipsLocalizeDTO = townshipsLocalizeMapper.toDto(townshipsLocalize);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTownshipsLocalizeMockMvc.perform(post("/api/townships-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townshipsLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TownshipsLocalize in the database
        List<TownshipsLocalize> townshipsLocalizeList = townshipsLocalizeRepository.findAll();
        assertThat(townshipsLocalizeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = townshipsLocalizeRepository.findAll().size();
        // set the field null
        townshipsLocalize.setName(null);

        // Create the TownshipsLocalize, which fails.
        TownshipsLocalizeDTO townshipsLocalizeDTO = townshipsLocalizeMapper.toDto(townshipsLocalize);


        restTownshipsLocalizeMockMvc.perform(post("/api/townships-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townshipsLocalizeDTO)))
            .andExpect(status().isBadRequest());

        List<TownshipsLocalize> townshipsLocalizeList = townshipsLocalizeRepository.findAll();
        assertThat(townshipsLocalizeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTownshipsLocalizes() throws Exception {
        // Initialize the database
        townshipsLocalizeRepository.saveAndFlush(townshipsLocalize);

        // Get all the townshipsLocalizeList
        restTownshipsLocalizeMockMvc.perform(get("/api/townships-localizes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(townshipsLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getTownshipsLocalize() throws Exception {
        // Initialize the database
        townshipsLocalizeRepository.saveAndFlush(townshipsLocalize);

        // Get the townshipsLocalize
        restTownshipsLocalizeMockMvc.perform(get("/api/townships-localizes/{id}", townshipsLocalize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(townshipsLocalize.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getTownshipsLocalizesByIdFiltering() throws Exception {
        // Initialize the database
        townshipsLocalizeRepository.saveAndFlush(townshipsLocalize);

        Long id = townshipsLocalize.getId();

        defaultTownshipsLocalizeShouldBeFound("id.equals=" + id);
        defaultTownshipsLocalizeShouldNotBeFound("id.notEquals=" + id);

        defaultTownshipsLocalizeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTownshipsLocalizeShouldNotBeFound("id.greaterThan=" + id);

        defaultTownshipsLocalizeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTownshipsLocalizeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTownshipsLocalizesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        townshipsLocalizeRepository.saveAndFlush(townshipsLocalize);

        // Get all the townshipsLocalizeList where name equals to DEFAULT_NAME
        defaultTownshipsLocalizeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the townshipsLocalizeList where name equals to UPDATED_NAME
        defaultTownshipsLocalizeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownshipsLocalizesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        townshipsLocalizeRepository.saveAndFlush(townshipsLocalize);

        // Get all the townshipsLocalizeList where name not equals to DEFAULT_NAME
        defaultTownshipsLocalizeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the townshipsLocalizeList where name not equals to UPDATED_NAME
        defaultTownshipsLocalizeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownshipsLocalizesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        townshipsLocalizeRepository.saveAndFlush(townshipsLocalize);

        // Get all the townshipsLocalizeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTownshipsLocalizeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the townshipsLocalizeList where name equals to UPDATED_NAME
        defaultTownshipsLocalizeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownshipsLocalizesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        townshipsLocalizeRepository.saveAndFlush(townshipsLocalize);

        // Get all the townshipsLocalizeList where name is not null
        defaultTownshipsLocalizeShouldBeFound("name.specified=true");

        // Get all the townshipsLocalizeList where name is null
        defaultTownshipsLocalizeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTownshipsLocalizesByNameContainsSomething() throws Exception {
        // Initialize the database
        townshipsLocalizeRepository.saveAndFlush(townshipsLocalize);

        // Get all the townshipsLocalizeList where name contains DEFAULT_NAME
        defaultTownshipsLocalizeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the townshipsLocalizeList where name contains UPDATED_NAME
        defaultTownshipsLocalizeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownshipsLocalizesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        townshipsLocalizeRepository.saveAndFlush(townshipsLocalize);

        // Get all the townshipsLocalizeList where name does not contain DEFAULT_NAME
        defaultTownshipsLocalizeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the townshipsLocalizeList where name does not contain UPDATED_NAME
        defaultTownshipsLocalizeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTownshipsLocalizesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        townshipsLocalizeRepository.saveAndFlush(townshipsLocalize);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        townshipsLocalize.setCulture(culture);
        townshipsLocalizeRepository.saveAndFlush(townshipsLocalize);
        Long cultureId = culture.getId();

        // Get all the townshipsLocalizeList where culture equals to cultureId
        defaultTownshipsLocalizeShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the townshipsLocalizeList where culture equals to cultureId + 1
        defaultTownshipsLocalizeShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllTownshipsLocalizesByTownshipIsEqualToSomething() throws Exception {
        // Initialize the database
        townshipsLocalizeRepository.saveAndFlush(townshipsLocalize);
        Townships township = TownshipsResourceIT.createEntity(em);
        em.persist(township);
        em.flush();
        townshipsLocalize.setTownship(township);
        townshipsLocalizeRepository.saveAndFlush(townshipsLocalize);
        Long townshipId = township.getId();

        // Get all the townshipsLocalizeList where township equals to townshipId
        defaultTownshipsLocalizeShouldBeFound("townshipId.equals=" + townshipId);

        // Get all the townshipsLocalizeList where township equals to townshipId + 1
        defaultTownshipsLocalizeShouldNotBeFound("townshipId.equals=" + (townshipId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTownshipsLocalizeShouldBeFound(String filter) throws Exception {
        restTownshipsLocalizeMockMvc.perform(get("/api/townships-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(townshipsLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restTownshipsLocalizeMockMvc.perform(get("/api/townships-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTownshipsLocalizeShouldNotBeFound(String filter) throws Exception {
        restTownshipsLocalizeMockMvc.perform(get("/api/townships-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTownshipsLocalizeMockMvc.perform(get("/api/townships-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTownshipsLocalize() throws Exception {
        // Get the townshipsLocalize
        restTownshipsLocalizeMockMvc.perform(get("/api/townships-localizes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTownshipsLocalize() throws Exception {
        // Initialize the database
        townshipsLocalizeRepository.saveAndFlush(townshipsLocalize);

        int databaseSizeBeforeUpdate = townshipsLocalizeRepository.findAll().size();

        // Update the townshipsLocalize
        TownshipsLocalize updatedTownshipsLocalize = townshipsLocalizeRepository.findById(townshipsLocalize.getId()).get();
        // Disconnect from session so that the updates on updatedTownshipsLocalize are not directly saved in db
        em.detach(updatedTownshipsLocalize);
        updatedTownshipsLocalize
            .name(UPDATED_NAME);
        TownshipsLocalizeDTO townshipsLocalizeDTO = townshipsLocalizeMapper.toDto(updatedTownshipsLocalize);

        restTownshipsLocalizeMockMvc.perform(put("/api/townships-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townshipsLocalizeDTO)))
            .andExpect(status().isOk());

        // Validate the TownshipsLocalize in the database
        List<TownshipsLocalize> townshipsLocalizeList = townshipsLocalizeRepository.findAll();
        assertThat(townshipsLocalizeList).hasSize(databaseSizeBeforeUpdate);
        TownshipsLocalize testTownshipsLocalize = townshipsLocalizeList.get(townshipsLocalizeList.size() - 1);
        assertThat(testTownshipsLocalize.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTownshipsLocalize() throws Exception {
        int databaseSizeBeforeUpdate = townshipsLocalizeRepository.findAll().size();

        // Create the TownshipsLocalize
        TownshipsLocalizeDTO townshipsLocalizeDTO = townshipsLocalizeMapper.toDto(townshipsLocalize);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTownshipsLocalizeMockMvc.perform(put("/api/townships-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townshipsLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TownshipsLocalize in the database
        List<TownshipsLocalize> townshipsLocalizeList = townshipsLocalizeRepository.findAll();
        assertThat(townshipsLocalizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTownshipsLocalize() throws Exception {
        // Initialize the database
        townshipsLocalizeRepository.saveAndFlush(townshipsLocalize);

        int databaseSizeBeforeDelete = townshipsLocalizeRepository.findAll().size();

        // Delete the townshipsLocalize
        restTownshipsLocalizeMockMvc.perform(delete("/api/townships-localizes/{id}", townshipsLocalize.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TownshipsLocalize> townshipsLocalizeList = townshipsLocalizeRepository.findAll();
        assertThat(townshipsLocalizeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

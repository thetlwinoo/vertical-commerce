package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.StateProvincesCulture;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.StateProvinces;
import com.vertical.commerce.repository.StateProvincesCultureRepository;
import com.vertical.commerce.service.StateProvincesCultureService;
import com.vertical.commerce.service.dto.StateProvincesCultureDTO;
import com.vertical.commerce.service.mapper.StateProvincesCultureMapper;
import com.vertical.commerce.service.dto.StateProvincesCultureCriteria;
import com.vertical.commerce.service.StateProvincesCultureQueryService;

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
 * Integration tests for the {@link StateProvincesCultureResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class StateProvincesCultureResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private StateProvincesCultureRepository stateProvincesCultureRepository;

    @Autowired
    private StateProvincesCultureMapper stateProvincesCultureMapper;

    @Autowired
    private StateProvincesCultureService stateProvincesCultureService;

    @Autowired
    private StateProvincesCultureQueryService stateProvincesCultureQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStateProvincesCultureMockMvc;

    private StateProvincesCulture stateProvincesCulture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StateProvincesCulture createEntity(EntityManager em) {
        StateProvincesCulture stateProvincesCulture = new StateProvincesCulture()
            .name(DEFAULT_NAME);
        return stateProvincesCulture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StateProvincesCulture createUpdatedEntity(EntityManager em) {
        StateProvincesCulture stateProvincesCulture = new StateProvincesCulture()
            .name(UPDATED_NAME);
        return stateProvincesCulture;
    }

    @BeforeEach
    public void initTest() {
        stateProvincesCulture = createEntity(em);
    }

    @Test
    @Transactional
    public void createStateProvincesCulture() throws Exception {
        int databaseSizeBeforeCreate = stateProvincesCultureRepository.findAll().size();
        // Create the StateProvincesCulture
        StateProvincesCultureDTO stateProvincesCultureDTO = stateProvincesCultureMapper.toDto(stateProvincesCulture);
        restStateProvincesCultureMockMvc.perform(post("/api/state-provinces-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesCultureDTO)))
            .andExpect(status().isCreated());

        // Validate the StateProvincesCulture in the database
        List<StateProvincesCulture> stateProvincesCultureList = stateProvincesCultureRepository.findAll();
        assertThat(stateProvincesCultureList).hasSize(databaseSizeBeforeCreate + 1);
        StateProvincesCulture testStateProvincesCulture = stateProvincesCultureList.get(stateProvincesCultureList.size() - 1);
        assertThat(testStateProvincesCulture.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createStateProvincesCultureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stateProvincesCultureRepository.findAll().size();

        // Create the StateProvincesCulture with an existing ID
        stateProvincesCulture.setId(1L);
        StateProvincesCultureDTO stateProvincesCultureDTO = stateProvincesCultureMapper.toDto(stateProvincesCulture);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStateProvincesCultureMockMvc.perform(post("/api/state-provinces-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StateProvincesCulture in the database
        List<StateProvincesCulture> stateProvincesCultureList = stateProvincesCultureRepository.findAll();
        assertThat(stateProvincesCultureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateProvincesCultureRepository.findAll().size();
        // set the field null
        stateProvincesCulture.setName(null);

        // Create the StateProvincesCulture, which fails.
        StateProvincesCultureDTO stateProvincesCultureDTO = stateProvincesCultureMapper.toDto(stateProvincesCulture);


        restStateProvincesCultureMockMvc.perform(post("/api/state-provinces-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesCultureDTO)))
            .andExpect(status().isBadRequest());

        List<StateProvincesCulture> stateProvincesCultureList = stateProvincesCultureRepository.findAll();
        assertThat(stateProvincesCultureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStateProvincesCultures() throws Exception {
        // Initialize the database
        stateProvincesCultureRepository.saveAndFlush(stateProvincesCulture);

        // Get all the stateProvincesCultureList
        restStateProvincesCultureMockMvc.perform(get("/api/state-provinces-cultures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stateProvincesCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getStateProvincesCulture() throws Exception {
        // Initialize the database
        stateProvincesCultureRepository.saveAndFlush(stateProvincesCulture);

        // Get the stateProvincesCulture
        restStateProvincesCultureMockMvc.perform(get("/api/state-provinces-cultures/{id}", stateProvincesCulture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stateProvincesCulture.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getStateProvincesCulturesByIdFiltering() throws Exception {
        // Initialize the database
        stateProvincesCultureRepository.saveAndFlush(stateProvincesCulture);

        Long id = stateProvincesCulture.getId();

        defaultStateProvincesCultureShouldBeFound("id.equals=" + id);
        defaultStateProvincesCultureShouldNotBeFound("id.notEquals=" + id);

        defaultStateProvincesCultureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStateProvincesCultureShouldNotBeFound("id.greaterThan=" + id);

        defaultStateProvincesCultureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStateProvincesCultureShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStateProvincesCulturesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesCultureRepository.saveAndFlush(stateProvincesCulture);

        // Get all the stateProvincesCultureList where name equals to DEFAULT_NAME
        defaultStateProvincesCultureShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the stateProvincesCultureList where name equals to UPDATED_NAME
        defaultStateProvincesCultureShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStateProvincesCulturesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesCultureRepository.saveAndFlush(stateProvincesCulture);

        // Get all the stateProvincesCultureList where name not equals to DEFAULT_NAME
        defaultStateProvincesCultureShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the stateProvincesCultureList where name not equals to UPDATED_NAME
        defaultStateProvincesCultureShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStateProvincesCulturesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        stateProvincesCultureRepository.saveAndFlush(stateProvincesCulture);

        // Get all the stateProvincesCultureList where name in DEFAULT_NAME or UPDATED_NAME
        defaultStateProvincesCultureShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the stateProvincesCultureList where name equals to UPDATED_NAME
        defaultStateProvincesCultureShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStateProvincesCulturesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stateProvincesCultureRepository.saveAndFlush(stateProvincesCulture);

        // Get all the stateProvincesCultureList where name is not null
        defaultStateProvincesCultureShouldBeFound("name.specified=true");

        // Get all the stateProvincesCultureList where name is null
        defaultStateProvincesCultureShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllStateProvincesCulturesByNameContainsSomething() throws Exception {
        // Initialize the database
        stateProvincesCultureRepository.saveAndFlush(stateProvincesCulture);

        // Get all the stateProvincesCultureList where name contains DEFAULT_NAME
        defaultStateProvincesCultureShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the stateProvincesCultureList where name contains UPDATED_NAME
        defaultStateProvincesCultureShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStateProvincesCulturesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        stateProvincesCultureRepository.saveAndFlush(stateProvincesCulture);

        // Get all the stateProvincesCultureList where name does not contain DEFAULT_NAME
        defaultStateProvincesCultureShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the stateProvincesCultureList where name does not contain UPDATED_NAME
        defaultStateProvincesCultureShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllStateProvincesCulturesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesCultureRepository.saveAndFlush(stateProvincesCulture);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        stateProvincesCulture.setCulture(culture);
        stateProvincesCultureRepository.saveAndFlush(stateProvincesCulture);
        Long cultureId = culture.getId();

        // Get all the stateProvincesCultureList where culture equals to cultureId
        defaultStateProvincesCultureShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the stateProvincesCultureList where culture equals to cultureId + 1
        defaultStateProvincesCultureShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllStateProvincesCulturesByStateProvinceIsEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesCultureRepository.saveAndFlush(stateProvincesCulture);
        StateProvinces stateProvince = StateProvincesResourceIT.createEntity(em);
        em.persist(stateProvince);
        em.flush();
        stateProvincesCulture.setStateProvince(stateProvince);
        stateProvincesCultureRepository.saveAndFlush(stateProvincesCulture);
        Long stateProvinceId = stateProvince.getId();

        // Get all the stateProvincesCultureList where stateProvince equals to stateProvinceId
        defaultStateProvincesCultureShouldBeFound("stateProvinceId.equals=" + stateProvinceId);

        // Get all the stateProvincesCultureList where stateProvince equals to stateProvinceId + 1
        defaultStateProvincesCultureShouldNotBeFound("stateProvinceId.equals=" + (stateProvinceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStateProvincesCultureShouldBeFound(String filter) throws Exception {
        restStateProvincesCultureMockMvc.perform(get("/api/state-provinces-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stateProvincesCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restStateProvincesCultureMockMvc.perform(get("/api/state-provinces-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStateProvincesCultureShouldNotBeFound(String filter) throws Exception {
        restStateProvincesCultureMockMvc.perform(get("/api/state-provinces-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStateProvincesCultureMockMvc.perform(get("/api/state-provinces-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingStateProvincesCulture() throws Exception {
        // Get the stateProvincesCulture
        restStateProvincesCultureMockMvc.perform(get("/api/state-provinces-cultures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStateProvincesCulture() throws Exception {
        // Initialize the database
        stateProvincesCultureRepository.saveAndFlush(stateProvincesCulture);

        int databaseSizeBeforeUpdate = stateProvincesCultureRepository.findAll().size();

        // Update the stateProvincesCulture
        StateProvincesCulture updatedStateProvincesCulture = stateProvincesCultureRepository.findById(stateProvincesCulture.getId()).get();
        // Disconnect from session so that the updates on updatedStateProvincesCulture are not directly saved in db
        em.detach(updatedStateProvincesCulture);
        updatedStateProvincesCulture
            .name(UPDATED_NAME);
        StateProvincesCultureDTO stateProvincesCultureDTO = stateProvincesCultureMapper.toDto(updatedStateProvincesCulture);

        restStateProvincesCultureMockMvc.perform(put("/api/state-provinces-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesCultureDTO)))
            .andExpect(status().isOk());

        // Validate the StateProvincesCulture in the database
        List<StateProvincesCulture> stateProvincesCultureList = stateProvincesCultureRepository.findAll();
        assertThat(stateProvincesCultureList).hasSize(databaseSizeBeforeUpdate);
        StateProvincesCulture testStateProvincesCulture = stateProvincesCultureList.get(stateProvincesCultureList.size() - 1);
        assertThat(testStateProvincesCulture.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingStateProvincesCulture() throws Exception {
        int databaseSizeBeforeUpdate = stateProvincesCultureRepository.findAll().size();

        // Create the StateProvincesCulture
        StateProvincesCultureDTO stateProvincesCultureDTO = stateProvincesCultureMapper.toDto(stateProvincesCulture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStateProvincesCultureMockMvc.perform(put("/api/state-provinces-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StateProvincesCulture in the database
        List<StateProvincesCulture> stateProvincesCultureList = stateProvincesCultureRepository.findAll();
        assertThat(stateProvincesCultureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStateProvincesCulture() throws Exception {
        // Initialize the database
        stateProvincesCultureRepository.saveAndFlush(stateProvincesCulture);

        int databaseSizeBeforeDelete = stateProvincesCultureRepository.findAll().size();

        // Delete the stateProvincesCulture
        restStateProvincesCultureMockMvc.perform(delete("/api/state-provinces-cultures/{id}", stateProvincesCulture.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StateProvincesCulture> stateProvincesCultureList = stateProvincesCultureRepository.findAll();
        assertThat(stateProvincesCultureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

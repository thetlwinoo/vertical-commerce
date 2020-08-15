package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.StateProvincesLocalize;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.StateProvinces;
import com.vertical.commerce.repository.StateProvincesLocalizeRepository;
import com.vertical.commerce.service.StateProvincesLocalizeService;
import com.vertical.commerce.service.dto.StateProvincesLocalizeDTO;
import com.vertical.commerce.service.mapper.StateProvincesLocalizeMapper;
import com.vertical.commerce.service.dto.StateProvincesLocalizeCriteria;
import com.vertical.commerce.service.StateProvincesLocalizeQueryService;

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
 * Integration tests for the {@link StateProvincesLocalizeResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class StateProvincesLocalizeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private StateProvincesLocalizeRepository stateProvincesLocalizeRepository;

    @Autowired
    private StateProvincesLocalizeMapper stateProvincesLocalizeMapper;

    @Autowired
    private StateProvincesLocalizeService stateProvincesLocalizeService;

    @Autowired
    private StateProvincesLocalizeQueryService stateProvincesLocalizeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStateProvincesLocalizeMockMvc;

    private StateProvincesLocalize stateProvincesLocalize;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StateProvincesLocalize createEntity(EntityManager em) {
        StateProvincesLocalize stateProvincesLocalize = new StateProvincesLocalize()
            .name(DEFAULT_NAME);
        return stateProvincesLocalize;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StateProvincesLocalize createUpdatedEntity(EntityManager em) {
        StateProvincesLocalize stateProvincesLocalize = new StateProvincesLocalize()
            .name(UPDATED_NAME);
        return stateProvincesLocalize;
    }

    @BeforeEach
    public void initTest() {
        stateProvincesLocalize = createEntity(em);
    }

    @Test
    @Transactional
    public void createStateProvincesLocalize() throws Exception {
        int databaseSizeBeforeCreate = stateProvincesLocalizeRepository.findAll().size();
        // Create the StateProvincesLocalize
        StateProvincesLocalizeDTO stateProvincesLocalizeDTO = stateProvincesLocalizeMapper.toDto(stateProvincesLocalize);
        restStateProvincesLocalizeMockMvc.perform(post("/api/state-provinces-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesLocalizeDTO)))
            .andExpect(status().isCreated());

        // Validate the StateProvincesLocalize in the database
        List<StateProvincesLocalize> stateProvincesLocalizeList = stateProvincesLocalizeRepository.findAll();
        assertThat(stateProvincesLocalizeList).hasSize(databaseSizeBeforeCreate + 1);
        StateProvincesLocalize testStateProvincesLocalize = stateProvincesLocalizeList.get(stateProvincesLocalizeList.size() - 1);
        assertThat(testStateProvincesLocalize.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createStateProvincesLocalizeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stateProvincesLocalizeRepository.findAll().size();

        // Create the StateProvincesLocalize with an existing ID
        stateProvincesLocalize.setId(1L);
        StateProvincesLocalizeDTO stateProvincesLocalizeDTO = stateProvincesLocalizeMapper.toDto(stateProvincesLocalize);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStateProvincesLocalizeMockMvc.perform(post("/api/state-provinces-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StateProvincesLocalize in the database
        List<StateProvincesLocalize> stateProvincesLocalizeList = stateProvincesLocalizeRepository.findAll();
        assertThat(stateProvincesLocalizeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateProvincesLocalizeRepository.findAll().size();
        // set the field null
        stateProvincesLocalize.setName(null);

        // Create the StateProvincesLocalize, which fails.
        StateProvincesLocalizeDTO stateProvincesLocalizeDTO = stateProvincesLocalizeMapper.toDto(stateProvincesLocalize);


        restStateProvincesLocalizeMockMvc.perform(post("/api/state-provinces-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesLocalizeDTO)))
            .andExpect(status().isBadRequest());

        List<StateProvincesLocalize> stateProvincesLocalizeList = stateProvincesLocalizeRepository.findAll();
        assertThat(stateProvincesLocalizeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStateProvincesLocalizes() throws Exception {
        // Initialize the database
        stateProvincesLocalizeRepository.saveAndFlush(stateProvincesLocalize);

        // Get all the stateProvincesLocalizeList
        restStateProvincesLocalizeMockMvc.perform(get("/api/state-provinces-localizes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stateProvincesLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getStateProvincesLocalize() throws Exception {
        // Initialize the database
        stateProvincesLocalizeRepository.saveAndFlush(stateProvincesLocalize);

        // Get the stateProvincesLocalize
        restStateProvincesLocalizeMockMvc.perform(get("/api/state-provinces-localizes/{id}", stateProvincesLocalize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stateProvincesLocalize.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getStateProvincesLocalizesByIdFiltering() throws Exception {
        // Initialize the database
        stateProvincesLocalizeRepository.saveAndFlush(stateProvincesLocalize);

        Long id = stateProvincesLocalize.getId();

        defaultStateProvincesLocalizeShouldBeFound("id.equals=" + id);
        defaultStateProvincesLocalizeShouldNotBeFound("id.notEquals=" + id);

        defaultStateProvincesLocalizeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStateProvincesLocalizeShouldNotBeFound("id.greaterThan=" + id);

        defaultStateProvincesLocalizeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStateProvincesLocalizeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStateProvincesLocalizesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesLocalizeRepository.saveAndFlush(stateProvincesLocalize);

        // Get all the stateProvincesLocalizeList where name equals to DEFAULT_NAME
        defaultStateProvincesLocalizeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the stateProvincesLocalizeList where name equals to UPDATED_NAME
        defaultStateProvincesLocalizeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStateProvincesLocalizesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesLocalizeRepository.saveAndFlush(stateProvincesLocalize);

        // Get all the stateProvincesLocalizeList where name not equals to DEFAULT_NAME
        defaultStateProvincesLocalizeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the stateProvincesLocalizeList where name not equals to UPDATED_NAME
        defaultStateProvincesLocalizeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStateProvincesLocalizesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        stateProvincesLocalizeRepository.saveAndFlush(stateProvincesLocalize);

        // Get all the stateProvincesLocalizeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultStateProvincesLocalizeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the stateProvincesLocalizeList where name equals to UPDATED_NAME
        defaultStateProvincesLocalizeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStateProvincesLocalizesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stateProvincesLocalizeRepository.saveAndFlush(stateProvincesLocalize);

        // Get all the stateProvincesLocalizeList where name is not null
        defaultStateProvincesLocalizeShouldBeFound("name.specified=true");

        // Get all the stateProvincesLocalizeList where name is null
        defaultStateProvincesLocalizeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllStateProvincesLocalizesByNameContainsSomething() throws Exception {
        // Initialize the database
        stateProvincesLocalizeRepository.saveAndFlush(stateProvincesLocalize);

        // Get all the stateProvincesLocalizeList where name contains DEFAULT_NAME
        defaultStateProvincesLocalizeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the stateProvincesLocalizeList where name contains UPDATED_NAME
        defaultStateProvincesLocalizeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStateProvincesLocalizesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        stateProvincesLocalizeRepository.saveAndFlush(stateProvincesLocalize);

        // Get all the stateProvincesLocalizeList where name does not contain DEFAULT_NAME
        defaultStateProvincesLocalizeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the stateProvincesLocalizeList where name does not contain UPDATED_NAME
        defaultStateProvincesLocalizeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllStateProvincesLocalizesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesLocalizeRepository.saveAndFlush(stateProvincesLocalize);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        stateProvincesLocalize.setCulture(culture);
        stateProvincesLocalizeRepository.saveAndFlush(stateProvincesLocalize);
        Long cultureId = culture.getId();

        // Get all the stateProvincesLocalizeList where culture equals to cultureId
        defaultStateProvincesLocalizeShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the stateProvincesLocalizeList where culture equals to cultureId + 1
        defaultStateProvincesLocalizeShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllStateProvincesLocalizesByStateProvinceIsEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesLocalizeRepository.saveAndFlush(stateProvincesLocalize);
        StateProvinces stateProvince = StateProvincesResourceIT.createEntity(em);
        em.persist(stateProvince);
        em.flush();
        stateProvincesLocalize.setStateProvince(stateProvince);
        stateProvincesLocalizeRepository.saveAndFlush(stateProvincesLocalize);
        Long stateProvinceId = stateProvince.getId();

        // Get all the stateProvincesLocalizeList where stateProvince equals to stateProvinceId
        defaultStateProvincesLocalizeShouldBeFound("stateProvinceId.equals=" + stateProvinceId);

        // Get all the stateProvincesLocalizeList where stateProvince equals to stateProvinceId + 1
        defaultStateProvincesLocalizeShouldNotBeFound("stateProvinceId.equals=" + (stateProvinceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStateProvincesLocalizeShouldBeFound(String filter) throws Exception {
        restStateProvincesLocalizeMockMvc.perform(get("/api/state-provinces-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stateProvincesLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restStateProvincesLocalizeMockMvc.perform(get("/api/state-provinces-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStateProvincesLocalizeShouldNotBeFound(String filter) throws Exception {
        restStateProvincesLocalizeMockMvc.perform(get("/api/state-provinces-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStateProvincesLocalizeMockMvc.perform(get("/api/state-provinces-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingStateProvincesLocalize() throws Exception {
        // Get the stateProvincesLocalize
        restStateProvincesLocalizeMockMvc.perform(get("/api/state-provinces-localizes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStateProvincesLocalize() throws Exception {
        // Initialize the database
        stateProvincesLocalizeRepository.saveAndFlush(stateProvincesLocalize);

        int databaseSizeBeforeUpdate = stateProvincesLocalizeRepository.findAll().size();

        // Update the stateProvincesLocalize
        StateProvincesLocalize updatedStateProvincesLocalize = stateProvincesLocalizeRepository.findById(stateProvincesLocalize.getId()).get();
        // Disconnect from session so that the updates on updatedStateProvincesLocalize are not directly saved in db
        em.detach(updatedStateProvincesLocalize);
        updatedStateProvincesLocalize
            .name(UPDATED_NAME);
        StateProvincesLocalizeDTO stateProvincesLocalizeDTO = stateProvincesLocalizeMapper.toDto(updatedStateProvincesLocalize);

        restStateProvincesLocalizeMockMvc.perform(put("/api/state-provinces-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesLocalizeDTO)))
            .andExpect(status().isOk());

        // Validate the StateProvincesLocalize in the database
        List<StateProvincesLocalize> stateProvincesLocalizeList = stateProvincesLocalizeRepository.findAll();
        assertThat(stateProvincesLocalizeList).hasSize(databaseSizeBeforeUpdate);
        StateProvincesLocalize testStateProvincesLocalize = stateProvincesLocalizeList.get(stateProvincesLocalizeList.size() - 1);
        assertThat(testStateProvincesLocalize.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingStateProvincesLocalize() throws Exception {
        int databaseSizeBeforeUpdate = stateProvincesLocalizeRepository.findAll().size();

        // Create the StateProvincesLocalize
        StateProvincesLocalizeDTO stateProvincesLocalizeDTO = stateProvincesLocalizeMapper.toDto(stateProvincesLocalize);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStateProvincesLocalizeMockMvc.perform(put("/api/state-provinces-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StateProvincesLocalize in the database
        List<StateProvincesLocalize> stateProvincesLocalizeList = stateProvincesLocalizeRepository.findAll();
        assertThat(stateProvincesLocalizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStateProvincesLocalize() throws Exception {
        // Initialize the database
        stateProvincesLocalizeRepository.saveAndFlush(stateProvincesLocalize);

        int databaseSizeBeforeDelete = stateProvincesLocalizeRepository.findAll().size();

        // Delete the stateProvincesLocalize
        restStateProvincesLocalizeMockMvc.perform(delete("/api/state-provinces-localizes/{id}", stateProvincesLocalize.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StateProvincesLocalize> stateProvincesLocalizeList = stateProvincesLocalizeRepository.findAll();
        assertThat(stateProvincesLocalizeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

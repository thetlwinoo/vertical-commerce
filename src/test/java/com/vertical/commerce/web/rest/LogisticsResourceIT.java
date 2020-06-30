package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.Logistics;
import com.vertical.commerce.repository.LogisticsRepository;
import com.vertical.commerce.service.LogisticsService;
import com.vertical.commerce.service.dto.LogisticsDTO;
import com.vertical.commerce.service.mapper.LogisticsMapper;
import com.vertical.commerce.service.dto.LogisticsCriteria;
import com.vertical.commerce.service.LogisticsQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LogisticsResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class LogisticsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE_IND = false;
    private static final Boolean UPDATED_ACTIVE_IND = true;

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private LogisticsRepository logisticsRepository;

    @Autowired
    private LogisticsMapper logisticsMapper;

    @Autowired
    private LogisticsService logisticsService;

    @Autowired
    private LogisticsQueryService logisticsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLogisticsMockMvc;

    private Logistics logistics;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Logistics createEntity(EntityManager em) {
        Logistics logistics = new Logistics()
            .name(DEFAULT_NAME)
            .activeInd(DEFAULT_ACTIVE_IND)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return logistics;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Logistics createUpdatedEntity(EntityManager em) {
        Logistics logistics = new Logistics()
            .name(UPDATED_NAME)
            .activeInd(UPDATED_ACTIVE_IND)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return logistics;
    }

    @BeforeEach
    public void initTest() {
        logistics = createEntity(em);
    }

    @Test
    @Transactional
    public void createLogistics() throws Exception {
        int databaseSizeBeforeCreate = logisticsRepository.findAll().size();
        // Create the Logistics
        LogisticsDTO logisticsDTO = logisticsMapper.toDto(logistics);
        restLogisticsMockMvc.perform(post("/api/logistics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logisticsDTO)))
            .andExpect(status().isCreated());

        // Validate the Logistics in the database
        List<Logistics> logisticsList = logisticsRepository.findAll();
        assertThat(logisticsList).hasSize(databaseSizeBeforeCreate + 1);
        Logistics testLogistics = logisticsList.get(logisticsList.size() - 1);
        assertThat(testLogistics.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLogistics.isActiveInd()).isEqualTo(DEFAULT_ACTIVE_IND);
        assertThat(testLogistics.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testLogistics.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createLogisticsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logisticsRepository.findAll().size();

        // Create the Logistics with an existing ID
        logistics.setId(1L);
        LogisticsDTO logisticsDTO = logisticsMapper.toDto(logistics);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogisticsMockMvc.perform(post("/api/logistics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logisticsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Logistics in the database
        List<Logistics> logisticsList = logisticsRepository.findAll();
        assertThat(logisticsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = logisticsRepository.findAll().size();
        // set the field null
        logistics.setName(null);

        // Create the Logistics, which fails.
        LogisticsDTO logisticsDTO = logisticsMapper.toDto(logistics);


        restLogisticsMockMvc.perform(post("/api/logistics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logisticsDTO)))
            .andExpect(status().isBadRequest());

        List<Logistics> logisticsList = logisticsRepository.findAll();
        assertThat(logisticsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIndIsRequired() throws Exception {
        int databaseSizeBeforeTest = logisticsRepository.findAll().size();
        // set the field null
        logistics.setActiveInd(null);

        // Create the Logistics, which fails.
        LogisticsDTO logisticsDTO = logisticsMapper.toDto(logistics);


        restLogisticsMockMvc.perform(post("/api/logistics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logisticsDTO)))
            .andExpect(status().isBadRequest());

        List<Logistics> logisticsList = logisticsRepository.findAll();
        assertThat(logisticsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = logisticsRepository.findAll().size();
        // set the field null
        logistics.setLastEditedBy(null);

        // Create the Logistics, which fails.
        LogisticsDTO logisticsDTO = logisticsMapper.toDto(logistics);


        restLogisticsMockMvc.perform(post("/api/logistics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logisticsDTO)))
            .andExpect(status().isBadRequest());

        List<Logistics> logisticsList = logisticsRepository.findAll();
        assertThat(logisticsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = logisticsRepository.findAll().size();
        // set the field null
        logistics.setLastEditedWhen(null);

        // Create the Logistics, which fails.
        LogisticsDTO logisticsDTO = logisticsMapper.toDto(logistics);


        restLogisticsMockMvc.perform(post("/api/logistics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logisticsDTO)))
            .andExpect(status().isBadRequest());

        List<Logistics> logisticsList = logisticsRepository.findAll();
        assertThat(logisticsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLogistics() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get all the logisticsList
        restLogisticsMockMvc.perform(get("/api/logistics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logistics.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getLogistics() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get the logistics
        restLogisticsMockMvc.perform(get("/api/logistics/{id}", logistics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(logistics.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.activeInd").value(DEFAULT_ACTIVE_IND.booleanValue()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getLogisticsByIdFiltering() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        Long id = logistics.getId();

        defaultLogisticsShouldBeFound("id.equals=" + id);
        defaultLogisticsShouldNotBeFound("id.notEquals=" + id);

        defaultLogisticsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLogisticsShouldNotBeFound("id.greaterThan=" + id);

        defaultLogisticsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLogisticsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLogisticsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get all the logisticsList where name equals to DEFAULT_NAME
        defaultLogisticsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the logisticsList where name equals to UPDATED_NAME
        defaultLogisticsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLogisticsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get all the logisticsList where name not equals to DEFAULT_NAME
        defaultLogisticsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the logisticsList where name not equals to UPDATED_NAME
        defaultLogisticsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLogisticsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get all the logisticsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultLogisticsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the logisticsList where name equals to UPDATED_NAME
        defaultLogisticsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLogisticsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get all the logisticsList where name is not null
        defaultLogisticsShouldBeFound("name.specified=true");

        // Get all the logisticsList where name is null
        defaultLogisticsShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllLogisticsByNameContainsSomething() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get all the logisticsList where name contains DEFAULT_NAME
        defaultLogisticsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the logisticsList where name contains UPDATED_NAME
        defaultLogisticsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLogisticsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get all the logisticsList where name does not contain DEFAULT_NAME
        defaultLogisticsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the logisticsList where name does not contain UPDATED_NAME
        defaultLogisticsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllLogisticsByActiveIndIsEqualToSomething() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get all the logisticsList where activeInd equals to DEFAULT_ACTIVE_IND
        defaultLogisticsShouldBeFound("activeInd.equals=" + DEFAULT_ACTIVE_IND);

        // Get all the logisticsList where activeInd equals to UPDATED_ACTIVE_IND
        defaultLogisticsShouldNotBeFound("activeInd.equals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllLogisticsByActiveIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get all the logisticsList where activeInd not equals to DEFAULT_ACTIVE_IND
        defaultLogisticsShouldNotBeFound("activeInd.notEquals=" + DEFAULT_ACTIVE_IND);

        // Get all the logisticsList where activeInd not equals to UPDATED_ACTIVE_IND
        defaultLogisticsShouldBeFound("activeInd.notEquals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllLogisticsByActiveIndIsInShouldWork() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get all the logisticsList where activeInd in DEFAULT_ACTIVE_IND or UPDATED_ACTIVE_IND
        defaultLogisticsShouldBeFound("activeInd.in=" + DEFAULT_ACTIVE_IND + "," + UPDATED_ACTIVE_IND);

        // Get all the logisticsList where activeInd equals to UPDATED_ACTIVE_IND
        defaultLogisticsShouldNotBeFound("activeInd.in=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllLogisticsByActiveIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get all the logisticsList where activeInd is not null
        defaultLogisticsShouldBeFound("activeInd.specified=true");

        // Get all the logisticsList where activeInd is null
        defaultLogisticsShouldNotBeFound("activeInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllLogisticsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get all the logisticsList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultLogisticsShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the logisticsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultLogisticsShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllLogisticsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get all the logisticsList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultLogisticsShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the logisticsList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultLogisticsShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllLogisticsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get all the logisticsList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultLogisticsShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the logisticsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultLogisticsShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllLogisticsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get all the logisticsList where lastEditedBy is not null
        defaultLogisticsShouldBeFound("lastEditedBy.specified=true");

        // Get all the logisticsList where lastEditedBy is null
        defaultLogisticsShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllLogisticsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get all the logisticsList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultLogisticsShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the logisticsList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultLogisticsShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllLogisticsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get all the logisticsList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultLogisticsShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the logisticsList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultLogisticsShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllLogisticsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get all the logisticsList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultLogisticsShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the logisticsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultLogisticsShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllLogisticsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get all the logisticsList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultLogisticsShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the logisticsList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultLogisticsShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllLogisticsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get all the logisticsList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultLogisticsShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the logisticsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultLogisticsShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllLogisticsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get all the logisticsList where lastEditedWhen is not null
        defaultLogisticsShouldBeFound("lastEditedWhen.specified=true");

        // Get all the logisticsList where lastEditedWhen is null
        defaultLogisticsShouldNotBeFound("lastEditedWhen.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLogisticsShouldBeFound(String filter) throws Exception {
        restLogisticsMockMvc.perform(get("/api/logistics?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logistics.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restLogisticsMockMvc.perform(get("/api/logistics/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLogisticsShouldNotBeFound(String filter) throws Exception {
        restLogisticsMockMvc.perform(get("/api/logistics?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLogisticsMockMvc.perform(get("/api/logistics/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLogistics() throws Exception {
        // Get the logistics
        restLogisticsMockMvc.perform(get("/api/logistics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogistics() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        int databaseSizeBeforeUpdate = logisticsRepository.findAll().size();

        // Update the logistics
        Logistics updatedLogistics = logisticsRepository.findById(logistics.getId()).get();
        // Disconnect from session so that the updates on updatedLogistics are not directly saved in db
        em.detach(updatedLogistics);
        updatedLogistics
            .name(UPDATED_NAME)
            .activeInd(UPDATED_ACTIVE_IND)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        LogisticsDTO logisticsDTO = logisticsMapper.toDto(updatedLogistics);

        restLogisticsMockMvc.perform(put("/api/logistics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logisticsDTO)))
            .andExpect(status().isOk());

        // Validate the Logistics in the database
        List<Logistics> logisticsList = logisticsRepository.findAll();
        assertThat(logisticsList).hasSize(databaseSizeBeforeUpdate);
        Logistics testLogistics = logisticsList.get(logisticsList.size() - 1);
        assertThat(testLogistics.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLogistics.isActiveInd()).isEqualTo(UPDATED_ACTIVE_IND);
        assertThat(testLogistics.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testLogistics.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingLogistics() throws Exception {
        int databaseSizeBeforeUpdate = logisticsRepository.findAll().size();

        // Create the Logistics
        LogisticsDTO logisticsDTO = logisticsMapper.toDto(logistics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLogisticsMockMvc.perform(put("/api/logistics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logisticsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Logistics in the database
        List<Logistics> logisticsList = logisticsRepository.findAll();
        assertThat(logisticsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLogistics() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        int databaseSizeBeforeDelete = logisticsRepository.findAll().size();

        // Delete the logistics
        restLogisticsMockMvc.perform(delete("/api/logistics/{id}", logistics.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Logistics> logisticsList = logisticsRepository.findAll();
        assertThat(logisticsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

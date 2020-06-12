package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.TrackingEvent;
import com.vertical.commerce.repository.TrackingEventRepository;
import com.vertical.commerce.service.TrackingEventService;
import com.vertical.commerce.service.dto.TrackingEventDTO;
import com.vertical.commerce.service.mapper.TrackingEventMapper;
import com.vertical.commerce.service.dto.TrackingEventCriteria;
import com.vertical.commerce.service.TrackingEventQueryService;

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
 * Integration tests for the {@link TrackingEventResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TrackingEventResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TrackingEventRepository trackingEventRepository;

    @Autowired
    private TrackingEventMapper trackingEventMapper;

    @Autowired
    private TrackingEventService trackingEventService;

    @Autowired
    private TrackingEventQueryService trackingEventQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrackingEventMockMvc;

    private TrackingEvent trackingEvent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrackingEvent createEntity(EntityManager em) {
        TrackingEvent trackingEvent = new TrackingEvent()
            .name(DEFAULT_NAME);
        return trackingEvent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrackingEvent createUpdatedEntity(EntityManager em) {
        TrackingEvent trackingEvent = new TrackingEvent()
            .name(UPDATED_NAME);
        return trackingEvent;
    }

    @BeforeEach
    public void initTest() {
        trackingEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrackingEvent() throws Exception {
        int databaseSizeBeforeCreate = trackingEventRepository.findAll().size();
        // Create the TrackingEvent
        TrackingEventDTO trackingEventDTO = trackingEventMapper.toDto(trackingEvent);
        restTrackingEventMockMvc.perform(post("/api/tracking-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trackingEventDTO)))
            .andExpect(status().isCreated());

        // Validate the TrackingEvent in the database
        List<TrackingEvent> trackingEventList = trackingEventRepository.findAll();
        assertThat(trackingEventList).hasSize(databaseSizeBeforeCreate + 1);
        TrackingEvent testTrackingEvent = trackingEventList.get(trackingEventList.size() - 1);
        assertThat(testTrackingEvent.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTrackingEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trackingEventRepository.findAll().size();

        // Create the TrackingEvent with an existing ID
        trackingEvent.setId(1L);
        TrackingEventDTO trackingEventDTO = trackingEventMapper.toDto(trackingEvent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrackingEventMockMvc.perform(post("/api/tracking-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trackingEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrackingEvent in the database
        List<TrackingEvent> trackingEventList = trackingEventRepository.findAll();
        assertThat(trackingEventList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = trackingEventRepository.findAll().size();
        // set the field null
        trackingEvent.setName(null);

        // Create the TrackingEvent, which fails.
        TrackingEventDTO trackingEventDTO = trackingEventMapper.toDto(trackingEvent);


        restTrackingEventMockMvc.perform(post("/api/tracking-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trackingEventDTO)))
            .andExpect(status().isBadRequest());

        List<TrackingEvent> trackingEventList = trackingEventRepository.findAll();
        assertThat(trackingEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrackingEvents() throws Exception {
        // Initialize the database
        trackingEventRepository.saveAndFlush(trackingEvent);

        // Get all the trackingEventList
        restTrackingEventMockMvc.perform(get("/api/tracking-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trackingEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getTrackingEvent() throws Exception {
        // Initialize the database
        trackingEventRepository.saveAndFlush(trackingEvent);

        // Get the trackingEvent
        restTrackingEventMockMvc.perform(get("/api/tracking-events/{id}", trackingEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trackingEvent.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getTrackingEventsByIdFiltering() throws Exception {
        // Initialize the database
        trackingEventRepository.saveAndFlush(trackingEvent);

        Long id = trackingEvent.getId();

        defaultTrackingEventShouldBeFound("id.equals=" + id);
        defaultTrackingEventShouldNotBeFound("id.notEquals=" + id);

        defaultTrackingEventShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTrackingEventShouldNotBeFound("id.greaterThan=" + id);

        defaultTrackingEventShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTrackingEventShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTrackingEventsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        trackingEventRepository.saveAndFlush(trackingEvent);

        // Get all the trackingEventList where name equals to DEFAULT_NAME
        defaultTrackingEventShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the trackingEventList where name equals to UPDATED_NAME
        defaultTrackingEventShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTrackingEventsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trackingEventRepository.saveAndFlush(trackingEvent);

        // Get all the trackingEventList where name not equals to DEFAULT_NAME
        defaultTrackingEventShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the trackingEventList where name not equals to UPDATED_NAME
        defaultTrackingEventShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTrackingEventsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        trackingEventRepository.saveAndFlush(trackingEvent);

        // Get all the trackingEventList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTrackingEventShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the trackingEventList where name equals to UPDATED_NAME
        defaultTrackingEventShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTrackingEventsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        trackingEventRepository.saveAndFlush(trackingEvent);

        // Get all the trackingEventList where name is not null
        defaultTrackingEventShouldBeFound("name.specified=true");

        // Get all the trackingEventList where name is null
        defaultTrackingEventShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTrackingEventsByNameContainsSomething() throws Exception {
        // Initialize the database
        trackingEventRepository.saveAndFlush(trackingEvent);

        // Get all the trackingEventList where name contains DEFAULT_NAME
        defaultTrackingEventShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the trackingEventList where name contains UPDATED_NAME
        defaultTrackingEventShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTrackingEventsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        trackingEventRepository.saveAndFlush(trackingEvent);

        // Get all the trackingEventList where name does not contain DEFAULT_NAME
        defaultTrackingEventShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the trackingEventList where name does not contain UPDATED_NAME
        defaultTrackingEventShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTrackingEventShouldBeFound(String filter) throws Exception {
        restTrackingEventMockMvc.perform(get("/api/tracking-events?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trackingEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restTrackingEventMockMvc.perform(get("/api/tracking-events/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTrackingEventShouldNotBeFound(String filter) throws Exception {
        restTrackingEventMockMvc.perform(get("/api/tracking-events?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTrackingEventMockMvc.perform(get("/api/tracking-events/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTrackingEvent() throws Exception {
        // Get the trackingEvent
        restTrackingEventMockMvc.perform(get("/api/tracking-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrackingEvent() throws Exception {
        // Initialize the database
        trackingEventRepository.saveAndFlush(trackingEvent);

        int databaseSizeBeforeUpdate = trackingEventRepository.findAll().size();

        // Update the trackingEvent
        TrackingEvent updatedTrackingEvent = trackingEventRepository.findById(trackingEvent.getId()).get();
        // Disconnect from session so that the updates on updatedTrackingEvent are not directly saved in db
        em.detach(updatedTrackingEvent);
        updatedTrackingEvent
            .name(UPDATED_NAME);
        TrackingEventDTO trackingEventDTO = trackingEventMapper.toDto(updatedTrackingEvent);

        restTrackingEventMockMvc.perform(put("/api/tracking-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trackingEventDTO)))
            .andExpect(status().isOk());

        // Validate the TrackingEvent in the database
        List<TrackingEvent> trackingEventList = trackingEventRepository.findAll();
        assertThat(trackingEventList).hasSize(databaseSizeBeforeUpdate);
        TrackingEvent testTrackingEvent = trackingEventList.get(trackingEventList.size() - 1);
        assertThat(testTrackingEvent.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTrackingEvent() throws Exception {
        int databaseSizeBeforeUpdate = trackingEventRepository.findAll().size();

        // Create the TrackingEvent
        TrackingEventDTO trackingEventDTO = trackingEventMapper.toDto(trackingEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrackingEventMockMvc.perform(put("/api/tracking-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trackingEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrackingEvent in the database
        List<TrackingEvent> trackingEventList = trackingEventRepository.findAll();
        assertThat(trackingEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTrackingEvent() throws Exception {
        // Initialize the database
        trackingEventRepository.saveAndFlush(trackingEvent);

        int databaseSizeBeforeDelete = trackingEventRepository.findAll().size();

        // Delete the trackingEvent
        restTrackingEventMockMvc.perform(delete("/api/tracking-events/{id}", trackingEvent.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrackingEvent> trackingEventList = trackingEventRepository.findAll();
        assertThat(trackingEventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

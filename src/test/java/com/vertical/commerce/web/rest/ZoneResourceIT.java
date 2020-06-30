package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.Zone;
import com.vertical.commerce.domain.Cities;
import com.vertical.commerce.repository.ZoneRepository;
import com.vertical.commerce.service.ZoneService;
import com.vertical.commerce.service.dto.ZoneDTO;
import com.vertical.commerce.service.mapper.ZoneMapper;
import com.vertical.commerce.service.dto.ZoneCriteria;
import com.vertical.commerce.service.ZoneQueryService;

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
 * Integration tests for the {@link ZoneResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ZoneResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private ZoneMapper zoneMapper;

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private ZoneQueryService zoneQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restZoneMockMvc;

    private Zone zone;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zone createEntity(EntityManager em) {
        Zone zone = new Zone()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return zone;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zone createUpdatedEntity(EntityManager em) {
        Zone zone = new Zone()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return zone;
    }

    @BeforeEach
    public void initTest() {
        zone = createEntity(em);
    }

    @Test
    @Transactional
    public void createZone() throws Exception {
        int databaseSizeBeforeCreate = zoneRepository.findAll().size();
        // Create the Zone
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);
        restZoneMockMvc.perform(post("/api/zones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(zoneDTO)))
            .andExpect(status().isCreated());

        // Validate the Zone in the database
        List<Zone> zoneList = zoneRepository.findAll();
        assertThat(zoneList).hasSize(databaseSizeBeforeCreate + 1);
        Zone testZone = zoneList.get(zoneList.size() - 1);
        assertThat(testZone.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testZone.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testZone.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testZone.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createZoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zoneRepository.findAll().size();

        // Create the Zone with an existing ID
        zone.setId(1L);
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restZoneMockMvc.perform(post("/api/zones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(zoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Zone in the database
        List<Zone> zoneList = zoneRepository.findAll();
        assertThat(zoneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = zoneRepository.findAll().size();
        // set the field null
        zone.setCode(null);

        // Create the Zone, which fails.
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);


        restZoneMockMvc.perform(post("/api/zones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(zoneDTO)))
            .andExpect(status().isBadRequest());

        List<Zone> zoneList = zoneRepository.findAll();
        assertThat(zoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = zoneRepository.findAll().size();
        // set the field null
        zone.setName(null);

        // Create the Zone, which fails.
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);


        restZoneMockMvc.perform(post("/api/zones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(zoneDTO)))
            .andExpect(status().isBadRequest());

        List<Zone> zoneList = zoneRepository.findAll();
        assertThat(zoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = zoneRepository.findAll().size();
        // set the field null
        zone.setValidFrom(null);

        // Create the Zone, which fails.
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);


        restZoneMockMvc.perform(post("/api/zones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(zoneDTO)))
            .andExpect(status().isBadRequest());

        List<Zone> zoneList = zoneRepository.findAll();
        assertThat(zoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = zoneRepository.findAll().size();
        // set the field null
        zone.setValidTo(null);

        // Create the Zone, which fails.
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);


        restZoneMockMvc.perform(post("/api/zones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(zoneDTO)))
            .andExpect(status().isBadRequest());

        List<Zone> zoneList = zoneRepository.findAll();
        assertThat(zoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllZones() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList
        restZoneMockMvc.perform(get("/api/zones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zone.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getZone() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get the zone
        restZoneMockMvc.perform(get("/api/zones/{id}", zone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(zone.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getZonesByIdFiltering() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        Long id = zone.getId();

        defaultZoneShouldBeFound("id.equals=" + id);
        defaultZoneShouldNotBeFound("id.notEquals=" + id);

        defaultZoneShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultZoneShouldNotBeFound("id.greaterThan=" + id);

        defaultZoneShouldBeFound("id.lessThanOrEqual=" + id);
        defaultZoneShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllZonesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where code equals to DEFAULT_CODE
        defaultZoneShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the zoneList where code equals to UPDATED_CODE
        defaultZoneShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllZonesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where code not equals to DEFAULT_CODE
        defaultZoneShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the zoneList where code not equals to UPDATED_CODE
        defaultZoneShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllZonesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where code in DEFAULT_CODE or UPDATED_CODE
        defaultZoneShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the zoneList where code equals to UPDATED_CODE
        defaultZoneShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllZonesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where code is not null
        defaultZoneShouldBeFound("code.specified=true");

        // Get all the zoneList where code is null
        defaultZoneShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllZonesByCodeContainsSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where code contains DEFAULT_CODE
        defaultZoneShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the zoneList where code contains UPDATED_CODE
        defaultZoneShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllZonesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where code does not contain DEFAULT_CODE
        defaultZoneShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the zoneList where code does not contain UPDATED_CODE
        defaultZoneShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllZonesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where name equals to DEFAULT_NAME
        defaultZoneShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the zoneList where name equals to UPDATED_NAME
        defaultZoneShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllZonesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where name not equals to DEFAULT_NAME
        defaultZoneShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the zoneList where name not equals to UPDATED_NAME
        defaultZoneShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllZonesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where name in DEFAULT_NAME or UPDATED_NAME
        defaultZoneShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the zoneList where name equals to UPDATED_NAME
        defaultZoneShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllZonesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where name is not null
        defaultZoneShouldBeFound("name.specified=true");

        // Get all the zoneList where name is null
        defaultZoneShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllZonesByNameContainsSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where name contains DEFAULT_NAME
        defaultZoneShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the zoneList where name contains UPDATED_NAME
        defaultZoneShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllZonesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where name does not contain DEFAULT_NAME
        defaultZoneShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the zoneList where name does not contain UPDATED_NAME
        defaultZoneShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllZonesByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where validFrom equals to DEFAULT_VALID_FROM
        defaultZoneShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the zoneList where validFrom equals to UPDATED_VALID_FROM
        defaultZoneShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllZonesByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where validFrom not equals to DEFAULT_VALID_FROM
        defaultZoneShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the zoneList where validFrom not equals to UPDATED_VALID_FROM
        defaultZoneShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllZonesByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultZoneShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the zoneList where validFrom equals to UPDATED_VALID_FROM
        defaultZoneShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllZonesByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where validFrom is not null
        defaultZoneShouldBeFound("validFrom.specified=true");

        // Get all the zoneList where validFrom is null
        defaultZoneShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllZonesByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where validTo equals to DEFAULT_VALID_TO
        defaultZoneShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the zoneList where validTo equals to UPDATED_VALID_TO
        defaultZoneShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllZonesByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where validTo not equals to DEFAULT_VALID_TO
        defaultZoneShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the zoneList where validTo not equals to UPDATED_VALID_TO
        defaultZoneShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllZonesByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultZoneShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the zoneList where validTo equals to UPDATED_VALID_TO
        defaultZoneShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllZonesByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        // Get all the zoneList where validTo is not null
        defaultZoneShouldBeFound("validTo.specified=true");

        // Get all the zoneList where validTo is null
        defaultZoneShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllZonesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);
        Cities city = CitiesResourceIT.createEntity(em);
        em.persist(city);
        em.flush();
        zone.setCity(city);
        zoneRepository.saveAndFlush(zone);
        Long cityId = city.getId();

        // Get all the zoneList where city equals to cityId
        defaultZoneShouldBeFound("cityId.equals=" + cityId);

        // Get all the zoneList where city equals to cityId + 1
        defaultZoneShouldNotBeFound("cityId.equals=" + (cityId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultZoneShouldBeFound(String filter) throws Exception {
        restZoneMockMvc.perform(get("/api/zones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zone.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restZoneMockMvc.perform(get("/api/zones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultZoneShouldNotBeFound(String filter) throws Exception {
        restZoneMockMvc.perform(get("/api/zones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restZoneMockMvc.perform(get("/api/zones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingZone() throws Exception {
        // Get the zone
        restZoneMockMvc.perform(get("/api/zones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZone() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        int databaseSizeBeforeUpdate = zoneRepository.findAll().size();

        // Update the zone
        Zone updatedZone = zoneRepository.findById(zone.getId()).get();
        // Disconnect from session so that the updates on updatedZone are not directly saved in db
        em.detach(updatedZone);
        updatedZone
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        ZoneDTO zoneDTO = zoneMapper.toDto(updatedZone);

        restZoneMockMvc.perform(put("/api/zones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(zoneDTO)))
            .andExpect(status().isOk());

        // Validate the Zone in the database
        List<Zone> zoneList = zoneRepository.findAll();
        assertThat(zoneList).hasSize(databaseSizeBeforeUpdate);
        Zone testZone = zoneList.get(zoneList.size() - 1);
        assertThat(testZone.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testZone.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testZone.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testZone.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingZone() throws Exception {
        int databaseSizeBeforeUpdate = zoneRepository.findAll().size();

        // Create the Zone
        ZoneDTO zoneDTO = zoneMapper.toDto(zone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZoneMockMvc.perform(put("/api/zones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(zoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Zone in the database
        List<Zone> zoneList = zoneRepository.findAll();
        assertThat(zoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteZone() throws Exception {
        // Initialize the database
        zoneRepository.saveAndFlush(zone);

        int databaseSizeBeforeDelete = zoneRepository.findAll().size();

        // Delete the zone
        restZoneMockMvc.perform(delete("/api/zones/{id}", zone.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Zone> zoneList = zoneRepository.findAll();
        assertThat(zoneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

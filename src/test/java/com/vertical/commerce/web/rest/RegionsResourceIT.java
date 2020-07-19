package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.Regions;
import com.vertical.commerce.domain.StateProvinces;
import com.vertical.commerce.repository.RegionsRepository;
import com.vertical.commerce.service.RegionsService;
import com.vertical.commerce.service.dto.RegionsDTO;
import com.vertical.commerce.service.mapper.RegionsMapper;
import com.vertical.commerce.service.dto.RegionsCriteria;
import com.vertical.commerce.service.RegionsQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@link RegionsResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class RegionsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CULTURE_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_CULTURE_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private RegionsRepository regionsRepository;

    @Autowired
    private RegionsMapper regionsMapper;

    @Autowired
    private RegionsService regionsService;

    @Autowired
    private RegionsQueryService regionsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegionsMockMvc;

    private Regions regions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Regions createEntity(EntityManager em) {
        Regions regions = new Regions()
            .name(DEFAULT_NAME)
            .cultureDetails(DEFAULT_CULTURE_DETAILS)
            .description(DEFAULT_DESCRIPTION)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return regions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Regions createUpdatedEntity(EntityManager em) {
        Regions regions = new Regions()
            .name(UPDATED_NAME)
            .cultureDetails(UPDATED_CULTURE_DETAILS)
            .description(UPDATED_DESCRIPTION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return regions;
    }

    @BeforeEach
    public void initTest() {
        regions = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegions() throws Exception {
        int databaseSizeBeforeCreate = regionsRepository.findAll().size();
        // Create the Regions
        RegionsDTO regionsDTO = regionsMapper.toDto(regions);
        restRegionsMockMvc.perform(post("/api/regions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regionsDTO)))
            .andExpect(status().isCreated());

        // Validate the Regions in the database
        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeCreate + 1);
        Regions testRegions = regionsList.get(regionsList.size() - 1);
        assertThat(testRegions.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRegions.getCultureDetails()).isEqualTo(DEFAULT_CULTURE_DETAILS);
        assertThat(testRegions.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRegions.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testRegions.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createRegionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regionsRepository.findAll().size();

        // Create the Regions with an existing ID
        regions.setId(1L);
        RegionsDTO regionsDTO = regionsMapper.toDto(regions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegionsMockMvc.perform(post("/api/regions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Regions in the database
        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = regionsRepository.findAll().size();
        // set the field null
        regions.setName(null);

        // Create the Regions, which fails.
        RegionsDTO regionsDTO = regionsMapper.toDto(regions);


        restRegionsMockMvc.perform(post("/api/regions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regionsDTO)))
            .andExpect(status().isBadRequest());

        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = regionsRepository.findAll().size();
        // set the field null
        regions.setValidFrom(null);

        // Create the Regions, which fails.
        RegionsDTO regionsDTO = regionsMapper.toDto(regions);


        restRegionsMockMvc.perform(post("/api/regions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regionsDTO)))
            .andExpect(status().isBadRequest());

        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegions() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList
        restRegionsMockMvc.perform(get("/api/regions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].cultureDetails").value(hasItem(DEFAULT_CULTURE_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getRegions() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get the regions
        restRegionsMockMvc.perform(get("/api/regions/{id}", regions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(regions.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.cultureDetails").value(DEFAULT_CULTURE_DETAILS.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getRegionsByIdFiltering() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        Long id = regions.getId();

        defaultRegionsShouldBeFound("id.equals=" + id);
        defaultRegionsShouldNotBeFound("id.notEquals=" + id);

        defaultRegionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRegionsShouldNotBeFound("id.greaterThan=" + id);

        defaultRegionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRegionsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRegionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where name equals to DEFAULT_NAME
        defaultRegionsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the regionsList where name equals to UPDATED_NAME
        defaultRegionsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegionsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where name not equals to DEFAULT_NAME
        defaultRegionsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the regionsList where name not equals to UPDATED_NAME
        defaultRegionsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRegionsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the regionsList where name equals to UPDATED_NAME
        defaultRegionsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where name is not null
        defaultRegionsShouldBeFound("name.specified=true");

        // Get all the regionsList where name is null
        defaultRegionsShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllRegionsByNameContainsSomething() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where name contains DEFAULT_NAME
        defaultRegionsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the regionsList where name contains UPDATED_NAME
        defaultRegionsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where name does not contain DEFAULT_NAME
        defaultRegionsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the regionsList where name does not contain UPDATED_NAME
        defaultRegionsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllRegionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where description equals to DEFAULT_DESCRIPTION
        defaultRegionsShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the regionsList where description equals to UPDATED_DESCRIPTION
        defaultRegionsShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllRegionsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where description not equals to DEFAULT_DESCRIPTION
        defaultRegionsShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the regionsList where description not equals to UPDATED_DESCRIPTION
        defaultRegionsShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllRegionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultRegionsShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the regionsList where description equals to UPDATED_DESCRIPTION
        defaultRegionsShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllRegionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where description is not null
        defaultRegionsShouldBeFound("description.specified=true");

        // Get all the regionsList where description is null
        defaultRegionsShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllRegionsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where description contains DEFAULT_DESCRIPTION
        defaultRegionsShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the regionsList where description contains UPDATED_DESCRIPTION
        defaultRegionsShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllRegionsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where description does not contain DEFAULT_DESCRIPTION
        defaultRegionsShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the regionsList where description does not contain UPDATED_DESCRIPTION
        defaultRegionsShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllRegionsByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where validFrom equals to DEFAULT_VALID_FROM
        defaultRegionsShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the regionsList where validFrom equals to UPDATED_VALID_FROM
        defaultRegionsShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllRegionsByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where validFrom not equals to DEFAULT_VALID_FROM
        defaultRegionsShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the regionsList where validFrom not equals to UPDATED_VALID_FROM
        defaultRegionsShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllRegionsByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultRegionsShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the regionsList where validFrom equals to UPDATED_VALID_FROM
        defaultRegionsShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllRegionsByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where validFrom is not null
        defaultRegionsShouldBeFound("validFrom.specified=true");

        // Get all the regionsList where validFrom is null
        defaultRegionsShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegionsByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where validTo equals to DEFAULT_VALID_TO
        defaultRegionsShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the regionsList where validTo equals to UPDATED_VALID_TO
        defaultRegionsShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllRegionsByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where validTo not equals to DEFAULT_VALID_TO
        defaultRegionsShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the regionsList where validTo not equals to UPDATED_VALID_TO
        defaultRegionsShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllRegionsByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultRegionsShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the regionsList where validTo equals to UPDATED_VALID_TO
        defaultRegionsShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllRegionsByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where validTo is not null
        defaultRegionsShouldBeFound("validTo.specified=true");

        // Get all the regionsList where validTo is null
        defaultRegionsShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegionsByStateProvinceIsEqualToSomething() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);
        StateProvinces stateProvince = StateProvincesResourceIT.createEntity(em);
        em.persist(stateProvince);
        em.flush();
        regions.setStateProvince(stateProvince);
        regionsRepository.saveAndFlush(regions);
        Long stateProvinceId = stateProvince.getId();

        // Get all the regionsList where stateProvince equals to stateProvinceId
        defaultRegionsShouldBeFound("stateProvinceId.equals=" + stateProvinceId);

        // Get all the regionsList where stateProvince equals to stateProvinceId + 1
        defaultRegionsShouldNotBeFound("stateProvinceId.equals=" + (stateProvinceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRegionsShouldBeFound(String filter) throws Exception {
        restRegionsMockMvc.perform(get("/api/regions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].cultureDetails").value(hasItem(DEFAULT_CULTURE_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restRegionsMockMvc.perform(get("/api/regions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRegionsShouldNotBeFound(String filter) throws Exception {
        restRegionsMockMvc.perform(get("/api/regions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRegionsMockMvc.perform(get("/api/regions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRegions() throws Exception {
        // Get the regions
        restRegionsMockMvc.perform(get("/api/regions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegions() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        int databaseSizeBeforeUpdate = regionsRepository.findAll().size();

        // Update the regions
        Regions updatedRegions = regionsRepository.findById(regions.getId()).get();
        // Disconnect from session so that the updates on updatedRegions are not directly saved in db
        em.detach(updatedRegions);
        updatedRegions
            .name(UPDATED_NAME)
            .cultureDetails(UPDATED_CULTURE_DETAILS)
            .description(UPDATED_DESCRIPTION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        RegionsDTO regionsDTO = regionsMapper.toDto(updatedRegions);

        restRegionsMockMvc.perform(put("/api/regions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regionsDTO)))
            .andExpect(status().isOk());

        // Validate the Regions in the database
        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeUpdate);
        Regions testRegions = regionsList.get(regionsList.size() - 1);
        assertThat(testRegions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRegions.getCultureDetails()).isEqualTo(UPDATED_CULTURE_DETAILS);
        assertThat(testRegions.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRegions.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testRegions.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingRegions() throws Exception {
        int databaseSizeBeforeUpdate = regionsRepository.findAll().size();

        // Create the Regions
        RegionsDTO regionsDTO = regionsMapper.toDto(regions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegionsMockMvc.perform(put("/api/regions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Regions in the database
        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegions() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        int databaseSizeBeforeDelete = regionsRepository.findAll().size();

        // Delete the regions
        restRegionsMockMvc.perform(delete("/api/regions/{id}", regions.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

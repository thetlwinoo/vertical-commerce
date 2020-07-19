package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.Districts;
import com.vertical.commerce.repository.DistrictsRepository;
import com.vertical.commerce.service.DistrictsService;
import com.vertical.commerce.service.dto.DistrictsDTO;
import com.vertical.commerce.service.mapper.DistrictsMapper;
import com.vertical.commerce.service.dto.DistrictsCriteria;
import com.vertical.commerce.service.DistrictsQueryService;

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
 * Integration tests for the {@link DistrictsResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class DistrictsResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CULTURE_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_CULTURE_DETAILS = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DistrictsRepository districtsRepository;

    @Autowired
    private DistrictsMapper districtsMapper;

    @Autowired
    private DistrictsService districtsService;

    @Autowired
    private DistrictsQueryService districtsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDistrictsMockMvc;

    private Districts districts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Districts createEntity(EntityManager em) {
        Districts districts = new Districts()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .cultureDetails(DEFAULT_CULTURE_DETAILS)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return districts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Districts createUpdatedEntity(EntityManager em) {
        Districts districts = new Districts()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .cultureDetails(UPDATED_CULTURE_DETAILS)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return districts;
    }

    @BeforeEach
    public void initTest() {
        districts = createEntity(em);
    }

    @Test
    @Transactional
    public void createDistricts() throws Exception {
        int databaseSizeBeforeCreate = districtsRepository.findAll().size();
        // Create the Districts
        DistrictsDTO districtsDTO = districtsMapper.toDto(districts);
        restDistrictsMockMvc.perform(post("/api/districts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(districtsDTO)))
            .andExpect(status().isCreated());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeCreate + 1);
        Districts testDistricts = districtsList.get(districtsList.size() - 1);
        assertThat(testDistricts.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDistricts.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDistricts.getCultureDetails()).isEqualTo(DEFAULT_CULTURE_DETAILS);
        assertThat(testDistricts.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testDistricts.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createDistrictsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = districtsRepository.findAll().size();

        // Create the Districts with an existing ID
        districts.setId(1L);
        DistrictsDTO districtsDTO = districtsMapper.toDto(districts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistrictsMockMvc.perform(post("/api/districts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(districtsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = districtsRepository.findAll().size();
        // set the field null
        districts.setName(null);

        // Create the Districts, which fails.
        DistrictsDTO districtsDTO = districtsMapper.toDto(districts);


        restDistrictsMockMvc.perform(post("/api/districts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(districtsDTO)))
            .andExpect(status().isBadRequest());

        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = districtsRepository.findAll().size();
        // set the field null
        districts.setValidFrom(null);

        // Create the Districts, which fails.
        DistrictsDTO districtsDTO = districtsMapper.toDto(districts);


        restDistrictsMockMvc.perform(post("/api/districts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(districtsDTO)))
            .andExpect(status().isBadRequest());

        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDistricts() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList
        restDistrictsMockMvc.perform(get("/api/districts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(districts.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].cultureDetails").value(hasItem(DEFAULT_CULTURE_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getDistricts() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get the districts
        restDistrictsMockMvc.perform(get("/api/districts/{id}", districts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(districts.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.cultureDetails").value(DEFAULT_CULTURE_DETAILS.toString()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getDistrictsByIdFiltering() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        Long id = districts.getId();

        defaultDistrictsShouldBeFound("id.equals=" + id);
        defaultDistrictsShouldNotBeFound("id.notEquals=" + id);

        defaultDistrictsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDistrictsShouldNotBeFound("id.greaterThan=" + id);

        defaultDistrictsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDistrictsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDistrictsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where code equals to DEFAULT_CODE
        defaultDistrictsShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the districtsList where code equals to UPDATED_CODE
        defaultDistrictsShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllDistrictsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where code not equals to DEFAULT_CODE
        defaultDistrictsShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the districtsList where code not equals to UPDATED_CODE
        defaultDistrictsShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllDistrictsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where code in DEFAULT_CODE or UPDATED_CODE
        defaultDistrictsShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the districtsList where code equals to UPDATED_CODE
        defaultDistrictsShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllDistrictsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where code is not null
        defaultDistrictsShouldBeFound("code.specified=true");

        // Get all the districtsList where code is null
        defaultDistrictsShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllDistrictsByCodeContainsSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where code contains DEFAULT_CODE
        defaultDistrictsShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the districtsList where code contains UPDATED_CODE
        defaultDistrictsShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllDistrictsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where code does not contain DEFAULT_CODE
        defaultDistrictsShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the districtsList where code does not contain UPDATED_CODE
        defaultDistrictsShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllDistrictsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where name equals to DEFAULT_NAME
        defaultDistrictsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the districtsList where name equals to UPDATED_NAME
        defaultDistrictsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where name not equals to DEFAULT_NAME
        defaultDistrictsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the districtsList where name not equals to UPDATED_NAME
        defaultDistrictsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDistrictsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the districtsList where name equals to UPDATED_NAME
        defaultDistrictsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where name is not null
        defaultDistrictsShouldBeFound("name.specified=true");

        // Get all the districtsList where name is null
        defaultDistrictsShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDistrictsByNameContainsSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where name contains DEFAULT_NAME
        defaultDistrictsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the districtsList where name contains UPDATED_NAME
        defaultDistrictsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where name does not contain DEFAULT_NAME
        defaultDistrictsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the districtsList where name does not contain UPDATED_NAME
        defaultDistrictsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDistrictsByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where validFrom equals to DEFAULT_VALID_FROM
        defaultDistrictsShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the districtsList where validFrom equals to UPDATED_VALID_FROM
        defaultDistrictsShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllDistrictsByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where validFrom not equals to DEFAULT_VALID_FROM
        defaultDistrictsShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the districtsList where validFrom not equals to UPDATED_VALID_FROM
        defaultDistrictsShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllDistrictsByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultDistrictsShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the districtsList where validFrom equals to UPDATED_VALID_FROM
        defaultDistrictsShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllDistrictsByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where validFrom is not null
        defaultDistrictsShouldBeFound("validFrom.specified=true");

        // Get all the districtsList where validFrom is null
        defaultDistrictsShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistrictsByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where validTo equals to DEFAULT_VALID_TO
        defaultDistrictsShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the districtsList where validTo equals to UPDATED_VALID_TO
        defaultDistrictsShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllDistrictsByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where validTo not equals to DEFAULT_VALID_TO
        defaultDistrictsShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the districtsList where validTo not equals to UPDATED_VALID_TO
        defaultDistrictsShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllDistrictsByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultDistrictsShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the districtsList where validTo equals to UPDATED_VALID_TO
        defaultDistrictsShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllDistrictsByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where validTo is not null
        defaultDistrictsShouldBeFound("validTo.specified=true");

        // Get all the districtsList where validTo is null
        defaultDistrictsShouldNotBeFound("validTo.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDistrictsShouldBeFound(String filter) throws Exception {
        restDistrictsMockMvc.perform(get("/api/districts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(districts.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].cultureDetails").value(hasItem(DEFAULT_CULTURE_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restDistrictsMockMvc.perform(get("/api/districts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDistrictsShouldNotBeFound(String filter) throws Exception {
        restDistrictsMockMvc.perform(get("/api/districts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDistrictsMockMvc.perform(get("/api/districts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDistricts() throws Exception {
        // Get the districts
        restDistrictsMockMvc.perform(get("/api/districts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDistricts() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        int databaseSizeBeforeUpdate = districtsRepository.findAll().size();

        // Update the districts
        Districts updatedDistricts = districtsRepository.findById(districts.getId()).get();
        // Disconnect from session so that the updates on updatedDistricts are not directly saved in db
        em.detach(updatedDistricts);
        updatedDistricts
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .cultureDetails(UPDATED_CULTURE_DETAILS)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        DistrictsDTO districtsDTO = districtsMapper.toDto(updatedDistricts);

        restDistrictsMockMvc.perform(put("/api/districts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(districtsDTO)))
            .andExpect(status().isOk());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeUpdate);
        Districts testDistricts = districtsList.get(districtsList.size() - 1);
        assertThat(testDistricts.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDistricts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDistricts.getCultureDetails()).isEqualTo(UPDATED_CULTURE_DETAILS);
        assertThat(testDistricts.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testDistricts.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingDistricts() throws Exception {
        int databaseSizeBeforeUpdate = districtsRepository.findAll().size();

        // Create the Districts
        DistrictsDTO districtsDTO = districtsMapper.toDto(districts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictsMockMvc.perform(put("/api/districts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(districtsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDistricts() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        int databaseSizeBeforeDelete = districtsRepository.findAll().size();

        // Delete the districts
        restDistrictsMockMvc.perform(delete("/api/districts/{id}", districts.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

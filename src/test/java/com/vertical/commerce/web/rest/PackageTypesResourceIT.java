package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.PackageTypes;
import com.vertical.commerce.repository.PackageTypesRepository;
import com.vertical.commerce.service.PackageTypesService;
import com.vertical.commerce.service.dto.PackageTypesDTO;
import com.vertical.commerce.service.mapper.PackageTypesMapper;
import com.vertical.commerce.service.dto.PackageTypesCriteria;
import com.vertical.commerce.service.PackageTypesQueryService;

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
 * Integration tests for the {@link PackageTypesResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PackageTypesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PackageTypesRepository packageTypesRepository;

    @Autowired
    private PackageTypesMapper packageTypesMapper;

    @Autowired
    private PackageTypesService packageTypesService;

    @Autowired
    private PackageTypesQueryService packageTypesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPackageTypesMockMvc;

    private PackageTypes packageTypes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PackageTypes createEntity(EntityManager em) {
        PackageTypes packageTypes = new PackageTypes()
            .name(DEFAULT_NAME)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return packageTypes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PackageTypes createUpdatedEntity(EntityManager em) {
        PackageTypes packageTypes = new PackageTypes()
            .name(UPDATED_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return packageTypes;
    }

    @BeforeEach
    public void initTest() {
        packageTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createPackageTypes() throws Exception {
        int databaseSizeBeforeCreate = packageTypesRepository.findAll().size();
        // Create the PackageTypes
        PackageTypesDTO packageTypesDTO = packageTypesMapper.toDto(packageTypes);
        restPackageTypesMockMvc.perform(post("/api/package-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(packageTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the PackageTypes in the database
        List<PackageTypes> packageTypesList = packageTypesRepository.findAll();
        assertThat(packageTypesList).hasSize(databaseSizeBeforeCreate + 1);
        PackageTypes testPackageTypes = packageTypesList.get(packageTypesList.size() - 1);
        assertThat(testPackageTypes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPackageTypes.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testPackageTypes.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createPackageTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = packageTypesRepository.findAll().size();

        // Create the PackageTypes with an existing ID
        packageTypes.setId(1L);
        PackageTypesDTO packageTypesDTO = packageTypesMapper.toDto(packageTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPackageTypesMockMvc.perform(post("/api/package-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(packageTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PackageTypes in the database
        List<PackageTypes> packageTypesList = packageTypesRepository.findAll();
        assertThat(packageTypesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = packageTypesRepository.findAll().size();
        // set the field null
        packageTypes.setName(null);

        // Create the PackageTypes, which fails.
        PackageTypesDTO packageTypesDTO = packageTypesMapper.toDto(packageTypes);


        restPackageTypesMockMvc.perform(post("/api/package-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(packageTypesDTO)))
            .andExpect(status().isBadRequest());

        List<PackageTypes> packageTypesList = packageTypesRepository.findAll();
        assertThat(packageTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = packageTypesRepository.findAll().size();
        // set the field null
        packageTypes.setValidFrom(null);

        // Create the PackageTypes, which fails.
        PackageTypesDTO packageTypesDTO = packageTypesMapper.toDto(packageTypes);


        restPackageTypesMockMvc.perform(post("/api/package-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(packageTypesDTO)))
            .andExpect(status().isBadRequest());

        List<PackageTypes> packageTypesList = packageTypesRepository.findAll();
        assertThat(packageTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = packageTypesRepository.findAll().size();
        // set the field null
        packageTypes.setValidTo(null);

        // Create the PackageTypes, which fails.
        PackageTypesDTO packageTypesDTO = packageTypesMapper.toDto(packageTypes);


        restPackageTypesMockMvc.perform(post("/api/package-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(packageTypesDTO)))
            .andExpect(status().isBadRequest());

        List<PackageTypes> packageTypesList = packageTypesRepository.findAll();
        assertThat(packageTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPackageTypes() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList
        restPackageTypesMockMvc.perform(get("/api/package-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(packageTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getPackageTypes() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get the packageTypes
        restPackageTypesMockMvc.perform(get("/api/package-types/{id}", packageTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(packageTypes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getPackageTypesByIdFiltering() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        Long id = packageTypes.getId();

        defaultPackageTypesShouldBeFound("id.equals=" + id);
        defaultPackageTypesShouldNotBeFound("id.notEquals=" + id);

        defaultPackageTypesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPackageTypesShouldNotBeFound("id.greaterThan=" + id);

        defaultPackageTypesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPackageTypesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPackageTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where name equals to DEFAULT_NAME
        defaultPackageTypesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the packageTypesList where name equals to UPDATED_NAME
        defaultPackageTypesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where name not equals to DEFAULT_NAME
        defaultPackageTypesShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the packageTypesList where name not equals to UPDATED_NAME
        defaultPackageTypesShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPackageTypesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the packageTypesList where name equals to UPDATED_NAME
        defaultPackageTypesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where name is not null
        defaultPackageTypesShouldBeFound("name.specified=true");

        // Get all the packageTypesList where name is null
        defaultPackageTypesShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPackageTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where name contains DEFAULT_NAME
        defaultPackageTypesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the packageTypesList where name contains UPDATED_NAME
        defaultPackageTypesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where name does not contain DEFAULT_NAME
        defaultPackageTypesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the packageTypesList where name does not contain UPDATED_NAME
        defaultPackageTypesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPackageTypesByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validFrom equals to DEFAULT_VALID_FROM
        defaultPackageTypesShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the packageTypesList where validFrom equals to UPDATED_VALID_FROM
        defaultPackageTypesShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validFrom not equals to DEFAULT_VALID_FROM
        defaultPackageTypesShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the packageTypesList where validFrom not equals to UPDATED_VALID_FROM
        defaultPackageTypesShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultPackageTypesShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the packageTypesList where validFrom equals to UPDATED_VALID_FROM
        defaultPackageTypesShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validFrom is not null
        defaultPackageTypesShouldBeFound("validFrom.specified=true");

        // Get all the packageTypesList where validFrom is null
        defaultPackageTypesShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllPackageTypesByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validTo equals to DEFAULT_VALID_TO
        defaultPackageTypesShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the packageTypesList where validTo equals to UPDATED_VALID_TO
        defaultPackageTypesShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validTo not equals to DEFAULT_VALID_TO
        defaultPackageTypesShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the packageTypesList where validTo not equals to UPDATED_VALID_TO
        defaultPackageTypesShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultPackageTypesShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the packageTypesList where validTo equals to UPDATED_VALID_TO
        defaultPackageTypesShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validTo is not null
        defaultPackageTypesShouldBeFound("validTo.specified=true");

        // Get all the packageTypesList where validTo is null
        defaultPackageTypesShouldNotBeFound("validTo.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPackageTypesShouldBeFound(String filter) throws Exception {
        restPackageTypesMockMvc.perform(get("/api/package-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(packageTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restPackageTypesMockMvc.perform(get("/api/package-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPackageTypesShouldNotBeFound(String filter) throws Exception {
        restPackageTypesMockMvc.perform(get("/api/package-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPackageTypesMockMvc.perform(get("/api/package-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPackageTypes() throws Exception {
        // Get the packageTypes
        restPackageTypesMockMvc.perform(get("/api/package-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePackageTypes() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        int databaseSizeBeforeUpdate = packageTypesRepository.findAll().size();

        // Update the packageTypes
        PackageTypes updatedPackageTypes = packageTypesRepository.findById(packageTypes.getId()).get();
        // Disconnect from session so that the updates on updatedPackageTypes are not directly saved in db
        em.detach(updatedPackageTypes);
        updatedPackageTypes
            .name(UPDATED_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        PackageTypesDTO packageTypesDTO = packageTypesMapper.toDto(updatedPackageTypes);

        restPackageTypesMockMvc.perform(put("/api/package-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(packageTypesDTO)))
            .andExpect(status().isOk());

        // Validate the PackageTypes in the database
        List<PackageTypes> packageTypesList = packageTypesRepository.findAll();
        assertThat(packageTypesList).hasSize(databaseSizeBeforeUpdate);
        PackageTypes testPackageTypes = packageTypesList.get(packageTypesList.size() - 1);
        assertThat(testPackageTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPackageTypes.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testPackageTypes.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingPackageTypes() throws Exception {
        int databaseSizeBeforeUpdate = packageTypesRepository.findAll().size();

        // Create the PackageTypes
        PackageTypesDTO packageTypesDTO = packageTypesMapper.toDto(packageTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPackageTypesMockMvc.perform(put("/api/package-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(packageTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PackageTypes in the database
        List<PackageTypes> packageTypesList = packageTypesRepository.findAll();
        assertThat(packageTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePackageTypes() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        int databaseSizeBeforeDelete = packageTypesRepository.findAll().size();

        // Delete the packageTypes
        restPackageTypesMockMvc.perform(delete("/api/package-types/{id}", packageTypes.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PackageTypes> packageTypesList = packageTypesRepository.findAll();
        assertThat(packageTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

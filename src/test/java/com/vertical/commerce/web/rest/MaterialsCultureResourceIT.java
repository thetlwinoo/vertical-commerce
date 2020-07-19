package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.MaterialsCulture;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.Materials;
import com.vertical.commerce.repository.MaterialsCultureRepository;
import com.vertical.commerce.service.MaterialsCultureService;
import com.vertical.commerce.service.dto.MaterialsCultureDTO;
import com.vertical.commerce.service.mapper.MaterialsCultureMapper;
import com.vertical.commerce.service.dto.MaterialsCultureCriteria;
import com.vertical.commerce.service.MaterialsCultureQueryService;

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
 * Integration tests for the {@link MaterialsCultureResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class MaterialsCultureResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private MaterialsCultureRepository materialsCultureRepository;

    @Autowired
    private MaterialsCultureMapper materialsCultureMapper;

    @Autowired
    private MaterialsCultureService materialsCultureService;

    @Autowired
    private MaterialsCultureQueryService materialsCultureQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaterialsCultureMockMvc;

    private MaterialsCulture materialsCulture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterialsCulture createEntity(EntityManager em) {
        MaterialsCulture materialsCulture = new MaterialsCulture()
            .name(DEFAULT_NAME);
        return materialsCulture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterialsCulture createUpdatedEntity(EntityManager em) {
        MaterialsCulture materialsCulture = new MaterialsCulture()
            .name(UPDATED_NAME);
        return materialsCulture;
    }

    @BeforeEach
    public void initTest() {
        materialsCulture = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaterialsCulture() throws Exception {
        int databaseSizeBeforeCreate = materialsCultureRepository.findAll().size();
        // Create the MaterialsCulture
        MaterialsCultureDTO materialsCultureDTO = materialsCultureMapper.toDto(materialsCulture);
        restMaterialsCultureMockMvc.perform(post("/api/materials-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialsCultureDTO)))
            .andExpect(status().isCreated());

        // Validate the MaterialsCulture in the database
        List<MaterialsCulture> materialsCultureList = materialsCultureRepository.findAll();
        assertThat(materialsCultureList).hasSize(databaseSizeBeforeCreate + 1);
        MaterialsCulture testMaterialsCulture = materialsCultureList.get(materialsCultureList.size() - 1);
        assertThat(testMaterialsCulture.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createMaterialsCultureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = materialsCultureRepository.findAll().size();

        // Create the MaterialsCulture with an existing ID
        materialsCulture.setId(1L);
        MaterialsCultureDTO materialsCultureDTO = materialsCultureMapper.toDto(materialsCulture);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialsCultureMockMvc.perform(post("/api/materials-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialsCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MaterialsCulture in the database
        List<MaterialsCulture> materialsCultureList = materialsCultureRepository.findAll();
        assertThat(materialsCultureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialsCultureRepository.findAll().size();
        // set the field null
        materialsCulture.setName(null);

        // Create the MaterialsCulture, which fails.
        MaterialsCultureDTO materialsCultureDTO = materialsCultureMapper.toDto(materialsCulture);


        restMaterialsCultureMockMvc.perform(post("/api/materials-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialsCultureDTO)))
            .andExpect(status().isBadRequest());

        List<MaterialsCulture> materialsCultureList = materialsCultureRepository.findAll();
        assertThat(materialsCultureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMaterialsCultures() throws Exception {
        // Initialize the database
        materialsCultureRepository.saveAndFlush(materialsCulture);

        // Get all the materialsCultureList
        restMaterialsCultureMockMvc.perform(get("/api/materials-cultures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materialsCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getMaterialsCulture() throws Exception {
        // Initialize the database
        materialsCultureRepository.saveAndFlush(materialsCulture);

        // Get the materialsCulture
        restMaterialsCultureMockMvc.perform(get("/api/materials-cultures/{id}", materialsCulture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materialsCulture.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getMaterialsCulturesByIdFiltering() throws Exception {
        // Initialize the database
        materialsCultureRepository.saveAndFlush(materialsCulture);

        Long id = materialsCulture.getId();

        defaultMaterialsCultureShouldBeFound("id.equals=" + id);
        defaultMaterialsCultureShouldNotBeFound("id.notEquals=" + id);

        defaultMaterialsCultureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMaterialsCultureShouldNotBeFound("id.greaterThan=" + id);

        defaultMaterialsCultureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMaterialsCultureShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMaterialsCulturesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        materialsCultureRepository.saveAndFlush(materialsCulture);

        // Get all the materialsCultureList where name equals to DEFAULT_NAME
        defaultMaterialsCultureShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the materialsCultureList where name equals to UPDATED_NAME
        defaultMaterialsCultureShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMaterialsCulturesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        materialsCultureRepository.saveAndFlush(materialsCulture);

        // Get all the materialsCultureList where name not equals to DEFAULT_NAME
        defaultMaterialsCultureShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the materialsCultureList where name not equals to UPDATED_NAME
        defaultMaterialsCultureShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMaterialsCulturesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        materialsCultureRepository.saveAndFlush(materialsCulture);

        // Get all the materialsCultureList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMaterialsCultureShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the materialsCultureList where name equals to UPDATED_NAME
        defaultMaterialsCultureShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMaterialsCulturesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialsCultureRepository.saveAndFlush(materialsCulture);

        // Get all the materialsCultureList where name is not null
        defaultMaterialsCultureShouldBeFound("name.specified=true");

        // Get all the materialsCultureList where name is null
        defaultMaterialsCultureShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllMaterialsCulturesByNameContainsSomething() throws Exception {
        // Initialize the database
        materialsCultureRepository.saveAndFlush(materialsCulture);

        // Get all the materialsCultureList where name contains DEFAULT_NAME
        defaultMaterialsCultureShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the materialsCultureList where name contains UPDATED_NAME
        defaultMaterialsCultureShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMaterialsCulturesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        materialsCultureRepository.saveAndFlush(materialsCulture);

        // Get all the materialsCultureList where name does not contain DEFAULT_NAME
        defaultMaterialsCultureShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the materialsCultureList where name does not contain UPDATED_NAME
        defaultMaterialsCultureShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllMaterialsCulturesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        materialsCultureRepository.saveAndFlush(materialsCulture);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        materialsCulture.setCulture(culture);
        materialsCultureRepository.saveAndFlush(materialsCulture);
        Long cultureId = culture.getId();

        // Get all the materialsCultureList where culture equals to cultureId
        defaultMaterialsCultureShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the materialsCultureList where culture equals to cultureId + 1
        defaultMaterialsCultureShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllMaterialsCulturesByMaterialIsEqualToSomething() throws Exception {
        // Initialize the database
        materialsCultureRepository.saveAndFlush(materialsCulture);
        Materials material = MaterialsResourceIT.createEntity(em);
        em.persist(material);
        em.flush();
        materialsCulture.setMaterial(material);
        materialsCultureRepository.saveAndFlush(materialsCulture);
        Long materialId = material.getId();

        // Get all the materialsCultureList where material equals to materialId
        defaultMaterialsCultureShouldBeFound("materialId.equals=" + materialId);

        // Get all the materialsCultureList where material equals to materialId + 1
        defaultMaterialsCultureShouldNotBeFound("materialId.equals=" + (materialId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMaterialsCultureShouldBeFound(String filter) throws Exception {
        restMaterialsCultureMockMvc.perform(get("/api/materials-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materialsCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restMaterialsCultureMockMvc.perform(get("/api/materials-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMaterialsCultureShouldNotBeFound(String filter) throws Exception {
        restMaterialsCultureMockMvc.perform(get("/api/materials-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMaterialsCultureMockMvc.perform(get("/api/materials-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMaterialsCulture() throws Exception {
        // Get the materialsCulture
        restMaterialsCultureMockMvc.perform(get("/api/materials-cultures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaterialsCulture() throws Exception {
        // Initialize the database
        materialsCultureRepository.saveAndFlush(materialsCulture);

        int databaseSizeBeforeUpdate = materialsCultureRepository.findAll().size();

        // Update the materialsCulture
        MaterialsCulture updatedMaterialsCulture = materialsCultureRepository.findById(materialsCulture.getId()).get();
        // Disconnect from session so that the updates on updatedMaterialsCulture are not directly saved in db
        em.detach(updatedMaterialsCulture);
        updatedMaterialsCulture
            .name(UPDATED_NAME);
        MaterialsCultureDTO materialsCultureDTO = materialsCultureMapper.toDto(updatedMaterialsCulture);

        restMaterialsCultureMockMvc.perform(put("/api/materials-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialsCultureDTO)))
            .andExpect(status().isOk());

        // Validate the MaterialsCulture in the database
        List<MaterialsCulture> materialsCultureList = materialsCultureRepository.findAll();
        assertThat(materialsCultureList).hasSize(databaseSizeBeforeUpdate);
        MaterialsCulture testMaterialsCulture = materialsCultureList.get(materialsCultureList.size() - 1);
        assertThat(testMaterialsCulture.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMaterialsCulture() throws Exception {
        int databaseSizeBeforeUpdate = materialsCultureRepository.findAll().size();

        // Create the MaterialsCulture
        MaterialsCultureDTO materialsCultureDTO = materialsCultureMapper.toDto(materialsCulture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialsCultureMockMvc.perform(put("/api/materials-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialsCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MaterialsCulture in the database
        List<MaterialsCulture> materialsCultureList = materialsCultureRepository.findAll();
        assertThat(materialsCultureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMaterialsCulture() throws Exception {
        // Initialize the database
        materialsCultureRepository.saveAndFlush(materialsCulture);

        int databaseSizeBeforeDelete = materialsCultureRepository.findAll().size();

        // Delete the materialsCulture
        restMaterialsCultureMockMvc.perform(delete("/api/materials-cultures/{id}", materialsCulture.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MaterialsCulture> materialsCultureList = materialsCultureRepository.findAll();
        assertThat(materialsCultureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.MaterialsLocalize;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.Materials;
import com.vertical.commerce.repository.MaterialsLocalizeRepository;
import com.vertical.commerce.service.MaterialsLocalizeService;
import com.vertical.commerce.service.dto.MaterialsLocalizeDTO;
import com.vertical.commerce.service.mapper.MaterialsLocalizeMapper;
import com.vertical.commerce.service.dto.MaterialsLocalizeCriteria;
import com.vertical.commerce.service.MaterialsLocalizeQueryService;

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
 * Integration tests for the {@link MaterialsLocalizeResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class MaterialsLocalizeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private MaterialsLocalizeRepository materialsLocalizeRepository;

    @Autowired
    private MaterialsLocalizeMapper materialsLocalizeMapper;

    @Autowired
    private MaterialsLocalizeService materialsLocalizeService;

    @Autowired
    private MaterialsLocalizeQueryService materialsLocalizeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaterialsLocalizeMockMvc;

    private MaterialsLocalize materialsLocalize;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterialsLocalize createEntity(EntityManager em) {
        MaterialsLocalize materialsLocalize = new MaterialsLocalize()
            .name(DEFAULT_NAME);
        return materialsLocalize;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterialsLocalize createUpdatedEntity(EntityManager em) {
        MaterialsLocalize materialsLocalize = new MaterialsLocalize()
            .name(UPDATED_NAME);
        return materialsLocalize;
    }

    @BeforeEach
    public void initTest() {
        materialsLocalize = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaterialsLocalize() throws Exception {
        int databaseSizeBeforeCreate = materialsLocalizeRepository.findAll().size();
        // Create the MaterialsLocalize
        MaterialsLocalizeDTO materialsLocalizeDTO = materialsLocalizeMapper.toDto(materialsLocalize);
        restMaterialsLocalizeMockMvc.perform(post("/api/materials-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialsLocalizeDTO)))
            .andExpect(status().isCreated());

        // Validate the MaterialsLocalize in the database
        List<MaterialsLocalize> materialsLocalizeList = materialsLocalizeRepository.findAll();
        assertThat(materialsLocalizeList).hasSize(databaseSizeBeforeCreate + 1);
        MaterialsLocalize testMaterialsLocalize = materialsLocalizeList.get(materialsLocalizeList.size() - 1);
        assertThat(testMaterialsLocalize.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createMaterialsLocalizeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = materialsLocalizeRepository.findAll().size();

        // Create the MaterialsLocalize with an existing ID
        materialsLocalize.setId(1L);
        MaterialsLocalizeDTO materialsLocalizeDTO = materialsLocalizeMapper.toDto(materialsLocalize);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialsLocalizeMockMvc.perform(post("/api/materials-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialsLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MaterialsLocalize in the database
        List<MaterialsLocalize> materialsLocalizeList = materialsLocalizeRepository.findAll();
        assertThat(materialsLocalizeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialsLocalizeRepository.findAll().size();
        // set the field null
        materialsLocalize.setName(null);

        // Create the MaterialsLocalize, which fails.
        MaterialsLocalizeDTO materialsLocalizeDTO = materialsLocalizeMapper.toDto(materialsLocalize);


        restMaterialsLocalizeMockMvc.perform(post("/api/materials-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialsLocalizeDTO)))
            .andExpect(status().isBadRequest());

        List<MaterialsLocalize> materialsLocalizeList = materialsLocalizeRepository.findAll();
        assertThat(materialsLocalizeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMaterialsLocalizes() throws Exception {
        // Initialize the database
        materialsLocalizeRepository.saveAndFlush(materialsLocalize);

        // Get all the materialsLocalizeList
        restMaterialsLocalizeMockMvc.perform(get("/api/materials-localizes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materialsLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getMaterialsLocalize() throws Exception {
        // Initialize the database
        materialsLocalizeRepository.saveAndFlush(materialsLocalize);

        // Get the materialsLocalize
        restMaterialsLocalizeMockMvc.perform(get("/api/materials-localizes/{id}", materialsLocalize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materialsLocalize.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getMaterialsLocalizesByIdFiltering() throws Exception {
        // Initialize the database
        materialsLocalizeRepository.saveAndFlush(materialsLocalize);

        Long id = materialsLocalize.getId();

        defaultMaterialsLocalizeShouldBeFound("id.equals=" + id);
        defaultMaterialsLocalizeShouldNotBeFound("id.notEquals=" + id);

        defaultMaterialsLocalizeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMaterialsLocalizeShouldNotBeFound("id.greaterThan=" + id);

        defaultMaterialsLocalizeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMaterialsLocalizeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMaterialsLocalizesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        materialsLocalizeRepository.saveAndFlush(materialsLocalize);

        // Get all the materialsLocalizeList where name equals to DEFAULT_NAME
        defaultMaterialsLocalizeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the materialsLocalizeList where name equals to UPDATED_NAME
        defaultMaterialsLocalizeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMaterialsLocalizesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        materialsLocalizeRepository.saveAndFlush(materialsLocalize);

        // Get all the materialsLocalizeList where name not equals to DEFAULT_NAME
        defaultMaterialsLocalizeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the materialsLocalizeList where name not equals to UPDATED_NAME
        defaultMaterialsLocalizeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMaterialsLocalizesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        materialsLocalizeRepository.saveAndFlush(materialsLocalize);

        // Get all the materialsLocalizeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMaterialsLocalizeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the materialsLocalizeList where name equals to UPDATED_NAME
        defaultMaterialsLocalizeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMaterialsLocalizesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialsLocalizeRepository.saveAndFlush(materialsLocalize);

        // Get all the materialsLocalizeList where name is not null
        defaultMaterialsLocalizeShouldBeFound("name.specified=true");

        // Get all the materialsLocalizeList where name is null
        defaultMaterialsLocalizeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllMaterialsLocalizesByNameContainsSomething() throws Exception {
        // Initialize the database
        materialsLocalizeRepository.saveAndFlush(materialsLocalize);

        // Get all the materialsLocalizeList where name contains DEFAULT_NAME
        defaultMaterialsLocalizeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the materialsLocalizeList where name contains UPDATED_NAME
        defaultMaterialsLocalizeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMaterialsLocalizesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        materialsLocalizeRepository.saveAndFlush(materialsLocalize);

        // Get all the materialsLocalizeList where name does not contain DEFAULT_NAME
        defaultMaterialsLocalizeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the materialsLocalizeList where name does not contain UPDATED_NAME
        defaultMaterialsLocalizeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllMaterialsLocalizesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        materialsLocalizeRepository.saveAndFlush(materialsLocalize);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        materialsLocalize.setCulture(culture);
        materialsLocalizeRepository.saveAndFlush(materialsLocalize);
        Long cultureId = culture.getId();

        // Get all the materialsLocalizeList where culture equals to cultureId
        defaultMaterialsLocalizeShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the materialsLocalizeList where culture equals to cultureId + 1
        defaultMaterialsLocalizeShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllMaterialsLocalizesByMaterialIsEqualToSomething() throws Exception {
        // Initialize the database
        materialsLocalizeRepository.saveAndFlush(materialsLocalize);
        Materials material = MaterialsResourceIT.createEntity(em);
        em.persist(material);
        em.flush();
        materialsLocalize.setMaterial(material);
        materialsLocalizeRepository.saveAndFlush(materialsLocalize);
        Long materialId = material.getId();

        // Get all the materialsLocalizeList where material equals to materialId
        defaultMaterialsLocalizeShouldBeFound("materialId.equals=" + materialId);

        // Get all the materialsLocalizeList where material equals to materialId + 1
        defaultMaterialsLocalizeShouldNotBeFound("materialId.equals=" + (materialId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMaterialsLocalizeShouldBeFound(String filter) throws Exception {
        restMaterialsLocalizeMockMvc.perform(get("/api/materials-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materialsLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restMaterialsLocalizeMockMvc.perform(get("/api/materials-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMaterialsLocalizeShouldNotBeFound(String filter) throws Exception {
        restMaterialsLocalizeMockMvc.perform(get("/api/materials-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMaterialsLocalizeMockMvc.perform(get("/api/materials-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMaterialsLocalize() throws Exception {
        // Get the materialsLocalize
        restMaterialsLocalizeMockMvc.perform(get("/api/materials-localizes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaterialsLocalize() throws Exception {
        // Initialize the database
        materialsLocalizeRepository.saveAndFlush(materialsLocalize);

        int databaseSizeBeforeUpdate = materialsLocalizeRepository.findAll().size();

        // Update the materialsLocalize
        MaterialsLocalize updatedMaterialsLocalize = materialsLocalizeRepository.findById(materialsLocalize.getId()).get();
        // Disconnect from session so that the updates on updatedMaterialsLocalize are not directly saved in db
        em.detach(updatedMaterialsLocalize);
        updatedMaterialsLocalize
            .name(UPDATED_NAME);
        MaterialsLocalizeDTO materialsLocalizeDTO = materialsLocalizeMapper.toDto(updatedMaterialsLocalize);

        restMaterialsLocalizeMockMvc.perform(put("/api/materials-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialsLocalizeDTO)))
            .andExpect(status().isOk());

        // Validate the MaterialsLocalize in the database
        List<MaterialsLocalize> materialsLocalizeList = materialsLocalizeRepository.findAll();
        assertThat(materialsLocalizeList).hasSize(databaseSizeBeforeUpdate);
        MaterialsLocalize testMaterialsLocalize = materialsLocalizeList.get(materialsLocalizeList.size() - 1);
        assertThat(testMaterialsLocalize.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMaterialsLocalize() throws Exception {
        int databaseSizeBeforeUpdate = materialsLocalizeRepository.findAll().size();

        // Create the MaterialsLocalize
        MaterialsLocalizeDTO materialsLocalizeDTO = materialsLocalizeMapper.toDto(materialsLocalize);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialsLocalizeMockMvc.perform(put("/api/materials-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialsLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MaterialsLocalize in the database
        List<MaterialsLocalize> materialsLocalizeList = materialsLocalizeRepository.findAll();
        assertThat(materialsLocalizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMaterialsLocalize() throws Exception {
        // Initialize the database
        materialsLocalizeRepository.saveAndFlush(materialsLocalize);

        int databaseSizeBeforeDelete = materialsLocalizeRepository.findAll().size();

        // Delete the materialsLocalize
        restMaterialsLocalizeMockMvc.perform(delete("/api/materials-localizes/{id}", materialsLocalize.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MaterialsLocalize> materialsLocalizeList = materialsLocalizeRepository.findAll();
        assertThat(materialsLocalizeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

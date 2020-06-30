package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.TaxClass;
import com.vertical.commerce.repository.TaxClassRepository;
import com.vertical.commerce.service.TaxClassService;
import com.vertical.commerce.service.dto.TaxClassDTO;
import com.vertical.commerce.service.mapper.TaxClassMapper;
import com.vertical.commerce.service.dto.TaxClassCriteria;
import com.vertical.commerce.service.TaxClassQueryService;

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
 * Integration tests for the {@link TaxClassResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TaxClassResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TaxClassRepository taxClassRepository;

    @Autowired
    private TaxClassMapper taxClassMapper;

    @Autowired
    private TaxClassService taxClassService;

    @Autowired
    private TaxClassQueryService taxClassQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaxClassMockMvc;

    private TaxClass taxClass;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxClass createEntity(EntityManager em) {
        TaxClass taxClass = new TaxClass()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME);
        return taxClass;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxClass createUpdatedEntity(EntityManager em) {
        TaxClass taxClass = new TaxClass()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);
        return taxClass;
    }

    @BeforeEach
    public void initTest() {
        taxClass = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaxClass() throws Exception {
        int databaseSizeBeforeCreate = taxClassRepository.findAll().size();
        // Create the TaxClass
        TaxClassDTO taxClassDTO = taxClassMapper.toDto(taxClass);
        restTaxClassMockMvc.perform(post("/api/tax-classes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxClassDTO)))
            .andExpect(status().isCreated());

        // Validate the TaxClass in the database
        List<TaxClass> taxClassList = taxClassRepository.findAll();
        assertThat(taxClassList).hasSize(databaseSizeBeforeCreate + 1);
        TaxClass testTaxClass = taxClassList.get(taxClassList.size() - 1);
        assertThat(testTaxClass.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTaxClass.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTaxClassWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taxClassRepository.findAll().size();

        // Create the TaxClass with an existing ID
        taxClass.setId(1L);
        TaxClassDTO taxClassDTO = taxClassMapper.toDto(taxClass);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxClassMockMvc.perform(post("/api/tax-classes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaxClass in the database
        List<TaxClass> taxClassList = taxClassRepository.findAll();
        assertThat(taxClassList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTaxClasses() throws Exception {
        // Initialize the database
        taxClassRepository.saveAndFlush(taxClass);

        // Get all the taxClassList
        restTaxClassMockMvc.perform(get("/api/tax-classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getTaxClass() throws Exception {
        // Initialize the database
        taxClassRepository.saveAndFlush(taxClass);

        // Get the taxClass
        restTaxClassMockMvc.perform(get("/api/tax-classes/{id}", taxClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taxClass.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getTaxClassesByIdFiltering() throws Exception {
        // Initialize the database
        taxClassRepository.saveAndFlush(taxClass);

        Long id = taxClass.getId();

        defaultTaxClassShouldBeFound("id.equals=" + id);
        defaultTaxClassShouldNotBeFound("id.notEquals=" + id);

        defaultTaxClassShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTaxClassShouldNotBeFound("id.greaterThan=" + id);

        defaultTaxClassShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTaxClassShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTaxClassesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        taxClassRepository.saveAndFlush(taxClass);

        // Get all the taxClassList where code equals to DEFAULT_CODE
        defaultTaxClassShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the taxClassList where code equals to UPDATED_CODE
        defaultTaxClassShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllTaxClassesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taxClassRepository.saveAndFlush(taxClass);

        // Get all the taxClassList where code not equals to DEFAULT_CODE
        defaultTaxClassShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the taxClassList where code not equals to UPDATED_CODE
        defaultTaxClassShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllTaxClassesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        taxClassRepository.saveAndFlush(taxClass);

        // Get all the taxClassList where code in DEFAULT_CODE or UPDATED_CODE
        defaultTaxClassShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the taxClassList where code equals to UPDATED_CODE
        defaultTaxClassShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllTaxClassesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        taxClassRepository.saveAndFlush(taxClass);

        // Get all the taxClassList where code is not null
        defaultTaxClassShouldBeFound("code.specified=true");

        // Get all the taxClassList where code is null
        defaultTaxClassShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllTaxClassesByCodeContainsSomething() throws Exception {
        // Initialize the database
        taxClassRepository.saveAndFlush(taxClass);

        // Get all the taxClassList where code contains DEFAULT_CODE
        defaultTaxClassShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the taxClassList where code contains UPDATED_CODE
        defaultTaxClassShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllTaxClassesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        taxClassRepository.saveAndFlush(taxClass);

        // Get all the taxClassList where code does not contain DEFAULT_CODE
        defaultTaxClassShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the taxClassList where code does not contain UPDATED_CODE
        defaultTaxClassShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllTaxClassesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        taxClassRepository.saveAndFlush(taxClass);

        // Get all the taxClassList where name equals to DEFAULT_NAME
        defaultTaxClassShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the taxClassList where name equals to UPDATED_NAME
        defaultTaxClassShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTaxClassesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taxClassRepository.saveAndFlush(taxClass);

        // Get all the taxClassList where name not equals to DEFAULT_NAME
        defaultTaxClassShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the taxClassList where name not equals to UPDATED_NAME
        defaultTaxClassShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTaxClassesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        taxClassRepository.saveAndFlush(taxClass);

        // Get all the taxClassList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTaxClassShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the taxClassList where name equals to UPDATED_NAME
        defaultTaxClassShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTaxClassesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        taxClassRepository.saveAndFlush(taxClass);

        // Get all the taxClassList where name is not null
        defaultTaxClassShouldBeFound("name.specified=true");

        // Get all the taxClassList where name is null
        defaultTaxClassShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTaxClassesByNameContainsSomething() throws Exception {
        // Initialize the database
        taxClassRepository.saveAndFlush(taxClass);

        // Get all the taxClassList where name contains DEFAULT_NAME
        defaultTaxClassShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the taxClassList where name contains UPDATED_NAME
        defaultTaxClassShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTaxClassesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        taxClassRepository.saveAndFlush(taxClass);

        // Get all the taxClassList where name does not contain DEFAULT_NAME
        defaultTaxClassShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the taxClassList where name does not contain UPDATED_NAME
        defaultTaxClassShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTaxClassShouldBeFound(String filter) throws Exception {
        restTaxClassMockMvc.perform(get("/api/tax-classes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restTaxClassMockMvc.perform(get("/api/tax-classes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTaxClassShouldNotBeFound(String filter) throws Exception {
        restTaxClassMockMvc.perform(get("/api/tax-classes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTaxClassMockMvc.perform(get("/api/tax-classes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTaxClass() throws Exception {
        // Get the taxClass
        restTaxClassMockMvc.perform(get("/api/tax-classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaxClass() throws Exception {
        // Initialize the database
        taxClassRepository.saveAndFlush(taxClass);

        int databaseSizeBeforeUpdate = taxClassRepository.findAll().size();

        // Update the taxClass
        TaxClass updatedTaxClass = taxClassRepository.findById(taxClass.getId()).get();
        // Disconnect from session so that the updates on updatedTaxClass are not directly saved in db
        em.detach(updatedTaxClass);
        updatedTaxClass
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);
        TaxClassDTO taxClassDTO = taxClassMapper.toDto(updatedTaxClass);

        restTaxClassMockMvc.perform(put("/api/tax-classes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxClassDTO)))
            .andExpect(status().isOk());

        // Validate the TaxClass in the database
        List<TaxClass> taxClassList = taxClassRepository.findAll();
        assertThat(taxClassList).hasSize(databaseSizeBeforeUpdate);
        TaxClass testTaxClass = taxClassList.get(taxClassList.size() - 1);
        assertThat(testTaxClass.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTaxClass.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTaxClass() throws Exception {
        int databaseSizeBeforeUpdate = taxClassRepository.findAll().size();

        // Create the TaxClass
        TaxClassDTO taxClassDTO = taxClassMapper.toDto(taxClass);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxClassMockMvc.perform(put("/api/tax-classes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaxClass in the database
        List<TaxClass> taxClassList = taxClassRepository.findAll();
        assertThat(taxClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaxClass() throws Exception {
        // Initialize the database
        taxClassRepository.saveAndFlush(taxClass);

        int databaseSizeBeforeDelete = taxClassRepository.findAll().size();

        // Delete the taxClass
        restTaxClassMockMvc.perform(delete("/api/tax-classes/{id}", taxClass.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaxClass> taxClassList = taxClassRepository.findAll();
        assertThat(taxClassList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.Tax;
import com.vertical.commerce.domain.TaxClass;
import com.vertical.commerce.repository.TaxRepository;
import com.vertical.commerce.service.TaxService;
import com.vertical.commerce.service.dto.TaxDTO;
import com.vertical.commerce.service.mapper.TaxMapper;
import com.vertical.commerce.service.dto.TaxCriteria;
import com.vertical.commerce.service.TaxQueryService;

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
 * Integration tests for the {@link TaxResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TaxResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_RATE = 1D;
    private static final Double UPDATED_RATE = 2D;
    private static final Double SMALLER_RATE = 1D - 1D;

    private static final Instant DEFAULT_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TaxRepository taxRepository;

    @Autowired
    private TaxMapper taxMapper;

    @Autowired
    private TaxService taxService;

    @Autowired
    private TaxQueryService taxQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaxMockMvc;

    private Tax tax;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tax createEntity(EntityManager em) {
        Tax tax = new Tax()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .rate(DEFAULT_RATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return tax;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tax createUpdatedEntity(EntityManager em) {
        Tax tax = new Tax()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .rate(UPDATED_RATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return tax;
    }

    @BeforeEach
    public void initTest() {
        tax = createEntity(em);
    }

    @Test
    @Transactional
    public void createTax() throws Exception {
        int databaseSizeBeforeCreate = taxRepository.findAll().size();
        // Create the Tax
        TaxDTO taxDTO = taxMapper.toDto(tax);
        restTaxMockMvc.perform(post("/api/taxes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxDTO)))
            .andExpect(status().isCreated());

        // Validate the Tax in the database
        List<Tax> taxList = taxRepository.findAll();
        assertThat(taxList).hasSize(databaseSizeBeforeCreate + 1);
        Tax testTax = taxList.get(taxList.size() - 1);
        assertThat(testTax.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTax.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTax.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testTax.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createTaxWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taxRepository.findAll().size();

        // Create the Tax with an existing ID
        tax.setId(1L);
        TaxDTO taxDTO = taxMapper.toDto(tax);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxMockMvc.perform(post("/api/taxes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tax in the database
        List<Tax> taxList = taxRepository.findAll();
        assertThat(taxList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxRepository.findAll().size();
        // set the field null
        tax.setName(null);

        // Create the Tax, which fails.
        TaxDTO taxDTO = taxMapper.toDto(tax);


        restTaxMockMvc.perform(post("/api/taxes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxDTO)))
            .andExpect(status().isBadRequest());

        List<Tax> taxList = taxRepository.findAll();
        assertThat(taxList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxRepository.findAll().size();
        // set the field null
        tax.setRate(null);

        // Create the Tax, which fails.
        TaxDTO taxDTO = taxMapper.toDto(tax);


        restTaxMockMvc.perform(post("/api/taxes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxDTO)))
            .andExpect(status().isBadRequest());

        List<Tax> taxList = taxRepository.findAll();
        assertThat(taxList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModifiedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxRepository.findAll().size();
        // set the field null
        tax.setModifiedDate(null);

        // Create the Tax, which fails.
        TaxDTO taxDTO = taxMapper.toDto(tax);


        restTaxMockMvc.perform(post("/api/taxes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxDTO)))
            .andExpect(status().isBadRequest());

        List<Tax> taxList = taxRepository.findAll();
        assertThat(taxList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaxes() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList
        restTaxMockMvc.perform(get("/api/taxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tax.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getTax() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get the tax
        restTaxMockMvc.perform(get("/api/taxes/{id}", tax.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tax.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.doubleValue()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getTaxesByIdFiltering() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        Long id = tax.getId();

        defaultTaxShouldBeFound("id.equals=" + id);
        defaultTaxShouldNotBeFound("id.notEquals=" + id);

        defaultTaxShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTaxShouldNotBeFound("id.greaterThan=" + id);

        defaultTaxShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTaxShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTaxesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where code equals to DEFAULT_CODE
        defaultTaxShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the taxList where code equals to UPDATED_CODE
        defaultTaxShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllTaxesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where code not equals to DEFAULT_CODE
        defaultTaxShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the taxList where code not equals to UPDATED_CODE
        defaultTaxShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllTaxesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where code in DEFAULT_CODE or UPDATED_CODE
        defaultTaxShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the taxList where code equals to UPDATED_CODE
        defaultTaxShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllTaxesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where code is not null
        defaultTaxShouldBeFound("code.specified=true");

        // Get all the taxList where code is null
        defaultTaxShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllTaxesByCodeContainsSomething() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where code contains DEFAULT_CODE
        defaultTaxShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the taxList where code contains UPDATED_CODE
        defaultTaxShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllTaxesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where code does not contain DEFAULT_CODE
        defaultTaxShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the taxList where code does not contain UPDATED_CODE
        defaultTaxShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllTaxesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where name equals to DEFAULT_NAME
        defaultTaxShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the taxList where name equals to UPDATED_NAME
        defaultTaxShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTaxesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where name not equals to DEFAULT_NAME
        defaultTaxShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the taxList where name not equals to UPDATED_NAME
        defaultTaxShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTaxesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTaxShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the taxList where name equals to UPDATED_NAME
        defaultTaxShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTaxesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where name is not null
        defaultTaxShouldBeFound("name.specified=true");

        // Get all the taxList where name is null
        defaultTaxShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTaxesByNameContainsSomething() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where name contains DEFAULT_NAME
        defaultTaxShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the taxList where name contains UPDATED_NAME
        defaultTaxShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTaxesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where name does not contain DEFAULT_NAME
        defaultTaxShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the taxList where name does not contain UPDATED_NAME
        defaultTaxShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTaxesByRateIsEqualToSomething() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where rate equals to DEFAULT_RATE
        defaultTaxShouldBeFound("rate.equals=" + DEFAULT_RATE);

        // Get all the taxList where rate equals to UPDATED_RATE
        defaultTaxShouldNotBeFound("rate.equals=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllTaxesByRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where rate not equals to DEFAULT_RATE
        defaultTaxShouldNotBeFound("rate.notEquals=" + DEFAULT_RATE);

        // Get all the taxList where rate not equals to UPDATED_RATE
        defaultTaxShouldBeFound("rate.notEquals=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllTaxesByRateIsInShouldWork() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where rate in DEFAULT_RATE or UPDATED_RATE
        defaultTaxShouldBeFound("rate.in=" + DEFAULT_RATE + "," + UPDATED_RATE);

        // Get all the taxList where rate equals to UPDATED_RATE
        defaultTaxShouldNotBeFound("rate.in=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllTaxesByRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where rate is not null
        defaultTaxShouldBeFound("rate.specified=true");

        // Get all the taxList where rate is null
        defaultTaxShouldNotBeFound("rate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTaxesByRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where rate is greater than or equal to DEFAULT_RATE
        defaultTaxShouldBeFound("rate.greaterThanOrEqual=" + DEFAULT_RATE);

        // Get all the taxList where rate is greater than or equal to UPDATED_RATE
        defaultTaxShouldNotBeFound("rate.greaterThanOrEqual=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllTaxesByRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where rate is less than or equal to DEFAULT_RATE
        defaultTaxShouldBeFound("rate.lessThanOrEqual=" + DEFAULT_RATE);

        // Get all the taxList where rate is less than or equal to SMALLER_RATE
        defaultTaxShouldNotBeFound("rate.lessThanOrEqual=" + SMALLER_RATE);
    }

    @Test
    @Transactional
    public void getAllTaxesByRateIsLessThanSomething() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where rate is less than DEFAULT_RATE
        defaultTaxShouldNotBeFound("rate.lessThan=" + DEFAULT_RATE);

        // Get all the taxList where rate is less than UPDATED_RATE
        defaultTaxShouldBeFound("rate.lessThan=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllTaxesByRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where rate is greater than DEFAULT_RATE
        defaultTaxShouldNotBeFound("rate.greaterThan=" + DEFAULT_RATE);

        // Get all the taxList where rate is greater than SMALLER_RATE
        defaultTaxShouldBeFound("rate.greaterThan=" + SMALLER_RATE);
    }


    @Test
    @Transactional
    public void getAllTaxesByModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where modifiedDate equals to DEFAULT_MODIFIED_DATE
        defaultTaxShouldBeFound("modifiedDate.equals=" + DEFAULT_MODIFIED_DATE);

        // Get all the taxList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultTaxShouldNotBeFound("modifiedDate.equals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllTaxesByModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where modifiedDate not equals to DEFAULT_MODIFIED_DATE
        defaultTaxShouldNotBeFound("modifiedDate.notEquals=" + DEFAULT_MODIFIED_DATE);

        // Get all the taxList where modifiedDate not equals to UPDATED_MODIFIED_DATE
        defaultTaxShouldBeFound("modifiedDate.notEquals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllTaxesByModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where modifiedDate in DEFAULT_MODIFIED_DATE or UPDATED_MODIFIED_DATE
        defaultTaxShouldBeFound("modifiedDate.in=" + DEFAULT_MODIFIED_DATE + "," + UPDATED_MODIFIED_DATE);

        // Get all the taxList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultTaxShouldNotBeFound("modifiedDate.in=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllTaxesByModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where modifiedDate is not null
        defaultTaxShouldBeFound("modifiedDate.specified=true");

        // Get all the taxList where modifiedDate is null
        defaultTaxShouldNotBeFound("modifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTaxesByTaxClassIsEqualToSomething() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);
        TaxClass taxClass = TaxClassResourceIT.createEntity(em);
        em.persist(taxClass);
        em.flush();
        tax.setTaxClass(taxClass);
        taxRepository.saveAndFlush(tax);
        Long taxClassId = taxClass.getId();

        // Get all the taxList where taxClass equals to taxClassId
        defaultTaxShouldBeFound("taxClassId.equals=" + taxClassId);

        // Get all the taxList where taxClass equals to taxClassId + 1
        defaultTaxShouldNotBeFound("taxClassId.equals=" + (taxClassId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTaxShouldBeFound(String filter) throws Exception {
        restTaxMockMvc.perform(get("/api/taxes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tax.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restTaxMockMvc.perform(get("/api/taxes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTaxShouldNotBeFound(String filter) throws Exception {
        restTaxMockMvc.perform(get("/api/taxes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTaxMockMvc.perform(get("/api/taxes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTax() throws Exception {
        // Get the tax
        restTaxMockMvc.perform(get("/api/taxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTax() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        int databaseSizeBeforeUpdate = taxRepository.findAll().size();

        // Update the tax
        Tax updatedTax = taxRepository.findById(tax.getId()).get();
        // Disconnect from session so that the updates on updatedTax are not directly saved in db
        em.detach(updatedTax);
        updatedTax
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .rate(UPDATED_RATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        TaxDTO taxDTO = taxMapper.toDto(updatedTax);

        restTaxMockMvc.perform(put("/api/taxes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxDTO)))
            .andExpect(status().isOk());

        // Validate the Tax in the database
        List<Tax> taxList = taxRepository.findAll();
        assertThat(taxList).hasSize(databaseSizeBeforeUpdate);
        Tax testTax = taxList.get(taxList.size() - 1);
        assertThat(testTax.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTax.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTax.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testTax.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTax() throws Exception {
        int databaseSizeBeforeUpdate = taxRepository.findAll().size();

        // Create the Tax
        TaxDTO taxDTO = taxMapper.toDto(tax);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxMockMvc.perform(put("/api/taxes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tax in the database
        List<Tax> taxList = taxRepository.findAll();
        assertThat(taxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTax() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        int databaseSizeBeforeDelete = taxRepository.findAll().size();

        // Delete the tax
        restTaxMockMvc.perform(delete("/api/taxes/{id}", tax.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tax> taxList = taxRepository.findAll();
        assertThat(taxList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

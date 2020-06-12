package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.CreditCardType;
import com.vertical.commerce.repository.CreditCardTypeRepository;
import com.vertical.commerce.service.CreditCardTypeService;
import com.vertical.commerce.service.dto.CreditCardTypeDTO;
import com.vertical.commerce.service.mapper.CreditCardTypeMapper;
import com.vertical.commerce.service.dto.CreditCardTypeCriteria;
import com.vertical.commerce.service.CreditCardTypeQueryService;

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
 * Integration tests for the {@link CreditCardTypeResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CreditCardTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CreditCardTypeRepository creditCardTypeRepository;

    @Autowired
    private CreditCardTypeMapper creditCardTypeMapper;

    @Autowired
    private CreditCardTypeService creditCardTypeService;

    @Autowired
    private CreditCardTypeQueryService creditCardTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCreditCardTypeMockMvc;

    private CreditCardType creditCardType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreditCardType createEntity(EntityManager em) {
        CreditCardType creditCardType = new CreditCardType()
            .name(DEFAULT_NAME)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return creditCardType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreditCardType createUpdatedEntity(EntityManager em) {
        CreditCardType creditCardType = new CreditCardType()
            .name(UPDATED_NAME)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return creditCardType;
    }

    @BeforeEach
    public void initTest() {
        creditCardType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCreditCardType() throws Exception {
        int databaseSizeBeforeCreate = creditCardTypeRepository.findAll().size();
        // Create the CreditCardType
        CreditCardTypeDTO creditCardTypeDTO = creditCardTypeMapper.toDto(creditCardType);
        restCreditCardTypeMockMvc.perform(post("/api/credit-card-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(creditCardTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CreditCardType in the database
        List<CreditCardType> creditCardTypeList = creditCardTypeRepository.findAll();
        assertThat(creditCardTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CreditCardType testCreditCardType = creditCardTypeList.get(creditCardTypeList.size() - 1);
        assertThat(testCreditCardType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCreditCardType.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createCreditCardTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = creditCardTypeRepository.findAll().size();

        // Create the CreditCardType with an existing ID
        creditCardType.setId(1L);
        CreditCardTypeDTO creditCardTypeDTO = creditCardTypeMapper.toDto(creditCardType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreditCardTypeMockMvc.perform(post("/api/credit-card-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(creditCardTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CreditCardType in the database
        List<CreditCardType> creditCardTypeList = creditCardTypeRepository.findAll();
        assertThat(creditCardTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCreditCardTypes() throws Exception {
        // Initialize the database
        creditCardTypeRepository.saveAndFlush(creditCardType);

        // Get all the creditCardTypeList
        restCreditCardTypeMockMvc.perform(get("/api/credit-card-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditCardType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getCreditCardType() throws Exception {
        // Initialize the database
        creditCardTypeRepository.saveAndFlush(creditCardType);

        // Get the creditCardType
        restCreditCardTypeMockMvc.perform(get("/api/credit-card-types/{id}", creditCardType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(creditCardType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getCreditCardTypesByIdFiltering() throws Exception {
        // Initialize the database
        creditCardTypeRepository.saveAndFlush(creditCardType);

        Long id = creditCardType.getId();

        defaultCreditCardTypeShouldBeFound("id.equals=" + id);
        defaultCreditCardTypeShouldNotBeFound("id.notEquals=" + id);

        defaultCreditCardTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCreditCardTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultCreditCardTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCreditCardTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCreditCardTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        creditCardTypeRepository.saveAndFlush(creditCardType);

        // Get all the creditCardTypeList where name equals to DEFAULT_NAME
        defaultCreditCardTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the creditCardTypeList where name equals to UPDATED_NAME
        defaultCreditCardTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCreditCardTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        creditCardTypeRepository.saveAndFlush(creditCardType);

        // Get all the creditCardTypeList where name not equals to DEFAULT_NAME
        defaultCreditCardTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the creditCardTypeList where name not equals to UPDATED_NAME
        defaultCreditCardTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCreditCardTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        creditCardTypeRepository.saveAndFlush(creditCardType);

        // Get all the creditCardTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCreditCardTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the creditCardTypeList where name equals to UPDATED_NAME
        defaultCreditCardTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCreditCardTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditCardTypeRepository.saveAndFlush(creditCardType);

        // Get all the creditCardTypeList where name is not null
        defaultCreditCardTypeShouldBeFound("name.specified=true");

        // Get all the creditCardTypeList where name is null
        defaultCreditCardTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCreditCardTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        creditCardTypeRepository.saveAndFlush(creditCardType);

        // Get all the creditCardTypeList where name contains DEFAULT_NAME
        defaultCreditCardTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the creditCardTypeList where name contains UPDATED_NAME
        defaultCreditCardTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCreditCardTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        creditCardTypeRepository.saveAndFlush(creditCardType);

        // Get all the creditCardTypeList where name does not contain DEFAULT_NAME
        defaultCreditCardTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the creditCardTypeList where name does not contain UPDATED_NAME
        defaultCreditCardTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCreditCardTypesByModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        creditCardTypeRepository.saveAndFlush(creditCardType);

        // Get all the creditCardTypeList where modifiedDate equals to DEFAULT_MODIFIED_DATE
        defaultCreditCardTypeShouldBeFound("modifiedDate.equals=" + DEFAULT_MODIFIED_DATE);

        // Get all the creditCardTypeList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultCreditCardTypeShouldNotBeFound("modifiedDate.equals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllCreditCardTypesByModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        creditCardTypeRepository.saveAndFlush(creditCardType);

        // Get all the creditCardTypeList where modifiedDate not equals to DEFAULT_MODIFIED_DATE
        defaultCreditCardTypeShouldNotBeFound("modifiedDate.notEquals=" + DEFAULT_MODIFIED_DATE);

        // Get all the creditCardTypeList where modifiedDate not equals to UPDATED_MODIFIED_DATE
        defaultCreditCardTypeShouldBeFound("modifiedDate.notEquals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllCreditCardTypesByModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        creditCardTypeRepository.saveAndFlush(creditCardType);

        // Get all the creditCardTypeList where modifiedDate in DEFAULT_MODIFIED_DATE or UPDATED_MODIFIED_DATE
        defaultCreditCardTypeShouldBeFound("modifiedDate.in=" + DEFAULT_MODIFIED_DATE + "," + UPDATED_MODIFIED_DATE);

        // Get all the creditCardTypeList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultCreditCardTypeShouldNotBeFound("modifiedDate.in=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllCreditCardTypesByModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditCardTypeRepository.saveAndFlush(creditCardType);

        // Get all the creditCardTypeList where modifiedDate is not null
        defaultCreditCardTypeShouldBeFound("modifiedDate.specified=true");

        // Get all the creditCardTypeList where modifiedDate is null
        defaultCreditCardTypeShouldNotBeFound("modifiedDate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCreditCardTypeShouldBeFound(String filter) throws Exception {
        restCreditCardTypeMockMvc.perform(get("/api/credit-card-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditCardType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restCreditCardTypeMockMvc.perform(get("/api/credit-card-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCreditCardTypeShouldNotBeFound(String filter) throws Exception {
        restCreditCardTypeMockMvc.perform(get("/api/credit-card-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCreditCardTypeMockMvc.perform(get("/api/credit-card-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCreditCardType() throws Exception {
        // Get the creditCardType
        restCreditCardTypeMockMvc.perform(get("/api/credit-card-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCreditCardType() throws Exception {
        // Initialize the database
        creditCardTypeRepository.saveAndFlush(creditCardType);

        int databaseSizeBeforeUpdate = creditCardTypeRepository.findAll().size();

        // Update the creditCardType
        CreditCardType updatedCreditCardType = creditCardTypeRepository.findById(creditCardType.getId()).get();
        // Disconnect from session so that the updates on updatedCreditCardType are not directly saved in db
        em.detach(updatedCreditCardType);
        updatedCreditCardType
            .name(UPDATED_NAME)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        CreditCardTypeDTO creditCardTypeDTO = creditCardTypeMapper.toDto(updatedCreditCardType);

        restCreditCardTypeMockMvc.perform(put("/api/credit-card-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(creditCardTypeDTO)))
            .andExpect(status().isOk());

        // Validate the CreditCardType in the database
        List<CreditCardType> creditCardTypeList = creditCardTypeRepository.findAll();
        assertThat(creditCardTypeList).hasSize(databaseSizeBeforeUpdate);
        CreditCardType testCreditCardType = creditCardTypeList.get(creditCardTypeList.size() - 1);
        assertThat(testCreditCardType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCreditCardType.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCreditCardType() throws Exception {
        int databaseSizeBeforeUpdate = creditCardTypeRepository.findAll().size();

        // Create the CreditCardType
        CreditCardTypeDTO creditCardTypeDTO = creditCardTypeMapper.toDto(creditCardType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditCardTypeMockMvc.perform(put("/api/credit-card-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(creditCardTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CreditCardType in the database
        List<CreditCardType> creditCardTypeList = creditCardTypeRepository.findAll();
        assertThat(creditCardTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCreditCardType() throws Exception {
        // Initialize the database
        creditCardTypeRepository.saveAndFlush(creditCardType);

        int databaseSizeBeforeDelete = creditCardTypeRepository.findAll().size();

        // Delete the creditCardType
        restCreditCardTypeMockMvc.perform(delete("/api/credit-card-types/{id}", creditCardType.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CreditCardType> creditCardTypeList = creditCardTypeRepository.findAll();
        assertThat(creditCardTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

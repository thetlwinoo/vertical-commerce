package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.PhoneNumberType;
import com.vertical.commerce.repository.PhoneNumberTypeRepository;
import com.vertical.commerce.service.PhoneNumberTypeService;
import com.vertical.commerce.service.dto.PhoneNumberTypeDTO;
import com.vertical.commerce.service.mapper.PhoneNumberTypeMapper;
import com.vertical.commerce.service.dto.PhoneNumberTypeCriteria;
import com.vertical.commerce.service.PhoneNumberTypeQueryService;

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
 * Integration tests for the {@link PhoneNumberTypeResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PhoneNumberTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PhoneNumberTypeRepository phoneNumberTypeRepository;

    @Autowired
    private PhoneNumberTypeMapper phoneNumberTypeMapper;

    @Autowired
    private PhoneNumberTypeService phoneNumberTypeService;

    @Autowired
    private PhoneNumberTypeQueryService phoneNumberTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhoneNumberTypeMockMvc;

    private PhoneNumberType phoneNumberType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhoneNumberType createEntity(EntityManager em) {
        PhoneNumberType phoneNumberType = new PhoneNumberType()
            .name(DEFAULT_NAME);
        return phoneNumberType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhoneNumberType createUpdatedEntity(EntityManager em) {
        PhoneNumberType phoneNumberType = new PhoneNumberType()
            .name(UPDATED_NAME);
        return phoneNumberType;
    }

    @BeforeEach
    public void initTest() {
        phoneNumberType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPhoneNumberType() throws Exception {
        int databaseSizeBeforeCreate = phoneNumberTypeRepository.findAll().size();
        // Create the PhoneNumberType
        PhoneNumberTypeDTO phoneNumberTypeDTO = phoneNumberTypeMapper.toDto(phoneNumberType);
        restPhoneNumberTypeMockMvc.perform(post("/api/phone-number-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumberTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the PhoneNumberType in the database
        List<PhoneNumberType> phoneNumberTypeList = phoneNumberTypeRepository.findAll();
        assertThat(phoneNumberTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PhoneNumberType testPhoneNumberType = phoneNumberTypeList.get(phoneNumberTypeList.size() - 1);
        assertThat(testPhoneNumberType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPhoneNumberTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = phoneNumberTypeRepository.findAll().size();

        // Create the PhoneNumberType with an existing ID
        phoneNumberType.setId(1L);
        PhoneNumberTypeDTO phoneNumberTypeDTO = phoneNumberTypeMapper.toDto(phoneNumberType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhoneNumberTypeMockMvc.perform(post("/api/phone-number-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumberTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PhoneNumberType in the database
        List<PhoneNumberType> phoneNumberTypeList = phoneNumberTypeRepository.findAll();
        assertThat(phoneNumberTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = phoneNumberTypeRepository.findAll().size();
        // set the field null
        phoneNumberType.setName(null);

        // Create the PhoneNumberType, which fails.
        PhoneNumberTypeDTO phoneNumberTypeDTO = phoneNumberTypeMapper.toDto(phoneNumberType);


        restPhoneNumberTypeMockMvc.perform(post("/api/phone-number-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumberTypeDTO)))
            .andExpect(status().isBadRequest());

        List<PhoneNumberType> phoneNumberTypeList = phoneNumberTypeRepository.findAll();
        assertThat(phoneNumberTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPhoneNumberTypes() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        // Get all the phoneNumberTypeList
        restPhoneNumberTypeMockMvc.perform(get("/api/phone-number-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phoneNumberType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getPhoneNumberType() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        // Get the phoneNumberType
        restPhoneNumberTypeMockMvc.perform(get("/api/phone-number-types/{id}", phoneNumberType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phoneNumberType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getPhoneNumberTypesByIdFiltering() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        Long id = phoneNumberType.getId();

        defaultPhoneNumberTypeShouldBeFound("id.equals=" + id);
        defaultPhoneNumberTypeShouldNotBeFound("id.notEquals=" + id);

        defaultPhoneNumberTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPhoneNumberTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultPhoneNumberTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPhoneNumberTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPhoneNumberTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        // Get all the phoneNumberTypeList where name equals to DEFAULT_NAME
        defaultPhoneNumberTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the phoneNumberTypeList where name equals to UPDATED_NAME
        defaultPhoneNumberTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPhoneNumberTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        // Get all the phoneNumberTypeList where name not equals to DEFAULT_NAME
        defaultPhoneNumberTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the phoneNumberTypeList where name not equals to UPDATED_NAME
        defaultPhoneNumberTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPhoneNumberTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        // Get all the phoneNumberTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPhoneNumberTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the phoneNumberTypeList where name equals to UPDATED_NAME
        defaultPhoneNumberTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPhoneNumberTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        // Get all the phoneNumberTypeList where name is not null
        defaultPhoneNumberTypeShouldBeFound("name.specified=true");

        // Get all the phoneNumberTypeList where name is null
        defaultPhoneNumberTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhoneNumberTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        // Get all the phoneNumberTypeList where name contains DEFAULT_NAME
        defaultPhoneNumberTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the phoneNumberTypeList where name contains UPDATED_NAME
        defaultPhoneNumberTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPhoneNumberTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        // Get all the phoneNumberTypeList where name does not contain DEFAULT_NAME
        defaultPhoneNumberTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the phoneNumberTypeList where name does not contain UPDATED_NAME
        defaultPhoneNumberTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPhoneNumberTypeShouldBeFound(String filter) throws Exception {
        restPhoneNumberTypeMockMvc.perform(get("/api/phone-number-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phoneNumberType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restPhoneNumberTypeMockMvc.perform(get("/api/phone-number-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPhoneNumberTypeShouldNotBeFound(String filter) throws Exception {
        restPhoneNumberTypeMockMvc.perform(get("/api/phone-number-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPhoneNumberTypeMockMvc.perform(get("/api/phone-number-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPhoneNumberType() throws Exception {
        // Get the phoneNumberType
        restPhoneNumberTypeMockMvc.perform(get("/api/phone-number-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhoneNumberType() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        int databaseSizeBeforeUpdate = phoneNumberTypeRepository.findAll().size();

        // Update the phoneNumberType
        PhoneNumberType updatedPhoneNumberType = phoneNumberTypeRepository.findById(phoneNumberType.getId()).get();
        // Disconnect from session so that the updates on updatedPhoneNumberType are not directly saved in db
        em.detach(updatedPhoneNumberType);
        updatedPhoneNumberType
            .name(UPDATED_NAME);
        PhoneNumberTypeDTO phoneNumberTypeDTO = phoneNumberTypeMapper.toDto(updatedPhoneNumberType);

        restPhoneNumberTypeMockMvc.perform(put("/api/phone-number-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumberTypeDTO)))
            .andExpect(status().isOk());

        // Validate the PhoneNumberType in the database
        List<PhoneNumberType> phoneNumberTypeList = phoneNumberTypeRepository.findAll();
        assertThat(phoneNumberTypeList).hasSize(databaseSizeBeforeUpdate);
        PhoneNumberType testPhoneNumberType = phoneNumberTypeList.get(phoneNumberTypeList.size() - 1);
        assertThat(testPhoneNumberType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPhoneNumberType() throws Exception {
        int databaseSizeBeforeUpdate = phoneNumberTypeRepository.findAll().size();

        // Create the PhoneNumberType
        PhoneNumberTypeDTO phoneNumberTypeDTO = phoneNumberTypeMapper.toDto(phoneNumberType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhoneNumberTypeMockMvc.perform(put("/api/phone-number-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumberTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PhoneNumberType in the database
        List<PhoneNumberType> phoneNumberTypeList = phoneNumberTypeRepository.findAll();
        assertThat(phoneNumberTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePhoneNumberType() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        int databaseSizeBeforeDelete = phoneNumberTypeRepository.findAll().size();

        // Delete the phoneNumberType
        restPhoneNumberTypeMockMvc.perform(delete("/api/phone-number-types/{id}", phoneNumberType.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PhoneNumberType> phoneNumberTypeList = phoneNumberTypeRepository.findAll();
        assertThat(phoneNumberTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

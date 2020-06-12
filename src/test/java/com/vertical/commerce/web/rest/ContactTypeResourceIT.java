package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.ContactType;
import com.vertical.commerce.repository.ContactTypeRepository;
import com.vertical.commerce.service.ContactTypeService;
import com.vertical.commerce.service.dto.ContactTypeDTO;
import com.vertical.commerce.service.mapper.ContactTypeMapper;
import com.vertical.commerce.service.dto.ContactTypeCriteria;
import com.vertical.commerce.service.ContactTypeQueryService;

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
 * Integration tests for the {@link ContactTypeResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ContactTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ContactTypeRepository contactTypeRepository;

    @Autowired
    private ContactTypeMapper contactTypeMapper;

    @Autowired
    private ContactTypeService contactTypeService;

    @Autowired
    private ContactTypeQueryService contactTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactTypeMockMvc;

    private ContactType contactType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactType createEntity(EntityManager em) {
        ContactType contactType = new ContactType()
            .name(DEFAULT_NAME);
        return contactType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactType createUpdatedEntity(EntityManager em) {
        ContactType contactType = new ContactType()
            .name(UPDATED_NAME);
        return contactType;
    }

    @BeforeEach
    public void initTest() {
        contactType = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactType() throws Exception {
        int databaseSizeBeforeCreate = contactTypeRepository.findAll().size();
        // Create the ContactType
        ContactTypeDTO contactTypeDTO = contactTypeMapper.toDto(contactType);
        restContactTypeMockMvc.perform(post("/api/contact-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ContactType in the database
        List<ContactType> contactTypeList = contactTypeRepository.findAll();
        assertThat(contactTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ContactType testContactType = contactTypeList.get(contactTypeList.size() - 1);
        assertThat(testContactType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createContactTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactTypeRepository.findAll().size();

        // Create the ContactType with an existing ID
        contactType.setId(1L);
        ContactTypeDTO contactTypeDTO = contactTypeMapper.toDto(contactType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactTypeMockMvc.perform(post("/api/contact-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContactType in the database
        List<ContactType> contactTypeList = contactTypeRepository.findAll();
        assertThat(contactTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactTypeRepository.findAll().size();
        // set the field null
        contactType.setName(null);

        // Create the ContactType, which fails.
        ContactTypeDTO contactTypeDTO = contactTypeMapper.toDto(contactType);


        restContactTypeMockMvc.perform(post("/api/contact-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ContactType> contactTypeList = contactTypeRepository.findAll();
        assertThat(contactTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContactTypes() throws Exception {
        // Initialize the database
        contactTypeRepository.saveAndFlush(contactType);

        // Get all the contactTypeList
        restContactTypeMockMvc.perform(get("/api/contact-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getContactType() throws Exception {
        // Initialize the database
        contactTypeRepository.saveAndFlush(contactType);

        // Get the contactType
        restContactTypeMockMvc.perform(get("/api/contact-types/{id}", contactType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contactType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getContactTypesByIdFiltering() throws Exception {
        // Initialize the database
        contactTypeRepository.saveAndFlush(contactType);

        Long id = contactType.getId();

        defaultContactTypeShouldBeFound("id.equals=" + id);
        defaultContactTypeShouldNotBeFound("id.notEquals=" + id);

        defaultContactTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultContactTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultContactTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultContactTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllContactTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        contactTypeRepository.saveAndFlush(contactType);

        // Get all the contactTypeList where name equals to DEFAULT_NAME
        defaultContactTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the contactTypeList where name equals to UPDATED_NAME
        defaultContactTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllContactTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contactTypeRepository.saveAndFlush(contactType);

        // Get all the contactTypeList where name not equals to DEFAULT_NAME
        defaultContactTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the contactTypeList where name not equals to UPDATED_NAME
        defaultContactTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllContactTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        contactTypeRepository.saveAndFlush(contactType);

        // Get all the contactTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultContactTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the contactTypeList where name equals to UPDATED_NAME
        defaultContactTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllContactTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactTypeRepository.saveAndFlush(contactType);

        // Get all the contactTypeList where name is not null
        defaultContactTypeShouldBeFound("name.specified=true");

        // Get all the contactTypeList where name is null
        defaultContactTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllContactTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        contactTypeRepository.saveAndFlush(contactType);

        // Get all the contactTypeList where name contains DEFAULT_NAME
        defaultContactTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the contactTypeList where name contains UPDATED_NAME
        defaultContactTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllContactTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        contactTypeRepository.saveAndFlush(contactType);

        // Get all the contactTypeList where name does not contain DEFAULT_NAME
        defaultContactTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the contactTypeList where name does not contain UPDATED_NAME
        defaultContactTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContactTypeShouldBeFound(String filter) throws Exception {
        restContactTypeMockMvc.perform(get("/api/contact-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restContactTypeMockMvc.perform(get("/api/contact-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContactTypeShouldNotBeFound(String filter) throws Exception {
        restContactTypeMockMvc.perform(get("/api/contact-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContactTypeMockMvc.perform(get("/api/contact-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingContactType() throws Exception {
        // Get the contactType
        restContactTypeMockMvc.perform(get("/api/contact-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactType() throws Exception {
        // Initialize the database
        contactTypeRepository.saveAndFlush(contactType);

        int databaseSizeBeforeUpdate = contactTypeRepository.findAll().size();

        // Update the contactType
        ContactType updatedContactType = contactTypeRepository.findById(contactType.getId()).get();
        // Disconnect from session so that the updates on updatedContactType are not directly saved in db
        em.detach(updatedContactType);
        updatedContactType
            .name(UPDATED_NAME);
        ContactTypeDTO contactTypeDTO = contactTypeMapper.toDto(updatedContactType);

        restContactTypeMockMvc.perform(put("/api/contact-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ContactType in the database
        List<ContactType> contactTypeList = contactTypeRepository.findAll();
        assertThat(contactTypeList).hasSize(databaseSizeBeforeUpdate);
        ContactType testContactType = contactTypeList.get(contactTypeList.size() - 1);
        assertThat(testContactType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingContactType() throws Exception {
        int databaseSizeBeforeUpdate = contactTypeRepository.findAll().size();

        // Create the ContactType
        ContactTypeDTO contactTypeDTO = contactTypeMapper.toDto(contactType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactTypeMockMvc.perform(put("/api/contact-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContactType in the database
        List<ContactType> contactTypeList = contactTypeRepository.findAll();
        assertThat(contactTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContactType() throws Exception {
        // Initialize the database
        contactTypeRepository.saveAndFlush(contactType);

        int databaseSizeBeforeDelete = contactTypeRepository.findAll().size();

        // Delete the contactType
        restContactTypeMockMvc.perform(delete("/api/contact-types/{id}", contactType.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactType> contactTypeList = contactTypeRepository.findAll();
        assertThat(contactTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

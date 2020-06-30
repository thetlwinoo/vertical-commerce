package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.DiscountTypes;
import com.vertical.commerce.repository.DiscountTypesRepository;
import com.vertical.commerce.service.DiscountTypesService;
import com.vertical.commerce.service.dto.DiscountTypesDTO;
import com.vertical.commerce.service.mapper.DiscountTypesMapper;
import com.vertical.commerce.service.dto.DiscountTypesCriteria;
import com.vertical.commerce.service.DiscountTypesQueryService;

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
 * Integration tests for the {@link DiscountTypesResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class DiscountTypesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DiscountTypesRepository discountTypesRepository;

    @Autowired
    private DiscountTypesMapper discountTypesMapper;

    @Autowired
    private DiscountTypesService discountTypesService;

    @Autowired
    private DiscountTypesQueryService discountTypesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiscountTypesMockMvc;

    private DiscountTypes discountTypes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiscountTypes createEntity(EntityManager em) {
        DiscountTypes discountTypes = new DiscountTypes()
            .name(DEFAULT_NAME)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return discountTypes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiscountTypes createUpdatedEntity(EntityManager em) {
        DiscountTypes discountTypes = new DiscountTypes()
            .name(UPDATED_NAME)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return discountTypes;
    }

    @BeforeEach
    public void initTest() {
        discountTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiscountTypes() throws Exception {
        int databaseSizeBeforeCreate = discountTypesRepository.findAll().size();
        // Create the DiscountTypes
        DiscountTypesDTO discountTypesDTO = discountTypesMapper.toDto(discountTypes);
        restDiscountTypesMockMvc.perform(post("/api/discount-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the DiscountTypes in the database
        List<DiscountTypes> discountTypesList = discountTypesRepository.findAll();
        assertThat(discountTypesList).hasSize(databaseSizeBeforeCreate + 1);
        DiscountTypes testDiscountTypes = discountTypesList.get(discountTypesList.size() - 1);
        assertThat(testDiscountTypes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDiscountTypes.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createDiscountTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = discountTypesRepository.findAll().size();

        // Create the DiscountTypes with an existing ID
        discountTypes.setId(1L);
        DiscountTypesDTO discountTypesDTO = discountTypesMapper.toDto(discountTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiscountTypesMockMvc.perform(post("/api/discount-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DiscountTypes in the database
        List<DiscountTypes> discountTypesList = discountTypesRepository.findAll();
        assertThat(discountTypesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountTypesRepository.findAll().size();
        // set the field null
        discountTypes.setName(null);

        // Create the DiscountTypes, which fails.
        DiscountTypesDTO discountTypesDTO = discountTypesMapper.toDto(discountTypes);


        restDiscountTypesMockMvc.perform(post("/api/discount-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountTypesDTO)))
            .andExpect(status().isBadRequest());

        List<DiscountTypes> discountTypesList = discountTypesRepository.findAll();
        assertThat(discountTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModifiedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountTypesRepository.findAll().size();
        // set the field null
        discountTypes.setModifiedDate(null);

        // Create the DiscountTypes, which fails.
        DiscountTypesDTO discountTypesDTO = discountTypesMapper.toDto(discountTypes);


        restDiscountTypesMockMvc.perform(post("/api/discount-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountTypesDTO)))
            .andExpect(status().isBadRequest());

        List<DiscountTypes> discountTypesList = discountTypesRepository.findAll();
        assertThat(discountTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDiscountTypes() throws Exception {
        // Initialize the database
        discountTypesRepository.saveAndFlush(discountTypes);

        // Get all the discountTypesList
        restDiscountTypesMockMvc.perform(get("/api/discount-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discountTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getDiscountTypes() throws Exception {
        // Initialize the database
        discountTypesRepository.saveAndFlush(discountTypes);

        // Get the discountTypes
        restDiscountTypesMockMvc.perform(get("/api/discount-types/{id}", discountTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(discountTypes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getDiscountTypesByIdFiltering() throws Exception {
        // Initialize the database
        discountTypesRepository.saveAndFlush(discountTypes);

        Long id = discountTypes.getId();

        defaultDiscountTypesShouldBeFound("id.equals=" + id);
        defaultDiscountTypesShouldNotBeFound("id.notEquals=" + id);

        defaultDiscountTypesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDiscountTypesShouldNotBeFound("id.greaterThan=" + id);

        defaultDiscountTypesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDiscountTypesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDiscountTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        discountTypesRepository.saveAndFlush(discountTypes);

        // Get all the discountTypesList where name equals to DEFAULT_NAME
        defaultDiscountTypesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the discountTypesList where name equals to UPDATED_NAME
        defaultDiscountTypesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDiscountTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        discountTypesRepository.saveAndFlush(discountTypes);

        // Get all the discountTypesList where name not equals to DEFAULT_NAME
        defaultDiscountTypesShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the discountTypesList where name not equals to UPDATED_NAME
        defaultDiscountTypesShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDiscountTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        discountTypesRepository.saveAndFlush(discountTypes);

        // Get all the discountTypesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDiscountTypesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the discountTypesList where name equals to UPDATED_NAME
        defaultDiscountTypesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDiscountTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        discountTypesRepository.saveAndFlush(discountTypes);

        // Get all the discountTypesList where name is not null
        defaultDiscountTypesShouldBeFound("name.specified=true");

        // Get all the discountTypesList where name is null
        defaultDiscountTypesShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDiscountTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        discountTypesRepository.saveAndFlush(discountTypes);

        // Get all the discountTypesList where name contains DEFAULT_NAME
        defaultDiscountTypesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the discountTypesList where name contains UPDATED_NAME
        defaultDiscountTypesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDiscountTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        discountTypesRepository.saveAndFlush(discountTypes);

        // Get all the discountTypesList where name does not contain DEFAULT_NAME
        defaultDiscountTypesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the discountTypesList where name does not contain UPDATED_NAME
        defaultDiscountTypesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDiscountTypesByModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        discountTypesRepository.saveAndFlush(discountTypes);

        // Get all the discountTypesList where modifiedDate equals to DEFAULT_MODIFIED_DATE
        defaultDiscountTypesShouldBeFound("modifiedDate.equals=" + DEFAULT_MODIFIED_DATE);

        // Get all the discountTypesList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultDiscountTypesShouldNotBeFound("modifiedDate.equals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllDiscountTypesByModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        discountTypesRepository.saveAndFlush(discountTypes);

        // Get all the discountTypesList where modifiedDate not equals to DEFAULT_MODIFIED_DATE
        defaultDiscountTypesShouldNotBeFound("modifiedDate.notEquals=" + DEFAULT_MODIFIED_DATE);

        // Get all the discountTypesList where modifiedDate not equals to UPDATED_MODIFIED_DATE
        defaultDiscountTypesShouldBeFound("modifiedDate.notEquals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllDiscountTypesByModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        discountTypesRepository.saveAndFlush(discountTypes);

        // Get all the discountTypesList where modifiedDate in DEFAULT_MODIFIED_DATE or UPDATED_MODIFIED_DATE
        defaultDiscountTypesShouldBeFound("modifiedDate.in=" + DEFAULT_MODIFIED_DATE + "," + UPDATED_MODIFIED_DATE);

        // Get all the discountTypesList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultDiscountTypesShouldNotBeFound("modifiedDate.in=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllDiscountTypesByModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        discountTypesRepository.saveAndFlush(discountTypes);

        // Get all the discountTypesList where modifiedDate is not null
        defaultDiscountTypesShouldBeFound("modifiedDate.specified=true");

        // Get all the discountTypesList where modifiedDate is null
        defaultDiscountTypesShouldNotBeFound("modifiedDate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDiscountTypesShouldBeFound(String filter) throws Exception {
        restDiscountTypesMockMvc.perform(get("/api/discount-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discountTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restDiscountTypesMockMvc.perform(get("/api/discount-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDiscountTypesShouldNotBeFound(String filter) throws Exception {
        restDiscountTypesMockMvc.perform(get("/api/discount-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDiscountTypesMockMvc.perform(get("/api/discount-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDiscountTypes() throws Exception {
        // Get the discountTypes
        restDiscountTypesMockMvc.perform(get("/api/discount-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiscountTypes() throws Exception {
        // Initialize the database
        discountTypesRepository.saveAndFlush(discountTypes);

        int databaseSizeBeforeUpdate = discountTypesRepository.findAll().size();

        // Update the discountTypes
        DiscountTypes updatedDiscountTypes = discountTypesRepository.findById(discountTypes.getId()).get();
        // Disconnect from session so that the updates on updatedDiscountTypes are not directly saved in db
        em.detach(updatedDiscountTypes);
        updatedDiscountTypes
            .name(UPDATED_NAME)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        DiscountTypesDTO discountTypesDTO = discountTypesMapper.toDto(updatedDiscountTypes);

        restDiscountTypesMockMvc.perform(put("/api/discount-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountTypesDTO)))
            .andExpect(status().isOk());

        // Validate the DiscountTypes in the database
        List<DiscountTypes> discountTypesList = discountTypesRepository.findAll();
        assertThat(discountTypesList).hasSize(databaseSizeBeforeUpdate);
        DiscountTypes testDiscountTypes = discountTypesList.get(discountTypesList.size() - 1);
        assertThat(testDiscountTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDiscountTypes.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDiscountTypes() throws Exception {
        int databaseSizeBeforeUpdate = discountTypesRepository.findAll().size();

        // Create the DiscountTypes
        DiscountTypesDTO discountTypesDTO = discountTypesMapper.toDto(discountTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiscountTypesMockMvc.perform(put("/api/discount-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DiscountTypes in the database
        List<DiscountTypes> discountTypesList = discountTypesRepository.findAll();
        assertThat(discountTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiscountTypes() throws Exception {
        // Initialize the database
        discountTypesRepository.saveAndFlush(discountTypes);

        int databaseSizeBeforeDelete = discountTypesRepository.findAll().size();

        // Delete the discountTypes
        restDiscountTypesMockMvc.perform(delete("/api/discount-types/{id}", discountTypes.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DiscountTypes> discountTypesList = discountTypesRepository.findAll();
        assertThat(discountTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

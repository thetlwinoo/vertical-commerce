package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.CustomerCategories;
import com.vertical.commerce.repository.CustomerCategoriesRepository;
import com.vertical.commerce.service.CustomerCategoriesService;
import com.vertical.commerce.service.dto.CustomerCategoriesDTO;
import com.vertical.commerce.service.mapper.CustomerCategoriesMapper;
import com.vertical.commerce.service.dto.CustomerCategoriesCriteria;
import com.vertical.commerce.service.CustomerCategoriesQueryService;

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
 * Integration tests for the {@link CustomerCategoriesResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CustomerCategoriesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CustomerCategoriesRepository customerCategoriesRepository;

    @Autowired
    private CustomerCategoriesMapper customerCategoriesMapper;

    @Autowired
    private CustomerCategoriesService customerCategoriesService;

    @Autowired
    private CustomerCategoriesQueryService customerCategoriesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerCategoriesMockMvc;

    private CustomerCategories customerCategories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerCategories createEntity(EntityManager em) {
        CustomerCategories customerCategories = new CustomerCategories()
            .name(DEFAULT_NAME)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return customerCategories;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerCategories createUpdatedEntity(EntityManager em) {
        CustomerCategories customerCategories = new CustomerCategories()
            .name(UPDATED_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return customerCategories;
    }

    @BeforeEach
    public void initTest() {
        customerCategories = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerCategories() throws Exception {
        int databaseSizeBeforeCreate = customerCategoriesRepository.findAll().size();
        // Create the CustomerCategories
        CustomerCategoriesDTO customerCategoriesDTO = customerCategoriesMapper.toDto(customerCategories);
        restCustomerCategoriesMockMvc.perform(post("/api/customer-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerCategoriesDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerCategories in the database
        List<CustomerCategories> customerCategoriesList = customerCategoriesRepository.findAll();
        assertThat(customerCategoriesList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerCategories testCustomerCategories = customerCategoriesList.get(customerCategoriesList.size() - 1);
        assertThat(testCustomerCategories.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomerCategories.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testCustomerCategories.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createCustomerCategoriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerCategoriesRepository.findAll().size();

        // Create the CustomerCategories with an existing ID
        customerCategories.setId(1L);
        CustomerCategoriesDTO customerCategoriesDTO = customerCategoriesMapper.toDto(customerCategories);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerCategoriesMockMvc.perform(post("/api/customer-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerCategoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerCategories in the database
        List<CustomerCategories> customerCategoriesList = customerCategoriesRepository.findAll();
        assertThat(customerCategoriesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerCategoriesRepository.findAll().size();
        // set the field null
        customerCategories.setValidFrom(null);

        // Create the CustomerCategories, which fails.
        CustomerCategoriesDTO customerCategoriesDTO = customerCategoriesMapper.toDto(customerCategories);


        restCustomerCategoriesMockMvc.perform(post("/api/customer-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerCategoriesDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerCategories> customerCategoriesList = customerCategoriesRepository.findAll();
        assertThat(customerCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerCategoriesRepository.findAll().size();
        // set the field null
        customerCategories.setValidTo(null);

        // Create the CustomerCategories, which fails.
        CustomerCategoriesDTO customerCategoriesDTO = customerCategoriesMapper.toDto(customerCategories);


        restCustomerCategoriesMockMvc.perform(post("/api/customer-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerCategoriesDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerCategories> customerCategoriesList = customerCategoriesRepository.findAll();
        assertThat(customerCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerCategories() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList
        restCustomerCategoriesMockMvc.perform(get("/api/customer-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerCategories.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getCustomerCategories() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get the customerCategories
        restCustomerCategoriesMockMvc.perform(get("/api/customer-categories/{id}", customerCategories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerCategories.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getCustomerCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        Long id = customerCategories.getId();

        defaultCustomerCategoriesShouldBeFound("id.equals=" + id);
        defaultCustomerCategoriesShouldNotBeFound("id.notEquals=" + id);

        defaultCustomerCategoriesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomerCategoriesShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomerCategoriesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomerCategoriesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCustomerCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where name equals to DEFAULT_NAME
        defaultCustomerCategoriesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the customerCategoriesList where name equals to UPDATED_NAME
        defaultCustomerCategoriesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where name not equals to DEFAULT_NAME
        defaultCustomerCategoriesShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the customerCategoriesList where name not equals to UPDATED_NAME
        defaultCustomerCategoriesShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCustomerCategoriesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the customerCategoriesList where name equals to UPDATED_NAME
        defaultCustomerCategoriesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where name is not null
        defaultCustomerCategoriesShouldBeFound("name.specified=true");

        // Get all the customerCategoriesList where name is null
        defaultCustomerCategoriesShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomerCategoriesByNameContainsSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where name contains DEFAULT_NAME
        defaultCustomerCategoriesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the customerCategoriesList where name contains UPDATED_NAME
        defaultCustomerCategoriesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where name does not contain DEFAULT_NAME
        defaultCustomerCategoriesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the customerCategoriesList where name does not contain UPDATED_NAME
        defaultCustomerCategoriesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validFrom equals to DEFAULT_VALID_FROM
        defaultCustomerCategoriesShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the customerCategoriesList where validFrom equals to UPDATED_VALID_FROM
        defaultCustomerCategoriesShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validFrom not equals to DEFAULT_VALID_FROM
        defaultCustomerCategoriesShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the customerCategoriesList where validFrom not equals to UPDATED_VALID_FROM
        defaultCustomerCategoriesShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultCustomerCategoriesShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the customerCategoriesList where validFrom equals to UPDATED_VALID_FROM
        defaultCustomerCategoriesShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validFrom is not null
        defaultCustomerCategoriesShouldBeFound("validFrom.specified=true");

        // Get all the customerCategoriesList where validFrom is null
        defaultCustomerCategoriesShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validTo equals to DEFAULT_VALID_TO
        defaultCustomerCategoriesShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the customerCategoriesList where validTo equals to UPDATED_VALID_TO
        defaultCustomerCategoriesShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validTo not equals to DEFAULT_VALID_TO
        defaultCustomerCategoriesShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the customerCategoriesList where validTo not equals to UPDATED_VALID_TO
        defaultCustomerCategoriesShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultCustomerCategoriesShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the customerCategoriesList where validTo equals to UPDATED_VALID_TO
        defaultCustomerCategoriesShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validTo is not null
        defaultCustomerCategoriesShouldBeFound("validTo.specified=true");

        // Get all the customerCategoriesList where validTo is null
        defaultCustomerCategoriesShouldNotBeFound("validTo.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomerCategoriesShouldBeFound(String filter) throws Exception {
        restCustomerCategoriesMockMvc.perform(get("/api/customer-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerCategories.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restCustomerCategoriesMockMvc.perform(get("/api/customer-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomerCategoriesShouldNotBeFound(String filter) throws Exception {
        restCustomerCategoriesMockMvc.perform(get("/api/customer-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomerCategoriesMockMvc.perform(get("/api/customer-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerCategories() throws Exception {
        // Get the customerCategories
        restCustomerCategoriesMockMvc.perform(get("/api/customer-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerCategories() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        int databaseSizeBeforeUpdate = customerCategoriesRepository.findAll().size();

        // Update the customerCategories
        CustomerCategories updatedCustomerCategories = customerCategoriesRepository.findById(customerCategories.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerCategories are not directly saved in db
        em.detach(updatedCustomerCategories);
        updatedCustomerCategories
            .name(UPDATED_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        CustomerCategoriesDTO customerCategoriesDTO = customerCategoriesMapper.toDto(updatedCustomerCategories);

        restCustomerCategoriesMockMvc.perform(put("/api/customer-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerCategoriesDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerCategories in the database
        List<CustomerCategories> customerCategoriesList = customerCategoriesRepository.findAll();
        assertThat(customerCategoriesList).hasSize(databaseSizeBeforeUpdate);
        CustomerCategories testCustomerCategories = customerCategoriesList.get(customerCategoriesList.size() - 1);
        assertThat(testCustomerCategories.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerCategories.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCustomerCategories.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerCategories() throws Exception {
        int databaseSizeBeforeUpdate = customerCategoriesRepository.findAll().size();

        // Create the CustomerCategories
        CustomerCategoriesDTO customerCategoriesDTO = customerCategoriesMapper.toDto(customerCategories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerCategoriesMockMvc.perform(put("/api/customer-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerCategoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerCategories in the database
        List<CustomerCategories> customerCategoriesList = customerCategoriesRepository.findAll();
        assertThat(customerCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerCategories() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        int databaseSizeBeforeDelete = customerCategoriesRepository.findAll().size();

        // Delete the customerCategories
        restCustomerCategoriesMockMvc.perform(delete("/api/customer-categories/{id}", customerCategories.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerCategories> customerCategoriesList = customerCategoriesRepository.findAll();
        assertThat(customerCategoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

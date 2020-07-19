package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.Discounts;
import com.vertical.commerce.domain.Suppliers;
import com.vertical.commerce.domain.DiscountTypes;
import com.vertical.commerce.repository.DiscountsRepository;
import com.vertical.commerce.service.DiscountsService;
import com.vertical.commerce.service.dto.DiscountsDTO;
import com.vertical.commerce.service.mapper.DiscountsMapper;
import com.vertical.commerce.service.dto.DiscountsCriteria;
import com.vertical.commerce.service.DiscountsQueryService;

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
 * Integration tests for the {@link DiscountsResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class DiscountsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DISCOUNT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DiscountsRepository discountsRepository;

    @Autowired
    private DiscountsMapper discountsMapper;

    @Autowired
    private DiscountsService discountsService;

    @Autowired
    private DiscountsQueryService discountsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiscountsMockMvc;

    private Discounts discounts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Discounts createEntity(EntityManager em) {
        Discounts discounts = new Discounts()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .discountCode(DEFAULT_DISCOUNT_CODE)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return discounts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Discounts createUpdatedEntity(EntityManager em) {
        Discounts discounts = new Discounts()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .discountCode(UPDATED_DISCOUNT_CODE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return discounts;
    }

    @BeforeEach
    public void initTest() {
        discounts = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiscounts() throws Exception {
        int databaseSizeBeforeCreate = discountsRepository.findAll().size();
        // Create the Discounts
        DiscountsDTO discountsDTO = discountsMapper.toDto(discounts);
        restDiscountsMockMvc.perform(post("/api/discounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountsDTO)))
            .andExpect(status().isCreated());

        // Validate the Discounts in the database
        List<Discounts> discountsList = discountsRepository.findAll();
        assertThat(discountsList).hasSize(databaseSizeBeforeCreate + 1);
        Discounts testDiscounts = discountsList.get(discountsList.size() - 1);
        assertThat(testDiscounts.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDiscounts.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDiscounts.getDiscountCode()).isEqualTo(DEFAULT_DISCOUNT_CODE);
        assertThat(testDiscounts.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testDiscounts.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createDiscountsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = discountsRepository.findAll().size();

        // Create the Discounts with an existing ID
        discounts.setId(1L);
        DiscountsDTO discountsDTO = discountsMapper.toDto(discounts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiscountsMockMvc.perform(post("/api/discounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Discounts in the database
        List<Discounts> discountsList = discountsRepository.findAll();
        assertThat(discountsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountsRepository.findAll().size();
        // set the field null
        discounts.setName(null);

        // Create the Discounts, which fails.
        DiscountsDTO discountsDTO = discountsMapper.toDto(discounts);


        restDiscountsMockMvc.perform(post("/api/discounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountsDTO)))
            .andExpect(status().isBadRequest());

        List<Discounts> discountsList = discountsRepository.findAll();
        assertThat(discountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountsRepository.findAll().size();
        // set the field null
        discounts.setValidFrom(null);

        // Create the Discounts, which fails.
        DiscountsDTO discountsDTO = discountsMapper.toDto(discounts);


        restDiscountsMockMvc.perform(post("/api/discounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountsDTO)))
            .andExpect(status().isBadRequest());

        List<Discounts> discountsList = discountsRepository.findAll();
        assertThat(discountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDiscounts() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList
        restDiscountsMockMvc.perform(get("/api/discounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discounts.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].discountCode").value(hasItem(DEFAULT_DISCOUNT_CODE)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getDiscounts() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get the discounts
        restDiscountsMockMvc.perform(get("/api/discounts/{id}", discounts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(discounts.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.discountCode").value(DEFAULT_DISCOUNT_CODE))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getDiscountsByIdFiltering() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        Long id = discounts.getId();

        defaultDiscountsShouldBeFound("id.equals=" + id);
        defaultDiscountsShouldNotBeFound("id.notEquals=" + id);

        defaultDiscountsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDiscountsShouldNotBeFound("id.greaterThan=" + id);

        defaultDiscountsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDiscountsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDiscountsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where name equals to DEFAULT_NAME
        defaultDiscountsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the discountsList where name equals to UPDATED_NAME
        defaultDiscountsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDiscountsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where name not equals to DEFAULT_NAME
        defaultDiscountsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the discountsList where name not equals to UPDATED_NAME
        defaultDiscountsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDiscountsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDiscountsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the discountsList where name equals to UPDATED_NAME
        defaultDiscountsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDiscountsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where name is not null
        defaultDiscountsShouldBeFound("name.specified=true");

        // Get all the discountsList where name is null
        defaultDiscountsShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDiscountsByNameContainsSomething() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where name contains DEFAULT_NAME
        defaultDiscountsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the discountsList where name contains UPDATED_NAME
        defaultDiscountsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDiscountsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where name does not contain DEFAULT_NAME
        defaultDiscountsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the discountsList where name does not contain UPDATED_NAME
        defaultDiscountsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDiscountsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where description equals to DEFAULT_DESCRIPTION
        defaultDiscountsShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the discountsList where description equals to UPDATED_DESCRIPTION
        defaultDiscountsShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDiscountsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where description not equals to DEFAULT_DESCRIPTION
        defaultDiscountsShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the discountsList where description not equals to UPDATED_DESCRIPTION
        defaultDiscountsShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDiscountsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultDiscountsShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the discountsList where description equals to UPDATED_DESCRIPTION
        defaultDiscountsShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDiscountsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where description is not null
        defaultDiscountsShouldBeFound("description.specified=true");

        // Get all the discountsList where description is null
        defaultDiscountsShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllDiscountsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where description contains DEFAULT_DESCRIPTION
        defaultDiscountsShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the discountsList where description contains UPDATED_DESCRIPTION
        defaultDiscountsShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDiscountsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where description does not contain DEFAULT_DESCRIPTION
        defaultDiscountsShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the discountsList where description does not contain UPDATED_DESCRIPTION
        defaultDiscountsShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllDiscountsByDiscountCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where discountCode equals to DEFAULT_DISCOUNT_CODE
        defaultDiscountsShouldBeFound("discountCode.equals=" + DEFAULT_DISCOUNT_CODE);

        // Get all the discountsList where discountCode equals to UPDATED_DISCOUNT_CODE
        defaultDiscountsShouldNotBeFound("discountCode.equals=" + UPDATED_DISCOUNT_CODE);
    }

    @Test
    @Transactional
    public void getAllDiscountsByDiscountCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where discountCode not equals to DEFAULT_DISCOUNT_CODE
        defaultDiscountsShouldNotBeFound("discountCode.notEquals=" + DEFAULT_DISCOUNT_CODE);

        // Get all the discountsList where discountCode not equals to UPDATED_DISCOUNT_CODE
        defaultDiscountsShouldBeFound("discountCode.notEquals=" + UPDATED_DISCOUNT_CODE);
    }

    @Test
    @Transactional
    public void getAllDiscountsByDiscountCodeIsInShouldWork() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where discountCode in DEFAULT_DISCOUNT_CODE or UPDATED_DISCOUNT_CODE
        defaultDiscountsShouldBeFound("discountCode.in=" + DEFAULT_DISCOUNT_CODE + "," + UPDATED_DISCOUNT_CODE);

        // Get all the discountsList where discountCode equals to UPDATED_DISCOUNT_CODE
        defaultDiscountsShouldNotBeFound("discountCode.in=" + UPDATED_DISCOUNT_CODE);
    }

    @Test
    @Transactional
    public void getAllDiscountsByDiscountCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where discountCode is not null
        defaultDiscountsShouldBeFound("discountCode.specified=true");

        // Get all the discountsList where discountCode is null
        defaultDiscountsShouldNotBeFound("discountCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllDiscountsByDiscountCodeContainsSomething() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where discountCode contains DEFAULT_DISCOUNT_CODE
        defaultDiscountsShouldBeFound("discountCode.contains=" + DEFAULT_DISCOUNT_CODE);

        // Get all the discountsList where discountCode contains UPDATED_DISCOUNT_CODE
        defaultDiscountsShouldNotBeFound("discountCode.contains=" + UPDATED_DISCOUNT_CODE);
    }

    @Test
    @Transactional
    public void getAllDiscountsByDiscountCodeNotContainsSomething() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where discountCode does not contain DEFAULT_DISCOUNT_CODE
        defaultDiscountsShouldNotBeFound("discountCode.doesNotContain=" + DEFAULT_DISCOUNT_CODE);

        // Get all the discountsList where discountCode does not contain UPDATED_DISCOUNT_CODE
        defaultDiscountsShouldBeFound("discountCode.doesNotContain=" + UPDATED_DISCOUNT_CODE);
    }


    @Test
    @Transactional
    public void getAllDiscountsByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where validFrom equals to DEFAULT_VALID_FROM
        defaultDiscountsShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the discountsList where validFrom equals to UPDATED_VALID_FROM
        defaultDiscountsShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllDiscountsByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where validFrom not equals to DEFAULT_VALID_FROM
        defaultDiscountsShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the discountsList where validFrom not equals to UPDATED_VALID_FROM
        defaultDiscountsShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllDiscountsByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultDiscountsShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the discountsList where validFrom equals to UPDATED_VALID_FROM
        defaultDiscountsShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllDiscountsByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where validFrom is not null
        defaultDiscountsShouldBeFound("validFrom.specified=true");

        // Get all the discountsList where validFrom is null
        defaultDiscountsShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllDiscountsByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where validTo equals to DEFAULT_VALID_TO
        defaultDiscountsShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the discountsList where validTo equals to UPDATED_VALID_TO
        defaultDiscountsShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllDiscountsByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where validTo not equals to DEFAULT_VALID_TO
        defaultDiscountsShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the discountsList where validTo not equals to UPDATED_VALID_TO
        defaultDiscountsShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllDiscountsByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultDiscountsShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the discountsList where validTo equals to UPDATED_VALID_TO
        defaultDiscountsShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllDiscountsByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList where validTo is not null
        defaultDiscountsShouldBeFound("validTo.specified=true");

        // Get all the discountsList where validTo is null
        defaultDiscountsShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllDiscountsBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);
        Suppliers supplier = SuppliersResourceIT.createEntity(em);
        em.persist(supplier);
        em.flush();
        discounts.setSupplier(supplier);
        discountsRepository.saveAndFlush(discounts);
        Long supplierId = supplier.getId();

        // Get all the discountsList where supplier equals to supplierId
        defaultDiscountsShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the discountsList where supplier equals to supplierId + 1
        defaultDiscountsShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }


    @Test
    @Transactional
    public void getAllDiscountsByDiscountTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);
        DiscountTypes discountType = DiscountTypesResourceIT.createEntity(em);
        em.persist(discountType);
        em.flush();
        discounts.setDiscountType(discountType);
        discountsRepository.saveAndFlush(discounts);
        Long discountTypeId = discountType.getId();

        // Get all the discountsList where discountType equals to discountTypeId
        defaultDiscountsShouldBeFound("discountTypeId.equals=" + discountTypeId);

        // Get all the discountsList where discountType equals to discountTypeId + 1
        defaultDiscountsShouldNotBeFound("discountTypeId.equals=" + (discountTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDiscountsShouldBeFound(String filter) throws Exception {
        restDiscountsMockMvc.perform(get("/api/discounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discounts.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].discountCode").value(hasItem(DEFAULT_DISCOUNT_CODE)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restDiscountsMockMvc.perform(get("/api/discounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDiscountsShouldNotBeFound(String filter) throws Exception {
        restDiscountsMockMvc.perform(get("/api/discounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDiscountsMockMvc.perform(get("/api/discounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDiscounts() throws Exception {
        // Get the discounts
        restDiscountsMockMvc.perform(get("/api/discounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiscounts() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        int databaseSizeBeforeUpdate = discountsRepository.findAll().size();

        // Update the discounts
        Discounts updatedDiscounts = discountsRepository.findById(discounts.getId()).get();
        // Disconnect from session so that the updates on updatedDiscounts are not directly saved in db
        em.detach(updatedDiscounts);
        updatedDiscounts
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .discountCode(UPDATED_DISCOUNT_CODE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        DiscountsDTO discountsDTO = discountsMapper.toDto(updatedDiscounts);

        restDiscountsMockMvc.perform(put("/api/discounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountsDTO)))
            .andExpect(status().isOk());

        // Validate the Discounts in the database
        List<Discounts> discountsList = discountsRepository.findAll();
        assertThat(discountsList).hasSize(databaseSizeBeforeUpdate);
        Discounts testDiscounts = discountsList.get(discountsList.size() - 1);
        assertThat(testDiscounts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDiscounts.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDiscounts.getDiscountCode()).isEqualTo(UPDATED_DISCOUNT_CODE);
        assertThat(testDiscounts.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testDiscounts.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingDiscounts() throws Exception {
        int databaseSizeBeforeUpdate = discountsRepository.findAll().size();

        // Create the Discounts
        DiscountsDTO discountsDTO = discountsMapper.toDto(discounts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiscountsMockMvc.perform(put("/api/discounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Discounts in the database
        List<Discounts> discountsList = discountsRepository.findAll();
        assertThat(discountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiscounts() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        int databaseSizeBeforeDelete = discountsRepository.findAll().size();

        // Delete the discounts
        restDiscountsMockMvc.perform(delete("/api/discounts/{id}", discounts.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Discounts> discountsList = discountsRepository.findAll();
        assertThat(discountsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

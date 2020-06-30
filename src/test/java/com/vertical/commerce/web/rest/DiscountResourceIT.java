package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.Discount;
import com.vertical.commerce.domain.Suppliers;
import com.vertical.commerce.domain.DiscountTypes;
import com.vertical.commerce.repository.DiscountRepository;
import com.vertical.commerce.service.DiscountService;
import com.vertical.commerce.service.dto.DiscountDTO;
import com.vertical.commerce.service.mapper.DiscountMapper;
import com.vertical.commerce.service.dto.DiscountCriteria;
import com.vertical.commerce.service.DiscountQueryService;

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
 * Integration tests for the {@link DiscountResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class DiscountResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private DiscountMapper discountMapper;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private DiscountQueryService discountQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiscountMockMvc;

    private Discount discount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Discount createEntity(EntityManager em) {
        Discount discount = new Discount()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return discount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Discount createUpdatedEntity(EntityManager em) {
        Discount discount = new Discount()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return discount;
    }

    @BeforeEach
    public void initTest() {
        discount = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiscount() throws Exception {
        int databaseSizeBeforeCreate = discountRepository.findAll().size();
        // Create the Discount
        DiscountDTO discountDTO = discountMapper.toDto(discount);
        restDiscountMockMvc.perform(post("/api/discounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountDTO)))
            .andExpect(status().isCreated());

        // Validate the Discount in the database
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeCreate + 1);
        Discount testDiscount = discountList.get(discountList.size() - 1);
        assertThat(testDiscount.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDiscount.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDiscount.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testDiscount.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createDiscountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = discountRepository.findAll().size();

        // Create the Discount with an existing ID
        discount.setId(1L);
        DiscountDTO discountDTO = discountMapper.toDto(discount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiscountMockMvc.perform(post("/api/discounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Discount in the database
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountRepository.findAll().size();
        // set the field null
        discount.setName(null);

        // Create the Discount, which fails.
        DiscountDTO discountDTO = discountMapper.toDto(discount);


        restDiscountMockMvc.perform(post("/api/discounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountDTO)))
            .andExpect(status().isBadRequest());

        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountRepository.findAll().size();
        // set the field null
        discount.setValidFrom(null);

        // Create the Discount, which fails.
        DiscountDTO discountDTO = discountMapper.toDto(discount);


        restDiscountMockMvc.perform(post("/api/discounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountDTO)))
            .andExpect(status().isBadRequest());

        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountRepository.findAll().size();
        // set the field null
        discount.setValidTo(null);

        // Create the Discount, which fails.
        DiscountDTO discountDTO = discountMapper.toDto(discount);


        restDiscountMockMvc.perform(post("/api/discounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountDTO)))
            .andExpect(status().isBadRequest());

        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDiscounts() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList
        restDiscountMockMvc.perform(get("/api/discounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discount.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getDiscount() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get the discount
        restDiscountMockMvc.perform(get("/api/discounts/{id}", discount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(discount.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getDiscountsByIdFiltering() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        Long id = discount.getId();

        defaultDiscountShouldBeFound("id.equals=" + id);
        defaultDiscountShouldNotBeFound("id.notEquals=" + id);

        defaultDiscountShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDiscountShouldNotBeFound("id.greaterThan=" + id);

        defaultDiscountShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDiscountShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDiscountsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList where name equals to DEFAULT_NAME
        defaultDiscountShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the discountList where name equals to UPDATED_NAME
        defaultDiscountShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDiscountsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList where name not equals to DEFAULT_NAME
        defaultDiscountShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the discountList where name not equals to UPDATED_NAME
        defaultDiscountShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDiscountsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDiscountShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the discountList where name equals to UPDATED_NAME
        defaultDiscountShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDiscountsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList where name is not null
        defaultDiscountShouldBeFound("name.specified=true");

        // Get all the discountList where name is null
        defaultDiscountShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDiscountsByNameContainsSomething() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList where name contains DEFAULT_NAME
        defaultDiscountShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the discountList where name contains UPDATED_NAME
        defaultDiscountShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDiscountsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList where name does not contain DEFAULT_NAME
        defaultDiscountShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the discountList where name does not contain UPDATED_NAME
        defaultDiscountShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDiscountsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList where description equals to DEFAULT_DESCRIPTION
        defaultDiscountShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the discountList where description equals to UPDATED_DESCRIPTION
        defaultDiscountShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDiscountsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList where description not equals to DEFAULT_DESCRIPTION
        defaultDiscountShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the discountList where description not equals to UPDATED_DESCRIPTION
        defaultDiscountShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDiscountsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultDiscountShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the discountList where description equals to UPDATED_DESCRIPTION
        defaultDiscountShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDiscountsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList where description is not null
        defaultDiscountShouldBeFound("description.specified=true");

        // Get all the discountList where description is null
        defaultDiscountShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllDiscountsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList where description contains DEFAULT_DESCRIPTION
        defaultDiscountShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the discountList where description contains UPDATED_DESCRIPTION
        defaultDiscountShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDiscountsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList where description does not contain DEFAULT_DESCRIPTION
        defaultDiscountShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the discountList where description does not contain UPDATED_DESCRIPTION
        defaultDiscountShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllDiscountsByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList where validFrom equals to DEFAULT_VALID_FROM
        defaultDiscountShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the discountList where validFrom equals to UPDATED_VALID_FROM
        defaultDiscountShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllDiscountsByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList where validFrom not equals to DEFAULT_VALID_FROM
        defaultDiscountShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the discountList where validFrom not equals to UPDATED_VALID_FROM
        defaultDiscountShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllDiscountsByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultDiscountShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the discountList where validFrom equals to UPDATED_VALID_FROM
        defaultDiscountShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllDiscountsByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList where validFrom is not null
        defaultDiscountShouldBeFound("validFrom.specified=true");

        // Get all the discountList where validFrom is null
        defaultDiscountShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllDiscountsByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList where validTo equals to DEFAULT_VALID_TO
        defaultDiscountShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the discountList where validTo equals to UPDATED_VALID_TO
        defaultDiscountShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllDiscountsByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList where validTo not equals to DEFAULT_VALID_TO
        defaultDiscountShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the discountList where validTo not equals to UPDATED_VALID_TO
        defaultDiscountShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllDiscountsByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultDiscountShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the discountList where validTo equals to UPDATED_VALID_TO
        defaultDiscountShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllDiscountsByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        // Get all the discountList where validTo is not null
        defaultDiscountShouldBeFound("validTo.specified=true");

        // Get all the discountList where validTo is null
        defaultDiscountShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllDiscountsBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);
        Suppliers supplier = SuppliersResourceIT.createEntity(em);
        em.persist(supplier);
        em.flush();
        discount.setSupplier(supplier);
        discountRepository.saveAndFlush(discount);
        Long supplierId = supplier.getId();

        // Get all the discountList where supplier equals to supplierId
        defaultDiscountShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the discountList where supplier equals to supplierId + 1
        defaultDiscountShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }


    @Test
    @Transactional
    public void getAllDiscountsByDiscountTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);
        DiscountTypes discountType = DiscountTypesResourceIT.createEntity(em);
        em.persist(discountType);
        em.flush();
        discount.setDiscountType(discountType);
        discountRepository.saveAndFlush(discount);
        Long discountTypeId = discountType.getId();

        // Get all the discountList where discountType equals to discountTypeId
        defaultDiscountShouldBeFound("discountTypeId.equals=" + discountTypeId);

        // Get all the discountList where discountType equals to discountTypeId + 1
        defaultDiscountShouldNotBeFound("discountTypeId.equals=" + (discountTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDiscountShouldBeFound(String filter) throws Exception {
        restDiscountMockMvc.perform(get("/api/discounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discount.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restDiscountMockMvc.perform(get("/api/discounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDiscountShouldNotBeFound(String filter) throws Exception {
        restDiscountMockMvc.perform(get("/api/discounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDiscountMockMvc.perform(get("/api/discounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDiscount() throws Exception {
        // Get the discount
        restDiscountMockMvc.perform(get("/api/discounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiscount() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        int databaseSizeBeforeUpdate = discountRepository.findAll().size();

        // Update the discount
        Discount updatedDiscount = discountRepository.findById(discount.getId()).get();
        // Disconnect from session so that the updates on updatedDiscount are not directly saved in db
        em.detach(updatedDiscount);
        updatedDiscount
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        DiscountDTO discountDTO = discountMapper.toDto(updatedDiscount);

        restDiscountMockMvc.perform(put("/api/discounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountDTO)))
            .andExpect(status().isOk());

        // Validate the Discount in the database
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeUpdate);
        Discount testDiscount = discountList.get(discountList.size() - 1);
        assertThat(testDiscount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDiscount.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDiscount.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testDiscount.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingDiscount() throws Exception {
        int databaseSizeBeforeUpdate = discountRepository.findAll().size();

        // Create the Discount
        DiscountDTO discountDTO = discountMapper.toDto(discount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiscountMockMvc.perform(put("/api/discounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Discount in the database
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiscount() throws Exception {
        // Initialize the database
        discountRepository.saveAndFlush(discount);

        int databaseSizeBeforeDelete = discountRepository.findAll().size();

        // Delete the discount
        restDiscountMockMvc.perform(delete("/api/discounts/{id}", discount.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Discount> discountList = discountRepository.findAll();
        assertThat(discountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

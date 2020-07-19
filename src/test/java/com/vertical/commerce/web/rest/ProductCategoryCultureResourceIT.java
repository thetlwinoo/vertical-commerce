package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.ProductCategoryCulture;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.ProductCategory;
import com.vertical.commerce.repository.ProductCategoryCultureRepository;
import com.vertical.commerce.service.ProductCategoryCultureService;
import com.vertical.commerce.service.dto.ProductCategoryCultureDTO;
import com.vertical.commerce.service.mapper.ProductCategoryCultureMapper;
import com.vertical.commerce.service.dto.ProductCategoryCultureCriteria;
import com.vertical.commerce.service.ProductCategoryCultureQueryService;

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
 * Integration tests for the {@link ProductCategoryCultureResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ProductCategoryCultureResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ProductCategoryCultureRepository productCategoryCultureRepository;

    @Autowired
    private ProductCategoryCultureMapper productCategoryCultureMapper;

    @Autowired
    private ProductCategoryCultureService productCategoryCultureService;

    @Autowired
    private ProductCategoryCultureQueryService productCategoryCultureQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductCategoryCultureMockMvc;

    private ProductCategoryCulture productCategoryCulture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductCategoryCulture createEntity(EntityManager em) {
        ProductCategoryCulture productCategoryCulture = new ProductCategoryCulture()
            .name(DEFAULT_NAME);
        return productCategoryCulture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductCategoryCulture createUpdatedEntity(EntityManager em) {
        ProductCategoryCulture productCategoryCulture = new ProductCategoryCulture()
            .name(UPDATED_NAME);
        return productCategoryCulture;
    }

    @BeforeEach
    public void initTest() {
        productCategoryCulture = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductCategoryCulture() throws Exception {
        int databaseSizeBeforeCreate = productCategoryCultureRepository.findAll().size();
        // Create the ProductCategoryCulture
        ProductCategoryCultureDTO productCategoryCultureDTO = productCategoryCultureMapper.toDto(productCategoryCulture);
        restProductCategoryCultureMockMvc.perform(post("/api/product-category-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryCultureDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductCategoryCulture in the database
        List<ProductCategoryCulture> productCategoryCultureList = productCategoryCultureRepository.findAll();
        assertThat(productCategoryCultureList).hasSize(databaseSizeBeforeCreate + 1);
        ProductCategoryCulture testProductCategoryCulture = productCategoryCultureList.get(productCategoryCultureList.size() - 1);
        assertThat(testProductCategoryCulture.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createProductCategoryCultureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productCategoryCultureRepository.findAll().size();

        // Create the ProductCategoryCulture with an existing ID
        productCategoryCulture.setId(1L);
        ProductCategoryCultureDTO productCategoryCultureDTO = productCategoryCultureMapper.toDto(productCategoryCulture);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductCategoryCultureMockMvc.perform(post("/api/product-category-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductCategoryCulture in the database
        List<ProductCategoryCulture> productCategoryCultureList = productCategoryCultureRepository.findAll();
        assertThat(productCategoryCultureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productCategoryCultureRepository.findAll().size();
        // set the field null
        productCategoryCulture.setName(null);

        // Create the ProductCategoryCulture, which fails.
        ProductCategoryCultureDTO productCategoryCultureDTO = productCategoryCultureMapper.toDto(productCategoryCulture);


        restProductCategoryCultureMockMvc.perform(post("/api/product-category-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryCultureDTO)))
            .andExpect(status().isBadRequest());

        List<ProductCategoryCulture> productCategoryCultureList = productCategoryCultureRepository.findAll();
        assertThat(productCategoryCultureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductCategoryCultures() throws Exception {
        // Initialize the database
        productCategoryCultureRepository.saveAndFlush(productCategoryCulture);

        // Get all the productCategoryCultureList
        restProductCategoryCultureMockMvc.perform(get("/api/product-category-cultures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCategoryCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getProductCategoryCulture() throws Exception {
        // Initialize the database
        productCategoryCultureRepository.saveAndFlush(productCategoryCulture);

        // Get the productCategoryCulture
        restProductCategoryCultureMockMvc.perform(get("/api/product-category-cultures/{id}", productCategoryCulture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productCategoryCulture.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getProductCategoryCulturesByIdFiltering() throws Exception {
        // Initialize the database
        productCategoryCultureRepository.saveAndFlush(productCategoryCulture);

        Long id = productCategoryCulture.getId();

        defaultProductCategoryCultureShouldBeFound("id.equals=" + id);
        defaultProductCategoryCultureShouldNotBeFound("id.notEquals=" + id);

        defaultProductCategoryCultureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductCategoryCultureShouldNotBeFound("id.greaterThan=" + id);

        defaultProductCategoryCultureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductCategoryCultureShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductCategoryCulturesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryCultureRepository.saveAndFlush(productCategoryCulture);

        // Get all the productCategoryCultureList where name equals to DEFAULT_NAME
        defaultProductCategoryCultureShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productCategoryCultureList where name equals to UPDATED_NAME
        defaultProductCategoryCultureShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductCategoryCulturesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryCultureRepository.saveAndFlush(productCategoryCulture);

        // Get all the productCategoryCultureList where name not equals to DEFAULT_NAME
        defaultProductCategoryCultureShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productCategoryCultureList where name not equals to UPDATED_NAME
        defaultProductCategoryCultureShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductCategoryCulturesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryCultureRepository.saveAndFlush(productCategoryCulture);

        // Get all the productCategoryCultureList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductCategoryCultureShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productCategoryCultureList where name equals to UPDATED_NAME
        defaultProductCategoryCultureShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductCategoryCulturesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryCultureRepository.saveAndFlush(productCategoryCulture);

        // Get all the productCategoryCultureList where name is not null
        defaultProductCategoryCultureShouldBeFound("name.specified=true");

        // Get all the productCategoryCultureList where name is null
        defaultProductCategoryCultureShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductCategoryCulturesByNameContainsSomething() throws Exception {
        // Initialize the database
        productCategoryCultureRepository.saveAndFlush(productCategoryCulture);

        // Get all the productCategoryCultureList where name contains DEFAULT_NAME
        defaultProductCategoryCultureShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productCategoryCultureList where name contains UPDATED_NAME
        defaultProductCategoryCultureShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductCategoryCulturesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryCultureRepository.saveAndFlush(productCategoryCulture);

        // Get all the productCategoryCultureList where name does not contain DEFAULT_NAME
        defaultProductCategoryCultureShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productCategoryCultureList where name does not contain UPDATED_NAME
        defaultProductCategoryCultureShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllProductCategoryCulturesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryCultureRepository.saveAndFlush(productCategoryCulture);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        productCategoryCulture.setCulture(culture);
        productCategoryCultureRepository.saveAndFlush(productCategoryCulture);
        Long cultureId = culture.getId();

        // Get all the productCategoryCultureList where culture equals to cultureId
        defaultProductCategoryCultureShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the productCategoryCultureList where culture equals to cultureId + 1
        defaultProductCategoryCultureShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllProductCategoryCulturesByProductCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryCultureRepository.saveAndFlush(productCategoryCulture);
        ProductCategory productCategory = ProductCategoryResourceIT.createEntity(em);
        em.persist(productCategory);
        em.flush();
        productCategoryCulture.setProductCategory(productCategory);
        productCategoryCultureRepository.saveAndFlush(productCategoryCulture);
        Long productCategoryId = productCategory.getId();

        // Get all the productCategoryCultureList where productCategory equals to productCategoryId
        defaultProductCategoryCultureShouldBeFound("productCategoryId.equals=" + productCategoryId);

        // Get all the productCategoryCultureList where productCategory equals to productCategoryId + 1
        defaultProductCategoryCultureShouldNotBeFound("productCategoryId.equals=" + (productCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductCategoryCultureShouldBeFound(String filter) throws Exception {
        restProductCategoryCultureMockMvc.perform(get("/api/product-category-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCategoryCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restProductCategoryCultureMockMvc.perform(get("/api/product-category-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductCategoryCultureShouldNotBeFound(String filter) throws Exception {
        restProductCategoryCultureMockMvc.perform(get("/api/product-category-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductCategoryCultureMockMvc.perform(get("/api/product-category-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductCategoryCulture() throws Exception {
        // Get the productCategoryCulture
        restProductCategoryCultureMockMvc.perform(get("/api/product-category-cultures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductCategoryCulture() throws Exception {
        // Initialize the database
        productCategoryCultureRepository.saveAndFlush(productCategoryCulture);

        int databaseSizeBeforeUpdate = productCategoryCultureRepository.findAll().size();

        // Update the productCategoryCulture
        ProductCategoryCulture updatedProductCategoryCulture = productCategoryCultureRepository.findById(productCategoryCulture.getId()).get();
        // Disconnect from session so that the updates on updatedProductCategoryCulture are not directly saved in db
        em.detach(updatedProductCategoryCulture);
        updatedProductCategoryCulture
            .name(UPDATED_NAME);
        ProductCategoryCultureDTO productCategoryCultureDTO = productCategoryCultureMapper.toDto(updatedProductCategoryCulture);

        restProductCategoryCultureMockMvc.perform(put("/api/product-category-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryCultureDTO)))
            .andExpect(status().isOk());

        // Validate the ProductCategoryCulture in the database
        List<ProductCategoryCulture> productCategoryCultureList = productCategoryCultureRepository.findAll();
        assertThat(productCategoryCultureList).hasSize(databaseSizeBeforeUpdate);
        ProductCategoryCulture testProductCategoryCulture = productCategoryCultureList.get(productCategoryCultureList.size() - 1);
        assertThat(testProductCategoryCulture.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingProductCategoryCulture() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryCultureRepository.findAll().size();

        // Create the ProductCategoryCulture
        ProductCategoryCultureDTO productCategoryCultureDTO = productCategoryCultureMapper.toDto(productCategoryCulture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductCategoryCultureMockMvc.perform(put("/api/product-category-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductCategoryCulture in the database
        List<ProductCategoryCulture> productCategoryCultureList = productCategoryCultureRepository.findAll();
        assertThat(productCategoryCultureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductCategoryCulture() throws Exception {
        // Initialize the database
        productCategoryCultureRepository.saveAndFlush(productCategoryCulture);

        int databaseSizeBeforeDelete = productCategoryCultureRepository.findAll().size();

        // Delete the productCategoryCulture
        restProductCategoryCultureMockMvc.perform(delete("/api/product-category-cultures/{id}", productCategoryCulture.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductCategoryCulture> productCategoryCultureList = productCategoryCultureRepository.findAll();
        assertThat(productCategoryCultureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

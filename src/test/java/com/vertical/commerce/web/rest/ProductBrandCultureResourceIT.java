package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.ProductBrandCulture;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.ProductBrand;
import com.vertical.commerce.repository.ProductBrandCultureRepository;
import com.vertical.commerce.service.ProductBrandCultureService;
import com.vertical.commerce.service.dto.ProductBrandCultureDTO;
import com.vertical.commerce.service.mapper.ProductBrandCultureMapper;
import com.vertical.commerce.service.dto.ProductBrandCultureCriteria;
import com.vertical.commerce.service.ProductBrandCultureQueryService;

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
 * Integration tests for the {@link ProductBrandCultureResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ProductBrandCultureResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ProductBrandCultureRepository productBrandCultureRepository;

    @Autowired
    private ProductBrandCultureMapper productBrandCultureMapper;

    @Autowired
    private ProductBrandCultureService productBrandCultureService;

    @Autowired
    private ProductBrandCultureQueryService productBrandCultureQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductBrandCultureMockMvc;

    private ProductBrandCulture productBrandCulture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductBrandCulture createEntity(EntityManager em) {
        ProductBrandCulture productBrandCulture = new ProductBrandCulture()
            .name(DEFAULT_NAME);
        return productBrandCulture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductBrandCulture createUpdatedEntity(EntityManager em) {
        ProductBrandCulture productBrandCulture = new ProductBrandCulture()
            .name(UPDATED_NAME);
        return productBrandCulture;
    }

    @BeforeEach
    public void initTest() {
        productBrandCulture = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductBrandCulture() throws Exception {
        int databaseSizeBeforeCreate = productBrandCultureRepository.findAll().size();
        // Create the ProductBrandCulture
        ProductBrandCultureDTO productBrandCultureDTO = productBrandCultureMapper.toDto(productBrandCulture);
        restProductBrandCultureMockMvc.perform(post("/api/product-brand-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productBrandCultureDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductBrandCulture in the database
        List<ProductBrandCulture> productBrandCultureList = productBrandCultureRepository.findAll();
        assertThat(productBrandCultureList).hasSize(databaseSizeBeforeCreate + 1);
        ProductBrandCulture testProductBrandCulture = productBrandCultureList.get(productBrandCultureList.size() - 1);
        assertThat(testProductBrandCulture.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createProductBrandCultureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productBrandCultureRepository.findAll().size();

        // Create the ProductBrandCulture with an existing ID
        productBrandCulture.setId(1L);
        ProductBrandCultureDTO productBrandCultureDTO = productBrandCultureMapper.toDto(productBrandCulture);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductBrandCultureMockMvc.perform(post("/api/product-brand-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productBrandCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductBrandCulture in the database
        List<ProductBrandCulture> productBrandCultureList = productBrandCultureRepository.findAll();
        assertThat(productBrandCultureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productBrandCultureRepository.findAll().size();
        // set the field null
        productBrandCulture.setName(null);

        // Create the ProductBrandCulture, which fails.
        ProductBrandCultureDTO productBrandCultureDTO = productBrandCultureMapper.toDto(productBrandCulture);


        restProductBrandCultureMockMvc.perform(post("/api/product-brand-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productBrandCultureDTO)))
            .andExpect(status().isBadRequest());

        List<ProductBrandCulture> productBrandCultureList = productBrandCultureRepository.findAll();
        assertThat(productBrandCultureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductBrandCultures() throws Exception {
        // Initialize the database
        productBrandCultureRepository.saveAndFlush(productBrandCulture);

        // Get all the productBrandCultureList
        restProductBrandCultureMockMvc.perform(get("/api/product-brand-cultures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productBrandCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getProductBrandCulture() throws Exception {
        // Initialize the database
        productBrandCultureRepository.saveAndFlush(productBrandCulture);

        // Get the productBrandCulture
        restProductBrandCultureMockMvc.perform(get("/api/product-brand-cultures/{id}", productBrandCulture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productBrandCulture.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getProductBrandCulturesByIdFiltering() throws Exception {
        // Initialize the database
        productBrandCultureRepository.saveAndFlush(productBrandCulture);

        Long id = productBrandCulture.getId();

        defaultProductBrandCultureShouldBeFound("id.equals=" + id);
        defaultProductBrandCultureShouldNotBeFound("id.notEquals=" + id);

        defaultProductBrandCultureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductBrandCultureShouldNotBeFound("id.greaterThan=" + id);

        defaultProductBrandCultureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductBrandCultureShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductBrandCulturesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productBrandCultureRepository.saveAndFlush(productBrandCulture);

        // Get all the productBrandCultureList where name equals to DEFAULT_NAME
        defaultProductBrandCultureShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productBrandCultureList where name equals to UPDATED_NAME
        defaultProductBrandCultureShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductBrandCulturesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productBrandCultureRepository.saveAndFlush(productBrandCulture);

        // Get all the productBrandCultureList where name not equals to DEFAULT_NAME
        defaultProductBrandCultureShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productBrandCultureList where name not equals to UPDATED_NAME
        defaultProductBrandCultureShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductBrandCulturesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productBrandCultureRepository.saveAndFlush(productBrandCulture);

        // Get all the productBrandCultureList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductBrandCultureShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productBrandCultureList where name equals to UPDATED_NAME
        defaultProductBrandCultureShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductBrandCulturesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productBrandCultureRepository.saveAndFlush(productBrandCulture);

        // Get all the productBrandCultureList where name is not null
        defaultProductBrandCultureShouldBeFound("name.specified=true");

        // Get all the productBrandCultureList where name is null
        defaultProductBrandCultureShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductBrandCulturesByNameContainsSomething() throws Exception {
        // Initialize the database
        productBrandCultureRepository.saveAndFlush(productBrandCulture);

        // Get all the productBrandCultureList where name contains DEFAULT_NAME
        defaultProductBrandCultureShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productBrandCultureList where name contains UPDATED_NAME
        defaultProductBrandCultureShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductBrandCulturesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productBrandCultureRepository.saveAndFlush(productBrandCulture);

        // Get all the productBrandCultureList where name does not contain DEFAULT_NAME
        defaultProductBrandCultureShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productBrandCultureList where name does not contain UPDATED_NAME
        defaultProductBrandCultureShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllProductBrandCulturesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        productBrandCultureRepository.saveAndFlush(productBrandCulture);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        productBrandCulture.setCulture(culture);
        productBrandCultureRepository.saveAndFlush(productBrandCulture);
        Long cultureId = culture.getId();

        // Get all the productBrandCultureList where culture equals to cultureId
        defaultProductBrandCultureShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the productBrandCultureList where culture equals to cultureId + 1
        defaultProductBrandCultureShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllProductBrandCulturesByProductBrandIsEqualToSomething() throws Exception {
        // Initialize the database
        productBrandCultureRepository.saveAndFlush(productBrandCulture);
        ProductBrand productBrand = ProductBrandResourceIT.createEntity(em);
        em.persist(productBrand);
        em.flush();
        productBrandCulture.setProductBrand(productBrand);
        productBrandCultureRepository.saveAndFlush(productBrandCulture);
        Long productBrandId = productBrand.getId();

        // Get all the productBrandCultureList where productBrand equals to productBrandId
        defaultProductBrandCultureShouldBeFound("productBrandId.equals=" + productBrandId);

        // Get all the productBrandCultureList where productBrand equals to productBrandId + 1
        defaultProductBrandCultureShouldNotBeFound("productBrandId.equals=" + (productBrandId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductBrandCultureShouldBeFound(String filter) throws Exception {
        restProductBrandCultureMockMvc.perform(get("/api/product-brand-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productBrandCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restProductBrandCultureMockMvc.perform(get("/api/product-brand-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductBrandCultureShouldNotBeFound(String filter) throws Exception {
        restProductBrandCultureMockMvc.perform(get("/api/product-brand-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductBrandCultureMockMvc.perform(get("/api/product-brand-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductBrandCulture() throws Exception {
        // Get the productBrandCulture
        restProductBrandCultureMockMvc.perform(get("/api/product-brand-cultures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductBrandCulture() throws Exception {
        // Initialize the database
        productBrandCultureRepository.saveAndFlush(productBrandCulture);

        int databaseSizeBeforeUpdate = productBrandCultureRepository.findAll().size();

        // Update the productBrandCulture
        ProductBrandCulture updatedProductBrandCulture = productBrandCultureRepository.findById(productBrandCulture.getId()).get();
        // Disconnect from session so that the updates on updatedProductBrandCulture are not directly saved in db
        em.detach(updatedProductBrandCulture);
        updatedProductBrandCulture
            .name(UPDATED_NAME);
        ProductBrandCultureDTO productBrandCultureDTO = productBrandCultureMapper.toDto(updatedProductBrandCulture);

        restProductBrandCultureMockMvc.perform(put("/api/product-brand-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productBrandCultureDTO)))
            .andExpect(status().isOk());

        // Validate the ProductBrandCulture in the database
        List<ProductBrandCulture> productBrandCultureList = productBrandCultureRepository.findAll();
        assertThat(productBrandCultureList).hasSize(databaseSizeBeforeUpdate);
        ProductBrandCulture testProductBrandCulture = productBrandCultureList.get(productBrandCultureList.size() - 1);
        assertThat(testProductBrandCulture.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingProductBrandCulture() throws Exception {
        int databaseSizeBeforeUpdate = productBrandCultureRepository.findAll().size();

        // Create the ProductBrandCulture
        ProductBrandCultureDTO productBrandCultureDTO = productBrandCultureMapper.toDto(productBrandCulture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductBrandCultureMockMvc.perform(put("/api/product-brand-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productBrandCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductBrandCulture in the database
        List<ProductBrandCulture> productBrandCultureList = productBrandCultureRepository.findAll();
        assertThat(productBrandCultureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductBrandCulture() throws Exception {
        // Initialize the database
        productBrandCultureRepository.saveAndFlush(productBrandCulture);

        int databaseSizeBeforeDelete = productBrandCultureRepository.findAll().size();

        // Delete the productBrandCulture
        restProductBrandCultureMockMvc.perform(delete("/api/product-brand-cultures/{id}", productBrandCulture.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductBrandCulture> productBrandCultureList = productBrandCultureRepository.findAll();
        assertThat(productBrandCultureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

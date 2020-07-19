package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.ProductsCulture;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.Products;
import com.vertical.commerce.repository.ProductsCultureRepository;
import com.vertical.commerce.service.ProductsCultureService;
import com.vertical.commerce.service.dto.ProductsCultureDTO;
import com.vertical.commerce.service.mapper.ProductsCultureMapper;
import com.vertical.commerce.service.dto.ProductsCultureCriteria;
import com.vertical.commerce.service.ProductsCultureQueryService;

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
 * Integration tests for the {@link ProductsCultureResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ProductsCultureResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ProductsCultureRepository productsCultureRepository;

    @Autowired
    private ProductsCultureMapper productsCultureMapper;

    @Autowired
    private ProductsCultureService productsCultureService;

    @Autowired
    private ProductsCultureQueryService productsCultureQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductsCultureMockMvc;

    private ProductsCulture productsCulture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductsCulture createEntity(EntityManager em) {
        ProductsCulture productsCulture = new ProductsCulture()
            .name(DEFAULT_NAME);
        return productsCulture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductsCulture createUpdatedEntity(EntityManager em) {
        ProductsCulture productsCulture = new ProductsCulture()
            .name(UPDATED_NAME);
        return productsCulture;
    }

    @BeforeEach
    public void initTest() {
        productsCulture = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductsCulture() throws Exception {
        int databaseSizeBeforeCreate = productsCultureRepository.findAll().size();
        // Create the ProductsCulture
        ProductsCultureDTO productsCultureDTO = productsCultureMapper.toDto(productsCulture);
        restProductsCultureMockMvc.perform(post("/api/products-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productsCultureDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductsCulture in the database
        List<ProductsCulture> productsCultureList = productsCultureRepository.findAll();
        assertThat(productsCultureList).hasSize(databaseSizeBeforeCreate + 1);
        ProductsCulture testProductsCulture = productsCultureList.get(productsCultureList.size() - 1);
        assertThat(testProductsCulture.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createProductsCultureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productsCultureRepository.findAll().size();

        // Create the ProductsCulture with an existing ID
        productsCulture.setId(1L);
        ProductsCultureDTO productsCultureDTO = productsCultureMapper.toDto(productsCulture);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductsCultureMockMvc.perform(post("/api/products-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productsCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductsCulture in the database
        List<ProductsCulture> productsCultureList = productsCultureRepository.findAll();
        assertThat(productsCultureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsCultureRepository.findAll().size();
        // set the field null
        productsCulture.setName(null);

        // Create the ProductsCulture, which fails.
        ProductsCultureDTO productsCultureDTO = productsCultureMapper.toDto(productsCulture);


        restProductsCultureMockMvc.perform(post("/api/products-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productsCultureDTO)))
            .andExpect(status().isBadRequest());

        List<ProductsCulture> productsCultureList = productsCultureRepository.findAll();
        assertThat(productsCultureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductsCultures() throws Exception {
        // Initialize the database
        productsCultureRepository.saveAndFlush(productsCulture);

        // Get all the productsCultureList
        restProductsCultureMockMvc.perform(get("/api/products-cultures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productsCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getProductsCulture() throws Exception {
        // Initialize the database
        productsCultureRepository.saveAndFlush(productsCulture);

        // Get the productsCulture
        restProductsCultureMockMvc.perform(get("/api/products-cultures/{id}", productsCulture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productsCulture.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getProductsCulturesByIdFiltering() throws Exception {
        // Initialize the database
        productsCultureRepository.saveAndFlush(productsCulture);

        Long id = productsCulture.getId();

        defaultProductsCultureShouldBeFound("id.equals=" + id);
        defaultProductsCultureShouldNotBeFound("id.notEquals=" + id);

        defaultProductsCultureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductsCultureShouldNotBeFound("id.greaterThan=" + id);

        defaultProductsCultureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductsCultureShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductsCulturesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productsCultureRepository.saveAndFlush(productsCulture);

        // Get all the productsCultureList where name equals to DEFAULT_NAME
        defaultProductsCultureShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productsCultureList where name equals to UPDATED_NAME
        defaultProductsCultureShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsCulturesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsCultureRepository.saveAndFlush(productsCulture);

        // Get all the productsCultureList where name not equals to DEFAULT_NAME
        defaultProductsCultureShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productsCultureList where name not equals to UPDATED_NAME
        defaultProductsCultureShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsCulturesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productsCultureRepository.saveAndFlush(productsCulture);

        // Get all the productsCultureList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductsCultureShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productsCultureList where name equals to UPDATED_NAME
        defaultProductsCultureShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsCulturesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsCultureRepository.saveAndFlush(productsCulture);

        // Get all the productsCultureList where name is not null
        defaultProductsCultureShouldBeFound("name.specified=true");

        // Get all the productsCultureList where name is null
        defaultProductsCultureShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsCulturesByNameContainsSomething() throws Exception {
        // Initialize the database
        productsCultureRepository.saveAndFlush(productsCulture);

        // Get all the productsCultureList where name contains DEFAULT_NAME
        defaultProductsCultureShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productsCultureList where name contains UPDATED_NAME
        defaultProductsCultureShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsCulturesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productsCultureRepository.saveAndFlush(productsCulture);

        // Get all the productsCultureList where name does not contain DEFAULT_NAME
        defaultProductsCultureShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productsCultureList where name does not contain UPDATED_NAME
        defaultProductsCultureShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllProductsCulturesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        productsCultureRepository.saveAndFlush(productsCulture);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        productsCulture.setCulture(culture);
        productsCultureRepository.saveAndFlush(productsCulture);
        Long cultureId = culture.getId();

        // Get all the productsCultureList where culture equals to cultureId
        defaultProductsCultureShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the productsCultureList where culture equals to cultureId + 1
        defaultProductsCultureShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsCulturesByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productsCultureRepository.saveAndFlush(productsCulture);
        Products product = ProductsResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        productsCulture.setProduct(product);
        productsCultureRepository.saveAndFlush(productsCulture);
        Long productId = product.getId();

        // Get all the productsCultureList where product equals to productId
        defaultProductsCultureShouldBeFound("productId.equals=" + productId);

        // Get all the productsCultureList where product equals to productId + 1
        defaultProductsCultureShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductsCultureShouldBeFound(String filter) throws Exception {
        restProductsCultureMockMvc.perform(get("/api/products-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productsCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restProductsCultureMockMvc.perform(get("/api/products-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductsCultureShouldNotBeFound(String filter) throws Exception {
        restProductsCultureMockMvc.perform(get("/api/products-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductsCultureMockMvc.perform(get("/api/products-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductsCulture() throws Exception {
        // Get the productsCulture
        restProductsCultureMockMvc.perform(get("/api/products-cultures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductsCulture() throws Exception {
        // Initialize the database
        productsCultureRepository.saveAndFlush(productsCulture);

        int databaseSizeBeforeUpdate = productsCultureRepository.findAll().size();

        // Update the productsCulture
        ProductsCulture updatedProductsCulture = productsCultureRepository.findById(productsCulture.getId()).get();
        // Disconnect from session so that the updates on updatedProductsCulture are not directly saved in db
        em.detach(updatedProductsCulture);
        updatedProductsCulture
            .name(UPDATED_NAME);
        ProductsCultureDTO productsCultureDTO = productsCultureMapper.toDto(updatedProductsCulture);

        restProductsCultureMockMvc.perform(put("/api/products-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productsCultureDTO)))
            .andExpect(status().isOk());

        // Validate the ProductsCulture in the database
        List<ProductsCulture> productsCultureList = productsCultureRepository.findAll();
        assertThat(productsCultureList).hasSize(databaseSizeBeforeUpdate);
        ProductsCulture testProductsCulture = productsCultureList.get(productsCultureList.size() - 1);
        assertThat(testProductsCulture.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingProductsCulture() throws Exception {
        int databaseSizeBeforeUpdate = productsCultureRepository.findAll().size();

        // Create the ProductsCulture
        ProductsCultureDTO productsCultureDTO = productsCultureMapper.toDto(productsCulture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductsCultureMockMvc.perform(put("/api/products-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productsCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductsCulture in the database
        List<ProductsCulture> productsCultureList = productsCultureRepository.findAll();
        assertThat(productsCultureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductsCulture() throws Exception {
        // Initialize the database
        productsCultureRepository.saveAndFlush(productsCulture);

        int databaseSizeBeforeDelete = productsCultureRepository.findAll().size();

        // Delete the productsCulture
        restProductsCultureMockMvc.perform(delete("/api/products-cultures/{id}", productsCulture.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductsCulture> productsCultureList = productsCultureRepository.findAll();
        assertThat(productsCultureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

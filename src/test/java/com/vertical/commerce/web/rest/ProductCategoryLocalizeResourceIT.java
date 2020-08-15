package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.ProductCategoryLocalize;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.ProductCategory;
import com.vertical.commerce.repository.ProductCategoryLocalizeRepository;
import com.vertical.commerce.service.ProductCategoryLocalizeService;
import com.vertical.commerce.service.dto.ProductCategoryLocalizeDTO;
import com.vertical.commerce.service.mapper.ProductCategoryLocalizeMapper;
import com.vertical.commerce.service.dto.ProductCategoryLocalizeCriteria;
import com.vertical.commerce.service.ProductCategoryLocalizeQueryService;

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
 * Integration tests for the {@link ProductCategoryLocalizeResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ProductCategoryLocalizeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ProductCategoryLocalizeRepository productCategoryLocalizeRepository;

    @Autowired
    private ProductCategoryLocalizeMapper productCategoryLocalizeMapper;

    @Autowired
    private ProductCategoryLocalizeService productCategoryLocalizeService;

    @Autowired
    private ProductCategoryLocalizeQueryService productCategoryLocalizeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductCategoryLocalizeMockMvc;

    private ProductCategoryLocalize productCategoryLocalize;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductCategoryLocalize createEntity(EntityManager em) {
        ProductCategoryLocalize productCategoryLocalize = new ProductCategoryLocalize()
            .name(DEFAULT_NAME);
        return productCategoryLocalize;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductCategoryLocalize createUpdatedEntity(EntityManager em) {
        ProductCategoryLocalize productCategoryLocalize = new ProductCategoryLocalize()
            .name(UPDATED_NAME);
        return productCategoryLocalize;
    }

    @BeforeEach
    public void initTest() {
        productCategoryLocalize = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductCategoryLocalize() throws Exception {
        int databaseSizeBeforeCreate = productCategoryLocalizeRepository.findAll().size();
        // Create the ProductCategoryLocalize
        ProductCategoryLocalizeDTO productCategoryLocalizeDTO = productCategoryLocalizeMapper.toDto(productCategoryLocalize);
        restProductCategoryLocalizeMockMvc.perform(post("/api/product-category-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryLocalizeDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductCategoryLocalize in the database
        List<ProductCategoryLocalize> productCategoryLocalizeList = productCategoryLocalizeRepository.findAll();
        assertThat(productCategoryLocalizeList).hasSize(databaseSizeBeforeCreate + 1);
        ProductCategoryLocalize testProductCategoryLocalize = productCategoryLocalizeList.get(productCategoryLocalizeList.size() - 1);
        assertThat(testProductCategoryLocalize.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createProductCategoryLocalizeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productCategoryLocalizeRepository.findAll().size();

        // Create the ProductCategoryLocalize with an existing ID
        productCategoryLocalize.setId(1L);
        ProductCategoryLocalizeDTO productCategoryLocalizeDTO = productCategoryLocalizeMapper.toDto(productCategoryLocalize);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductCategoryLocalizeMockMvc.perform(post("/api/product-category-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductCategoryLocalize in the database
        List<ProductCategoryLocalize> productCategoryLocalizeList = productCategoryLocalizeRepository.findAll();
        assertThat(productCategoryLocalizeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productCategoryLocalizeRepository.findAll().size();
        // set the field null
        productCategoryLocalize.setName(null);

        // Create the ProductCategoryLocalize, which fails.
        ProductCategoryLocalizeDTO productCategoryLocalizeDTO = productCategoryLocalizeMapper.toDto(productCategoryLocalize);


        restProductCategoryLocalizeMockMvc.perform(post("/api/product-category-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryLocalizeDTO)))
            .andExpect(status().isBadRequest());

        List<ProductCategoryLocalize> productCategoryLocalizeList = productCategoryLocalizeRepository.findAll();
        assertThat(productCategoryLocalizeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductCategoryLocalizes() throws Exception {
        // Initialize the database
        productCategoryLocalizeRepository.saveAndFlush(productCategoryLocalize);

        // Get all the productCategoryLocalizeList
        restProductCategoryLocalizeMockMvc.perform(get("/api/product-category-localizes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCategoryLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getProductCategoryLocalize() throws Exception {
        // Initialize the database
        productCategoryLocalizeRepository.saveAndFlush(productCategoryLocalize);

        // Get the productCategoryLocalize
        restProductCategoryLocalizeMockMvc.perform(get("/api/product-category-localizes/{id}", productCategoryLocalize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productCategoryLocalize.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getProductCategoryLocalizesByIdFiltering() throws Exception {
        // Initialize the database
        productCategoryLocalizeRepository.saveAndFlush(productCategoryLocalize);

        Long id = productCategoryLocalize.getId();

        defaultProductCategoryLocalizeShouldBeFound("id.equals=" + id);
        defaultProductCategoryLocalizeShouldNotBeFound("id.notEquals=" + id);

        defaultProductCategoryLocalizeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductCategoryLocalizeShouldNotBeFound("id.greaterThan=" + id);

        defaultProductCategoryLocalizeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductCategoryLocalizeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductCategoryLocalizesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryLocalizeRepository.saveAndFlush(productCategoryLocalize);

        // Get all the productCategoryLocalizeList where name equals to DEFAULT_NAME
        defaultProductCategoryLocalizeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productCategoryLocalizeList where name equals to UPDATED_NAME
        defaultProductCategoryLocalizeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductCategoryLocalizesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryLocalizeRepository.saveAndFlush(productCategoryLocalize);

        // Get all the productCategoryLocalizeList where name not equals to DEFAULT_NAME
        defaultProductCategoryLocalizeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productCategoryLocalizeList where name not equals to UPDATED_NAME
        defaultProductCategoryLocalizeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductCategoryLocalizesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryLocalizeRepository.saveAndFlush(productCategoryLocalize);

        // Get all the productCategoryLocalizeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductCategoryLocalizeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productCategoryLocalizeList where name equals to UPDATED_NAME
        defaultProductCategoryLocalizeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductCategoryLocalizesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryLocalizeRepository.saveAndFlush(productCategoryLocalize);

        // Get all the productCategoryLocalizeList where name is not null
        defaultProductCategoryLocalizeShouldBeFound("name.specified=true");

        // Get all the productCategoryLocalizeList where name is null
        defaultProductCategoryLocalizeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductCategoryLocalizesByNameContainsSomething() throws Exception {
        // Initialize the database
        productCategoryLocalizeRepository.saveAndFlush(productCategoryLocalize);

        // Get all the productCategoryLocalizeList where name contains DEFAULT_NAME
        defaultProductCategoryLocalizeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productCategoryLocalizeList where name contains UPDATED_NAME
        defaultProductCategoryLocalizeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductCategoryLocalizesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryLocalizeRepository.saveAndFlush(productCategoryLocalize);

        // Get all the productCategoryLocalizeList where name does not contain DEFAULT_NAME
        defaultProductCategoryLocalizeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productCategoryLocalizeList where name does not contain UPDATED_NAME
        defaultProductCategoryLocalizeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllProductCategoryLocalizesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryLocalizeRepository.saveAndFlush(productCategoryLocalize);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        productCategoryLocalize.setCulture(culture);
        productCategoryLocalizeRepository.saveAndFlush(productCategoryLocalize);
        Long cultureId = culture.getId();

        // Get all the productCategoryLocalizeList where culture equals to cultureId
        defaultProductCategoryLocalizeShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the productCategoryLocalizeList where culture equals to cultureId + 1
        defaultProductCategoryLocalizeShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllProductCategoryLocalizesByProductCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryLocalizeRepository.saveAndFlush(productCategoryLocalize);
        ProductCategory productCategory = ProductCategoryResourceIT.createEntity(em);
        em.persist(productCategory);
        em.flush();
        productCategoryLocalize.setProductCategory(productCategory);
        productCategoryLocalizeRepository.saveAndFlush(productCategoryLocalize);
        Long productCategoryId = productCategory.getId();

        // Get all the productCategoryLocalizeList where productCategory equals to productCategoryId
        defaultProductCategoryLocalizeShouldBeFound("productCategoryId.equals=" + productCategoryId);

        // Get all the productCategoryLocalizeList where productCategory equals to productCategoryId + 1
        defaultProductCategoryLocalizeShouldNotBeFound("productCategoryId.equals=" + (productCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductCategoryLocalizeShouldBeFound(String filter) throws Exception {
        restProductCategoryLocalizeMockMvc.perform(get("/api/product-category-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCategoryLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restProductCategoryLocalizeMockMvc.perform(get("/api/product-category-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductCategoryLocalizeShouldNotBeFound(String filter) throws Exception {
        restProductCategoryLocalizeMockMvc.perform(get("/api/product-category-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductCategoryLocalizeMockMvc.perform(get("/api/product-category-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductCategoryLocalize() throws Exception {
        // Get the productCategoryLocalize
        restProductCategoryLocalizeMockMvc.perform(get("/api/product-category-localizes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductCategoryLocalize() throws Exception {
        // Initialize the database
        productCategoryLocalizeRepository.saveAndFlush(productCategoryLocalize);

        int databaseSizeBeforeUpdate = productCategoryLocalizeRepository.findAll().size();

        // Update the productCategoryLocalize
        ProductCategoryLocalize updatedProductCategoryLocalize = productCategoryLocalizeRepository.findById(productCategoryLocalize.getId()).get();
        // Disconnect from session so that the updates on updatedProductCategoryLocalize are not directly saved in db
        em.detach(updatedProductCategoryLocalize);
        updatedProductCategoryLocalize
            .name(UPDATED_NAME);
        ProductCategoryLocalizeDTO productCategoryLocalizeDTO = productCategoryLocalizeMapper.toDto(updatedProductCategoryLocalize);

        restProductCategoryLocalizeMockMvc.perform(put("/api/product-category-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryLocalizeDTO)))
            .andExpect(status().isOk());

        // Validate the ProductCategoryLocalize in the database
        List<ProductCategoryLocalize> productCategoryLocalizeList = productCategoryLocalizeRepository.findAll();
        assertThat(productCategoryLocalizeList).hasSize(databaseSizeBeforeUpdate);
        ProductCategoryLocalize testProductCategoryLocalize = productCategoryLocalizeList.get(productCategoryLocalizeList.size() - 1);
        assertThat(testProductCategoryLocalize.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingProductCategoryLocalize() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryLocalizeRepository.findAll().size();

        // Create the ProductCategoryLocalize
        ProductCategoryLocalizeDTO productCategoryLocalizeDTO = productCategoryLocalizeMapper.toDto(productCategoryLocalize);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductCategoryLocalizeMockMvc.perform(put("/api/product-category-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductCategoryLocalize in the database
        List<ProductCategoryLocalize> productCategoryLocalizeList = productCategoryLocalizeRepository.findAll();
        assertThat(productCategoryLocalizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductCategoryLocalize() throws Exception {
        // Initialize the database
        productCategoryLocalizeRepository.saveAndFlush(productCategoryLocalize);

        int databaseSizeBeforeDelete = productCategoryLocalizeRepository.findAll().size();

        // Delete the productCategoryLocalize
        restProductCategoryLocalizeMockMvc.perform(delete("/api/product-category-localizes/{id}", productCategoryLocalize.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductCategoryLocalize> productCategoryLocalizeList = productCategoryLocalizeRepository.findAll();
        assertThat(productCategoryLocalizeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

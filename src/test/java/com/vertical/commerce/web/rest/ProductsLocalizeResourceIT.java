package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.ProductsLocalize;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.Products;
import com.vertical.commerce.repository.ProductsLocalizeRepository;
import com.vertical.commerce.service.ProductsLocalizeService;
import com.vertical.commerce.service.dto.ProductsLocalizeDTO;
import com.vertical.commerce.service.mapper.ProductsLocalizeMapper;
import com.vertical.commerce.service.dto.ProductsLocalizeCriteria;
import com.vertical.commerce.service.ProductsLocalizeQueryService;

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
 * Integration tests for the {@link ProductsLocalizeResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ProductsLocalizeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ProductsLocalizeRepository productsLocalizeRepository;

    @Autowired
    private ProductsLocalizeMapper productsLocalizeMapper;

    @Autowired
    private ProductsLocalizeService productsLocalizeService;

    @Autowired
    private ProductsLocalizeQueryService productsLocalizeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductsLocalizeMockMvc;

    private ProductsLocalize productsLocalize;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductsLocalize createEntity(EntityManager em) {
        ProductsLocalize productsLocalize = new ProductsLocalize()
            .name(DEFAULT_NAME);
        return productsLocalize;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductsLocalize createUpdatedEntity(EntityManager em) {
        ProductsLocalize productsLocalize = new ProductsLocalize()
            .name(UPDATED_NAME);
        return productsLocalize;
    }

    @BeforeEach
    public void initTest() {
        productsLocalize = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductsLocalize() throws Exception {
        int databaseSizeBeforeCreate = productsLocalizeRepository.findAll().size();
        // Create the ProductsLocalize
        ProductsLocalizeDTO productsLocalizeDTO = productsLocalizeMapper.toDto(productsLocalize);
        restProductsLocalizeMockMvc.perform(post("/api/products-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productsLocalizeDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductsLocalize in the database
        List<ProductsLocalize> productsLocalizeList = productsLocalizeRepository.findAll();
        assertThat(productsLocalizeList).hasSize(databaseSizeBeforeCreate + 1);
        ProductsLocalize testProductsLocalize = productsLocalizeList.get(productsLocalizeList.size() - 1);
        assertThat(testProductsLocalize.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createProductsLocalizeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productsLocalizeRepository.findAll().size();

        // Create the ProductsLocalize with an existing ID
        productsLocalize.setId(1L);
        ProductsLocalizeDTO productsLocalizeDTO = productsLocalizeMapper.toDto(productsLocalize);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductsLocalizeMockMvc.perform(post("/api/products-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productsLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductsLocalize in the database
        List<ProductsLocalize> productsLocalizeList = productsLocalizeRepository.findAll();
        assertThat(productsLocalizeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsLocalizeRepository.findAll().size();
        // set the field null
        productsLocalize.setName(null);

        // Create the ProductsLocalize, which fails.
        ProductsLocalizeDTO productsLocalizeDTO = productsLocalizeMapper.toDto(productsLocalize);


        restProductsLocalizeMockMvc.perform(post("/api/products-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productsLocalizeDTO)))
            .andExpect(status().isBadRequest());

        List<ProductsLocalize> productsLocalizeList = productsLocalizeRepository.findAll();
        assertThat(productsLocalizeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductsLocalizes() throws Exception {
        // Initialize the database
        productsLocalizeRepository.saveAndFlush(productsLocalize);

        // Get all the productsLocalizeList
        restProductsLocalizeMockMvc.perform(get("/api/products-localizes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productsLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getProductsLocalize() throws Exception {
        // Initialize the database
        productsLocalizeRepository.saveAndFlush(productsLocalize);

        // Get the productsLocalize
        restProductsLocalizeMockMvc.perform(get("/api/products-localizes/{id}", productsLocalize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productsLocalize.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getProductsLocalizesByIdFiltering() throws Exception {
        // Initialize the database
        productsLocalizeRepository.saveAndFlush(productsLocalize);

        Long id = productsLocalize.getId();

        defaultProductsLocalizeShouldBeFound("id.equals=" + id);
        defaultProductsLocalizeShouldNotBeFound("id.notEquals=" + id);

        defaultProductsLocalizeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductsLocalizeShouldNotBeFound("id.greaterThan=" + id);

        defaultProductsLocalizeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductsLocalizeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductsLocalizesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productsLocalizeRepository.saveAndFlush(productsLocalize);

        // Get all the productsLocalizeList where name equals to DEFAULT_NAME
        defaultProductsLocalizeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productsLocalizeList where name equals to UPDATED_NAME
        defaultProductsLocalizeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsLocalizesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsLocalizeRepository.saveAndFlush(productsLocalize);

        // Get all the productsLocalizeList where name not equals to DEFAULT_NAME
        defaultProductsLocalizeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productsLocalizeList where name not equals to UPDATED_NAME
        defaultProductsLocalizeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsLocalizesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productsLocalizeRepository.saveAndFlush(productsLocalize);

        // Get all the productsLocalizeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductsLocalizeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productsLocalizeList where name equals to UPDATED_NAME
        defaultProductsLocalizeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsLocalizesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsLocalizeRepository.saveAndFlush(productsLocalize);

        // Get all the productsLocalizeList where name is not null
        defaultProductsLocalizeShouldBeFound("name.specified=true");

        // Get all the productsLocalizeList where name is null
        defaultProductsLocalizeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsLocalizesByNameContainsSomething() throws Exception {
        // Initialize the database
        productsLocalizeRepository.saveAndFlush(productsLocalize);

        // Get all the productsLocalizeList where name contains DEFAULT_NAME
        defaultProductsLocalizeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productsLocalizeList where name contains UPDATED_NAME
        defaultProductsLocalizeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsLocalizesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productsLocalizeRepository.saveAndFlush(productsLocalize);

        // Get all the productsLocalizeList where name does not contain DEFAULT_NAME
        defaultProductsLocalizeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productsLocalizeList where name does not contain UPDATED_NAME
        defaultProductsLocalizeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllProductsLocalizesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        productsLocalizeRepository.saveAndFlush(productsLocalize);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        productsLocalize.setCulture(culture);
        productsLocalizeRepository.saveAndFlush(productsLocalize);
        Long cultureId = culture.getId();

        // Get all the productsLocalizeList where culture equals to cultureId
        defaultProductsLocalizeShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the productsLocalizeList where culture equals to cultureId + 1
        defaultProductsLocalizeShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsLocalizesByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productsLocalizeRepository.saveAndFlush(productsLocalize);
        Products product = ProductsResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        productsLocalize.setProduct(product);
        productsLocalizeRepository.saveAndFlush(productsLocalize);
        Long productId = product.getId();

        // Get all the productsLocalizeList where product equals to productId
        defaultProductsLocalizeShouldBeFound("productId.equals=" + productId);

        // Get all the productsLocalizeList where product equals to productId + 1
        defaultProductsLocalizeShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductsLocalizeShouldBeFound(String filter) throws Exception {
        restProductsLocalizeMockMvc.perform(get("/api/products-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productsLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restProductsLocalizeMockMvc.perform(get("/api/products-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductsLocalizeShouldNotBeFound(String filter) throws Exception {
        restProductsLocalizeMockMvc.perform(get("/api/products-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductsLocalizeMockMvc.perform(get("/api/products-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductsLocalize() throws Exception {
        // Get the productsLocalize
        restProductsLocalizeMockMvc.perform(get("/api/products-localizes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductsLocalize() throws Exception {
        // Initialize the database
        productsLocalizeRepository.saveAndFlush(productsLocalize);

        int databaseSizeBeforeUpdate = productsLocalizeRepository.findAll().size();

        // Update the productsLocalize
        ProductsLocalize updatedProductsLocalize = productsLocalizeRepository.findById(productsLocalize.getId()).get();
        // Disconnect from session so that the updates on updatedProductsLocalize are not directly saved in db
        em.detach(updatedProductsLocalize);
        updatedProductsLocalize
            .name(UPDATED_NAME);
        ProductsLocalizeDTO productsLocalizeDTO = productsLocalizeMapper.toDto(updatedProductsLocalize);

        restProductsLocalizeMockMvc.perform(put("/api/products-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productsLocalizeDTO)))
            .andExpect(status().isOk());

        // Validate the ProductsLocalize in the database
        List<ProductsLocalize> productsLocalizeList = productsLocalizeRepository.findAll();
        assertThat(productsLocalizeList).hasSize(databaseSizeBeforeUpdate);
        ProductsLocalize testProductsLocalize = productsLocalizeList.get(productsLocalizeList.size() - 1);
        assertThat(testProductsLocalize.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingProductsLocalize() throws Exception {
        int databaseSizeBeforeUpdate = productsLocalizeRepository.findAll().size();

        // Create the ProductsLocalize
        ProductsLocalizeDTO productsLocalizeDTO = productsLocalizeMapper.toDto(productsLocalize);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductsLocalizeMockMvc.perform(put("/api/products-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productsLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductsLocalize in the database
        List<ProductsLocalize> productsLocalizeList = productsLocalizeRepository.findAll();
        assertThat(productsLocalizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductsLocalize() throws Exception {
        // Initialize the database
        productsLocalizeRepository.saveAndFlush(productsLocalize);

        int databaseSizeBeforeDelete = productsLocalizeRepository.findAll().size();

        // Delete the productsLocalize
        restProductsLocalizeMockMvc.perform(delete("/api/products-localizes/{id}", productsLocalize.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductsLocalize> productsLocalizeList = productsLocalizeRepository.findAll();
        assertThat(productsLocalizeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

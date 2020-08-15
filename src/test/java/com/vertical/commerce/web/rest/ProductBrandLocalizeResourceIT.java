package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.ProductBrandLocalize;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.ProductBrand;
import com.vertical.commerce.repository.ProductBrandLocalizeRepository;
import com.vertical.commerce.service.ProductBrandLocalizeService;
import com.vertical.commerce.service.dto.ProductBrandLocalizeDTO;
import com.vertical.commerce.service.mapper.ProductBrandLocalizeMapper;
import com.vertical.commerce.service.dto.ProductBrandLocalizeCriteria;
import com.vertical.commerce.service.ProductBrandLocalizeQueryService;

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
 * Integration tests for the {@link ProductBrandLocalizeResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ProductBrandLocalizeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ProductBrandLocalizeRepository productBrandLocalizeRepository;

    @Autowired
    private ProductBrandLocalizeMapper productBrandLocalizeMapper;

    @Autowired
    private ProductBrandLocalizeService productBrandLocalizeService;

    @Autowired
    private ProductBrandLocalizeQueryService productBrandLocalizeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductBrandLocalizeMockMvc;

    private ProductBrandLocalize productBrandLocalize;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductBrandLocalize createEntity(EntityManager em) {
        ProductBrandLocalize productBrandLocalize = new ProductBrandLocalize()
            .name(DEFAULT_NAME);
        return productBrandLocalize;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductBrandLocalize createUpdatedEntity(EntityManager em) {
        ProductBrandLocalize productBrandLocalize = new ProductBrandLocalize()
            .name(UPDATED_NAME);
        return productBrandLocalize;
    }

    @BeforeEach
    public void initTest() {
        productBrandLocalize = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductBrandLocalize() throws Exception {
        int databaseSizeBeforeCreate = productBrandLocalizeRepository.findAll().size();
        // Create the ProductBrandLocalize
        ProductBrandLocalizeDTO productBrandLocalizeDTO = productBrandLocalizeMapper.toDto(productBrandLocalize);
        restProductBrandLocalizeMockMvc.perform(post("/api/product-brand-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productBrandLocalizeDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductBrandLocalize in the database
        List<ProductBrandLocalize> productBrandLocalizeList = productBrandLocalizeRepository.findAll();
        assertThat(productBrandLocalizeList).hasSize(databaseSizeBeforeCreate + 1);
        ProductBrandLocalize testProductBrandLocalize = productBrandLocalizeList.get(productBrandLocalizeList.size() - 1);
        assertThat(testProductBrandLocalize.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createProductBrandLocalizeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productBrandLocalizeRepository.findAll().size();

        // Create the ProductBrandLocalize with an existing ID
        productBrandLocalize.setId(1L);
        ProductBrandLocalizeDTO productBrandLocalizeDTO = productBrandLocalizeMapper.toDto(productBrandLocalize);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductBrandLocalizeMockMvc.perform(post("/api/product-brand-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productBrandLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductBrandLocalize in the database
        List<ProductBrandLocalize> productBrandLocalizeList = productBrandLocalizeRepository.findAll();
        assertThat(productBrandLocalizeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productBrandLocalizeRepository.findAll().size();
        // set the field null
        productBrandLocalize.setName(null);

        // Create the ProductBrandLocalize, which fails.
        ProductBrandLocalizeDTO productBrandLocalizeDTO = productBrandLocalizeMapper.toDto(productBrandLocalize);


        restProductBrandLocalizeMockMvc.perform(post("/api/product-brand-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productBrandLocalizeDTO)))
            .andExpect(status().isBadRequest());

        List<ProductBrandLocalize> productBrandLocalizeList = productBrandLocalizeRepository.findAll();
        assertThat(productBrandLocalizeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductBrandLocalizes() throws Exception {
        // Initialize the database
        productBrandLocalizeRepository.saveAndFlush(productBrandLocalize);

        // Get all the productBrandLocalizeList
        restProductBrandLocalizeMockMvc.perform(get("/api/product-brand-localizes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productBrandLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getProductBrandLocalize() throws Exception {
        // Initialize the database
        productBrandLocalizeRepository.saveAndFlush(productBrandLocalize);

        // Get the productBrandLocalize
        restProductBrandLocalizeMockMvc.perform(get("/api/product-brand-localizes/{id}", productBrandLocalize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productBrandLocalize.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getProductBrandLocalizesByIdFiltering() throws Exception {
        // Initialize the database
        productBrandLocalizeRepository.saveAndFlush(productBrandLocalize);

        Long id = productBrandLocalize.getId();

        defaultProductBrandLocalizeShouldBeFound("id.equals=" + id);
        defaultProductBrandLocalizeShouldNotBeFound("id.notEquals=" + id);

        defaultProductBrandLocalizeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductBrandLocalizeShouldNotBeFound("id.greaterThan=" + id);

        defaultProductBrandLocalizeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductBrandLocalizeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductBrandLocalizesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productBrandLocalizeRepository.saveAndFlush(productBrandLocalize);

        // Get all the productBrandLocalizeList where name equals to DEFAULT_NAME
        defaultProductBrandLocalizeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productBrandLocalizeList where name equals to UPDATED_NAME
        defaultProductBrandLocalizeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductBrandLocalizesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productBrandLocalizeRepository.saveAndFlush(productBrandLocalize);

        // Get all the productBrandLocalizeList where name not equals to DEFAULT_NAME
        defaultProductBrandLocalizeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productBrandLocalizeList where name not equals to UPDATED_NAME
        defaultProductBrandLocalizeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductBrandLocalizesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productBrandLocalizeRepository.saveAndFlush(productBrandLocalize);

        // Get all the productBrandLocalizeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductBrandLocalizeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productBrandLocalizeList where name equals to UPDATED_NAME
        defaultProductBrandLocalizeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductBrandLocalizesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productBrandLocalizeRepository.saveAndFlush(productBrandLocalize);

        // Get all the productBrandLocalizeList where name is not null
        defaultProductBrandLocalizeShouldBeFound("name.specified=true");

        // Get all the productBrandLocalizeList where name is null
        defaultProductBrandLocalizeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductBrandLocalizesByNameContainsSomething() throws Exception {
        // Initialize the database
        productBrandLocalizeRepository.saveAndFlush(productBrandLocalize);

        // Get all the productBrandLocalizeList where name contains DEFAULT_NAME
        defaultProductBrandLocalizeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productBrandLocalizeList where name contains UPDATED_NAME
        defaultProductBrandLocalizeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductBrandLocalizesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productBrandLocalizeRepository.saveAndFlush(productBrandLocalize);

        // Get all the productBrandLocalizeList where name does not contain DEFAULT_NAME
        defaultProductBrandLocalizeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productBrandLocalizeList where name does not contain UPDATED_NAME
        defaultProductBrandLocalizeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllProductBrandLocalizesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        productBrandLocalizeRepository.saveAndFlush(productBrandLocalize);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        productBrandLocalize.setCulture(culture);
        productBrandLocalizeRepository.saveAndFlush(productBrandLocalize);
        Long cultureId = culture.getId();

        // Get all the productBrandLocalizeList where culture equals to cultureId
        defaultProductBrandLocalizeShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the productBrandLocalizeList where culture equals to cultureId + 1
        defaultProductBrandLocalizeShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllProductBrandLocalizesByProductBrandIsEqualToSomething() throws Exception {
        // Initialize the database
        productBrandLocalizeRepository.saveAndFlush(productBrandLocalize);
        ProductBrand productBrand = ProductBrandResourceIT.createEntity(em);
        em.persist(productBrand);
        em.flush();
        productBrandLocalize.setProductBrand(productBrand);
        productBrandLocalizeRepository.saveAndFlush(productBrandLocalize);
        Long productBrandId = productBrand.getId();

        // Get all the productBrandLocalizeList where productBrand equals to productBrandId
        defaultProductBrandLocalizeShouldBeFound("productBrandId.equals=" + productBrandId);

        // Get all the productBrandLocalizeList where productBrand equals to productBrandId + 1
        defaultProductBrandLocalizeShouldNotBeFound("productBrandId.equals=" + (productBrandId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductBrandLocalizeShouldBeFound(String filter) throws Exception {
        restProductBrandLocalizeMockMvc.perform(get("/api/product-brand-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productBrandLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restProductBrandLocalizeMockMvc.perform(get("/api/product-brand-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductBrandLocalizeShouldNotBeFound(String filter) throws Exception {
        restProductBrandLocalizeMockMvc.perform(get("/api/product-brand-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductBrandLocalizeMockMvc.perform(get("/api/product-brand-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductBrandLocalize() throws Exception {
        // Get the productBrandLocalize
        restProductBrandLocalizeMockMvc.perform(get("/api/product-brand-localizes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductBrandLocalize() throws Exception {
        // Initialize the database
        productBrandLocalizeRepository.saveAndFlush(productBrandLocalize);

        int databaseSizeBeforeUpdate = productBrandLocalizeRepository.findAll().size();

        // Update the productBrandLocalize
        ProductBrandLocalize updatedProductBrandLocalize = productBrandLocalizeRepository.findById(productBrandLocalize.getId()).get();
        // Disconnect from session so that the updates on updatedProductBrandLocalize are not directly saved in db
        em.detach(updatedProductBrandLocalize);
        updatedProductBrandLocalize
            .name(UPDATED_NAME);
        ProductBrandLocalizeDTO productBrandLocalizeDTO = productBrandLocalizeMapper.toDto(updatedProductBrandLocalize);

        restProductBrandLocalizeMockMvc.perform(put("/api/product-brand-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productBrandLocalizeDTO)))
            .andExpect(status().isOk());

        // Validate the ProductBrandLocalize in the database
        List<ProductBrandLocalize> productBrandLocalizeList = productBrandLocalizeRepository.findAll();
        assertThat(productBrandLocalizeList).hasSize(databaseSizeBeforeUpdate);
        ProductBrandLocalize testProductBrandLocalize = productBrandLocalizeList.get(productBrandLocalizeList.size() - 1);
        assertThat(testProductBrandLocalize.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingProductBrandLocalize() throws Exception {
        int databaseSizeBeforeUpdate = productBrandLocalizeRepository.findAll().size();

        // Create the ProductBrandLocalize
        ProductBrandLocalizeDTO productBrandLocalizeDTO = productBrandLocalizeMapper.toDto(productBrandLocalize);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductBrandLocalizeMockMvc.perform(put("/api/product-brand-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productBrandLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductBrandLocalize in the database
        List<ProductBrandLocalize> productBrandLocalizeList = productBrandLocalizeRepository.findAll();
        assertThat(productBrandLocalizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductBrandLocalize() throws Exception {
        // Initialize the database
        productBrandLocalizeRepository.saveAndFlush(productBrandLocalize);

        int databaseSizeBeforeDelete = productBrandLocalizeRepository.findAll().size();

        // Delete the productBrandLocalize
        restProductBrandLocalizeMockMvc.perform(delete("/api/product-brand-localizes/{id}", productBrandLocalize.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductBrandLocalize> productBrandLocalizeList = productBrandLocalizeRepository.findAll();
        assertThat(productBrandLocalizeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.ProductDocumentsCulture;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.ProductDocuments;
import com.vertical.commerce.repository.ProductDocumentsCultureRepository;
import com.vertical.commerce.service.ProductDocumentsCultureService;
import com.vertical.commerce.service.dto.ProductDocumentsCultureDTO;
import com.vertical.commerce.service.mapper.ProductDocumentsCultureMapper;
import com.vertical.commerce.service.dto.ProductDocumentsCultureCriteria;
import com.vertical.commerce.service.ProductDocumentsCultureQueryService;

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
 * Integration tests for the {@link ProductDocumentsCultureResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ProductDocumentsCultureResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ProductDocumentsCultureRepository productDocumentsCultureRepository;

    @Autowired
    private ProductDocumentsCultureMapper productDocumentsCultureMapper;

    @Autowired
    private ProductDocumentsCultureService productDocumentsCultureService;

    @Autowired
    private ProductDocumentsCultureQueryService productDocumentsCultureQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductDocumentsCultureMockMvc;

    private ProductDocumentsCulture productDocumentsCulture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductDocumentsCulture createEntity(EntityManager em) {
        ProductDocumentsCulture productDocumentsCulture = new ProductDocumentsCulture()
            .name(DEFAULT_NAME);
        return productDocumentsCulture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductDocumentsCulture createUpdatedEntity(EntityManager em) {
        ProductDocumentsCulture productDocumentsCulture = new ProductDocumentsCulture()
            .name(UPDATED_NAME);
        return productDocumentsCulture;
    }

    @BeforeEach
    public void initTest() {
        productDocumentsCulture = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductDocumentsCulture() throws Exception {
        int databaseSizeBeforeCreate = productDocumentsCultureRepository.findAll().size();
        // Create the ProductDocumentsCulture
        ProductDocumentsCultureDTO productDocumentsCultureDTO = productDocumentsCultureMapper.toDto(productDocumentsCulture);
        restProductDocumentsCultureMockMvc.perform(post("/api/product-documents-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentsCultureDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductDocumentsCulture in the database
        List<ProductDocumentsCulture> productDocumentsCultureList = productDocumentsCultureRepository.findAll();
        assertThat(productDocumentsCultureList).hasSize(databaseSizeBeforeCreate + 1);
        ProductDocumentsCulture testProductDocumentsCulture = productDocumentsCultureList.get(productDocumentsCultureList.size() - 1);
        assertThat(testProductDocumentsCulture.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createProductDocumentsCultureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productDocumentsCultureRepository.findAll().size();

        // Create the ProductDocumentsCulture with an existing ID
        productDocumentsCulture.setId(1L);
        ProductDocumentsCultureDTO productDocumentsCultureDTO = productDocumentsCultureMapper.toDto(productDocumentsCulture);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductDocumentsCultureMockMvc.perform(post("/api/product-documents-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentsCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductDocumentsCulture in the database
        List<ProductDocumentsCulture> productDocumentsCultureList = productDocumentsCultureRepository.findAll();
        assertThat(productDocumentsCultureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productDocumentsCultureRepository.findAll().size();
        // set the field null
        productDocumentsCulture.setName(null);

        // Create the ProductDocumentsCulture, which fails.
        ProductDocumentsCultureDTO productDocumentsCultureDTO = productDocumentsCultureMapper.toDto(productDocumentsCulture);


        restProductDocumentsCultureMockMvc.perform(post("/api/product-documents-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentsCultureDTO)))
            .andExpect(status().isBadRequest());

        List<ProductDocumentsCulture> productDocumentsCultureList = productDocumentsCultureRepository.findAll();
        assertThat(productDocumentsCultureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsCultures() throws Exception {
        // Initialize the database
        productDocumentsCultureRepository.saveAndFlush(productDocumentsCulture);

        // Get all the productDocumentsCultureList
        restProductDocumentsCultureMockMvc.perform(get("/api/product-documents-cultures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productDocumentsCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getProductDocumentsCulture() throws Exception {
        // Initialize the database
        productDocumentsCultureRepository.saveAndFlush(productDocumentsCulture);

        // Get the productDocumentsCulture
        restProductDocumentsCultureMockMvc.perform(get("/api/product-documents-cultures/{id}", productDocumentsCulture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productDocumentsCulture.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getProductDocumentsCulturesByIdFiltering() throws Exception {
        // Initialize the database
        productDocumentsCultureRepository.saveAndFlush(productDocumentsCulture);

        Long id = productDocumentsCulture.getId();

        defaultProductDocumentsCultureShouldBeFound("id.equals=" + id);
        defaultProductDocumentsCultureShouldNotBeFound("id.notEquals=" + id);

        defaultProductDocumentsCultureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductDocumentsCultureShouldNotBeFound("id.greaterThan=" + id);

        defaultProductDocumentsCultureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductDocumentsCultureShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsCulturesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsCultureRepository.saveAndFlush(productDocumentsCulture);

        // Get all the productDocumentsCultureList where name equals to DEFAULT_NAME
        defaultProductDocumentsCultureShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productDocumentsCultureList where name equals to UPDATED_NAME
        defaultProductDocumentsCultureShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsCulturesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsCultureRepository.saveAndFlush(productDocumentsCulture);

        // Get all the productDocumentsCultureList where name not equals to DEFAULT_NAME
        defaultProductDocumentsCultureShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productDocumentsCultureList where name not equals to UPDATED_NAME
        defaultProductDocumentsCultureShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsCulturesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentsCultureRepository.saveAndFlush(productDocumentsCulture);

        // Get all the productDocumentsCultureList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductDocumentsCultureShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productDocumentsCultureList where name equals to UPDATED_NAME
        defaultProductDocumentsCultureShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsCulturesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentsCultureRepository.saveAndFlush(productDocumentsCulture);

        // Get all the productDocumentsCultureList where name is not null
        defaultProductDocumentsCultureShouldBeFound("name.specified=true");

        // Get all the productDocumentsCultureList where name is null
        defaultProductDocumentsCultureShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsCulturesByNameContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsCultureRepository.saveAndFlush(productDocumentsCulture);

        // Get all the productDocumentsCultureList where name contains DEFAULT_NAME
        defaultProductDocumentsCultureShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productDocumentsCultureList where name contains UPDATED_NAME
        defaultProductDocumentsCultureShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsCulturesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsCultureRepository.saveAndFlush(productDocumentsCulture);

        // Get all the productDocumentsCultureList where name does not contain DEFAULT_NAME
        defaultProductDocumentsCultureShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productDocumentsCultureList where name does not contain UPDATED_NAME
        defaultProductDocumentsCultureShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsCulturesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsCultureRepository.saveAndFlush(productDocumentsCulture);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        productDocumentsCulture.setCulture(culture);
        productDocumentsCultureRepository.saveAndFlush(productDocumentsCulture);
        Long cultureId = culture.getId();

        // Get all the productDocumentsCultureList where culture equals to cultureId
        defaultProductDocumentsCultureShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the productDocumentsCultureList where culture equals to cultureId + 1
        defaultProductDocumentsCultureShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllProductDocumentsCulturesByProductDocumentIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsCultureRepository.saveAndFlush(productDocumentsCulture);
        ProductDocuments productDocument = ProductDocumentsResourceIT.createEntity(em);
        em.persist(productDocument);
        em.flush();
        productDocumentsCulture.setProductDocument(productDocument);
        productDocumentsCultureRepository.saveAndFlush(productDocumentsCulture);
        Long productDocumentId = productDocument.getId();

        // Get all the productDocumentsCultureList where productDocument equals to productDocumentId
        defaultProductDocumentsCultureShouldBeFound("productDocumentId.equals=" + productDocumentId);

        // Get all the productDocumentsCultureList where productDocument equals to productDocumentId + 1
        defaultProductDocumentsCultureShouldNotBeFound("productDocumentId.equals=" + (productDocumentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductDocumentsCultureShouldBeFound(String filter) throws Exception {
        restProductDocumentsCultureMockMvc.perform(get("/api/product-documents-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productDocumentsCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restProductDocumentsCultureMockMvc.perform(get("/api/product-documents-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductDocumentsCultureShouldNotBeFound(String filter) throws Exception {
        restProductDocumentsCultureMockMvc.perform(get("/api/product-documents-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductDocumentsCultureMockMvc.perform(get("/api/product-documents-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductDocumentsCulture() throws Exception {
        // Get the productDocumentsCulture
        restProductDocumentsCultureMockMvc.perform(get("/api/product-documents-cultures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductDocumentsCulture() throws Exception {
        // Initialize the database
        productDocumentsCultureRepository.saveAndFlush(productDocumentsCulture);

        int databaseSizeBeforeUpdate = productDocumentsCultureRepository.findAll().size();

        // Update the productDocumentsCulture
        ProductDocumentsCulture updatedProductDocumentsCulture = productDocumentsCultureRepository.findById(productDocumentsCulture.getId()).get();
        // Disconnect from session so that the updates on updatedProductDocumentsCulture are not directly saved in db
        em.detach(updatedProductDocumentsCulture);
        updatedProductDocumentsCulture
            .name(UPDATED_NAME);
        ProductDocumentsCultureDTO productDocumentsCultureDTO = productDocumentsCultureMapper.toDto(updatedProductDocumentsCulture);

        restProductDocumentsCultureMockMvc.perform(put("/api/product-documents-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentsCultureDTO)))
            .andExpect(status().isOk());

        // Validate the ProductDocumentsCulture in the database
        List<ProductDocumentsCulture> productDocumentsCultureList = productDocumentsCultureRepository.findAll();
        assertThat(productDocumentsCultureList).hasSize(databaseSizeBeforeUpdate);
        ProductDocumentsCulture testProductDocumentsCulture = productDocumentsCultureList.get(productDocumentsCultureList.size() - 1);
        assertThat(testProductDocumentsCulture.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingProductDocumentsCulture() throws Exception {
        int databaseSizeBeforeUpdate = productDocumentsCultureRepository.findAll().size();

        // Create the ProductDocumentsCulture
        ProductDocumentsCultureDTO productDocumentsCultureDTO = productDocumentsCultureMapper.toDto(productDocumentsCulture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductDocumentsCultureMockMvc.perform(put("/api/product-documents-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentsCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductDocumentsCulture in the database
        List<ProductDocumentsCulture> productDocumentsCultureList = productDocumentsCultureRepository.findAll();
        assertThat(productDocumentsCultureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductDocumentsCulture() throws Exception {
        // Initialize the database
        productDocumentsCultureRepository.saveAndFlush(productDocumentsCulture);

        int databaseSizeBeforeDelete = productDocumentsCultureRepository.findAll().size();

        // Delete the productDocumentsCulture
        restProductDocumentsCultureMockMvc.perform(delete("/api/product-documents-cultures/{id}", productDocumentsCulture.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductDocumentsCulture> productDocumentsCultureList = productDocumentsCultureRepository.findAll();
        assertThat(productDocumentsCultureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.ProductTags;
import com.vertical.commerce.repository.ProductTagsRepository;
import com.vertical.commerce.service.ProductTagsService;
import com.vertical.commerce.service.dto.ProductTagsDTO;
import com.vertical.commerce.service.mapper.ProductTagsMapper;
import com.vertical.commerce.service.dto.ProductTagsCriteria;
import com.vertical.commerce.service.ProductTagsQueryService;

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
 * Integration tests for the {@link ProductTagsResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ProductTagsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ProductTagsRepository productTagsRepository;

    @Autowired
    private ProductTagsMapper productTagsMapper;

    @Autowired
    private ProductTagsService productTagsService;

    @Autowired
    private ProductTagsQueryService productTagsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductTagsMockMvc;

    private ProductTags productTags;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductTags createEntity(EntityManager em) {
        ProductTags productTags = new ProductTags()
            .name(DEFAULT_NAME);
        return productTags;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductTags createUpdatedEntity(EntityManager em) {
        ProductTags productTags = new ProductTags()
            .name(UPDATED_NAME);
        return productTags;
    }

    @BeforeEach
    public void initTest() {
        productTags = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductTags() throws Exception {
        int databaseSizeBeforeCreate = productTagsRepository.findAll().size();
        // Create the ProductTags
        ProductTagsDTO productTagsDTO = productTagsMapper.toDto(productTags);
        restProductTagsMockMvc.perform(post("/api/product-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productTagsDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductTags in the database
        List<ProductTags> productTagsList = productTagsRepository.findAll();
        assertThat(productTagsList).hasSize(databaseSizeBeforeCreate + 1);
        ProductTags testProductTags = productTagsList.get(productTagsList.size() - 1);
        assertThat(testProductTags.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createProductTagsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productTagsRepository.findAll().size();

        // Create the ProductTags with an existing ID
        productTags.setId(1L);
        ProductTagsDTO productTagsDTO = productTagsMapper.toDto(productTags);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductTagsMockMvc.perform(post("/api/product-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productTagsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductTags in the database
        List<ProductTags> productTagsList = productTagsRepository.findAll();
        assertThat(productTagsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productTagsRepository.findAll().size();
        // set the field null
        productTags.setName(null);

        // Create the ProductTags, which fails.
        ProductTagsDTO productTagsDTO = productTagsMapper.toDto(productTags);


        restProductTagsMockMvc.perform(post("/api/product-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productTagsDTO)))
            .andExpect(status().isBadRequest());

        List<ProductTags> productTagsList = productTagsRepository.findAll();
        assertThat(productTagsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductTags() throws Exception {
        // Initialize the database
        productTagsRepository.saveAndFlush(productTags);

        // Get all the productTagsList
        restProductTagsMockMvc.perform(get("/api/product-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productTags.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getProductTags() throws Exception {
        // Initialize the database
        productTagsRepository.saveAndFlush(productTags);

        // Get the productTags
        restProductTagsMockMvc.perform(get("/api/product-tags/{id}", productTags.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productTags.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getProductTagsByIdFiltering() throws Exception {
        // Initialize the database
        productTagsRepository.saveAndFlush(productTags);

        Long id = productTags.getId();

        defaultProductTagsShouldBeFound("id.equals=" + id);
        defaultProductTagsShouldNotBeFound("id.notEquals=" + id);

        defaultProductTagsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductTagsShouldNotBeFound("id.greaterThan=" + id);

        defaultProductTagsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductTagsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductTagsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productTagsRepository.saveAndFlush(productTags);

        // Get all the productTagsList where name equals to DEFAULT_NAME
        defaultProductTagsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productTagsList where name equals to UPDATED_NAME
        defaultProductTagsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductTagsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTagsRepository.saveAndFlush(productTags);

        // Get all the productTagsList where name not equals to DEFAULT_NAME
        defaultProductTagsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productTagsList where name not equals to UPDATED_NAME
        defaultProductTagsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductTagsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productTagsRepository.saveAndFlush(productTags);

        // Get all the productTagsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductTagsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productTagsList where name equals to UPDATED_NAME
        defaultProductTagsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductTagsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTagsRepository.saveAndFlush(productTags);

        // Get all the productTagsList where name is not null
        defaultProductTagsShouldBeFound("name.specified=true");

        // Get all the productTagsList where name is null
        defaultProductTagsShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductTagsByNameContainsSomething() throws Exception {
        // Initialize the database
        productTagsRepository.saveAndFlush(productTags);

        // Get all the productTagsList where name contains DEFAULT_NAME
        defaultProductTagsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productTagsList where name contains UPDATED_NAME
        defaultProductTagsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductTagsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productTagsRepository.saveAndFlush(productTags);

        // Get all the productTagsList where name does not contain DEFAULT_NAME
        defaultProductTagsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productTagsList where name does not contain UPDATED_NAME
        defaultProductTagsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductTagsShouldBeFound(String filter) throws Exception {
        restProductTagsMockMvc.perform(get("/api/product-tags?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productTags.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restProductTagsMockMvc.perform(get("/api/product-tags/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductTagsShouldNotBeFound(String filter) throws Exception {
        restProductTagsMockMvc.perform(get("/api/product-tags?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductTagsMockMvc.perform(get("/api/product-tags/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductTags() throws Exception {
        // Get the productTags
        restProductTagsMockMvc.perform(get("/api/product-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductTags() throws Exception {
        // Initialize the database
        productTagsRepository.saveAndFlush(productTags);

        int databaseSizeBeforeUpdate = productTagsRepository.findAll().size();

        // Update the productTags
        ProductTags updatedProductTags = productTagsRepository.findById(productTags.getId()).get();
        // Disconnect from session so that the updates on updatedProductTags are not directly saved in db
        em.detach(updatedProductTags);
        updatedProductTags
            .name(UPDATED_NAME);
        ProductTagsDTO productTagsDTO = productTagsMapper.toDto(updatedProductTags);

        restProductTagsMockMvc.perform(put("/api/product-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productTagsDTO)))
            .andExpect(status().isOk());

        // Validate the ProductTags in the database
        List<ProductTags> productTagsList = productTagsRepository.findAll();
        assertThat(productTagsList).hasSize(databaseSizeBeforeUpdate);
        ProductTags testProductTags = productTagsList.get(productTagsList.size() - 1);
        assertThat(testProductTags.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingProductTags() throws Exception {
        int databaseSizeBeforeUpdate = productTagsRepository.findAll().size();

        // Create the ProductTags
        ProductTagsDTO productTagsDTO = productTagsMapper.toDto(productTags);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductTagsMockMvc.perform(put("/api/product-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productTagsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductTags in the database
        List<ProductTags> productTagsList = productTagsRepository.findAll();
        assertThat(productTagsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductTags() throws Exception {
        // Initialize the database
        productTagsRepository.saveAndFlush(productTags);

        int databaseSizeBeforeDelete = productTagsRepository.findAll().size();

        // Delete the productTags
        restProductTagsMockMvc.perform(delete("/api/product-tags/{id}", productTags.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductTags> productTagsList = productTagsRepository.findAll();
        assertThat(productTagsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

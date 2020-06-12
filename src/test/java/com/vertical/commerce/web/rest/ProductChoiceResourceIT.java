package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.ProductChoice;
import com.vertical.commerce.domain.ProductCategory;
import com.vertical.commerce.domain.ProductAttributeSet;
import com.vertical.commerce.domain.ProductOptionSet;
import com.vertical.commerce.repository.ProductChoiceRepository;
import com.vertical.commerce.service.ProductChoiceService;
import com.vertical.commerce.service.dto.ProductChoiceDTO;
import com.vertical.commerce.service.mapper.ProductChoiceMapper;
import com.vertical.commerce.service.dto.ProductChoiceCriteria;
import com.vertical.commerce.service.ProductChoiceQueryService;

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
 * Integration tests for the {@link ProductChoiceResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ProductChoiceResourceIT {

    private static final Boolean DEFAULT_IS_MULTIPLY = false;
    private static final Boolean UPDATED_IS_MULTIPLY = true;

    @Autowired
    private ProductChoiceRepository productChoiceRepository;

    @Autowired
    private ProductChoiceMapper productChoiceMapper;

    @Autowired
    private ProductChoiceService productChoiceService;

    @Autowired
    private ProductChoiceQueryService productChoiceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductChoiceMockMvc;

    private ProductChoice productChoice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductChoice createEntity(EntityManager em) {
        ProductChoice productChoice = new ProductChoice()
            .isMultiply(DEFAULT_IS_MULTIPLY);
        return productChoice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductChoice createUpdatedEntity(EntityManager em) {
        ProductChoice productChoice = new ProductChoice()
            .isMultiply(UPDATED_IS_MULTIPLY);
        return productChoice;
    }

    @BeforeEach
    public void initTest() {
        productChoice = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductChoice() throws Exception {
        int databaseSizeBeforeCreate = productChoiceRepository.findAll().size();
        // Create the ProductChoice
        ProductChoiceDTO productChoiceDTO = productChoiceMapper.toDto(productChoice);
        restProductChoiceMockMvc.perform(post("/api/product-choices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productChoiceDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductChoice in the database
        List<ProductChoice> productChoiceList = productChoiceRepository.findAll();
        assertThat(productChoiceList).hasSize(databaseSizeBeforeCreate + 1);
        ProductChoice testProductChoice = productChoiceList.get(productChoiceList.size() - 1);
        assertThat(testProductChoice.isIsMultiply()).isEqualTo(DEFAULT_IS_MULTIPLY);
    }

    @Test
    @Transactional
    public void createProductChoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productChoiceRepository.findAll().size();

        // Create the ProductChoice with an existing ID
        productChoice.setId(1L);
        ProductChoiceDTO productChoiceDTO = productChoiceMapper.toDto(productChoice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductChoiceMockMvc.perform(post("/api/product-choices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productChoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductChoice in the database
        List<ProductChoice> productChoiceList = productChoiceRepository.findAll();
        assertThat(productChoiceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIsMultiplyIsRequired() throws Exception {
        int databaseSizeBeforeTest = productChoiceRepository.findAll().size();
        // set the field null
        productChoice.setIsMultiply(null);

        // Create the ProductChoice, which fails.
        ProductChoiceDTO productChoiceDTO = productChoiceMapper.toDto(productChoice);


        restProductChoiceMockMvc.perform(post("/api/product-choices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productChoiceDTO)))
            .andExpect(status().isBadRequest());

        List<ProductChoice> productChoiceList = productChoiceRepository.findAll();
        assertThat(productChoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductChoices() throws Exception {
        // Initialize the database
        productChoiceRepository.saveAndFlush(productChoice);

        // Get all the productChoiceList
        restProductChoiceMockMvc.perform(get("/api/product-choices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productChoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].isMultiply").value(hasItem(DEFAULT_IS_MULTIPLY.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getProductChoice() throws Exception {
        // Initialize the database
        productChoiceRepository.saveAndFlush(productChoice);

        // Get the productChoice
        restProductChoiceMockMvc.perform(get("/api/product-choices/{id}", productChoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productChoice.getId().intValue()))
            .andExpect(jsonPath("$.isMultiply").value(DEFAULT_IS_MULTIPLY.booleanValue()));
    }


    @Test
    @Transactional
    public void getProductChoicesByIdFiltering() throws Exception {
        // Initialize the database
        productChoiceRepository.saveAndFlush(productChoice);

        Long id = productChoice.getId();

        defaultProductChoiceShouldBeFound("id.equals=" + id);
        defaultProductChoiceShouldNotBeFound("id.notEquals=" + id);

        defaultProductChoiceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductChoiceShouldNotBeFound("id.greaterThan=" + id);

        defaultProductChoiceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductChoiceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductChoicesByIsMultiplyIsEqualToSomething() throws Exception {
        // Initialize the database
        productChoiceRepository.saveAndFlush(productChoice);

        // Get all the productChoiceList where isMultiply equals to DEFAULT_IS_MULTIPLY
        defaultProductChoiceShouldBeFound("isMultiply.equals=" + DEFAULT_IS_MULTIPLY);

        // Get all the productChoiceList where isMultiply equals to UPDATED_IS_MULTIPLY
        defaultProductChoiceShouldNotBeFound("isMultiply.equals=" + UPDATED_IS_MULTIPLY);
    }

    @Test
    @Transactional
    public void getAllProductChoicesByIsMultiplyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productChoiceRepository.saveAndFlush(productChoice);

        // Get all the productChoiceList where isMultiply not equals to DEFAULT_IS_MULTIPLY
        defaultProductChoiceShouldNotBeFound("isMultiply.notEquals=" + DEFAULT_IS_MULTIPLY);

        // Get all the productChoiceList where isMultiply not equals to UPDATED_IS_MULTIPLY
        defaultProductChoiceShouldBeFound("isMultiply.notEquals=" + UPDATED_IS_MULTIPLY);
    }

    @Test
    @Transactional
    public void getAllProductChoicesByIsMultiplyIsInShouldWork() throws Exception {
        // Initialize the database
        productChoiceRepository.saveAndFlush(productChoice);

        // Get all the productChoiceList where isMultiply in DEFAULT_IS_MULTIPLY or UPDATED_IS_MULTIPLY
        defaultProductChoiceShouldBeFound("isMultiply.in=" + DEFAULT_IS_MULTIPLY + "," + UPDATED_IS_MULTIPLY);

        // Get all the productChoiceList where isMultiply equals to UPDATED_IS_MULTIPLY
        defaultProductChoiceShouldNotBeFound("isMultiply.in=" + UPDATED_IS_MULTIPLY);
    }

    @Test
    @Transactional
    public void getAllProductChoicesByIsMultiplyIsNullOrNotNull() throws Exception {
        // Initialize the database
        productChoiceRepository.saveAndFlush(productChoice);

        // Get all the productChoiceList where isMultiply is not null
        defaultProductChoiceShouldBeFound("isMultiply.specified=true");

        // Get all the productChoiceList where isMultiply is null
        defaultProductChoiceShouldNotBeFound("isMultiply.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductChoicesByProductCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        productChoiceRepository.saveAndFlush(productChoice);
        ProductCategory productCategory = ProductCategoryResourceIT.createEntity(em);
        em.persist(productCategory);
        em.flush();
        productChoice.setProductCategory(productCategory);
        productChoiceRepository.saveAndFlush(productChoice);
        Long productCategoryId = productCategory.getId();

        // Get all the productChoiceList where productCategory equals to productCategoryId
        defaultProductChoiceShouldBeFound("productCategoryId.equals=" + productCategoryId);

        // Get all the productChoiceList where productCategory equals to productCategoryId + 1
        defaultProductChoiceShouldNotBeFound("productCategoryId.equals=" + (productCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllProductChoicesByProductAttributeSetIsEqualToSomething() throws Exception {
        // Initialize the database
        productChoiceRepository.saveAndFlush(productChoice);
        ProductAttributeSet productAttributeSet = ProductAttributeSetResourceIT.createEntity(em);
        em.persist(productAttributeSet);
        em.flush();
        productChoice.setProductAttributeSet(productAttributeSet);
        productChoiceRepository.saveAndFlush(productChoice);
        Long productAttributeSetId = productAttributeSet.getId();

        // Get all the productChoiceList where productAttributeSet equals to productAttributeSetId
        defaultProductChoiceShouldBeFound("productAttributeSetId.equals=" + productAttributeSetId);

        // Get all the productChoiceList where productAttributeSet equals to productAttributeSetId + 1
        defaultProductChoiceShouldNotBeFound("productAttributeSetId.equals=" + (productAttributeSetId + 1));
    }


    @Test
    @Transactional
    public void getAllProductChoicesByProductOptionSetIsEqualToSomething() throws Exception {
        // Initialize the database
        productChoiceRepository.saveAndFlush(productChoice);
        ProductOptionSet productOptionSet = ProductOptionSetResourceIT.createEntity(em);
        em.persist(productOptionSet);
        em.flush();
        productChoice.setProductOptionSet(productOptionSet);
        productChoiceRepository.saveAndFlush(productChoice);
        Long productOptionSetId = productOptionSet.getId();

        // Get all the productChoiceList where productOptionSet equals to productOptionSetId
        defaultProductChoiceShouldBeFound("productOptionSetId.equals=" + productOptionSetId);

        // Get all the productChoiceList where productOptionSet equals to productOptionSetId + 1
        defaultProductChoiceShouldNotBeFound("productOptionSetId.equals=" + (productOptionSetId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductChoiceShouldBeFound(String filter) throws Exception {
        restProductChoiceMockMvc.perform(get("/api/product-choices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productChoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].isMultiply").value(hasItem(DEFAULT_IS_MULTIPLY.booleanValue())));

        // Check, that the count call also returns 1
        restProductChoiceMockMvc.perform(get("/api/product-choices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductChoiceShouldNotBeFound(String filter) throws Exception {
        restProductChoiceMockMvc.perform(get("/api/product-choices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductChoiceMockMvc.perform(get("/api/product-choices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductChoice() throws Exception {
        // Get the productChoice
        restProductChoiceMockMvc.perform(get("/api/product-choices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductChoice() throws Exception {
        // Initialize the database
        productChoiceRepository.saveAndFlush(productChoice);

        int databaseSizeBeforeUpdate = productChoiceRepository.findAll().size();

        // Update the productChoice
        ProductChoice updatedProductChoice = productChoiceRepository.findById(productChoice.getId()).get();
        // Disconnect from session so that the updates on updatedProductChoice are not directly saved in db
        em.detach(updatedProductChoice);
        updatedProductChoice
            .isMultiply(UPDATED_IS_MULTIPLY);
        ProductChoiceDTO productChoiceDTO = productChoiceMapper.toDto(updatedProductChoice);

        restProductChoiceMockMvc.perform(put("/api/product-choices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productChoiceDTO)))
            .andExpect(status().isOk());

        // Validate the ProductChoice in the database
        List<ProductChoice> productChoiceList = productChoiceRepository.findAll();
        assertThat(productChoiceList).hasSize(databaseSizeBeforeUpdate);
        ProductChoice testProductChoice = productChoiceList.get(productChoiceList.size() - 1);
        assertThat(testProductChoice.isIsMultiply()).isEqualTo(UPDATED_IS_MULTIPLY);
    }

    @Test
    @Transactional
    public void updateNonExistingProductChoice() throws Exception {
        int databaseSizeBeforeUpdate = productChoiceRepository.findAll().size();

        // Create the ProductChoice
        ProductChoiceDTO productChoiceDTO = productChoiceMapper.toDto(productChoice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductChoiceMockMvc.perform(put("/api/product-choices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productChoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductChoice in the database
        List<ProductChoice> productChoiceList = productChoiceRepository.findAll();
        assertThat(productChoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductChoice() throws Exception {
        // Initialize the database
        productChoiceRepository.saveAndFlush(productChoice);

        int databaseSizeBeforeDelete = productChoiceRepository.findAll().size();

        // Delete the productChoice
        restProductChoiceMockMvc.perform(delete("/api/product-choices/{id}", productChoice.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductChoice> productChoiceList = productChoiceRepository.findAll();
        assertThat(productChoiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

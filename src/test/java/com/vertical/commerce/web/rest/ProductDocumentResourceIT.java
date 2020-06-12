package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.ProductDocument;
import com.vertical.commerce.domain.WarrantyTypes;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.Products;
import com.vertical.commerce.repository.ProductDocumentRepository;
import com.vertical.commerce.service.ProductDocumentService;
import com.vertical.commerce.service.dto.ProductDocumentDTO;
import com.vertical.commerce.service.mapper.ProductDocumentMapper;
import com.vertical.commerce.service.dto.ProductDocumentCriteria;
import com.vertical.commerce.service.ProductDocumentQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@link ProductDocumentResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ProductDocumentResourceIT {

    private static final String DEFAULT_VIDEO_URL = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_HIGHLIGHTS = "AAAAAAAAAA";
    private static final String UPDATED_HIGHLIGHTS = "BBBBBBBBBB";

    private static final String DEFAULT_LONG_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_LONG_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_WHAT_IN_THE_BOX = "AAAAAAAAAA";
    private static final String UPDATED_WHAT_IN_THE_BOX = "BBBBBBBBBB";

    private static final String DEFAULT_CARE_INSTRUCTIONS = "AAAAAAAAAA";
    private static final String UPDATED_CARE_INSTRUCTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MODEL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MODEL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FABRIC_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FABRIC_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIAL_FEATURES = "AAAAAAAAAA";
    private static final String UPDATED_SPECIAL_FEATURES = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GENUINE_AND_LEGAL = false;
    private static final Boolean UPDATED_GENUINE_AND_LEGAL = true;

    private static final String DEFAULT_COUNTRY_OF_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_OF_ORIGIN = "BBBBBBBBBB";

    private static final String DEFAULT_USAGE_AND_SIDE_EFFECTS = "AAAAAAAAAA";
    private static final String UPDATED_USAGE_AND_SIDE_EFFECTS = "BBBBBBBBBB";

    private static final String DEFAULT_SAFETY_WARNNING = "AAAAAAAAAA";
    private static final String UPDATED_SAFETY_WARNNING = "BBBBBBBBBB";

    private static final String DEFAULT_WARRANTY_PERIOD = "AAAAAAAAAA";
    private static final String UPDATED_WARRANTY_PERIOD = "BBBBBBBBBB";

    private static final String DEFAULT_WARRANTY_POLICY = "AAAAAAAAAA";
    private static final String UPDATED_WARRANTY_POLICY = "BBBBBBBBBB";

    private static final String DEFAULT_DANGEROUS_GOODS = "AAAAAAAAAA";
    private static final String UPDATED_DANGEROUS_GOODS = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProductDocumentRepository productDocumentRepository;

    @Autowired
    private ProductDocumentMapper productDocumentMapper;

    @Autowired
    private ProductDocumentService productDocumentService;

    @Autowired
    private ProductDocumentQueryService productDocumentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductDocumentMockMvc;

    private ProductDocument productDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductDocument createEntity(EntityManager em) {
        ProductDocument productDocument = new ProductDocument()
            .videoUrl(DEFAULT_VIDEO_URL)
            .highlights(DEFAULT_HIGHLIGHTS)
            .longDescription(DEFAULT_LONG_DESCRIPTION)
            .shortDescription(DEFAULT_SHORT_DESCRIPTION)
            .whatInTheBox(DEFAULT_WHAT_IN_THE_BOX)
            .careInstructions(DEFAULT_CARE_INSTRUCTIONS)
            .productType(DEFAULT_PRODUCT_TYPE)
            .modelName(DEFAULT_MODEL_NAME)
            .modelNumber(DEFAULT_MODEL_NUMBER)
            .fabricType(DEFAULT_FABRIC_TYPE)
            .specialFeatures(DEFAULT_SPECIAL_FEATURES)
            .productComplianceCertificate(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE)
            .genuineAndLegal(DEFAULT_GENUINE_AND_LEGAL)
            .countryOfOrigin(DEFAULT_COUNTRY_OF_ORIGIN)
            .usageAndSideEffects(DEFAULT_USAGE_AND_SIDE_EFFECTS)
            .safetyWarnning(DEFAULT_SAFETY_WARNNING)
            .warrantyPeriod(DEFAULT_WARRANTY_PERIOD)
            .warrantyPolicy(DEFAULT_WARRANTY_POLICY)
            .dangerousGoods(DEFAULT_DANGEROUS_GOODS)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return productDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductDocument createUpdatedEntity(EntityManager em) {
        ProductDocument productDocument = new ProductDocument()
            .videoUrl(UPDATED_VIDEO_URL)
            .highlights(UPDATED_HIGHLIGHTS)
            .longDescription(UPDATED_LONG_DESCRIPTION)
            .shortDescription(UPDATED_SHORT_DESCRIPTION)
            .whatInTheBox(UPDATED_WHAT_IN_THE_BOX)
            .careInstructions(UPDATED_CARE_INSTRUCTIONS)
            .productType(UPDATED_PRODUCT_TYPE)
            .modelName(UPDATED_MODEL_NAME)
            .modelNumber(UPDATED_MODEL_NUMBER)
            .fabricType(UPDATED_FABRIC_TYPE)
            .specialFeatures(UPDATED_SPECIAL_FEATURES)
            .productComplianceCertificate(UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE)
            .genuineAndLegal(UPDATED_GENUINE_AND_LEGAL)
            .countryOfOrigin(UPDATED_COUNTRY_OF_ORIGIN)
            .usageAndSideEffects(UPDATED_USAGE_AND_SIDE_EFFECTS)
            .safetyWarnning(UPDATED_SAFETY_WARNNING)
            .warrantyPeriod(UPDATED_WARRANTY_PERIOD)
            .warrantyPolicy(UPDATED_WARRANTY_POLICY)
            .dangerousGoods(UPDATED_DANGEROUS_GOODS)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return productDocument;
    }

    @BeforeEach
    public void initTest() {
        productDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductDocument() throws Exception {
        int databaseSizeBeforeCreate = productDocumentRepository.findAll().size();
        // Create the ProductDocument
        ProductDocumentDTO productDocumentDTO = productDocumentMapper.toDto(productDocument);
        restProductDocumentMockMvc.perform(post("/api/product-documents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductDocument in the database
        List<ProductDocument> productDocumentList = productDocumentRepository.findAll();
        assertThat(productDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        ProductDocument testProductDocument = productDocumentList.get(productDocumentList.size() - 1);
        assertThat(testProductDocument.getVideoUrl()).isEqualTo(DEFAULT_VIDEO_URL);
        assertThat(testProductDocument.getHighlights()).isEqualTo(DEFAULT_HIGHLIGHTS);
        assertThat(testProductDocument.getLongDescription()).isEqualTo(DEFAULT_LONG_DESCRIPTION);
        assertThat(testProductDocument.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testProductDocument.getWhatInTheBox()).isEqualTo(DEFAULT_WHAT_IN_THE_BOX);
        assertThat(testProductDocument.getCareInstructions()).isEqualTo(DEFAULT_CARE_INSTRUCTIONS);
        assertThat(testProductDocument.getProductType()).isEqualTo(DEFAULT_PRODUCT_TYPE);
        assertThat(testProductDocument.getModelName()).isEqualTo(DEFAULT_MODEL_NAME);
        assertThat(testProductDocument.getModelNumber()).isEqualTo(DEFAULT_MODEL_NUMBER);
        assertThat(testProductDocument.getFabricType()).isEqualTo(DEFAULT_FABRIC_TYPE);
        assertThat(testProductDocument.getSpecialFeatures()).isEqualTo(DEFAULT_SPECIAL_FEATURES);
        assertThat(testProductDocument.getProductComplianceCertificate()).isEqualTo(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE);
        assertThat(testProductDocument.isGenuineAndLegal()).isEqualTo(DEFAULT_GENUINE_AND_LEGAL);
        assertThat(testProductDocument.getCountryOfOrigin()).isEqualTo(DEFAULT_COUNTRY_OF_ORIGIN);
        assertThat(testProductDocument.getUsageAndSideEffects()).isEqualTo(DEFAULT_USAGE_AND_SIDE_EFFECTS);
        assertThat(testProductDocument.getSafetyWarnning()).isEqualTo(DEFAULT_SAFETY_WARNNING);
        assertThat(testProductDocument.getWarrantyPeriod()).isEqualTo(DEFAULT_WARRANTY_PERIOD);
        assertThat(testProductDocument.getWarrantyPolicy()).isEqualTo(DEFAULT_WARRANTY_POLICY);
        assertThat(testProductDocument.getDangerousGoods()).isEqualTo(DEFAULT_DANGEROUS_GOODS);
        assertThat(testProductDocument.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testProductDocument.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createProductDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productDocumentRepository.findAll().size();

        // Create the ProductDocument with an existing ID
        productDocument.setId(1L);
        ProductDocumentDTO productDocumentDTO = productDocumentMapper.toDto(productDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductDocumentMockMvc.perform(post("/api/product-documents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductDocument in the database
        List<ProductDocument> productDocumentList = productDocumentRepository.findAll();
        assertThat(productDocumentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLastEditedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = productDocumentRepository.findAll().size();
        // set the field null
        productDocument.setLastEditedBy(null);

        // Create the ProductDocument, which fails.
        ProductDocumentDTO productDocumentDTO = productDocumentMapper.toDto(productDocument);


        restProductDocumentMockMvc.perform(post("/api/product-documents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<ProductDocument> productDocumentList = productDocumentRepository.findAll();
        assertThat(productDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = productDocumentRepository.findAll().size();
        // set the field null
        productDocument.setLastEditedWhen(null);

        // Create the ProductDocument, which fails.
        ProductDocumentDTO productDocumentDTO = productDocumentMapper.toDto(productDocument);


        restProductDocumentMockMvc.perform(post("/api/product-documents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<ProductDocument> productDocumentList = productDocumentRepository.findAll();
        assertThat(productDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductDocuments() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList
        restProductDocumentMockMvc.perform(get("/api/product-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].videoUrl").value(hasItem(DEFAULT_VIDEO_URL)))
            .andExpect(jsonPath("$.[*].highlights").value(hasItem(DEFAULT_HIGHLIGHTS.toString())))
            .andExpect(jsonPath("$.[*].longDescription").value(hasItem(DEFAULT_LONG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].whatInTheBox").value(hasItem(DEFAULT_WHAT_IN_THE_BOX.toString())))
            .andExpect(jsonPath("$.[*].careInstructions").value(hasItem(DEFAULT_CARE_INSTRUCTIONS.toString())))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE)))
            .andExpect(jsonPath("$.[*].modelName").value(hasItem(DEFAULT_MODEL_NAME)))
            .andExpect(jsonPath("$.[*].modelNumber").value(hasItem(DEFAULT_MODEL_NUMBER)))
            .andExpect(jsonPath("$.[*].fabricType").value(hasItem(DEFAULT_FABRIC_TYPE)))
            .andExpect(jsonPath("$.[*].specialFeatures").value(hasItem(DEFAULT_SPECIAL_FEATURES.toString())))
            .andExpect(jsonPath("$.[*].productComplianceCertificate").value(hasItem(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE)))
            .andExpect(jsonPath("$.[*].genuineAndLegal").value(hasItem(DEFAULT_GENUINE_AND_LEGAL.booleanValue())))
            .andExpect(jsonPath("$.[*].countryOfOrigin").value(hasItem(DEFAULT_COUNTRY_OF_ORIGIN)))
            .andExpect(jsonPath("$.[*].usageAndSideEffects").value(hasItem(DEFAULT_USAGE_AND_SIDE_EFFECTS.toString())))
            .andExpect(jsonPath("$.[*].safetyWarnning").value(hasItem(DEFAULT_SAFETY_WARNNING.toString())))
            .andExpect(jsonPath("$.[*].warrantyPeriod").value(hasItem(DEFAULT_WARRANTY_PERIOD)))
            .andExpect(jsonPath("$.[*].warrantyPolicy").value(hasItem(DEFAULT_WARRANTY_POLICY)))
            .andExpect(jsonPath("$.[*].dangerousGoods").value(hasItem(DEFAULT_DANGEROUS_GOODS)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getProductDocument() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get the productDocument
        restProductDocumentMockMvc.perform(get("/api/product-documents/{id}", productDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productDocument.getId().intValue()))
            .andExpect(jsonPath("$.videoUrl").value(DEFAULT_VIDEO_URL))
            .andExpect(jsonPath("$.highlights").value(DEFAULT_HIGHLIGHTS.toString()))
            .andExpect(jsonPath("$.longDescription").value(DEFAULT_LONG_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.whatInTheBox").value(DEFAULT_WHAT_IN_THE_BOX.toString()))
            .andExpect(jsonPath("$.careInstructions").value(DEFAULT_CARE_INSTRUCTIONS.toString()))
            .andExpect(jsonPath("$.productType").value(DEFAULT_PRODUCT_TYPE))
            .andExpect(jsonPath("$.modelName").value(DEFAULT_MODEL_NAME))
            .andExpect(jsonPath("$.modelNumber").value(DEFAULT_MODEL_NUMBER))
            .andExpect(jsonPath("$.fabricType").value(DEFAULT_FABRIC_TYPE))
            .andExpect(jsonPath("$.specialFeatures").value(DEFAULT_SPECIAL_FEATURES.toString()))
            .andExpect(jsonPath("$.productComplianceCertificate").value(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE))
            .andExpect(jsonPath("$.genuineAndLegal").value(DEFAULT_GENUINE_AND_LEGAL.booleanValue()))
            .andExpect(jsonPath("$.countryOfOrigin").value(DEFAULT_COUNTRY_OF_ORIGIN))
            .andExpect(jsonPath("$.usageAndSideEffects").value(DEFAULT_USAGE_AND_SIDE_EFFECTS.toString()))
            .andExpect(jsonPath("$.safetyWarnning").value(DEFAULT_SAFETY_WARNNING.toString()))
            .andExpect(jsonPath("$.warrantyPeriod").value(DEFAULT_WARRANTY_PERIOD))
            .andExpect(jsonPath("$.warrantyPolicy").value(DEFAULT_WARRANTY_POLICY))
            .andExpect(jsonPath("$.dangerousGoods").value(DEFAULT_DANGEROUS_GOODS))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getProductDocumentsByIdFiltering() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        Long id = productDocument.getId();

        defaultProductDocumentShouldBeFound("id.equals=" + id);
        defaultProductDocumentShouldNotBeFound("id.notEquals=" + id);

        defaultProductDocumentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductDocumentShouldNotBeFound("id.greaterThan=" + id);

        defaultProductDocumentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductDocumentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByVideoUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where videoUrl equals to DEFAULT_VIDEO_URL
        defaultProductDocumentShouldBeFound("videoUrl.equals=" + DEFAULT_VIDEO_URL);

        // Get all the productDocumentList where videoUrl equals to UPDATED_VIDEO_URL
        defaultProductDocumentShouldNotBeFound("videoUrl.equals=" + UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByVideoUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where videoUrl not equals to DEFAULT_VIDEO_URL
        defaultProductDocumentShouldNotBeFound("videoUrl.notEquals=" + DEFAULT_VIDEO_URL);

        // Get all the productDocumentList where videoUrl not equals to UPDATED_VIDEO_URL
        defaultProductDocumentShouldBeFound("videoUrl.notEquals=" + UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByVideoUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where videoUrl in DEFAULT_VIDEO_URL or UPDATED_VIDEO_URL
        defaultProductDocumentShouldBeFound("videoUrl.in=" + DEFAULT_VIDEO_URL + "," + UPDATED_VIDEO_URL);

        // Get all the productDocumentList where videoUrl equals to UPDATED_VIDEO_URL
        defaultProductDocumentShouldNotBeFound("videoUrl.in=" + UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByVideoUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where videoUrl is not null
        defaultProductDocumentShouldBeFound("videoUrl.specified=true");

        // Get all the productDocumentList where videoUrl is null
        defaultProductDocumentShouldNotBeFound("videoUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByVideoUrlContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where videoUrl contains DEFAULT_VIDEO_URL
        defaultProductDocumentShouldBeFound("videoUrl.contains=" + DEFAULT_VIDEO_URL);

        // Get all the productDocumentList where videoUrl contains UPDATED_VIDEO_URL
        defaultProductDocumentShouldNotBeFound("videoUrl.contains=" + UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByVideoUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where videoUrl does not contain DEFAULT_VIDEO_URL
        defaultProductDocumentShouldNotBeFound("videoUrl.doesNotContain=" + DEFAULT_VIDEO_URL);

        // Get all the productDocumentList where videoUrl does not contain UPDATED_VIDEO_URL
        defaultProductDocumentShouldBeFound("videoUrl.doesNotContain=" + UPDATED_VIDEO_URL);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByProductTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productType equals to DEFAULT_PRODUCT_TYPE
        defaultProductDocumentShouldBeFound("productType.equals=" + DEFAULT_PRODUCT_TYPE);

        // Get all the productDocumentList where productType equals to UPDATED_PRODUCT_TYPE
        defaultProductDocumentShouldNotBeFound("productType.equals=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productType not equals to DEFAULT_PRODUCT_TYPE
        defaultProductDocumentShouldNotBeFound("productType.notEquals=" + DEFAULT_PRODUCT_TYPE);

        // Get all the productDocumentList where productType not equals to UPDATED_PRODUCT_TYPE
        defaultProductDocumentShouldBeFound("productType.notEquals=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productType in DEFAULT_PRODUCT_TYPE or UPDATED_PRODUCT_TYPE
        defaultProductDocumentShouldBeFound("productType.in=" + DEFAULT_PRODUCT_TYPE + "," + UPDATED_PRODUCT_TYPE);

        // Get all the productDocumentList where productType equals to UPDATED_PRODUCT_TYPE
        defaultProductDocumentShouldNotBeFound("productType.in=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productType is not null
        defaultProductDocumentShouldBeFound("productType.specified=true");

        // Get all the productDocumentList where productType is null
        defaultProductDocumentShouldNotBeFound("productType.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByProductTypeContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productType contains DEFAULT_PRODUCT_TYPE
        defaultProductDocumentShouldBeFound("productType.contains=" + DEFAULT_PRODUCT_TYPE);

        // Get all the productDocumentList where productType contains UPDATED_PRODUCT_TYPE
        defaultProductDocumentShouldNotBeFound("productType.contains=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductTypeNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productType does not contain DEFAULT_PRODUCT_TYPE
        defaultProductDocumentShouldNotBeFound("productType.doesNotContain=" + DEFAULT_PRODUCT_TYPE);

        // Get all the productDocumentList where productType does not contain UPDATED_PRODUCT_TYPE
        defaultProductDocumentShouldBeFound("productType.doesNotContain=" + UPDATED_PRODUCT_TYPE);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByModelNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelName equals to DEFAULT_MODEL_NAME
        defaultProductDocumentShouldBeFound("modelName.equals=" + DEFAULT_MODEL_NAME);

        // Get all the productDocumentList where modelName equals to UPDATED_MODEL_NAME
        defaultProductDocumentShouldNotBeFound("modelName.equals=" + UPDATED_MODEL_NAME);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelName not equals to DEFAULT_MODEL_NAME
        defaultProductDocumentShouldNotBeFound("modelName.notEquals=" + DEFAULT_MODEL_NAME);

        // Get all the productDocumentList where modelName not equals to UPDATED_MODEL_NAME
        defaultProductDocumentShouldBeFound("modelName.notEquals=" + UPDATED_MODEL_NAME);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNameIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelName in DEFAULT_MODEL_NAME or UPDATED_MODEL_NAME
        defaultProductDocumentShouldBeFound("modelName.in=" + DEFAULT_MODEL_NAME + "," + UPDATED_MODEL_NAME);

        // Get all the productDocumentList where modelName equals to UPDATED_MODEL_NAME
        defaultProductDocumentShouldNotBeFound("modelName.in=" + UPDATED_MODEL_NAME);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelName is not null
        defaultProductDocumentShouldBeFound("modelName.specified=true");

        // Get all the productDocumentList where modelName is null
        defaultProductDocumentShouldNotBeFound("modelName.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByModelNameContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelName contains DEFAULT_MODEL_NAME
        defaultProductDocumentShouldBeFound("modelName.contains=" + DEFAULT_MODEL_NAME);

        // Get all the productDocumentList where modelName contains UPDATED_MODEL_NAME
        defaultProductDocumentShouldNotBeFound("modelName.contains=" + UPDATED_MODEL_NAME);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNameNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelName does not contain DEFAULT_MODEL_NAME
        defaultProductDocumentShouldNotBeFound("modelName.doesNotContain=" + DEFAULT_MODEL_NAME);

        // Get all the productDocumentList where modelName does not contain UPDATED_MODEL_NAME
        defaultProductDocumentShouldBeFound("modelName.doesNotContain=" + UPDATED_MODEL_NAME);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByModelNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelNumber equals to DEFAULT_MODEL_NUMBER
        defaultProductDocumentShouldBeFound("modelNumber.equals=" + DEFAULT_MODEL_NUMBER);

        // Get all the productDocumentList where modelNumber equals to UPDATED_MODEL_NUMBER
        defaultProductDocumentShouldNotBeFound("modelNumber.equals=" + UPDATED_MODEL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelNumber not equals to DEFAULT_MODEL_NUMBER
        defaultProductDocumentShouldNotBeFound("modelNumber.notEquals=" + DEFAULT_MODEL_NUMBER);

        // Get all the productDocumentList where modelNumber not equals to UPDATED_MODEL_NUMBER
        defaultProductDocumentShouldBeFound("modelNumber.notEquals=" + UPDATED_MODEL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNumberIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelNumber in DEFAULT_MODEL_NUMBER or UPDATED_MODEL_NUMBER
        defaultProductDocumentShouldBeFound("modelNumber.in=" + DEFAULT_MODEL_NUMBER + "," + UPDATED_MODEL_NUMBER);

        // Get all the productDocumentList where modelNumber equals to UPDATED_MODEL_NUMBER
        defaultProductDocumentShouldNotBeFound("modelNumber.in=" + UPDATED_MODEL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelNumber is not null
        defaultProductDocumentShouldBeFound("modelNumber.specified=true");

        // Get all the productDocumentList where modelNumber is null
        defaultProductDocumentShouldNotBeFound("modelNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByModelNumberContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelNumber contains DEFAULT_MODEL_NUMBER
        defaultProductDocumentShouldBeFound("modelNumber.contains=" + DEFAULT_MODEL_NUMBER);

        // Get all the productDocumentList where modelNumber contains UPDATED_MODEL_NUMBER
        defaultProductDocumentShouldNotBeFound("modelNumber.contains=" + UPDATED_MODEL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNumberNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where modelNumber does not contain DEFAULT_MODEL_NUMBER
        defaultProductDocumentShouldNotBeFound("modelNumber.doesNotContain=" + DEFAULT_MODEL_NUMBER);

        // Get all the productDocumentList where modelNumber does not contain UPDATED_MODEL_NUMBER
        defaultProductDocumentShouldBeFound("modelNumber.doesNotContain=" + UPDATED_MODEL_NUMBER);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByFabricTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where fabricType equals to DEFAULT_FABRIC_TYPE
        defaultProductDocumentShouldBeFound("fabricType.equals=" + DEFAULT_FABRIC_TYPE);

        // Get all the productDocumentList where fabricType equals to UPDATED_FABRIC_TYPE
        defaultProductDocumentShouldNotBeFound("fabricType.equals=" + UPDATED_FABRIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByFabricTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where fabricType not equals to DEFAULT_FABRIC_TYPE
        defaultProductDocumentShouldNotBeFound("fabricType.notEquals=" + DEFAULT_FABRIC_TYPE);

        // Get all the productDocumentList where fabricType not equals to UPDATED_FABRIC_TYPE
        defaultProductDocumentShouldBeFound("fabricType.notEquals=" + UPDATED_FABRIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByFabricTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where fabricType in DEFAULT_FABRIC_TYPE or UPDATED_FABRIC_TYPE
        defaultProductDocumentShouldBeFound("fabricType.in=" + DEFAULT_FABRIC_TYPE + "," + UPDATED_FABRIC_TYPE);

        // Get all the productDocumentList where fabricType equals to UPDATED_FABRIC_TYPE
        defaultProductDocumentShouldNotBeFound("fabricType.in=" + UPDATED_FABRIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByFabricTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where fabricType is not null
        defaultProductDocumentShouldBeFound("fabricType.specified=true");

        // Get all the productDocumentList where fabricType is null
        defaultProductDocumentShouldNotBeFound("fabricType.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByFabricTypeContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where fabricType contains DEFAULT_FABRIC_TYPE
        defaultProductDocumentShouldBeFound("fabricType.contains=" + DEFAULT_FABRIC_TYPE);

        // Get all the productDocumentList where fabricType contains UPDATED_FABRIC_TYPE
        defaultProductDocumentShouldNotBeFound("fabricType.contains=" + UPDATED_FABRIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByFabricTypeNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where fabricType does not contain DEFAULT_FABRIC_TYPE
        defaultProductDocumentShouldNotBeFound("fabricType.doesNotContain=" + DEFAULT_FABRIC_TYPE);

        // Get all the productDocumentList where fabricType does not contain UPDATED_FABRIC_TYPE
        defaultProductDocumentShouldBeFound("fabricType.doesNotContain=" + UPDATED_FABRIC_TYPE);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByProductComplianceCertificateIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productComplianceCertificate equals to DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentShouldBeFound("productComplianceCertificate.equals=" + DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE);

        // Get all the productDocumentList where productComplianceCertificate equals to UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentShouldNotBeFound("productComplianceCertificate.equals=" + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductComplianceCertificateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productComplianceCertificate not equals to DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentShouldNotBeFound("productComplianceCertificate.notEquals=" + DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE);

        // Get all the productDocumentList where productComplianceCertificate not equals to UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentShouldBeFound("productComplianceCertificate.notEquals=" + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductComplianceCertificateIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productComplianceCertificate in DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE or UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentShouldBeFound("productComplianceCertificate.in=" + DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE + "," + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);

        // Get all the productDocumentList where productComplianceCertificate equals to UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentShouldNotBeFound("productComplianceCertificate.in=" + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductComplianceCertificateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productComplianceCertificate is not null
        defaultProductDocumentShouldBeFound("productComplianceCertificate.specified=true");

        // Get all the productDocumentList where productComplianceCertificate is null
        defaultProductDocumentShouldNotBeFound("productComplianceCertificate.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByProductComplianceCertificateContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productComplianceCertificate contains DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentShouldBeFound("productComplianceCertificate.contains=" + DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE);

        // Get all the productDocumentList where productComplianceCertificate contains UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentShouldNotBeFound("productComplianceCertificate.contains=" + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductComplianceCertificateNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where productComplianceCertificate does not contain DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentShouldNotBeFound("productComplianceCertificate.doesNotContain=" + DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE);

        // Get all the productDocumentList where productComplianceCertificate does not contain UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentShouldBeFound("productComplianceCertificate.doesNotContain=" + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByGenuineAndLegalIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where genuineAndLegal equals to DEFAULT_GENUINE_AND_LEGAL
        defaultProductDocumentShouldBeFound("genuineAndLegal.equals=" + DEFAULT_GENUINE_AND_LEGAL);

        // Get all the productDocumentList where genuineAndLegal equals to UPDATED_GENUINE_AND_LEGAL
        defaultProductDocumentShouldNotBeFound("genuineAndLegal.equals=" + UPDATED_GENUINE_AND_LEGAL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByGenuineAndLegalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where genuineAndLegal not equals to DEFAULT_GENUINE_AND_LEGAL
        defaultProductDocumentShouldNotBeFound("genuineAndLegal.notEquals=" + DEFAULT_GENUINE_AND_LEGAL);

        // Get all the productDocumentList where genuineAndLegal not equals to UPDATED_GENUINE_AND_LEGAL
        defaultProductDocumentShouldBeFound("genuineAndLegal.notEquals=" + UPDATED_GENUINE_AND_LEGAL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByGenuineAndLegalIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where genuineAndLegal in DEFAULT_GENUINE_AND_LEGAL or UPDATED_GENUINE_AND_LEGAL
        defaultProductDocumentShouldBeFound("genuineAndLegal.in=" + DEFAULT_GENUINE_AND_LEGAL + "," + UPDATED_GENUINE_AND_LEGAL);

        // Get all the productDocumentList where genuineAndLegal equals to UPDATED_GENUINE_AND_LEGAL
        defaultProductDocumentShouldNotBeFound("genuineAndLegal.in=" + UPDATED_GENUINE_AND_LEGAL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByGenuineAndLegalIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where genuineAndLegal is not null
        defaultProductDocumentShouldBeFound("genuineAndLegal.specified=true");

        // Get all the productDocumentList where genuineAndLegal is null
        defaultProductDocumentShouldNotBeFound("genuineAndLegal.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByCountryOfOriginIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where countryOfOrigin equals to DEFAULT_COUNTRY_OF_ORIGIN
        defaultProductDocumentShouldBeFound("countryOfOrigin.equals=" + DEFAULT_COUNTRY_OF_ORIGIN);

        // Get all the productDocumentList where countryOfOrigin equals to UPDATED_COUNTRY_OF_ORIGIN
        defaultProductDocumentShouldNotBeFound("countryOfOrigin.equals=" + UPDATED_COUNTRY_OF_ORIGIN);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByCountryOfOriginIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where countryOfOrigin not equals to DEFAULT_COUNTRY_OF_ORIGIN
        defaultProductDocumentShouldNotBeFound("countryOfOrigin.notEquals=" + DEFAULT_COUNTRY_OF_ORIGIN);

        // Get all the productDocumentList where countryOfOrigin not equals to UPDATED_COUNTRY_OF_ORIGIN
        defaultProductDocumentShouldBeFound("countryOfOrigin.notEquals=" + UPDATED_COUNTRY_OF_ORIGIN);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByCountryOfOriginIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where countryOfOrigin in DEFAULT_COUNTRY_OF_ORIGIN or UPDATED_COUNTRY_OF_ORIGIN
        defaultProductDocumentShouldBeFound("countryOfOrigin.in=" + DEFAULT_COUNTRY_OF_ORIGIN + "," + UPDATED_COUNTRY_OF_ORIGIN);

        // Get all the productDocumentList where countryOfOrigin equals to UPDATED_COUNTRY_OF_ORIGIN
        defaultProductDocumentShouldNotBeFound("countryOfOrigin.in=" + UPDATED_COUNTRY_OF_ORIGIN);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByCountryOfOriginIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where countryOfOrigin is not null
        defaultProductDocumentShouldBeFound("countryOfOrigin.specified=true");

        // Get all the productDocumentList where countryOfOrigin is null
        defaultProductDocumentShouldNotBeFound("countryOfOrigin.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByCountryOfOriginContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where countryOfOrigin contains DEFAULT_COUNTRY_OF_ORIGIN
        defaultProductDocumentShouldBeFound("countryOfOrigin.contains=" + DEFAULT_COUNTRY_OF_ORIGIN);

        // Get all the productDocumentList where countryOfOrigin contains UPDATED_COUNTRY_OF_ORIGIN
        defaultProductDocumentShouldNotBeFound("countryOfOrigin.contains=" + UPDATED_COUNTRY_OF_ORIGIN);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByCountryOfOriginNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where countryOfOrigin does not contain DEFAULT_COUNTRY_OF_ORIGIN
        defaultProductDocumentShouldNotBeFound("countryOfOrigin.doesNotContain=" + DEFAULT_COUNTRY_OF_ORIGIN);

        // Get all the productDocumentList where countryOfOrigin does not contain UPDATED_COUNTRY_OF_ORIGIN
        defaultProductDocumentShouldBeFound("countryOfOrigin.doesNotContain=" + UPDATED_COUNTRY_OF_ORIGIN);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPeriod equals to DEFAULT_WARRANTY_PERIOD
        defaultProductDocumentShouldBeFound("warrantyPeriod.equals=" + DEFAULT_WARRANTY_PERIOD);

        // Get all the productDocumentList where warrantyPeriod equals to UPDATED_WARRANTY_PERIOD
        defaultProductDocumentShouldNotBeFound("warrantyPeriod.equals=" + UPDATED_WARRANTY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPeriodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPeriod not equals to DEFAULT_WARRANTY_PERIOD
        defaultProductDocumentShouldNotBeFound("warrantyPeriod.notEquals=" + DEFAULT_WARRANTY_PERIOD);

        // Get all the productDocumentList where warrantyPeriod not equals to UPDATED_WARRANTY_PERIOD
        defaultProductDocumentShouldBeFound("warrantyPeriod.notEquals=" + UPDATED_WARRANTY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPeriodIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPeriod in DEFAULT_WARRANTY_PERIOD or UPDATED_WARRANTY_PERIOD
        defaultProductDocumentShouldBeFound("warrantyPeriod.in=" + DEFAULT_WARRANTY_PERIOD + "," + UPDATED_WARRANTY_PERIOD);

        // Get all the productDocumentList where warrantyPeriod equals to UPDATED_WARRANTY_PERIOD
        defaultProductDocumentShouldNotBeFound("warrantyPeriod.in=" + UPDATED_WARRANTY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPeriod is not null
        defaultProductDocumentShouldBeFound("warrantyPeriod.specified=true");

        // Get all the productDocumentList where warrantyPeriod is null
        defaultProductDocumentShouldNotBeFound("warrantyPeriod.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPeriodContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPeriod contains DEFAULT_WARRANTY_PERIOD
        defaultProductDocumentShouldBeFound("warrantyPeriod.contains=" + DEFAULT_WARRANTY_PERIOD);

        // Get all the productDocumentList where warrantyPeriod contains UPDATED_WARRANTY_PERIOD
        defaultProductDocumentShouldNotBeFound("warrantyPeriod.contains=" + UPDATED_WARRANTY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPeriodNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPeriod does not contain DEFAULT_WARRANTY_PERIOD
        defaultProductDocumentShouldNotBeFound("warrantyPeriod.doesNotContain=" + DEFAULT_WARRANTY_PERIOD);

        // Get all the productDocumentList where warrantyPeriod does not contain UPDATED_WARRANTY_PERIOD
        defaultProductDocumentShouldBeFound("warrantyPeriod.doesNotContain=" + UPDATED_WARRANTY_PERIOD);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPolicyIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPolicy equals to DEFAULT_WARRANTY_POLICY
        defaultProductDocumentShouldBeFound("warrantyPolicy.equals=" + DEFAULT_WARRANTY_POLICY);

        // Get all the productDocumentList where warrantyPolicy equals to UPDATED_WARRANTY_POLICY
        defaultProductDocumentShouldNotBeFound("warrantyPolicy.equals=" + UPDATED_WARRANTY_POLICY);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPolicyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPolicy not equals to DEFAULT_WARRANTY_POLICY
        defaultProductDocumentShouldNotBeFound("warrantyPolicy.notEquals=" + DEFAULT_WARRANTY_POLICY);

        // Get all the productDocumentList where warrantyPolicy not equals to UPDATED_WARRANTY_POLICY
        defaultProductDocumentShouldBeFound("warrantyPolicy.notEquals=" + UPDATED_WARRANTY_POLICY);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPolicyIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPolicy in DEFAULT_WARRANTY_POLICY or UPDATED_WARRANTY_POLICY
        defaultProductDocumentShouldBeFound("warrantyPolicy.in=" + DEFAULT_WARRANTY_POLICY + "," + UPDATED_WARRANTY_POLICY);

        // Get all the productDocumentList where warrantyPolicy equals to UPDATED_WARRANTY_POLICY
        defaultProductDocumentShouldNotBeFound("warrantyPolicy.in=" + UPDATED_WARRANTY_POLICY);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPolicyIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPolicy is not null
        defaultProductDocumentShouldBeFound("warrantyPolicy.specified=true");

        // Get all the productDocumentList where warrantyPolicy is null
        defaultProductDocumentShouldNotBeFound("warrantyPolicy.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPolicyContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPolicy contains DEFAULT_WARRANTY_POLICY
        defaultProductDocumentShouldBeFound("warrantyPolicy.contains=" + DEFAULT_WARRANTY_POLICY);

        // Get all the productDocumentList where warrantyPolicy contains UPDATED_WARRANTY_POLICY
        defaultProductDocumentShouldNotBeFound("warrantyPolicy.contains=" + UPDATED_WARRANTY_POLICY);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPolicyNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where warrantyPolicy does not contain DEFAULT_WARRANTY_POLICY
        defaultProductDocumentShouldNotBeFound("warrantyPolicy.doesNotContain=" + DEFAULT_WARRANTY_POLICY);

        // Get all the productDocumentList where warrantyPolicy does not contain UPDATED_WARRANTY_POLICY
        defaultProductDocumentShouldBeFound("warrantyPolicy.doesNotContain=" + UPDATED_WARRANTY_POLICY);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByDangerousGoodsIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where dangerousGoods equals to DEFAULT_DANGEROUS_GOODS
        defaultProductDocumentShouldBeFound("dangerousGoods.equals=" + DEFAULT_DANGEROUS_GOODS);

        // Get all the productDocumentList where dangerousGoods equals to UPDATED_DANGEROUS_GOODS
        defaultProductDocumentShouldNotBeFound("dangerousGoods.equals=" + UPDATED_DANGEROUS_GOODS);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByDangerousGoodsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where dangerousGoods not equals to DEFAULT_DANGEROUS_GOODS
        defaultProductDocumentShouldNotBeFound("dangerousGoods.notEquals=" + DEFAULT_DANGEROUS_GOODS);

        // Get all the productDocumentList where dangerousGoods not equals to UPDATED_DANGEROUS_GOODS
        defaultProductDocumentShouldBeFound("dangerousGoods.notEquals=" + UPDATED_DANGEROUS_GOODS);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByDangerousGoodsIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where dangerousGoods in DEFAULT_DANGEROUS_GOODS or UPDATED_DANGEROUS_GOODS
        defaultProductDocumentShouldBeFound("dangerousGoods.in=" + DEFAULT_DANGEROUS_GOODS + "," + UPDATED_DANGEROUS_GOODS);

        // Get all the productDocumentList where dangerousGoods equals to UPDATED_DANGEROUS_GOODS
        defaultProductDocumentShouldNotBeFound("dangerousGoods.in=" + UPDATED_DANGEROUS_GOODS);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByDangerousGoodsIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where dangerousGoods is not null
        defaultProductDocumentShouldBeFound("dangerousGoods.specified=true");

        // Get all the productDocumentList where dangerousGoods is null
        defaultProductDocumentShouldNotBeFound("dangerousGoods.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByDangerousGoodsContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where dangerousGoods contains DEFAULT_DANGEROUS_GOODS
        defaultProductDocumentShouldBeFound("dangerousGoods.contains=" + DEFAULT_DANGEROUS_GOODS);

        // Get all the productDocumentList where dangerousGoods contains UPDATED_DANGEROUS_GOODS
        defaultProductDocumentShouldNotBeFound("dangerousGoods.contains=" + UPDATED_DANGEROUS_GOODS);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByDangerousGoodsNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where dangerousGoods does not contain DEFAULT_DANGEROUS_GOODS
        defaultProductDocumentShouldNotBeFound("dangerousGoods.doesNotContain=" + DEFAULT_DANGEROUS_GOODS);

        // Get all the productDocumentList where dangerousGoods does not contain UPDATED_DANGEROUS_GOODS
        defaultProductDocumentShouldBeFound("dangerousGoods.doesNotContain=" + UPDATED_DANGEROUS_GOODS);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultProductDocumentShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the productDocumentList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultProductDocumentShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultProductDocumentShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the productDocumentList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultProductDocumentShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultProductDocumentShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the productDocumentList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultProductDocumentShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where lastEditedBy is not null
        defaultProductDocumentShouldBeFound("lastEditedBy.specified=true");

        // Get all the productDocumentList where lastEditedBy is null
        defaultProductDocumentShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultProductDocumentShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the productDocumentList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultProductDocumentShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultProductDocumentShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the productDocumentList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultProductDocumentShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultProductDocumentShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the productDocumentList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultProductDocumentShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultProductDocumentShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the productDocumentList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultProductDocumentShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultProductDocumentShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the productDocumentList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultProductDocumentShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        // Get all the productDocumentList where lastEditedWhen is not null
        defaultProductDocumentShouldBeFound("lastEditedWhen.specified=true");

        // Get all the productDocumentList where lastEditedWhen is null
        defaultProductDocumentShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);
        WarrantyTypes warrantyType = WarrantyTypesResourceIT.createEntity(em);
        em.persist(warrantyType);
        em.flush();
        productDocument.setWarrantyType(warrantyType);
        productDocumentRepository.saveAndFlush(productDocument);
        Long warrantyTypeId = warrantyType.getId();

        // Get all the productDocumentList where warrantyType equals to warrantyTypeId
        defaultProductDocumentShouldBeFound("warrantyTypeId.equals=" + warrantyTypeId);

        // Get all the productDocumentList where warrantyType equals to warrantyTypeId + 1
        defaultProductDocumentShouldNotBeFound("warrantyTypeId.equals=" + (warrantyTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        productDocument.setCulture(culture);
        productDocumentRepository.saveAndFlush(productDocument);
        Long cultureId = culture.getId();

        // Get all the productDocumentList where culture equals to cultureId
        defaultProductDocumentShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the productDocumentList where culture equals to cultureId + 1
        defaultProductDocumentShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);
        Products product = ProductsResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        productDocument.setProduct(product);
        product.setProductDocument(productDocument);
        productDocumentRepository.saveAndFlush(productDocument);
        Long productId = product.getId();

        // Get all the productDocumentList where product equals to productId
        defaultProductDocumentShouldBeFound("productId.equals=" + productId);

        // Get all the productDocumentList where product equals to productId + 1
        defaultProductDocumentShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductDocumentShouldBeFound(String filter) throws Exception {
        restProductDocumentMockMvc.perform(get("/api/product-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].videoUrl").value(hasItem(DEFAULT_VIDEO_URL)))
            .andExpect(jsonPath("$.[*].highlights").value(hasItem(DEFAULT_HIGHLIGHTS.toString())))
            .andExpect(jsonPath("$.[*].longDescription").value(hasItem(DEFAULT_LONG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].whatInTheBox").value(hasItem(DEFAULT_WHAT_IN_THE_BOX.toString())))
            .andExpect(jsonPath("$.[*].careInstructions").value(hasItem(DEFAULT_CARE_INSTRUCTIONS.toString())))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE)))
            .andExpect(jsonPath("$.[*].modelName").value(hasItem(DEFAULT_MODEL_NAME)))
            .andExpect(jsonPath("$.[*].modelNumber").value(hasItem(DEFAULT_MODEL_NUMBER)))
            .andExpect(jsonPath("$.[*].fabricType").value(hasItem(DEFAULT_FABRIC_TYPE)))
            .andExpect(jsonPath("$.[*].specialFeatures").value(hasItem(DEFAULT_SPECIAL_FEATURES.toString())))
            .andExpect(jsonPath("$.[*].productComplianceCertificate").value(hasItem(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE)))
            .andExpect(jsonPath("$.[*].genuineAndLegal").value(hasItem(DEFAULT_GENUINE_AND_LEGAL.booleanValue())))
            .andExpect(jsonPath("$.[*].countryOfOrigin").value(hasItem(DEFAULT_COUNTRY_OF_ORIGIN)))
            .andExpect(jsonPath("$.[*].usageAndSideEffects").value(hasItem(DEFAULT_USAGE_AND_SIDE_EFFECTS.toString())))
            .andExpect(jsonPath("$.[*].safetyWarnning").value(hasItem(DEFAULT_SAFETY_WARNNING.toString())))
            .andExpect(jsonPath("$.[*].warrantyPeriod").value(hasItem(DEFAULT_WARRANTY_PERIOD)))
            .andExpect(jsonPath("$.[*].warrantyPolicy").value(hasItem(DEFAULT_WARRANTY_POLICY)))
            .andExpect(jsonPath("$.[*].dangerousGoods").value(hasItem(DEFAULT_DANGEROUS_GOODS)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restProductDocumentMockMvc.perform(get("/api/product-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductDocumentShouldNotBeFound(String filter) throws Exception {
        restProductDocumentMockMvc.perform(get("/api/product-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductDocumentMockMvc.perform(get("/api/product-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductDocument() throws Exception {
        // Get the productDocument
        restProductDocumentMockMvc.perform(get("/api/product-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductDocument() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        int databaseSizeBeforeUpdate = productDocumentRepository.findAll().size();

        // Update the productDocument
        ProductDocument updatedProductDocument = productDocumentRepository.findById(productDocument.getId()).get();
        // Disconnect from session so that the updates on updatedProductDocument are not directly saved in db
        em.detach(updatedProductDocument);
        updatedProductDocument
            .videoUrl(UPDATED_VIDEO_URL)
            .highlights(UPDATED_HIGHLIGHTS)
            .longDescription(UPDATED_LONG_DESCRIPTION)
            .shortDescription(UPDATED_SHORT_DESCRIPTION)
            .whatInTheBox(UPDATED_WHAT_IN_THE_BOX)
            .careInstructions(UPDATED_CARE_INSTRUCTIONS)
            .productType(UPDATED_PRODUCT_TYPE)
            .modelName(UPDATED_MODEL_NAME)
            .modelNumber(UPDATED_MODEL_NUMBER)
            .fabricType(UPDATED_FABRIC_TYPE)
            .specialFeatures(UPDATED_SPECIAL_FEATURES)
            .productComplianceCertificate(UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE)
            .genuineAndLegal(UPDATED_GENUINE_AND_LEGAL)
            .countryOfOrigin(UPDATED_COUNTRY_OF_ORIGIN)
            .usageAndSideEffects(UPDATED_USAGE_AND_SIDE_EFFECTS)
            .safetyWarnning(UPDATED_SAFETY_WARNNING)
            .warrantyPeriod(UPDATED_WARRANTY_PERIOD)
            .warrantyPolicy(UPDATED_WARRANTY_POLICY)
            .dangerousGoods(UPDATED_DANGEROUS_GOODS)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        ProductDocumentDTO productDocumentDTO = productDocumentMapper.toDto(updatedProductDocument);

        restProductDocumentMockMvc.perform(put("/api/product-documents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the ProductDocument in the database
        List<ProductDocument> productDocumentList = productDocumentRepository.findAll();
        assertThat(productDocumentList).hasSize(databaseSizeBeforeUpdate);
        ProductDocument testProductDocument = productDocumentList.get(productDocumentList.size() - 1);
        assertThat(testProductDocument.getVideoUrl()).isEqualTo(UPDATED_VIDEO_URL);
        assertThat(testProductDocument.getHighlights()).isEqualTo(UPDATED_HIGHLIGHTS);
        assertThat(testProductDocument.getLongDescription()).isEqualTo(UPDATED_LONG_DESCRIPTION);
        assertThat(testProductDocument.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testProductDocument.getWhatInTheBox()).isEqualTo(UPDATED_WHAT_IN_THE_BOX);
        assertThat(testProductDocument.getCareInstructions()).isEqualTo(UPDATED_CARE_INSTRUCTIONS);
        assertThat(testProductDocument.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
        assertThat(testProductDocument.getModelName()).isEqualTo(UPDATED_MODEL_NAME);
        assertThat(testProductDocument.getModelNumber()).isEqualTo(UPDATED_MODEL_NUMBER);
        assertThat(testProductDocument.getFabricType()).isEqualTo(UPDATED_FABRIC_TYPE);
        assertThat(testProductDocument.getSpecialFeatures()).isEqualTo(UPDATED_SPECIAL_FEATURES);
        assertThat(testProductDocument.getProductComplianceCertificate()).isEqualTo(UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
        assertThat(testProductDocument.isGenuineAndLegal()).isEqualTo(UPDATED_GENUINE_AND_LEGAL);
        assertThat(testProductDocument.getCountryOfOrigin()).isEqualTo(UPDATED_COUNTRY_OF_ORIGIN);
        assertThat(testProductDocument.getUsageAndSideEffects()).isEqualTo(UPDATED_USAGE_AND_SIDE_EFFECTS);
        assertThat(testProductDocument.getSafetyWarnning()).isEqualTo(UPDATED_SAFETY_WARNNING);
        assertThat(testProductDocument.getWarrantyPeriod()).isEqualTo(UPDATED_WARRANTY_PERIOD);
        assertThat(testProductDocument.getWarrantyPolicy()).isEqualTo(UPDATED_WARRANTY_POLICY);
        assertThat(testProductDocument.getDangerousGoods()).isEqualTo(UPDATED_DANGEROUS_GOODS);
        assertThat(testProductDocument.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testProductDocument.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingProductDocument() throws Exception {
        int databaseSizeBeforeUpdate = productDocumentRepository.findAll().size();

        // Create the ProductDocument
        ProductDocumentDTO productDocumentDTO = productDocumentMapper.toDto(productDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductDocumentMockMvc.perform(put("/api/product-documents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductDocument in the database
        List<ProductDocument> productDocumentList = productDocumentRepository.findAll();
        assertThat(productDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductDocument() throws Exception {
        // Initialize the database
        productDocumentRepository.saveAndFlush(productDocument);

        int databaseSizeBeforeDelete = productDocumentRepository.findAll().size();

        // Delete the productDocument
        restProductDocumentMockMvc.perform(delete("/api/product-documents/{id}", productDocument.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductDocument> productDocumentList = productDocumentRepository.findAll();
        assertThat(productDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

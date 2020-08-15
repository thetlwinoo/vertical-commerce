package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.ProductDocuments;
import com.vertical.commerce.domain.Products;
import com.vertical.commerce.domain.WarrantyTypes;
import com.vertical.commerce.repository.ProductDocumentsRepository;
import com.vertical.commerce.service.ProductDocumentsService;
import com.vertical.commerce.service.dto.ProductDocumentsDTO;
import com.vertical.commerce.service.mapper.ProductDocumentsMapper;
import com.vertical.commerce.service.dto.ProductDocumentsCriteria;
import com.vertical.commerce.service.ProductDocumentsQueryService;

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
 * Integration tests for the {@link ProductDocumentsResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ProductDocumentsResourceIT {

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
    private ProductDocumentsRepository productDocumentsRepository;

    @Autowired
    private ProductDocumentsMapper productDocumentsMapper;

    @Autowired
    private ProductDocumentsService productDocumentsService;

    @Autowired
    private ProductDocumentsQueryService productDocumentsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductDocumentsMockMvc;

    private ProductDocuments productDocuments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductDocuments createEntity(EntityManager em) {
        ProductDocuments productDocuments = new ProductDocuments()
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
        return productDocuments;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductDocuments createUpdatedEntity(EntityManager em) {
        ProductDocuments productDocuments = new ProductDocuments()
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
        return productDocuments;
    }

    @BeforeEach
    public void initTest() {
        productDocuments = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductDocuments() throws Exception {
        int databaseSizeBeforeCreate = productDocumentsRepository.findAll().size();
        // Create the ProductDocuments
        ProductDocumentsDTO productDocumentsDTO = productDocumentsMapper.toDto(productDocuments);
        restProductDocumentsMockMvc.perform(post("/api/product-documents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentsDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductDocuments in the database
        List<ProductDocuments> productDocumentsList = productDocumentsRepository.findAll();
        assertThat(productDocumentsList).hasSize(databaseSizeBeforeCreate + 1);
        ProductDocuments testProductDocuments = productDocumentsList.get(productDocumentsList.size() - 1);
        assertThat(testProductDocuments.getVideoUrl()).isEqualTo(DEFAULT_VIDEO_URL);
        assertThat(testProductDocuments.getHighlights()).isEqualTo(DEFAULT_HIGHLIGHTS);
        assertThat(testProductDocuments.getLongDescription()).isEqualTo(DEFAULT_LONG_DESCRIPTION);
        assertThat(testProductDocuments.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testProductDocuments.getWhatInTheBox()).isEqualTo(DEFAULT_WHAT_IN_THE_BOX);
        assertThat(testProductDocuments.getCareInstructions()).isEqualTo(DEFAULT_CARE_INSTRUCTIONS);
        assertThat(testProductDocuments.getProductType()).isEqualTo(DEFAULT_PRODUCT_TYPE);
        assertThat(testProductDocuments.getModelName()).isEqualTo(DEFAULT_MODEL_NAME);
        assertThat(testProductDocuments.getModelNumber()).isEqualTo(DEFAULT_MODEL_NUMBER);
        assertThat(testProductDocuments.getFabricType()).isEqualTo(DEFAULT_FABRIC_TYPE);
        assertThat(testProductDocuments.getSpecialFeatures()).isEqualTo(DEFAULT_SPECIAL_FEATURES);
        assertThat(testProductDocuments.getProductComplianceCertificate()).isEqualTo(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE);
        assertThat(testProductDocuments.isGenuineAndLegal()).isEqualTo(DEFAULT_GENUINE_AND_LEGAL);
        assertThat(testProductDocuments.getCountryOfOrigin()).isEqualTo(DEFAULT_COUNTRY_OF_ORIGIN);
        assertThat(testProductDocuments.getUsageAndSideEffects()).isEqualTo(DEFAULT_USAGE_AND_SIDE_EFFECTS);
        assertThat(testProductDocuments.getSafetyWarnning()).isEqualTo(DEFAULT_SAFETY_WARNNING);
        assertThat(testProductDocuments.getWarrantyPeriod()).isEqualTo(DEFAULT_WARRANTY_PERIOD);
        assertThat(testProductDocuments.getWarrantyPolicy()).isEqualTo(DEFAULT_WARRANTY_POLICY);
        assertThat(testProductDocuments.getDangerousGoods()).isEqualTo(DEFAULT_DANGEROUS_GOODS);
        assertThat(testProductDocuments.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testProductDocuments.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createProductDocumentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productDocumentsRepository.findAll().size();

        // Create the ProductDocuments with an existing ID
        productDocuments.setId(1L);
        ProductDocumentsDTO productDocumentsDTO = productDocumentsMapper.toDto(productDocuments);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductDocumentsMockMvc.perform(post("/api/product-documents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductDocuments in the database
        List<ProductDocuments> productDocumentsList = productDocumentsRepository.findAll();
        assertThat(productDocumentsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLastEditedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = productDocumentsRepository.findAll().size();
        // set the field null
        productDocuments.setLastEditedBy(null);

        // Create the ProductDocuments, which fails.
        ProductDocumentsDTO productDocumentsDTO = productDocumentsMapper.toDto(productDocuments);


        restProductDocumentsMockMvc.perform(post("/api/product-documents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentsDTO)))
            .andExpect(status().isBadRequest());

        List<ProductDocuments> productDocumentsList = productDocumentsRepository.findAll();
        assertThat(productDocumentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = productDocumentsRepository.findAll().size();
        // set the field null
        productDocuments.setLastEditedWhen(null);

        // Create the ProductDocuments, which fails.
        ProductDocumentsDTO productDocumentsDTO = productDocumentsMapper.toDto(productDocuments);


        restProductDocumentsMockMvc.perform(post("/api/product-documents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentsDTO)))
            .andExpect(status().isBadRequest());

        List<ProductDocuments> productDocumentsList = productDocumentsRepository.findAll();
        assertThat(productDocumentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductDocuments() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList
        restProductDocumentsMockMvc.perform(get("/api/product-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productDocuments.getId().intValue())))
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
    public void getProductDocuments() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get the productDocuments
        restProductDocumentsMockMvc.perform(get("/api/product-documents/{id}", productDocuments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productDocuments.getId().intValue()))
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
        productDocumentsRepository.saveAndFlush(productDocuments);

        Long id = productDocuments.getId();

        defaultProductDocumentsShouldBeFound("id.equals=" + id);
        defaultProductDocumentsShouldNotBeFound("id.notEquals=" + id);

        defaultProductDocumentsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductDocumentsShouldNotBeFound("id.greaterThan=" + id);

        defaultProductDocumentsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductDocumentsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByVideoUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where videoUrl equals to DEFAULT_VIDEO_URL
        defaultProductDocumentsShouldBeFound("videoUrl.equals=" + DEFAULT_VIDEO_URL);

        // Get all the productDocumentsList where videoUrl equals to UPDATED_VIDEO_URL
        defaultProductDocumentsShouldNotBeFound("videoUrl.equals=" + UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByVideoUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where videoUrl not equals to DEFAULT_VIDEO_URL
        defaultProductDocumentsShouldNotBeFound("videoUrl.notEquals=" + DEFAULT_VIDEO_URL);

        // Get all the productDocumentsList where videoUrl not equals to UPDATED_VIDEO_URL
        defaultProductDocumentsShouldBeFound("videoUrl.notEquals=" + UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByVideoUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where videoUrl in DEFAULT_VIDEO_URL or UPDATED_VIDEO_URL
        defaultProductDocumentsShouldBeFound("videoUrl.in=" + DEFAULT_VIDEO_URL + "," + UPDATED_VIDEO_URL);

        // Get all the productDocumentsList where videoUrl equals to UPDATED_VIDEO_URL
        defaultProductDocumentsShouldNotBeFound("videoUrl.in=" + UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByVideoUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where videoUrl is not null
        defaultProductDocumentsShouldBeFound("videoUrl.specified=true");

        // Get all the productDocumentsList where videoUrl is null
        defaultProductDocumentsShouldNotBeFound("videoUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByVideoUrlContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where videoUrl contains DEFAULT_VIDEO_URL
        defaultProductDocumentsShouldBeFound("videoUrl.contains=" + DEFAULT_VIDEO_URL);

        // Get all the productDocumentsList where videoUrl contains UPDATED_VIDEO_URL
        defaultProductDocumentsShouldNotBeFound("videoUrl.contains=" + UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByVideoUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where videoUrl does not contain DEFAULT_VIDEO_URL
        defaultProductDocumentsShouldNotBeFound("videoUrl.doesNotContain=" + DEFAULT_VIDEO_URL);

        // Get all the productDocumentsList where videoUrl does not contain UPDATED_VIDEO_URL
        defaultProductDocumentsShouldBeFound("videoUrl.doesNotContain=" + UPDATED_VIDEO_URL);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByProductTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where productType equals to DEFAULT_PRODUCT_TYPE
        defaultProductDocumentsShouldBeFound("productType.equals=" + DEFAULT_PRODUCT_TYPE);

        // Get all the productDocumentsList where productType equals to UPDATED_PRODUCT_TYPE
        defaultProductDocumentsShouldNotBeFound("productType.equals=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where productType not equals to DEFAULT_PRODUCT_TYPE
        defaultProductDocumentsShouldNotBeFound("productType.notEquals=" + DEFAULT_PRODUCT_TYPE);

        // Get all the productDocumentsList where productType not equals to UPDATED_PRODUCT_TYPE
        defaultProductDocumentsShouldBeFound("productType.notEquals=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where productType in DEFAULT_PRODUCT_TYPE or UPDATED_PRODUCT_TYPE
        defaultProductDocumentsShouldBeFound("productType.in=" + DEFAULT_PRODUCT_TYPE + "," + UPDATED_PRODUCT_TYPE);

        // Get all the productDocumentsList where productType equals to UPDATED_PRODUCT_TYPE
        defaultProductDocumentsShouldNotBeFound("productType.in=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where productType is not null
        defaultProductDocumentsShouldBeFound("productType.specified=true");

        // Get all the productDocumentsList where productType is null
        defaultProductDocumentsShouldNotBeFound("productType.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByProductTypeContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where productType contains DEFAULT_PRODUCT_TYPE
        defaultProductDocumentsShouldBeFound("productType.contains=" + DEFAULT_PRODUCT_TYPE);

        // Get all the productDocumentsList where productType contains UPDATED_PRODUCT_TYPE
        defaultProductDocumentsShouldNotBeFound("productType.contains=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductTypeNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where productType does not contain DEFAULT_PRODUCT_TYPE
        defaultProductDocumentsShouldNotBeFound("productType.doesNotContain=" + DEFAULT_PRODUCT_TYPE);

        // Get all the productDocumentsList where productType does not contain UPDATED_PRODUCT_TYPE
        defaultProductDocumentsShouldBeFound("productType.doesNotContain=" + UPDATED_PRODUCT_TYPE);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByModelNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where modelName equals to DEFAULT_MODEL_NAME
        defaultProductDocumentsShouldBeFound("modelName.equals=" + DEFAULT_MODEL_NAME);

        // Get all the productDocumentsList where modelName equals to UPDATED_MODEL_NAME
        defaultProductDocumentsShouldNotBeFound("modelName.equals=" + UPDATED_MODEL_NAME);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where modelName not equals to DEFAULT_MODEL_NAME
        defaultProductDocumentsShouldNotBeFound("modelName.notEquals=" + DEFAULT_MODEL_NAME);

        // Get all the productDocumentsList where modelName not equals to UPDATED_MODEL_NAME
        defaultProductDocumentsShouldBeFound("modelName.notEquals=" + UPDATED_MODEL_NAME);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNameIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where modelName in DEFAULT_MODEL_NAME or UPDATED_MODEL_NAME
        defaultProductDocumentsShouldBeFound("modelName.in=" + DEFAULT_MODEL_NAME + "," + UPDATED_MODEL_NAME);

        // Get all the productDocumentsList where modelName equals to UPDATED_MODEL_NAME
        defaultProductDocumentsShouldNotBeFound("modelName.in=" + UPDATED_MODEL_NAME);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where modelName is not null
        defaultProductDocumentsShouldBeFound("modelName.specified=true");

        // Get all the productDocumentsList where modelName is null
        defaultProductDocumentsShouldNotBeFound("modelName.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByModelNameContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where modelName contains DEFAULT_MODEL_NAME
        defaultProductDocumentsShouldBeFound("modelName.contains=" + DEFAULT_MODEL_NAME);

        // Get all the productDocumentsList where modelName contains UPDATED_MODEL_NAME
        defaultProductDocumentsShouldNotBeFound("modelName.contains=" + UPDATED_MODEL_NAME);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNameNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where modelName does not contain DEFAULT_MODEL_NAME
        defaultProductDocumentsShouldNotBeFound("modelName.doesNotContain=" + DEFAULT_MODEL_NAME);

        // Get all the productDocumentsList where modelName does not contain UPDATED_MODEL_NAME
        defaultProductDocumentsShouldBeFound("modelName.doesNotContain=" + UPDATED_MODEL_NAME);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByModelNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where modelNumber equals to DEFAULT_MODEL_NUMBER
        defaultProductDocumentsShouldBeFound("modelNumber.equals=" + DEFAULT_MODEL_NUMBER);

        // Get all the productDocumentsList where modelNumber equals to UPDATED_MODEL_NUMBER
        defaultProductDocumentsShouldNotBeFound("modelNumber.equals=" + UPDATED_MODEL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where modelNumber not equals to DEFAULT_MODEL_NUMBER
        defaultProductDocumentsShouldNotBeFound("modelNumber.notEquals=" + DEFAULT_MODEL_NUMBER);

        // Get all the productDocumentsList where modelNumber not equals to UPDATED_MODEL_NUMBER
        defaultProductDocumentsShouldBeFound("modelNumber.notEquals=" + UPDATED_MODEL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNumberIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where modelNumber in DEFAULT_MODEL_NUMBER or UPDATED_MODEL_NUMBER
        defaultProductDocumentsShouldBeFound("modelNumber.in=" + DEFAULT_MODEL_NUMBER + "," + UPDATED_MODEL_NUMBER);

        // Get all the productDocumentsList where modelNumber equals to UPDATED_MODEL_NUMBER
        defaultProductDocumentsShouldNotBeFound("modelNumber.in=" + UPDATED_MODEL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where modelNumber is not null
        defaultProductDocumentsShouldBeFound("modelNumber.specified=true");

        // Get all the productDocumentsList where modelNumber is null
        defaultProductDocumentsShouldNotBeFound("modelNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByModelNumberContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where modelNumber contains DEFAULT_MODEL_NUMBER
        defaultProductDocumentsShouldBeFound("modelNumber.contains=" + DEFAULT_MODEL_NUMBER);

        // Get all the productDocumentsList where modelNumber contains UPDATED_MODEL_NUMBER
        defaultProductDocumentsShouldNotBeFound("modelNumber.contains=" + UPDATED_MODEL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByModelNumberNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where modelNumber does not contain DEFAULT_MODEL_NUMBER
        defaultProductDocumentsShouldNotBeFound("modelNumber.doesNotContain=" + DEFAULT_MODEL_NUMBER);

        // Get all the productDocumentsList where modelNumber does not contain UPDATED_MODEL_NUMBER
        defaultProductDocumentsShouldBeFound("modelNumber.doesNotContain=" + UPDATED_MODEL_NUMBER);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByFabricTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where fabricType equals to DEFAULT_FABRIC_TYPE
        defaultProductDocumentsShouldBeFound("fabricType.equals=" + DEFAULT_FABRIC_TYPE);

        // Get all the productDocumentsList where fabricType equals to UPDATED_FABRIC_TYPE
        defaultProductDocumentsShouldNotBeFound("fabricType.equals=" + UPDATED_FABRIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByFabricTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where fabricType not equals to DEFAULT_FABRIC_TYPE
        defaultProductDocumentsShouldNotBeFound("fabricType.notEquals=" + DEFAULT_FABRIC_TYPE);

        // Get all the productDocumentsList where fabricType not equals to UPDATED_FABRIC_TYPE
        defaultProductDocumentsShouldBeFound("fabricType.notEquals=" + UPDATED_FABRIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByFabricTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where fabricType in DEFAULT_FABRIC_TYPE or UPDATED_FABRIC_TYPE
        defaultProductDocumentsShouldBeFound("fabricType.in=" + DEFAULT_FABRIC_TYPE + "," + UPDATED_FABRIC_TYPE);

        // Get all the productDocumentsList where fabricType equals to UPDATED_FABRIC_TYPE
        defaultProductDocumentsShouldNotBeFound("fabricType.in=" + UPDATED_FABRIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByFabricTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where fabricType is not null
        defaultProductDocumentsShouldBeFound("fabricType.specified=true");

        // Get all the productDocumentsList where fabricType is null
        defaultProductDocumentsShouldNotBeFound("fabricType.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByFabricTypeContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where fabricType contains DEFAULT_FABRIC_TYPE
        defaultProductDocumentsShouldBeFound("fabricType.contains=" + DEFAULT_FABRIC_TYPE);

        // Get all the productDocumentsList where fabricType contains UPDATED_FABRIC_TYPE
        defaultProductDocumentsShouldNotBeFound("fabricType.contains=" + UPDATED_FABRIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByFabricTypeNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where fabricType does not contain DEFAULT_FABRIC_TYPE
        defaultProductDocumentsShouldNotBeFound("fabricType.doesNotContain=" + DEFAULT_FABRIC_TYPE);

        // Get all the productDocumentsList where fabricType does not contain UPDATED_FABRIC_TYPE
        defaultProductDocumentsShouldBeFound("fabricType.doesNotContain=" + UPDATED_FABRIC_TYPE);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByProductComplianceCertificateIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where productComplianceCertificate equals to DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentsShouldBeFound("productComplianceCertificate.equals=" + DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE);

        // Get all the productDocumentsList where productComplianceCertificate equals to UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentsShouldNotBeFound("productComplianceCertificate.equals=" + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductComplianceCertificateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where productComplianceCertificate not equals to DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentsShouldNotBeFound("productComplianceCertificate.notEquals=" + DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE);

        // Get all the productDocumentsList where productComplianceCertificate not equals to UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentsShouldBeFound("productComplianceCertificate.notEquals=" + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductComplianceCertificateIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where productComplianceCertificate in DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE or UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentsShouldBeFound("productComplianceCertificate.in=" + DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE + "," + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);

        // Get all the productDocumentsList where productComplianceCertificate equals to UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentsShouldNotBeFound("productComplianceCertificate.in=" + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductComplianceCertificateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where productComplianceCertificate is not null
        defaultProductDocumentsShouldBeFound("productComplianceCertificate.specified=true");

        // Get all the productDocumentsList where productComplianceCertificate is null
        defaultProductDocumentsShouldNotBeFound("productComplianceCertificate.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByProductComplianceCertificateContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where productComplianceCertificate contains DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentsShouldBeFound("productComplianceCertificate.contains=" + DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE);

        // Get all the productDocumentsList where productComplianceCertificate contains UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentsShouldNotBeFound("productComplianceCertificate.contains=" + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductComplianceCertificateNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where productComplianceCertificate does not contain DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentsShouldNotBeFound("productComplianceCertificate.doesNotContain=" + DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE);

        // Get all the productDocumentsList where productComplianceCertificate does not contain UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultProductDocumentsShouldBeFound("productComplianceCertificate.doesNotContain=" + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByGenuineAndLegalIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where genuineAndLegal equals to DEFAULT_GENUINE_AND_LEGAL
        defaultProductDocumentsShouldBeFound("genuineAndLegal.equals=" + DEFAULT_GENUINE_AND_LEGAL);

        // Get all the productDocumentsList where genuineAndLegal equals to UPDATED_GENUINE_AND_LEGAL
        defaultProductDocumentsShouldNotBeFound("genuineAndLegal.equals=" + UPDATED_GENUINE_AND_LEGAL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByGenuineAndLegalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where genuineAndLegal not equals to DEFAULT_GENUINE_AND_LEGAL
        defaultProductDocumentsShouldNotBeFound("genuineAndLegal.notEquals=" + DEFAULT_GENUINE_AND_LEGAL);

        // Get all the productDocumentsList where genuineAndLegal not equals to UPDATED_GENUINE_AND_LEGAL
        defaultProductDocumentsShouldBeFound("genuineAndLegal.notEquals=" + UPDATED_GENUINE_AND_LEGAL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByGenuineAndLegalIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where genuineAndLegal in DEFAULT_GENUINE_AND_LEGAL or UPDATED_GENUINE_AND_LEGAL
        defaultProductDocumentsShouldBeFound("genuineAndLegal.in=" + DEFAULT_GENUINE_AND_LEGAL + "," + UPDATED_GENUINE_AND_LEGAL);

        // Get all the productDocumentsList where genuineAndLegal equals to UPDATED_GENUINE_AND_LEGAL
        defaultProductDocumentsShouldNotBeFound("genuineAndLegal.in=" + UPDATED_GENUINE_AND_LEGAL);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByGenuineAndLegalIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where genuineAndLegal is not null
        defaultProductDocumentsShouldBeFound("genuineAndLegal.specified=true");

        // Get all the productDocumentsList where genuineAndLegal is null
        defaultProductDocumentsShouldNotBeFound("genuineAndLegal.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByCountryOfOriginIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where countryOfOrigin equals to DEFAULT_COUNTRY_OF_ORIGIN
        defaultProductDocumentsShouldBeFound("countryOfOrigin.equals=" + DEFAULT_COUNTRY_OF_ORIGIN);

        // Get all the productDocumentsList where countryOfOrigin equals to UPDATED_COUNTRY_OF_ORIGIN
        defaultProductDocumentsShouldNotBeFound("countryOfOrigin.equals=" + UPDATED_COUNTRY_OF_ORIGIN);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByCountryOfOriginIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where countryOfOrigin not equals to DEFAULT_COUNTRY_OF_ORIGIN
        defaultProductDocumentsShouldNotBeFound("countryOfOrigin.notEquals=" + DEFAULT_COUNTRY_OF_ORIGIN);

        // Get all the productDocumentsList where countryOfOrigin not equals to UPDATED_COUNTRY_OF_ORIGIN
        defaultProductDocumentsShouldBeFound("countryOfOrigin.notEquals=" + UPDATED_COUNTRY_OF_ORIGIN);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByCountryOfOriginIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where countryOfOrigin in DEFAULT_COUNTRY_OF_ORIGIN or UPDATED_COUNTRY_OF_ORIGIN
        defaultProductDocumentsShouldBeFound("countryOfOrigin.in=" + DEFAULT_COUNTRY_OF_ORIGIN + "," + UPDATED_COUNTRY_OF_ORIGIN);

        // Get all the productDocumentsList where countryOfOrigin equals to UPDATED_COUNTRY_OF_ORIGIN
        defaultProductDocumentsShouldNotBeFound("countryOfOrigin.in=" + UPDATED_COUNTRY_OF_ORIGIN);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByCountryOfOriginIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where countryOfOrigin is not null
        defaultProductDocumentsShouldBeFound("countryOfOrigin.specified=true");

        // Get all the productDocumentsList where countryOfOrigin is null
        defaultProductDocumentsShouldNotBeFound("countryOfOrigin.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByCountryOfOriginContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where countryOfOrigin contains DEFAULT_COUNTRY_OF_ORIGIN
        defaultProductDocumentsShouldBeFound("countryOfOrigin.contains=" + DEFAULT_COUNTRY_OF_ORIGIN);

        // Get all the productDocumentsList where countryOfOrigin contains UPDATED_COUNTRY_OF_ORIGIN
        defaultProductDocumentsShouldNotBeFound("countryOfOrigin.contains=" + UPDATED_COUNTRY_OF_ORIGIN);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByCountryOfOriginNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where countryOfOrigin does not contain DEFAULT_COUNTRY_OF_ORIGIN
        defaultProductDocumentsShouldNotBeFound("countryOfOrigin.doesNotContain=" + DEFAULT_COUNTRY_OF_ORIGIN);

        // Get all the productDocumentsList where countryOfOrigin does not contain UPDATED_COUNTRY_OF_ORIGIN
        defaultProductDocumentsShouldBeFound("countryOfOrigin.doesNotContain=" + UPDATED_COUNTRY_OF_ORIGIN);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where warrantyPeriod equals to DEFAULT_WARRANTY_PERIOD
        defaultProductDocumentsShouldBeFound("warrantyPeriod.equals=" + DEFAULT_WARRANTY_PERIOD);

        // Get all the productDocumentsList where warrantyPeriod equals to UPDATED_WARRANTY_PERIOD
        defaultProductDocumentsShouldNotBeFound("warrantyPeriod.equals=" + UPDATED_WARRANTY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPeriodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where warrantyPeriod not equals to DEFAULT_WARRANTY_PERIOD
        defaultProductDocumentsShouldNotBeFound("warrantyPeriod.notEquals=" + DEFAULT_WARRANTY_PERIOD);

        // Get all the productDocumentsList where warrantyPeriod not equals to UPDATED_WARRANTY_PERIOD
        defaultProductDocumentsShouldBeFound("warrantyPeriod.notEquals=" + UPDATED_WARRANTY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPeriodIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where warrantyPeriod in DEFAULT_WARRANTY_PERIOD or UPDATED_WARRANTY_PERIOD
        defaultProductDocumentsShouldBeFound("warrantyPeriod.in=" + DEFAULT_WARRANTY_PERIOD + "," + UPDATED_WARRANTY_PERIOD);

        // Get all the productDocumentsList where warrantyPeriod equals to UPDATED_WARRANTY_PERIOD
        defaultProductDocumentsShouldNotBeFound("warrantyPeriod.in=" + UPDATED_WARRANTY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where warrantyPeriod is not null
        defaultProductDocumentsShouldBeFound("warrantyPeriod.specified=true");

        // Get all the productDocumentsList where warrantyPeriod is null
        defaultProductDocumentsShouldNotBeFound("warrantyPeriod.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPeriodContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where warrantyPeriod contains DEFAULT_WARRANTY_PERIOD
        defaultProductDocumentsShouldBeFound("warrantyPeriod.contains=" + DEFAULT_WARRANTY_PERIOD);

        // Get all the productDocumentsList where warrantyPeriod contains UPDATED_WARRANTY_PERIOD
        defaultProductDocumentsShouldNotBeFound("warrantyPeriod.contains=" + UPDATED_WARRANTY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPeriodNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where warrantyPeriod does not contain DEFAULT_WARRANTY_PERIOD
        defaultProductDocumentsShouldNotBeFound("warrantyPeriod.doesNotContain=" + DEFAULT_WARRANTY_PERIOD);

        // Get all the productDocumentsList where warrantyPeriod does not contain UPDATED_WARRANTY_PERIOD
        defaultProductDocumentsShouldBeFound("warrantyPeriod.doesNotContain=" + UPDATED_WARRANTY_PERIOD);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPolicyIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where warrantyPolicy equals to DEFAULT_WARRANTY_POLICY
        defaultProductDocumentsShouldBeFound("warrantyPolicy.equals=" + DEFAULT_WARRANTY_POLICY);

        // Get all the productDocumentsList where warrantyPolicy equals to UPDATED_WARRANTY_POLICY
        defaultProductDocumentsShouldNotBeFound("warrantyPolicy.equals=" + UPDATED_WARRANTY_POLICY);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPolicyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where warrantyPolicy not equals to DEFAULT_WARRANTY_POLICY
        defaultProductDocumentsShouldNotBeFound("warrantyPolicy.notEquals=" + DEFAULT_WARRANTY_POLICY);

        // Get all the productDocumentsList where warrantyPolicy not equals to UPDATED_WARRANTY_POLICY
        defaultProductDocumentsShouldBeFound("warrantyPolicy.notEquals=" + UPDATED_WARRANTY_POLICY);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPolicyIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where warrantyPolicy in DEFAULT_WARRANTY_POLICY or UPDATED_WARRANTY_POLICY
        defaultProductDocumentsShouldBeFound("warrantyPolicy.in=" + DEFAULT_WARRANTY_POLICY + "," + UPDATED_WARRANTY_POLICY);

        // Get all the productDocumentsList where warrantyPolicy equals to UPDATED_WARRANTY_POLICY
        defaultProductDocumentsShouldNotBeFound("warrantyPolicy.in=" + UPDATED_WARRANTY_POLICY);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPolicyIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where warrantyPolicy is not null
        defaultProductDocumentsShouldBeFound("warrantyPolicy.specified=true");

        // Get all the productDocumentsList where warrantyPolicy is null
        defaultProductDocumentsShouldNotBeFound("warrantyPolicy.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPolicyContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where warrantyPolicy contains DEFAULT_WARRANTY_POLICY
        defaultProductDocumentsShouldBeFound("warrantyPolicy.contains=" + DEFAULT_WARRANTY_POLICY);

        // Get all the productDocumentsList where warrantyPolicy contains UPDATED_WARRANTY_POLICY
        defaultProductDocumentsShouldNotBeFound("warrantyPolicy.contains=" + UPDATED_WARRANTY_POLICY);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyPolicyNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where warrantyPolicy does not contain DEFAULT_WARRANTY_POLICY
        defaultProductDocumentsShouldNotBeFound("warrantyPolicy.doesNotContain=" + DEFAULT_WARRANTY_POLICY);

        // Get all the productDocumentsList where warrantyPolicy does not contain UPDATED_WARRANTY_POLICY
        defaultProductDocumentsShouldBeFound("warrantyPolicy.doesNotContain=" + UPDATED_WARRANTY_POLICY);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByDangerousGoodsIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where dangerousGoods equals to DEFAULT_DANGEROUS_GOODS
        defaultProductDocumentsShouldBeFound("dangerousGoods.equals=" + DEFAULT_DANGEROUS_GOODS);

        // Get all the productDocumentsList where dangerousGoods equals to UPDATED_DANGEROUS_GOODS
        defaultProductDocumentsShouldNotBeFound("dangerousGoods.equals=" + UPDATED_DANGEROUS_GOODS);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByDangerousGoodsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where dangerousGoods not equals to DEFAULT_DANGEROUS_GOODS
        defaultProductDocumentsShouldNotBeFound("dangerousGoods.notEquals=" + DEFAULT_DANGEROUS_GOODS);

        // Get all the productDocumentsList where dangerousGoods not equals to UPDATED_DANGEROUS_GOODS
        defaultProductDocumentsShouldBeFound("dangerousGoods.notEquals=" + UPDATED_DANGEROUS_GOODS);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByDangerousGoodsIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where dangerousGoods in DEFAULT_DANGEROUS_GOODS or UPDATED_DANGEROUS_GOODS
        defaultProductDocumentsShouldBeFound("dangerousGoods.in=" + DEFAULT_DANGEROUS_GOODS + "," + UPDATED_DANGEROUS_GOODS);

        // Get all the productDocumentsList where dangerousGoods equals to UPDATED_DANGEROUS_GOODS
        defaultProductDocumentsShouldNotBeFound("dangerousGoods.in=" + UPDATED_DANGEROUS_GOODS);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByDangerousGoodsIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where dangerousGoods is not null
        defaultProductDocumentsShouldBeFound("dangerousGoods.specified=true");

        // Get all the productDocumentsList where dangerousGoods is null
        defaultProductDocumentsShouldNotBeFound("dangerousGoods.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByDangerousGoodsContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where dangerousGoods contains DEFAULT_DANGEROUS_GOODS
        defaultProductDocumentsShouldBeFound("dangerousGoods.contains=" + DEFAULT_DANGEROUS_GOODS);

        // Get all the productDocumentsList where dangerousGoods contains UPDATED_DANGEROUS_GOODS
        defaultProductDocumentsShouldNotBeFound("dangerousGoods.contains=" + UPDATED_DANGEROUS_GOODS);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByDangerousGoodsNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where dangerousGoods does not contain DEFAULT_DANGEROUS_GOODS
        defaultProductDocumentsShouldNotBeFound("dangerousGoods.doesNotContain=" + DEFAULT_DANGEROUS_GOODS);

        // Get all the productDocumentsList where dangerousGoods does not contain UPDATED_DANGEROUS_GOODS
        defaultProductDocumentsShouldBeFound("dangerousGoods.doesNotContain=" + UPDATED_DANGEROUS_GOODS);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultProductDocumentsShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the productDocumentsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultProductDocumentsShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultProductDocumentsShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the productDocumentsList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultProductDocumentsShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultProductDocumentsShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the productDocumentsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultProductDocumentsShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where lastEditedBy is not null
        defaultProductDocumentsShouldBeFound("lastEditedBy.specified=true");

        // Get all the productDocumentsList where lastEditedBy is null
        defaultProductDocumentsShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDocumentsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultProductDocumentsShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the productDocumentsList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultProductDocumentsShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultProductDocumentsShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the productDocumentsList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultProductDocumentsShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultProductDocumentsShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the productDocumentsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultProductDocumentsShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultProductDocumentsShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the productDocumentsList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultProductDocumentsShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultProductDocumentsShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the productDocumentsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultProductDocumentsShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        // Get all the productDocumentsList where lastEditedWhen is not null
        defaultProductDocumentsShouldBeFound("lastEditedWhen.specified=true");

        // Get all the productDocumentsList where lastEditedWhen is null
        defaultProductDocumentsShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductDocumentsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);
        Products product = ProductsResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        productDocuments.setProduct(product);
        productDocumentsRepository.saveAndFlush(productDocuments);
        Long productId = product.getId();

        // Get all the productDocumentsList where product equals to productId
        defaultProductDocumentsShouldBeFound("productId.equals=" + productId);

        // Get all the productDocumentsList where product equals to productId + 1
        defaultProductDocumentsShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllProductDocumentsByWarrantyTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);
        WarrantyTypes warrantyType = WarrantyTypesResourceIT.createEntity(em);
        em.persist(warrantyType);
        em.flush();
        productDocuments.setWarrantyType(warrantyType);
        productDocumentsRepository.saveAndFlush(productDocuments);
        Long warrantyTypeId = warrantyType.getId();

        // Get all the productDocumentsList where warrantyType equals to warrantyTypeId
        defaultProductDocumentsShouldBeFound("warrantyTypeId.equals=" + warrantyTypeId);

        // Get all the productDocumentsList where warrantyType equals to warrantyTypeId + 1
        defaultProductDocumentsShouldNotBeFound("warrantyTypeId.equals=" + (warrantyTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductDocumentsShouldBeFound(String filter) throws Exception {
        restProductDocumentsMockMvc.perform(get("/api/product-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productDocuments.getId().intValue())))
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
        restProductDocumentsMockMvc.perform(get("/api/product-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductDocumentsShouldNotBeFound(String filter) throws Exception {
        restProductDocumentsMockMvc.perform(get("/api/product-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductDocumentsMockMvc.perform(get("/api/product-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductDocuments() throws Exception {
        // Get the productDocuments
        restProductDocumentsMockMvc.perform(get("/api/product-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductDocuments() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        int databaseSizeBeforeUpdate = productDocumentsRepository.findAll().size();

        // Update the productDocuments
        ProductDocuments updatedProductDocuments = productDocumentsRepository.findById(productDocuments.getId()).get();
        // Disconnect from session so that the updates on updatedProductDocuments are not directly saved in db
        em.detach(updatedProductDocuments);
        updatedProductDocuments
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
        ProductDocumentsDTO productDocumentsDTO = productDocumentsMapper.toDto(updatedProductDocuments);

        restProductDocumentsMockMvc.perform(put("/api/product-documents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentsDTO)))
            .andExpect(status().isOk());

        // Validate the ProductDocuments in the database
        List<ProductDocuments> productDocumentsList = productDocumentsRepository.findAll();
        assertThat(productDocumentsList).hasSize(databaseSizeBeforeUpdate);
        ProductDocuments testProductDocuments = productDocumentsList.get(productDocumentsList.size() - 1);
        assertThat(testProductDocuments.getVideoUrl()).isEqualTo(UPDATED_VIDEO_URL);
        assertThat(testProductDocuments.getHighlights()).isEqualTo(UPDATED_HIGHLIGHTS);
        assertThat(testProductDocuments.getLongDescription()).isEqualTo(UPDATED_LONG_DESCRIPTION);
        assertThat(testProductDocuments.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testProductDocuments.getWhatInTheBox()).isEqualTo(UPDATED_WHAT_IN_THE_BOX);
        assertThat(testProductDocuments.getCareInstructions()).isEqualTo(UPDATED_CARE_INSTRUCTIONS);
        assertThat(testProductDocuments.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
        assertThat(testProductDocuments.getModelName()).isEqualTo(UPDATED_MODEL_NAME);
        assertThat(testProductDocuments.getModelNumber()).isEqualTo(UPDATED_MODEL_NUMBER);
        assertThat(testProductDocuments.getFabricType()).isEqualTo(UPDATED_FABRIC_TYPE);
        assertThat(testProductDocuments.getSpecialFeatures()).isEqualTo(UPDATED_SPECIAL_FEATURES);
        assertThat(testProductDocuments.getProductComplianceCertificate()).isEqualTo(UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
        assertThat(testProductDocuments.isGenuineAndLegal()).isEqualTo(UPDATED_GENUINE_AND_LEGAL);
        assertThat(testProductDocuments.getCountryOfOrigin()).isEqualTo(UPDATED_COUNTRY_OF_ORIGIN);
        assertThat(testProductDocuments.getUsageAndSideEffects()).isEqualTo(UPDATED_USAGE_AND_SIDE_EFFECTS);
        assertThat(testProductDocuments.getSafetyWarnning()).isEqualTo(UPDATED_SAFETY_WARNNING);
        assertThat(testProductDocuments.getWarrantyPeriod()).isEqualTo(UPDATED_WARRANTY_PERIOD);
        assertThat(testProductDocuments.getWarrantyPolicy()).isEqualTo(UPDATED_WARRANTY_POLICY);
        assertThat(testProductDocuments.getDangerousGoods()).isEqualTo(UPDATED_DANGEROUS_GOODS);
        assertThat(testProductDocuments.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testProductDocuments.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingProductDocuments() throws Exception {
        int databaseSizeBeforeUpdate = productDocumentsRepository.findAll().size();

        // Create the ProductDocuments
        ProductDocumentsDTO productDocumentsDTO = productDocumentsMapper.toDto(productDocuments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductDocumentsMockMvc.perform(put("/api/product-documents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDocumentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductDocuments in the database
        List<ProductDocuments> productDocumentsList = productDocumentsRepository.findAll();
        assertThat(productDocumentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductDocuments() throws Exception {
        // Initialize the database
        productDocumentsRepository.saveAndFlush(productDocuments);

        int databaseSizeBeforeDelete = productDocumentsRepository.findAll().size();

        // Delete the productDocuments
        restProductDocumentsMockMvc.perform(delete("/api/product-documents/{id}", productDocuments.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductDocuments> productDocumentsList = productDocumentsRepository.findAll();
        assertThat(productDocumentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.UploadTransactions;
import com.vertical.commerce.domain.Suppliers;
import com.vertical.commerce.domain.UploadActionTypes;
import com.vertical.commerce.repository.UploadTransactionsRepository;
import com.vertical.commerce.service.UploadTransactionsService;
import com.vertical.commerce.service.dto.UploadTransactionsDTO;
import com.vertical.commerce.service.mapper.UploadTransactionsMapper;
import com.vertical.commerce.service.dto.UploadTransactionsCriteria;
import com.vertical.commerce.service.UploadTransactionsQueryService;

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
 * Integration tests for the {@link UploadTransactionsResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UploadTransactionsResourceIT {

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMPORTED_TEMPLATE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMPORTED_TEMPLATE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMPORTED_TEMPLATE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMPORTED_TEMPLATE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMPORTED_FAILED_TEMPLATE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMPORTED_FAILED_TEMPLATE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final Integer SMALLER_STATUS = 1 - 1;

    private static final String DEFAULT_GENERATED_CODE = "AAAAAAAAAA";
    private static final String UPDATED_GENERATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UploadTransactionsRepository uploadTransactionsRepository;

    @Autowired
    private UploadTransactionsMapper uploadTransactionsMapper;

    @Autowired
    private UploadTransactionsService uploadTransactionsService;

    @Autowired
    private UploadTransactionsQueryService uploadTransactionsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUploadTransactionsMockMvc;

    private UploadTransactions uploadTransactions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UploadTransactions createEntity(EntityManager em) {
        UploadTransactions uploadTransactions = new UploadTransactions()
            .fileName(DEFAULT_FILE_NAME)
            .importedTemplate(DEFAULT_IMPORTED_TEMPLATE)
            .importedTemplateContentType(DEFAULT_IMPORTED_TEMPLATE_CONTENT_TYPE)
            .importedFailedTemplate(DEFAULT_IMPORTED_FAILED_TEMPLATE)
            .importedFailedTemplateContentType(DEFAULT_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE)
            .status(DEFAULT_STATUS)
            .generatedCode(DEFAULT_GENERATED_CODE)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return uploadTransactions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UploadTransactions createUpdatedEntity(EntityManager em) {
        UploadTransactions uploadTransactions = new UploadTransactions()
            .fileName(UPDATED_FILE_NAME)
            .importedTemplate(UPDATED_IMPORTED_TEMPLATE)
            .importedTemplateContentType(UPDATED_IMPORTED_TEMPLATE_CONTENT_TYPE)
            .importedFailedTemplate(UPDATED_IMPORTED_FAILED_TEMPLATE)
            .importedFailedTemplateContentType(UPDATED_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .generatedCode(UPDATED_GENERATED_CODE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return uploadTransactions;
    }

    @BeforeEach
    public void initTest() {
        uploadTransactions = createEntity(em);
    }

    @Test
    @Transactional
    public void createUploadTransactions() throws Exception {
        int databaseSizeBeforeCreate = uploadTransactionsRepository.findAll().size();
        // Create the UploadTransactions
        UploadTransactionsDTO uploadTransactionsDTO = uploadTransactionsMapper.toDto(uploadTransactions);
        restUploadTransactionsMockMvc.perform(post("/api/upload-transactions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadTransactionsDTO)))
            .andExpect(status().isCreated());

        // Validate the UploadTransactions in the database
        List<UploadTransactions> uploadTransactionsList = uploadTransactionsRepository.findAll();
        assertThat(uploadTransactionsList).hasSize(databaseSizeBeforeCreate + 1);
        UploadTransactions testUploadTransactions = uploadTransactionsList.get(uploadTransactionsList.size() - 1);
        assertThat(testUploadTransactions.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testUploadTransactions.getImportedTemplate()).isEqualTo(DEFAULT_IMPORTED_TEMPLATE);
        assertThat(testUploadTransactions.getImportedTemplateContentType()).isEqualTo(DEFAULT_IMPORTED_TEMPLATE_CONTENT_TYPE);
        assertThat(testUploadTransactions.getImportedFailedTemplate()).isEqualTo(DEFAULT_IMPORTED_FAILED_TEMPLATE);
        assertThat(testUploadTransactions.getImportedFailedTemplateContentType()).isEqualTo(DEFAULT_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE);
        assertThat(testUploadTransactions.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUploadTransactions.getGeneratedCode()).isEqualTo(DEFAULT_GENERATED_CODE);
        assertThat(testUploadTransactions.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testUploadTransactions.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createUploadTransactionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uploadTransactionsRepository.findAll().size();

        // Create the UploadTransactions with an existing ID
        uploadTransactions.setId(1L);
        UploadTransactionsDTO uploadTransactionsDTO = uploadTransactionsMapper.toDto(uploadTransactions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUploadTransactionsMockMvc.perform(post("/api/upload-transactions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UploadTransactions in the database
        List<UploadTransactions> uploadTransactionsList = uploadTransactionsRepository.findAll();
        assertThat(uploadTransactionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLastEditedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = uploadTransactionsRepository.findAll().size();
        // set the field null
        uploadTransactions.setLastEditedBy(null);

        // Create the UploadTransactions, which fails.
        UploadTransactionsDTO uploadTransactionsDTO = uploadTransactionsMapper.toDto(uploadTransactions);


        restUploadTransactionsMockMvc.perform(post("/api/upload-transactions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<UploadTransactions> uploadTransactionsList = uploadTransactionsRepository.findAll();
        assertThat(uploadTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = uploadTransactionsRepository.findAll().size();
        // set the field null
        uploadTransactions.setLastEditedWhen(null);

        // Create the UploadTransactions, which fails.
        UploadTransactionsDTO uploadTransactionsDTO = uploadTransactionsMapper.toDto(uploadTransactions);


        restUploadTransactionsMockMvc.perform(post("/api/upload-transactions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<UploadTransactions> uploadTransactionsList = uploadTransactionsRepository.findAll();
        assertThat(uploadTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUploadTransactions() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList
        restUploadTransactionsMockMvc.perform(get("/api/upload-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uploadTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].importedTemplateContentType").value(hasItem(DEFAULT_IMPORTED_TEMPLATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].importedTemplate").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMPORTED_TEMPLATE))))
            .andExpect(jsonPath("$.[*].importedFailedTemplateContentType").value(hasItem(DEFAULT_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].importedFailedTemplate").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMPORTED_FAILED_TEMPLATE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].generatedCode").value(hasItem(DEFAULT_GENERATED_CODE)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getUploadTransactions() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get the uploadTransactions
        restUploadTransactionsMockMvc.perform(get("/api/upload-transactions/{id}", uploadTransactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uploadTransactions.getId().intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.importedTemplateContentType").value(DEFAULT_IMPORTED_TEMPLATE_CONTENT_TYPE))
            .andExpect(jsonPath("$.importedTemplate").value(Base64Utils.encodeToString(DEFAULT_IMPORTED_TEMPLATE)))
            .andExpect(jsonPath("$.importedFailedTemplateContentType").value(DEFAULT_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE))
            .andExpect(jsonPath("$.importedFailedTemplate").value(Base64Utils.encodeToString(DEFAULT_IMPORTED_FAILED_TEMPLATE)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.generatedCode").value(DEFAULT_GENERATED_CODE))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getUploadTransactionsByIdFiltering() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        Long id = uploadTransactions.getId();

        defaultUploadTransactionsShouldBeFound("id.equals=" + id);
        defaultUploadTransactionsShouldNotBeFound("id.notEquals=" + id);

        defaultUploadTransactionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUploadTransactionsShouldNotBeFound("id.greaterThan=" + id);

        defaultUploadTransactionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUploadTransactionsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUploadTransactionsByFileNameIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where fileName equals to DEFAULT_FILE_NAME
        defaultUploadTransactionsShouldBeFound("fileName.equals=" + DEFAULT_FILE_NAME);

        // Get all the uploadTransactionsList where fileName equals to UPDATED_FILE_NAME
        defaultUploadTransactionsShouldNotBeFound("fileName.equals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByFileNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where fileName not equals to DEFAULT_FILE_NAME
        defaultUploadTransactionsShouldNotBeFound("fileName.notEquals=" + DEFAULT_FILE_NAME);

        // Get all the uploadTransactionsList where fileName not equals to UPDATED_FILE_NAME
        defaultUploadTransactionsShouldBeFound("fileName.notEquals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByFileNameIsInShouldWork() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where fileName in DEFAULT_FILE_NAME or UPDATED_FILE_NAME
        defaultUploadTransactionsShouldBeFound("fileName.in=" + DEFAULT_FILE_NAME + "," + UPDATED_FILE_NAME);

        // Get all the uploadTransactionsList where fileName equals to UPDATED_FILE_NAME
        defaultUploadTransactionsShouldNotBeFound("fileName.in=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByFileNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where fileName is not null
        defaultUploadTransactionsShouldBeFound("fileName.specified=true");

        // Get all the uploadTransactionsList where fileName is null
        defaultUploadTransactionsShouldNotBeFound("fileName.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadTransactionsByFileNameContainsSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where fileName contains DEFAULT_FILE_NAME
        defaultUploadTransactionsShouldBeFound("fileName.contains=" + DEFAULT_FILE_NAME);

        // Get all the uploadTransactionsList where fileName contains UPDATED_FILE_NAME
        defaultUploadTransactionsShouldNotBeFound("fileName.contains=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByFileNameNotContainsSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where fileName does not contain DEFAULT_FILE_NAME
        defaultUploadTransactionsShouldNotBeFound("fileName.doesNotContain=" + DEFAULT_FILE_NAME);

        // Get all the uploadTransactionsList where fileName does not contain UPDATED_FILE_NAME
        defaultUploadTransactionsShouldBeFound("fileName.doesNotContain=" + UPDATED_FILE_NAME);
    }


    @Test
    @Transactional
    public void getAllUploadTransactionsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where status equals to DEFAULT_STATUS
        defaultUploadTransactionsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the uploadTransactionsList where status equals to UPDATED_STATUS
        defaultUploadTransactionsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where status not equals to DEFAULT_STATUS
        defaultUploadTransactionsShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the uploadTransactionsList where status not equals to UPDATED_STATUS
        defaultUploadTransactionsShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultUploadTransactionsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the uploadTransactionsList where status equals to UPDATED_STATUS
        defaultUploadTransactionsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where status is not null
        defaultUploadTransactionsShouldBeFound("status.specified=true");

        // Get all the uploadTransactionsList where status is null
        defaultUploadTransactionsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where status is greater than or equal to DEFAULT_STATUS
        defaultUploadTransactionsShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the uploadTransactionsList where status is greater than or equal to UPDATED_STATUS
        defaultUploadTransactionsShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where status is less than or equal to DEFAULT_STATUS
        defaultUploadTransactionsShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the uploadTransactionsList where status is less than or equal to SMALLER_STATUS
        defaultUploadTransactionsShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where status is less than DEFAULT_STATUS
        defaultUploadTransactionsShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the uploadTransactionsList where status is less than UPDATED_STATUS
        defaultUploadTransactionsShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where status is greater than DEFAULT_STATUS
        defaultUploadTransactionsShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the uploadTransactionsList where status is greater than SMALLER_STATUS
        defaultUploadTransactionsShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }


    @Test
    @Transactional
    public void getAllUploadTransactionsByGeneratedCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where generatedCode equals to DEFAULT_GENERATED_CODE
        defaultUploadTransactionsShouldBeFound("generatedCode.equals=" + DEFAULT_GENERATED_CODE);

        // Get all the uploadTransactionsList where generatedCode equals to UPDATED_GENERATED_CODE
        defaultUploadTransactionsShouldNotBeFound("generatedCode.equals=" + UPDATED_GENERATED_CODE);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByGeneratedCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where generatedCode not equals to DEFAULT_GENERATED_CODE
        defaultUploadTransactionsShouldNotBeFound("generatedCode.notEquals=" + DEFAULT_GENERATED_CODE);

        // Get all the uploadTransactionsList where generatedCode not equals to UPDATED_GENERATED_CODE
        defaultUploadTransactionsShouldBeFound("generatedCode.notEquals=" + UPDATED_GENERATED_CODE);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByGeneratedCodeIsInShouldWork() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where generatedCode in DEFAULT_GENERATED_CODE or UPDATED_GENERATED_CODE
        defaultUploadTransactionsShouldBeFound("generatedCode.in=" + DEFAULT_GENERATED_CODE + "," + UPDATED_GENERATED_CODE);

        // Get all the uploadTransactionsList where generatedCode equals to UPDATED_GENERATED_CODE
        defaultUploadTransactionsShouldNotBeFound("generatedCode.in=" + UPDATED_GENERATED_CODE);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByGeneratedCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where generatedCode is not null
        defaultUploadTransactionsShouldBeFound("generatedCode.specified=true");

        // Get all the uploadTransactionsList where generatedCode is null
        defaultUploadTransactionsShouldNotBeFound("generatedCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadTransactionsByGeneratedCodeContainsSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where generatedCode contains DEFAULT_GENERATED_CODE
        defaultUploadTransactionsShouldBeFound("generatedCode.contains=" + DEFAULT_GENERATED_CODE);

        // Get all the uploadTransactionsList where generatedCode contains UPDATED_GENERATED_CODE
        defaultUploadTransactionsShouldNotBeFound("generatedCode.contains=" + UPDATED_GENERATED_CODE);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByGeneratedCodeNotContainsSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where generatedCode does not contain DEFAULT_GENERATED_CODE
        defaultUploadTransactionsShouldNotBeFound("generatedCode.doesNotContain=" + DEFAULT_GENERATED_CODE);

        // Get all the uploadTransactionsList where generatedCode does not contain UPDATED_GENERATED_CODE
        defaultUploadTransactionsShouldBeFound("generatedCode.doesNotContain=" + UPDATED_GENERATED_CODE);
    }


    @Test
    @Transactional
    public void getAllUploadTransactionsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultUploadTransactionsShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the uploadTransactionsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultUploadTransactionsShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultUploadTransactionsShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the uploadTransactionsList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultUploadTransactionsShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultUploadTransactionsShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the uploadTransactionsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultUploadTransactionsShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where lastEditedBy is not null
        defaultUploadTransactionsShouldBeFound("lastEditedBy.specified=true");

        // Get all the uploadTransactionsList where lastEditedBy is null
        defaultUploadTransactionsShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadTransactionsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultUploadTransactionsShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the uploadTransactionsList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultUploadTransactionsShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultUploadTransactionsShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the uploadTransactionsList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultUploadTransactionsShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllUploadTransactionsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultUploadTransactionsShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the uploadTransactionsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultUploadTransactionsShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultUploadTransactionsShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the uploadTransactionsList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultUploadTransactionsShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultUploadTransactionsShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the uploadTransactionsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultUploadTransactionsShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where lastEditedWhen is not null
        defaultUploadTransactionsShouldBeFound("lastEditedWhen.specified=true");

        // Get all the uploadTransactionsList where lastEditedWhen is null
        defaultUploadTransactionsShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);
        Suppliers supplier = SuppliersResourceIT.createEntity(em);
        em.persist(supplier);
        em.flush();
        uploadTransactions.setSupplier(supplier);
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);
        Long supplierId = supplier.getId();

        // Get all the uploadTransactionsList where supplier equals to supplierId
        defaultUploadTransactionsShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the uploadTransactionsList where supplier equals to supplierId + 1
        defaultUploadTransactionsShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }


    @Test
    @Transactional
    public void getAllUploadTransactionsByActionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);
        UploadActionTypes actionType = UploadActionTypesResourceIT.createEntity(em);
        em.persist(actionType);
        em.flush();
        uploadTransactions.setActionType(actionType);
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);
        Long actionTypeId = actionType.getId();

        // Get all the uploadTransactionsList where actionType equals to actionTypeId
        defaultUploadTransactionsShouldBeFound("actionTypeId.equals=" + actionTypeId);

        // Get all the uploadTransactionsList where actionType equals to actionTypeId + 1
        defaultUploadTransactionsShouldNotBeFound("actionTypeId.equals=" + (actionTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUploadTransactionsShouldBeFound(String filter) throws Exception {
        restUploadTransactionsMockMvc.perform(get("/api/upload-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uploadTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].importedTemplateContentType").value(hasItem(DEFAULT_IMPORTED_TEMPLATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].importedTemplate").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMPORTED_TEMPLATE))))
            .andExpect(jsonPath("$.[*].importedFailedTemplateContentType").value(hasItem(DEFAULT_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].importedFailedTemplate").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMPORTED_FAILED_TEMPLATE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].generatedCode").value(hasItem(DEFAULT_GENERATED_CODE)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restUploadTransactionsMockMvc.perform(get("/api/upload-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUploadTransactionsShouldNotBeFound(String filter) throws Exception {
        restUploadTransactionsMockMvc.perform(get("/api/upload-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUploadTransactionsMockMvc.perform(get("/api/upload-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingUploadTransactions() throws Exception {
        // Get the uploadTransactions
        restUploadTransactionsMockMvc.perform(get("/api/upload-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUploadTransactions() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        int databaseSizeBeforeUpdate = uploadTransactionsRepository.findAll().size();

        // Update the uploadTransactions
        UploadTransactions updatedUploadTransactions = uploadTransactionsRepository.findById(uploadTransactions.getId()).get();
        // Disconnect from session so that the updates on updatedUploadTransactions are not directly saved in db
        em.detach(updatedUploadTransactions);
        updatedUploadTransactions
            .fileName(UPDATED_FILE_NAME)
            .importedTemplate(UPDATED_IMPORTED_TEMPLATE)
            .importedTemplateContentType(UPDATED_IMPORTED_TEMPLATE_CONTENT_TYPE)
            .importedFailedTemplate(UPDATED_IMPORTED_FAILED_TEMPLATE)
            .importedFailedTemplateContentType(UPDATED_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .generatedCode(UPDATED_GENERATED_CODE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        UploadTransactionsDTO uploadTransactionsDTO = uploadTransactionsMapper.toDto(updatedUploadTransactions);

        restUploadTransactionsMockMvc.perform(put("/api/upload-transactions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadTransactionsDTO)))
            .andExpect(status().isOk());

        // Validate the UploadTransactions in the database
        List<UploadTransactions> uploadTransactionsList = uploadTransactionsRepository.findAll();
        assertThat(uploadTransactionsList).hasSize(databaseSizeBeforeUpdate);
        UploadTransactions testUploadTransactions = uploadTransactionsList.get(uploadTransactionsList.size() - 1);
        assertThat(testUploadTransactions.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testUploadTransactions.getImportedTemplate()).isEqualTo(UPDATED_IMPORTED_TEMPLATE);
        assertThat(testUploadTransactions.getImportedTemplateContentType()).isEqualTo(UPDATED_IMPORTED_TEMPLATE_CONTENT_TYPE);
        assertThat(testUploadTransactions.getImportedFailedTemplate()).isEqualTo(UPDATED_IMPORTED_FAILED_TEMPLATE);
        assertThat(testUploadTransactions.getImportedFailedTemplateContentType()).isEqualTo(UPDATED_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE);
        assertThat(testUploadTransactions.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUploadTransactions.getGeneratedCode()).isEqualTo(UPDATED_GENERATED_CODE);
        assertThat(testUploadTransactions.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testUploadTransactions.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingUploadTransactions() throws Exception {
        int databaseSizeBeforeUpdate = uploadTransactionsRepository.findAll().size();

        // Create the UploadTransactions
        UploadTransactionsDTO uploadTransactionsDTO = uploadTransactionsMapper.toDto(uploadTransactions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUploadTransactionsMockMvc.perform(put("/api/upload-transactions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UploadTransactions in the database
        List<UploadTransactions> uploadTransactionsList = uploadTransactionsRepository.findAll();
        assertThat(uploadTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUploadTransactions() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        int databaseSizeBeforeDelete = uploadTransactionsRepository.findAll().size();

        // Delete the uploadTransactions
        restUploadTransactionsMockMvc.perform(delete("/api/upload-transactions/{id}", uploadTransactions.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UploadTransactions> uploadTransactionsList = uploadTransactionsRepository.findAll();
        assertThat(uploadTransactionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

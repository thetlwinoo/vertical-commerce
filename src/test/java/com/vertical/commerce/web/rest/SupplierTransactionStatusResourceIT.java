package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.SupplierTransactionStatus;
import com.vertical.commerce.repository.SupplierTransactionStatusRepository;
import com.vertical.commerce.service.SupplierTransactionStatusService;
import com.vertical.commerce.service.dto.SupplierTransactionStatusDTO;
import com.vertical.commerce.service.mapper.SupplierTransactionStatusMapper;
import com.vertical.commerce.service.dto.SupplierTransactionStatusCriteria;
import com.vertical.commerce.service.SupplierTransactionStatusQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SupplierTransactionStatusResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class SupplierTransactionStatusResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SupplierTransactionStatusRepository supplierTransactionStatusRepository;

    @Autowired
    private SupplierTransactionStatusMapper supplierTransactionStatusMapper;

    @Autowired
    private SupplierTransactionStatusService supplierTransactionStatusService;

    @Autowired
    private SupplierTransactionStatusQueryService supplierTransactionStatusQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupplierTransactionStatusMockMvc;

    private SupplierTransactionStatus supplierTransactionStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupplierTransactionStatus createEntity(EntityManager em) {
        SupplierTransactionStatus supplierTransactionStatus = new SupplierTransactionStatus()
            .name(DEFAULT_NAME)
            .label(DEFAULT_LABEL)
            .shortLabel(DEFAULT_SHORT_LABEL)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return supplierTransactionStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupplierTransactionStatus createUpdatedEntity(EntityManager em) {
        SupplierTransactionStatus supplierTransactionStatus = new SupplierTransactionStatus()
            .name(UPDATED_NAME)
            .label(UPDATED_LABEL)
            .shortLabel(UPDATED_SHORT_LABEL)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return supplierTransactionStatus;
    }

    @BeforeEach
    public void initTest() {
        supplierTransactionStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplierTransactionStatus() throws Exception {
        int databaseSizeBeforeCreate = supplierTransactionStatusRepository.findAll().size();
        // Create the SupplierTransactionStatus
        SupplierTransactionStatusDTO supplierTransactionStatusDTO = supplierTransactionStatusMapper.toDto(supplierTransactionStatus);
        restSupplierTransactionStatusMockMvc.perform(post("/api/supplier-transaction-statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the SupplierTransactionStatus in the database
        List<SupplierTransactionStatus> supplierTransactionStatusList = supplierTransactionStatusRepository.findAll();
        assertThat(supplierTransactionStatusList).hasSize(databaseSizeBeforeCreate + 1);
        SupplierTransactionStatus testSupplierTransactionStatus = supplierTransactionStatusList.get(supplierTransactionStatusList.size() - 1);
        assertThat(testSupplierTransactionStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSupplierTransactionStatus.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testSupplierTransactionStatus.getShortLabel()).isEqualTo(DEFAULT_SHORT_LABEL);
        assertThat(testSupplierTransactionStatus.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testSupplierTransactionStatus.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createSupplierTransactionStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplierTransactionStatusRepository.findAll().size();

        // Create the SupplierTransactionStatus with an existing ID
        supplierTransactionStatus.setId(1L);
        SupplierTransactionStatusDTO supplierTransactionStatusDTO = supplierTransactionStatusMapper.toDto(supplierTransactionStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplierTransactionStatusMockMvc.perform(post("/api/supplier-transaction-statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplierTransactionStatus in the database
        List<SupplierTransactionStatus> supplierTransactionStatusList = supplierTransactionStatusRepository.findAll();
        assertThat(supplierTransactionStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLastEditedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierTransactionStatusRepository.findAll().size();
        // set the field null
        supplierTransactionStatus.setLastEditedBy(null);

        // Create the SupplierTransactionStatus, which fails.
        SupplierTransactionStatusDTO supplierTransactionStatusDTO = supplierTransactionStatusMapper.toDto(supplierTransactionStatus);


        restSupplierTransactionStatusMockMvc.perform(post("/api/supplier-transaction-statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionStatusDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierTransactionStatus> supplierTransactionStatusList = supplierTransactionStatusRepository.findAll();
        assertThat(supplierTransactionStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierTransactionStatusRepository.findAll().size();
        // set the field null
        supplierTransactionStatus.setLastEditedWhen(null);

        // Create the SupplierTransactionStatus, which fails.
        SupplierTransactionStatusDTO supplierTransactionStatusDTO = supplierTransactionStatusMapper.toDto(supplierTransactionStatus);


        restSupplierTransactionStatusMockMvc.perform(post("/api/supplier-transaction-statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionStatusDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierTransactionStatus> supplierTransactionStatusList = supplierTransactionStatusRepository.findAll();
        assertThat(supplierTransactionStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionStatuses() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList
        restSupplierTransactionStatusMockMvc.perform(get("/api/supplier-transaction-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierTransactionStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].shortLabel").value(hasItem(DEFAULT_SHORT_LABEL)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getSupplierTransactionStatus() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get the supplierTransactionStatus
        restSupplierTransactionStatusMockMvc.perform(get("/api/supplier-transaction-statuses/{id}", supplierTransactionStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supplierTransactionStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.shortLabel").value(DEFAULT_SHORT_LABEL))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getSupplierTransactionStatusesByIdFiltering() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        Long id = supplierTransactionStatus.getId();

        defaultSupplierTransactionStatusShouldBeFound("id.equals=" + id);
        defaultSupplierTransactionStatusShouldNotBeFound("id.notEquals=" + id);

        defaultSupplierTransactionStatusShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSupplierTransactionStatusShouldNotBeFound("id.greaterThan=" + id);

        defaultSupplierTransactionStatusShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSupplierTransactionStatusShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where name equals to DEFAULT_NAME
        defaultSupplierTransactionStatusShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the supplierTransactionStatusList where name equals to UPDATED_NAME
        defaultSupplierTransactionStatusShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where name not equals to DEFAULT_NAME
        defaultSupplierTransactionStatusShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the supplierTransactionStatusList where name not equals to UPDATED_NAME
        defaultSupplierTransactionStatusShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSupplierTransactionStatusShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the supplierTransactionStatusList where name equals to UPDATED_NAME
        defaultSupplierTransactionStatusShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where name is not null
        defaultSupplierTransactionStatusShouldBeFound("name.specified=true");

        // Get all the supplierTransactionStatusList where name is null
        defaultSupplierTransactionStatusShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByNameContainsSomething() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where name contains DEFAULT_NAME
        defaultSupplierTransactionStatusShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the supplierTransactionStatusList where name contains UPDATED_NAME
        defaultSupplierTransactionStatusShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where name does not contain DEFAULT_NAME
        defaultSupplierTransactionStatusShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the supplierTransactionStatusList where name does not contain UPDATED_NAME
        defaultSupplierTransactionStatusShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where label equals to DEFAULT_LABEL
        defaultSupplierTransactionStatusShouldBeFound("label.equals=" + DEFAULT_LABEL);

        // Get all the supplierTransactionStatusList where label equals to UPDATED_LABEL
        defaultSupplierTransactionStatusShouldNotBeFound("label.equals=" + UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByLabelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where label not equals to DEFAULT_LABEL
        defaultSupplierTransactionStatusShouldNotBeFound("label.notEquals=" + DEFAULT_LABEL);

        // Get all the supplierTransactionStatusList where label not equals to UPDATED_LABEL
        defaultSupplierTransactionStatusShouldBeFound("label.notEquals=" + UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByLabelIsInShouldWork() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where label in DEFAULT_LABEL or UPDATED_LABEL
        defaultSupplierTransactionStatusShouldBeFound("label.in=" + DEFAULT_LABEL + "," + UPDATED_LABEL);

        // Get all the supplierTransactionStatusList where label equals to UPDATED_LABEL
        defaultSupplierTransactionStatusShouldNotBeFound("label.in=" + UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where label is not null
        defaultSupplierTransactionStatusShouldBeFound("label.specified=true");

        // Get all the supplierTransactionStatusList where label is null
        defaultSupplierTransactionStatusShouldNotBeFound("label.specified=false");
    }
                @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByLabelContainsSomething() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where label contains DEFAULT_LABEL
        defaultSupplierTransactionStatusShouldBeFound("label.contains=" + DEFAULT_LABEL);

        // Get all the supplierTransactionStatusList where label contains UPDATED_LABEL
        defaultSupplierTransactionStatusShouldNotBeFound("label.contains=" + UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByLabelNotContainsSomething() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where label does not contain DEFAULT_LABEL
        defaultSupplierTransactionStatusShouldNotBeFound("label.doesNotContain=" + DEFAULT_LABEL);

        // Get all the supplierTransactionStatusList where label does not contain UPDATED_LABEL
        defaultSupplierTransactionStatusShouldBeFound("label.doesNotContain=" + UPDATED_LABEL);
    }


    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByShortLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where shortLabel equals to DEFAULT_SHORT_LABEL
        defaultSupplierTransactionStatusShouldBeFound("shortLabel.equals=" + DEFAULT_SHORT_LABEL);

        // Get all the supplierTransactionStatusList where shortLabel equals to UPDATED_SHORT_LABEL
        defaultSupplierTransactionStatusShouldNotBeFound("shortLabel.equals=" + UPDATED_SHORT_LABEL);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByShortLabelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where shortLabel not equals to DEFAULT_SHORT_LABEL
        defaultSupplierTransactionStatusShouldNotBeFound("shortLabel.notEquals=" + DEFAULT_SHORT_LABEL);

        // Get all the supplierTransactionStatusList where shortLabel not equals to UPDATED_SHORT_LABEL
        defaultSupplierTransactionStatusShouldBeFound("shortLabel.notEquals=" + UPDATED_SHORT_LABEL);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByShortLabelIsInShouldWork() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where shortLabel in DEFAULT_SHORT_LABEL or UPDATED_SHORT_LABEL
        defaultSupplierTransactionStatusShouldBeFound("shortLabel.in=" + DEFAULT_SHORT_LABEL + "," + UPDATED_SHORT_LABEL);

        // Get all the supplierTransactionStatusList where shortLabel equals to UPDATED_SHORT_LABEL
        defaultSupplierTransactionStatusShouldNotBeFound("shortLabel.in=" + UPDATED_SHORT_LABEL);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByShortLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where shortLabel is not null
        defaultSupplierTransactionStatusShouldBeFound("shortLabel.specified=true");

        // Get all the supplierTransactionStatusList where shortLabel is null
        defaultSupplierTransactionStatusShouldNotBeFound("shortLabel.specified=false");
    }
                @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByShortLabelContainsSomething() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where shortLabel contains DEFAULT_SHORT_LABEL
        defaultSupplierTransactionStatusShouldBeFound("shortLabel.contains=" + DEFAULT_SHORT_LABEL);

        // Get all the supplierTransactionStatusList where shortLabel contains UPDATED_SHORT_LABEL
        defaultSupplierTransactionStatusShouldNotBeFound("shortLabel.contains=" + UPDATED_SHORT_LABEL);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByShortLabelNotContainsSomething() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where shortLabel does not contain DEFAULT_SHORT_LABEL
        defaultSupplierTransactionStatusShouldNotBeFound("shortLabel.doesNotContain=" + DEFAULT_SHORT_LABEL);

        // Get all the supplierTransactionStatusList where shortLabel does not contain UPDATED_SHORT_LABEL
        defaultSupplierTransactionStatusShouldBeFound("shortLabel.doesNotContain=" + UPDATED_SHORT_LABEL);
    }


    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultSupplierTransactionStatusShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the supplierTransactionStatusList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultSupplierTransactionStatusShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultSupplierTransactionStatusShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the supplierTransactionStatusList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultSupplierTransactionStatusShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultSupplierTransactionStatusShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the supplierTransactionStatusList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultSupplierTransactionStatusShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where lastEditedBy is not null
        defaultSupplierTransactionStatusShouldBeFound("lastEditedBy.specified=true");

        // Get all the supplierTransactionStatusList where lastEditedBy is null
        defaultSupplierTransactionStatusShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultSupplierTransactionStatusShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the supplierTransactionStatusList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultSupplierTransactionStatusShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultSupplierTransactionStatusShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the supplierTransactionStatusList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultSupplierTransactionStatusShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultSupplierTransactionStatusShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the supplierTransactionStatusList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultSupplierTransactionStatusShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultSupplierTransactionStatusShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the supplierTransactionStatusList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultSupplierTransactionStatusShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultSupplierTransactionStatusShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the supplierTransactionStatusList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultSupplierTransactionStatusShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionStatusesByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        // Get all the supplierTransactionStatusList where lastEditedWhen is not null
        defaultSupplierTransactionStatusShouldBeFound("lastEditedWhen.specified=true");

        // Get all the supplierTransactionStatusList where lastEditedWhen is null
        defaultSupplierTransactionStatusShouldNotBeFound("lastEditedWhen.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSupplierTransactionStatusShouldBeFound(String filter) throws Exception {
        restSupplierTransactionStatusMockMvc.perform(get("/api/supplier-transaction-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierTransactionStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].shortLabel").value(hasItem(DEFAULT_SHORT_LABEL)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restSupplierTransactionStatusMockMvc.perform(get("/api/supplier-transaction-statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSupplierTransactionStatusShouldNotBeFound(String filter) throws Exception {
        restSupplierTransactionStatusMockMvc.perform(get("/api/supplier-transaction-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplierTransactionStatusMockMvc.perform(get("/api/supplier-transaction-statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSupplierTransactionStatus() throws Exception {
        // Get the supplierTransactionStatus
        restSupplierTransactionStatusMockMvc.perform(get("/api/supplier-transaction-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplierTransactionStatus() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        int databaseSizeBeforeUpdate = supplierTransactionStatusRepository.findAll().size();

        // Update the supplierTransactionStatus
        SupplierTransactionStatus updatedSupplierTransactionStatus = supplierTransactionStatusRepository.findById(supplierTransactionStatus.getId()).get();
        // Disconnect from session so that the updates on updatedSupplierTransactionStatus are not directly saved in db
        em.detach(updatedSupplierTransactionStatus);
        updatedSupplierTransactionStatus
            .name(UPDATED_NAME)
            .label(UPDATED_LABEL)
            .shortLabel(UPDATED_SHORT_LABEL)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        SupplierTransactionStatusDTO supplierTransactionStatusDTO = supplierTransactionStatusMapper.toDto(updatedSupplierTransactionStatus);

        restSupplierTransactionStatusMockMvc.perform(put("/api/supplier-transaction-statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionStatusDTO)))
            .andExpect(status().isOk());

        // Validate the SupplierTransactionStatus in the database
        List<SupplierTransactionStatus> supplierTransactionStatusList = supplierTransactionStatusRepository.findAll();
        assertThat(supplierTransactionStatusList).hasSize(databaseSizeBeforeUpdate);
        SupplierTransactionStatus testSupplierTransactionStatus = supplierTransactionStatusList.get(supplierTransactionStatusList.size() - 1);
        assertThat(testSupplierTransactionStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSupplierTransactionStatus.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testSupplierTransactionStatus.getShortLabel()).isEqualTo(UPDATED_SHORT_LABEL);
        assertThat(testSupplierTransactionStatus.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testSupplierTransactionStatus.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplierTransactionStatus() throws Exception {
        int databaseSizeBeforeUpdate = supplierTransactionStatusRepository.findAll().size();

        // Create the SupplierTransactionStatus
        SupplierTransactionStatusDTO supplierTransactionStatusDTO = supplierTransactionStatusMapper.toDto(supplierTransactionStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierTransactionStatusMockMvc.perform(put("/api/supplier-transaction-statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplierTransactionStatus in the database
        List<SupplierTransactionStatus> supplierTransactionStatusList = supplierTransactionStatusRepository.findAll();
        assertThat(supplierTransactionStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSupplierTransactionStatus() throws Exception {
        // Initialize the database
        supplierTransactionStatusRepository.saveAndFlush(supplierTransactionStatus);

        int databaseSizeBeforeDelete = supplierTransactionStatusRepository.findAll().size();

        // Delete the supplierTransactionStatus
        restSupplierTransactionStatusMockMvc.perform(delete("/api/supplier-transaction-statuses/{id}", supplierTransactionStatus.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SupplierTransactionStatus> supplierTransactionStatusList = supplierTransactionStatusRepository.findAll();
        assertThat(supplierTransactionStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.Receipts;
import com.vertical.commerce.domain.Orders;
import com.vertical.commerce.domain.Invoices;
import com.vertical.commerce.repository.ReceiptsRepository;
import com.vertical.commerce.service.ReceiptsService;
import com.vertical.commerce.service.dto.ReceiptsDTO;
import com.vertical.commerce.service.mapper.ReceiptsMapper;
import com.vertical.commerce.service.dto.ReceiptsCriteria;
import com.vertical.commerce.service.ReceiptsQueryService;

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
 * Integration tests for the {@link ReceiptsResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ReceiptsResourceIT {

    private static final String DEFAULT_RECEIPT_NO = "AAAAAAAAAA";
    private static final String UPDATED_RECEIPT_NO = "BBBBBBBBBB";

    private static final Instant DEFAULT_ISSUED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ISSUED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_PRINT_COUNT = 1;
    private static final Integer UPDATED_PRINT_COUNT = 2;
    private static final Integer SMALLER_PRINT_COUNT = 1 - 1;

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ReceiptsRepository receiptsRepository;

    @Autowired
    private ReceiptsMapper receiptsMapper;

    @Autowired
    private ReceiptsService receiptsService;

    @Autowired
    private ReceiptsQueryService receiptsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReceiptsMockMvc;

    private Receipts receipts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Receipts createEntity(EntityManager em) {
        Receipts receipts = new Receipts()
            .receiptNo(DEFAULT_RECEIPT_NO)
            .issuedDate(DEFAULT_ISSUED_DATE)
            .printCount(DEFAULT_PRINT_COUNT)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return receipts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Receipts createUpdatedEntity(EntityManager em) {
        Receipts receipts = new Receipts()
            .receiptNo(UPDATED_RECEIPT_NO)
            .issuedDate(UPDATED_ISSUED_DATE)
            .printCount(UPDATED_PRINT_COUNT)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return receipts;
    }

    @BeforeEach
    public void initTest() {
        receipts = createEntity(em);
    }

    @Test
    @Transactional
    public void createReceipts() throws Exception {
        int databaseSizeBeforeCreate = receiptsRepository.findAll().size();
        // Create the Receipts
        ReceiptsDTO receiptsDTO = receiptsMapper.toDto(receipts);
        restReceiptsMockMvc.perform(post("/api/receipts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receiptsDTO)))
            .andExpect(status().isCreated());

        // Validate the Receipts in the database
        List<Receipts> receiptsList = receiptsRepository.findAll();
        assertThat(receiptsList).hasSize(databaseSizeBeforeCreate + 1);
        Receipts testReceipts = receiptsList.get(receiptsList.size() - 1);
        assertThat(testReceipts.getReceiptNo()).isEqualTo(DEFAULT_RECEIPT_NO);
        assertThat(testReceipts.getIssuedDate()).isEqualTo(DEFAULT_ISSUED_DATE);
        assertThat(testReceipts.getPrintCount()).isEqualTo(DEFAULT_PRINT_COUNT);
        assertThat(testReceipts.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testReceipts.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createReceiptsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = receiptsRepository.findAll().size();

        // Create the Receipts with an existing ID
        receipts.setId(1L);
        ReceiptsDTO receiptsDTO = receiptsMapper.toDto(receipts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReceiptsMockMvc.perform(post("/api/receipts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receiptsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Receipts in the database
        List<Receipts> receiptsList = receiptsRepository.findAll();
        assertThat(receiptsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkReceiptNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptsRepository.findAll().size();
        // set the field null
        receipts.setReceiptNo(null);

        // Create the Receipts, which fails.
        ReceiptsDTO receiptsDTO = receiptsMapper.toDto(receipts);


        restReceiptsMockMvc.perform(post("/api/receipts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receiptsDTO)))
            .andExpect(status().isBadRequest());

        List<Receipts> receiptsList = receiptsRepository.findAll();
        assertThat(receiptsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIssuedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptsRepository.findAll().size();
        // set the field null
        receipts.setIssuedDate(null);

        // Create the Receipts, which fails.
        ReceiptsDTO receiptsDTO = receiptsMapper.toDto(receipts);


        restReceiptsMockMvc.perform(post("/api/receipts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receiptsDTO)))
            .andExpect(status().isBadRequest());

        List<Receipts> receiptsList = receiptsRepository.findAll();
        assertThat(receiptsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrintCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptsRepository.findAll().size();
        // set the field null
        receipts.setPrintCount(null);

        // Create the Receipts, which fails.
        ReceiptsDTO receiptsDTO = receiptsMapper.toDto(receipts);


        restReceiptsMockMvc.perform(post("/api/receipts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receiptsDTO)))
            .andExpect(status().isBadRequest());

        List<Receipts> receiptsList = receiptsRepository.findAll();
        assertThat(receiptsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptsRepository.findAll().size();
        // set the field null
        receipts.setLastEditedBy(null);

        // Create the Receipts, which fails.
        ReceiptsDTO receiptsDTO = receiptsMapper.toDto(receipts);


        restReceiptsMockMvc.perform(post("/api/receipts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receiptsDTO)))
            .andExpect(status().isBadRequest());

        List<Receipts> receiptsList = receiptsRepository.findAll();
        assertThat(receiptsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptsRepository.findAll().size();
        // set the field null
        receipts.setLastEditedWhen(null);

        // Create the Receipts, which fails.
        ReceiptsDTO receiptsDTO = receiptsMapper.toDto(receipts);


        restReceiptsMockMvc.perform(post("/api/receipts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receiptsDTO)))
            .andExpect(status().isBadRequest());

        List<Receipts> receiptsList = receiptsRepository.findAll();
        assertThat(receiptsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReceipts() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList
        restReceiptsMockMvc.perform(get("/api/receipts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receipts.getId().intValue())))
            .andExpect(jsonPath("$.[*].receiptNo").value(hasItem(DEFAULT_RECEIPT_NO)))
            .andExpect(jsonPath("$.[*].issuedDate").value(hasItem(DEFAULT_ISSUED_DATE.toString())))
            .andExpect(jsonPath("$.[*].printCount").value(hasItem(DEFAULT_PRINT_COUNT)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getReceipts() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get the receipts
        restReceiptsMockMvc.perform(get("/api/receipts/{id}", receipts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(receipts.getId().intValue()))
            .andExpect(jsonPath("$.receiptNo").value(DEFAULT_RECEIPT_NO))
            .andExpect(jsonPath("$.issuedDate").value(DEFAULT_ISSUED_DATE.toString()))
            .andExpect(jsonPath("$.printCount").value(DEFAULT_PRINT_COUNT))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getReceiptsByIdFiltering() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        Long id = receipts.getId();

        defaultReceiptsShouldBeFound("id.equals=" + id);
        defaultReceiptsShouldNotBeFound("id.notEquals=" + id);

        defaultReceiptsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultReceiptsShouldNotBeFound("id.greaterThan=" + id);

        defaultReceiptsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultReceiptsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllReceiptsByReceiptNoIsEqualToSomething() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where receiptNo equals to DEFAULT_RECEIPT_NO
        defaultReceiptsShouldBeFound("receiptNo.equals=" + DEFAULT_RECEIPT_NO);

        // Get all the receiptsList where receiptNo equals to UPDATED_RECEIPT_NO
        defaultReceiptsShouldNotBeFound("receiptNo.equals=" + UPDATED_RECEIPT_NO);
    }

    @Test
    @Transactional
    public void getAllReceiptsByReceiptNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where receiptNo not equals to DEFAULT_RECEIPT_NO
        defaultReceiptsShouldNotBeFound("receiptNo.notEquals=" + DEFAULT_RECEIPT_NO);

        // Get all the receiptsList where receiptNo not equals to UPDATED_RECEIPT_NO
        defaultReceiptsShouldBeFound("receiptNo.notEquals=" + UPDATED_RECEIPT_NO);
    }

    @Test
    @Transactional
    public void getAllReceiptsByReceiptNoIsInShouldWork() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where receiptNo in DEFAULT_RECEIPT_NO or UPDATED_RECEIPT_NO
        defaultReceiptsShouldBeFound("receiptNo.in=" + DEFAULT_RECEIPT_NO + "," + UPDATED_RECEIPT_NO);

        // Get all the receiptsList where receiptNo equals to UPDATED_RECEIPT_NO
        defaultReceiptsShouldNotBeFound("receiptNo.in=" + UPDATED_RECEIPT_NO);
    }

    @Test
    @Transactional
    public void getAllReceiptsByReceiptNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where receiptNo is not null
        defaultReceiptsShouldBeFound("receiptNo.specified=true");

        // Get all the receiptsList where receiptNo is null
        defaultReceiptsShouldNotBeFound("receiptNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllReceiptsByReceiptNoContainsSomething() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where receiptNo contains DEFAULT_RECEIPT_NO
        defaultReceiptsShouldBeFound("receiptNo.contains=" + DEFAULT_RECEIPT_NO);

        // Get all the receiptsList where receiptNo contains UPDATED_RECEIPT_NO
        defaultReceiptsShouldNotBeFound("receiptNo.contains=" + UPDATED_RECEIPT_NO);
    }

    @Test
    @Transactional
    public void getAllReceiptsByReceiptNoNotContainsSomething() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where receiptNo does not contain DEFAULT_RECEIPT_NO
        defaultReceiptsShouldNotBeFound("receiptNo.doesNotContain=" + DEFAULT_RECEIPT_NO);

        // Get all the receiptsList where receiptNo does not contain UPDATED_RECEIPT_NO
        defaultReceiptsShouldBeFound("receiptNo.doesNotContain=" + UPDATED_RECEIPT_NO);
    }


    @Test
    @Transactional
    public void getAllReceiptsByIssuedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where issuedDate equals to DEFAULT_ISSUED_DATE
        defaultReceiptsShouldBeFound("issuedDate.equals=" + DEFAULT_ISSUED_DATE);

        // Get all the receiptsList where issuedDate equals to UPDATED_ISSUED_DATE
        defaultReceiptsShouldNotBeFound("issuedDate.equals=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllReceiptsByIssuedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where issuedDate not equals to DEFAULT_ISSUED_DATE
        defaultReceiptsShouldNotBeFound("issuedDate.notEquals=" + DEFAULT_ISSUED_DATE);

        // Get all the receiptsList where issuedDate not equals to UPDATED_ISSUED_DATE
        defaultReceiptsShouldBeFound("issuedDate.notEquals=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllReceiptsByIssuedDateIsInShouldWork() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where issuedDate in DEFAULT_ISSUED_DATE or UPDATED_ISSUED_DATE
        defaultReceiptsShouldBeFound("issuedDate.in=" + DEFAULT_ISSUED_DATE + "," + UPDATED_ISSUED_DATE);

        // Get all the receiptsList where issuedDate equals to UPDATED_ISSUED_DATE
        defaultReceiptsShouldNotBeFound("issuedDate.in=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllReceiptsByIssuedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where issuedDate is not null
        defaultReceiptsShouldBeFound("issuedDate.specified=true");

        // Get all the receiptsList where issuedDate is null
        defaultReceiptsShouldNotBeFound("issuedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllReceiptsByPrintCountIsEqualToSomething() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where printCount equals to DEFAULT_PRINT_COUNT
        defaultReceiptsShouldBeFound("printCount.equals=" + DEFAULT_PRINT_COUNT);

        // Get all the receiptsList where printCount equals to UPDATED_PRINT_COUNT
        defaultReceiptsShouldNotBeFound("printCount.equals=" + UPDATED_PRINT_COUNT);
    }

    @Test
    @Transactional
    public void getAllReceiptsByPrintCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where printCount not equals to DEFAULT_PRINT_COUNT
        defaultReceiptsShouldNotBeFound("printCount.notEquals=" + DEFAULT_PRINT_COUNT);

        // Get all the receiptsList where printCount not equals to UPDATED_PRINT_COUNT
        defaultReceiptsShouldBeFound("printCount.notEquals=" + UPDATED_PRINT_COUNT);
    }

    @Test
    @Transactional
    public void getAllReceiptsByPrintCountIsInShouldWork() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where printCount in DEFAULT_PRINT_COUNT or UPDATED_PRINT_COUNT
        defaultReceiptsShouldBeFound("printCount.in=" + DEFAULT_PRINT_COUNT + "," + UPDATED_PRINT_COUNT);

        // Get all the receiptsList where printCount equals to UPDATED_PRINT_COUNT
        defaultReceiptsShouldNotBeFound("printCount.in=" + UPDATED_PRINT_COUNT);
    }

    @Test
    @Transactional
    public void getAllReceiptsByPrintCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where printCount is not null
        defaultReceiptsShouldBeFound("printCount.specified=true");

        // Get all the receiptsList where printCount is null
        defaultReceiptsShouldNotBeFound("printCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllReceiptsByPrintCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where printCount is greater than or equal to DEFAULT_PRINT_COUNT
        defaultReceiptsShouldBeFound("printCount.greaterThanOrEqual=" + DEFAULT_PRINT_COUNT);

        // Get all the receiptsList where printCount is greater than or equal to UPDATED_PRINT_COUNT
        defaultReceiptsShouldNotBeFound("printCount.greaterThanOrEqual=" + UPDATED_PRINT_COUNT);
    }

    @Test
    @Transactional
    public void getAllReceiptsByPrintCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where printCount is less than or equal to DEFAULT_PRINT_COUNT
        defaultReceiptsShouldBeFound("printCount.lessThanOrEqual=" + DEFAULT_PRINT_COUNT);

        // Get all the receiptsList where printCount is less than or equal to SMALLER_PRINT_COUNT
        defaultReceiptsShouldNotBeFound("printCount.lessThanOrEqual=" + SMALLER_PRINT_COUNT);
    }

    @Test
    @Transactional
    public void getAllReceiptsByPrintCountIsLessThanSomething() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where printCount is less than DEFAULT_PRINT_COUNT
        defaultReceiptsShouldNotBeFound("printCount.lessThan=" + DEFAULT_PRINT_COUNT);

        // Get all the receiptsList where printCount is less than UPDATED_PRINT_COUNT
        defaultReceiptsShouldBeFound("printCount.lessThan=" + UPDATED_PRINT_COUNT);
    }

    @Test
    @Transactional
    public void getAllReceiptsByPrintCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where printCount is greater than DEFAULT_PRINT_COUNT
        defaultReceiptsShouldNotBeFound("printCount.greaterThan=" + DEFAULT_PRINT_COUNT);

        // Get all the receiptsList where printCount is greater than SMALLER_PRINT_COUNT
        defaultReceiptsShouldBeFound("printCount.greaterThan=" + SMALLER_PRINT_COUNT);
    }


    @Test
    @Transactional
    public void getAllReceiptsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultReceiptsShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the receiptsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultReceiptsShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllReceiptsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultReceiptsShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the receiptsList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultReceiptsShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllReceiptsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultReceiptsShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the receiptsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultReceiptsShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllReceiptsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where lastEditedBy is not null
        defaultReceiptsShouldBeFound("lastEditedBy.specified=true");

        // Get all the receiptsList where lastEditedBy is null
        defaultReceiptsShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllReceiptsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultReceiptsShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the receiptsList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultReceiptsShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllReceiptsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultReceiptsShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the receiptsList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultReceiptsShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllReceiptsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultReceiptsShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the receiptsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultReceiptsShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllReceiptsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultReceiptsShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the receiptsList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultReceiptsShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllReceiptsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultReceiptsShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the receiptsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultReceiptsShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllReceiptsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        // Get all the receiptsList where lastEditedWhen is not null
        defaultReceiptsShouldBeFound("lastEditedWhen.specified=true");

        // Get all the receiptsList where lastEditedWhen is null
        defaultReceiptsShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllReceiptsByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);
        Orders order = OrdersResourceIT.createEntity(em);
        em.persist(order);
        em.flush();
        receipts.setOrder(order);
        receiptsRepository.saveAndFlush(receipts);
        Long orderId = order.getId();

        // Get all the receiptsList where order equals to orderId
        defaultReceiptsShouldBeFound("orderId.equals=" + orderId);

        // Get all the receiptsList where order equals to orderId + 1
        defaultReceiptsShouldNotBeFound("orderId.equals=" + (orderId + 1));
    }


    @Test
    @Transactional
    public void getAllReceiptsByInvoiceIsEqualToSomething() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);
        Invoices invoice = InvoicesResourceIT.createEntity(em);
        em.persist(invoice);
        em.flush();
        receipts.setInvoice(invoice);
        receiptsRepository.saveAndFlush(receipts);
        Long invoiceId = invoice.getId();

        // Get all the receiptsList where invoice equals to invoiceId
        defaultReceiptsShouldBeFound("invoiceId.equals=" + invoiceId);

        // Get all the receiptsList where invoice equals to invoiceId + 1
        defaultReceiptsShouldNotBeFound("invoiceId.equals=" + (invoiceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReceiptsShouldBeFound(String filter) throws Exception {
        restReceiptsMockMvc.perform(get("/api/receipts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receipts.getId().intValue())))
            .andExpect(jsonPath("$.[*].receiptNo").value(hasItem(DEFAULT_RECEIPT_NO)))
            .andExpect(jsonPath("$.[*].issuedDate").value(hasItem(DEFAULT_ISSUED_DATE.toString())))
            .andExpect(jsonPath("$.[*].printCount").value(hasItem(DEFAULT_PRINT_COUNT)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restReceiptsMockMvc.perform(get("/api/receipts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReceiptsShouldNotBeFound(String filter) throws Exception {
        restReceiptsMockMvc.perform(get("/api/receipts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReceiptsMockMvc.perform(get("/api/receipts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingReceipts() throws Exception {
        // Get the receipts
        restReceiptsMockMvc.perform(get("/api/receipts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReceipts() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        int databaseSizeBeforeUpdate = receiptsRepository.findAll().size();

        // Update the receipts
        Receipts updatedReceipts = receiptsRepository.findById(receipts.getId()).get();
        // Disconnect from session so that the updates on updatedReceipts are not directly saved in db
        em.detach(updatedReceipts);
        updatedReceipts
            .receiptNo(UPDATED_RECEIPT_NO)
            .issuedDate(UPDATED_ISSUED_DATE)
            .printCount(UPDATED_PRINT_COUNT)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        ReceiptsDTO receiptsDTO = receiptsMapper.toDto(updatedReceipts);

        restReceiptsMockMvc.perform(put("/api/receipts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receiptsDTO)))
            .andExpect(status().isOk());

        // Validate the Receipts in the database
        List<Receipts> receiptsList = receiptsRepository.findAll();
        assertThat(receiptsList).hasSize(databaseSizeBeforeUpdate);
        Receipts testReceipts = receiptsList.get(receiptsList.size() - 1);
        assertThat(testReceipts.getReceiptNo()).isEqualTo(UPDATED_RECEIPT_NO);
        assertThat(testReceipts.getIssuedDate()).isEqualTo(UPDATED_ISSUED_DATE);
        assertThat(testReceipts.getPrintCount()).isEqualTo(UPDATED_PRINT_COUNT);
        assertThat(testReceipts.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testReceipts.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingReceipts() throws Exception {
        int databaseSizeBeforeUpdate = receiptsRepository.findAll().size();

        // Create the Receipts
        ReceiptsDTO receiptsDTO = receiptsMapper.toDto(receipts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceiptsMockMvc.perform(put("/api/receipts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receiptsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Receipts in the database
        List<Receipts> receiptsList = receiptsRepository.findAll();
        assertThat(receiptsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReceipts() throws Exception {
        // Initialize the database
        receiptsRepository.saveAndFlush(receipts);

        int databaseSizeBeforeDelete = receiptsRepository.findAll().size();

        // Delete the receipts
        restReceiptsMockMvc.perform(delete("/api/receipts/{id}", receipts.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Receipts> receiptsList = receiptsRepository.findAll();
        assertThat(receiptsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

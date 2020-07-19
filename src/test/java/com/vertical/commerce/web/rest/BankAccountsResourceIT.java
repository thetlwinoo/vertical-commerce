package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.BankAccounts;
import com.vertical.commerce.repository.BankAccountsRepository;
import com.vertical.commerce.service.BankAccountsService;
import com.vertical.commerce.service.dto.BankAccountsDTO;
import com.vertical.commerce.service.mapper.BankAccountsMapper;
import com.vertical.commerce.service.dto.BankAccountsCriteria;
import com.vertical.commerce.service.BankAccountsQueryService;

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
 * Integration tests for the {@link BankAccountsResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class BankAccountsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_BANK = "AAAAAAAAAA";
    private static final String UPDATED_BANK = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNATIONAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_INTERNATIONAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_PHOTO = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FORM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FORM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private BankAccountsRepository bankAccountsRepository;

    @Autowired
    private BankAccountsMapper bankAccountsMapper;

    @Autowired
    private BankAccountsService bankAccountsService;

    @Autowired
    private BankAccountsQueryService bankAccountsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankAccountsMockMvc;

    private BankAccounts bankAccounts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankAccounts createEntity(EntityManager em) {
        BankAccounts bankAccounts = new BankAccounts()
            .name(DEFAULT_NAME)
            .branch(DEFAULT_BRANCH)
            .code(DEFAULT_CODE)
            .number(DEFAULT_NUMBER)
            .type(DEFAULT_TYPE)
            .bank(DEFAULT_BANK)
            .internationalCode(DEFAULT_INTERNATIONAL_CODE)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .logoPhoto(DEFAULT_LOGO_PHOTO)
            .validForm(DEFAULT_VALID_FORM)
            .validTo(DEFAULT_VALID_TO);
        return bankAccounts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankAccounts createUpdatedEntity(EntityManager em) {
        BankAccounts bankAccounts = new BankAccounts()
            .name(UPDATED_NAME)
            .branch(UPDATED_BRANCH)
            .code(UPDATED_CODE)
            .number(UPDATED_NUMBER)
            .type(UPDATED_TYPE)
            .bank(UPDATED_BANK)
            .internationalCode(UPDATED_INTERNATIONAL_CODE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .logoPhoto(UPDATED_LOGO_PHOTO)
            .validForm(UPDATED_VALID_FORM)
            .validTo(UPDATED_VALID_TO);
        return bankAccounts;
    }

    @BeforeEach
    public void initTest() {
        bankAccounts = createEntity(em);
    }

    @Test
    @Transactional
    public void createBankAccounts() throws Exception {
        int databaseSizeBeforeCreate = bankAccountsRepository.findAll().size();
        // Create the BankAccounts
        BankAccountsDTO bankAccountsDTO = bankAccountsMapper.toDto(bankAccounts);
        restBankAccountsMockMvc.perform(post("/api/bank-accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bankAccountsDTO)))
            .andExpect(status().isCreated());

        // Validate the BankAccounts in the database
        List<BankAccounts> bankAccountsList = bankAccountsRepository.findAll();
        assertThat(bankAccountsList).hasSize(databaseSizeBeforeCreate + 1);
        BankAccounts testBankAccounts = bankAccountsList.get(bankAccountsList.size() - 1);
        assertThat(testBankAccounts.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBankAccounts.getBranch()).isEqualTo(DEFAULT_BRANCH);
        assertThat(testBankAccounts.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testBankAccounts.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testBankAccounts.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBankAccounts.getBank()).isEqualTo(DEFAULT_BANK);
        assertThat(testBankAccounts.getInternationalCode()).isEqualTo(DEFAULT_INTERNATIONAL_CODE);
        assertThat(testBankAccounts.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testBankAccounts.getLogoPhoto()).isEqualTo(DEFAULT_LOGO_PHOTO);
        assertThat(testBankAccounts.getValidForm()).isEqualTo(DEFAULT_VALID_FORM);
        assertThat(testBankAccounts.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createBankAccountsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bankAccountsRepository.findAll().size();

        // Create the BankAccounts with an existing ID
        bankAccounts.setId(1L);
        BankAccountsDTO bankAccountsDTO = bankAccountsMapper.toDto(bankAccounts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankAccountsMockMvc.perform(post("/api/bank-accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bankAccountsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BankAccounts in the database
        List<BankAccounts> bankAccountsList = bankAccountsRepository.findAll();
        assertThat(bankAccountsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLastEditedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankAccountsRepository.findAll().size();
        // set the field null
        bankAccounts.setLastEditedBy(null);

        // Create the BankAccounts, which fails.
        BankAccountsDTO bankAccountsDTO = bankAccountsMapper.toDto(bankAccounts);


        restBankAccountsMockMvc.perform(post("/api/bank-accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bankAccountsDTO)))
            .andExpect(status().isBadRequest());

        List<BankAccounts> bankAccountsList = bankAccountsRepository.findAll();
        assertThat(bankAccountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFormIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankAccountsRepository.findAll().size();
        // set the field null
        bankAccounts.setValidForm(null);

        // Create the BankAccounts, which fails.
        BankAccountsDTO bankAccountsDTO = bankAccountsMapper.toDto(bankAccounts);


        restBankAccountsMockMvc.perform(post("/api/bank-accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bankAccountsDTO)))
            .andExpect(status().isBadRequest());

        List<BankAccounts> bankAccountsList = bankAccountsRepository.findAll();
        assertThat(bankAccountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBankAccounts() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList
        restBankAccountsMockMvc.perform(get("/api/bank-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankAccounts.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].bank").value(hasItem(DEFAULT_BANK)))
            .andExpect(jsonPath("$.[*].internationalCode").value(hasItem(DEFAULT_INTERNATIONAL_CODE)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].logoPhoto").value(hasItem(DEFAULT_LOGO_PHOTO)))
            .andExpect(jsonPath("$.[*].validForm").value(hasItem(DEFAULT_VALID_FORM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getBankAccounts() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get the bankAccounts
        restBankAccountsMockMvc.perform(get("/api/bank-accounts/{id}", bankAccounts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankAccounts.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.branch").value(DEFAULT_BRANCH))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.bank").value(DEFAULT_BANK))
            .andExpect(jsonPath("$.internationalCode").value(DEFAULT_INTERNATIONAL_CODE))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.logoPhoto").value(DEFAULT_LOGO_PHOTO))
            .andExpect(jsonPath("$.validForm").value(DEFAULT_VALID_FORM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getBankAccountsByIdFiltering() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        Long id = bankAccounts.getId();

        defaultBankAccountsShouldBeFound("id.equals=" + id);
        defaultBankAccountsShouldNotBeFound("id.notEquals=" + id);

        defaultBankAccountsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBankAccountsShouldNotBeFound("id.greaterThan=" + id);

        defaultBankAccountsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBankAccountsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBankAccountsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where name equals to DEFAULT_NAME
        defaultBankAccountsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the bankAccountsList where name equals to UPDATED_NAME
        defaultBankAccountsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where name not equals to DEFAULT_NAME
        defaultBankAccountsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the bankAccountsList where name not equals to UPDATED_NAME
        defaultBankAccountsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBankAccountsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the bankAccountsList where name equals to UPDATED_NAME
        defaultBankAccountsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where name is not null
        defaultBankAccountsShouldBeFound("name.specified=true");

        // Get all the bankAccountsList where name is null
        defaultBankAccountsShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllBankAccountsByNameContainsSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where name contains DEFAULT_NAME
        defaultBankAccountsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the bankAccountsList where name contains UPDATED_NAME
        defaultBankAccountsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where name does not contain DEFAULT_NAME
        defaultBankAccountsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the bankAccountsList where name does not contain UPDATED_NAME
        defaultBankAccountsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllBankAccountsByBranchIsEqualToSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where branch equals to DEFAULT_BRANCH
        defaultBankAccountsShouldBeFound("branch.equals=" + DEFAULT_BRANCH);

        // Get all the bankAccountsList where branch equals to UPDATED_BRANCH
        defaultBankAccountsShouldNotBeFound("branch.equals=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByBranchIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where branch not equals to DEFAULT_BRANCH
        defaultBankAccountsShouldNotBeFound("branch.notEquals=" + DEFAULT_BRANCH);

        // Get all the bankAccountsList where branch not equals to UPDATED_BRANCH
        defaultBankAccountsShouldBeFound("branch.notEquals=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByBranchIsInShouldWork() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where branch in DEFAULT_BRANCH or UPDATED_BRANCH
        defaultBankAccountsShouldBeFound("branch.in=" + DEFAULT_BRANCH + "," + UPDATED_BRANCH);

        // Get all the bankAccountsList where branch equals to UPDATED_BRANCH
        defaultBankAccountsShouldNotBeFound("branch.in=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByBranchIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where branch is not null
        defaultBankAccountsShouldBeFound("branch.specified=true");

        // Get all the bankAccountsList where branch is null
        defaultBankAccountsShouldNotBeFound("branch.specified=false");
    }
                @Test
    @Transactional
    public void getAllBankAccountsByBranchContainsSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where branch contains DEFAULT_BRANCH
        defaultBankAccountsShouldBeFound("branch.contains=" + DEFAULT_BRANCH);

        // Get all the bankAccountsList where branch contains UPDATED_BRANCH
        defaultBankAccountsShouldNotBeFound("branch.contains=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByBranchNotContainsSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where branch does not contain DEFAULT_BRANCH
        defaultBankAccountsShouldNotBeFound("branch.doesNotContain=" + DEFAULT_BRANCH);

        // Get all the bankAccountsList where branch does not contain UPDATED_BRANCH
        defaultBankAccountsShouldBeFound("branch.doesNotContain=" + UPDATED_BRANCH);
    }


    @Test
    @Transactional
    public void getAllBankAccountsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where code equals to DEFAULT_CODE
        defaultBankAccountsShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the bankAccountsList where code equals to UPDATED_CODE
        defaultBankAccountsShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where code not equals to DEFAULT_CODE
        defaultBankAccountsShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the bankAccountsList where code not equals to UPDATED_CODE
        defaultBankAccountsShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where code in DEFAULT_CODE or UPDATED_CODE
        defaultBankAccountsShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the bankAccountsList where code equals to UPDATED_CODE
        defaultBankAccountsShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where code is not null
        defaultBankAccountsShouldBeFound("code.specified=true");

        // Get all the bankAccountsList where code is null
        defaultBankAccountsShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllBankAccountsByCodeContainsSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where code contains DEFAULT_CODE
        defaultBankAccountsShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the bankAccountsList where code contains UPDATED_CODE
        defaultBankAccountsShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where code does not contain DEFAULT_CODE
        defaultBankAccountsShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the bankAccountsList where code does not contain UPDATED_CODE
        defaultBankAccountsShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllBankAccountsByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where number equals to DEFAULT_NUMBER
        defaultBankAccountsShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the bankAccountsList where number equals to UPDATED_NUMBER
        defaultBankAccountsShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where number not equals to DEFAULT_NUMBER
        defaultBankAccountsShouldNotBeFound("number.notEquals=" + DEFAULT_NUMBER);

        // Get all the bankAccountsList where number not equals to UPDATED_NUMBER
        defaultBankAccountsShouldBeFound("number.notEquals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultBankAccountsShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the bankAccountsList where number equals to UPDATED_NUMBER
        defaultBankAccountsShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where number is not null
        defaultBankAccountsShouldBeFound("number.specified=true");

        // Get all the bankAccountsList where number is null
        defaultBankAccountsShouldNotBeFound("number.specified=false");
    }
                @Test
    @Transactional
    public void getAllBankAccountsByNumberContainsSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where number contains DEFAULT_NUMBER
        defaultBankAccountsShouldBeFound("number.contains=" + DEFAULT_NUMBER);

        // Get all the bankAccountsList where number contains UPDATED_NUMBER
        defaultBankAccountsShouldNotBeFound("number.contains=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByNumberNotContainsSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where number does not contain DEFAULT_NUMBER
        defaultBankAccountsShouldNotBeFound("number.doesNotContain=" + DEFAULT_NUMBER);

        // Get all the bankAccountsList where number does not contain UPDATED_NUMBER
        defaultBankAccountsShouldBeFound("number.doesNotContain=" + UPDATED_NUMBER);
    }


    @Test
    @Transactional
    public void getAllBankAccountsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where type equals to DEFAULT_TYPE
        defaultBankAccountsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the bankAccountsList where type equals to UPDATED_TYPE
        defaultBankAccountsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where type not equals to DEFAULT_TYPE
        defaultBankAccountsShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the bankAccountsList where type not equals to UPDATED_TYPE
        defaultBankAccountsShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultBankAccountsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the bankAccountsList where type equals to UPDATED_TYPE
        defaultBankAccountsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where type is not null
        defaultBankAccountsShouldBeFound("type.specified=true");

        // Get all the bankAccountsList where type is null
        defaultBankAccountsShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllBankAccountsByTypeContainsSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where type contains DEFAULT_TYPE
        defaultBankAccountsShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the bankAccountsList where type contains UPDATED_TYPE
        defaultBankAccountsShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where type does not contain DEFAULT_TYPE
        defaultBankAccountsShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the bankAccountsList where type does not contain UPDATED_TYPE
        defaultBankAccountsShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllBankAccountsByBankIsEqualToSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where bank equals to DEFAULT_BANK
        defaultBankAccountsShouldBeFound("bank.equals=" + DEFAULT_BANK);

        // Get all the bankAccountsList where bank equals to UPDATED_BANK
        defaultBankAccountsShouldNotBeFound("bank.equals=" + UPDATED_BANK);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByBankIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where bank not equals to DEFAULT_BANK
        defaultBankAccountsShouldNotBeFound("bank.notEquals=" + DEFAULT_BANK);

        // Get all the bankAccountsList where bank not equals to UPDATED_BANK
        defaultBankAccountsShouldBeFound("bank.notEquals=" + UPDATED_BANK);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByBankIsInShouldWork() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where bank in DEFAULT_BANK or UPDATED_BANK
        defaultBankAccountsShouldBeFound("bank.in=" + DEFAULT_BANK + "," + UPDATED_BANK);

        // Get all the bankAccountsList where bank equals to UPDATED_BANK
        defaultBankAccountsShouldNotBeFound("bank.in=" + UPDATED_BANK);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByBankIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where bank is not null
        defaultBankAccountsShouldBeFound("bank.specified=true");

        // Get all the bankAccountsList where bank is null
        defaultBankAccountsShouldNotBeFound("bank.specified=false");
    }
                @Test
    @Transactional
    public void getAllBankAccountsByBankContainsSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where bank contains DEFAULT_BANK
        defaultBankAccountsShouldBeFound("bank.contains=" + DEFAULT_BANK);

        // Get all the bankAccountsList where bank contains UPDATED_BANK
        defaultBankAccountsShouldNotBeFound("bank.contains=" + UPDATED_BANK);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByBankNotContainsSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where bank does not contain DEFAULT_BANK
        defaultBankAccountsShouldNotBeFound("bank.doesNotContain=" + DEFAULT_BANK);

        // Get all the bankAccountsList where bank does not contain UPDATED_BANK
        defaultBankAccountsShouldBeFound("bank.doesNotContain=" + UPDATED_BANK);
    }


    @Test
    @Transactional
    public void getAllBankAccountsByInternationalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where internationalCode equals to DEFAULT_INTERNATIONAL_CODE
        defaultBankAccountsShouldBeFound("internationalCode.equals=" + DEFAULT_INTERNATIONAL_CODE);

        // Get all the bankAccountsList where internationalCode equals to UPDATED_INTERNATIONAL_CODE
        defaultBankAccountsShouldNotBeFound("internationalCode.equals=" + UPDATED_INTERNATIONAL_CODE);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByInternationalCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where internationalCode not equals to DEFAULT_INTERNATIONAL_CODE
        defaultBankAccountsShouldNotBeFound("internationalCode.notEquals=" + DEFAULT_INTERNATIONAL_CODE);

        // Get all the bankAccountsList where internationalCode not equals to UPDATED_INTERNATIONAL_CODE
        defaultBankAccountsShouldBeFound("internationalCode.notEquals=" + UPDATED_INTERNATIONAL_CODE);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByInternationalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where internationalCode in DEFAULT_INTERNATIONAL_CODE or UPDATED_INTERNATIONAL_CODE
        defaultBankAccountsShouldBeFound("internationalCode.in=" + DEFAULT_INTERNATIONAL_CODE + "," + UPDATED_INTERNATIONAL_CODE);

        // Get all the bankAccountsList where internationalCode equals to UPDATED_INTERNATIONAL_CODE
        defaultBankAccountsShouldNotBeFound("internationalCode.in=" + UPDATED_INTERNATIONAL_CODE);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByInternationalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where internationalCode is not null
        defaultBankAccountsShouldBeFound("internationalCode.specified=true");

        // Get all the bankAccountsList where internationalCode is null
        defaultBankAccountsShouldNotBeFound("internationalCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllBankAccountsByInternationalCodeContainsSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where internationalCode contains DEFAULT_INTERNATIONAL_CODE
        defaultBankAccountsShouldBeFound("internationalCode.contains=" + DEFAULT_INTERNATIONAL_CODE);

        // Get all the bankAccountsList where internationalCode contains UPDATED_INTERNATIONAL_CODE
        defaultBankAccountsShouldNotBeFound("internationalCode.contains=" + UPDATED_INTERNATIONAL_CODE);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByInternationalCodeNotContainsSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where internationalCode does not contain DEFAULT_INTERNATIONAL_CODE
        defaultBankAccountsShouldNotBeFound("internationalCode.doesNotContain=" + DEFAULT_INTERNATIONAL_CODE);

        // Get all the bankAccountsList where internationalCode does not contain UPDATED_INTERNATIONAL_CODE
        defaultBankAccountsShouldBeFound("internationalCode.doesNotContain=" + UPDATED_INTERNATIONAL_CODE);
    }


    @Test
    @Transactional
    public void getAllBankAccountsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultBankAccountsShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the bankAccountsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultBankAccountsShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultBankAccountsShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the bankAccountsList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultBankAccountsShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultBankAccountsShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the bankAccountsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultBankAccountsShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where lastEditedBy is not null
        defaultBankAccountsShouldBeFound("lastEditedBy.specified=true");

        // Get all the bankAccountsList where lastEditedBy is null
        defaultBankAccountsShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllBankAccountsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultBankAccountsShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the bankAccountsList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultBankAccountsShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultBankAccountsShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the bankAccountsList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultBankAccountsShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllBankAccountsByLogoPhotoIsEqualToSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where logoPhoto equals to DEFAULT_LOGO_PHOTO
        defaultBankAccountsShouldBeFound("logoPhoto.equals=" + DEFAULT_LOGO_PHOTO);

        // Get all the bankAccountsList where logoPhoto equals to UPDATED_LOGO_PHOTO
        defaultBankAccountsShouldNotBeFound("logoPhoto.equals=" + UPDATED_LOGO_PHOTO);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByLogoPhotoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where logoPhoto not equals to DEFAULT_LOGO_PHOTO
        defaultBankAccountsShouldNotBeFound("logoPhoto.notEquals=" + DEFAULT_LOGO_PHOTO);

        // Get all the bankAccountsList where logoPhoto not equals to UPDATED_LOGO_PHOTO
        defaultBankAccountsShouldBeFound("logoPhoto.notEquals=" + UPDATED_LOGO_PHOTO);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByLogoPhotoIsInShouldWork() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where logoPhoto in DEFAULT_LOGO_PHOTO or UPDATED_LOGO_PHOTO
        defaultBankAccountsShouldBeFound("logoPhoto.in=" + DEFAULT_LOGO_PHOTO + "," + UPDATED_LOGO_PHOTO);

        // Get all the bankAccountsList where logoPhoto equals to UPDATED_LOGO_PHOTO
        defaultBankAccountsShouldNotBeFound("logoPhoto.in=" + UPDATED_LOGO_PHOTO);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByLogoPhotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where logoPhoto is not null
        defaultBankAccountsShouldBeFound("logoPhoto.specified=true");

        // Get all the bankAccountsList where logoPhoto is null
        defaultBankAccountsShouldNotBeFound("logoPhoto.specified=false");
    }
                @Test
    @Transactional
    public void getAllBankAccountsByLogoPhotoContainsSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where logoPhoto contains DEFAULT_LOGO_PHOTO
        defaultBankAccountsShouldBeFound("logoPhoto.contains=" + DEFAULT_LOGO_PHOTO);

        // Get all the bankAccountsList where logoPhoto contains UPDATED_LOGO_PHOTO
        defaultBankAccountsShouldNotBeFound("logoPhoto.contains=" + UPDATED_LOGO_PHOTO);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByLogoPhotoNotContainsSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where logoPhoto does not contain DEFAULT_LOGO_PHOTO
        defaultBankAccountsShouldNotBeFound("logoPhoto.doesNotContain=" + DEFAULT_LOGO_PHOTO);

        // Get all the bankAccountsList where logoPhoto does not contain UPDATED_LOGO_PHOTO
        defaultBankAccountsShouldBeFound("logoPhoto.doesNotContain=" + UPDATED_LOGO_PHOTO);
    }


    @Test
    @Transactional
    public void getAllBankAccountsByValidFormIsEqualToSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where validForm equals to DEFAULT_VALID_FORM
        defaultBankAccountsShouldBeFound("validForm.equals=" + DEFAULT_VALID_FORM);

        // Get all the bankAccountsList where validForm equals to UPDATED_VALID_FORM
        defaultBankAccountsShouldNotBeFound("validForm.equals=" + UPDATED_VALID_FORM);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByValidFormIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where validForm not equals to DEFAULT_VALID_FORM
        defaultBankAccountsShouldNotBeFound("validForm.notEquals=" + DEFAULT_VALID_FORM);

        // Get all the bankAccountsList where validForm not equals to UPDATED_VALID_FORM
        defaultBankAccountsShouldBeFound("validForm.notEquals=" + UPDATED_VALID_FORM);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByValidFormIsInShouldWork() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where validForm in DEFAULT_VALID_FORM or UPDATED_VALID_FORM
        defaultBankAccountsShouldBeFound("validForm.in=" + DEFAULT_VALID_FORM + "," + UPDATED_VALID_FORM);

        // Get all the bankAccountsList where validForm equals to UPDATED_VALID_FORM
        defaultBankAccountsShouldNotBeFound("validForm.in=" + UPDATED_VALID_FORM);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByValidFormIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where validForm is not null
        defaultBankAccountsShouldBeFound("validForm.specified=true");

        // Get all the bankAccountsList where validForm is null
        defaultBankAccountsShouldNotBeFound("validForm.specified=false");
    }

    @Test
    @Transactional
    public void getAllBankAccountsByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where validTo equals to DEFAULT_VALID_TO
        defaultBankAccountsShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the bankAccountsList where validTo equals to UPDATED_VALID_TO
        defaultBankAccountsShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where validTo not equals to DEFAULT_VALID_TO
        defaultBankAccountsShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the bankAccountsList where validTo not equals to UPDATED_VALID_TO
        defaultBankAccountsShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultBankAccountsShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the bankAccountsList where validTo equals to UPDATED_VALID_TO
        defaultBankAccountsShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllBankAccountsByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        // Get all the bankAccountsList where validTo is not null
        defaultBankAccountsShouldBeFound("validTo.specified=true");

        // Get all the bankAccountsList where validTo is null
        defaultBankAccountsShouldNotBeFound("validTo.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBankAccountsShouldBeFound(String filter) throws Exception {
        restBankAccountsMockMvc.perform(get("/api/bank-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankAccounts.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].bank").value(hasItem(DEFAULT_BANK)))
            .andExpect(jsonPath("$.[*].internationalCode").value(hasItem(DEFAULT_INTERNATIONAL_CODE)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].logoPhoto").value(hasItem(DEFAULT_LOGO_PHOTO)))
            .andExpect(jsonPath("$.[*].validForm").value(hasItem(DEFAULT_VALID_FORM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restBankAccountsMockMvc.perform(get("/api/bank-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBankAccountsShouldNotBeFound(String filter) throws Exception {
        restBankAccountsMockMvc.perform(get("/api/bank-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBankAccountsMockMvc.perform(get("/api/bank-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingBankAccounts() throws Exception {
        // Get the bankAccounts
        restBankAccountsMockMvc.perform(get("/api/bank-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBankAccounts() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        int databaseSizeBeforeUpdate = bankAccountsRepository.findAll().size();

        // Update the bankAccounts
        BankAccounts updatedBankAccounts = bankAccountsRepository.findById(bankAccounts.getId()).get();
        // Disconnect from session so that the updates on updatedBankAccounts are not directly saved in db
        em.detach(updatedBankAccounts);
        updatedBankAccounts
            .name(UPDATED_NAME)
            .branch(UPDATED_BRANCH)
            .code(UPDATED_CODE)
            .number(UPDATED_NUMBER)
            .type(UPDATED_TYPE)
            .bank(UPDATED_BANK)
            .internationalCode(UPDATED_INTERNATIONAL_CODE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .logoPhoto(UPDATED_LOGO_PHOTO)
            .validForm(UPDATED_VALID_FORM)
            .validTo(UPDATED_VALID_TO);
        BankAccountsDTO bankAccountsDTO = bankAccountsMapper.toDto(updatedBankAccounts);

        restBankAccountsMockMvc.perform(put("/api/bank-accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bankAccountsDTO)))
            .andExpect(status().isOk());

        // Validate the BankAccounts in the database
        List<BankAccounts> bankAccountsList = bankAccountsRepository.findAll();
        assertThat(bankAccountsList).hasSize(databaseSizeBeforeUpdate);
        BankAccounts testBankAccounts = bankAccountsList.get(bankAccountsList.size() - 1);
        assertThat(testBankAccounts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBankAccounts.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testBankAccounts.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBankAccounts.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testBankAccounts.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBankAccounts.getBank()).isEqualTo(UPDATED_BANK);
        assertThat(testBankAccounts.getInternationalCode()).isEqualTo(UPDATED_INTERNATIONAL_CODE);
        assertThat(testBankAccounts.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testBankAccounts.getLogoPhoto()).isEqualTo(UPDATED_LOGO_PHOTO);
        assertThat(testBankAccounts.getValidForm()).isEqualTo(UPDATED_VALID_FORM);
        assertThat(testBankAccounts.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingBankAccounts() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountsRepository.findAll().size();

        // Create the BankAccounts
        BankAccountsDTO bankAccountsDTO = bankAccountsMapper.toDto(bankAccounts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankAccountsMockMvc.perform(put("/api/bank-accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bankAccountsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BankAccounts in the database
        List<BankAccounts> bankAccountsList = bankAccountsRepository.findAll();
        assertThat(bankAccountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBankAccounts() throws Exception {
        // Initialize the database
        bankAccountsRepository.saveAndFlush(bankAccounts);

        int databaseSizeBeforeDelete = bankAccountsRepository.findAll().size();

        // Delete the bankAccounts
        restBankAccountsMockMvc.perform(delete("/api/bank-accounts/{id}", bankAccounts.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankAccounts> bankAccountsList = bankAccountsRepository.findAll();
        assertThat(bankAccountsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

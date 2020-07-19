package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.Suppliers;
import com.vertical.commerce.domain.Photos;
import com.vertical.commerce.domain.SupplierCategories;
import com.vertical.commerce.domain.Addresses;
import com.vertical.commerce.domain.People;
import com.vertical.commerce.domain.DeliveryMethods;
import com.vertical.commerce.repository.SuppliersRepository;
import com.vertical.commerce.service.SuppliersService;
import com.vertical.commerce.service.dto.SuppliersDTO;
import com.vertical.commerce.service.mapper.SuppliersMapper;
import com.vertical.commerce.service.dto.SuppliersCriteria;
import com.vertical.commerce.service.SuppliersQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SuppliersResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SuppliersResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_ACCOUNT_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ACCOUNT_BRANCH = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_ACCOUNT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ACCOUNT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_INTERNATIONAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BANK_INTERNATIONAL_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAYMENT_DAYS = 1;
    private static final Integer UPDATED_PAYMENT_DAYS = 2;
    private static final Integer SMALLER_PAYMENT_DAYS = 1 - 1;

    private static final String DEFAULT_INTERNAL_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_NRIC = "AAAAAAAAAA";
    private static final String UPDATED_NRIC = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_REGISTRATION_NO = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_REGISTRATION_NO = "BBBBBBBBBB";

    private static final String DEFAULT_FAX_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FAX_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE_URL = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_SERVICE_URL = "AAAAAAAAAA";
    private static final String UPDATED_WEB_SERVICE_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREDIT_RATING = 1;
    private static final Integer UPDATED_CREDIT_RATING = 2;
    private static final Integer SMALLER_CREDIT_RATING = 1 - 1;

    private static final Boolean DEFAULT_OFFICIAL_STORE_IND = false;
    private static final Boolean UPDATED_OFFICIAL_STORE_IND = true;

    private static final String DEFAULT_STORE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STORE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_LOGO = "BBBBBBBBBB";

    private static final String DEFAULT_NRIC_FRONT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_NRIC_FRONT_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_NRIC_BACK_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_NRIC_BACK_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_BOOK_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_BANK_BOOK_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_REGISTRATION_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_REGISTRATION_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRIBUTOR_CERTIFICATE_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_DISTRIBUTOR_CERTIFICATE_PHOTO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE_FLAG = false;
    private static final Boolean UPDATED_ACTIVE_FLAG = true;

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SuppliersRepository suppliersRepository;

    @Mock
    private SuppliersRepository suppliersRepositoryMock;

    @Autowired
    private SuppliersMapper suppliersMapper;

    @Mock
    private SuppliersService suppliersServiceMock;

    @Autowired
    private SuppliersService suppliersService;

    @Autowired
    private SuppliersQueryService suppliersQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSuppliersMockMvc;

    private Suppliers suppliers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Suppliers createEntity(EntityManager em) {
        Suppliers suppliers = new Suppliers()
            .name(DEFAULT_NAME)
            .supplierReference(DEFAULT_SUPPLIER_REFERENCE)
            .bankAccountName(DEFAULT_BANK_ACCOUNT_NAME)
            .bankAccountBranch(DEFAULT_BANK_ACCOUNT_BRANCH)
            .bankAccountCode(DEFAULT_BANK_ACCOUNT_CODE)
            .bankAccountNumber(DEFAULT_BANK_ACCOUNT_NUMBER)
            .bankInternationalCode(DEFAULT_BANK_INTERNATIONAL_CODE)
            .paymentDays(DEFAULT_PAYMENT_DAYS)
            .internalComments(DEFAULT_INTERNAL_COMMENTS)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .nric(DEFAULT_NRIC)
            .companyRegistrationNo(DEFAULT_COMPANY_REGISTRATION_NO)
            .faxNumber(DEFAULT_FAX_NUMBER)
            .websiteUrl(DEFAULT_WEBSITE_URL)
            .webServiceUrl(DEFAULT_WEB_SERVICE_URL)
            .creditRating(DEFAULT_CREDIT_RATING)
            .officialStoreInd(DEFAULT_OFFICIAL_STORE_IND)
            .storeName(DEFAULT_STORE_NAME)
            .logo(DEFAULT_LOGO)
            .nricFrontPhoto(DEFAULT_NRIC_FRONT_PHOTO)
            .nricBackPhoto(DEFAULT_NRIC_BACK_PHOTO)
            .bankBookPhoto(DEFAULT_BANK_BOOK_PHOTO)
            .companyRegistrationPhoto(DEFAULT_COMPANY_REGISTRATION_PHOTO)
            .distributorCertificatePhoto(DEFAULT_DISTRIBUTOR_CERTIFICATE_PHOTO)
            .activeFlag(DEFAULT_ACTIVE_FLAG)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return suppliers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Suppliers createUpdatedEntity(EntityManager em) {
        Suppliers suppliers = new Suppliers()
            .name(UPDATED_NAME)
            .supplierReference(UPDATED_SUPPLIER_REFERENCE)
            .bankAccountName(UPDATED_BANK_ACCOUNT_NAME)
            .bankAccountBranch(UPDATED_BANK_ACCOUNT_BRANCH)
            .bankAccountCode(UPDATED_BANK_ACCOUNT_CODE)
            .bankAccountNumber(UPDATED_BANK_ACCOUNT_NUMBER)
            .bankInternationalCode(UPDATED_BANK_INTERNATIONAL_CODE)
            .paymentDays(UPDATED_PAYMENT_DAYS)
            .internalComments(UPDATED_INTERNAL_COMMENTS)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .nric(UPDATED_NRIC)
            .companyRegistrationNo(UPDATED_COMPANY_REGISTRATION_NO)
            .faxNumber(UPDATED_FAX_NUMBER)
            .websiteUrl(UPDATED_WEBSITE_URL)
            .webServiceUrl(UPDATED_WEB_SERVICE_URL)
            .creditRating(UPDATED_CREDIT_RATING)
            .officialStoreInd(UPDATED_OFFICIAL_STORE_IND)
            .storeName(UPDATED_STORE_NAME)
            .logo(UPDATED_LOGO)
            .nricFrontPhoto(UPDATED_NRIC_FRONT_PHOTO)
            .nricBackPhoto(UPDATED_NRIC_BACK_PHOTO)
            .bankBookPhoto(UPDATED_BANK_BOOK_PHOTO)
            .companyRegistrationPhoto(UPDATED_COMPANY_REGISTRATION_PHOTO)
            .distributorCertificatePhoto(UPDATED_DISTRIBUTOR_CERTIFICATE_PHOTO)
            .activeFlag(UPDATED_ACTIVE_FLAG)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return suppliers;
    }

    @BeforeEach
    public void initTest() {
        suppliers = createEntity(em);
    }

    @Test
    @Transactional
    public void createSuppliers() throws Exception {
        int databaseSizeBeforeCreate = suppliersRepository.findAll().size();
        // Create the Suppliers
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);
        restSuppliersMockMvc.perform(post("/api/suppliers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isCreated());

        // Validate the Suppliers in the database
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeCreate + 1);
        Suppliers testSuppliers = suppliersList.get(suppliersList.size() - 1);
        assertThat(testSuppliers.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSuppliers.getSupplierReference()).isEqualTo(DEFAULT_SUPPLIER_REFERENCE);
        assertThat(testSuppliers.getBankAccountName()).isEqualTo(DEFAULT_BANK_ACCOUNT_NAME);
        assertThat(testSuppliers.getBankAccountBranch()).isEqualTo(DEFAULT_BANK_ACCOUNT_BRANCH);
        assertThat(testSuppliers.getBankAccountCode()).isEqualTo(DEFAULT_BANK_ACCOUNT_CODE);
        assertThat(testSuppliers.getBankAccountNumber()).isEqualTo(DEFAULT_BANK_ACCOUNT_NUMBER);
        assertThat(testSuppliers.getBankInternationalCode()).isEqualTo(DEFAULT_BANK_INTERNATIONAL_CODE);
        assertThat(testSuppliers.getPaymentDays()).isEqualTo(DEFAULT_PAYMENT_DAYS);
        assertThat(testSuppliers.getInternalComments()).isEqualTo(DEFAULT_INTERNAL_COMMENTS);
        assertThat(testSuppliers.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testSuppliers.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testSuppliers.getNric()).isEqualTo(DEFAULT_NRIC);
        assertThat(testSuppliers.getCompanyRegistrationNo()).isEqualTo(DEFAULT_COMPANY_REGISTRATION_NO);
        assertThat(testSuppliers.getFaxNumber()).isEqualTo(DEFAULT_FAX_NUMBER);
        assertThat(testSuppliers.getWebsiteUrl()).isEqualTo(DEFAULT_WEBSITE_URL);
        assertThat(testSuppliers.getWebServiceUrl()).isEqualTo(DEFAULT_WEB_SERVICE_URL);
        assertThat(testSuppliers.getCreditRating()).isEqualTo(DEFAULT_CREDIT_RATING);
        assertThat(testSuppliers.isOfficialStoreInd()).isEqualTo(DEFAULT_OFFICIAL_STORE_IND);
        assertThat(testSuppliers.getStoreName()).isEqualTo(DEFAULT_STORE_NAME);
        assertThat(testSuppliers.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testSuppliers.getNricFrontPhoto()).isEqualTo(DEFAULT_NRIC_FRONT_PHOTO);
        assertThat(testSuppliers.getNricBackPhoto()).isEqualTo(DEFAULT_NRIC_BACK_PHOTO);
        assertThat(testSuppliers.getBankBookPhoto()).isEqualTo(DEFAULT_BANK_BOOK_PHOTO);
        assertThat(testSuppliers.getCompanyRegistrationPhoto()).isEqualTo(DEFAULT_COMPANY_REGISTRATION_PHOTO);
        assertThat(testSuppliers.getDistributorCertificatePhoto()).isEqualTo(DEFAULT_DISTRIBUTOR_CERTIFICATE_PHOTO);
        assertThat(testSuppliers.isActiveFlag()).isEqualTo(DEFAULT_ACTIVE_FLAG);
        assertThat(testSuppliers.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testSuppliers.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createSuppliersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = suppliersRepository.findAll().size();

        // Create the Suppliers with an existing ID
        suppliers.setId(1L);
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSuppliersMockMvc.perform(post("/api/suppliers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Suppliers in the database
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = suppliersRepository.findAll().size();
        // set the field null
        suppliers.setName(null);

        // Create the Suppliers, which fails.
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);


        restSuppliersMockMvc.perform(post("/api/suppliers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isBadRequest());

        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = suppliersRepository.findAll().size();
        // set the field null
        suppliers.setPhoneNumber(null);

        // Create the Suppliers, which fails.
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);


        restSuppliersMockMvc.perform(post("/api/suppliers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isBadRequest());

        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBankBookPhotoIsRequired() throws Exception {
        int databaseSizeBeforeTest = suppliersRepository.findAll().size();
        // set the field null
        suppliers.setBankBookPhoto(null);

        // Create the Suppliers, which fails.
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);


        restSuppliersMockMvc.perform(post("/api/suppliers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isBadRequest());

        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = suppliersRepository.findAll().size();
        // set the field null
        suppliers.setActiveFlag(null);

        // Create the Suppliers, which fails.
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);


        restSuppliersMockMvc.perform(post("/api/suppliers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isBadRequest());

        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = suppliersRepository.findAll().size();
        // set the field null
        suppliers.setValidFrom(null);

        // Create the Suppliers, which fails.
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);


        restSuppliersMockMvc.perform(post("/api/suppliers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isBadRequest());

        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSuppliers() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList
        restSuppliersMockMvc.perform(get("/api/suppliers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suppliers.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].supplierReference").value(hasItem(DEFAULT_SUPPLIER_REFERENCE)))
            .andExpect(jsonPath("$.[*].bankAccountName").value(hasItem(DEFAULT_BANK_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].bankAccountBranch").value(hasItem(DEFAULT_BANK_ACCOUNT_BRANCH)))
            .andExpect(jsonPath("$.[*].bankAccountCode").value(hasItem(DEFAULT_BANK_ACCOUNT_CODE)))
            .andExpect(jsonPath("$.[*].bankAccountNumber").value(hasItem(DEFAULT_BANK_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].bankInternationalCode").value(hasItem(DEFAULT_BANK_INTERNATIONAL_CODE)))
            .andExpect(jsonPath("$.[*].paymentDays").value(hasItem(DEFAULT_PAYMENT_DAYS)))
            .andExpect(jsonPath("$.[*].internalComments").value(hasItem(DEFAULT_INTERNAL_COMMENTS)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].nric").value(hasItem(DEFAULT_NRIC)))
            .andExpect(jsonPath("$.[*].companyRegistrationNo").value(hasItem(DEFAULT_COMPANY_REGISTRATION_NO)))
            .andExpect(jsonPath("$.[*].faxNumber").value(hasItem(DEFAULT_FAX_NUMBER)))
            .andExpect(jsonPath("$.[*].websiteUrl").value(hasItem(DEFAULT_WEBSITE_URL)))
            .andExpect(jsonPath("$.[*].webServiceUrl").value(hasItem(DEFAULT_WEB_SERVICE_URL)))
            .andExpect(jsonPath("$.[*].creditRating").value(hasItem(DEFAULT_CREDIT_RATING)))
            .andExpect(jsonPath("$.[*].officialStoreInd").value(hasItem(DEFAULT_OFFICIAL_STORE_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].storeName").value(hasItem(DEFAULT_STORE_NAME)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.[*].nricFrontPhoto").value(hasItem(DEFAULT_NRIC_FRONT_PHOTO)))
            .andExpect(jsonPath("$.[*].nricBackPhoto").value(hasItem(DEFAULT_NRIC_BACK_PHOTO)))
            .andExpect(jsonPath("$.[*].bankBookPhoto").value(hasItem(DEFAULT_BANK_BOOK_PHOTO)))
            .andExpect(jsonPath("$.[*].companyRegistrationPhoto").value(hasItem(DEFAULT_COMPANY_REGISTRATION_PHOTO)))
            .andExpect(jsonPath("$.[*].distributorCertificatePhoto").value(hasItem(DEFAULT_DISTRIBUTOR_CERTIFICATE_PHOTO)))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllSuppliersWithEagerRelationshipsIsEnabled() throws Exception {
        when(suppliersServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSuppliersMockMvc.perform(get("/api/suppliers?eagerload=true"))
            .andExpect(status().isOk());

        verify(suppliersServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllSuppliersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(suppliersServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSuppliersMockMvc.perform(get("/api/suppliers?eagerload=true"))
            .andExpect(status().isOk());

        verify(suppliersServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getSuppliers() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get the suppliers
        restSuppliersMockMvc.perform(get("/api/suppliers/{id}", suppliers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(suppliers.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.supplierReference").value(DEFAULT_SUPPLIER_REFERENCE))
            .andExpect(jsonPath("$.bankAccountName").value(DEFAULT_BANK_ACCOUNT_NAME))
            .andExpect(jsonPath("$.bankAccountBranch").value(DEFAULT_BANK_ACCOUNT_BRANCH))
            .andExpect(jsonPath("$.bankAccountCode").value(DEFAULT_BANK_ACCOUNT_CODE))
            .andExpect(jsonPath("$.bankAccountNumber").value(DEFAULT_BANK_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.bankInternationalCode").value(DEFAULT_BANK_INTERNATIONAL_CODE))
            .andExpect(jsonPath("$.paymentDays").value(DEFAULT_PAYMENT_DAYS))
            .andExpect(jsonPath("$.internalComments").value(DEFAULT_INTERNAL_COMMENTS))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS))
            .andExpect(jsonPath("$.nric").value(DEFAULT_NRIC))
            .andExpect(jsonPath("$.companyRegistrationNo").value(DEFAULT_COMPANY_REGISTRATION_NO))
            .andExpect(jsonPath("$.faxNumber").value(DEFAULT_FAX_NUMBER))
            .andExpect(jsonPath("$.websiteUrl").value(DEFAULT_WEBSITE_URL))
            .andExpect(jsonPath("$.webServiceUrl").value(DEFAULT_WEB_SERVICE_URL))
            .andExpect(jsonPath("$.creditRating").value(DEFAULT_CREDIT_RATING))
            .andExpect(jsonPath("$.officialStoreInd").value(DEFAULT_OFFICIAL_STORE_IND.booleanValue()))
            .andExpect(jsonPath("$.storeName").value(DEFAULT_STORE_NAME))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO))
            .andExpect(jsonPath("$.nricFrontPhoto").value(DEFAULT_NRIC_FRONT_PHOTO))
            .andExpect(jsonPath("$.nricBackPhoto").value(DEFAULT_NRIC_BACK_PHOTO))
            .andExpect(jsonPath("$.bankBookPhoto").value(DEFAULT_BANK_BOOK_PHOTO))
            .andExpect(jsonPath("$.companyRegistrationPhoto").value(DEFAULT_COMPANY_REGISTRATION_PHOTO))
            .andExpect(jsonPath("$.distributorCertificatePhoto").value(DEFAULT_DISTRIBUTOR_CERTIFICATE_PHOTO))
            .andExpect(jsonPath("$.activeFlag").value(DEFAULT_ACTIVE_FLAG.booleanValue()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getSuppliersByIdFiltering() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        Long id = suppliers.getId();

        defaultSuppliersShouldBeFound("id.equals=" + id);
        defaultSuppliersShouldNotBeFound("id.notEquals=" + id);

        defaultSuppliersShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSuppliersShouldNotBeFound("id.greaterThan=" + id);

        defaultSuppliersShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSuppliersShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSuppliersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where name equals to DEFAULT_NAME
        defaultSuppliersShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the suppliersList where name equals to UPDATED_NAME
        defaultSuppliersShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where name not equals to DEFAULT_NAME
        defaultSuppliersShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the suppliersList where name not equals to UPDATED_NAME
        defaultSuppliersShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSuppliersShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the suppliersList where name equals to UPDATED_NAME
        defaultSuppliersShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where name is not null
        defaultSuppliersShouldBeFound("name.specified=true");

        // Get all the suppliersList where name is null
        defaultSuppliersShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByNameContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where name contains DEFAULT_NAME
        defaultSuppliersShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the suppliersList where name contains UPDATED_NAME
        defaultSuppliersShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where name does not contain DEFAULT_NAME
        defaultSuppliersShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the suppliersList where name does not contain UPDATED_NAME
        defaultSuppliersShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllSuppliersBySupplierReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierReference equals to DEFAULT_SUPPLIER_REFERENCE
        defaultSuppliersShouldBeFound("supplierReference.equals=" + DEFAULT_SUPPLIER_REFERENCE);

        // Get all the suppliersList where supplierReference equals to UPDATED_SUPPLIER_REFERENCE
        defaultSuppliersShouldNotBeFound("supplierReference.equals=" + UPDATED_SUPPLIER_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllSuppliersBySupplierReferenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierReference not equals to DEFAULT_SUPPLIER_REFERENCE
        defaultSuppliersShouldNotBeFound("supplierReference.notEquals=" + DEFAULT_SUPPLIER_REFERENCE);

        // Get all the suppliersList where supplierReference not equals to UPDATED_SUPPLIER_REFERENCE
        defaultSuppliersShouldBeFound("supplierReference.notEquals=" + UPDATED_SUPPLIER_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllSuppliersBySupplierReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierReference in DEFAULT_SUPPLIER_REFERENCE or UPDATED_SUPPLIER_REFERENCE
        defaultSuppliersShouldBeFound("supplierReference.in=" + DEFAULT_SUPPLIER_REFERENCE + "," + UPDATED_SUPPLIER_REFERENCE);

        // Get all the suppliersList where supplierReference equals to UPDATED_SUPPLIER_REFERENCE
        defaultSuppliersShouldNotBeFound("supplierReference.in=" + UPDATED_SUPPLIER_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllSuppliersBySupplierReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierReference is not null
        defaultSuppliersShouldBeFound("supplierReference.specified=true");

        // Get all the suppliersList where supplierReference is null
        defaultSuppliersShouldNotBeFound("supplierReference.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersBySupplierReferenceContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierReference contains DEFAULT_SUPPLIER_REFERENCE
        defaultSuppliersShouldBeFound("supplierReference.contains=" + DEFAULT_SUPPLIER_REFERENCE);

        // Get all the suppliersList where supplierReference contains UPDATED_SUPPLIER_REFERENCE
        defaultSuppliersShouldNotBeFound("supplierReference.contains=" + UPDATED_SUPPLIER_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllSuppliersBySupplierReferenceNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierReference does not contain DEFAULT_SUPPLIER_REFERENCE
        defaultSuppliersShouldNotBeFound("supplierReference.doesNotContain=" + DEFAULT_SUPPLIER_REFERENCE);

        // Get all the suppliersList where supplierReference does not contain UPDATED_SUPPLIER_REFERENCE
        defaultSuppliersShouldBeFound("supplierReference.doesNotContain=" + UPDATED_SUPPLIER_REFERENCE);
    }


    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNameIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountName equals to DEFAULT_BANK_ACCOUNT_NAME
        defaultSuppliersShouldBeFound("bankAccountName.equals=" + DEFAULT_BANK_ACCOUNT_NAME);

        // Get all the suppliersList where bankAccountName equals to UPDATED_BANK_ACCOUNT_NAME
        defaultSuppliersShouldNotBeFound("bankAccountName.equals=" + UPDATED_BANK_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountName not equals to DEFAULT_BANK_ACCOUNT_NAME
        defaultSuppliersShouldNotBeFound("bankAccountName.notEquals=" + DEFAULT_BANK_ACCOUNT_NAME);

        // Get all the suppliersList where bankAccountName not equals to UPDATED_BANK_ACCOUNT_NAME
        defaultSuppliersShouldBeFound("bankAccountName.notEquals=" + UPDATED_BANK_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNameIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountName in DEFAULT_BANK_ACCOUNT_NAME or UPDATED_BANK_ACCOUNT_NAME
        defaultSuppliersShouldBeFound("bankAccountName.in=" + DEFAULT_BANK_ACCOUNT_NAME + "," + UPDATED_BANK_ACCOUNT_NAME);

        // Get all the suppliersList where bankAccountName equals to UPDATED_BANK_ACCOUNT_NAME
        defaultSuppliersShouldNotBeFound("bankAccountName.in=" + UPDATED_BANK_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountName is not null
        defaultSuppliersShouldBeFound("bankAccountName.specified=true");

        // Get all the suppliersList where bankAccountName is null
        defaultSuppliersShouldNotBeFound("bankAccountName.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByBankAccountNameContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountName contains DEFAULT_BANK_ACCOUNT_NAME
        defaultSuppliersShouldBeFound("bankAccountName.contains=" + DEFAULT_BANK_ACCOUNT_NAME);

        // Get all the suppliersList where bankAccountName contains UPDATED_BANK_ACCOUNT_NAME
        defaultSuppliersShouldNotBeFound("bankAccountName.contains=" + UPDATED_BANK_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNameNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountName does not contain DEFAULT_BANK_ACCOUNT_NAME
        defaultSuppliersShouldNotBeFound("bankAccountName.doesNotContain=" + DEFAULT_BANK_ACCOUNT_NAME);

        // Get all the suppliersList where bankAccountName does not contain UPDATED_BANK_ACCOUNT_NAME
        defaultSuppliersShouldBeFound("bankAccountName.doesNotContain=" + UPDATED_BANK_ACCOUNT_NAME);
    }


    @Test
    @Transactional
    public void getAllSuppliersByBankAccountBranchIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountBranch equals to DEFAULT_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldBeFound("bankAccountBranch.equals=" + DEFAULT_BANK_ACCOUNT_BRANCH);

        // Get all the suppliersList where bankAccountBranch equals to UPDATED_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldNotBeFound("bankAccountBranch.equals=" + UPDATED_BANK_ACCOUNT_BRANCH);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountBranchIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountBranch not equals to DEFAULT_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldNotBeFound("bankAccountBranch.notEquals=" + DEFAULT_BANK_ACCOUNT_BRANCH);

        // Get all the suppliersList where bankAccountBranch not equals to UPDATED_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldBeFound("bankAccountBranch.notEquals=" + UPDATED_BANK_ACCOUNT_BRANCH);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountBranchIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountBranch in DEFAULT_BANK_ACCOUNT_BRANCH or UPDATED_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldBeFound("bankAccountBranch.in=" + DEFAULT_BANK_ACCOUNT_BRANCH + "," + UPDATED_BANK_ACCOUNT_BRANCH);

        // Get all the suppliersList where bankAccountBranch equals to UPDATED_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldNotBeFound("bankAccountBranch.in=" + UPDATED_BANK_ACCOUNT_BRANCH);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountBranchIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountBranch is not null
        defaultSuppliersShouldBeFound("bankAccountBranch.specified=true");

        // Get all the suppliersList where bankAccountBranch is null
        defaultSuppliersShouldNotBeFound("bankAccountBranch.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByBankAccountBranchContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountBranch contains DEFAULT_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldBeFound("bankAccountBranch.contains=" + DEFAULT_BANK_ACCOUNT_BRANCH);

        // Get all the suppliersList where bankAccountBranch contains UPDATED_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldNotBeFound("bankAccountBranch.contains=" + UPDATED_BANK_ACCOUNT_BRANCH);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountBranchNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountBranch does not contain DEFAULT_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldNotBeFound("bankAccountBranch.doesNotContain=" + DEFAULT_BANK_ACCOUNT_BRANCH);

        // Get all the suppliersList where bankAccountBranch does not contain UPDATED_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldBeFound("bankAccountBranch.doesNotContain=" + UPDATED_BANK_ACCOUNT_BRANCH);
    }


    @Test
    @Transactional
    public void getAllSuppliersByBankAccountCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountCode equals to DEFAULT_BANK_ACCOUNT_CODE
        defaultSuppliersShouldBeFound("bankAccountCode.equals=" + DEFAULT_BANK_ACCOUNT_CODE);

        // Get all the suppliersList where bankAccountCode equals to UPDATED_BANK_ACCOUNT_CODE
        defaultSuppliersShouldNotBeFound("bankAccountCode.equals=" + UPDATED_BANK_ACCOUNT_CODE);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountCode not equals to DEFAULT_BANK_ACCOUNT_CODE
        defaultSuppliersShouldNotBeFound("bankAccountCode.notEquals=" + DEFAULT_BANK_ACCOUNT_CODE);

        // Get all the suppliersList where bankAccountCode not equals to UPDATED_BANK_ACCOUNT_CODE
        defaultSuppliersShouldBeFound("bankAccountCode.notEquals=" + UPDATED_BANK_ACCOUNT_CODE);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountCodeIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountCode in DEFAULT_BANK_ACCOUNT_CODE or UPDATED_BANK_ACCOUNT_CODE
        defaultSuppliersShouldBeFound("bankAccountCode.in=" + DEFAULT_BANK_ACCOUNT_CODE + "," + UPDATED_BANK_ACCOUNT_CODE);

        // Get all the suppliersList where bankAccountCode equals to UPDATED_BANK_ACCOUNT_CODE
        defaultSuppliersShouldNotBeFound("bankAccountCode.in=" + UPDATED_BANK_ACCOUNT_CODE);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountCode is not null
        defaultSuppliersShouldBeFound("bankAccountCode.specified=true");

        // Get all the suppliersList where bankAccountCode is null
        defaultSuppliersShouldNotBeFound("bankAccountCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByBankAccountCodeContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountCode contains DEFAULT_BANK_ACCOUNT_CODE
        defaultSuppliersShouldBeFound("bankAccountCode.contains=" + DEFAULT_BANK_ACCOUNT_CODE);

        // Get all the suppliersList where bankAccountCode contains UPDATED_BANK_ACCOUNT_CODE
        defaultSuppliersShouldNotBeFound("bankAccountCode.contains=" + UPDATED_BANK_ACCOUNT_CODE);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountCodeNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountCode does not contain DEFAULT_BANK_ACCOUNT_CODE
        defaultSuppliersShouldNotBeFound("bankAccountCode.doesNotContain=" + DEFAULT_BANK_ACCOUNT_CODE);

        // Get all the suppliersList where bankAccountCode does not contain UPDATED_BANK_ACCOUNT_CODE
        defaultSuppliersShouldBeFound("bankAccountCode.doesNotContain=" + UPDATED_BANK_ACCOUNT_CODE);
    }


    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountNumber equals to DEFAULT_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldBeFound("bankAccountNumber.equals=" + DEFAULT_BANK_ACCOUNT_NUMBER);

        // Get all the suppliersList where bankAccountNumber equals to UPDATED_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldNotBeFound("bankAccountNumber.equals=" + UPDATED_BANK_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountNumber not equals to DEFAULT_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldNotBeFound("bankAccountNumber.notEquals=" + DEFAULT_BANK_ACCOUNT_NUMBER);

        // Get all the suppliersList where bankAccountNumber not equals to UPDATED_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldBeFound("bankAccountNumber.notEquals=" + UPDATED_BANK_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountNumber in DEFAULT_BANK_ACCOUNT_NUMBER or UPDATED_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldBeFound("bankAccountNumber.in=" + DEFAULT_BANK_ACCOUNT_NUMBER + "," + UPDATED_BANK_ACCOUNT_NUMBER);

        // Get all the suppliersList where bankAccountNumber equals to UPDATED_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldNotBeFound("bankAccountNumber.in=" + UPDATED_BANK_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountNumber is not null
        defaultSuppliersShouldBeFound("bankAccountNumber.specified=true");

        // Get all the suppliersList where bankAccountNumber is null
        defaultSuppliersShouldNotBeFound("bankAccountNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByBankAccountNumberContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountNumber contains DEFAULT_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldBeFound("bankAccountNumber.contains=" + DEFAULT_BANK_ACCOUNT_NUMBER);

        // Get all the suppliersList where bankAccountNumber contains UPDATED_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldNotBeFound("bankAccountNumber.contains=" + UPDATED_BANK_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNumberNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountNumber does not contain DEFAULT_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldNotBeFound("bankAccountNumber.doesNotContain=" + DEFAULT_BANK_ACCOUNT_NUMBER);

        // Get all the suppliersList where bankAccountNumber does not contain UPDATED_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldBeFound("bankAccountNumber.doesNotContain=" + UPDATED_BANK_ACCOUNT_NUMBER);
    }


    @Test
    @Transactional
    public void getAllSuppliersByBankInternationalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankInternationalCode equals to DEFAULT_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldBeFound("bankInternationalCode.equals=" + DEFAULT_BANK_INTERNATIONAL_CODE);

        // Get all the suppliersList where bankInternationalCode equals to UPDATED_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldNotBeFound("bankInternationalCode.equals=" + UPDATED_BANK_INTERNATIONAL_CODE);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankInternationalCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankInternationalCode not equals to DEFAULT_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldNotBeFound("bankInternationalCode.notEquals=" + DEFAULT_BANK_INTERNATIONAL_CODE);

        // Get all the suppliersList where bankInternationalCode not equals to UPDATED_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldBeFound("bankInternationalCode.notEquals=" + UPDATED_BANK_INTERNATIONAL_CODE);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankInternationalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankInternationalCode in DEFAULT_BANK_INTERNATIONAL_CODE or UPDATED_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldBeFound("bankInternationalCode.in=" + DEFAULT_BANK_INTERNATIONAL_CODE + "," + UPDATED_BANK_INTERNATIONAL_CODE);

        // Get all the suppliersList where bankInternationalCode equals to UPDATED_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldNotBeFound("bankInternationalCode.in=" + UPDATED_BANK_INTERNATIONAL_CODE);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankInternationalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankInternationalCode is not null
        defaultSuppliersShouldBeFound("bankInternationalCode.specified=true");

        // Get all the suppliersList where bankInternationalCode is null
        defaultSuppliersShouldNotBeFound("bankInternationalCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByBankInternationalCodeContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankInternationalCode contains DEFAULT_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldBeFound("bankInternationalCode.contains=" + DEFAULT_BANK_INTERNATIONAL_CODE);

        // Get all the suppliersList where bankInternationalCode contains UPDATED_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldNotBeFound("bankInternationalCode.contains=" + UPDATED_BANK_INTERNATIONAL_CODE);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankInternationalCodeNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankInternationalCode does not contain DEFAULT_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldNotBeFound("bankInternationalCode.doesNotContain=" + DEFAULT_BANK_INTERNATIONAL_CODE);

        // Get all the suppliersList where bankInternationalCode does not contain UPDATED_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldBeFound("bankInternationalCode.doesNotContain=" + UPDATED_BANK_INTERNATIONAL_CODE);
    }


    @Test
    @Transactional
    public void getAllSuppliersByPaymentDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where paymentDays equals to DEFAULT_PAYMENT_DAYS
        defaultSuppliersShouldBeFound("paymentDays.equals=" + DEFAULT_PAYMENT_DAYS);

        // Get all the suppliersList where paymentDays equals to UPDATED_PAYMENT_DAYS
        defaultSuppliersShouldNotBeFound("paymentDays.equals=" + UPDATED_PAYMENT_DAYS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPaymentDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where paymentDays not equals to DEFAULT_PAYMENT_DAYS
        defaultSuppliersShouldNotBeFound("paymentDays.notEquals=" + DEFAULT_PAYMENT_DAYS);

        // Get all the suppliersList where paymentDays not equals to UPDATED_PAYMENT_DAYS
        defaultSuppliersShouldBeFound("paymentDays.notEquals=" + UPDATED_PAYMENT_DAYS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPaymentDaysIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where paymentDays in DEFAULT_PAYMENT_DAYS or UPDATED_PAYMENT_DAYS
        defaultSuppliersShouldBeFound("paymentDays.in=" + DEFAULT_PAYMENT_DAYS + "," + UPDATED_PAYMENT_DAYS);

        // Get all the suppliersList where paymentDays equals to UPDATED_PAYMENT_DAYS
        defaultSuppliersShouldNotBeFound("paymentDays.in=" + UPDATED_PAYMENT_DAYS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPaymentDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where paymentDays is not null
        defaultSuppliersShouldBeFound("paymentDays.specified=true");

        // Get all the suppliersList where paymentDays is null
        defaultSuppliersShouldNotBeFound("paymentDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByPaymentDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where paymentDays is greater than or equal to DEFAULT_PAYMENT_DAYS
        defaultSuppliersShouldBeFound("paymentDays.greaterThanOrEqual=" + DEFAULT_PAYMENT_DAYS);

        // Get all the suppliersList where paymentDays is greater than or equal to UPDATED_PAYMENT_DAYS
        defaultSuppliersShouldNotBeFound("paymentDays.greaterThanOrEqual=" + UPDATED_PAYMENT_DAYS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPaymentDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where paymentDays is less than or equal to DEFAULT_PAYMENT_DAYS
        defaultSuppliersShouldBeFound("paymentDays.lessThanOrEqual=" + DEFAULT_PAYMENT_DAYS);

        // Get all the suppliersList where paymentDays is less than or equal to SMALLER_PAYMENT_DAYS
        defaultSuppliersShouldNotBeFound("paymentDays.lessThanOrEqual=" + SMALLER_PAYMENT_DAYS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPaymentDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where paymentDays is less than DEFAULT_PAYMENT_DAYS
        defaultSuppliersShouldNotBeFound("paymentDays.lessThan=" + DEFAULT_PAYMENT_DAYS);

        // Get all the suppliersList where paymentDays is less than UPDATED_PAYMENT_DAYS
        defaultSuppliersShouldBeFound("paymentDays.lessThan=" + UPDATED_PAYMENT_DAYS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPaymentDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where paymentDays is greater than DEFAULT_PAYMENT_DAYS
        defaultSuppliersShouldNotBeFound("paymentDays.greaterThan=" + DEFAULT_PAYMENT_DAYS);

        // Get all the suppliersList where paymentDays is greater than SMALLER_PAYMENT_DAYS
        defaultSuppliersShouldBeFound("paymentDays.greaterThan=" + SMALLER_PAYMENT_DAYS);
    }


    @Test
    @Transactional
    public void getAllSuppliersByInternalCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where internalComments equals to DEFAULT_INTERNAL_COMMENTS
        defaultSuppliersShouldBeFound("internalComments.equals=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the suppliersList where internalComments equals to UPDATED_INTERNAL_COMMENTS
        defaultSuppliersShouldNotBeFound("internalComments.equals=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByInternalCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where internalComments not equals to DEFAULT_INTERNAL_COMMENTS
        defaultSuppliersShouldNotBeFound("internalComments.notEquals=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the suppliersList where internalComments not equals to UPDATED_INTERNAL_COMMENTS
        defaultSuppliersShouldBeFound("internalComments.notEquals=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByInternalCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where internalComments in DEFAULT_INTERNAL_COMMENTS or UPDATED_INTERNAL_COMMENTS
        defaultSuppliersShouldBeFound("internalComments.in=" + DEFAULT_INTERNAL_COMMENTS + "," + UPDATED_INTERNAL_COMMENTS);

        // Get all the suppliersList where internalComments equals to UPDATED_INTERNAL_COMMENTS
        defaultSuppliersShouldNotBeFound("internalComments.in=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByInternalCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where internalComments is not null
        defaultSuppliersShouldBeFound("internalComments.specified=true");

        // Get all the suppliersList where internalComments is null
        defaultSuppliersShouldNotBeFound("internalComments.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByInternalCommentsContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where internalComments contains DEFAULT_INTERNAL_COMMENTS
        defaultSuppliersShouldBeFound("internalComments.contains=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the suppliersList where internalComments contains UPDATED_INTERNAL_COMMENTS
        defaultSuppliersShouldNotBeFound("internalComments.contains=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByInternalCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where internalComments does not contain DEFAULT_INTERNAL_COMMENTS
        defaultSuppliersShouldNotBeFound("internalComments.doesNotContain=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the suppliersList where internalComments does not contain UPDATED_INTERNAL_COMMENTS
        defaultSuppliersShouldBeFound("internalComments.doesNotContain=" + UPDATED_INTERNAL_COMMENTS);
    }


    @Test
    @Transactional
    public void getAllSuppliersByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultSuppliersShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the suppliersList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultSuppliersShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where phoneNumber not equals to DEFAULT_PHONE_NUMBER
        defaultSuppliersShouldNotBeFound("phoneNumber.notEquals=" + DEFAULT_PHONE_NUMBER);

        // Get all the suppliersList where phoneNumber not equals to UPDATED_PHONE_NUMBER
        defaultSuppliersShouldBeFound("phoneNumber.notEquals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultSuppliersShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the suppliersList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultSuppliersShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where phoneNumber is not null
        defaultSuppliersShouldBeFound("phoneNumber.specified=true");

        // Get all the suppliersList where phoneNumber is null
        defaultSuppliersShouldNotBeFound("phoneNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultSuppliersShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the suppliersList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultSuppliersShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultSuppliersShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the suppliersList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultSuppliersShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllSuppliersByEmailAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where emailAddress equals to DEFAULT_EMAIL_ADDRESS
        defaultSuppliersShouldBeFound("emailAddress.equals=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the suppliersList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultSuppliersShouldNotBeFound("emailAddress.equals=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByEmailAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where emailAddress not equals to DEFAULT_EMAIL_ADDRESS
        defaultSuppliersShouldNotBeFound("emailAddress.notEquals=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the suppliersList where emailAddress not equals to UPDATED_EMAIL_ADDRESS
        defaultSuppliersShouldBeFound("emailAddress.notEquals=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByEmailAddressIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where emailAddress in DEFAULT_EMAIL_ADDRESS or UPDATED_EMAIL_ADDRESS
        defaultSuppliersShouldBeFound("emailAddress.in=" + DEFAULT_EMAIL_ADDRESS + "," + UPDATED_EMAIL_ADDRESS);

        // Get all the suppliersList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultSuppliersShouldNotBeFound("emailAddress.in=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByEmailAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where emailAddress is not null
        defaultSuppliersShouldBeFound("emailAddress.specified=true");

        // Get all the suppliersList where emailAddress is null
        defaultSuppliersShouldNotBeFound("emailAddress.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByEmailAddressContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where emailAddress contains DEFAULT_EMAIL_ADDRESS
        defaultSuppliersShouldBeFound("emailAddress.contains=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the suppliersList where emailAddress contains UPDATED_EMAIL_ADDRESS
        defaultSuppliersShouldNotBeFound("emailAddress.contains=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByEmailAddressNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where emailAddress does not contain DEFAULT_EMAIL_ADDRESS
        defaultSuppliersShouldNotBeFound("emailAddress.doesNotContain=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the suppliersList where emailAddress does not contain UPDATED_EMAIL_ADDRESS
        defaultSuppliersShouldBeFound("emailAddress.doesNotContain=" + UPDATED_EMAIL_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllSuppliersByNricIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where nric equals to DEFAULT_NRIC
        defaultSuppliersShouldBeFound("nric.equals=" + DEFAULT_NRIC);

        // Get all the suppliersList where nric equals to UPDATED_NRIC
        defaultSuppliersShouldNotBeFound("nric.equals=" + UPDATED_NRIC);
    }

    @Test
    @Transactional
    public void getAllSuppliersByNricIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where nric not equals to DEFAULT_NRIC
        defaultSuppliersShouldNotBeFound("nric.notEquals=" + DEFAULT_NRIC);

        // Get all the suppliersList where nric not equals to UPDATED_NRIC
        defaultSuppliersShouldBeFound("nric.notEquals=" + UPDATED_NRIC);
    }

    @Test
    @Transactional
    public void getAllSuppliersByNricIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where nric in DEFAULT_NRIC or UPDATED_NRIC
        defaultSuppliersShouldBeFound("nric.in=" + DEFAULT_NRIC + "," + UPDATED_NRIC);

        // Get all the suppliersList where nric equals to UPDATED_NRIC
        defaultSuppliersShouldNotBeFound("nric.in=" + UPDATED_NRIC);
    }

    @Test
    @Transactional
    public void getAllSuppliersByNricIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where nric is not null
        defaultSuppliersShouldBeFound("nric.specified=true");

        // Get all the suppliersList where nric is null
        defaultSuppliersShouldNotBeFound("nric.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByNricContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where nric contains DEFAULT_NRIC
        defaultSuppliersShouldBeFound("nric.contains=" + DEFAULT_NRIC);

        // Get all the suppliersList where nric contains UPDATED_NRIC
        defaultSuppliersShouldNotBeFound("nric.contains=" + UPDATED_NRIC);
    }

    @Test
    @Transactional
    public void getAllSuppliersByNricNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where nric does not contain DEFAULT_NRIC
        defaultSuppliersShouldNotBeFound("nric.doesNotContain=" + DEFAULT_NRIC);

        // Get all the suppliersList where nric does not contain UPDATED_NRIC
        defaultSuppliersShouldBeFound("nric.doesNotContain=" + UPDATED_NRIC);
    }


    @Test
    @Transactional
    public void getAllSuppliersByCompanyRegistrationNoIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where companyRegistrationNo equals to DEFAULT_COMPANY_REGISTRATION_NO
        defaultSuppliersShouldBeFound("companyRegistrationNo.equals=" + DEFAULT_COMPANY_REGISTRATION_NO);

        // Get all the suppliersList where companyRegistrationNo equals to UPDATED_COMPANY_REGISTRATION_NO
        defaultSuppliersShouldNotBeFound("companyRegistrationNo.equals=" + UPDATED_COMPANY_REGISTRATION_NO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByCompanyRegistrationNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where companyRegistrationNo not equals to DEFAULT_COMPANY_REGISTRATION_NO
        defaultSuppliersShouldNotBeFound("companyRegistrationNo.notEquals=" + DEFAULT_COMPANY_REGISTRATION_NO);

        // Get all the suppliersList where companyRegistrationNo not equals to UPDATED_COMPANY_REGISTRATION_NO
        defaultSuppliersShouldBeFound("companyRegistrationNo.notEquals=" + UPDATED_COMPANY_REGISTRATION_NO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByCompanyRegistrationNoIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where companyRegistrationNo in DEFAULT_COMPANY_REGISTRATION_NO or UPDATED_COMPANY_REGISTRATION_NO
        defaultSuppliersShouldBeFound("companyRegistrationNo.in=" + DEFAULT_COMPANY_REGISTRATION_NO + "," + UPDATED_COMPANY_REGISTRATION_NO);

        // Get all the suppliersList where companyRegistrationNo equals to UPDATED_COMPANY_REGISTRATION_NO
        defaultSuppliersShouldNotBeFound("companyRegistrationNo.in=" + UPDATED_COMPANY_REGISTRATION_NO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByCompanyRegistrationNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where companyRegistrationNo is not null
        defaultSuppliersShouldBeFound("companyRegistrationNo.specified=true");

        // Get all the suppliersList where companyRegistrationNo is null
        defaultSuppliersShouldNotBeFound("companyRegistrationNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByCompanyRegistrationNoContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where companyRegistrationNo contains DEFAULT_COMPANY_REGISTRATION_NO
        defaultSuppliersShouldBeFound("companyRegistrationNo.contains=" + DEFAULT_COMPANY_REGISTRATION_NO);

        // Get all the suppliersList where companyRegistrationNo contains UPDATED_COMPANY_REGISTRATION_NO
        defaultSuppliersShouldNotBeFound("companyRegistrationNo.contains=" + UPDATED_COMPANY_REGISTRATION_NO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByCompanyRegistrationNoNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where companyRegistrationNo does not contain DEFAULT_COMPANY_REGISTRATION_NO
        defaultSuppliersShouldNotBeFound("companyRegistrationNo.doesNotContain=" + DEFAULT_COMPANY_REGISTRATION_NO);

        // Get all the suppliersList where companyRegistrationNo does not contain UPDATED_COMPANY_REGISTRATION_NO
        defaultSuppliersShouldBeFound("companyRegistrationNo.doesNotContain=" + UPDATED_COMPANY_REGISTRATION_NO);
    }


    @Test
    @Transactional
    public void getAllSuppliersByFaxNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where faxNumber equals to DEFAULT_FAX_NUMBER
        defaultSuppliersShouldBeFound("faxNumber.equals=" + DEFAULT_FAX_NUMBER);

        // Get all the suppliersList where faxNumber equals to UPDATED_FAX_NUMBER
        defaultSuppliersShouldNotBeFound("faxNumber.equals=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByFaxNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where faxNumber not equals to DEFAULT_FAX_NUMBER
        defaultSuppliersShouldNotBeFound("faxNumber.notEquals=" + DEFAULT_FAX_NUMBER);

        // Get all the suppliersList where faxNumber not equals to UPDATED_FAX_NUMBER
        defaultSuppliersShouldBeFound("faxNumber.notEquals=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByFaxNumberIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where faxNumber in DEFAULT_FAX_NUMBER or UPDATED_FAX_NUMBER
        defaultSuppliersShouldBeFound("faxNumber.in=" + DEFAULT_FAX_NUMBER + "," + UPDATED_FAX_NUMBER);

        // Get all the suppliersList where faxNumber equals to UPDATED_FAX_NUMBER
        defaultSuppliersShouldNotBeFound("faxNumber.in=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByFaxNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where faxNumber is not null
        defaultSuppliersShouldBeFound("faxNumber.specified=true");

        // Get all the suppliersList where faxNumber is null
        defaultSuppliersShouldNotBeFound("faxNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByFaxNumberContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where faxNumber contains DEFAULT_FAX_NUMBER
        defaultSuppliersShouldBeFound("faxNumber.contains=" + DEFAULT_FAX_NUMBER);

        // Get all the suppliersList where faxNumber contains UPDATED_FAX_NUMBER
        defaultSuppliersShouldNotBeFound("faxNumber.contains=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByFaxNumberNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where faxNumber does not contain DEFAULT_FAX_NUMBER
        defaultSuppliersShouldNotBeFound("faxNumber.doesNotContain=" + DEFAULT_FAX_NUMBER);

        // Get all the suppliersList where faxNumber does not contain UPDATED_FAX_NUMBER
        defaultSuppliersShouldBeFound("faxNumber.doesNotContain=" + UPDATED_FAX_NUMBER);
    }


    @Test
    @Transactional
    public void getAllSuppliersByWebsiteUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where websiteUrl equals to DEFAULT_WEBSITE_URL
        defaultSuppliersShouldBeFound("websiteUrl.equals=" + DEFAULT_WEBSITE_URL);

        // Get all the suppliersList where websiteUrl equals to UPDATED_WEBSITE_URL
        defaultSuppliersShouldNotBeFound("websiteUrl.equals=" + UPDATED_WEBSITE_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByWebsiteUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where websiteUrl not equals to DEFAULT_WEBSITE_URL
        defaultSuppliersShouldNotBeFound("websiteUrl.notEquals=" + DEFAULT_WEBSITE_URL);

        // Get all the suppliersList where websiteUrl not equals to UPDATED_WEBSITE_URL
        defaultSuppliersShouldBeFound("websiteUrl.notEquals=" + UPDATED_WEBSITE_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByWebsiteUrlIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where websiteUrl in DEFAULT_WEBSITE_URL or UPDATED_WEBSITE_URL
        defaultSuppliersShouldBeFound("websiteUrl.in=" + DEFAULT_WEBSITE_URL + "," + UPDATED_WEBSITE_URL);

        // Get all the suppliersList where websiteUrl equals to UPDATED_WEBSITE_URL
        defaultSuppliersShouldNotBeFound("websiteUrl.in=" + UPDATED_WEBSITE_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByWebsiteUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where websiteUrl is not null
        defaultSuppliersShouldBeFound("websiteUrl.specified=true");

        // Get all the suppliersList where websiteUrl is null
        defaultSuppliersShouldNotBeFound("websiteUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByWebsiteUrlContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where websiteUrl contains DEFAULT_WEBSITE_URL
        defaultSuppliersShouldBeFound("websiteUrl.contains=" + DEFAULT_WEBSITE_URL);

        // Get all the suppliersList where websiteUrl contains UPDATED_WEBSITE_URL
        defaultSuppliersShouldNotBeFound("websiteUrl.contains=" + UPDATED_WEBSITE_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByWebsiteUrlNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where websiteUrl does not contain DEFAULT_WEBSITE_URL
        defaultSuppliersShouldNotBeFound("websiteUrl.doesNotContain=" + DEFAULT_WEBSITE_URL);

        // Get all the suppliersList where websiteUrl does not contain UPDATED_WEBSITE_URL
        defaultSuppliersShouldBeFound("websiteUrl.doesNotContain=" + UPDATED_WEBSITE_URL);
    }


    @Test
    @Transactional
    public void getAllSuppliersByWebServiceUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where webServiceUrl equals to DEFAULT_WEB_SERVICE_URL
        defaultSuppliersShouldBeFound("webServiceUrl.equals=" + DEFAULT_WEB_SERVICE_URL);

        // Get all the suppliersList where webServiceUrl equals to UPDATED_WEB_SERVICE_URL
        defaultSuppliersShouldNotBeFound("webServiceUrl.equals=" + UPDATED_WEB_SERVICE_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByWebServiceUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where webServiceUrl not equals to DEFAULT_WEB_SERVICE_URL
        defaultSuppliersShouldNotBeFound("webServiceUrl.notEquals=" + DEFAULT_WEB_SERVICE_URL);

        // Get all the suppliersList where webServiceUrl not equals to UPDATED_WEB_SERVICE_URL
        defaultSuppliersShouldBeFound("webServiceUrl.notEquals=" + UPDATED_WEB_SERVICE_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByWebServiceUrlIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where webServiceUrl in DEFAULT_WEB_SERVICE_URL or UPDATED_WEB_SERVICE_URL
        defaultSuppliersShouldBeFound("webServiceUrl.in=" + DEFAULT_WEB_SERVICE_URL + "," + UPDATED_WEB_SERVICE_URL);

        // Get all the suppliersList where webServiceUrl equals to UPDATED_WEB_SERVICE_URL
        defaultSuppliersShouldNotBeFound("webServiceUrl.in=" + UPDATED_WEB_SERVICE_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByWebServiceUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where webServiceUrl is not null
        defaultSuppliersShouldBeFound("webServiceUrl.specified=true");

        // Get all the suppliersList where webServiceUrl is null
        defaultSuppliersShouldNotBeFound("webServiceUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByWebServiceUrlContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where webServiceUrl contains DEFAULT_WEB_SERVICE_URL
        defaultSuppliersShouldBeFound("webServiceUrl.contains=" + DEFAULT_WEB_SERVICE_URL);

        // Get all the suppliersList where webServiceUrl contains UPDATED_WEB_SERVICE_URL
        defaultSuppliersShouldNotBeFound("webServiceUrl.contains=" + UPDATED_WEB_SERVICE_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByWebServiceUrlNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where webServiceUrl does not contain DEFAULT_WEB_SERVICE_URL
        defaultSuppliersShouldNotBeFound("webServiceUrl.doesNotContain=" + DEFAULT_WEB_SERVICE_URL);

        // Get all the suppliersList where webServiceUrl does not contain UPDATED_WEB_SERVICE_URL
        defaultSuppliersShouldBeFound("webServiceUrl.doesNotContain=" + UPDATED_WEB_SERVICE_URL);
    }


    @Test
    @Transactional
    public void getAllSuppliersByCreditRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where creditRating equals to DEFAULT_CREDIT_RATING
        defaultSuppliersShouldBeFound("creditRating.equals=" + DEFAULT_CREDIT_RATING);

        // Get all the suppliersList where creditRating equals to UPDATED_CREDIT_RATING
        defaultSuppliersShouldNotBeFound("creditRating.equals=" + UPDATED_CREDIT_RATING);
    }

    @Test
    @Transactional
    public void getAllSuppliersByCreditRatingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where creditRating not equals to DEFAULT_CREDIT_RATING
        defaultSuppliersShouldNotBeFound("creditRating.notEquals=" + DEFAULT_CREDIT_RATING);

        // Get all the suppliersList where creditRating not equals to UPDATED_CREDIT_RATING
        defaultSuppliersShouldBeFound("creditRating.notEquals=" + UPDATED_CREDIT_RATING);
    }

    @Test
    @Transactional
    public void getAllSuppliersByCreditRatingIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where creditRating in DEFAULT_CREDIT_RATING or UPDATED_CREDIT_RATING
        defaultSuppliersShouldBeFound("creditRating.in=" + DEFAULT_CREDIT_RATING + "," + UPDATED_CREDIT_RATING);

        // Get all the suppliersList where creditRating equals to UPDATED_CREDIT_RATING
        defaultSuppliersShouldNotBeFound("creditRating.in=" + UPDATED_CREDIT_RATING);
    }

    @Test
    @Transactional
    public void getAllSuppliersByCreditRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where creditRating is not null
        defaultSuppliersShouldBeFound("creditRating.specified=true");

        // Get all the suppliersList where creditRating is null
        defaultSuppliersShouldNotBeFound("creditRating.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByCreditRatingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where creditRating is greater than or equal to DEFAULT_CREDIT_RATING
        defaultSuppliersShouldBeFound("creditRating.greaterThanOrEqual=" + DEFAULT_CREDIT_RATING);

        // Get all the suppliersList where creditRating is greater than or equal to UPDATED_CREDIT_RATING
        defaultSuppliersShouldNotBeFound("creditRating.greaterThanOrEqual=" + UPDATED_CREDIT_RATING);
    }

    @Test
    @Transactional
    public void getAllSuppliersByCreditRatingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where creditRating is less than or equal to DEFAULT_CREDIT_RATING
        defaultSuppliersShouldBeFound("creditRating.lessThanOrEqual=" + DEFAULT_CREDIT_RATING);

        // Get all the suppliersList where creditRating is less than or equal to SMALLER_CREDIT_RATING
        defaultSuppliersShouldNotBeFound("creditRating.lessThanOrEqual=" + SMALLER_CREDIT_RATING);
    }

    @Test
    @Transactional
    public void getAllSuppliersByCreditRatingIsLessThanSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where creditRating is less than DEFAULT_CREDIT_RATING
        defaultSuppliersShouldNotBeFound("creditRating.lessThan=" + DEFAULT_CREDIT_RATING);

        // Get all the suppliersList where creditRating is less than UPDATED_CREDIT_RATING
        defaultSuppliersShouldBeFound("creditRating.lessThan=" + UPDATED_CREDIT_RATING);
    }

    @Test
    @Transactional
    public void getAllSuppliersByCreditRatingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where creditRating is greater than DEFAULT_CREDIT_RATING
        defaultSuppliersShouldNotBeFound("creditRating.greaterThan=" + DEFAULT_CREDIT_RATING);

        // Get all the suppliersList where creditRating is greater than SMALLER_CREDIT_RATING
        defaultSuppliersShouldBeFound("creditRating.greaterThan=" + SMALLER_CREDIT_RATING);
    }


    @Test
    @Transactional
    public void getAllSuppliersByOfficialStoreIndIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where officialStoreInd equals to DEFAULT_OFFICIAL_STORE_IND
        defaultSuppliersShouldBeFound("officialStoreInd.equals=" + DEFAULT_OFFICIAL_STORE_IND);

        // Get all the suppliersList where officialStoreInd equals to UPDATED_OFFICIAL_STORE_IND
        defaultSuppliersShouldNotBeFound("officialStoreInd.equals=" + UPDATED_OFFICIAL_STORE_IND);
    }

    @Test
    @Transactional
    public void getAllSuppliersByOfficialStoreIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where officialStoreInd not equals to DEFAULT_OFFICIAL_STORE_IND
        defaultSuppliersShouldNotBeFound("officialStoreInd.notEquals=" + DEFAULT_OFFICIAL_STORE_IND);

        // Get all the suppliersList where officialStoreInd not equals to UPDATED_OFFICIAL_STORE_IND
        defaultSuppliersShouldBeFound("officialStoreInd.notEquals=" + UPDATED_OFFICIAL_STORE_IND);
    }

    @Test
    @Transactional
    public void getAllSuppliersByOfficialStoreIndIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where officialStoreInd in DEFAULT_OFFICIAL_STORE_IND or UPDATED_OFFICIAL_STORE_IND
        defaultSuppliersShouldBeFound("officialStoreInd.in=" + DEFAULT_OFFICIAL_STORE_IND + "," + UPDATED_OFFICIAL_STORE_IND);

        // Get all the suppliersList where officialStoreInd equals to UPDATED_OFFICIAL_STORE_IND
        defaultSuppliersShouldNotBeFound("officialStoreInd.in=" + UPDATED_OFFICIAL_STORE_IND);
    }

    @Test
    @Transactional
    public void getAllSuppliersByOfficialStoreIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where officialStoreInd is not null
        defaultSuppliersShouldBeFound("officialStoreInd.specified=true");

        // Get all the suppliersList where officialStoreInd is null
        defaultSuppliersShouldNotBeFound("officialStoreInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByStoreNameIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where storeName equals to DEFAULT_STORE_NAME
        defaultSuppliersShouldBeFound("storeName.equals=" + DEFAULT_STORE_NAME);

        // Get all the suppliersList where storeName equals to UPDATED_STORE_NAME
        defaultSuppliersShouldNotBeFound("storeName.equals=" + UPDATED_STORE_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersByStoreNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where storeName not equals to DEFAULT_STORE_NAME
        defaultSuppliersShouldNotBeFound("storeName.notEquals=" + DEFAULT_STORE_NAME);

        // Get all the suppliersList where storeName not equals to UPDATED_STORE_NAME
        defaultSuppliersShouldBeFound("storeName.notEquals=" + UPDATED_STORE_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersByStoreNameIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where storeName in DEFAULT_STORE_NAME or UPDATED_STORE_NAME
        defaultSuppliersShouldBeFound("storeName.in=" + DEFAULT_STORE_NAME + "," + UPDATED_STORE_NAME);

        // Get all the suppliersList where storeName equals to UPDATED_STORE_NAME
        defaultSuppliersShouldNotBeFound("storeName.in=" + UPDATED_STORE_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersByStoreNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where storeName is not null
        defaultSuppliersShouldBeFound("storeName.specified=true");

        // Get all the suppliersList where storeName is null
        defaultSuppliersShouldNotBeFound("storeName.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByStoreNameContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where storeName contains DEFAULT_STORE_NAME
        defaultSuppliersShouldBeFound("storeName.contains=" + DEFAULT_STORE_NAME);

        // Get all the suppliersList where storeName contains UPDATED_STORE_NAME
        defaultSuppliersShouldNotBeFound("storeName.contains=" + UPDATED_STORE_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersByStoreNameNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where storeName does not contain DEFAULT_STORE_NAME
        defaultSuppliersShouldNotBeFound("storeName.doesNotContain=" + DEFAULT_STORE_NAME);

        // Get all the suppliersList where storeName does not contain UPDATED_STORE_NAME
        defaultSuppliersShouldBeFound("storeName.doesNotContain=" + UPDATED_STORE_NAME);
    }


    @Test
    @Transactional
    public void getAllSuppliersByLogoIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where logo equals to DEFAULT_LOGO
        defaultSuppliersShouldBeFound("logo.equals=" + DEFAULT_LOGO);

        // Get all the suppliersList where logo equals to UPDATED_LOGO
        defaultSuppliersShouldNotBeFound("logo.equals=" + UPDATED_LOGO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByLogoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where logo not equals to DEFAULT_LOGO
        defaultSuppliersShouldNotBeFound("logo.notEquals=" + DEFAULT_LOGO);

        // Get all the suppliersList where logo not equals to UPDATED_LOGO
        defaultSuppliersShouldBeFound("logo.notEquals=" + UPDATED_LOGO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByLogoIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where logo in DEFAULT_LOGO or UPDATED_LOGO
        defaultSuppliersShouldBeFound("logo.in=" + DEFAULT_LOGO + "," + UPDATED_LOGO);

        // Get all the suppliersList where logo equals to UPDATED_LOGO
        defaultSuppliersShouldNotBeFound("logo.in=" + UPDATED_LOGO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByLogoIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where logo is not null
        defaultSuppliersShouldBeFound("logo.specified=true");

        // Get all the suppliersList where logo is null
        defaultSuppliersShouldNotBeFound("logo.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByLogoContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where logo contains DEFAULT_LOGO
        defaultSuppliersShouldBeFound("logo.contains=" + DEFAULT_LOGO);

        // Get all the suppliersList where logo contains UPDATED_LOGO
        defaultSuppliersShouldNotBeFound("logo.contains=" + UPDATED_LOGO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByLogoNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where logo does not contain DEFAULT_LOGO
        defaultSuppliersShouldNotBeFound("logo.doesNotContain=" + DEFAULT_LOGO);

        // Get all the suppliersList where logo does not contain UPDATED_LOGO
        defaultSuppliersShouldBeFound("logo.doesNotContain=" + UPDATED_LOGO);
    }


    @Test
    @Transactional
    public void getAllSuppliersByNricFrontPhotoIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where nricFrontPhoto equals to DEFAULT_NRIC_FRONT_PHOTO
        defaultSuppliersShouldBeFound("nricFrontPhoto.equals=" + DEFAULT_NRIC_FRONT_PHOTO);

        // Get all the suppliersList where nricFrontPhoto equals to UPDATED_NRIC_FRONT_PHOTO
        defaultSuppliersShouldNotBeFound("nricFrontPhoto.equals=" + UPDATED_NRIC_FRONT_PHOTO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByNricFrontPhotoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where nricFrontPhoto not equals to DEFAULT_NRIC_FRONT_PHOTO
        defaultSuppliersShouldNotBeFound("nricFrontPhoto.notEquals=" + DEFAULT_NRIC_FRONT_PHOTO);

        // Get all the suppliersList where nricFrontPhoto not equals to UPDATED_NRIC_FRONT_PHOTO
        defaultSuppliersShouldBeFound("nricFrontPhoto.notEquals=" + UPDATED_NRIC_FRONT_PHOTO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByNricFrontPhotoIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where nricFrontPhoto in DEFAULT_NRIC_FRONT_PHOTO or UPDATED_NRIC_FRONT_PHOTO
        defaultSuppliersShouldBeFound("nricFrontPhoto.in=" + DEFAULT_NRIC_FRONT_PHOTO + "," + UPDATED_NRIC_FRONT_PHOTO);

        // Get all the suppliersList where nricFrontPhoto equals to UPDATED_NRIC_FRONT_PHOTO
        defaultSuppliersShouldNotBeFound("nricFrontPhoto.in=" + UPDATED_NRIC_FRONT_PHOTO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByNricFrontPhotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where nricFrontPhoto is not null
        defaultSuppliersShouldBeFound("nricFrontPhoto.specified=true");

        // Get all the suppliersList where nricFrontPhoto is null
        defaultSuppliersShouldNotBeFound("nricFrontPhoto.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByNricFrontPhotoContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where nricFrontPhoto contains DEFAULT_NRIC_FRONT_PHOTO
        defaultSuppliersShouldBeFound("nricFrontPhoto.contains=" + DEFAULT_NRIC_FRONT_PHOTO);

        // Get all the suppliersList where nricFrontPhoto contains UPDATED_NRIC_FRONT_PHOTO
        defaultSuppliersShouldNotBeFound("nricFrontPhoto.contains=" + UPDATED_NRIC_FRONT_PHOTO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByNricFrontPhotoNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where nricFrontPhoto does not contain DEFAULT_NRIC_FRONT_PHOTO
        defaultSuppliersShouldNotBeFound("nricFrontPhoto.doesNotContain=" + DEFAULT_NRIC_FRONT_PHOTO);

        // Get all the suppliersList where nricFrontPhoto does not contain UPDATED_NRIC_FRONT_PHOTO
        defaultSuppliersShouldBeFound("nricFrontPhoto.doesNotContain=" + UPDATED_NRIC_FRONT_PHOTO);
    }


    @Test
    @Transactional
    public void getAllSuppliersByNricBackPhotoIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where nricBackPhoto equals to DEFAULT_NRIC_BACK_PHOTO
        defaultSuppliersShouldBeFound("nricBackPhoto.equals=" + DEFAULT_NRIC_BACK_PHOTO);

        // Get all the suppliersList where nricBackPhoto equals to UPDATED_NRIC_BACK_PHOTO
        defaultSuppliersShouldNotBeFound("nricBackPhoto.equals=" + UPDATED_NRIC_BACK_PHOTO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByNricBackPhotoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where nricBackPhoto not equals to DEFAULT_NRIC_BACK_PHOTO
        defaultSuppliersShouldNotBeFound("nricBackPhoto.notEquals=" + DEFAULT_NRIC_BACK_PHOTO);

        // Get all the suppliersList where nricBackPhoto not equals to UPDATED_NRIC_BACK_PHOTO
        defaultSuppliersShouldBeFound("nricBackPhoto.notEquals=" + UPDATED_NRIC_BACK_PHOTO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByNricBackPhotoIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where nricBackPhoto in DEFAULT_NRIC_BACK_PHOTO or UPDATED_NRIC_BACK_PHOTO
        defaultSuppliersShouldBeFound("nricBackPhoto.in=" + DEFAULT_NRIC_BACK_PHOTO + "," + UPDATED_NRIC_BACK_PHOTO);

        // Get all the suppliersList where nricBackPhoto equals to UPDATED_NRIC_BACK_PHOTO
        defaultSuppliersShouldNotBeFound("nricBackPhoto.in=" + UPDATED_NRIC_BACK_PHOTO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByNricBackPhotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where nricBackPhoto is not null
        defaultSuppliersShouldBeFound("nricBackPhoto.specified=true");

        // Get all the suppliersList where nricBackPhoto is null
        defaultSuppliersShouldNotBeFound("nricBackPhoto.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByNricBackPhotoContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where nricBackPhoto contains DEFAULT_NRIC_BACK_PHOTO
        defaultSuppliersShouldBeFound("nricBackPhoto.contains=" + DEFAULT_NRIC_BACK_PHOTO);

        // Get all the suppliersList where nricBackPhoto contains UPDATED_NRIC_BACK_PHOTO
        defaultSuppliersShouldNotBeFound("nricBackPhoto.contains=" + UPDATED_NRIC_BACK_PHOTO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByNricBackPhotoNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where nricBackPhoto does not contain DEFAULT_NRIC_BACK_PHOTO
        defaultSuppliersShouldNotBeFound("nricBackPhoto.doesNotContain=" + DEFAULT_NRIC_BACK_PHOTO);

        // Get all the suppliersList where nricBackPhoto does not contain UPDATED_NRIC_BACK_PHOTO
        defaultSuppliersShouldBeFound("nricBackPhoto.doesNotContain=" + UPDATED_NRIC_BACK_PHOTO);
    }


    @Test
    @Transactional
    public void getAllSuppliersByBankBookPhotoIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankBookPhoto equals to DEFAULT_BANK_BOOK_PHOTO
        defaultSuppliersShouldBeFound("bankBookPhoto.equals=" + DEFAULT_BANK_BOOK_PHOTO);

        // Get all the suppliersList where bankBookPhoto equals to UPDATED_BANK_BOOK_PHOTO
        defaultSuppliersShouldNotBeFound("bankBookPhoto.equals=" + UPDATED_BANK_BOOK_PHOTO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankBookPhotoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankBookPhoto not equals to DEFAULT_BANK_BOOK_PHOTO
        defaultSuppliersShouldNotBeFound("bankBookPhoto.notEquals=" + DEFAULT_BANK_BOOK_PHOTO);

        // Get all the suppliersList where bankBookPhoto not equals to UPDATED_BANK_BOOK_PHOTO
        defaultSuppliersShouldBeFound("bankBookPhoto.notEquals=" + UPDATED_BANK_BOOK_PHOTO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankBookPhotoIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankBookPhoto in DEFAULT_BANK_BOOK_PHOTO or UPDATED_BANK_BOOK_PHOTO
        defaultSuppliersShouldBeFound("bankBookPhoto.in=" + DEFAULT_BANK_BOOK_PHOTO + "," + UPDATED_BANK_BOOK_PHOTO);

        // Get all the suppliersList where bankBookPhoto equals to UPDATED_BANK_BOOK_PHOTO
        defaultSuppliersShouldNotBeFound("bankBookPhoto.in=" + UPDATED_BANK_BOOK_PHOTO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankBookPhotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankBookPhoto is not null
        defaultSuppliersShouldBeFound("bankBookPhoto.specified=true");

        // Get all the suppliersList where bankBookPhoto is null
        defaultSuppliersShouldNotBeFound("bankBookPhoto.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByBankBookPhotoContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankBookPhoto contains DEFAULT_BANK_BOOK_PHOTO
        defaultSuppliersShouldBeFound("bankBookPhoto.contains=" + DEFAULT_BANK_BOOK_PHOTO);

        // Get all the suppliersList where bankBookPhoto contains UPDATED_BANK_BOOK_PHOTO
        defaultSuppliersShouldNotBeFound("bankBookPhoto.contains=" + UPDATED_BANK_BOOK_PHOTO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankBookPhotoNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankBookPhoto does not contain DEFAULT_BANK_BOOK_PHOTO
        defaultSuppliersShouldNotBeFound("bankBookPhoto.doesNotContain=" + DEFAULT_BANK_BOOK_PHOTO);

        // Get all the suppliersList where bankBookPhoto does not contain UPDATED_BANK_BOOK_PHOTO
        defaultSuppliersShouldBeFound("bankBookPhoto.doesNotContain=" + UPDATED_BANK_BOOK_PHOTO);
    }


    @Test
    @Transactional
    public void getAllSuppliersByCompanyRegistrationPhotoIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where companyRegistrationPhoto equals to DEFAULT_COMPANY_REGISTRATION_PHOTO
        defaultSuppliersShouldBeFound("companyRegistrationPhoto.equals=" + DEFAULT_COMPANY_REGISTRATION_PHOTO);

        // Get all the suppliersList where companyRegistrationPhoto equals to UPDATED_COMPANY_REGISTRATION_PHOTO
        defaultSuppliersShouldNotBeFound("companyRegistrationPhoto.equals=" + UPDATED_COMPANY_REGISTRATION_PHOTO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByCompanyRegistrationPhotoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where companyRegistrationPhoto not equals to DEFAULT_COMPANY_REGISTRATION_PHOTO
        defaultSuppliersShouldNotBeFound("companyRegistrationPhoto.notEquals=" + DEFAULT_COMPANY_REGISTRATION_PHOTO);

        // Get all the suppliersList where companyRegistrationPhoto not equals to UPDATED_COMPANY_REGISTRATION_PHOTO
        defaultSuppliersShouldBeFound("companyRegistrationPhoto.notEquals=" + UPDATED_COMPANY_REGISTRATION_PHOTO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByCompanyRegistrationPhotoIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where companyRegistrationPhoto in DEFAULT_COMPANY_REGISTRATION_PHOTO or UPDATED_COMPANY_REGISTRATION_PHOTO
        defaultSuppliersShouldBeFound("companyRegistrationPhoto.in=" + DEFAULT_COMPANY_REGISTRATION_PHOTO + "," + UPDATED_COMPANY_REGISTRATION_PHOTO);

        // Get all the suppliersList where companyRegistrationPhoto equals to UPDATED_COMPANY_REGISTRATION_PHOTO
        defaultSuppliersShouldNotBeFound("companyRegistrationPhoto.in=" + UPDATED_COMPANY_REGISTRATION_PHOTO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByCompanyRegistrationPhotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where companyRegistrationPhoto is not null
        defaultSuppliersShouldBeFound("companyRegistrationPhoto.specified=true");

        // Get all the suppliersList where companyRegistrationPhoto is null
        defaultSuppliersShouldNotBeFound("companyRegistrationPhoto.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByCompanyRegistrationPhotoContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where companyRegistrationPhoto contains DEFAULT_COMPANY_REGISTRATION_PHOTO
        defaultSuppliersShouldBeFound("companyRegistrationPhoto.contains=" + DEFAULT_COMPANY_REGISTRATION_PHOTO);

        // Get all the suppliersList where companyRegistrationPhoto contains UPDATED_COMPANY_REGISTRATION_PHOTO
        defaultSuppliersShouldNotBeFound("companyRegistrationPhoto.contains=" + UPDATED_COMPANY_REGISTRATION_PHOTO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByCompanyRegistrationPhotoNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where companyRegistrationPhoto does not contain DEFAULT_COMPANY_REGISTRATION_PHOTO
        defaultSuppliersShouldNotBeFound("companyRegistrationPhoto.doesNotContain=" + DEFAULT_COMPANY_REGISTRATION_PHOTO);

        // Get all the suppliersList where companyRegistrationPhoto does not contain UPDATED_COMPANY_REGISTRATION_PHOTO
        defaultSuppliersShouldBeFound("companyRegistrationPhoto.doesNotContain=" + UPDATED_COMPANY_REGISTRATION_PHOTO);
    }


    @Test
    @Transactional
    public void getAllSuppliersByDistributorCertificatePhotoIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where distributorCertificatePhoto equals to DEFAULT_DISTRIBUTOR_CERTIFICATE_PHOTO
        defaultSuppliersShouldBeFound("distributorCertificatePhoto.equals=" + DEFAULT_DISTRIBUTOR_CERTIFICATE_PHOTO);

        // Get all the suppliersList where distributorCertificatePhoto equals to UPDATED_DISTRIBUTOR_CERTIFICATE_PHOTO
        defaultSuppliersShouldNotBeFound("distributorCertificatePhoto.equals=" + UPDATED_DISTRIBUTOR_CERTIFICATE_PHOTO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByDistributorCertificatePhotoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where distributorCertificatePhoto not equals to DEFAULT_DISTRIBUTOR_CERTIFICATE_PHOTO
        defaultSuppliersShouldNotBeFound("distributorCertificatePhoto.notEquals=" + DEFAULT_DISTRIBUTOR_CERTIFICATE_PHOTO);

        // Get all the suppliersList where distributorCertificatePhoto not equals to UPDATED_DISTRIBUTOR_CERTIFICATE_PHOTO
        defaultSuppliersShouldBeFound("distributorCertificatePhoto.notEquals=" + UPDATED_DISTRIBUTOR_CERTIFICATE_PHOTO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByDistributorCertificatePhotoIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where distributorCertificatePhoto in DEFAULT_DISTRIBUTOR_CERTIFICATE_PHOTO or UPDATED_DISTRIBUTOR_CERTIFICATE_PHOTO
        defaultSuppliersShouldBeFound("distributorCertificatePhoto.in=" + DEFAULT_DISTRIBUTOR_CERTIFICATE_PHOTO + "," + UPDATED_DISTRIBUTOR_CERTIFICATE_PHOTO);

        // Get all the suppliersList where distributorCertificatePhoto equals to UPDATED_DISTRIBUTOR_CERTIFICATE_PHOTO
        defaultSuppliersShouldNotBeFound("distributorCertificatePhoto.in=" + UPDATED_DISTRIBUTOR_CERTIFICATE_PHOTO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByDistributorCertificatePhotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where distributorCertificatePhoto is not null
        defaultSuppliersShouldBeFound("distributorCertificatePhoto.specified=true");

        // Get all the suppliersList where distributorCertificatePhoto is null
        defaultSuppliersShouldNotBeFound("distributorCertificatePhoto.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByDistributorCertificatePhotoContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where distributorCertificatePhoto contains DEFAULT_DISTRIBUTOR_CERTIFICATE_PHOTO
        defaultSuppliersShouldBeFound("distributorCertificatePhoto.contains=" + DEFAULT_DISTRIBUTOR_CERTIFICATE_PHOTO);

        // Get all the suppliersList where distributorCertificatePhoto contains UPDATED_DISTRIBUTOR_CERTIFICATE_PHOTO
        defaultSuppliersShouldNotBeFound("distributorCertificatePhoto.contains=" + UPDATED_DISTRIBUTOR_CERTIFICATE_PHOTO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByDistributorCertificatePhotoNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where distributorCertificatePhoto does not contain DEFAULT_DISTRIBUTOR_CERTIFICATE_PHOTO
        defaultSuppliersShouldNotBeFound("distributorCertificatePhoto.doesNotContain=" + DEFAULT_DISTRIBUTOR_CERTIFICATE_PHOTO);

        // Get all the suppliersList where distributorCertificatePhoto does not contain UPDATED_DISTRIBUTOR_CERTIFICATE_PHOTO
        defaultSuppliersShouldBeFound("distributorCertificatePhoto.doesNotContain=" + UPDATED_DISTRIBUTOR_CERTIFICATE_PHOTO);
    }


    @Test
    @Transactional
    public void getAllSuppliersByActiveFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where activeFlag equals to DEFAULT_ACTIVE_FLAG
        defaultSuppliersShouldBeFound("activeFlag.equals=" + DEFAULT_ACTIVE_FLAG);

        // Get all the suppliersList where activeFlag equals to UPDATED_ACTIVE_FLAG
        defaultSuppliersShouldNotBeFound("activeFlag.equals=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    public void getAllSuppliersByActiveFlagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where activeFlag not equals to DEFAULT_ACTIVE_FLAG
        defaultSuppliersShouldNotBeFound("activeFlag.notEquals=" + DEFAULT_ACTIVE_FLAG);

        // Get all the suppliersList where activeFlag not equals to UPDATED_ACTIVE_FLAG
        defaultSuppliersShouldBeFound("activeFlag.notEquals=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    public void getAllSuppliersByActiveFlagIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where activeFlag in DEFAULT_ACTIVE_FLAG or UPDATED_ACTIVE_FLAG
        defaultSuppliersShouldBeFound("activeFlag.in=" + DEFAULT_ACTIVE_FLAG + "," + UPDATED_ACTIVE_FLAG);

        // Get all the suppliersList where activeFlag equals to UPDATED_ACTIVE_FLAG
        defaultSuppliersShouldNotBeFound("activeFlag.in=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    public void getAllSuppliersByActiveFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where activeFlag is not null
        defaultSuppliersShouldBeFound("activeFlag.specified=true");

        // Get all the suppliersList where activeFlag is null
        defaultSuppliersShouldNotBeFound("activeFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validFrom equals to DEFAULT_VALID_FROM
        defaultSuppliersShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the suppliersList where validFrom equals to UPDATED_VALID_FROM
        defaultSuppliersShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validFrom not equals to DEFAULT_VALID_FROM
        defaultSuppliersShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the suppliersList where validFrom not equals to UPDATED_VALID_FROM
        defaultSuppliersShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultSuppliersShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the suppliersList where validFrom equals to UPDATED_VALID_FROM
        defaultSuppliersShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validFrom is not null
        defaultSuppliersShouldBeFound("validFrom.specified=true");

        // Get all the suppliersList where validFrom is null
        defaultSuppliersShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validTo equals to DEFAULT_VALID_TO
        defaultSuppliersShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the suppliersList where validTo equals to UPDATED_VALID_TO
        defaultSuppliersShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validTo not equals to DEFAULT_VALID_TO
        defaultSuppliersShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the suppliersList where validTo not equals to UPDATED_VALID_TO
        defaultSuppliersShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultSuppliersShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the suppliersList where validTo equals to UPDATED_VALID_TO
        defaultSuppliersShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validTo is not null
        defaultSuppliersShouldBeFound("validTo.specified=true");

        // Get all the suppliersList where validTo is null
        defaultSuppliersShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByBannerListIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);
        Photos bannerList = PhotosResourceIT.createEntity(em);
        em.persist(bannerList);
        em.flush();
        suppliers.addBannerList(bannerList);
        suppliersRepository.saveAndFlush(suppliers);
        Long bannerListId = bannerList.getId();

        // Get all the suppliersList where bannerList equals to bannerListId
        defaultSuppliersShouldBeFound("bannerListId.equals=" + bannerListId);

        // Get all the suppliersList where bannerList equals to bannerListId + 1
        defaultSuppliersShouldNotBeFound("bannerListId.equals=" + (bannerListId + 1));
    }


    @Test
    @Transactional
    public void getAllSuppliersByDocumentListIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);
        Photos documentList = PhotosResourceIT.createEntity(em);
        em.persist(documentList);
        em.flush();
        suppliers.addDocumentList(documentList);
        suppliersRepository.saveAndFlush(suppliers);
        Long documentListId = documentList.getId();

        // Get all the suppliersList where documentList equals to documentListId
        defaultSuppliersShouldBeFound("documentListId.equals=" + documentListId);

        // Get all the suppliersList where documentList equals to documentListId + 1
        defaultSuppliersShouldNotBeFound("documentListId.equals=" + (documentListId + 1));
    }


    @Test
    @Transactional
    public void getAllSuppliersBySupplierCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);
        SupplierCategories supplierCategory = SupplierCategoriesResourceIT.createEntity(em);
        em.persist(supplierCategory);
        em.flush();
        suppliers.setSupplierCategory(supplierCategory);
        suppliersRepository.saveAndFlush(suppliers);
        Long supplierCategoryId = supplierCategory.getId();

        // Get all the suppliersList where supplierCategory equals to supplierCategoryId
        defaultSuppliersShouldBeFound("supplierCategoryId.equals=" + supplierCategoryId);

        // Get all the suppliersList where supplierCategory equals to supplierCategoryId + 1
        defaultSuppliersShouldNotBeFound("supplierCategoryId.equals=" + (supplierCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllSuppliersByPickupAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);
        Addresses pickupAddress = AddressesResourceIT.createEntity(em);
        em.persist(pickupAddress);
        em.flush();
        suppliers.setPickupAddress(pickupAddress);
        suppliersRepository.saveAndFlush(suppliers);
        Long pickupAddressId = pickupAddress.getId();

        // Get all the suppliersList where pickupAddress equals to pickupAddressId
        defaultSuppliersShouldBeFound("pickupAddressId.equals=" + pickupAddressId);

        // Get all the suppliersList where pickupAddress equals to pickupAddressId + 1
        defaultSuppliersShouldNotBeFound("pickupAddressId.equals=" + (pickupAddressId + 1));
    }


    @Test
    @Transactional
    public void getAllSuppliersByHeadOfficeAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);
        Addresses headOfficeAddress = AddressesResourceIT.createEntity(em);
        em.persist(headOfficeAddress);
        em.flush();
        suppliers.setHeadOfficeAddress(headOfficeAddress);
        suppliersRepository.saveAndFlush(suppliers);
        Long headOfficeAddressId = headOfficeAddress.getId();

        // Get all the suppliersList where headOfficeAddress equals to headOfficeAddressId
        defaultSuppliersShouldBeFound("headOfficeAddressId.equals=" + headOfficeAddressId);

        // Get all the suppliersList where headOfficeAddress equals to headOfficeAddressId + 1
        defaultSuppliersShouldNotBeFound("headOfficeAddressId.equals=" + (headOfficeAddressId + 1));
    }


    @Test
    @Transactional
    public void getAllSuppliersByReturnAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);
        Addresses returnAddress = AddressesResourceIT.createEntity(em);
        em.persist(returnAddress);
        em.flush();
        suppliers.setReturnAddress(returnAddress);
        suppliersRepository.saveAndFlush(suppliers);
        Long returnAddressId = returnAddress.getId();

        // Get all the suppliersList where returnAddress equals to returnAddressId
        defaultSuppliersShouldBeFound("returnAddressId.equals=" + returnAddressId);

        // Get all the suppliersList where returnAddress equals to returnAddressId + 1
        defaultSuppliersShouldNotBeFound("returnAddressId.equals=" + (returnAddressId + 1));
    }


    @Test
    @Transactional
    public void getAllSuppliersByContactPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);
        People contactPerson = PeopleResourceIT.createEntity(em);
        em.persist(contactPerson);
        em.flush();
        suppliers.setContactPerson(contactPerson);
        suppliersRepository.saveAndFlush(suppliers);
        Long contactPersonId = contactPerson.getId();

        // Get all the suppliersList where contactPerson equals to contactPersonId
        defaultSuppliersShouldBeFound("contactPersonId.equals=" + contactPersonId);

        // Get all the suppliersList where contactPerson equals to contactPersonId + 1
        defaultSuppliersShouldNotBeFound("contactPersonId.equals=" + (contactPersonId + 1));
    }


    @Test
    @Transactional
    public void getAllSuppliersByDeliveryMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);
        DeliveryMethods deliveryMethod = DeliveryMethodsResourceIT.createEntity(em);
        em.persist(deliveryMethod);
        em.flush();
        suppliers.addDeliveryMethod(deliveryMethod);
        suppliersRepository.saveAndFlush(suppliers);
        Long deliveryMethodId = deliveryMethod.getId();

        // Get all the suppliersList where deliveryMethod equals to deliveryMethodId
        defaultSuppliersShouldBeFound("deliveryMethodId.equals=" + deliveryMethodId);

        // Get all the suppliersList where deliveryMethod equals to deliveryMethodId + 1
        defaultSuppliersShouldNotBeFound("deliveryMethodId.equals=" + (deliveryMethodId + 1));
    }


    @Test
    @Transactional
    public void getAllSuppliersByPeopleIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);
        People people = PeopleResourceIT.createEntity(em);
        em.persist(people);
        em.flush();
        suppliers.addPeople(people);
        suppliersRepository.saveAndFlush(suppliers);
        Long peopleId = people.getId();

        // Get all the suppliersList where people equals to peopleId
        defaultSuppliersShouldBeFound("peopleId.equals=" + peopleId);

        // Get all the suppliersList where people equals to peopleId + 1
        defaultSuppliersShouldNotBeFound("peopleId.equals=" + (peopleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSuppliersShouldBeFound(String filter) throws Exception {
        restSuppliersMockMvc.perform(get("/api/suppliers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suppliers.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].supplierReference").value(hasItem(DEFAULT_SUPPLIER_REFERENCE)))
            .andExpect(jsonPath("$.[*].bankAccountName").value(hasItem(DEFAULT_BANK_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].bankAccountBranch").value(hasItem(DEFAULT_BANK_ACCOUNT_BRANCH)))
            .andExpect(jsonPath("$.[*].bankAccountCode").value(hasItem(DEFAULT_BANK_ACCOUNT_CODE)))
            .andExpect(jsonPath("$.[*].bankAccountNumber").value(hasItem(DEFAULT_BANK_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].bankInternationalCode").value(hasItem(DEFAULT_BANK_INTERNATIONAL_CODE)))
            .andExpect(jsonPath("$.[*].paymentDays").value(hasItem(DEFAULT_PAYMENT_DAYS)))
            .andExpect(jsonPath("$.[*].internalComments").value(hasItem(DEFAULT_INTERNAL_COMMENTS)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].nric").value(hasItem(DEFAULT_NRIC)))
            .andExpect(jsonPath("$.[*].companyRegistrationNo").value(hasItem(DEFAULT_COMPANY_REGISTRATION_NO)))
            .andExpect(jsonPath("$.[*].faxNumber").value(hasItem(DEFAULT_FAX_NUMBER)))
            .andExpect(jsonPath("$.[*].websiteUrl").value(hasItem(DEFAULT_WEBSITE_URL)))
            .andExpect(jsonPath("$.[*].webServiceUrl").value(hasItem(DEFAULT_WEB_SERVICE_URL)))
            .andExpect(jsonPath("$.[*].creditRating").value(hasItem(DEFAULT_CREDIT_RATING)))
            .andExpect(jsonPath("$.[*].officialStoreInd").value(hasItem(DEFAULT_OFFICIAL_STORE_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].storeName").value(hasItem(DEFAULT_STORE_NAME)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.[*].nricFrontPhoto").value(hasItem(DEFAULT_NRIC_FRONT_PHOTO)))
            .andExpect(jsonPath("$.[*].nricBackPhoto").value(hasItem(DEFAULT_NRIC_BACK_PHOTO)))
            .andExpect(jsonPath("$.[*].bankBookPhoto").value(hasItem(DEFAULT_BANK_BOOK_PHOTO)))
            .andExpect(jsonPath("$.[*].companyRegistrationPhoto").value(hasItem(DEFAULT_COMPANY_REGISTRATION_PHOTO)))
            .andExpect(jsonPath("$.[*].distributorCertificatePhoto").value(hasItem(DEFAULT_DISTRIBUTOR_CERTIFICATE_PHOTO)))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restSuppliersMockMvc.perform(get("/api/suppliers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSuppliersShouldNotBeFound(String filter) throws Exception {
        restSuppliersMockMvc.perform(get("/api/suppliers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSuppliersMockMvc.perform(get("/api/suppliers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSuppliers() throws Exception {
        // Get the suppliers
        restSuppliersMockMvc.perform(get("/api/suppliers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSuppliers() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        int databaseSizeBeforeUpdate = suppliersRepository.findAll().size();

        // Update the suppliers
        Suppliers updatedSuppliers = suppliersRepository.findById(suppliers.getId()).get();
        // Disconnect from session so that the updates on updatedSuppliers are not directly saved in db
        em.detach(updatedSuppliers);
        updatedSuppliers
            .name(UPDATED_NAME)
            .supplierReference(UPDATED_SUPPLIER_REFERENCE)
            .bankAccountName(UPDATED_BANK_ACCOUNT_NAME)
            .bankAccountBranch(UPDATED_BANK_ACCOUNT_BRANCH)
            .bankAccountCode(UPDATED_BANK_ACCOUNT_CODE)
            .bankAccountNumber(UPDATED_BANK_ACCOUNT_NUMBER)
            .bankInternationalCode(UPDATED_BANK_INTERNATIONAL_CODE)
            .paymentDays(UPDATED_PAYMENT_DAYS)
            .internalComments(UPDATED_INTERNAL_COMMENTS)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .nric(UPDATED_NRIC)
            .companyRegistrationNo(UPDATED_COMPANY_REGISTRATION_NO)
            .faxNumber(UPDATED_FAX_NUMBER)
            .websiteUrl(UPDATED_WEBSITE_URL)
            .webServiceUrl(UPDATED_WEB_SERVICE_URL)
            .creditRating(UPDATED_CREDIT_RATING)
            .officialStoreInd(UPDATED_OFFICIAL_STORE_IND)
            .storeName(UPDATED_STORE_NAME)
            .logo(UPDATED_LOGO)
            .nricFrontPhoto(UPDATED_NRIC_FRONT_PHOTO)
            .nricBackPhoto(UPDATED_NRIC_BACK_PHOTO)
            .bankBookPhoto(UPDATED_BANK_BOOK_PHOTO)
            .companyRegistrationPhoto(UPDATED_COMPANY_REGISTRATION_PHOTO)
            .distributorCertificatePhoto(UPDATED_DISTRIBUTOR_CERTIFICATE_PHOTO)
            .activeFlag(UPDATED_ACTIVE_FLAG)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(updatedSuppliers);

        restSuppliersMockMvc.perform(put("/api/suppliers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isOk());

        // Validate the Suppliers in the database
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeUpdate);
        Suppliers testSuppliers = suppliersList.get(suppliersList.size() - 1);
        assertThat(testSuppliers.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSuppliers.getSupplierReference()).isEqualTo(UPDATED_SUPPLIER_REFERENCE);
        assertThat(testSuppliers.getBankAccountName()).isEqualTo(UPDATED_BANK_ACCOUNT_NAME);
        assertThat(testSuppliers.getBankAccountBranch()).isEqualTo(UPDATED_BANK_ACCOUNT_BRANCH);
        assertThat(testSuppliers.getBankAccountCode()).isEqualTo(UPDATED_BANK_ACCOUNT_CODE);
        assertThat(testSuppliers.getBankAccountNumber()).isEqualTo(UPDATED_BANK_ACCOUNT_NUMBER);
        assertThat(testSuppliers.getBankInternationalCode()).isEqualTo(UPDATED_BANK_INTERNATIONAL_CODE);
        assertThat(testSuppliers.getPaymentDays()).isEqualTo(UPDATED_PAYMENT_DAYS);
        assertThat(testSuppliers.getInternalComments()).isEqualTo(UPDATED_INTERNAL_COMMENTS);
        assertThat(testSuppliers.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testSuppliers.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testSuppliers.getNric()).isEqualTo(UPDATED_NRIC);
        assertThat(testSuppliers.getCompanyRegistrationNo()).isEqualTo(UPDATED_COMPANY_REGISTRATION_NO);
        assertThat(testSuppliers.getFaxNumber()).isEqualTo(UPDATED_FAX_NUMBER);
        assertThat(testSuppliers.getWebsiteUrl()).isEqualTo(UPDATED_WEBSITE_URL);
        assertThat(testSuppliers.getWebServiceUrl()).isEqualTo(UPDATED_WEB_SERVICE_URL);
        assertThat(testSuppliers.getCreditRating()).isEqualTo(UPDATED_CREDIT_RATING);
        assertThat(testSuppliers.isOfficialStoreInd()).isEqualTo(UPDATED_OFFICIAL_STORE_IND);
        assertThat(testSuppliers.getStoreName()).isEqualTo(UPDATED_STORE_NAME);
        assertThat(testSuppliers.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testSuppliers.getNricFrontPhoto()).isEqualTo(UPDATED_NRIC_FRONT_PHOTO);
        assertThat(testSuppliers.getNricBackPhoto()).isEqualTo(UPDATED_NRIC_BACK_PHOTO);
        assertThat(testSuppliers.getBankBookPhoto()).isEqualTo(UPDATED_BANK_BOOK_PHOTO);
        assertThat(testSuppliers.getCompanyRegistrationPhoto()).isEqualTo(UPDATED_COMPANY_REGISTRATION_PHOTO);
        assertThat(testSuppliers.getDistributorCertificatePhoto()).isEqualTo(UPDATED_DISTRIBUTOR_CERTIFICATE_PHOTO);
        assertThat(testSuppliers.isActiveFlag()).isEqualTo(UPDATED_ACTIVE_FLAG);
        assertThat(testSuppliers.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testSuppliers.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingSuppliers() throws Exception {
        int databaseSizeBeforeUpdate = suppliersRepository.findAll().size();

        // Create the Suppliers
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSuppliersMockMvc.perform(put("/api/suppliers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Suppliers in the database
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSuppliers() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        int databaseSizeBeforeDelete = suppliersRepository.findAll().size();

        // Delete the suppliers
        restSuppliersMockMvc.perform(delete("/api/suppliers/{id}", suppliers.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

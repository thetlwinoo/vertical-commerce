package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.PaymentMethods;
import com.vertical.commerce.repository.PaymentMethodsRepository;
import com.vertical.commerce.service.PaymentMethodsService;
import com.vertical.commerce.service.dto.PaymentMethodsDTO;
import com.vertical.commerce.service.mapper.PaymentMethodsMapper;
import com.vertical.commerce.service.dto.PaymentMethodsCriteria;
import com.vertical.commerce.service.PaymentMethodsQueryService;

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
 * Integration tests for the {@link PaymentMethodsResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PaymentMethodsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISABLED = false;
    private static final Boolean UPDATED_DISABLED = true;

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;
    private static final Integer SMALLER_SORT_ORDER = 1 - 1;

    private static final String DEFAULT_ICON_FONT = "AAAAAAAAAA";
    private static final String UPDATED_ICON_FONT = "BBBBBBBBBB";

    private static final String DEFAULT_ICON_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_ICON_PHOTO = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PaymentMethodsRepository paymentMethodsRepository;

    @Autowired
    private PaymentMethodsMapper paymentMethodsMapper;

    @Autowired
    private PaymentMethodsService paymentMethodsService;

    @Autowired
    private PaymentMethodsQueryService paymentMethodsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentMethodsMockMvc;

    private PaymentMethods paymentMethods;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentMethods createEntity(EntityManager em) {
        PaymentMethods paymentMethods = new PaymentMethods()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .disabled(DEFAULT_DISABLED)
            .sortOrder(DEFAULT_SORT_ORDER)
            .iconFont(DEFAULT_ICON_FONT)
            .iconPhoto(DEFAULT_ICON_PHOTO)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return paymentMethods;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentMethods createUpdatedEntity(EntityManager em) {
        PaymentMethods paymentMethods = new PaymentMethods()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .disabled(UPDATED_DISABLED)
            .sortOrder(UPDATED_SORT_ORDER)
            .iconFont(UPDATED_ICON_FONT)
            .iconPhoto(UPDATED_ICON_PHOTO)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return paymentMethods;
    }

    @BeforeEach
    public void initTest() {
        paymentMethods = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentMethods() throws Exception {
        int databaseSizeBeforeCreate = paymentMethodsRepository.findAll().size();
        // Create the PaymentMethods
        PaymentMethodsDTO paymentMethodsDTO = paymentMethodsMapper.toDto(paymentMethods);
        restPaymentMethodsMockMvc.perform(post("/api/payment-methods").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodsDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentMethods in the database
        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentMethods testPaymentMethods = paymentMethodsList.get(paymentMethodsList.size() - 1);
        assertThat(testPaymentMethods.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPaymentMethods.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPaymentMethods.isDisabled()).isEqualTo(DEFAULT_DISABLED);
        assertThat(testPaymentMethods.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testPaymentMethods.getIconFont()).isEqualTo(DEFAULT_ICON_FONT);
        assertThat(testPaymentMethods.getIconPhoto()).isEqualTo(DEFAULT_ICON_PHOTO);
        assertThat(testPaymentMethods.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testPaymentMethods.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createPaymentMethodsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentMethodsRepository.findAll().size();

        // Create the PaymentMethods with an existing ID
        paymentMethods.setId(1L);
        PaymentMethodsDTO paymentMethodsDTO = paymentMethodsMapper.toDto(paymentMethods);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentMethodsMockMvc.perform(post("/api/payment-methods").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethods in the database
        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentMethodsRepository.findAll().size();
        // set the field null
        paymentMethods.setValidFrom(null);

        // Create the PaymentMethods, which fails.
        PaymentMethodsDTO paymentMethodsDTO = paymentMethodsMapper.toDto(paymentMethods);


        restPaymentMethodsMockMvc.perform(post("/api/payment-methods").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodsDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaymentMethods() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList
        restPaymentMethodsMockMvc.perform(get("/api/payment-methods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentMethods.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].disabled").value(hasItem(DEFAULT_DISABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].iconFont").value(hasItem(DEFAULT_ICON_FONT)))
            .andExpect(jsonPath("$.[*].iconPhoto").value(hasItem(DEFAULT_ICON_PHOTO)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getPaymentMethods() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get the paymentMethods
        restPaymentMethodsMockMvc.perform(get("/api/payment-methods/{id}", paymentMethods.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentMethods.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.disabled").value(DEFAULT_DISABLED.booleanValue()))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.iconFont").value(DEFAULT_ICON_FONT))
            .andExpect(jsonPath("$.iconPhoto").value(DEFAULT_ICON_PHOTO))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getPaymentMethodsByIdFiltering() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        Long id = paymentMethods.getId();

        defaultPaymentMethodsShouldBeFound("id.equals=" + id);
        defaultPaymentMethodsShouldNotBeFound("id.notEquals=" + id);

        defaultPaymentMethodsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaymentMethodsShouldNotBeFound("id.greaterThan=" + id);

        defaultPaymentMethodsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaymentMethodsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPaymentMethodsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where name equals to DEFAULT_NAME
        defaultPaymentMethodsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the paymentMethodsList where name equals to UPDATED_NAME
        defaultPaymentMethodsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where name not equals to DEFAULT_NAME
        defaultPaymentMethodsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the paymentMethodsList where name not equals to UPDATED_NAME
        defaultPaymentMethodsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPaymentMethodsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the paymentMethodsList where name equals to UPDATED_NAME
        defaultPaymentMethodsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where name is not null
        defaultPaymentMethodsShouldBeFound("name.specified=true");

        // Get all the paymentMethodsList where name is null
        defaultPaymentMethodsShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentMethodsByNameContainsSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where name contains DEFAULT_NAME
        defaultPaymentMethodsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the paymentMethodsList where name contains UPDATED_NAME
        defaultPaymentMethodsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where name does not contain DEFAULT_NAME
        defaultPaymentMethodsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the paymentMethodsList where name does not contain UPDATED_NAME
        defaultPaymentMethodsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPaymentMethodsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where code equals to DEFAULT_CODE
        defaultPaymentMethodsShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the paymentMethodsList where code equals to UPDATED_CODE
        defaultPaymentMethodsShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where code not equals to DEFAULT_CODE
        defaultPaymentMethodsShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the paymentMethodsList where code not equals to UPDATED_CODE
        defaultPaymentMethodsShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where code in DEFAULT_CODE or UPDATED_CODE
        defaultPaymentMethodsShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the paymentMethodsList where code equals to UPDATED_CODE
        defaultPaymentMethodsShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where code is not null
        defaultPaymentMethodsShouldBeFound("code.specified=true");

        // Get all the paymentMethodsList where code is null
        defaultPaymentMethodsShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentMethodsByCodeContainsSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where code contains DEFAULT_CODE
        defaultPaymentMethodsShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the paymentMethodsList where code contains UPDATED_CODE
        defaultPaymentMethodsShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where code does not contain DEFAULT_CODE
        defaultPaymentMethodsShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the paymentMethodsList where code does not contain UPDATED_CODE
        defaultPaymentMethodsShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllPaymentMethodsByDisabledIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where disabled equals to DEFAULT_DISABLED
        defaultPaymentMethodsShouldBeFound("disabled.equals=" + DEFAULT_DISABLED);

        // Get all the paymentMethodsList where disabled equals to UPDATED_DISABLED
        defaultPaymentMethodsShouldNotBeFound("disabled.equals=" + UPDATED_DISABLED);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByDisabledIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where disabled not equals to DEFAULT_DISABLED
        defaultPaymentMethodsShouldNotBeFound("disabled.notEquals=" + DEFAULT_DISABLED);

        // Get all the paymentMethodsList where disabled not equals to UPDATED_DISABLED
        defaultPaymentMethodsShouldBeFound("disabled.notEquals=" + UPDATED_DISABLED);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByDisabledIsInShouldWork() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where disabled in DEFAULT_DISABLED or UPDATED_DISABLED
        defaultPaymentMethodsShouldBeFound("disabled.in=" + DEFAULT_DISABLED + "," + UPDATED_DISABLED);

        // Get all the paymentMethodsList where disabled equals to UPDATED_DISABLED
        defaultPaymentMethodsShouldNotBeFound("disabled.in=" + UPDATED_DISABLED);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByDisabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where disabled is not null
        defaultPaymentMethodsShouldBeFound("disabled.specified=true");

        // Get all the paymentMethodsList where disabled is null
        defaultPaymentMethodsShouldNotBeFound("disabled.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsBySortOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where sortOrder equals to DEFAULT_SORT_ORDER
        defaultPaymentMethodsShouldBeFound("sortOrder.equals=" + DEFAULT_SORT_ORDER);

        // Get all the paymentMethodsList where sortOrder equals to UPDATED_SORT_ORDER
        defaultPaymentMethodsShouldNotBeFound("sortOrder.equals=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsBySortOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where sortOrder not equals to DEFAULT_SORT_ORDER
        defaultPaymentMethodsShouldNotBeFound("sortOrder.notEquals=" + DEFAULT_SORT_ORDER);

        // Get all the paymentMethodsList where sortOrder not equals to UPDATED_SORT_ORDER
        defaultPaymentMethodsShouldBeFound("sortOrder.notEquals=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsBySortOrderIsInShouldWork() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where sortOrder in DEFAULT_SORT_ORDER or UPDATED_SORT_ORDER
        defaultPaymentMethodsShouldBeFound("sortOrder.in=" + DEFAULT_SORT_ORDER + "," + UPDATED_SORT_ORDER);

        // Get all the paymentMethodsList where sortOrder equals to UPDATED_SORT_ORDER
        defaultPaymentMethodsShouldNotBeFound("sortOrder.in=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsBySortOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where sortOrder is not null
        defaultPaymentMethodsShouldBeFound("sortOrder.specified=true");

        // Get all the paymentMethodsList where sortOrder is null
        defaultPaymentMethodsShouldNotBeFound("sortOrder.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsBySortOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where sortOrder is greater than or equal to DEFAULT_SORT_ORDER
        defaultPaymentMethodsShouldBeFound("sortOrder.greaterThanOrEqual=" + DEFAULT_SORT_ORDER);

        // Get all the paymentMethodsList where sortOrder is greater than or equal to UPDATED_SORT_ORDER
        defaultPaymentMethodsShouldNotBeFound("sortOrder.greaterThanOrEqual=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsBySortOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where sortOrder is less than or equal to DEFAULT_SORT_ORDER
        defaultPaymentMethodsShouldBeFound("sortOrder.lessThanOrEqual=" + DEFAULT_SORT_ORDER);

        // Get all the paymentMethodsList where sortOrder is less than or equal to SMALLER_SORT_ORDER
        defaultPaymentMethodsShouldNotBeFound("sortOrder.lessThanOrEqual=" + SMALLER_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsBySortOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where sortOrder is less than DEFAULT_SORT_ORDER
        defaultPaymentMethodsShouldNotBeFound("sortOrder.lessThan=" + DEFAULT_SORT_ORDER);

        // Get all the paymentMethodsList where sortOrder is less than UPDATED_SORT_ORDER
        defaultPaymentMethodsShouldBeFound("sortOrder.lessThan=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsBySortOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where sortOrder is greater than DEFAULT_SORT_ORDER
        defaultPaymentMethodsShouldNotBeFound("sortOrder.greaterThan=" + DEFAULT_SORT_ORDER);

        // Get all the paymentMethodsList where sortOrder is greater than SMALLER_SORT_ORDER
        defaultPaymentMethodsShouldBeFound("sortOrder.greaterThan=" + SMALLER_SORT_ORDER);
    }


    @Test
    @Transactional
    public void getAllPaymentMethodsByIconFontIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where iconFont equals to DEFAULT_ICON_FONT
        defaultPaymentMethodsShouldBeFound("iconFont.equals=" + DEFAULT_ICON_FONT);

        // Get all the paymentMethodsList where iconFont equals to UPDATED_ICON_FONT
        defaultPaymentMethodsShouldNotBeFound("iconFont.equals=" + UPDATED_ICON_FONT);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByIconFontIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where iconFont not equals to DEFAULT_ICON_FONT
        defaultPaymentMethodsShouldNotBeFound("iconFont.notEquals=" + DEFAULT_ICON_FONT);

        // Get all the paymentMethodsList where iconFont not equals to UPDATED_ICON_FONT
        defaultPaymentMethodsShouldBeFound("iconFont.notEquals=" + UPDATED_ICON_FONT);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByIconFontIsInShouldWork() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where iconFont in DEFAULT_ICON_FONT or UPDATED_ICON_FONT
        defaultPaymentMethodsShouldBeFound("iconFont.in=" + DEFAULT_ICON_FONT + "," + UPDATED_ICON_FONT);

        // Get all the paymentMethodsList where iconFont equals to UPDATED_ICON_FONT
        defaultPaymentMethodsShouldNotBeFound("iconFont.in=" + UPDATED_ICON_FONT);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByIconFontIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where iconFont is not null
        defaultPaymentMethodsShouldBeFound("iconFont.specified=true");

        // Get all the paymentMethodsList where iconFont is null
        defaultPaymentMethodsShouldNotBeFound("iconFont.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentMethodsByIconFontContainsSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where iconFont contains DEFAULT_ICON_FONT
        defaultPaymentMethodsShouldBeFound("iconFont.contains=" + DEFAULT_ICON_FONT);

        // Get all the paymentMethodsList where iconFont contains UPDATED_ICON_FONT
        defaultPaymentMethodsShouldNotBeFound("iconFont.contains=" + UPDATED_ICON_FONT);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByIconFontNotContainsSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where iconFont does not contain DEFAULT_ICON_FONT
        defaultPaymentMethodsShouldNotBeFound("iconFont.doesNotContain=" + DEFAULT_ICON_FONT);

        // Get all the paymentMethodsList where iconFont does not contain UPDATED_ICON_FONT
        defaultPaymentMethodsShouldBeFound("iconFont.doesNotContain=" + UPDATED_ICON_FONT);
    }


    @Test
    @Transactional
    public void getAllPaymentMethodsByIconPhotoIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where iconPhoto equals to DEFAULT_ICON_PHOTO
        defaultPaymentMethodsShouldBeFound("iconPhoto.equals=" + DEFAULT_ICON_PHOTO);

        // Get all the paymentMethodsList where iconPhoto equals to UPDATED_ICON_PHOTO
        defaultPaymentMethodsShouldNotBeFound("iconPhoto.equals=" + UPDATED_ICON_PHOTO);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByIconPhotoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where iconPhoto not equals to DEFAULT_ICON_PHOTO
        defaultPaymentMethodsShouldNotBeFound("iconPhoto.notEquals=" + DEFAULT_ICON_PHOTO);

        // Get all the paymentMethodsList where iconPhoto not equals to UPDATED_ICON_PHOTO
        defaultPaymentMethodsShouldBeFound("iconPhoto.notEquals=" + UPDATED_ICON_PHOTO);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByIconPhotoIsInShouldWork() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where iconPhoto in DEFAULT_ICON_PHOTO or UPDATED_ICON_PHOTO
        defaultPaymentMethodsShouldBeFound("iconPhoto.in=" + DEFAULT_ICON_PHOTO + "," + UPDATED_ICON_PHOTO);

        // Get all the paymentMethodsList where iconPhoto equals to UPDATED_ICON_PHOTO
        defaultPaymentMethodsShouldNotBeFound("iconPhoto.in=" + UPDATED_ICON_PHOTO);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByIconPhotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where iconPhoto is not null
        defaultPaymentMethodsShouldBeFound("iconPhoto.specified=true");

        // Get all the paymentMethodsList where iconPhoto is null
        defaultPaymentMethodsShouldNotBeFound("iconPhoto.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentMethodsByIconPhotoContainsSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where iconPhoto contains DEFAULT_ICON_PHOTO
        defaultPaymentMethodsShouldBeFound("iconPhoto.contains=" + DEFAULT_ICON_PHOTO);

        // Get all the paymentMethodsList where iconPhoto contains UPDATED_ICON_PHOTO
        defaultPaymentMethodsShouldNotBeFound("iconPhoto.contains=" + UPDATED_ICON_PHOTO);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByIconPhotoNotContainsSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where iconPhoto does not contain DEFAULT_ICON_PHOTO
        defaultPaymentMethodsShouldNotBeFound("iconPhoto.doesNotContain=" + DEFAULT_ICON_PHOTO);

        // Get all the paymentMethodsList where iconPhoto does not contain UPDATED_ICON_PHOTO
        defaultPaymentMethodsShouldBeFound("iconPhoto.doesNotContain=" + UPDATED_ICON_PHOTO);
    }


    @Test
    @Transactional
    public void getAllPaymentMethodsByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validFrom equals to DEFAULT_VALID_FROM
        defaultPaymentMethodsShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the paymentMethodsList where validFrom equals to UPDATED_VALID_FROM
        defaultPaymentMethodsShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validFrom not equals to DEFAULT_VALID_FROM
        defaultPaymentMethodsShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the paymentMethodsList where validFrom not equals to UPDATED_VALID_FROM
        defaultPaymentMethodsShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultPaymentMethodsShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the paymentMethodsList where validFrom equals to UPDATED_VALID_FROM
        defaultPaymentMethodsShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validFrom is not null
        defaultPaymentMethodsShouldBeFound("validFrom.specified=true");

        // Get all the paymentMethodsList where validFrom is null
        defaultPaymentMethodsShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validTo equals to DEFAULT_VALID_TO
        defaultPaymentMethodsShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the paymentMethodsList where validTo equals to UPDATED_VALID_TO
        defaultPaymentMethodsShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validTo not equals to DEFAULT_VALID_TO
        defaultPaymentMethodsShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the paymentMethodsList where validTo not equals to UPDATED_VALID_TO
        defaultPaymentMethodsShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultPaymentMethodsShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the paymentMethodsList where validTo equals to UPDATED_VALID_TO
        defaultPaymentMethodsShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validTo is not null
        defaultPaymentMethodsShouldBeFound("validTo.specified=true");

        // Get all the paymentMethodsList where validTo is null
        defaultPaymentMethodsShouldNotBeFound("validTo.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaymentMethodsShouldBeFound(String filter) throws Exception {
        restPaymentMethodsMockMvc.perform(get("/api/payment-methods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentMethods.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].disabled").value(hasItem(DEFAULT_DISABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].iconFont").value(hasItem(DEFAULT_ICON_FONT)))
            .andExpect(jsonPath("$.[*].iconPhoto").value(hasItem(DEFAULT_ICON_PHOTO)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restPaymentMethodsMockMvc.perform(get("/api/payment-methods/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaymentMethodsShouldNotBeFound(String filter) throws Exception {
        restPaymentMethodsMockMvc.perform(get("/api/payment-methods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaymentMethodsMockMvc.perform(get("/api/payment-methods/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentMethods() throws Exception {
        // Get the paymentMethods
        restPaymentMethodsMockMvc.perform(get("/api/payment-methods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentMethods() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        int databaseSizeBeforeUpdate = paymentMethodsRepository.findAll().size();

        // Update the paymentMethods
        PaymentMethods updatedPaymentMethods = paymentMethodsRepository.findById(paymentMethods.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentMethods are not directly saved in db
        em.detach(updatedPaymentMethods);
        updatedPaymentMethods
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .disabled(UPDATED_DISABLED)
            .sortOrder(UPDATED_SORT_ORDER)
            .iconFont(UPDATED_ICON_FONT)
            .iconPhoto(UPDATED_ICON_PHOTO)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        PaymentMethodsDTO paymentMethodsDTO = paymentMethodsMapper.toDto(updatedPaymentMethods);

        restPaymentMethodsMockMvc.perform(put("/api/payment-methods").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodsDTO)))
            .andExpect(status().isOk());

        // Validate the PaymentMethods in the database
        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeUpdate);
        PaymentMethods testPaymentMethods = paymentMethodsList.get(paymentMethodsList.size() - 1);
        assertThat(testPaymentMethods.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaymentMethods.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPaymentMethods.isDisabled()).isEqualTo(UPDATED_DISABLED);
        assertThat(testPaymentMethods.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testPaymentMethods.getIconFont()).isEqualTo(UPDATED_ICON_FONT);
        assertThat(testPaymentMethods.getIconPhoto()).isEqualTo(UPDATED_ICON_PHOTO);
        assertThat(testPaymentMethods.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testPaymentMethods.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentMethods() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodsRepository.findAll().size();

        // Create the PaymentMethods
        PaymentMethodsDTO paymentMethodsDTO = paymentMethodsMapper.toDto(paymentMethods);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMethodsMockMvc.perform(put("/api/payment-methods").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethods in the database
        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaymentMethods() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        int databaseSizeBeforeDelete = paymentMethodsRepository.findAll().size();

        // Delete the paymentMethods
        restPaymentMethodsMockMvc.perform(delete("/api/payment-methods/{id}", paymentMethods.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

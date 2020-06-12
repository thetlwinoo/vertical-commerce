package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.PaymentMethods;
import com.vertical.commerce.domain.Photos;
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

    private static final Boolean DEFAULT_ACTIVE_IND = false;
    private static final Boolean UPDATED_ACTIVE_IND = true;

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;
    private static final Integer SMALLER_SORT_ORDER = 1 - 1;

    private static final String DEFAULT_ICON_FONT = "AAAAAAAAAA";
    private static final String UPDATED_ICON_FONT = "BBBBBBBBBB";

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
            .activeInd(DEFAULT_ACTIVE_IND)
            .sortOrder(DEFAULT_SORT_ORDER)
            .iconFont(DEFAULT_ICON_FONT);
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
            .activeInd(UPDATED_ACTIVE_IND)
            .sortOrder(UPDATED_SORT_ORDER)
            .iconFont(UPDATED_ICON_FONT);
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
        assertThat(testPaymentMethods.isActiveInd()).isEqualTo(DEFAULT_ACTIVE_IND);
        assertThat(testPaymentMethods.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testPaymentMethods.getIconFont()).isEqualTo(DEFAULT_ICON_FONT);
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
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].iconFont").value(hasItem(DEFAULT_ICON_FONT)));
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
            .andExpect(jsonPath("$.activeInd").value(DEFAULT_ACTIVE_IND.booleanValue()))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.iconFont").value(DEFAULT_ICON_FONT));
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
    public void getAllPaymentMethodsByActiveIndIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where activeInd equals to DEFAULT_ACTIVE_IND
        defaultPaymentMethodsShouldBeFound("activeInd.equals=" + DEFAULT_ACTIVE_IND);

        // Get all the paymentMethodsList where activeInd equals to UPDATED_ACTIVE_IND
        defaultPaymentMethodsShouldNotBeFound("activeInd.equals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByActiveIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where activeInd not equals to DEFAULT_ACTIVE_IND
        defaultPaymentMethodsShouldNotBeFound("activeInd.notEquals=" + DEFAULT_ACTIVE_IND);

        // Get all the paymentMethodsList where activeInd not equals to UPDATED_ACTIVE_IND
        defaultPaymentMethodsShouldBeFound("activeInd.notEquals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByActiveIndIsInShouldWork() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where activeInd in DEFAULT_ACTIVE_IND or UPDATED_ACTIVE_IND
        defaultPaymentMethodsShouldBeFound("activeInd.in=" + DEFAULT_ACTIVE_IND + "," + UPDATED_ACTIVE_IND);

        // Get all the paymentMethodsList where activeInd equals to UPDATED_ACTIVE_IND
        defaultPaymentMethodsShouldNotBeFound("activeInd.in=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByActiveIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where activeInd is not null
        defaultPaymentMethodsShouldBeFound("activeInd.specified=true");

        // Get all the paymentMethodsList where activeInd is null
        defaultPaymentMethodsShouldNotBeFound("activeInd.specified=false");
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
    public void getAllPaymentMethodsByIconIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);
        Photos icon = PhotosResourceIT.createEntity(em);
        em.persist(icon);
        em.flush();
        paymentMethods.setIcon(icon);
        paymentMethodsRepository.saveAndFlush(paymentMethods);
        Long iconId = icon.getId();

        // Get all the paymentMethodsList where icon equals to iconId
        defaultPaymentMethodsShouldBeFound("iconId.equals=" + iconId);

        // Get all the paymentMethodsList where icon equals to iconId + 1
        defaultPaymentMethodsShouldNotBeFound("iconId.equals=" + (iconId + 1));
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
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].iconFont").value(hasItem(DEFAULT_ICON_FONT)));

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
            .activeInd(UPDATED_ACTIVE_IND)
            .sortOrder(UPDATED_SORT_ORDER)
            .iconFont(UPDATED_ICON_FONT);
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
        assertThat(testPaymentMethods.isActiveInd()).isEqualTo(UPDATED_ACTIVE_IND);
        assertThat(testPaymentMethods.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testPaymentMethods.getIconFont()).isEqualTo(UPDATED_ICON_FONT);
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

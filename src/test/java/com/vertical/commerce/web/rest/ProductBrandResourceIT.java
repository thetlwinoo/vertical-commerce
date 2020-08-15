package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.ProductBrand;
import com.vertical.commerce.repository.ProductBrandRepository;
import com.vertical.commerce.service.ProductBrandService;
import com.vertical.commerce.service.dto.ProductBrandDTO;
import com.vertical.commerce.service.mapper.ProductBrandMapper;
import com.vertical.commerce.service.dto.ProductBrandCriteria;
import com.vertical.commerce.service.ProductBrandQueryService;

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
 * Integration tests for the {@link ProductBrandResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ProductBrandResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HANDLE = "AAAAAAAAAA";
    private static final String UPDATED_HANDLE = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_LABEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;
    private static final Integer SMALLER_SORT_ORDER = 1 - 1;

    private static final String DEFAULT_ICON_FONT = "AAAAAAAAAA";
    private static final String UPDATED_ICON_FONT = "BBBBBBBBBB";

    private static final String DEFAULT_ICON_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_ICON_PHOTO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE_FLAG = false;
    private static final Boolean UPDATED_ACTIVE_FLAG = true;

    private static final String DEFAULT_LOCALIZATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIZATION = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProductBrandRepository productBrandRepository;

    @Autowired
    private ProductBrandMapper productBrandMapper;

    @Autowired
    private ProductBrandService productBrandService;

    @Autowired
    private ProductBrandQueryService productBrandQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductBrandMockMvc;

    private ProductBrand productBrand;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductBrand createEntity(EntityManager em) {
        ProductBrand productBrand = new ProductBrand()
            .name(DEFAULT_NAME)
            .handle(DEFAULT_HANDLE)
            .shortLabel(DEFAULT_SHORT_LABEL)
            .sortOrder(DEFAULT_SORT_ORDER)
            .iconFont(DEFAULT_ICON_FONT)
            .iconPhoto(DEFAULT_ICON_PHOTO)
            .activeFlag(DEFAULT_ACTIVE_FLAG)
            .localization(DEFAULT_LOCALIZATION)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return productBrand;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductBrand createUpdatedEntity(EntityManager em) {
        ProductBrand productBrand = new ProductBrand()
            .name(UPDATED_NAME)
            .handle(UPDATED_HANDLE)
            .shortLabel(UPDATED_SHORT_LABEL)
            .sortOrder(UPDATED_SORT_ORDER)
            .iconFont(UPDATED_ICON_FONT)
            .iconPhoto(UPDATED_ICON_PHOTO)
            .activeFlag(UPDATED_ACTIVE_FLAG)
            .localization(UPDATED_LOCALIZATION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return productBrand;
    }

    @BeforeEach
    public void initTest() {
        productBrand = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductBrand() throws Exception {
        int databaseSizeBeforeCreate = productBrandRepository.findAll().size();
        // Create the ProductBrand
        ProductBrandDTO productBrandDTO = productBrandMapper.toDto(productBrand);
        restProductBrandMockMvc.perform(post("/api/product-brands").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productBrandDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductBrand in the database
        List<ProductBrand> productBrandList = productBrandRepository.findAll();
        assertThat(productBrandList).hasSize(databaseSizeBeforeCreate + 1);
        ProductBrand testProductBrand = productBrandList.get(productBrandList.size() - 1);
        assertThat(testProductBrand.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductBrand.getHandle()).isEqualTo(DEFAULT_HANDLE);
        assertThat(testProductBrand.getShortLabel()).isEqualTo(DEFAULT_SHORT_LABEL);
        assertThat(testProductBrand.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testProductBrand.getIconFont()).isEqualTo(DEFAULT_ICON_FONT);
        assertThat(testProductBrand.getIconPhoto()).isEqualTo(DEFAULT_ICON_PHOTO);
        assertThat(testProductBrand.isActiveFlag()).isEqualTo(DEFAULT_ACTIVE_FLAG);
        assertThat(testProductBrand.getLocalization()).isEqualTo(DEFAULT_LOCALIZATION);
        assertThat(testProductBrand.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testProductBrand.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createProductBrandWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productBrandRepository.findAll().size();

        // Create the ProductBrand with an existing ID
        productBrand.setId(1L);
        ProductBrandDTO productBrandDTO = productBrandMapper.toDto(productBrand);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductBrandMockMvc.perform(post("/api/product-brands").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productBrandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductBrand in the database
        List<ProductBrand> productBrandList = productBrandRepository.findAll();
        assertThat(productBrandList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productBrandRepository.findAll().size();
        // set the field null
        productBrand.setName(null);

        // Create the ProductBrand, which fails.
        ProductBrandDTO productBrandDTO = productBrandMapper.toDto(productBrand);


        restProductBrandMockMvc.perform(post("/api/product-brands").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productBrandDTO)))
            .andExpect(status().isBadRequest());

        List<ProductBrand> productBrandList = productBrandRepository.findAll();
        assertThat(productBrandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = productBrandRepository.findAll().size();
        // set the field null
        productBrand.setActiveFlag(null);

        // Create the ProductBrand, which fails.
        ProductBrandDTO productBrandDTO = productBrandMapper.toDto(productBrand);


        restProductBrandMockMvc.perform(post("/api/product-brands").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productBrandDTO)))
            .andExpect(status().isBadRequest());

        List<ProductBrand> productBrandList = productBrandRepository.findAll();
        assertThat(productBrandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = productBrandRepository.findAll().size();
        // set the field null
        productBrand.setValidFrom(null);

        // Create the ProductBrand, which fails.
        ProductBrandDTO productBrandDTO = productBrandMapper.toDto(productBrand);


        restProductBrandMockMvc.perform(post("/api/product-brands").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productBrandDTO)))
            .andExpect(status().isBadRequest());

        List<ProductBrand> productBrandList = productBrandRepository.findAll();
        assertThat(productBrandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductBrands() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList
        restProductBrandMockMvc.perform(get("/api/product-brands?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productBrand.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].handle").value(hasItem(DEFAULT_HANDLE)))
            .andExpect(jsonPath("$.[*].shortLabel").value(hasItem(DEFAULT_SHORT_LABEL)))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].iconFont").value(hasItem(DEFAULT_ICON_FONT)))
            .andExpect(jsonPath("$.[*].iconPhoto").value(hasItem(DEFAULT_ICON_PHOTO)))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].localization").value(hasItem(DEFAULT_LOCALIZATION.toString())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getProductBrand() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get the productBrand
        restProductBrandMockMvc.perform(get("/api/product-brands/{id}", productBrand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productBrand.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.handle").value(DEFAULT_HANDLE))
            .andExpect(jsonPath("$.shortLabel").value(DEFAULT_SHORT_LABEL))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.iconFont").value(DEFAULT_ICON_FONT))
            .andExpect(jsonPath("$.iconPhoto").value(DEFAULT_ICON_PHOTO))
            .andExpect(jsonPath("$.activeFlag").value(DEFAULT_ACTIVE_FLAG.booleanValue()))
            .andExpect(jsonPath("$.localization").value(DEFAULT_LOCALIZATION.toString()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getProductBrandsByIdFiltering() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        Long id = productBrand.getId();

        defaultProductBrandShouldBeFound("id.equals=" + id);
        defaultProductBrandShouldNotBeFound("id.notEquals=" + id);

        defaultProductBrandShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductBrandShouldNotBeFound("id.greaterThan=" + id);

        defaultProductBrandShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductBrandShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductBrandsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where name equals to DEFAULT_NAME
        defaultProductBrandShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productBrandList where name equals to UPDATED_NAME
        defaultProductBrandShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where name not equals to DEFAULT_NAME
        defaultProductBrandShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productBrandList where name not equals to UPDATED_NAME
        defaultProductBrandShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductBrandShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productBrandList where name equals to UPDATED_NAME
        defaultProductBrandShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where name is not null
        defaultProductBrandShouldBeFound("name.specified=true");

        // Get all the productBrandList where name is null
        defaultProductBrandShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductBrandsByNameContainsSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where name contains DEFAULT_NAME
        defaultProductBrandShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productBrandList where name contains UPDATED_NAME
        defaultProductBrandShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where name does not contain DEFAULT_NAME
        defaultProductBrandShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productBrandList where name does not contain UPDATED_NAME
        defaultProductBrandShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllProductBrandsByHandleIsEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where handle equals to DEFAULT_HANDLE
        defaultProductBrandShouldBeFound("handle.equals=" + DEFAULT_HANDLE);

        // Get all the productBrandList where handle equals to UPDATED_HANDLE
        defaultProductBrandShouldNotBeFound("handle.equals=" + UPDATED_HANDLE);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByHandleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where handle not equals to DEFAULT_HANDLE
        defaultProductBrandShouldNotBeFound("handle.notEquals=" + DEFAULT_HANDLE);

        // Get all the productBrandList where handle not equals to UPDATED_HANDLE
        defaultProductBrandShouldBeFound("handle.notEquals=" + UPDATED_HANDLE);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByHandleIsInShouldWork() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where handle in DEFAULT_HANDLE or UPDATED_HANDLE
        defaultProductBrandShouldBeFound("handle.in=" + DEFAULT_HANDLE + "," + UPDATED_HANDLE);

        // Get all the productBrandList where handle equals to UPDATED_HANDLE
        defaultProductBrandShouldNotBeFound("handle.in=" + UPDATED_HANDLE);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByHandleIsNullOrNotNull() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where handle is not null
        defaultProductBrandShouldBeFound("handle.specified=true");

        // Get all the productBrandList where handle is null
        defaultProductBrandShouldNotBeFound("handle.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductBrandsByHandleContainsSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where handle contains DEFAULT_HANDLE
        defaultProductBrandShouldBeFound("handle.contains=" + DEFAULT_HANDLE);

        // Get all the productBrandList where handle contains UPDATED_HANDLE
        defaultProductBrandShouldNotBeFound("handle.contains=" + UPDATED_HANDLE);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByHandleNotContainsSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where handle does not contain DEFAULT_HANDLE
        defaultProductBrandShouldNotBeFound("handle.doesNotContain=" + DEFAULT_HANDLE);

        // Get all the productBrandList where handle does not contain UPDATED_HANDLE
        defaultProductBrandShouldBeFound("handle.doesNotContain=" + UPDATED_HANDLE);
    }


    @Test
    @Transactional
    public void getAllProductBrandsByShortLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where shortLabel equals to DEFAULT_SHORT_LABEL
        defaultProductBrandShouldBeFound("shortLabel.equals=" + DEFAULT_SHORT_LABEL);

        // Get all the productBrandList where shortLabel equals to UPDATED_SHORT_LABEL
        defaultProductBrandShouldNotBeFound("shortLabel.equals=" + UPDATED_SHORT_LABEL);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByShortLabelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where shortLabel not equals to DEFAULT_SHORT_LABEL
        defaultProductBrandShouldNotBeFound("shortLabel.notEquals=" + DEFAULT_SHORT_LABEL);

        // Get all the productBrandList where shortLabel not equals to UPDATED_SHORT_LABEL
        defaultProductBrandShouldBeFound("shortLabel.notEquals=" + UPDATED_SHORT_LABEL);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByShortLabelIsInShouldWork() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where shortLabel in DEFAULT_SHORT_LABEL or UPDATED_SHORT_LABEL
        defaultProductBrandShouldBeFound("shortLabel.in=" + DEFAULT_SHORT_LABEL + "," + UPDATED_SHORT_LABEL);

        // Get all the productBrandList where shortLabel equals to UPDATED_SHORT_LABEL
        defaultProductBrandShouldNotBeFound("shortLabel.in=" + UPDATED_SHORT_LABEL);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByShortLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where shortLabel is not null
        defaultProductBrandShouldBeFound("shortLabel.specified=true");

        // Get all the productBrandList where shortLabel is null
        defaultProductBrandShouldNotBeFound("shortLabel.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductBrandsByShortLabelContainsSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where shortLabel contains DEFAULT_SHORT_LABEL
        defaultProductBrandShouldBeFound("shortLabel.contains=" + DEFAULT_SHORT_LABEL);

        // Get all the productBrandList where shortLabel contains UPDATED_SHORT_LABEL
        defaultProductBrandShouldNotBeFound("shortLabel.contains=" + UPDATED_SHORT_LABEL);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByShortLabelNotContainsSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where shortLabel does not contain DEFAULT_SHORT_LABEL
        defaultProductBrandShouldNotBeFound("shortLabel.doesNotContain=" + DEFAULT_SHORT_LABEL);

        // Get all the productBrandList where shortLabel does not contain UPDATED_SHORT_LABEL
        defaultProductBrandShouldBeFound("shortLabel.doesNotContain=" + UPDATED_SHORT_LABEL);
    }


    @Test
    @Transactional
    public void getAllProductBrandsBySortOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where sortOrder equals to DEFAULT_SORT_ORDER
        defaultProductBrandShouldBeFound("sortOrder.equals=" + DEFAULT_SORT_ORDER);

        // Get all the productBrandList where sortOrder equals to UPDATED_SORT_ORDER
        defaultProductBrandShouldNotBeFound("sortOrder.equals=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllProductBrandsBySortOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where sortOrder not equals to DEFAULT_SORT_ORDER
        defaultProductBrandShouldNotBeFound("sortOrder.notEquals=" + DEFAULT_SORT_ORDER);

        // Get all the productBrandList where sortOrder not equals to UPDATED_SORT_ORDER
        defaultProductBrandShouldBeFound("sortOrder.notEquals=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllProductBrandsBySortOrderIsInShouldWork() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where sortOrder in DEFAULT_SORT_ORDER or UPDATED_SORT_ORDER
        defaultProductBrandShouldBeFound("sortOrder.in=" + DEFAULT_SORT_ORDER + "," + UPDATED_SORT_ORDER);

        // Get all the productBrandList where sortOrder equals to UPDATED_SORT_ORDER
        defaultProductBrandShouldNotBeFound("sortOrder.in=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllProductBrandsBySortOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where sortOrder is not null
        defaultProductBrandShouldBeFound("sortOrder.specified=true");

        // Get all the productBrandList where sortOrder is null
        defaultProductBrandShouldNotBeFound("sortOrder.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductBrandsBySortOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where sortOrder is greater than or equal to DEFAULT_SORT_ORDER
        defaultProductBrandShouldBeFound("sortOrder.greaterThanOrEqual=" + DEFAULT_SORT_ORDER);

        // Get all the productBrandList where sortOrder is greater than or equal to UPDATED_SORT_ORDER
        defaultProductBrandShouldNotBeFound("sortOrder.greaterThanOrEqual=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllProductBrandsBySortOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where sortOrder is less than or equal to DEFAULT_SORT_ORDER
        defaultProductBrandShouldBeFound("sortOrder.lessThanOrEqual=" + DEFAULT_SORT_ORDER);

        // Get all the productBrandList where sortOrder is less than or equal to SMALLER_SORT_ORDER
        defaultProductBrandShouldNotBeFound("sortOrder.lessThanOrEqual=" + SMALLER_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllProductBrandsBySortOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where sortOrder is less than DEFAULT_SORT_ORDER
        defaultProductBrandShouldNotBeFound("sortOrder.lessThan=" + DEFAULT_SORT_ORDER);

        // Get all the productBrandList where sortOrder is less than UPDATED_SORT_ORDER
        defaultProductBrandShouldBeFound("sortOrder.lessThan=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllProductBrandsBySortOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where sortOrder is greater than DEFAULT_SORT_ORDER
        defaultProductBrandShouldNotBeFound("sortOrder.greaterThan=" + DEFAULT_SORT_ORDER);

        // Get all the productBrandList where sortOrder is greater than SMALLER_SORT_ORDER
        defaultProductBrandShouldBeFound("sortOrder.greaterThan=" + SMALLER_SORT_ORDER);
    }


    @Test
    @Transactional
    public void getAllProductBrandsByIconFontIsEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where iconFont equals to DEFAULT_ICON_FONT
        defaultProductBrandShouldBeFound("iconFont.equals=" + DEFAULT_ICON_FONT);

        // Get all the productBrandList where iconFont equals to UPDATED_ICON_FONT
        defaultProductBrandShouldNotBeFound("iconFont.equals=" + UPDATED_ICON_FONT);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByIconFontIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where iconFont not equals to DEFAULT_ICON_FONT
        defaultProductBrandShouldNotBeFound("iconFont.notEquals=" + DEFAULT_ICON_FONT);

        // Get all the productBrandList where iconFont not equals to UPDATED_ICON_FONT
        defaultProductBrandShouldBeFound("iconFont.notEquals=" + UPDATED_ICON_FONT);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByIconFontIsInShouldWork() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where iconFont in DEFAULT_ICON_FONT or UPDATED_ICON_FONT
        defaultProductBrandShouldBeFound("iconFont.in=" + DEFAULT_ICON_FONT + "," + UPDATED_ICON_FONT);

        // Get all the productBrandList where iconFont equals to UPDATED_ICON_FONT
        defaultProductBrandShouldNotBeFound("iconFont.in=" + UPDATED_ICON_FONT);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByIconFontIsNullOrNotNull() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where iconFont is not null
        defaultProductBrandShouldBeFound("iconFont.specified=true");

        // Get all the productBrandList where iconFont is null
        defaultProductBrandShouldNotBeFound("iconFont.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductBrandsByIconFontContainsSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where iconFont contains DEFAULT_ICON_FONT
        defaultProductBrandShouldBeFound("iconFont.contains=" + DEFAULT_ICON_FONT);

        // Get all the productBrandList where iconFont contains UPDATED_ICON_FONT
        defaultProductBrandShouldNotBeFound("iconFont.contains=" + UPDATED_ICON_FONT);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByIconFontNotContainsSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where iconFont does not contain DEFAULT_ICON_FONT
        defaultProductBrandShouldNotBeFound("iconFont.doesNotContain=" + DEFAULT_ICON_FONT);

        // Get all the productBrandList where iconFont does not contain UPDATED_ICON_FONT
        defaultProductBrandShouldBeFound("iconFont.doesNotContain=" + UPDATED_ICON_FONT);
    }


    @Test
    @Transactional
    public void getAllProductBrandsByIconPhotoIsEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where iconPhoto equals to DEFAULT_ICON_PHOTO
        defaultProductBrandShouldBeFound("iconPhoto.equals=" + DEFAULT_ICON_PHOTO);

        // Get all the productBrandList where iconPhoto equals to UPDATED_ICON_PHOTO
        defaultProductBrandShouldNotBeFound("iconPhoto.equals=" + UPDATED_ICON_PHOTO);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByIconPhotoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where iconPhoto not equals to DEFAULT_ICON_PHOTO
        defaultProductBrandShouldNotBeFound("iconPhoto.notEquals=" + DEFAULT_ICON_PHOTO);

        // Get all the productBrandList where iconPhoto not equals to UPDATED_ICON_PHOTO
        defaultProductBrandShouldBeFound("iconPhoto.notEquals=" + UPDATED_ICON_PHOTO);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByIconPhotoIsInShouldWork() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where iconPhoto in DEFAULT_ICON_PHOTO or UPDATED_ICON_PHOTO
        defaultProductBrandShouldBeFound("iconPhoto.in=" + DEFAULT_ICON_PHOTO + "," + UPDATED_ICON_PHOTO);

        // Get all the productBrandList where iconPhoto equals to UPDATED_ICON_PHOTO
        defaultProductBrandShouldNotBeFound("iconPhoto.in=" + UPDATED_ICON_PHOTO);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByIconPhotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where iconPhoto is not null
        defaultProductBrandShouldBeFound("iconPhoto.specified=true");

        // Get all the productBrandList where iconPhoto is null
        defaultProductBrandShouldNotBeFound("iconPhoto.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductBrandsByIconPhotoContainsSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where iconPhoto contains DEFAULT_ICON_PHOTO
        defaultProductBrandShouldBeFound("iconPhoto.contains=" + DEFAULT_ICON_PHOTO);

        // Get all the productBrandList where iconPhoto contains UPDATED_ICON_PHOTO
        defaultProductBrandShouldNotBeFound("iconPhoto.contains=" + UPDATED_ICON_PHOTO);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByIconPhotoNotContainsSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where iconPhoto does not contain DEFAULT_ICON_PHOTO
        defaultProductBrandShouldNotBeFound("iconPhoto.doesNotContain=" + DEFAULT_ICON_PHOTO);

        // Get all the productBrandList where iconPhoto does not contain UPDATED_ICON_PHOTO
        defaultProductBrandShouldBeFound("iconPhoto.doesNotContain=" + UPDATED_ICON_PHOTO);
    }


    @Test
    @Transactional
    public void getAllProductBrandsByActiveFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where activeFlag equals to DEFAULT_ACTIVE_FLAG
        defaultProductBrandShouldBeFound("activeFlag.equals=" + DEFAULT_ACTIVE_FLAG);

        // Get all the productBrandList where activeFlag equals to UPDATED_ACTIVE_FLAG
        defaultProductBrandShouldNotBeFound("activeFlag.equals=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByActiveFlagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where activeFlag not equals to DEFAULT_ACTIVE_FLAG
        defaultProductBrandShouldNotBeFound("activeFlag.notEquals=" + DEFAULT_ACTIVE_FLAG);

        // Get all the productBrandList where activeFlag not equals to UPDATED_ACTIVE_FLAG
        defaultProductBrandShouldBeFound("activeFlag.notEquals=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByActiveFlagIsInShouldWork() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where activeFlag in DEFAULT_ACTIVE_FLAG or UPDATED_ACTIVE_FLAG
        defaultProductBrandShouldBeFound("activeFlag.in=" + DEFAULT_ACTIVE_FLAG + "," + UPDATED_ACTIVE_FLAG);

        // Get all the productBrandList where activeFlag equals to UPDATED_ACTIVE_FLAG
        defaultProductBrandShouldNotBeFound("activeFlag.in=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByActiveFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where activeFlag is not null
        defaultProductBrandShouldBeFound("activeFlag.specified=true");

        // Get all the productBrandList where activeFlag is null
        defaultProductBrandShouldNotBeFound("activeFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductBrandsByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where validFrom equals to DEFAULT_VALID_FROM
        defaultProductBrandShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the productBrandList where validFrom equals to UPDATED_VALID_FROM
        defaultProductBrandShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where validFrom not equals to DEFAULT_VALID_FROM
        defaultProductBrandShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the productBrandList where validFrom not equals to UPDATED_VALID_FROM
        defaultProductBrandShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultProductBrandShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the productBrandList where validFrom equals to UPDATED_VALID_FROM
        defaultProductBrandShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where validFrom is not null
        defaultProductBrandShouldBeFound("validFrom.specified=true");

        // Get all the productBrandList where validFrom is null
        defaultProductBrandShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductBrandsByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where validTo equals to DEFAULT_VALID_TO
        defaultProductBrandShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the productBrandList where validTo equals to UPDATED_VALID_TO
        defaultProductBrandShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where validTo not equals to DEFAULT_VALID_TO
        defaultProductBrandShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the productBrandList where validTo not equals to UPDATED_VALID_TO
        defaultProductBrandShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultProductBrandShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the productBrandList where validTo equals to UPDATED_VALID_TO
        defaultProductBrandShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where validTo is not null
        defaultProductBrandShouldBeFound("validTo.specified=true");

        // Get all the productBrandList where validTo is null
        defaultProductBrandShouldNotBeFound("validTo.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductBrandShouldBeFound(String filter) throws Exception {
        restProductBrandMockMvc.perform(get("/api/product-brands?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productBrand.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].handle").value(hasItem(DEFAULT_HANDLE)))
            .andExpect(jsonPath("$.[*].shortLabel").value(hasItem(DEFAULT_SHORT_LABEL)))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].iconFont").value(hasItem(DEFAULT_ICON_FONT)))
            .andExpect(jsonPath("$.[*].iconPhoto").value(hasItem(DEFAULT_ICON_PHOTO)))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].localization").value(hasItem(DEFAULT_LOCALIZATION.toString())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restProductBrandMockMvc.perform(get("/api/product-brands/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductBrandShouldNotBeFound(String filter) throws Exception {
        restProductBrandMockMvc.perform(get("/api/product-brands?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductBrandMockMvc.perform(get("/api/product-brands/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductBrand() throws Exception {
        // Get the productBrand
        restProductBrandMockMvc.perform(get("/api/product-brands/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductBrand() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        int databaseSizeBeforeUpdate = productBrandRepository.findAll().size();

        // Update the productBrand
        ProductBrand updatedProductBrand = productBrandRepository.findById(productBrand.getId()).get();
        // Disconnect from session so that the updates on updatedProductBrand are not directly saved in db
        em.detach(updatedProductBrand);
        updatedProductBrand
            .name(UPDATED_NAME)
            .handle(UPDATED_HANDLE)
            .shortLabel(UPDATED_SHORT_LABEL)
            .sortOrder(UPDATED_SORT_ORDER)
            .iconFont(UPDATED_ICON_FONT)
            .iconPhoto(UPDATED_ICON_PHOTO)
            .activeFlag(UPDATED_ACTIVE_FLAG)
            .localization(UPDATED_LOCALIZATION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        ProductBrandDTO productBrandDTO = productBrandMapper.toDto(updatedProductBrand);

        restProductBrandMockMvc.perform(put("/api/product-brands").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productBrandDTO)))
            .andExpect(status().isOk());

        // Validate the ProductBrand in the database
        List<ProductBrand> productBrandList = productBrandRepository.findAll();
        assertThat(productBrandList).hasSize(databaseSizeBeforeUpdate);
        ProductBrand testProductBrand = productBrandList.get(productBrandList.size() - 1);
        assertThat(testProductBrand.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductBrand.getHandle()).isEqualTo(UPDATED_HANDLE);
        assertThat(testProductBrand.getShortLabel()).isEqualTo(UPDATED_SHORT_LABEL);
        assertThat(testProductBrand.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testProductBrand.getIconFont()).isEqualTo(UPDATED_ICON_FONT);
        assertThat(testProductBrand.getIconPhoto()).isEqualTo(UPDATED_ICON_PHOTO);
        assertThat(testProductBrand.isActiveFlag()).isEqualTo(UPDATED_ACTIVE_FLAG);
        assertThat(testProductBrand.getLocalization()).isEqualTo(UPDATED_LOCALIZATION);
        assertThat(testProductBrand.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testProductBrand.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingProductBrand() throws Exception {
        int databaseSizeBeforeUpdate = productBrandRepository.findAll().size();

        // Create the ProductBrand
        ProductBrandDTO productBrandDTO = productBrandMapper.toDto(productBrand);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductBrandMockMvc.perform(put("/api/product-brands").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productBrandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductBrand in the database
        List<ProductBrand> productBrandList = productBrandRepository.findAll();
        assertThat(productBrandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductBrand() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        int databaseSizeBeforeDelete = productBrandRepository.findAll().size();

        // Delete the productBrand
        restProductBrandMockMvc.perform(delete("/api/product-brands/{id}", productBrand.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductBrand> productBrandList = productBrandRepository.findAll();
        assertThat(productBrandList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.ProductCategory;
import com.vertical.commerce.domain.ProductCategory;
import com.vertical.commerce.repository.ProductCategoryRepository;
import com.vertical.commerce.service.ProductCategoryService;
import com.vertical.commerce.service.dto.ProductCategoryDTO;
import com.vertical.commerce.service.mapper.ProductCategoryMapper;
import com.vertical.commerce.service.dto.ProductCategoryCriteria;
import com.vertical.commerce.service.ProductCategoryQueryService;

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
 * Integration tests for the {@link ProductCategoryResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ProductCategoryResourceIT {

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

    private static final Boolean DEFAULT_JUST_FOR_YOU_IND = false;
    private static final Boolean UPDATED_JUST_FOR_YOU_IND = true;

    private static final String DEFAULT_PARENT_CASCADE = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_CASCADE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE_FLAG = false;
    private static final Boolean UPDATED_ACTIVE_FLAG = true;

    private static final String DEFAULT_LOCALIZATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIZATION = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductCategoryQueryService productCategoryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductCategoryMockMvc;

    private ProductCategory productCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductCategory createEntity(EntityManager em) {
        ProductCategory productCategory = new ProductCategory()
            .name(DEFAULT_NAME)
            .handle(DEFAULT_HANDLE)
            .shortLabel(DEFAULT_SHORT_LABEL)
            .sortOrder(DEFAULT_SORT_ORDER)
            .iconFont(DEFAULT_ICON_FONT)
            .iconPhoto(DEFAULT_ICON_PHOTO)
            .justForYouInd(DEFAULT_JUST_FOR_YOU_IND)
            .parentCascade(DEFAULT_PARENT_CASCADE)
            .activeFlag(DEFAULT_ACTIVE_FLAG)
            .localization(DEFAULT_LOCALIZATION)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return productCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductCategory createUpdatedEntity(EntityManager em) {
        ProductCategory productCategory = new ProductCategory()
            .name(UPDATED_NAME)
            .handle(UPDATED_HANDLE)
            .shortLabel(UPDATED_SHORT_LABEL)
            .sortOrder(UPDATED_SORT_ORDER)
            .iconFont(UPDATED_ICON_FONT)
            .iconPhoto(UPDATED_ICON_PHOTO)
            .justForYouInd(UPDATED_JUST_FOR_YOU_IND)
            .parentCascade(UPDATED_PARENT_CASCADE)
            .activeFlag(UPDATED_ACTIVE_FLAG)
            .localization(UPDATED_LOCALIZATION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return productCategory;
    }

    @BeforeEach
    public void initTest() {
        productCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductCategory() throws Exception {
        int databaseSizeBeforeCreate = productCategoryRepository.findAll().size();
        // Create the ProductCategory
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);
        restProductCategoryMockMvc.perform(post("/api/product-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ProductCategory testProductCategory = productCategoryList.get(productCategoryList.size() - 1);
        assertThat(testProductCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductCategory.getHandle()).isEqualTo(DEFAULT_HANDLE);
        assertThat(testProductCategory.getShortLabel()).isEqualTo(DEFAULT_SHORT_LABEL);
        assertThat(testProductCategory.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testProductCategory.getIconFont()).isEqualTo(DEFAULT_ICON_FONT);
        assertThat(testProductCategory.getIconPhoto()).isEqualTo(DEFAULT_ICON_PHOTO);
        assertThat(testProductCategory.isJustForYouInd()).isEqualTo(DEFAULT_JUST_FOR_YOU_IND);
        assertThat(testProductCategory.getParentCascade()).isEqualTo(DEFAULT_PARENT_CASCADE);
        assertThat(testProductCategory.isActiveFlag()).isEqualTo(DEFAULT_ACTIVE_FLAG);
        assertThat(testProductCategory.getLocalization()).isEqualTo(DEFAULT_LOCALIZATION);
        assertThat(testProductCategory.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testProductCategory.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createProductCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productCategoryRepository.findAll().size();

        // Create the ProductCategory with an existing ID
        productCategory.setId(1L);
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductCategoryMockMvc.perform(post("/api/product-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productCategoryRepository.findAll().size();
        // set the field null
        productCategory.setName(null);

        // Create the ProductCategory, which fails.
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);


        restProductCategoryMockMvc.perform(post("/api/product-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = productCategoryRepository.findAll().size();
        // set the field null
        productCategory.setActiveFlag(null);

        // Create the ProductCategory, which fails.
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);


        restProductCategoryMockMvc.perform(post("/api/product-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = productCategoryRepository.findAll().size();
        // set the field null
        productCategory.setValidFrom(null);

        // Create the ProductCategory, which fails.
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);


        restProductCategoryMockMvc.perform(post("/api/product-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductCategories() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList
        restProductCategoryMockMvc.perform(get("/api/product-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].handle").value(hasItem(DEFAULT_HANDLE)))
            .andExpect(jsonPath("$.[*].shortLabel").value(hasItem(DEFAULT_SHORT_LABEL)))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].iconFont").value(hasItem(DEFAULT_ICON_FONT)))
            .andExpect(jsonPath("$.[*].iconPhoto").value(hasItem(DEFAULT_ICON_PHOTO)))
            .andExpect(jsonPath("$.[*].justForYouInd").value(hasItem(DEFAULT_JUST_FOR_YOU_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].parentCascade").value(hasItem(DEFAULT_PARENT_CASCADE)))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].localization").value(hasItem(DEFAULT_LOCALIZATION.toString())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getProductCategory() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get the productCategory
        restProductCategoryMockMvc.perform(get("/api/product-categories/{id}", productCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.handle").value(DEFAULT_HANDLE))
            .andExpect(jsonPath("$.shortLabel").value(DEFAULT_SHORT_LABEL))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.iconFont").value(DEFAULT_ICON_FONT))
            .andExpect(jsonPath("$.iconPhoto").value(DEFAULT_ICON_PHOTO))
            .andExpect(jsonPath("$.justForYouInd").value(DEFAULT_JUST_FOR_YOU_IND.booleanValue()))
            .andExpect(jsonPath("$.parentCascade").value(DEFAULT_PARENT_CASCADE))
            .andExpect(jsonPath("$.activeFlag").value(DEFAULT_ACTIVE_FLAG.booleanValue()))
            .andExpect(jsonPath("$.localization").value(DEFAULT_LOCALIZATION.toString()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getProductCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        Long id = productCategory.getId();

        defaultProductCategoryShouldBeFound("id.equals=" + id);
        defaultProductCategoryShouldNotBeFound("id.notEquals=" + id);

        defaultProductCategoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductCategoryShouldNotBeFound("id.greaterThan=" + id);

        defaultProductCategoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductCategoryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name equals to DEFAULT_NAME
        defaultProductCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productCategoryList where name equals to UPDATED_NAME
        defaultProductCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name not equals to DEFAULT_NAME
        defaultProductCategoryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productCategoryList where name not equals to UPDATED_NAME
        defaultProductCategoryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductCategoryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productCategoryList where name equals to UPDATED_NAME
        defaultProductCategoryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name is not null
        defaultProductCategoryShouldBeFound("name.specified=true");

        // Get all the productCategoryList where name is null
        defaultProductCategoryShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductCategoriesByNameContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name contains DEFAULT_NAME
        defaultProductCategoryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productCategoryList where name contains UPDATED_NAME
        defaultProductCategoryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name does not contain DEFAULT_NAME
        defaultProductCategoryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productCategoryList where name does not contain UPDATED_NAME
        defaultProductCategoryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllProductCategoriesByHandleIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where handle equals to DEFAULT_HANDLE
        defaultProductCategoryShouldBeFound("handle.equals=" + DEFAULT_HANDLE);

        // Get all the productCategoryList where handle equals to UPDATED_HANDLE
        defaultProductCategoryShouldNotBeFound("handle.equals=" + UPDATED_HANDLE);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByHandleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where handle not equals to DEFAULT_HANDLE
        defaultProductCategoryShouldNotBeFound("handle.notEquals=" + DEFAULT_HANDLE);

        // Get all the productCategoryList where handle not equals to UPDATED_HANDLE
        defaultProductCategoryShouldBeFound("handle.notEquals=" + UPDATED_HANDLE);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByHandleIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where handle in DEFAULT_HANDLE or UPDATED_HANDLE
        defaultProductCategoryShouldBeFound("handle.in=" + DEFAULT_HANDLE + "," + UPDATED_HANDLE);

        // Get all the productCategoryList where handle equals to UPDATED_HANDLE
        defaultProductCategoryShouldNotBeFound("handle.in=" + UPDATED_HANDLE);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByHandleIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where handle is not null
        defaultProductCategoryShouldBeFound("handle.specified=true");

        // Get all the productCategoryList where handle is null
        defaultProductCategoryShouldNotBeFound("handle.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductCategoriesByHandleContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where handle contains DEFAULT_HANDLE
        defaultProductCategoryShouldBeFound("handle.contains=" + DEFAULT_HANDLE);

        // Get all the productCategoryList where handle contains UPDATED_HANDLE
        defaultProductCategoryShouldNotBeFound("handle.contains=" + UPDATED_HANDLE);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByHandleNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where handle does not contain DEFAULT_HANDLE
        defaultProductCategoryShouldNotBeFound("handle.doesNotContain=" + DEFAULT_HANDLE);

        // Get all the productCategoryList where handle does not contain UPDATED_HANDLE
        defaultProductCategoryShouldBeFound("handle.doesNotContain=" + UPDATED_HANDLE);
    }


    @Test
    @Transactional
    public void getAllProductCategoriesByShortLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where shortLabel equals to DEFAULT_SHORT_LABEL
        defaultProductCategoryShouldBeFound("shortLabel.equals=" + DEFAULT_SHORT_LABEL);

        // Get all the productCategoryList where shortLabel equals to UPDATED_SHORT_LABEL
        defaultProductCategoryShouldNotBeFound("shortLabel.equals=" + UPDATED_SHORT_LABEL);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByShortLabelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where shortLabel not equals to DEFAULT_SHORT_LABEL
        defaultProductCategoryShouldNotBeFound("shortLabel.notEquals=" + DEFAULT_SHORT_LABEL);

        // Get all the productCategoryList where shortLabel not equals to UPDATED_SHORT_LABEL
        defaultProductCategoryShouldBeFound("shortLabel.notEquals=" + UPDATED_SHORT_LABEL);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByShortLabelIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where shortLabel in DEFAULT_SHORT_LABEL or UPDATED_SHORT_LABEL
        defaultProductCategoryShouldBeFound("shortLabel.in=" + DEFAULT_SHORT_LABEL + "," + UPDATED_SHORT_LABEL);

        // Get all the productCategoryList where shortLabel equals to UPDATED_SHORT_LABEL
        defaultProductCategoryShouldNotBeFound("shortLabel.in=" + UPDATED_SHORT_LABEL);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByShortLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where shortLabel is not null
        defaultProductCategoryShouldBeFound("shortLabel.specified=true");

        // Get all the productCategoryList where shortLabel is null
        defaultProductCategoryShouldNotBeFound("shortLabel.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductCategoriesByShortLabelContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where shortLabel contains DEFAULT_SHORT_LABEL
        defaultProductCategoryShouldBeFound("shortLabel.contains=" + DEFAULT_SHORT_LABEL);

        // Get all the productCategoryList where shortLabel contains UPDATED_SHORT_LABEL
        defaultProductCategoryShouldNotBeFound("shortLabel.contains=" + UPDATED_SHORT_LABEL);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByShortLabelNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where shortLabel does not contain DEFAULT_SHORT_LABEL
        defaultProductCategoryShouldNotBeFound("shortLabel.doesNotContain=" + DEFAULT_SHORT_LABEL);

        // Get all the productCategoryList where shortLabel does not contain UPDATED_SHORT_LABEL
        defaultProductCategoryShouldBeFound("shortLabel.doesNotContain=" + UPDATED_SHORT_LABEL);
    }


    @Test
    @Transactional
    public void getAllProductCategoriesBySortOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sortOrder equals to DEFAULT_SORT_ORDER
        defaultProductCategoryShouldBeFound("sortOrder.equals=" + DEFAULT_SORT_ORDER);

        // Get all the productCategoryList where sortOrder equals to UPDATED_SORT_ORDER
        defaultProductCategoryShouldNotBeFound("sortOrder.equals=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesBySortOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sortOrder not equals to DEFAULT_SORT_ORDER
        defaultProductCategoryShouldNotBeFound("sortOrder.notEquals=" + DEFAULT_SORT_ORDER);

        // Get all the productCategoryList where sortOrder not equals to UPDATED_SORT_ORDER
        defaultProductCategoryShouldBeFound("sortOrder.notEquals=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesBySortOrderIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sortOrder in DEFAULT_SORT_ORDER or UPDATED_SORT_ORDER
        defaultProductCategoryShouldBeFound("sortOrder.in=" + DEFAULT_SORT_ORDER + "," + UPDATED_SORT_ORDER);

        // Get all the productCategoryList where sortOrder equals to UPDATED_SORT_ORDER
        defaultProductCategoryShouldNotBeFound("sortOrder.in=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesBySortOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sortOrder is not null
        defaultProductCategoryShouldBeFound("sortOrder.specified=true");

        // Get all the productCategoryList where sortOrder is null
        defaultProductCategoryShouldNotBeFound("sortOrder.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductCategoriesBySortOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sortOrder is greater than or equal to DEFAULT_SORT_ORDER
        defaultProductCategoryShouldBeFound("sortOrder.greaterThanOrEqual=" + DEFAULT_SORT_ORDER);

        // Get all the productCategoryList where sortOrder is greater than or equal to UPDATED_SORT_ORDER
        defaultProductCategoryShouldNotBeFound("sortOrder.greaterThanOrEqual=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesBySortOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sortOrder is less than or equal to DEFAULT_SORT_ORDER
        defaultProductCategoryShouldBeFound("sortOrder.lessThanOrEqual=" + DEFAULT_SORT_ORDER);

        // Get all the productCategoryList where sortOrder is less than or equal to SMALLER_SORT_ORDER
        defaultProductCategoryShouldNotBeFound("sortOrder.lessThanOrEqual=" + SMALLER_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesBySortOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sortOrder is less than DEFAULT_SORT_ORDER
        defaultProductCategoryShouldNotBeFound("sortOrder.lessThan=" + DEFAULT_SORT_ORDER);

        // Get all the productCategoryList where sortOrder is less than UPDATED_SORT_ORDER
        defaultProductCategoryShouldBeFound("sortOrder.lessThan=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesBySortOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sortOrder is greater than DEFAULT_SORT_ORDER
        defaultProductCategoryShouldNotBeFound("sortOrder.greaterThan=" + DEFAULT_SORT_ORDER);

        // Get all the productCategoryList where sortOrder is greater than SMALLER_SORT_ORDER
        defaultProductCategoryShouldBeFound("sortOrder.greaterThan=" + SMALLER_SORT_ORDER);
    }


    @Test
    @Transactional
    public void getAllProductCategoriesByIconFontIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where iconFont equals to DEFAULT_ICON_FONT
        defaultProductCategoryShouldBeFound("iconFont.equals=" + DEFAULT_ICON_FONT);

        // Get all the productCategoryList where iconFont equals to UPDATED_ICON_FONT
        defaultProductCategoryShouldNotBeFound("iconFont.equals=" + UPDATED_ICON_FONT);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByIconFontIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where iconFont not equals to DEFAULT_ICON_FONT
        defaultProductCategoryShouldNotBeFound("iconFont.notEquals=" + DEFAULT_ICON_FONT);

        // Get all the productCategoryList where iconFont not equals to UPDATED_ICON_FONT
        defaultProductCategoryShouldBeFound("iconFont.notEquals=" + UPDATED_ICON_FONT);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByIconFontIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where iconFont in DEFAULT_ICON_FONT or UPDATED_ICON_FONT
        defaultProductCategoryShouldBeFound("iconFont.in=" + DEFAULT_ICON_FONT + "," + UPDATED_ICON_FONT);

        // Get all the productCategoryList where iconFont equals to UPDATED_ICON_FONT
        defaultProductCategoryShouldNotBeFound("iconFont.in=" + UPDATED_ICON_FONT);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByIconFontIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where iconFont is not null
        defaultProductCategoryShouldBeFound("iconFont.specified=true");

        // Get all the productCategoryList where iconFont is null
        defaultProductCategoryShouldNotBeFound("iconFont.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductCategoriesByIconFontContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where iconFont contains DEFAULT_ICON_FONT
        defaultProductCategoryShouldBeFound("iconFont.contains=" + DEFAULT_ICON_FONT);

        // Get all the productCategoryList where iconFont contains UPDATED_ICON_FONT
        defaultProductCategoryShouldNotBeFound("iconFont.contains=" + UPDATED_ICON_FONT);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByIconFontNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where iconFont does not contain DEFAULT_ICON_FONT
        defaultProductCategoryShouldNotBeFound("iconFont.doesNotContain=" + DEFAULT_ICON_FONT);

        // Get all the productCategoryList where iconFont does not contain UPDATED_ICON_FONT
        defaultProductCategoryShouldBeFound("iconFont.doesNotContain=" + UPDATED_ICON_FONT);
    }


    @Test
    @Transactional
    public void getAllProductCategoriesByIconPhotoIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where iconPhoto equals to DEFAULT_ICON_PHOTO
        defaultProductCategoryShouldBeFound("iconPhoto.equals=" + DEFAULT_ICON_PHOTO);

        // Get all the productCategoryList where iconPhoto equals to UPDATED_ICON_PHOTO
        defaultProductCategoryShouldNotBeFound("iconPhoto.equals=" + UPDATED_ICON_PHOTO);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByIconPhotoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where iconPhoto not equals to DEFAULT_ICON_PHOTO
        defaultProductCategoryShouldNotBeFound("iconPhoto.notEquals=" + DEFAULT_ICON_PHOTO);

        // Get all the productCategoryList where iconPhoto not equals to UPDATED_ICON_PHOTO
        defaultProductCategoryShouldBeFound("iconPhoto.notEquals=" + UPDATED_ICON_PHOTO);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByIconPhotoIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where iconPhoto in DEFAULT_ICON_PHOTO or UPDATED_ICON_PHOTO
        defaultProductCategoryShouldBeFound("iconPhoto.in=" + DEFAULT_ICON_PHOTO + "," + UPDATED_ICON_PHOTO);

        // Get all the productCategoryList where iconPhoto equals to UPDATED_ICON_PHOTO
        defaultProductCategoryShouldNotBeFound("iconPhoto.in=" + UPDATED_ICON_PHOTO);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByIconPhotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where iconPhoto is not null
        defaultProductCategoryShouldBeFound("iconPhoto.specified=true");

        // Get all the productCategoryList where iconPhoto is null
        defaultProductCategoryShouldNotBeFound("iconPhoto.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductCategoriesByIconPhotoContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where iconPhoto contains DEFAULT_ICON_PHOTO
        defaultProductCategoryShouldBeFound("iconPhoto.contains=" + DEFAULT_ICON_PHOTO);

        // Get all the productCategoryList where iconPhoto contains UPDATED_ICON_PHOTO
        defaultProductCategoryShouldNotBeFound("iconPhoto.contains=" + UPDATED_ICON_PHOTO);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByIconPhotoNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where iconPhoto does not contain DEFAULT_ICON_PHOTO
        defaultProductCategoryShouldNotBeFound("iconPhoto.doesNotContain=" + DEFAULT_ICON_PHOTO);

        // Get all the productCategoryList where iconPhoto does not contain UPDATED_ICON_PHOTO
        defaultProductCategoryShouldBeFound("iconPhoto.doesNotContain=" + UPDATED_ICON_PHOTO);
    }


    @Test
    @Transactional
    public void getAllProductCategoriesByJustForYouIndIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where justForYouInd equals to DEFAULT_JUST_FOR_YOU_IND
        defaultProductCategoryShouldBeFound("justForYouInd.equals=" + DEFAULT_JUST_FOR_YOU_IND);

        // Get all the productCategoryList where justForYouInd equals to UPDATED_JUST_FOR_YOU_IND
        defaultProductCategoryShouldNotBeFound("justForYouInd.equals=" + UPDATED_JUST_FOR_YOU_IND);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByJustForYouIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where justForYouInd not equals to DEFAULT_JUST_FOR_YOU_IND
        defaultProductCategoryShouldNotBeFound("justForYouInd.notEquals=" + DEFAULT_JUST_FOR_YOU_IND);

        // Get all the productCategoryList where justForYouInd not equals to UPDATED_JUST_FOR_YOU_IND
        defaultProductCategoryShouldBeFound("justForYouInd.notEquals=" + UPDATED_JUST_FOR_YOU_IND);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByJustForYouIndIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where justForYouInd in DEFAULT_JUST_FOR_YOU_IND or UPDATED_JUST_FOR_YOU_IND
        defaultProductCategoryShouldBeFound("justForYouInd.in=" + DEFAULT_JUST_FOR_YOU_IND + "," + UPDATED_JUST_FOR_YOU_IND);

        // Get all the productCategoryList where justForYouInd equals to UPDATED_JUST_FOR_YOU_IND
        defaultProductCategoryShouldNotBeFound("justForYouInd.in=" + UPDATED_JUST_FOR_YOU_IND);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByJustForYouIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where justForYouInd is not null
        defaultProductCategoryShouldBeFound("justForYouInd.specified=true");

        // Get all the productCategoryList where justForYouInd is null
        defaultProductCategoryShouldNotBeFound("justForYouInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByParentCascadeIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where parentCascade equals to DEFAULT_PARENT_CASCADE
        defaultProductCategoryShouldBeFound("parentCascade.equals=" + DEFAULT_PARENT_CASCADE);

        // Get all the productCategoryList where parentCascade equals to UPDATED_PARENT_CASCADE
        defaultProductCategoryShouldNotBeFound("parentCascade.equals=" + UPDATED_PARENT_CASCADE);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByParentCascadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where parentCascade not equals to DEFAULT_PARENT_CASCADE
        defaultProductCategoryShouldNotBeFound("parentCascade.notEquals=" + DEFAULT_PARENT_CASCADE);

        // Get all the productCategoryList where parentCascade not equals to UPDATED_PARENT_CASCADE
        defaultProductCategoryShouldBeFound("parentCascade.notEquals=" + UPDATED_PARENT_CASCADE);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByParentCascadeIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where parentCascade in DEFAULT_PARENT_CASCADE or UPDATED_PARENT_CASCADE
        defaultProductCategoryShouldBeFound("parentCascade.in=" + DEFAULT_PARENT_CASCADE + "," + UPDATED_PARENT_CASCADE);

        // Get all the productCategoryList where parentCascade equals to UPDATED_PARENT_CASCADE
        defaultProductCategoryShouldNotBeFound("parentCascade.in=" + UPDATED_PARENT_CASCADE);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByParentCascadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where parentCascade is not null
        defaultProductCategoryShouldBeFound("parentCascade.specified=true");

        // Get all the productCategoryList where parentCascade is null
        defaultProductCategoryShouldNotBeFound("parentCascade.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductCategoriesByParentCascadeContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where parentCascade contains DEFAULT_PARENT_CASCADE
        defaultProductCategoryShouldBeFound("parentCascade.contains=" + DEFAULT_PARENT_CASCADE);

        // Get all the productCategoryList where parentCascade contains UPDATED_PARENT_CASCADE
        defaultProductCategoryShouldNotBeFound("parentCascade.contains=" + UPDATED_PARENT_CASCADE);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByParentCascadeNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where parentCascade does not contain DEFAULT_PARENT_CASCADE
        defaultProductCategoryShouldNotBeFound("parentCascade.doesNotContain=" + DEFAULT_PARENT_CASCADE);

        // Get all the productCategoryList where parentCascade does not contain UPDATED_PARENT_CASCADE
        defaultProductCategoryShouldBeFound("parentCascade.doesNotContain=" + UPDATED_PARENT_CASCADE);
    }


    @Test
    @Transactional
    public void getAllProductCategoriesByActiveFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where activeFlag equals to DEFAULT_ACTIVE_FLAG
        defaultProductCategoryShouldBeFound("activeFlag.equals=" + DEFAULT_ACTIVE_FLAG);

        // Get all the productCategoryList where activeFlag equals to UPDATED_ACTIVE_FLAG
        defaultProductCategoryShouldNotBeFound("activeFlag.equals=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByActiveFlagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where activeFlag not equals to DEFAULT_ACTIVE_FLAG
        defaultProductCategoryShouldNotBeFound("activeFlag.notEquals=" + DEFAULT_ACTIVE_FLAG);

        // Get all the productCategoryList where activeFlag not equals to UPDATED_ACTIVE_FLAG
        defaultProductCategoryShouldBeFound("activeFlag.notEquals=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByActiveFlagIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where activeFlag in DEFAULT_ACTIVE_FLAG or UPDATED_ACTIVE_FLAG
        defaultProductCategoryShouldBeFound("activeFlag.in=" + DEFAULT_ACTIVE_FLAG + "," + UPDATED_ACTIVE_FLAG);

        // Get all the productCategoryList where activeFlag equals to UPDATED_ACTIVE_FLAG
        defaultProductCategoryShouldNotBeFound("activeFlag.in=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByActiveFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where activeFlag is not null
        defaultProductCategoryShouldBeFound("activeFlag.specified=true");

        // Get all the productCategoryList where activeFlag is null
        defaultProductCategoryShouldNotBeFound("activeFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where validFrom equals to DEFAULT_VALID_FROM
        defaultProductCategoryShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the productCategoryList where validFrom equals to UPDATED_VALID_FROM
        defaultProductCategoryShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where validFrom not equals to DEFAULT_VALID_FROM
        defaultProductCategoryShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the productCategoryList where validFrom not equals to UPDATED_VALID_FROM
        defaultProductCategoryShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultProductCategoryShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the productCategoryList where validFrom equals to UPDATED_VALID_FROM
        defaultProductCategoryShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where validFrom is not null
        defaultProductCategoryShouldBeFound("validFrom.specified=true");

        // Get all the productCategoryList where validFrom is null
        defaultProductCategoryShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where validTo equals to DEFAULT_VALID_TO
        defaultProductCategoryShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the productCategoryList where validTo equals to UPDATED_VALID_TO
        defaultProductCategoryShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where validTo not equals to DEFAULT_VALID_TO
        defaultProductCategoryShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the productCategoryList where validTo not equals to UPDATED_VALID_TO
        defaultProductCategoryShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultProductCategoryShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the productCategoryList where validTo equals to UPDATED_VALID_TO
        defaultProductCategoryShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where validTo is not null
        defaultProductCategoryShouldBeFound("validTo.specified=true");

        // Get all the productCategoryList where validTo is null
        defaultProductCategoryShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);
        ProductCategory parent = ProductCategoryResourceIT.createEntity(em);
        em.persist(parent);
        em.flush();
        productCategory.setParent(parent);
        productCategoryRepository.saveAndFlush(productCategory);
        Long parentId = parent.getId();

        // Get all the productCategoryList where parent equals to parentId
        defaultProductCategoryShouldBeFound("parentId.equals=" + parentId);

        // Get all the productCategoryList where parent equals to parentId + 1
        defaultProductCategoryShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductCategoryShouldBeFound(String filter) throws Exception {
        restProductCategoryMockMvc.perform(get("/api/product-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].handle").value(hasItem(DEFAULT_HANDLE)))
            .andExpect(jsonPath("$.[*].shortLabel").value(hasItem(DEFAULT_SHORT_LABEL)))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].iconFont").value(hasItem(DEFAULT_ICON_FONT)))
            .andExpect(jsonPath("$.[*].iconPhoto").value(hasItem(DEFAULT_ICON_PHOTO)))
            .andExpect(jsonPath("$.[*].justForYouInd").value(hasItem(DEFAULT_JUST_FOR_YOU_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].parentCascade").value(hasItem(DEFAULT_PARENT_CASCADE)))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].localization").value(hasItem(DEFAULT_LOCALIZATION.toString())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restProductCategoryMockMvc.perform(get("/api/product-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductCategoryShouldNotBeFound(String filter) throws Exception {
        restProductCategoryMockMvc.perform(get("/api/product-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductCategoryMockMvc.perform(get("/api/product-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductCategory() throws Exception {
        // Get the productCategory
        restProductCategoryMockMvc.perform(get("/api/product-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductCategory() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();

        // Update the productCategory
        ProductCategory updatedProductCategory = productCategoryRepository.findById(productCategory.getId()).get();
        // Disconnect from session so that the updates on updatedProductCategory are not directly saved in db
        em.detach(updatedProductCategory);
        updatedProductCategory
            .name(UPDATED_NAME)
            .handle(UPDATED_HANDLE)
            .shortLabel(UPDATED_SHORT_LABEL)
            .sortOrder(UPDATED_SORT_ORDER)
            .iconFont(UPDATED_ICON_FONT)
            .iconPhoto(UPDATED_ICON_PHOTO)
            .justForYouInd(UPDATED_JUST_FOR_YOU_IND)
            .parentCascade(UPDATED_PARENT_CASCADE)
            .activeFlag(UPDATED_ACTIVE_FLAG)
            .localization(UPDATED_LOCALIZATION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(updatedProductCategory);

        restProductCategoryMockMvc.perform(put("/api/product-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
        ProductCategory testProductCategory = productCategoryList.get(productCategoryList.size() - 1);
        assertThat(testProductCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductCategory.getHandle()).isEqualTo(UPDATED_HANDLE);
        assertThat(testProductCategory.getShortLabel()).isEqualTo(UPDATED_SHORT_LABEL);
        assertThat(testProductCategory.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testProductCategory.getIconFont()).isEqualTo(UPDATED_ICON_FONT);
        assertThat(testProductCategory.getIconPhoto()).isEqualTo(UPDATED_ICON_PHOTO);
        assertThat(testProductCategory.isJustForYouInd()).isEqualTo(UPDATED_JUST_FOR_YOU_IND);
        assertThat(testProductCategory.getParentCascade()).isEqualTo(UPDATED_PARENT_CASCADE);
        assertThat(testProductCategory.isActiveFlag()).isEqualTo(UPDATED_ACTIVE_FLAG);
        assertThat(testProductCategory.getLocalization()).isEqualTo(UPDATED_LOCALIZATION);
        assertThat(testProductCategory.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testProductCategory.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingProductCategory() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();

        // Create the ProductCategory
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductCategoryMockMvc.perform(put("/api/product-categories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductCategory() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        int databaseSizeBeforeDelete = productCategoryRepository.findAll().size();

        // Delete the productCategory
        restProductCategoryMockMvc.perform(delete("/api/product-categories/{id}", productCategory.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

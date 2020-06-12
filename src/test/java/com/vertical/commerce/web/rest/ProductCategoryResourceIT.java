package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.ProductCategory;
import com.vertical.commerce.domain.ProductCategory;
import com.vertical.commerce.domain.Photos;
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
import javax.persistence.EntityManager;
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

    private static final String DEFAULT_SHORT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_LABEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;
    private static final Integer SMALLER_SORT_ORDER = 1 - 1;

    private static final String DEFAULT_ICON_FONT = "AAAAAAAAAA";
    private static final String UPDATED_ICON_FONT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_JUST_FOR_YOU_IND = false;
    private static final Boolean UPDATED_JUST_FOR_YOU_IND = true;

    private static final Boolean DEFAULT_SHOW_IN_NAV_IND = false;
    private static final Boolean UPDATED_SHOW_IN_NAV_IND = true;

    private static final Boolean DEFAULT_ACTIVE_IND = false;
    private static final Boolean UPDATED_ACTIVE_IND = true;

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
            .shortLabel(DEFAULT_SHORT_LABEL)
            .sortOrder(DEFAULT_SORT_ORDER)
            .iconFont(DEFAULT_ICON_FONT)
            .justForYouInd(DEFAULT_JUST_FOR_YOU_IND)
            .showInNavInd(DEFAULT_SHOW_IN_NAV_IND)
            .activeInd(DEFAULT_ACTIVE_IND);
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
            .shortLabel(UPDATED_SHORT_LABEL)
            .sortOrder(UPDATED_SORT_ORDER)
            .iconFont(UPDATED_ICON_FONT)
            .justForYouInd(UPDATED_JUST_FOR_YOU_IND)
            .showInNavInd(UPDATED_SHOW_IN_NAV_IND)
            .activeInd(UPDATED_ACTIVE_IND);
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
        assertThat(testProductCategory.getShortLabel()).isEqualTo(DEFAULT_SHORT_LABEL);
        assertThat(testProductCategory.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testProductCategory.getIconFont()).isEqualTo(DEFAULT_ICON_FONT);
        assertThat(testProductCategory.isJustForYouInd()).isEqualTo(DEFAULT_JUST_FOR_YOU_IND);
        assertThat(testProductCategory.isShowInNavInd()).isEqualTo(DEFAULT_SHOW_IN_NAV_IND);
        assertThat(testProductCategory.isActiveInd()).isEqualTo(DEFAULT_ACTIVE_IND);
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
    public void getAllProductCategories() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList
        restProductCategoryMockMvc.perform(get("/api/product-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortLabel").value(hasItem(DEFAULT_SHORT_LABEL)))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].iconFont").value(hasItem(DEFAULT_ICON_FONT)))
            .andExpect(jsonPath("$.[*].justForYouInd").value(hasItem(DEFAULT_JUST_FOR_YOU_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].showInNavInd").value(hasItem(DEFAULT_SHOW_IN_NAV_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())));
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
            .andExpect(jsonPath("$.shortLabel").value(DEFAULT_SHORT_LABEL))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.iconFont").value(DEFAULT_ICON_FONT))
            .andExpect(jsonPath("$.justForYouInd").value(DEFAULT_JUST_FOR_YOU_IND.booleanValue()))
            .andExpect(jsonPath("$.showInNavInd").value(DEFAULT_SHOW_IN_NAV_IND.booleanValue()))
            .andExpect(jsonPath("$.activeInd").value(DEFAULT_ACTIVE_IND.booleanValue()));
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
    public void getAllProductCategoriesByShowInNavIndIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where showInNavInd equals to DEFAULT_SHOW_IN_NAV_IND
        defaultProductCategoryShouldBeFound("showInNavInd.equals=" + DEFAULT_SHOW_IN_NAV_IND);

        // Get all the productCategoryList where showInNavInd equals to UPDATED_SHOW_IN_NAV_IND
        defaultProductCategoryShouldNotBeFound("showInNavInd.equals=" + UPDATED_SHOW_IN_NAV_IND);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByShowInNavIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where showInNavInd not equals to DEFAULT_SHOW_IN_NAV_IND
        defaultProductCategoryShouldNotBeFound("showInNavInd.notEquals=" + DEFAULT_SHOW_IN_NAV_IND);

        // Get all the productCategoryList where showInNavInd not equals to UPDATED_SHOW_IN_NAV_IND
        defaultProductCategoryShouldBeFound("showInNavInd.notEquals=" + UPDATED_SHOW_IN_NAV_IND);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByShowInNavIndIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where showInNavInd in DEFAULT_SHOW_IN_NAV_IND or UPDATED_SHOW_IN_NAV_IND
        defaultProductCategoryShouldBeFound("showInNavInd.in=" + DEFAULT_SHOW_IN_NAV_IND + "," + UPDATED_SHOW_IN_NAV_IND);

        // Get all the productCategoryList where showInNavInd equals to UPDATED_SHOW_IN_NAV_IND
        defaultProductCategoryShouldNotBeFound("showInNavInd.in=" + UPDATED_SHOW_IN_NAV_IND);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByShowInNavIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where showInNavInd is not null
        defaultProductCategoryShouldBeFound("showInNavInd.specified=true");

        // Get all the productCategoryList where showInNavInd is null
        defaultProductCategoryShouldNotBeFound("showInNavInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByActiveIndIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where activeInd equals to DEFAULT_ACTIVE_IND
        defaultProductCategoryShouldBeFound("activeInd.equals=" + DEFAULT_ACTIVE_IND);

        // Get all the productCategoryList where activeInd equals to UPDATED_ACTIVE_IND
        defaultProductCategoryShouldNotBeFound("activeInd.equals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByActiveIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where activeInd not equals to DEFAULT_ACTIVE_IND
        defaultProductCategoryShouldNotBeFound("activeInd.notEquals=" + DEFAULT_ACTIVE_IND);

        // Get all the productCategoryList where activeInd not equals to UPDATED_ACTIVE_IND
        defaultProductCategoryShouldBeFound("activeInd.notEquals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByActiveIndIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where activeInd in DEFAULT_ACTIVE_IND or UPDATED_ACTIVE_IND
        defaultProductCategoryShouldBeFound("activeInd.in=" + DEFAULT_ACTIVE_IND + "," + UPDATED_ACTIVE_IND);

        // Get all the productCategoryList where activeInd equals to UPDATED_ACTIVE_IND
        defaultProductCategoryShouldNotBeFound("activeInd.in=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByActiveIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where activeInd is not null
        defaultProductCategoryShouldBeFound("activeInd.specified=true");

        // Get all the productCategoryList where activeInd is null
        defaultProductCategoryShouldNotBeFound("activeInd.specified=false");
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


    @Test
    @Transactional
    public void getAllProductCategoriesByIconIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);
        Photos icon = PhotosResourceIT.createEntity(em);
        em.persist(icon);
        em.flush();
        productCategory.setIcon(icon);
        productCategoryRepository.saveAndFlush(productCategory);
        Long iconId = icon.getId();

        // Get all the productCategoryList where icon equals to iconId
        defaultProductCategoryShouldBeFound("iconId.equals=" + iconId);

        // Get all the productCategoryList where icon equals to iconId + 1
        defaultProductCategoryShouldNotBeFound("iconId.equals=" + (iconId + 1));
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
            .andExpect(jsonPath("$.[*].shortLabel").value(hasItem(DEFAULT_SHORT_LABEL)))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].iconFont").value(hasItem(DEFAULT_ICON_FONT)))
            .andExpect(jsonPath("$.[*].justForYouInd").value(hasItem(DEFAULT_JUST_FOR_YOU_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].showInNavInd").value(hasItem(DEFAULT_SHOW_IN_NAV_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())));

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
            .shortLabel(UPDATED_SHORT_LABEL)
            .sortOrder(UPDATED_SORT_ORDER)
            .iconFont(UPDATED_ICON_FONT)
            .justForYouInd(UPDATED_JUST_FOR_YOU_IND)
            .showInNavInd(UPDATED_SHOW_IN_NAV_IND)
            .activeInd(UPDATED_ACTIVE_IND);
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
        assertThat(testProductCategory.getShortLabel()).isEqualTo(UPDATED_SHORT_LABEL);
        assertThat(testProductCategory.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testProductCategory.getIconFont()).isEqualTo(UPDATED_ICON_FONT);
        assertThat(testProductCategory.isJustForYouInd()).isEqualTo(UPDATED_JUST_FOR_YOU_IND);
        assertThat(testProductCategory.isShowInNavInd()).isEqualTo(UPDATED_SHOW_IN_NAV_IND);
        assertThat(testProductCategory.isActiveInd()).isEqualTo(UPDATED_ACTIVE_IND);
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

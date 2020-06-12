package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.ProductBrand;
import com.vertical.commerce.domain.Suppliers;
import com.vertical.commerce.domain.Photos;
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
import javax.persistence.EntityManager;
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

    private static final String DEFAULT_SHORT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_LABEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;
    private static final Integer SMALLER_SORT_ORDER = 1 - 1;

    private static final String DEFAULT_ICON_FONT = "AAAAAAAAAA";
    private static final String UPDATED_ICON_FONT = "BBBBBBBBBB";

    private static final String DEFAULT_THUMBNAIL_URL = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL_URL = "BBBBBBBBBB";

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
            .shortLabel(DEFAULT_SHORT_LABEL)
            .sortOrder(DEFAULT_SORT_ORDER)
            .iconFont(DEFAULT_ICON_FONT)
            .thumbnailUrl(DEFAULT_THUMBNAIL_URL);
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
            .shortLabel(UPDATED_SHORT_LABEL)
            .sortOrder(UPDATED_SORT_ORDER)
            .iconFont(UPDATED_ICON_FONT)
            .thumbnailUrl(UPDATED_THUMBNAIL_URL);
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
        assertThat(testProductBrand.getShortLabel()).isEqualTo(DEFAULT_SHORT_LABEL);
        assertThat(testProductBrand.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testProductBrand.getIconFont()).isEqualTo(DEFAULT_ICON_FONT);
        assertThat(testProductBrand.getThumbnailUrl()).isEqualTo(DEFAULT_THUMBNAIL_URL);
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
    public void getAllProductBrands() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList
        restProductBrandMockMvc.perform(get("/api/product-brands?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productBrand.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortLabel").value(hasItem(DEFAULT_SHORT_LABEL)))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].iconFont").value(hasItem(DEFAULT_ICON_FONT)))
            .andExpect(jsonPath("$.[*].thumbnailUrl").value(hasItem(DEFAULT_THUMBNAIL_URL)));
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
            .andExpect(jsonPath("$.shortLabel").value(DEFAULT_SHORT_LABEL))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.iconFont").value(DEFAULT_ICON_FONT))
            .andExpect(jsonPath("$.thumbnailUrl").value(DEFAULT_THUMBNAIL_URL));
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
    public void getAllProductBrandsByThumbnailUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where thumbnailUrl equals to DEFAULT_THUMBNAIL_URL
        defaultProductBrandShouldBeFound("thumbnailUrl.equals=" + DEFAULT_THUMBNAIL_URL);

        // Get all the productBrandList where thumbnailUrl equals to UPDATED_THUMBNAIL_URL
        defaultProductBrandShouldNotBeFound("thumbnailUrl.equals=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByThumbnailUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where thumbnailUrl not equals to DEFAULT_THUMBNAIL_URL
        defaultProductBrandShouldNotBeFound("thumbnailUrl.notEquals=" + DEFAULT_THUMBNAIL_URL);

        // Get all the productBrandList where thumbnailUrl not equals to UPDATED_THUMBNAIL_URL
        defaultProductBrandShouldBeFound("thumbnailUrl.notEquals=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByThumbnailUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where thumbnailUrl in DEFAULT_THUMBNAIL_URL or UPDATED_THUMBNAIL_URL
        defaultProductBrandShouldBeFound("thumbnailUrl.in=" + DEFAULT_THUMBNAIL_URL + "," + UPDATED_THUMBNAIL_URL);

        // Get all the productBrandList where thumbnailUrl equals to UPDATED_THUMBNAIL_URL
        defaultProductBrandShouldNotBeFound("thumbnailUrl.in=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByThumbnailUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where thumbnailUrl is not null
        defaultProductBrandShouldBeFound("thumbnailUrl.specified=true");

        // Get all the productBrandList where thumbnailUrl is null
        defaultProductBrandShouldNotBeFound("thumbnailUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductBrandsByThumbnailUrlContainsSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where thumbnailUrl contains DEFAULT_THUMBNAIL_URL
        defaultProductBrandShouldBeFound("thumbnailUrl.contains=" + DEFAULT_THUMBNAIL_URL);

        // Get all the productBrandList where thumbnailUrl contains UPDATED_THUMBNAIL_URL
        defaultProductBrandShouldNotBeFound("thumbnailUrl.contains=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByThumbnailUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where thumbnailUrl does not contain DEFAULT_THUMBNAIL_URL
        defaultProductBrandShouldNotBeFound("thumbnailUrl.doesNotContain=" + DEFAULT_THUMBNAIL_URL);

        // Get all the productBrandList where thumbnailUrl does not contain UPDATED_THUMBNAIL_URL
        defaultProductBrandShouldBeFound("thumbnailUrl.doesNotContain=" + UPDATED_THUMBNAIL_URL);
    }


    @Test
    @Transactional
    public void getAllProductBrandsBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);
        Suppliers supplier = SuppliersResourceIT.createEntity(em);
        em.persist(supplier);
        em.flush();
        productBrand.setSupplier(supplier);
        productBrandRepository.saveAndFlush(productBrand);
        Long supplierId = supplier.getId();

        // Get all the productBrandList where supplier equals to supplierId
        defaultProductBrandShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the productBrandList where supplier equals to supplierId + 1
        defaultProductBrandShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }


    @Test
    @Transactional
    public void getAllProductBrandsByIconIsEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);
        Photos icon = PhotosResourceIT.createEntity(em);
        em.persist(icon);
        em.flush();
        productBrand.setIcon(icon);
        productBrandRepository.saveAndFlush(productBrand);
        Long iconId = icon.getId();

        // Get all the productBrandList where icon equals to iconId
        defaultProductBrandShouldBeFound("iconId.equals=" + iconId);

        // Get all the productBrandList where icon equals to iconId + 1
        defaultProductBrandShouldNotBeFound("iconId.equals=" + (iconId + 1));
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
            .andExpect(jsonPath("$.[*].shortLabel").value(hasItem(DEFAULT_SHORT_LABEL)))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].iconFont").value(hasItem(DEFAULT_ICON_FONT)))
            .andExpect(jsonPath("$.[*].thumbnailUrl").value(hasItem(DEFAULT_THUMBNAIL_URL)));

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
            .shortLabel(UPDATED_SHORT_LABEL)
            .sortOrder(UPDATED_SORT_ORDER)
            .iconFont(UPDATED_ICON_FONT)
            .thumbnailUrl(UPDATED_THUMBNAIL_URL);
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
        assertThat(testProductBrand.getShortLabel()).isEqualTo(UPDATED_SHORT_LABEL);
        assertThat(testProductBrand.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testProductBrand.getIconFont()).isEqualTo(UPDATED_ICON_FONT);
        assertThat(testProductBrand.getThumbnailUrl()).isEqualTo(UPDATED_THUMBNAIL_URL);
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

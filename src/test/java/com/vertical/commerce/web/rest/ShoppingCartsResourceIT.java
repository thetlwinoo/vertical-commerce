package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.ShoppingCarts;
import com.vertical.commerce.domain.People;
import com.vertical.commerce.domain.ShoppingCartItems;
import com.vertical.commerce.domain.Customers;
import com.vertical.commerce.domain.SpecialDeals;
import com.vertical.commerce.repository.ShoppingCartsRepository;
import com.vertical.commerce.service.ShoppingCartsService;
import com.vertical.commerce.service.dto.ShoppingCartsDTO;
import com.vertical.commerce.service.mapper.ShoppingCartsMapper;
import com.vertical.commerce.service.dto.ShoppingCartsCriteria;
import com.vertical.commerce.service.ShoppingCartsQueryService;

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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ShoppingCartsResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ShoppingCartsResourceIT {

    private static final BigDecimal DEFAULT_TOTAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SUB_TOTAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUB_TOTAL_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_SUB_TOTAL_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_SHIPPING_FEE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_SHIPPING_FEE = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_SHIPPING_FEE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_SHIPPING_FEE_DISCOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PROMOTION_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROMOTION_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_PROMOTION_TOTAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VOUCHER_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_VOUCHER_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_VOUCHER_TOTAL = new BigDecimal(1 - 1);

    private static final String DEFAULT_PACKAGE_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_PACKAGE_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_CART_STRING = "AAAAAAAAAA";
    private static final String UPDATED_CART_STRING = "BBBBBBBBBB";

    private static final String DEFAULT_DEAL_STRING = "AAAAAAAAAA";
    private static final String UPDATED_DEAL_STRING = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ShoppingCartsRepository shoppingCartsRepository;

    @Autowired
    private ShoppingCartsMapper shoppingCartsMapper;

    @Autowired
    private ShoppingCartsService shoppingCartsService;

    @Autowired
    private ShoppingCartsQueryService shoppingCartsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShoppingCartsMockMvc;

    private ShoppingCarts shoppingCarts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShoppingCarts createEntity(EntityManager em) {
        ShoppingCarts shoppingCarts = new ShoppingCarts()
            .totalPrice(DEFAULT_TOTAL_PRICE)
            .subTotalPrice(DEFAULT_SUB_TOTAL_PRICE)
            .totalShippingFee(DEFAULT_TOTAL_SHIPPING_FEE)
            .totalShippingFeeDiscount(DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT)
            .promotionTotal(DEFAULT_PROMOTION_TOTAL)
            .voucherTotal(DEFAULT_VOUCHER_TOTAL)
            .packageDetails(DEFAULT_PACKAGE_DETAILS)
            .cartString(DEFAULT_CART_STRING)
            .dealString(DEFAULT_DEAL_STRING)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return shoppingCarts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShoppingCarts createUpdatedEntity(EntityManager em) {
        ShoppingCarts shoppingCarts = new ShoppingCarts()
            .totalPrice(UPDATED_TOTAL_PRICE)
            .subTotalPrice(UPDATED_SUB_TOTAL_PRICE)
            .totalShippingFee(UPDATED_TOTAL_SHIPPING_FEE)
            .totalShippingFeeDiscount(UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT)
            .promotionTotal(UPDATED_PROMOTION_TOTAL)
            .voucherTotal(UPDATED_VOUCHER_TOTAL)
            .packageDetails(UPDATED_PACKAGE_DETAILS)
            .cartString(UPDATED_CART_STRING)
            .dealString(UPDATED_DEAL_STRING)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return shoppingCarts;
    }

    @BeforeEach
    public void initTest() {
        shoppingCarts = createEntity(em);
    }

    @Test
    @Transactional
    public void createShoppingCarts() throws Exception {
        int databaseSizeBeforeCreate = shoppingCartsRepository.findAll().size();
        // Create the ShoppingCarts
        ShoppingCartsDTO shoppingCartsDTO = shoppingCartsMapper.toDto(shoppingCarts);
        restShoppingCartsMockMvc.perform(post("/api/shopping-carts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartsDTO)))
            .andExpect(status().isCreated());

        // Validate the ShoppingCarts in the database
        List<ShoppingCarts> shoppingCartsList = shoppingCartsRepository.findAll();
        assertThat(shoppingCartsList).hasSize(databaseSizeBeforeCreate + 1);
        ShoppingCarts testShoppingCarts = shoppingCartsList.get(shoppingCartsList.size() - 1);
        assertThat(testShoppingCarts.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testShoppingCarts.getSubTotalPrice()).isEqualTo(DEFAULT_SUB_TOTAL_PRICE);
        assertThat(testShoppingCarts.getTotalShippingFee()).isEqualTo(DEFAULT_TOTAL_SHIPPING_FEE);
        assertThat(testShoppingCarts.getTotalShippingFeeDiscount()).isEqualTo(DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT);
        assertThat(testShoppingCarts.getPromotionTotal()).isEqualTo(DEFAULT_PROMOTION_TOTAL);
        assertThat(testShoppingCarts.getVoucherTotal()).isEqualTo(DEFAULT_VOUCHER_TOTAL);
        assertThat(testShoppingCarts.getPackageDetails()).isEqualTo(DEFAULT_PACKAGE_DETAILS);
        assertThat(testShoppingCarts.getCartString()).isEqualTo(DEFAULT_CART_STRING);
        assertThat(testShoppingCarts.getDealString()).isEqualTo(DEFAULT_DEAL_STRING);
        assertThat(testShoppingCarts.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testShoppingCarts.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createShoppingCartsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shoppingCartsRepository.findAll().size();

        // Create the ShoppingCarts with an existing ID
        shoppingCarts.setId(1L);
        ShoppingCartsDTO shoppingCartsDTO = shoppingCartsMapper.toDto(shoppingCarts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShoppingCartsMockMvc.perform(post("/api/shopping-carts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShoppingCarts in the database
        List<ShoppingCarts> shoppingCartsList = shoppingCartsRepository.findAll();
        assertThat(shoppingCartsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLastEditedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingCartsRepository.findAll().size();
        // set the field null
        shoppingCarts.setLastEditedBy(null);

        // Create the ShoppingCarts, which fails.
        ShoppingCartsDTO shoppingCartsDTO = shoppingCartsMapper.toDto(shoppingCarts);


        restShoppingCartsMockMvc.perform(post("/api/shopping-carts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartsDTO)))
            .andExpect(status().isBadRequest());

        List<ShoppingCarts> shoppingCartsList = shoppingCartsRepository.findAll();
        assertThat(shoppingCartsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingCartsRepository.findAll().size();
        // set the field null
        shoppingCarts.setLastEditedWhen(null);

        // Create the ShoppingCarts, which fails.
        ShoppingCartsDTO shoppingCartsDTO = shoppingCartsMapper.toDto(shoppingCarts);


        restShoppingCartsMockMvc.perform(post("/api/shopping-carts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartsDTO)))
            .andExpect(status().isBadRequest());

        List<ShoppingCarts> shoppingCartsList = shoppingCartsRepository.findAll();
        assertThat(shoppingCartsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShoppingCarts() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList
        restShoppingCartsMockMvc.perform(get("/api/shopping-carts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingCarts.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].subTotalPrice").value(hasItem(DEFAULT_SUB_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].totalShippingFee").value(hasItem(DEFAULT_TOTAL_SHIPPING_FEE.intValue())))
            .andExpect(jsonPath("$.[*].totalShippingFeeDiscount").value(hasItem(DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].promotionTotal").value(hasItem(DEFAULT_PROMOTION_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].voucherTotal").value(hasItem(DEFAULT_VOUCHER_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].packageDetails").value(hasItem(DEFAULT_PACKAGE_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].cartString").value(hasItem(DEFAULT_CART_STRING.toString())))
            .andExpect(jsonPath("$.[*].dealString").value(hasItem(DEFAULT_DEAL_STRING.toString())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getShoppingCarts() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get the shoppingCarts
        restShoppingCartsMockMvc.perform(get("/api/shopping-carts/{id}", shoppingCarts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shoppingCarts.getId().intValue()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.intValue()))
            .andExpect(jsonPath("$.subTotalPrice").value(DEFAULT_SUB_TOTAL_PRICE.intValue()))
            .andExpect(jsonPath("$.totalShippingFee").value(DEFAULT_TOTAL_SHIPPING_FEE.intValue()))
            .andExpect(jsonPath("$.totalShippingFeeDiscount").value(DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.promotionTotal").value(DEFAULT_PROMOTION_TOTAL.intValue()))
            .andExpect(jsonPath("$.voucherTotal").value(DEFAULT_VOUCHER_TOTAL.intValue()))
            .andExpect(jsonPath("$.packageDetails").value(DEFAULT_PACKAGE_DETAILS.toString()))
            .andExpect(jsonPath("$.cartString").value(DEFAULT_CART_STRING.toString()))
            .andExpect(jsonPath("$.dealString").value(DEFAULT_DEAL_STRING.toString()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getShoppingCartsByIdFiltering() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        Long id = shoppingCarts.getId();

        defaultShoppingCartsShouldBeFound("id.equals=" + id);
        defaultShoppingCartsShouldNotBeFound("id.notEquals=" + id);

        defaultShoppingCartsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultShoppingCartsShouldNotBeFound("id.greaterThan=" + id);

        defaultShoppingCartsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultShoppingCartsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllShoppingCartsByTotalPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalPrice equals to DEFAULT_TOTAL_PRICE
        defaultShoppingCartsShouldBeFound("totalPrice.equals=" + DEFAULT_TOTAL_PRICE);

        // Get all the shoppingCartsList where totalPrice equals to UPDATED_TOTAL_PRICE
        defaultShoppingCartsShouldNotBeFound("totalPrice.equals=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalPrice not equals to DEFAULT_TOTAL_PRICE
        defaultShoppingCartsShouldNotBeFound("totalPrice.notEquals=" + DEFAULT_TOTAL_PRICE);

        // Get all the shoppingCartsList where totalPrice not equals to UPDATED_TOTAL_PRICE
        defaultShoppingCartsShouldBeFound("totalPrice.notEquals=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalPriceIsInShouldWork() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalPrice in DEFAULT_TOTAL_PRICE or UPDATED_TOTAL_PRICE
        defaultShoppingCartsShouldBeFound("totalPrice.in=" + DEFAULT_TOTAL_PRICE + "," + UPDATED_TOTAL_PRICE);

        // Get all the shoppingCartsList where totalPrice equals to UPDATED_TOTAL_PRICE
        defaultShoppingCartsShouldNotBeFound("totalPrice.in=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalPrice is not null
        defaultShoppingCartsShouldBeFound("totalPrice.specified=true");

        // Get all the shoppingCartsList where totalPrice is null
        defaultShoppingCartsShouldNotBeFound("totalPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalPrice is greater than or equal to DEFAULT_TOTAL_PRICE
        defaultShoppingCartsShouldBeFound("totalPrice.greaterThanOrEqual=" + DEFAULT_TOTAL_PRICE);

        // Get all the shoppingCartsList where totalPrice is greater than or equal to UPDATED_TOTAL_PRICE
        defaultShoppingCartsShouldNotBeFound("totalPrice.greaterThanOrEqual=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalPrice is less than or equal to DEFAULT_TOTAL_PRICE
        defaultShoppingCartsShouldBeFound("totalPrice.lessThanOrEqual=" + DEFAULT_TOTAL_PRICE);

        // Get all the shoppingCartsList where totalPrice is less than or equal to SMALLER_TOTAL_PRICE
        defaultShoppingCartsShouldNotBeFound("totalPrice.lessThanOrEqual=" + SMALLER_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalPrice is less than DEFAULT_TOTAL_PRICE
        defaultShoppingCartsShouldNotBeFound("totalPrice.lessThan=" + DEFAULT_TOTAL_PRICE);

        // Get all the shoppingCartsList where totalPrice is less than UPDATED_TOTAL_PRICE
        defaultShoppingCartsShouldBeFound("totalPrice.lessThan=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalPrice is greater than DEFAULT_TOTAL_PRICE
        defaultShoppingCartsShouldNotBeFound("totalPrice.greaterThan=" + DEFAULT_TOTAL_PRICE);

        // Get all the shoppingCartsList where totalPrice is greater than SMALLER_TOTAL_PRICE
        defaultShoppingCartsShouldBeFound("totalPrice.greaterThan=" + SMALLER_TOTAL_PRICE);
    }


    @Test
    @Transactional
    public void getAllShoppingCartsBySubTotalPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where subTotalPrice equals to DEFAULT_SUB_TOTAL_PRICE
        defaultShoppingCartsShouldBeFound("subTotalPrice.equals=" + DEFAULT_SUB_TOTAL_PRICE);

        // Get all the shoppingCartsList where subTotalPrice equals to UPDATED_SUB_TOTAL_PRICE
        defaultShoppingCartsShouldNotBeFound("subTotalPrice.equals=" + UPDATED_SUB_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsBySubTotalPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where subTotalPrice not equals to DEFAULT_SUB_TOTAL_PRICE
        defaultShoppingCartsShouldNotBeFound("subTotalPrice.notEquals=" + DEFAULT_SUB_TOTAL_PRICE);

        // Get all the shoppingCartsList where subTotalPrice not equals to UPDATED_SUB_TOTAL_PRICE
        defaultShoppingCartsShouldBeFound("subTotalPrice.notEquals=" + UPDATED_SUB_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsBySubTotalPriceIsInShouldWork() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where subTotalPrice in DEFAULT_SUB_TOTAL_PRICE or UPDATED_SUB_TOTAL_PRICE
        defaultShoppingCartsShouldBeFound("subTotalPrice.in=" + DEFAULT_SUB_TOTAL_PRICE + "," + UPDATED_SUB_TOTAL_PRICE);

        // Get all the shoppingCartsList where subTotalPrice equals to UPDATED_SUB_TOTAL_PRICE
        defaultShoppingCartsShouldNotBeFound("subTotalPrice.in=" + UPDATED_SUB_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsBySubTotalPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where subTotalPrice is not null
        defaultShoppingCartsShouldBeFound("subTotalPrice.specified=true");

        // Get all the shoppingCartsList where subTotalPrice is null
        defaultShoppingCartsShouldNotBeFound("subTotalPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllShoppingCartsBySubTotalPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where subTotalPrice is greater than or equal to DEFAULT_SUB_TOTAL_PRICE
        defaultShoppingCartsShouldBeFound("subTotalPrice.greaterThanOrEqual=" + DEFAULT_SUB_TOTAL_PRICE);

        // Get all the shoppingCartsList where subTotalPrice is greater than or equal to UPDATED_SUB_TOTAL_PRICE
        defaultShoppingCartsShouldNotBeFound("subTotalPrice.greaterThanOrEqual=" + UPDATED_SUB_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsBySubTotalPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where subTotalPrice is less than or equal to DEFAULT_SUB_TOTAL_PRICE
        defaultShoppingCartsShouldBeFound("subTotalPrice.lessThanOrEqual=" + DEFAULT_SUB_TOTAL_PRICE);

        // Get all the shoppingCartsList where subTotalPrice is less than or equal to SMALLER_SUB_TOTAL_PRICE
        defaultShoppingCartsShouldNotBeFound("subTotalPrice.lessThanOrEqual=" + SMALLER_SUB_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsBySubTotalPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where subTotalPrice is less than DEFAULT_SUB_TOTAL_PRICE
        defaultShoppingCartsShouldNotBeFound("subTotalPrice.lessThan=" + DEFAULT_SUB_TOTAL_PRICE);

        // Get all the shoppingCartsList where subTotalPrice is less than UPDATED_SUB_TOTAL_PRICE
        defaultShoppingCartsShouldBeFound("subTotalPrice.lessThan=" + UPDATED_SUB_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsBySubTotalPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where subTotalPrice is greater than DEFAULT_SUB_TOTAL_PRICE
        defaultShoppingCartsShouldNotBeFound("subTotalPrice.greaterThan=" + DEFAULT_SUB_TOTAL_PRICE);

        // Get all the shoppingCartsList where subTotalPrice is greater than SMALLER_SUB_TOTAL_PRICE
        defaultShoppingCartsShouldBeFound("subTotalPrice.greaterThan=" + SMALLER_SUB_TOTAL_PRICE);
    }


    @Test
    @Transactional
    public void getAllShoppingCartsByTotalShippingFeeIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalShippingFee equals to DEFAULT_TOTAL_SHIPPING_FEE
        defaultShoppingCartsShouldBeFound("totalShippingFee.equals=" + DEFAULT_TOTAL_SHIPPING_FEE);

        // Get all the shoppingCartsList where totalShippingFee equals to UPDATED_TOTAL_SHIPPING_FEE
        defaultShoppingCartsShouldNotBeFound("totalShippingFee.equals=" + UPDATED_TOTAL_SHIPPING_FEE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalShippingFeeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalShippingFee not equals to DEFAULT_TOTAL_SHIPPING_FEE
        defaultShoppingCartsShouldNotBeFound("totalShippingFee.notEquals=" + DEFAULT_TOTAL_SHIPPING_FEE);

        // Get all the shoppingCartsList where totalShippingFee not equals to UPDATED_TOTAL_SHIPPING_FEE
        defaultShoppingCartsShouldBeFound("totalShippingFee.notEquals=" + UPDATED_TOTAL_SHIPPING_FEE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalShippingFeeIsInShouldWork() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalShippingFee in DEFAULT_TOTAL_SHIPPING_FEE or UPDATED_TOTAL_SHIPPING_FEE
        defaultShoppingCartsShouldBeFound("totalShippingFee.in=" + DEFAULT_TOTAL_SHIPPING_FEE + "," + UPDATED_TOTAL_SHIPPING_FEE);

        // Get all the shoppingCartsList where totalShippingFee equals to UPDATED_TOTAL_SHIPPING_FEE
        defaultShoppingCartsShouldNotBeFound("totalShippingFee.in=" + UPDATED_TOTAL_SHIPPING_FEE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalShippingFeeIsNullOrNotNull() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalShippingFee is not null
        defaultShoppingCartsShouldBeFound("totalShippingFee.specified=true");

        // Get all the shoppingCartsList where totalShippingFee is null
        defaultShoppingCartsShouldNotBeFound("totalShippingFee.specified=false");
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalShippingFeeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalShippingFee is greater than or equal to DEFAULT_TOTAL_SHIPPING_FEE
        defaultShoppingCartsShouldBeFound("totalShippingFee.greaterThanOrEqual=" + DEFAULT_TOTAL_SHIPPING_FEE);

        // Get all the shoppingCartsList where totalShippingFee is greater than or equal to UPDATED_TOTAL_SHIPPING_FEE
        defaultShoppingCartsShouldNotBeFound("totalShippingFee.greaterThanOrEqual=" + UPDATED_TOTAL_SHIPPING_FEE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalShippingFeeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalShippingFee is less than or equal to DEFAULT_TOTAL_SHIPPING_FEE
        defaultShoppingCartsShouldBeFound("totalShippingFee.lessThanOrEqual=" + DEFAULT_TOTAL_SHIPPING_FEE);

        // Get all the shoppingCartsList where totalShippingFee is less than or equal to SMALLER_TOTAL_SHIPPING_FEE
        defaultShoppingCartsShouldNotBeFound("totalShippingFee.lessThanOrEqual=" + SMALLER_TOTAL_SHIPPING_FEE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalShippingFeeIsLessThanSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalShippingFee is less than DEFAULT_TOTAL_SHIPPING_FEE
        defaultShoppingCartsShouldNotBeFound("totalShippingFee.lessThan=" + DEFAULT_TOTAL_SHIPPING_FEE);

        // Get all the shoppingCartsList where totalShippingFee is less than UPDATED_TOTAL_SHIPPING_FEE
        defaultShoppingCartsShouldBeFound("totalShippingFee.lessThan=" + UPDATED_TOTAL_SHIPPING_FEE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalShippingFeeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalShippingFee is greater than DEFAULT_TOTAL_SHIPPING_FEE
        defaultShoppingCartsShouldNotBeFound("totalShippingFee.greaterThan=" + DEFAULT_TOTAL_SHIPPING_FEE);

        // Get all the shoppingCartsList where totalShippingFee is greater than SMALLER_TOTAL_SHIPPING_FEE
        defaultShoppingCartsShouldBeFound("totalShippingFee.greaterThan=" + SMALLER_TOTAL_SHIPPING_FEE);
    }


    @Test
    @Transactional
    public void getAllShoppingCartsByTotalShippingFeeDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalShippingFeeDiscount equals to DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultShoppingCartsShouldBeFound("totalShippingFeeDiscount.equals=" + DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT);

        // Get all the shoppingCartsList where totalShippingFeeDiscount equals to UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultShoppingCartsShouldNotBeFound("totalShippingFeeDiscount.equals=" + UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalShippingFeeDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalShippingFeeDiscount not equals to DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultShoppingCartsShouldNotBeFound("totalShippingFeeDiscount.notEquals=" + DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT);

        // Get all the shoppingCartsList where totalShippingFeeDiscount not equals to UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultShoppingCartsShouldBeFound("totalShippingFeeDiscount.notEquals=" + UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalShippingFeeDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalShippingFeeDiscount in DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT or UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultShoppingCartsShouldBeFound("totalShippingFeeDiscount.in=" + DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT + "," + UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT);

        // Get all the shoppingCartsList where totalShippingFeeDiscount equals to UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultShoppingCartsShouldNotBeFound("totalShippingFeeDiscount.in=" + UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalShippingFeeDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalShippingFeeDiscount is not null
        defaultShoppingCartsShouldBeFound("totalShippingFeeDiscount.specified=true");

        // Get all the shoppingCartsList where totalShippingFeeDiscount is null
        defaultShoppingCartsShouldNotBeFound("totalShippingFeeDiscount.specified=false");
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalShippingFeeDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalShippingFeeDiscount is greater than or equal to DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultShoppingCartsShouldBeFound("totalShippingFeeDiscount.greaterThanOrEqual=" + DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT);

        // Get all the shoppingCartsList where totalShippingFeeDiscount is greater than or equal to UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultShoppingCartsShouldNotBeFound("totalShippingFeeDiscount.greaterThanOrEqual=" + UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalShippingFeeDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalShippingFeeDiscount is less than or equal to DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultShoppingCartsShouldBeFound("totalShippingFeeDiscount.lessThanOrEqual=" + DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT);

        // Get all the shoppingCartsList where totalShippingFeeDiscount is less than or equal to SMALLER_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultShoppingCartsShouldNotBeFound("totalShippingFeeDiscount.lessThanOrEqual=" + SMALLER_TOTAL_SHIPPING_FEE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalShippingFeeDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalShippingFeeDiscount is less than DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultShoppingCartsShouldNotBeFound("totalShippingFeeDiscount.lessThan=" + DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT);

        // Get all the shoppingCartsList where totalShippingFeeDiscount is less than UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultShoppingCartsShouldBeFound("totalShippingFeeDiscount.lessThan=" + UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalShippingFeeDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalShippingFeeDiscount is greater than DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultShoppingCartsShouldNotBeFound("totalShippingFeeDiscount.greaterThan=" + DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT);

        // Get all the shoppingCartsList where totalShippingFeeDiscount is greater than SMALLER_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultShoppingCartsShouldBeFound("totalShippingFeeDiscount.greaterThan=" + SMALLER_TOTAL_SHIPPING_FEE_DISCOUNT);
    }


    @Test
    @Transactional
    public void getAllShoppingCartsByPromotionTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where promotionTotal equals to DEFAULT_PROMOTION_TOTAL
        defaultShoppingCartsShouldBeFound("promotionTotal.equals=" + DEFAULT_PROMOTION_TOTAL);

        // Get all the shoppingCartsList where promotionTotal equals to UPDATED_PROMOTION_TOTAL
        defaultShoppingCartsShouldNotBeFound("promotionTotal.equals=" + UPDATED_PROMOTION_TOTAL);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByPromotionTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where promotionTotal not equals to DEFAULT_PROMOTION_TOTAL
        defaultShoppingCartsShouldNotBeFound("promotionTotal.notEquals=" + DEFAULT_PROMOTION_TOTAL);

        // Get all the shoppingCartsList where promotionTotal not equals to UPDATED_PROMOTION_TOTAL
        defaultShoppingCartsShouldBeFound("promotionTotal.notEquals=" + UPDATED_PROMOTION_TOTAL);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByPromotionTotalIsInShouldWork() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where promotionTotal in DEFAULT_PROMOTION_TOTAL or UPDATED_PROMOTION_TOTAL
        defaultShoppingCartsShouldBeFound("promotionTotal.in=" + DEFAULT_PROMOTION_TOTAL + "," + UPDATED_PROMOTION_TOTAL);

        // Get all the shoppingCartsList where promotionTotal equals to UPDATED_PROMOTION_TOTAL
        defaultShoppingCartsShouldNotBeFound("promotionTotal.in=" + UPDATED_PROMOTION_TOTAL);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByPromotionTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where promotionTotal is not null
        defaultShoppingCartsShouldBeFound("promotionTotal.specified=true");

        // Get all the shoppingCartsList where promotionTotal is null
        defaultShoppingCartsShouldNotBeFound("promotionTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByPromotionTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where promotionTotal is greater than or equal to DEFAULT_PROMOTION_TOTAL
        defaultShoppingCartsShouldBeFound("promotionTotal.greaterThanOrEqual=" + DEFAULT_PROMOTION_TOTAL);

        // Get all the shoppingCartsList where promotionTotal is greater than or equal to UPDATED_PROMOTION_TOTAL
        defaultShoppingCartsShouldNotBeFound("promotionTotal.greaterThanOrEqual=" + UPDATED_PROMOTION_TOTAL);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByPromotionTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where promotionTotal is less than or equal to DEFAULT_PROMOTION_TOTAL
        defaultShoppingCartsShouldBeFound("promotionTotal.lessThanOrEqual=" + DEFAULT_PROMOTION_TOTAL);

        // Get all the shoppingCartsList where promotionTotal is less than or equal to SMALLER_PROMOTION_TOTAL
        defaultShoppingCartsShouldNotBeFound("promotionTotal.lessThanOrEqual=" + SMALLER_PROMOTION_TOTAL);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByPromotionTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where promotionTotal is less than DEFAULT_PROMOTION_TOTAL
        defaultShoppingCartsShouldNotBeFound("promotionTotal.lessThan=" + DEFAULT_PROMOTION_TOTAL);

        // Get all the shoppingCartsList where promotionTotal is less than UPDATED_PROMOTION_TOTAL
        defaultShoppingCartsShouldBeFound("promotionTotal.lessThan=" + UPDATED_PROMOTION_TOTAL);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByPromotionTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where promotionTotal is greater than DEFAULT_PROMOTION_TOTAL
        defaultShoppingCartsShouldNotBeFound("promotionTotal.greaterThan=" + DEFAULT_PROMOTION_TOTAL);

        // Get all the shoppingCartsList where promotionTotal is greater than SMALLER_PROMOTION_TOTAL
        defaultShoppingCartsShouldBeFound("promotionTotal.greaterThan=" + SMALLER_PROMOTION_TOTAL);
    }


    @Test
    @Transactional
    public void getAllShoppingCartsByVoucherTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where voucherTotal equals to DEFAULT_VOUCHER_TOTAL
        defaultShoppingCartsShouldBeFound("voucherTotal.equals=" + DEFAULT_VOUCHER_TOTAL);

        // Get all the shoppingCartsList where voucherTotal equals to UPDATED_VOUCHER_TOTAL
        defaultShoppingCartsShouldNotBeFound("voucherTotal.equals=" + UPDATED_VOUCHER_TOTAL);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByVoucherTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where voucherTotal not equals to DEFAULT_VOUCHER_TOTAL
        defaultShoppingCartsShouldNotBeFound("voucherTotal.notEquals=" + DEFAULT_VOUCHER_TOTAL);

        // Get all the shoppingCartsList where voucherTotal not equals to UPDATED_VOUCHER_TOTAL
        defaultShoppingCartsShouldBeFound("voucherTotal.notEquals=" + UPDATED_VOUCHER_TOTAL);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByVoucherTotalIsInShouldWork() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where voucherTotal in DEFAULT_VOUCHER_TOTAL or UPDATED_VOUCHER_TOTAL
        defaultShoppingCartsShouldBeFound("voucherTotal.in=" + DEFAULT_VOUCHER_TOTAL + "," + UPDATED_VOUCHER_TOTAL);

        // Get all the shoppingCartsList where voucherTotal equals to UPDATED_VOUCHER_TOTAL
        defaultShoppingCartsShouldNotBeFound("voucherTotal.in=" + UPDATED_VOUCHER_TOTAL);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByVoucherTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where voucherTotal is not null
        defaultShoppingCartsShouldBeFound("voucherTotal.specified=true");

        // Get all the shoppingCartsList where voucherTotal is null
        defaultShoppingCartsShouldNotBeFound("voucherTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByVoucherTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where voucherTotal is greater than or equal to DEFAULT_VOUCHER_TOTAL
        defaultShoppingCartsShouldBeFound("voucherTotal.greaterThanOrEqual=" + DEFAULT_VOUCHER_TOTAL);

        // Get all the shoppingCartsList where voucherTotal is greater than or equal to UPDATED_VOUCHER_TOTAL
        defaultShoppingCartsShouldNotBeFound("voucherTotal.greaterThanOrEqual=" + UPDATED_VOUCHER_TOTAL);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByVoucherTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where voucherTotal is less than or equal to DEFAULT_VOUCHER_TOTAL
        defaultShoppingCartsShouldBeFound("voucherTotal.lessThanOrEqual=" + DEFAULT_VOUCHER_TOTAL);

        // Get all the shoppingCartsList where voucherTotal is less than or equal to SMALLER_VOUCHER_TOTAL
        defaultShoppingCartsShouldNotBeFound("voucherTotal.lessThanOrEqual=" + SMALLER_VOUCHER_TOTAL);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByVoucherTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where voucherTotal is less than DEFAULT_VOUCHER_TOTAL
        defaultShoppingCartsShouldNotBeFound("voucherTotal.lessThan=" + DEFAULT_VOUCHER_TOTAL);

        // Get all the shoppingCartsList where voucherTotal is less than UPDATED_VOUCHER_TOTAL
        defaultShoppingCartsShouldBeFound("voucherTotal.lessThan=" + UPDATED_VOUCHER_TOTAL);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByVoucherTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where voucherTotal is greater than DEFAULT_VOUCHER_TOTAL
        defaultShoppingCartsShouldNotBeFound("voucherTotal.greaterThan=" + DEFAULT_VOUCHER_TOTAL);

        // Get all the shoppingCartsList where voucherTotal is greater than SMALLER_VOUCHER_TOTAL
        defaultShoppingCartsShouldBeFound("voucherTotal.greaterThan=" + SMALLER_VOUCHER_TOTAL);
    }


    @Test
    @Transactional
    public void getAllShoppingCartsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultShoppingCartsShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the shoppingCartsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultShoppingCartsShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultShoppingCartsShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the shoppingCartsList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultShoppingCartsShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultShoppingCartsShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the shoppingCartsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultShoppingCartsShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where lastEditedBy is not null
        defaultShoppingCartsShouldBeFound("lastEditedBy.specified=true");

        // Get all the shoppingCartsList where lastEditedBy is null
        defaultShoppingCartsShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllShoppingCartsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultShoppingCartsShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the shoppingCartsList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultShoppingCartsShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultShoppingCartsShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the shoppingCartsList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultShoppingCartsShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllShoppingCartsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultShoppingCartsShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the shoppingCartsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultShoppingCartsShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultShoppingCartsShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the shoppingCartsList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultShoppingCartsShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultShoppingCartsShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the shoppingCartsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultShoppingCartsShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where lastEditedWhen is not null
        defaultShoppingCartsShouldBeFound("lastEditedWhen.specified=true");

        // Get all the shoppingCartsList where lastEditedWhen is null
        defaultShoppingCartsShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByCartUserIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);
        People cartUser = PeopleResourceIT.createEntity(em);
        em.persist(cartUser);
        em.flush();
        shoppingCarts.setCartUser(cartUser);
        shoppingCartsRepository.saveAndFlush(shoppingCarts);
        Long cartUserId = cartUser.getId();

        // Get all the shoppingCartsList where cartUser equals to cartUserId
        defaultShoppingCartsShouldBeFound("cartUserId.equals=" + cartUserId);

        // Get all the shoppingCartsList where cartUser equals to cartUserId + 1
        defaultShoppingCartsShouldNotBeFound("cartUserId.equals=" + (cartUserId + 1));
    }


    @Test
    @Transactional
    public void getAllShoppingCartsByCartItemListIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);
        ShoppingCartItems cartItemList = ShoppingCartItemsResourceIT.createEntity(em);
        em.persist(cartItemList);
        em.flush();
        shoppingCarts.addCartItemList(cartItemList);
        shoppingCartsRepository.saveAndFlush(shoppingCarts);
        Long cartItemListId = cartItemList.getId();

        // Get all the shoppingCartsList where cartItemList equals to cartItemListId
        defaultShoppingCartsShouldBeFound("cartItemListId.equals=" + cartItemListId);

        // Get all the shoppingCartsList where cartItemList equals to cartItemListId + 1
        defaultShoppingCartsShouldNotBeFound("cartItemListId.equals=" + (cartItemListId + 1));
    }


    @Test
    @Transactional
    public void getAllShoppingCartsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);
        Customers customer = CustomersResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        shoppingCarts.setCustomer(customer);
        shoppingCartsRepository.saveAndFlush(shoppingCarts);
        Long customerId = customer.getId();

        // Get all the shoppingCartsList where customer equals to customerId
        defaultShoppingCartsShouldBeFound("customerId.equals=" + customerId);

        // Get all the shoppingCartsList where customer equals to customerId + 1
        defaultShoppingCartsShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }


    @Test
    @Transactional
    public void getAllShoppingCartsBySpecialDealsIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);
        SpecialDeals specialDeals = SpecialDealsResourceIT.createEntity(em);
        em.persist(specialDeals);
        em.flush();
        shoppingCarts.setSpecialDeals(specialDeals);
        shoppingCartsRepository.saveAndFlush(shoppingCarts);
        Long specialDealsId = specialDeals.getId();

        // Get all the shoppingCartsList where specialDeals equals to specialDealsId
        defaultShoppingCartsShouldBeFound("specialDealsId.equals=" + specialDealsId);

        // Get all the shoppingCartsList where specialDeals equals to specialDealsId + 1
        defaultShoppingCartsShouldNotBeFound("specialDealsId.equals=" + (specialDealsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultShoppingCartsShouldBeFound(String filter) throws Exception {
        restShoppingCartsMockMvc.perform(get("/api/shopping-carts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingCarts.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].subTotalPrice").value(hasItem(DEFAULT_SUB_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].totalShippingFee").value(hasItem(DEFAULT_TOTAL_SHIPPING_FEE.intValue())))
            .andExpect(jsonPath("$.[*].totalShippingFeeDiscount").value(hasItem(DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].promotionTotal").value(hasItem(DEFAULT_PROMOTION_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].voucherTotal").value(hasItem(DEFAULT_VOUCHER_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].packageDetails").value(hasItem(DEFAULT_PACKAGE_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].cartString").value(hasItem(DEFAULT_CART_STRING.toString())))
            .andExpect(jsonPath("$.[*].dealString").value(hasItem(DEFAULT_DEAL_STRING.toString())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restShoppingCartsMockMvc.perform(get("/api/shopping-carts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultShoppingCartsShouldNotBeFound(String filter) throws Exception {
        restShoppingCartsMockMvc.perform(get("/api/shopping-carts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restShoppingCartsMockMvc.perform(get("/api/shopping-carts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingShoppingCarts() throws Exception {
        // Get the shoppingCarts
        restShoppingCartsMockMvc.perform(get("/api/shopping-carts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShoppingCarts() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        int databaseSizeBeforeUpdate = shoppingCartsRepository.findAll().size();

        // Update the shoppingCarts
        ShoppingCarts updatedShoppingCarts = shoppingCartsRepository.findById(shoppingCarts.getId()).get();
        // Disconnect from session so that the updates on updatedShoppingCarts are not directly saved in db
        em.detach(updatedShoppingCarts);
        updatedShoppingCarts
            .totalPrice(UPDATED_TOTAL_PRICE)
            .subTotalPrice(UPDATED_SUB_TOTAL_PRICE)
            .totalShippingFee(UPDATED_TOTAL_SHIPPING_FEE)
            .totalShippingFeeDiscount(UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT)
            .promotionTotal(UPDATED_PROMOTION_TOTAL)
            .voucherTotal(UPDATED_VOUCHER_TOTAL)
            .packageDetails(UPDATED_PACKAGE_DETAILS)
            .cartString(UPDATED_CART_STRING)
            .dealString(UPDATED_DEAL_STRING)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        ShoppingCartsDTO shoppingCartsDTO = shoppingCartsMapper.toDto(updatedShoppingCarts);

        restShoppingCartsMockMvc.perform(put("/api/shopping-carts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartsDTO)))
            .andExpect(status().isOk());

        // Validate the ShoppingCarts in the database
        List<ShoppingCarts> shoppingCartsList = shoppingCartsRepository.findAll();
        assertThat(shoppingCartsList).hasSize(databaseSizeBeforeUpdate);
        ShoppingCarts testShoppingCarts = shoppingCartsList.get(shoppingCartsList.size() - 1);
        assertThat(testShoppingCarts.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testShoppingCarts.getSubTotalPrice()).isEqualTo(UPDATED_SUB_TOTAL_PRICE);
        assertThat(testShoppingCarts.getTotalShippingFee()).isEqualTo(UPDATED_TOTAL_SHIPPING_FEE);
        assertThat(testShoppingCarts.getTotalShippingFeeDiscount()).isEqualTo(UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT);
        assertThat(testShoppingCarts.getPromotionTotal()).isEqualTo(UPDATED_PROMOTION_TOTAL);
        assertThat(testShoppingCarts.getVoucherTotal()).isEqualTo(UPDATED_VOUCHER_TOTAL);
        assertThat(testShoppingCarts.getPackageDetails()).isEqualTo(UPDATED_PACKAGE_DETAILS);
        assertThat(testShoppingCarts.getCartString()).isEqualTo(UPDATED_CART_STRING);
        assertThat(testShoppingCarts.getDealString()).isEqualTo(UPDATED_DEAL_STRING);
        assertThat(testShoppingCarts.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testShoppingCarts.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingShoppingCarts() throws Exception {
        int databaseSizeBeforeUpdate = shoppingCartsRepository.findAll().size();

        // Create the ShoppingCarts
        ShoppingCartsDTO shoppingCartsDTO = shoppingCartsMapper.toDto(shoppingCarts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShoppingCartsMockMvc.perform(put("/api/shopping-carts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShoppingCarts in the database
        List<ShoppingCarts> shoppingCartsList = shoppingCartsRepository.findAll();
        assertThat(shoppingCartsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShoppingCarts() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        int databaseSizeBeforeDelete = shoppingCartsRepository.findAll().size();

        // Delete the shoppingCarts
        restShoppingCartsMockMvc.perform(delete("/api/shopping-carts/{id}", shoppingCarts.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShoppingCarts> shoppingCartsList = shoppingCartsRepository.findAll();
        assertThat(shoppingCartsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

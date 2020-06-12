package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.StockItemHoldings;
import com.vertical.commerce.domain.StockItems;
import com.vertical.commerce.repository.StockItemHoldingsRepository;
import com.vertical.commerce.service.StockItemHoldingsService;
import com.vertical.commerce.service.dto.StockItemHoldingsDTO;
import com.vertical.commerce.service.mapper.StockItemHoldingsMapper;
import com.vertical.commerce.service.dto.StockItemHoldingsCriteria;
import com.vertical.commerce.service.StockItemHoldingsQueryService;

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
 * Integration tests for the {@link StockItemHoldingsResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class StockItemHoldingsResourceIT {

    private static final Integer DEFAULT_QUANTITY_ON_HAND = 1;
    private static final Integer UPDATED_QUANTITY_ON_HAND = 2;
    private static final Integer SMALLER_QUANTITY_ON_HAND = 1 - 1;

    private static final String DEFAULT_BIN_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_BIN_LOCATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_LAST_STOCK_TAKE_QUANTITY = 1;
    private static final Integer UPDATED_LAST_STOCK_TAKE_QUANTITY = 2;
    private static final Integer SMALLER_LAST_STOCK_TAKE_QUANTITY = 1 - 1;

    private static final BigDecimal DEFAULT_LAST_COST_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LAST_COST_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_LAST_COST_PRICE = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_REORDER_LEVEL = 1;
    private static final Integer UPDATED_REORDER_LEVEL = 2;
    private static final Integer SMALLER_REORDER_LEVEL = 1 - 1;

    private static final Integer DEFAULT_TARGER_STOCK_LEVEL = 1;
    private static final Integer UPDATED_TARGER_STOCK_LEVEL = 2;
    private static final Integer SMALLER_TARGER_STOCK_LEVEL = 1 - 1;

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private StockItemHoldingsRepository stockItemHoldingsRepository;

    @Autowired
    private StockItemHoldingsMapper stockItemHoldingsMapper;

    @Autowired
    private StockItemHoldingsService stockItemHoldingsService;

    @Autowired
    private StockItemHoldingsQueryService stockItemHoldingsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStockItemHoldingsMockMvc;

    private StockItemHoldings stockItemHoldings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockItemHoldings createEntity(EntityManager em) {
        StockItemHoldings stockItemHoldings = new StockItemHoldings()
            .quantityOnHand(DEFAULT_QUANTITY_ON_HAND)
            .binLocation(DEFAULT_BIN_LOCATION)
            .lastStockTakeQuantity(DEFAULT_LAST_STOCK_TAKE_QUANTITY)
            .lastCostPrice(DEFAULT_LAST_COST_PRICE)
            .reorderLevel(DEFAULT_REORDER_LEVEL)
            .targerStockLevel(DEFAULT_TARGER_STOCK_LEVEL)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return stockItemHoldings;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockItemHoldings createUpdatedEntity(EntityManager em) {
        StockItemHoldings stockItemHoldings = new StockItemHoldings()
            .quantityOnHand(UPDATED_QUANTITY_ON_HAND)
            .binLocation(UPDATED_BIN_LOCATION)
            .lastStockTakeQuantity(UPDATED_LAST_STOCK_TAKE_QUANTITY)
            .lastCostPrice(UPDATED_LAST_COST_PRICE)
            .reorderLevel(UPDATED_REORDER_LEVEL)
            .targerStockLevel(UPDATED_TARGER_STOCK_LEVEL)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return stockItemHoldings;
    }

    @BeforeEach
    public void initTest() {
        stockItemHoldings = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockItemHoldings() throws Exception {
        int databaseSizeBeforeCreate = stockItemHoldingsRepository.findAll().size();
        // Create the StockItemHoldings
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);
        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isCreated());

        // Validate the StockItemHoldings in the database
        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeCreate + 1);
        StockItemHoldings testStockItemHoldings = stockItemHoldingsList.get(stockItemHoldingsList.size() - 1);
        assertThat(testStockItemHoldings.getQuantityOnHand()).isEqualTo(DEFAULT_QUANTITY_ON_HAND);
        assertThat(testStockItemHoldings.getBinLocation()).isEqualTo(DEFAULT_BIN_LOCATION);
        assertThat(testStockItemHoldings.getLastStockTakeQuantity()).isEqualTo(DEFAULT_LAST_STOCK_TAKE_QUANTITY);
        assertThat(testStockItemHoldings.getLastCostPrice()).isEqualTo(DEFAULT_LAST_COST_PRICE);
        assertThat(testStockItemHoldings.getReorderLevel()).isEqualTo(DEFAULT_REORDER_LEVEL);
        assertThat(testStockItemHoldings.getTargerStockLevel()).isEqualTo(DEFAULT_TARGER_STOCK_LEVEL);
        assertThat(testStockItemHoldings.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testStockItemHoldings.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createStockItemHoldingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockItemHoldingsRepository.findAll().size();

        // Create the StockItemHoldings with an existing ID
        stockItemHoldings.setId(1L);
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItemHoldings in the database
        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantityOnHandIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemHoldingsRepository.findAll().size();
        // set the field null
        stockItemHoldings.setQuantityOnHand(null);

        // Create the StockItemHoldings, which fails.
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);


        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBinLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemHoldingsRepository.findAll().size();
        // set the field null
        stockItemHoldings.setBinLocation(null);

        // Create the StockItemHoldings, which fails.
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);


        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastStockTakeQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemHoldingsRepository.findAll().size();
        // set the field null
        stockItemHoldings.setLastStockTakeQuantity(null);

        // Create the StockItemHoldings, which fails.
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);


        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastCostPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemHoldingsRepository.findAll().size();
        // set the field null
        stockItemHoldings.setLastCostPrice(null);

        // Create the StockItemHoldings, which fails.
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);


        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReorderLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemHoldingsRepository.findAll().size();
        // set the field null
        stockItemHoldings.setReorderLevel(null);

        // Create the StockItemHoldings, which fails.
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);


        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTargerStockLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemHoldingsRepository.findAll().size();
        // set the field null
        stockItemHoldings.setTargerStockLevel(null);

        // Create the StockItemHoldings, which fails.
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);


        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemHoldingsRepository.findAll().size();
        // set the field null
        stockItemHoldings.setLastEditedBy(null);

        // Create the StockItemHoldings, which fails.
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);


        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemHoldingsRepository.findAll().size();
        // set the field null
        stockItemHoldings.setLastEditedWhen(null);

        // Create the StockItemHoldings, which fails.
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);


        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldings() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList
        restStockItemHoldingsMockMvc.perform(get("/api/stock-item-holdings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockItemHoldings.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantityOnHand").value(hasItem(DEFAULT_QUANTITY_ON_HAND)))
            .andExpect(jsonPath("$.[*].binLocation").value(hasItem(DEFAULT_BIN_LOCATION)))
            .andExpect(jsonPath("$.[*].lastStockTakeQuantity").value(hasItem(DEFAULT_LAST_STOCK_TAKE_QUANTITY)))
            .andExpect(jsonPath("$.[*].lastCostPrice").value(hasItem(DEFAULT_LAST_COST_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].reorderLevel").value(hasItem(DEFAULT_REORDER_LEVEL)))
            .andExpect(jsonPath("$.[*].targerStockLevel").value(hasItem(DEFAULT_TARGER_STOCK_LEVEL)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getStockItemHoldings() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get the stockItemHoldings
        restStockItemHoldingsMockMvc.perform(get("/api/stock-item-holdings/{id}", stockItemHoldings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stockItemHoldings.getId().intValue()))
            .andExpect(jsonPath("$.quantityOnHand").value(DEFAULT_QUANTITY_ON_HAND))
            .andExpect(jsonPath("$.binLocation").value(DEFAULT_BIN_LOCATION))
            .andExpect(jsonPath("$.lastStockTakeQuantity").value(DEFAULT_LAST_STOCK_TAKE_QUANTITY))
            .andExpect(jsonPath("$.lastCostPrice").value(DEFAULT_LAST_COST_PRICE.intValue()))
            .andExpect(jsonPath("$.reorderLevel").value(DEFAULT_REORDER_LEVEL))
            .andExpect(jsonPath("$.targerStockLevel").value(DEFAULT_TARGER_STOCK_LEVEL))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getStockItemHoldingsByIdFiltering() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        Long id = stockItemHoldings.getId();

        defaultStockItemHoldingsShouldBeFound("id.equals=" + id);
        defaultStockItemHoldingsShouldNotBeFound("id.notEquals=" + id);

        defaultStockItemHoldingsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStockItemHoldingsShouldNotBeFound("id.greaterThan=" + id);

        defaultStockItemHoldingsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStockItemHoldingsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStockItemHoldingsByQuantityOnHandIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where quantityOnHand equals to DEFAULT_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldBeFound("quantityOnHand.equals=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemHoldingsList where quantityOnHand equals to UPDATED_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldNotBeFound("quantityOnHand.equals=" + UPDATED_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByQuantityOnHandIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where quantityOnHand not equals to DEFAULT_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldNotBeFound("quantityOnHand.notEquals=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemHoldingsList where quantityOnHand not equals to UPDATED_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldBeFound("quantityOnHand.notEquals=" + UPDATED_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByQuantityOnHandIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where quantityOnHand in DEFAULT_QUANTITY_ON_HAND or UPDATED_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldBeFound("quantityOnHand.in=" + DEFAULT_QUANTITY_ON_HAND + "," + UPDATED_QUANTITY_ON_HAND);

        // Get all the stockItemHoldingsList where quantityOnHand equals to UPDATED_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldNotBeFound("quantityOnHand.in=" + UPDATED_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByQuantityOnHandIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where quantityOnHand is not null
        defaultStockItemHoldingsShouldBeFound("quantityOnHand.specified=true");

        // Get all the stockItemHoldingsList where quantityOnHand is null
        defaultStockItemHoldingsShouldNotBeFound("quantityOnHand.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByQuantityOnHandIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where quantityOnHand is greater than or equal to DEFAULT_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldBeFound("quantityOnHand.greaterThanOrEqual=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemHoldingsList where quantityOnHand is greater than or equal to UPDATED_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldNotBeFound("quantityOnHand.greaterThanOrEqual=" + UPDATED_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByQuantityOnHandIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where quantityOnHand is less than or equal to DEFAULT_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldBeFound("quantityOnHand.lessThanOrEqual=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemHoldingsList where quantityOnHand is less than or equal to SMALLER_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldNotBeFound("quantityOnHand.lessThanOrEqual=" + SMALLER_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByQuantityOnHandIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where quantityOnHand is less than DEFAULT_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldNotBeFound("quantityOnHand.lessThan=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemHoldingsList where quantityOnHand is less than UPDATED_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldBeFound("quantityOnHand.lessThan=" + UPDATED_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByQuantityOnHandIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where quantityOnHand is greater than DEFAULT_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldNotBeFound("quantityOnHand.greaterThan=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemHoldingsList where quantityOnHand is greater than SMALLER_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldBeFound("quantityOnHand.greaterThan=" + SMALLER_QUANTITY_ON_HAND);
    }


    @Test
    @Transactional
    public void getAllStockItemHoldingsByBinLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where binLocation equals to DEFAULT_BIN_LOCATION
        defaultStockItemHoldingsShouldBeFound("binLocation.equals=" + DEFAULT_BIN_LOCATION);

        // Get all the stockItemHoldingsList where binLocation equals to UPDATED_BIN_LOCATION
        defaultStockItemHoldingsShouldNotBeFound("binLocation.equals=" + UPDATED_BIN_LOCATION);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByBinLocationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where binLocation not equals to DEFAULT_BIN_LOCATION
        defaultStockItemHoldingsShouldNotBeFound("binLocation.notEquals=" + DEFAULT_BIN_LOCATION);

        // Get all the stockItemHoldingsList where binLocation not equals to UPDATED_BIN_LOCATION
        defaultStockItemHoldingsShouldBeFound("binLocation.notEquals=" + UPDATED_BIN_LOCATION);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByBinLocationIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where binLocation in DEFAULT_BIN_LOCATION or UPDATED_BIN_LOCATION
        defaultStockItemHoldingsShouldBeFound("binLocation.in=" + DEFAULT_BIN_LOCATION + "," + UPDATED_BIN_LOCATION);

        // Get all the stockItemHoldingsList where binLocation equals to UPDATED_BIN_LOCATION
        defaultStockItemHoldingsShouldNotBeFound("binLocation.in=" + UPDATED_BIN_LOCATION);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByBinLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where binLocation is not null
        defaultStockItemHoldingsShouldBeFound("binLocation.specified=true");

        // Get all the stockItemHoldingsList where binLocation is null
        defaultStockItemHoldingsShouldNotBeFound("binLocation.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemHoldingsByBinLocationContainsSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where binLocation contains DEFAULT_BIN_LOCATION
        defaultStockItemHoldingsShouldBeFound("binLocation.contains=" + DEFAULT_BIN_LOCATION);

        // Get all the stockItemHoldingsList where binLocation contains UPDATED_BIN_LOCATION
        defaultStockItemHoldingsShouldNotBeFound("binLocation.contains=" + UPDATED_BIN_LOCATION);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByBinLocationNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where binLocation does not contain DEFAULT_BIN_LOCATION
        defaultStockItemHoldingsShouldNotBeFound("binLocation.doesNotContain=" + DEFAULT_BIN_LOCATION);

        // Get all the stockItemHoldingsList where binLocation does not contain UPDATED_BIN_LOCATION
        defaultStockItemHoldingsShouldBeFound("binLocation.doesNotContain=" + UPDATED_BIN_LOCATION);
    }


    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastStockTakeQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastStockTakeQuantity equals to DEFAULT_LAST_STOCK_TAKE_QUANTITY
        defaultStockItemHoldingsShouldBeFound("lastStockTakeQuantity.equals=" + DEFAULT_LAST_STOCK_TAKE_QUANTITY);

        // Get all the stockItemHoldingsList where lastStockTakeQuantity equals to UPDATED_LAST_STOCK_TAKE_QUANTITY
        defaultStockItemHoldingsShouldNotBeFound("lastStockTakeQuantity.equals=" + UPDATED_LAST_STOCK_TAKE_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastStockTakeQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastStockTakeQuantity not equals to DEFAULT_LAST_STOCK_TAKE_QUANTITY
        defaultStockItemHoldingsShouldNotBeFound("lastStockTakeQuantity.notEquals=" + DEFAULT_LAST_STOCK_TAKE_QUANTITY);

        // Get all the stockItemHoldingsList where lastStockTakeQuantity not equals to UPDATED_LAST_STOCK_TAKE_QUANTITY
        defaultStockItemHoldingsShouldBeFound("lastStockTakeQuantity.notEquals=" + UPDATED_LAST_STOCK_TAKE_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastStockTakeQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastStockTakeQuantity in DEFAULT_LAST_STOCK_TAKE_QUANTITY or UPDATED_LAST_STOCK_TAKE_QUANTITY
        defaultStockItemHoldingsShouldBeFound("lastStockTakeQuantity.in=" + DEFAULT_LAST_STOCK_TAKE_QUANTITY + "," + UPDATED_LAST_STOCK_TAKE_QUANTITY);

        // Get all the stockItemHoldingsList where lastStockTakeQuantity equals to UPDATED_LAST_STOCK_TAKE_QUANTITY
        defaultStockItemHoldingsShouldNotBeFound("lastStockTakeQuantity.in=" + UPDATED_LAST_STOCK_TAKE_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastStockTakeQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastStockTakeQuantity is not null
        defaultStockItemHoldingsShouldBeFound("lastStockTakeQuantity.specified=true");

        // Get all the stockItemHoldingsList where lastStockTakeQuantity is null
        defaultStockItemHoldingsShouldNotBeFound("lastStockTakeQuantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastStockTakeQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastStockTakeQuantity is greater than or equal to DEFAULT_LAST_STOCK_TAKE_QUANTITY
        defaultStockItemHoldingsShouldBeFound("lastStockTakeQuantity.greaterThanOrEqual=" + DEFAULT_LAST_STOCK_TAKE_QUANTITY);

        // Get all the stockItemHoldingsList where lastStockTakeQuantity is greater than or equal to UPDATED_LAST_STOCK_TAKE_QUANTITY
        defaultStockItemHoldingsShouldNotBeFound("lastStockTakeQuantity.greaterThanOrEqual=" + UPDATED_LAST_STOCK_TAKE_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastStockTakeQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastStockTakeQuantity is less than or equal to DEFAULT_LAST_STOCK_TAKE_QUANTITY
        defaultStockItemHoldingsShouldBeFound("lastStockTakeQuantity.lessThanOrEqual=" + DEFAULT_LAST_STOCK_TAKE_QUANTITY);

        // Get all the stockItemHoldingsList where lastStockTakeQuantity is less than or equal to SMALLER_LAST_STOCK_TAKE_QUANTITY
        defaultStockItemHoldingsShouldNotBeFound("lastStockTakeQuantity.lessThanOrEqual=" + SMALLER_LAST_STOCK_TAKE_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastStockTakeQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastStockTakeQuantity is less than DEFAULT_LAST_STOCK_TAKE_QUANTITY
        defaultStockItemHoldingsShouldNotBeFound("lastStockTakeQuantity.lessThan=" + DEFAULT_LAST_STOCK_TAKE_QUANTITY);

        // Get all the stockItemHoldingsList where lastStockTakeQuantity is less than UPDATED_LAST_STOCK_TAKE_QUANTITY
        defaultStockItemHoldingsShouldBeFound("lastStockTakeQuantity.lessThan=" + UPDATED_LAST_STOCK_TAKE_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastStockTakeQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastStockTakeQuantity is greater than DEFAULT_LAST_STOCK_TAKE_QUANTITY
        defaultStockItemHoldingsShouldNotBeFound("lastStockTakeQuantity.greaterThan=" + DEFAULT_LAST_STOCK_TAKE_QUANTITY);

        // Get all the stockItemHoldingsList where lastStockTakeQuantity is greater than SMALLER_LAST_STOCK_TAKE_QUANTITY
        defaultStockItemHoldingsShouldBeFound("lastStockTakeQuantity.greaterThan=" + SMALLER_LAST_STOCK_TAKE_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastCostPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastCostPrice equals to DEFAULT_LAST_COST_PRICE
        defaultStockItemHoldingsShouldBeFound("lastCostPrice.equals=" + DEFAULT_LAST_COST_PRICE);

        // Get all the stockItemHoldingsList where lastCostPrice equals to UPDATED_LAST_COST_PRICE
        defaultStockItemHoldingsShouldNotBeFound("lastCostPrice.equals=" + UPDATED_LAST_COST_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastCostPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastCostPrice not equals to DEFAULT_LAST_COST_PRICE
        defaultStockItemHoldingsShouldNotBeFound("lastCostPrice.notEquals=" + DEFAULT_LAST_COST_PRICE);

        // Get all the stockItemHoldingsList where lastCostPrice not equals to UPDATED_LAST_COST_PRICE
        defaultStockItemHoldingsShouldBeFound("lastCostPrice.notEquals=" + UPDATED_LAST_COST_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastCostPriceIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastCostPrice in DEFAULT_LAST_COST_PRICE or UPDATED_LAST_COST_PRICE
        defaultStockItemHoldingsShouldBeFound("lastCostPrice.in=" + DEFAULT_LAST_COST_PRICE + "," + UPDATED_LAST_COST_PRICE);

        // Get all the stockItemHoldingsList where lastCostPrice equals to UPDATED_LAST_COST_PRICE
        defaultStockItemHoldingsShouldNotBeFound("lastCostPrice.in=" + UPDATED_LAST_COST_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastCostPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastCostPrice is not null
        defaultStockItemHoldingsShouldBeFound("lastCostPrice.specified=true");

        // Get all the stockItemHoldingsList where lastCostPrice is null
        defaultStockItemHoldingsShouldNotBeFound("lastCostPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastCostPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastCostPrice is greater than or equal to DEFAULT_LAST_COST_PRICE
        defaultStockItemHoldingsShouldBeFound("lastCostPrice.greaterThanOrEqual=" + DEFAULT_LAST_COST_PRICE);

        // Get all the stockItemHoldingsList where lastCostPrice is greater than or equal to UPDATED_LAST_COST_PRICE
        defaultStockItemHoldingsShouldNotBeFound("lastCostPrice.greaterThanOrEqual=" + UPDATED_LAST_COST_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastCostPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastCostPrice is less than or equal to DEFAULT_LAST_COST_PRICE
        defaultStockItemHoldingsShouldBeFound("lastCostPrice.lessThanOrEqual=" + DEFAULT_LAST_COST_PRICE);

        // Get all the stockItemHoldingsList where lastCostPrice is less than or equal to SMALLER_LAST_COST_PRICE
        defaultStockItemHoldingsShouldNotBeFound("lastCostPrice.lessThanOrEqual=" + SMALLER_LAST_COST_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastCostPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastCostPrice is less than DEFAULT_LAST_COST_PRICE
        defaultStockItemHoldingsShouldNotBeFound("lastCostPrice.lessThan=" + DEFAULT_LAST_COST_PRICE);

        // Get all the stockItemHoldingsList where lastCostPrice is less than UPDATED_LAST_COST_PRICE
        defaultStockItemHoldingsShouldBeFound("lastCostPrice.lessThan=" + UPDATED_LAST_COST_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastCostPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastCostPrice is greater than DEFAULT_LAST_COST_PRICE
        defaultStockItemHoldingsShouldNotBeFound("lastCostPrice.greaterThan=" + DEFAULT_LAST_COST_PRICE);

        // Get all the stockItemHoldingsList where lastCostPrice is greater than SMALLER_LAST_COST_PRICE
        defaultStockItemHoldingsShouldBeFound("lastCostPrice.greaterThan=" + SMALLER_LAST_COST_PRICE);
    }


    @Test
    @Transactional
    public void getAllStockItemHoldingsByReorderLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where reorderLevel equals to DEFAULT_REORDER_LEVEL
        defaultStockItemHoldingsShouldBeFound("reorderLevel.equals=" + DEFAULT_REORDER_LEVEL);

        // Get all the stockItemHoldingsList where reorderLevel equals to UPDATED_REORDER_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("reorderLevel.equals=" + UPDATED_REORDER_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByReorderLevelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where reorderLevel not equals to DEFAULT_REORDER_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("reorderLevel.notEquals=" + DEFAULT_REORDER_LEVEL);

        // Get all the stockItemHoldingsList where reorderLevel not equals to UPDATED_REORDER_LEVEL
        defaultStockItemHoldingsShouldBeFound("reorderLevel.notEquals=" + UPDATED_REORDER_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByReorderLevelIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where reorderLevel in DEFAULT_REORDER_LEVEL or UPDATED_REORDER_LEVEL
        defaultStockItemHoldingsShouldBeFound("reorderLevel.in=" + DEFAULT_REORDER_LEVEL + "," + UPDATED_REORDER_LEVEL);

        // Get all the stockItemHoldingsList where reorderLevel equals to UPDATED_REORDER_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("reorderLevel.in=" + UPDATED_REORDER_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByReorderLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where reorderLevel is not null
        defaultStockItemHoldingsShouldBeFound("reorderLevel.specified=true");

        // Get all the stockItemHoldingsList where reorderLevel is null
        defaultStockItemHoldingsShouldNotBeFound("reorderLevel.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByReorderLevelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where reorderLevel is greater than or equal to DEFAULT_REORDER_LEVEL
        defaultStockItemHoldingsShouldBeFound("reorderLevel.greaterThanOrEqual=" + DEFAULT_REORDER_LEVEL);

        // Get all the stockItemHoldingsList where reorderLevel is greater than or equal to UPDATED_REORDER_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("reorderLevel.greaterThanOrEqual=" + UPDATED_REORDER_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByReorderLevelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where reorderLevel is less than or equal to DEFAULT_REORDER_LEVEL
        defaultStockItemHoldingsShouldBeFound("reorderLevel.lessThanOrEqual=" + DEFAULT_REORDER_LEVEL);

        // Get all the stockItemHoldingsList where reorderLevel is less than or equal to SMALLER_REORDER_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("reorderLevel.lessThanOrEqual=" + SMALLER_REORDER_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByReorderLevelIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where reorderLevel is less than DEFAULT_REORDER_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("reorderLevel.lessThan=" + DEFAULT_REORDER_LEVEL);

        // Get all the stockItemHoldingsList where reorderLevel is less than UPDATED_REORDER_LEVEL
        defaultStockItemHoldingsShouldBeFound("reorderLevel.lessThan=" + UPDATED_REORDER_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByReorderLevelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where reorderLevel is greater than DEFAULT_REORDER_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("reorderLevel.greaterThan=" + DEFAULT_REORDER_LEVEL);

        // Get all the stockItemHoldingsList where reorderLevel is greater than SMALLER_REORDER_LEVEL
        defaultStockItemHoldingsShouldBeFound("reorderLevel.greaterThan=" + SMALLER_REORDER_LEVEL);
    }


    @Test
    @Transactional
    public void getAllStockItemHoldingsByTargerStockLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where targerStockLevel equals to DEFAULT_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldBeFound("targerStockLevel.equals=" + DEFAULT_TARGER_STOCK_LEVEL);

        // Get all the stockItemHoldingsList where targerStockLevel equals to UPDATED_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("targerStockLevel.equals=" + UPDATED_TARGER_STOCK_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByTargerStockLevelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where targerStockLevel not equals to DEFAULT_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("targerStockLevel.notEquals=" + DEFAULT_TARGER_STOCK_LEVEL);

        // Get all the stockItemHoldingsList where targerStockLevel not equals to UPDATED_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldBeFound("targerStockLevel.notEquals=" + UPDATED_TARGER_STOCK_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByTargerStockLevelIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where targerStockLevel in DEFAULT_TARGER_STOCK_LEVEL or UPDATED_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldBeFound("targerStockLevel.in=" + DEFAULT_TARGER_STOCK_LEVEL + "," + UPDATED_TARGER_STOCK_LEVEL);

        // Get all the stockItemHoldingsList where targerStockLevel equals to UPDATED_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("targerStockLevel.in=" + UPDATED_TARGER_STOCK_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByTargerStockLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where targerStockLevel is not null
        defaultStockItemHoldingsShouldBeFound("targerStockLevel.specified=true");

        // Get all the stockItemHoldingsList where targerStockLevel is null
        defaultStockItemHoldingsShouldNotBeFound("targerStockLevel.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByTargerStockLevelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where targerStockLevel is greater than or equal to DEFAULT_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldBeFound("targerStockLevel.greaterThanOrEqual=" + DEFAULT_TARGER_STOCK_LEVEL);

        // Get all the stockItemHoldingsList where targerStockLevel is greater than or equal to UPDATED_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("targerStockLevel.greaterThanOrEqual=" + UPDATED_TARGER_STOCK_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByTargerStockLevelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where targerStockLevel is less than or equal to DEFAULT_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldBeFound("targerStockLevel.lessThanOrEqual=" + DEFAULT_TARGER_STOCK_LEVEL);

        // Get all the stockItemHoldingsList where targerStockLevel is less than or equal to SMALLER_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("targerStockLevel.lessThanOrEqual=" + SMALLER_TARGER_STOCK_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByTargerStockLevelIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where targerStockLevel is less than DEFAULT_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("targerStockLevel.lessThan=" + DEFAULT_TARGER_STOCK_LEVEL);

        // Get all the stockItemHoldingsList where targerStockLevel is less than UPDATED_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldBeFound("targerStockLevel.lessThan=" + UPDATED_TARGER_STOCK_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByTargerStockLevelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where targerStockLevel is greater than DEFAULT_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("targerStockLevel.greaterThan=" + DEFAULT_TARGER_STOCK_LEVEL);

        // Get all the stockItemHoldingsList where targerStockLevel is greater than SMALLER_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldBeFound("targerStockLevel.greaterThan=" + SMALLER_TARGER_STOCK_LEVEL);
    }


    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultStockItemHoldingsShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the stockItemHoldingsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultStockItemHoldingsShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultStockItemHoldingsShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the stockItemHoldingsList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultStockItemHoldingsShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultStockItemHoldingsShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the stockItemHoldingsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultStockItemHoldingsShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastEditedBy is not null
        defaultStockItemHoldingsShouldBeFound("lastEditedBy.specified=true");

        // Get all the stockItemHoldingsList where lastEditedBy is null
        defaultStockItemHoldingsShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemHoldingsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultStockItemHoldingsShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the stockItemHoldingsList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultStockItemHoldingsShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultStockItemHoldingsShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the stockItemHoldingsList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultStockItemHoldingsShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultStockItemHoldingsShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the stockItemHoldingsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultStockItemHoldingsShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultStockItemHoldingsShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the stockItemHoldingsList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultStockItemHoldingsShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultStockItemHoldingsShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the stockItemHoldingsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultStockItemHoldingsShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastEditedWhen is not null
        defaultStockItemHoldingsShouldBeFound("lastEditedWhen.specified=true");

        // Get all the stockItemHoldingsList where lastEditedWhen is null
        defaultStockItemHoldingsShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByStockItemIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);
        StockItems stockItem = StockItemsResourceIT.createEntity(em);
        em.persist(stockItem);
        em.flush();
        stockItemHoldings.setStockItem(stockItem);
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);
        Long stockItemId = stockItem.getId();

        // Get all the stockItemHoldingsList where stockItem equals to stockItemId
        defaultStockItemHoldingsShouldBeFound("stockItemId.equals=" + stockItemId);

        // Get all the stockItemHoldingsList where stockItem equals to stockItemId + 1
        defaultStockItemHoldingsShouldNotBeFound("stockItemId.equals=" + (stockItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStockItemHoldingsShouldBeFound(String filter) throws Exception {
        restStockItemHoldingsMockMvc.perform(get("/api/stock-item-holdings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockItemHoldings.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantityOnHand").value(hasItem(DEFAULT_QUANTITY_ON_HAND)))
            .andExpect(jsonPath("$.[*].binLocation").value(hasItem(DEFAULT_BIN_LOCATION)))
            .andExpect(jsonPath("$.[*].lastStockTakeQuantity").value(hasItem(DEFAULT_LAST_STOCK_TAKE_QUANTITY)))
            .andExpect(jsonPath("$.[*].lastCostPrice").value(hasItem(DEFAULT_LAST_COST_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].reorderLevel").value(hasItem(DEFAULT_REORDER_LEVEL)))
            .andExpect(jsonPath("$.[*].targerStockLevel").value(hasItem(DEFAULT_TARGER_STOCK_LEVEL)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restStockItemHoldingsMockMvc.perform(get("/api/stock-item-holdings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStockItemHoldingsShouldNotBeFound(String filter) throws Exception {
        restStockItemHoldingsMockMvc.perform(get("/api/stock-item-holdings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStockItemHoldingsMockMvc.perform(get("/api/stock-item-holdings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingStockItemHoldings() throws Exception {
        // Get the stockItemHoldings
        restStockItemHoldingsMockMvc.perform(get("/api/stock-item-holdings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockItemHoldings() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        int databaseSizeBeforeUpdate = stockItemHoldingsRepository.findAll().size();

        // Update the stockItemHoldings
        StockItemHoldings updatedStockItemHoldings = stockItemHoldingsRepository.findById(stockItemHoldings.getId()).get();
        // Disconnect from session so that the updates on updatedStockItemHoldings are not directly saved in db
        em.detach(updatedStockItemHoldings);
        updatedStockItemHoldings
            .quantityOnHand(UPDATED_QUANTITY_ON_HAND)
            .binLocation(UPDATED_BIN_LOCATION)
            .lastStockTakeQuantity(UPDATED_LAST_STOCK_TAKE_QUANTITY)
            .lastCostPrice(UPDATED_LAST_COST_PRICE)
            .reorderLevel(UPDATED_REORDER_LEVEL)
            .targerStockLevel(UPDATED_TARGER_STOCK_LEVEL)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(updatedStockItemHoldings);

        restStockItemHoldingsMockMvc.perform(put("/api/stock-item-holdings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isOk());

        // Validate the StockItemHoldings in the database
        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeUpdate);
        StockItemHoldings testStockItemHoldings = stockItemHoldingsList.get(stockItemHoldingsList.size() - 1);
        assertThat(testStockItemHoldings.getQuantityOnHand()).isEqualTo(UPDATED_QUANTITY_ON_HAND);
        assertThat(testStockItemHoldings.getBinLocation()).isEqualTo(UPDATED_BIN_LOCATION);
        assertThat(testStockItemHoldings.getLastStockTakeQuantity()).isEqualTo(UPDATED_LAST_STOCK_TAKE_QUANTITY);
        assertThat(testStockItemHoldings.getLastCostPrice()).isEqualTo(UPDATED_LAST_COST_PRICE);
        assertThat(testStockItemHoldings.getReorderLevel()).isEqualTo(UPDATED_REORDER_LEVEL);
        assertThat(testStockItemHoldings.getTargerStockLevel()).isEqualTo(UPDATED_TARGER_STOCK_LEVEL);
        assertThat(testStockItemHoldings.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testStockItemHoldings.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingStockItemHoldings() throws Exception {
        int databaseSizeBeforeUpdate = stockItemHoldingsRepository.findAll().size();

        // Create the StockItemHoldings
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockItemHoldingsMockMvc.perform(put("/api/stock-item-holdings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItemHoldings in the database
        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStockItemHoldings() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        int databaseSizeBeforeDelete = stockItemHoldingsRepository.findAll().size();

        // Delete the stockItemHoldings
        restStockItemHoldingsMockMvc.perform(delete("/api/stock-item-holdings/{id}", stockItemHoldings.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

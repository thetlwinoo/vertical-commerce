package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.ShippingFeeChart;
import com.vertical.commerce.domain.Zone;
import com.vertical.commerce.domain.DeliveryMethods;
import com.vertical.commerce.repository.ShippingFeeChartRepository;
import com.vertical.commerce.service.ShippingFeeChartService;
import com.vertical.commerce.service.dto.ShippingFeeChartDTO;
import com.vertical.commerce.service.mapper.ShippingFeeChartMapper;
import com.vertical.commerce.service.dto.ShippingFeeChartCriteria;
import com.vertical.commerce.service.ShippingFeeChartQueryService;

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
 * Integration tests for the {@link ShippingFeeChartResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ShippingFeeChartResourceIT {

    private static final String DEFAULT_SIZE_OF_PERCEL = "AAAAAAAAAA";
    private static final String UPDATED_SIZE_OF_PERCEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_MIN_VOLUME_WEIGHT = 1;
    private static final Integer UPDATED_MIN_VOLUME_WEIGHT = 2;
    private static final Integer SMALLER_MIN_VOLUME_WEIGHT = 1 - 1;

    private static final Integer DEFAULT_MAX_VOLUME_WEIGHT = 1;
    private static final Integer UPDATED_MAX_VOLUME_WEIGHT = 2;
    private static final Integer SMALLER_MAX_VOLUME_WEIGHT = 1 - 1;

    private static final Integer DEFAULT_MIN_ACTUAL_WEIGHT = 1;
    private static final Integer UPDATED_MIN_ACTUAL_WEIGHT = 2;
    private static final Integer SMALLER_MIN_ACTUAL_WEIGHT = 1 - 1;

    private static final Integer DEFAULT_MAX_ACTUAL_WEIGHT = 1;
    private static final Integer UPDATED_MAX_ACTUAL_WEIGHT = 2;
    private static final Integer SMALLER_MAX_ACTUAL_WEIGHT = 1 - 1;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(1 - 1);

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ShippingFeeChartRepository shippingFeeChartRepository;

    @Autowired
    private ShippingFeeChartMapper shippingFeeChartMapper;

    @Autowired
    private ShippingFeeChartService shippingFeeChartService;

    @Autowired
    private ShippingFeeChartQueryService shippingFeeChartQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShippingFeeChartMockMvc;

    private ShippingFeeChart shippingFeeChart;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShippingFeeChart createEntity(EntityManager em) {
        ShippingFeeChart shippingFeeChart = new ShippingFeeChart()
            .sizeOfPercel(DEFAULT_SIZE_OF_PERCEL)
            .minVolumeWeight(DEFAULT_MIN_VOLUME_WEIGHT)
            .maxVolumeWeight(DEFAULT_MAX_VOLUME_WEIGHT)
            .minActualWeight(DEFAULT_MIN_ACTUAL_WEIGHT)
            .maxActualWeight(DEFAULT_MAX_ACTUAL_WEIGHT)
            .price(DEFAULT_PRICE)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return shippingFeeChart;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShippingFeeChart createUpdatedEntity(EntityManager em) {
        ShippingFeeChart shippingFeeChart = new ShippingFeeChart()
            .sizeOfPercel(UPDATED_SIZE_OF_PERCEL)
            .minVolumeWeight(UPDATED_MIN_VOLUME_WEIGHT)
            .maxVolumeWeight(UPDATED_MAX_VOLUME_WEIGHT)
            .minActualWeight(UPDATED_MIN_ACTUAL_WEIGHT)
            .maxActualWeight(UPDATED_MAX_ACTUAL_WEIGHT)
            .price(UPDATED_PRICE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return shippingFeeChart;
    }

    @BeforeEach
    public void initTest() {
        shippingFeeChart = createEntity(em);
    }

    @Test
    @Transactional
    public void createShippingFeeChart() throws Exception {
        int databaseSizeBeforeCreate = shippingFeeChartRepository.findAll().size();
        // Create the ShippingFeeChart
        ShippingFeeChartDTO shippingFeeChartDTO = shippingFeeChartMapper.toDto(shippingFeeChart);
        restShippingFeeChartMockMvc.perform(post("/api/shipping-fee-charts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shippingFeeChartDTO)))
            .andExpect(status().isCreated());

        // Validate the ShippingFeeChart in the database
        List<ShippingFeeChart> shippingFeeChartList = shippingFeeChartRepository.findAll();
        assertThat(shippingFeeChartList).hasSize(databaseSizeBeforeCreate + 1);
        ShippingFeeChart testShippingFeeChart = shippingFeeChartList.get(shippingFeeChartList.size() - 1);
        assertThat(testShippingFeeChart.getSizeOfPercel()).isEqualTo(DEFAULT_SIZE_OF_PERCEL);
        assertThat(testShippingFeeChart.getMinVolumeWeight()).isEqualTo(DEFAULT_MIN_VOLUME_WEIGHT);
        assertThat(testShippingFeeChart.getMaxVolumeWeight()).isEqualTo(DEFAULT_MAX_VOLUME_WEIGHT);
        assertThat(testShippingFeeChart.getMinActualWeight()).isEqualTo(DEFAULT_MIN_ACTUAL_WEIGHT);
        assertThat(testShippingFeeChart.getMaxActualWeight()).isEqualTo(DEFAULT_MAX_ACTUAL_WEIGHT);
        assertThat(testShippingFeeChart.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testShippingFeeChart.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testShippingFeeChart.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createShippingFeeChartWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shippingFeeChartRepository.findAll().size();

        // Create the ShippingFeeChart with an existing ID
        shippingFeeChart.setId(1L);
        ShippingFeeChartDTO shippingFeeChartDTO = shippingFeeChartMapper.toDto(shippingFeeChart);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShippingFeeChartMockMvc.perform(post("/api/shipping-fee-charts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shippingFeeChartDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShippingFeeChart in the database
        List<ShippingFeeChart> shippingFeeChartList = shippingFeeChartRepository.findAll();
        assertThat(shippingFeeChartList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSizeOfPercelIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingFeeChartRepository.findAll().size();
        // set the field null
        shippingFeeChart.setSizeOfPercel(null);

        // Create the ShippingFeeChart, which fails.
        ShippingFeeChartDTO shippingFeeChartDTO = shippingFeeChartMapper.toDto(shippingFeeChart);


        restShippingFeeChartMockMvc.perform(post("/api/shipping-fee-charts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shippingFeeChartDTO)))
            .andExpect(status().isBadRequest());

        List<ShippingFeeChart> shippingFeeChartList = shippingFeeChartRepository.findAll();
        assertThat(shippingFeeChartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMinVolumeWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingFeeChartRepository.findAll().size();
        // set the field null
        shippingFeeChart.setMinVolumeWeight(null);

        // Create the ShippingFeeChart, which fails.
        ShippingFeeChartDTO shippingFeeChartDTO = shippingFeeChartMapper.toDto(shippingFeeChart);


        restShippingFeeChartMockMvc.perform(post("/api/shipping-fee-charts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shippingFeeChartDTO)))
            .andExpect(status().isBadRequest());

        List<ShippingFeeChart> shippingFeeChartList = shippingFeeChartRepository.findAll();
        assertThat(shippingFeeChartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaxVolumeWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingFeeChartRepository.findAll().size();
        // set the field null
        shippingFeeChart.setMaxVolumeWeight(null);

        // Create the ShippingFeeChart, which fails.
        ShippingFeeChartDTO shippingFeeChartDTO = shippingFeeChartMapper.toDto(shippingFeeChart);


        restShippingFeeChartMockMvc.perform(post("/api/shipping-fee-charts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shippingFeeChartDTO)))
            .andExpect(status().isBadRequest());

        List<ShippingFeeChart> shippingFeeChartList = shippingFeeChartRepository.findAll();
        assertThat(shippingFeeChartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMinActualWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingFeeChartRepository.findAll().size();
        // set the field null
        shippingFeeChart.setMinActualWeight(null);

        // Create the ShippingFeeChart, which fails.
        ShippingFeeChartDTO shippingFeeChartDTO = shippingFeeChartMapper.toDto(shippingFeeChart);


        restShippingFeeChartMockMvc.perform(post("/api/shipping-fee-charts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shippingFeeChartDTO)))
            .andExpect(status().isBadRequest());

        List<ShippingFeeChart> shippingFeeChartList = shippingFeeChartRepository.findAll();
        assertThat(shippingFeeChartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaxActualWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingFeeChartRepository.findAll().size();
        // set the field null
        shippingFeeChart.setMaxActualWeight(null);

        // Create the ShippingFeeChart, which fails.
        ShippingFeeChartDTO shippingFeeChartDTO = shippingFeeChartMapper.toDto(shippingFeeChart);


        restShippingFeeChartMockMvc.perform(post("/api/shipping-fee-charts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shippingFeeChartDTO)))
            .andExpect(status().isBadRequest());

        List<ShippingFeeChart> shippingFeeChartList = shippingFeeChartRepository.findAll();
        assertThat(shippingFeeChartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingFeeChartRepository.findAll().size();
        // set the field null
        shippingFeeChart.setPrice(null);

        // Create the ShippingFeeChart, which fails.
        ShippingFeeChartDTO shippingFeeChartDTO = shippingFeeChartMapper.toDto(shippingFeeChart);


        restShippingFeeChartMockMvc.perform(post("/api/shipping-fee-charts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shippingFeeChartDTO)))
            .andExpect(status().isBadRequest());

        List<ShippingFeeChart> shippingFeeChartList = shippingFeeChartRepository.findAll();
        assertThat(shippingFeeChartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingFeeChartRepository.findAll().size();
        // set the field null
        shippingFeeChart.setLastEditedBy(null);

        // Create the ShippingFeeChart, which fails.
        ShippingFeeChartDTO shippingFeeChartDTO = shippingFeeChartMapper.toDto(shippingFeeChart);


        restShippingFeeChartMockMvc.perform(post("/api/shipping-fee-charts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shippingFeeChartDTO)))
            .andExpect(status().isBadRequest());

        List<ShippingFeeChart> shippingFeeChartList = shippingFeeChartRepository.findAll();
        assertThat(shippingFeeChartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingFeeChartRepository.findAll().size();
        // set the field null
        shippingFeeChart.setLastEditedWhen(null);

        // Create the ShippingFeeChart, which fails.
        ShippingFeeChartDTO shippingFeeChartDTO = shippingFeeChartMapper.toDto(shippingFeeChart);


        restShippingFeeChartMockMvc.perform(post("/api/shipping-fee-charts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shippingFeeChartDTO)))
            .andExpect(status().isBadRequest());

        List<ShippingFeeChart> shippingFeeChartList = shippingFeeChartRepository.findAll();
        assertThat(shippingFeeChartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShippingFeeCharts() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList
        restShippingFeeChartMockMvc.perform(get("/api/shipping-fee-charts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shippingFeeChart.getId().intValue())))
            .andExpect(jsonPath("$.[*].sizeOfPercel").value(hasItem(DEFAULT_SIZE_OF_PERCEL)))
            .andExpect(jsonPath("$.[*].minVolumeWeight").value(hasItem(DEFAULT_MIN_VOLUME_WEIGHT)))
            .andExpect(jsonPath("$.[*].maxVolumeWeight").value(hasItem(DEFAULT_MAX_VOLUME_WEIGHT)))
            .andExpect(jsonPath("$.[*].minActualWeight").value(hasItem(DEFAULT_MIN_ACTUAL_WEIGHT)))
            .andExpect(jsonPath("$.[*].maxActualWeight").value(hasItem(DEFAULT_MAX_ACTUAL_WEIGHT)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getShippingFeeChart() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get the shippingFeeChart
        restShippingFeeChartMockMvc.perform(get("/api/shipping-fee-charts/{id}", shippingFeeChart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shippingFeeChart.getId().intValue()))
            .andExpect(jsonPath("$.sizeOfPercel").value(DEFAULT_SIZE_OF_PERCEL))
            .andExpect(jsonPath("$.minVolumeWeight").value(DEFAULT_MIN_VOLUME_WEIGHT))
            .andExpect(jsonPath("$.maxVolumeWeight").value(DEFAULT_MAX_VOLUME_WEIGHT))
            .andExpect(jsonPath("$.minActualWeight").value(DEFAULT_MIN_ACTUAL_WEIGHT))
            .andExpect(jsonPath("$.maxActualWeight").value(DEFAULT_MAX_ACTUAL_WEIGHT))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getShippingFeeChartsByIdFiltering() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        Long id = shippingFeeChart.getId();

        defaultShippingFeeChartShouldBeFound("id.equals=" + id);
        defaultShippingFeeChartShouldNotBeFound("id.notEquals=" + id);

        defaultShippingFeeChartShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultShippingFeeChartShouldNotBeFound("id.greaterThan=" + id);

        defaultShippingFeeChartShouldBeFound("id.lessThanOrEqual=" + id);
        defaultShippingFeeChartShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllShippingFeeChartsBySizeOfPercelIsEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where sizeOfPercel equals to DEFAULT_SIZE_OF_PERCEL
        defaultShippingFeeChartShouldBeFound("sizeOfPercel.equals=" + DEFAULT_SIZE_OF_PERCEL);

        // Get all the shippingFeeChartList where sizeOfPercel equals to UPDATED_SIZE_OF_PERCEL
        defaultShippingFeeChartShouldNotBeFound("sizeOfPercel.equals=" + UPDATED_SIZE_OF_PERCEL);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsBySizeOfPercelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where sizeOfPercel not equals to DEFAULT_SIZE_OF_PERCEL
        defaultShippingFeeChartShouldNotBeFound("sizeOfPercel.notEquals=" + DEFAULT_SIZE_OF_PERCEL);

        // Get all the shippingFeeChartList where sizeOfPercel not equals to UPDATED_SIZE_OF_PERCEL
        defaultShippingFeeChartShouldBeFound("sizeOfPercel.notEquals=" + UPDATED_SIZE_OF_PERCEL);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsBySizeOfPercelIsInShouldWork() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where sizeOfPercel in DEFAULT_SIZE_OF_PERCEL or UPDATED_SIZE_OF_PERCEL
        defaultShippingFeeChartShouldBeFound("sizeOfPercel.in=" + DEFAULT_SIZE_OF_PERCEL + "," + UPDATED_SIZE_OF_PERCEL);

        // Get all the shippingFeeChartList where sizeOfPercel equals to UPDATED_SIZE_OF_PERCEL
        defaultShippingFeeChartShouldNotBeFound("sizeOfPercel.in=" + UPDATED_SIZE_OF_PERCEL);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsBySizeOfPercelIsNullOrNotNull() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where sizeOfPercel is not null
        defaultShippingFeeChartShouldBeFound("sizeOfPercel.specified=true");

        // Get all the shippingFeeChartList where sizeOfPercel is null
        defaultShippingFeeChartShouldNotBeFound("sizeOfPercel.specified=false");
    }
                @Test
    @Transactional
    public void getAllShippingFeeChartsBySizeOfPercelContainsSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where sizeOfPercel contains DEFAULT_SIZE_OF_PERCEL
        defaultShippingFeeChartShouldBeFound("sizeOfPercel.contains=" + DEFAULT_SIZE_OF_PERCEL);

        // Get all the shippingFeeChartList where sizeOfPercel contains UPDATED_SIZE_OF_PERCEL
        defaultShippingFeeChartShouldNotBeFound("sizeOfPercel.contains=" + UPDATED_SIZE_OF_PERCEL);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsBySizeOfPercelNotContainsSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where sizeOfPercel does not contain DEFAULT_SIZE_OF_PERCEL
        defaultShippingFeeChartShouldNotBeFound("sizeOfPercel.doesNotContain=" + DEFAULT_SIZE_OF_PERCEL);

        // Get all the shippingFeeChartList where sizeOfPercel does not contain UPDATED_SIZE_OF_PERCEL
        defaultShippingFeeChartShouldBeFound("sizeOfPercel.doesNotContain=" + UPDATED_SIZE_OF_PERCEL);
    }


    @Test
    @Transactional
    public void getAllShippingFeeChartsByMinVolumeWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where minVolumeWeight equals to DEFAULT_MIN_VOLUME_WEIGHT
        defaultShippingFeeChartShouldBeFound("minVolumeWeight.equals=" + DEFAULT_MIN_VOLUME_WEIGHT);

        // Get all the shippingFeeChartList where minVolumeWeight equals to UPDATED_MIN_VOLUME_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("minVolumeWeight.equals=" + UPDATED_MIN_VOLUME_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMinVolumeWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where minVolumeWeight not equals to DEFAULT_MIN_VOLUME_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("minVolumeWeight.notEquals=" + DEFAULT_MIN_VOLUME_WEIGHT);

        // Get all the shippingFeeChartList where minVolumeWeight not equals to UPDATED_MIN_VOLUME_WEIGHT
        defaultShippingFeeChartShouldBeFound("minVolumeWeight.notEquals=" + UPDATED_MIN_VOLUME_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMinVolumeWeightIsInShouldWork() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where minVolumeWeight in DEFAULT_MIN_VOLUME_WEIGHT or UPDATED_MIN_VOLUME_WEIGHT
        defaultShippingFeeChartShouldBeFound("minVolumeWeight.in=" + DEFAULT_MIN_VOLUME_WEIGHT + "," + UPDATED_MIN_VOLUME_WEIGHT);

        // Get all the shippingFeeChartList where minVolumeWeight equals to UPDATED_MIN_VOLUME_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("minVolumeWeight.in=" + UPDATED_MIN_VOLUME_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMinVolumeWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where minVolumeWeight is not null
        defaultShippingFeeChartShouldBeFound("minVolumeWeight.specified=true");

        // Get all the shippingFeeChartList where minVolumeWeight is null
        defaultShippingFeeChartShouldNotBeFound("minVolumeWeight.specified=false");
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMinVolumeWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where minVolumeWeight is greater than or equal to DEFAULT_MIN_VOLUME_WEIGHT
        defaultShippingFeeChartShouldBeFound("minVolumeWeight.greaterThanOrEqual=" + DEFAULT_MIN_VOLUME_WEIGHT);

        // Get all the shippingFeeChartList where minVolumeWeight is greater than or equal to UPDATED_MIN_VOLUME_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("minVolumeWeight.greaterThanOrEqual=" + UPDATED_MIN_VOLUME_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMinVolumeWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where minVolumeWeight is less than or equal to DEFAULT_MIN_VOLUME_WEIGHT
        defaultShippingFeeChartShouldBeFound("minVolumeWeight.lessThanOrEqual=" + DEFAULT_MIN_VOLUME_WEIGHT);

        // Get all the shippingFeeChartList where minVolumeWeight is less than or equal to SMALLER_MIN_VOLUME_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("minVolumeWeight.lessThanOrEqual=" + SMALLER_MIN_VOLUME_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMinVolumeWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where minVolumeWeight is less than DEFAULT_MIN_VOLUME_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("minVolumeWeight.lessThan=" + DEFAULT_MIN_VOLUME_WEIGHT);

        // Get all the shippingFeeChartList where minVolumeWeight is less than UPDATED_MIN_VOLUME_WEIGHT
        defaultShippingFeeChartShouldBeFound("minVolumeWeight.lessThan=" + UPDATED_MIN_VOLUME_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMinVolumeWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where minVolumeWeight is greater than DEFAULT_MIN_VOLUME_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("minVolumeWeight.greaterThan=" + DEFAULT_MIN_VOLUME_WEIGHT);

        // Get all the shippingFeeChartList where minVolumeWeight is greater than SMALLER_MIN_VOLUME_WEIGHT
        defaultShippingFeeChartShouldBeFound("minVolumeWeight.greaterThan=" + SMALLER_MIN_VOLUME_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllShippingFeeChartsByMaxVolumeWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where maxVolumeWeight equals to DEFAULT_MAX_VOLUME_WEIGHT
        defaultShippingFeeChartShouldBeFound("maxVolumeWeight.equals=" + DEFAULT_MAX_VOLUME_WEIGHT);

        // Get all the shippingFeeChartList where maxVolumeWeight equals to UPDATED_MAX_VOLUME_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("maxVolumeWeight.equals=" + UPDATED_MAX_VOLUME_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMaxVolumeWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where maxVolumeWeight not equals to DEFAULT_MAX_VOLUME_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("maxVolumeWeight.notEquals=" + DEFAULT_MAX_VOLUME_WEIGHT);

        // Get all the shippingFeeChartList where maxVolumeWeight not equals to UPDATED_MAX_VOLUME_WEIGHT
        defaultShippingFeeChartShouldBeFound("maxVolumeWeight.notEquals=" + UPDATED_MAX_VOLUME_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMaxVolumeWeightIsInShouldWork() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where maxVolumeWeight in DEFAULT_MAX_VOLUME_WEIGHT or UPDATED_MAX_VOLUME_WEIGHT
        defaultShippingFeeChartShouldBeFound("maxVolumeWeight.in=" + DEFAULT_MAX_VOLUME_WEIGHT + "," + UPDATED_MAX_VOLUME_WEIGHT);

        // Get all the shippingFeeChartList where maxVolumeWeight equals to UPDATED_MAX_VOLUME_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("maxVolumeWeight.in=" + UPDATED_MAX_VOLUME_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMaxVolumeWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where maxVolumeWeight is not null
        defaultShippingFeeChartShouldBeFound("maxVolumeWeight.specified=true");

        // Get all the shippingFeeChartList where maxVolumeWeight is null
        defaultShippingFeeChartShouldNotBeFound("maxVolumeWeight.specified=false");
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMaxVolumeWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where maxVolumeWeight is greater than or equal to DEFAULT_MAX_VOLUME_WEIGHT
        defaultShippingFeeChartShouldBeFound("maxVolumeWeight.greaterThanOrEqual=" + DEFAULT_MAX_VOLUME_WEIGHT);

        // Get all the shippingFeeChartList where maxVolumeWeight is greater than or equal to UPDATED_MAX_VOLUME_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("maxVolumeWeight.greaterThanOrEqual=" + UPDATED_MAX_VOLUME_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMaxVolumeWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where maxVolumeWeight is less than or equal to DEFAULT_MAX_VOLUME_WEIGHT
        defaultShippingFeeChartShouldBeFound("maxVolumeWeight.lessThanOrEqual=" + DEFAULT_MAX_VOLUME_WEIGHT);

        // Get all the shippingFeeChartList where maxVolumeWeight is less than or equal to SMALLER_MAX_VOLUME_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("maxVolumeWeight.lessThanOrEqual=" + SMALLER_MAX_VOLUME_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMaxVolumeWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where maxVolumeWeight is less than DEFAULT_MAX_VOLUME_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("maxVolumeWeight.lessThan=" + DEFAULT_MAX_VOLUME_WEIGHT);

        // Get all the shippingFeeChartList where maxVolumeWeight is less than UPDATED_MAX_VOLUME_WEIGHT
        defaultShippingFeeChartShouldBeFound("maxVolumeWeight.lessThan=" + UPDATED_MAX_VOLUME_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMaxVolumeWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where maxVolumeWeight is greater than DEFAULT_MAX_VOLUME_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("maxVolumeWeight.greaterThan=" + DEFAULT_MAX_VOLUME_WEIGHT);

        // Get all the shippingFeeChartList where maxVolumeWeight is greater than SMALLER_MAX_VOLUME_WEIGHT
        defaultShippingFeeChartShouldBeFound("maxVolumeWeight.greaterThan=" + SMALLER_MAX_VOLUME_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllShippingFeeChartsByMinActualWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where minActualWeight equals to DEFAULT_MIN_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldBeFound("minActualWeight.equals=" + DEFAULT_MIN_ACTUAL_WEIGHT);

        // Get all the shippingFeeChartList where minActualWeight equals to UPDATED_MIN_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("minActualWeight.equals=" + UPDATED_MIN_ACTUAL_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMinActualWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where minActualWeight not equals to DEFAULT_MIN_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("minActualWeight.notEquals=" + DEFAULT_MIN_ACTUAL_WEIGHT);

        // Get all the shippingFeeChartList where minActualWeight not equals to UPDATED_MIN_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldBeFound("minActualWeight.notEquals=" + UPDATED_MIN_ACTUAL_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMinActualWeightIsInShouldWork() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where minActualWeight in DEFAULT_MIN_ACTUAL_WEIGHT or UPDATED_MIN_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldBeFound("minActualWeight.in=" + DEFAULT_MIN_ACTUAL_WEIGHT + "," + UPDATED_MIN_ACTUAL_WEIGHT);

        // Get all the shippingFeeChartList where minActualWeight equals to UPDATED_MIN_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("minActualWeight.in=" + UPDATED_MIN_ACTUAL_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMinActualWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where minActualWeight is not null
        defaultShippingFeeChartShouldBeFound("minActualWeight.specified=true");

        // Get all the shippingFeeChartList where minActualWeight is null
        defaultShippingFeeChartShouldNotBeFound("minActualWeight.specified=false");
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMinActualWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where minActualWeight is greater than or equal to DEFAULT_MIN_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldBeFound("minActualWeight.greaterThanOrEqual=" + DEFAULT_MIN_ACTUAL_WEIGHT);

        // Get all the shippingFeeChartList where minActualWeight is greater than or equal to UPDATED_MIN_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("minActualWeight.greaterThanOrEqual=" + UPDATED_MIN_ACTUAL_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMinActualWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where minActualWeight is less than or equal to DEFAULT_MIN_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldBeFound("minActualWeight.lessThanOrEqual=" + DEFAULT_MIN_ACTUAL_WEIGHT);

        // Get all the shippingFeeChartList where minActualWeight is less than or equal to SMALLER_MIN_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("minActualWeight.lessThanOrEqual=" + SMALLER_MIN_ACTUAL_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMinActualWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where minActualWeight is less than DEFAULT_MIN_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("minActualWeight.lessThan=" + DEFAULT_MIN_ACTUAL_WEIGHT);

        // Get all the shippingFeeChartList where minActualWeight is less than UPDATED_MIN_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldBeFound("minActualWeight.lessThan=" + UPDATED_MIN_ACTUAL_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMinActualWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where minActualWeight is greater than DEFAULT_MIN_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("minActualWeight.greaterThan=" + DEFAULT_MIN_ACTUAL_WEIGHT);

        // Get all the shippingFeeChartList where minActualWeight is greater than SMALLER_MIN_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldBeFound("minActualWeight.greaterThan=" + SMALLER_MIN_ACTUAL_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllShippingFeeChartsByMaxActualWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where maxActualWeight equals to DEFAULT_MAX_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldBeFound("maxActualWeight.equals=" + DEFAULT_MAX_ACTUAL_WEIGHT);

        // Get all the shippingFeeChartList where maxActualWeight equals to UPDATED_MAX_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("maxActualWeight.equals=" + UPDATED_MAX_ACTUAL_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMaxActualWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where maxActualWeight not equals to DEFAULT_MAX_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("maxActualWeight.notEquals=" + DEFAULT_MAX_ACTUAL_WEIGHT);

        // Get all the shippingFeeChartList where maxActualWeight not equals to UPDATED_MAX_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldBeFound("maxActualWeight.notEquals=" + UPDATED_MAX_ACTUAL_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMaxActualWeightIsInShouldWork() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where maxActualWeight in DEFAULT_MAX_ACTUAL_WEIGHT or UPDATED_MAX_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldBeFound("maxActualWeight.in=" + DEFAULT_MAX_ACTUAL_WEIGHT + "," + UPDATED_MAX_ACTUAL_WEIGHT);

        // Get all the shippingFeeChartList where maxActualWeight equals to UPDATED_MAX_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("maxActualWeight.in=" + UPDATED_MAX_ACTUAL_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMaxActualWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where maxActualWeight is not null
        defaultShippingFeeChartShouldBeFound("maxActualWeight.specified=true");

        // Get all the shippingFeeChartList where maxActualWeight is null
        defaultShippingFeeChartShouldNotBeFound("maxActualWeight.specified=false");
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMaxActualWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where maxActualWeight is greater than or equal to DEFAULT_MAX_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldBeFound("maxActualWeight.greaterThanOrEqual=" + DEFAULT_MAX_ACTUAL_WEIGHT);

        // Get all the shippingFeeChartList where maxActualWeight is greater than or equal to UPDATED_MAX_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("maxActualWeight.greaterThanOrEqual=" + UPDATED_MAX_ACTUAL_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMaxActualWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where maxActualWeight is less than or equal to DEFAULT_MAX_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldBeFound("maxActualWeight.lessThanOrEqual=" + DEFAULT_MAX_ACTUAL_WEIGHT);

        // Get all the shippingFeeChartList where maxActualWeight is less than or equal to SMALLER_MAX_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("maxActualWeight.lessThanOrEqual=" + SMALLER_MAX_ACTUAL_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMaxActualWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where maxActualWeight is less than DEFAULT_MAX_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("maxActualWeight.lessThan=" + DEFAULT_MAX_ACTUAL_WEIGHT);

        // Get all the shippingFeeChartList where maxActualWeight is less than UPDATED_MAX_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldBeFound("maxActualWeight.lessThan=" + UPDATED_MAX_ACTUAL_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByMaxActualWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where maxActualWeight is greater than DEFAULT_MAX_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldNotBeFound("maxActualWeight.greaterThan=" + DEFAULT_MAX_ACTUAL_WEIGHT);

        // Get all the shippingFeeChartList where maxActualWeight is greater than SMALLER_MAX_ACTUAL_WEIGHT
        defaultShippingFeeChartShouldBeFound("maxActualWeight.greaterThan=" + SMALLER_MAX_ACTUAL_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllShippingFeeChartsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where price equals to DEFAULT_PRICE
        defaultShippingFeeChartShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the shippingFeeChartList where price equals to UPDATED_PRICE
        defaultShippingFeeChartShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where price not equals to DEFAULT_PRICE
        defaultShippingFeeChartShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the shippingFeeChartList where price not equals to UPDATED_PRICE
        defaultShippingFeeChartShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultShippingFeeChartShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the shippingFeeChartList where price equals to UPDATED_PRICE
        defaultShippingFeeChartShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where price is not null
        defaultShippingFeeChartShouldBeFound("price.specified=true");

        // Get all the shippingFeeChartList where price is null
        defaultShippingFeeChartShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where price is greater than or equal to DEFAULT_PRICE
        defaultShippingFeeChartShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the shippingFeeChartList where price is greater than or equal to UPDATED_PRICE
        defaultShippingFeeChartShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where price is less than or equal to DEFAULT_PRICE
        defaultShippingFeeChartShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the shippingFeeChartList where price is less than or equal to SMALLER_PRICE
        defaultShippingFeeChartShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where price is less than DEFAULT_PRICE
        defaultShippingFeeChartShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the shippingFeeChartList where price is less than UPDATED_PRICE
        defaultShippingFeeChartShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where price is greater than DEFAULT_PRICE
        defaultShippingFeeChartShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the shippingFeeChartList where price is greater than SMALLER_PRICE
        defaultShippingFeeChartShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllShippingFeeChartsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultShippingFeeChartShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the shippingFeeChartList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultShippingFeeChartShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultShippingFeeChartShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the shippingFeeChartList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultShippingFeeChartShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultShippingFeeChartShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the shippingFeeChartList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultShippingFeeChartShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where lastEditedBy is not null
        defaultShippingFeeChartShouldBeFound("lastEditedBy.specified=true");

        // Get all the shippingFeeChartList where lastEditedBy is null
        defaultShippingFeeChartShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllShippingFeeChartsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultShippingFeeChartShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the shippingFeeChartList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultShippingFeeChartShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultShippingFeeChartShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the shippingFeeChartList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultShippingFeeChartShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllShippingFeeChartsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultShippingFeeChartShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the shippingFeeChartList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultShippingFeeChartShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultShippingFeeChartShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the shippingFeeChartList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultShippingFeeChartShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultShippingFeeChartShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the shippingFeeChartList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultShippingFeeChartShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        // Get all the shippingFeeChartList where lastEditedWhen is not null
        defaultShippingFeeChartShouldBeFound("lastEditedWhen.specified=true");

        // Get all the shippingFeeChartList where lastEditedWhen is null
        defaultShippingFeeChartShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllShippingFeeChartsBySourceZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);
        Zone sourceZone = ZoneResourceIT.createEntity(em);
        em.persist(sourceZone);
        em.flush();
        shippingFeeChart.setSourceZone(sourceZone);
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);
        Long sourceZoneId = sourceZone.getId();

        // Get all the shippingFeeChartList where sourceZone equals to sourceZoneId
        defaultShippingFeeChartShouldBeFound("sourceZoneId.equals=" + sourceZoneId);

        // Get all the shippingFeeChartList where sourceZone equals to sourceZoneId + 1
        defaultShippingFeeChartShouldNotBeFound("sourceZoneId.equals=" + (sourceZoneId + 1));
    }


    @Test
    @Transactional
    public void getAllShippingFeeChartsByDestinationZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);
        Zone destinationZone = ZoneResourceIT.createEntity(em);
        em.persist(destinationZone);
        em.flush();
        shippingFeeChart.setDestinationZone(destinationZone);
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);
        Long destinationZoneId = destinationZone.getId();

        // Get all the shippingFeeChartList where destinationZone equals to destinationZoneId
        defaultShippingFeeChartShouldBeFound("destinationZoneId.equals=" + destinationZoneId);

        // Get all the shippingFeeChartList where destinationZone equals to destinationZoneId + 1
        defaultShippingFeeChartShouldNotBeFound("destinationZoneId.equals=" + (destinationZoneId + 1));
    }


    @Test
    @Transactional
    public void getAllShippingFeeChartsByDeliveryMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);
        DeliveryMethods deliveryMethod = DeliveryMethodsResourceIT.createEntity(em);
        em.persist(deliveryMethod);
        em.flush();
        shippingFeeChart.setDeliveryMethod(deliveryMethod);
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);
        Long deliveryMethodId = deliveryMethod.getId();

        // Get all the shippingFeeChartList where deliveryMethod equals to deliveryMethodId
        defaultShippingFeeChartShouldBeFound("deliveryMethodId.equals=" + deliveryMethodId);

        // Get all the shippingFeeChartList where deliveryMethod equals to deliveryMethodId + 1
        defaultShippingFeeChartShouldNotBeFound("deliveryMethodId.equals=" + (deliveryMethodId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultShippingFeeChartShouldBeFound(String filter) throws Exception {
        restShippingFeeChartMockMvc.perform(get("/api/shipping-fee-charts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shippingFeeChart.getId().intValue())))
            .andExpect(jsonPath("$.[*].sizeOfPercel").value(hasItem(DEFAULT_SIZE_OF_PERCEL)))
            .andExpect(jsonPath("$.[*].minVolumeWeight").value(hasItem(DEFAULT_MIN_VOLUME_WEIGHT)))
            .andExpect(jsonPath("$.[*].maxVolumeWeight").value(hasItem(DEFAULT_MAX_VOLUME_WEIGHT)))
            .andExpect(jsonPath("$.[*].minActualWeight").value(hasItem(DEFAULT_MIN_ACTUAL_WEIGHT)))
            .andExpect(jsonPath("$.[*].maxActualWeight").value(hasItem(DEFAULT_MAX_ACTUAL_WEIGHT)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restShippingFeeChartMockMvc.perform(get("/api/shipping-fee-charts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultShippingFeeChartShouldNotBeFound(String filter) throws Exception {
        restShippingFeeChartMockMvc.perform(get("/api/shipping-fee-charts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restShippingFeeChartMockMvc.perform(get("/api/shipping-fee-charts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingShippingFeeChart() throws Exception {
        // Get the shippingFeeChart
        restShippingFeeChartMockMvc.perform(get("/api/shipping-fee-charts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShippingFeeChart() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        int databaseSizeBeforeUpdate = shippingFeeChartRepository.findAll().size();

        // Update the shippingFeeChart
        ShippingFeeChart updatedShippingFeeChart = shippingFeeChartRepository.findById(shippingFeeChart.getId()).get();
        // Disconnect from session so that the updates on updatedShippingFeeChart are not directly saved in db
        em.detach(updatedShippingFeeChart);
        updatedShippingFeeChart
            .sizeOfPercel(UPDATED_SIZE_OF_PERCEL)
            .minVolumeWeight(UPDATED_MIN_VOLUME_WEIGHT)
            .maxVolumeWeight(UPDATED_MAX_VOLUME_WEIGHT)
            .minActualWeight(UPDATED_MIN_ACTUAL_WEIGHT)
            .maxActualWeight(UPDATED_MAX_ACTUAL_WEIGHT)
            .price(UPDATED_PRICE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        ShippingFeeChartDTO shippingFeeChartDTO = shippingFeeChartMapper.toDto(updatedShippingFeeChart);

        restShippingFeeChartMockMvc.perform(put("/api/shipping-fee-charts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shippingFeeChartDTO)))
            .andExpect(status().isOk());

        // Validate the ShippingFeeChart in the database
        List<ShippingFeeChart> shippingFeeChartList = shippingFeeChartRepository.findAll();
        assertThat(shippingFeeChartList).hasSize(databaseSizeBeforeUpdate);
        ShippingFeeChart testShippingFeeChart = shippingFeeChartList.get(shippingFeeChartList.size() - 1);
        assertThat(testShippingFeeChart.getSizeOfPercel()).isEqualTo(UPDATED_SIZE_OF_PERCEL);
        assertThat(testShippingFeeChart.getMinVolumeWeight()).isEqualTo(UPDATED_MIN_VOLUME_WEIGHT);
        assertThat(testShippingFeeChart.getMaxVolumeWeight()).isEqualTo(UPDATED_MAX_VOLUME_WEIGHT);
        assertThat(testShippingFeeChart.getMinActualWeight()).isEqualTo(UPDATED_MIN_ACTUAL_WEIGHT);
        assertThat(testShippingFeeChart.getMaxActualWeight()).isEqualTo(UPDATED_MAX_ACTUAL_WEIGHT);
        assertThat(testShippingFeeChart.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testShippingFeeChart.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testShippingFeeChart.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingShippingFeeChart() throws Exception {
        int databaseSizeBeforeUpdate = shippingFeeChartRepository.findAll().size();

        // Create the ShippingFeeChart
        ShippingFeeChartDTO shippingFeeChartDTO = shippingFeeChartMapper.toDto(shippingFeeChart);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShippingFeeChartMockMvc.perform(put("/api/shipping-fee-charts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shippingFeeChartDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShippingFeeChart in the database
        List<ShippingFeeChart> shippingFeeChartList = shippingFeeChartRepository.findAll();
        assertThat(shippingFeeChartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShippingFeeChart() throws Exception {
        // Initialize the database
        shippingFeeChartRepository.saveAndFlush(shippingFeeChart);

        int databaseSizeBeforeDelete = shippingFeeChartRepository.findAll().size();

        // Delete the shippingFeeChart
        restShippingFeeChartMockMvc.perform(delete("/api/shipping-fee-charts/{id}", shippingFeeChart.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShippingFeeChart> shippingFeeChartList = shippingFeeChartRepository.findAll();
        assertThat(shippingFeeChartList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

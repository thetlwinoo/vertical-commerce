package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.StockItemsCulture;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.StockItems;
import com.vertical.commerce.repository.StockItemsCultureRepository;
import com.vertical.commerce.service.StockItemsCultureService;
import com.vertical.commerce.service.dto.StockItemsCultureDTO;
import com.vertical.commerce.service.mapper.StockItemsCultureMapper;
import com.vertical.commerce.service.dto.StockItemsCultureCriteria;
import com.vertical.commerce.service.StockItemsCultureQueryService;

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
 * Integration tests for the {@link StockItemsCultureResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class StockItemsCultureResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private StockItemsCultureRepository stockItemsCultureRepository;

    @Autowired
    private StockItemsCultureMapper stockItemsCultureMapper;

    @Autowired
    private StockItemsCultureService stockItemsCultureService;

    @Autowired
    private StockItemsCultureQueryService stockItemsCultureQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStockItemsCultureMockMvc;

    private StockItemsCulture stockItemsCulture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockItemsCulture createEntity(EntityManager em) {
        StockItemsCulture stockItemsCulture = new StockItemsCulture()
            .name(DEFAULT_NAME);
        return stockItemsCulture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockItemsCulture createUpdatedEntity(EntityManager em) {
        StockItemsCulture stockItemsCulture = new StockItemsCulture()
            .name(UPDATED_NAME);
        return stockItemsCulture;
    }

    @BeforeEach
    public void initTest() {
        stockItemsCulture = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockItemsCulture() throws Exception {
        int databaseSizeBeforeCreate = stockItemsCultureRepository.findAll().size();
        // Create the StockItemsCulture
        StockItemsCultureDTO stockItemsCultureDTO = stockItemsCultureMapper.toDto(stockItemsCulture);
        restStockItemsCultureMockMvc.perform(post("/api/stock-items-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsCultureDTO)))
            .andExpect(status().isCreated());

        // Validate the StockItemsCulture in the database
        List<StockItemsCulture> stockItemsCultureList = stockItemsCultureRepository.findAll();
        assertThat(stockItemsCultureList).hasSize(databaseSizeBeforeCreate + 1);
        StockItemsCulture testStockItemsCulture = stockItemsCultureList.get(stockItemsCultureList.size() - 1);
        assertThat(testStockItemsCulture.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createStockItemsCultureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockItemsCultureRepository.findAll().size();

        // Create the StockItemsCulture with an existing ID
        stockItemsCulture.setId(1L);
        StockItemsCultureDTO stockItemsCultureDTO = stockItemsCultureMapper.toDto(stockItemsCulture);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockItemsCultureMockMvc.perform(post("/api/stock-items-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItemsCulture in the database
        List<StockItemsCulture> stockItemsCultureList = stockItemsCultureRepository.findAll();
        assertThat(stockItemsCultureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemsCultureRepository.findAll().size();
        // set the field null
        stockItemsCulture.setName(null);

        // Create the StockItemsCulture, which fails.
        StockItemsCultureDTO stockItemsCultureDTO = stockItemsCultureMapper.toDto(stockItemsCulture);


        restStockItemsCultureMockMvc.perform(post("/api/stock-items-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsCultureDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemsCulture> stockItemsCultureList = stockItemsCultureRepository.findAll();
        assertThat(stockItemsCultureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockItemsCultures() throws Exception {
        // Initialize the database
        stockItemsCultureRepository.saveAndFlush(stockItemsCulture);

        // Get all the stockItemsCultureList
        restStockItemsCultureMockMvc.perform(get("/api/stock-items-cultures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockItemsCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getStockItemsCulture() throws Exception {
        // Initialize the database
        stockItemsCultureRepository.saveAndFlush(stockItemsCulture);

        // Get the stockItemsCulture
        restStockItemsCultureMockMvc.perform(get("/api/stock-items-cultures/{id}", stockItemsCulture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stockItemsCulture.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getStockItemsCulturesByIdFiltering() throws Exception {
        // Initialize the database
        stockItemsCultureRepository.saveAndFlush(stockItemsCulture);

        Long id = stockItemsCulture.getId();

        defaultStockItemsCultureShouldBeFound("id.equals=" + id);
        defaultStockItemsCultureShouldNotBeFound("id.notEquals=" + id);

        defaultStockItemsCultureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStockItemsCultureShouldNotBeFound("id.greaterThan=" + id);

        defaultStockItemsCultureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStockItemsCultureShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStockItemsCulturesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsCultureRepository.saveAndFlush(stockItemsCulture);

        // Get all the stockItemsCultureList where name equals to DEFAULT_NAME
        defaultStockItemsCultureShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the stockItemsCultureList where name equals to UPDATED_NAME
        defaultStockItemsCultureShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemsCulturesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsCultureRepository.saveAndFlush(stockItemsCulture);

        // Get all the stockItemsCultureList where name not equals to DEFAULT_NAME
        defaultStockItemsCultureShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the stockItemsCultureList where name not equals to UPDATED_NAME
        defaultStockItemsCultureShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemsCulturesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsCultureRepository.saveAndFlush(stockItemsCulture);

        // Get all the stockItemsCultureList where name in DEFAULT_NAME or UPDATED_NAME
        defaultStockItemsCultureShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the stockItemsCultureList where name equals to UPDATED_NAME
        defaultStockItemsCultureShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemsCulturesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsCultureRepository.saveAndFlush(stockItemsCulture);

        // Get all the stockItemsCultureList where name is not null
        defaultStockItemsCultureShouldBeFound("name.specified=true");

        // Get all the stockItemsCultureList where name is null
        defaultStockItemsCultureShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemsCulturesByNameContainsSomething() throws Exception {
        // Initialize the database
        stockItemsCultureRepository.saveAndFlush(stockItemsCulture);

        // Get all the stockItemsCultureList where name contains DEFAULT_NAME
        defaultStockItemsCultureShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the stockItemsCultureList where name contains UPDATED_NAME
        defaultStockItemsCultureShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemsCulturesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemsCultureRepository.saveAndFlush(stockItemsCulture);

        // Get all the stockItemsCultureList where name does not contain DEFAULT_NAME
        defaultStockItemsCultureShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the stockItemsCultureList where name does not contain UPDATED_NAME
        defaultStockItemsCultureShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllStockItemsCulturesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsCultureRepository.saveAndFlush(stockItemsCulture);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        stockItemsCulture.setCulture(culture);
        stockItemsCultureRepository.saveAndFlush(stockItemsCulture);
        Long cultureId = culture.getId();

        // Get all the stockItemsCultureList where culture equals to cultureId
        defaultStockItemsCultureShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the stockItemsCultureList where culture equals to cultureId + 1
        defaultStockItemsCultureShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemsCulturesByStockItemIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsCultureRepository.saveAndFlush(stockItemsCulture);
        StockItems stockItem = StockItemsResourceIT.createEntity(em);
        em.persist(stockItem);
        em.flush();
        stockItemsCulture.setStockItem(stockItem);
        stockItemsCultureRepository.saveAndFlush(stockItemsCulture);
        Long stockItemId = stockItem.getId();

        // Get all the stockItemsCultureList where stockItem equals to stockItemId
        defaultStockItemsCultureShouldBeFound("stockItemId.equals=" + stockItemId);

        // Get all the stockItemsCultureList where stockItem equals to stockItemId + 1
        defaultStockItemsCultureShouldNotBeFound("stockItemId.equals=" + (stockItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStockItemsCultureShouldBeFound(String filter) throws Exception {
        restStockItemsCultureMockMvc.perform(get("/api/stock-items-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockItemsCulture.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restStockItemsCultureMockMvc.perform(get("/api/stock-items-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStockItemsCultureShouldNotBeFound(String filter) throws Exception {
        restStockItemsCultureMockMvc.perform(get("/api/stock-items-cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStockItemsCultureMockMvc.perform(get("/api/stock-items-cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingStockItemsCulture() throws Exception {
        // Get the stockItemsCulture
        restStockItemsCultureMockMvc.perform(get("/api/stock-items-cultures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockItemsCulture() throws Exception {
        // Initialize the database
        stockItemsCultureRepository.saveAndFlush(stockItemsCulture);

        int databaseSizeBeforeUpdate = stockItemsCultureRepository.findAll().size();

        // Update the stockItemsCulture
        StockItemsCulture updatedStockItemsCulture = stockItemsCultureRepository.findById(stockItemsCulture.getId()).get();
        // Disconnect from session so that the updates on updatedStockItemsCulture are not directly saved in db
        em.detach(updatedStockItemsCulture);
        updatedStockItemsCulture
            .name(UPDATED_NAME);
        StockItemsCultureDTO stockItemsCultureDTO = stockItemsCultureMapper.toDto(updatedStockItemsCulture);

        restStockItemsCultureMockMvc.perform(put("/api/stock-items-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsCultureDTO)))
            .andExpect(status().isOk());

        // Validate the StockItemsCulture in the database
        List<StockItemsCulture> stockItemsCultureList = stockItemsCultureRepository.findAll();
        assertThat(stockItemsCultureList).hasSize(databaseSizeBeforeUpdate);
        StockItemsCulture testStockItemsCulture = stockItemsCultureList.get(stockItemsCultureList.size() - 1);
        assertThat(testStockItemsCulture.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingStockItemsCulture() throws Exception {
        int databaseSizeBeforeUpdate = stockItemsCultureRepository.findAll().size();

        // Create the StockItemsCulture
        StockItemsCultureDTO stockItemsCultureDTO = stockItemsCultureMapper.toDto(stockItemsCulture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockItemsCultureMockMvc.perform(put("/api/stock-items-cultures").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsCultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItemsCulture in the database
        List<StockItemsCulture> stockItemsCultureList = stockItemsCultureRepository.findAll();
        assertThat(stockItemsCultureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStockItemsCulture() throws Exception {
        // Initialize the database
        stockItemsCultureRepository.saveAndFlush(stockItemsCulture);

        int databaseSizeBeforeDelete = stockItemsCultureRepository.findAll().size();

        // Delete the stockItemsCulture
        restStockItemsCultureMockMvc.perform(delete("/api/stock-items-cultures/{id}", stockItemsCulture.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StockItemsCulture> stockItemsCultureList = stockItemsCultureRepository.findAll();
        assertThat(stockItemsCultureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.StockItemsLocalize;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.StockItems;
import com.vertical.commerce.repository.StockItemsLocalizeRepository;
import com.vertical.commerce.service.StockItemsLocalizeService;
import com.vertical.commerce.service.dto.StockItemsLocalizeDTO;
import com.vertical.commerce.service.mapper.StockItemsLocalizeMapper;
import com.vertical.commerce.service.dto.StockItemsLocalizeCriteria;
import com.vertical.commerce.service.StockItemsLocalizeQueryService;

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
 * Integration tests for the {@link StockItemsLocalizeResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class StockItemsLocalizeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private StockItemsLocalizeRepository stockItemsLocalizeRepository;

    @Autowired
    private StockItemsLocalizeMapper stockItemsLocalizeMapper;

    @Autowired
    private StockItemsLocalizeService stockItemsLocalizeService;

    @Autowired
    private StockItemsLocalizeQueryService stockItemsLocalizeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStockItemsLocalizeMockMvc;

    private StockItemsLocalize stockItemsLocalize;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockItemsLocalize createEntity(EntityManager em) {
        StockItemsLocalize stockItemsLocalize = new StockItemsLocalize()
            .name(DEFAULT_NAME);
        return stockItemsLocalize;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockItemsLocalize createUpdatedEntity(EntityManager em) {
        StockItemsLocalize stockItemsLocalize = new StockItemsLocalize()
            .name(UPDATED_NAME);
        return stockItemsLocalize;
    }

    @BeforeEach
    public void initTest() {
        stockItemsLocalize = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockItemsLocalize() throws Exception {
        int databaseSizeBeforeCreate = stockItemsLocalizeRepository.findAll().size();
        // Create the StockItemsLocalize
        StockItemsLocalizeDTO stockItemsLocalizeDTO = stockItemsLocalizeMapper.toDto(stockItemsLocalize);
        restStockItemsLocalizeMockMvc.perform(post("/api/stock-items-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsLocalizeDTO)))
            .andExpect(status().isCreated());

        // Validate the StockItemsLocalize in the database
        List<StockItemsLocalize> stockItemsLocalizeList = stockItemsLocalizeRepository.findAll();
        assertThat(stockItemsLocalizeList).hasSize(databaseSizeBeforeCreate + 1);
        StockItemsLocalize testStockItemsLocalize = stockItemsLocalizeList.get(stockItemsLocalizeList.size() - 1);
        assertThat(testStockItemsLocalize.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createStockItemsLocalizeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockItemsLocalizeRepository.findAll().size();

        // Create the StockItemsLocalize with an existing ID
        stockItemsLocalize.setId(1L);
        StockItemsLocalizeDTO stockItemsLocalizeDTO = stockItemsLocalizeMapper.toDto(stockItemsLocalize);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockItemsLocalizeMockMvc.perform(post("/api/stock-items-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItemsLocalize in the database
        List<StockItemsLocalize> stockItemsLocalizeList = stockItemsLocalizeRepository.findAll();
        assertThat(stockItemsLocalizeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemsLocalizeRepository.findAll().size();
        // set the field null
        stockItemsLocalize.setName(null);

        // Create the StockItemsLocalize, which fails.
        StockItemsLocalizeDTO stockItemsLocalizeDTO = stockItemsLocalizeMapper.toDto(stockItemsLocalize);


        restStockItemsLocalizeMockMvc.perform(post("/api/stock-items-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsLocalizeDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemsLocalize> stockItemsLocalizeList = stockItemsLocalizeRepository.findAll();
        assertThat(stockItemsLocalizeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockItemsLocalizes() throws Exception {
        // Initialize the database
        stockItemsLocalizeRepository.saveAndFlush(stockItemsLocalize);

        // Get all the stockItemsLocalizeList
        restStockItemsLocalizeMockMvc.perform(get("/api/stock-items-localizes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockItemsLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getStockItemsLocalize() throws Exception {
        // Initialize the database
        stockItemsLocalizeRepository.saveAndFlush(stockItemsLocalize);

        // Get the stockItemsLocalize
        restStockItemsLocalizeMockMvc.perform(get("/api/stock-items-localizes/{id}", stockItemsLocalize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stockItemsLocalize.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getStockItemsLocalizesByIdFiltering() throws Exception {
        // Initialize the database
        stockItemsLocalizeRepository.saveAndFlush(stockItemsLocalize);

        Long id = stockItemsLocalize.getId();

        defaultStockItemsLocalizeShouldBeFound("id.equals=" + id);
        defaultStockItemsLocalizeShouldNotBeFound("id.notEquals=" + id);

        defaultStockItemsLocalizeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStockItemsLocalizeShouldNotBeFound("id.greaterThan=" + id);

        defaultStockItemsLocalizeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStockItemsLocalizeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStockItemsLocalizesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsLocalizeRepository.saveAndFlush(stockItemsLocalize);

        // Get all the stockItemsLocalizeList where name equals to DEFAULT_NAME
        defaultStockItemsLocalizeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the stockItemsLocalizeList where name equals to UPDATED_NAME
        defaultStockItemsLocalizeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemsLocalizesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsLocalizeRepository.saveAndFlush(stockItemsLocalize);

        // Get all the stockItemsLocalizeList where name not equals to DEFAULT_NAME
        defaultStockItemsLocalizeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the stockItemsLocalizeList where name not equals to UPDATED_NAME
        defaultStockItemsLocalizeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemsLocalizesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsLocalizeRepository.saveAndFlush(stockItemsLocalize);

        // Get all the stockItemsLocalizeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultStockItemsLocalizeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the stockItemsLocalizeList where name equals to UPDATED_NAME
        defaultStockItemsLocalizeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemsLocalizesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsLocalizeRepository.saveAndFlush(stockItemsLocalize);

        // Get all the stockItemsLocalizeList where name is not null
        defaultStockItemsLocalizeShouldBeFound("name.specified=true");

        // Get all the stockItemsLocalizeList where name is null
        defaultStockItemsLocalizeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemsLocalizesByNameContainsSomething() throws Exception {
        // Initialize the database
        stockItemsLocalizeRepository.saveAndFlush(stockItemsLocalize);

        // Get all the stockItemsLocalizeList where name contains DEFAULT_NAME
        defaultStockItemsLocalizeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the stockItemsLocalizeList where name contains UPDATED_NAME
        defaultStockItemsLocalizeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemsLocalizesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemsLocalizeRepository.saveAndFlush(stockItemsLocalize);

        // Get all the stockItemsLocalizeList where name does not contain DEFAULT_NAME
        defaultStockItemsLocalizeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the stockItemsLocalizeList where name does not contain UPDATED_NAME
        defaultStockItemsLocalizeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllStockItemsLocalizesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsLocalizeRepository.saveAndFlush(stockItemsLocalize);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        stockItemsLocalize.setCulture(culture);
        stockItemsLocalizeRepository.saveAndFlush(stockItemsLocalize);
        Long cultureId = culture.getId();

        // Get all the stockItemsLocalizeList where culture equals to cultureId
        defaultStockItemsLocalizeShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the stockItemsLocalizeList where culture equals to cultureId + 1
        defaultStockItemsLocalizeShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemsLocalizesByStockItemIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsLocalizeRepository.saveAndFlush(stockItemsLocalize);
        StockItems stockItem = StockItemsResourceIT.createEntity(em);
        em.persist(stockItem);
        em.flush();
        stockItemsLocalize.setStockItem(stockItem);
        stockItemsLocalizeRepository.saveAndFlush(stockItemsLocalize);
        Long stockItemId = stockItem.getId();

        // Get all the stockItemsLocalizeList where stockItem equals to stockItemId
        defaultStockItemsLocalizeShouldBeFound("stockItemId.equals=" + stockItemId);

        // Get all the stockItemsLocalizeList where stockItem equals to stockItemId + 1
        defaultStockItemsLocalizeShouldNotBeFound("stockItemId.equals=" + (stockItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStockItemsLocalizeShouldBeFound(String filter) throws Exception {
        restStockItemsLocalizeMockMvc.perform(get("/api/stock-items-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockItemsLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restStockItemsLocalizeMockMvc.perform(get("/api/stock-items-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStockItemsLocalizeShouldNotBeFound(String filter) throws Exception {
        restStockItemsLocalizeMockMvc.perform(get("/api/stock-items-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStockItemsLocalizeMockMvc.perform(get("/api/stock-items-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingStockItemsLocalize() throws Exception {
        // Get the stockItemsLocalize
        restStockItemsLocalizeMockMvc.perform(get("/api/stock-items-localizes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockItemsLocalize() throws Exception {
        // Initialize the database
        stockItemsLocalizeRepository.saveAndFlush(stockItemsLocalize);

        int databaseSizeBeforeUpdate = stockItemsLocalizeRepository.findAll().size();

        // Update the stockItemsLocalize
        StockItemsLocalize updatedStockItemsLocalize = stockItemsLocalizeRepository.findById(stockItemsLocalize.getId()).get();
        // Disconnect from session so that the updates on updatedStockItemsLocalize are not directly saved in db
        em.detach(updatedStockItemsLocalize);
        updatedStockItemsLocalize
            .name(UPDATED_NAME);
        StockItemsLocalizeDTO stockItemsLocalizeDTO = stockItemsLocalizeMapper.toDto(updatedStockItemsLocalize);

        restStockItemsLocalizeMockMvc.perform(put("/api/stock-items-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsLocalizeDTO)))
            .andExpect(status().isOk());

        // Validate the StockItemsLocalize in the database
        List<StockItemsLocalize> stockItemsLocalizeList = stockItemsLocalizeRepository.findAll();
        assertThat(stockItemsLocalizeList).hasSize(databaseSizeBeforeUpdate);
        StockItemsLocalize testStockItemsLocalize = stockItemsLocalizeList.get(stockItemsLocalizeList.size() - 1);
        assertThat(testStockItemsLocalize.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingStockItemsLocalize() throws Exception {
        int databaseSizeBeforeUpdate = stockItemsLocalizeRepository.findAll().size();

        // Create the StockItemsLocalize
        StockItemsLocalizeDTO stockItemsLocalizeDTO = stockItemsLocalizeMapper.toDto(stockItemsLocalize);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockItemsLocalizeMockMvc.perform(put("/api/stock-items-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItemsLocalize in the database
        List<StockItemsLocalize> stockItemsLocalizeList = stockItemsLocalizeRepository.findAll();
        assertThat(stockItemsLocalizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStockItemsLocalize() throws Exception {
        // Initialize the database
        stockItemsLocalizeRepository.saveAndFlush(stockItemsLocalize);

        int databaseSizeBeforeDelete = stockItemsLocalizeRepository.findAll().size();

        // Delete the stockItemsLocalize
        restStockItemsLocalizeMockMvc.perform(delete("/api/stock-items-localizes/{id}", stockItemsLocalize.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StockItemsLocalize> stockItemsLocalizeList = stockItemsLocalizeRepository.findAll();
        assertThat(stockItemsLocalizeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

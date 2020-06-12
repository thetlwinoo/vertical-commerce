package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.CompareLines;
import com.vertical.commerce.domain.StockItems;
import com.vertical.commerce.domain.Compares;
import com.vertical.commerce.repository.CompareLinesRepository;
import com.vertical.commerce.service.CompareLinesService;
import com.vertical.commerce.service.dto.CompareLinesDTO;
import com.vertical.commerce.service.mapper.CompareLinesMapper;
import com.vertical.commerce.service.dto.CompareLinesCriteria;
import com.vertical.commerce.service.CompareLinesQueryService;

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
 * Integration tests for the {@link CompareLinesResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CompareLinesResourceIT {

    @Autowired
    private CompareLinesRepository compareLinesRepository;

    @Autowired
    private CompareLinesMapper compareLinesMapper;

    @Autowired
    private CompareLinesService compareLinesService;

    @Autowired
    private CompareLinesQueryService compareLinesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompareLinesMockMvc;

    private CompareLines compareLines;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompareLines createEntity(EntityManager em) {
        CompareLines compareLines = new CompareLines();
        return compareLines;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompareLines createUpdatedEntity(EntityManager em) {
        CompareLines compareLines = new CompareLines();
        return compareLines;
    }

    @BeforeEach
    public void initTest() {
        compareLines = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompareLines() throws Exception {
        int databaseSizeBeforeCreate = compareLinesRepository.findAll().size();
        // Create the CompareLines
        CompareLinesDTO compareLinesDTO = compareLinesMapper.toDto(compareLines);
        restCompareLinesMockMvc.perform(post("/api/compare-lines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(compareLinesDTO)))
            .andExpect(status().isCreated());

        // Validate the CompareLines in the database
        List<CompareLines> compareLinesList = compareLinesRepository.findAll();
        assertThat(compareLinesList).hasSize(databaseSizeBeforeCreate + 1);
        CompareLines testCompareLines = compareLinesList.get(compareLinesList.size() - 1);
    }

    @Test
    @Transactional
    public void createCompareLinesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compareLinesRepository.findAll().size();

        // Create the CompareLines with an existing ID
        compareLines.setId(1L);
        CompareLinesDTO compareLinesDTO = compareLinesMapper.toDto(compareLines);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompareLinesMockMvc.perform(post("/api/compare-lines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(compareLinesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompareLines in the database
        List<CompareLines> compareLinesList = compareLinesRepository.findAll();
        assertThat(compareLinesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompareLines() throws Exception {
        // Initialize the database
        compareLinesRepository.saveAndFlush(compareLines);

        // Get all the compareLinesList
        restCompareLinesMockMvc.perform(get("/api/compare-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compareLines.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getCompareLines() throws Exception {
        // Initialize the database
        compareLinesRepository.saveAndFlush(compareLines);

        // Get the compareLines
        restCompareLinesMockMvc.perform(get("/api/compare-lines/{id}", compareLines.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(compareLines.getId().intValue()));
    }


    @Test
    @Transactional
    public void getCompareLinesByIdFiltering() throws Exception {
        // Initialize the database
        compareLinesRepository.saveAndFlush(compareLines);

        Long id = compareLines.getId();

        defaultCompareLinesShouldBeFound("id.equals=" + id);
        defaultCompareLinesShouldNotBeFound("id.notEquals=" + id);

        defaultCompareLinesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompareLinesShouldNotBeFound("id.greaterThan=" + id);

        defaultCompareLinesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompareLinesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCompareLinesByStockItemIsEqualToSomething() throws Exception {
        // Initialize the database
        compareLinesRepository.saveAndFlush(compareLines);
        StockItems stockItem = StockItemsResourceIT.createEntity(em);
        em.persist(stockItem);
        em.flush();
        compareLines.setStockItem(stockItem);
        compareLinesRepository.saveAndFlush(compareLines);
        Long stockItemId = stockItem.getId();

        // Get all the compareLinesList where stockItem equals to stockItemId
        defaultCompareLinesShouldBeFound("stockItemId.equals=" + stockItemId);

        // Get all the compareLinesList where stockItem equals to stockItemId + 1
        defaultCompareLinesShouldNotBeFound("stockItemId.equals=" + (stockItemId + 1));
    }


    @Test
    @Transactional
    public void getAllCompareLinesByCompareIsEqualToSomething() throws Exception {
        // Initialize the database
        compareLinesRepository.saveAndFlush(compareLines);
        Compares compare = ComparesResourceIT.createEntity(em);
        em.persist(compare);
        em.flush();
        compareLines.setCompare(compare);
        compareLinesRepository.saveAndFlush(compareLines);
        Long compareId = compare.getId();

        // Get all the compareLinesList where compare equals to compareId
        defaultCompareLinesShouldBeFound("compareId.equals=" + compareId);

        // Get all the compareLinesList where compare equals to compareId + 1
        defaultCompareLinesShouldNotBeFound("compareId.equals=" + (compareId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompareLinesShouldBeFound(String filter) throws Exception {
        restCompareLinesMockMvc.perform(get("/api/compare-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compareLines.getId().intValue())));

        // Check, that the count call also returns 1
        restCompareLinesMockMvc.perform(get("/api/compare-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompareLinesShouldNotBeFound(String filter) throws Exception {
        restCompareLinesMockMvc.perform(get("/api/compare-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompareLinesMockMvc.perform(get("/api/compare-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCompareLines() throws Exception {
        // Get the compareLines
        restCompareLinesMockMvc.perform(get("/api/compare-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompareLines() throws Exception {
        // Initialize the database
        compareLinesRepository.saveAndFlush(compareLines);

        int databaseSizeBeforeUpdate = compareLinesRepository.findAll().size();

        // Update the compareLines
        CompareLines updatedCompareLines = compareLinesRepository.findById(compareLines.getId()).get();
        // Disconnect from session so that the updates on updatedCompareLines are not directly saved in db
        em.detach(updatedCompareLines);
        CompareLinesDTO compareLinesDTO = compareLinesMapper.toDto(updatedCompareLines);

        restCompareLinesMockMvc.perform(put("/api/compare-lines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(compareLinesDTO)))
            .andExpect(status().isOk());

        // Validate the CompareLines in the database
        List<CompareLines> compareLinesList = compareLinesRepository.findAll();
        assertThat(compareLinesList).hasSize(databaseSizeBeforeUpdate);
        CompareLines testCompareLines = compareLinesList.get(compareLinesList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCompareLines() throws Exception {
        int databaseSizeBeforeUpdate = compareLinesRepository.findAll().size();

        // Create the CompareLines
        CompareLinesDTO compareLinesDTO = compareLinesMapper.toDto(compareLines);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompareLinesMockMvc.perform(put("/api/compare-lines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(compareLinesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompareLines in the database
        List<CompareLines> compareLinesList = compareLinesRepository.findAll();
        assertThat(compareLinesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompareLines() throws Exception {
        // Initialize the database
        compareLinesRepository.saveAndFlush(compareLines);

        int databaseSizeBeforeDelete = compareLinesRepository.findAll().size();

        // Delete the compareLines
        restCompareLinesMockMvc.perform(delete("/api/compare-lines/{id}", compareLines.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompareLines> compareLinesList = compareLinesRepository.findAll();
        assertThat(compareLinesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

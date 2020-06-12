package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.WishlistLines;
import com.vertical.commerce.domain.StockItems;
import com.vertical.commerce.domain.Wishlists;
import com.vertical.commerce.repository.WishlistLinesRepository;
import com.vertical.commerce.service.WishlistLinesService;
import com.vertical.commerce.service.dto.WishlistLinesDTO;
import com.vertical.commerce.service.mapper.WishlistLinesMapper;
import com.vertical.commerce.service.dto.WishlistLinesCriteria;
import com.vertical.commerce.service.WishlistLinesQueryService;

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
 * Integration tests for the {@link WishlistLinesResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class WishlistLinesResourceIT {

    @Autowired
    private WishlistLinesRepository wishlistLinesRepository;

    @Autowired
    private WishlistLinesMapper wishlistLinesMapper;

    @Autowired
    private WishlistLinesService wishlistLinesService;

    @Autowired
    private WishlistLinesQueryService wishlistLinesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWishlistLinesMockMvc;

    private WishlistLines wishlistLines;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WishlistLines createEntity(EntityManager em) {
        WishlistLines wishlistLines = new WishlistLines();
        return wishlistLines;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WishlistLines createUpdatedEntity(EntityManager em) {
        WishlistLines wishlistLines = new WishlistLines();
        return wishlistLines;
    }

    @BeforeEach
    public void initTest() {
        wishlistLines = createEntity(em);
    }

    @Test
    @Transactional
    public void createWishlistLines() throws Exception {
        int databaseSizeBeforeCreate = wishlistLinesRepository.findAll().size();
        // Create the WishlistLines
        WishlistLinesDTO wishlistLinesDTO = wishlistLinesMapper.toDto(wishlistLines);
        restWishlistLinesMockMvc.perform(post("/api/wishlist-lines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wishlistLinesDTO)))
            .andExpect(status().isCreated());

        // Validate the WishlistLines in the database
        List<WishlistLines> wishlistLinesList = wishlistLinesRepository.findAll();
        assertThat(wishlistLinesList).hasSize(databaseSizeBeforeCreate + 1);
        WishlistLines testWishlistLines = wishlistLinesList.get(wishlistLinesList.size() - 1);
    }

    @Test
    @Transactional
    public void createWishlistLinesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wishlistLinesRepository.findAll().size();

        // Create the WishlistLines with an existing ID
        wishlistLines.setId(1L);
        WishlistLinesDTO wishlistLinesDTO = wishlistLinesMapper.toDto(wishlistLines);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWishlistLinesMockMvc.perform(post("/api/wishlist-lines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wishlistLinesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WishlistLines in the database
        List<WishlistLines> wishlistLinesList = wishlistLinesRepository.findAll();
        assertThat(wishlistLinesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWishlistLines() throws Exception {
        // Initialize the database
        wishlistLinesRepository.saveAndFlush(wishlistLines);

        // Get all the wishlistLinesList
        restWishlistLinesMockMvc.perform(get("/api/wishlist-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wishlistLines.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getWishlistLines() throws Exception {
        // Initialize the database
        wishlistLinesRepository.saveAndFlush(wishlistLines);

        // Get the wishlistLines
        restWishlistLinesMockMvc.perform(get("/api/wishlist-lines/{id}", wishlistLines.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wishlistLines.getId().intValue()));
    }


    @Test
    @Transactional
    public void getWishlistLinesByIdFiltering() throws Exception {
        // Initialize the database
        wishlistLinesRepository.saveAndFlush(wishlistLines);

        Long id = wishlistLines.getId();

        defaultWishlistLinesShouldBeFound("id.equals=" + id);
        defaultWishlistLinesShouldNotBeFound("id.notEquals=" + id);

        defaultWishlistLinesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWishlistLinesShouldNotBeFound("id.greaterThan=" + id);

        defaultWishlistLinesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWishlistLinesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWishlistLinesByStockItemIsEqualToSomething() throws Exception {
        // Initialize the database
        wishlistLinesRepository.saveAndFlush(wishlistLines);
        StockItems stockItem = StockItemsResourceIT.createEntity(em);
        em.persist(stockItem);
        em.flush();
        wishlistLines.setStockItem(stockItem);
        wishlistLinesRepository.saveAndFlush(wishlistLines);
        Long stockItemId = stockItem.getId();

        // Get all the wishlistLinesList where stockItem equals to stockItemId
        defaultWishlistLinesShouldBeFound("stockItemId.equals=" + stockItemId);

        // Get all the wishlistLinesList where stockItem equals to stockItemId + 1
        defaultWishlistLinesShouldNotBeFound("stockItemId.equals=" + (stockItemId + 1));
    }


    @Test
    @Transactional
    public void getAllWishlistLinesByWishlistIsEqualToSomething() throws Exception {
        // Initialize the database
        wishlistLinesRepository.saveAndFlush(wishlistLines);
        Wishlists wishlist = WishlistsResourceIT.createEntity(em);
        em.persist(wishlist);
        em.flush();
        wishlistLines.setWishlist(wishlist);
        wishlistLinesRepository.saveAndFlush(wishlistLines);
        Long wishlistId = wishlist.getId();

        // Get all the wishlistLinesList where wishlist equals to wishlistId
        defaultWishlistLinesShouldBeFound("wishlistId.equals=" + wishlistId);

        // Get all the wishlistLinesList where wishlist equals to wishlistId + 1
        defaultWishlistLinesShouldNotBeFound("wishlistId.equals=" + (wishlistId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWishlistLinesShouldBeFound(String filter) throws Exception {
        restWishlistLinesMockMvc.perform(get("/api/wishlist-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wishlistLines.getId().intValue())));

        // Check, that the count call also returns 1
        restWishlistLinesMockMvc.perform(get("/api/wishlist-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWishlistLinesShouldNotBeFound(String filter) throws Exception {
        restWishlistLinesMockMvc.perform(get("/api/wishlist-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWishlistLinesMockMvc.perform(get("/api/wishlist-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingWishlistLines() throws Exception {
        // Get the wishlistLines
        restWishlistLinesMockMvc.perform(get("/api/wishlist-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWishlistLines() throws Exception {
        // Initialize the database
        wishlistLinesRepository.saveAndFlush(wishlistLines);

        int databaseSizeBeforeUpdate = wishlistLinesRepository.findAll().size();

        // Update the wishlistLines
        WishlistLines updatedWishlistLines = wishlistLinesRepository.findById(wishlistLines.getId()).get();
        // Disconnect from session so that the updates on updatedWishlistLines are not directly saved in db
        em.detach(updatedWishlistLines);
        WishlistLinesDTO wishlistLinesDTO = wishlistLinesMapper.toDto(updatedWishlistLines);

        restWishlistLinesMockMvc.perform(put("/api/wishlist-lines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wishlistLinesDTO)))
            .andExpect(status().isOk());

        // Validate the WishlistLines in the database
        List<WishlistLines> wishlistLinesList = wishlistLinesRepository.findAll();
        assertThat(wishlistLinesList).hasSize(databaseSizeBeforeUpdate);
        WishlistLines testWishlistLines = wishlistLinesList.get(wishlistLinesList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingWishlistLines() throws Exception {
        int databaseSizeBeforeUpdate = wishlistLinesRepository.findAll().size();

        // Create the WishlistLines
        WishlistLinesDTO wishlistLinesDTO = wishlistLinesMapper.toDto(wishlistLines);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWishlistLinesMockMvc.perform(put("/api/wishlist-lines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wishlistLinesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WishlistLines in the database
        List<WishlistLines> wishlistLinesList = wishlistLinesRepository.findAll();
        assertThat(wishlistLinesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWishlistLines() throws Exception {
        // Initialize the database
        wishlistLinesRepository.saveAndFlush(wishlistLines);

        int databaseSizeBeforeDelete = wishlistLinesRepository.findAll().size();

        // Delete the wishlistLines
        restWishlistLinesMockMvc.perform(delete("/api/wishlist-lines/{id}", wishlistLines.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WishlistLines> wishlistLinesList = wishlistLinesRepository.findAll();
        assertThat(wishlistLinesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

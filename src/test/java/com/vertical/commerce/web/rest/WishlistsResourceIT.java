package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.Wishlists;
import com.vertical.commerce.domain.People;
import com.vertical.commerce.domain.WishlistLines;
import com.vertical.commerce.repository.WishlistsRepository;
import com.vertical.commerce.service.WishlistsService;
import com.vertical.commerce.service.dto.WishlistsDTO;
import com.vertical.commerce.service.mapper.WishlistsMapper;
import com.vertical.commerce.service.dto.WishlistsCriteria;
import com.vertical.commerce.service.WishlistsQueryService;

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
 * Integration tests for the {@link WishlistsResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class WishlistsResourceIT {

    @Autowired
    private WishlistsRepository wishlistsRepository;

    @Autowired
    private WishlistsMapper wishlistsMapper;

    @Autowired
    private WishlistsService wishlistsService;

    @Autowired
    private WishlistsQueryService wishlistsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWishlistsMockMvc;

    private Wishlists wishlists;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wishlists createEntity(EntityManager em) {
        Wishlists wishlists = new Wishlists();
        return wishlists;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wishlists createUpdatedEntity(EntityManager em) {
        Wishlists wishlists = new Wishlists();
        return wishlists;
    }

    @BeforeEach
    public void initTest() {
        wishlists = createEntity(em);
    }

    @Test
    @Transactional
    public void createWishlists() throws Exception {
        int databaseSizeBeforeCreate = wishlistsRepository.findAll().size();
        // Create the Wishlists
        WishlistsDTO wishlistsDTO = wishlistsMapper.toDto(wishlists);
        restWishlistsMockMvc.perform(post("/api/wishlists").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wishlistsDTO)))
            .andExpect(status().isCreated());

        // Validate the Wishlists in the database
        List<Wishlists> wishlistsList = wishlistsRepository.findAll();
        assertThat(wishlistsList).hasSize(databaseSizeBeforeCreate + 1);
        Wishlists testWishlists = wishlistsList.get(wishlistsList.size() - 1);
    }

    @Test
    @Transactional
    public void createWishlistsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wishlistsRepository.findAll().size();

        // Create the Wishlists with an existing ID
        wishlists.setId(1L);
        WishlistsDTO wishlistsDTO = wishlistsMapper.toDto(wishlists);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWishlistsMockMvc.perform(post("/api/wishlists").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wishlistsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wishlists in the database
        List<Wishlists> wishlistsList = wishlistsRepository.findAll();
        assertThat(wishlistsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWishlists() throws Exception {
        // Initialize the database
        wishlistsRepository.saveAndFlush(wishlists);

        // Get all the wishlistsList
        restWishlistsMockMvc.perform(get("/api/wishlists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wishlists.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getWishlists() throws Exception {
        // Initialize the database
        wishlistsRepository.saveAndFlush(wishlists);

        // Get the wishlists
        restWishlistsMockMvc.perform(get("/api/wishlists/{id}", wishlists.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wishlists.getId().intValue()));
    }


    @Test
    @Transactional
    public void getWishlistsByIdFiltering() throws Exception {
        // Initialize the database
        wishlistsRepository.saveAndFlush(wishlists);

        Long id = wishlists.getId();

        defaultWishlistsShouldBeFound("id.equals=" + id);
        defaultWishlistsShouldNotBeFound("id.notEquals=" + id);

        defaultWishlistsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWishlistsShouldNotBeFound("id.greaterThan=" + id);

        defaultWishlistsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWishlistsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWishlistsByWishlistUserIsEqualToSomething() throws Exception {
        // Initialize the database
        wishlistsRepository.saveAndFlush(wishlists);
        People wishlistUser = PeopleResourceIT.createEntity(em);
        em.persist(wishlistUser);
        em.flush();
        wishlists.setWishlistUser(wishlistUser);
        wishlistsRepository.saveAndFlush(wishlists);
        Long wishlistUserId = wishlistUser.getId();

        // Get all the wishlistsList where wishlistUser equals to wishlistUserId
        defaultWishlistsShouldBeFound("wishlistUserId.equals=" + wishlistUserId);

        // Get all the wishlistsList where wishlistUser equals to wishlistUserId + 1
        defaultWishlistsShouldNotBeFound("wishlistUserId.equals=" + (wishlistUserId + 1));
    }


    @Test
    @Transactional
    public void getAllWishlistsByWishlistLineListIsEqualToSomething() throws Exception {
        // Initialize the database
        wishlistsRepository.saveAndFlush(wishlists);
        WishlistLines wishlistLineList = WishlistLinesResourceIT.createEntity(em);
        em.persist(wishlistLineList);
        em.flush();
        wishlists.addWishlistLineList(wishlistLineList);
        wishlistsRepository.saveAndFlush(wishlists);
        Long wishlistLineListId = wishlistLineList.getId();

        // Get all the wishlistsList where wishlistLineList equals to wishlistLineListId
        defaultWishlistsShouldBeFound("wishlistLineListId.equals=" + wishlistLineListId);

        // Get all the wishlistsList where wishlistLineList equals to wishlistLineListId + 1
        defaultWishlistsShouldNotBeFound("wishlistLineListId.equals=" + (wishlistLineListId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWishlistsShouldBeFound(String filter) throws Exception {
        restWishlistsMockMvc.perform(get("/api/wishlists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wishlists.getId().intValue())));

        // Check, that the count call also returns 1
        restWishlistsMockMvc.perform(get("/api/wishlists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWishlistsShouldNotBeFound(String filter) throws Exception {
        restWishlistsMockMvc.perform(get("/api/wishlists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWishlistsMockMvc.perform(get("/api/wishlists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingWishlists() throws Exception {
        // Get the wishlists
        restWishlistsMockMvc.perform(get("/api/wishlists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWishlists() throws Exception {
        // Initialize the database
        wishlistsRepository.saveAndFlush(wishlists);

        int databaseSizeBeforeUpdate = wishlistsRepository.findAll().size();

        // Update the wishlists
        Wishlists updatedWishlists = wishlistsRepository.findById(wishlists.getId()).get();
        // Disconnect from session so that the updates on updatedWishlists are not directly saved in db
        em.detach(updatedWishlists);
        WishlistsDTO wishlistsDTO = wishlistsMapper.toDto(updatedWishlists);

        restWishlistsMockMvc.perform(put("/api/wishlists").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wishlistsDTO)))
            .andExpect(status().isOk());

        // Validate the Wishlists in the database
        List<Wishlists> wishlistsList = wishlistsRepository.findAll();
        assertThat(wishlistsList).hasSize(databaseSizeBeforeUpdate);
        Wishlists testWishlists = wishlistsList.get(wishlistsList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingWishlists() throws Exception {
        int databaseSizeBeforeUpdate = wishlistsRepository.findAll().size();

        // Create the Wishlists
        WishlistsDTO wishlistsDTO = wishlistsMapper.toDto(wishlists);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWishlistsMockMvc.perform(put("/api/wishlists").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wishlistsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wishlists in the database
        List<Wishlists> wishlistsList = wishlistsRepository.findAll();
        assertThat(wishlistsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWishlists() throws Exception {
        // Initialize the database
        wishlistsRepository.saveAndFlush(wishlists);

        int databaseSizeBeforeDelete = wishlistsRepository.findAll().size();

        // Delete the wishlists
        restWishlistsMockMvc.perform(delete("/api/wishlists/{id}", wishlists.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Wishlists> wishlistsList = wishlistsRepository.findAll();
        assertThat(wishlistsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

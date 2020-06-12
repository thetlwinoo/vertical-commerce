package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.ShipMethod;
import com.vertical.commerce.repository.ShipMethodRepository;
import com.vertical.commerce.service.ShipMethodService;
import com.vertical.commerce.service.dto.ShipMethodDTO;
import com.vertical.commerce.service.mapper.ShipMethodMapper;
import com.vertical.commerce.service.dto.ShipMethodCriteria;
import com.vertical.commerce.service.ShipMethodQueryService;

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
 * Integration tests for the {@link ShipMethodResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ShipMethodResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ShipMethodRepository shipMethodRepository;

    @Autowired
    private ShipMethodMapper shipMethodMapper;

    @Autowired
    private ShipMethodService shipMethodService;

    @Autowired
    private ShipMethodQueryService shipMethodQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShipMethodMockMvc;

    private ShipMethod shipMethod;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipMethod createEntity(EntityManager em) {
        ShipMethod shipMethod = new ShipMethod()
            .name(DEFAULT_NAME);
        return shipMethod;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipMethod createUpdatedEntity(EntityManager em) {
        ShipMethod shipMethod = new ShipMethod()
            .name(UPDATED_NAME);
        return shipMethod;
    }

    @BeforeEach
    public void initTest() {
        shipMethod = createEntity(em);
    }

    @Test
    @Transactional
    public void createShipMethod() throws Exception {
        int databaseSizeBeforeCreate = shipMethodRepository.findAll().size();
        // Create the ShipMethod
        ShipMethodDTO shipMethodDTO = shipMethodMapper.toDto(shipMethod);
        restShipMethodMockMvc.perform(post("/api/ship-methods").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shipMethodDTO)))
            .andExpect(status().isCreated());

        // Validate the ShipMethod in the database
        List<ShipMethod> shipMethodList = shipMethodRepository.findAll();
        assertThat(shipMethodList).hasSize(databaseSizeBeforeCreate + 1);
        ShipMethod testShipMethod = shipMethodList.get(shipMethodList.size() - 1);
        assertThat(testShipMethod.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createShipMethodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shipMethodRepository.findAll().size();

        // Create the ShipMethod with an existing ID
        shipMethod.setId(1L);
        ShipMethodDTO shipMethodDTO = shipMethodMapper.toDto(shipMethod);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipMethodMockMvc.perform(post("/api/ship-methods").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shipMethodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShipMethod in the database
        List<ShipMethod> shipMethodList = shipMethodRepository.findAll();
        assertThat(shipMethodList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllShipMethods() throws Exception {
        // Initialize the database
        shipMethodRepository.saveAndFlush(shipMethod);

        // Get all the shipMethodList
        restShipMethodMockMvc.perform(get("/api/ship-methods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getShipMethod() throws Exception {
        // Initialize the database
        shipMethodRepository.saveAndFlush(shipMethod);

        // Get the shipMethod
        restShipMethodMockMvc.perform(get("/api/ship-methods/{id}", shipMethod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shipMethod.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getShipMethodsByIdFiltering() throws Exception {
        // Initialize the database
        shipMethodRepository.saveAndFlush(shipMethod);

        Long id = shipMethod.getId();

        defaultShipMethodShouldBeFound("id.equals=" + id);
        defaultShipMethodShouldNotBeFound("id.notEquals=" + id);

        defaultShipMethodShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultShipMethodShouldNotBeFound("id.greaterThan=" + id);

        defaultShipMethodShouldBeFound("id.lessThanOrEqual=" + id);
        defaultShipMethodShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllShipMethodsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        shipMethodRepository.saveAndFlush(shipMethod);

        // Get all the shipMethodList where name equals to DEFAULT_NAME
        defaultShipMethodShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the shipMethodList where name equals to UPDATED_NAME
        defaultShipMethodShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllShipMethodsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shipMethodRepository.saveAndFlush(shipMethod);

        // Get all the shipMethodList where name not equals to DEFAULT_NAME
        defaultShipMethodShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the shipMethodList where name not equals to UPDATED_NAME
        defaultShipMethodShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllShipMethodsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        shipMethodRepository.saveAndFlush(shipMethod);

        // Get all the shipMethodList where name in DEFAULT_NAME or UPDATED_NAME
        defaultShipMethodShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the shipMethodList where name equals to UPDATED_NAME
        defaultShipMethodShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllShipMethodsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        shipMethodRepository.saveAndFlush(shipMethod);

        // Get all the shipMethodList where name is not null
        defaultShipMethodShouldBeFound("name.specified=true");

        // Get all the shipMethodList where name is null
        defaultShipMethodShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllShipMethodsByNameContainsSomething() throws Exception {
        // Initialize the database
        shipMethodRepository.saveAndFlush(shipMethod);

        // Get all the shipMethodList where name contains DEFAULT_NAME
        defaultShipMethodShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the shipMethodList where name contains UPDATED_NAME
        defaultShipMethodShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllShipMethodsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        shipMethodRepository.saveAndFlush(shipMethod);

        // Get all the shipMethodList where name does not contain DEFAULT_NAME
        defaultShipMethodShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the shipMethodList where name does not contain UPDATED_NAME
        defaultShipMethodShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultShipMethodShouldBeFound(String filter) throws Exception {
        restShipMethodMockMvc.perform(get("/api/ship-methods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restShipMethodMockMvc.perform(get("/api/ship-methods/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultShipMethodShouldNotBeFound(String filter) throws Exception {
        restShipMethodMockMvc.perform(get("/api/ship-methods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restShipMethodMockMvc.perform(get("/api/ship-methods/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingShipMethod() throws Exception {
        // Get the shipMethod
        restShipMethodMockMvc.perform(get("/api/ship-methods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipMethod() throws Exception {
        // Initialize the database
        shipMethodRepository.saveAndFlush(shipMethod);

        int databaseSizeBeforeUpdate = shipMethodRepository.findAll().size();

        // Update the shipMethod
        ShipMethod updatedShipMethod = shipMethodRepository.findById(shipMethod.getId()).get();
        // Disconnect from session so that the updates on updatedShipMethod are not directly saved in db
        em.detach(updatedShipMethod);
        updatedShipMethod
            .name(UPDATED_NAME);
        ShipMethodDTO shipMethodDTO = shipMethodMapper.toDto(updatedShipMethod);

        restShipMethodMockMvc.perform(put("/api/ship-methods").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shipMethodDTO)))
            .andExpect(status().isOk());

        // Validate the ShipMethod in the database
        List<ShipMethod> shipMethodList = shipMethodRepository.findAll();
        assertThat(shipMethodList).hasSize(databaseSizeBeforeUpdate);
        ShipMethod testShipMethod = shipMethodList.get(shipMethodList.size() - 1);
        assertThat(testShipMethod.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingShipMethod() throws Exception {
        int databaseSizeBeforeUpdate = shipMethodRepository.findAll().size();

        // Create the ShipMethod
        ShipMethodDTO shipMethodDTO = shipMethodMapper.toDto(shipMethod);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipMethodMockMvc.perform(put("/api/ship-methods").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shipMethodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShipMethod in the database
        List<ShipMethod> shipMethodList = shipMethodRepository.findAll();
        assertThat(shipMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShipMethod() throws Exception {
        // Initialize the database
        shipMethodRepository.saveAndFlush(shipMethod);

        int databaseSizeBeforeDelete = shipMethodRepository.findAll().size();

        // Delete the shipMethod
        restShipMethodMockMvc.perform(delete("/api/ship-methods/{id}", shipMethod.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShipMethod> shipMethodList = shipMethodRepository.findAll();
        assertThat(shipMethodList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.RegionsLocalize;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.Regions;
import com.vertical.commerce.repository.RegionsLocalizeRepository;
import com.vertical.commerce.service.RegionsLocalizeService;
import com.vertical.commerce.service.dto.RegionsLocalizeDTO;
import com.vertical.commerce.service.mapper.RegionsLocalizeMapper;
import com.vertical.commerce.service.dto.RegionsLocalizeCriteria;
import com.vertical.commerce.service.RegionsLocalizeQueryService;

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
 * Integration tests for the {@link RegionsLocalizeResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class RegionsLocalizeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private RegionsLocalizeRepository regionsLocalizeRepository;

    @Autowired
    private RegionsLocalizeMapper regionsLocalizeMapper;

    @Autowired
    private RegionsLocalizeService regionsLocalizeService;

    @Autowired
    private RegionsLocalizeQueryService regionsLocalizeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegionsLocalizeMockMvc;

    private RegionsLocalize regionsLocalize;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegionsLocalize createEntity(EntityManager em) {
        RegionsLocalize regionsLocalize = new RegionsLocalize()
            .name(DEFAULT_NAME);
        return regionsLocalize;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegionsLocalize createUpdatedEntity(EntityManager em) {
        RegionsLocalize regionsLocalize = new RegionsLocalize()
            .name(UPDATED_NAME);
        return regionsLocalize;
    }

    @BeforeEach
    public void initTest() {
        regionsLocalize = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegionsLocalize() throws Exception {
        int databaseSizeBeforeCreate = regionsLocalizeRepository.findAll().size();
        // Create the RegionsLocalize
        RegionsLocalizeDTO regionsLocalizeDTO = regionsLocalizeMapper.toDto(regionsLocalize);
        restRegionsLocalizeMockMvc.perform(post("/api/regions-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regionsLocalizeDTO)))
            .andExpect(status().isCreated());

        // Validate the RegionsLocalize in the database
        List<RegionsLocalize> regionsLocalizeList = regionsLocalizeRepository.findAll();
        assertThat(regionsLocalizeList).hasSize(databaseSizeBeforeCreate + 1);
        RegionsLocalize testRegionsLocalize = regionsLocalizeList.get(regionsLocalizeList.size() - 1);
        assertThat(testRegionsLocalize.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createRegionsLocalizeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regionsLocalizeRepository.findAll().size();

        // Create the RegionsLocalize with an existing ID
        regionsLocalize.setId(1L);
        RegionsLocalizeDTO regionsLocalizeDTO = regionsLocalizeMapper.toDto(regionsLocalize);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegionsLocalizeMockMvc.perform(post("/api/regions-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regionsLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegionsLocalize in the database
        List<RegionsLocalize> regionsLocalizeList = regionsLocalizeRepository.findAll();
        assertThat(regionsLocalizeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = regionsLocalizeRepository.findAll().size();
        // set the field null
        regionsLocalize.setName(null);

        // Create the RegionsLocalize, which fails.
        RegionsLocalizeDTO regionsLocalizeDTO = regionsLocalizeMapper.toDto(regionsLocalize);


        restRegionsLocalizeMockMvc.perform(post("/api/regions-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regionsLocalizeDTO)))
            .andExpect(status().isBadRequest());

        List<RegionsLocalize> regionsLocalizeList = regionsLocalizeRepository.findAll();
        assertThat(regionsLocalizeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegionsLocalizes() throws Exception {
        // Initialize the database
        regionsLocalizeRepository.saveAndFlush(regionsLocalize);

        // Get all the regionsLocalizeList
        restRegionsLocalizeMockMvc.perform(get("/api/regions-localizes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regionsLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getRegionsLocalize() throws Exception {
        // Initialize the database
        regionsLocalizeRepository.saveAndFlush(regionsLocalize);

        // Get the regionsLocalize
        restRegionsLocalizeMockMvc.perform(get("/api/regions-localizes/{id}", regionsLocalize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(regionsLocalize.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getRegionsLocalizesByIdFiltering() throws Exception {
        // Initialize the database
        regionsLocalizeRepository.saveAndFlush(regionsLocalize);

        Long id = regionsLocalize.getId();

        defaultRegionsLocalizeShouldBeFound("id.equals=" + id);
        defaultRegionsLocalizeShouldNotBeFound("id.notEquals=" + id);

        defaultRegionsLocalizeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRegionsLocalizeShouldNotBeFound("id.greaterThan=" + id);

        defaultRegionsLocalizeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRegionsLocalizeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRegionsLocalizesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        regionsLocalizeRepository.saveAndFlush(regionsLocalize);

        // Get all the regionsLocalizeList where name equals to DEFAULT_NAME
        defaultRegionsLocalizeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the regionsLocalizeList where name equals to UPDATED_NAME
        defaultRegionsLocalizeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegionsLocalizesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regionsLocalizeRepository.saveAndFlush(regionsLocalize);

        // Get all the regionsLocalizeList where name not equals to DEFAULT_NAME
        defaultRegionsLocalizeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the regionsLocalizeList where name not equals to UPDATED_NAME
        defaultRegionsLocalizeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegionsLocalizesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        regionsLocalizeRepository.saveAndFlush(regionsLocalize);

        // Get all the regionsLocalizeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRegionsLocalizeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the regionsLocalizeList where name equals to UPDATED_NAME
        defaultRegionsLocalizeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegionsLocalizesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionsLocalizeRepository.saveAndFlush(regionsLocalize);

        // Get all the regionsLocalizeList where name is not null
        defaultRegionsLocalizeShouldBeFound("name.specified=true");

        // Get all the regionsLocalizeList where name is null
        defaultRegionsLocalizeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllRegionsLocalizesByNameContainsSomething() throws Exception {
        // Initialize the database
        regionsLocalizeRepository.saveAndFlush(regionsLocalize);

        // Get all the regionsLocalizeList where name contains DEFAULT_NAME
        defaultRegionsLocalizeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the regionsLocalizeList where name contains UPDATED_NAME
        defaultRegionsLocalizeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegionsLocalizesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        regionsLocalizeRepository.saveAndFlush(regionsLocalize);

        // Get all the regionsLocalizeList where name does not contain DEFAULT_NAME
        defaultRegionsLocalizeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the regionsLocalizeList where name does not contain UPDATED_NAME
        defaultRegionsLocalizeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllRegionsLocalizesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        regionsLocalizeRepository.saveAndFlush(regionsLocalize);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        regionsLocalize.setCulture(culture);
        regionsLocalizeRepository.saveAndFlush(regionsLocalize);
        Long cultureId = culture.getId();

        // Get all the regionsLocalizeList where culture equals to cultureId
        defaultRegionsLocalizeShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the regionsLocalizeList where culture equals to cultureId + 1
        defaultRegionsLocalizeShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllRegionsLocalizesByRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        regionsLocalizeRepository.saveAndFlush(regionsLocalize);
        Regions region = RegionsResourceIT.createEntity(em);
        em.persist(region);
        em.flush();
        regionsLocalize.setRegion(region);
        regionsLocalizeRepository.saveAndFlush(regionsLocalize);
        Long regionId = region.getId();

        // Get all the regionsLocalizeList where region equals to regionId
        defaultRegionsLocalizeShouldBeFound("regionId.equals=" + regionId);

        // Get all the regionsLocalizeList where region equals to regionId + 1
        defaultRegionsLocalizeShouldNotBeFound("regionId.equals=" + (regionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRegionsLocalizeShouldBeFound(String filter) throws Exception {
        restRegionsLocalizeMockMvc.perform(get("/api/regions-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regionsLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restRegionsLocalizeMockMvc.perform(get("/api/regions-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRegionsLocalizeShouldNotBeFound(String filter) throws Exception {
        restRegionsLocalizeMockMvc.perform(get("/api/regions-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRegionsLocalizeMockMvc.perform(get("/api/regions-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRegionsLocalize() throws Exception {
        // Get the regionsLocalize
        restRegionsLocalizeMockMvc.perform(get("/api/regions-localizes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegionsLocalize() throws Exception {
        // Initialize the database
        regionsLocalizeRepository.saveAndFlush(regionsLocalize);

        int databaseSizeBeforeUpdate = regionsLocalizeRepository.findAll().size();

        // Update the regionsLocalize
        RegionsLocalize updatedRegionsLocalize = regionsLocalizeRepository.findById(regionsLocalize.getId()).get();
        // Disconnect from session so that the updates on updatedRegionsLocalize are not directly saved in db
        em.detach(updatedRegionsLocalize);
        updatedRegionsLocalize
            .name(UPDATED_NAME);
        RegionsLocalizeDTO regionsLocalizeDTO = regionsLocalizeMapper.toDto(updatedRegionsLocalize);

        restRegionsLocalizeMockMvc.perform(put("/api/regions-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regionsLocalizeDTO)))
            .andExpect(status().isOk());

        // Validate the RegionsLocalize in the database
        List<RegionsLocalize> regionsLocalizeList = regionsLocalizeRepository.findAll();
        assertThat(regionsLocalizeList).hasSize(databaseSizeBeforeUpdate);
        RegionsLocalize testRegionsLocalize = regionsLocalizeList.get(regionsLocalizeList.size() - 1);
        assertThat(testRegionsLocalize.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingRegionsLocalize() throws Exception {
        int databaseSizeBeforeUpdate = regionsLocalizeRepository.findAll().size();

        // Create the RegionsLocalize
        RegionsLocalizeDTO regionsLocalizeDTO = regionsLocalizeMapper.toDto(regionsLocalize);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegionsLocalizeMockMvc.perform(put("/api/regions-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regionsLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegionsLocalize in the database
        List<RegionsLocalize> regionsLocalizeList = regionsLocalizeRepository.findAll();
        assertThat(regionsLocalizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegionsLocalize() throws Exception {
        // Initialize the database
        regionsLocalizeRepository.saveAndFlush(regionsLocalize);

        int databaseSizeBeforeDelete = regionsLocalizeRepository.findAll().size();

        // Delete the regionsLocalize
        restRegionsLocalizeMockMvc.perform(delete("/api/regions-localizes/{id}", regionsLocalize.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegionsLocalize> regionsLocalizeList = regionsLocalizeRepository.findAll();
        assertThat(regionsLocalizeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

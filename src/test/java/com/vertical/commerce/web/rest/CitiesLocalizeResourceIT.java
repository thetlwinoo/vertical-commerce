package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.CitiesLocalize;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.Cities;
import com.vertical.commerce.repository.CitiesLocalizeRepository;
import com.vertical.commerce.service.CitiesLocalizeService;
import com.vertical.commerce.service.dto.CitiesLocalizeDTO;
import com.vertical.commerce.service.mapper.CitiesLocalizeMapper;
import com.vertical.commerce.service.dto.CitiesLocalizeCriteria;
import com.vertical.commerce.service.CitiesLocalizeQueryService;

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
 * Integration tests for the {@link CitiesLocalizeResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CitiesLocalizeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CitiesLocalizeRepository citiesLocalizeRepository;

    @Autowired
    private CitiesLocalizeMapper citiesLocalizeMapper;

    @Autowired
    private CitiesLocalizeService citiesLocalizeService;

    @Autowired
    private CitiesLocalizeQueryService citiesLocalizeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCitiesLocalizeMockMvc;

    private CitiesLocalize citiesLocalize;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CitiesLocalize createEntity(EntityManager em) {
        CitiesLocalize citiesLocalize = new CitiesLocalize()
            .name(DEFAULT_NAME);
        return citiesLocalize;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CitiesLocalize createUpdatedEntity(EntityManager em) {
        CitiesLocalize citiesLocalize = new CitiesLocalize()
            .name(UPDATED_NAME);
        return citiesLocalize;
    }

    @BeforeEach
    public void initTest() {
        citiesLocalize = createEntity(em);
    }

    @Test
    @Transactional
    public void createCitiesLocalize() throws Exception {
        int databaseSizeBeforeCreate = citiesLocalizeRepository.findAll().size();
        // Create the CitiesLocalize
        CitiesLocalizeDTO citiesLocalizeDTO = citiesLocalizeMapper.toDto(citiesLocalize);
        restCitiesLocalizeMockMvc.perform(post("/api/cities-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(citiesLocalizeDTO)))
            .andExpect(status().isCreated());

        // Validate the CitiesLocalize in the database
        List<CitiesLocalize> citiesLocalizeList = citiesLocalizeRepository.findAll();
        assertThat(citiesLocalizeList).hasSize(databaseSizeBeforeCreate + 1);
        CitiesLocalize testCitiesLocalize = citiesLocalizeList.get(citiesLocalizeList.size() - 1);
        assertThat(testCitiesLocalize.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCitiesLocalizeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = citiesLocalizeRepository.findAll().size();

        // Create the CitiesLocalize with an existing ID
        citiesLocalize.setId(1L);
        CitiesLocalizeDTO citiesLocalizeDTO = citiesLocalizeMapper.toDto(citiesLocalize);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCitiesLocalizeMockMvc.perform(post("/api/cities-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(citiesLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CitiesLocalize in the database
        List<CitiesLocalize> citiesLocalizeList = citiesLocalizeRepository.findAll();
        assertThat(citiesLocalizeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = citiesLocalizeRepository.findAll().size();
        // set the field null
        citiesLocalize.setName(null);

        // Create the CitiesLocalize, which fails.
        CitiesLocalizeDTO citiesLocalizeDTO = citiesLocalizeMapper.toDto(citiesLocalize);


        restCitiesLocalizeMockMvc.perform(post("/api/cities-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(citiesLocalizeDTO)))
            .andExpect(status().isBadRequest());

        List<CitiesLocalize> citiesLocalizeList = citiesLocalizeRepository.findAll();
        assertThat(citiesLocalizeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCitiesLocalizes() throws Exception {
        // Initialize the database
        citiesLocalizeRepository.saveAndFlush(citiesLocalize);

        // Get all the citiesLocalizeList
        restCitiesLocalizeMockMvc.perform(get("/api/cities-localizes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(citiesLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getCitiesLocalize() throws Exception {
        // Initialize the database
        citiesLocalizeRepository.saveAndFlush(citiesLocalize);

        // Get the citiesLocalize
        restCitiesLocalizeMockMvc.perform(get("/api/cities-localizes/{id}", citiesLocalize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(citiesLocalize.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getCitiesLocalizesByIdFiltering() throws Exception {
        // Initialize the database
        citiesLocalizeRepository.saveAndFlush(citiesLocalize);

        Long id = citiesLocalize.getId();

        defaultCitiesLocalizeShouldBeFound("id.equals=" + id);
        defaultCitiesLocalizeShouldNotBeFound("id.notEquals=" + id);

        defaultCitiesLocalizeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCitiesLocalizeShouldNotBeFound("id.greaterThan=" + id);

        defaultCitiesLocalizeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCitiesLocalizeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCitiesLocalizesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesLocalizeRepository.saveAndFlush(citiesLocalize);

        // Get all the citiesLocalizeList where name equals to DEFAULT_NAME
        defaultCitiesLocalizeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the citiesLocalizeList where name equals to UPDATED_NAME
        defaultCitiesLocalizeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCitiesLocalizesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citiesLocalizeRepository.saveAndFlush(citiesLocalize);

        // Get all the citiesLocalizeList where name not equals to DEFAULT_NAME
        defaultCitiesLocalizeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the citiesLocalizeList where name not equals to UPDATED_NAME
        defaultCitiesLocalizeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCitiesLocalizesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        citiesLocalizeRepository.saveAndFlush(citiesLocalize);

        // Get all the citiesLocalizeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCitiesLocalizeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the citiesLocalizeList where name equals to UPDATED_NAME
        defaultCitiesLocalizeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCitiesLocalizesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesLocalizeRepository.saveAndFlush(citiesLocalize);

        // Get all the citiesLocalizeList where name is not null
        defaultCitiesLocalizeShouldBeFound("name.specified=true");

        // Get all the citiesLocalizeList where name is null
        defaultCitiesLocalizeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCitiesLocalizesByNameContainsSomething() throws Exception {
        // Initialize the database
        citiesLocalizeRepository.saveAndFlush(citiesLocalize);

        // Get all the citiesLocalizeList where name contains DEFAULT_NAME
        defaultCitiesLocalizeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the citiesLocalizeList where name contains UPDATED_NAME
        defaultCitiesLocalizeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCitiesLocalizesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        citiesLocalizeRepository.saveAndFlush(citiesLocalize);

        // Get all the citiesLocalizeList where name does not contain DEFAULT_NAME
        defaultCitiesLocalizeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the citiesLocalizeList where name does not contain UPDATED_NAME
        defaultCitiesLocalizeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCitiesLocalizesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesLocalizeRepository.saveAndFlush(citiesLocalize);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        citiesLocalize.setCulture(culture);
        citiesLocalizeRepository.saveAndFlush(citiesLocalize);
        Long cultureId = culture.getId();

        // Get all the citiesLocalizeList where culture equals to cultureId
        defaultCitiesLocalizeShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the citiesLocalizeList where culture equals to cultureId + 1
        defaultCitiesLocalizeShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllCitiesLocalizesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesLocalizeRepository.saveAndFlush(citiesLocalize);
        Cities city = CitiesResourceIT.createEntity(em);
        em.persist(city);
        em.flush();
        citiesLocalize.setCity(city);
        citiesLocalizeRepository.saveAndFlush(citiesLocalize);
        Long cityId = city.getId();

        // Get all the citiesLocalizeList where city equals to cityId
        defaultCitiesLocalizeShouldBeFound("cityId.equals=" + cityId);

        // Get all the citiesLocalizeList where city equals to cityId + 1
        defaultCitiesLocalizeShouldNotBeFound("cityId.equals=" + (cityId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCitiesLocalizeShouldBeFound(String filter) throws Exception {
        restCitiesLocalizeMockMvc.perform(get("/api/cities-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(citiesLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restCitiesLocalizeMockMvc.perform(get("/api/cities-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCitiesLocalizeShouldNotBeFound(String filter) throws Exception {
        restCitiesLocalizeMockMvc.perform(get("/api/cities-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCitiesLocalizeMockMvc.perform(get("/api/cities-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCitiesLocalize() throws Exception {
        // Get the citiesLocalize
        restCitiesLocalizeMockMvc.perform(get("/api/cities-localizes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCitiesLocalize() throws Exception {
        // Initialize the database
        citiesLocalizeRepository.saveAndFlush(citiesLocalize);

        int databaseSizeBeforeUpdate = citiesLocalizeRepository.findAll().size();

        // Update the citiesLocalize
        CitiesLocalize updatedCitiesLocalize = citiesLocalizeRepository.findById(citiesLocalize.getId()).get();
        // Disconnect from session so that the updates on updatedCitiesLocalize are not directly saved in db
        em.detach(updatedCitiesLocalize);
        updatedCitiesLocalize
            .name(UPDATED_NAME);
        CitiesLocalizeDTO citiesLocalizeDTO = citiesLocalizeMapper.toDto(updatedCitiesLocalize);

        restCitiesLocalizeMockMvc.perform(put("/api/cities-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(citiesLocalizeDTO)))
            .andExpect(status().isOk());

        // Validate the CitiesLocalize in the database
        List<CitiesLocalize> citiesLocalizeList = citiesLocalizeRepository.findAll();
        assertThat(citiesLocalizeList).hasSize(databaseSizeBeforeUpdate);
        CitiesLocalize testCitiesLocalize = citiesLocalizeList.get(citiesLocalizeList.size() - 1);
        assertThat(testCitiesLocalize.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCitiesLocalize() throws Exception {
        int databaseSizeBeforeUpdate = citiesLocalizeRepository.findAll().size();

        // Create the CitiesLocalize
        CitiesLocalizeDTO citiesLocalizeDTO = citiesLocalizeMapper.toDto(citiesLocalize);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitiesLocalizeMockMvc.perform(put("/api/cities-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(citiesLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CitiesLocalize in the database
        List<CitiesLocalize> citiesLocalizeList = citiesLocalizeRepository.findAll();
        assertThat(citiesLocalizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCitiesLocalize() throws Exception {
        // Initialize the database
        citiesLocalizeRepository.saveAndFlush(citiesLocalize);

        int databaseSizeBeforeDelete = citiesLocalizeRepository.findAll().size();

        // Delete the citiesLocalize
        restCitiesLocalizeMockMvc.perform(delete("/api/cities-localizes/{id}", citiesLocalize.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CitiesLocalize> citiesLocalizeList = citiesLocalizeRepository.findAll();
        assertThat(citiesLocalizeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

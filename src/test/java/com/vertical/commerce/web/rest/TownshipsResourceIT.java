package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.Townships;
import com.vertical.commerce.domain.Cities;
import com.vertical.commerce.repository.TownshipsRepository;
import com.vertical.commerce.service.TownshipsService;
import com.vertical.commerce.service.dto.TownshipsDTO;
import com.vertical.commerce.service.mapper.TownshipsMapper;
import com.vertical.commerce.service.dto.TownshipsCriteria;
import com.vertical.commerce.service.TownshipsQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TownshipsResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TownshipsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CULTURE_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_CULTURE_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TownshipsRepository townshipsRepository;

    @Autowired
    private TownshipsMapper townshipsMapper;

    @Autowired
    private TownshipsService townshipsService;

    @Autowired
    private TownshipsQueryService townshipsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTownshipsMockMvc;

    private Townships townships;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Townships createEntity(EntityManager em) {
        Townships townships = new Townships()
            .name(DEFAULT_NAME)
            .cultureDetails(DEFAULT_CULTURE_DETAILS)
            .description(DEFAULT_DESCRIPTION)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return townships;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Townships createUpdatedEntity(EntityManager em) {
        Townships townships = new Townships()
            .name(UPDATED_NAME)
            .cultureDetails(UPDATED_CULTURE_DETAILS)
            .description(UPDATED_DESCRIPTION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return townships;
    }

    @BeforeEach
    public void initTest() {
        townships = createEntity(em);
    }

    @Test
    @Transactional
    public void createTownships() throws Exception {
        int databaseSizeBeforeCreate = townshipsRepository.findAll().size();
        // Create the Townships
        TownshipsDTO townshipsDTO = townshipsMapper.toDto(townships);
        restTownshipsMockMvc.perform(post("/api/townships").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townshipsDTO)))
            .andExpect(status().isCreated());

        // Validate the Townships in the database
        List<Townships> townshipsList = townshipsRepository.findAll();
        assertThat(townshipsList).hasSize(databaseSizeBeforeCreate + 1);
        Townships testTownships = townshipsList.get(townshipsList.size() - 1);
        assertThat(testTownships.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTownships.getCultureDetails()).isEqualTo(DEFAULT_CULTURE_DETAILS);
        assertThat(testTownships.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTownships.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testTownships.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createTownshipsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = townshipsRepository.findAll().size();

        // Create the Townships with an existing ID
        townships.setId(1L);
        TownshipsDTO townshipsDTO = townshipsMapper.toDto(townships);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTownshipsMockMvc.perform(post("/api/townships").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townshipsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Townships in the database
        List<Townships> townshipsList = townshipsRepository.findAll();
        assertThat(townshipsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = townshipsRepository.findAll().size();
        // set the field null
        townships.setName(null);

        // Create the Townships, which fails.
        TownshipsDTO townshipsDTO = townshipsMapper.toDto(townships);


        restTownshipsMockMvc.perform(post("/api/townships").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townshipsDTO)))
            .andExpect(status().isBadRequest());

        List<Townships> townshipsList = townshipsRepository.findAll();
        assertThat(townshipsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = townshipsRepository.findAll().size();
        // set the field null
        townships.setValidFrom(null);

        // Create the Townships, which fails.
        TownshipsDTO townshipsDTO = townshipsMapper.toDto(townships);


        restTownshipsMockMvc.perform(post("/api/townships").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townshipsDTO)))
            .andExpect(status().isBadRequest());

        List<Townships> townshipsList = townshipsRepository.findAll();
        assertThat(townshipsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTownships() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        // Get all the townshipsList
        restTownshipsMockMvc.perform(get("/api/townships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(townships.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].cultureDetails").value(hasItem(DEFAULT_CULTURE_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getTownships() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        // Get the townships
        restTownshipsMockMvc.perform(get("/api/townships/{id}", townships.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(townships.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.cultureDetails").value(DEFAULT_CULTURE_DETAILS.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getTownshipsByIdFiltering() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        Long id = townships.getId();

        defaultTownshipsShouldBeFound("id.equals=" + id);
        defaultTownshipsShouldNotBeFound("id.notEquals=" + id);

        defaultTownshipsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTownshipsShouldNotBeFound("id.greaterThan=" + id);

        defaultTownshipsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTownshipsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTownshipsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        // Get all the townshipsList where name equals to DEFAULT_NAME
        defaultTownshipsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the townshipsList where name equals to UPDATED_NAME
        defaultTownshipsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownshipsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        // Get all the townshipsList where name not equals to DEFAULT_NAME
        defaultTownshipsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the townshipsList where name not equals to UPDATED_NAME
        defaultTownshipsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownshipsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        // Get all the townshipsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTownshipsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the townshipsList where name equals to UPDATED_NAME
        defaultTownshipsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownshipsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        // Get all the townshipsList where name is not null
        defaultTownshipsShouldBeFound("name.specified=true");

        // Get all the townshipsList where name is null
        defaultTownshipsShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTownshipsByNameContainsSomething() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        // Get all the townshipsList where name contains DEFAULT_NAME
        defaultTownshipsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the townshipsList where name contains UPDATED_NAME
        defaultTownshipsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTownshipsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        // Get all the townshipsList where name does not contain DEFAULT_NAME
        defaultTownshipsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the townshipsList where name does not contain UPDATED_NAME
        defaultTownshipsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTownshipsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        // Get all the townshipsList where description equals to DEFAULT_DESCRIPTION
        defaultTownshipsShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the townshipsList where description equals to UPDATED_DESCRIPTION
        defaultTownshipsShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTownshipsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        // Get all the townshipsList where description not equals to DEFAULT_DESCRIPTION
        defaultTownshipsShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the townshipsList where description not equals to UPDATED_DESCRIPTION
        defaultTownshipsShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTownshipsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        // Get all the townshipsList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultTownshipsShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the townshipsList where description equals to UPDATED_DESCRIPTION
        defaultTownshipsShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTownshipsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        // Get all the townshipsList where description is not null
        defaultTownshipsShouldBeFound("description.specified=true");

        // Get all the townshipsList where description is null
        defaultTownshipsShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllTownshipsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        // Get all the townshipsList where description contains DEFAULT_DESCRIPTION
        defaultTownshipsShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the townshipsList where description contains UPDATED_DESCRIPTION
        defaultTownshipsShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTownshipsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        // Get all the townshipsList where description does not contain DEFAULT_DESCRIPTION
        defaultTownshipsShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the townshipsList where description does not contain UPDATED_DESCRIPTION
        defaultTownshipsShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllTownshipsByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        // Get all the townshipsList where validFrom equals to DEFAULT_VALID_FROM
        defaultTownshipsShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the townshipsList where validFrom equals to UPDATED_VALID_FROM
        defaultTownshipsShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllTownshipsByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        // Get all the townshipsList where validFrom not equals to DEFAULT_VALID_FROM
        defaultTownshipsShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the townshipsList where validFrom not equals to UPDATED_VALID_FROM
        defaultTownshipsShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllTownshipsByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        // Get all the townshipsList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultTownshipsShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the townshipsList where validFrom equals to UPDATED_VALID_FROM
        defaultTownshipsShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllTownshipsByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        // Get all the townshipsList where validFrom is not null
        defaultTownshipsShouldBeFound("validFrom.specified=true");

        // Get all the townshipsList where validFrom is null
        defaultTownshipsShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllTownshipsByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        // Get all the townshipsList where validTo equals to DEFAULT_VALID_TO
        defaultTownshipsShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the townshipsList where validTo equals to UPDATED_VALID_TO
        defaultTownshipsShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllTownshipsByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        // Get all the townshipsList where validTo not equals to DEFAULT_VALID_TO
        defaultTownshipsShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the townshipsList where validTo not equals to UPDATED_VALID_TO
        defaultTownshipsShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllTownshipsByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        // Get all the townshipsList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultTownshipsShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the townshipsList where validTo equals to UPDATED_VALID_TO
        defaultTownshipsShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllTownshipsByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        // Get all the townshipsList where validTo is not null
        defaultTownshipsShouldBeFound("validTo.specified=true");

        // Get all the townshipsList where validTo is null
        defaultTownshipsShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllTownshipsByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);
        Cities city = CitiesResourceIT.createEntity(em);
        em.persist(city);
        em.flush();
        townships.setCity(city);
        townshipsRepository.saveAndFlush(townships);
        Long cityId = city.getId();

        // Get all the townshipsList where city equals to cityId
        defaultTownshipsShouldBeFound("cityId.equals=" + cityId);

        // Get all the townshipsList where city equals to cityId + 1
        defaultTownshipsShouldNotBeFound("cityId.equals=" + (cityId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTownshipsShouldBeFound(String filter) throws Exception {
        restTownshipsMockMvc.perform(get("/api/townships?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(townships.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].cultureDetails").value(hasItem(DEFAULT_CULTURE_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restTownshipsMockMvc.perform(get("/api/townships/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTownshipsShouldNotBeFound(String filter) throws Exception {
        restTownshipsMockMvc.perform(get("/api/townships?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTownshipsMockMvc.perform(get("/api/townships/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTownships() throws Exception {
        // Get the townships
        restTownshipsMockMvc.perform(get("/api/townships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTownships() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        int databaseSizeBeforeUpdate = townshipsRepository.findAll().size();

        // Update the townships
        Townships updatedTownships = townshipsRepository.findById(townships.getId()).get();
        // Disconnect from session so that the updates on updatedTownships are not directly saved in db
        em.detach(updatedTownships);
        updatedTownships
            .name(UPDATED_NAME)
            .cultureDetails(UPDATED_CULTURE_DETAILS)
            .description(UPDATED_DESCRIPTION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        TownshipsDTO townshipsDTO = townshipsMapper.toDto(updatedTownships);

        restTownshipsMockMvc.perform(put("/api/townships").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townshipsDTO)))
            .andExpect(status().isOk());

        // Validate the Townships in the database
        List<Townships> townshipsList = townshipsRepository.findAll();
        assertThat(townshipsList).hasSize(databaseSizeBeforeUpdate);
        Townships testTownships = townshipsList.get(townshipsList.size() - 1);
        assertThat(testTownships.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTownships.getCultureDetails()).isEqualTo(UPDATED_CULTURE_DETAILS);
        assertThat(testTownships.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTownships.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testTownships.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingTownships() throws Exception {
        int databaseSizeBeforeUpdate = townshipsRepository.findAll().size();

        // Create the Townships
        TownshipsDTO townshipsDTO = townshipsMapper.toDto(townships);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTownshipsMockMvc.perform(put("/api/townships").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townshipsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Townships in the database
        List<Townships> townshipsList = townshipsRepository.findAll();
        assertThat(townshipsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTownships() throws Exception {
        // Initialize the database
        townshipsRepository.saveAndFlush(townships);

        int databaseSizeBeforeDelete = townshipsRepository.findAll().size();

        // Delete the townships
        restTownshipsMockMvc.perform(delete("/api/townships/{id}", townships.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Townships> townshipsList = townshipsRepository.findAll();
        assertThat(townshipsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

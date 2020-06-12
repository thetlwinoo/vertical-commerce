package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.CardTypes;
import com.vertical.commerce.repository.CardTypesRepository;
import com.vertical.commerce.service.CardTypesService;
import com.vertical.commerce.service.dto.CardTypesDTO;
import com.vertical.commerce.service.mapper.CardTypesMapper;
import com.vertical.commerce.service.dto.CardTypesCriteria;
import com.vertical.commerce.service.CardTypesQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CardTypesResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CardTypesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_ISSUER_ID = 1L;
    private static final Long UPDATED_ISSUER_ID = 2L;
    private static final Long SMALLER_ISSUER_ID = 1L - 1L;

    private static final Instant DEFAULT_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CardTypesRepository cardTypesRepository;

    @Autowired
    private CardTypesMapper cardTypesMapper;

    @Autowired
    private CardTypesService cardTypesService;

    @Autowired
    private CardTypesQueryService cardTypesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCardTypesMockMvc;

    private CardTypes cardTypes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CardTypes createEntity(EntityManager em) {
        CardTypes cardTypes = new CardTypes()
            .name(DEFAULT_NAME)
            .issuerId(DEFAULT_ISSUER_ID)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return cardTypes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CardTypes createUpdatedEntity(EntityManager em) {
        CardTypes cardTypes = new CardTypes()
            .name(UPDATED_NAME)
            .issuerId(UPDATED_ISSUER_ID)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return cardTypes;
    }

    @BeforeEach
    public void initTest() {
        cardTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createCardTypes() throws Exception {
        int databaseSizeBeforeCreate = cardTypesRepository.findAll().size();
        // Create the CardTypes
        CardTypesDTO cardTypesDTO = cardTypesMapper.toDto(cardTypes);
        restCardTypesMockMvc.perform(post("/api/card-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cardTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the CardTypes in the database
        List<CardTypes> cardTypesList = cardTypesRepository.findAll();
        assertThat(cardTypesList).hasSize(databaseSizeBeforeCreate + 1);
        CardTypes testCardTypes = cardTypesList.get(cardTypesList.size() - 1);
        assertThat(testCardTypes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCardTypes.getIssuerId()).isEqualTo(DEFAULT_ISSUER_ID);
        assertThat(testCardTypes.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createCardTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cardTypesRepository.findAll().size();

        // Create the CardTypes with an existing ID
        cardTypes.setId(1L);
        CardTypesDTO cardTypesDTO = cardTypesMapper.toDto(cardTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCardTypesMockMvc.perform(post("/api/card-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cardTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CardTypes in the database
        List<CardTypes> cardTypesList = cardTypesRepository.findAll();
        assertThat(cardTypesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCardTypes() throws Exception {
        // Initialize the database
        cardTypesRepository.saveAndFlush(cardTypes);

        // Get all the cardTypesList
        restCardTypesMockMvc.perform(get("/api/card-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cardTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].issuerId").value(hasItem(DEFAULT_ISSUER_ID.intValue())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getCardTypes() throws Exception {
        // Initialize the database
        cardTypesRepository.saveAndFlush(cardTypes);

        // Get the cardTypes
        restCardTypesMockMvc.perform(get("/api/card-types/{id}", cardTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cardTypes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.issuerId").value(DEFAULT_ISSUER_ID.intValue()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getCardTypesByIdFiltering() throws Exception {
        // Initialize the database
        cardTypesRepository.saveAndFlush(cardTypes);

        Long id = cardTypes.getId();

        defaultCardTypesShouldBeFound("id.equals=" + id);
        defaultCardTypesShouldNotBeFound("id.notEquals=" + id);

        defaultCardTypesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCardTypesShouldNotBeFound("id.greaterThan=" + id);

        defaultCardTypesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCardTypesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCardTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cardTypesRepository.saveAndFlush(cardTypes);

        // Get all the cardTypesList where name equals to DEFAULT_NAME
        defaultCardTypesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cardTypesList where name equals to UPDATED_NAME
        defaultCardTypesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCardTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardTypesRepository.saveAndFlush(cardTypes);

        // Get all the cardTypesList where name not equals to DEFAULT_NAME
        defaultCardTypesShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cardTypesList where name not equals to UPDATED_NAME
        defaultCardTypesShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCardTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cardTypesRepository.saveAndFlush(cardTypes);

        // Get all the cardTypesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCardTypesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cardTypesList where name equals to UPDATED_NAME
        defaultCardTypesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCardTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardTypesRepository.saveAndFlush(cardTypes);

        // Get all the cardTypesList where name is not null
        defaultCardTypesShouldBeFound("name.specified=true");

        // Get all the cardTypesList where name is null
        defaultCardTypesShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCardTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        cardTypesRepository.saveAndFlush(cardTypes);

        // Get all the cardTypesList where name contains DEFAULT_NAME
        defaultCardTypesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cardTypesList where name contains UPDATED_NAME
        defaultCardTypesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCardTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cardTypesRepository.saveAndFlush(cardTypes);

        // Get all the cardTypesList where name does not contain DEFAULT_NAME
        defaultCardTypesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cardTypesList where name does not contain UPDATED_NAME
        defaultCardTypesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCardTypesByIssuerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        cardTypesRepository.saveAndFlush(cardTypes);

        // Get all the cardTypesList where issuerId equals to DEFAULT_ISSUER_ID
        defaultCardTypesShouldBeFound("issuerId.equals=" + DEFAULT_ISSUER_ID);

        // Get all the cardTypesList where issuerId equals to UPDATED_ISSUER_ID
        defaultCardTypesShouldNotBeFound("issuerId.equals=" + UPDATED_ISSUER_ID);
    }

    @Test
    @Transactional
    public void getAllCardTypesByIssuerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardTypesRepository.saveAndFlush(cardTypes);

        // Get all the cardTypesList where issuerId not equals to DEFAULT_ISSUER_ID
        defaultCardTypesShouldNotBeFound("issuerId.notEquals=" + DEFAULT_ISSUER_ID);

        // Get all the cardTypesList where issuerId not equals to UPDATED_ISSUER_ID
        defaultCardTypesShouldBeFound("issuerId.notEquals=" + UPDATED_ISSUER_ID);
    }

    @Test
    @Transactional
    public void getAllCardTypesByIssuerIdIsInShouldWork() throws Exception {
        // Initialize the database
        cardTypesRepository.saveAndFlush(cardTypes);

        // Get all the cardTypesList where issuerId in DEFAULT_ISSUER_ID or UPDATED_ISSUER_ID
        defaultCardTypesShouldBeFound("issuerId.in=" + DEFAULT_ISSUER_ID + "," + UPDATED_ISSUER_ID);

        // Get all the cardTypesList where issuerId equals to UPDATED_ISSUER_ID
        defaultCardTypesShouldNotBeFound("issuerId.in=" + UPDATED_ISSUER_ID);
    }

    @Test
    @Transactional
    public void getAllCardTypesByIssuerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardTypesRepository.saveAndFlush(cardTypes);

        // Get all the cardTypesList where issuerId is not null
        defaultCardTypesShouldBeFound("issuerId.specified=true");

        // Get all the cardTypesList where issuerId is null
        defaultCardTypesShouldNotBeFound("issuerId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCardTypesByIssuerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cardTypesRepository.saveAndFlush(cardTypes);

        // Get all the cardTypesList where issuerId is greater than or equal to DEFAULT_ISSUER_ID
        defaultCardTypesShouldBeFound("issuerId.greaterThanOrEqual=" + DEFAULT_ISSUER_ID);

        // Get all the cardTypesList where issuerId is greater than or equal to UPDATED_ISSUER_ID
        defaultCardTypesShouldNotBeFound("issuerId.greaterThanOrEqual=" + UPDATED_ISSUER_ID);
    }

    @Test
    @Transactional
    public void getAllCardTypesByIssuerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cardTypesRepository.saveAndFlush(cardTypes);

        // Get all the cardTypesList where issuerId is less than or equal to DEFAULT_ISSUER_ID
        defaultCardTypesShouldBeFound("issuerId.lessThanOrEqual=" + DEFAULT_ISSUER_ID);

        // Get all the cardTypesList where issuerId is less than or equal to SMALLER_ISSUER_ID
        defaultCardTypesShouldNotBeFound("issuerId.lessThanOrEqual=" + SMALLER_ISSUER_ID);
    }

    @Test
    @Transactional
    public void getAllCardTypesByIssuerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        cardTypesRepository.saveAndFlush(cardTypes);

        // Get all the cardTypesList where issuerId is less than DEFAULT_ISSUER_ID
        defaultCardTypesShouldNotBeFound("issuerId.lessThan=" + DEFAULT_ISSUER_ID);

        // Get all the cardTypesList where issuerId is less than UPDATED_ISSUER_ID
        defaultCardTypesShouldBeFound("issuerId.lessThan=" + UPDATED_ISSUER_ID);
    }

    @Test
    @Transactional
    public void getAllCardTypesByIssuerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cardTypesRepository.saveAndFlush(cardTypes);

        // Get all the cardTypesList where issuerId is greater than DEFAULT_ISSUER_ID
        defaultCardTypesShouldNotBeFound("issuerId.greaterThan=" + DEFAULT_ISSUER_ID);

        // Get all the cardTypesList where issuerId is greater than SMALLER_ISSUER_ID
        defaultCardTypesShouldBeFound("issuerId.greaterThan=" + SMALLER_ISSUER_ID);
    }


    @Test
    @Transactional
    public void getAllCardTypesByModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        cardTypesRepository.saveAndFlush(cardTypes);

        // Get all the cardTypesList where modifiedDate equals to DEFAULT_MODIFIED_DATE
        defaultCardTypesShouldBeFound("modifiedDate.equals=" + DEFAULT_MODIFIED_DATE);

        // Get all the cardTypesList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultCardTypesShouldNotBeFound("modifiedDate.equals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllCardTypesByModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardTypesRepository.saveAndFlush(cardTypes);

        // Get all the cardTypesList where modifiedDate not equals to DEFAULT_MODIFIED_DATE
        defaultCardTypesShouldNotBeFound("modifiedDate.notEquals=" + DEFAULT_MODIFIED_DATE);

        // Get all the cardTypesList where modifiedDate not equals to UPDATED_MODIFIED_DATE
        defaultCardTypesShouldBeFound("modifiedDate.notEquals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllCardTypesByModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        cardTypesRepository.saveAndFlush(cardTypes);

        // Get all the cardTypesList where modifiedDate in DEFAULT_MODIFIED_DATE or UPDATED_MODIFIED_DATE
        defaultCardTypesShouldBeFound("modifiedDate.in=" + DEFAULT_MODIFIED_DATE + "," + UPDATED_MODIFIED_DATE);

        // Get all the cardTypesList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultCardTypesShouldNotBeFound("modifiedDate.in=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllCardTypesByModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardTypesRepository.saveAndFlush(cardTypes);

        // Get all the cardTypesList where modifiedDate is not null
        defaultCardTypesShouldBeFound("modifiedDate.specified=true");

        // Get all the cardTypesList where modifiedDate is null
        defaultCardTypesShouldNotBeFound("modifiedDate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCardTypesShouldBeFound(String filter) throws Exception {
        restCardTypesMockMvc.perform(get("/api/card-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cardTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].issuerId").value(hasItem(DEFAULT_ISSUER_ID.intValue())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restCardTypesMockMvc.perform(get("/api/card-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCardTypesShouldNotBeFound(String filter) throws Exception {
        restCardTypesMockMvc.perform(get("/api/card-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCardTypesMockMvc.perform(get("/api/card-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCardTypes() throws Exception {
        // Get the cardTypes
        restCardTypesMockMvc.perform(get("/api/card-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCardTypes() throws Exception {
        // Initialize the database
        cardTypesRepository.saveAndFlush(cardTypes);

        int databaseSizeBeforeUpdate = cardTypesRepository.findAll().size();

        // Update the cardTypes
        CardTypes updatedCardTypes = cardTypesRepository.findById(cardTypes.getId()).get();
        // Disconnect from session so that the updates on updatedCardTypes are not directly saved in db
        em.detach(updatedCardTypes);
        updatedCardTypes
            .name(UPDATED_NAME)
            .issuerId(UPDATED_ISSUER_ID)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        CardTypesDTO cardTypesDTO = cardTypesMapper.toDto(updatedCardTypes);

        restCardTypesMockMvc.perform(put("/api/card-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cardTypesDTO)))
            .andExpect(status().isOk());

        // Validate the CardTypes in the database
        List<CardTypes> cardTypesList = cardTypesRepository.findAll();
        assertThat(cardTypesList).hasSize(databaseSizeBeforeUpdate);
        CardTypes testCardTypes = cardTypesList.get(cardTypesList.size() - 1);
        assertThat(testCardTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCardTypes.getIssuerId()).isEqualTo(UPDATED_ISSUER_ID);
        assertThat(testCardTypes.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCardTypes() throws Exception {
        int databaseSizeBeforeUpdate = cardTypesRepository.findAll().size();

        // Create the CardTypes
        CardTypesDTO cardTypesDTO = cardTypesMapper.toDto(cardTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCardTypesMockMvc.perform(put("/api/card-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cardTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CardTypes in the database
        List<CardTypes> cardTypesList = cardTypesRepository.findAll();
        assertThat(cardTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCardTypes() throws Exception {
        // Initialize the database
        cardTypesRepository.saveAndFlush(cardTypes);

        int databaseSizeBeforeDelete = cardTypesRepository.findAll().size();

        // Delete the cardTypes
        restCardTypesMockMvc.perform(delete("/api/card-types/{id}", cardTypes.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CardTypes> cardTypesList = cardTypesRepository.findAll();
        assertThat(cardTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

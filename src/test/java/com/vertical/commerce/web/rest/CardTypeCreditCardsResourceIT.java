package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.CardTypeCreditCards;
import com.vertical.commerce.repository.CardTypeCreditCardsRepository;
import com.vertical.commerce.service.CardTypeCreditCardsService;
import com.vertical.commerce.service.dto.CardTypeCreditCardsDTO;
import com.vertical.commerce.service.mapper.CardTypeCreditCardsMapper;
import com.vertical.commerce.service.dto.CardTypeCreditCardsCriteria;
import com.vertical.commerce.service.CardTypeCreditCardsQueryService;

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
 * Integration tests for the {@link CardTypeCreditCardsResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CardTypeCreditCardsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_START_NUMBER = 1;
    private static final Integer UPDATED_START_NUMBER = 2;
    private static final Integer SMALLER_START_NUMBER = 1 - 1;

    private static final Integer DEFAULT_END_NUMBER = 1;
    private static final Integer UPDATED_END_NUMBER = 2;
    private static final Integer SMALLER_END_NUMBER = 1 - 1;

    private static final Instant DEFAULT_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CardTypeCreditCardsRepository cardTypeCreditCardsRepository;

    @Autowired
    private CardTypeCreditCardsMapper cardTypeCreditCardsMapper;

    @Autowired
    private CardTypeCreditCardsService cardTypeCreditCardsService;

    @Autowired
    private CardTypeCreditCardsQueryService cardTypeCreditCardsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCardTypeCreditCardsMockMvc;

    private CardTypeCreditCards cardTypeCreditCards;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CardTypeCreditCards createEntity(EntityManager em) {
        CardTypeCreditCards cardTypeCreditCards = new CardTypeCreditCards()
            .name(DEFAULT_NAME)
            .startNumber(DEFAULT_START_NUMBER)
            .endNumber(DEFAULT_END_NUMBER)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return cardTypeCreditCards;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CardTypeCreditCards createUpdatedEntity(EntityManager em) {
        CardTypeCreditCards cardTypeCreditCards = new CardTypeCreditCards()
            .name(UPDATED_NAME)
            .startNumber(UPDATED_START_NUMBER)
            .endNumber(UPDATED_END_NUMBER)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return cardTypeCreditCards;
    }

    @BeforeEach
    public void initTest() {
        cardTypeCreditCards = createEntity(em);
    }

    @Test
    @Transactional
    public void createCardTypeCreditCards() throws Exception {
        int databaseSizeBeforeCreate = cardTypeCreditCardsRepository.findAll().size();
        // Create the CardTypeCreditCards
        CardTypeCreditCardsDTO cardTypeCreditCardsDTO = cardTypeCreditCardsMapper.toDto(cardTypeCreditCards);
        restCardTypeCreditCardsMockMvc.perform(post("/api/card-type-credit-cards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cardTypeCreditCardsDTO)))
            .andExpect(status().isCreated());

        // Validate the CardTypeCreditCards in the database
        List<CardTypeCreditCards> cardTypeCreditCardsList = cardTypeCreditCardsRepository.findAll();
        assertThat(cardTypeCreditCardsList).hasSize(databaseSizeBeforeCreate + 1);
        CardTypeCreditCards testCardTypeCreditCards = cardTypeCreditCardsList.get(cardTypeCreditCardsList.size() - 1);
        assertThat(testCardTypeCreditCards.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCardTypeCreditCards.getStartNumber()).isEqualTo(DEFAULT_START_NUMBER);
        assertThat(testCardTypeCreditCards.getEndNumber()).isEqualTo(DEFAULT_END_NUMBER);
        assertThat(testCardTypeCreditCards.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createCardTypeCreditCardsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cardTypeCreditCardsRepository.findAll().size();

        // Create the CardTypeCreditCards with an existing ID
        cardTypeCreditCards.setId(1L);
        CardTypeCreditCardsDTO cardTypeCreditCardsDTO = cardTypeCreditCardsMapper.toDto(cardTypeCreditCards);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCardTypeCreditCardsMockMvc.perform(post("/api/card-type-credit-cards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cardTypeCreditCardsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CardTypeCreditCards in the database
        List<CardTypeCreditCards> cardTypeCreditCardsList = cardTypeCreditCardsRepository.findAll();
        assertThat(cardTypeCreditCardsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCardTypeCreditCards() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList
        restCardTypeCreditCardsMockMvc.perform(get("/api/card-type-credit-cards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cardTypeCreditCards.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].startNumber").value(hasItem(DEFAULT_START_NUMBER)))
            .andExpect(jsonPath("$.[*].endNumber").value(hasItem(DEFAULT_END_NUMBER)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getCardTypeCreditCards() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get the cardTypeCreditCards
        restCardTypeCreditCardsMockMvc.perform(get("/api/card-type-credit-cards/{id}", cardTypeCreditCards.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cardTypeCreditCards.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.startNumber").value(DEFAULT_START_NUMBER))
            .andExpect(jsonPath("$.endNumber").value(DEFAULT_END_NUMBER))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getCardTypeCreditCardsByIdFiltering() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        Long id = cardTypeCreditCards.getId();

        defaultCardTypeCreditCardsShouldBeFound("id.equals=" + id);
        defaultCardTypeCreditCardsShouldNotBeFound("id.notEquals=" + id);

        defaultCardTypeCreditCardsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCardTypeCreditCardsShouldNotBeFound("id.greaterThan=" + id);

        defaultCardTypeCreditCardsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCardTypeCreditCardsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where name equals to DEFAULT_NAME
        defaultCardTypeCreditCardsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cardTypeCreditCardsList where name equals to UPDATED_NAME
        defaultCardTypeCreditCardsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where name not equals to DEFAULT_NAME
        defaultCardTypeCreditCardsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cardTypeCreditCardsList where name not equals to UPDATED_NAME
        defaultCardTypeCreditCardsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCardTypeCreditCardsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cardTypeCreditCardsList where name equals to UPDATED_NAME
        defaultCardTypeCreditCardsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where name is not null
        defaultCardTypeCreditCardsShouldBeFound("name.specified=true");

        // Get all the cardTypeCreditCardsList where name is null
        defaultCardTypeCreditCardsShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCardTypeCreditCardsByNameContainsSomething() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where name contains DEFAULT_NAME
        defaultCardTypeCreditCardsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cardTypeCreditCardsList where name contains UPDATED_NAME
        defaultCardTypeCreditCardsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where name does not contain DEFAULT_NAME
        defaultCardTypeCreditCardsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cardTypeCreditCardsList where name does not contain UPDATED_NAME
        defaultCardTypeCreditCardsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByStartNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where startNumber equals to DEFAULT_START_NUMBER
        defaultCardTypeCreditCardsShouldBeFound("startNumber.equals=" + DEFAULT_START_NUMBER);

        // Get all the cardTypeCreditCardsList where startNumber equals to UPDATED_START_NUMBER
        defaultCardTypeCreditCardsShouldNotBeFound("startNumber.equals=" + UPDATED_START_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByStartNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where startNumber not equals to DEFAULT_START_NUMBER
        defaultCardTypeCreditCardsShouldNotBeFound("startNumber.notEquals=" + DEFAULT_START_NUMBER);

        // Get all the cardTypeCreditCardsList where startNumber not equals to UPDATED_START_NUMBER
        defaultCardTypeCreditCardsShouldBeFound("startNumber.notEquals=" + UPDATED_START_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByStartNumberIsInShouldWork() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where startNumber in DEFAULT_START_NUMBER or UPDATED_START_NUMBER
        defaultCardTypeCreditCardsShouldBeFound("startNumber.in=" + DEFAULT_START_NUMBER + "," + UPDATED_START_NUMBER);

        // Get all the cardTypeCreditCardsList where startNumber equals to UPDATED_START_NUMBER
        defaultCardTypeCreditCardsShouldNotBeFound("startNumber.in=" + UPDATED_START_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByStartNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where startNumber is not null
        defaultCardTypeCreditCardsShouldBeFound("startNumber.specified=true");

        // Get all the cardTypeCreditCardsList where startNumber is null
        defaultCardTypeCreditCardsShouldNotBeFound("startNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByStartNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where startNumber is greater than or equal to DEFAULT_START_NUMBER
        defaultCardTypeCreditCardsShouldBeFound("startNumber.greaterThanOrEqual=" + DEFAULT_START_NUMBER);

        // Get all the cardTypeCreditCardsList where startNumber is greater than or equal to UPDATED_START_NUMBER
        defaultCardTypeCreditCardsShouldNotBeFound("startNumber.greaterThanOrEqual=" + UPDATED_START_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByStartNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where startNumber is less than or equal to DEFAULT_START_NUMBER
        defaultCardTypeCreditCardsShouldBeFound("startNumber.lessThanOrEqual=" + DEFAULT_START_NUMBER);

        // Get all the cardTypeCreditCardsList where startNumber is less than or equal to SMALLER_START_NUMBER
        defaultCardTypeCreditCardsShouldNotBeFound("startNumber.lessThanOrEqual=" + SMALLER_START_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByStartNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where startNumber is less than DEFAULT_START_NUMBER
        defaultCardTypeCreditCardsShouldNotBeFound("startNumber.lessThan=" + DEFAULT_START_NUMBER);

        // Get all the cardTypeCreditCardsList where startNumber is less than UPDATED_START_NUMBER
        defaultCardTypeCreditCardsShouldBeFound("startNumber.lessThan=" + UPDATED_START_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByStartNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where startNumber is greater than DEFAULT_START_NUMBER
        defaultCardTypeCreditCardsShouldNotBeFound("startNumber.greaterThan=" + DEFAULT_START_NUMBER);

        // Get all the cardTypeCreditCardsList where startNumber is greater than SMALLER_START_NUMBER
        defaultCardTypeCreditCardsShouldBeFound("startNumber.greaterThan=" + SMALLER_START_NUMBER);
    }


    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByEndNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where endNumber equals to DEFAULT_END_NUMBER
        defaultCardTypeCreditCardsShouldBeFound("endNumber.equals=" + DEFAULT_END_NUMBER);

        // Get all the cardTypeCreditCardsList where endNumber equals to UPDATED_END_NUMBER
        defaultCardTypeCreditCardsShouldNotBeFound("endNumber.equals=" + UPDATED_END_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByEndNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where endNumber not equals to DEFAULT_END_NUMBER
        defaultCardTypeCreditCardsShouldNotBeFound("endNumber.notEquals=" + DEFAULT_END_NUMBER);

        // Get all the cardTypeCreditCardsList where endNumber not equals to UPDATED_END_NUMBER
        defaultCardTypeCreditCardsShouldBeFound("endNumber.notEquals=" + UPDATED_END_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByEndNumberIsInShouldWork() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where endNumber in DEFAULT_END_NUMBER or UPDATED_END_NUMBER
        defaultCardTypeCreditCardsShouldBeFound("endNumber.in=" + DEFAULT_END_NUMBER + "," + UPDATED_END_NUMBER);

        // Get all the cardTypeCreditCardsList where endNumber equals to UPDATED_END_NUMBER
        defaultCardTypeCreditCardsShouldNotBeFound("endNumber.in=" + UPDATED_END_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByEndNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where endNumber is not null
        defaultCardTypeCreditCardsShouldBeFound("endNumber.specified=true");

        // Get all the cardTypeCreditCardsList where endNumber is null
        defaultCardTypeCreditCardsShouldNotBeFound("endNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByEndNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where endNumber is greater than or equal to DEFAULT_END_NUMBER
        defaultCardTypeCreditCardsShouldBeFound("endNumber.greaterThanOrEqual=" + DEFAULT_END_NUMBER);

        // Get all the cardTypeCreditCardsList where endNumber is greater than or equal to UPDATED_END_NUMBER
        defaultCardTypeCreditCardsShouldNotBeFound("endNumber.greaterThanOrEqual=" + UPDATED_END_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByEndNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where endNumber is less than or equal to DEFAULT_END_NUMBER
        defaultCardTypeCreditCardsShouldBeFound("endNumber.lessThanOrEqual=" + DEFAULT_END_NUMBER);

        // Get all the cardTypeCreditCardsList where endNumber is less than or equal to SMALLER_END_NUMBER
        defaultCardTypeCreditCardsShouldNotBeFound("endNumber.lessThanOrEqual=" + SMALLER_END_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByEndNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where endNumber is less than DEFAULT_END_NUMBER
        defaultCardTypeCreditCardsShouldNotBeFound("endNumber.lessThan=" + DEFAULT_END_NUMBER);

        // Get all the cardTypeCreditCardsList where endNumber is less than UPDATED_END_NUMBER
        defaultCardTypeCreditCardsShouldBeFound("endNumber.lessThan=" + UPDATED_END_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByEndNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where endNumber is greater than DEFAULT_END_NUMBER
        defaultCardTypeCreditCardsShouldNotBeFound("endNumber.greaterThan=" + DEFAULT_END_NUMBER);

        // Get all the cardTypeCreditCardsList where endNumber is greater than SMALLER_END_NUMBER
        defaultCardTypeCreditCardsShouldBeFound("endNumber.greaterThan=" + SMALLER_END_NUMBER);
    }


    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where modifiedDate equals to DEFAULT_MODIFIED_DATE
        defaultCardTypeCreditCardsShouldBeFound("modifiedDate.equals=" + DEFAULT_MODIFIED_DATE);

        // Get all the cardTypeCreditCardsList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultCardTypeCreditCardsShouldNotBeFound("modifiedDate.equals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where modifiedDate not equals to DEFAULT_MODIFIED_DATE
        defaultCardTypeCreditCardsShouldNotBeFound("modifiedDate.notEquals=" + DEFAULT_MODIFIED_DATE);

        // Get all the cardTypeCreditCardsList where modifiedDate not equals to UPDATED_MODIFIED_DATE
        defaultCardTypeCreditCardsShouldBeFound("modifiedDate.notEquals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where modifiedDate in DEFAULT_MODIFIED_DATE or UPDATED_MODIFIED_DATE
        defaultCardTypeCreditCardsShouldBeFound("modifiedDate.in=" + DEFAULT_MODIFIED_DATE + "," + UPDATED_MODIFIED_DATE);

        // Get all the cardTypeCreditCardsList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultCardTypeCreditCardsShouldNotBeFound("modifiedDate.in=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllCardTypeCreditCardsByModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        // Get all the cardTypeCreditCardsList where modifiedDate is not null
        defaultCardTypeCreditCardsShouldBeFound("modifiedDate.specified=true");

        // Get all the cardTypeCreditCardsList where modifiedDate is null
        defaultCardTypeCreditCardsShouldNotBeFound("modifiedDate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCardTypeCreditCardsShouldBeFound(String filter) throws Exception {
        restCardTypeCreditCardsMockMvc.perform(get("/api/card-type-credit-cards?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cardTypeCreditCards.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].startNumber").value(hasItem(DEFAULT_START_NUMBER)))
            .andExpect(jsonPath("$.[*].endNumber").value(hasItem(DEFAULT_END_NUMBER)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restCardTypeCreditCardsMockMvc.perform(get("/api/card-type-credit-cards/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCardTypeCreditCardsShouldNotBeFound(String filter) throws Exception {
        restCardTypeCreditCardsMockMvc.perform(get("/api/card-type-credit-cards?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCardTypeCreditCardsMockMvc.perform(get("/api/card-type-credit-cards/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCardTypeCreditCards() throws Exception {
        // Get the cardTypeCreditCards
        restCardTypeCreditCardsMockMvc.perform(get("/api/card-type-credit-cards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCardTypeCreditCards() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        int databaseSizeBeforeUpdate = cardTypeCreditCardsRepository.findAll().size();

        // Update the cardTypeCreditCards
        CardTypeCreditCards updatedCardTypeCreditCards = cardTypeCreditCardsRepository.findById(cardTypeCreditCards.getId()).get();
        // Disconnect from session so that the updates on updatedCardTypeCreditCards are not directly saved in db
        em.detach(updatedCardTypeCreditCards);
        updatedCardTypeCreditCards
            .name(UPDATED_NAME)
            .startNumber(UPDATED_START_NUMBER)
            .endNumber(UPDATED_END_NUMBER)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        CardTypeCreditCardsDTO cardTypeCreditCardsDTO = cardTypeCreditCardsMapper.toDto(updatedCardTypeCreditCards);

        restCardTypeCreditCardsMockMvc.perform(put("/api/card-type-credit-cards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cardTypeCreditCardsDTO)))
            .andExpect(status().isOk());

        // Validate the CardTypeCreditCards in the database
        List<CardTypeCreditCards> cardTypeCreditCardsList = cardTypeCreditCardsRepository.findAll();
        assertThat(cardTypeCreditCardsList).hasSize(databaseSizeBeforeUpdate);
        CardTypeCreditCards testCardTypeCreditCards = cardTypeCreditCardsList.get(cardTypeCreditCardsList.size() - 1);
        assertThat(testCardTypeCreditCards.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCardTypeCreditCards.getStartNumber()).isEqualTo(UPDATED_START_NUMBER);
        assertThat(testCardTypeCreditCards.getEndNumber()).isEqualTo(UPDATED_END_NUMBER);
        assertThat(testCardTypeCreditCards.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCardTypeCreditCards() throws Exception {
        int databaseSizeBeforeUpdate = cardTypeCreditCardsRepository.findAll().size();

        // Create the CardTypeCreditCards
        CardTypeCreditCardsDTO cardTypeCreditCardsDTO = cardTypeCreditCardsMapper.toDto(cardTypeCreditCards);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCardTypeCreditCardsMockMvc.perform(put("/api/card-type-credit-cards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cardTypeCreditCardsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CardTypeCreditCards in the database
        List<CardTypeCreditCards> cardTypeCreditCardsList = cardTypeCreditCardsRepository.findAll();
        assertThat(cardTypeCreditCardsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCardTypeCreditCards() throws Exception {
        // Initialize the database
        cardTypeCreditCardsRepository.saveAndFlush(cardTypeCreditCards);

        int databaseSizeBeforeDelete = cardTypeCreditCardsRepository.findAll().size();

        // Delete the cardTypeCreditCards
        restCardTypeCreditCardsMockMvc.perform(delete("/api/card-type-credit-cards/{id}", cardTypeCreditCards.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CardTypeCreditCards> cardTypeCreditCardsList = cardTypeCreditCardsRepository.findAll();
        assertThat(cardTypeCreditCardsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

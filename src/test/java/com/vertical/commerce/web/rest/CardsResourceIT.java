package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.Cards;
import com.vertical.commerce.repository.CardsRepository;
import com.vertical.commerce.service.CardsService;
import com.vertical.commerce.service.dto.CardsDTO;
import com.vertical.commerce.service.mapper.CardsMapper;
import com.vertical.commerce.service.dto.CardsCriteria;
import com.vertical.commerce.service.CardsQueryService;

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
 * Integration tests for the {@link CardsResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CardsResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private CardsMapper cardsMapper;

    @Autowired
    private CardsService cardsService;

    @Autowired
    private CardsQueryService cardsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCardsMockMvc;

    private Cards cards;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cards createEntity(EntityManager em) {
        Cards cards = new Cards()
            .number(DEFAULT_NUMBER)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return cards;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cards createUpdatedEntity(EntityManager em) {
        Cards cards = new Cards()
            .number(UPDATED_NUMBER)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return cards;
    }

    @BeforeEach
    public void initTest() {
        cards = createEntity(em);
    }

    @Test
    @Transactional
    public void createCards() throws Exception {
        int databaseSizeBeforeCreate = cardsRepository.findAll().size();
        // Create the Cards
        CardsDTO cardsDTO = cardsMapper.toDto(cards);
        restCardsMockMvc.perform(post("/api/cards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cardsDTO)))
            .andExpect(status().isCreated());

        // Validate the Cards in the database
        List<Cards> cardsList = cardsRepository.findAll();
        assertThat(cardsList).hasSize(databaseSizeBeforeCreate + 1);
        Cards testCards = cardsList.get(cardsList.size() - 1);
        assertThat(testCards.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testCards.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createCardsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cardsRepository.findAll().size();

        // Create the Cards with an existing ID
        cards.setId(1L);
        CardsDTO cardsDTO = cardsMapper.toDto(cards);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCardsMockMvc.perform(post("/api/cards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cardsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cards in the database
        List<Cards> cardsList = cardsRepository.findAll();
        assertThat(cardsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCards() throws Exception {
        // Initialize the database
        cardsRepository.saveAndFlush(cards);

        // Get all the cardsList
        restCardsMockMvc.perform(get("/api/cards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cards.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getCards() throws Exception {
        // Initialize the database
        cardsRepository.saveAndFlush(cards);

        // Get the cards
        restCardsMockMvc.perform(get("/api/cards/{id}", cards.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cards.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getCardsByIdFiltering() throws Exception {
        // Initialize the database
        cardsRepository.saveAndFlush(cards);

        Long id = cards.getId();

        defaultCardsShouldBeFound("id.equals=" + id);
        defaultCardsShouldNotBeFound("id.notEquals=" + id);

        defaultCardsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCardsShouldNotBeFound("id.greaterThan=" + id);

        defaultCardsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCardsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCardsByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        cardsRepository.saveAndFlush(cards);

        // Get all the cardsList where number equals to DEFAULT_NUMBER
        defaultCardsShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the cardsList where number equals to UPDATED_NUMBER
        defaultCardsShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCardsByNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardsRepository.saveAndFlush(cards);

        // Get all the cardsList where number not equals to DEFAULT_NUMBER
        defaultCardsShouldNotBeFound("number.notEquals=" + DEFAULT_NUMBER);

        // Get all the cardsList where number not equals to UPDATED_NUMBER
        defaultCardsShouldBeFound("number.notEquals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCardsByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        cardsRepository.saveAndFlush(cards);

        // Get all the cardsList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultCardsShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the cardsList where number equals to UPDATED_NUMBER
        defaultCardsShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCardsByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardsRepository.saveAndFlush(cards);

        // Get all the cardsList where number is not null
        defaultCardsShouldBeFound("number.specified=true");

        // Get all the cardsList where number is null
        defaultCardsShouldNotBeFound("number.specified=false");
    }
                @Test
    @Transactional
    public void getAllCardsByNumberContainsSomething() throws Exception {
        // Initialize the database
        cardsRepository.saveAndFlush(cards);

        // Get all the cardsList where number contains DEFAULT_NUMBER
        defaultCardsShouldBeFound("number.contains=" + DEFAULT_NUMBER);

        // Get all the cardsList where number contains UPDATED_NUMBER
        defaultCardsShouldNotBeFound("number.contains=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCardsByNumberNotContainsSomething() throws Exception {
        // Initialize the database
        cardsRepository.saveAndFlush(cards);

        // Get all the cardsList where number does not contain DEFAULT_NUMBER
        defaultCardsShouldNotBeFound("number.doesNotContain=" + DEFAULT_NUMBER);

        // Get all the cardsList where number does not contain UPDATED_NUMBER
        defaultCardsShouldBeFound("number.doesNotContain=" + UPDATED_NUMBER);
    }


    @Test
    @Transactional
    public void getAllCardsByModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        cardsRepository.saveAndFlush(cards);

        // Get all the cardsList where modifiedDate equals to DEFAULT_MODIFIED_DATE
        defaultCardsShouldBeFound("modifiedDate.equals=" + DEFAULT_MODIFIED_DATE);

        // Get all the cardsList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultCardsShouldNotBeFound("modifiedDate.equals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllCardsByModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardsRepository.saveAndFlush(cards);

        // Get all the cardsList where modifiedDate not equals to DEFAULT_MODIFIED_DATE
        defaultCardsShouldNotBeFound("modifiedDate.notEquals=" + DEFAULT_MODIFIED_DATE);

        // Get all the cardsList where modifiedDate not equals to UPDATED_MODIFIED_DATE
        defaultCardsShouldBeFound("modifiedDate.notEquals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllCardsByModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        cardsRepository.saveAndFlush(cards);

        // Get all the cardsList where modifiedDate in DEFAULT_MODIFIED_DATE or UPDATED_MODIFIED_DATE
        defaultCardsShouldBeFound("modifiedDate.in=" + DEFAULT_MODIFIED_DATE + "," + UPDATED_MODIFIED_DATE);

        // Get all the cardsList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultCardsShouldNotBeFound("modifiedDate.in=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllCardsByModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardsRepository.saveAndFlush(cards);

        // Get all the cardsList where modifiedDate is not null
        defaultCardsShouldBeFound("modifiedDate.specified=true");

        // Get all the cardsList where modifiedDate is null
        defaultCardsShouldNotBeFound("modifiedDate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCardsShouldBeFound(String filter) throws Exception {
        restCardsMockMvc.perform(get("/api/cards?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cards.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restCardsMockMvc.perform(get("/api/cards/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCardsShouldNotBeFound(String filter) throws Exception {
        restCardsMockMvc.perform(get("/api/cards?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCardsMockMvc.perform(get("/api/cards/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCards() throws Exception {
        // Get the cards
        restCardsMockMvc.perform(get("/api/cards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCards() throws Exception {
        // Initialize the database
        cardsRepository.saveAndFlush(cards);

        int databaseSizeBeforeUpdate = cardsRepository.findAll().size();

        // Update the cards
        Cards updatedCards = cardsRepository.findById(cards.getId()).get();
        // Disconnect from session so that the updates on updatedCards are not directly saved in db
        em.detach(updatedCards);
        updatedCards
            .number(UPDATED_NUMBER)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        CardsDTO cardsDTO = cardsMapper.toDto(updatedCards);

        restCardsMockMvc.perform(put("/api/cards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cardsDTO)))
            .andExpect(status().isOk());

        // Validate the Cards in the database
        List<Cards> cardsList = cardsRepository.findAll();
        assertThat(cardsList).hasSize(databaseSizeBeforeUpdate);
        Cards testCards = cardsList.get(cardsList.size() - 1);
        assertThat(testCards.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCards.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCards() throws Exception {
        int databaseSizeBeforeUpdate = cardsRepository.findAll().size();

        // Create the Cards
        CardsDTO cardsDTO = cardsMapper.toDto(cards);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCardsMockMvc.perform(put("/api/cards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cardsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cards in the database
        List<Cards> cardsList = cardsRepository.findAll();
        assertThat(cardsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCards() throws Exception {
        // Initialize the database
        cardsRepository.saveAndFlush(cards);

        int databaseSizeBeforeDelete = cardsRepository.findAll().size();

        // Delete the cards
        restCardsMockMvc.perform(delete("/api/cards/{id}", cards.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cards> cardsList = cardsRepository.findAll();
        assertThat(cardsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

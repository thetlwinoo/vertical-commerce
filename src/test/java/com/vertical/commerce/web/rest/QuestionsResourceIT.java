package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.Questions;
import com.vertical.commerce.domain.Suppliers;
import com.vertical.commerce.domain.People;
import com.vertical.commerce.domain.Products;
import com.vertical.commerce.repository.QuestionsRepository;
import com.vertical.commerce.service.QuestionsService;
import com.vertical.commerce.service.dto.QuestionsDTO;
import com.vertical.commerce.service.mapper.QuestionsMapper;
import com.vertical.commerce.service.dto.QuestionsCriteria;
import com.vertical.commerce.service.QuestionsQueryService;

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
 * Integration tests for the {@link QuestionsResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class QuestionsResourceIT {

    private static final String DEFAULT_CUSTOMER_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_QUESTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CUSTOMER_QUESTION_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CUSTOMER_QUESTION_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SUPPLIER_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_ANSWER = "BBBBBBBBBB";

    private static final Instant DEFAULT_SUPPLIER_ANSWER_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SUPPLIER_ANSWER_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private QuestionsMapper questionsMapper;

    @Autowired
    private QuestionsService questionsService;

    @Autowired
    private QuestionsQueryService questionsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuestionsMockMvc;

    private Questions questions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Questions createEntity(EntityManager em) {
        Questions questions = new Questions()
            .customerQuestion(DEFAULT_CUSTOMER_QUESTION)
            .customerQuestionOn(DEFAULT_CUSTOMER_QUESTION_ON)
            .supplierAnswer(DEFAULT_SUPPLIER_ANSWER)
            .supplierAnswerOn(DEFAULT_SUPPLIER_ANSWER_ON)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return questions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Questions createUpdatedEntity(EntityManager em) {
        Questions questions = new Questions()
            .customerQuestion(UPDATED_CUSTOMER_QUESTION)
            .customerQuestionOn(UPDATED_CUSTOMER_QUESTION_ON)
            .supplierAnswer(UPDATED_SUPPLIER_ANSWER)
            .supplierAnswerOn(UPDATED_SUPPLIER_ANSWER_ON)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return questions;
    }

    @BeforeEach
    public void initTest() {
        questions = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestions() throws Exception {
        int databaseSizeBeforeCreate = questionsRepository.findAll().size();
        // Create the Questions
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);
        restQuestionsMockMvc.perform(post("/api/questions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isCreated());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeCreate + 1);
        Questions testQuestions = questionsList.get(questionsList.size() - 1);
        assertThat(testQuestions.getCustomerQuestion()).isEqualTo(DEFAULT_CUSTOMER_QUESTION);
        assertThat(testQuestions.getCustomerQuestionOn()).isEqualTo(DEFAULT_CUSTOMER_QUESTION_ON);
        assertThat(testQuestions.getSupplierAnswer()).isEqualTo(DEFAULT_SUPPLIER_ANSWER);
        assertThat(testQuestions.getSupplierAnswerOn()).isEqualTo(DEFAULT_SUPPLIER_ANSWER_ON);
        assertThat(testQuestions.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testQuestions.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createQuestionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionsRepository.findAll().size();

        // Create the Questions with an existing ID
        questions.setId(1L);
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionsMockMvc.perform(post("/api/questions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCustomerQuestionOnIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsRepository.findAll().size();
        // set the field null
        questions.setCustomerQuestionOn(null);

        // Create the Questions, which fails.
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);


        restQuestionsMockMvc.perform(post("/api/questions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isBadRequest());

        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsRepository.findAll().size();
        // set the field null
        questions.setValidFrom(null);

        // Create the Questions, which fails.
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);


        restQuestionsMockMvc.perform(post("/api/questions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isBadRequest());

        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList
        restQuestionsMockMvc.perform(get("/api/questions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questions.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerQuestion").value(hasItem(DEFAULT_CUSTOMER_QUESTION.toString())))
            .andExpect(jsonPath("$.[*].customerQuestionOn").value(hasItem(DEFAULT_CUSTOMER_QUESTION_ON.toString())))
            .andExpect(jsonPath("$.[*].supplierAnswer").value(hasItem(DEFAULT_SUPPLIER_ANSWER.toString())))
            .andExpect(jsonPath("$.[*].supplierAnswerOn").value(hasItem(DEFAULT_SUPPLIER_ANSWER_ON.toString())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get the questions
        restQuestionsMockMvc.perform(get("/api/questions/{id}", questions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(questions.getId().intValue()))
            .andExpect(jsonPath("$.customerQuestion").value(DEFAULT_CUSTOMER_QUESTION.toString()))
            .andExpect(jsonPath("$.customerQuestionOn").value(DEFAULT_CUSTOMER_QUESTION_ON.toString()))
            .andExpect(jsonPath("$.supplierAnswer").value(DEFAULT_SUPPLIER_ANSWER.toString()))
            .andExpect(jsonPath("$.supplierAnswerOn").value(DEFAULT_SUPPLIER_ANSWER_ON.toString()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getQuestionsByIdFiltering() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        Long id = questions.getId();

        defaultQuestionsShouldBeFound("id.equals=" + id);
        defaultQuestionsShouldNotBeFound("id.notEquals=" + id);

        defaultQuestionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQuestionsShouldNotBeFound("id.greaterThan=" + id);

        defaultQuestionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQuestionsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllQuestionsByCustomerQuestionOnIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where customerQuestionOn equals to DEFAULT_CUSTOMER_QUESTION_ON
        defaultQuestionsShouldBeFound("customerQuestionOn.equals=" + DEFAULT_CUSTOMER_QUESTION_ON);

        // Get all the questionsList where customerQuestionOn equals to UPDATED_CUSTOMER_QUESTION_ON
        defaultQuestionsShouldNotBeFound("customerQuestionOn.equals=" + UPDATED_CUSTOMER_QUESTION_ON);
    }

    @Test
    @Transactional
    public void getAllQuestionsByCustomerQuestionOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where customerQuestionOn not equals to DEFAULT_CUSTOMER_QUESTION_ON
        defaultQuestionsShouldNotBeFound("customerQuestionOn.notEquals=" + DEFAULT_CUSTOMER_QUESTION_ON);

        // Get all the questionsList where customerQuestionOn not equals to UPDATED_CUSTOMER_QUESTION_ON
        defaultQuestionsShouldBeFound("customerQuestionOn.notEquals=" + UPDATED_CUSTOMER_QUESTION_ON);
    }

    @Test
    @Transactional
    public void getAllQuestionsByCustomerQuestionOnIsInShouldWork() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where customerQuestionOn in DEFAULT_CUSTOMER_QUESTION_ON or UPDATED_CUSTOMER_QUESTION_ON
        defaultQuestionsShouldBeFound("customerQuestionOn.in=" + DEFAULT_CUSTOMER_QUESTION_ON + "," + UPDATED_CUSTOMER_QUESTION_ON);

        // Get all the questionsList where customerQuestionOn equals to UPDATED_CUSTOMER_QUESTION_ON
        defaultQuestionsShouldNotBeFound("customerQuestionOn.in=" + UPDATED_CUSTOMER_QUESTION_ON);
    }

    @Test
    @Transactional
    public void getAllQuestionsByCustomerQuestionOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where customerQuestionOn is not null
        defaultQuestionsShouldBeFound("customerQuestionOn.specified=true");

        // Get all the questionsList where customerQuestionOn is null
        defaultQuestionsShouldNotBeFound("customerQuestionOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuestionsBySupplierAnswerOnIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where supplierAnswerOn equals to DEFAULT_SUPPLIER_ANSWER_ON
        defaultQuestionsShouldBeFound("supplierAnswerOn.equals=" + DEFAULT_SUPPLIER_ANSWER_ON);

        // Get all the questionsList where supplierAnswerOn equals to UPDATED_SUPPLIER_ANSWER_ON
        defaultQuestionsShouldNotBeFound("supplierAnswerOn.equals=" + UPDATED_SUPPLIER_ANSWER_ON);
    }

    @Test
    @Transactional
    public void getAllQuestionsBySupplierAnswerOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where supplierAnswerOn not equals to DEFAULT_SUPPLIER_ANSWER_ON
        defaultQuestionsShouldNotBeFound("supplierAnswerOn.notEquals=" + DEFAULT_SUPPLIER_ANSWER_ON);

        // Get all the questionsList where supplierAnswerOn not equals to UPDATED_SUPPLIER_ANSWER_ON
        defaultQuestionsShouldBeFound("supplierAnswerOn.notEquals=" + UPDATED_SUPPLIER_ANSWER_ON);
    }

    @Test
    @Transactional
    public void getAllQuestionsBySupplierAnswerOnIsInShouldWork() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where supplierAnswerOn in DEFAULT_SUPPLIER_ANSWER_ON or UPDATED_SUPPLIER_ANSWER_ON
        defaultQuestionsShouldBeFound("supplierAnswerOn.in=" + DEFAULT_SUPPLIER_ANSWER_ON + "," + UPDATED_SUPPLIER_ANSWER_ON);

        // Get all the questionsList where supplierAnswerOn equals to UPDATED_SUPPLIER_ANSWER_ON
        defaultQuestionsShouldNotBeFound("supplierAnswerOn.in=" + UPDATED_SUPPLIER_ANSWER_ON);
    }

    @Test
    @Transactional
    public void getAllQuestionsBySupplierAnswerOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where supplierAnswerOn is not null
        defaultQuestionsShouldBeFound("supplierAnswerOn.specified=true");

        // Get all the questionsList where supplierAnswerOn is null
        defaultQuestionsShouldNotBeFound("supplierAnswerOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuestionsByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where validFrom equals to DEFAULT_VALID_FROM
        defaultQuestionsShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the questionsList where validFrom equals to UPDATED_VALID_FROM
        defaultQuestionsShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllQuestionsByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where validFrom not equals to DEFAULT_VALID_FROM
        defaultQuestionsShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the questionsList where validFrom not equals to UPDATED_VALID_FROM
        defaultQuestionsShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllQuestionsByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultQuestionsShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the questionsList where validFrom equals to UPDATED_VALID_FROM
        defaultQuestionsShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllQuestionsByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where validFrom is not null
        defaultQuestionsShouldBeFound("validFrom.specified=true");

        // Get all the questionsList where validFrom is null
        defaultQuestionsShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuestionsByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where validTo equals to DEFAULT_VALID_TO
        defaultQuestionsShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the questionsList where validTo equals to UPDATED_VALID_TO
        defaultQuestionsShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllQuestionsByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where validTo not equals to DEFAULT_VALID_TO
        defaultQuestionsShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the questionsList where validTo not equals to UPDATED_VALID_TO
        defaultQuestionsShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllQuestionsByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultQuestionsShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the questionsList where validTo equals to UPDATED_VALID_TO
        defaultQuestionsShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllQuestionsByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList where validTo is not null
        defaultQuestionsShouldBeFound("validTo.specified=true");

        // Get all the questionsList where validTo is null
        defaultQuestionsShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuestionsBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);
        Suppliers supplier = SuppliersResourceIT.createEntity(em);
        em.persist(supplier);
        em.flush();
        questions.setSupplier(supplier);
        questionsRepository.saveAndFlush(questions);
        Long supplierId = supplier.getId();

        // Get all the questionsList where supplier equals to supplierId
        defaultQuestionsShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the questionsList where supplier equals to supplierId + 1
        defaultQuestionsShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }


    @Test
    @Transactional
    public void getAllQuestionsByPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);
        People person = PeopleResourceIT.createEntity(em);
        em.persist(person);
        em.flush();
        questions.setPerson(person);
        questionsRepository.saveAndFlush(questions);
        Long personId = person.getId();

        // Get all the questionsList where person equals to personId
        defaultQuestionsShouldBeFound("personId.equals=" + personId);

        // Get all the questionsList where person equals to personId + 1
        defaultQuestionsShouldNotBeFound("personId.equals=" + (personId + 1));
    }


    @Test
    @Transactional
    public void getAllQuestionsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);
        Products product = ProductsResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        questions.setProduct(product);
        questionsRepository.saveAndFlush(questions);
        Long productId = product.getId();

        // Get all the questionsList where product equals to productId
        defaultQuestionsShouldBeFound("productId.equals=" + productId);

        // Get all the questionsList where product equals to productId + 1
        defaultQuestionsShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuestionsShouldBeFound(String filter) throws Exception {
        restQuestionsMockMvc.perform(get("/api/questions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questions.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerQuestion").value(hasItem(DEFAULT_CUSTOMER_QUESTION.toString())))
            .andExpect(jsonPath("$.[*].customerQuestionOn").value(hasItem(DEFAULT_CUSTOMER_QUESTION_ON.toString())))
            .andExpect(jsonPath("$.[*].supplierAnswer").value(hasItem(DEFAULT_SUPPLIER_ANSWER.toString())))
            .andExpect(jsonPath("$.[*].supplierAnswerOn").value(hasItem(DEFAULT_SUPPLIER_ANSWER_ON.toString())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restQuestionsMockMvc.perform(get("/api/questions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuestionsShouldNotBeFound(String filter) throws Exception {
        restQuestionsMockMvc.perform(get("/api/questions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuestionsMockMvc.perform(get("/api/questions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingQuestions() throws Exception {
        // Get the questions
        restQuestionsMockMvc.perform(get("/api/questions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();

        // Update the questions
        Questions updatedQuestions = questionsRepository.findById(questions.getId()).get();
        // Disconnect from session so that the updates on updatedQuestions are not directly saved in db
        em.detach(updatedQuestions);
        updatedQuestions
            .customerQuestion(UPDATED_CUSTOMER_QUESTION)
            .customerQuestionOn(UPDATED_CUSTOMER_QUESTION_ON)
            .supplierAnswer(UPDATED_SUPPLIER_ANSWER)
            .supplierAnswerOn(UPDATED_SUPPLIER_ANSWER_ON)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        QuestionsDTO questionsDTO = questionsMapper.toDto(updatedQuestions);

        restQuestionsMockMvc.perform(put("/api/questions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isOk());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
        Questions testQuestions = questionsList.get(questionsList.size() - 1);
        assertThat(testQuestions.getCustomerQuestion()).isEqualTo(UPDATED_CUSTOMER_QUESTION);
        assertThat(testQuestions.getCustomerQuestionOn()).isEqualTo(UPDATED_CUSTOMER_QUESTION_ON);
        assertThat(testQuestions.getSupplierAnswer()).isEqualTo(UPDATED_SUPPLIER_ANSWER);
        assertThat(testQuestions.getSupplierAnswerOn()).isEqualTo(UPDATED_SUPPLIER_ANSWER_ON);
        assertThat(testQuestions.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testQuestions.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestions() throws Exception {
        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();

        // Create the Questions
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionsMockMvc.perform(put("/api/questions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        int databaseSizeBeforeDelete = questionsRepository.findAll().size();

        // Delete the questions
        restQuestionsMockMvc.perform(delete("/api/questions/{id}", questions.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

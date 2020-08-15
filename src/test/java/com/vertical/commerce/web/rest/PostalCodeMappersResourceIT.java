package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.PostalCodeMappers;
import com.vertical.commerce.domain.Townships;
import com.vertical.commerce.repository.PostalCodeMappersRepository;
import com.vertical.commerce.service.PostalCodeMappersService;
import com.vertical.commerce.service.dto.PostalCodeMappersDTO;
import com.vertical.commerce.service.mapper.PostalCodeMappersMapper;
import com.vertical.commerce.service.dto.PostalCodeMappersCriteria;
import com.vertical.commerce.service.PostalCodeMappersQueryService;

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
 * Integration tests for the {@link PostalCodeMappersResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PostalCodeMappersResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALIZATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIZATION = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PostalCodeMappersRepository postalCodeMappersRepository;

    @Autowired
    private PostalCodeMappersMapper postalCodeMappersMapper;

    @Autowired
    private PostalCodeMappersService postalCodeMappersService;

    @Autowired
    private PostalCodeMappersQueryService postalCodeMappersQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostalCodeMappersMockMvc;

    private PostalCodeMappers postalCodeMappers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostalCodeMappers createEntity(EntityManager em) {
        PostalCodeMappers postalCodeMappers = new PostalCodeMappers()
            .name(DEFAULT_NAME)
            .postalCode(DEFAULT_POSTAL_CODE)
            .description(DEFAULT_DESCRIPTION)
            .localization(DEFAULT_LOCALIZATION)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return postalCodeMappers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostalCodeMappers createUpdatedEntity(EntityManager em) {
        PostalCodeMappers postalCodeMappers = new PostalCodeMappers()
            .name(UPDATED_NAME)
            .postalCode(UPDATED_POSTAL_CODE)
            .description(UPDATED_DESCRIPTION)
            .localization(UPDATED_LOCALIZATION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return postalCodeMappers;
    }

    @BeforeEach
    public void initTest() {
        postalCodeMappers = createEntity(em);
    }

    @Test
    @Transactional
    public void createPostalCodeMappers() throws Exception {
        int databaseSizeBeforeCreate = postalCodeMappersRepository.findAll().size();
        // Create the PostalCodeMappers
        PostalCodeMappersDTO postalCodeMappersDTO = postalCodeMappersMapper.toDto(postalCodeMappers);
        restPostalCodeMappersMockMvc.perform(post("/api/postal-code-mappers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postalCodeMappersDTO)))
            .andExpect(status().isCreated());

        // Validate the PostalCodeMappers in the database
        List<PostalCodeMappers> postalCodeMappersList = postalCodeMappersRepository.findAll();
        assertThat(postalCodeMappersList).hasSize(databaseSizeBeforeCreate + 1);
        PostalCodeMappers testPostalCodeMappers = postalCodeMappersList.get(postalCodeMappersList.size() - 1);
        assertThat(testPostalCodeMappers.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPostalCodeMappers.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testPostalCodeMappers.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPostalCodeMappers.getLocalization()).isEqualTo(DEFAULT_LOCALIZATION);
        assertThat(testPostalCodeMappers.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testPostalCodeMappers.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createPostalCodeMappersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postalCodeMappersRepository.findAll().size();

        // Create the PostalCodeMappers with an existing ID
        postalCodeMappers.setId(1L);
        PostalCodeMappersDTO postalCodeMappersDTO = postalCodeMappersMapper.toDto(postalCodeMappers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostalCodeMappersMockMvc.perform(post("/api/postal-code-mappers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postalCodeMappersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostalCodeMappers in the database
        List<PostalCodeMappers> postalCodeMappersList = postalCodeMappersRepository.findAll();
        assertThat(postalCodeMappersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = postalCodeMappersRepository.findAll().size();
        // set the field null
        postalCodeMappers.setName(null);

        // Create the PostalCodeMappers, which fails.
        PostalCodeMappersDTO postalCodeMappersDTO = postalCodeMappersMapper.toDto(postalCodeMappers);


        restPostalCodeMappersMockMvc.perform(post("/api/postal-code-mappers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postalCodeMappersDTO)))
            .andExpect(status().isBadRequest());

        List<PostalCodeMappers> postalCodeMappersList = postalCodeMappersRepository.findAll();
        assertThat(postalCodeMappersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = postalCodeMappersRepository.findAll().size();
        // set the field null
        postalCodeMappers.setValidFrom(null);

        // Create the PostalCodeMappers, which fails.
        PostalCodeMappersDTO postalCodeMappersDTO = postalCodeMappersMapper.toDto(postalCodeMappers);


        restPostalCodeMappersMockMvc.perform(post("/api/postal-code-mappers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postalCodeMappersDTO)))
            .andExpect(status().isBadRequest());

        List<PostalCodeMappers> postalCodeMappersList = postalCodeMappersRepository.findAll();
        assertThat(postalCodeMappersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappers() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList
        restPostalCodeMappersMockMvc.perform(get("/api/postal-code-mappers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postalCodeMappers.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].localization").value(hasItem(DEFAULT_LOCALIZATION.toString())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getPostalCodeMappers() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get the postalCodeMappers
        restPostalCodeMappersMockMvc.perform(get("/api/postal-code-mappers/{id}", postalCodeMappers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postalCodeMappers.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.localization").value(DEFAULT_LOCALIZATION.toString()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getPostalCodeMappersByIdFiltering() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        Long id = postalCodeMappers.getId();

        defaultPostalCodeMappersShouldBeFound("id.equals=" + id);
        defaultPostalCodeMappersShouldNotBeFound("id.notEquals=" + id);

        defaultPostalCodeMappersShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPostalCodeMappersShouldNotBeFound("id.greaterThan=" + id);

        defaultPostalCodeMappersShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPostalCodeMappersShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPostalCodeMappersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where name equals to DEFAULT_NAME
        defaultPostalCodeMappersShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the postalCodeMappersList where name equals to UPDATED_NAME
        defaultPostalCodeMappersShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where name not equals to DEFAULT_NAME
        defaultPostalCodeMappersShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the postalCodeMappersList where name not equals to UPDATED_NAME
        defaultPostalCodeMappersShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPostalCodeMappersShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the postalCodeMappersList where name equals to UPDATED_NAME
        defaultPostalCodeMappersShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where name is not null
        defaultPostalCodeMappersShouldBeFound("name.specified=true");

        // Get all the postalCodeMappersList where name is null
        defaultPostalCodeMappersShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostalCodeMappersByNameContainsSomething() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where name contains DEFAULT_NAME
        defaultPostalCodeMappersShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the postalCodeMappersList where name contains UPDATED_NAME
        defaultPostalCodeMappersShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where name does not contain DEFAULT_NAME
        defaultPostalCodeMappersShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the postalCodeMappersList where name does not contain UPDATED_NAME
        defaultPostalCodeMappersShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPostalCodeMappersByPostalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where postalCode equals to DEFAULT_POSTAL_CODE
        defaultPostalCodeMappersShouldBeFound("postalCode.equals=" + DEFAULT_POSTAL_CODE);

        // Get all the postalCodeMappersList where postalCode equals to UPDATED_POSTAL_CODE
        defaultPostalCodeMappersShouldNotBeFound("postalCode.equals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersByPostalCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where postalCode not equals to DEFAULT_POSTAL_CODE
        defaultPostalCodeMappersShouldNotBeFound("postalCode.notEquals=" + DEFAULT_POSTAL_CODE);

        // Get all the postalCodeMappersList where postalCode not equals to UPDATED_POSTAL_CODE
        defaultPostalCodeMappersShouldBeFound("postalCode.notEquals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersByPostalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where postalCode in DEFAULT_POSTAL_CODE or UPDATED_POSTAL_CODE
        defaultPostalCodeMappersShouldBeFound("postalCode.in=" + DEFAULT_POSTAL_CODE + "," + UPDATED_POSTAL_CODE);

        // Get all the postalCodeMappersList where postalCode equals to UPDATED_POSTAL_CODE
        defaultPostalCodeMappersShouldNotBeFound("postalCode.in=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersByPostalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where postalCode is not null
        defaultPostalCodeMappersShouldBeFound("postalCode.specified=true");

        // Get all the postalCodeMappersList where postalCode is null
        defaultPostalCodeMappersShouldNotBeFound("postalCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostalCodeMappersByPostalCodeContainsSomething() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where postalCode contains DEFAULT_POSTAL_CODE
        defaultPostalCodeMappersShouldBeFound("postalCode.contains=" + DEFAULT_POSTAL_CODE);

        // Get all the postalCodeMappersList where postalCode contains UPDATED_POSTAL_CODE
        defaultPostalCodeMappersShouldNotBeFound("postalCode.contains=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersByPostalCodeNotContainsSomething() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where postalCode does not contain DEFAULT_POSTAL_CODE
        defaultPostalCodeMappersShouldNotBeFound("postalCode.doesNotContain=" + DEFAULT_POSTAL_CODE);

        // Get all the postalCodeMappersList where postalCode does not contain UPDATED_POSTAL_CODE
        defaultPostalCodeMappersShouldBeFound("postalCode.doesNotContain=" + UPDATED_POSTAL_CODE);
    }


    @Test
    @Transactional
    public void getAllPostalCodeMappersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where description equals to DEFAULT_DESCRIPTION
        defaultPostalCodeMappersShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the postalCodeMappersList where description equals to UPDATED_DESCRIPTION
        defaultPostalCodeMappersShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where description not equals to DEFAULT_DESCRIPTION
        defaultPostalCodeMappersShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the postalCodeMappersList where description not equals to UPDATED_DESCRIPTION
        defaultPostalCodeMappersShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultPostalCodeMappersShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the postalCodeMappersList where description equals to UPDATED_DESCRIPTION
        defaultPostalCodeMappersShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where description is not null
        defaultPostalCodeMappersShouldBeFound("description.specified=true");

        // Get all the postalCodeMappersList where description is null
        defaultPostalCodeMappersShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostalCodeMappersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where description contains DEFAULT_DESCRIPTION
        defaultPostalCodeMappersShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the postalCodeMappersList where description contains UPDATED_DESCRIPTION
        defaultPostalCodeMappersShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where description does not contain DEFAULT_DESCRIPTION
        defaultPostalCodeMappersShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the postalCodeMappersList where description does not contain UPDATED_DESCRIPTION
        defaultPostalCodeMappersShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllPostalCodeMappersByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where validFrom equals to DEFAULT_VALID_FROM
        defaultPostalCodeMappersShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the postalCodeMappersList where validFrom equals to UPDATED_VALID_FROM
        defaultPostalCodeMappersShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where validFrom not equals to DEFAULT_VALID_FROM
        defaultPostalCodeMappersShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the postalCodeMappersList where validFrom not equals to UPDATED_VALID_FROM
        defaultPostalCodeMappersShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultPostalCodeMappersShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the postalCodeMappersList where validFrom equals to UPDATED_VALID_FROM
        defaultPostalCodeMappersShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where validFrom is not null
        defaultPostalCodeMappersShouldBeFound("validFrom.specified=true");

        // Get all the postalCodeMappersList where validFrom is null
        defaultPostalCodeMappersShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where validTo equals to DEFAULT_VALID_TO
        defaultPostalCodeMappersShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the postalCodeMappersList where validTo equals to UPDATED_VALID_TO
        defaultPostalCodeMappersShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where validTo not equals to DEFAULT_VALID_TO
        defaultPostalCodeMappersShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the postalCodeMappersList where validTo not equals to UPDATED_VALID_TO
        defaultPostalCodeMappersShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultPostalCodeMappersShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the postalCodeMappersList where validTo equals to UPDATED_VALID_TO
        defaultPostalCodeMappersShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        // Get all the postalCodeMappersList where validTo is not null
        defaultPostalCodeMappersShouldBeFound("validTo.specified=true");

        // Get all the postalCodeMappersList where validTo is null
        defaultPostalCodeMappersShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersByTownshipIsEqualToSomething() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);
        Townships township = TownshipsResourceIT.createEntity(em);
        em.persist(township);
        em.flush();
        postalCodeMappers.setTownship(township);
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);
        Long townshipId = township.getId();

        // Get all the postalCodeMappersList where township equals to townshipId
        defaultPostalCodeMappersShouldBeFound("townshipId.equals=" + townshipId);

        // Get all the postalCodeMappersList where township equals to townshipId + 1
        defaultPostalCodeMappersShouldNotBeFound("townshipId.equals=" + (townshipId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPostalCodeMappersShouldBeFound(String filter) throws Exception {
        restPostalCodeMappersMockMvc.perform(get("/api/postal-code-mappers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postalCodeMappers.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].localization").value(hasItem(DEFAULT_LOCALIZATION.toString())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restPostalCodeMappersMockMvc.perform(get("/api/postal-code-mappers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPostalCodeMappersShouldNotBeFound(String filter) throws Exception {
        restPostalCodeMappersMockMvc.perform(get("/api/postal-code-mappers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPostalCodeMappersMockMvc.perform(get("/api/postal-code-mappers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPostalCodeMappers() throws Exception {
        // Get the postalCodeMappers
        restPostalCodeMappersMockMvc.perform(get("/api/postal-code-mappers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePostalCodeMappers() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        int databaseSizeBeforeUpdate = postalCodeMappersRepository.findAll().size();

        // Update the postalCodeMappers
        PostalCodeMappers updatedPostalCodeMappers = postalCodeMappersRepository.findById(postalCodeMappers.getId()).get();
        // Disconnect from session so that the updates on updatedPostalCodeMappers are not directly saved in db
        em.detach(updatedPostalCodeMappers);
        updatedPostalCodeMappers
            .name(UPDATED_NAME)
            .postalCode(UPDATED_POSTAL_CODE)
            .description(UPDATED_DESCRIPTION)
            .localization(UPDATED_LOCALIZATION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        PostalCodeMappersDTO postalCodeMappersDTO = postalCodeMappersMapper.toDto(updatedPostalCodeMappers);

        restPostalCodeMappersMockMvc.perform(put("/api/postal-code-mappers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postalCodeMappersDTO)))
            .andExpect(status().isOk());

        // Validate the PostalCodeMappers in the database
        List<PostalCodeMappers> postalCodeMappersList = postalCodeMappersRepository.findAll();
        assertThat(postalCodeMappersList).hasSize(databaseSizeBeforeUpdate);
        PostalCodeMappers testPostalCodeMappers = postalCodeMappersList.get(postalCodeMappersList.size() - 1);
        assertThat(testPostalCodeMappers.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPostalCodeMappers.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testPostalCodeMappers.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPostalCodeMappers.getLocalization()).isEqualTo(UPDATED_LOCALIZATION);
        assertThat(testPostalCodeMappers.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testPostalCodeMappers.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingPostalCodeMappers() throws Exception {
        int databaseSizeBeforeUpdate = postalCodeMappersRepository.findAll().size();

        // Create the PostalCodeMappers
        PostalCodeMappersDTO postalCodeMappersDTO = postalCodeMappersMapper.toDto(postalCodeMappers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostalCodeMappersMockMvc.perform(put("/api/postal-code-mappers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postalCodeMappersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostalCodeMappers in the database
        List<PostalCodeMappers> postalCodeMappersList = postalCodeMappersRepository.findAll();
        assertThat(postalCodeMappersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePostalCodeMappers() throws Exception {
        // Initialize the database
        postalCodeMappersRepository.saveAndFlush(postalCodeMappers);

        int databaseSizeBeforeDelete = postalCodeMappersRepository.findAll().size();

        // Delete the postalCodeMappers
        restPostalCodeMappersMockMvc.perform(delete("/api/postal-code-mappers/{id}", postalCodeMappers.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostalCodeMappers> postalCodeMappersList = postalCodeMappersRepository.findAll();
        assertThat(postalCodeMappersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

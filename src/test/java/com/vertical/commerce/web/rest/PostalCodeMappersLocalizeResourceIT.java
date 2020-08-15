package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.PostalCodeMappersLocalize;
import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.PostalCodeMappers;
import com.vertical.commerce.repository.PostalCodeMappersLocalizeRepository;
import com.vertical.commerce.service.PostalCodeMappersLocalizeService;
import com.vertical.commerce.service.dto.PostalCodeMappersLocalizeDTO;
import com.vertical.commerce.service.mapper.PostalCodeMappersLocalizeMapper;
import com.vertical.commerce.service.dto.PostalCodeMappersLocalizeCriteria;
import com.vertical.commerce.service.PostalCodeMappersLocalizeQueryService;

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
 * Integration tests for the {@link PostalCodeMappersLocalizeResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PostalCodeMappersLocalizeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PostalCodeMappersLocalizeRepository postalCodeMappersLocalizeRepository;

    @Autowired
    private PostalCodeMappersLocalizeMapper postalCodeMappersLocalizeMapper;

    @Autowired
    private PostalCodeMappersLocalizeService postalCodeMappersLocalizeService;

    @Autowired
    private PostalCodeMappersLocalizeQueryService postalCodeMappersLocalizeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostalCodeMappersLocalizeMockMvc;

    private PostalCodeMappersLocalize postalCodeMappersLocalize;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostalCodeMappersLocalize createEntity(EntityManager em) {
        PostalCodeMappersLocalize postalCodeMappersLocalize = new PostalCodeMappersLocalize()
            .name(DEFAULT_NAME);
        return postalCodeMappersLocalize;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostalCodeMappersLocalize createUpdatedEntity(EntityManager em) {
        PostalCodeMappersLocalize postalCodeMappersLocalize = new PostalCodeMappersLocalize()
            .name(UPDATED_NAME);
        return postalCodeMappersLocalize;
    }

    @BeforeEach
    public void initTest() {
        postalCodeMappersLocalize = createEntity(em);
    }

    @Test
    @Transactional
    public void createPostalCodeMappersLocalize() throws Exception {
        int databaseSizeBeforeCreate = postalCodeMappersLocalizeRepository.findAll().size();
        // Create the PostalCodeMappersLocalize
        PostalCodeMappersLocalizeDTO postalCodeMappersLocalizeDTO = postalCodeMappersLocalizeMapper.toDto(postalCodeMappersLocalize);
        restPostalCodeMappersLocalizeMockMvc.perform(post("/api/postal-code-mappers-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postalCodeMappersLocalizeDTO)))
            .andExpect(status().isCreated());

        // Validate the PostalCodeMappersLocalize in the database
        List<PostalCodeMappersLocalize> postalCodeMappersLocalizeList = postalCodeMappersLocalizeRepository.findAll();
        assertThat(postalCodeMappersLocalizeList).hasSize(databaseSizeBeforeCreate + 1);
        PostalCodeMappersLocalize testPostalCodeMappersLocalize = postalCodeMappersLocalizeList.get(postalCodeMappersLocalizeList.size() - 1);
        assertThat(testPostalCodeMappersLocalize.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPostalCodeMappersLocalizeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postalCodeMappersLocalizeRepository.findAll().size();

        // Create the PostalCodeMappersLocalize with an existing ID
        postalCodeMappersLocalize.setId(1L);
        PostalCodeMappersLocalizeDTO postalCodeMappersLocalizeDTO = postalCodeMappersLocalizeMapper.toDto(postalCodeMappersLocalize);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostalCodeMappersLocalizeMockMvc.perform(post("/api/postal-code-mappers-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postalCodeMappersLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostalCodeMappersLocalize in the database
        List<PostalCodeMappersLocalize> postalCodeMappersLocalizeList = postalCodeMappersLocalizeRepository.findAll();
        assertThat(postalCodeMappersLocalizeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = postalCodeMappersLocalizeRepository.findAll().size();
        // set the field null
        postalCodeMappersLocalize.setName(null);

        // Create the PostalCodeMappersLocalize, which fails.
        PostalCodeMappersLocalizeDTO postalCodeMappersLocalizeDTO = postalCodeMappersLocalizeMapper.toDto(postalCodeMappersLocalize);


        restPostalCodeMappersLocalizeMockMvc.perform(post("/api/postal-code-mappers-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postalCodeMappersLocalizeDTO)))
            .andExpect(status().isBadRequest());

        List<PostalCodeMappersLocalize> postalCodeMappersLocalizeList = postalCodeMappersLocalizeRepository.findAll();
        assertThat(postalCodeMappersLocalizeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersLocalizes() throws Exception {
        // Initialize the database
        postalCodeMappersLocalizeRepository.saveAndFlush(postalCodeMappersLocalize);

        // Get all the postalCodeMappersLocalizeList
        restPostalCodeMappersLocalizeMockMvc.perform(get("/api/postal-code-mappers-localizes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postalCodeMappersLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getPostalCodeMappersLocalize() throws Exception {
        // Initialize the database
        postalCodeMappersLocalizeRepository.saveAndFlush(postalCodeMappersLocalize);

        // Get the postalCodeMappersLocalize
        restPostalCodeMappersLocalizeMockMvc.perform(get("/api/postal-code-mappers-localizes/{id}", postalCodeMappersLocalize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postalCodeMappersLocalize.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getPostalCodeMappersLocalizesByIdFiltering() throws Exception {
        // Initialize the database
        postalCodeMappersLocalizeRepository.saveAndFlush(postalCodeMappersLocalize);

        Long id = postalCodeMappersLocalize.getId();

        defaultPostalCodeMappersLocalizeShouldBeFound("id.equals=" + id);
        defaultPostalCodeMappersLocalizeShouldNotBeFound("id.notEquals=" + id);

        defaultPostalCodeMappersLocalizeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPostalCodeMappersLocalizeShouldNotBeFound("id.greaterThan=" + id);

        defaultPostalCodeMappersLocalizeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPostalCodeMappersLocalizeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPostalCodeMappersLocalizesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        postalCodeMappersLocalizeRepository.saveAndFlush(postalCodeMappersLocalize);

        // Get all the postalCodeMappersLocalizeList where name equals to DEFAULT_NAME
        defaultPostalCodeMappersLocalizeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the postalCodeMappersLocalizeList where name equals to UPDATED_NAME
        defaultPostalCodeMappersLocalizeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersLocalizesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postalCodeMappersLocalizeRepository.saveAndFlush(postalCodeMappersLocalize);

        // Get all the postalCodeMappersLocalizeList where name not equals to DEFAULT_NAME
        defaultPostalCodeMappersLocalizeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the postalCodeMappersLocalizeList where name not equals to UPDATED_NAME
        defaultPostalCodeMappersLocalizeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersLocalizesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        postalCodeMappersLocalizeRepository.saveAndFlush(postalCodeMappersLocalize);

        // Get all the postalCodeMappersLocalizeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPostalCodeMappersLocalizeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the postalCodeMappersLocalizeList where name equals to UPDATED_NAME
        defaultPostalCodeMappersLocalizeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersLocalizesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        postalCodeMappersLocalizeRepository.saveAndFlush(postalCodeMappersLocalize);

        // Get all the postalCodeMappersLocalizeList where name is not null
        defaultPostalCodeMappersLocalizeShouldBeFound("name.specified=true");

        // Get all the postalCodeMappersLocalizeList where name is null
        defaultPostalCodeMappersLocalizeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostalCodeMappersLocalizesByNameContainsSomething() throws Exception {
        // Initialize the database
        postalCodeMappersLocalizeRepository.saveAndFlush(postalCodeMappersLocalize);

        // Get all the postalCodeMappersLocalizeList where name contains DEFAULT_NAME
        defaultPostalCodeMappersLocalizeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the postalCodeMappersLocalizeList where name contains UPDATED_NAME
        defaultPostalCodeMappersLocalizeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPostalCodeMappersLocalizesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        postalCodeMappersLocalizeRepository.saveAndFlush(postalCodeMappersLocalize);

        // Get all the postalCodeMappersLocalizeList where name does not contain DEFAULT_NAME
        defaultPostalCodeMappersLocalizeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the postalCodeMappersLocalizeList where name does not contain UPDATED_NAME
        defaultPostalCodeMappersLocalizeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPostalCodeMappersLocalizesByCultureIsEqualToSomething() throws Exception {
        // Initialize the database
        postalCodeMappersLocalizeRepository.saveAndFlush(postalCodeMappersLocalize);
        Culture culture = CultureResourceIT.createEntity(em);
        em.persist(culture);
        em.flush();
        postalCodeMappersLocalize.setCulture(culture);
        postalCodeMappersLocalizeRepository.saveAndFlush(postalCodeMappersLocalize);
        Long cultureId = culture.getId();

        // Get all the postalCodeMappersLocalizeList where culture equals to cultureId
        defaultPostalCodeMappersLocalizeShouldBeFound("cultureId.equals=" + cultureId);

        // Get all the postalCodeMappersLocalizeList where culture equals to cultureId + 1
        defaultPostalCodeMappersLocalizeShouldNotBeFound("cultureId.equals=" + (cultureId + 1));
    }


    @Test
    @Transactional
    public void getAllPostalCodeMappersLocalizesByPostalCodeMapperIsEqualToSomething() throws Exception {
        // Initialize the database
        postalCodeMappersLocalizeRepository.saveAndFlush(postalCodeMappersLocalize);
        PostalCodeMappers postalCodeMapper = PostalCodeMappersResourceIT.createEntity(em);
        em.persist(postalCodeMapper);
        em.flush();
        postalCodeMappersLocalize.setPostalCodeMapper(postalCodeMapper);
        postalCodeMappersLocalizeRepository.saveAndFlush(postalCodeMappersLocalize);
        Long postalCodeMapperId = postalCodeMapper.getId();

        // Get all the postalCodeMappersLocalizeList where postalCodeMapper equals to postalCodeMapperId
        defaultPostalCodeMappersLocalizeShouldBeFound("postalCodeMapperId.equals=" + postalCodeMapperId);

        // Get all the postalCodeMappersLocalizeList where postalCodeMapper equals to postalCodeMapperId + 1
        defaultPostalCodeMappersLocalizeShouldNotBeFound("postalCodeMapperId.equals=" + (postalCodeMapperId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPostalCodeMappersLocalizeShouldBeFound(String filter) throws Exception {
        restPostalCodeMappersLocalizeMockMvc.perform(get("/api/postal-code-mappers-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postalCodeMappersLocalize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restPostalCodeMappersLocalizeMockMvc.perform(get("/api/postal-code-mappers-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPostalCodeMappersLocalizeShouldNotBeFound(String filter) throws Exception {
        restPostalCodeMappersLocalizeMockMvc.perform(get("/api/postal-code-mappers-localizes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPostalCodeMappersLocalizeMockMvc.perform(get("/api/postal-code-mappers-localizes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPostalCodeMappersLocalize() throws Exception {
        // Get the postalCodeMappersLocalize
        restPostalCodeMappersLocalizeMockMvc.perform(get("/api/postal-code-mappers-localizes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePostalCodeMappersLocalize() throws Exception {
        // Initialize the database
        postalCodeMappersLocalizeRepository.saveAndFlush(postalCodeMappersLocalize);

        int databaseSizeBeforeUpdate = postalCodeMappersLocalizeRepository.findAll().size();

        // Update the postalCodeMappersLocalize
        PostalCodeMappersLocalize updatedPostalCodeMappersLocalize = postalCodeMappersLocalizeRepository.findById(postalCodeMappersLocalize.getId()).get();
        // Disconnect from session so that the updates on updatedPostalCodeMappersLocalize are not directly saved in db
        em.detach(updatedPostalCodeMappersLocalize);
        updatedPostalCodeMappersLocalize
            .name(UPDATED_NAME);
        PostalCodeMappersLocalizeDTO postalCodeMappersLocalizeDTO = postalCodeMappersLocalizeMapper.toDto(updatedPostalCodeMappersLocalize);

        restPostalCodeMappersLocalizeMockMvc.perform(put("/api/postal-code-mappers-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postalCodeMappersLocalizeDTO)))
            .andExpect(status().isOk());

        // Validate the PostalCodeMappersLocalize in the database
        List<PostalCodeMappersLocalize> postalCodeMappersLocalizeList = postalCodeMappersLocalizeRepository.findAll();
        assertThat(postalCodeMappersLocalizeList).hasSize(databaseSizeBeforeUpdate);
        PostalCodeMappersLocalize testPostalCodeMappersLocalize = postalCodeMappersLocalizeList.get(postalCodeMappersLocalizeList.size() - 1);
        assertThat(testPostalCodeMappersLocalize.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPostalCodeMappersLocalize() throws Exception {
        int databaseSizeBeforeUpdate = postalCodeMappersLocalizeRepository.findAll().size();

        // Create the PostalCodeMappersLocalize
        PostalCodeMappersLocalizeDTO postalCodeMappersLocalizeDTO = postalCodeMappersLocalizeMapper.toDto(postalCodeMappersLocalize);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostalCodeMappersLocalizeMockMvc.perform(put("/api/postal-code-mappers-localizes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postalCodeMappersLocalizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostalCodeMappersLocalize in the database
        List<PostalCodeMappersLocalize> postalCodeMappersLocalizeList = postalCodeMappersLocalizeRepository.findAll();
        assertThat(postalCodeMappersLocalizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePostalCodeMappersLocalize() throws Exception {
        // Initialize the database
        postalCodeMappersLocalizeRepository.saveAndFlush(postalCodeMappersLocalize);

        int databaseSizeBeforeDelete = postalCodeMappersLocalizeRepository.findAll().size();

        // Delete the postalCodeMappersLocalize
        restPostalCodeMappersLocalizeMockMvc.perform(delete("/api/postal-code-mappers-localizes/{id}", postalCodeMappersLocalize.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostalCodeMappersLocalize> postalCodeMappersLocalizeList = postalCodeMappersLocalizeRepository.findAll();
        assertThat(postalCodeMappersLocalizeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

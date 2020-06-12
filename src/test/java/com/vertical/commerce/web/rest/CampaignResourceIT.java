package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.Campaign;
import com.vertical.commerce.domain.Photos;
import com.vertical.commerce.repository.CampaignRepository;
import com.vertical.commerce.service.CampaignService;
import com.vertical.commerce.service.dto.CampaignDTO;
import com.vertical.commerce.service.mapper.CampaignMapper;
import com.vertical.commerce.service.dto.CampaignCriteria;
import com.vertical.commerce.service.CampaignQueryService;

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
 * Integration tests for the {@link CampaignResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CampaignResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_LABEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;
    private static final Integer SMALLER_SORT_ORDER = 1 - 1;

    private static final String DEFAULT_ICON_FONT = "AAAAAAAAAA";
    private static final String UPDATED_ICON_FONT = "BBBBBBBBBB";

    private static final String DEFAULT_THUMBNAIL_URL = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL_URL = "BBBBBBBBBB";

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private CampaignMapper campaignMapper;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private CampaignQueryService campaignQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCampaignMockMvc;

    private Campaign campaign;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Campaign createEntity(EntityManager em) {
        Campaign campaign = new Campaign()
            .name(DEFAULT_NAME)
            .shortLabel(DEFAULT_SHORT_LABEL)
            .sortOrder(DEFAULT_SORT_ORDER)
            .iconFont(DEFAULT_ICON_FONT)
            .thumbnailUrl(DEFAULT_THUMBNAIL_URL);
        return campaign;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Campaign createUpdatedEntity(EntityManager em) {
        Campaign campaign = new Campaign()
            .name(UPDATED_NAME)
            .shortLabel(UPDATED_SHORT_LABEL)
            .sortOrder(UPDATED_SORT_ORDER)
            .iconFont(UPDATED_ICON_FONT)
            .thumbnailUrl(UPDATED_THUMBNAIL_URL);
        return campaign;
    }

    @BeforeEach
    public void initTest() {
        campaign = createEntity(em);
    }

    @Test
    @Transactional
    public void createCampaign() throws Exception {
        int databaseSizeBeforeCreate = campaignRepository.findAll().size();
        // Create the Campaign
        CampaignDTO campaignDTO = campaignMapper.toDto(campaign);
        restCampaignMockMvc.perform(post("/api/campaigns").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(campaignDTO)))
            .andExpect(status().isCreated());

        // Validate the Campaign in the database
        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeCreate + 1);
        Campaign testCampaign = campaignList.get(campaignList.size() - 1);
        assertThat(testCampaign.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCampaign.getShortLabel()).isEqualTo(DEFAULT_SHORT_LABEL);
        assertThat(testCampaign.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testCampaign.getIconFont()).isEqualTo(DEFAULT_ICON_FONT);
        assertThat(testCampaign.getThumbnailUrl()).isEqualTo(DEFAULT_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void createCampaignWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = campaignRepository.findAll().size();

        // Create the Campaign with an existing ID
        campaign.setId(1L);
        CampaignDTO campaignDTO = campaignMapper.toDto(campaign);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCampaignMockMvc.perform(post("/api/campaigns").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(campaignDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Campaign in the database
        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignRepository.findAll().size();
        // set the field null
        campaign.setName(null);

        // Create the Campaign, which fails.
        CampaignDTO campaignDTO = campaignMapper.toDto(campaign);


        restCampaignMockMvc.perform(post("/api/campaigns").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(campaignDTO)))
            .andExpect(status().isBadRequest());

        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCampaigns() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList
        restCampaignMockMvc.perform(get("/api/campaigns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campaign.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortLabel").value(hasItem(DEFAULT_SHORT_LABEL)))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].iconFont").value(hasItem(DEFAULT_ICON_FONT)))
            .andExpect(jsonPath("$.[*].thumbnailUrl").value(hasItem(DEFAULT_THUMBNAIL_URL)));
    }
    
    @Test
    @Transactional
    public void getCampaign() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get the campaign
        restCampaignMockMvc.perform(get("/api/campaigns/{id}", campaign.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(campaign.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.shortLabel").value(DEFAULT_SHORT_LABEL))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.iconFont").value(DEFAULT_ICON_FONT))
            .andExpect(jsonPath("$.thumbnailUrl").value(DEFAULT_THUMBNAIL_URL));
    }


    @Test
    @Transactional
    public void getCampaignsByIdFiltering() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        Long id = campaign.getId();

        defaultCampaignShouldBeFound("id.equals=" + id);
        defaultCampaignShouldNotBeFound("id.notEquals=" + id);

        defaultCampaignShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCampaignShouldNotBeFound("id.greaterThan=" + id);

        defaultCampaignShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCampaignShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCampaignsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where name equals to DEFAULT_NAME
        defaultCampaignShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the campaignList where name equals to UPDATED_NAME
        defaultCampaignShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCampaignsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where name not equals to DEFAULT_NAME
        defaultCampaignShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the campaignList where name not equals to UPDATED_NAME
        defaultCampaignShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCampaignsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCampaignShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the campaignList where name equals to UPDATED_NAME
        defaultCampaignShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCampaignsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where name is not null
        defaultCampaignShouldBeFound("name.specified=true");

        // Get all the campaignList where name is null
        defaultCampaignShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCampaignsByNameContainsSomething() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where name contains DEFAULT_NAME
        defaultCampaignShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the campaignList where name contains UPDATED_NAME
        defaultCampaignShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCampaignsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where name does not contain DEFAULT_NAME
        defaultCampaignShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the campaignList where name does not contain UPDATED_NAME
        defaultCampaignShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCampaignsByShortLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where shortLabel equals to DEFAULT_SHORT_LABEL
        defaultCampaignShouldBeFound("shortLabel.equals=" + DEFAULT_SHORT_LABEL);

        // Get all the campaignList where shortLabel equals to UPDATED_SHORT_LABEL
        defaultCampaignShouldNotBeFound("shortLabel.equals=" + UPDATED_SHORT_LABEL);
    }

    @Test
    @Transactional
    public void getAllCampaignsByShortLabelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where shortLabel not equals to DEFAULT_SHORT_LABEL
        defaultCampaignShouldNotBeFound("shortLabel.notEquals=" + DEFAULT_SHORT_LABEL);

        // Get all the campaignList where shortLabel not equals to UPDATED_SHORT_LABEL
        defaultCampaignShouldBeFound("shortLabel.notEquals=" + UPDATED_SHORT_LABEL);
    }

    @Test
    @Transactional
    public void getAllCampaignsByShortLabelIsInShouldWork() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where shortLabel in DEFAULT_SHORT_LABEL or UPDATED_SHORT_LABEL
        defaultCampaignShouldBeFound("shortLabel.in=" + DEFAULT_SHORT_LABEL + "," + UPDATED_SHORT_LABEL);

        // Get all the campaignList where shortLabel equals to UPDATED_SHORT_LABEL
        defaultCampaignShouldNotBeFound("shortLabel.in=" + UPDATED_SHORT_LABEL);
    }

    @Test
    @Transactional
    public void getAllCampaignsByShortLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where shortLabel is not null
        defaultCampaignShouldBeFound("shortLabel.specified=true");

        // Get all the campaignList where shortLabel is null
        defaultCampaignShouldNotBeFound("shortLabel.specified=false");
    }
                @Test
    @Transactional
    public void getAllCampaignsByShortLabelContainsSomething() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where shortLabel contains DEFAULT_SHORT_LABEL
        defaultCampaignShouldBeFound("shortLabel.contains=" + DEFAULT_SHORT_LABEL);

        // Get all the campaignList where shortLabel contains UPDATED_SHORT_LABEL
        defaultCampaignShouldNotBeFound("shortLabel.contains=" + UPDATED_SHORT_LABEL);
    }

    @Test
    @Transactional
    public void getAllCampaignsByShortLabelNotContainsSomething() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where shortLabel does not contain DEFAULT_SHORT_LABEL
        defaultCampaignShouldNotBeFound("shortLabel.doesNotContain=" + DEFAULT_SHORT_LABEL);

        // Get all the campaignList where shortLabel does not contain UPDATED_SHORT_LABEL
        defaultCampaignShouldBeFound("shortLabel.doesNotContain=" + UPDATED_SHORT_LABEL);
    }


    @Test
    @Transactional
    public void getAllCampaignsBySortOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where sortOrder equals to DEFAULT_SORT_ORDER
        defaultCampaignShouldBeFound("sortOrder.equals=" + DEFAULT_SORT_ORDER);

        // Get all the campaignList where sortOrder equals to UPDATED_SORT_ORDER
        defaultCampaignShouldNotBeFound("sortOrder.equals=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllCampaignsBySortOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where sortOrder not equals to DEFAULT_SORT_ORDER
        defaultCampaignShouldNotBeFound("sortOrder.notEquals=" + DEFAULT_SORT_ORDER);

        // Get all the campaignList where sortOrder not equals to UPDATED_SORT_ORDER
        defaultCampaignShouldBeFound("sortOrder.notEquals=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllCampaignsBySortOrderIsInShouldWork() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where sortOrder in DEFAULT_SORT_ORDER or UPDATED_SORT_ORDER
        defaultCampaignShouldBeFound("sortOrder.in=" + DEFAULT_SORT_ORDER + "," + UPDATED_SORT_ORDER);

        // Get all the campaignList where sortOrder equals to UPDATED_SORT_ORDER
        defaultCampaignShouldNotBeFound("sortOrder.in=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllCampaignsBySortOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where sortOrder is not null
        defaultCampaignShouldBeFound("sortOrder.specified=true");

        // Get all the campaignList where sortOrder is null
        defaultCampaignShouldNotBeFound("sortOrder.specified=false");
    }

    @Test
    @Transactional
    public void getAllCampaignsBySortOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where sortOrder is greater than or equal to DEFAULT_SORT_ORDER
        defaultCampaignShouldBeFound("sortOrder.greaterThanOrEqual=" + DEFAULT_SORT_ORDER);

        // Get all the campaignList where sortOrder is greater than or equal to UPDATED_SORT_ORDER
        defaultCampaignShouldNotBeFound("sortOrder.greaterThanOrEqual=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllCampaignsBySortOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where sortOrder is less than or equal to DEFAULT_SORT_ORDER
        defaultCampaignShouldBeFound("sortOrder.lessThanOrEqual=" + DEFAULT_SORT_ORDER);

        // Get all the campaignList where sortOrder is less than or equal to SMALLER_SORT_ORDER
        defaultCampaignShouldNotBeFound("sortOrder.lessThanOrEqual=" + SMALLER_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllCampaignsBySortOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where sortOrder is less than DEFAULT_SORT_ORDER
        defaultCampaignShouldNotBeFound("sortOrder.lessThan=" + DEFAULT_SORT_ORDER);

        // Get all the campaignList where sortOrder is less than UPDATED_SORT_ORDER
        defaultCampaignShouldBeFound("sortOrder.lessThan=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllCampaignsBySortOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where sortOrder is greater than DEFAULT_SORT_ORDER
        defaultCampaignShouldNotBeFound("sortOrder.greaterThan=" + DEFAULT_SORT_ORDER);

        // Get all the campaignList where sortOrder is greater than SMALLER_SORT_ORDER
        defaultCampaignShouldBeFound("sortOrder.greaterThan=" + SMALLER_SORT_ORDER);
    }


    @Test
    @Transactional
    public void getAllCampaignsByIconFontIsEqualToSomething() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where iconFont equals to DEFAULT_ICON_FONT
        defaultCampaignShouldBeFound("iconFont.equals=" + DEFAULT_ICON_FONT);

        // Get all the campaignList where iconFont equals to UPDATED_ICON_FONT
        defaultCampaignShouldNotBeFound("iconFont.equals=" + UPDATED_ICON_FONT);
    }

    @Test
    @Transactional
    public void getAllCampaignsByIconFontIsNotEqualToSomething() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where iconFont not equals to DEFAULT_ICON_FONT
        defaultCampaignShouldNotBeFound("iconFont.notEquals=" + DEFAULT_ICON_FONT);

        // Get all the campaignList where iconFont not equals to UPDATED_ICON_FONT
        defaultCampaignShouldBeFound("iconFont.notEquals=" + UPDATED_ICON_FONT);
    }

    @Test
    @Transactional
    public void getAllCampaignsByIconFontIsInShouldWork() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where iconFont in DEFAULT_ICON_FONT or UPDATED_ICON_FONT
        defaultCampaignShouldBeFound("iconFont.in=" + DEFAULT_ICON_FONT + "," + UPDATED_ICON_FONT);

        // Get all the campaignList where iconFont equals to UPDATED_ICON_FONT
        defaultCampaignShouldNotBeFound("iconFont.in=" + UPDATED_ICON_FONT);
    }

    @Test
    @Transactional
    public void getAllCampaignsByIconFontIsNullOrNotNull() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where iconFont is not null
        defaultCampaignShouldBeFound("iconFont.specified=true");

        // Get all the campaignList where iconFont is null
        defaultCampaignShouldNotBeFound("iconFont.specified=false");
    }
                @Test
    @Transactional
    public void getAllCampaignsByIconFontContainsSomething() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where iconFont contains DEFAULT_ICON_FONT
        defaultCampaignShouldBeFound("iconFont.contains=" + DEFAULT_ICON_FONT);

        // Get all the campaignList where iconFont contains UPDATED_ICON_FONT
        defaultCampaignShouldNotBeFound("iconFont.contains=" + UPDATED_ICON_FONT);
    }

    @Test
    @Transactional
    public void getAllCampaignsByIconFontNotContainsSomething() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where iconFont does not contain DEFAULT_ICON_FONT
        defaultCampaignShouldNotBeFound("iconFont.doesNotContain=" + DEFAULT_ICON_FONT);

        // Get all the campaignList where iconFont does not contain UPDATED_ICON_FONT
        defaultCampaignShouldBeFound("iconFont.doesNotContain=" + UPDATED_ICON_FONT);
    }


    @Test
    @Transactional
    public void getAllCampaignsByThumbnailUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where thumbnailUrl equals to DEFAULT_THUMBNAIL_URL
        defaultCampaignShouldBeFound("thumbnailUrl.equals=" + DEFAULT_THUMBNAIL_URL);

        // Get all the campaignList where thumbnailUrl equals to UPDATED_THUMBNAIL_URL
        defaultCampaignShouldNotBeFound("thumbnailUrl.equals=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllCampaignsByThumbnailUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where thumbnailUrl not equals to DEFAULT_THUMBNAIL_URL
        defaultCampaignShouldNotBeFound("thumbnailUrl.notEquals=" + DEFAULT_THUMBNAIL_URL);

        // Get all the campaignList where thumbnailUrl not equals to UPDATED_THUMBNAIL_URL
        defaultCampaignShouldBeFound("thumbnailUrl.notEquals=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllCampaignsByThumbnailUrlIsInShouldWork() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where thumbnailUrl in DEFAULT_THUMBNAIL_URL or UPDATED_THUMBNAIL_URL
        defaultCampaignShouldBeFound("thumbnailUrl.in=" + DEFAULT_THUMBNAIL_URL + "," + UPDATED_THUMBNAIL_URL);

        // Get all the campaignList where thumbnailUrl equals to UPDATED_THUMBNAIL_URL
        defaultCampaignShouldNotBeFound("thumbnailUrl.in=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllCampaignsByThumbnailUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where thumbnailUrl is not null
        defaultCampaignShouldBeFound("thumbnailUrl.specified=true");

        // Get all the campaignList where thumbnailUrl is null
        defaultCampaignShouldNotBeFound("thumbnailUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllCampaignsByThumbnailUrlContainsSomething() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where thumbnailUrl contains DEFAULT_THUMBNAIL_URL
        defaultCampaignShouldBeFound("thumbnailUrl.contains=" + DEFAULT_THUMBNAIL_URL);

        // Get all the campaignList where thumbnailUrl contains UPDATED_THUMBNAIL_URL
        defaultCampaignShouldNotBeFound("thumbnailUrl.contains=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllCampaignsByThumbnailUrlNotContainsSomething() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList where thumbnailUrl does not contain DEFAULT_THUMBNAIL_URL
        defaultCampaignShouldNotBeFound("thumbnailUrl.doesNotContain=" + DEFAULT_THUMBNAIL_URL);

        // Get all the campaignList where thumbnailUrl does not contain UPDATED_THUMBNAIL_URL
        defaultCampaignShouldBeFound("thumbnailUrl.doesNotContain=" + UPDATED_THUMBNAIL_URL);
    }


    @Test
    @Transactional
    public void getAllCampaignsByIconIsEqualToSomething() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);
        Photos icon = PhotosResourceIT.createEntity(em);
        em.persist(icon);
        em.flush();
        campaign.setIcon(icon);
        campaignRepository.saveAndFlush(campaign);
        Long iconId = icon.getId();

        // Get all the campaignList where icon equals to iconId
        defaultCampaignShouldBeFound("iconId.equals=" + iconId);

        // Get all the campaignList where icon equals to iconId + 1
        defaultCampaignShouldNotBeFound("iconId.equals=" + (iconId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCampaignShouldBeFound(String filter) throws Exception {
        restCampaignMockMvc.perform(get("/api/campaigns?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campaign.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortLabel").value(hasItem(DEFAULT_SHORT_LABEL)))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].iconFont").value(hasItem(DEFAULT_ICON_FONT)))
            .andExpect(jsonPath("$.[*].thumbnailUrl").value(hasItem(DEFAULT_THUMBNAIL_URL)));

        // Check, that the count call also returns 1
        restCampaignMockMvc.perform(get("/api/campaigns/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCampaignShouldNotBeFound(String filter) throws Exception {
        restCampaignMockMvc.perform(get("/api/campaigns?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCampaignMockMvc.perform(get("/api/campaigns/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCampaign() throws Exception {
        // Get the campaign
        restCampaignMockMvc.perform(get("/api/campaigns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCampaign() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        int databaseSizeBeforeUpdate = campaignRepository.findAll().size();

        // Update the campaign
        Campaign updatedCampaign = campaignRepository.findById(campaign.getId()).get();
        // Disconnect from session so that the updates on updatedCampaign are not directly saved in db
        em.detach(updatedCampaign);
        updatedCampaign
            .name(UPDATED_NAME)
            .shortLabel(UPDATED_SHORT_LABEL)
            .sortOrder(UPDATED_SORT_ORDER)
            .iconFont(UPDATED_ICON_FONT)
            .thumbnailUrl(UPDATED_THUMBNAIL_URL);
        CampaignDTO campaignDTO = campaignMapper.toDto(updatedCampaign);

        restCampaignMockMvc.perform(put("/api/campaigns").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(campaignDTO)))
            .andExpect(status().isOk());

        // Validate the Campaign in the database
        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeUpdate);
        Campaign testCampaign = campaignList.get(campaignList.size() - 1);
        assertThat(testCampaign.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCampaign.getShortLabel()).isEqualTo(UPDATED_SHORT_LABEL);
        assertThat(testCampaign.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testCampaign.getIconFont()).isEqualTo(UPDATED_ICON_FONT);
        assertThat(testCampaign.getThumbnailUrl()).isEqualTo(UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingCampaign() throws Exception {
        int databaseSizeBeforeUpdate = campaignRepository.findAll().size();

        // Create the Campaign
        CampaignDTO campaignDTO = campaignMapper.toDto(campaign);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCampaignMockMvc.perform(put("/api/campaigns").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(campaignDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Campaign in the database
        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCampaign() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        int databaseSizeBeforeDelete = campaignRepository.findAll().size();

        // Delete the campaign
        restCampaignMockMvc.perform(delete("/api/campaigns/{id}", campaign.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.Photos;
import com.vertical.commerce.domain.StockItems;
import com.vertical.commerce.domain.Suppliers;
import com.vertical.commerce.repository.PhotosRepository;
import com.vertical.commerce.service.PhotosService;
import com.vertical.commerce.service.dto.PhotosDTO;
import com.vertical.commerce.service.mapper.PhotosMapper;
import com.vertical.commerce.service.dto.PhotosCriteria;
import com.vertical.commerce.service.PhotosQueryService;

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
 * Integration tests for the {@link PhotosResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PhotosResourceIT {

    private static final String DEFAULT_BLOB_ID = "AAAAAAAAAA";
    private static final String UPDATED_BLOB_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRIORITY = 1;
    private static final Integer UPDATED_PRIORITY = 2;
    private static final Integer SMALLER_PRIORITY = 1 - 1;

    private static final String DEFAULT_UID = "AAAAAAAAAA";
    private static final String UPDATED_UID = "BBBBBBBBBB";

    private static final Integer DEFAULT_SIZE = 1;
    private static final Integer UPDATED_SIZE = 2;
    private static final Integer SMALLER_SIZE = 1 - 1;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_THUMB_URL = "AAAAAAAAAA";
    private static final String UPDATED_THUMB_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_PERCENT = 1;
    private static final Integer UPDATED_PERCENT = 2;
    private static final Integer SMALLER_PERCENT = 1 - 1;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEFAULT_IND = false;
    private static final Boolean UPDATED_DEFAULT_IND = true;

    private static final Boolean DEFAULT_ACTIVE_FLAG = false;
    private static final Boolean UPDATED_ACTIVE_FLAG = true;

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PhotosRepository photosRepository;

    @Autowired
    private PhotosMapper photosMapper;

    @Autowired
    private PhotosService photosService;

    @Autowired
    private PhotosQueryService photosQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhotosMockMvc;

    private Photos photos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Photos createEntity(EntityManager em) {
        Photos photos = new Photos()
            .blobId(DEFAULT_BLOB_ID)
            .priority(DEFAULT_PRIORITY)
            .uid(DEFAULT_UID)
            .size(DEFAULT_SIZE)
            .name(DEFAULT_NAME)
            .fileName(DEFAULT_FILE_NAME)
            .url(DEFAULT_URL)
            .status(DEFAULT_STATUS)
            .thumbUrl(DEFAULT_THUMB_URL)
            .percent(DEFAULT_PERCENT)
            .type(DEFAULT_TYPE)
            .defaultInd(DEFAULT_DEFAULT_IND)
            .activeFlag(DEFAULT_ACTIVE_FLAG)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return photos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Photos createUpdatedEntity(EntityManager em) {
        Photos photos = new Photos()
            .blobId(UPDATED_BLOB_ID)
            .priority(UPDATED_PRIORITY)
            .uid(UPDATED_UID)
            .size(UPDATED_SIZE)
            .name(UPDATED_NAME)
            .fileName(UPDATED_FILE_NAME)
            .url(UPDATED_URL)
            .status(UPDATED_STATUS)
            .thumbUrl(UPDATED_THUMB_URL)
            .percent(UPDATED_PERCENT)
            .type(UPDATED_TYPE)
            .defaultInd(UPDATED_DEFAULT_IND)
            .activeFlag(UPDATED_ACTIVE_FLAG)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return photos;
    }

    @BeforeEach
    public void initTest() {
        photos = createEntity(em);
    }

    @Test
    @Transactional
    public void createPhotos() throws Exception {
        int databaseSizeBeforeCreate = photosRepository.findAll().size();
        // Create the Photos
        PhotosDTO photosDTO = photosMapper.toDto(photos);
        restPhotosMockMvc.perform(post("/api/photos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(photosDTO)))
            .andExpect(status().isCreated());

        // Validate the Photos in the database
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeCreate + 1);
        Photos testPhotos = photosList.get(photosList.size() - 1);
        assertThat(testPhotos.getBlobId()).isEqualTo(DEFAULT_BLOB_ID);
        assertThat(testPhotos.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testPhotos.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testPhotos.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testPhotos.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPhotos.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testPhotos.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testPhotos.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPhotos.getThumbUrl()).isEqualTo(DEFAULT_THUMB_URL);
        assertThat(testPhotos.getPercent()).isEqualTo(DEFAULT_PERCENT);
        assertThat(testPhotos.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPhotos.isDefaultInd()).isEqualTo(DEFAULT_DEFAULT_IND);
        assertThat(testPhotos.isActiveFlag()).isEqualTo(DEFAULT_ACTIVE_FLAG);
        assertThat(testPhotos.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testPhotos.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createPhotosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = photosRepository.findAll().size();

        // Create the Photos with an existing ID
        photos.setId(1L);
        PhotosDTO photosDTO = photosMapper.toDto(photos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhotosMockMvc.perform(post("/api/photos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(photosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Photos in the database
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBlobIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = photosRepository.findAll().size();
        // set the field null
        photos.setBlobId(null);

        // Create the Photos, which fails.
        PhotosDTO photosDTO = photosMapper.toDto(photos);


        restPhotosMockMvc.perform(post("/api/photos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(photosDTO)))
            .andExpect(status().isBadRequest());

        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDefaultIndIsRequired() throws Exception {
        int databaseSizeBeforeTest = photosRepository.findAll().size();
        // set the field null
        photos.setDefaultInd(null);

        // Create the Photos, which fails.
        PhotosDTO photosDTO = photosMapper.toDto(photos);


        restPhotosMockMvc.perform(post("/api/photos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(photosDTO)))
            .andExpect(status().isBadRequest());

        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPhotos() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList
        restPhotosMockMvc.perform(get("/api/photos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(photos.getId().intValue())))
            .andExpect(jsonPath("$.[*].blobId").value(hasItem(DEFAULT_BLOB_ID)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].thumbUrl").value(hasItem(DEFAULT_THUMB_URL)))
            .andExpect(jsonPath("$.[*].percent").value(hasItem(DEFAULT_PERCENT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].defaultInd").value(hasItem(DEFAULT_DEFAULT_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPhotos() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get the photos
        restPhotosMockMvc.perform(get("/api/photos/{id}", photos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(photos.getId().intValue()))
            .andExpect(jsonPath("$.blobId").value(DEFAULT_BLOB_ID))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.thumbUrl").value(DEFAULT_THUMB_URL))
            .andExpect(jsonPath("$.percent").value(DEFAULT_PERCENT))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.defaultInd").value(DEFAULT_DEFAULT_IND.booleanValue()))
            .andExpect(jsonPath("$.activeFlag").value(DEFAULT_ACTIVE_FLAG.booleanValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getPhotosByIdFiltering() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        Long id = photos.getId();

        defaultPhotosShouldBeFound("id.equals=" + id);
        defaultPhotosShouldNotBeFound("id.notEquals=" + id);

        defaultPhotosShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPhotosShouldNotBeFound("id.greaterThan=" + id);

        defaultPhotosShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPhotosShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPhotosByBlobIdIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where blobId equals to DEFAULT_BLOB_ID
        defaultPhotosShouldBeFound("blobId.equals=" + DEFAULT_BLOB_ID);

        // Get all the photosList where blobId equals to UPDATED_BLOB_ID
        defaultPhotosShouldNotBeFound("blobId.equals=" + UPDATED_BLOB_ID);
    }

    @Test
    @Transactional
    public void getAllPhotosByBlobIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where blobId not equals to DEFAULT_BLOB_ID
        defaultPhotosShouldNotBeFound("blobId.notEquals=" + DEFAULT_BLOB_ID);

        // Get all the photosList where blobId not equals to UPDATED_BLOB_ID
        defaultPhotosShouldBeFound("blobId.notEquals=" + UPDATED_BLOB_ID);
    }

    @Test
    @Transactional
    public void getAllPhotosByBlobIdIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where blobId in DEFAULT_BLOB_ID or UPDATED_BLOB_ID
        defaultPhotosShouldBeFound("blobId.in=" + DEFAULT_BLOB_ID + "," + UPDATED_BLOB_ID);

        // Get all the photosList where blobId equals to UPDATED_BLOB_ID
        defaultPhotosShouldNotBeFound("blobId.in=" + UPDATED_BLOB_ID);
    }

    @Test
    @Transactional
    public void getAllPhotosByBlobIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where blobId is not null
        defaultPhotosShouldBeFound("blobId.specified=true");

        // Get all the photosList where blobId is null
        defaultPhotosShouldNotBeFound("blobId.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosByBlobIdContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where blobId contains DEFAULT_BLOB_ID
        defaultPhotosShouldBeFound("blobId.contains=" + DEFAULT_BLOB_ID);

        // Get all the photosList where blobId contains UPDATED_BLOB_ID
        defaultPhotosShouldNotBeFound("blobId.contains=" + UPDATED_BLOB_ID);
    }

    @Test
    @Transactional
    public void getAllPhotosByBlobIdNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where blobId does not contain DEFAULT_BLOB_ID
        defaultPhotosShouldNotBeFound("blobId.doesNotContain=" + DEFAULT_BLOB_ID);

        // Get all the photosList where blobId does not contain UPDATED_BLOB_ID
        defaultPhotosShouldBeFound("blobId.doesNotContain=" + UPDATED_BLOB_ID);
    }


    @Test
    @Transactional
    public void getAllPhotosByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where priority equals to DEFAULT_PRIORITY
        defaultPhotosShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the photosList where priority equals to UPDATED_PRIORITY
        defaultPhotosShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllPhotosByPriorityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where priority not equals to DEFAULT_PRIORITY
        defaultPhotosShouldNotBeFound("priority.notEquals=" + DEFAULT_PRIORITY);

        // Get all the photosList where priority not equals to UPDATED_PRIORITY
        defaultPhotosShouldBeFound("priority.notEquals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllPhotosByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultPhotosShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the photosList where priority equals to UPDATED_PRIORITY
        defaultPhotosShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllPhotosByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where priority is not null
        defaultPhotosShouldBeFound("priority.specified=true");

        // Get all the photosList where priority is null
        defaultPhotosShouldNotBeFound("priority.specified=false");
    }

    @Test
    @Transactional
    public void getAllPhotosByPriorityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where priority is greater than or equal to DEFAULT_PRIORITY
        defaultPhotosShouldBeFound("priority.greaterThanOrEqual=" + DEFAULT_PRIORITY);

        // Get all the photosList where priority is greater than or equal to UPDATED_PRIORITY
        defaultPhotosShouldNotBeFound("priority.greaterThanOrEqual=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllPhotosByPriorityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where priority is less than or equal to DEFAULT_PRIORITY
        defaultPhotosShouldBeFound("priority.lessThanOrEqual=" + DEFAULT_PRIORITY);

        // Get all the photosList where priority is less than or equal to SMALLER_PRIORITY
        defaultPhotosShouldNotBeFound("priority.lessThanOrEqual=" + SMALLER_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllPhotosByPriorityIsLessThanSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where priority is less than DEFAULT_PRIORITY
        defaultPhotosShouldNotBeFound("priority.lessThan=" + DEFAULT_PRIORITY);

        // Get all the photosList where priority is less than UPDATED_PRIORITY
        defaultPhotosShouldBeFound("priority.lessThan=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllPhotosByPriorityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where priority is greater than DEFAULT_PRIORITY
        defaultPhotosShouldNotBeFound("priority.greaterThan=" + DEFAULT_PRIORITY);

        // Get all the photosList where priority is greater than SMALLER_PRIORITY
        defaultPhotosShouldBeFound("priority.greaterThan=" + SMALLER_PRIORITY);
    }


    @Test
    @Transactional
    public void getAllPhotosByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where uid equals to DEFAULT_UID
        defaultPhotosShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the photosList where uid equals to UPDATED_UID
        defaultPhotosShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllPhotosByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where uid not equals to DEFAULT_UID
        defaultPhotosShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the photosList where uid not equals to UPDATED_UID
        defaultPhotosShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllPhotosByUidIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where uid in DEFAULT_UID or UPDATED_UID
        defaultPhotosShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the photosList where uid equals to UPDATED_UID
        defaultPhotosShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllPhotosByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where uid is not null
        defaultPhotosShouldBeFound("uid.specified=true");

        // Get all the photosList where uid is null
        defaultPhotosShouldNotBeFound("uid.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosByUidContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where uid contains DEFAULT_UID
        defaultPhotosShouldBeFound("uid.contains=" + DEFAULT_UID);

        // Get all the photosList where uid contains UPDATED_UID
        defaultPhotosShouldNotBeFound("uid.contains=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllPhotosByUidNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where uid does not contain DEFAULT_UID
        defaultPhotosShouldNotBeFound("uid.doesNotContain=" + DEFAULT_UID);

        // Get all the photosList where uid does not contain UPDATED_UID
        defaultPhotosShouldBeFound("uid.doesNotContain=" + UPDATED_UID);
    }


    @Test
    @Transactional
    public void getAllPhotosBySizeIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where size equals to DEFAULT_SIZE
        defaultPhotosShouldBeFound("size.equals=" + DEFAULT_SIZE);

        // Get all the photosList where size equals to UPDATED_SIZE
        defaultPhotosShouldNotBeFound("size.equals=" + UPDATED_SIZE);
    }

    @Test
    @Transactional
    public void getAllPhotosBySizeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where size not equals to DEFAULT_SIZE
        defaultPhotosShouldNotBeFound("size.notEquals=" + DEFAULT_SIZE);

        // Get all the photosList where size not equals to UPDATED_SIZE
        defaultPhotosShouldBeFound("size.notEquals=" + UPDATED_SIZE);
    }

    @Test
    @Transactional
    public void getAllPhotosBySizeIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where size in DEFAULT_SIZE or UPDATED_SIZE
        defaultPhotosShouldBeFound("size.in=" + DEFAULT_SIZE + "," + UPDATED_SIZE);

        // Get all the photosList where size equals to UPDATED_SIZE
        defaultPhotosShouldNotBeFound("size.in=" + UPDATED_SIZE);
    }

    @Test
    @Transactional
    public void getAllPhotosBySizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where size is not null
        defaultPhotosShouldBeFound("size.specified=true");

        // Get all the photosList where size is null
        defaultPhotosShouldNotBeFound("size.specified=false");
    }

    @Test
    @Transactional
    public void getAllPhotosBySizeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where size is greater than or equal to DEFAULT_SIZE
        defaultPhotosShouldBeFound("size.greaterThanOrEqual=" + DEFAULT_SIZE);

        // Get all the photosList where size is greater than or equal to UPDATED_SIZE
        defaultPhotosShouldNotBeFound("size.greaterThanOrEqual=" + UPDATED_SIZE);
    }

    @Test
    @Transactional
    public void getAllPhotosBySizeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where size is less than or equal to DEFAULT_SIZE
        defaultPhotosShouldBeFound("size.lessThanOrEqual=" + DEFAULT_SIZE);

        // Get all the photosList where size is less than or equal to SMALLER_SIZE
        defaultPhotosShouldNotBeFound("size.lessThanOrEqual=" + SMALLER_SIZE);
    }

    @Test
    @Transactional
    public void getAllPhotosBySizeIsLessThanSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where size is less than DEFAULT_SIZE
        defaultPhotosShouldNotBeFound("size.lessThan=" + DEFAULT_SIZE);

        // Get all the photosList where size is less than UPDATED_SIZE
        defaultPhotosShouldBeFound("size.lessThan=" + UPDATED_SIZE);
    }

    @Test
    @Transactional
    public void getAllPhotosBySizeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where size is greater than DEFAULT_SIZE
        defaultPhotosShouldNotBeFound("size.greaterThan=" + DEFAULT_SIZE);

        // Get all the photosList where size is greater than SMALLER_SIZE
        defaultPhotosShouldBeFound("size.greaterThan=" + SMALLER_SIZE);
    }


    @Test
    @Transactional
    public void getAllPhotosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where name equals to DEFAULT_NAME
        defaultPhotosShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the photosList where name equals to UPDATED_NAME
        defaultPhotosShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPhotosByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where name not equals to DEFAULT_NAME
        defaultPhotosShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the photosList where name not equals to UPDATED_NAME
        defaultPhotosShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPhotosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPhotosShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the photosList where name equals to UPDATED_NAME
        defaultPhotosShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPhotosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where name is not null
        defaultPhotosShouldBeFound("name.specified=true");

        // Get all the photosList where name is null
        defaultPhotosShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosByNameContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where name contains DEFAULT_NAME
        defaultPhotosShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the photosList where name contains UPDATED_NAME
        defaultPhotosShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPhotosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where name does not contain DEFAULT_NAME
        defaultPhotosShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the photosList where name does not contain UPDATED_NAME
        defaultPhotosShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPhotosByFileNameIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where fileName equals to DEFAULT_FILE_NAME
        defaultPhotosShouldBeFound("fileName.equals=" + DEFAULT_FILE_NAME);

        // Get all the photosList where fileName equals to UPDATED_FILE_NAME
        defaultPhotosShouldNotBeFound("fileName.equals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllPhotosByFileNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where fileName not equals to DEFAULT_FILE_NAME
        defaultPhotosShouldNotBeFound("fileName.notEquals=" + DEFAULT_FILE_NAME);

        // Get all the photosList where fileName not equals to UPDATED_FILE_NAME
        defaultPhotosShouldBeFound("fileName.notEquals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllPhotosByFileNameIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where fileName in DEFAULT_FILE_NAME or UPDATED_FILE_NAME
        defaultPhotosShouldBeFound("fileName.in=" + DEFAULT_FILE_NAME + "," + UPDATED_FILE_NAME);

        // Get all the photosList where fileName equals to UPDATED_FILE_NAME
        defaultPhotosShouldNotBeFound("fileName.in=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllPhotosByFileNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where fileName is not null
        defaultPhotosShouldBeFound("fileName.specified=true");

        // Get all the photosList where fileName is null
        defaultPhotosShouldNotBeFound("fileName.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosByFileNameContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where fileName contains DEFAULT_FILE_NAME
        defaultPhotosShouldBeFound("fileName.contains=" + DEFAULT_FILE_NAME);

        // Get all the photosList where fileName contains UPDATED_FILE_NAME
        defaultPhotosShouldNotBeFound("fileName.contains=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllPhotosByFileNameNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where fileName does not contain DEFAULT_FILE_NAME
        defaultPhotosShouldNotBeFound("fileName.doesNotContain=" + DEFAULT_FILE_NAME);

        // Get all the photosList where fileName does not contain UPDATED_FILE_NAME
        defaultPhotosShouldBeFound("fileName.doesNotContain=" + UPDATED_FILE_NAME);
    }


    @Test
    @Transactional
    public void getAllPhotosByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where url equals to DEFAULT_URL
        defaultPhotosShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the photosList where url equals to UPDATED_URL
        defaultPhotosShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where url not equals to DEFAULT_URL
        defaultPhotosShouldNotBeFound("url.notEquals=" + DEFAULT_URL);

        // Get all the photosList where url not equals to UPDATED_URL
        defaultPhotosShouldBeFound("url.notEquals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where url in DEFAULT_URL or UPDATED_URL
        defaultPhotosShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the photosList where url equals to UPDATED_URL
        defaultPhotosShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where url is not null
        defaultPhotosShouldBeFound("url.specified=true");

        // Get all the photosList where url is null
        defaultPhotosShouldNotBeFound("url.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosByUrlContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where url contains DEFAULT_URL
        defaultPhotosShouldBeFound("url.contains=" + DEFAULT_URL);

        // Get all the photosList where url contains UPDATED_URL
        defaultPhotosShouldNotBeFound("url.contains=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByUrlNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where url does not contain DEFAULT_URL
        defaultPhotosShouldNotBeFound("url.doesNotContain=" + DEFAULT_URL);

        // Get all the photosList where url does not contain UPDATED_URL
        defaultPhotosShouldBeFound("url.doesNotContain=" + UPDATED_URL);
    }


    @Test
    @Transactional
    public void getAllPhotosByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where status equals to DEFAULT_STATUS
        defaultPhotosShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the photosList where status equals to UPDATED_STATUS
        defaultPhotosShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPhotosByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where status not equals to DEFAULT_STATUS
        defaultPhotosShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the photosList where status not equals to UPDATED_STATUS
        defaultPhotosShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPhotosByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultPhotosShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the photosList where status equals to UPDATED_STATUS
        defaultPhotosShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPhotosByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where status is not null
        defaultPhotosShouldBeFound("status.specified=true");

        // Get all the photosList where status is null
        defaultPhotosShouldNotBeFound("status.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosByStatusContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where status contains DEFAULT_STATUS
        defaultPhotosShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the photosList where status contains UPDATED_STATUS
        defaultPhotosShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPhotosByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where status does not contain DEFAULT_STATUS
        defaultPhotosShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the photosList where status does not contain UPDATED_STATUS
        defaultPhotosShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllPhotosByThumbUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where thumbUrl equals to DEFAULT_THUMB_URL
        defaultPhotosShouldBeFound("thumbUrl.equals=" + DEFAULT_THUMB_URL);

        // Get all the photosList where thumbUrl equals to UPDATED_THUMB_URL
        defaultPhotosShouldNotBeFound("thumbUrl.equals=" + UPDATED_THUMB_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByThumbUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where thumbUrl not equals to DEFAULT_THUMB_URL
        defaultPhotosShouldNotBeFound("thumbUrl.notEquals=" + DEFAULT_THUMB_URL);

        // Get all the photosList where thumbUrl not equals to UPDATED_THUMB_URL
        defaultPhotosShouldBeFound("thumbUrl.notEquals=" + UPDATED_THUMB_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByThumbUrlIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where thumbUrl in DEFAULT_THUMB_URL or UPDATED_THUMB_URL
        defaultPhotosShouldBeFound("thumbUrl.in=" + DEFAULT_THUMB_URL + "," + UPDATED_THUMB_URL);

        // Get all the photosList where thumbUrl equals to UPDATED_THUMB_URL
        defaultPhotosShouldNotBeFound("thumbUrl.in=" + UPDATED_THUMB_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByThumbUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where thumbUrl is not null
        defaultPhotosShouldBeFound("thumbUrl.specified=true");

        // Get all the photosList where thumbUrl is null
        defaultPhotosShouldNotBeFound("thumbUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosByThumbUrlContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where thumbUrl contains DEFAULT_THUMB_URL
        defaultPhotosShouldBeFound("thumbUrl.contains=" + DEFAULT_THUMB_URL);

        // Get all the photosList where thumbUrl contains UPDATED_THUMB_URL
        defaultPhotosShouldNotBeFound("thumbUrl.contains=" + UPDATED_THUMB_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByThumbUrlNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where thumbUrl does not contain DEFAULT_THUMB_URL
        defaultPhotosShouldNotBeFound("thumbUrl.doesNotContain=" + DEFAULT_THUMB_URL);

        // Get all the photosList where thumbUrl does not contain UPDATED_THUMB_URL
        defaultPhotosShouldBeFound("thumbUrl.doesNotContain=" + UPDATED_THUMB_URL);
    }


    @Test
    @Transactional
    public void getAllPhotosByPercentIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where percent equals to DEFAULT_PERCENT
        defaultPhotosShouldBeFound("percent.equals=" + DEFAULT_PERCENT);

        // Get all the photosList where percent equals to UPDATED_PERCENT
        defaultPhotosShouldNotBeFound("percent.equals=" + UPDATED_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPhotosByPercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where percent not equals to DEFAULT_PERCENT
        defaultPhotosShouldNotBeFound("percent.notEquals=" + DEFAULT_PERCENT);

        // Get all the photosList where percent not equals to UPDATED_PERCENT
        defaultPhotosShouldBeFound("percent.notEquals=" + UPDATED_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPhotosByPercentIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where percent in DEFAULT_PERCENT or UPDATED_PERCENT
        defaultPhotosShouldBeFound("percent.in=" + DEFAULT_PERCENT + "," + UPDATED_PERCENT);

        // Get all the photosList where percent equals to UPDATED_PERCENT
        defaultPhotosShouldNotBeFound("percent.in=" + UPDATED_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPhotosByPercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where percent is not null
        defaultPhotosShouldBeFound("percent.specified=true");

        // Get all the photosList where percent is null
        defaultPhotosShouldNotBeFound("percent.specified=false");
    }

    @Test
    @Transactional
    public void getAllPhotosByPercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where percent is greater than or equal to DEFAULT_PERCENT
        defaultPhotosShouldBeFound("percent.greaterThanOrEqual=" + DEFAULT_PERCENT);

        // Get all the photosList where percent is greater than or equal to UPDATED_PERCENT
        defaultPhotosShouldNotBeFound("percent.greaterThanOrEqual=" + UPDATED_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPhotosByPercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where percent is less than or equal to DEFAULT_PERCENT
        defaultPhotosShouldBeFound("percent.lessThanOrEqual=" + DEFAULT_PERCENT);

        // Get all the photosList where percent is less than or equal to SMALLER_PERCENT
        defaultPhotosShouldNotBeFound("percent.lessThanOrEqual=" + SMALLER_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPhotosByPercentIsLessThanSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where percent is less than DEFAULT_PERCENT
        defaultPhotosShouldNotBeFound("percent.lessThan=" + DEFAULT_PERCENT);

        // Get all the photosList where percent is less than UPDATED_PERCENT
        defaultPhotosShouldBeFound("percent.lessThan=" + UPDATED_PERCENT);
    }

    @Test
    @Transactional
    public void getAllPhotosByPercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where percent is greater than DEFAULT_PERCENT
        defaultPhotosShouldNotBeFound("percent.greaterThan=" + DEFAULT_PERCENT);

        // Get all the photosList where percent is greater than SMALLER_PERCENT
        defaultPhotosShouldBeFound("percent.greaterThan=" + SMALLER_PERCENT);
    }


    @Test
    @Transactional
    public void getAllPhotosByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where type equals to DEFAULT_TYPE
        defaultPhotosShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the photosList where type equals to UPDATED_TYPE
        defaultPhotosShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPhotosByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where type not equals to DEFAULT_TYPE
        defaultPhotosShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the photosList where type not equals to UPDATED_TYPE
        defaultPhotosShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPhotosByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultPhotosShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the photosList where type equals to UPDATED_TYPE
        defaultPhotosShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPhotosByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where type is not null
        defaultPhotosShouldBeFound("type.specified=true");

        // Get all the photosList where type is null
        defaultPhotosShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosByTypeContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where type contains DEFAULT_TYPE
        defaultPhotosShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the photosList where type contains UPDATED_TYPE
        defaultPhotosShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPhotosByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where type does not contain DEFAULT_TYPE
        defaultPhotosShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the photosList where type does not contain UPDATED_TYPE
        defaultPhotosShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllPhotosByDefaultIndIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where defaultInd equals to DEFAULT_DEFAULT_IND
        defaultPhotosShouldBeFound("defaultInd.equals=" + DEFAULT_DEFAULT_IND);

        // Get all the photosList where defaultInd equals to UPDATED_DEFAULT_IND
        defaultPhotosShouldNotBeFound("defaultInd.equals=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllPhotosByDefaultIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where defaultInd not equals to DEFAULT_DEFAULT_IND
        defaultPhotosShouldNotBeFound("defaultInd.notEquals=" + DEFAULT_DEFAULT_IND);

        // Get all the photosList where defaultInd not equals to UPDATED_DEFAULT_IND
        defaultPhotosShouldBeFound("defaultInd.notEquals=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllPhotosByDefaultIndIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where defaultInd in DEFAULT_DEFAULT_IND or UPDATED_DEFAULT_IND
        defaultPhotosShouldBeFound("defaultInd.in=" + DEFAULT_DEFAULT_IND + "," + UPDATED_DEFAULT_IND);

        // Get all the photosList where defaultInd equals to UPDATED_DEFAULT_IND
        defaultPhotosShouldNotBeFound("defaultInd.in=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllPhotosByDefaultIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where defaultInd is not null
        defaultPhotosShouldBeFound("defaultInd.specified=true");

        // Get all the photosList where defaultInd is null
        defaultPhotosShouldNotBeFound("defaultInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllPhotosByActiveFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where activeFlag equals to DEFAULT_ACTIVE_FLAG
        defaultPhotosShouldBeFound("activeFlag.equals=" + DEFAULT_ACTIVE_FLAG);

        // Get all the photosList where activeFlag equals to UPDATED_ACTIVE_FLAG
        defaultPhotosShouldNotBeFound("activeFlag.equals=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    public void getAllPhotosByActiveFlagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where activeFlag not equals to DEFAULT_ACTIVE_FLAG
        defaultPhotosShouldNotBeFound("activeFlag.notEquals=" + DEFAULT_ACTIVE_FLAG);

        // Get all the photosList where activeFlag not equals to UPDATED_ACTIVE_FLAG
        defaultPhotosShouldBeFound("activeFlag.notEquals=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    public void getAllPhotosByActiveFlagIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where activeFlag in DEFAULT_ACTIVE_FLAG or UPDATED_ACTIVE_FLAG
        defaultPhotosShouldBeFound("activeFlag.in=" + DEFAULT_ACTIVE_FLAG + "," + UPDATED_ACTIVE_FLAG);

        // Get all the photosList where activeFlag equals to UPDATED_ACTIVE_FLAG
        defaultPhotosShouldNotBeFound("activeFlag.in=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    public void getAllPhotosByActiveFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where activeFlag is not null
        defaultPhotosShouldBeFound("activeFlag.specified=true");

        // Get all the photosList where activeFlag is null
        defaultPhotosShouldNotBeFound("activeFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllPhotosByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultPhotosShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the photosList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPhotosShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllPhotosByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultPhotosShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the photosList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultPhotosShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllPhotosByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultPhotosShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the photosList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPhotosShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllPhotosByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where lastModified is not null
        defaultPhotosShouldBeFound("lastModified.specified=true");

        // Get all the photosList where lastModified is null
        defaultPhotosShouldNotBeFound("lastModified.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultPhotosShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the photosList where lastModified contains UPDATED_LAST_MODIFIED
        defaultPhotosShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllPhotosByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultPhotosShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the photosList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultPhotosShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }


    @Test
    @Transactional
    public void getAllPhotosByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultPhotosShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the photosList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultPhotosShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllPhotosByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultPhotosShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the photosList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultPhotosShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllPhotosByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultPhotosShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the photosList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultPhotosShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllPhotosByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where lastModifiedDate is not null
        defaultPhotosShouldBeFound("lastModifiedDate.specified=true");

        // Get all the photosList where lastModifiedDate is null
        defaultPhotosShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPhotosByStockItemIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);
        StockItems stockItem = StockItemsResourceIT.createEntity(em);
        em.persist(stockItem);
        em.flush();
        photos.setStockItem(stockItem);
        photosRepository.saveAndFlush(photos);
        Long stockItemId = stockItem.getId();

        // Get all the photosList where stockItem equals to stockItemId
        defaultPhotosShouldBeFound("stockItemId.equals=" + stockItemId);

        // Get all the photosList where stockItem equals to stockItemId + 1
        defaultPhotosShouldNotBeFound("stockItemId.equals=" + (stockItemId + 1));
    }


    @Test
    @Transactional
    public void getAllPhotosBySupplierBannerIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);
        Suppliers supplierBanner = SuppliersResourceIT.createEntity(em);
        em.persist(supplierBanner);
        em.flush();
        photos.setSupplierBanner(supplierBanner);
        photosRepository.saveAndFlush(photos);
        Long supplierBannerId = supplierBanner.getId();

        // Get all the photosList where supplierBanner equals to supplierBannerId
        defaultPhotosShouldBeFound("supplierBannerId.equals=" + supplierBannerId);

        // Get all the photosList where supplierBanner equals to supplierBannerId + 1
        defaultPhotosShouldNotBeFound("supplierBannerId.equals=" + (supplierBannerId + 1));
    }


    @Test
    @Transactional
    public void getAllPhotosBySupplierDocumentIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);
        Suppliers supplierDocument = SuppliersResourceIT.createEntity(em);
        em.persist(supplierDocument);
        em.flush();
        photos.setSupplierDocument(supplierDocument);
        photosRepository.saveAndFlush(photos);
        Long supplierDocumentId = supplierDocument.getId();

        // Get all the photosList where supplierDocument equals to supplierDocumentId
        defaultPhotosShouldBeFound("supplierDocumentId.equals=" + supplierDocumentId);

        // Get all the photosList where supplierDocument equals to supplierDocumentId + 1
        defaultPhotosShouldNotBeFound("supplierDocumentId.equals=" + (supplierDocumentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPhotosShouldBeFound(String filter) throws Exception {
        restPhotosMockMvc.perform(get("/api/photos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(photos.getId().intValue())))
            .andExpect(jsonPath("$.[*].blobId").value(hasItem(DEFAULT_BLOB_ID)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].thumbUrl").value(hasItem(DEFAULT_THUMB_URL)))
            .andExpect(jsonPath("$.[*].percent").value(hasItem(DEFAULT_PERCENT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].defaultInd").value(hasItem(DEFAULT_DEFAULT_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restPhotosMockMvc.perform(get("/api/photos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPhotosShouldNotBeFound(String filter) throws Exception {
        restPhotosMockMvc.perform(get("/api/photos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPhotosMockMvc.perform(get("/api/photos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPhotos() throws Exception {
        // Get the photos
        restPhotosMockMvc.perform(get("/api/photos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhotos() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        int databaseSizeBeforeUpdate = photosRepository.findAll().size();

        // Update the photos
        Photos updatedPhotos = photosRepository.findById(photos.getId()).get();
        // Disconnect from session so that the updates on updatedPhotos are not directly saved in db
        em.detach(updatedPhotos);
        updatedPhotos
            .blobId(UPDATED_BLOB_ID)
            .priority(UPDATED_PRIORITY)
            .uid(UPDATED_UID)
            .size(UPDATED_SIZE)
            .name(UPDATED_NAME)
            .fileName(UPDATED_FILE_NAME)
            .url(UPDATED_URL)
            .status(UPDATED_STATUS)
            .thumbUrl(UPDATED_THUMB_URL)
            .percent(UPDATED_PERCENT)
            .type(UPDATED_TYPE)
            .defaultInd(UPDATED_DEFAULT_IND)
            .activeFlag(UPDATED_ACTIVE_FLAG)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        PhotosDTO photosDTO = photosMapper.toDto(updatedPhotos);

        restPhotosMockMvc.perform(put("/api/photos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(photosDTO)))
            .andExpect(status().isOk());

        // Validate the Photos in the database
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeUpdate);
        Photos testPhotos = photosList.get(photosList.size() - 1);
        assertThat(testPhotos.getBlobId()).isEqualTo(UPDATED_BLOB_ID);
        assertThat(testPhotos.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testPhotos.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testPhotos.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testPhotos.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPhotos.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testPhotos.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testPhotos.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPhotos.getThumbUrl()).isEqualTo(UPDATED_THUMB_URL);
        assertThat(testPhotos.getPercent()).isEqualTo(UPDATED_PERCENT);
        assertThat(testPhotos.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPhotos.isDefaultInd()).isEqualTo(UPDATED_DEFAULT_IND);
        assertThat(testPhotos.isActiveFlag()).isEqualTo(UPDATED_ACTIVE_FLAG);
        assertThat(testPhotos.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPhotos.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPhotos() throws Exception {
        int databaseSizeBeforeUpdate = photosRepository.findAll().size();

        // Create the Photos
        PhotosDTO photosDTO = photosMapper.toDto(photos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhotosMockMvc.perform(put("/api/photos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(photosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Photos in the database
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePhotos() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        int databaseSizeBeforeDelete = photosRepository.findAll().size();

        // Delete the photos
        restPhotosMockMvc.perform(delete("/api/photos/{id}", photos.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

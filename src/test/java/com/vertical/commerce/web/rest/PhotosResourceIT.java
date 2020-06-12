package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.Photos;
import com.vertical.commerce.domain.StockItems;
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

    private static final String DEFAULT_THUMBNAIL_URL = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINAL_URL = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_URL = "BBBBBBBBBB";

    private static final String DEFAULT_BANNER_TALL_URL = "AAAAAAAAAA";
    private static final String UPDATED_BANNER_TALL_URL = "BBBBBBBBBB";

    private static final String DEFAULT_BANNER_WIDE_URL = "AAAAAAAAAA";
    private static final String UPDATED_BANNER_WIDE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CIRCLE_URL = "AAAAAAAAAA";
    private static final String UPDATED_CIRCLE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SHARPENED_URL = "AAAAAAAAAA";
    private static final String UPDATED_SHARPENED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SQUARE_URL = "AAAAAAAAAA";
    private static final String UPDATED_SQUARE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_WATERMARK_URL = "AAAAAAAAAA";
    private static final String UPDATED_WATERMARK_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRIORITY = 1;
    private static final Integer UPDATED_PRIORITY = 2;
    private static final Integer SMALLER_PRIORITY = 1 - 1;

    private static final Boolean DEFAULT_DEFAULT_IND = false;
    private static final Boolean UPDATED_DEFAULT_IND = true;

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
            .thumbnailUrl(DEFAULT_THUMBNAIL_URL)
            .originalUrl(DEFAULT_ORIGINAL_URL)
            .bannerTallUrl(DEFAULT_BANNER_TALL_URL)
            .bannerWideUrl(DEFAULT_BANNER_WIDE_URL)
            .circleUrl(DEFAULT_CIRCLE_URL)
            .sharpenedUrl(DEFAULT_SHARPENED_URL)
            .squareUrl(DEFAULT_SQUARE_URL)
            .watermarkUrl(DEFAULT_WATERMARK_URL)
            .priority(DEFAULT_PRIORITY)
            .defaultInd(DEFAULT_DEFAULT_IND);
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
            .thumbnailUrl(UPDATED_THUMBNAIL_URL)
            .originalUrl(UPDATED_ORIGINAL_URL)
            .bannerTallUrl(UPDATED_BANNER_TALL_URL)
            .bannerWideUrl(UPDATED_BANNER_WIDE_URL)
            .circleUrl(UPDATED_CIRCLE_URL)
            .sharpenedUrl(UPDATED_SHARPENED_URL)
            .squareUrl(UPDATED_SQUARE_URL)
            .watermarkUrl(UPDATED_WATERMARK_URL)
            .priority(UPDATED_PRIORITY)
            .defaultInd(UPDATED_DEFAULT_IND);
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
        assertThat(testPhotos.getThumbnailUrl()).isEqualTo(DEFAULT_THUMBNAIL_URL);
        assertThat(testPhotos.getOriginalUrl()).isEqualTo(DEFAULT_ORIGINAL_URL);
        assertThat(testPhotos.getBannerTallUrl()).isEqualTo(DEFAULT_BANNER_TALL_URL);
        assertThat(testPhotos.getBannerWideUrl()).isEqualTo(DEFAULT_BANNER_WIDE_URL);
        assertThat(testPhotos.getCircleUrl()).isEqualTo(DEFAULT_CIRCLE_URL);
        assertThat(testPhotos.getSharpenedUrl()).isEqualTo(DEFAULT_SHARPENED_URL);
        assertThat(testPhotos.getSquareUrl()).isEqualTo(DEFAULT_SQUARE_URL);
        assertThat(testPhotos.getWatermarkUrl()).isEqualTo(DEFAULT_WATERMARK_URL);
        assertThat(testPhotos.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testPhotos.isDefaultInd()).isEqualTo(DEFAULT_DEFAULT_IND);
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
    public void checkThumbnailUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = photosRepository.findAll().size();
        // set the field null
        photos.setThumbnailUrl(null);

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
    public void checkOriginalUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = photosRepository.findAll().size();
        // set the field null
        photos.setOriginalUrl(null);

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
            .andExpect(jsonPath("$.[*].thumbnailUrl").value(hasItem(DEFAULT_THUMBNAIL_URL)))
            .andExpect(jsonPath("$.[*].originalUrl").value(hasItem(DEFAULT_ORIGINAL_URL)))
            .andExpect(jsonPath("$.[*].bannerTallUrl").value(hasItem(DEFAULT_BANNER_TALL_URL)))
            .andExpect(jsonPath("$.[*].bannerWideUrl").value(hasItem(DEFAULT_BANNER_WIDE_URL)))
            .andExpect(jsonPath("$.[*].circleUrl").value(hasItem(DEFAULT_CIRCLE_URL)))
            .andExpect(jsonPath("$.[*].sharpenedUrl").value(hasItem(DEFAULT_SHARPENED_URL)))
            .andExpect(jsonPath("$.[*].squareUrl").value(hasItem(DEFAULT_SQUARE_URL)))
            .andExpect(jsonPath("$.[*].watermarkUrl").value(hasItem(DEFAULT_WATERMARK_URL)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].defaultInd").value(hasItem(DEFAULT_DEFAULT_IND.booleanValue())));
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
            .andExpect(jsonPath("$.thumbnailUrl").value(DEFAULT_THUMBNAIL_URL))
            .andExpect(jsonPath("$.originalUrl").value(DEFAULT_ORIGINAL_URL))
            .andExpect(jsonPath("$.bannerTallUrl").value(DEFAULT_BANNER_TALL_URL))
            .andExpect(jsonPath("$.bannerWideUrl").value(DEFAULT_BANNER_WIDE_URL))
            .andExpect(jsonPath("$.circleUrl").value(DEFAULT_CIRCLE_URL))
            .andExpect(jsonPath("$.sharpenedUrl").value(DEFAULT_SHARPENED_URL))
            .andExpect(jsonPath("$.squareUrl").value(DEFAULT_SQUARE_URL))
            .andExpect(jsonPath("$.watermarkUrl").value(DEFAULT_WATERMARK_URL))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.defaultInd").value(DEFAULT_DEFAULT_IND.booleanValue()));
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
    public void getAllPhotosByThumbnailUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where thumbnailUrl equals to DEFAULT_THUMBNAIL_URL
        defaultPhotosShouldBeFound("thumbnailUrl.equals=" + DEFAULT_THUMBNAIL_URL);

        // Get all the photosList where thumbnailUrl equals to UPDATED_THUMBNAIL_URL
        defaultPhotosShouldNotBeFound("thumbnailUrl.equals=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByThumbnailUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where thumbnailUrl not equals to DEFAULT_THUMBNAIL_URL
        defaultPhotosShouldNotBeFound("thumbnailUrl.notEquals=" + DEFAULT_THUMBNAIL_URL);

        // Get all the photosList where thumbnailUrl not equals to UPDATED_THUMBNAIL_URL
        defaultPhotosShouldBeFound("thumbnailUrl.notEquals=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByThumbnailUrlIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where thumbnailUrl in DEFAULT_THUMBNAIL_URL or UPDATED_THUMBNAIL_URL
        defaultPhotosShouldBeFound("thumbnailUrl.in=" + DEFAULT_THUMBNAIL_URL + "," + UPDATED_THUMBNAIL_URL);

        // Get all the photosList where thumbnailUrl equals to UPDATED_THUMBNAIL_URL
        defaultPhotosShouldNotBeFound("thumbnailUrl.in=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByThumbnailUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where thumbnailUrl is not null
        defaultPhotosShouldBeFound("thumbnailUrl.specified=true");

        // Get all the photosList where thumbnailUrl is null
        defaultPhotosShouldNotBeFound("thumbnailUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosByThumbnailUrlContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where thumbnailUrl contains DEFAULT_THUMBNAIL_URL
        defaultPhotosShouldBeFound("thumbnailUrl.contains=" + DEFAULT_THUMBNAIL_URL);

        // Get all the photosList where thumbnailUrl contains UPDATED_THUMBNAIL_URL
        defaultPhotosShouldNotBeFound("thumbnailUrl.contains=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByThumbnailUrlNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where thumbnailUrl does not contain DEFAULT_THUMBNAIL_URL
        defaultPhotosShouldNotBeFound("thumbnailUrl.doesNotContain=" + DEFAULT_THUMBNAIL_URL);

        // Get all the photosList where thumbnailUrl does not contain UPDATED_THUMBNAIL_URL
        defaultPhotosShouldBeFound("thumbnailUrl.doesNotContain=" + UPDATED_THUMBNAIL_URL);
    }


    @Test
    @Transactional
    public void getAllPhotosByOriginalUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where originalUrl equals to DEFAULT_ORIGINAL_URL
        defaultPhotosShouldBeFound("originalUrl.equals=" + DEFAULT_ORIGINAL_URL);

        // Get all the photosList where originalUrl equals to UPDATED_ORIGINAL_URL
        defaultPhotosShouldNotBeFound("originalUrl.equals=" + UPDATED_ORIGINAL_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByOriginalUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where originalUrl not equals to DEFAULT_ORIGINAL_URL
        defaultPhotosShouldNotBeFound("originalUrl.notEquals=" + DEFAULT_ORIGINAL_URL);

        // Get all the photosList where originalUrl not equals to UPDATED_ORIGINAL_URL
        defaultPhotosShouldBeFound("originalUrl.notEquals=" + UPDATED_ORIGINAL_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByOriginalUrlIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where originalUrl in DEFAULT_ORIGINAL_URL or UPDATED_ORIGINAL_URL
        defaultPhotosShouldBeFound("originalUrl.in=" + DEFAULT_ORIGINAL_URL + "," + UPDATED_ORIGINAL_URL);

        // Get all the photosList where originalUrl equals to UPDATED_ORIGINAL_URL
        defaultPhotosShouldNotBeFound("originalUrl.in=" + UPDATED_ORIGINAL_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByOriginalUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where originalUrl is not null
        defaultPhotosShouldBeFound("originalUrl.specified=true");

        // Get all the photosList where originalUrl is null
        defaultPhotosShouldNotBeFound("originalUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosByOriginalUrlContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where originalUrl contains DEFAULT_ORIGINAL_URL
        defaultPhotosShouldBeFound("originalUrl.contains=" + DEFAULT_ORIGINAL_URL);

        // Get all the photosList where originalUrl contains UPDATED_ORIGINAL_URL
        defaultPhotosShouldNotBeFound("originalUrl.contains=" + UPDATED_ORIGINAL_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByOriginalUrlNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where originalUrl does not contain DEFAULT_ORIGINAL_URL
        defaultPhotosShouldNotBeFound("originalUrl.doesNotContain=" + DEFAULT_ORIGINAL_URL);

        // Get all the photosList where originalUrl does not contain UPDATED_ORIGINAL_URL
        defaultPhotosShouldBeFound("originalUrl.doesNotContain=" + UPDATED_ORIGINAL_URL);
    }


    @Test
    @Transactional
    public void getAllPhotosByBannerTallUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerTallUrl equals to DEFAULT_BANNER_TALL_URL
        defaultPhotosShouldBeFound("bannerTallUrl.equals=" + DEFAULT_BANNER_TALL_URL);

        // Get all the photosList where bannerTallUrl equals to UPDATED_BANNER_TALL_URL
        defaultPhotosShouldNotBeFound("bannerTallUrl.equals=" + UPDATED_BANNER_TALL_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByBannerTallUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerTallUrl not equals to DEFAULT_BANNER_TALL_URL
        defaultPhotosShouldNotBeFound("bannerTallUrl.notEquals=" + DEFAULT_BANNER_TALL_URL);

        // Get all the photosList where bannerTallUrl not equals to UPDATED_BANNER_TALL_URL
        defaultPhotosShouldBeFound("bannerTallUrl.notEquals=" + UPDATED_BANNER_TALL_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByBannerTallUrlIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerTallUrl in DEFAULT_BANNER_TALL_URL or UPDATED_BANNER_TALL_URL
        defaultPhotosShouldBeFound("bannerTallUrl.in=" + DEFAULT_BANNER_TALL_URL + "," + UPDATED_BANNER_TALL_URL);

        // Get all the photosList where bannerTallUrl equals to UPDATED_BANNER_TALL_URL
        defaultPhotosShouldNotBeFound("bannerTallUrl.in=" + UPDATED_BANNER_TALL_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByBannerTallUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerTallUrl is not null
        defaultPhotosShouldBeFound("bannerTallUrl.specified=true");

        // Get all the photosList where bannerTallUrl is null
        defaultPhotosShouldNotBeFound("bannerTallUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosByBannerTallUrlContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerTallUrl contains DEFAULT_BANNER_TALL_URL
        defaultPhotosShouldBeFound("bannerTallUrl.contains=" + DEFAULT_BANNER_TALL_URL);

        // Get all the photosList where bannerTallUrl contains UPDATED_BANNER_TALL_URL
        defaultPhotosShouldNotBeFound("bannerTallUrl.contains=" + UPDATED_BANNER_TALL_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByBannerTallUrlNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerTallUrl does not contain DEFAULT_BANNER_TALL_URL
        defaultPhotosShouldNotBeFound("bannerTallUrl.doesNotContain=" + DEFAULT_BANNER_TALL_URL);

        // Get all the photosList where bannerTallUrl does not contain UPDATED_BANNER_TALL_URL
        defaultPhotosShouldBeFound("bannerTallUrl.doesNotContain=" + UPDATED_BANNER_TALL_URL);
    }


    @Test
    @Transactional
    public void getAllPhotosByBannerWideUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerWideUrl equals to DEFAULT_BANNER_WIDE_URL
        defaultPhotosShouldBeFound("bannerWideUrl.equals=" + DEFAULT_BANNER_WIDE_URL);

        // Get all the photosList where bannerWideUrl equals to UPDATED_BANNER_WIDE_URL
        defaultPhotosShouldNotBeFound("bannerWideUrl.equals=" + UPDATED_BANNER_WIDE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByBannerWideUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerWideUrl not equals to DEFAULT_BANNER_WIDE_URL
        defaultPhotosShouldNotBeFound("bannerWideUrl.notEquals=" + DEFAULT_BANNER_WIDE_URL);

        // Get all the photosList where bannerWideUrl not equals to UPDATED_BANNER_WIDE_URL
        defaultPhotosShouldBeFound("bannerWideUrl.notEquals=" + UPDATED_BANNER_WIDE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByBannerWideUrlIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerWideUrl in DEFAULT_BANNER_WIDE_URL or UPDATED_BANNER_WIDE_URL
        defaultPhotosShouldBeFound("bannerWideUrl.in=" + DEFAULT_BANNER_WIDE_URL + "," + UPDATED_BANNER_WIDE_URL);

        // Get all the photosList where bannerWideUrl equals to UPDATED_BANNER_WIDE_URL
        defaultPhotosShouldNotBeFound("bannerWideUrl.in=" + UPDATED_BANNER_WIDE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByBannerWideUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerWideUrl is not null
        defaultPhotosShouldBeFound("bannerWideUrl.specified=true");

        // Get all the photosList where bannerWideUrl is null
        defaultPhotosShouldNotBeFound("bannerWideUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosByBannerWideUrlContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerWideUrl contains DEFAULT_BANNER_WIDE_URL
        defaultPhotosShouldBeFound("bannerWideUrl.contains=" + DEFAULT_BANNER_WIDE_URL);

        // Get all the photosList where bannerWideUrl contains UPDATED_BANNER_WIDE_URL
        defaultPhotosShouldNotBeFound("bannerWideUrl.contains=" + UPDATED_BANNER_WIDE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByBannerWideUrlNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerWideUrl does not contain DEFAULT_BANNER_WIDE_URL
        defaultPhotosShouldNotBeFound("bannerWideUrl.doesNotContain=" + DEFAULT_BANNER_WIDE_URL);

        // Get all the photosList where bannerWideUrl does not contain UPDATED_BANNER_WIDE_URL
        defaultPhotosShouldBeFound("bannerWideUrl.doesNotContain=" + UPDATED_BANNER_WIDE_URL);
    }


    @Test
    @Transactional
    public void getAllPhotosByCircleUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where circleUrl equals to DEFAULT_CIRCLE_URL
        defaultPhotosShouldBeFound("circleUrl.equals=" + DEFAULT_CIRCLE_URL);

        // Get all the photosList where circleUrl equals to UPDATED_CIRCLE_URL
        defaultPhotosShouldNotBeFound("circleUrl.equals=" + UPDATED_CIRCLE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByCircleUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where circleUrl not equals to DEFAULT_CIRCLE_URL
        defaultPhotosShouldNotBeFound("circleUrl.notEquals=" + DEFAULT_CIRCLE_URL);

        // Get all the photosList where circleUrl not equals to UPDATED_CIRCLE_URL
        defaultPhotosShouldBeFound("circleUrl.notEquals=" + UPDATED_CIRCLE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByCircleUrlIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where circleUrl in DEFAULT_CIRCLE_URL or UPDATED_CIRCLE_URL
        defaultPhotosShouldBeFound("circleUrl.in=" + DEFAULT_CIRCLE_URL + "," + UPDATED_CIRCLE_URL);

        // Get all the photosList where circleUrl equals to UPDATED_CIRCLE_URL
        defaultPhotosShouldNotBeFound("circleUrl.in=" + UPDATED_CIRCLE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByCircleUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where circleUrl is not null
        defaultPhotosShouldBeFound("circleUrl.specified=true");

        // Get all the photosList where circleUrl is null
        defaultPhotosShouldNotBeFound("circleUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosByCircleUrlContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where circleUrl contains DEFAULT_CIRCLE_URL
        defaultPhotosShouldBeFound("circleUrl.contains=" + DEFAULT_CIRCLE_URL);

        // Get all the photosList where circleUrl contains UPDATED_CIRCLE_URL
        defaultPhotosShouldNotBeFound("circleUrl.contains=" + UPDATED_CIRCLE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByCircleUrlNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where circleUrl does not contain DEFAULT_CIRCLE_URL
        defaultPhotosShouldNotBeFound("circleUrl.doesNotContain=" + DEFAULT_CIRCLE_URL);

        // Get all the photosList where circleUrl does not contain UPDATED_CIRCLE_URL
        defaultPhotosShouldBeFound("circleUrl.doesNotContain=" + UPDATED_CIRCLE_URL);
    }


    @Test
    @Transactional
    public void getAllPhotosBySharpenedUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where sharpenedUrl equals to DEFAULT_SHARPENED_URL
        defaultPhotosShouldBeFound("sharpenedUrl.equals=" + DEFAULT_SHARPENED_URL);

        // Get all the photosList where sharpenedUrl equals to UPDATED_SHARPENED_URL
        defaultPhotosShouldNotBeFound("sharpenedUrl.equals=" + UPDATED_SHARPENED_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosBySharpenedUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where sharpenedUrl not equals to DEFAULT_SHARPENED_URL
        defaultPhotosShouldNotBeFound("sharpenedUrl.notEquals=" + DEFAULT_SHARPENED_URL);

        // Get all the photosList where sharpenedUrl not equals to UPDATED_SHARPENED_URL
        defaultPhotosShouldBeFound("sharpenedUrl.notEquals=" + UPDATED_SHARPENED_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosBySharpenedUrlIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where sharpenedUrl in DEFAULT_SHARPENED_URL or UPDATED_SHARPENED_URL
        defaultPhotosShouldBeFound("sharpenedUrl.in=" + DEFAULT_SHARPENED_URL + "," + UPDATED_SHARPENED_URL);

        // Get all the photosList where sharpenedUrl equals to UPDATED_SHARPENED_URL
        defaultPhotosShouldNotBeFound("sharpenedUrl.in=" + UPDATED_SHARPENED_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosBySharpenedUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where sharpenedUrl is not null
        defaultPhotosShouldBeFound("sharpenedUrl.specified=true");

        // Get all the photosList where sharpenedUrl is null
        defaultPhotosShouldNotBeFound("sharpenedUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosBySharpenedUrlContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where sharpenedUrl contains DEFAULT_SHARPENED_URL
        defaultPhotosShouldBeFound("sharpenedUrl.contains=" + DEFAULT_SHARPENED_URL);

        // Get all the photosList where sharpenedUrl contains UPDATED_SHARPENED_URL
        defaultPhotosShouldNotBeFound("sharpenedUrl.contains=" + UPDATED_SHARPENED_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosBySharpenedUrlNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where sharpenedUrl does not contain DEFAULT_SHARPENED_URL
        defaultPhotosShouldNotBeFound("sharpenedUrl.doesNotContain=" + DEFAULT_SHARPENED_URL);

        // Get all the photosList where sharpenedUrl does not contain UPDATED_SHARPENED_URL
        defaultPhotosShouldBeFound("sharpenedUrl.doesNotContain=" + UPDATED_SHARPENED_URL);
    }


    @Test
    @Transactional
    public void getAllPhotosBySquareUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where squareUrl equals to DEFAULT_SQUARE_URL
        defaultPhotosShouldBeFound("squareUrl.equals=" + DEFAULT_SQUARE_URL);

        // Get all the photosList where squareUrl equals to UPDATED_SQUARE_URL
        defaultPhotosShouldNotBeFound("squareUrl.equals=" + UPDATED_SQUARE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosBySquareUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where squareUrl not equals to DEFAULT_SQUARE_URL
        defaultPhotosShouldNotBeFound("squareUrl.notEquals=" + DEFAULT_SQUARE_URL);

        // Get all the photosList where squareUrl not equals to UPDATED_SQUARE_URL
        defaultPhotosShouldBeFound("squareUrl.notEquals=" + UPDATED_SQUARE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosBySquareUrlIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where squareUrl in DEFAULT_SQUARE_URL or UPDATED_SQUARE_URL
        defaultPhotosShouldBeFound("squareUrl.in=" + DEFAULT_SQUARE_URL + "," + UPDATED_SQUARE_URL);

        // Get all the photosList where squareUrl equals to UPDATED_SQUARE_URL
        defaultPhotosShouldNotBeFound("squareUrl.in=" + UPDATED_SQUARE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosBySquareUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where squareUrl is not null
        defaultPhotosShouldBeFound("squareUrl.specified=true");

        // Get all the photosList where squareUrl is null
        defaultPhotosShouldNotBeFound("squareUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosBySquareUrlContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where squareUrl contains DEFAULT_SQUARE_URL
        defaultPhotosShouldBeFound("squareUrl.contains=" + DEFAULT_SQUARE_URL);

        // Get all the photosList where squareUrl contains UPDATED_SQUARE_URL
        defaultPhotosShouldNotBeFound("squareUrl.contains=" + UPDATED_SQUARE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosBySquareUrlNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where squareUrl does not contain DEFAULT_SQUARE_URL
        defaultPhotosShouldNotBeFound("squareUrl.doesNotContain=" + DEFAULT_SQUARE_URL);

        // Get all the photosList where squareUrl does not contain UPDATED_SQUARE_URL
        defaultPhotosShouldBeFound("squareUrl.doesNotContain=" + UPDATED_SQUARE_URL);
    }


    @Test
    @Transactional
    public void getAllPhotosByWatermarkUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where watermarkUrl equals to DEFAULT_WATERMARK_URL
        defaultPhotosShouldBeFound("watermarkUrl.equals=" + DEFAULT_WATERMARK_URL);

        // Get all the photosList where watermarkUrl equals to UPDATED_WATERMARK_URL
        defaultPhotosShouldNotBeFound("watermarkUrl.equals=" + UPDATED_WATERMARK_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByWatermarkUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where watermarkUrl not equals to DEFAULT_WATERMARK_URL
        defaultPhotosShouldNotBeFound("watermarkUrl.notEquals=" + DEFAULT_WATERMARK_URL);

        // Get all the photosList where watermarkUrl not equals to UPDATED_WATERMARK_URL
        defaultPhotosShouldBeFound("watermarkUrl.notEquals=" + UPDATED_WATERMARK_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByWatermarkUrlIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where watermarkUrl in DEFAULT_WATERMARK_URL or UPDATED_WATERMARK_URL
        defaultPhotosShouldBeFound("watermarkUrl.in=" + DEFAULT_WATERMARK_URL + "," + UPDATED_WATERMARK_URL);

        // Get all the photosList where watermarkUrl equals to UPDATED_WATERMARK_URL
        defaultPhotosShouldNotBeFound("watermarkUrl.in=" + UPDATED_WATERMARK_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByWatermarkUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where watermarkUrl is not null
        defaultPhotosShouldBeFound("watermarkUrl.specified=true");

        // Get all the photosList where watermarkUrl is null
        defaultPhotosShouldNotBeFound("watermarkUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosByWatermarkUrlContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where watermarkUrl contains DEFAULT_WATERMARK_URL
        defaultPhotosShouldBeFound("watermarkUrl.contains=" + DEFAULT_WATERMARK_URL);

        // Get all the photosList where watermarkUrl contains UPDATED_WATERMARK_URL
        defaultPhotosShouldNotBeFound("watermarkUrl.contains=" + UPDATED_WATERMARK_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByWatermarkUrlNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where watermarkUrl does not contain DEFAULT_WATERMARK_URL
        defaultPhotosShouldNotBeFound("watermarkUrl.doesNotContain=" + DEFAULT_WATERMARK_URL);

        // Get all the photosList where watermarkUrl does not contain UPDATED_WATERMARK_URL
        defaultPhotosShouldBeFound("watermarkUrl.doesNotContain=" + UPDATED_WATERMARK_URL);
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

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPhotosShouldBeFound(String filter) throws Exception {
        restPhotosMockMvc.perform(get("/api/photos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(photos.getId().intValue())))
            .andExpect(jsonPath("$.[*].blobId").value(hasItem(DEFAULT_BLOB_ID)))
            .andExpect(jsonPath("$.[*].thumbnailUrl").value(hasItem(DEFAULT_THUMBNAIL_URL)))
            .andExpect(jsonPath("$.[*].originalUrl").value(hasItem(DEFAULT_ORIGINAL_URL)))
            .andExpect(jsonPath("$.[*].bannerTallUrl").value(hasItem(DEFAULT_BANNER_TALL_URL)))
            .andExpect(jsonPath("$.[*].bannerWideUrl").value(hasItem(DEFAULT_BANNER_WIDE_URL)))
            .andExpect(jsonPath("$.[*].circleUrl").value(hasItem(DEFAULT_CIRCLE_URL)))
            .andExpect(jsonPath("$.[*].sharpenedUrl").value(hasItem(DEFAULT_SHARPENED_URL)))
            .andExpect(jsonPath("$.[*].squareUrl").value(hasItem(DEFAULT_SQUARE_URL)))
            .andExpect(jsonPath("$.[*].watermarkUrl").value(hasItem(DEFAULT_WATERMARK_URL)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].defaultInd").value(hasItem(DEFAULT_DEFAULT_IND.booleanValue())));

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
            .thumbnailUrl(UPDATED_THUMBNAIL_URL)
            .originalUrl(UPDATED_ORIGINAL_URL)
            .bannerTallUrl(UPDATED_BANNER_TALL_URL)
            .bannerWideUrl(UPDATED_BANNER_WIDE_URL)
            .circleUrl(UPDATED_CIRCLE_URL)
            .sharpenedUrl(UPDATED_SHARPENED_URL)
            .squareUrl(UPDATED_SQUARE_URL)
            .watermarkUrl(UPDATED_WATERMARK_URL)
            .priority(UPDATED_PRIORITY)
            .defaultInd(UPDATED_DEFAULT_IND);
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
        assertThat(testPhotos.getThumbnailUrl()).isEqualTo(UPDATED_THUMBNAIL_URL);
        assertThat(testPhotos.getOriginalUrl()).isEqualTo(UPDATED_ORIGINAL_URL);
        assertThat(testPhotos.getBannerTallUrl()).isEqualTo(UPDATED_BANNER_TALL_URL);
        assertThat(testPhotos.getBannerWideUrl()).isEqualTo(UPDATED_BANNER_WIDE_URL);
        assertThat(testPhotos.getCircleUrl()).isEqualTo(UPDATED_CIRCLE_URL);
        assertThat(testPhotos.getSharpenedUrl()).isEqualTo(UPDATED_SHARPENED_URL);
        assertThat(testPhotos.getSquareUrl()).isEqualTo(UPDATED_SQUARE_URL);
        assertThat(testPhotos.getWatermarkUrl()).isEqualTo(UPDATED_WATERMARK_URL);
        assertThat(testPhotos.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testPhotos.isDefaultInd()).isEqualTo(UPDATED_DEFAULT_IND);
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

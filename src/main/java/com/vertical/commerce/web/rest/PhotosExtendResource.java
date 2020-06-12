package com.vertical.commerce.web.rest;

import com.vertical.commerce.repository.PhotosExtendRepository;
import com.vertical.commerce.service.PhotosExtendService;
import com.vertical.commerce.service.dto.PhotosDTO;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * PhotosExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class PhotosExtendResource {

    private final Logger log = LoggerFactory.getLogger(PhotosExtendResource.class);
    private final PhotosExtendService photosExtendService;
    private static final String ENTITY_NAME = "photos-extend";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public PhotosExtendResource(PhotosExtendService photosExtendService) {
        this.photosExtendService = photosExtendService;
    }

    @PostMapping("/photos-extend")
    public ResponseEntity<PhotosDTO> createPhotos(@RequestBody PhotosDTO photosDTO) throws URISyntaxException {
        log.debug("REST request to save Photos : {}", photosDTO);
        if (photosDTO.getId() != null) {
            throw new BadRequestAlertException("A new photos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhotosDTO result = photosExtendService.save(photosDTO);
        return ResponseEntity.created(new URI("/api/photos-extend/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/photos-extend")
    public ResponseEntity<PhotosDTO> updatePhotos(@RequestBody PhotosDTO photosDTO) throws URISyntaxException {
        log.debug("REST request to update Photos : {}", photosDTO);
        if (photosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PhotosDTO result = photosExtendService.save(photosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, photosDTO.getId().toString()))
            .body(result);
    }

    @Transactional
    @PutMapping("/photos-extend/default")
    public ResponseEntity<PhotosDTO> setDefault(@RequestBody PhotosDTO photosDTO) throws URISyntaxException {
        log.debug("REST request to update Photos : {}", photosDTO);
        if (photosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PhotosDTO result = photosExtendService.setDefault(photosDTO.getId()).get();
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, photosDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/photos-extend/blob/{id}")
    public ResponseEntity<Void> deletePhotos(@PathVariable String id) {
        log.debug("REST request to delete Photos : {}", id);
        photosExtendService.deleteByBlobId(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

}

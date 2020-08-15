package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.PostalCodeMappersLocalizeService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.PostalCodeMappersLocalizeDTO;
import com.vertical.commerce.service.dto.PostalCodeMappersLocalizeCriteria;
import com.vertical.commerce.service.PostalCodeMappersLocalizeQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vertical.commerce.domain.PostalCodeMappersLocalize}.
 */
@RestController
@RequestMapping("/api")
public class PostalCodeMappersLocalizeResource {

    private final Logger log = LoggerFactory.getLogger(PostalCodeMappersLocalizeResource.class);

    private static final String ENTITY_NAME = "vscommercePostalCodeMappersLocalize";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostalCodeMappersLocalizeService postalCodeMappersLocalizeService;

    private final PostalCodeMappersLocalizeQueryService postalCodeMappersLocalizeQueryService;

    public PostalCodeMappersLocalizeResource(PostalCodeMappersLocalizeService postalCodeMappersLocalizeService, PostalCodeMappersLocalizeQueryService postalCodeMappersLocalizeQueryService) {
        this.postalCodeMappersLocalizeService = postalCodeMappersLocalizeService;
        this.postalCodeMappersLocalizeQueryService = postalCodeMappersLocalizeQueryService;
    }

    /**
     * {@code POST  /postal-code-mappers-localizes} : Create a new postalCodeMappersLocalize.
     *
     * @param postalCodeMappersLocalizeDTO the postalCodeMappersLocalizeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postalCodeMappersLocalizeDTO, or with status {@code 400 (Bad Request)} if the postalCodeMappersLocalize has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/postal-code-mappers-localizes")
    public ResponseEntity<PostalCodeMappersLocalizeDTO> createPostalCodeMappersLocalize(@Valid @RequestBody PostalCodeMappersLocalizeDTO postalCodeMappersLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to save PostalCodeMappersLocalize : {}", postalCodeMappersLocalizeDTO);
        if (postalCodeMappersLocalizeDTO.getId() != null) {
            throw new BadRequestAlertException("A new postalCodeMappersLocalize cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostalCodeMappersLocalizeDTO result = postalCodeMappersLocalizeService.save(postalCodeMappersLocalizeDTO);
        return ResponseEntity.created(new URI("/api/postal-code-mappers-localizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /postal-code-mappers-localizes} : Updates an existing postalCodeMappersLocalize.
     *
     * @param postalCodeMappersLocalizeDTO the postalCodeMappersLocalizeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postalCodeMappersLocalizeDTO,
     * or with status {@code 400 (Bad Request)} if the postalCodeMappersLocalizeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postalCodeMappersLocalizeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/postal-code-mappers-localizes")
    public ResponseEntity<PostalCodeMappersLocalizeDTO> updatePostalCodeMappersLocalize(@Valid @RequestBody PostalCodeMappersLocalizeDTO postalCodeMappersLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to update PostalCodeMappersLocalize : {}", postalCodeMappersLocalizeDTO);
        if (postalCodeMappersLocalizeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PostalCodeMappersLocalizeDTO result = postalCodeMappersLocalizeService.save(postalCodeMappersLocalizeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, postalCodeMappersLocalizeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /postal-code-mappers-localizes} : get all the postalCodeMappersLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postalCodeMappersLocalizes in body.
     */
    @GetMapping("/postal-code-mappers-localizes")
    public ResponseEntity<List<PostalCodeMappersLocalizeDTO>> getAllPostalCodeMappersLocalizes(PostalCodeMappersLocalizeCriteria criteria) {
        log.debug("REST request to get PostalCodeMappersLocalizes by criteria: {}", criteria);
        List<PostalCodeMappersLocalizeDTO> entityList = postalCodeMappersLocalizeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /postal-code-mappers-localizes/count} : count all the postalCodeMappersLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/postal-code-mappers-localizes/count")
    public ResponseEntity<Long> countPostalCodeMappersLocalizes(PostalCodeMappersLocalizeCriteria criteria) {
        log.debug("REST request to count PostalCodeMappersLocalizes by criteria: {}", criteria);
        return ResponseEntity.ok().body(postalCodeMappersLocalizeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /postal-code-mappers-localizes/:id} : get the "id" postalCodeMappersLocalize.
     *
     * @param id the id of the postalCodeMappersLocalizeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postalCodeMappersLocalizeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/postal-code-mappers-localizes/{id}")
    public ResponseEntity<PostalCodeMappersLocalizeDTO> getPostalCodeMappersLocalize(@PathVariable Long id) {
        log.debug("REST request to get PostalCodeMappersLocalize : {}", id);
        Optional<PostalCodeMappersLocalizeDTO> postalCodeMappersLocalizeDTO = postalCodeMappersLocalizeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postalCodeMappersLocalizeDTO);
    }

    /**
     * {@code DELETE  /postal-code-mappers-localizes/:id} : delete the "id" postalCodeMappersLocalize.
     *
     * @param id the id of the postalCodeMappersLocalizeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/postal-code-mappers-localizes/{id}")
    public ResponseEntity<Void> deletePostalCodeMappersLocalize(@PathVariable Long id) {
        log.debug("REST request to delete PostalCodeMappersLocalize : {}", id);

        postalCodeMappersLocalizeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

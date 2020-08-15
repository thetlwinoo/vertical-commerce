package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.PostalCodeMappersService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.PostalCodeMappersDTO;
import com.vertical.commerce.service.dto.PostalCodeMappersCriteria;
import com.vertical.commerce.service.PostalCodeMappersQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.PostalCodeMappers}.
 */
@RestController
@RequestMapping("/api")
public class PostalCodeMappersResource {

    private final Logger log = LoggerFactory.getLogger(PostalCodeMappersResource.class);

    private static final String ENTITY_NAME = "vscommercePostalCodeMappers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostalCodeMappersService postalCodeMappersService;

    private final PostalCodeMappersQueryService postalCodeMappersQueryService;

    public PostalCodeMappersResource(PostalCodeMappersService postalCodeMappersService, PostalCodeMappersQueryService postalCodeMappersQueryService) {
        this.postalCodeMappersService = postalCodeMappersService;
        this.postalCodeMappersQueryService = postalCodeMappersQueryService;
    }

    /**
     * {@code POST  /postal-code-mappers} : Create a new postalCodeMappers.
     *
     * @param postalCodeMappersDTO the postalCodeMappersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postalCodeMappersDTO, or with status {@code 400 (Bad Request)} if the postalCodeMappers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/postal-code-mappers")
    public ResponseEntity<PostalCodeMappersDTO> createPostalCodeMappers(@Valid @RequestBody PostalCodeMappersDTO postalCodeMappersDTO) throws URISyntaxException {
        log.debug("REST request to save PostalCodeMappers : {}", postalCodeMappersDTO);
        if (postalCodeMappersDTO.getId() != null) {
            throw new BadRequestAlertException("A new postalCodeMappers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostalCodeMappersDTO result = postalCodeMappersService.save(postalCodeMappersDTO);
        return ResponseEntity.created(new URI("/api/postal-code-mappers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /postal-code-mappers} : Updates an existing postalCodeMappers.
     *
     * @param postalCodeMappersDTO the postalCodeMappersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postalCodeMappersDTO,
     * or with status {@code 400 (Bad Request)} if the postalCodeMappersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postalCodeMappersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/postal-code-mappers")
    public ResponseEntity<PostalCodeMappersDTO> updatePostalCodeMappers(@Valid @RequestBody PostalCodeMappersDTO postalCodeMappersDTO) throws URISyntaxException {
        log.debug("REST request to update PostalCodeMappers : {}", postalCodeMappersDTO);
        if (postalCodeMappersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PostalCodeMappersDTO result = postalCodeMappersService.save(postalCodeMappersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, postalCodeMappersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /postal-code-mappers} : get all the postalCodeMappers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postalCodeMappers in body.
     */
    @GetMapping("/postal-code-mappers")
    public ResponseEntity<List<PostalCodeMappersDTO>> getAllPostalCodeMappers(PostalCodeMappersCriteria criteria) {
        log.debug("REST request to get PostalCodeMappers by criteria: {}", criteria);
        List<PostalCodeMappersDTO> entityList = postalCodeMappersQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /postal-code-mappers/count} : count all the postalCodeMappers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/postal-code-mappers/count")
    public ResponseEntity<Long> countPostalCodeMappers(PostalCodeMappersCriteria criteria) {
        log.debug("REST request to count PostalCodeMappers by criteria: {}", criteria);
        return ResponseEntity.ok().body(postalCodeMappersQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /postal-code-mappers/:id} : get the "id" postalCodeMappers.
     *
     * @param id the id of the postalCodeMappersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postalCodeMappersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/postal-code-mappers/{id}")
    public ResponseEntity<PostalCodeMappersDTO> getPostalCodeMappers(@PathVariable Long id) {
        log.debug("REST request to get PostalCodeMappers : {}", id);
        Optional<PostalCodeMappersDTO> postalCodeMappersDTO = postalCodeMappersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postalCodeMappersDTO);
    }

    /**
     * {@code DELETE  /postal-code-mappers/:id} : delete the "id" postalCodeMappers.
     *
     * @param id the id of the postalCodeMappersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/postal-code-mappers/{id}")
    public ResponseEntity<Void> deletePostalCodeMappers(@PathVariable Long id) {
        log.debug("REST request to delete PostalCodeMappers : {}", id);

        postalCodeMappersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

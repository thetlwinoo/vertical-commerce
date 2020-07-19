package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.CitiesCultureService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.CitiesCultureDTO;
import com.vertical.commerce.service.dto.CitiesCultureCriteria;
import com.vertical.commerce.service.CitiesCultureQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.CitiesCulture}.
 */
@RestController
@RequestMapping("/api")
public class CitiesCultureResource {

    private final Logger log = LoggerFactory.getLogger(CitiesCultureResource.class);

    private static final String ENTITY_NAME = "vscommerceCitiesCulture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CitiesCultureService citiesCultureService;

    private final CitiesCultureQueryService citiesCultureQueryService;

    public CitiesCultureResource(CitiesCultureService citiesCultureService, CitiesCultureQueryService citiesCultureQueryService) {
        this.citiesCultureService = citiesCultureService;
        this.citiesCultureQueryService = citiesCultureQueryService;
    }

    /**
     * {@code POST  /cities-cultures} : Create a new citiesCulture.
     *
     * @param citiesCultureDTO the citiesCultureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new citiesCultureDTO, or with status {@code 400 (Bad Request)} if the citiesCulture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cities-cultures")
    public ResponseEntity<CitiesCultureDTO> createCitiesCulture(@Valid @RequestBody CitiesCultureDTO citiesCultureDTO) throws URISyntaxException {
        log.debug("REST request to save CitiesCulture : {}", citiesCultureDTO);
        if (citiesCultureDTO.getId() != null) {
            throw new BadRequestAlertException("A new citiesCulture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CitiesCultureDTO result = citiesCultureService.save(citiesCultureDTO);
        return ResponseEntity.created(new URI("/api/cities-cultures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cities-cultures} : Updates an existing citiesCulture.
     *
     * @param citiesCultureDTO the citiesCultureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated citiesCultureDTO,
     * or with status {@code 400 (Bad Request)} if the citiesCultureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the citiesCultureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cities-cultures")
    public ResponseEntity<CitiesCultureDTO> updateCitiesCulture(@Valid @RequestBody CitiesCultureDTO citiesCultureDTO) throws URISyntaxException {
        log.debug("REST request to update CitiesCulture : {}", citiesCultureDTO);
        if (citiesCultureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CitiesCultureDTO result = citiesCultureService.save(citiesCultureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, citiesCultureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cities-cultures} : get all the citiesCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of citiesCultures in body.
     */
    @GetMapping("/cities-cultures")
    public ResponseEntity<List<CitiesCultureDTO>> getAllCitiesCultures(CitiesCultureCriteria criteria) {
        log.debug("REST request to get CitiesCultures by criteria: {}", criteria);
        List<CitiesCultureDTO> entityList = citiesCultureQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /cities-cultures/count} : count all the citiesCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cities-cultures/count")
    public ResponseEntity<Long> countCitiesCultures(CitiesCultureCriteria criteria) {
        log.debug("REST request to count CitiesCultures by criteria: {}", criteria);
        return ResponseEntity.ok().body(citiesCultureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cities-cultures/:id} : get the "id" citiesCulture.
     *
     * @param id the id of the citiesCultureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the citiesCultureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cities-cultures/{id}")
    public ResponseEntity<CitiesCultureDTO> getCitiesCulture(@PathVariable Long id) {
        log.debug("REST request to get CitiesCulture : {}", id);
        Optional<CitiesCultureDTO> citiesCultureDTO = citiesCultureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(citiesCultureDTO);
    }

    /**
     * {@code DELETE  /cities-cultures/:id} : delete the "id" citiesCulture.
     *
     * @param id the id of the citiesCultureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cities-cultures/{id}")
    public ResponseEntity<Void> deleteCitiesCulture(@PathVariable Long id) {
        log.debug("REST request to delete CitiesCulture : {}", id);

        citiesCultureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

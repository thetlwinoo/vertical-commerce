package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.CountriesCultureService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.CountriesCultureDTO;
import com.vertical.commerce.service.dto.CountriesCultureCriteria;
import com.vertical.commerce.service.CountriesCultureQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.CountriesCulture}.
 */
@RestController
@RequestMapping("/api")
public class CountriesCultureResource {

    private final Logger log = LoggerFactory.getLogger(CountriesCultureResource.class);

    private static final String ENTITY_NAME = "vscommerceCountriesCulture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CountriesCultureService countriesCultureService;

    private final CountriesCultureQueryService countriesCultureQueryService;

    public CountriesCultureResource(CountriesCultureService countriesCultureService, CountriesCultureQueryService countriesCultureQueryService) {
        this.countriesCultureService = countriesCultureService;
        this.countriesCultureQueryService = countriesCultureQueryService;
    }

    /**
     * {@code POST  /countries-cultures} : Create a new countriesCulture.
     *
     * @param countriesCultureDTO the countriesCultureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new countriesCultureDTO, or with status {@code 400 (Bad Request)} if the countriesCulture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/countries-cultures")
    public ResponseEntity<CountriesCultureDTO> createCountriesCulture(@Valid @RequestBody CountriesCultureDTO countriesCultureDTO) throws URISyntaxException {
        log.debug("REST request to save CountriesCulture : {}", countriesCultureDTO);
        if (countriesCultureDTO.getId() != null) {
            throw new BadRequestAlertException("A new countriesCulture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CountriesCultureDTO result = countriesCultureService.save(countriesCultureDTO);
        return ResponseEntity.created(new URI("/api/countries-cultures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /countries-cultures} : Updates an existing countriesCulture.
     *
     * @param countriesCultureDTO the countriesCultureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated countriesCultureDTO,
     * or with status {@code 400 (Bad Request)} if the countriesCultureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the countriesCultureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/countries-cultures")
    public ResponseEntity<CountriesCultureDTO> updateCountriesCulture(@Valid @RequestBody CountriesCultureDTO countriesCultureDTO) throws URISyntaxException {
        log.debug("REST request to update CountriesCulture : {}", countriesCultureDTO);
        if (countriesCultureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CountriesCultureDTO result = countriesCultureService.save(countriesCultureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, countriesCultureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /countries-cultures} : get all the countriesCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of countriesCultures in body.
     */
    @GetMapping("/countries-cultures")
    public ResponseEntity<List<CountriesCultureDTO>> getAllCountriesCultures(CountriesCultureCriteria criteria) {
        log.debug("REST request to get CountriesCultures by criteria: {}", criteria);
        List<CountriesCultureDTO> entityList = countriesCultureQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /countries-cultures/count} : count all the countriesCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/countries-cultures/count")
    public ResponseEntity<Long> countCountriesCultures(CountriesCultureCriteria criteria) {
        log.debug("REST request to count CountriesCultures by criteria: {}", criteria);
        return ResponseEntity.ok().body(countriesCultureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /countries-cultures/:id} : get the "id" countriesCulture.
     *
     * @param id the id of the countriesCultureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the countriesCultureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/countries-cultures/{id}")
    public ResponseEntity<CountriesCultureDTO> getCountriesCulture(@PathVariable Long id) {
        log.debug("REST request to get CountriesCulture : {}", id);
        Optional<CountriesCultureDTO> countriesCultureDTO = countriesCultureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(countriesCultureDTO);
    }

    /**
     * {@code DELETE  /countries-cultures/:id} : delete the "id" countriesCulture.
     *
     * @param id the id of the countriesCultureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/countries-cultures/{id}")
    public ResponseEntity<Void> deleteCountriesCulture(@PathVariable Long id) {
        log.debug("REST request to delete CountriesCulture : {}", id);

        countriesCultureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.CountriesLocalizeService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.CountriesLocalizeDTO;
import com.vertical.commerce.service.dto.CountriesLocalizeCriteria;
import com.vertical.commerce.service.CountriesLocalizeQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.CountriesLocalize}.
 */
@RestController
@RequestMapping("/api")
public class CountriesLocalizeResource {

    private final Logger log = LoggerFactory.getLogger(CountriesLocalizeResource.class);

    private static final String ENTITY_NAME = "vscommerceCountriesLocalize";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CountriesLocalizeService countriesLocalizeService;

    private final CountriesLocalizeQueryService countriesLocalizeQueryService;

    public CountriesLocalizeResource(CountriesLocalizeService countriesLocalizeService, CountriesLocalizeQueryService countriesLocalizeQueryService) {
        this.countriesLocalizeService = countriesLocalizeService;
        this.countriesLocalizeQueryService = countriesLocalizeQueryService;
    }

    /**
     * {@code POST  /countries-localizes} : Create a new countriesLocalize.
     *
     * @param countriesLocalizeDTO the countriesLocalizeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new countriesLocalizeDTO, or with status {@code 400 (Bad Request)} if the countriesLocalize has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/countries-localizes")
    public ResponseEntity<CountriesLocalizeDTO> createCountriesLocalize(@Valid @RequestBody CountriesLocalizeDTO countriesLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to save CountriesLocalize : {}", countriesLocalizeDTO);
        if (countriesLocalizeDTO.getId() != null) {
            throw new BadRequestAlertException("A new countriesLocalize cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CountriesLocalizeDTO result = countriesLocalizeService.save(countriesLocalizeDTO);
        return ResponseEntity.created(new URI("/api/countries-localizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /countries-localizes} : Updates an existing countriesLocalize.
     *
     * @param countriesLocalizeDTO the countriesLocalizeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated countriesLocalizeDTO,
     * or with status {@code 400 (Bad Request)} if the countriesLocalizeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the countriesLocalizeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/countries-localizes")
    public ResponseEntity<CountriesLocalizeDTO> updateCountriesLocalize(@Valid @RequestBody CountriesLocalizeDTO countriesLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to update CountriesLocalize : {}", countriesLocalizeDTO);
        if (countriesLocalizeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CountriesLocalizeDTO result = countriesLocalizeService.save(countriesLocalizeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, countriesLocalizeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /countries-localizes} : get all the countriesLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of countriesLocalizes in body.
     */
    @GetMapping("/countries-localizes")
    public ResponseEntity<List<CountriesLocalizeDTO>> getAllCountriesLocalizes(CountriesLocalizeCriteria criteria) {
        log.debug("REST request to get CountriesLocalizes by criteria: {}", criteria);
        List<CountriesLocalizeDTO> entityList = countriesLocalizeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /countries-localizes/count} : count all the countriesLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/countries-localizes/count")
    public ResponseEntity<Long> countCountriesLocalizes(CountriesLocalizeCriteria criteria) {
        log.debug("REST request to count CountriesLocalizes by criteria: {}", criteria);
        return ResponseEntity.ok().body(countriesLocalizeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /countries-localizes/:id} : get the "id" countriesLocalize.
     *
     * @param id the id of the countriesLocalizeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the countriesLocalizeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/countries-localizes/{id}")
    public ResponseEntity<CountriesLocalizeDTO> getCountriesLocalize(@PathVariable Long id) {
        log.debug("REST request to get CountriesLocalize : {}", id);
        Optional<CountriesLocalizeDTO> countriesLocalizeDTO = countriesLocalizeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(countriesLocalizeDTO);
    }

    /**
     * {@code DELETE  /countries-localizes/:id} : delete the "id" countriesLocalize.
     *
     * @param id the id of the countriesLocalizeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/countries-localizes/{id}")
    public ResponseEntity<Void> deleteCountriesLocalize(@PathVariable Long id) {
        log.debug("REST request to delete CountriesLocalize : {}", id);

        countriesLocalizeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.TaxClassService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.TaxClassDTO;
import com.vertical.commerce.service.dto.TaxClassCriteria;
import com.vertical.commerce.service.TaxClassQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vertical.commerce.domain.TaxClass}.
 */
@RestController
@RequestMapping("/api")
public class TaxClassResource {

    private final Logger log = LoggerFactory.getLogger(TaxClassResource.class);

    private static final String ENTITY_NAME = "vscommerceTaxClass";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaxClassService taxClassService;

    private final TaxClassQueryService taxClassQueryService;

    public TaxClassResource(TaxClassService taxClassService, TaxClassQueryService taxClassQueryService) {
        this.taxClassService = taxClassService;
        this.taxClassQueryService = taxClassQueryService;
    }

    /**
     * {@code POST  /tax-classes} : Create a new taxClass.
     *
     * @param taxClassDTO the taxClassDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taxClassDTO, or with status {@code 400 (Bad Request)} if the taxClass has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tax-classes")
    public ResponseEntity<TaxClassDTO> createTaxClass(@RequestBody TaxClassDTO taxClassDTO) throws URISyntaxException {
        log.debug("REST request to save TaxClass : {}", taxClassDTO);
        if (taxClassDTO.getId() != null) {
            throw new BadRequestAlertException("A new taxClass cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaxClassDTO result = taxClassService.save(taxClassDTO);
        return ResponseEntity.created(new URI("/api/tax-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tax-classes} : Updates an existing taxClass.
     *
     * @param taxClassDTO the taxClassDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxClassDTO,
     * or with status {@code 400 (Bad Request)} if the taxClassDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taxClassDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tax-classes")
    public ResponseEntity<TaxClassDTO> updateTaxClass(@RequestBody TaxClassDTO taxClassDTO) throws URISyntaxException {
        log.debug("REST request to update TaxClass : {}", taxClassDTO);
        if (taxClassDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaxClassDTO result = taxClassService.save(taxClassDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taxClassDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tax-classes} : get all the taxClasses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taxClasses in body.
     */
    @GetMapping("/tax-classes")
    public ResponseEntity<List<TaxClassDTO>> getAllTaxClasses(TaxClassCriteria criteria) {
        log.debug("REST request to get TaxClasses by criteria: {}", criteria);
        List<TaxClassDTO> entityList = taxClassQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /tax-classes/count} : count all the taxClasses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tax-classes/count")
    public ResponseEntity<Long> countTaxClasses(TaxClassCriteria criteria) {
        log.debug("REST request to count TaxClasses by criteria: {}", criteria);
        return ResponseEntity.ok().body(taxClassQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tax-classes/:id} : get the "id" taxClass.
     *
     * @param id the id of the taxClassDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taxClassDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tax-classes/{id}")
    public ResponseEntity<TaxClassDTO> getTaxClass(@PathVariable Long id) {
        log.debug("REST request to get TaxClass : {}", id);
        Optional<TaxClassDTO> taxClassDTO = taxClassService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taxClassDTO);
    }

    /**
     * {@code DELETE  /tax-classes/:id} : delete the "id" taxClass.
     *
     * @param id the id of the taxClassDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tax-classes/{id}")
    public ResponseEntity<Void> deleteTaxClass(@PathVariable Long id) {
        log.debug("REST request to delete TaxClass : {}", id);

        taxClassService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

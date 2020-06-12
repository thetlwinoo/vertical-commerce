package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.CompareLinesService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.CompareLinesDTO;
import com.vertical.commerce.service.dto.CompareLinesCriteria;
import com.vertical.commerce.service.CompareLinesQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.CompareLines}.
 */
@RestController
@RequestMapping("/api")
public class CompareLinesResource {

    private final Logger log = LoggerFactory.getLogger(CompareLinesResource.class);

    private static final String ENTITY_NAME = "vscommerceCompareLines";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompareLinesService compareLinesService;

    private final CompareLinesQueryService compareLinesQueryService;

    public CompareLinesResource(CompareLinesService compareLinesService, CompareLinesQueryService compareLinesQueryService) {
        this.compareLinesService = compareLinesService;
        this.compareLinesQueryService = compareLinesQueryService;
    }

    /**
     * {@code POST  /compare-lines} : Create a new compareLines.
     *
     * @param compareLinesDTO the compareLinesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new compareLinesDTO, or with status {@code 400 (Bad Request)} if the compareLines has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/compare-lines")
    public ResponseEntity<CompareLinesDTO> createCompareLines(@RequestBody CompareLinesDTO compareLinesDTO) throws URISyntaxException {
        log.debug("REST request to save CompareLines : {}", compareLinesDTO);
        if (compareLinesDTO.getId() != null) {
            throw new BadRequestAlertException("A new compareLines cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompareLinesDTO result = compareLinesService.save(compareLinesDTO);
        return ResponseEntity.created(new URI("/api/compare-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /compare-lines} : Updates an existing compareLines.
     *
     * @param compareLinesDTO the compareLinesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compareLinesDTO,
     * or with status {@code 400 (Bad Request)} if the compareLinesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the compareLinesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/compare-lines")
    public ResponseEntity<CompareLinesDTO> updateCompareLines(@RequestBody CompareLinesDTO compareLinesDTO) throws URISyntaxException {
        log.debug("REST request to update CompareLines : {}", compareLinesDTO);
        if (compareLinesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompareLinesDTO result = compareLinesService.save(compareLinesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compareLinesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /compare-lines} : get all the compareLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of compareLines in body.
     */
    @GetMapping("/compare-lines")
    public ResponseEntity<List<CompareLinesDTO>> getAllCompareLines(CompareLinesCriteria criteria) {
        log.debug("REST request to get CompareLines by criteria: {}", criteria);
        List<CompareLinesDTO> entityList = compareLinesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /compare-lines/count} : count all the compareLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/compare-lines/count")
    public ResponseEntity<Long> countCompareLines(CompareLinesCriteria criteria) {
        log.debug("REST request to count CompareLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(compareLinesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /compare-lines/:id} : get the "id" compareLines.
     *
     * @param id the id of the compareLinesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compareLinesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compare-lines/{id}")
    public ResponseEntity<CompareLinesDTO> getCompareLines(@PathVariable Long id) {
        log.debug("REST request to get CompareLines : {}", id);
        Optional<CompareLinesDTO> compareLinesDTO = compareLinesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(compareLinesDTO);
    }

    /**
     * {@code DELETE  /compare-lines/:id} : delete the "id" compareLines.
     *
     * @param id the id of the compareLinesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compare-lines/{id}")
    public ResponseEntity<Void> deleteCompareLines(@PathVariable Long id) {
        log.debug("REST request to delete CompareLines : {}", id);

        compareLinesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

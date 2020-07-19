package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.TownsService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.TownsDTO;
import com.vertical.commerce.service.dto.TownsCriteria;
import com.vertical.commerce.service.TownsQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.Towns}.
 */
@RestController
@RequestMapping("/api")
public class TownsResource {

    private final Logger log = LoggerFactory.getLogger(TownsResource.class);

    private static final String ENTITY_NAME = "vscommerceTowns";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TownsService townsService;

    private final TownsQueryService townsQueryService;

    public TownsResource(TownsService townsService, TownsQueryService townsQueryService) {
        this.townsService = townsService;
        this.townsQueryService = townsQueryService;
    }

    /**
     * {@code POST  /towns} : Create a new towns.
     *
     * @param townsDTO the townsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new townsDTO, or with status {@code 400 (Bad Request)} if the towns has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/towns")
    public ResponseEntity<TownsDTO> createTowns(@Valid @RequestBody TownsDTO townsDTO) throws URISyntaxException {
        log.debug("REST request to save Towns : {}", townsDTO);
        if (townsDTO.getId() != null) {
            throw new BadRequestAlertException("A new towns cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TownsDTO result = townsService.save(townsDTO);
        return ResponseEntity.created(new URI("/api/towns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /towns} : Updates an existing towns.
     *
     * @param townsDTO the townsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated townsDTO,
     * or with status {@code 400 (Bad Request)} if the townsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the townsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/towns")
    public ResponseEntity<TownsDTO> updateTowns(@Valid @RequestBody TownsDTO townsDTO) throws URISyntaxException {
        log.debug("REST request to update Towns : {}", townsDTO);
        if (townsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TownsDTO result = townsService.save(townsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, townsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /towns} : get all the towns.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of towns in body.
     */
    @GetMapping("/towns")
    public ResponseEntity<List<TownsDTO>> getAllTowns(TownsCriteria criteria) {
        log.debug("REST request to get Towns by criteria: {}", criteria);
        List<TownsDTO> entityList = townsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /towns/count} : count all the towns.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/towns/count")
    public ResponseEntity<Long> countTowns(TownsCriteria criteria) {
        log.debug("REST request to count Towns by criteria: {}", criteria);
        return ResponseEntity.ok().body(townsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /towns/:id} : get the "id" towns.
     *
     * @param id the id of the townsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the townsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/towns/{id}")
    public ResponseEntity<TownsDTO> getTowns(@PathVariable Long id) {
        log.debug("REST request to get Towns : {}", id);
        Optional<TownsDTO> townsDTO = townsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(townsDTO);
    }

    /**
     * {@code DELETE  /towns/:id} : delete the "id" towns.
     *
     * @param id the id of the townsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/towns/{id}")
    public ResponseEntity<Void> deleteTowns(@PathVariable Long id) {
        log.debug("REST request to delete Towns : {}", id);

        townsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

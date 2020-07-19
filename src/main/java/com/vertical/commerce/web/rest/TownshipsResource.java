package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.TownshipsService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.TownshipsDTO;
import com.vertical.commerce.service.dto.TownshipsCriteria;
import com.vertical.commerce.service.TownshipsQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.Townships}.
 */
@RestController
@RequestMapping("/api")
public class TownshipsResource {

    private final Logger log = LoggerFactory.getLogger(TownshipsResource.class);

    private static final String ENTITY_NAME = "vscommerceTownships";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TownshipsService townshipsService;

    private final TownshipsQueryService townshipsQueryService;

    public TownshipsResource(TownshipsService townshipsService, TownshipsQueryService townshipsQueryService) {
        this.townshipsService = townshipsService;
        this.townshipsQueryService = townshipsQueryService;
    }

    /**
     * {@code POST  /townships} : Create a new townships.
     *
     * @param townshipsDTO the townshipsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new townshipsDTO, or with status {@code 400 (Bad Request)} if the townships has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/townships")
    public ResponseEntity<TownshipsDTO> createTownships(@Valid @RequestBody TownshipsDTO townshipsDTO) throws URISyntaxException {
        log.debug("REST request to save Townships : {}", townshipsDTO);
        if (townshipsDTO.getId() != null) {
            throw new BadRequestAlertException("A new townships cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TownshipsDTO result = townshipsService.save(townshipsDTO);
        return ResponseEntity.created(new URI("/api/townships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /townships} : Updates an existing townships.
     *
     * @param townshipsDTO the townshipsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated townshipsDTO,
     * or with status {@code 400 (Bad Request)} if the townshipsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the townshipsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/townships")
    public ResponseEntity<TownshipsDTO> updateTownships(@Valid @RequestBody TownshipsDTO townshipsDTO) throws URISyntaxException {
        log.debug("REST request to update Townships : {}", townshipsDTO);
        if (townshipsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TownshipsDTO result = townshipsService.save(townshipsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, townshipsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /townships} : get all the townships.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of townships in body.
     */
    @GetMapping("/townships")
    public ResponseEntity<List<TownshipsDTO>> getAllTownships(TownshipsCriteria criteria) {
        log.debug("REST request to get Townships by criteria: {}", criteria);
        List<TownshipsDTO> entityList = townshipsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /townships/count} : count all the townships.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/townships/count")
    public ResponseEntity<Long> countTownships(TownshipsCriteria criteria) {
        log.debug("REST request to count Townships by criteria: {}", criteria);
        return ResponseEntity.ok().body(townshipsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /townships/:id} : get the "id" townships.
     *
     * @param id the id of the townshipsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the townshipsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/townships/{id}")
    public ResponseEntity<TownshipsDTO> getTownships(@PathVariable Long id) {
        log.debug("REST request to get Townships : {}", id);
        Optional<TownshipsDTO> townshipsDTO = townshipsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(townshipsDTO);
    }

    /**
     * {@code DELETE  /townships/:id} : delete the "id" townships.
     *
     * @param id the id of the townshipsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/townships/{id}")
    public ResponseEntity<Void> deleteTownships(@PathVariable Long id) {
        log.debug("REST request to delete Townships : {}", id);

        townshipsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

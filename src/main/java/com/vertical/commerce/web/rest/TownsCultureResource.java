package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.TownsCultureService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.TownsCultureDTO;
import com.vertical.commerce.service.dto.TownsCultureCriteria;
import com.vertical.commerce.service.TownsCultureQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.TownsCulture}.
 */
@RestController
@RequestMapping("/api")
public class TownsCultureResource {

    private final Logger log = LoggerFactory.getLogger(TownsCultureResource.class);

    private static final String ENTITY_NAME = "vscommerceTownsCulture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TownsCultureService townsCultureService;

    private final TownsCultureQueryService townsCultureQueryService;

    public TownsCultureResource(TownsCultureService townsCultureService, TownsCultureQueryService townsCultureQueryService) {
        this.townsCultureService = townsCultureService;
        this.townsCultureQueryService = townsCultureQueryService;
    }

    /**
     * {@code POST  /towns-cultures} : Create a new townsCulture.
     *
     * @param townsCultureDTO the townsCultureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new townsCultureDTO, or with status {@code 400 (Bad Request)} if the townsCulture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/towns-cultures")
    public ResponseEntity<TownsCultureDTO> createTownsCulture(@Valid @RequestBody TownsCultureDTO townsCultureDTO) throws URISyntaxException {
        log.debug("REST request to save TownsCulture : {}", townsCultureDTO);
        if (townsCultureDTO.getId() != null) {
            throw new BadRequestAlertException("A new townsCulture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TownsCultureDTO result = townsCultureService.save(townsCultureDTO);
        return ResponseEntity.created(new URI("/api/towns-cultures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /towns-cultures} : Updates an existing townsCulture.
     *
     * @param townsCultureDTO the townsCultureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated townsCultureDTO,
     * or with status {@code 400 (Bad Request)} if the townsCultureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the townsCultureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/towns-cultures")
    public ResponseEntity<TownsCultureDTO> updateTownsCulture(@Valid @RequestBody TownsCultureDTO townsCultureDTO) throws URISyntaxException {
        log.debug("REST request to update TownsCulture : {}", townsCultureDTO);
        if (townsCultureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TownsCultureDTO result = townsCultureService.save(townsCultureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, townsCultureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /towns-cultures} : get all the townsCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of townsCultures in body.
     */
    @GetMapping("/towns-cultures")
    public ResponseEntity<List<TownsCultureDTO>> getAllTownsCultures(TownsCultureCriteria criteria) {
        log.debug("REST request to get TownsCultures by criteria: {}", criteria);
        List<TownsCultureDTO> entityList = townsCultureQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /towns-cultures/count} : count all the townsCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/towns-cultures/count")
    public ResponseEntity<Long> countTownsCultures(TownsCultureCriteria criteria) {
        log.debug("REST request to count TownsCultures by criteria: {}", criteria);
        return ResponseEntity.ok().body(townsCultureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /towns-cultures/:id} : get the "id" townsCulture.
     *
     * @param id the id of the townsCultureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the townsCultureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/towns-cultures/{id}")
    public ResponseEntity<TownsCultureDTO> getTownsCulture(@PathVariable Long id) {
        log.debug("REST request to get TownsCulture : {}", id);
        Optional<TownsCultureDTO> townsCultureDTO = townsCultureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(townsCultureDTO);
    }

    /**
     * {@code DELETE  /towns-cultures/:id} : delete the "id" townsCulture.
     *
     * @param id the id of the townsCultureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/towns-cultures/{id}")
    public ResponseEntity<Void> deleteTownsCulture(@PathVariable Long id) {
        log.debug("REST request to delete TownsCulture : {}", id);

        townsCultureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

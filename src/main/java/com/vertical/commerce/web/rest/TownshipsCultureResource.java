package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.TownshipsCultureService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.TownshipsCultureDTO;
import com.vertical.commerce.service.dto.TownshipsCultureCriteria;
import com.vertical.commerce.service.TownshipsCultureQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.TownshipsCulture}.
 */
@RestController
@RequestMapping("/api")
public class TownshipsCultureResource {

    private final Logger log = LoggerFactory.getLogger(TownshipsCultureResource.class);

    private static final String ENTITY_NAME = "vscommerceTownshipsCulture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TownshipsCultureService townshipsCultureService;

    private final TownshipsCultureQueryService townshipsCultureQueryService;

    public TownshipsCultureResource(TownshipsCultureService townshipsCultureService, TownshipsCultureQueryService townshipsCultureQueryService) {
        this.townshipsCultureService = townshipsCultureService;
        this.townshipsCultureQueryService = townshipsCultureQueryService;
    }

    /**
     * {@code POST  /townships-cultures} : Create a new townshipsCulture.
     *
     * @param townshipsCultureDTO the townshipsCultureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new townshipsCultureDTO, or with status {@code 400 (Bad Request)} if the townshipsCulture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/townships-cultures")
    public ResponseEntity<TownshipsCultureDTO> createTownshipsCulture(@Valid @RequestBody TownshipsCultureDTO townshipsCultureDTO) throws URISyntaxException {
        log.debug("REST request to save TownshipsCulture : {}", townshipsCultureDTO);
        if (townshipsCultureDTO.getId() != null) {
            throw new BadRequestAlertException("A new townshipsCulture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TownshipsCultureDTO result = townshipsCultureService.save(townshipsCultureDTO);
        return ResponseEntity.created(new URI("/api/townships-cultures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /townships-cultures} : Updates an existing townshipsCulture.
     *
     * @param townshipsCultureDTO the townshipsCultureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated townshipsCultureDTO,
     * or with status {@code 400 (Bad Request)} if the townshipsCultureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the townshipsCultureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/townships-cultures")
    public ResponseEntity<TownshipsCultureDTO> updateTownshipsCulture(@Valid @RequestBody TownshipsCultureDTO townshipsCultureDTO) throws URISyntaxException {
        log.debug("REST request to update TownshipsCulture : {}", townshipsCultureDTO);
        if (townshipsCultureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TownshipsCultureDTO result = townshipsCultureService.save(townshipsCultureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, townshipsCultureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /townships-cultures} : get all the townshipsCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of townshipsCultures in body.
     */
    @GetMapping("/townships-cultures")
    public ResponseEntity<List<TownshipsCultureDTO>> getAllTownshipsCultures(TownshipsCultureCriteria criteria) {
        log.debug("REST request to get TownshipsCultures by criteria: {}", criteria);
        List<TownshipsCultureDTO> entityList = townshipsCultureQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /townships-cultures/count} : count all the townshipsCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/townships-cultures/count")
    public ResponseEntity<Long> countTownshipsCultures(TownshipsCultureCriteria criteria) {
        log.debug("REST request to count TownshipsCultures by criteria: {}", criteria);
        return ResponseEntity.ok().body(townshipsCultureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /townships-cultures/:id} : get the "id" townshipsCulture.
     *
     * @param id the id of the townshipsCultureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the townshipsCultureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/townships-cultures/{id}")
    public ResponseEntity<TownshipsCultureDTO> getTownshipsCulture(@PathVariable Long id) {
        log.debug("REST request to get TownshipsCulture : {}", id);
        Optional<TownshipsCultureDTO> townshipsCultureDTO = townshipsCultureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(townshipsCultureDTO);
    }

    /**
     * {@code DELETE  /townships-cultures/:id} : delete the "id" townshipsCulture.
     *
     * @param id the id of the townshipsCultureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/townships-cultures/{id}")
    public ResponseEntity<Void> deleteTownshipsCulture(@PathVariable Long id) {
        log.debug("REST request to delete TownshipsCulture : {}", id);

        townshipsCultureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

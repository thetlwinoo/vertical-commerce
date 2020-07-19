package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.StateProvincesCultureService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.StateProvincesCultureDTO;
import com.vertical.commerce.service.dto.StateProvincesCultureCriteria;
import com.vertical.commerce.service.StateProvincesCultureQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.StateProvincesCulture}.
 */
@RestController
@RequestMapping("/api")
public class StateProvincesCultureResource {

    private final Logger log = LoggerFactory.getLogger(StateProvincesCultureResource.class);

    private static final String ENTITY_NAME = "vscommerceStateProvincesCulture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StateProvincesCultureService stateProvincesCultureService;

    private final StateProvincesCultureQueryService stateProvincesCultureQueryService;

    public StateProvincesCultureResource(StateProvincesCultureService stateProvincesCultureService, StateProvincesCultureQueryService stateProvincesCultureQueryService) {
        this.stateProvincesCultureService = stateProvincesCultureService;
        this.stateProvincesCultureQueryService = stateProvincesCultureQueryService;
    }

    /**
     * {@code POST  /state-provinces-cultures} : Create a new stateProvincesCulture.
     *
     * @param stateProvincesCultureDTO the stateProvincesCultureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stateProvincesCultureDTO, or with status {@code 400 (Bad Request)} if the stateProvincesCulture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/state-provinces-cultures")
    public ResponseEntity<StateProvincesCultureDTO> createStateProvincesCulture(@Valid @RequestBody StateProvincesCultureDTO stateProvincesCultureDTO) throws URISyntaxException {
        log.debug("REST request to save StateProvincesCulture : {}", stateProvincesCultureDTO);
        if (stateProvincesCultureDTO.getId() != null) {
            throw new BadRequestAlertException("A new stateProvincesCulture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StateProvincesCultureDTO result = stateProvincesCultureService.save(stateProvincesCultureDTO);
        return ResponseEntity.created(new URI("/api/state-provinces-cultures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /state-provinces-cultures} : Updates an existing stateProvincesCulture.
     *
     * @param stateProvincesCultureDTO the stateProvincesCultureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stateProvincesCultureDTO,
     * or with status {@code 400 (Bad Request)} if the stateProvincesCultureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stateProvincesCultureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/state-provinces-cultures")
    public ResponseEntity<StateProvincesCultureDTO> updateStateProvincesCulture(@Valid @RequestBody StateProvincesCultureDTO stateProvincesCultureDTO) throws URISyntaxException {
        log.debug("REST request to update StateProvincesCulture : {}", stateProvincesCultureDTO);
        if (stateProvincesCultureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StateProvincesCultureDTO result = stateProvincesCultureService.save(stateProvincesCultureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stateProvincesCultureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /state-provinces-cultures} : get all the stateProvincesCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stateProvincesCultures in body.
     */
    @GetMapping("/state-provinces-cultures")
    public ResponseEntity<List<StateProvincesCultureDTO>> getAllStateProvincesCultures(StateProvincesCultureCriteria criteria) {
        log.debug("REST request to get StateProvincesCultures by criteria: {}", criteria);
        List<StateProvincesCultureDTO> entityList = stateProvincesCultureQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /state-provinces-cultures/count} : count all the stateProvincesCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/state-provinces-cultures/count")
    public ResponseEntity<Long> countStateProvincesCultures(StateProvincesCultureCriteria criteria) {
        log.debug("REST request to count StateProvincesCultures by criteria: {}", criteria);
        return ResponseEntity.ok().body(stateProvincesCultureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /state-provinces-cultures/:id} : get the "id" stateProvincesCulture.
     *
     * @param id the id of the stateProvincesCultureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stateProvincesCultureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/state-provinces-cultures/{id}")
    public ResponseEntity<StateProvincesCultureDTO> getStateProvincesCulture(@PathVariable Long id) {
        log.debug("REST request to get StateProvincesCulture : {}", id);
        Optional<StateProvincesCultureDTO> stateProvincesCultureDTO = stateProvincesCultureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stateProvincesCultureDTO);
    }

    /**
     * {@code DELETE  /state-provinces-cultures/:id} : delete the "id" stateProvincesCulture.
     *
     * @param id the id of the stateProvincesCultureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/state-provinces-cultures/{id}")
    public ResponseEntity<Void> deleteStateProvincesCulture(@PathVariable Long id) {
        log.debug("REST request to delete StateProvincesCulture : {}", id);

        stateProvincesCultureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

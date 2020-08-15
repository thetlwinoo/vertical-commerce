package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.StateProvincesLocalizeService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.StateProvincesLocalizeDTO;
import com.vertical.commerce.service.dto.StateProvincesLocalizeCriteria;
import com.vertical.commerce.service.StateProvincesLocalizeQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.StateProvincesLocalize}.
 */
@RestController
@RequestMapping("/api")
public class StateProvincesLocalizeResource {

    private final Logger log = LoggerFactory.getLogger(StateProvincesLocalizeResource.class);

    private static final String ENTITY_NAME = "vscommerceStateProvincesLocalize";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StateProvincesLocalizeService stateProvincesLocalizeService;

    private final StateProvincesLocalizeQueryService stateProvincesLocalizeQueryService;

    public StateProvincesLocalizeResource(StateProvincesLocalizeService stateProvincesLocalizeService, StateProvincesLocalizeQueryService stateProvincesLocalizeQueryService) {
        this.stateProvincesLocalizeService = stateProvincesLocalizeService;
        this.stateProvincesLocalizeQueryService = stateProvincesLocalizeQueryService;
    }

    /**
     * {@code POST  /state-provinces-localizes} : Create a new stateProvincesLocalize.
     *
     * @param stateProvincesLocalizeDTO the stateProvincesLocalizeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stateProvincesLocalizeDTO, or with status {@code 400 (Bad Request)} if the stateProvincesLocalize has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/state-provinces-localizes")
    public ResponseEntity<StateProvincesLocalizeDTO> createStateProvincesLocalize(@Valid @RequestBody StateProvincesLocalizeDTO stateProvincesLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to save StateProvincesLocalize : {}", stateProvincesLocalizeDTO);
        if (stateProvincesLocalizeDTO.getId() != null) {
            throw new BadRequestAlertException("A new stateProvincesLocalize cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StateProvincesLocalizeDTO result = stateProvincesLocalizeService.save(stateProvincesLocalizeDTO);
        return ResponseEntity.created(new URI("/api/state-provinces-localizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /state-provinces-localizes} : Updates an existing stateProvincesLocalize.
     *
     * @param stateProvincesLocalizeDTO the stateProvincesLocalizeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stateProvincesLocalizeDTO,
     * or with status {@code 400 (Bad Request)} if the stateProvincesLocalizeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stateProvincesLocalizeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/state-provinces-localizes")
    public ResponseEntity<StateProvincesLocalizeDTO> updateStateProvincesLocalize(@Valid @RequestBody StateProvincesLocalizeDTO stateProvincesLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to update StateProvincesLocalize : {}", stateProvincesLocalizeDTO);
        if (stateProvincesLocalizeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StateProvincesLocalizeDTO result = stateProvincesLocalizeService.save(stateProvincesLocalizeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stateProvincesLocalizeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /state-provinces-localizes} : get all the stateProvincesLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stateProvincesLocalizes in body.
     */
    @GetMapping("/state-provinces-localizes")
    public ResponseEntity<List<StateProvincesLocalizeDTO>> getAllStateProvincesLocalizes(StateProvincesLocalizeCriteria criteria) {
        log.debug("REST request to get StateProvincesLocalizes by criteria: {}", criteria);
        List<StateProvincesLocalizeDTO> entityList = stateProvincesLocalizeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /state-provinces-localizes/count} : count all the stateProvincesLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/state-provinces-localizes/count")
    public ResponseEntity<Long> countStateProvincesLocalizes(StateProvincesLocalizeCriteria criteria) {
        log.debug("REST request to count StateProvincesLocalizes by criteria: {}", criteria);
        return ResponseEntity.ok().body(stateProvincesLocalizeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /state-provinces-localizes/:id} : get the "id" stateProvincesLocalize.
     *
     * @param id the id of the stateProvincesLocalizeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stateProvincesLocalizeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/state-provinces-localizes/{id}")
    public ResponseEntity<StateProvincesLocalizeDTO> getStateProvincesLocalize(@PathVariable Long id) {
        log.debug("REST request to get StateProvincesLocalize : {}", id);
        Optional<StateProvincesLocalizeDTO> stateProvincesLocalizeDTO = stateProvincesLocalizeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stateProvincesLocalizeDTO);
    }

    /**
     * {@code DELETE  /state-provinces-localizes/:id} : delete the "id" stateProvincesLocalize.
     *
     * @param id the id of the stateProvincesLocalizeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/state-provinces-localizes/{id}")
    public ResponseEntity<Void> deleteStateProvincesLocalize(@PathVariable Long id) {
        log.debug("REST request to delete StateProvincesLocalize : {}", id);

        stateProvincesLocalizeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

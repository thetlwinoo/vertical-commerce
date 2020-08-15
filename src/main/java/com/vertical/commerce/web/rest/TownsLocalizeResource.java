package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.TownsLocalizeService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.TownsLocalizeDTO;
import com.vertical.commerce.service.dto.TownsLocalizeCriteria;
import com.vertical.commerce.service.TownsLocalizeQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.TownsLocalize}.
 */
@RestController
@RequestMapping("/api")
public class TownsLocalizeResource {

    private final Logger log = LoggerFactory.getLogger(TownsLocalizeResource.class);

    private static final String ENTITY_NAME = "vscommerceTownsLocalize";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TownsLocalizeService townsLocalizeService;

    private final TownsLocalizeQueryService townsLocalizeQueryService;

    public TownsLocalizeResource(TownsLocalizeService townsLocalizeService, TownsLocalizeQueryService townsLocalizeQueryService) {
        this.townsLocalizeService = townsLocalizeService;
        this.townsLocalizeQueryService = townsLocalizeQueryService;
    }

    /**
     * {@code POST  /towns-localizes} : Create a new townsLocalize.
     *
     * @param townsLocalizeDTO the townsLocalizeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new townsLocalizeDTO, or with status {@code 400 (Bad Request)} if the townsLocalize has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/towns-localizes")
    public ResponseEntity<TownsLocalizeDTO> createTownsLocalize(@Valid @RequestBody TownsLocalizeDTO townsLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to save TownsLocalize : {}", townsLocalizeDTO);
        if (townsLocalizeDTO.getId() != null) {
            throw new BadRequestAlertException("A new townsLocalize cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TownsLocalizeDTO result = townsLocalizeService.save(townsLocalizeDTO);
        return ResponseEntity.created(new URI("/api/towns-localizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /towns-localizes} : Updates an existing townsLocalize.
     *
     * @param townsLocalizeDTO the townsLocalizeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated townsLocalizeDTO,
     * or with status {@code 400 (Bad Request)} if the townsLocalizeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the townsLocalizeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/towns-localizes")
    public ResponseEntity<TownsLocalizeDTO> updateTownsLocalize(@Valid @RequestBody TownsLocalizeDTO townsLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to update TownsLocalize : {}", townsLocalizeDTO);
        if (townsLocalizeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TownsLocalizeDTO result = townsLocalizeService.save(townsLocalizeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, townsLocalizeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /towns-localizes} : get all the townsLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of townsLocalizes in body.
     */
    @GetMapping("/towns-localizes")
    public ResponseEntity<List<TownsLocalizeDTO>> getAllTownsLocalizes(TownsLocalizeCriteria criteria) {
        log.debug("REST request to get TownsLocalizes by criteria: {}", criteria);
        List<TownsLocalizeDTO> entityList = townsLocalizeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /towns-localizes/count} : count all the townsLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/towns-localizes/count")
    public ResponseEntity<Long> countTownsLocalizes(TownsLocalizeCriteria criteria) {
        log.debug("REST request to count TownsLocalizes by criteria: {}", criteria);
        return ResponseEntity.ok().body(townsLocalizeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /towns-localizes/:id} : get the "id" townsLocalize.
     *
     * @param id the id of the townsLocalizeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the townsLocalizeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/towns-localizes/{id}")
    public ResponseEntity<TownsLocalizeDTO> getTownsLocalize(@PathVariable Long id) {
        log.debug("REST request to get TownsLocalize : {}", id);
        Optional<TownsLocalizeDTO> townsLocalizeDTO = townsLocalizeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(townsLocalizeDTO);
    }

    /**
     * {@code DELETE  /towns-localizes/:id} : delete the "id" townsLocalize.
     *
     * @param id the id of the townsLocalizeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/towns-localizes/{id}")
    public ResponseEntity<Void> deleteTownsLocalize(@PathVariable Long id) {
        log.debug("REST request to delete TownsLocalize : {}", id);

        townsLocalizeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

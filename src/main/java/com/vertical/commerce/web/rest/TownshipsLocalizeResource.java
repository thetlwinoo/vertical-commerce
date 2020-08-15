package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.TownshipsLocalizeService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.TownshipsLocalizeDTO;
import com.vertical.commerce.service.dto.TownshipsLocalizeCriteria;
import com.vertical.commerce.service.TownshipsLocalizeQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.TownshipsLocalize}.
 */
@RestController
@RequestMapping("/api")
public class TownshipsLocalizeResource {

    private final Logger log = LoggerFactory.getLogger(TownshipsLocalizeResource.class);

    private static final String ENTITY_NAME = "vscommerceTownshipsLocalize";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TownshipsLocalizeService townshipsLocalizeService;

    private final TownshipsLocalizeQueryService townshipsLocalizeQueryService;

    public TownshipsLocalizeResource(TownshipsLocalizeService townshipsLocalizeService, TownshipsLocalizeQueryService townshipsLocalizeQueryService) {
        this.townshipsLocalizeService = townshipsLocalizeService;
        this.townshipsLocalizeQueryService = townshipsLocalizeQueryService;
    }

    /**
     * {@code POST  /townships-localizes} : Create a new townshipsLocalize.
     *
     * @param townshipsLocalizeDTO the townshipsLocalizeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new townshipsLocalizeDTO, or with status {@code 400 (Bad Request)} if the townshipsLocalize has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/townships-localizes")
    public ResponseEntity<TownshipsLocalizeDTO> createTownshipsLocalize(@Valid @RequestBody TownshipsLocalizeDTO townshipsLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to save TownshipsLocalize : {}", townshipsLocalizeDTO);
        if (townshipsLocalizeDTO.getId() != null) {
            throw new BadRequestAlertException("A new townshipsLocalize cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TownshipsLocalizeDTO result = townshipsLocalizeService.save(townshipsLocalizeDTO);
        return ResponseEntity.created(new URI("/api/townships-localizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /townships-localizes} : Updates an existing townshipsLocalize.
     *
     * @param townshipsLocalizeDTO the townshipsLocalizeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated townshipsLocalizeDTO,
     * or with status {@code 400 (Bad Request)} if the townshipsLocalizeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the townshipsLocalizeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/townships-localizes")
    public ResponseEntity<TownshipsLocalizeDTO> updateTownshipsLocalize(@Valid @RequestBody TownshipsLocalizeDTO townshipsLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to update TownshipsLocalize : {}", townshipsLocalizeDTO);
        if (townshipsLocalizeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TownshipsLocalizeDTO result = townshipsLocalizeService.save(townshipsLocalizeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, townshipsLocalizeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /townships-localizes} : get all the townshipsLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of townshipsLocalizes in body.
     */
    @GetMapping("/townships-localizes")
    public ResponseEntity<List<TownshipsLocalizeDTO>> getAllTownshipsLocalizes(TownshipsLocalizeCriteria criteria) {
        log.debug("REST request to get TownshipsLocalizes by criteria: {}", criteria);
        List<TownshipsLocalizeDTO> entityList = townshipsLocalizeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /townships-localizes/count} : count all the townshipsLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/townships-localizes/count")
    public ResponseEntity<Long> countTownshipsLocalizes(TownshipsLocalizeCriteria criteria) {
        log.debug("REST request to count TownshipsLocalizes by criteria: {}", criteria);
        return ResponseEntity.ok().body(townshipsLocalizeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /townships-localizes/:id} : get the "id" townshipsLocalize.
     *
     * @param id the id of the townshipsLocalizeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the townshipsLocalizeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/townships-localizes/{id}")
    public ResponseEntity<TownshipsLocalizeDTO> getTownshipsLocalize(@PathVariable Long id) {
        log.debug("REST request to get TownshipsLocalize : {}", id);
        Optional<TownshipsLocalizeDTO> townshipsLocalizeDTO = townshipsLocalizeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(townshipsLocalizeDTO);
    }

    /**
     * {@code DELETE  /townships-localizes/:id} : delete the "id" townshipsLocalize.
     *
     * @param id the id of the townshipsLocalizeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/townships-localizes/{id}")
    public ResponseEntity<Void> deleteTownshipsLocalize(@PathVariable Long id) {
        log.debug("REST request to delete TownshipsLocalize : {}", id);

        townshipsLocalizeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

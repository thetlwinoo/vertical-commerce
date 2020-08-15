package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.RegionsLocalizeService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.RegionsLocalizeDTO;
import com.vertical.commerce.service.dto.RegionsLocalizeCriteria;
import com.vertical.commerce.service.RegionsLocalizeQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.RegionsLocalize}.
 */
@RestController
@RequestMapping("/api")
public class RegionsLocalizeResource {

    private final Logger log = LoggerFactory.getLogger(RegionsLocalizeResource.class);

    private static final String ENTITY_NAME = "vscommerceRegionsLocalize";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegionsLocalizeService regionsLocalizeService;

    private final RegionsLocalizeQueryService regionsLocalizeQueryService;

    public RegionsLocalizeResource(RegionsLocalizeService regionsLocalizeService, RegionsLocalizeQueryService regionsLocalizeQueryService) {
        this.regionsLocalizeService = regionsLocalizeService;
        this.regionsLocalizeQueryService = regionsLocalizeQueryService;
    }

    /**
     * {@code POST  /regions-localizes} : Create a new regionsLocalize.
     *
     * @param regionsLocalizeDTO the regionsLocalizeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regionsLocalizeDTO, or with status {@code 400 (Bad Request)} if the regionsLocalize has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/regions-localizes")
    public ResponseEntity<RegionsLocalizeDTO> createRegionsLocalize(@Valid @RequestBody RegionsLocalizeDTO regionsLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to save RegionsLocalize : {}", regionsLocalizeDTO);
        if (regionsLocalizeDTO.getId() != null) {
            throw new BadRequestAlertException("A new regionsLocalize cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegionsLocalizeDTO result = regionsLocalizeService.save(regionsLocalizeDTO);
        return ResponseEntity.created(new URI("/api/regions-localizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /regions-localizes} : Updates an existing regionsLocalize.
     *
     * @param regionsLocalizeDTO the regionsLocalizeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regionsLocalizeDTO,
     * or with status {@code 400 (Bad Request)} if the regionsLocalizeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regionsLocalizeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/regions-localizes")
    public ResponseEntity<RegionsLocalizeDTO> updateRegionsLocalize(@Valid @RequestBody RegionsLocalizeDTO regionsLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to update RegionsLocalize : {}", regionsLocalizeDTO);
        if (regionsLocalizeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegionsLocalizeDTO result = regionsLocalizeService.save(regionsLocalizeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regionsLocalizeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /regions-localizes} : get all the regionsLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regionsLocalizes in body.
     */
    @GetMapping("/regions-localizes")
    public ResponseEntity<List<RegionsLocalizeDTO>> getAllRegionsLocalizes(RegionsLocalizeCriteria criteria) {
        log.debug("REST request to get RegionsLocalizes by criteria: {}", criteria);
        List<RegionsLocalizeDTO> entityList = regionsLocalizeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /regions-localizes/count} : count all the regionsLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/regions-localizes/count")
    public ResponseEntity<Long> countRegionsLocalizes(RegionsLocalizeCriteria criteria) {
        log.debug("REST request to count RegionsLocalizes by criteria: {}", criteria);
        return ResponseEntity.ok().body(regionsLocalizeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /regions-localizes/:id} : get the "id" regionsLocalize.
     *
     * @param id the id of the regionsLocalizeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regionsLocalizeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/regions-localizes/{id}")
    public ResponseEntity<RegionsLocalizeDTO> getRegionsLocalize(@PathVariable Long id) {
        log.debug("REST request to get RegionsLocalize : {}", id);
        Optional<RegionsLocalizeDTO> regionsLocalizeDTO = regionsLocalizeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(regionsLocalizeDTO);
    }

    /**
     * {@code DELETE  /regions-localizes/:id} : delete the "id" regionsLocalize.
     *
     * @param id the id of the regionsLocalizeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/regions-localizes/{id}")
    public ResponseEntity<Void> deleteRegionsLocalize(@PathVariable Long id) {
        log.debug("REST request to delete RegionsLocalize : {}", id);

        regionsLocalizeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

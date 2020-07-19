package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.RegionsCultureService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.RegionsCultureDTO;
import com.vertical.commerce.service.dto.RegionsCultureCriteria;
import com.vertical.commerce.service.RegionsCultureQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.RegionsCulture}.
 */
@RestController
@RequestMapping("/api")
public class RegionsCultureResource {

    private final Logger log = LoggerFactory.getLogger(RegionsCultureResource.class);

    private static final String ENTITY_NAME = "vscommerceRegionsCulture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegionsCultureService regionsCultureService;

    private final RegionsCultureQueryService regionsCultureQueryService;

    public RegionsCultureResource(RegionsCultureService regionsCultureService, RegionsCultureQueryService regionsCultureQueryService) {
        this.regionsCultureService = regionsCultureService;
        this.regionsCultureQueryService = regionsCultureQueryService;
    }

    /**
     * {@code POST  /regions-cultures} : Create a new regionsCulture.
     *
     * @param regionsCultureDTO the regionsCultureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regionsCultureDTO, or with status {@code 400 (Bad Request)} if the regionsCulture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/regions-cultures")
    public ResponseEntity<RegionsCultureDTO> createRegionsCulture(@Valid @RequestBody RegionsCultureDTO regionsCultureDTO) throws URISyntaxException {
        log.debug("REST request to save RegionsCulture : {}", regionsCultureDTO);
        if (regionsCultureDTO.getId() != null) {
            throw new BadRequestAlertException("A new regionsCulture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegionsCultureDTO result = regionsCultureService.save(regionsCultureDTO);
        return ResponseEntity.created(new URI("/api/regions-cultures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /regions-cultures} : Updates an existing regionsCulture.
     *
     * @param regionsCultureDTO the regionsCultureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regionsCultureDTO,
     * or with status {@code 400 (Bad Request)} if the regionsCultureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regionsCultureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/regions-cultures")
    public ResponseEntity<RegionsCultureDTO> updateRegionsCulture(@Valid @RequestBody RegionsCultureDTO regionsCultureDTO) throws URISyntaxException {
        log.debug("REST request to update RegionsCulture : {}", regionsCultureDTO);
        if (regionsCultureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegionsCultureDTO result = regionsCultureService.save(regionsCultureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regionsCultureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /regions-cultures} : get all the regionsCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regionsCultures in body.
     */
    @GetMapping("/regions-cultures")
    public ResponseEntity<List<RegionsCultureDTO>> getAllRegionsCultures(RegionsCultureCriteria criteria) {
        log.debug("REST request to get RegionsCultures by criteria: {}", criteria);
        List<RegionsCultureDTO> entityList = regionsCultureQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /regions-cultures/count} : count all the regionsCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/regions-cultures/count")
    public ResponseEntity<Long> countRegionsCultures(RegionsCultureCriteria criteria) {
        log.debug("REST request to count RegionsCultures by criteria: {}", criteria);
        return ResponseEntity.ok().body(regionsCultureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /regions-cultures/:id} : get the "id" regionsCulture.
     *
     * @param id the id of the regionsCultureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regionsCultureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/regions-cultures/{id}")
    public ResponseEntity<RegionsCultureDTO> getRegionsCulture(@PathVariable Long id) {
        log.debug("REST request to get RegionsCulture : {}", id);
        Optional<RegionsCultureDTO> regionsCultureDTO = regionsCultureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(regionsCultureDTO);
    }

    /**
     * {@code DELETE  /regions-cultures/:id} : delete the "id" regionsCulture.
     *
     * @param id the id of the regionsCultureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/regions-cultures/{id}")
    public ResponseEntity<Void> deleteRegionsCulture(@PathVariable Long id) {
        log.debug("REST request to delete RegionsCulture : {}", id);

        regionsCultureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

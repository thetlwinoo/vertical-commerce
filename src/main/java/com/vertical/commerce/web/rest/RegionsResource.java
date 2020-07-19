package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.RegionsService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.RegionsDTO;
import com.vertical.commerce.service.dto.RegionsCriteria;
import com.vertical.commerce.service.RegionsQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.Regions}.
 */
@RestController
@RequestMapping("/api")
public class RegionsResource {

    private final Logger log = LoggerFactory.getLogger(RegionsResource.class);

    private static final String ENTITY_NAME = "vscommerceRegions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegionsService regionsService;

    private final RegionsQueryService regionsQueryService;

    public RegionsResource(RegionsService regionsService, RegionsQueryService regionsQueryService) {
        this.regionsService = regionsService;
        this.regionsQueryService = regionsQueryService;
    }

    /**
     * {@code POST  /regions} : Create a new regions.
     *
     * @param regionsDTO the regionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regionsDTO, or with status {@code 400 (Bad Request)} if the regions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/regions")
    public ResponseEntity<RegionsDTO> createRegions(@Valid @RequestBody RegionsDTO regionsDTO) throws URISyntaxException {
        log.debug("REST request to save Regions : {}", regionsDTO);
        if (regionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new regions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegionsDTO result = regionsService.save(regionsDTO);
        return ResponseEntity.created(new URI("/api/regions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /regions} : Updates an existing regions.
     *
     * @param regionsDTO the regionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regionsDTO,
     * or with status {@code 400 (Bad Request)} if the regionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/regions")
    public ResponseEntity<RegionsDTO> updateRegions(@Valid @RequestBody RegionsDTO regionsDTO) throws URISyntaxException {
        log.debug("REST request to update Regions : {}", regionsDTO);
        if (regionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegionsDTO result = regionsService.save(regionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /regions} : get all the regions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regions in body.
     */
    @GetMapping("/regions")
    public ResponseEntity<List<RegionsDTO>> getAllRegions(RegionsCriteria criteria) {
        log.debug("REST request to get Regions by criteria: {}", criteria);
        List<RegionsDTO> entityList = regionsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /regions/count} : count all the regions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/regions/count")
    public ResponseEntity<Long> countRegions(RegionsCriteria criteria) {
        log.debug("REST request to count Regions by criteria: {}", criteria);
        return ResponseEntity.ok().body(regionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /regions/:id} : get the "id" regions.
     *
     * @param id the id of the regionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/regions/{id}")
    public ResponseEntity<RegionsDTO> getRegions(@PathVariable Long id) {
        log.debug("REST request to get Regions : {}", id);
        Optional<RegionsDTO> regionsDTO = regionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(regionsDTO);
    }

    /**
     * {@code DELETE  /regions/:id} : delete the "id" regions.
     *
     * @param id the id of the regionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/regions/{id}")
    public ResponseEntity<Void> deleteRegions(@PathVariable Long id) {
        log.debug("REST request to delete Regions : {}", id);

        regionsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

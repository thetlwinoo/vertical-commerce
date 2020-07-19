package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.DistrictsCultureService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.DistrictsCultureDTO;
import com.vertical.commerce.service.dto.DistrictsCultureCriteria;
import com.vertical.commerce.service.DistrictsCultureQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.DistrictsCulture}.
 */
@RestController
@RequestMapping("/api")
public class DistrictsCultureResource {

    private final Logger log = LoggerFactory.getLogger(DistrictsCultureResource.class);

    private static final String ENTITY_NAME = "vscommerceDistrictsCulture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DistrictsCultureService districtsCultureService;

    private final DistrictsCultureQueryService districtsCultureQueryService;

    public DistrictsCultureResource(DistrictsCultureService districtsCultureService, DistrictsCultureQueryService districtsCultureQueryService) {
        this.districtsCultureService = districtsCultureService;
        this.districtsCultureQueryService = districtsCultureQueryService;
    }

    /**
     * {@code POST  /districts-cultures} : Create a new districtsCulture.
     *
     * @param districtsCultureDTO the districtsCultureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new districtsCultureDTO, or with status {@code 400 (Bad Request)} if the districtsCulture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/districts-cultures")
    public ResponseEntity<DistrictsCultureDTO> createDistrictsCulture(@Valid @RequestBody DistrictsCultureDTO districtsCultureDTO) throws URISyntaxException {
        log.debug("REST request to save DistrictsCulture : {}", districtsCultureDTO);
        if (districtsCultureDTO.getId() != null) {
            throw new BadRequestAlertException("A new districtsCulture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DistrictsCultureDTO result = districtsCultureService.save(districtsCultureDTO);
        return ResponseEntity.created(new URI("/api/districts-cultures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /districts-cultures} : Updates an existing districtsCulture.
     *
     * @param districtsCultureDTO the districtsCultureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated districtsCultureDTO,
     * or with status {@code 400 (Bad Request)} if the districtsCultureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the districtsCultureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/districts-cultures")
    public ResponseEntity<DistrictsCultureDTO> updateDistrictsCulture(@Valid @RequestBody DistrictsCultureDTO districtsCultureDTO) throws URISyntaxException {
        log.debug("REST request to update DistrictsCulture : {}", districtsCultureDTO);
        if (districtsCultureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DistrictsCultureDTO result = districtsCultureService.save(districtsCultureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, districtsCultureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /districts-cultures} : get all the districtsCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of districtsCultures in body.
     */
    @GetMapping("/districts-cultures")
    public ResponseEntity<List<DistrictsCultureDTO>> getAllDistrictsCultures(DistrictsCultureCriteria criteria) {
        log.debug("REST request to get DistrictsCultures by criteria: {}", criteria);
        List<DistrictsCultureDTO> entityList = districtsCultureQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /districts-cultures/count} : count all the districtsCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/districts-cultures/count")
    public ResponseEntity<Long> countDistrictsCultures(DistrictsCultureCriteria criteria) {
        log.debug("REST request to count DistrictsCultures by criteria: {}", criteria);
        return ResponseEntity.ok().body(districtsCultureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /districts-cultures/:id} : get the "id" districtsCulture.
     *
     * @param id the id of the districtsCultureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the districtsCultureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/districts-cultures/{id}")
    public ResponseEntity<DistrictsCultureDTO> getDistrictsCulture(@PathVariable Long id) {
        log.debug("REST request to get DistrictsCulture : {}", id);
        Optional<DistrictsCultureDTO> districtsCultureDTO = districtsCultureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(districtsCultureDTO);
    }

    /**
     * {@code DELETE  /districts-cultures/:id} : delete the "id" districtsCulture.
     *
     * @param id the id of the districtsCultureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/districts-cultures/{id}")
    public ResponseEntity<Void> deleteDistrictsCulture(@PathVariable Long id) {
        log.debug("REST request to delete DistrictsCulture : {}", id);

        districtsCultureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

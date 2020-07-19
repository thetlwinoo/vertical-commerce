package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.MaterialsCultureService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.MaterialsCultureDTO;
import com.vertical.commerce.service.dto.MaterialsCultureCriteria;
import com.vertical.commerce.service.MaterialsCultureQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.MaterialsCulture}.
 */
@RestController
@RequestMapping("/api")
public class MaterialsCultureResource {

    private final Logger log = LoggerFactory.getLogger(MaterialsCultureResource.class);

    private static final String ENTITY_NAME = "vscommerceMaterialsCulture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaterialsCultureService materialsCultureService;

    private final MaterialsCultureQueryService materialsCultureQueryService;

    public MaterialsCultureResource(MaterialsCultureService materialsCultureService, MaterialsCultureQueryService materialsCultureQueryService) {
        this.materialsCultureService = materialsCultureService;
        this.materialsCultureQueryService = materialsCultureQueryService;
    }

    /**
     * {@code POST  /materials-cultures} : Create a new materialsCulture.
     *
     * @param materialsCultureDTO the materialsCultureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materialsCultureDTO, or with status {@code 400 (Bad Request)} if the materialsCulture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/materials-cultures")
    public ResponseEntity<MaterialsCultureDTO> createMaterialsCulture(@Valid @RequestBody MaterialsCultureDTO materialsCultureDTO) throws URISyntaxException {
        log.debug("REST request to save MaterialsCulture : {}", materialsCultureDTO);
        if (materialsCultureDTO.getId() != null) {
            throw new BadRequestAlertException("A new materialsCulture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MaterialsCultureDTO result = materialsCultureService.save(materialsCultureDTO);
        return ResponseEntity.created(new URI("/api/materials-cultures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /materials-cultures} : Updates an existing materialsCulture.
     *
     * @param materialsCultureDTO the materialsCultureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materialsCultureDTO,
     * or with status {@code 400 (Bad Request)} if the materialsCultureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materialsCultureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/materials-cultures")
    public ResponseEntity<MaterialsCultureDTO> updateMaterialsCulture(@Valid @RequestBody MaterialsCultureDTO materialsCultureDTO) throws URISyntaxException {
        log.debug("REST request to update MaterialsCulture : {}", materialsCultureDTO);
        if (materialsCultureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MaterialsCultureDTO result = materialsCultureService.save(materialsCultureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materialsCultureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /materials-cultures} : get all the materialsCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materialsCultures in body.
     */
    @GetMapping("/materials-cultures")
    public ResponseEntity<List<MaterialsCultureDTO>> getAllMaterialsCultures(MaterialsCultureCriteria criteria) {
        log.debug("REST request to get MaterialsCultures by criteria: {}", criteria);
        List<MaterialsCultureDTO> entityList = materialsCultureQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /materials-cultures/count} : count all the materialsCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/materials-cultures/count")
    public ResponseEntity<Long> countMaterialsCultures(MaterialsCultureCriteria criteria) {
        log.debug("REST request to count MaterialsCultures by criteria: {}", criteria);
        return ResponseEntity.ok().body(materialsCultureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /materials-cultures/:id} : get the "id" materialsCulture.
     *
     * @param id the id of the materialsCultureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materialsCultureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/materials-cultures/{id}")
    public ResponseEntity<MaterialsCultureDTO> getMaterialsCulture(@PathVariable Long id) {
        log.debug("REST request to get MaterialsCulture : {}", id);
        Optional<MaterialsCultureDTO> materialsCultureDTO = materialsCultureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(materialsCultureDTO);
    }

    /**
     * {@code DELETE  /materials-cultures/:id} : delete the "id" materialsCulture.
     *
     * @param id the id of the materialsCultureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/materials-cultures/{id}")
    public ResponseEntity<Void> deleteMaterialsCulture(@PathVariable Long id) {
        log.debug("REST request to delete MaterialsCulture : {}", id);

        materialsCultureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

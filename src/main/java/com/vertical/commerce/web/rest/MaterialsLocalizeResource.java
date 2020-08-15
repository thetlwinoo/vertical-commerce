package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.MaterialsLocalizeService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.MaterialsLocalizeDTO;
import com.vertical.commerce.service.dto.MaterialsLocalizeCriteria;
import com.vertical.commerce.service.MaterialsLocalizeQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.MaterialsLocalize}.
 */
@RestController
@RequestMapping("/api")
public class MaterialsLocalizeResource {

    private final Logger log = LoggerFactory.getLogger(MaterialsLocalizeResource.class);

    private static final String ENTITY_NAME = "vscommerceMaterialsLocalize";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaterialsLocalizeService materialsLocalizeService;

    private final MaterialsLocalizeQueryService materialsLocalizeQueryService;

    public MaterialsLocalizeResource(MaterialsLocalizeService materialsLocalizeService, MaterialsLocalizeQueryService materialsLocalizeQueryService) {
        this.materialsLocalizeService = materialsLocalizeService;
        this.materialsLocalizeQueryService = materialsLocalizeQueryService;
    }

    /**
     * {@code POST  /materials-localizes} : Create a new materialsLocalize.
     *
     * @param materialsLocalizeDTO the materialsLocalizeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materialsLocalizeDTO, or with status {@code 400 (Bad Request)} if the materialsLocalize has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/materials-localizes")
    public ResponseEntity<MaterialsLocalizeDTO> createMaterialsLocalize(@Valid @RequestBody MaterialsLocalizeDTO materialsLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to save MaterialsLocalize : {}", materialsLocalizeDTO);
        if (materialsLocalizeDTO.getId() != null) {
            throw new BadRequestAlertException("A new materialsLocalize cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MaterialsLocalizeDTO result = materialsLocalizeService.save(materialsLocalizeDTO);
        return ResponseEntity.created(new URI("/api/materials-localizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /materials-localizes} : Updates an existing materialsLocalize.
     *
     * @param materialsLocalizeDTO the materialsLocalizeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materialsLocalizeDTO,
     * or with status {@code 400 (Bad Request)} if the materialsLocalizeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materialsLocalizeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/materials-localizes")
    public ResponseEntity<MaterialsLocalizeDTO> updateMaterialsLocalize(@Valid @RequestBody MaterialsLocalizeDTO materialsLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to update MaterialsLocalize : {}", materialsLocalizeDTO);
        if (materialsLocalizeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MaterialsLocalizeDTO result = materialsLocalizeService.save(materialsLocalizeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materialsLocalizeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /materials-localizes} : get all the materialsLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materialsLocalizes in body.
     */
    @GetMapping("/materials-localizes")
    public ResponseEntity<List<MaterialsLocalizeDTO>> getAllMaterialsLocalizes(MaterialsLocalizeCriteria criteria) {
        log.debug("REST request to get MaterialsLocalizes by criteria: {}", criteria);
        List<MaterialsLocalizeDTO> entityList = materialsLocalizeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /materials-localizes/count} : count all the materialsLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/materials-localizes/count")
    public ResponseEntity<Long> countMaterialsLocalizes(MaterialsLocalizeCriteria criteria) {
        log.debug("REST request to count MaterialsLocalizes by criteria: {}", criteria);
        return ResponseEntity.ok().body(materialsLocalizeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /materials-localizes/:id} : get the "id" materialsLocalize.
     *
     * @param id the id of the materialsLocalizeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materialsLocalizeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/materials-localizes/{id}")
    public ResponseEntity<MaterialsLocalizeDTO> getMaterialsLocalize(@PathVariable Long id) {
        log.debug("REST request to get MaterialsLocalize : {}", id);
        Optional<MaterialsLocalizeDTO> materialsLocalizeDTO = materialsLocalizeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(materialsLocalizeDTO);
    }

    /**
     * {@code DELETE  /materials-localizes/:id} : delete the "id" materialsLocalize.
     *
     * @param id the id of the materialsLocalizeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/materials-localizes/{id}")
    public ResponseEntity<Void> deleteMaterialsLocalize(@PathVariable Long id) {
        log.debug("REST request to delete MaterialsLocalize : {}", id);

        materialsLocalizeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.DiscountTypesService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.DiscountTypesDTO;
import com.vertical.commerce.service.dto.DiscountTypesCriteria;
import com.vertical.commerce.service.DiscountTypesQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.DiscountTypes}.
 */
@RestController
@RequestMapping("/api")
public class DiscountTypesResource {

    private final Logger log = LoggerFactory.getLogger(DiscountTypesResource.class);

    private static final String ENTITY_NAME = "vscommerceDiscountTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiscountTypesService discountTypesService;

    private final DiscountTypesQueryService discountTypesQueryService;

    public DiscountTypesResource(DiscountTypesService discountTypesService, DiscountTypesQueryService discountTypesQueryService) {
        this.discountTypesService = discountTypesService;
        this.discountTypesQueryService = discountTypesQueryService;
    }

    /**
     * {@code POST  /discount-types} : Create a new discountTypes.
     *
     * @param discountTypesDTO the discountTypesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new discountTypesDTO, or with status {@code 400 (Bad Request)} if the discountTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/discount-types")
    public ResponseEntity<DiscountTypesDTO> createDiscountTypes(@Valid @RequestBody DiscountTypesDTO discountTypesDTO) throws URISyntaxException {
        log.debug("REST request to save DiscountTypes : {}", discountTypesDTO);
        if (discountTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new discountTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiscountTypesDTO result = discountTypesService.save(discountTypesDTO);
        return ResponseEntity.created(new URI("/api/discount-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /discount-types} : Updates an existing discountTypes.
     *
     * @param discountTypesDTO the discountTypesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated discountTypesDTO,
     * or with status {@code 400 (Bad Request)} if the discountTypesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the discountTypesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/discount-types")
    public ResponseEntity<DiscountTypesDTO> updateDiscountTypes(@Valid @RequestBody DiscountTypesDTO discountTypesDTO) throws URISyntaxException {
        log.debug("REST request to update DiscountTypes : {}", discountTypesDTO);
        if (discountTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiscountTypesDTO result = discountTypesService.save(discountTypesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, discountTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /discount-types} : get all the discountTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of discountTypes in body.
     */
    @GetMapping("/discount-types")
    public ResponseEntity<List<DiscountTypesDTO>> getAllDiscountTypes(DiscountTypesCriteria criteria) {
        log.debug("REST request to get DiscountTypes by criteria: {}", criteria);
        List<DiscountTypesDTO> entityList = discountTypesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /discount-types/count} : count all the discountTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/discount-types/count")
    public ResponseEntity<Long> countDiscountTypes(DiscountTypesCriteria criteria) {
        log.debug("REST request to count DiscountTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(discountTypesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /discount-types/:id} : get the "id" discountTypes.
     *
     * @param id the id of the discountTypesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the discountTypesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/discount-types/{id}")
    public ResponseEntity<DiscountTypesDTO> getDiscountTypes(@PathVariable Long id) {
        log.debug("REST request to get DiscountTypes : {}", id);
        Optional<DiscountTypesDTO> discountTypesDTO = discountTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(discountTypesDTO);
    }

    /**
     * {@code DELETE  /discount-types/:id} : delete the "id" discountTypes.
     *
     * @param id the id of the discountTypesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/discount-types/{id}")
    public ResponseEntity<Void> deleteDiscountTypes(@PathVariable Long id) {
        log.debug("REST request to delete DiscountTypes : {}", id);

        discountTypesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

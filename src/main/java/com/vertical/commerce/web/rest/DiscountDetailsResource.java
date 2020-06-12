package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.DiscountDetailsService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.DiscountDetailsDTO;
import com.vertical.commerce.service.dto.DiscountDetailsCriteria;
import com.vertical.commerce.service.DiscountDetailsQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.DiscountDetails}.
 */
@RestController
@RequestMapping("/api")
public class DiscountDetailsResource {

    private final Logger log = LoggerFactory.getLogger(DiscountDetailsResource.class);

    private static final String ENTITY_NAME = "vscommerceDiscountDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiscountDetailsService discountDetailsService;

    private final DiscountDetailsQueryService discountDetailsQueryService;

    public DiscountDetailsResource(DiscountDetailsService discountDetailsService, DiscountDetailsQueryService discountDetailsQueryService) {
        this.discountDetailsService = discountDetailsService;
        this.discountDetailsQueryService = discountDetailsQueryService;
    }

    /**
     * {@code POST  /discount-details} : Create a new discountDetails.
     *
     * @param discountDetailsDTO the discountDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new discountDetailsDTO, or with status {@code 400 (Bad Request)} if the discountDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/discount-details")
    public ResponseEntity<DiscountDetailsDTO> createDiscountDetails(@Valid @RequestBody DiscountDetailsDTO discountDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save DiscountDetails : {}", discountDetailsDTO);
        if (discountDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new discountDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiscountDetailsDTO result = discountDetailsService.save(discountDetailsDTO);
        return ResponseEntity.created(new URI("/api/discount-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /discount-details} : Updates an existing discountDetails.
     *
     * @param discountDetailsDTO the discountDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated discountDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the discountDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the discountDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/discount-details")
    public ResponseEntity<DiscountDetailsDTO> updateDiscountDetails(@Valid @RequestBody DiscountDetailsDTO discountDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update DiscountDetails : {}", discountDetailsDTO);
        if (discountDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiscountDetailsDTO result = discountDetailsService.save(discountDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, discountDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /discount-details} : get all the discountDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of discountDetails in body.
     */
    @GetMapping("/discount-details")
    public ResponseEntity<List<DiscountDetailsDTO>> getAllDiscountDetails(DiscountDetailsCriteria criteria) {
        log.debug("REST request to get DiscountDetails by criteria: {}", criteria);
        List<DiscountDetailsDTO> entityList = discountDetailsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /discount-details/count} : count all the discountDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/discount-details/count")
    public ResponseEntity<Long> countDiscountDetails(DiscountDetailsCriteria criteria) {
        log.debug("REST request to count DiscountDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(discountDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /discount-details/:id} : get the "id" discountDetails.
     *
     * @param id the id of the discountDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the discountDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/discount-details/{id}")
    public ResponseEntity<DiscountDetailsDTO> getDiscountDetails(@PathVariable Long id) {
        log.debug("REST request to get DiscountDetails : {}", id);
        Optional<DiscountDetailsDTO> discountDetailsDTO = discountDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(discountDetailsDTO);
    }

    /**
     * {@code DELETE  /discount-details/:id} : delete the "id" discountDetails.
     *
     * @param id the id of the discountDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/discount-details/{id}")
    public ResponseEntity<Void> deleteDiscountDetails(@PathVariable Long id) {
        log.debug("REST request to delete DiscountDetails : {}", id);

        discountDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

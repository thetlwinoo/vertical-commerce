package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.DiscountService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.DiscountDTO;
import com.vertical.commerce.service.dto.DiscountCriteria;
import com.vertical.commerce.service.DiscountQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.Discount}.
 */
@RestController
@RequestMapping("/api")
public class DiscountResource {

    private final Logger log = LoggerFactory.getLogger(DiscountResource.class);

    private static final String ENTITY_NAME = "vscommerceDiscount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiscountService discountService;

    private final DiscountQueryService discountQueryService;

    public DiscountResource(DiscountService discountService, DiscountQueryService discountQueryService) {
        this.discountService = discountService;
        this.discountQueryService = discountQueryService;
    }

    /**
     * {@code POST  /discounts} : Create a new discount.
     *
     * @param discountDTO the discountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new discountDTO, or with status {@code 400 (Bad Request)} if the discount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/discounts")
    public ResponseEntity<DiscountDTO> createDiscount(@Valid @RequestBody DiscountDTO discountDTO) throws URISyntaxException {
        log.debug("REST request to save Discount : {}", discountDTO);
        if (discountDTO.getId() != null) {
            throw new BadRequestAlertException("A new discount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiscountDTO result = discountService.save(discountDTO);
        return ResponseEntity.created(new URI("/api/discounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /discounts} : Updates an existing discount.
     *
     * @param discountDTO the discountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated discountDTO,
     * or with status {@code 400 (Bad Request)} if the discountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the discountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/discounts")
    public ResponseEntity<DiscountDTO> updateDiscount(@Valid @RequestBody DiscountDTO discountDTO) throws URISyntaxException {
        log.debug("REST request to update Discount : {}", discountDTO);
        if (discountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiscountDTO result = discountService.save(discountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, discountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /discounts} : get all the discounts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of discounts in body.
     */
    @GetMapping("/discounts")
    public ResponseEntity<List<DiscountDTO>> getAllDiscounts(DiscountCriteria criteria) {
        log.debug("REST request to get Discounts by criteria: {}", criteria);
        List<DiscountDTO> entityList = discountQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /discounts/count} : count all the discounts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/discounts/count")
    public ResponseEntity<Long> countDiscounts(DiscountCriteria criteria) {
        log.debug("REST request to count Discounts by criteria: {}", criteria);
        return ResponseEntity.ok().body(discountQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /discounts/:id} : get the "id" discount.
     *
     * @param id the id of the discountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the discountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/discounts/{id}")
    public ResponseEntity<DiscountDTO> getDiscount(@PathVariable Long id) {
        log.debug("REST request to get Discount : {}", id);
        Optional<DiscountDTO> discountDTO = discountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(discountDTO);
    }

    /**
     * {@code DELETE  /discounts/:id} : delete the "id" discount.
     *
     * @param id the id of the discountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/discounts/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) {
        log.debug("REST request to delete Discount : {}", id);

        discountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

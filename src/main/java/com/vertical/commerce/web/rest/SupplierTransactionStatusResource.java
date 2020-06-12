package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.SupplierTransactionStatusService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.SupplierTransactionStatusDTO;
import com.vertical.commerce.service.dto.SupplierTransactionStatusCriteria;
import com.vertical.commerce.service.SupplierTransactionStatusQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.SupplierTransactionStatus}.
 */
@RestController
@RequestMapping("/api")
public class SupplierTransactionStatusResource {

    private final Logger log = LoggerFactory.getLogger(SupplierTransactionStatusResource.class);

    private static final String ENTITY_NAME = "vscommerceSupplierTransactionStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupplierTransactionStatusService supplierTransactionStatusService;

    private final SupplierTransactionStatusQueryService supplierTransactionStatusQueryService;

    public SupplierTransactionStatusResource(SupplierTransactionStatusService supplierTransactionStatusService, SupplierTransactionStatusQueryService supplierTransactionStatusQueryService) {
        this.supplierTransactionStatusService = supplierTransactionStatusService;
        this.supplierTransactionStatusQueryService = supplierTransactionStatusQueryService;
    }

    /**
     * {@code POST  /supplier-transaction-statuses} : Create a new supplierTransactionStatus.
     *
     * @param supplierTransactionStatusDTO the supplierTransactionStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supplierTransactionStatusDTO, or with status {@code 400 (Bad Request)} if the supplierTransactionStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/supplier-transaction-statuses")
    public ResponseEntity<SupplierTransactionStatusDTO> createSupplierTransactionStatus(@Valid @RequestBody SupplierTransactionStatusDTO supplierTransactionStatusDTO) throws URISyntaxException {
        log.debug("REST request to save SupplierTransactionStatus : {}", supplierTransactionStatusDTO);
        if (supplierTransactionStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplierTransactionStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplierTransactionStatusDTO result = supplierTransactionStatusService.save(supplierTransactionStatusDTO);
        return ResponseEntity.created(new URI("/api/supplier-transaction-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /supplier-transaction-statuses} : Updates an existing supplierTransactionStatus.
     *
     * @param supplierTransactionStatusDTO the supplierTransactionStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supplierTransactionStatusDTO,
     * or with status {@code 400 (Bad Request)} if the supplierTransactionStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supplierTransactionStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/supplier-transaction-statuses")
    public ResponseEntity<SupplierTransactionStatusDTO> updateSupplierTransactionStatus(@Valid @RequestBody SupplierTransactionStatusDTO supplierTransactionStatusDTO) throws URISyntaxException {
        log.debug("REST request to update SupplierTransactionStatus : {}", supplierTransactionStatusDTO);
        if (supplierTransactionStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplierTransactionStatusDTO result = supplierTransactionStatusService.save(supplierTransactionStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supplierTransactionStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /supplier-transaction-statuses} : get all the supplierTransactionStatuses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supplierTransactionStatuses in body.
     */
    @GetMapping("/supplier-transaction-statuses")
    public ResponseEntity<List<SupplierTransactionStatusDTO>> getAllSupplierTransactionStatuses(SupplierTransactionStatusCriteria criteria) {
        log.debug("REST request to get SupplierTransactionStatuses by criteria: {}", criteria);
        List<SupplierTransactionStatusDTO> entityList = supplierTransactionStatusQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /supplier-transaction-statuses/count} : count all the supplierTransactionStatuses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/supplier-transaction-statuses/count")
    public ResponseEntity<Long> countSupplierTransactionStatuses(SupplierTransactionStatusCriteria criteria) {
        log.debug("REST request to count SupplierTransactionStatuses by criteria: {}", criteria);
        return ResponseEntity.ok().body(supplierTransactionStatusQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /supplier-transaction-statuses/:id} : get the "id" supplierTransactionStatus.
     *
     * @param id the id of the supplierTransactionStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supplierTransactionStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/supplier-transaction-statuses/{id}")
    public ResponseEntity<SupplierTransactionStatusDTO> getSupplierTransactionStatus(@PathVariable Long id) {
        log.debug("REST request to get SupplierTransactionStatus : {}", id);
        Optional<SupplierTransactionStatusDTO> supplierTransactionStatusDTO = supplierTransactionStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplierTransactionStatusDTO);
    }

    /**
     * {@code DELETE  /supplier-transaction-statuses/:id} : delete the "id" supplierTransactionStatus.
     *
     * @param id the id of the supplierTransactionStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/supplier-transaction-statuses/{id}")
    public ResponseEntity<Void> deleteSupplierTransactionStatus(@PathVariable Long id) {
        log.debug("REST request to delete SupplierTransactionStatus : {}", id);

        supplierTransactionStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

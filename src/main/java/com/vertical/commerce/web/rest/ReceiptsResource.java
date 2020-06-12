package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.ReceiptsService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.ReceiptsDTO;
import com.vertical.commerce.service.dto.ReceiptsCriteria;
import com.vertical.commerce.service.ReceiptsQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.Receipts}.
 */
@RestController
@RequestMapping("/api")
public class ReceiptsResource {

    private final Logger log = LoggerFactory.getLogger(ReceiptsResource.class);

    private static final String ENTITY_NAME = "vscommerceReceipts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReceiptsService receiptsService;

    private final ReceiptsQueryService receiptsQueryService;

    public ReceiptsResource(ReceiptsService receiptsService, ReceiptsQueryService receiptsQueryService) {
        this.receiptsService = receiptsService;
        this.receiptsQueryService = receiptsQueryService;
    }

    /**
     * {@code POST  /receipts} : Create a new receipts.
     *
     * @param receiptsDTO the receiptsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new receiptsDTO, or with status {@code 400 (Bad Request)} if the receipts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/receipts")
    public ResponseEntity<ReceiptsDTO> createReceipts(@Valid @RequestBody ReceiptsDTO receiptsDTO) throws URISyntaxException {
        log.debug("REST request to save Receipts : {}", receiptsDTO);
        if (receiptsDTO.getId() != null) {
            throw new BadRequestAlertException("A new receipts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReceiptsDTO result = receiptsService.save(receiptsDTO);
        return ResponseEntity.created(new URI("/api/receipts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /receipts} : Updates an existing receipts.
     *
     * @param receiptsDTO the receiptsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated receiptsDTO,
     * or with status {@code 400 (Bad Request)} if the receiptsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the receiptsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/receipts")
    public ResponseEntity<ReceiptsDTO> updateReceipts(@Valid @RequestBody ReceiptsDTO receiptsDTO) throws URISyntaxException {
        log.debug("REST request to update Receipts : {}", receiptsDTO);
        if (receiptsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReceiptsDTO result = receiptsService.save(receiptsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, receiptsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /receipts} : get all the receipts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of receipts in body.
     */
    @GetMapping("/receipts")
    public ResponseEntity<List<ReceiptsDTO>> getAllReceipts(ReceiptsCriteria criteria) {
        log.debug("REST request to get Receipts by criteria: {}", criteria);
        List<ReceiptsDTO> entityList = receiptsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /receipts/count} : count all the receipts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/receipts/count")
    public ResponseEntity<Long> countReceipts(ReceiptsCriteria criteria) {
        log.debug("REST request to count Receipts by criteria: {}", criteria);
        return ResponseEntity.ok().body(receiptsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /receipts/:id} : get the "id" receipts.
     *
     * @param id the id of the receiptsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the receiptsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/receipts/{id}")
    public ResponseEntity<ReceiptsDTO> getReceipts(@PathVariable Long id) {
        log.debug("REST request to get Receipts : {}", id);
        Optional<ReceiptsDTO> receiptsDTO = receiptsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(receiptsDTO);
    }

    /**
     * {@code DELETE  /receipts/:id} : delete the "id" receipts.
     *
     * @param id the id of the receiptsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/receipts/{id}")
    public ResponseEntity<Void> deleteReceipts(@PathVariable Long id) {
        log.debug("REST request to delete Receipts : {}", id);

        receiptsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

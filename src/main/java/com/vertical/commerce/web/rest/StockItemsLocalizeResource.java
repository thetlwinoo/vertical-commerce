package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.StockItemsLocalizeService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.StockItemsLocalizeDTO;
import com.vertical.commerce.service.dto.StockItemsLocalizeCriteria;
import com.vertical.commerce.service.StockItemsLocalizeQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.StockItemsLocalize}.
 */
@RestController
@RequestMapping("/api")
public class StockItemsLocalizeResource {

    private final Logger log = LoggerFactory.getLogger(StockItemsLocalizeResource.class);

    private static final String ENTITY_NAME = "vscommerceStockItemsLocalize";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StockItemsLocalizeService stockItemsLocalizeService;

    private final StockItemsLocalizeQueryService stockItemsLocalizeQueryService;

    public StockItemsLocalizeResource(StockItemsLocalizeService stockItemsLocalizeService, StockItemsLocalizeQueryService stockItemsLocalizeQueryService) {
        this.stockItemsLocalizeService = stockItemsLocalizeService;
        this.stockItemsLocalizeQueryService = stockItemsLocalizeQueryService;
    }

    /**
     * {@code POST  /stock-items-localizes} : Create a new stockItemsLocalize.
     *
     * @param stockItemsLocalizeDTO the stockItemsLocalizeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockItemsLocalizeDTO, or with status {@code 400 (Bad Request)} if the stockItemsLocalize has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stock-items-localizes")
    public ResponseEntity<StockItemsLocalizeDTO> createStockItemsLocalize(@Valid @RequestBody StockItemsLocalizeDTO stockItemsLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to save StockItemsLocalize : {}", stockItemsLocalizeDTO);
        if (stockItemsLocalizeDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockItemsLocalize cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockItemsLocalizeDTO result = stockItemsLocalizeService.save(stockItemsLocalizeDTO);
        return ResponseEntity.created(new URI("/api/stock-items-localizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stock-items-localizes} : Updates an existing stockItemsLocalize.
     *
     * @param stockItemsLocalizeDTO the stockItemsLocalizeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockItemsLocalizeDTO,
     * or with status {@code 400 (Bad Request)} if the stockItemsLocalizeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockItemsLocalizeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stock-items-localizes")
    public ResponseEntity<StockItemsLocalizeDTO> updateStockItemsLocalize(@Valid @RequestBody StockItemsLocalizeDTO stockItemsLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to update StockItemsLocalize : {}", stockItemsLocalizeDTO);
        if (stockItemsLocalizeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockItemsLocalizeDTO result = stockItemsLocalizeService.save(stockItemsLocalizeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockItemsLocalizeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stock-items-localizes} : get all the stockItemsLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stockItemsLocalizes in body.
     */
    @GetMapping("/stock-items-localizes")
    public ResponseEntity<List<StockItemsLocalizeDTO>> getAllStockItemsLocalizes(StockItemsLocalizeCriteria criteria) {
        log.debug("REST request to get StockItemsLocalizes by criteria: {}", criteria);
        List<StockItemsLocalizeDTO> entityList = stockItemsLocalizeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /stock-items-localizes/count} : count all the stockItemsLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/stock-items-localizes/count")
    public ResponseEntity<Long> countStockItemsLocalizes(StockItemsLocalizeCriteria criteria) {
        log.debug("REST request to count StockItemsLocalizes by criteria: {}", criteria);
        return ResponseEntity.ok().body(stockItemsLocalizeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /stock-items-localizes/:id} : get the "id" stockItemsLocalize.
     *
     * @param id the id of the stockItemsLocalizeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockItemsLocalizeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stock-items-localizes/{id}")
    public ResponseEntity<StockItemsLocalizeDTO> getStockItemsLocalize(@PathVariable Long id) {
        log.debug("REST request to get StockItemsLocalize : {}", id);
        Optional<StockItemsLocalizeDTO> stockItemsLocalizeDTO = stockItemsLocalizeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockItemsLocalizeDTO);
    }

    /**
     * {@code DELETE  /stock-items-localizes/:id} : delete the "id" stockItemsLocalize.
     *
     * @param id the id of the stockItemsLocalizeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stock-items-localizes/{id}")
    public ResponseEntity<Void> deleteStockItemsLocalize(@PathVariable Long id) {
        log.debug("REST request to delete StockItemsLocalize : {}", id);

        stockItemsLocalizeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

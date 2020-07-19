package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.StockItemsCultureService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.StockItemsCultureDTO;
import com.vertical.commerce.service.dto.StockItemsCultureCriteria;
import com.vertical.commerce.service.StockItemsCultureQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.StockItemsCulture}.
 */
@RestController
@RequestMapping("/api")
public class StockItemsCultureResource {

    private final Logger log = LoggerFactory.getLogger(StockItemsCultureResource.class);

    private static final String ENTITY_NAME = "vscommerceStockItemsCulture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StockItemsCultureService stockItemsCultureService;

    private final StockItemsCultureQueryService stockItemsCultureQueryService;

    public StockItemsCultureResource(StockItemsCultureService stockItemsCultureService, StockItemsCultureQueryService stockItemsCultureQueryService) {
        this.stockItemsCultureService = stockItemsCultureService;
        this.stockItemsCultureQueryService = stockItemsCultureQueryService;
    }

    /**
     * {@code POST  /stock-items-cultures} : Create a new stockItemsCulture.
     *
     * @param stockItemsCultureDTO the stockItemsCultureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockItemsCultureDTO, or with status {@code 400 (Bad Request)} if the stockItemsCulture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stock-items-cultures")
    public ResponseEntity<StockItemsCultureDTO> createStockItemsCulture(@Valid @RequestBody StockItemsCultureDTO stockItemsCultureDTO) throws URISyntaxException {
        log.debug("REST request to save StockItemsCulture : {}", stockItemsCultureDTO);
        if (stockItemsCultureDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockItemsCulture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockItemsCultureDTO result = stockItemsCultureService.save(stockItemsCultureDTO);
        return ResponseEntity.created(new URI("/api/stock-items-cultures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stock-items-cultures} : Updates an existing stockItemsCulture.
     *
     * @param stockItemsCultureDTO the stockItemsCultureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockItemsCultureDTO,
     * or with status {@code 400 (Bad Request)} if the stockItemsCultureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockItemsCultureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stock-items-cultures")
    public ResponseEntity<StockItemsCultureDTO> updateStockItemsCulture(@Valid @RequestBody StockItemsCultureDTO stockItemsCultureDTO) throws URISyntaxException {
        log.debug("REST request to update StockItemsCulture : {}", stockItemsCultureDTO);
        if (stockItemsCultureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockItemsCultureDTO result = stockItemsCultureService.save(stockItemsCultureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockItemsCultureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stock-items-cultures} : get all the stockItemsCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stockItemsCultures in body.
     */
    @GetMapping("/stock-items-cultures")
    public ResponseEntity<List<StockItemsCultureDTO>> getAllStockItemsCultures(StockItemsCultureCriteria criteria) {
        log.debug("REST request to get StockItemsCultures by criteria: {}", criteria);
        List<StockItemsCultureDTO> entityList = stockItemsCultureQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /stock-items-cultures/count} : count all the stockItemsCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/stock-items-cultures/count")
    public ResponseEntity<Long> countStockItemsCultures(StockItemsCultureCriteria criteria) {
        log.debug("REST request to count StockItemsCultures by criteria: {}", criteria);
        return ResponseEntity.ok().body(stockItemsCultureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /stock-items-cultures/:id} : get the "id" stockItemsCulture.
     *
     * @param id the id of the stockItemsCultureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockItemsCultureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stock-items-cultures/{id}")
    public ResponseEntity<StockItemsCultureDTO> getStockItemsCulture(@PathVariable Long id) {
        log.debug("REST request to get StockItemsCulture : {}", id);
        Optional<StockItemsCultureDTO> stockItemsCultureDTO = stockItemsCultureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockItemsCultureDTO);
    }

    /**
     * {@code DELETE  /stock-items-cultures/:id} : delete the "id" stockItemsCulture.
     *
     * @param id the id of the stockItemsCultureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stock-items-cultures/{id}")
    public ResponseEntity<Void> deleteStockItemsCulture(@PathVariable Long id) {
        log.debug("REST request to delete StockItemsCulture : {}", id);

        stockItemsCultureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

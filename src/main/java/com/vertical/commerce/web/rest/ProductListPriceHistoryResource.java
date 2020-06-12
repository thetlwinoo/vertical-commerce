package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.ProductListPriceHistoryService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.ProductListPriceHistoryDTO;
import com.vertical.commerce.service.dto.ProductListPriceHistoryCriteria;
import com.vertical.commerce.service.ProductListPriceHistoryQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.ProductListPriceHistory}.
 */
@RestController
@RequestMapping("/api")
public class ProductListPriceHistoryResource {

    private final Logger log = LoggerFactory.getLogger(ProductListPriceHistoryResource.class);

    private static final String ENTITY_NAME = "vscommerceProductListPriceHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductListPriceHistoryService productListPriceHistoryService;

    private final ProductListPriceHistoryQueryService productListPriceHistoryQueryService;

    public ProductListPriceHistoryResource(ProductListPriceHistoryService productListPriceHistoryService, ProductListPriceHistoryQueryService productListPriceHistoryQueryService) {
        this.productListPriceHistoryService = productListPriceHistoryService;
        this.productListPriceHistoryQueryService = productListPriceHistoryQueryService;
    }

    /**
     * {@code POST  /product-list-price-histories} : Create a new productListPriceHistory.
     *
     * @param productListPriceHistoryDTO the productListPriceHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productListPriceHistoryDTO, or with status {@code 400 (Bad Request)} if the productListPriceHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-list-price-histories")
    public ResponseEntity<ProductListPriceHistoryDTO> createProductListPriceHistory(@Valid @RequestBody ProductListPriceHistoryDTO productListPriceHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save ProductListPriceHistory : {}", productListPriceHistoryDTO);
        if (productListPriceHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new productListPriceHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductListPriceHistoryDTO result = productListPriceHistoryService.save(productListPriceHistoryDTO);
        return ResponseEntity.created(new URI("/api/product-list-price-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-list-price-histories} : Updates an existing productListPriceHistory.
     *
     * @param productListPriceHistoryDTO the productListPriceHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productListPriceHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the productListPriceHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productListPriceHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-list-price-histories")
    public ResponseEntity<ProductListPriceHistoryDTO> updateProductListPriceHistory(@Valid @RequestBody ProductListPriceHistoryDTO productListPriceHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update ProductListPriceHistory : {}", productListPriceHistoryDTO);
        if (productListPriceHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductListPriceHistoryDTO result = productListPriceHistoryService.save(productListPriceHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productListPriceHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-list-price-histories} : get all the productListPriceHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productListPriceHistories in body.
     */
    @GetMapping("/product-list-price-histories")
    public ResponseEntity<List<ProductListPriceHistoryDTO>> getAllProductListPriceHistories(ProductListPriceHistoryCriteria criteria) {
        log.debug("REST request to get ProductListPriceHistories by criteria: {}", criteria);
        List<ProductListPriceHistoryDTO> entityList = productListPriceHistoryQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /product-list-price-histories/count} : count all the productListPriceHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-list-price-histories/count")
    public ResponseEntity<Long> countProductListPriceHistories(ProductListPriceHistoryCriteria criteria) {
        log.debug("REST request to count ProductListPriceHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(productListPriceHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-list-price-histories/:id} : get the "id" productListPriceHistory.
     *
     * @param id the id of the productListPriceHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productListPriceHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-list-price-histories/{id}")
    public ResponseEntity<ProductListPriceHistoryDTO> getProductListPriceHistory(@PathVariable Long id) {
        log.debug("REST request to get ProductListPriceHistory : {}", id);
        Optional<ProductListPriceHistoryDTO> productListPriceHistoryDTO = productListPriceHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productListPriceHistoryDTO);
    }

    /**
     * {@code DELETE  /product-list-price-histories/:id} : delete the "id" productListPriceHistory.
     *
     * @param id the id of the productListPriceHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-list-price-histories/{id}")
    public ResponseEntity<Void> deleteProductListPriceHistory(@PathVariable Long id) {
        log.debug("REST request to delete ProductListPriceHistory : {}", id);

        productListPriceHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.ProductSetDetailPriceService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.ProductSetDetailPriceDTO;
import com.vertical.commerce.service.dto.ProductSetDetailPriceCriteria;
import com.vertical.commerce.service.ProductSetDetailPriceQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.ProductSetDetailPrice}.
 */
@RestController
@RequestMapping("/api")
public class ProductSetDetailPriceResource {

    private final Logger log = LoggerFactory.getLogger(ProductSetDetailPriceResource.class);

    private static final String ENTITY_NAME = "vscommerceProductSetDetailPrice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductSetDetailPriceService productSetDetailPriceService;

    private final ProductSetDetailPriceQueryService productSetDetailPriceQueryService;

    public ProductSetDetailPriceResource(ProductSetDetailPriceService productSetDetailPriceService, ProductSetDetailPriceQueryService productSetDetailPriceQueryService) {
        this.productSetDetailPriceService = productSetDetailPriceService;
        this.productSetDetailPriceQueryService = productSetDetailPriceQueryService;
    }

    /**
     * {@code POST  /product-set-detail-prices} : Create a new productSetDetailPrice.
     *
     * @param productSetDetailPriceDTO the productSetDetailPriceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productSetDetailPriceDTO, or with status {@code 400 (Bad Request)} if the productSetDetailPrice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-set-detail-prices")
    public ResponseEntity<ProductSetDetailPriceDTO> createProductSetDetailPrice(@Valid @RequestBody ProductSetDetailPriceDTO productSetDetailPriceDTO) throws URISyntaxException {
        log.debug("REST request to save ProductSetDetailPrice : {}", productSetDetailPriceDTO);
        if (productSetDetailPriceDTO.getId() != null) {
            throw new BadRequestAlertException("A new productSetDetailPrice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductSetDetailPriceDTO result = productSetDetailPriceService.save(productSetDetailPriceDTO);
        return ResponseEntity.created(new URI("/api/product-set-detail-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-set-detail-prices} : Updates an existing productSetDetailPrice.
     *
     * @param productSetDetailPriceDTO the productSetDetailPriceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productSetDetailPriceDTO,
     * or with status {@code 400 (Bad Request)} if the productSetDetailPriceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productSetDetailPriceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-set-detail-prices")
    public ResponseEntity<ProductSetDetailPriceDTO> updateProductSetDetailPrice(@Valid @RequestBody ProductSetDetailPriceDTO productSetDetailPriceDTO) throws URISyntaxException {
        log.debug("REST request to update ProductSetDetailPrice : {}", productSetDetailPriceDTO);
        if (productSetDetailPriceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductSetDetailPriceDTO result = productSetDetailPriceService.save(productSetDetailPriceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productSetDetailPriceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-set-detail-prices} : get all the productSetDetailPrices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productSetDetailPrices in body.
     */
    @GetMapping("/product-set-detail-prices")
    public ResponseEntity<List<ProductSetDetailPriceDTO>> getAllProductSetDetailPrices(ProductSetDetailPriceCriteria criteria) {
        log.debug("REST request to get ProductSetDetailPrices by criteria: {}", criteria);
        List<ProductSetDetailPriceDTO> entityList = productSetDetailPriceQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /product-set-detail-prices/count} : count all the productSetDetailPrices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-set-detail-prices/count")
    public ResponseEntity<Long> countProductSetDetailPrices(ProductSetDetailPriceCriteria criteria) {
        log.debug("REST request to count ProductSetDetailPrices by criteria: {}", criteria);
        return ResponseEntity.ok().body(productSetDetailPriceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-set-detail-prices/:id} : get the "id" productSetDetailPrice.
     *
     * @param id the id of the productSetDetailPriceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productSetDetailPriceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-set-detail-prices/{id}")
    public ResponseEntity<ProductSetDetailPriceDTO> getProductSetDetailPrice(@PathVariable Long id) {
        log.debug("REST request to get ProductSetDetailPrice : {}", id);
        Optional<ProductSetDetailPriceDTO> productSetDetailPriceDTO = productSetDetailPriceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productSetDetailPriceDTO);
    }

    /**
     * {@code DELETE  /product-set-detail-prices/:id} : delete the "id" productSetDetailPrice.
     *
     * @param id the id of the productSetDetailPriceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-set-detail-prices/{id}")
    public ResponseEntity<Void> deleteProductSetDetailPrice(@PathVariable Long id) {
        log.debug("REST request to delete ProductSetDetailPrice : {}", id);

        productSetDetailPriceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

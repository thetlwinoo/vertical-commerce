package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.ProductsLocalizeService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.ProductsLocalizeDTO;
import com.vertical.commerce.service.dto.ProductsLocalizeCriteria;
import com.vertical.commerce.service.ProductsLocalizeQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.ProductsLocalize}.
 */
@RestController
@RequestMapping("/api")
public class ProductsLocalizeResource {

    private final Logger log = LoggerFactory.getLogger(ProductsLocalizeResource.class);

    private static final String ENTITY_NAME = "vscommerceProductsLocalize";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductsLocalizeService productsLocalizeService;

    private final ProductsLocalizeQueryService productsLocalizeQueryService;

    public ProductsLocalizeResource(ProductsLocalizeService productsLocalizeService, ProductsLocalizeQueryService productsLocalizeQueryService) {
        this.productsLocalizeService = productsLocalizeService;
        this.productsLocalizeQueryService = productsLocalizeQueryService;
    }

    /**
     * {@code POST  /products-localizes} : Create a new productsLocalize.
     *
     * @param productsLocalizeDTO the productsLocalizeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productsLocalizeDTO, or with status {@code 400 (Bad Request)} if the productsLocalize has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/products-localizes")
    public ResponseEntity<ProductsLocalizeDTO> createProductsLocalize(@Valid @RequestBody ProductsLocalizeDTO productsLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to save ProductsLocalize : {}", productsLocalizeDTO);
        if (productsLocalizeDTO.getId() != null) {
            throw new BadRequestAlertException("A new productsLocalize cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductsLocalizeDTO result = productsLocalizeService.save(productsLocalizeDTO);
        return ResponseEntity.created(new URI("/api/products-localizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /products-localizes} : Updates an existing productsLocalize.
     *
     * @param productsLocalizeDTO the productsLocalizeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productsLocalizeDTO,
     * or with status {@code 400 (Bad Request)} if the productsLocalizeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productsLocalizeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/products-localizes")
    public ResponseEntity<ProductsLocalizeDTO> updateProductsLocalize(@Valid @RequestBody ProductsLocalizeDTO productsLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to update ProductsLocalize : {}", productsLocalizeDTO);
        if (productsLocalizeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductsLocalizeDTO result = productsLocalizeService.save(productsLocalizeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productsLocalizeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /products-localizes} : get all the productsLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productsLocalizes in body.
     */
    @GetMapping("/products-localizes")
    public ResponseEntity<List<ProductsLocalizeDTO>> getAllProductsLocalizes(ProductsLocalizeCriteria criteria) {
        log.debug("REST request to get ProductsLocalizes by criteria: {}", criteria);
        List<ProductsLocalizeDTO> entityList = productsLocalizeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /products-localizes/count} : count all the productsLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/products-localizes/count")
    public ResponseEntity<Long> countProductsLocalizes(ProductsLocalizeCriteria criteria) {
        log.debug("REST request to count ProductsLocalizes by criteria: {}", criteria);
        return ResponseEntity.ok().body(productsLocalizeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /products-localizes/:id} : get the "id" productsLocalize.
     *
     * @param id the id of the productsLocalizeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productsLocalizeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/products-localizes/{id}")
    public ResponseEntity<ProductsLocalizeDTO> getProductsLocalize(@PathVariable Long id) {
        log.debug("REST request to get ProductsLocalize : {}", id);
        Optional<ProductsLocalizeDTO> productsLocalizeDTO = productsLocalizeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productsLocalizeDTO);
    }

    /**
     * {@code DELETE  /products-localizes/:id} : delete the "id" productsLocalize.
     *
     * @param id the id of the productsLocalizeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/products-localizes/{id}")
    public ResponseEntity<Void> deleteProductsLocalize(@PathVariable Long id) {
        log.debug("REST request to delete ProductsLocalize : {}", id);

        productsLocalizeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

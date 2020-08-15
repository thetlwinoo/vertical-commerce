package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.ProductBrandLocalizeService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.ProductBrandLocalizeDTO;
import com.vertical.commerce.service.dto.ProductBrandLocalizeCriteria;
import com.vertical.commerce.service.ProductBrandLocalizeQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.ProductBrandLocalize}.
 */
@RestController
@RequestMapping("/api")
public class ProductBrandLocalizeResource {

    private final Logger log = LoggerFactory.getLogger(ProductBrandLocalizeResource.class);

    private static final String ENTITY_NAME = "vscommerceProductBrandLocalize";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductBrandLocalizeService productBrandLocalizeService;

    private final ProductBrandLocalizeQueryService productBrandLocalizeQueryService;

    public ProductBrandLocalizeResource(ProductBrandLocalizeService productBrandLocalizeService, ProductBrandLocalizeQueryService productBrandLocalizeQueryService) {
        this.productBrandLocalizeService = productBrandLocalizeService;
        this.productBrandLocalizeQueryService = productBrandLocalizeQueryService;
    }

    /**
     * {@code POST  /product-brand-localizes} : Create a new productBrandLocalize.
     *
     * @param productBrandLocalizeDTO the productBrandLocalizeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productBrandLocalizeDTO, or with status {@code 400 (Bad Request)} if the productBrandLocalize has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-brand-localizes")
    public ResponseEntity<ProductBrandLocalizeDTO> createProductBrandLocalize(@Valid @RequestBody ProductBrandLocalizeDTO productBrandLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to save ProductBrandLocalize : {}", productBrandLocalizeDTO);
        if (productBrandLocalizeDTO.getId() != null) {
            throw new BadRequestAlertException("A new productBrandLocalize cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductBrandLocalizeDTO result = productBrandLocalizeService.save(productBrandLocalizeDTO);
        return ResponseEntity.created(new URI("/api/product-brand-localizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-brand-localizes} : Updates an existing productBrandLocalize.
     *
     * @param productBrandLocalizeDTO the productBrandLocalizeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productBrandLocalizeDTO,
     * or with status {@code 400 (Bad Request)} if the productBrandLocalizeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productBrandLocalizeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-brand-localizes")
    public ResponseEntity<ProductBrandLocalizeDTO> updateProductBrandLocalize(@Valid @RequestBody ProductBrandLocalizeDTO productBrandLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to update ProductBrandLocalize : {}", productBrandLocalizeDTO);
        if (productBrandLocalizeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductBrandLocalizeDTO result = productBrandLocalizeService.save(productBrandLocalizeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productBrandLocalizeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-brand-localizes} : get all the productBrandLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productBrandLocalizes in body.
     */
    @GetMapping("/product-brand-localizes")
    public ResponseEntity<List<ProductBrandLocalizeDTO>> getAllProductBrandLocalizes(ProductBrandLocalizeCriteria criteria) {
        log.debug("REST request to get ProductBrandLocalizes by criteria: {}", criteria);
        List<ProductBrandLocalizeDTO> entityList = productBrandLocalizeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /product-brand-localizes/count} : count all the productBrandLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-brand-localizes/count")
    public ResponseEntity<Long> countProductBrandLocalizes(ProductBrandLocalizeCriteria criteria) {
        log.debug("REST request to count ProductBrandLocalizes by criteria: {}", criteria);
        return ResponseEntity.ok().body(productBrandLocalizeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-brand-localizes/:id} : get the "id" productBrandLocalize.
     *
     * @param id the id of the productBrandLocalizeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productBrandLocalizeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-brand-localizes/{id}")
    public ResponseEntity<ProductBrandLocalizeDTO> getProductBrandLocalize(@PathVariable Long id) {
        log.debug("REST request to get ProductBrandLocalize : {}", id);
        Optional<ProductBrandLocalizeDTO> productBrandLocalizeDTO = productBrandLocalizeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productBrandLocalizeDTO);
    }

    /**
     * {@code DELETE  /product-brand-localizes/:id} : delete the "id" productBrandLocalize.
     *
     * @param id the id of the productBrandLocalizeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-brand-localizes/{id}")
    public ResponseEntity<Void> deleteProductBrandLocalize(@PathVariable Long id) {
        log.debug("REST request to delete ProductBrandLocalize : {}", id);

        productBrandLocalizeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

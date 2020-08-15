package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.ProductCategoryLocalizeService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.ProductCategoryLocalizeDTO;
import com.vertical.commerce.service.dto.ProductCategoryLocalizeCriteria;
import com.vertical.commerce.service.ProductCategoryLocalizeQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.ProductCategoryLocalize}.
 */
@RestController
@RequestMapping("/api")
public class ProductCategoryLocalizeResource {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryLocalizeResource.class);

    private static final String ENTITY_NAME = "vscommerceProductCategoryLocalize";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductCategoryLocalizeService productCategoryLocalizeService;

    private final ProductCategoryLocalizeQueryService productCategoryLocalizeQueryService;

    public ProductCategoryLocalizeResource(ProductCategoryLocalizeService productCategoryLocalizeService, ProductCategoryLocalizeQueryService productCategoryLocalizeQueryService) {
        this.productCategoryLocalizeService = productCategoryLocalizeService;
        this.productCategoryLocalizeQueryService = productCategoryLocalizeQueryService;
    }

    /**
     * {@code POST  /product-category-localizes} : Create a new productCategoryLocalize.
     *
     * @param productCategoryLocalizeDTO the productCategoryLocalizeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productCategoryLocalizeDTO, or with status {@code 400 (Bad Request)} if the productCategoryLocalize has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-category-localizes")
    public ResponseEntity<ProductCategoryLocalizeDTO> createProductCategoryLocalize(@Valid @RequestBody ProductCategoryLocalizeDTO productCategoryLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to save ProductCategoryLocalize : {}", productCategoryLocalizeDTO);
        if (productCategoryLocalizeDTO.getId() != null) {
            throw new BadRequestAlertException("A new productCategoryLocalize cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductCategoryLocalizeDTO result = productCategoryLocalizeService.save(productCategoryLocalizeDTO);
        return ResponseEntity.created(new URI("/api/product-category-localizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-category-localizes} : Updates an existing productCategoryLocalize.
     *
     * @param productCategoryLocalizeDTO the productCategoryLocalizeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productCategoryLocalizeDTO,
     * or with status {@code 400 (Bad Request)} if the productCategoryLocalizeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productCategoryLocalizeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-category-localizes")
    public ResponseEntity<ProductCategoryLocalizeDTO> updateProductCategoryLocalize(@Valid @RequestBody ProductCategoryLocalizeDTO productCategoryLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to update ProductCategoryLocalize : {}", productCategoryLocalizeDTO);
        if (productCategoryLocalizeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductCategoryLocalizeDTO result = productCategoryLocalizeService.save(productCategoryLocalizeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productCategoryLocalizeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-category-localizes} : get all the productCategoryLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productCategoryLocalizes in body.
     */
    @GetMapping("/product-category-localizes")
    public ResponseEntity<List<ProductCategoryLocalizeDTO>> getAllProductCategoryLocalizes(ProductCategoryLocalizeCriteria criteria) {
        log.debug("REST request to get ProductCategoryLocalizes by criteria: {}", criteria);
        List<ProductCategoryLocalizeDTO> entityList = productCategoryLocalizeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /product-category-localizes/count} : count all the productCategoryLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-category-localizes/count")
    public ResponseEntity<Long> countProductCategoryLocalizes(ProductCategoryLocalizeCriteria criteria) {
        log.debug("REST request to count ProductCategoryLocalizes by criteria: {}", criteria);
        return ResponseEntity.ok().body(productCategoryLocalizeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-category-localizes/:id} : get the "id" productCategoryLocalize.
     *
     * @param id the id of the productCategoryLocalizeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productCategoryLocalizeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-category-localizes/{id}")
    public ResponseEntity<ProductCategoryLocalizeDTO> getProductCategoryLocalize(@PathVariable Long id) {
        log.debug("REST request to get ProductCategoryLocalize : {}", id);
        Optional<ProductCategoryLocalizeDTO> productCategoryLocalizeDTO = productCategoryLocalizeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productCategoryLocalizeDTO);
    }

    /**
     * {@code DELETE  /product-category-localizes/:id} : delete the "id" productCategoryLocalize.
     *
     * @param id the id of the productCategoryLocalizeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-category-localizes/{id}")
    public ResponseEntity<Void> deleteProductCategoryLocalize(@PathVariable Long id) {
        log.debug("REST request to delete ProductCategoryLocalize : {}", id);

        productCategoryLocalizeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

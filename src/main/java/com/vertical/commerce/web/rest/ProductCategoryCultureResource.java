package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.ProductCategoryCultureService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.ProductCategoryCultureDTO;
import com.vertical.commerce.service.dto.ProductCategoryCultureCriteria;
import com.vertical.commerce.service.ProductCategoryCultureQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.ProductCategoryCulture}.
 */
@RestController
@RequestMapping("/api")
public class ProductCategoryCultureResource {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryCultureResource.class);

    private static final String ENTITY_NAME = "vscommerceProductCategoryCulture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductCategoryCultureService productCategoryCultureService;

    private final ProductCategoryCultureQueryService productCategoryCultureQueryService;

    public ProductCategoryCultureResource(ProductCategoryCultureService productCategoryCultureService, ProductCategoryCultureQueryService productCategoryCultureQueryService) {
        this.productCategoryCultureService = productCategoryCultureService;
        this.productCategoryCultureQueryService = productCategoryCultureQueryService;
    }

    /**
     * {@code POST  /product-category-cultures} : Create a new productCategoryCulture.
     *
     * @param productCategoryCultureDTO the productCategoryCultureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productCategoryCultureDTO, or with status {@code 400 (Bad Request)} if the productCategoryCulture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-category-cultures")
    public ResponseEntity<ProductCategoryCultureDTO> createProductCategoryCulture(@Valid @RequestBody ProductCategoryCultureDTO productCategoryCultureDTO) throws URISyntaxException {
        log.debug("REST request to save ProductCategoryCulture : {}", productCategoryCultureDTO);
        if (productCategoryCultureDTO.getId() != null) {
            throw new BadRequestAlertException("A new productCategoryCulture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductCategoryCultureDTO result = productCategoryCultureService.save(productCategoryCultureDTO);
        return ResponseEntity.created(new URI("/api/product-category-cultures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-category-cultures} : Updates an existing productCategoryCulture.
     *
     * @param productCategoryCultureDTO the productCategoryCultureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productCategoryCultureDTO,
     * or with status {@code 400 (Bad Request)} if the productCategoryCultureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productCategoryCultureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-category-cultures")
    public ResponseEntity<ProductCategoryCultureDTO> updateProductCategoryCulture(@Valid @RequestBody ProductCategoryCultureDTO productCategoryCultureDTO) throws URISyntaxException {
        log.debug("REST request to update ProductCategoryCulture : {}", productCategoryCultureDTO);
        if (productCategoryCultureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductCategoryCultureDTO result = productCategoryCultureService.save(productCategoryCultureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productCategoryCultureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-category-cultures} : get all the productCategoryCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productCategoryCultures in body.
     */
    @GetMapping("/product-category-cultures")
    public ResponseEntity<List<ProductCategoryCultureDTO>> getAllProductCategoryCultures(ProductCategoryCultureCriteria criteria) {
        log.debug("REST request to get ProductCategoryCultures by criteria: {}", criteria);
        List<ProductCategoryCultureDTO> entityList = productCategoryCultureQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /product-category-cultures/count} : count all the productCategoryCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-category-cultures/count")
    public ResponseEntity<Long> countProductCategoryCultures(ProductCategoryCultureCriteria criteria) {
        log.debug("REST request to count ProductCategoryCultures by criteria: {}", criteria);
        return ResponseEntity.ok().body(productCategoryCultureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-category-cultures/:id} : get the "id" productCategoryCulture.
     *
     * @param id the id of the productCategoryCultureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productCategoryCultureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-category-cultures/{id}")
    public ResponseEntity<ProductCategoryCultureDTO> getProductCategoryCulture(@PathVariable Long id) {
        log.debug("REST request to get ProductCategoryCulture : {}", id);
        Optional<ProductCategoryCultureDTO> productCategoryCultureDTO = productCategoryCultureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productCategoryCultureDTO);
    }

    /**
     * {@code DELETE  /product-category-cultures/:id} : delete the "id" productCategoryCulture.
     *
     * @param id the id of the productCategoryCultureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-category-cultures/{id}")
    public ResponseEntity<Void> deleteProductCategoryCulture(@PathVariable Long id) {
        log.debug("REST request to delete ProductCategoryCulture : {}", id);

        productCategoryCultureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

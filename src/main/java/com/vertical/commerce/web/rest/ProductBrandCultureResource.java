package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.ProductBrandCultureService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.ProductBrandCultureDTO;
import com.vertical.commerce.service.dto.ProductBrandCultureCriteria;
import com.vertical.commerce.service.ProductBrandCultureQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.ProductBrandCulture}.
 */
@RestController
@RequestMapping("/api")
public class ProductBrandCultureResource {

    private final Logger log = LoggerFactory.getLogger(ProductBrandCultureResource.class);

    private static final String ENTITY_NAME = "vscommerceProductBrandCulture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductBrandCultureService productBrandCultureService;

    private final ProductBrandCultureQueryService productBrandCultureQueryService;

    public ProductBrandCultureResource(ProductBrandCultureService productBrandCultureService, ProductBrandCultureQueryService productBrandCultureQueryService) {
        this.productBrandCultureService = productBrandCultureService;
        this.productBrandCultureQueryService = productBrandCultureQueryService;
    }

    /**
     * {@code POST  /product-brand-cultures} : Create a new productBrandCulture.
     *
     * @param productBrandCultureDTO the productBrandCultureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productBrandCultureDTO, or with status {@code 400 (Bad Request)} if the productBrandCulture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-brand-cultures")
    public ResponseEntity<ProductBrandCultureDTO> createProductBrandCulture(@Valid @RequestBody ProductBrandCultureDTO productBrandCultureDTO) throws URISyntaxException {
        log.debug("REST request to save ProductBrandCulture : {}", productBrandCultureDTO);
        if (productBrandCultureDTO.getId() != null) {
            throw new BadRequestAlertException("A new productBrandCulture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductBrandCultureDTO result = productBrandCultureService.save(productBrandCultureDTO);
        return ResponseEntity.created(new URI("/api/product-brand-cultures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-brand-cultures} : Updates an existing productBrandCulture.
     *
     * @param productBrandCultureDTO the productBrandCultureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productBrandCultureDTO,
     * or with status {@code 400 (Bad Request)} if the productBrandCultureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productBrandCultureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-brand-cultures")
    public ResponseEntity<ProductBrandCultureDTO> updateProductBrandCulture(@Valid @RequestBody ProductBrandCultureDTO productBrandCultureDTO) throws URISyntaxException {
        log.debug("REST request to update ProductBrandCulture : {}", productBrandCultureDTO);
        if (productBrandCultureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductBrandCultureDTO result = productBrandCultureService.save(productBrandCultureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productBrandCultureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-brand-cultures} : get all the productBrandCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productBrandCultures in body.
     */
    @GetMapping("/product-brand-cultures")
    public ResponseEntity<List<ProductBrandCultureDTO>> getAllProductBrandCultures(ProductBrandCultureCriteria criteria) {
        log.debug("REST request to get ProductBrandCultures by criteria: {}", criteria);
        List<ProductBrandCultureDTO> entityList = productBrandCultureQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /product-brand-cultures/count} : count all the productBrandCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-brand-cultures/count")
    public ResponseEntity<Long> countProductBrandCultures(ProductBrandCultureCriteria criteria) {
        log.debug("REST request to count ProductBrandCultures by criteria: {}", criteria);
        return ResponseEntity.ok().body(productBrandCultureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-brand-cultures/:id} : get the "id" productBrandCulture.
     *
     * @param id the id of the productBrandCultureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productBrandCultureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-brand-cultures/{id}")
    public ResponseEntity<ProductBrandCultureDTO> getProductBrandCulture(@PathVariable Long id) {
        log.debug("REST request to get ProductBrandCulture : {}", id);
        Optional<ProductBrandCultureDTO> productBrandCultureDTO = productBrandCultureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productBrandCultureDTO);
    }

    /**
     * {@code DELETE  /product-brand-cultures/:id} : delete the "id" productBrandCulture.
     *
     * @param id the id of the productBrandCultureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-brand-cultures/{id}")
    public ResponseEntity<Void> deleteProductBrandCulture(@PathVariable Long id) {
        log.debug("REST request to delete ProductBrandCulture : {}", id);

        productBrandCultureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.ProductsCultureService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.ProductsCultureDTO;
import com.vertical.commerce.service.dto.ProductsCultureCriteria;
import com.vertical.commerce.service.ProductsCultureQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.ProductsCulture}.
 */
@RestController
@RequestMapping("/api")
public class ProductsCultureResource {

    private final Logger log = LoggerFactory.getLogger(ProductsCultureResource.class);

    private static final String ENTITY_NAME = "vscommerceProductsCulture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductsCultureService productsCultureService;

    private final ProductsCultureQueryService productsCultureQueryService;

    public ProductsCultureResource(ProductsCultureService productsCultureService, ProductsCultureQueryService productsCultureQueryService) {
        this.productsCultureService = productsCultureService;
        this.productsCultureQueryService = productsCultureQueryService;
    }

    /**
     * {@code POST  /products-cultures} : Create a new productsCulture.
     *
     * @param productsCultureDTO the productsCultureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productsCultureDTO, or with status {@code 400 (Bad Request)} if the productsCulture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/products-cultures")
    public ResponseEntity<ProductsCultureDTO> createProductsCulture(@Valid @RequestBody ProductsCultureDTO productsCultureDTO) throws URISyntaxException {
        log.debug("REST request to save ProductsCulture : {}", productsCultureDTO);
        if (productsCultureDTO.getId() != null) {
            throw new BadRequestAlertException("A new productsCulture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductsCultureDTO result = productsCultureService.save(productsCultureDTO);
        return ResponseEntity.created(new URI("/api/products-cultures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /products-cultures} : Updates an existing productsCulture.
     *
     * @param productsCultureDTO the productsCultureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productsCultureDTO,
     * or with status {@code 400 (Bad Request)} if the productsCultureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productsCultureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/products-cultures")
    public ResponseEntity<ProductsCultureDTO> updateProductsCulture(@Valid @RequestBody ProductsCultureDTO productsCultureDTO) throws URISyntaxException {
        log.debug("REST request to update ProductsCulture : {}", productsCultureDTO);
        if (productsCultureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductsCultureDTO result = productsCultureService.save(productsCultureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productsCultureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /products-cultures} : get all the productsCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productsCultures in body.
     */
    @GetMapping("/products-cultures")
    public ResponseEntity<List<ProductsCultureDTO>> getAllProductsCultures(ProductsCultureCriteria criteria) {
        log.debug("REST request to get ProductsCultures by criteria: {}", criteria);
        List<ProductsCultureDTO> entityList = productsCultureQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /products-cultures/count} : count all the productsCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/products-cultures/count")
    public ResponseEntity<Long> countProductsCultures(ProductsCultureCriteria criteria) {
        log.debug("REST request to count ProductsCultures by criteria: {}", criteria);
        return ResponseEntity.ok().body(productsCultureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /products-cultures/:id} : get the "id" productsCulture.
     *
     * @param id the id of the productsCultureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productsCultureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/products-cultures/{id}")
    public ResponseEntity<ProductsCultureDTO> getProductsCulture(@PathVariable Long id) {
        log.debug("REST request to get ProductsCulture : {}", id);
        Optional<ProductsCultureDTO> productsCultureDTO = productsCultureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productsCultureDTO);
    }

    /**
     * {@code DELETE  /products-cultures/:id} : delete the "id" productsCulture.
     *
     * @param id the id of the productsCultureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/products-cultures/{id}")
    public ResponseEntity<Void> deleteProductsCulture(@PathVariable Long id) {
        log.debug("REST request to delete ProductsCulture : {}", id);

        productsCultureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

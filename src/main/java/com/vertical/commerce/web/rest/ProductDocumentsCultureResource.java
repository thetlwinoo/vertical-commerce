package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.ProductDocumentsCultureService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.ProductDocumentsCultureDTO;
import com.vertical.commerce.service.dto.ProductDocumentsCultureCriteria;
import com.vertical.commerce.service.ProductDocumentsCultureQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.ProductDocumentsCulture}.
 */
@RestController
@RequestMapping("/api")
public class ProductDocumentsCultureResource {

    private final Logger log = LoggerFactory.getLogger(ProductDocumentsCultureResource.class);

    private static final String ENTITY_NAME = "vscommerceProductDocumentsCulture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductDocumentsCultureService productDocumentsCultureService;

    private final ProductDocumentsCultureQueryService productDocumentsCultureQueryService;

    public ProductDocumentsCultureResource(ProductDocumentsCultureService productDocumentsCultureService, ProductDocumentsCultureQueryService productDocumentsCultureQueryService) {
        this.productDocumentsCultureService = productDocumentsCultureService;
        this.productDocumentsCultureQueryService = productDocumentsCultureQueryService;
    }

    /**
     * {@code POST  /product-documents-cultures} : Create a new productDocumentsCulture.
     *
     * @param productDocumentsCultureDTO the productDocumentsCultureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productDocumentsCultureDTO, or with status {@code 400 (Bad Request)} if the productDocumentsCulture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-documents-cultures")
    public ResponseEntity<ProductDocumentsCultureDTO> createProductDocumentsCulture(@Valid @RequestBody ProductDocumentsCultureDTO productDocumentsCultureDTO) throws URISyntaxException {
        log.debug("REST request to save ProductDocumentsCulture : {}", productDocumentsCultureDTO);
        if (productDocumentsCultureDTO.getId() != null) {
            throw new BadRequestAlertException("A new productDocumentsCulture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductDocumentsCultureDTO result = productDocumentsCultureService.save(productDocumentsCultureDTO);
        return ResponseEntity.created(new URI("/api/product-documents-cultures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-documents-cultures} : Updates an existing productDocumentsCulture.
     *
     * @param productDocumentsCultureDTO the productDocumentsCultureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productDocumentsCultureDTO,
     * or with status {@code 400 (Bad Request)} if the productDocumentsCultureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productDocumentsCultureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-documents-cultures")
    public ResponseEntity<ProductDocumentsCultureDTO> updateProductDocumentsCulture(@Valid @RequestBody ProductDocumentsCultureDTO productDocumentsCultureDTO) throws URISyntaxException {
        log.debug("REST request to update ProductDocumentsCulture : {}", productDocumentsCultureDTO);
        if (productDocumentsCultureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductDocumentsCultureDTO result = productDocumentsCultureService.save(productDocumentsCultureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productDocumentsCultureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-documents-cultures} : get all the productDocumentsCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productDocumentsCultures in body.
     */
    @GetMapping("/product-documents-cultures")
    public ResponseEntity<List<ProductDocumentsCultureDTO>> getAllProductDocumentsCultures(ProductDocumentsCultureCriteria criteria) {
        log.debug("REST request to get ProductDocumentsCultures by criteria: {}", criteria);
        List<ProductDocumentsCultureDTO> entityList = productDocumentsCultureQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /product-documents-cultures/count} : count all the productDocumentsCultures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-documents-cultures/count")
    public ResponseEntity<Long> countProductDocumentsCultures(ProductDocumentsCultureCriteria criteria) {
        log.debug("REST request to count ProductDocumentsCultures by criteria: {}", criteria);
        return ResponseEntity.ok().body(productDocumentsCultureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-documents-cultures/:id} : get the "id" productDocumentsCulture.
     *
     * @param id the id of the productDocumentsCultureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productDocumentsCultureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-documents-cultures/{id}")
    public ResponseEntity<ProductDocumentsCultureDTO> getProductDocumentsCulture(@PathVariable Long id) {
        log.debug("REST request to get ProductDocumentsCulture : {}", id);
        Optional<ProductDocumentsCultureDTO> productDocumentsCultureDTO = productDocumentsCultureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productDocumentsCultureDTO);
    }

    /**
     * {@code DELETE  /product-documents-cultures/:id} : delete the "id" productDocumentsCulture.
     *
     * @param id the id of the productDocumentsCultureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-documents-cultures/{id}")
    public ResponseEntity<Void> deleteProductDocumentsCulture(@PathVariable Long id) {
        log.debug("REST request to delete ProductDocumentsCulture : {}", id);

        productDocumentsCultureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

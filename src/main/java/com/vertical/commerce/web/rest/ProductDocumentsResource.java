package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.ProductDocumentsService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.ProductDocumentsDTO;
import com.vertical.commerce.service.dto.ProductDocumentsCriteria;
import com.vertical.commerce.service.ProductDocumentsQueryService;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.vertical.commerce.domain.ProductDocuments}.
 */
@RestController
@RequestMapping("/api")
public class ProductDocumentsResource {

    private final Logger log = LoggerFactory.getLogger(ProductDocumentsResource.class);

    private static final String ENTITY_NAME = "vscommerceProductDocuments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductDocumentsService productDocumentsService;

    private final ProductDocumentsQueryService productDocumentsQueryService;

    public ProductDocumentsResource(ProductDocumentsService productDocumentsService, ProductDocumentsQueryService productDocumentsQueryService) {
        this.productDocumentsService = productDocumentsService;
        this.productDocumentsQueryService = productDocumentsQueryService;
    }

    /**
     * {@code POST  /product-documents} : Create a new productDocuments.
     *
     * @param productDocumentsDTO the productDocumentsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productDocumentsDTO, or with status {@code 400 (Bad Request)} if the productDocuments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-documents")
    public ResponseEntity<ProductDocumentsDTO> createProductDocuments(@Valid @RequestBody ProductDocumentsDTO productDocumentsDTO) throws URISyntaxException {
        log.debug("REST request to save ProductDocuments : {}", productDocumentsDTO);
        if (productDocumentsDTO.getId() != null) {
            throw new BadRequestAlertException("A new productDocuments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductDocumentsDTO result = productDocumentsService.save(productDocumentsDTO);
        return ResponseEntity.created(new URI("/api/product-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-documents} : Updates an existing productDocuments.
     *
     * @param productDocumentsDTO the productDocumentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productDocumentsDTO,
     * or with status {@code 400 (Bad Request)} if the productDocumentsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productDocumentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-documents")
    public ResponseEntity<ProductDocumentsDTO> updateProductDocuments(@Valid @RequestBody ProductDocumentsDTO productDocumentsDTO) throws URISyntaxException {
        log.debug("REST request to update ProductDocuments : {}", productDocumentsDTO);
        if (productDocumentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductDocumentsDTO result = productDocumentsService.save(productDocumentsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productDocumentsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-documents} : get all the productDocuments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productDocuments in body.
     */
    @GetMapping("/product-documents")
    public ResponseEntity<List<ProductDocumentsDTO>> getAllProductDocuments(ProductDocumentsCriteria criteria) {
        log.debug("REST request to get ProductDocuments by criteria: {}", criteria);
        List<ProductDocumentsDTO> entityList = productDocumentsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /product-documents/count} : count all the productDocuments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-documents/count")
    public ResponseEntity<Long> countProductDocuments(ProductDocumentsCriteria criteria) {
        log.debug("REST request to count ProductDocuments by criteria: {}", criteria);
        return ResponseEntity.ok().body(productDocumentsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-documents/:id} : get the "id" productDocuments.
     *
     * @param id the id of the productDocumentsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productDocumentsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-documents/{id}")
    public ResponseEntity<ProductDocumentsDTO> getProductDocuments(@PathVariable Long id) {
        log.debug("REST request to get ProductDocuments : {}", id);
        Optional<ProductDocumentsDTO> productDocumentsDTO = productDocumentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productDocumentsDTO);
    }

    /**
     * {@code DELETE  /product-documents/:id} : delete the "id" productDocuments.
     *
     * @param id the id of the productDocumentsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-documents/{id}")
    public ResponseEntity<Void> deleteProductDocuments(@PathVariable Long id) {
        log.debug("REST request to delete ProductDocuments : {}", id);

        productDocumentsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

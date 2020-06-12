package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.ProductDocumentExtendService;
import com.vertical.commerce.service.dto.ProductDocumentDTO;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;

/**
 * ProductDocumentExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class ProductDocumentExtendResource {

    private final Logger log = LoggerFactory.getLogger(ProductDocumentExtendResource.class);
    private final ProductDocumentExtendService productDocumentExtendService;

    private static final String ENTITY_NAME = "zezawarProductDocumentExtend";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public ProductDocumentExtendResource(ProductDocumentExtendService productDocumentExtendService) {
        this.productDocumentExtendService = productDocumentExtendService;
    }

    @PostMapping("/product-documents-extend/import")
    public ResponseEntity<ProductDocumentDTO> createProductDocument(@RequestBody ProductDocumentDTO productDocumentDTO, Principal principal) throws URISyntaxException {
        log.debug("REST request to save ProductDocument : {}", productDocumentDTO);
        ProductDocumentDTO result = productDocumentExtendService.importProductDocument(productDocumentDTO, principal);
        return ResponseEntity.created(new URI("/api/product-documents-extend/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
}

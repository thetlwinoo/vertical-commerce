package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.ProductBrandExtendService;
import com.vertical.commerce.service.dto.ProductBrandDTO;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * ProductBrandExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class ProductBrandExtendResource {

    private final Logger log = LoggerFactory.getLogger(ProductBrandExtendResource.class);
    private final ProductBrandExtendService productBrandExtendService;
    private static final String ENTITY_NAME = "vscommerceProductBrand";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public ProductBrandExtendResource(ProductBrandExtendService productBrandExtendService) {
        this.productBrandExtendService = productBrandExtendService;
    }

    @PostMapping("/product-brands-extend")
    public ResponseEntity<ProductBrandDTO> saveProductBrand(@Valid @RequestBody ProductBrandDTO productBrandDTO) throws URISyntaxException {
        ProductBrandDTO result = productBrandExtendService.save(productBrandDTO);
        return ResponseEntity.created(new URI("/api/product-brands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

}

package com.vertical.commerce.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vertical.commerce.domain.Products;
import com.vertical.commerce.service.ProductsExtendService;
import com.vertical.commerce.service.dto.ProductCategoryDTO;
import com.vertical.commerce.service.dto.ProductsCriteria;
import com.vertical.commerce.service.dto.ProductsDTO;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * ProductsExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class ProductsExtendResource {

    private final Logger log = LoggerFactory.getLogger(ProductsExtendResource.class);
    private final ProductsExtendService productsExtendService;

    public ProductsExtendResource(ProductsExtendService productsExtendService) {
        this.productsExtendService = productsExtendService;
    }

    private static final String ENTITY_NAME = "products-extend";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @PostMapping("/products-extend")
    public ResponseEntity<ProductsDTO> createProducts(@Valid @RequestBody Products products, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save Products : {}", products);
        if (products.getId() != null) {
            throw new BadRequestAlertException("A new products cannot already have an ID", ENTITY_NAME, "idexists");
        }
        String _serverUrl = request.getRequestURL().toString().replace("/products-extend/products", "");
        try {
            ProductsDTO result = productsExtendService.saveProducts(products, _serverUrl);

            return ResponseEntity.created(new URI("/products-extend/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (Exception ex) {
            throw new BadRequestAlertException(ex.getMessage(), ENTITY_NAME, "error");
        }
    }

    @PutMapping("/products-extend")
    public ResponseEntity<ProductsDTO> updateProducts(@Valid @RequestBody Products products, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to update Products : {}", products);
        if (products.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        String _serverUrl = request.getRequestURL().toString().replace("/products-extend/products", "");
        try {
            ProductsDTO result = productsExtendService.saveProducts(products, _serverUrl);

            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (Exception ex) {
            throw new BadRequestAlertException(ex.getMessage(), ENTITY_NAME, "error");
        }
    }

    @PostMapping("/products-extend/import")
    public ResponseEntity<ProductsDTO> importProducts(@Valid @RequestBody ProductsDTO productsDTO, Principal principal) throws URISyntaxException {
        log.debug("REST request to save Products : {}", productsDTO);
        ProductsDTO result = productsExtendService.importProducts(productsDTO, principal);
        return ResponseEntity.created(new URI("/api/products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @RequestMapping(value = "/products-extend/{id}", method = RequestMethod.GET)
    public ResponseEntity getFullById(@PathVariable Long id) {
        Optional<ProductsDTO> products = productsExtendService.findOne(id);
        return ResponseUtil.wrapOrNotFound(products);
    }

    @RequestMapping(value = "/products-extend/details/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getProductDetails(@PathVariable Long id) {
        String details = productsExtendService.getProductDetails(id);
        return ResponseEntity.ok().body(details);
    }

    @RequestMapping(value = "/products-extend/related", method = RequestMethod.GET, params = "id")
    public ResponseEntity getByRelated(@RequestParam("id") Long id) {
        ProductsDTO productsDTO = productsExtendService.findOne(id).get();
        if (productsDTO == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List>(productsExtendService.getRelatedProducts(productsDTO.getProductCategoryId(), id), HttpStatus.OK);
    }

    @RequestMapping(value = "/products-extend/related/categories", method = RequestMethod.GET)
    public ResponseEntity<List<ProductCategoryDTO>> getRelatedCategories(
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam(value = "category", required = false) Long category
    ) {
        List<ProductCategoryDTO> entityList = this.productsExtendService.getRelatedCategories(keyword, category);
        return ResponseEntity.ok().body(entityList);
    }

    @RequestMapping(value = "/products-extend/related/colors", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getRelatedColors(
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam(value = "category", required = false) Long category
    ) {
        List<String> entityList = this.productsExtendService.getRelatedColors(keyword, category);
        return ResponseEntity.ok().body(entityList);
    }

    @RequestMapping(value = "/products-extend/related/brands", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getRelatedBrands(
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam(value = "category", required = false) Long category
    ) {
        List<String> entityList = this.productsExtendService.getRelatedBrands(keyword, category);
        return ResponseEntity.ok().body(entityList);
    }

    @RequestMapping(value = "/products-extend/related/pricerange", method = RequestMethod.GET)
    public ResponseEntity<Object> getRelatedPriceRange(
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam(value = "category", required = false) Long category
    ) {
        Object entityList = this.productsExtendService.getRelatedPriceRange(keyword, category);
        return ResponseEntity.ok().body(entityList);
    }

    @RequestMapping(value = "/products-extend/recent", method = RequestMethod.GET)
    public ResponseEntity getByNewlyAdded(Principal principal) {
        List returnList = productsExtendService.findTop18ByOrderByLastEditedWhenDesc();
        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }

    @RequestMapping(value = "/products-extend/dailydiscover", method = RequestMethod.GET)
    public ResponseEntity getByDailyDiscover() {
        List returnList = productsExtendService.findTop18ByOrderByLastEditedWhenDesc();
        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }

    @RequestMapping(value = "/products-extend/mostselling", method = RequestMethod.GET)
    public ResponseEntity getByMostSelling() {
        List returnList = productsExtendService.findTop12ByOrderBySellCountDesc();
        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }

    @RequestMapping(value = "/products-extend/interested", method = RequestMethod.GET)
    public ResponseEntity getByInterested() {
        List returnList = productsExtendService.findTop12ByOrderBySellCountDesc();
        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }

    @RequestMapping(value = "/products-extend/searchall", method = RequestMethod.GET, params = {"keyword"})
    public ResponseEntity searchAll(@RequestParam(value = "keyword", required = false) String keyword) {

        List returnList;

        returnList = productsExtendService.searchProductsAll(keyword);

        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }

    @RequestMapping(value = "/products-extend/searchkeywords", method = RequestMethod.GET, params = {"keyword"})
    public ResponseEntity searchKeywords(@RequestParam(value = "keyword", required = false) String keyword) {

        List returnList;

        returnList = productsExtendService.searchKeywords(keyword);

        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }

    @RequestMapping(value = "/products-extend/search", method = RequestMethod.GET)
    public ResponseEntity<List<ProductsDTO>> search(ProductsCriteria criteria, @RequestParam(value = "color.in", required = false) String colors, Pageable pageable) {
        log.debug("REST request to get Products by criteria: {}", criteria);

        Page<ProductsDTO> page = productsExtendService.searchProductsWithPaging(criteria, pageable,colors);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @RequestMapping(value = "/products-extend/filter", method = RequestMethod.GET)
    public ResponseEntity<String> filterProducts(
        @RequestParam(value = "tag", required = false) String tag,
        @RequestParam(value = "categoryId", required = false) Long categoryId,
        @RequestParam(value = "brandId", required = false) Long brandId,
        @RequestParam(value = "supplierId", required = false) Long supplierId,
        @RequestParam(value = "brandIdList", required = false) String brandIdList,
        @RequestParam(value = "attributes", required = false) String attributes,
        @RequestParam(value = "options", required = false) String options,
        @RequestParam(value = "priceRange", required = false) String priceRange,
        @RequestParam(value = "rating", required = false) Integer rating,
        Pageable pageable
    ) throws JsonProcessingException {
        String result = productsExtendService.getFilterProducts(categoryId == null? -1:categoryId,brandId == null? -1:brandId,supplierId == null? -1:supplierId,brandIdList == null ? "" : brandIdList,tag == null?"":tag,attributes == null? "": attributes,options == null? "":options,priceRange == null?"":priceRange,rating == null?-1:rating,pageable.getPageNumber(),pageable.getPageSize());
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping(value = "/products-extend/filter-controllers", method = RequestMethod.GET)
    public ResponseEntity<String> getFilterControllers(
        @RequestParam(value = "tag", required = false) String tag,
        @RequestParam(value = "categoryId", required = false) Long categoryId,
        @RequestParam(value = "brandId", required = false) Long brandId,
        @RequestParam(value = "supplierId", required = false) Long supplierId
    ) throws JsonProcessingException {
        String result = productsExtendService.getFilterControllers(categoryId == null? -1:categoryId,brandId == null? -1:brandId,supplierId == null? -1:supplierId,tag == null?"":tag);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/products-extend/details-update/{id}")
    public void productDetailsUpdate(@PathVariable Long id) throws JsonProcessingException {
        productsExtendService.productDetailsUpdate(id);
    }

    @PostMapping("/products-extend/details-batch-update")
    public void productDetailsBatchUpdate() throws JsonProcessingException {
        productsExtendService.productDetailsBatchUpdate();
    }

    @RequestMapping(value = "/products-extend/home", method = RequestMethod.GET)
    public ResponseEntity getProductsHome() {
        String result = productsExtendService.getProductsHome();
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping(value = "/products-extend/tags", method = RequestMethod.GET)
    public ResponseEntity getTags(@RequestParam("filter") String filter) {
        List<String> result = productsExtendService.getTags(filter);
        return ResponseEntity.ok().body(result);
    }
}

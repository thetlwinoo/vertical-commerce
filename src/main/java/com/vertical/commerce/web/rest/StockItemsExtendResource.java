package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.StockItemsExtendService;
import com.vertical.commerce.service.dto.StockItemsCriteria;
import com.vertical.commerce.service.dto.StockItemsDTO;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;

/**
 * StockItemsExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class StockItemsExtendResource {

    private final Logger log = LoggerFactory.getLogger(StockItemsExtendResource.class);
    private final StockItemsExtendService stockItemsExtendService;
    private static final String ENTITY_NAME = "stockItemsExtend";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public StockItemsExtendResource(StockItemsExtendService stockItemsExtendService) {
        this.stockItemsExtendService = stockItemsExtendService;
    }

    @GetMapping("/stock-items-extend")
    public ResponseEntity<List<StockItemsDTO>> getAllStockItems(@RequestParam(value="supplierId") Long supplierId, StockItemsCriteria criteria, Pageable pageable, Principal principal) {
        log.debug("REST request to get StockItems by criteria: {}", criteria);

        Page<StockItemsDTO> page = stockItemsExtendService.getAllStockItems(supplierId, criteria, pageable, principal);
        JSONObject jsonObject = stockItemsExtendService.getStatistics(supplierId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        headers.add("Extra",jsonObject.toJSONString());
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/stock-items-extend/import")
    public ResponseEntity<StockItemsDTO> importStockItems(@Valid @RequestBody StockItemsDTO stockItemsDTO, Principal principal) throws URISyntaxException {
        log.debug("REST request to save StockItems : {}", stockItemsDTO);
        StockItemsDTO result = stockItemsExtendService.importStockItems(stockItemsDTO, principal);
        return ResponseEntity.created(new URI("/api/stock-items-extend/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

}

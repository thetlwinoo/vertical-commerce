package com.vertical.commerce.web.rest;

import com.vertical.commerce.domain.Orders;
import com.vertical.commerce.service.OrderLinesExtendService;
import com.vertical.commerce.service.dto.OrderLinesCriteria;
import com.vertical.commerce.service.dto.OrderLinesDTO;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * OrderLinesExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class OrderLinesExtendResource {

    private final Logger log = LoggerFactory.getLogger(OrderLinesExtendResource.class);
    private static final String ENTITY_NAME = "vscommerceOrderLinesExtend";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderLinesExtendService orderLinesExtendService;

    public OrderLinesExtendResource(OrderLinesExtendService orderLinesExtendService) {
        this.orderLinesExtendService = orderLinesExtendService;
    }

    @PutMapping("/order-lines-extend")
    public ResponseEntity<OrderLinesDTO> updateOrderLines(@Valid @RequestBody OrderLinesDTO orderLinesDTO) throws URISyntaxException {
        log.debug("REST request to update OrderLines : {}", orderLinesDTO);
        if (orderLinesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderLinesDTO result = orderLinesExtendService.save(orderLinesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderLinesDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/order-lines-extend")
    public ResponseEntity<List<OrderLinesDTO>> getOrderLinesByProduct(@RequestParam(value = "productId", required = false) Long productId) {
        List<OrderLinesDTO> entityList = orderLinesExtendService.getOrderLinesByProduct(productId);
        return ResponseEntity.ok().body(entityList);
    }

    @DeleteMapping("/order-lines-extend/cancel/{id}")
    public ResponseEntity<Map<String, Object>> deleteOrderLines(@PathVariable Long id) {
        log.debug("REST request to delete OrderLines : {}", id);

        Map<String, Object> response = orderLinesExtendService.cancelOrderLine(id);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .body(response);
    }
}

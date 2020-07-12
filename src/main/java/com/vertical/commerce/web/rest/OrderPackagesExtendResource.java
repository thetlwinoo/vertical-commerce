package com.vertical.commerce.web.rest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hazelcast.internal.json.Json;
import com.vertical.commerce.domain.OrderPackages;
import com.vertical.commerce.domain.Products;
import com.vertical.commerce.service.OrderPackagesExtendService;
import com.vertical.commerce.service.dto.OrderLinesDTO;
import com.vertical.commerce.service.dto.OrderPackagesDTO;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * OrderPackagesExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class OrderPackagesExtendResource {

    private final Logger log = LoggerFactory.getLogger(OrderPackagesExtendResource.class);

    private static final String ENTITY_NAME = "vscommerceOrderPackagesExtend";
    private final OrderPackagesExtendService orderPackagesExtendService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public OrderPackagesExtendResource(OrderPackagesExtendService orderPackagesExtendService) {
        this.orderPackagesExtendService = orderPackagesExtendService;
    }

    @PutMapping("/order-packages-extend/reviews")
    public ResponseEntity<OrderPackagesDTO> updateReviews(@Valid @RequestBody String payload) throws URISyntaxException, JsonProcessingException {
        OrderPackagesDTO orderPackagesDTO = orderPackagesExtendService.saveOrderPackage(payload);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderPackagesDTO.getId().toString()))
            .body(orderPackagesDTO);
    }

    @PostMapping("/order-packages-extend/order-details-batch-update")
    public void productDetailsBatchUpdate(){
        orderPackagesExtendService.orderDetailsBatchUpdate();
    }
}

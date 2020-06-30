package com.vertical.commerce.web.rest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hazelcast.internal.json.Json;
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
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(payload);
        JsonNode jsonNode1 = actualObj.get("id");
        JsonNode jsonNode2 = actualObj.get("completedReview");
        JsonNode jsonNode3 = actualObj.get("customerReviewedOn");
        JsonNode jsonNode4 = actualObj.get("deliveryRating");
        JsonNode jsonNode5 = actualObj.get("deliveryReview");
        JsonNode jsonNode6 = actualObj.get("reviewAsAnonymous");
        JsonNode jsonNode7 = actualObj.get("sellerRating");
        JsonNode jsonNode8 = actualObj.get("sellerReview");
        JsonNode jsonNode9 = actualObj.get("lineReviewList");
        if (jsonNode1 == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<OrderPackagesDTO> orderPackagesDTOOptional = orderPackagesExtendService.findOrderPackage(jsonNode1.longValue());
        OrderPackagesDTO orderPackagesDTO = null;

        if(orderPackagesDTOOptional.isPresent()){
            orderPackagesDTO = orderPackagesDTOOptional.get();

            orderPackagesDTO.setCompletedReview(jsonNode2.booleanValue());
            orderPackagesDTO.setCustomerReviewedOn(Instant.parse(jsonNode3.textValue()));
            orderPackagesDTO.setDeliveryRating(jsonNode4.intValue());
            orderPackagesDTO.setDeliveryReview(jsonNode5.textValue());
            orderPackagesDTO.setReviewAsAnonymous(jsonNode6.booleanValue());
            orderPackagesDTO.setSellerRating(jsonNode7.intValue());
            orderPackagesDTO.setSellerReview(jsonNode8.textValue());

            if(jsonNode9.isArray()){
                for (final JsonNode objNode : jsonNode9) {
                    System.out.println(objNode);
                    JsonNode idNode = objNode.get("id");
                    JsonNode lineRatingNode = objNode.get("lineRating");
                    JsonNode lineReviewNode = objNode.get("lineReview");
                    JsonNode reviewImageNode = objNode.get("reviewImageId");
                    Optional<OrderLinesDTO>  orderLinesDTOOptional = orderPackagesExtendService.findOrderLine(idNode.longValue());

                    if(orderLinesDTOOptional.isPresent()){
                        OrderLinesDTO orderLinesDTO = orderLinesDTOOptional.get();
                        orderLinesDTO.setLineRating(lineRatingNode.intValue());
                        orderLinesDTO.setLineReview(lineReviewNode.textValue());
                        orderLinesDTO.setCustomerReviewedOn(Instant.now());

                        if(reviewImageNode != null){
                            orderLinesDTO.setReviewImageId(reviewImageNode.longValue());
                        }

                        orderPackagesExtendService.saveOrderLine(orderLinesDTO);
                    }
                }
            }

            orderPackagesDTO = orderPackagesExtendService.saveOrderPackage(orderPackagesDTO);
        }

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderPackagesDTO.getId().toString()))
            .body(orderPackagesDTO);
    }

}

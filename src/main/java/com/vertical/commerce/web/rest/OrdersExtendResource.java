package com.vertical.commerce.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vertical.commerce.domain.Customers;
import com.vertical.commerce.service.OrdersExtendService;
import com.vertical.commerce.service.dto.OrdersCriteria;
import com.vertical.commerce.service.dto.OrdersDTO;
import io.github.jhipster.web.util.PaginationUtil;
import net.minidev.json.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * OrdersExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class OrdersExtendResource {

    private final Logger log = LoggerFactory.getLogger(OrdersExtendResource.class);
    private final OrdersExtendService ordersExtendService;

    public OrdersExtendResource(OrdersExtendService ordersExtendService) {
        this.ordersExtendService = ordersExtendService;
    }

    @RequestMapping(value = "/orders-extend/order/count", method = RequestMethod.GET)
    public ResponseEntity getAllOrdersCount(Principal principal) {
        Integer orderCount = ordersExtendService.getAllOrdersCount(principal);
        return new ResponseEntity<Integer>(orderCount, HttpStatus.OK);
    }


    @RequestMapping(value = "/orders-extend/order", method = RequestMethod.POST)
    public ResponseEntity postOrder(@Valid @RequestBody OrdersDTO ordersDTO, BindingResult bindingResult, Principal principal) throws JsonProcessingException, ParseException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        OrdersDTO saveOrder = ordersExtendService.postOrder(principal, ordersDTO);
        return new ResponseEntity<OrdersDTO>(saveOrder, HttpStatus.OK);
    }

    @GetMapping("/orders-extend/customer-orders/reviews")
    public ResponseEntity<List<OrdersDTO>> getCustomerOrdersReviews(@RequestParam(value = "completedReview", required = false) Boolean completedReview, OrdersCriteria criteria, Pageable pageable, Principal principal) {
        log.debug("REST request to get Orders by criteria: {}", criteria);
        Page<OrdersDTO> page = ordersExtendService.getCustomerOrdersReviews(completedReview,criteria, pageable, principal);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/orders-extend")
    public ResponseEntity<List<OrdersDTO>> getAllOrders(OrdersCriteria criteria, Pageable pageable, Principal principal) {
        log.debug("REST request to get Orders by criteria: {}", criteria);

        Page<OrdersDTO> page = ordersExtendService.getAllOrders(criteria,pageable,principal);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}

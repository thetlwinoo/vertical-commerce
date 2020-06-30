package com.vertical.commerce.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OrderPackagesExtendResource controller
 */
@RestController
@RequestMapping("/api/order-packages-extend")
public class OrderPackagesExtendResource {

    private final Logger log = LoggerFactory.getLogger(OrderPackagesExtendResource.class);

    /**
    * GET defaultAction
    */
    @GetMapping("/default-action")
    public String defaultAction() {
        return "defaultAction";
    }

}

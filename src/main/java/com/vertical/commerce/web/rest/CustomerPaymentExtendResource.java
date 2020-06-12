package com.vertical.commerce.web.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.base.rest.PayPalRESTException;
import com.vertical.commerce.service.CustomerPaymentExtendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

/**
 * CustomerPaymentExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class CustomerPaymentExtendResource {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentExtendResource.class);
    private final CustomerPaymentExtendService customerPaymentExtendService;

    public CustomerPaymentExtendResource(CustomerPaymentExtendService customerPaymentExtendService) {
        this.customerPaymentExtendService = customerPaymentExtendService;
    }

    @PostMapping(value = "/customer-payment-extend/cash-on-delivery", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> cashOnDelivery(@Valid @RequestBody String payload, Principal principal) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(payload);
        JsonNode jsonNode2 = actualObj.get("orderId");
        JsonNode jsonNode3 = actualObj.get("paymentMethodId");

        Long orderId = jsonNode2.longValue();
        Long paymentMethodId = jsonNode3.longValue();

        Map<String, Object> response = customerPaymentExtendService.cashOnDelivery(orderId,paymentMethodId, principal);
        return response;
    }

    @PostMapping(value = "/customer-payment-extend/paypal/make/payment")
    public Map<String, Object> makePayment(@Valid @RequestBody String payload) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(payload);
        JsonNode jsonNode1 = actualObj.get("sum");
        JsonNode jsonNode2 = actualObj.get("returnUrl");
        JsonNode jsonNode3 = actualObj.get("cancelUrl");
        String totalPrice = jsonNode1.textValue();
        String returnUrl = jsonNode2.textValue();
        String cancelUrl = jsonNode3.textValue();
        Map<String, Object> response =  customerPaymentExtendService.createPaypalPayment(totalPrice, returnUrl, cancelUrl);
        return response;
    }

    //    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/customer-payment-extend/paypal/complete/payment", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> completePayment(@Valid @RequestBody String payload, Principal principal) throws PayPalRESTException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(payload);
        JsonNode jsonNode1 = actualObj.get("paymentId");
        JsonNode jsonNode2 = actualObj.get("payerId");
        JsonNode jsonNode3 = actualObj.get("orderId");
        String paymentId = jsonNode1.textValue();
        String payerId = jsonNode2.textValue();
        Long orderId = jsonNode3.longValue();
        Map<String, Object> result = customerPaymentExtendService.completePaypalPayment(paymentId, payerId, orderId,principal);
        return result;
    }

    //    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/customer-payment-extend/stripe/charge", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> chargeCard(@Valid @RequestBody String payload, Principal principal) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(payload);
        JsonNode jsonNode1 = actualObj.get("token");
        JsonNode jsonNode2 = actualObj.get("amount");
        JsonNode jsonNode3 = actualObj.get("orderId");
        String token = jsonNode1.textValue();

        Double amount = jsonNode2.doubleValue();
        Long orderId = jsonNode3.longValue();

        return this.customerPaymentExtendService.chargeStripeNewCard(token, amount, orderId,principal);
    }
}

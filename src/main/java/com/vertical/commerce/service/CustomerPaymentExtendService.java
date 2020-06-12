package com.vertical.commerce.service;

import com.stripe.model.Customer;

import java.security.Principal;
import java.util.Map;

public interface CustomerPaymentExtendService {

    Map<String, Object> cashOnDelivery(Long orderId,Long paymentMethodId, Principal principal);

    //Stripe Serivces
    Customer createStripeCustomer(String token, String email) throws Exception;

    Map<String, Object> chargeStripeNewCard(String token, double amount, Long orderId, Principal principal) throws Exception;

    Map<String, Object> chargeStripeCustomerCard(String customerId, int amount) throws Exception;

    //Paypal Services
    Map<String, Object> createPaypalPayment(String sum, String returnUrl, String cancelUrl);

    Map<String, Object> completePaypalPayment(String paymentId, String payerId, Long orderId, Principal principal);

    //Payment
    Map<String, Object> completePayment(Long orderId,Principal principal);

    Map<String, Object> completeOrder(Long orderId,Principal principal);
}

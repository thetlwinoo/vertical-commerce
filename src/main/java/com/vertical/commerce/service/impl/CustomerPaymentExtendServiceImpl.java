package com.vertical.commerce.service.impl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.vertical.commerce.config.Constants;
import com.vertical.commerce.domain.*;
import com.vertical.commerce.domain.enumeration.OrderStatus;
import com.vertical.commerce.domain.enumeration.PaymentStatus;
import com.vertical.commerce.repository.*;
import com.vertical.commerce.service.CommonService;
import com.vertical.commerce.service.CustomerPaymentExtendService;
import com.vertical.commerce.service.CustomerPaymentService;
import com.vertical.commerce.service.dto.CustomerPaymentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CustomerPaymentExtendServiceImpl implements CustomerPaymentExtendService {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentExtendServiceImpl.class);
    private final CustomerPaymentRepository customerPaymentRepository;
    private final CustomerPaymentService customerPaymentService;
    private final PaymentMethodsRepository paymentMethodsRepository;
    private final InvoicesRepository invoicesRepository;
    private final InvoiceLinesRepository invoiceLinesRepository;
    private final OrdersRepository ordersRepository;
    private final StockItemHoldingsExtendRepository stockItemHoldingsExtendRepository;
    private final CommonService commonService;
    private final InvoicesExtendRepository invoicesExtendRepository;
    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    public static final MathContext ROUND_VALUE = new MathContext(2);

    public CustomerPaymentExtendServiceImpl(CustomerPaymentRepository customerPaymentRepository, CustomerPaymentService customerPaymentService, PaymentMethodsRepository paymentMethodsRepository, InvoicesRepository invoicesRepository, InvoiceLinesRepository invoiceLinesRepository, OrdersRepository ordersRepository, StockItemHoldingsExtendRepository stockItemHoldingsExtendRepository, CommonService commonService, InvoicesExtendRepository invoicesExtendRepository) {
        this.customerPaymentRepository = customerPaymentRepository;
        this.customerPaymentService = customerPaymentService;
        this.paymentMethodsRepository = paymentMethodsRepository;
        this.invoicesRepository = invoicesRepository;
        this.invoiceLinesRepository = invoiceLinesRepository;
        this.ordersRepository = ordersRepository;
        this.stockItemHoldingsExtendRepository = stockItemHoldingsExtendRepository;
        this.commonService = commonService;
        this.invoicesExtendRepository = invoicesExtendRepository;
    }

    @Override
    @Transactional
    public Map<String, Object> cashOnDelivery(Long orderId,Long paymentMethodId, Principal principal) {
        Map<String, Object> response = new HashMap<String, Object>();
        People people = commonService.getPeopleByPrincipal(principal);
        Customers customers = commonService.getCustomerByPrincipal(principal);
        try {
            Orders orders = ordersRepository.getOne(orderId);
            orders.setStatus(OrderStatus.NEW_ORDER);
            PaymentMethods paymentMethods = paymentMethodsRepository.getOne(paymentMethodId);
//            orders.setPaymentStatus(PaymentStatus.CASH_ON_DELIVERY);
            orders.setPaymentMethod(paymentMethods);
            ordersRepository.save(orders);

            response.put("status", "succeeded");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return response;
    }

    @Override
    public Customer createStripeCustomer(String token, String email) throws Exception {
        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("email", email);
        customerParams.put("source", token);
        return Customer.create(customerParams);
    }

    private Customer getCustomer(String id) throws Exception {
        return Customer.retrieve(id);
    }

    @Override
    @Transactional
    public Map<String, Object> chargeStripeNewCard(String token, double amount, Long orderId, Principal principal) throws Exception {
        People people = commonService.getPeopleByPrincipal(principal);

        Map<String, Object> chargeParams = new HashMap<String, Object>();
        Map<String, Object> response = new HashMap<String, Object>();

        chargeParams.put("amount", (int) (amount * 100));
        chargeParams.put("currency", "SGD");
        chargeParams.put("source", token);
        try {
            Charge charge = Charge.create(chargeParams);
            String createdPaymentJSON = charge.toJson();

            if (charge != null) {
                Orders orders = ordersRepository.getOne(orderId.longValue());
                orders.setStatus(OrderStatus.NEW_ORDER);
                orders.setPaymentStatus(PaymentStatus.PAID);
                ordersRepository.save(orders);

                CustomerPaymentDTO customerPaymentDTO = new CustomerPaymentDTO();
                customerPaymentDTO.setPaymentMethodName("Paypal");
                customerPaymentDTO.setAmountExcludingTax(orders.getSubTotal());
                customerPaymentDTO.setTaxAmount(orders.getTaxAmount());
                customerPaymentDTO.setTransactionAmount(orders.getTotalDue());
                customerPaymentDTO.setLastEditedBy(people.getFullName());
                customerPaymentDTO.setLastEditedWhen(Instant.now());
                customerPaymentService.save(customerPaymentDTO);

                response.put("status", charge.getStatus());
//                response.put("payment", createdPaymentJSON);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return response;
    }

    @Override
    public Map<String, Object> chargeStripeCustomerCard(String customerId, int amount) throws Exception {
        String sourceCard = getCustomer(customerId).getDefaultSource();
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        Map<String, Object> response = new HashMap<String, Object>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "SGD");
        chargeParams.put("customer", customerId);
        chargeParams.put("source", sourceCard);

        try {
            Charge charge = Charge.create(chargeParams);
            String obj = charge.toJson();

            if (charge != null) {
                response.put("status", "success");
                response.put("payment", obj);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return response;
    }

    @Override
    public Map<String, Object> createPaypalPayment(String sum, String returnUrl, String cancelUrl) {
        Map<String, Object> response = new HashMap<String, Object>();
        Amount amount = new Amount();
        amount.setCurrency("SGD");
        amount.setTotal(sum);
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(returnUrl);
        payment.setRedirectUrls(redirectUrls);
        Payment createdPayment;
        try {
            String redirectUrl = "";
            APIContext context = new APIContext(Constants.PAYPAL_CLIENT_ID, Constants.PAYPAL_CLIENT_SECRET, "sandbox");
            createdPayment = payment.create(context);
            if (createdPayment != null) {
                List<Links> links = createdPayment.getLinks();
                for (Links link : links) {
                    if (link.getRel().equals("approval_url")) {
                        redirectUrl = link.getHref();
                        break;
                    }
                }
                response.put("status", "success");
                response.put("redirect_url", redirectUrl);
            }
        } catch (PayPalRESTException e) {
            System.out.println("Error happened during payment creation!");
        }
        return response;
    }

    @Override
    @Transactional
    public Map<String, Object> completePaypalPayment(String paymentId, String payerId, Long orderId, Principal principal) {
        People people = commonService.getPeopleByPrincipal(principal);

        Map<String, Object> response = new HashMap<String, Object>();
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        try {
            APIContext context = new APIContext(Constants.PAYPAL_CLIENT_ID, Constants.PAYPAL_CLIENT_SECRET, "sandbox");
            Payment createdPayment = payment.execute(context, paymentExecution);
            String createdPaymentJSON = createdPayment.toJSON();
            if (createdPayment != null) {
                Orders orders = ordersRepository.getOne(orderId.longValue());
                orders.setStatus(OrderStatus.NEW_ORDER);
                orders.setPaymentStatus(PaymentStatus.PAID);
                ordersRepository.save(orders);

                CustomerPaymentDTO customerPaymentDTO = new CustomerPaymentDTO();
                customerPaymentDTO.setPaymentMethodName("Paypal");
                customerPaymentDTO.setAmountExcludingTax(orders.getSubTotal());
                customerPaymentDTO.setTaxAmount(orders.getTaxAmount());
                customerPaymentDTO.setTransactionAmount(orders.getTotalDue());
                customerPaymentDTO.setLastEditedBy(people.getFullName());
                customerPaymentDTO.setLastEditedWhen(Instant.now());
                customerPaymentService.save(customerPaymentDTO);

                response.put("status", createdPayment.getState());
                //response.put("payment", createdPaymentJSON);
            }
        } catch (PayPalRESTException e) {
            System.err.println(e.getDetails());
        }

        return response;
    }

    @Override
    @Transactional
    public Map<String, Object> completePayment(Long orderId,Principal principal) {
        Map<String, Object> response = new HashMap<String, Object>();
        People people = commonService.getPeopleByPrincipal(principal);

        try {
            Orders orders = ordersRepository.getOne(orderId);
            orders.setStatus(OrderStatus.PENDING);
            orders.setPaymentStatus(PaymentStatus.PAID);
            ordersRepository.save(orders);

            CustomerPaymentDTO customerPaymentDTO = new CustomerPaymentDTO();
//            customerPaymentDTO.setPaymentMethodId(orders);
            customerPaymentDTO.setAmountExcludingTax(orders.getSubTotal() == null ? BigDecimal.ZERO : orders.getSubTotal());
            customerPaymentDTO.setTaxAmount(orders.getTaxAmount() == null? BigDecimal.ZERO : orders.getTaxAmount());
            customerPaymentDTO.setTransactionAmount(orders.getTotalDue());
            customerPaymentDTO.setLastEditedBy(people.getFullName());
            customerPaymentDTO.setLastEditedWhen(Instant.now());
            customerPaymentService.save(customerPaymentDTO);

            response.put("status", "succeeded");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return response;
    }

    @Override
    @Transactional
    public Map<String, Object> completeOrder(Long orderId,Principal principal) {
        Map<String, Object> response = new HashMap<String, Object>();
        People people = commonService.getPeopleByPrincipal(principal);

        try {
            Orders orders = ordersRepository.getOne(orderId);

            if(orders.getPaymentStatus() == PaymentStatus.PAID){
                orders.setStatus(OrderStatus.COMPLETED);
                ordersRepository.save(orders);
                response.put("status", "succeeded");
            }else{
                response.put("status", "failed");
            }

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return response;
    }

    private void generateInvoices(Customers customers,People people,Orders orders){
        invoicesExtendRepository.invoiceCustomerOrders(orders.getId(),people.getId(),people.getId());
//        Invoices invoices = new Invoices();
//        invoices.setBillToCustomer(customers);
//        invoices.setContactPerson(people);
////            invoices.setDeliveryMethod();
//        invoices.setCustomer(customers);
//        invoices.setInvoiceDate(Instant.now());
//        invoices.setLastEditedBy(people.getFullName());
//        invoices.setLastEditedWhen(Instant.now());
//        invoices.setIsCreditNote(false);
//        invoices.setOrder(orders);
//        invoices.setStatus(InvoiceStatus.ISSUED);
//        invoices.setTotalDryItems(orders.getOrderLineLists().size());
//        invoices.setTotalChillerItems(orders.getOrderLineLists().size());
//        invoices.setCustomerPurchaseOrderNumber(orders.getAccountNumber());
//        invoices = invoicesRepository.save(invoices);
//
//        Integer totalQuantity = 0;
//        for(OrderLines orderLine:orders.getOrderLineLists()){
//            InvoiceLines invoiceLines=new InvoiceLines();
//            invoiceLines.setInvoice(invoices);
//            invoiceLines.setStockItem(orderLine.getStockItem());
//            invoiceLines.setDescription(orderLine.getDescription());
//            invoiceLines.setPackageType(orderLine.getPackageType());
//            invoiceLines.setQuantity(orderLine.getPickedQuantity());
//            invoiceLines.setUnitPrice(orderLine.getUnitPrice());
//            invoiceLines.setTaxRate(orderLine.getTaxRate());
//
//            //Tax Amount
//            BigDecimal taxAmount = new BigDecimal(orderLine.getPickedQuantity()).multiply(orderLine.getUnitPrice()).multiply(orderLine.getTaxRate().divide(ONE_HUNDRED)).round(ROUND_VALUE);
//            invoiceLines.setTaxAmount(taxAmount);
//            //Line Profit
//            StockItemHoldings stockItemHoldings = stockItemHoldingsExtendRepository.findStockItemHoldingsByStockItemId(orderLine.getStockItem().getId());
//            invoiceLines.setLineProfit(new BigDecimal(orderLine.getPickedQuantity()).multiply(orderLine.getUnitPrice().subtract(stockItemHoldings.getLastCostPrice())).round(ROUND_VALUE));
//            //Extended Price
//            invoiceLines.setExtendedPrice(new BigDecimal(orderLine.getPickedQuantity()).multiply(orderLine.getUnitPrice()).round(ROUND_VALUE).add(taxAmount));
//            invoiceLines.setLastEditedBy(people.getFullName());
//            invoiceLines.setLastEditedWhen(Instant.now());
//            totalQuantity = totalQuantity + orderLine.getQuantity();
//            invoiceLinesRepository.save(invoiceLines);
//        }
    }
}

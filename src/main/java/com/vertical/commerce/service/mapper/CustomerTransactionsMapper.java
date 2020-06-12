package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.CustomerTransactionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerTransactions} and its DTO {@link CustomerTransactionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomersMapper.class, PaymentMethodsMapper.class, TransactionTypesMapper.class, InvoicesMapper.class, OrdersMapper.class})
public interface CustomerTransactionsMapper extends EntityMapper<CustomerTransactionsDTO, CustomerTransactions> {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "paymentMethod.id", target = "paymentMethodId")
    @Mapping(source = "transactionType.id", target = "transactionTypeId")
    @Mapping(source = "transactionType.name", target = "transactionTypeName")
    @Mapping(source = "invoice.id", target = "invoiceId")
    @Mapping(source = "order.id", target = "orderId")
    CustomerTransactionsDTO toDto(CustomerTransactions customerTransactions);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "paymentMethodId", target = "paymentMethod")
    @Mapping(source = "transactionTypeId", target = "transactionType")
    @Mapping(source = "invoiceId", target = "invoice")
    @Mapping(source = "orderId", target = "order")
    @Mapping(target = "customerPayment", ignore = true)
    CustomerTransactions toEntity(CustomerTransactionsDTO customerTransactionsDTO);

    default CustomerTransactions fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerTransactions customerTransactions = new CustomerTransactions();
        customerTransactions.setId(id);
        return customerTransactions;
    }
}

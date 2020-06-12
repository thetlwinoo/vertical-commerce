package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.InvoicesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Invoices} and its DTO {@link InvoicesDTO}.
 */
@Mapper(componentModel = "spring", uses = {PeopleMapper.class, CustomersMapper.class, DeliveryMethodsMapper.class, OrdersMapper.class, PaymentMethodsMapper.class})
public interface InvoicesMapper extends EntityMapper<InvoicesDTO, Invoices> {

    @Mapping(source = "contactPerson.id", target = "contactPersonId")
    @Mapping(source = "contactPerson.fullName", target = "contactPersonFullName")
    @Mapping(source = "salespersonPerson.id", target = "salespersonPersonId")
    @Mapping(source = "salespersonPerson.fullName", target = "salespersonPersonFullName")
    @Mapping(source = "packedByPerson.id", target = "packedByPersonId")
    @Mapping(source = "packedByPerson.fullName", target = "packedByPersonFullName")
    @Mapping(source = "accountsPerson.id", target = "accountsPersonId")
    @Mapping(source = "accountsPerson.fullName", target = "accountsPersonFullName")
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "billToCustomer.id", target = "billToCustomerId")
    @Mapping(source = "deliveryMethod.id", target = "deliveryMethodId")
    @Mapping(source = "deliveryMethod.name", target = "deliveryMethodName")
    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "paymentMethod.id", target = "paymentMethodId")
    InvoicesDTO toDto(Invoices invoices);

    @Mapping(target = "invoiceLineLists", ignore = true)
    @Mapping(target = "removeInvoiceLineList", ignore = true)
    @Mapping(source = "contactPersonId", target = "contactPerson")
    @Mapping(source = "salespersonPersonId", target = "salespersonPerson")
    @Mapping(source = "packedByPersonId", target = "packedByPerson")
    @Mapping(source = "accountsPersonId", target = "accountsPerson")
    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "billToCustomerId", target = "billToCustomer")
    @Mapping(source = "deliveryMethodId", target = "deliveryMethod")
    @Mapping(source = "orderId", target = "order")
    @Mapping(source = "paymentMethodId", target = "paymentMethod")
    Invoices toEntity(InvoicesDTO invoicesDTO);

    default Invoices fromId(Long id) {
        if (id == null) {
            return null;
        }
        Invoices invoices = new Invoices();
        invoices.setId(id);
        return invoices;
    }
}

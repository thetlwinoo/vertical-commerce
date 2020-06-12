package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.SupplierTransactionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SupplierTransactions} and its DTO {@link SupplierTransactionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {SuppliersMapper.class, TransactionTypesMapper.class, PurchaseOrdersMapper.class, OrdersMapper.class, InvoicesMapper.class, SupplierTransactionStatusMapper.class})
public interface SupplierTransactionsMapper extends EntityMapper<SupplierTransactionsDTO, SupplierTransactions> {

    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.name", target = "supplierName")
    @Mapping(source = "transactionType.id", target = "transactionTypeId")
    @Mapping(source = "transactionType.name", target = "transactionTypeName")
    @Mapping(source = "purchaseOrder.id", target = "purchaseOrderId")
    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "invoice.id", target = "invoiceId")
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.name", target = "statusName")
    SupplierTransactionsDTO toDto(SupplierTransactions supplierTransactions);

    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "transactionTypeId", target = "transactionType")
    @Mapping(source = "purchaseOrderId", target = "purchaseOrder")
    @Mapping(source = "orderId", target = "order")
    @Mapping(source = "invoiceId", target = "invoice")
    @Mapping(source = "statusId", target = "status")
    SupplierTransactions toEntity(SupplierTransactionsDTO supplierTransactionsDTO);

    default SupplierTransactions fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplierTransactions supplierTransactions = new SupplierTransactions();
        supplierTransactions.setId(id);
        return supplierTransactions;
    }
}

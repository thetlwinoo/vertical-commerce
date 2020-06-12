package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ReceiptsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Receipts} and its DTO {@link ReceiptsDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrdersMapper.class, InvoicesMapper.class})
public interface ReceiptsMapper extends EntityMapper<ReceiptsDTO, Receipts> {

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "invoice.id", target = "invoiceId")
    ReceiptsDTO toDto(Receipts receipts);

    @Mapping(source = "orderId", target = "order")
    @Mapping(source = "invoiceId", target = "invoice")
    Receipts toEntity(ReceiptsDTO receiptsDTO);

    default Receipts fromId(Long id) {
        if (id == null) {
            return null;
        }
        Receipts receipts = new Receipts();
        receipts.setId(id);
        return receipts;
    }
}

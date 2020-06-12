package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.SupplierTransactionStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SupplierTransactionStatus} and its DTO {@link SupplierTransactionStatusDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SupplierTransactionStatusMapper extends EntityMapper<SupplierTransactionStatusDTO, SupplierTransactionStatus> {



    default SupplierTransactionStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplierTransactionStatus supplierTransactionStatus = new SupplierTransactionStatus();
        supplierTransactionStatus.setId(id);
        return supplierTransactionStatus;
    }
}

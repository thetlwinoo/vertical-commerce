package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.PaymentMethodsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentMethods} and its DTO {@link PaymentMethodsDTO}.
 */
@Mapper(componentModel = "spring", uses = {PhotosMapper.class})
public interface PaymentMethodsMapper extends EntityMapper<PaymentMethodsDTO, PaymentMethods> {

    @Mapping(source = "icon.id", target = "iconId")
    @Mapping(source = "icon.thumbnailUrl", target = "iconThumbnailUrl")
    PaymentMethodsDTO toDto(PaymentMethods paymentMethods);

    @Mapping(source = "iconId", target = "icon")
    PaymentMethods toEntity(PaymentMethodsDTO paymentMethodsDTO);

    default PaymentMethods fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentMethods paymentMethods = new PaymentMethods();
        paymentMethods.setId(id);
        return paymentMethods;
    }
}

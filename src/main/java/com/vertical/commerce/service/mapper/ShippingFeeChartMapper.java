package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ShippingFeeChartDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShippingFeeChart} and its DTO {@link ShippingFeeChartDTO}.
 */
@Mapper(componentModel = "spring", uses = {ZoneMapper.class, DeliveryMethodsMapper.class})
public interface ShippingFeeChartMapper extends EntityMapper<ShippingFeeChartDTO, ShippingFeeChart> {

    @Mapping(source = "sourceZone.id", target = "sourceZoneId")
    @Mapping(source = "sourceZone.code", target = "sourceZoneCode")
    @Mapping(source = "destinationZone.id", target = "destinationZoneId")
    @Mapping(source = "destinationZone.code", target = "destinationZoneCode")
    @Mapping(source = "deliveryMethod.id", target = "deliveryMethodId")
    @Mapping(source = "deliveryMethod.name", target = "deliveryMethodName")
    ShippingFeeChartDTO toDto(ShippingFeeChart shippingFeeChart);

    @Mapping(source = "sourceZoneId", target = "sourceZone")
    @Mapping(source = "destinationZoneId", target = "destinationZone")
    @Mapping(source = "deliveryMethodId", target = "deliveryMethod")
    ShippingFeeChart toEntity(ShippingFeeChartDTO shippingFeeChartDTO);

    default ShippingFeeChart fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShippingFeeChart shippingFeeChart = new ShippingFeeChart();
        shippingFeeChart.setId(id);
        return shippingFeeChart;
    }
}

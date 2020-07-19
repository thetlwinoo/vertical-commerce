package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ShippingFeeChartDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShippingFeeChart} and its DTO {@link ShippingFeeChartDTO}.
 */
@Mapper(componentModel = "spring", uses = {TownsMapper.class, DeliveryMethodsMapper.class})
public interface ShippingFeeChartMapper extends EntityMapper<ShippingFeeChartDTO, ShippingFeeChart> {

    @Mapping(source = "sourceTown.id", target = "sourceTownId")
    @Mapping(source = "sourceTown.name", target = "sourceTownName")
    @Mapping(source = "destinationTown.id", target = "destinationTownId")
    @Mapping(source = "destinationTown.name", target = "destinationTownName")
    @Mapping(source = "deliveryMethod.id", target = "deliveryMethodId")
    @Mapping(source = "deliveryMethod.name", target = "deliveryMethodName")
    ShippingFeeChartDTO toDto(ShippingFeeChart shippingFeeChart);

    @Mapping(source = "sourceTownId", target = "sourceTown")
    @Mapping(source = "destinationTownId", target = "destinationTown")
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

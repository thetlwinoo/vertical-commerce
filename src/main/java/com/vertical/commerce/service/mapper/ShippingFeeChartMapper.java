package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ShippingFeeChartDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShippingFeeChart} and its DTO {@link ShippingFeeChartDTO}.
 */
@Mapper(componentModel = "spring", uses = {TownshipsMapper.class, DeliveryMethodsMapper.class})
public interface ShippingFeeChartMapper extends EntityMapper<ShippingFeeChartDTO, ShippingFeeChart> {

    @Mapping(source = "sourceTownship.id", target = "sourceTownshipId")
    @Mapping(source = "sourceTownship.name", target = "sourceTownshipName")
    @Mapping(source = "destinationTownship.id", target = "destinationTownshipId")
    @Mapping(source = "destinationTownship.name", target = "destinationTownshipName")
    @Mapping(source = "deliveryMethod.id", target = "deliveryMethodId")
    @Mapping(source = "deliveryMethod.name", target = "deliveryMethodName")
    ShippingFeeChartDTO toDto(ShippingFeeChart shippingFeeChart);

    @Mapping(source = "sourceTownshipId", target = "sourceTownship")
    @Mapping(source = "destinationTownshipId", target = "destinationTownship")
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

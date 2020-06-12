package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.SuppliersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Suppliers} and its DTO {@link SuppliersDTO}.
 */
@Mapper(componentModel = "spring", uses = {PeopleMapper.class, SupplierCategoriesMapper.class, DeliveryMethodsMapper.class, CitiesMapper.class})
public interface SuppliersMapper extends EntityMapper<SuppliersDTO, Suppliers> {

    @Mapping(source = "people.id", target = "peopleId")
    @Mapping(source = "people.fullName", target = "peopleFullName")
    @Mapping(source = "supplierCategory.id", target = "supplierCategoryId")
    @Mapping(source = "supplierCategory.name", target = "supplierCategoryName")
    @Mapping(source = "deliveryMethod.id", target = "deliveryMethodId")
    @Mapping(source = "deliveryMethod.name", target = "deliveryMethodName")
    @Mapping(source = "deliveryCity.id", target = "deliveryCityId")
    @Mapping(source = "deliveryCity.name", target = "deliveryCityName")
    @Mapping(source = "postalCity.id", target = "postalCityId")
    @Mapping(source = "postalCity.name", target = "postalCityName")
    SuppliersDTO toDto(Suppliers suppliers);

    @Mapping(source = "peopleId", target = "people")
    @Mapping(source = "supplierCategoryId", target = "supplierCategory")
    @Mapping(source = "deliveryMethodId", target = "deliveryMethod")
    @Mapping(source = "deliveryCityId", target = "deliveryCity")
    @Mapping(source = "postalCityId", target = "postalCity")
    Suppliers toEntity(SuppliersDTO suppliersDTO);

    default Suppliers fromId(Long id) {
        if (id == null) {
            return null;
        }
        Suppliers suppliers = new Suppliers();
        suppliers.setId(id);
        return suppliers;
    }
}

package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.SuppliersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Suppliers} and its DTO {@link SuppliersDTO}.
 */
@Mapper(componentModel = "spring", uses = {PeopleMapper.class, SupplierCategoriesMapper.class, AddressesMapper.class, DeliveryMethodsMapper.class})
public interface SuppliersMapper extends EntityMapper<SuppliersDTO, Suppliers> {

    @Mapping(source = "people.id", target = "peopleId")
    @Mapping(source = "people.fullName", target = "peopleFullName")
    @Mapping(source = "supplierCategory.id", target = "supplierCategoryId")
    @Mapping(source = "supplierCategory.name", target = "supplierCategoryName")
    @Mapping(source = "pickupAddress.id", target = "pickupAddressId")
    @Mapping(source = "headOfficeAddress.id", target = "headOfficeAddressId")
    SuppliersDTO toDto(Suppliers suppliers);

    @Mapping(source = "peopleId", target = "people")
    @Mapping(source = "supplierCategoryId", target = "supplierCategory")
    @Mapping(source = "pickupAddressId", target = "pickupAddress")
    @Mapping(source = "headOfficeAddressId", target = "headOfficeAddress")
    @Mapping(target = "removeDeliveryMethod", ignore = true)
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

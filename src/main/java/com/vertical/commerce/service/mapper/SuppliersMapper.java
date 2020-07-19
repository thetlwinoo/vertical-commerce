package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.SuppliersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Suppliers} and its DTO {@link SuppliersDTO}.
 */
@Mapper(componentModel = "spring", uses = {SupplierCategoriesMapper.class, AddressesMapper.class, PeopleMapper.class, DeliveryMethodsMapper.class})
public interface SuppliersMapper extends EntityMapper<SuppliersDTO, Suppliers> {

    @Mapping(source = "supplierCategory.id", target = "supplierCategoryId")
    @Mapping(source = "supplierCategory.name", target = "supplierCategoryName")
    @Mapping(source = "pickupAddress.id", target = "pickupAddressId")
    @Mapping(source = "headOfficeAddress.id", target = "headOfficeAddressId")
    @Mapping(source = "returnAddress.id", target = "returnAddressId")
    @Mapping(source = "contactPerson.id", target = "contactPersonId")
    @Mapping(source = "contactPerson.fullName", target = "contactPersonFullName")
    SuppliersDTO toDto(Suppliers suppliers);

    @Mapping(target = "bannerLists", ignore = true)
    @Mapping(target = "removeBannerList", ignore = true)
    @Mapping(target = "documentLists", ignore = true)
    @Mapping(target = "removeDocumentList", ignore = true)
    @Mapping(source = "supplierCategoryId", target = "supplierCategory")
    @Mapping(source = "pickupAddressId", target = "pickupAddress")
    @Mapping(source = "headOfficeAddressId", target = "headOfficeAddress")
    @Mapping(source = "returnAddressId", target = "returnAddress")
    @Mapping(source = "contactPersonId", target = "contactPerson")
    @Mapping(target = "removeDeliveryMethod", ignore = true)
    @Mapping(target = "people", ignore = true)
    @Mapping(target = "removePeople", ignore = true)
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

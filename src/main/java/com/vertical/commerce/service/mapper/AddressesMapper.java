package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.AddressesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Addresses} and its DTO {@link AddressesDTO}.
 */
@Mapper(componentModel = "spring", uses = {RegionsMapper.class, CitiesMapper.class, TownshipsMapper.class, AddressTypesMapper.class, CustomersMapper.class, SuppliersMapper.class})
public interface AddressesMapper extends EntityMapper<AddressesDTO, Addresses> {

    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "region.name", target = "regionName")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    @Mapping(source = "township.id", target = "townshipId")
    @Mapping(source = "township.name", target = "townshipName")
    @Mapping(source = "addressType.id", target = "addressTypeId")
    @Mapping(source = "addressType.name", target = "addressTypeName")
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.name", target = "customerName")
    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.name", target = "supplierName")
    AddressesDTO toDto(Addresses addresses);

    @Mapping(source = "regionId", target = "region")
    @Mapping(source = "cityId", target = "city")
    @Mapping(source = "townshipId", target = "township")
    @Mapping(source = "addressTypeId", target = "addressType")
    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "supplierId", target = "supplier")
    Addresses toEntity(AddressesDTO addressesDTO);

    default Addresses fromId(Long id) {
        if (id == null) {
            return null;
        }
        Addresses addresses = new Addresses();
        addresses.setId(id);
        return addresses;
    }
}

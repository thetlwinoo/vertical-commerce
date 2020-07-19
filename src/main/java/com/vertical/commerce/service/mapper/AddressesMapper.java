package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.AddressesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Addresses} and its DTO {@link AddressesDTO}.
 */
@Mapper(componentModel = "spring", uses = {RegionsMapper.class, CitiesMapper.class, TownshipsMapper.class, TownsMapper.class, AddressTypesMapper.class, CustomersMapper.class, SuppliersMapper.class})
public interface AddressesMapper extends EntityMapper<AddressesDTO, Addresses> {

    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "region.name", target = "regionName")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    @Mapping(source = "township.id", target = "townshipId")
    @Mapping(source = "township.name", target = "townshipName")
    @Mapping(source = "town.id", target = "townId")
    @Mapping(source = "town.name", target = "townName")
    @Mapping(source = "addressType.id", target = "addressTypeId")
    @Mapping(source = "addressType.name", target = "addressTypeName")
    @Mapping(source = "customerAddress.id", target = "customerAddressId")
    @Mapping(source = "customerAddress.name", target = "customerAddressName")
    @Mapping(source = "supplierAddress.id", target = "supplierAddressId")
    @Mapping(source = "supplierAddress.name", target = "supplierAddressName")
    AddressesDTO toDto(Addresses addresses);

    @Mapping(source = "regionId", target = "region")
    @Mapping(source = "cityId", target = "city")
    @Mapping(source = "townshipId", target = "township")
    @Mapping(source = "townId", target = "town")
    @Mapping(source = "addressTypeId", target = "addressType")
    @Mapping(source = "customerAddressId", target = "customerAddress")
    @Mapping(source = "supplierAddressId", target = "supplierAddress")
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

package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.AddressesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Addresses} and its DTO {@link AddressesDTO}.
 */
@Mapper(componentModel = "spring", uses = {ZoneMapper.class, AddressTypesMapper.class, PeopleMapper.class})
public interface AddressesMapper extends EntityMapper<AddressesDTO, Addresses> {

    @Mapping(source = "zone.id", target = "zoneId")
    @Mapping(source = "zone.code", target = "zoneCode")
    @Mapping(source = "addressType.id", target = "addressTypeId")
    @Mapping(source = "addressType.name", target = "addressTypeName")
    @Mapping(source = "person.id", target = "personId")
    AddressesDTO toDto(Addresses addresses);

    @Mapping(source = "zoneId", target = "zone")
    @Mapping(source = "addressTypeId", target = "addressType")
    @Mapping(source = "personId", target = "person")
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

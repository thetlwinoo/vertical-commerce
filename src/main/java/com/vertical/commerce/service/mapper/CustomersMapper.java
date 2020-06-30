package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.CustomersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Customers} and its DTO {@link CustomersDTO}.
 */
@Mapper(componentModel = "spring", uses = {PeopleMapper.class, DeliveryMethodsMapper.class, AddressesMapper.class})
public interface CustomersMapper extends EntityMapper<CustomersDTO, Customers> {

    @Mapping(source = "people.id", target = "peopleId")
    @Mapping(source = "people.fullName", target = "peopleFullName")
    @Mapping(source = "deliveryMethod.id", target = "deliveryMethodId")
    @Mapping(source = "deliveryMethod.name", target = "deliveryMethodName")
    @Mapping(source = "deliveryAddress.id", target = "deliveryAddressId")
    @Mapping(source = "billToAddress.id", target = "billToAddressId")
    CustomersDTO toDto(Customers customers);

    @Mapping(source = "peopleId", target = "people")
    @Mapping(source = "deliveryMethodId", target = "deliveryMethod")
    @Mapping(source = "deliveryAddressId", target = "deliveryAddress")
    @Mapping(source = "billToAddressId", target = "billToAddress")
    Customers toEntity(CustomersDTO customersDTO);

    default Customers fromId(Long id) {
        if (id == null) {
            return null;
        }
        Customers customers = new Customers();
        customers.setId(id);
        return customers;
    }
}

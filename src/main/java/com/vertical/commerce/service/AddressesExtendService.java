package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.AddressesDTO;

import java.security.Principal;
import java.util.List;

public interface AddressesExtendService {
    List<AddressesDTO> fetchAddresses(Principal principal);

    void clearDefaultAddress(Principal principal);

    AddressesDTO setDefaultAddress(Long addressId,Boolean isShippingAddress, Principal principal);

    AddressesDTO crateAddresses(AddressesDTO addressesDTO,Boolean isShippingAddress, Principal principal);

    AddressesDTO updateAddresses(AddressesDTO addressesDTO,Boolean isShippingAddress, Principal principal);
}

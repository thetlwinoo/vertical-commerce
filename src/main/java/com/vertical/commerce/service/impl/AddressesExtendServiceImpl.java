package com.vertical.commerce.service.impl;

import com.vertical.commerce.domain.Addresses;
import com.vertical.commerce.domain.Customers;
import com.vertical.commerce.domain.People;
import com.vertical.commerce.domain.Zone;
import com.vertical.commerce.repository.AddressesExtendRepository;
import com.vertical.commerce.repository.CustomersRepository;
import com.vertical.commerce.service.AddressesExtendService;
import com.vertical.commerce.service.AddressesService;
import com.vertical.commerce.service.CommonService;
import com.vertical.commerce.service.dto.AddressesDTO;
import com.vertical.commerce.service.mapper.AddressesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AddressesExtendServiceImpl implements AddressesExtendService {

    private final Logger log = LoggerFactory.getLogger(AddressesExtendServiceImpl.class);
    private final AddressesExtendRepository addressesExtendRepository;
    private final AddressesService addressesService;
    private final AddressesMapper addressesMapper;
    private final CommonService commonService;
    private final CustomersRepository customersRepository;

    public AddressesExtendServiceImpl(AddressesExtendRepository addressesExtendRepository, AddressesService addressesService, AddressesMapper addressesMapper, CommonService commonService, CustomersRepository customersRepository) {
        this.addressesExtendRepository = addressesExtendRepository;
        this.addressesService = addressesService;
        this.addressesMapper = addressesMapper;
        this.commonService = commonService;
        this.customersRepository = customersRepository;
    }

    @Override
    public List<AddressesDTO> fetchAddresses(Principal principal) {
        People people = commonService.getPeopleByPrincipal(principal);
        return addressesExtendRepository.findAllByPersonId(people.getId()).stream()
            .map(addressesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public void clearDefaultAddress(Principal principal) {
        People people = commonService.getPeopleByPrincipal(principal);
        List<Addresses> addressesList = addressesExtendRepository.findAllByPersonId(people.getId());

        for (Addresses addresses : addressesList) {
            addresses.setDefaultInd(false);
            addressesExtendRepository.save(addresses);
        }
    }

    @Override
    public AddressesDTO setDefaultAddress(Long addressId,Boolean isShippingAddress, Principal principal) {
        People people = commonService.getPeopleByPrincipal(principal);
        Customers customers = commonService.getCustomerByPrincipal(principal);
        List<Addresses> addressesList = addressesExtendRepository.findAllByPersonId(people.getId());
        Addresses defaultAddress = new Addresses();

        for (Addresses addresses : addressesList) {
            if (addresses.getId().equals(addressId)) {
                addresses.setDefaultInd(true);

                if(isShippingAddress){
                    customers.setDeliveryAddress(addresses);
                    if(customers.getBillToAddress() == null){
                        customers.setBillToAddress(addresses);
                    }
                }else{
                    customers.setBillToAddress(addresses);
                }

                customers.setBillToAddressSameAsDeliveryAddress(customers.getDeliveryAddress().getId().equals(customers.getBillToAddress().getId()));
                defaultAddress = addresses;
            } else {
                addresses.setDefaultInd(false);
            }

            addressesExtendRepository.save(addresses);
        }

        return addressesMapper.toDto(defaultAddress);
    }

    @Override
    public AddressesDTO crateAddresses(AddressesDTO addressesDTO,Boolean isShippingAddress, Principal principal) {
        People people = commonService.getPeopleByPrincipal(principal);
        if (addressesDTO.isDefaultInd()) {
            this.clearDefaultAddress(principal);
        }

        Zone zone = commonService.getZoneEntity(addressesDTO.getZoneId(),null);
        addressesDTO.setCity(commonService.getCitiesEntity(zone.getCity().getId(),null).getName());
        addressesDTO.setPostalCode(zone.getCode());

        addressesDTO.setPersonId(people.getId());
        AddressesDTO result = addressesService.save(addressesDTO);
        Addresses savedAddress = addressesMapper.toEntity(result);

        if(result.isDefaultInd()){
            Customers customers = commonService.getCustomerByPrincipal(principal);

            if(isShippingAddress){
                customers.setDeliveryAddress(savedAddress);
                if(customers.getBillToAddress() == null){
                    customers.setBillToAddress(savedAddress);
                }
            }else{
                customers.setBillToAddress(savedAddress);
            }

            customers.setBillToAddressSameAsDeliveryAddress(customers.getDeliveryAddress().getId().equals(customers.getBillToAddress().getId()));
            customersRepository.save(customers);
        }
        return result;
    }

    @Override
    public AddressesDTO updateAddresses(AddressesDTO addressesDTO,Boolean isShippingAddress, Principal principal) {
        if (addressesDTO.isDefaultInd()) {
            this.clearDefaultAddress(principal);
        }

        Zone zone = commonService.getZoneEntity(addressesDTO.getZoneId(),null);
        addressesDTO.setCity(commonService.getCitiesEntity(zone.getCity().getId(),null).getName());
        addressesDTO.setPostalCode(zone.getCode());

        AddressesDTO result = addressesService.save(addressesDTO);
        Addresses savedAddress = addressesMapper.toEntity(result);

        if(result.isDefaultInd()){
            Customers customers = commonService.getCustomerByPrincipal(principal);

            if(isShippingAddress){
                customers.setDeliveryAddress(savedAddress);
                if(customers.getBillToAddress() == null){ customers.setBillToAddress(savedAddress);customers.setBillToAddressSameAsDeliveryAddress(true);
                }
            }else{
                customers.setBillToAddress(savedAddress);
            }

            customers.setBillToAddressSameAsDeliveryAddress(customers.getDeliveryAddress().getId().equals(customers.getBillToAddress().getId()));
            customersRepository.save(customers);
        }

        return result;
    }
}

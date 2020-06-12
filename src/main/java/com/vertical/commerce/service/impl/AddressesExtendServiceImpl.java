package com.vertical.commerce.service.impl;

import com.vertical.commerce.domain.Addresses;
import com.vertical.commerce.domain.People;
import com.vertical.commerce.repository.AddressesExtendRepository;
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

    public AddressesExtendServiceImpl(AddressesExtendRepository addressesExtendRepository, AddressesService addressesService, AddressesMapper addressesMapper, CommonService commonService) {
        this.addressesExtendRepository = addressesExtendRepository;
        this.addressesService = addressesService;
        this.addressesMapper = addressesMapper;
        this.commonService = commonService;
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
    public void setDefaultAddress(Long addressId, Principal principal) {
        People people = commonService.getPeopleByPrincipal(principal);
        List<Addresses> addressesList = addressesExtendRepository.findAllByPersonId(people.getId());

        for (Addresses addresses : addressesList) {
            if (addresses.getId().equals(addressId)) {
                addresses.setDefaultInd(true);
            } else {
                addresses.setDefaultInd(false);
            }

            addressesExtendRepository.save(addresses);
        }
    }

    @Override
    public AddressesDTO crateAddresses(AddressesDTO addressesDTO, Principal principal) {
        People people = commonService.getPeopleByPrincipal(principal);
        if (addressesDTO.isDefaultInd()) {
            this.clearDefaultAddress(principal);
        }

        addressesDTO.setPersonId(people.getId());
        AddressesDTO result = addressesService.save(addressesDTO);

        return result;
    }

    @Override
    public AddressesDTO updateAddresses(AddressesDTO addressesDTO, Principal principal) {
        if (addressesDTO.isDefaultInd()) {
            this.clearDefaultAddress(principal);
        }
        AddressesDTO result = addressesService.save(addressesDTO);

        return result;
    }
}

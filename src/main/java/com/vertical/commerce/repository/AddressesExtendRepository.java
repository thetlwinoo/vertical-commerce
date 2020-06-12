package com.vertical.commerce.repository;

import com.vertical.commerce.domain.Addresses;

import java.util.List;

public interface AddressesExtendRepository extends AddressesRepository {
    List<Addresses> findAllByPersonId(Long id);
}

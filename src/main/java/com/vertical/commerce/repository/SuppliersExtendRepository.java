package com.vertical.commerce.repository;

import com.vertical.commerce.domain.Suppliers;

public interface SuppliersExtendRepository extends SuppliersRepository {
    Suppliers findSuppliersByPeopleId(Long id);
}

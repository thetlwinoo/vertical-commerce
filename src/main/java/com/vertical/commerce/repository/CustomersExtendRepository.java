package com.vertical.commerce.repository;

import com.vertical.commerce.domain.Customers;

public interface CustomersExtendRepository extends CustomersRepository {
    Customers findCustomersByPeopleId(Long id);
}

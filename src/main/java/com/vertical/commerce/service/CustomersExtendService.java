package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.CustomersDTO;

import java.security.Principal;

public interface CustomersExtendService {
    CustomersDTO createCustomerAccount(Principal principal);
}

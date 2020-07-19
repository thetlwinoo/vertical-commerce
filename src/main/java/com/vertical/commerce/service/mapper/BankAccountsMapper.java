package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.BankAccountsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankAccounts} and its DTO {@link BankAccountsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BankAccountsMapper extends EntityMapper<BankAccountsDTO, BankAccounts> {



    default BankAccounts fromId(Long id) {
        if (id == null) {
            return null;
        }
        BankAccounts bankAccounts = new BankAccounts();
        bankAccounts.setId(id);
        return bankAccounts;
    }
}

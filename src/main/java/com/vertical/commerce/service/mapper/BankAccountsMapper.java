package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.BankAccountsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankAccounts} and its DTO {@link BankAccountsDTO}.
 */
@Mapper(componentModel = "spring", uses = {PhotosMapper.class})
public interface BankAccountsMapper extends EntityMapper<BankAccountsDTO, BankAccounts> {

    @Mapping(source = "logo.id", target = "logoId")
    @Mapping(source = "logo.thumbnailUrl", target = "logoThumbnailUrl")
    BankAccountsDTO toDto(BankAccounts bankAccounts);

    @Mapping(source = "logoId", target = "logo")
    BankAccounts toEntity(BankAccountsDTO bankAccountsDTO);

    default BankAccounts fromId(Long id) {
        if (id == null) {
            return null;
        }
        BankAccounts bankAccounts = new BankAccounts();
        bankAccounts.setId(id);
        return bankAccounts;
    }
}

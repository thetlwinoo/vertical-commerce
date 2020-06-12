package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.BankAccountsService;
import com.vertical.commerce.domain.BankAccounts;
import com.vertical.commerce.repository.BankAccountsRepository;
import com.vertical.commerce.service.dto.BankAccountsDTO;
import com.vertical.commerce.service.mapper.BankAccountsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BankAccounts}.
 */
@Service
@Transactional
public class BankAccountsServiceImpl implements BankAccountsService {

    private final Logger log = LoggerFactory.getLogger(BankAccountsServiceImpl.class);

    private final BankAccountsRepository bankAccountsRepository;

    private final BankAccountsMapper bankAccountsMapper;

    public BankAccountsServiceImpl(BankAccountsRepository bankAccountsRepository, BankAccountsMapper bankAccountsMapper) {
        this.bankAccountsRepository = bankAccountsRepository;
        this.bankAccountsMapper = bankAccountsMapper;
    }

    /**
     * Save a bankAccounts.
     *
     * @param bankAccountsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BankAccountsDTO save(BankAccountsDTO bankAccountsDTO) {
        log.debug("Request to save BankAccounts : {}", bankAccountsDTO);
        BankAccounts bankAccounts = bankAccountsMapper.toEntity(bankAccountsDTO);
        bankAccounts = bankAccountsRepository.save(bankAccounts);
        return bankAccountsMapper.toDto(bankAccounts);
    }

    /**
     * Get all the bankAccounts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BankAccountsDTO> findAll() {
        log.debug("Request to get all BankAccounts");
        return bankAccountsRepository.findAll().stream()
            .map(bankAccountsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one bankAccounts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BankAccountsDTO> findOne(Long id) {
        log.debug("Request to get BankAccounts : {}", id);
        return bankAccountsRepository.findById(id)
            .map(bankAccountsMapper::toDto);
    }

    /**
     * Delete the bankAccounts by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BankAccounts : {}", id);

        bankAccountsRepository.deleteById(id);
    }
}

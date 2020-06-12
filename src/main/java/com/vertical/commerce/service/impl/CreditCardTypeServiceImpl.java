package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.CreditCardTypeService;
import com.vertical.commerce.domain.CreditCardType;
import com.vertical.commerce.repository.CreditCardTypeRepository;
import com.vertical.commerce.service.dto.CreditCardTypeDTO;
import com.vertical.commerce.service.mapper.CreditCardTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CreditCardType}.
 */
@Service
@Transactional
public class CreditCardTypeServiceImpl implements CreditCardTypeService {

    private final Logger log = LoggerFactory.getLogger(CreditCardTypeServiceImpl.class);

    private final CreditCardTypeRepository creditCardTypeRepository;

    private final CreditCardTypeMapper creditCardTypeMapper;

    public CreditCardTypeServiceImpl(CreditCardTypeRepository creditCardTypeRepository, CreditCardTypeMapper creditCardTypeMapper) {
        this.creditCardTypeRepository = creditCardTypeRepository;
        this.creditCardTypeMapper = creditCardTypeMapper;
    }

    /**
     * Save a creditCardType.
     *
     * @param creditCardTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CreditCardTypeDTO save(CreditCardTypeDTO creditCardTypeDTO) {
        log.debug("Request to save CreditCardType : {}", creditCardTypeDTO);
        CreditCardType creditCardType = creditCardTypeMapper.toEntity(creditCardTypeDTO);
        creditCardType = creditCardTypeRepository.save(creditCardType);
        return creditCardTypeMapper.toDto(creditCardType);
    }

    /**
     * Get all the creditCardTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CreditCardTypeDTO> findAll() {
        log.debug("Request to get all CreditCardTypes");
        return creditCardTypeRepository.findAll().stream()
            .map(creditCardTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one creditCardType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CreditCardTypeDTO> findOne(Long id) {
        log.debug("Request to get CreditCardType : {}", id);
        return creditCardTypeRepository.findById(id)
            .map(creditCardTypeMapper::toDto);
    }

    /**
     * Delete the creditCardType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CreditCardType : {}", id);

        creditCardTypeRepository.deleteById(id);
    }
}

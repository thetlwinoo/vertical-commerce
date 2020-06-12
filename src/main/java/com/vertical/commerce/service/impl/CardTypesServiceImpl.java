package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.CardTypesService;
import com.vertical.commerce.domain.CardTypes;
import com.vertical.commerce.repository.CardTypesRepository;
import com.vertical.commerce.service.dto.CardTypesDTO;
import com.vertical.commerce.service.mapper.CardTypesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CardTypes}.
 */
@Service
@Transactional
public class CardTypesServiceImpl implements CardTypesService {

    private final Logger log = LoggerFactory.getLogger(CardTypesServiceImpl.class);

    private final CardTypesRepository cardTypesRepository;

    private final CardTypesMapper cardTypesMapper;

    public CardTypesServiceImpl(CardTypesRepository cardTypesRepository, CardTypesMapper cardTypesMapper) {
        this.cardTypesRepository = cardTypesRepository;
        this.cardTypesMapper = cardTypesMapper;
    }

    /**
     * Save a cardTypes.
     *
     * @param cardTypesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CardTypesDTO save(CardTypesDTO cardTypesDTO) {
        log.debug("Request to save CardTypes : {}", cardTypesDTO);
        CardTypes cardTypes = cardTypesMapper.toEntity(cardTypesDTO);
        cardTypes = cardTypesRepository.save(cardTypes);
        return cardTypesMapper.toDto(cardTypes);
    }

    /**
     * Get all the cardTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CardTypesDTO> findAll() {
        log.debug("Request to get all CardTypes");
        return cardTypesRepository.findAll().stream()
            .map(cardTypesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one cardTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CardTypesDTO> findOne(Long id) {
        log.debug("Request to get CardTypes : {}", id);
        return cardTypesRepository.findById(id)
            .map(cardTypesMapper::toDto);
    }

    /**
     * Delete the cardTypes by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CardTypes : {}", id);

        cardTypesRepository.deleteById(id);
    }
}

package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.CardTypeCreditCardsService;
import com.vertical.commerce.domain.CardTypeCreditCards;
import com.vertical.commerce.repository.CardTypeCreditCardsRepository;
import com.vertical.commerce.service.dto.CardTypeCreditCardsDTO;
import com.vertical.commerce.service.mapper.CardTypeCreditCardsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CardTypeCreditCards}.
 */
@Service
@Transactional
public class CardTypeCreditCardsServiceImpl implements CardTypeCreditCardsService {

    private final Logger log = LoggerFactory.getLogger(CardTypeCreditCardsServiceImpl.class);

    private final CardTypeCreditCardsRepository cardTypeCreditCardsRepository;

    private final CardTypeCreditCardsMapper cardTypeCreditCardsMapper;

    public CardTypeCreditCardsServiceImpl(CardTypeCreditCardsRepository cardTypeCreditCardsRepository, CardTypeCreditCardsMapper cardTypeCreditCardsMapper) {
        this.cardTypeCreditCardsRepository = cardTypeCreditCardsRepository;
        this.cardTypeCreditCardsMapper = cardTypeCreditCardsMapper;
    }

    /**
     * Save a cardTypeCreditCards.
     *
     * @param cardTypeCreditCardsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CardTypeCreditCardsDTO save(CardTypeCreditCardsDTO cardTypeCreditCardsDTO) {
        log.debug("Request to save CardTypeCreditCards : {}", cardTypeCreditCardsDTO);
        CardTypeCreditCards cardTypeCreditCards = cardTypeCreditCardsMapper.toEntity(cardTypeCreditCardsDTO);
        cardTypeCreditCards = cardTypeCreditCardsRepository.save(cardTypeCreditCards);
        return cardTypeCreditCardsMapper.toDto(cardTypeCreditCards);
    }

    /**
     * Get all the cardTypeCreditCards.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CardTypeCreditCardsDTO> findAll() {
        log.debug("Request to get all CardTypeCreditCards");
        return cardTypeCreditCardsRepository.findAll().stream()
            .map(cardTypeCreditCardsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one cardTypeCreditCards by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CardTypeCreditCardsDTO> findOne(Long id) {
        log.debug("Request to get CardTypeCreditCards : {}", id);
        return cardTypeCreditCardsRepository.findById(id)
            .map(cardTypeCreditCardsMapper::toDto);
    }

    /**
     * Delete the cardTypeCreditCards by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CardTypeCreditCards : {}", id);

        cardTypeCreditCardsRepository.deleteById(id);
    }
}

package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.CardsService;
import com.vertical.commerce.domain.Cards;
import com.vertical.commerce.repository.CardsRepository;
import com.vertical.commerce.service.dto.CardsDTO;
import com.vertical.commerce.service.mapper.CardsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Cards}.
 */
@Service
@Transactional
public class CardsServiceImpl implements CardsService {

    private final Logger log = LoggerFactory.getLogger(CardsServiceImpl.class);

    private final CardsRepository cardsRepository;

    private final CardsMapper cardsMapper;

    public CardsServiceImpl(CardsRepository cardsRepository, CardsMapper cardsMapper) {
        this.cardsRepository = cardsRepository;
        this.cardsMapper = cardsMapper;
    }

    /**
     * Save a cards.
     *
     * @param cardsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CardsDTO save(CardsDTO cardsDTO) {
        log.debug("Request to save Cards : {}", cardsDTO);
        Cards cards = cardsMapper.toEntity(cardsDTO);
        cards = cardsRepository.save(cards);
        return cardsMapper.toDto(cards);
    }

    /**
     * Get all the cards.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CardsDTO> findAll() {
        log.debug("Request to get all Cards");
        return cardsRepository.findAll().stream()
            .map(cardsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one cards by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CardsDTO> findOne(Long id) {
        log.debug("Request to get Cards : {}", id);
        return cardsRepository.findById(id)
            .map(cardsMapper::toDto);
    }

    /**
     * Delete the cards by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cards : {}", id);

        cardsRepository.deleteById(id);
    }
}

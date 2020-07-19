package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.DiscountsService;
import com.vertical.commerce.domain.Discounts;
import com.vertical.commerce.repository.DiscountsRepository;
import com.vertical.commerce.service.dto.DiscountsDTO;
import com.vertical.commerce.service.mapper.DiscountsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Discounts}.
 */
@Service
@Transactional
public class DiscountsServiceImpl implements DiscountsService {

    private final Logger log = LoggerFactory.getLogger(DiscountsServiceImpl.class);

    private final DiscountsRepository discountsRepository;

    private final DiscountsMapper discountsMapper;

    public DiscountsServiceImpl(DiscountsRepository discountsRepository, DiscountsMapper discountsMapper) {
        this.discountsRepository = discountsRepository;
        this.discountsMapper = discountsMapper;
    }

    /**
     * Save a discounts.
     *
     * @param discountsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DiscountsDTO save(DiscountsDTO discountsDTO) {
        log.debug("Request to save Discounts : {}", discountsDTO);
        Discounts discounts = discountsMapper.toEntity(discountsDTO);
        discounts = discountsRepository.save(discounts);
        return discountsMapper.toDto(discounts);
    }

    /**
     * Get all the discounts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DiscountsDTO> findAll() {
        log.debug("Request to get all Discounts");
        return discountsRepository.findAll().stream()
            .map(discountsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one discounts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DiscountsDTO> findOne(Long id) {
        log.debug("Request to get Discounts : {}", id);
        return discountsRepository.findById(id)
            .map(discountsMapper::toDto);
    }

    /**
     * Delete the discounts by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Discounts : {}", id);

        discountsRepository.deleteById(id);
    }
}

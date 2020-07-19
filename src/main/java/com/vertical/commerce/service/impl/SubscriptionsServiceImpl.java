package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.SubscriptionsService;
import com.vertical.commerce.domain.Subscriptions;
import com.vertical.commerce.repository.SubscriptionsRepository;
import com.vertical.commerce.service.dto.SubscriptionsDTO;
import com.vertical.commerce.service.mapper.SubscriptionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Subscriptions}.
 */
@Service
@Transactional
public class SubscriptionsServiceImpl implements SubscriptionsService {

    private final Logger log = LoggerFactory.getLogger(SubscriptionsServiceImpl.class);

    private final SubscriptionsRepository subscriptionsRepository;

    private final SubscriptionsMapper subscriptionsMapper;

    public SubscriptionsServiceImpl(SubscriptionsRepository subscriptionsRepository, SubscriptionsMapper subscriptionsMapper) {
        this.subscriptionsRepository = subscriptionsRepository;
        this.subscriptionsMapper = subscriptionsMapper;
    }

    /**
     * Save a subscriptions.
     *
     * @param subscriptionsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SubscriptionsDTO save(SubscriptionsDTO subscriptionsDTO) {
        log.debug("Request to save Subscriptions : {}", subscriptionsDTO);
        Subscriptions subscriptions = subscriptionsMapper.toEntity(subscriptionsDTO);
        subscriptions = subscriptionsRepository.save(subscriptions);
        return subscriptionsMapper.toDto(subscriptions);
    }

    /**
     * Get all the subscriptions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionsDTO> findAll() {
        log.debug("Request to get all Subscriptions");
        return subscriptionsRepository.findAll().stream()
            .map(subscriptionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one subscriptions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SubscriptionsDTO> findOne(Long id) {
        log.debug("Request to get Subscriptions : {}", id);
        return subscriptionsRepository.findById(id)
            .map(subscriptionsMapper::toDto);
    }

    /**
     * Delete the subscriptions by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Subscriptions : {}", id);

        subscriptionsRepository.deleteById(id);
    }
}

package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.TrackingEventService;
import com.vertical.commerce.domain.TrackingEvent;
import com.vertical.commerce.repository.TrackingEventRepository;
import com.vertical.commerce.service.dto.TrackingEventDTO;
import com.vertical.commerce.service.mapper.TrackingEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TrackingEvent}.
 */
@Service
@Transactional
public class TrackingEventServiceImpl implements TrackingEventService {

    private final Logger log = LoggerFactory.getLogger(TrackingEventServiceImpl.class);

    private final TrackingEventRepository trackingEventRepository;

    private final TrackingEventMapper trackingEventMapper;

    public TrackingEventServiceImpl(TrackingEventRepository trackingEventRepository, TrackingEventMapper trackingEventMapper) {
        this.trackingEventRepository = trackingEventRepository;
        this.trackingEventMapper = trackingEventMapper;
    }

    /**
     * Save a trackingEvent.
     *
     * @param trackingEventDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TrackingEventDTO save(TrackingEventDTO trackingEventDTO) {
        log.debug("Request to save TrackingEvent : {}", trackingEventDTO);
        TrackingEvent trackingEvent = trackingEventMapper.toEntity(trackingEventDTO);
        trackingEvent = trackingEventRepository.save(trackingEvent);
        return trackingEventMapper.toDto(trackingEvent);
    }

    /**
     * Get all the trackingEvents.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrackingEventDTO> findAll() {
        log.debug("Request to get all TrackingEvents");
        return trackingEventRepository.findAll().stream()
            .map(trackingEventMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one trackingEvent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TrackingEventDTO> findOne(Long id) {
        log.debug("Request to get TrackingEvent : {}", id);
        return trackingEventRepository.findById(id)
            .map(trackingEventMapper::toDto);
    }

    /**
     * Delete the trackingEvent by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TrackingEvent : {}", id);

        trackingEventRepository.deleteById(id);
    }
}

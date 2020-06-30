package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.LogisticsService;
import com.vertical.commerce.domain.Logistics;
import com.vertical.commerce.repository.LogisticsRepository;
import com.vertical.commerce.service.dto.LogisticsDTO;
import com.vertical.commerce.service.mapper.LogisticsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Logistics}.
 */
@Service
@Transactional
public class LogisticsServiceImpl implements LogisticsService {

    private final Logger log = LoggerFactory.getLogger(LogisticsServiceImpl.class);

    private final LogisticsRepository logisticsRepository;

    private final LogisticsMapper logisticsMapper;

    public LogisticsServiceImpl(LogisticsRepository logisticsRepository, LogisticsMapper logisticsMapper) {
        this.logisticsRepository = logisticsRepository;
        this.logisticsMapper = logisticsMapper;
    }

    /**
     * Save a logistics.
     *
     * @param logisticsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LogisticsDTO save(LogisticsDTO logisticsDTO) {
        log.debug("Request to save Logistics : {}", logisticsDTO);
        Logistics logistics = logisticsMapper.toEntity(logisticsDTO);
        logistics = logisticsRepository.save(logistics);
        return logisticsMapper.toDto(logistics);
    }

    /**
     * Get all the logistics.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<LogisticsDTO> findAll() {
        log.debug("Request to get all Logistics");
        return logisticsRepository.findAll().stream()
            .map(logisticsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one logistics by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LogisticsDTO> findOne(Long id) {
        log.debug("Request to get Logistics : {}", id);
        return logisticsRepository.findById(id)
            .map(logisticsMapper::toDto);
    }

    /**
     * Delete the logistics by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Logistics : {}", id);

        logisticsRepository.deleteById(id);
    }
}

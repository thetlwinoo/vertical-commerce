package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.StockItemsCultureService;
import com.vertical.commerce.domain.StockItemsCulture;
import com.vertical.commerce.repository.StockItemsCultureRepository;
import com.vertical.commerce.service.dto.StockItemsCultureDTO;
import com.vertical.commerce.service.mapper.StockItemsCultureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link StockItemsCulture}.
 */
@Service
@Transactional
public class StockItemsCultureServiceImpl implements StockItemsCultureService {

    private final Logger log = LoggerFactory.getLogger(StockItemsCultureServiceImpl.class);

    private final StockItemsCultureRepository stockItemsCultureRepository;

    private final StockItemsCultureMapper stockItemsCultureMapper;

    public StockItemsCultureServiceImpl(StockItemsCultureRepository stockItemsCultureRepository, StockItemsCultureMapper stockItemsCultureMapper) {
        this.stockItemsCultureRepository = stockItemsCultureRepository;
        this.stockItemsCultureMapper = stockItemsCultureMapper;
    }

    /**
     * Save a stockItemsCulture.
     *
     * @param stockItemsCultureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StockItemsCultureDTO save(StockItemsCultureDTO stockItemsCultureDTO) {
        log.debug("Request to save StockItemsCulture : {}", stockItemsCultureDTO);
        StockItemsCulture stockItemsCulture = stockItemsCultureMapper.toEntity(stockItemsCultureDTO);
        stockItemsCulture = stockItemsCultureRepository.save(stockItemsCulture);
        return stockItemsCultureMapper.toDto(stockItemsCulture);
    }

    /**
     * Get all the stockItemsCultures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StockItemsCultureDTO> findAll() {
        log.debug("Request to get all StockItemsCultures");
        return stockItemsCultureRepository.findAll().stream()
            .map(stockItemsCultureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one stockItemsCulture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StockItemsCultureDTO> findOne(Long id) {
        log.debug("Request to get StockItemsCulture : {}", id);
        return stockItemsCultureRepository.findById(id)
            .map(stockItemsCultureMapper::toDto);
    }

    /**
     * Delete the stockItemsCulture by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockItemsCulture : {}", id);

        stockItemsCultureRepository.deleteById(id);
    }
}

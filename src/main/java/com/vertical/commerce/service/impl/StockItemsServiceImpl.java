package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.StockItemsService;
import com.vertical.commerce.domain.StockItems;
import com.vertical.commerce.repository.StockItemsRepository;
import com.vertical.commerce.service.dto.StockItemsDTO;
import com.vertical.commerce.service.mapper.StockItemsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StockItems}.
 */
@Service
@Transactional
public class StockItemsServiceImpl implements StockItemsService {

    private final Logger log = LoggerFactory.getLogger(StockItemsServiceImpl.class);

    private final StockItemsRepository stockItemsRepository;

    private final StockItemsMapper stockItemsMapper;

    public StockItemsServiceImpl(StockItemsRepository stockItemsRepository, StockItemsMapper stockItemsMapper) {
        this.stockItemsRepository = stockItemsRepository;
        this.stockItemsMapper = stockItemsMapper;
    }

    /**
     * Save a stockItems.
     *
     * @param stockItemsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StockItemsDTO save(StockItemsDTO stockItemsDTO) {
        log.debug("Request to save StockItems : {}", stockItemsDTO);
        StockItems stockItems = stockItemsMapper.toEntity(stockItemsDTO);
        stockItems = stockItemsRepository.save(stockItems);
        return stockItemsMapper.toDto(stockItems);
    }

    /**
     * Get all the stockItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StockItemsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StockItems");
        return stockItemsRepository.findAll(pageable)
            .map(stockItemsMapper::toDto);
    }


    /**
     * Get one stockItems by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StockItemsDTO> findOne(Long id) {
        log.debug("Request to get StockItems : {}", id);
        return stockItemsRepository.findById(id)
            .map(stockItemsMapper::toDto);
    }

    /**
     * Delete the stockItems by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockItems : {}", id);

        stockItemsRepository.deleteById(id);
    }
}

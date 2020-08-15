package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.StockItemsLocalizeService;
import com.vertical.commerce.domain.StockItemsLocalize;
import com.vertical.commerce.repository.StockItemsLocalizeRepository;
import com.vertical.commerce.service.dto.StockItemsLocalizeDTO;
import com.vertical.commerce.service.mapper.StockItemsLocalizeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link StockItemsLocalize}.
 */
@Service
@Transactional
public class StockItemsLocalizeServiceImpl implements StockItemsLocalizeService {

    private final Logger log = LoggerFactory.getLogger(StockItemsLocalizeServiceImpl.class);

    private final StockItemsLocalizeRepository stockItemsLocalizeRepository;

    private final StockItemsLocalizeMapper stockItemsLocalizeMapper;

    public StockItemsLocalizeServiceImpl(StockItemsLocalizeRepository stockItemsLocalizeRepository, StockItemsLocalizeMapper stockItemsLocalizeMapper) {
        this.stockItemsLocalizeRepository = stockItemsLocalizeRepository;
        this.stockItemsLocalizeMapper = stockItemsLocalizeMapper;
    }

    /**
     * Save a stockItemsLocalize.
     *
     * @param stockItemsLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StockItemsLocalizeDTO save(StockItemsLocalizeDTO stockItemsLocalizeDTO) {
        log.debug("Request to save StockItemsLocalize : {}", stockItemsLocalizeDTO);
        StockItemsLocalize stockItemsLocalize = stockItemsLocalizeMapper.toEntity(stockItemsLocalizeDTO);
        stockItemsLocalize = stockItemsLocalizeRepository.save(stockItemsLocalize);
        return stockItemsLocalizeMapper.toDto(stockItemsLocalize);
    }

    /**
     * Get all the stockItemsLocalizes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StockItemsLocalizeDTO> findAll() {
        log.debug("Request to get all StockItemsLocalizes");
        return stockItemsLocalizeRepository.findAll().stream()
            .map(stockItemsLocalizeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one stockItemsLocalize by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StockItemsLocalizeDTO> findOne(Long id) {
        log.debug("Request to get StockItemsLocalize : {}", id);
        return stockItemsLocalizeRepository.findById(id)
            .map(stockItemsLocalizeMapper::toDto);
    }

    /**
     * Delete the stockItemsLocalize by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockItemsLocalize : {}", id);

        stockItemsLocalizeRepository.deleteById(id);
    }
}

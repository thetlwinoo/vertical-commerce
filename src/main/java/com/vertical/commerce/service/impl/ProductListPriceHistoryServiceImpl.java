package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.ProductListPriceHistoryService;
import com.vertical.commerce.domain.ProductListPriceHistory;
import com.vertical.commerce.repository.ProductListPriceHistoryRepository;
import com.vertical.commerce.service.dto.ProductListPriceHistoryDTO;
import com.vertical.commerce.service.mapper.ProductListPriceHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductListPriceHistory}.
 */
@Service
@Transactional
public class ProductListPriceHistoryServiceImpl implements ProductListPriceHistoryService {

    private final Logger log = LoggerFactory.getLogger(ProductListPriceHistoryServiceImpl.class);

    private final ProductListPriceHistoryRepository productListPriceHistoryRepository;

    private final ProductListPriceHistoryMapper productListPriceHistoryMapper;

    public ProductListPriceHistoryServiceImpl(ProductListPriceHistoryRepository productListPriceHistoryRepository, ProductListPriceHistoryMapper productListPriceHistoryMapper) {
        this.productListPriceHistoryRepository = productListPriceHistoryRepository;
        this.productListPriceHistoryMapper = productListPriceHistoryMapper;
    }

    /**
     * Save a productListPriceHistory.
     *
     * @param productListPriceHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductListPriceHistoryDTO save(ProductListPriceHistoryDTO productListPriceHistoryDTO) {
        log.debug("Request to save ProductListPriceHistory : {}", productListPriceHistoryDTO);
        ProductListPriceHistory productListPriceHistory = productListPriceHistoryMapper.toEntity(productListPriceHistoryDTO);
        productListPriceHistory = productListPriceHistoryRepository.save(productListPriceHistory);
        return productListPriceHistoryMapper.toDto(productListPriceHistory);
    }

    /**
     * Get all the productListPriceHistories.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductListPriceHistoryDTO> findAll() {
        log.debug("Request to get all ProductListPriceHistories");
        return productListPriceHistoryRepository.findAll().stream()
            .map(productListPriceHistoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productListPriceHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductListPriceHistoryDTO> findOne(Long id) {
        log.debug("Request to get ProductListPriceHistory : {}", id);
        return productListPriceHistoryRepository.findById(id)
            .map(productListPriceHistoryMapper::toDto);
    }

    /**
     * Delete the productListPriceHistory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductListPriceHistory : {}", id);

        productListPriceHistoryRepository.deleteById(id);
    }
}

package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.ProductSetDetailPriceService;
import com.vertical.commerce.domain.ProductSetDetailPrice;
import com.vertical.commerce.repository.ProductSetDetailPriceRepository;
import com.vertical.commerce.service.dto.ProductSetDetailPriceDTO;
import com.vertical.commerce.service.mapper.ProductSetDetailPriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductSetDetailPrice}.
 */
@Service
@Transactional
public class ProductSetDetailPriceServiceImpl implements ProductSetDetailPriceService {

    private final Logger log = LoggerFactory.getLogger(ProductSetDetailPriceServiceImpl.class);

    private final ProductSetDetailPriceRepository productSetDetailPriceRepository;

    private final ProductSetDetailPriceMapper productSetDetailPriceMapper;

    public ProductSetDetailPriceServiceImpl(ProductSetDetailPriceRepository productSetDetailPriceRepository, ProductSetDetailPriceMapper productSetDetailPriceMapper) {
        this.productSetDetailPriceRepository = productSetDetailPriceRepository;
        this.productSetDetailPriceMapper = productSetDetailPriceMapper;
    }

    /**
     * Save a productSetDetailPrice.
     *
     * @param productSetDetailPriceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductSetDetailPriceDTO save(ProductSetDetailPriceDTO productSetDetailPriceDTO) {
        log.debug("Request to save ProductSetDetailPrice : {}", productSetDetailPriceDTO);
        ProductSetDetailPrice productSetDetailPrice = productSetDetailPriceMapper.toEntity(productSetDetailPriceDTO);
        productSetDetailPrice = productSetDetailPriceRepository.save(productSetDetailPrice);
        return productSetDetailPriceMapper.toDto(productSetDetailPrice);
    }

    /**
     * Get all the productSetDetailPrices.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductSetDetailPriceDTO> findAll() {
        log.debug("Request to get all ProductSetDetailPrices");
        return productSetDetailPriceRepository.findAll().stream()
            .map(productSetDetailPriceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productSetDetailPrice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductSetDetailPriceDTO> findOne(Long id) {
        log.debug("Request to get ProductSetDetailPrice : {}", id);
        return productSetDetailPriceRepository.findById(id)
            .map(productSetDetailPriceMapper::toDto);
    }

    /**
     * Delete the productSetDetailPrice by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductSetDetailPrice : {}", id);

        productSetDetailPriceRepository.deleteById(id);
    }
}

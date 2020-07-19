package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.ProductCategoryCultureService;
import com.vertical.commerce.domain.ProductCategoryCulture;
import com.vertical.commerce.repository.ProductCategoryCultureRepository;
import com.vertical.commerce.service.dto.ProductCategoryCultureDTO;
import com.vertical.commerce.service.mapper.ProductCategoryCultureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductCategoryCulture}.
 */
@Service
@Transactional
public class ProductCategoryCultureServiceImpl implements ProductCategoryCultureService {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryCultureServiceImpl.class);

    private final ProductCategoryCultureRepository productCategoryCultureRepository;

    private final ProductCategoryCultureMapper productCategoryCultureMapper;

    public ProductCategoryCultureServiceImpl(ProductCategoryCultureRepository productCategoryCultureRepository, ProductCategoryCultureMapper productCategoryCultureMapper) {
        this.productCategoryCultureRepository = productCategoryCultureRepository;
        this.productCategoryCultureMapper = productCategoryCultureMapper;
    }

    /**
     * Save a productCategoryCulture.
     *
     * @param productCategoryCultureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductCategoryCultureDTO save(ProductCategoryCultureDTO productCategoryCultureDTO) {
        log.debug("Request to save ProductCategoryCulture : {}", productCategoryCultureDTO);
        ProductCategoryCulture productCategoryCulture = productCategoryCultureMapper.toEntity(productCategoryCultureDTO);
        productCategoryCulture = productCategoryCultureRepository.save(productCategoryCulture);
        return productCategoryCultureMapper.toDto(productCategoryCulture);
    }

    /**
     * Get all the productCategoryCultures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductCategoryCultureDTO> findAll() {
        log.debug("Request to get all ProductCategoryCultures");
        return productCategoryCultureRepository.findAll().stream()
            .map(productCategoryCultureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productCategoryCulture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductCategoryCultureDTO> findOne(Long id) {
        log.debug("Request to get ProductCategoryCulture : {}", id);
        return productCategoryCultureRepository.findById(id)
            .map(productCategoryCultureMapper::toDto);
    }

    /**
     * Delete the productCategoryCulture by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductCategoryCulture : {}", id);

        productCategoryCultureRepository.deleteById(id);
    }
}

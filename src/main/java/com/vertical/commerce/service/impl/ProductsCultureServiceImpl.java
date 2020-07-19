package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.ProductsCultureService;
import com.vertical.commerce.domain.ProductsCulture;
import com.vertical.commerce.repository.ProductsCultureRepository;
import com.vertical.commerce.service.dto.ProductsCultureDTO;
import com.vertical.commerce.service.mapper.ProductsCultureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductsCulture}.
 */
@Service
@Transactional
public class ProductsCultureServiceImpl implements ProductsCultureService {

    private final Logger log = LoggerFactory.getLogger(ProductsCultureServiceImpl.class);

    private final ProductsCultureRepository productsCultureRepository;

    private final ProductsCultureMapper productsCultureMapper;

    public ProductsCultureServiceImpl(ProductsCultureRepository productsCultureRepository, ProductsCultureMapper productsCultureMapper) {
        this.productsCultureRepository = productsCultureRepository;
        this.productsCultureMapper = productsCultureMapper;
    }

    /**
     * Save a productsCulture.
     *
     * @param productsCultureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductsCultureDTO save(ProductsCultureDTO productsCultureDTO) {
        log.debug("Request to save ProductsCulture : {}", productsCultureDTO);
        ProductsCulture productsCulture = productsCultureMapper.toEntity(productsCultureDTO);
        productsCulture = productsCultureRepository.save(productsCulture);
        return productsCultureMapper.toDto(productsCulture);
    }

    /**
     * Get all the productsCultures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductsCultureDTO> findAll() {
        log.debug("Request to get all ProductsCultures");
        return productsCultureRepository.findAll().stream()
            .map(productsCultureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productsCulture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductsCultureDTO> findOne(Long id) {
        log.debug("Request to get ProductsCulture : {}", id);
        return productsCultureRepository.findById(id)
            .map(productsCultureMapper::toDto);
    }

    /**
     * Delete the productsCulture by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductsCulture : {}", id);

        productsCultureRepository.deleteById(id);
    }
}

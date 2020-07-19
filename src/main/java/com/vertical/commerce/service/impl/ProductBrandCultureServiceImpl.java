package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.ProductBrandCultureService;
import com.vertical.commerce.domain.ProductBrandCulture;
import com.vertical.commerce.repository.ProductBrandCultureRepository;
import com.vertical.commerce.service.dto.ProductBrandCultureDTO;
import com.vertical.commerce.service.mapper.ProductBrandCultureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductBrandCulture}.
 */
@Service
@Transactional
public class ProductBrandCultureServiceImpl implements ProductBrandCultureService {

    private final Logger log = LoggerFactory.getLogger(ProductBrandCultureServiceImpl.class);

    private final ProductBrandCultureRepository productBrandCultureRepository;

    private final ProductBrandCultureMapper productBrandCultureMapper;

    public ProductBrandCultureServiceImpl(ProductBrandCultureRepository productBrandCultureRepository, ProductBrandCultureMapper productBrandCultureMapper) {
        this.productBrandCultureRepository = productBrandCultureRepository;
        this.productBrandCultureMapper = productBrandCultureMapper;
    }

    /**
     * Save a productBrandCulture.
     *
     * @param productBrandCultureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductBrandCultureDTO save(ProductBrandCultureDTO productBrandCultureDTO) {
        log.debug("Request to save ProductBrandCulture : {}", productBrandCultureDTO);
        ProductBrandCulture productBrandCulture = productBrandCultureMapper.toEntity(productBrandCultureDTO);
        productBrandCulture = productBrandCultureRepository.save(productBrandCulture);
        return productBrandCultureMapper.toDto(productBrandCulture);
    }

    /**
     * Get all the productBrandCultures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductBrandCultureDTO> findAll() {
        log.debug("Request to get all ProductBrandCultures");
        return productBrandCultureRepository.findAll().stream()
            .map(productBrandCultureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productBrandCulture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductBrandCultureDTO> findOne(Long id) {
        log.debug("Request to get ProductBrandCulture : {}", id);
        return productBrandCultureRepository.findById(id)
            .map(productBrandCultureMapper::toDto);
    }

    /**
     * Delete the productBrandCulture by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductBrandCulture : {}", id);

        productBrandCultureRepository.deleteById(id);
    }
}

package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.ProductBrandLocalizeService;
import com.vertical.commerce.domain.ProductBrandLocalize;
import com.vertical.commerce.repository.ProductBrandLocalizeRepository;
import com.vertical.commerce.service.dto.ProductBrandLocalizeDTO;
import com.vertical.commerce.service.mapper.ProductBrandLocalizeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductBrandLocalize}.
 */
@Service
@Transactional
public class ProductBrandLocalizeServiceImpl implements ProductBrandLocalizeService {

    private final Logger log = LoggerFactory.getLogger(ProductBrandLocalizeServiceImpl.class);

    private final ProductBrandLocalizeRepository productBrandLocalizeRepository;

    private final ProductBrandLocalizeMapper productBrandLocalizeMapper;

    public ProductBrandLocalizeServiceImpl(ProductBrandLocalizeRepository productBrandLocalizeRepository, ProductBrandLocalizeMapper productBrandLocalizeMapper) {
        this.productBrandLocalizeRepository = productBrandLocalizeRepository;
        this.productBrandLocalizeMapper = productBrandLocalizeMapper;
    }

    /**
     * Save a productBrandLocalize.
     *
     * @param productBrandLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductBrandLocalizeDTO save(ProductBrandLocalizeDTO productBrandLocalizeDTO) {
        log.debug("Request to save ProductBrandLocalize : {}", productBrandLocalizeDTO);
        ProductBrandLocalize productBrandLocalize = productBrandLocalizeMapper.toEntity(productBrandLocalizeDTO);
        productBrandLocalize = productBrandLocalizeRepository.save(productBrandLocalize);
        return productBrandLocalizeMapper.toDto(productBrandLocalize);
    }

    /**
     * Get all the productBrandLocalizes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductBrandLocalizeDTO> findAll() {
        log.debug("Request to get all ProductBrandLocalizes");
        return productBrandLocalizeRepository.findAll().stream()
            .map(productBrandLocalizeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productBrandLocalize by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductBrandLocalizeDTO> findOne(Long id) {
        log.debug("Request to get ProductBrandLocalize : {}", id);
        return productBrandLocalizeRepository.findById(id)
            .map(productBrandLocalizeMapper::toDto);
    }

    /**
     * Delete the productBrandLocalize by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductBrandLocalize : {}", id);

        productBrandLocalizeRepository.deleteById(id);
    }
}

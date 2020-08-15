package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.ProductCategoryLocalizeService;
import com.vertical.commerce.domain.ProductCategoryLocalize;
import com.vertical.commerce.repository.ProductCategoryLocalizeRepository;
import com.vertical.commerce.service.dto.ProductCategoryLocalizeDTO;
import com.vertical.commerce.service.mapper.ProductCategoryLocalizeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductCategoryLocalize}.
 */
@Service
@Transactional
public class ProductCategoryLocalizeServiceImpl implements ProductCategoryLocalizeService {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryLocalizeServiceImpl.class);

    private final ProductCategoryLocalizeRepository productCategoryLocalizeRepository;

    private final ProductCategoryLocalizeMapper productCategoryLocalizeMapper;

    public ProductCategoryLocalizeServiceImpl(ProductCategoryLocalizeRepository productCategoryLocalizeRepository, ProductCategoryLocalizeMapper productCategoryLocalizeMapper) {
        this.productCategoryLocalizeRepository = productCategoryLocalizeRepository;
        this.productCategoryLocalizeMapper = productCategoryLocalizeMapper;
    }

    /**
     * Save a productCategoryLocalize.
     *
     * @param productCategoryLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductCategoryLocalizeDTO save(ProductCategoryLocalizeDTO productCategoryLocalizeDTO) {
        log.debug("Request to save ProductCategoryLocalize : {}", productCategoryLocalizeDTO);
        ProductCategoryLocalize productCategoryLocalize = productCategoryLocalizeMapper.toEntity(productCategoryLocalizeDTO);
        productCategoryLocalize = productCategoryLocalizeRepository.save(productCategoryLocalize);
        return productCategoryLocalizeMapper.toDto(productCategoryLocalize);
    }

    /**
     * Get all the productCategoryLocalizes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductCategoryLocalizeDTO> findAll() {
        log.debug("Request to get all ProductCategoryLocalizes");
        return productCategoryLocalizeRepository.findAll().stream()
            .map(productCategoryLocalizeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productCategoryLocalize by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductCategoryLocalizeDTO> findOne(Long id) {
        log.debug("Request to get ProductCategoryLocalize : {}", id);
        return productCategoryLocalizeRepository.findById(id)
            .map(productCategoryLocalizeMapper::toDto);
    }

    /**
     * Delete the productCategoryLocalize by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductCategoryLocalize : {}", id);

        productCategoryLocalizeRepository.deleteById(id);
    }
}

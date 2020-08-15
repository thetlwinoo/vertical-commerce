package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.ProductsLocalizeService;
import com.vertical.commerce.domain.ProductsLocalize;
import com.vertical.commerce.repository.ProductsLocalizeRepository;
import com.vertical.commerce.service.dto.ProductsLocalizeDTO;
import com.vertical.commerce.service.mapper.ProductsLocalizeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductsLocalize}.
 */
@Service
@Transactional
public class ProductsLocalizeServiceImpl implements ProductsLocalizeService {

    private final Logger log = LoggerFactory.getLogger(ProductsLocalizeServiceImpl.class);

    private final ProductsLocalizeRepository productsLocalizeRepository;

    private final ProductsLocalizeMapper productsLocalizeMapper;

    public ProductsLocalizeServiceImpl(ProductsLocalizeRepository productsLocalizeRepository, ProductsLocalizeMapper productsLocalizeMapper) {
        this.productsLocalizeRepository = productsLocalizeRepository;
        this.productsLocalizeMapper = productsLocalizeMapper;
    }

    /**
     * Save a productsLocalize.
     *
     * @param productsLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductsLocalizeDTO save(ProductsLocalizeDTO productsLocalizeDTO) {
        log.debug("Request to save ProductsLocalize : {}", productsLocalizeDTO);
        ProductsLocalize productsLocalize = productsLocalizeMapper.toEntity(productsLocalizeDTO);
        productsLocalize = productsLocalizeRepository.save(productsLocalize);
        return productsLocalizeMapper.toDto(productsLocalize);
    }

    /**
     * Get all the productsLocalizes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductsLocalizeDTO> findAll() {
        log.debug("Request to get all ProductsLocalizes");
        return productsLocalizeRepository.findAll().stream()
            .map(productsLocalizeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productsLocalize by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductsLocalizeDTO> findOne(Long id) {
        log.debug("Request to get ProductsLocalize : {}", id);
        return productsLocalizeRepository.findById(id)
            .map(productsLocalizeMapper::toDto);
    }

    /**
     * Delete the productsLocalize by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductsLocalize : {}", id);

        productsLocalizeRepository.deleteById(id);
    }
}

package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.ProductDocumentsCultureService;
import com.vertical.commerce.domain.ProductDocumentsCulture;
import com.vertical.commerce.repository.ProductDocumentsCultureRepository;
import com.vertical.commerce.service.dto.ProductDocumentsCultureDTO;
import com.vertical.commerce.service.mapper.ProductDocumentsCultureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductDocumentsCulture}.
 */
@Service
@Transactional
public class ProductDocumentsCultureServiceImpl implements ProductDocumentsCultureService {

    private final Logger log = LoggerFactory.getLogger(ProductDocumentsCultureServiceImpl.class);

    private final ProductDocumentsCultureRepository productDocumentsCultureRepository;

    private final ProductDocumentsCultureMapper productDocumentsCultureMapper;

    public ProductDocumentsCultureServiceImpl(ProductDocumentsCultureRepository productDocumentsCultureRepository, ProductDocumentsCultureMapper productDocumentsCultureMapper) {
        this.productDocumentsCultureRepository = productDocumentsCultureRepository;
        this.productDocumentsCultureMapper = productDocumentsCultureMapper;
    }

    /**
     * Save a productDocumentsCulture.
     *
     * @param productDocumentsCultureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductDocumentsCultureDTO save(ProductDocumentsCultureDTO productDocumentsCultureDTO) {
        log.debug("Request to save ProductDocumentsCulture : {}", productDocumentsCultureDTO);
        ProductDocumentsCulture productDocumentsCulture = productDocumentsCultureMapper.toEntity(productDocumentsCultureDTO);
        productDocumentsCulture = productDocumentsCultureRepository.save(productDocumentsCulture);
        return productDocumentsCultureMapper.toDto(productDocumentsCulture);
    }

    /**
     * Get all the productDocumentsCultures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductDocumentsCultureDTO> findAll() {
        log.debug("Request to get all ProductDocumentsCultures");
        return productDocumentsCultureRepository.findAll().stream()
            .map(productDocumentsCultureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productDocumentsCulture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDocumentsCultureDTO> findOne(Long id) {
        log.debug("Request to get ProductDocumentsCulture : {}", id);
        return productDocumentsCultureRepository.findById(id)
            .map(productDocumentsCultureMapper::toDto);
    }

    /**
     * Delete the productDocumentsCulture by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductDocumentsCulture : {}", id);

        productDocumentsCultureRepository.deleteById(id);
    }
}

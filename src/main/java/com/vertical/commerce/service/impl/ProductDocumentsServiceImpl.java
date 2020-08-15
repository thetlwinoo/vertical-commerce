package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.ProductDocumentsService;
import com.vertical.commerce.domain.ProductDocuments;
import com.vertical.commerce.repository.ProductDocumentsRepository;
import com.vertical.commerce.service.dto.ProductDocumentsDTO;
import com.vertical.commerce.service.mapper.ProductDocumentsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductDocuments}.
 */
@Service
@Transactional
public class ProductDocumentsServiceImpl implements ProductDocumentsService {

    private final Logger log = LoggerFactory.getLogger(ProductDocumentsServiceImpl.class);

    private final ProductDocumentsRepository productDocumentsRepository;

    private final ProductDocumentsMapper productDocumentsMapper;

    public ProductDocumentsServiceImpl(ProductDocumentsRepository productDocumentsRepository, ProductDocumentsMapper productDocumentsMapper) {
        this.productDocumentsRepository = productDocumentsRepository;
        this.productDocumentsMapper = productDocumentsMapper;
    }

    /**
     * Save a productDocuments.
     *
     * @param productDocumentsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductDocumentsDTO save(ProductDocumentsDTO productDocumentsDTO) {
        log.debug("Request to save ProductDocuments : {}", productDocumentsDTO);
        ProductDocuments productDocuments = productDocumentsMapper.toEntity(productDocumentsDTO);
        productDocuments = productDocumentsRepository.save(productDocuments);
        return productDocumentsMapper.toDto(productDocuments);
    }

    /**
     * Get all the productDocuments.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductDocumentsDTO> findAll() {
        log.debug("Request to get all ProductDocuments");
        return productDocumentsRepository.findAll().stream()
            .map(productDocumentsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productDocuments by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDocumentsDTO> findOne(Long id) {
        log.debug("Request to get ProductDocuments : {}", id);
        return productDocumentsRepository.findById(id)
            .map(productDocumentsMapper::toDto);
    }

    /**
     * Delete the productDocuments by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductDocuments : {}", id);

        productDocumentsRepository.deleteById(id);
    }
}

package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.ProductsService;
import com.vertical.commerce.domain.Products;
import com.vertical.commerce.repository.ProductsRepository;
import com.vertical.commerce.service.dto.ProductsDTO;
import com.vertical.commerce.service.mapper.ProductsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Products}.
 */
@Service
@Transactional
public class ProductsServiceImpl implements ProductsService {

    private final Logger log = LoggerFactory.getLogger(ProductsServiceImpl.class);

    private final ProductsRepository productsRepository;

    private final ProductsMapper productsMapper;

    public ProductsServiceImpl(ProductsRepository productsRepository, ProductsMapper productsMapper) {
        this.productsRepository = productsRepository;
        this.productsMapper = productsMapper;
    }

    /**
     * Save a products.
     *
     * @param productsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductsDTO save(ProductsDTO productsDTO) {
        log.debug("Request to save Products : {}", productsDTO);
        Products products = productsMapper.toEntity(productsDTO);
        products = productsRepository.save(products);
        return productsMapper.toDto(products);
    }

    /**
     * Get all the products.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        return productsRepository.findAll(pageable)
            .map(productsMapper::toDto);
    }



    /**
     *  Get all the products where ProductDocument is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<ProductsDTO> findAllWhereProductDocumentIsNull() {
        log.debug("Request to get all products where ProductDocument is null");
        return StreamSupport
            .stream(productsRepository.findAll().spliterator(), false)
            .filter(products -> products.getProductDocument() == null)
            .map(productsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one products by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductsDTO> findOne(Long id) {
        log.debug("Request to get Products : {}", id);
        return productsRepository.findById(id)
            .map(productsMapper::toDto);
    }

    /**
     * Delete the products by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Products : {}", id);

        productsRepository.deleteById(id);
    }
}

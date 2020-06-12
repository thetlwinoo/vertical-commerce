package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.ShoppingCartsService;
import com.vertical.commerce.domain.ShoppingCarts;
import com.vertical.commerce.repository.ShoppingCartsRepository;
import com.vertical.commerce.service.dto.ShoppingCartsDTO;
import com.vertical.commerce.service.mapper.ShoppingCartsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ShoppingCarts}.
 */
@Service
@Transactional
public class ShoppingCartsServiceImpl implements ShoppingCartsService {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartsServiceImpl.class);

    private final ShoppingCartsRepository shoppingCartsRepository;

    private final ShoppingCartsMapper shoppingCartsMapper;

    public ShoppingCartsServiceImpl(ShoppingCartsRepository shoppingCartsRepository, ShoppingCartsMapper shoppingCartsMapper) {
        this.shoppingCartsRepository = shoppingCartsRepository;
        this.shoppingCartsMapper = shoppingCartsMapper;
    }

    /**
     * Save a shoppingCarts.
     *
     * @param shoppingCartsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ShoppingCartsDTO save(ShoppingCartsDTO shoppingCartsDTO) {
        log.debug("Request to save ShoppingCarts : {}", shoppingCartsDTO);
        ShoppingCarts shoppingCarts = shoppingCartsMapper.toEntity(shoppingCartsDTO);
        shoppingCarts = shoppingCartsRepository.save(shoppingCarts);
        return shoppingCartsMapper.toDto(shoppingCarts);
    }

    /**
     * Get all the shoppingCarts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShoppingCartsDTO> findAll() {
        log.debug("Request to get all ShoppingCarts");
        return shoppingCartsRepository.findAll().stream()
            .map(shoppingCartsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one shoppingCarts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShoppingCartsDTO> findOne(Long id) {
        log.debug("Request to get ShoppingCarts : {}", id);
        return shoppingCartsRepository.findById(id)
            .map(shoppingCartsMapper::toDto);
    }

    /**
     * Delete the shoppingCarts by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShoppingCarts : {}", id);

        shoppingCartsRepository.deleteById(id);
    }
}

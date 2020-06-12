package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.WishlistLinesService;
import com.vertical.commerce.domain.WishlistLines;
import com.vertical.commerce.repository.WishlistLinesRepository;
import com.vertical.commerce.service.dto.WishlistLinesDTO;
import com.vertical.commerce.service.mapper.WishlistLinesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link WishlistLines}.
 */
@Service
@Transactional
public class WishlistLinesServiceImpl implements WishlistLinesService {

    private final Logger log = LoggerFactory.getLogger(WishlistLinesServiceImpl.class);

    private final WishlistLinesRepository wishlistLinesRepository;

    private final WishlistLinesMapper wishlistLinesMapper;

    public WishlistLinesServiceImpl(WishlistLinesRepository wishlistLinesRepository, WishlistLinesMapper wishlistLinesMapper) {
        this.wishlistLinesRepository = wishlistLinesRepository;
        this.wishlistLinesMapper = wishlistLinesMapper;
    }

    /**
     * Save a wishlistLines.
     *
     * @param wishlistLinesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WishlistLinesDTO save(WishlistLinesDTO wishlistLinesDTO) {
        log.debug("Request to save WishlistLines : {}", wishlistLinesDTO);
        WishlistLines wishlistLines = wishlistLinesMapper.toEntity(wishlistLinesDTO);
        wishlistLines = wishlistLinesRepository.save(wishlistLines);
        return wishlistLinesMapper.toDto(wishlistLines);
    }

    /**
     * Get all the wishlistLines.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<WishlistLinesDTO> findAll() {
        log.debug("Request to get all WishlistLines");
        return wishlistLinesRepository.findAll().stream()
            .map(wishlistLinesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one wishlistLines by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WishlistLinesDTO> findOne(Long id) {
        log.debug("Request to get WishlistLines : {}", id);
        return wishlistLinesRepository.findById(id)
            .map(wishlistLinesMapper::toDto);
    }

    /**
     * Delete the wishlistLines by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WishlistLines : {}", id);

        wishlistLinesRepository.deleteById(id);
    }
}

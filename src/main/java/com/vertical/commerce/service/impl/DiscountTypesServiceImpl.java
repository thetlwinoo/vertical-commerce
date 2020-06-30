package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.DiscountTypesService;
import com.vertical.commerce.domain.DiscountTypes;
import com.vertical.commerce.repository.DiscountTypesRepository;
import com.vertical.commerce.service.dto.DiscountTypesDTO;
import com.vertical.commerce.service.mapper.DiscountTypesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DiscountTypes}.
 */
@Service
@Transactional
public class DiscountTypesServiceImpl implements DiscountTypesService {

    private final Logger log = LoggerFactory.getLogger(DiscountTypesServiceImpl.class);

    private final DiscountTypesRepository discountTypesRepository;

    private final DiscountTypesMapper discountTypesMapper;

    public DiscountTypesServiceImpl(DiscountTypesRepository discountTypesRepository, DiscountTypesMapper discountTypesMapper) {
        this.discountTypesRepository = discountTypesRepository;
        this.discountTypesMapper = discountTypesMapper;
    }

    /**
     * Save a discountTypes.
     *
     * @param discountTypesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DiscountTypesDTO save(DiscountTypesDTO discountTypesDTO) {
        log.debug("Request to save DiscountTypes : {}", discountTypesDTO);
        DiscountTypes discountTypes = discountTypesMapper.toEntity(discountTypesDTO);
        discountTypes = discountTypesRepository.save(discountTypes);
        return discountTypesMapper.toDto(discountTypes);
    }

    /**
     * Get all the discountTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DiscountTypesDTO> findAll() {
        log.debug("Request to get all DiscountTypes");
        return discountTypesRepository.findAll().stream()
            .map(discountTypesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one discountTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DiscountTypesDTO> findOne(Long id) {
        log.debug("Request to get DiscountTypes : {}", id);
        return discountTypesRepository.findById(id)
            .map(discountTypesMapper::toDto);
    }

    /**
     * Delete the discountTypes by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DiscountTypes : {}", id);

        discountTypesRepository.deleteById(id);
    }
}

package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.DiscountDetailsService;
import com.vertical.commerce.domain.DiscountDetails;
import com.vertical.commerce.repository.DiscountDetailsRepository;
import com.vertical.commerce.service.dto.DiscountDetailsDTO;
import com.vertical.commerce.service.mapper.DiscountDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DiscountDetails}.
 */
@Service
@Transactional
public class DiscountDetailsServiceImpl implements DiscountDetailsService {

    private final Logger log = LoggerFactory.getLogger(DiscountDetailsServiceImpl.class);

    private final DiscountDetailsRepository discountDetailsRepository;

    private final DiscountDetailsMapper discountDetailsMapper;

    public DiscountDetailsServiceImpl(DiscountDetailsRepository discountDetailsRepository, DiscountDetailsMapper discountDetailsMapper) {
        this.discountDetailsRepository = discountDetailsRepository;
        this.discountDetailsMapper = discountDetailsMapper;
    }

    /**
     * Save a discountDetails.
     *
     * @param discountDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DiscountDetailsDTO save(DiscountDetailsDTO discountDetailsDTO) {
        log.debug("Request to save DiscountDetails : {}", discountDetailsDTO);
        DiscountDetails discountDetails = discountDetailsMapper.toEntity(discountDetailsDTO);
        discountDetails = discountDetailsRepository.save(discountDetails);
        return discountDetailsMapper.toDto(discountDetails);
    }

    /**
     * Get all the discountDetails.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DiscountDetailsDTO> findAll() {
        log.debug("Request to get all DiscountDetails");
        return discountDetailsRepository.findAll().stream()
            .map(discountDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one discountDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DiscountDetailsDTO> findOne(Long id) {
        log.debug("Request to get DiscountDetails : {}", id);
        return discountDetailsRepository.findById(id)
            .map(discountDetailsMapper::toDto);
    }

    /**
     * Delete the discountDetails by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DiscountDetails : {}", id);

        discountDetailsRepository.deleteById(id);
    }
}

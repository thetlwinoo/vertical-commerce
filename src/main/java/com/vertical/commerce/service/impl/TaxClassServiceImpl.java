package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.TaxClassService;
import com.vertical.commerce.domain.TaxClass;
import com.vertical.commerce.repository.TaxClassRepository;
import com.vertical.commerce.service.dto.TaxClassDTO;
import com.vertical.commerce.service.mapper.TaxClassMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TaxClass}.
 */
@Service
@Transactional
public class TaxClassServiceImpl implements TaxClassService {

    private final Logger log = LoggerFactory.getLogger(TaxClassServiceImpl.class);

    private final TaxClassRepository taxClassRepository;

    private final TaxClassMapper taxClassMapper;

    public TaxClassServiceImpl(TaxClassRepository taxClassRepository, TaxClassMapper taxClassMapper) {
        this.taxClassRepository = taxClassRepository;
        this.taxClassMapper = taxClassMapper;
    }

    /**
     * Save a taxClass.
     *
     * @param taxClassDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TaxClassDTO save(TaxClassDTO taxClassDTO) {
        log.debug("Request to save TaxClass : {}", taxClassDTO);
        TaxClass taxClass = taxClassMapper.toEntity(taxClassDTO);
        taxClass = taxClassRepository.save(taxClass);
        return taxClassMapper.toDto(taxClass);
    }

    /**
     * Get all the taxClasses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TaxClassDTO> findAll() {
        log.debug("Request to get all TaxClasses");
        return taxClassRepository.findAll().stream()
            .map(taxClassMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one taxClass by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TaxClassDTO> findOne(Long id) {
        log.debug("Request to get TaxClass : {}", id);
        return taxClassRepository.findById(id)
            .map(taxClassMapper::toDto);
    }

    /**
     * Delete the taxClass by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaxClass : {}", id);

        taxClassRepository.deleteById(id);
    }
}

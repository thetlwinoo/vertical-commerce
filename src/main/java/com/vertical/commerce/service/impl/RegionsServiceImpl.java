package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.RegionsService;
import com.vertical.commerce.domain.Regions;
import com.vertical.commerce.repository.RegionsRepository;
import com.vertical.commerce.service.dto.RegionsDTO;
import com.vertical.commerce.service.mapper.RegionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Regions}.
 */
@Service
@Transactional
public class RegionsServiceImpl implements RegionsService {

    private final Logger log = LoggerFactory.getLogger(RegionsServiceImpl.class);

    private final RegionsRepository regionsRepository;

    private final RegionsMapper regionsMapper;

    public RegionsServiceImpl(RegionsRepository regionsRepository, RegionsMapper regionsMapper) {
        this.regionsRepository = regionsRepository;
        this.regionsMapper = regionsMapper;
    }

    /**
     * Save a regions.
     *
     * @param regionsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RegionsDTO save(RegionsDTO regionsDTO) {
        log.debug("Request to save Regions : {}", regionsDTO);
        Regions regions = regionsMapper.toEntity(regionsDTO);
        regions = regionsRepository.save(regions);
        return regionsMapper.toDto(regions);
    }

    /**
     * Get all the regions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegionsDTO> findAll() {
        log.debug("Request to get all Regions");
        return regionsRepository.findAll().stream()
            .map(regionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one regions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RegionsDTO> findOne(Long id) {
        log.debug("Request to get Regions : {}", id);
        return regionsRepository.findById(id)
            .map(regionsMapper::toDto);
    }

    /**
     * Delete the regions by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Regions : {}", id);

        regionsRepository.deleteById(id);
    }
}

package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.RegionsLocalizeService;
import com.vertical.commerce.domain.RegionsLocalize;
import com.vertical.commerce.repository.RegionsLocalizeRepository;
import com.vertical.commerce.service.dto.RegionsLocalizeDTO;
import com.vertical.commerce.service.mapper.RegionsLocalizeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link RegionsLocalize}.
 */
@Service
@Transactional
public class RegionsLocalizeServiceImpl implements RegionsLocalizeService {

    private final Logger log = LoggerFactory.getLogger(RegionsLocalizeServiceImpl.class);

    private final RegionsLocalizeRepository regionsLocalizeRepository;

    private final RegionsLocalizeMapper regionsLocalizeMapper;

    public RegionsLocalizeServiceImpl(RegionsLocalizeRepository regionsLocalizeRepository, RegionsLocalizeMapper regionsLocalizeMapper) {
        this.regionsLocalizeRepository = regionsLocalizeRepository;
        this.regionsLocalizeMapper = regionsLocalizeMapper;
    }

    /**
     * Save a regionsLocalize.
     *
     * @param regionsLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RegionsLocalizeDTO save(RegionsLocalizeDTO regionsLocalizeDTO) {
        log.debug("Request to save RegionsLocalize : {}", regionsLocalizeDTO);
        RegionsLocalize regionsLocalize = regionsLocalizeMapper.toEntity(regionsLocalizeDTO);
        regionsLocalize = regionsLocalizeRepository.save(regionsLocalize);
        return regionsLocalizeMapper.toDto(regionsLocalize);
    }

    /**
     * Get all the regionsLocalizes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegionsLocalizeDTO> findAll() {
        log.debug("Request to get all RegionsLocalizes");
        return regionsLocalizeRepository.findAll().stream()
            .map(regionsLocalizeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one regionsLocalize by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RegionsLocalizeDTO> findOne(Long id) {
        log.debug("Request to get RegionsLocalize : {}", id);
        return regionsLocalizeRepository.findById(id)
            .map(regionsLocalizeMapper::toDto);
    }

    /**
     * Delete the regionsLocalize by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RegionsLocalize : {}", id);

        regionsLocalizeRepository.deleteById(id);
    }
}

package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.MaterialsCultureService;
import com.vertical.commerce.domain.MaterialsCulture;
import com.vertical.commerce.repository.MaterialsCultureRepository;
import com.vertical.commerce.service.dto.MaterialsCultureDTO;
import com.vertical.commerce.service.mapper.MaterialsCultureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link MaterialsCulture}.
 */
@Service
@Transactional
public class MaterialsCultureServiceImpl implements MaterialsCultureService {

    private final Logger log = LoggerFactory.getLogger(MaterialsCultureServiceImpl.class);

    private final MaterialsCultureRepository materialsCultureRepository;

    private final MaterialsCultureMapper materialsCultureMapper;

    public MaterialsCultureServiceImpl(MaterialsCultureRepository materialsCultureRepository, MaterialsCultureMapper materialsCultureMapper) {
        this.materialsCultureRepository = materialsCultureRepository;
        this.materialsCultureMapper = materialsCultureMapper;
    }

    /**
     * Save a materialsCulture.
     *
     * @param materialsCultureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MaterialsCultureDTO save(MaterialsCultureDTO materialsCultureDTO) {
        log.debug("Request to save MaterialsCulture : {}", materialsCultureDTO);
        MaterialsCulture materialsCulture = materialsCultureMapper.toEntity(materialsCultureDTO);
        materialsCulture = materialsCultureRepository.save(materialsCulture);
        return materialsCultureMapper.toDto(materialsCulture);
    }

    /**
     * Get all the materialsCultures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MaterialsCultureDTO> findAll() {
        log.debug("Request to get all MaterialsCultures");
        return materialsCultureRepository.findAll().stream()
            .map(materialsCultureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one materialsCulture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MaterialsCultureDTO> findOne(Long id) {
        log.debug("Request to get MaterialsCulture : {}", id);
        return materialsCultureRepository.findById(id)
            .map(materialsCultureMapper::toDto);
    }

    /**
     * Delete the materialsCulture by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MaterialsCulture : {}", id);

        materialsCultureRepository.deleteById(id);
    }
}

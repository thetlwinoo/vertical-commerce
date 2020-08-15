package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.MaterialsLocalizeService;
import com.vertical.commerce.domain.MaterialsLocalize;
import com.vertical.commerce.repository.MaterialsLocalizeRepository;
import com.vertical.commerce.service.dto.MaterialsLocalizeDTO;
import com.vertical.commerce.service.mapper.MaterialsLocalizeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link MaterialsLocalize}.
 */
@Service
@Transactional
public class MaterialsLocalizeServiceImpl implements MaterialsLocalizeService {

    private final Logger log = LoggerFactory.getLogger(MaterialsLocalizeServiceImpl.class);

    private final MaterialsLocalizeRepository materialsLocalizeRepository;

    private final MaterialsLocalizeMapper materialsLocalizeMapper;

    public MaterialsLocalizeServiceImpl(MaterialsLocalizeRepository materialsLocalizeRepository, MaterialsLocalizeMapper materialsLocalizeMapper) {
        this.materialsLocalizeRepository = materialsLocalizeRepository;
        this.materialsLocalizeMapper = materialsLocalizeMapper;
    }

    /**
     * Save a materialsLocalize.
     *
     * @param materialsLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MaterialsLocalizeDTO save(MaterialsLocalizeDTO materialsLocalizeDTO) {
        log.debug("Request to save MaterialsLocalize : {}", materialsLocalizeDTO);
        MaterialsLocalize materialsLocalize = materialsLocalizeMapper.toEntity(materialsLocalizeDTO);
        materialsLocalize = materialsLocalizeRepository.save(materialsLocalize);
        return materialsLocalizeMapper.toDto(materialsLocalize);
    }

    /**
     * Get all the materialsLocalizes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MaterialsLocalizeDTO> findAll() {
        log.debug("Request to get all MaterialsLocalizes");
        return materialsLocalizeRepository.findAll().stream()
            .map(materialsLocalizeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one materialsLocalize by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MaterialsLocalizeDTO> findOne(Long id) {
        log.debug("Request to get MaterialsLocalize : {}", id);
        return materialsLocalizeRepository.findById(id)
            .map(materialsLocalizeMapper::toDto);
    }

    /**
     * Delete the materialsLocalize by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MaterialsLocalize : {}", id);

        materialsLocalizeRepository.deleteById(id);
    }
}

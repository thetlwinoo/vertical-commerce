package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.WarrantyTypesService;
import com.vertical.commerce.domain.WarrantyTypes;
import com.vertical.commerce.repository.WarrantyTypesRepository;
import com.vertical.commerce.service.dto.WarrantyTypesDTO;
import com.vertical.commerce.service.mapper.WarrantyTypesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link WarrantyTypes}.
 */
@Service
@Transactional
public class WarrantyTypesServiceImpl implements WarrantyTypesService {

    private final Logger log = LoggerFactory.getLogger(WarrantyTypesServiceImpl.class);

    private final WarrantyTypesRepository warrantyTypesRepository;

    private final WarrantyTypesMapper warrantyTypesMapper;

    public WarrantyTypesServiceImpl(WarrantyTypesRepository warrantyTypesRepository, WarrantyTypesMapper warrantyTypesMapper) {
        this.warrantyTypesRepository = warrantyTypesRepository;
        this.warrantyTypesMapper = warrantyTypesMapper;
    }

    /**
     * Save a warrantyTypes.
     *
     * @param warrantyTypesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WarrantyTypesDTO save(WarrantyTypesDTO warrantyTypesDTO) {
        log.debug("Request to save WarrantyTypes : {}", warrantyTypesDTO);
        WarrantyTypes warrantyTypes = warrantyTypesMapper.toEntity(warrantyTypesDTO);
        warrantyTypes = warrantyTypesRepository.save(warrantyTypes);
        return warrantyTypesMapper.toDto(warrantyTypes);
    }

    /**
     * Get all the warrantyTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<WarrantyTypesDTO> findAll() {
        log.debug("Request to get all WarrantyTypes");
        return warrantyTypesRepository.findAll().stream()
            .map(warrantyTypesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one warrantyTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WarrantyTypesDTO> findOne(Long id) {
        log.debug("Request to get WarrantyTypes : {}", id);
        return warrantyTypesRepository.findById(id)
            .map(warrantyTypesMapper::toDto);
    }

    /**
     * Delete the warrantyTypes by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WarrantyTypes : {}", id);

        warrantyTypesRepository.deleteById(id);
    }
}

package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.DistrictsCultureService;
import com.vertical.commerce.domain.DistrictsCulture;
import com.vertical.commerce.repository.DistrictsCultureRepository;
import com.vertical.commerce.service.dto.DistrictsCultureDTO;
import com.vertical.commerce.service.mapper.DistrictsCultureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DistrictsCulture}.
 */
@Service
@Transactional
public class DistrictsCultureServiceImpl implements DistrictsCultureService {

    private final Logger log = LoggerFactory.getLogger(DistrictsCultureServiceImpl.class);

    private final DistrictsCultureRepository districtsCultureRepository;

    private final DistrictsCultureMapper districtsCultureMapper;

    public DistrictsCultureServiceImpl(DistrictsCultureRepository districtsCultureRepository, DistrictsCultureMapper districtsCultureMapper) {
        this.districtsCultureRepository = districtsCultureRepository;
        this.districtsCultureMapper = districtsCultureMapper;
    }

    /**
     * Save a districtsCulture.
     *
     * @param districtsCultureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DistrictsCultureDTO save(DistrictsCultureDTO districtsCultureDTO) {
        log.debug("Request to save DistrictsCulture : {}", districtsCultureDTO);
        DistrictsCulture districtsCulture = districtsCultureMapper.toEntity(districtsCultureDTO);
        districtsCulture = districtsCultureRepository.save(districtsCulture);
        return districtsCultureMapper.toDto(districtsCulture);
    }

    /**
     * Get all the districtsCultures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DistrictsCultureDTO> findAll() {
        log.debug("Request to get all DistrictsCultures");
        return districtsCultureRepository.findAll().stream()
            .map(districtsCultureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one districtsCulture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DistrictsCultureDTO> findOne(Long id) {
        log.debug("Request to get DistrictsCulture : {}", id);
        return districtsCultureRepository.findById(id)
            .map(districtsCultureMapper::toDto);
    }

    /**
     * Delete the districtsCulture by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DistrictsCulture : {}", id);

        districtsCultureRepository.deleteById(id);
    }
}

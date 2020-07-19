package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.DistrictsService;
import com.vertical.commerce.domain.Districts;
import com.vertical.commerce.repository.DistrictsRepository;
import com.vertical.commerce.service.dto.DistrictsDTO;
import com.vertical.commerce.service.mapper.DistrictsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Districts}.
 */
@Service
@Transactional
public class DistrictsServiceImpl implements DistrictsService {

    private final Logger log = LoggerFactory.getLogger(DistrictsServiceImpl.class);

    private final DistrictsRepository districtsRepository;

    private final DistrictsMapper districtsMapper;

    public DistrictsServiceImpl(DistrictsRepository districtsRepository, DistrictsMapper districtsMapper) {
        this.districtsRepository = districtsRepository;
        this.districtsMapper = districtsMapper;
    }

    /**
     * Save a districts.
     *
     * @param districtsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DistrictsDTO save(DistrictsDTO districtsDTO) {
        log.debug("Request to save Districts : {}", districtsDTO);
        Districts districts = districtsMapper.toEntity(districtsDTO);
        districts = districtsRepository.save(districts);
        return districtsMapper.toDto(districts);
    }

    /**
     * Get all the districts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DistrictsDTO> findAll() {
        log.debug("Request to get all Districts");
        return districtsRepository.findAll().stream()
            .map(districtsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one districts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DistrictsDTO> findOne(Long id) {
        log.debug("Request to get Districts : {}", id);
        return districtsRepository.findById(id)
            .map(districtsMapper::toDto);
    }

    /**
     * Delete the districts by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Districts : {}", id);

        districtsRepository.deleteById(id);
    }
}

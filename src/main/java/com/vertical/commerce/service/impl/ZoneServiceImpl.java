package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.ZoneService;
import com.vertical.commerce.domain.Zone;
import com.vertical.commerce.repository.ZoneRepository;
import com.vertical.commerce.service.dto.ZoneDTO;
import com.vertical.commerce.service.mapper.ZoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Zone}.
 */
@Service
@Transactional
public class ZoneServiceImpl implements ZoneService {

    private final Logger log = LoggerFactory.getLogger(ZoneServiceImpl.class);

    private final ZoneRepository zoneRepository;

    private final ZoneMapper zoneMapper;

    public ZoneServiceImpl(ZoneRepository zoneRepository, ZoneMapper zoneMapper) {
        this.zoneRepository = zoneRepository;
        this.zoneMapper = zoneMapper;
    }

    /**
     * Save a zone.
     *
     * @param zoneDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ZoneDTO save(ZoneDTO zoneDTO) {
        log.debug("Request to save Zone : {}", zoneDTO);
        Zone zone = zoneMapper.toEntity(zoneDTO);
        zone = zoneRepository.save(zone);
        return zoneMapper.toDto(zone);
    }

    /**
     * Get all the zones.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ZoneDTO> findAll() {
        log.debug("Request to get all Zones");
        return zoneRepository.findAll().stream()
            .map(zoneMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one zone by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ZoneDTO> findOne(Long id) {
        log.debug("Request to get Zone : {}", id);
        return zoneRepository.findById(id)
            .map(zoneMapper::toDto);
    }

    /**
     * Delete the zone by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Zone : {}", id);

        zoneRepository.deleteById(id);
    }
}

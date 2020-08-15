package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.PostalCodeMappersLocalizeService;
import com.vertical.commerce.domain.PostalCodeMappersLocalize;
import com.vertical.commerce.repository.PostalCodeMappersLocalizeRepository;
import com.vertical.commerce.service.dto.PostalCodeMappersLocalizeDTO;
import com.vertical.commerce.service.mapper.PostalCodeMappersLocalizeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PostalCodeMappersLocalize}.
 */
@Service
@Transactional
public class PostalCodeMappersLocalizeServiceImpl implements PostalCodeMappersLocalizeService {

    private final Logger log = LoggerFactory.getLogger(PostalCodeMappersLocalizeServiceImpl.class);

    private final PostalCodeMappersLocalizeRepository postalCodeMappersLocalizeRepository;

    private final PostalCodeMappersLocalizeMapper postalCodeMappersLocalizeMapper;

    public PostalCodeMappersLocalizeServiceImpl(PostalCodeMappersLocalizeRepository postalCodeMappersLocalizeRepository, PostalCodeMappersLocalizeMapper postalCodeMappersLocalizeMapper) {
        this.postalCodeMappersLocalizeRepository = postalCodeMappersLocalizeRepository;
        this.postalCodeMappersLocalizeMapper = postalCodeMappersLocalizeMapper;
    }

    /**
     * Save a postalCodeMappersLocalize.
     *
     * @param postalCodeMappersLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PostalCodeMappersLocalizeDTO save(PostalCodeMappersLocalizeDTO postalCodeMappersLocalizeDTO) {
        log.debug("Request to save PostalCodeMappersLocalize : {}", postalCodeMappersLocalizeDTO);
        PostalCodeMappersLocalize postalCodeMappersLocalize = postalCodeMappersLocalizeMapper.toEntity(postalCodeMappersLocalizeDTO);
        postalCodeMappersLocalize = postalCodeMappersLocalizeRepository.save(postalCodeMappersLocalize);
        return postalCodeMappersLocalizeMapper.toDto(postalCodeMappersLocalize);
    }

    /**
     * Get all the postalCodeMappersLocalizes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PostalCodeMappersLocalizeDTO> findAll() {
        log.debug("Request to get all PostalCodeMappersLocalizes");
        return postalCodeMappersLocalizeRepository.findAll().stream()
            .map(postalCodeMappersLocalizeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one postalCodeMappersLocalize by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PostalCodeMappersLocalizeDTO> findOne(Long id) {
        log.debug("Request to get PostalCodeMappersLocalize : {}", id);
        return postalCodeMappersLocalizeRepository.findById(id)
            .map(postalCodeMappersLocalizeMapper::toDto);
    }

    /**
     * Delete the postalCodeMappersLocalize by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PostalCodeMappersLocalize : {}", id);

        postalCodeMappersLocalizeRepository.deleteById(id);
    }
}

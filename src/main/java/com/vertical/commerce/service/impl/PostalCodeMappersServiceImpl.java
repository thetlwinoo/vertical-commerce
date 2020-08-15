package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.PostalCodeMappersService;
import com.vertical.commerce.domain.PostalCodeMappers;
import com.vertical.commerce.repository.PostalCodeMappersRepository;
import com.vertical.commerce.service.dto.PostalCodeMappersDTO;
import com.vertical.commerce.service.mapper.PostalCodeMappersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PostalCodeMappers}.
 */
@Service
@Transactional
public class PostalCodeMappersServiceImpl implements PostalCodeMappersService {

    private final Logger log = LoggerFactory.getLogger(PostalCodeMappersServiceImpl.class);

    private final PostalCodeMappersRepository postalCodeMappersRepository;

    private final PostalCodeMappersMapper postalCodeMappersMapper;

    public PostalCodeMappersServiceImpl(PostalCodeMappersRepository postalCodeMappersRepository, PostalCodeMappersMapper postalCodeMappersMapper) {
        this.postalCodeMappersRepository = postalCodeMappersRepository;
        this.postalCodeMappersMapper = postalCodeMappersMapper;
    }

    /**
     * Save a postalCodeMappers.
     *
     * @param postalCodeMappersDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PostalCodeMappersDTO save(PostalCodeMappersDTO postalCodeMappersDTO) {
        log.debug("Request to save PostalCodeMappers : {}", postalCodeMappersDTO);
        PostalCodeMappers postalCodeMappers = postalCodeMappersMapper.toEntity(postalCodeMappersDTO);
        postalCodeMappers = postalCodeMappersRepository.save(postalCodeMappers);
        return postalCodeMappersMapper.toDto(postalCodeMappers);
    }

    /**
     * Get all the postalCodeMappers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PostalCodeMappersDTO> findAll() {
        log.debug("Request to get all PostalCodeMappers");
        return postalCodeMappersRepository.findAll().stream()
            .map(postalCodeMappersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one postalCodeMappers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PostalCodeMappersDTO> findOne(Long id) {
        log.debug("Request to get PostalCodeMappers : {}", id);
        return postalCodeMappersRepository.findById(id)
            .map(postalCodeMappersMapper::toDto);
    }

    /**
     * Delete the postalCodeMappers by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PostalCodeMappers : {}", id);

        postalCodeMappersRepository.deleteById(id);
    }
}

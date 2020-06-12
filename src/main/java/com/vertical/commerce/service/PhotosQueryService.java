package com.vertical.commerce.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.vertical.commerce.domain.Photos;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.PhotosRepository;
import com.vertical.commerce.service.dto.PhotosCriteria;
import com.vertical.commerce.service.dto.PhotosDTO;
import com.vertical.commerce.service.mapper.PhotosMapper;

/**
 * Service for executing complex queries for {@link Photos} entities in the database.
 * The main input is a {@link PhotosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PhotosDTO} or a {@link Page} of {@link PhotosDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PhotosQueryService extends QueryService<Photos> {

    private final Logger log = LoggerFactory.getLogger(PhotosQueryService.class);

    private final PhotosRepository photosRepository;

    private final PhotosMapper photosMapper;

    public PhotosQueryService(PhotosRepository photosRepository, PhotosMapper photosMapper) {
        this.photosRepository = photosRepository;
        this.photosMapper = photosMapper;
    }

    /**
     * Return a {@link List} of {@link PhotosDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PhotosDTO> findByCriteria(PhotosCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Photos> specification = createSpecification(criteria);
        return photosMapper.toDto(photosRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PhotosDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PhotosDTO> findByCriteria(PhotosCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Photos> specification = createSpecification(criteria);
        return photosRepository.findAll(specification, page)
            .map(photosMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PhotosCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Photos> specification = createSpecification(criteria);
        return photosRepository.count(specification);
    }

    /**
     * Function to convert {@link PhotosCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Photos> createSpecification(PhotosCriteria criteria) {
        Specification<Photos> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Photos_.id));
            }
            if (criteria.getBlobId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBlobId(), Photos_.blobId));
            }
            if (criteria.getThumbnailUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getThumbnailUrl(), Photos_.thumbnailUrl));
            }
            if (criteria.getOriginalUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOriginalUrl(), Photos_.originalUrl));
            }
            if (criteria.getBannerTallUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBannerTallUrl(), Photos_.bannerTallUrl));
            }
            if (criteria.getBannerWideUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBannerWideUrl(), Photos_.bannerWideUrl));
            }
            if (criteria.getCircleUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCircleUrl(), Photos_.circleUrl));
            }
            if (criteria.getSharpenedUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSharpenedUrl(), Photos_.sharpenedUrl));
            }
            if (criteria.getSquareUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSquareUrl(), Photos_.squareUrl));
            }
            if (criteria.getWatermarkUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWatermarkUrl(), Photos_.watermarkUrl));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriority(), Photos_.priority));
            }
            if (criteria.getDefaultInd() != null) {
                specification = specification.and(buildSpecification(criteria.getDefaultInd(), Photos_.defaultInd));
            }
            if (criteria.getStockItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockItemId(),
                    root -> root.join(Photos_.stockItem, JoinType.LEFT).get(StockItems_.id)));
            }
        }
        return specification;
    }
}

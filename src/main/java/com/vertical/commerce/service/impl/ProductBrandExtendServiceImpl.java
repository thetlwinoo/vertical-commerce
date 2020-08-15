package com.vertical.commerce.service.impl;

import com.vertical.commerce.domain.ProductBrand;
import com.vertical.commerce.repository.ProductBrandExtendRepository;
import com.vertical.commerce.repository.ProductBrandRepository;
import com.vertical.commerce.service.ProductBrandExtendService;
import com.vertical.commerce.service.dto.ProductBrandDTO;
import com.vertical.commerce.service.mapper.ProductBrandMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductBrandExtendServiceImpl implements ProductBrandExtendService {

    private final Logger log = LoggerFactory.getLogger(ProductBrandExtendServiceImpl.class);
    private final ProductBrandRepository productBrandRepository;
    private final ProductBrandMapper productBrandMapper;
    private final ProductBrandExtendRepository productBrandExtendRepository;

    public ProductBrandExtendServiceImpl(ProductBrandRepository productBrandRepository, ProductBrandMapper productBrandMapper, ProductBrandExtendRepository productBrandExtendRepository) {
        this.productBrandRepository = productBrandRepository;
        this.productBrandMapper = productBrandMapper;
        this.productBrandExtendRepository = productBrandExtendRepository;
    }

    @Override
    public ProductBrandDTO save(ProductBrandDTO productBrandDTO) {
        ProductBrand productBrand;

        if(productBrandExtendRepository.existsByName(productBrandDTO.getName())) {
            productBrand = productBrandExtendRepository.findDistinctTopByNameEquals(productBrandDTO.getName());
        }else{
            productBrand = productBrandMapper.toEntity(productBrandDTO);
            productBrand = productBrandRepository.saveAndFlush(productBrand);
            return productBrandMapper.toDto(productBrand);
        }

        return productBrandMapper.toDto(productBrand);
    }
}

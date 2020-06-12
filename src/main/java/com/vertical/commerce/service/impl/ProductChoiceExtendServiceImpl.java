package com.vertical.commerce.service.impl;

import com.vertical.commerce.repository.ProductChoiceExtendRepository;
import com.vertical.commerce.service.ProductChoiceExtendService;
import com.vertical.commerce.service.dto.ProductChoiceDTO;
import com.vertical.commerce.service.mapper.ProductChoiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductChoiceExtendServiceImpl implements ProductChoiceExtendService {

    private final Logger log = LoggerFactory.getLogger(ProductChoiceExtendServiceImpl.class);
    private final ProductChoiceExtendRepository productChoiceExtendRepository;
    private final ProductChoiceMapper productChoiceMapper;

    public ProductChoiceExtendServiceImpl(ProductChoiceExtendRepository productChoiceExtendRepository, ProductChoiceMapper productChoiceMapper) {
        this.productChoiceExtendRepository = productChoiceExtendRepository;
        this.productChoiceMapper = productChoiceMapper;
    }

    @Override
    public List<ProductChoiceDTO> getAllProductChoice(Long categoryId) {
        return productChoiceExtendRepository.findAllByProductCategoryId(categoryId).stream()
            .map(productChoiceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}

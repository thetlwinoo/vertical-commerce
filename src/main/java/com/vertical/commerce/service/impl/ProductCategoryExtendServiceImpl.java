package com.vertical.commerce.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vertical.commerce.repository.ProductCategoryExtendRepository;
import com.vertical.commerce.service.ProductCategoryExtendService;
import com.vertical.commerce.service.dto.ProductCategoryDTO;
import com.vertical.commerce.service.mapper.ProductCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductCategoryExtendServiceImpl implements ProductCategoryExtendService {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryExtendServiceImpl.class);
    private final ProductCategoryExtendRepository productCategoryExtendRepository;
    private final ProductCategoryMapper productCategoryMapper;

    public ProductCategoryExtendServiceImpl(ProductCategoryExtendRepository productCategoryExtendRepository, ProductCategoryMapper productCategoryMapper) {
        this.productCategoryExtendRepository = productCategoryExtendRepository;
        this.productCategoryMapper = productCategoryMapper;
    }

    @Override
    public String getCategoriesTree(String idList) {
        String result =  productCategoryExtendRepository.getProductCategoryTree("{" + idList + "}");
        return result;
    }

//    @Override
//    public JsonNode getCategoriesTree(Boolean showNav) {
//
//        try {
//            List<ProductCategoryDTO> rootCategoriesDTO;
//            rootCategoriesDTO = productCategoryExtendRepository.findAllByParentIdIsNullOrderBySortOrder()
//                .stream()
//                .map(productCategoryMapper::toDto)
//                .collect(Collectors.toCollection(LinkedList::new));
//
//            ObjectMapper mapper = new ObjectMapper();
//            JsonNode rootNode = mapper.createArrayNode();
//
//            for (ProductCategoryDTO parentCategoryDTO : rootCategoriesDTO) {
//
//                List<ProductCategoryDTO> childCategoriesDTO = productCategoryExtendRepository.findAllByParentIdOrderBySortOrder(parentCategoryDTO.getId())
//                    .stream()
//                    .map(productCategoryMapper::toDto)
//                    .collect(Collectors.toCollection(LinkedList::new));
//
//                JsonNode categoryNode = mapper.convertValue(parentCategoryDTO, JsonNode.class);
//
//                JsonNode subCategoryNodes = mapper.valueToTree(childCategoriesDTO);
//                ((ObjectNode) categoryNode).put("children", subCategoryNodes);
//                ((ArrayNode) rootNode).add(categoryNode);
//            }
//
//            return rootNode;
//        } catch (Exception ex) {
//            throw new IllegalArgumentException(ex.getMessage());
//        }
//
//    }
}

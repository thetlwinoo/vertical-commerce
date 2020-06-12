package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ProductChoice;

import java.util.List;

public interface ProductChoiceExtendRepository extends ProductChoiceRepository {
    List<ProductChoice> findAllByProductCategoryId(Long categoryId);
}

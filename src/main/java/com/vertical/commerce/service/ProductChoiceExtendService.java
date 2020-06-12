package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.ProductChoiceDTO;

import java.util.List;

public interface ProductChoiceExtendService {
    List<ProductChoiceDTO> getAllProductChoice(Long categoryId);
}

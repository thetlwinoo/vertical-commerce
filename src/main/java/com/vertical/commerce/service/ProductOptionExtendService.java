package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.ProductOptionDTO;

import java.security.Principal;
import java.util.List;

public interface ProductOptionExtendService {
    List<ProductOptionDTO> getAllProductOptions(Long optionSetId, Principal principal);
}

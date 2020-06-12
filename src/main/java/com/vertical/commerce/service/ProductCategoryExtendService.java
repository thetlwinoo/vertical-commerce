package com.vertical.commerce.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface ProductCategoryExtendService {
    JsonNode getCategoriesTree(Boolean showNav);
}

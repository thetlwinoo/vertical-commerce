package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.ProductDocumentDTO;

import java.security.Principal;

public interface ProductDocumentExtendService {
    ProductDocumentDTO importProductDocument(ProductDocumentDTO productDocumentDTO, Principal principal);
}

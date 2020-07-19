package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.ProductDocumentsDTO;

import java.security.Principal;

public interface ProductDocumentExtendService {
    ProductDocumentsDTO importProductDocument(ProductDocumentsDTO productDocumentDTO, Principal principal);
}

package com.vertical.commerce.service.impl;

import com.vertical.commerce.domain.*;
import com.vertical.commerce.repository.ProductDocumentsRepository;
import com.vertical.commerce.repository.ProductsRepository;
import com.vertical.commerce.security.SecurityUtils;
import com.vertical.commerce.service.CommonService;
import com.vertical.commerce.service.ProductDocumentExtendService;
import com.vertical.commerce.service.dto.ProductDocumentsDTO;
import com.vertical.commerce.service.mapper.ProductDocumentsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.Instant;

@Service
@Transactional
public class ProductDocumentExtendServiceImpl implements ProductDocumentExtendService {

    private final Logger log = LoggerFactory.getLogger(ProductDocumentExtendServiceImpl.class);
    private final CommonService commonService;
    private final ProductDocumentsRepository productDocumentRepository;
    private final ProductDocumentsMapper productDocumentMapper;
    private final ProductsRepository productsRepository;

    public ProductDocumentExtendServiceImpl(CommonService commonService, ProductDocumentsRepository productDocumentRepository, ProductDocumentsMapper productDocumentMapper, ProductsRepository productsRepository) {
        this.commonService = commonService;
        this.productDocumentRepository = productDocumentRepository;
        this.productDocumentMapper = productDocumentMapper;
        this.productsRepository = productsRepository;
    }

    @Override
    public ProductDocumentsDTO importProductDocument(ProductDocumentsDTO productDocumentsDTO, Principal principal){
        ProductDocuments productDocument = new ProductDocuments();
//        People people = commonService.getPeopleByPrincipal(principal);
        String userLogin = SecurityUtils.getCurrentUserLogin().get();

        try{
            productDocument.setId(productDocumentsDTO.getId());
            productDocument.setVideoUrl(productDocumentsDTO.getVideoUrl());
            productDocument.setHighlights(productDocumentsDTO.getHighlights());
            productDocument.setLongDescription(productDocumentsDTO.getLongDescription());
            productDocument.setShortDescription(productDocumentsDTO.getShortDescription());
            productDocument.setCareInstructions(productDocumentsDTO.getCareInstructions());
            productDocument.setProductType(productDocumentsDTO.getProductType());
            productDocument.setModelName(productDocumentsDTO.getModelName());
            productDocument.setModelNumber(productDocumentsDTO.getModelNumber());
            productDocument.setFabricType(productDocumentsDTO.getFabricType());
            productDocument.setSpecialFeatures(productDocumentsDTO.getSpecialFeatures());
            productDocument.setProductComplianceCertificate(productDocumentsDTO.getProductComplianceCertificate());
            productDocument.setGenuineAndLegal(productDocumentsDTO.isGenuineAndLegal());
            productDocument.setCountryOfOrigin(productDocumentsDTO.getCountryOfOrigin());
            productDocument.setUsageAndSideEffects(productDocumentsDTO.getUsageAndSideEffects());
            productDocument.setSafetyWarnning(productDocumentsDTO.getSafetyWarnning());
            productDocument.setWarrantyPeriod(productDocumentsDTO.getWarrantyPeriod());
            productDocument.setWarrantyPolicy(productDocumentsDTO.getWarrantyPolicy());
            productDocument.setWhatInTheBox(productDocumentsDTO.getWhatInTheBox());
            productDocument.setDangerousGoods(productDocumentsDTO.getDangerousGoods());
            productDocument.setLastEditedBy(userLogin);
            productDocument.setLastEditedWhen(Instant.now());
            WarrantyTypes warrantyTypes= commonService.getWarrantyTypesEntity(productDocumentsDTO.getWarrantyTypeId(),productDocumentsDTO.getWarrantyTypeName());
            productDocument.setWarrantyType(warrantyTypes);
            Products products = productsRepository.getOne(productDocumentsDTO.getProductId());
            productDocument.setProduct(products);
            productDocument = productDocumentRepository.save(productDocument);
        }catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return productDocumentMapper.toDto(productDocument);
    }
}

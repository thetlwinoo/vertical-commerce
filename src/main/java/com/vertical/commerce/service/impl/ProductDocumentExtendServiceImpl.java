package com.vertical.commerce.service.impl;

import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.People;
import com.vertical.commerce.domain.ProductDocuments;
import com.vertical.commerce.domain.WarrantyTypes;
import com.vertical.commerce.repository.ProductDocumentsRepository;
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
import java.util.Optional;

@Service
@Transactional
public class ProductDocumentExtendServiceImpl implements ProductDocumentExtendService {

    private final Logger log = LoggerFactory.getLogger(ProductDocumentExtendServiceImpl.class);
    private final CommonService commonService;
    private final ProductDocumentsRepository productDocumentRepository;
    private final ProductDocumentsMapper productDocumentMapper;

    public ProductDocumentExtendServiceImpl(CommonService commonService, ProductDocumentsRepository productDocumentRepository, ProductDocumentsMapper productDocumentMapper) {
        this.commonService = commonService;
        this.productDocumentRepository = productDocumentRepository;
        this.productDocumentMapper = productDocumentMapper;
    }

    @Override
    public ProductDocumentsDTO importProductDocument(ProductDocumentsDTO ProductDocumentsDTO, Principal principal){
        ProductDocuments productDocument = new ProductDocuments();
//        People people = commonService.getPeopleByPrincipal(principal);
        String userLogin = SecurityUtils.getCurrentUserLogin().get();

        try{
            productDocument.setId(ProductDocumentsDTO.getId());
            productDocument.setVideoUrl(ProductDocumentsDTO.getVideoUrl());
            productDocument.setHighlights(ProductDocumentsDTO.getHighlights());
            productDocument.setLongDescription(ProductDocumentsDTO.getLongDescription());
            productDocument.setShortDescription(ProductDocumentsDTO.getShortDescription());
            productDocument.setCareInstructions(ProductDocumentsDTO.getCareInstructions());
            productDocument.setProductType(ProductDocumentsDTO.getProductType());
            productDocument.setModelName(ProductDocumentsDTO.getModelName());
            productDocument.setModelNumber(ProductDocumentsDTO.getModelNumber());
            productDocument.setFabricType(ProductDocumentsDTO.getFabricType());
            productDocument.setSpecialFeatures(ProductDocumentsDTO.getSpecialFeatures());
            productDocument.setProductComplianceCertificate(ProductDocumentsDTO.getProductComplianceCertificate());
            productDocument.setGenuineAndLegal(ProductDocumentsDTO.isGenuineAndLegal());
            productDocument.setCountryOfOrigin(ProductDocumentsDTO.getCountryOfOrigin());
            productDocument.setUsageAndSideEffects(ProductDocumentsDTO.getUsageAndSideEffects());
            productDocument.setSafetyWarnning(ProductDocumentsDTO.getSafetyWarnning());
            productDocument.setWarrantyPeriod(ProductDocumentsDTO.getWarrantyPeriod());
            productDocument.setWarrantyPolicy(ProductDocumentsDTO.getWarrantyPolicy());
            productDocument.setWhatInTheBox(ProductDocumentsDTO.getWhatInTheBox());
            productDocument.setDangerousGoods(ProductDocumentsDTO.getDangerousGoods());
            productDocument.setLastEditedBy(userLogin);
            productDocument.setLastEditedWhen(Instant.now());
            WarrantyTypes warrantyTypes= commonService.getWarrantyTypesEntity(ProductDocumentsDTO.getWarrantyTypeId(),ProductDocumentsDTO.getWarrantyTypeName());
            productDocument.setWarrantyType(warrantyTypes);

            productDocument = productDocumentRepository.save(productDocument);
        }catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return productDocumentMapper.toDto(productDocument);
    }
}

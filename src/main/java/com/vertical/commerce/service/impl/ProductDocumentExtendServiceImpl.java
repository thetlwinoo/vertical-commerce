package com.vertical.commerce.service.impl;

import com.vertical.commerce.domain.Culture;
import com.vertical.commerce.domain.People;
import com.vertical.commerce.domain.ProductDocument;
import com.vertical.commerce.domain.WarrantyTypes;
import com.vertical.commerce.repository.ProductDocumentRepository;
import com.vertical.commerce.service.CommonService;
import com.vertical.commerce.service.ProductDocumentExtendService;
import com.vertical.commerce.service.dto.ProductDocumentDTO;
import com.vertical.commerce.service.mapper.ProductDocumentMapper;
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
    private final ProductDocumentRepository productDocumentRepository;
    private final ProductDocumentMapper productDocumentMapper;

    public ProductDocumentExtendServiceImpl(CommonService commonService, ProductDocumentRepository productDocumentRepository, ProductDocumentMapper productDocumentMapper) {
        this.commonService = commonService;
        this.productDocumentRepository = productDocumentRepository;
        this.productDocumentMapper = productDocumentMapper;
    }

    @Override
    public ProductDocumentDTO importProductDocument(ProductDocumentDTO productDocumentDTO, Principal principal){
        ProductDocument productDocument = new ProductDocument();
        People people = commonService.getPeopleByPrincipal(principal);

        try{
            productDocument.setId(productDocumentDTO.getId());
            productDocument.setVideoUrl(productDocumentDTO.getVideoUrl());
            productDocument.setHighlights(productDocumentDTO.getHighlights());
            productDocument.setLongDescription(productDocumentDTO.getLongDescription());
            productDocument.setShortDescription(productDocumentDTO.getShortDescription());
            productDocument.setCareInstructions(productDocumentDTO.getCareInstructions());
            productDocument.setProductType(productDocumentDTO.getProductType());
            productDocument.setModelName(productDocumentDTO.getModelName());
            productDocument.setModelNumber(productDocumentDTO.getModelNumber());
            productDocument.setFabricType(productDocumentDTO.getFabricType());
            productDocument.setSpecialFeatures(productDocumentDTO.getSpecialFeatures());
            productDocument.setProductComplianceCertificate(productDocumentDTO.getProductComplianceCertificate());
            productDocument.setGenuineAndLegal(productDocumentDTO.isGenuineAndLegal());
            productDocument.setCountryOfOrigin(productDocumentDTO.getCountryOfOrigin());
            productDocument.setUsageAndSideEffects(productDocumentDTO.getUsageAndSideEffects());
            productDocument.setSafetyWarnning(productDocumentDTO.getSafetyWarnning());
            productDocument.setWarrantyPeriod(productDocumentDTO.getWarrantyPeriod());
            productDocument.setWarrantyPolicy(productDocumentDTO.getWarrantyPolicy());
            productDocument.setWhatInTheBox(productDocumentDTO.getWhatInTheBox());
            productDocument.setDangerousGoods(productDocumentDTO.getDangerousGoods());
            productDocument.setLastEditedBy(people.getFullName());
            productDocument.setLastEditedWhen(Instant.now());
            WarrantyTypes warrantyTypes= commonService.getWarrantyTypesEntity(productDocumentDTO.getWarrantyTypeId(),productDocumentDTO.getWarrantyTypeName());
            productDocument.setWarrantyType(warrantyTypes);

            Culture culture = commonService.getCultureEntity(productDocumentDTO.getCultureId(),productDocumentDTO.getCultureName());
            productDocument.setCulture(culture);

            productDocument = productDocumentRepository.save(productDocument);
        }catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return productDocumentMapper.toDto(productDocument);
    }
}

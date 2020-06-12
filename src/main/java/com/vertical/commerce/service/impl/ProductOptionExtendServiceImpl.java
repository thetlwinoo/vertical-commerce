package com.vertical.commerce.service.impl;

import com.vertical.commerce.domain.People;
import com.vertical.commerce.domain.Suppliers;
import com.vertical.commerce.domain.User;
import com.vertical.commerce.repository.PeopleExtendRepository;
import com.vertical.commerce.repository.ProductOptionExtendRepository;
import com.vertical.commerce.repository.SuppliersExtendRepository;
import com.vertical.commerce.repository.UserRepository;
import com.vertical.commerce.service.CommonService;
import com.vertical.commerce.service.ProductOptionExtendService;
import com.vertical.commerce.service.dto.ProductOptionDTO;
import com.vertical.commerce.service.mapper.ProductOptionMapper;
import com.vertical.commerce.service.mapper.SuppliersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductOptionExtendServiceImpl implements ProductOptionExtendService {

    private final Logger log = LoggerFactory.getLogger(ProductOptionExtendServiceImpl.class);
    private final PeopleExtendRepository peopleExtendRepository;
    private final SuppliersExtendRepository suppliersExtendRepository;
    private final SuppliersMapper suppliersMapper;
    private final ProductOptionExtendRepository productOptionExtendRepository;
    private final ProductOptionMapper productOptionMapper;
    private final UserRepository userRepository;
    private final CommonService commonService;


    public ProductOptionExtendServiceImpl(PeopleExtendRepository peopleExtendRepository, SuppliersExtendRepository suppliersExtendRepository, SuppliersMapper suppliersMapper, ProductOptionExtendRepository productOptionExtendRepository, ProductOptionMapper productOptionMapper, UserRepository userRepository, CommonService commonService) {
        this.peopleExtendRepository = peopleExtendRepository;
        this.suppliersExtendRepository = suppliersExtendRepository;
        this.suppliersMapper = suppliersMapper;
        this.productOptionExtendRepository = productOptionExtendRepository;
        this.productOptionMapper = productOptionMapper;
        this.userRepository = userRepository;
        this.commonService = commonService;
    }

    @Override
    public List<ProductOptionDTO> getAllProductOptions(Long optionSetId, Principal principal) {
        log.debug("Request to get all ProductAttributes");
        Suppliers suppliers = commonService.getSupplierByPrincipal(principal);

        return productOptionExtendRepository.findAllByProductOptionSetIdAndSupplierId(optionSetId,suppliers.getId()).stream()
            .map(productOptionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}

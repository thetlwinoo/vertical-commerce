package com.vertical.commerce.service.impl;

import com.vertical.commerce.domain.*;
import com.vertical.commerce.repository.*;
import com.vertical.commerce.service.CommonService;
import com.vertical.commerce.service.ProductsExtendService;
import com.vertical.commerce.service.ProductsQueryService;
import com.vertical.commerce.service.dto.ProductCategoryDTO;
import com.vertical.commerce.service.dto.ProductsCriteria;
import com.vertical.commerce.service.dto.ProductsDTO;
import com.vertical.commerce.service.mapper.ProductCategoryMapper;
import com.vertical.commerce.service.mapper.ProductsMapper;
import com.vertical.commerce.service.util.CommonUtil;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductsExtendServiceImpl implements ProductsExtendService {

    private final Logger log = LoggerFactory.getLogger(ProductsExtendServiceImpl.class);
    private final ProductsExtendRepository productsExtendRepository;
    private final ProductsRepository productsRepository;
    private final ProductsExtendFilterRepository productsExtendFilterRepository;
    private final ProductCategoryMapper productCategoryMapper;
    private final CommonService commonService;
    private final ProductsMapper productsMapper;
    private final ProductDocumentRepository productDocumentRepository;
    private final ProductsQueryService productsQueryService;
    private final ProductTagsRepository productTagsRepository;

    public ProductsExtendServiceImpl(ProductsExtendRepository productsExtendRepository, ProductsRepository productsRepository, ProductsExtendFilterRepository productsExtendFilterRepository, ProductCategoryMapper productCategoryMapper,CommonService commonService, ProductsMapper productsMapper, ProductDocumentRepository productDocumentRepository, ProductsQueryService productsQueryService, ProductTagsRepository productTagsRepository) {
        this.productsExtendRepository = productsExtendRepository;
        this.productsRepository = productsRepository;
        this.productsExtendFilterRepository = productsExtendFilterRepository;
        this.productCategoryMapper = productCategoryMapper;
        this.commonService = commonService;
        this.productsMapper = productsMapper;
        this.productDocumentRepository = productDocumentRepository;
        this.productsQueryService = productsQueryService;
        this.productTagsRepository = productTagsRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductsDTO> findOne(Long id) {
        log.debug("Request to get Products : {}", id);
        return productsRepository.findById(id)
            .map(productsMapper::toDto);
    }

    @Override
    @Cacheable(key = "{#pageable,#productCategoryId}")
    public List<ProductsDTO> findAllByProductCategory(Pageable pageable, Long productCategoryId) {
        return productsExtendRepository.findAllByProductCategoryId(pageable, productCategoryId).stream()
            .map(productsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
//    @Cacheable(key = "#root.methodName")
    public List<ProductsDTO> findTop18ByOrderByLastEditedWhenDesc() {
        return productsExtendRepository.findTop18ByOrderByLastEditedWhenDesc()
            .stream()
            .map(productsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
//    @Cacheable(key = "#root.methodName")
    public List<ProductsDTO> findTop12ByOrderBySellCountDesc() {
        return productsExtendRepository.findTop12ByOrderBySellCountDesc()
            .stream()
            .map(productsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
//    @CachePut(key = "'findTop12ByOrderBySellCountDesc'")
    public List<ProductsDTO> findTop12ByOrderBySellCountDescCacheRefresh() {
        return productsExtendRepository.findTop12ByOrderBySellCountDesc()
            .stream()
            .map(productsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
//    @Cacheable(key = "#root.methodName")
    public List<ProductsDTO> getRelatedProducts(Long productCategoryId, Long id) {
        List<Products> returnList = productsExtendRepository.findTop12ByProductCategoryIdAndIdIsNotOrderBySellCountDesc(productCategoryId, id);
        if (returnList.size() < 8) {
            returnList.addAll(productsExtendRepository.findAllByProductCategoryIdIsNotOrderBySellCountDesc(productCategoryId, PageRequest.of(0, 8 - returnList.size())));
        }
        return returnList
            .stream()
            .map(productsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<ProductsDTO> searchProducts(String keyword, Integer page, Integer size) {
        if (page == null || size == null) {
            throw new IllegalArgumentException("Page and size parameters are required");
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        return productsExtendRepository.findAllByNameContainingIgnoreCase(keyword, pageRequest)
            .stream()
            .map(productsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<ProductsDTO> searchProductsAll(String keyword) {
        return productsExtendRepository.findAllByNameContainingIgnoreCase(keyword)
            .stream()
            .map(productsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<ProductsDTO> searchKeywords(String keyword) {
        return productsExtendRepository.findAllBySearchDetailsContainingIgnoreCase(keyword)
            .stream()
            .map(productsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Page<ProductsDTO> searchProductsWithPaging(ProductsCriteria criteria, Pageable pageable, String colors) {
        if(!CommonUtil.isNullOrEmptyString(colors)){
            criteria = getCriteriaByColors(criteria,colors);
        }

        return productsQueryService.findByCriteria(criteria, pageable);
    }

    private ProductsCriteria getCriteriaByColors(ProductsCriteria criteria, String colors){
        List<Long> productIds = productsExtendFilterRepository.getIdsByColors(colors.split(","));
        LongFilter longFilter = new LongFilter();
        longFilter.setIn(productIds);
        criteria.setId(longFilter);

        return criteria;
    }

    @Override
    public List<Long> getSubCategoryList(Long categoryId) {
        return productsExtendFilterRepository.getCategoryIds(categoryId);
    }

    @Override
    public List<ProductCategoryDTO> getRelatedCategories(String keyword, Long category) {
        try {
            List<ProductCategoryDTO> returnList = productsExtendFilterRepository.selectCategoriesByTags(keyword == null ? "" : keyword, category == null ? 0 : category)
                .stream()
                .map(productCategoryMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
            return returnList;
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

    }

    @Override
    public List<String> getRelatedColors(String keyword, Long category) {
        try {
            return productsExtendFilterRepository.selectColorsByTags(keyword == null ? "" : keyword, category == null ? 0 : category);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @Override
    public List<String> getRelatedBrands(String keyword, Long category) {
        return productsExtendFilterRepository.selectBrandsByTags(keyword == null ? "" : keyword, category == null ? 0 : category);
    }

    @Override
    public Object getRelatedPriceRange(String keyword, Long category) {
        try {
            return productsExtendFilterRepository.selectPriceRangeByTags(keyword == null ? "" : keyword, category == null ? 0 : category);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductsDTO saveProducts(Products products, String serverUrl) {
        Products saveProduct = new Products();
        return productsMapper.toDto(saveProduct);
    }

    @Override
    @Transactional
    public ProductsDTO importProducts(ProductsDTO productsDTO, Principal principal) {
        Products saveProduct = new Products();
        People people = commonService.getPeopleByPrincipal(principal);

        try{
            saveProduct.setId(productsDTO.getId());
            saveProduct.setName(productsDTO.getName());
            saveProduct.setHandle(productsDTO.getHandle());
            saveProduct.setSearchDetails(productsDTO.getSearchDetails());
            saveProduct.setActiveInd(productsDTO.isActiveInd());
            ProductCategory productCategory = commonService.getProductCategoryEntity(productsDTO.getProductCategoryId(),productsDTO.getProductCategoryName());
            saveProduct.setProductCategory(productCategory);
            ProductBrand productBrand = commonService.getProductBrandsEntity(productsDTO.getProductBrandId(),productsDTO.getProductBrandName());
            saveProduct.setProductBrand(productBrand);
            saveProduct.setReleaseDate(productsDTO.getReleaseDate());
            saveProduct.setAvailableDate(productsDTO.getAvailableDate());
            saveProduct.setLastEditedBy(people.getFullName());
            saveProduct.setLastEditedWhen(Instant.now());
            saveProduct.setSellCount(0);
            Suppliers suppliers = commonService.getSupplierByPrincipal(principal);
            saveProduct.setSupplier(suppliers);
            ProductDocument productDocument = productDocumentRepository.getOne(productsDTO.getProductDocumentId());
            saveProduct.setProductDocument(productDocument);
            String prefixNumber = saveProduct.getName().replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
            Random random= new Random();
            int pnum = random.nextInt(10000);
            prefixNumber = prefixNumber + pnum;
            saveProduct.setProductNumber(prefixNumber);
            saveProduct = productsExtendRepository.save(saveProduct);
        }catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return productsMapper.toDto(saveProduct);
    }

    @Override
    public List<Long> getProductIdsBySupplier(Long supplierId) {
        return productsExtendFilterRepository.findIdsBySupplier(supplierId);
    }
}

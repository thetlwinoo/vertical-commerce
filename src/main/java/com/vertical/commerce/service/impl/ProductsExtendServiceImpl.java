package com.vertical.commerce.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vertical.commerce.domain.*;
import com.vertical.commerce.repository.*;
import com.vertical.commerce.security.SecurityUtils;
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
    private final ProductDocumentsRepository productDocumentsRepository;
    private final ProductsQueryService productsQueryService;
    private final ProductTagsRepository productTagsRepository;
    private final SuppliersRepository suppliersRepository;

    public ProductsExtendServiceImpl(ProductsExtendRepository productsExtendRepository, ProductsRepository productsRepository, ProductsExtendFilterRepository productsExtendFilterRepository, ProductCategoryMapper productCategoryMapper, CommonService commonService, ProductsMapper productsMapper, ProductDocumentsRepository productDocumentsRepository, ProductsQueryService productsQueryService, ProductTagsRepository productTagsRepository, SuppliersRepository suppliersRepository) {
        this.productsExtendRepository = productsExtendRepository;
        this.productsRepository = productsRepository;
        this.productsExtendFilterRepository = productsExtendFilterRepository;
        this.productCategoryMapper = productCategoryMapper;
        this.commonService = commonService;
        this.productsMapper = productsMapper;
        this.productDocumentsRepository = productDocumentsRepository;
        this.productsQueryService = productsQueryService;
        this.productTagsRepository = productTagsRepository;
        this.suppliersRepository = suppliersRepository;
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
//        People people = commonService.getPeopleByPrincipal(principal);
        String userLogin = SecurityUtils.getCurrentUserLogin().get();

        try{
            saveProduct.setId(productsDTO.getId());
            saveProduct.setName(productsDTO.getName());
            saveProduct.setHandle(productsDTO.getHandle());
            saveProduct.setSearchDetails(productsDTO.getSearchDetails());
            saveProduct.setActiveFlag(productsDTO.isActiveFlag());
            ProductCategory productCategory = commonService.getProductCategoryEntity(productsDTO.getProductCategoryId(),productsDTO.getProductCategoryName());
            saveProduct.setProductCategory(productCategory);
            ProductBrand productBrand = commonService.getProductBrandsEntity(productsDTO.getProductBrandId(),productsDTO.getProductBrandName());
            saveProduct.setProductBrand(productBrand);
            saveProduct.setReleaseDate(productsDTO.getReleaseDate());
            saveProduct.setAvailableDate(productsDTO.getAvailableDate());
            saveProduct.setAvailableDate(productsDTO.getAvailableDate());
            saveProduct.setLastEditedBy(userLogin);
            saveProduct.setLastEditedWhen(Instant.now());
            saveProduct.setSellCount(0);
            saveProduct.setPreferredInd(productsDTO.isPreferredInd());
            saveProduct.setMadeInMyanmarInd(productsDTO.isMadeInMyanmarInd());
            saveProduct.setFreeShippingInd(productsDTO.isFreeShippingInd());
            Suppliers suppliers = suppliersRepository.getOne(productsDTO.getSupplierId());
            saveProduct.setSupplier(suppliers);
            saveProduct.setQuestionsAboutProductInd(true);
            String prefixNumber = saveProduct.getName().replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
            prefixNumber = prefixNumber.length() > 7 ? prefixNumber.substring(0,7): prefixNumber;
            Random random= new Random();
            int pnum = random.nextInt(10000);
            prefixNumber = prefixNumber + pnum;
            saveProduct.setProductNumber(prefixNumber);
            saveProduct.validFrom(productsDTO.getValidFrom());
            saveProduct.validTo(productsDTO.getValidTo());
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

    @Override
    public String getProductDetails(Long productId) {
        return productsExtendFilterRepository.getProductDetails(productId);
    }

    @Override
    public String getProductDetailsShort(Long productId) {
        return productsExtendFilterRepository.getProductDetailsShort(productId);
    }

    @Override
    public String getFilterProducts(Long categoryId,Long brandId,Long supplierId,String brandIdList,String tag,String attributes,String options,String priceRange,Integer rating,Integer page,Integer limit) {
        try{
//            String.join(",", attributes)

            String result =  productsExtendFilterRepository.filterProducts(categoryId,brandId,supplierId,"{" + brandIdList + "}",tag, "{" + attributes + "}","{" + options + "}","{" + priceRange + "}",rating,page,limit);
            return result;
        }
        catch(Exception ex){
            throw ex;
        }

    }

    @Override
    public String getFilterControllers(Long categoryId,Long brandId,Long supplierId,String tag) {
        try{
            String result =  productsExtendFilterRepository.getFilterControllers(categoryId,brandId,supplierId,tag);
            return result;
        }
        catch(Exception ex){
            throw ex;
        }

    }

    @Override
    public List<Long> getProductsIdsByOrder(Long orderId) {
        try{
            List<Long> result =  productsExtendFilterRepository.getProductsIdsByOrder(orderId);
            return result;
        }
        catch(Exception ex){
            throw ex;
        }

    }

    @Override
    public void productDetailsUpdate(Long id) throws JsonProcessingException {
        Products products = productsRepository.getOne(id);
        String productDetails = getProductDetailsShort(id);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(productDetails);
        JsonNode jsonNode1 = actualObj.get("ratings").get("overallRating");
        JsonNode jsonNode2 = actualObj.get("totalWishlists");

        if(!(jsonNode1==null)){
            Integer overallRating = jsonNode1.intValue();
            products.setOverallRating(overallRating);
        }

        if(!(jsonNode2 == null)){
            Integer totalWishlist = jsonNode2.intValue();
            products.setTotalWishlist(totalWishlist);
        }

        products.setProductDetails(productDetails);
        productsRepository.save(products);
    }

    @Override
    public void productDetailsBatchUpdate() throws JsonProcessingException {
        for(Products products:productsRepository.findAll()){
            this.productDetailsUpdate(products.getId());
        }
    }

    @Override
    public void updateProductDetailsByOrder(Long orderId) throws JsonProcessingException {
        List<Long> productIds = getProductsIdsByOrder(orderId);
        for(Long productId: productIds){
            String productDetails = getProductDetailsShort(productId);
            Products products = productsRepository.getOne(productId);
            products.setProductDetails(productDetails);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode actualObj = mapper.readTree(productDetails);
            JsonNode jsonNode1 = actualObj.get("ratings").get("overallRating");
            JsonNode jsonNode2 = actualObj.get("totalWishlists");

            Integer overallRating = jsonNode1.intValue();
            Integer totalWishlist = jsonNode2.intValue();

            products.setProductDetails(productDetails);
            products.setOverallRating(overallRating);
            products.setTotalWishlist(totalWishlist);

            productsRepository.save(products);
        }
    }

    @Override
    public String getProductsHome() {
        try{
            String result =  productsExtendFilterRepository.getProductsHome();
            return result;
        }
        catch(Exception ex){
            throw ex;
        }

    }

    @Override
    public List<String> getTags(String filter) {
        try{
            List<String> result =  productsExtendFilterRepository.getTags(filter);
            return result;
        }
        catch(Exception ex){
            throw ex;
        }

    }
}

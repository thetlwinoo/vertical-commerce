package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.Collection;
import java.util.List;

public interface ProductsExtendFilterRepository extends JpaRepository<ProductCategory, Long> {
    @Query(value = "SELECT DISTINCT trim(tags) FROM products p WHERE p.search_details <> '' AND p.search_details LIKE %:keyword%", nativeQuery = true)
    List<String> getProductTags(@Param("keyword") String keyword);

    @Query(value = "SELECT DISTINCT A.* FROM product_category A LEFT JOIN products B ON A.id = B.product_category_id LEFT JOIN product_tags C ON B.id = C.product_id WHERE (:keyword = '' OR C.name ILIKE %:keyword%) AND (:category = 0 OR B.product_category_id = :category)", nativeQuery = true)
    List<ProductCategory> selectCategoriesByTags(@Param("keyword") String keyword, @Param("category") Long category);

    @Query(value = "SELECT DISTINCT C.Value FROM products A INNER JOIN stock_items B ON A.id = B.product_id INNER JOIN product_attribute C ON B.product_attribute_id = C.id WHERE C.value is not null AND C.value <> '' AND (:keyword = '' OR A.search_details ILIKE %:keyword%) AND (:category = 0 OR A.product_category_id = :category)", nativeQuery = true)
    List<String> selectColorsByTags(@Param("keyword") String keyword, @Param("category") Long category);

    @Query(value = "SELECT MIN(B.default_unit_price) as minprice,MAX(B.default_unit_price) as maxprice FROM product_category A LEFT JOIN products B ON A.id = B.product_category_id LEFT JOIN product_tags C ON B.id = C.product_id WHERE (:keyword = '' OR C.name ILIKE %:keyword%) AND (:category = 0 OR B.product_category_id = :category)", nativeQuery = true)
    Object selectPriceRangeByTags(@Param("keyword") String keyword, @Param("category") Long category);

    @Query(value = "SELECT DISTINCT B.brand FROM product_category A LEFT JOIN products B ON A.id = B.product_category_id LEFT JOIN product_tags C ON B.id = C.product_id WHERE B.brand IS NOT NULL AND B.brand <> '' AND (:keyword = '' OR C.name ILIKE %:keyword%) AND (:category = 0 OR B.product_category_id = :category)", nativeQuery = true)
    List<String> selectBrandsByTags(@Param("keyword") String keyword, @Param("category") Long category);

    @Query(value = "select id from product_category where product_category_id = :categoryId", nativeQuery = true)
    List<Long> getCategoryIds(@Param("categoryId") Long categoryId);

    @Query(value = "SELECT id FROM products p WHERE p.supplier_id = :supplierId", nativeQuery = true)
    List<Long> findIdsBySupplier(@Param("supplierId") Long supplierId);

    @Query(value = "SELECT A.product_id FROM stock_items A INNER JOIN product_attribute B ON A.product_attribute_id = B.id WHERE B.value in (:colors) ", nativeQuery = true)
    List<Long> getIdsByColors(@Param("colors") String[] colors);

    @Query(value = "SELECT * FROM get_product_details(:productId)", nativeQuery = true)
    String getProductDetails(@Param("productId") Long productId);

    @Query(value = "SELECT * FROM filter_products(:categoryId,:brandIdList,:tag,:attributes,:options,:priceRange,:rating,:page,:limit)",nativeQuery = true)
    String filterProducts(@Param("categoryId") Long categoryId, @Param("brandIdList") String brandIdList, @Param("tag") String tag, @Param("attributes") String attributes, @Param("options") String options, @Param("priceRange") String priceRange, @Param("rating") Integer rating, @Param("page") Integer page, @Param("limit") Integer limit);

    @Query(value = "SELECT * FROM get_filter_controllers(:categoryId,:tag)",nativeQuery = true)
    String getFilterControllers(@Param("categoryId") Long categoryId, @Param("tag") String tag);
}

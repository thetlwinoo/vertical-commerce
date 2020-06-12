package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.QuestionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Questions} and its DTO {@link QuestionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {SuppliersMapper.class, PeopleMapper.class, ProductsMapper.class})
public interface QuestionsMapper extends EntityMapper<QuestionsDTO, Questions> {

    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.name", target = "supplierName")
    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "person.fullName", target = "personFullName")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    QuestionsDTO toDto(Questions questions);

    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "personId", target = "person")
    @Mapping(source = "productId", target = "product")
    Questions toEntity(QuestionsDTO questionsDTO);

    default Questions fromId(Long id) {
        if (id == null) {
            return null;
        }
        Questions questions = new Questions();
        questions.setId(id);
        return questions;
    }
}

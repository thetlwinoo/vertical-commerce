package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Questions} entity.
 */
public class QuestionsDTO implements Serializable {
    
    private Long id;

    
    @Lob
    private String customerQuestion;

    @NotNull
    private Instant customerQuestionOn;

    @Lob
    private String supplierAnswer;

    private Instant supplierAnswerOn;

    private Boolean activeInd;


    private Long supplierId;

    private String supplierName;

    private Long personId;

    private String personFullName;

    private Long productId;

    private String productName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerQuestion() {
        return customerQuestion;
    }

    public void setCustomerQuestion(String customerQuestion) {
        this.customerQuestion = customerQuestion;
    }

    public Instant getCustomerQuestionOn() {
        return customerQuestionOn;
    }

    public void setCustomerQuestionOn(Instant customerQuestionOn) {
        this.customerQuestionOn = customerQuestionOn;
    }

    public String getSupplierAnswer() {
        return supplierAnswer;
    }

    public void setSupplierAnswer(String supplierAnswer) {
        this.supplierAnswer = supplierAnswer;
    }

    public Instant getSupplierAnswerOn() {
        return supplierAnswerOn;
    }

    public void setSupplierAnswerOn(Instant supplierAnswerOn) {
        this.supplierAnswerOn = supplierAnswerOn;
    }

    public Boolean isActiveInd() {
        return activeInd;
    }

    public void setActiveInd(Boolean activeInd) {
        this.activeInd = activeInd;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long suppliersId) {
        this.supplierId = suppliersId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String suppliersName) {
        this.supplierName = suppliersName;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long peopleId) {
        this.personId = peopleId;
    }

    public String getPersonFullName() {
        return personFullName;
    }

    public void setPersonFullName(String peopleFullName) {
        this.personFullName = peopleFullName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productsId) {
        this.productId = productsId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productsName) {
        this.productName = productsName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionsDTO)) {
            return false;
        }

        return id != null && id.equals(((QuestionsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionsDTO{" +
            "id=" + getId() +
            ", customerQuestion='" + getCustomerQuestion() + "'" +
            ", customerQuestionOn='" + getCustomerQuestionOn() + "'" +
            ", supplierAnswer='" + getSupplierAnswer() + "'" +
            ", supplierAnswerOn='" + getSupplierAnswerOn() + "'" +
            ", activeInd='" + isActiveInd() + "'" +
            ", supplierId=" + getSupplierId() +
            ", supplierName='" + getSupplierName() + "'" +
            ", personId=" + getPersonId() +
            ", personFullName='" + getPersonFullName() + "'" +
            ", productId=" + getProductId() +
            ", productName='" + getProductName() + "'" +
            "}";
    }
}

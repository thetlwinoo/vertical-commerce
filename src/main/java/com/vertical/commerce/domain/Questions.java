package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Questions.
 */
@Entity
@Table(name = "questions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Questions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "customer_question", nullable = false)
    private String customerQuestion;

    @NotNull
    @Column(name = "customer_question_on", nullable = false)
    private Instant customerQuestionOn;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "supplier_answer")
    private String supplierAnswer;

    @Column(name = "supplier_answer_on")
    private Instant supplierAnswerOn;

    @Column(name = "active_ind")
    private Boolean activeInd;

    @ManyToOne
    @JsonIgnoreProperties(value = "questions", allowSetters = true)
    private Suppliers supplier;

    @ManyToOne
    @JsonIgnoreProperties(value = "questions", allowSetters = true)
    private People person;

    @ManyToOne
    @JsonIgnoreProperties(value = "questions", allowSetters = true)
    private Products product;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerQuestion() {
        return customerQuestion;
    }

    public Questions customerQuestion(String customerQuestion) {
        this.customerQuestion = customerQuestion;
        return this;
    }

    public void setCustomerQuestion(String customerQuestion) {
        this.customerQuestion = customerQuestion;
    }

    public Instant getCustomerQuestionOn() {
        return customerQuestionOn;
    }

    public Questions customerQuestionOn(Instant customerQuestionOn) {
        this.customerQuestionOn = customerQuestionOn;
        return this;
    }

    public void setCustomerQuestionOn(Instant customerQuestionOn) {
        this.customerQuestionOn = customerQuestionOn;
    }

    public String getSupplierAnswer() {
        return supplierAnswer;
    }

    public Questions supplierAnswer(String supplierAnswer) {
        this.supplierAnswer = supplierAnswer;
        return this;
    }

    public void setSupplierAnswer(String supplierAnswer) {
        this.supplierAnswer = supplierAnswer;
    }

    public Instant getSupplierAnswerOn() {
        return supplierAnswerOn;
    }

    public Questions supplierAnswerOn(Instant supplierAnswerOn) {
        this.supplierAnswerOn = supplierAnswerOn;
        return this;
    }

    public void setSupplierAnswerOn(Instant supplierAnswerOn) {
        this.supplierAnswerOn = supplierAnswerOn;
    }

    public Boolean isActiveInd() {
        return activeInd;
    }

    public Questions activeInd(Boolean activeInd) {
        this.activeInd = activeInd;
        return this;
    }

    public void setActiveInd(Boolean activeInd) {
        this.activeInd = activeInd;
    }

    public Suppliers getSupplier() {
        return supplier;
    }

    public Questions supplier(Suppliers suppliers) {
        this.supplier = suppliers;
        return this;
    }

    public void setSupplier(Suppliers suppliers) {
        this.supplier = suppliers;
    }

    public People getPerson() {
        return person;
    }

    public Questions person(People people) {
        this.person = people;
        return this;
    }

    public void setPerson(People people) {
        this.person = people;
    }

    public Products getProduct() {
        return product;
    }

    public Questions product(Products products) {
        this.product = products;
        return this;
    }

    public void setProduct(Products products) {
        this.product = products;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Questions)) {
            return false;
        }
        return id != null && id.equals(((Questions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Questions{" +
            "id=" + getId() +
            ", customerQuestion='" + getCustomerQuestion() + "'" +
            ", customerQuestionOn='" + getCustomerQuestionOn() + "'" +
            ", supplierAnswer='" + getSupplierAnswer() + "'" +
            ", supplierAnswerOn='" + getSupplierAnswerOn() + "'" +
            ", activeInd='" + isActiveInd() + "'" +
            "}";
    }
}

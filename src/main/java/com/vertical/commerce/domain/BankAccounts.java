package com.vertical.commerce.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A BankAccounts.
 */
@Entity
@Table(name = "bank_accounts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BankAccounts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "branch")
    private String branch;

    @Column(name = "code")
    private String code;

    @Column(name = "number")
    private String number;

    @Column(name = "type")
    private String type;

    @Column(name = "bank")
    private String bank;

    @Column(name = "international_code")
    private String internationalCode;

    @NotNull
    @Column(name = "last_edited_by", nullable = false)
    private String lastEditedBy;

    @Column(name = "logo_photo")
    private String logoPhoto;

    @NotNull
    @Column(name = "valid_form", nullable = false)
    private Instant validForm;

    @Column(name = "valid_to")
    private Instant validTo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BankAccounts name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public BankAccounts branch(String branch) {
        this.branch = branch;
        return this;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCode() {
        return code;
    }

    public BankAccounts code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNumber() {
        return number;
    }

    public BankAccounts number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public BankAccounts type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBank() {
        return bank;
    }

    public BankAccounts bank(String bank) {
        this.bank = bank;
        return this;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getInternationalCode() {
        return internationalCode;
    }

    public BankAccounts internationalCode(String internationalCode) {
        this.internationalCode = internationalCode;
        return this;
    }

    public void setInternationalCode(String internationalCode) {
        this.internationalCode = internationalCode;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public BankAccounts lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public String getLogoPhoto() {
        return logoPhoto;
    }

    public BankAccounts logoPhoto(String logoPhoto) {
        this.logoPhoto = logoPhoto;
        return this;
    }

    public void setLogoPhoto(String logoPhoto) {
        this.logoPhoto = logoPhoto;
    }

    public Instant getValidForm() {
        return validForm;
    }

    public BankAccounts validForm(Instant validForm) {
        this.validForm = validForm;
        return this;
    }

    public void setValidForm(Instant validForm) {
        this.validForm = validForm;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public BankAccounts validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankAccounts)) {
            return false;
        }
        return id != null && id.equals(((BankAccounts) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankAccounts{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", branch='" + getBranch() + "'" +
            ", code='" + getCode() + "'" +
            ", number='" + getNumber() + "'" +
            ", type='" + getType() + "'" +
            ", bank='" + getBank() + "'" +
            ", internationalCode='" + getInternationalCode() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", logoPhoto='" + getLogoPhoto() + "'" +
            ", validForm='" + getValidForm() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}

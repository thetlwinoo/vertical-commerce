package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.BankAccounts} entity.
 */
public class BankAccountsDTO implements Serializable {
    
    private Long id;

    private String name;

    private String branch;

    private String code;

    private String number;

    private String type;

    private String bank;

    private String internationalCode;

    @NotNull
    private String lastEditedBy;

    @NotNull
    private Instant validForm;

    @NotNull
    private Instant validTo;


    private Long logoId;

    private String logoThumbnailUrl;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getInternationalCode() {
        return internationalCode;
    }

    public void setInternationalCode(String internationalCode) {
        this.internationalCode = internationalCode;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getValidForm() {
        return validForm;
    }

    public void setValidForm(Instant validForm) {
        this.validForm = validForm;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Long getLogoId() {
        return logoId;
    }

    public void setLogoId(Long photosId) {
        this.logoId = photosId;
    }

    public String getLogoThumbnailUrl() {
        return logoThumbnailUrl;
    }

    public void setLogoThumbnailUrl(String photosThumbnailUrl) {
        this.logoThumbnailUrl = photosThumbnailUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankAccountsDTO)) {
            return false;
        }

        return id != null && id.equals(((BankAccountsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankAccountsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", branch='" + getBranch() + "'" +
            ", code='" + getCode() + "'" +
            ", number='" + getNumber() + "'" +
            ", type='" + getType() + "'" +
            ", bank='" + getBank() + "'" +
            ", internationalCode='" + getInternationalCode() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", validForm='" + getValidForm() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", logoId=" + getLogoId() +
            ", logoThumbnailUrl='" + getLogoThumbnailUrl() + "'" +
            "}";
    }
}

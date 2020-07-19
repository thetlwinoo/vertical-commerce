package com.vertical.commerce.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.vertical.commerce.domain.BankAccounts} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.BankAccountsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bank-accounts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BankAccountsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter branch;

    private StringFilter code;

    private StringFilter number;

    private StringFilter type;

    private StringFilter bank;

    private StringFilter internationalCode;

    private StringFilter lastEditedBy;

    private StringFilter logoPhoto;

    private InstantFilter validForm;

    private InstantFilter validTo;

    public BankAccountsCriteria() {
    }

    public BankAccountsCriteria(BankAccountsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.branch = other.branch == null ? null : other.branch.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.number = other.number == null ? null : other.number.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.bank = other.bank == null ? null : other.bank.copy();
        this.internationalCode = other.internationalCode == null ? null : other.internationalCode.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.logoPhoto = other.logoPhoto == null ? null : other.logoPhoto.copy();
        this.validForm = other.validForm == null ? null : other.validForm.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
    }

    @Override
    public BankAccountsCriteria copy() {
        return new BankAccountsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getBranch() {
        return branch;
    }

    public void setBranch(StringFilter branch) {
        this.branch = branch;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getNumber() {
        return number;
    }

    public void setNumber(StringFilter number) {
        this.number = number;
    }

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public StringFilter getBank() {
        return bank;
    }

    public void setBank(StringFilter bank) {
        this.bank = bank;
    }

    public StringFilter getInternationalCode() {
        return internationalCode;
    }

    public void setInternationalCode(StringFilter internationalCode) {
        this.internationalCode = internationalCode;
    }

    public StringFilter getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(StringFilter lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public StringFilter getLogoPhoto() {
        return logoPhoto;
    }

    public void setLogoPhoto(StringFilter logoPhoto) {
        this.logoPhoto = logoPhoto;
    }

    public InstantFilter getValidForm() {
        return validForm;
    }

    public void setValidForm(InstantFilter validForm) {
        this.validForm = validForm;
    }

    public InstantFilter getValidTo() {
        return validTo;
    }

    public void setValidTo(InstantFilter validTo) {
        this.validTo = validTo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BankAccountsCriteria that = (BankAccountsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(branch, that.branch) &&
            Objects.equals(code, that.code) &&
            Objects.equals(number, that.number) &&
            Objects.equals(type, that.type) &&
            Objects.equals(bank, that.bank) &&
            Objects.equals(internationalCode, that.internationalCode) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(logoPhoto, that.logoPhoto) &&
            Objects.equals(validForm, that.validForm) &&
            Objects.equals(validTo, that.validTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        branch,
        code,
        number,
        type,
        bank,
        internationalCode,
        lastEditedBy,
        logoPhoto,
        validForm,
        validTo
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankAccountsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (branch != null ? "branch=" + branch + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (number != null ? "number=" + number + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (bank != null ? "bank=" + bank + ", " : "") +
                (internationalCode != null ? "internationalCode=" + internationalCode + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (logoPhoto != null ? "logoPhoto=" + logoPhoto + ", " : "") +
                (validForm != null ? "validForm=" + validForm + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
            "}";
    }

}

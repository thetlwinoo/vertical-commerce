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
 * Criteria class for the {@link com.vertical.commerce.domain.DeliveryMethods} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.DeliveryMethodsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /delivery-methods?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DeliveryMethodsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter thirdPartyName;

    private IntegerFilter expectedMinArrivalDays;

    private IntegerFilter expectedMaxArrivalDays;

    private BooleanFilter activeInd;

    private BooleanFilter defaultInd;

    private StringFilter deliveryNote;

    private InstantFilter validFrom;

    private InstantFilter validTo;

    private LongFilter supplierId;

    public DeliveryMethodsCriteria() {
    }

    public DeliveryMethodsCriteria(DeliveryMethodsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.thirdPartyName = other.thirdPartyName == null ? null : other.thirdPartyName.copy();
        this.expectedMinArrivalDays = other.expectedMinArrivalDays == null ? null : other.expectedMinArrivalDays.copy();
        this.expectedMaxArrivalDays = other.expectedMaxArrivalDays == null ? null : other.expectedMaxArrivalDays.copy();
        this.activeInd = other.activeInd == null ? null : other.activeInd.copy();
        this.defaultInd = other.defaultInd == null ? null : other.defaultInd.copy();
        this.deliveryNote = other.deliveryNote == null ? null : other.deliveryNote.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
    }

    @Override
    public DeliveryMethodsCriteria copy() {
        return new DeliveryMethodsCriteria(this);
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

    public StringFilter getThirdPartyName() {
        return thirdPartyName;
    }

    public void setThirdPartyName(StringFilter thirdPartyName) {
        this.thirdPartyName = thirdPartyName;
    }

    public IntegerFilter getExpectedMinArrivalDays() {
        return expectedMinArrivalDays;
    }

    public void setExpectedMinArrivalDays(IntegerFilter expectedMinArrivalDays) {
        this.expectedMinArrivalDays = expectedMinArrivalDays;
    }

    public IntegerFilter getExpectedMaxArrivalDays() {
        return expectedMaxArrivalDays;
    }

    public void setExpectedMaxArrivalDays(IntegerFilter expectedMaxArrivalDays) {
        this.expectedMaxArrivalDays = expectedMaxArrivalDays;
    }

    public BooleanFilter getActiveInd() {
        return activeInd;
    }

    public void setActiveInd(BooleanFilter activeInd) {
        this.activeInd = activeInd;
    }

    public BooleanFilter getDefaultInd() {
        return defaultInd;
    }

    public void setDefaultInd(BooleanFilter defaultInd) {
        this.defaultInd = defaultInd;
    }

    public StringFilter getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(StringFilter deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    public InstantFilter getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(InstantFilter validFrom) {
        this.validFrom = validFrom;
    }

    public InstantFilter getValidTo() {
        return validTo;
    }

    public void setValidTo(InstantFilter validTo) {
        this.validTo = validTo;
    }

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DeliveryMethodsCriteria that = (DeliveryMethodsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(thirdPartyName, that.thirdPartyName) &&
            Objects.equals(expectedMinArrivalDays, that.expectedMinArrivalDays) &&
            Objects.equals(expectedMaxArrivalDays, that.expectedMaxArrivalDays) &&
            Objects.equals(activeInd, that.activeInd) &&
            Objects.equals(defaultInd, that.defaultInd) &&
            Objects.equals(deliveryNote, that.deliveryNote) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo) &&
            Objects.equals(supplierId, that.supplierId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        thirdPartyName,
        expectedMinArrivalDays,
        expectedMaxArrivalDays,
        activeInd,
        defaultInd,
        deliveryNote,
        validFrom,
        validTo,
        supplierId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeliveryMethodsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (thirdPartyName != null ? "thirdPartyName=" + thirdPartyName + ", " : "") +
                (expectedMinArrivalDays != null ? "expectedMinArrivalDays=" + expectedMinArrivalDays + ", " : "") +
                (expectedMaxArrivalDays != null ? "expectedMaxArrivalDays=" + expectedMaxArrivalDays + ", " : "") +
                (activeInd != null ? "activeInd=" + activeInd + ", " : "") +
                (defaultInd != null ? "defaultInd=" + defaultInd + ", " : "") +
                (deliveryNote != null ? "deliveryNote=" + deliveryNote + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
            "}";
    }

}

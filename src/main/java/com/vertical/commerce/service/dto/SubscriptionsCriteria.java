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
 * Criteria class for the {@link com.vertical.commerce.domain.Subscriptions} entity. This class is used
 * in {@link com.vertical.commerce.web.rest.SubscriptionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /subscriptions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SubscriptionsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter emailAddress;

    private InstantFilter subscribedOn;

    private BooleanFilter activeFlag;

    private InstantFilter validFrom;

    private InstantFilter validTo;

    public SubscriptionsCriteria() {
    }

    public SubscriptionsCriteria(SubscriptionsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.emailAddress = other.emailAddress == null ? null : other.emailAddress.copy();
        this.subscribedOn = other.subscribedOn == null ? null : other.subscribedOn.copy();
        this.activeFlag = other.activeFlag == null ? null : other.activeFlag.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
    }

    @Override
    public SubscriptionsCriteria copy() {
        return new SubscriptionsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(StringFilter emailAddress) {
        this.emailAddress = emailAddress;
    }

    public InstantFilter getSubscribedOn() {
        return subscribedOn;
    }

    public void setSubscribedOn(InstantFilter subscribedOn) {
        this.subscribedOn = subscribedOn;
    }

    public BooleanFilter getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(BooleanFilter activeFlag) {
        this.activeFlag = activeFlag;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SubscriptionsCriteria that = (SubscriptionsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(emailAddress, that.emailAddress) &&
            Objects.equals(subscribedOn, that.subscribedOn) &&
            Objects.equals(activeFlag, that.activeFlag) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        emailAddress,
        subscribedOn,
        activeFlag,
        validFrom,
        validTo
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubscriptionsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (emailAddress != null ? "emailAddress=" + emailAddress + ", " : "") +
                (subscribedOn != null ? "subscribedOn=" + subscribedOn + ", " : "") +
                (activeFlag != null ? "activeFlag=" + activeFlag + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
            "}";
    }

}

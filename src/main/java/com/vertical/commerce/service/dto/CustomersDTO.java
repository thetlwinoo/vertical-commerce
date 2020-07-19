package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Customers} entity.
 */
public class CustomersDTO implements Serializable {
    
    private Long id;

    private String name;

    @NotNull
    private String accountNumber;

    @NotNull
    private Instant accountOpenedDate;

    @NotNull
    private BigDecimal standardDiscountPercentage;

    @NotNull
    private Boolean isStatementSent;

    @NotNull
    private Boolean isOnCreditHold;

    @NotNull
    private Integer paymentDays;

    private String deliveryRun;

    private String runPosition;

    private String profilePhoto;

    private Boolean billToAddressSameAsDeliveryAddress;

    @NotNull
    private String lastEditedBy;

    @NotNull
    private Boolean activeFlag;

    @NotNull
    private Instant validFrom;

    private Instant validTo;


    private Long peopleId;

    private String peopleFullName;

    private Long deliveryMethodId;

    private String deliveryMethodName;

    private Long deliveryAddressId;

    private Long billToAddressId;
    
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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Instant getAccountOpenedDate() {
        return accountOpenedDate;
    }

    public void setAccountOpenedDate(Instant accountOpenedDate) {
        this.accountOpenedDate = accountOpenedDate;
    }

    public BigDecimal getStandardDiscountPercentage() {
        return standardDiscountPercentage;
    }

    public void setStandardDiscountPercentage(BigDecimal standardDiscountPercentage) {
        this.standardDiscountPercentage = standardDiscountPercentage;
    }

    public Boolean isIsStatementSent() {
        return isStatementSent;
    }

    public void setIsStatementSent(Boolean isStatementSent) {
        this.isStatementSent = isStatementSent;
    }

    public Boolean isIsOnCreditHold() {
        return isOnCreditHold;
    }

    public void setIsOnCreditHold(Boolean isOnCreditHold) {
        this.isOnCreditHold = isOnCreditHold;
    }

    public Integer getPaymentDays() {
        return paymentDays;
    }

    public void setPaymentDays(Integer paymentDays) {
        this.paymentDays = paymentDays;
    }

    public String getDeliveryRun() {
        return deliveryRun;
    }

    public void setDeliveryRun(String deliveryRun) {
        this.deliveryRun = deliveryRun;
    }

    public String getRunPosition() {
        return runPosition;
    }

    public void setRunPosition(String runPosition) {
        this.runPosition = runPosition;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public Boolean isBillToAddressSameAsDeliveryAddress() {
        return billToAddressSameAsDeliveryAddress;
    }

    public void setBillToAddressSameAsDeliveryAddress(Boolean billToAddressSameAsDeliveryAddress) {
        this.billToAddressSameAsDeliveryAddress = billToAddressSameAsDeliveryAddress;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Long getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(Long peopleId) {
        this.peopleId = peopleId;
    }

    public String getPeopleFullName() {
        return peopleFullName;
    }

    public void setPeopleFullName(String peopleFullName) {
        this.peopleFullName = peopleFullName;
    }

    public Long getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(Long deliveryMethodsId) {
        this.deliveryMethodId = deliveryMethodsId;
    }

    public String getDeliveryMethodName() {
        return deliveryMethodName;
    }

    public void setDeliveryMethodName(String deliveryMethodsName) {
        this.deliveryMethodName = deliveryMethodsName;
    }

    public Long getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryAddressId(Long addressesId) {
        this.deliveryAddressId = addressesId;
    }

    public Long getBillToAddressId() {
        return billToAddressId;
    }

    public void setBillToAddressId(Long addressesId) {
        this.billToAddressId = addressesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomersDTO)) {
            return false;
        }

        return id != null && id.equals(((CustomersDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomersDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", accountOpenedDate='" + getAccountOpenedDate() + "'" +
            ", standardDiscountPercentage=" + getStandardDiscountPercentage() +
            ", isStatementSent='" + isIsStatementSent() + "'" +
            ", isOnCreditHold='" + isIsOnCreditHold() + "'" +
            ", paymentDays=" + getPaymentDays() +
            ", deliveryRun='" + getDeliveryRun() + "'" +
            ", runPosition='" + getRunPosition() + "'" +
            ", profilePhoto='" + getProfilePhoto() + "'" +
            ", billToAddressSameAsDeliveryAddress='" + isBillToAddressSameAsDeliveryAddress() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", activeFlag='" + isActiveFlag() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", peopleId=" + getPeopleId() +
            ", peopleFullName='" + getPeopleFullName() + "'" +
            ", deliveryMethodId=" + getDeliveryMethodId() +
            ", deliveryMethodName='" + getDeliveryMethodName() + "'" +
            ", deliveryAddressId=" + getDeliveryAddressId() +
            ", billToAddressId=" + getBillToAddressId() +
            "}";
    }
}

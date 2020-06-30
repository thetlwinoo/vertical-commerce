package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;
import com.vertical.commerce.domain.enumeration.InvoiceStatus;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Invoices} entity.
 */
public class InvoicesDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Instant invoiceDate;

    private String customerPurchaseOrderNumber;

    @NotNull
    private Boolean isCreditNote;

    private String creditNoteReason;

    private String comments;

    private String deliveryInstructions;

    private String internalComments;

    @NotNull
    private Integer totalDryItems;

    @NotNull
    private Integer totalChillerItems;

    private String deliveryRun;

    private String runPosition;

    @Lob
    private String returnedDeliveryData;

    private Instant confirmedDeliveryTime;

    private String confirmedReceivedBy;

    @NotNull
    private InvoiceStatus status;

    @NotNull
    private String lastEditedBy;

    @NotNull
    private Instant lastEditedWhen;


    private Long contactPersonId;

    private String contactPersonFullName;

    private Long salesPersonId;

    private String salesPersonFullName;

    private Long packedByPersonId;

    private String packedByPersonFullName;

    private Long accountsPersonId;

    private String accountsPersonFullName;

    private Long customerId;

    private String customerName;

    private Long billToCustomerId;

    private String billToCustomerName;

    private Long deliveryMethodId;

    private String deliveryMethodName;

    private Long orderId;

    private Long orderPackageId;

    private Long paymentMethodId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Instant invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getCustomerPurchaseOrderNumber() {
        return customerPurchaseOrderNumber;
    }

    public void setCustomerPurchaseOrderNumber(String customerPurchaseOrderNumber) {
        this.customerPurchaseOrderNumber = customerPurchaseOrderNumber;
    }

    public Boolean isIsCreditNote() {
        return isCreditNote;
    }

    public void setIsCreditNote(Boolean isCreditNote) {
        this.isCreditNote = isCreditNote;
    }

    public String getCreditNoteReason() {
        return creditNoteReason;
    }

    public void setCreditNoteReason(String creditNoteReason) {
        this.creditNoteReason = creditNoteReason;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDeliveryInstructions() {
        return deliveryInstructions;
    }

    public void setDeliveryInstructions(String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }

    public String getInternalComments() {
        return internalComments;
    }

    public void setInternalComments(String internalComments) {
        this.internalComments = internalComments;
    }

    public Integer getTotalDryItems() {
        return totalDryItems;
    }

    public void setTotalDryItems(Integer totalDryItems) {
        this.totalDryItems = totalDryItems;
    }

    public Integer getTotalChillerItems() {
        return totalChillerItems;
    }

    public void setTotalChillerItems(Integer totalChillerItems) {
        this.totalChillerItems = totalChillerItems;
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

    public String getReturnedDeliveryData() {
        return returnedDeliveryData;
    }

    public void setReturnedDeliveryData(String returnedDeliveryData) {
        this.returnedDeliveryData = returnedDeliveryData;
    }

    public Instant getConfirmedDeliveryTime() {
        return confirmedDeliveryTime;
    }

    public void setConfirmedDeliveryTime(Instant confirmedDeliveryTime) {
        this.confirmedDeliveryTime = confirmedDeliveryTime;
    }

    public String getConfirmedReceivedBy() {
        return confirmedReceivedBy;
    }

    public void setConfirmedReceivedBy(String confirmedReceivedBy) {
        this.confirmedReceivedBy = confirmedReceivedBy;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Long getContactPersonId() {
        return contactPersonId;
    }

    public void setContactPersonId(Long peopleId) {
        this.contactPersonId = peopleId;
    }

    public String getContactPersonFullName() {
        return contactPersonFullName;
    }

    public void setContactPersonFullName(String peopleFullName) {
        this.contactPersonFullName = peopleFullName;
    }

    public Long getSalesPersonId() {
        return salesPersonId;
    }

    public void setSalesPersonId(Long peopleId) {
        this.salesPersonId = peopleId;
    }

    public String getSalesPersonFullName() {
        return salesPersonFullName;
    }

    public void setSalesPersonFullName(String peopleFullName) {
        this.salesPersonFullName = peopleFullName;
    }

    public Long getPackedByPersonId() {
        return packedByPersonId;
    }

    public void setPackedByPersonId(Long peopleId) {
        this.packedByPersonId = peopleId;
    }

    public String getPackedByPersonFullName() {
        return packedByPersonFullName;
    }

    public void setPackedByPersonFullName(String peopleFullName) {
        this.packedByPersonFullName = peopleFullName;
    }

    public Long getAccountsPersonId() {
        return accountsPersonId;
    }

    public void setAccountsPersonId(Long peopleId) {
        this.accountsPersonId = peopleId;
    }

    public String getAccountsPersonFullName() {
        return accountsPersonFullName;
    }

    public void setAccountsPersonFullName(String peopleFullName) {
        this.accountsPersonFullName = peopleFullName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customersId) {
        this.customerId = customersId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customersName) {
        this.customerName = customersName;
    }

    public Long getBillToCustomerId() {
        return billToCustomerId;
    }

    public void setBillToCustomerId(Long customersId) {
        this.billToCustomerId = customersId;
    }

    public String getBillToCustomerName() {
        return billToCustomerName;
    }

    public void setBillToCustomerName(String customersName) {
        this.billToCustomerName = customersName;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long ordersId) {
        this.orderId = ordersId;
    }

    public Long getOrderPackageId() {
        return orderPackageId;
    }

    public void setOrderPackageId(Long orderPackagesId) {
        this.orderPackageId = orderPackagesId;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodsId) {
        this.paymentMethodId = paymentMethodsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvoicesDTO)) {
            return false;
        }

        return id != null && id.equals(((InvoicesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoicesDTO{" +
            "id=" + getId() +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", customerPurchaseOrderNumber='" + getCustomerPurchaseOrderNumber() + "'" +
            ", isCreditNote='" + isIsCreditNote() + "'" +
            ", creditNoteReason='" + getCreditNoteReason() + "'" +
            ", comments='" + getComments() + "'" +
            ", deliveryInstructions='" + getDeliveryInstructions() + "'" +
            ", internalComments='" + getInternalComments() + "'" +
            ", totalDryItems=" + getTotalDryItems() +
            ", totalChillerItems=" + getTotalChillerItems() +
            ", deliveryRun='" + getDeliveryRun() + "'" +
            ", runPosition='" + getRunPosition() + "'" +
            ", returnedDeliveryData='" + getReturnedDeliveryData() + "'" +
            ", confirmedDeliveryTime='" + getConfirmedDeliveryTime() + "'" +
            ", confirmedReceivedBy='" + getConfirmedReceivedBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", contactPersonId=" + getContactPersonId() +
            ", contactPersonFullName='" + getContactPersonFullName() + "'" +
            ", salesPersonId=" + getSalesPersonId() +
            ", salesPersonFullName='" + getSalesPersonFullName() + "'" +
            ", packedByPersonId=" + getPackedByPersonId() +
            ", packedByPersonFullName='" + getPackedByPersonFullName() + "'" +
            ", accountsPersonId=" + getAccountsPersonId() +
            ", accountsPersonFullName='" + getAccountsPersonFullName() + "'" +
            ", customerId=" + getCustomerId() +
            ", customerName='" + getCustomerName() + "'" +
            ", billToCustomerId=" + getBillToCustomerId() +
            ", billToCustomerName='" + getBillToCustomerName() + "'" +
            ", deliveryMethodId=" + getDeliveryMethodId() +
            ", deliveryMethodName='" + getDeliveryMethodName() + "'" +
            ", orderId=" + getOrderId() +
            ", orderPackageId=" + getOrderPackageId() +
            ", paymentMethodId=" + getPaymentMethodId() +
            "}";
    }
}

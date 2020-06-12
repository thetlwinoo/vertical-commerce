package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Receipts.
 */
@Entity
@Table(name = "receipts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Receipts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "receipt_no", nullable = false)
    private String receiptNo;

    @NotNull
    @Column(name = "issued_date", nullable = false)
    private Instant issuedDate;

    @NotNull
    @Column(name = "print_count", nullable = false)
    private Integer printCount;

    @NotNull
    @Column(name = "last_edited_by", nullable = false)
    private String lastEditedBy;

    @NotNull
    @Column(name = "last_edited_when", nullable = false)
    private Instant lastEditedWhen;

    @ManyToOne
    @JsonIgnoreProperties(value = "receipts", allowSetters = true)
    private Orders order;

    @ManyToOne
    @JsonIgnoreProperties(value = "receipts", allowSetters = true)
    private Invoices invoice;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public Receipts receiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
        return this;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Instant getIssuedDate() {
        return issuedDate;
    }

    public Receipts issuedDate(Instant issuedDate) {
        this.issuedDate = issuedDate;
        return this;
    }

    public void setIssuedDate(Instant issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Integer getPrintCount() {
        return printCount;
    }

    public Receipts printCount(Integer printCount) {
        this.printCount = printCount;
        return this;
    }

    public void setPrintCount(Integer printCount) {
        this.printCount = printCount;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public Receipts lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public Receipts lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Orders getOrder() {
        return order;
    }

    public Receipts order(Orders orders) {
        this.order = orders;
        return this;
    }

    public void setOrder(Orders orders) {
        this.order = orders;
    }

    public Invoices getInvoice() {
        return invoice;
    }

    public Receipts invoice(Invoices invoices) {
        this.invoice = invoices;
        return this;
    }

    public void setInvoice(Invoices invoices) {
        this.invoice = invoices;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Receipts)) {
            return false;
        }
        return id != null && id.equals(((Receipts) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Receipts{" +
            "id=" + getId() +
            ", receiptNo='" + getReceiptNo() + "'" +
            ", issuedDate='" + getIssuedDate() + "'" +
            ", printCount=" + getPrintCount() +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}

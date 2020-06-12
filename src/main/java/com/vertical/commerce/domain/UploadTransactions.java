package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A UploadTransactions.
 */
@Entity
@Table(name = "upload_transactions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UploadTransactions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Lob
    @Column(name = "imported_template")
    private byte[] importedTemplate;

    @Column(name = "imported_template_content_type")
    private String importedTemplateContentType;

    @Lob
    @Column(name = "imported_failed_template")
    private byte[] importedFailedTemplate;

    @Column(name = "imported_failed_template_content_type")
    private String importedFailedTemplateContentType;

    @Column(name = "status")
    private Integer status;

    @Column(name = "generated_code")
    private String generatedCode;

    @NotNull
    @Column(name = "last_edited_by", nullable = false)
    private String lastEditedBy;

    @NotNull
    @Column(name = "last_edited_when", nullable = false)
    private Instant lastEditedWhen;

    @ManyToOne
    @JsonIgnoreProperties(value = "uploadTransactions", allowSetters = true)
    private Suppliers supplier;

    @ManyToOne
    @JsonIgnoreProperties(value = "uploadTransactions", allowSetters = true)
    private UploadActionTypes actionType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public UploadTransactions fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getImportedTemplate() {
        return importedTemplate;
    }

    public UploadTransactions importedTemplate(byte[] importedTemplate) {
        this.importedTemplate = importedTemplate;
        return this;
    }

    public void setImportedTemplate(byte[] importedTemplate) {
        this.importedTemplate = importedTemplate;
    }

    public String getImportedTemplateContentType() {
        return importedTemplateContentType;
    }

    public UploadTransactions importedTemplateContentType(String importedTemplateContentType) {
        this.importedTemplateContentType = importedTemplateContentType;
        return this;
    }

    public void setImportedTemplateContentType(String importedTemplateContentType) {
        this.importedTemplateContentType = importedTemplateContentType;
    }

    public byte[] getImportedFailedTemplate() {
        return importedFailedTemplate;
    }

    public UploadTransactions importedFailedTemplate(byte[] importedFailedTemplate) {
        this.importedFailedTemplate = importedFailedTemplate;
        return this;
    }

    public void setImportedFailedTemplate(byte[] importedFailedTemplate) {
        this.importedFailedTemplate = importedFailedTemplate;
    }

    public String getImportedFailedTemplateContentType() {
        return importedFailedTemplateContentType;
    }

    public UploadTransactions importedFailedTemplateContentType(String importedFailedTemplateContentType) {
        this.importedFailedTemplateContentType = importedFailedTemplateContentType;
        return this;
    }

    public void setImportedFailedTemplateContentType(String importedFailedTemplateContentType) {
        this.importedFailedTemplateContentType = importedFailedTemplateContentType;
    }

    public Integer getStatus() {
        return status;
    }

    public UploadTransactions status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGeneratedCode() {
        return generatedCode;
    }

    public UploadTransactions generatedCode(String generatedCode) {
        this.generatedCode = generatedCode;
        return this;
    }

    public void setGeneratedCode(String generatedCode) {
        this.generatedCode = generatedCode;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public UploadTransactions lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public UploadTransactions lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Suppliers getSupplier() {
        return supplier;
    }

    public UploadTransactions supplier(Suppliers suppliers) {
        this.supplier = suppliers;
        return this;
    }

    public void setSupplier(Suppliers suppliers) {
        this.supplier = suppliers;
    }

    public UploadActionTypes getActionType() {
        return actionType;
    }

    public UploadTransactions actionType(UploadActionTypes uploadActionTypes) {
        this.actionType = uploadActionTypes;
        return this;
    }

    public void setActionType(UploadActionTypes uploadActionTypes) {
        this.actionType = uploadActionTypes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UploadTransactions)) {
            return false;
        }
        return id != null && id.equals(((UploadTransactions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UploadTransactions{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", importedTemplate='" + getImportedTemplate() + "'" +
            ", importedTemplateContentType='" + getImportedTemplateContentType() + "'" +
            ", importedFailedTemplate='" + getImportedFailedTemplate() + "'" +
            ", importedFailedTemplateContentType='" + getImportedFailedTemplateContentType() + "'" +
            ", status=" + getStatus() +
            ", generatedCode='" + getGeneratedCode() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}

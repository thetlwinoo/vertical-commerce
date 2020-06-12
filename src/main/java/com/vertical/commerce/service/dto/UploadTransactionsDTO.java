package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vertical.commerce.domain.UploadTransactions} entity.
 */
public class UploadTransactionsDTO implements Serializable {
    
    private Long id;

    private String fileName;

    @Lob
    private byte[] importedTemplate;

    private String importedTemplateContentType;
    @Lob
    private byte[] importedFailedTemplate;

    private String importedFailedTemplateContentType;
    private Integer status;

    private String generatedCode;

    @NotNull
    private String lastEditedBy;

    @NotNull
    private Instant lastEditedWhen;


    private Long supplierId;

    private String supplierName;

    private Long actionTypeId;

    private String actionTypeName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getImportedTemplate() {
        return importedTemplate;
    }

    public void setImportedTemplate(byte[] importedTemplate) {
        this.importedTemplate = importedTemplate;
    }

    public String getImportedTemplateContentType() {
        return importedTemplateContentType;
    }

    public void setImportedTemplateContentType(String importedTemplateContentType) {
        this.importedTemplateContentType = importedTemplateContentType;
    }

    public byte[] getImportedFailedTemplate() {
        return importedFailedTemplate;
    }

    public void setImportedFailedTemplate(byte[] importedFailedTemplate) {
        this.importedFailedTemplate = importedFailedTemplate;
    }

    public String getImportedFailedTemplateContentType() {
        return importedFailedTemplateContentType;
    }

    public void setImportedFailedTemplateContentType(String importedFailedTemplateContentType) {
        this.importedFailedTemplateContentType = importedFailedTemplateContentType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGeneratedCode() {
        return generatedCode;
    }

    public void setGeneratedCode(String generatedCode) {
        this.generatedCode = generatedCode;
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

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long suppliersId) {
        this.supplierId = suppliersId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String suppliersName) {
        this.supplierName = suppliersName;
    }

    public Long getActionTypeId() {
        return actionTypeId;
    }

    public void setActionTypeId(Long uploadActionTypesId) {
        this.actionTypeId = uploadActionTypesId;
    }

    public String getActionTypeName() {
        return actionTypeName;
    }

    public void setActionTypeName(String uploadActionTypesName) {
        this.actionTypeName = uploadActionTypesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UploadTransactionsDTO)) {
            return false;
        }

        return id != null && id.equals(((UploadTransactionsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UploadTransactionsDTO{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", importedTemplate='" + getImportedTemplate() + "'" +
            ", importedFailedTemplate='" + getImportedFailedTemplate() + "'" +
            ", status=" + getStatus() +
            ", generatedCode='" + getGeneratedCode() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", supplierId=" + getSupplierId() +
            ", supplierName='" + getSupplierName() + "'" +
            ", actionTypeId=" + getActionTypeId() +
            ", actionTypeName='" + getActionTypeName() + "'" +
            "}";
    }
}

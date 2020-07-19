package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ProductDocumentsCulture.
 */
@Entity
@Table(name = "product_documents_culture")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductDocumentsCulture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties(value = "productDocumentsCultures", allowSetters = true)
    private Culture culture;

    @ManyToOne
    @JsonIgnoreProperties(value = "productDocumentsCultures", allowSetters = true)
    private ProductDocuments productDocument;

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

    public ProductDocumentsCulture name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Culture getCulture() {
        return culture;
    }

    public ProductDocumentsCulture culture(Culture culture) {
        this.culture = culture;
        return this;
    }

    public void setCulture(Culture culture) {
        this.culture = culture;
    }

    public ProductDocuments getProductDocument() {
        return productDocument;
    }

    public ProductDocumentsCulture productDocument(ProductDocuments productDocuments) {
        this.productDocument = productDocuments;
        return this;
    }

    public void setProductDocument(ProductDocuments productDocuments) {
        this.productDocument = productDocuments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDocumentsCulture)) {
            return false;
        }
        return id != null && id.equals(((ProductDocumentsCulture) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDocumentsCulture{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}

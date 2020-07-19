package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import com.vertical.commerce.domain.enumeration.Gender;

/**
 * A DTO for the {@link com.vertical.commerce.domain.People} entity.
 */
public class PeopleDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String fullName;

    @NotNull
    private String preferredName;

    @NotNull
    private String searchName;

    private Gender gender;

    private Instant dateOfBirth;

    @NotNull
    private Boolean isPermittedToLogon;

    private String logonName;

    @NotNull
    private Boolean isExternalLogonProvider;

    @NotNull
    private Boolean isSystemUser;

    @NotNull
    private Boolean isEmployee;

    @NotNull
    private Boolean isSalesPerson;

    @NotNull
    private Boolean isGuestUser;

    @NotNull
    private Boolean emailPromotion;

    private String userPreferences;

    private String phoneNumber;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String emailAddress;

    private String customFields;

    private String otherLanguages;

    @NotNull
    private String userId;

    private String profilePhoto;

    @NotNull
    private Instant validFrom;

    private Instant validTo;

    private Set<SuppliersDTO> suppliers = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Instant getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Boolean isIsPermittedToLogon() {
        return isPermittedToLogon;
    }

    public void setIsPermittedToLogon(Boolean isPermittedToLogon) {
        this.isPermittedToLogon = isPermittedToLogon;
    }

    public String getLogonName() {
        return logonName;
    }

    public void setLogonName(String logonName) {
        this.logonName = logonName;
    }

    public Boolean isIsExternalLogonProvider() {
        return isExternalLogonProvider;
    }

    public void setIsExternalLogonProvider(Boolean isExternalLogonProvider) {
        this.isExternalLogonProvider = isExternalLogonProvider;
    }

    public Boolean isIsSystemUser() {
        return isSystemUser;
    }

    public void setIsSystemUser(Boolean isSystemUser) {
        this.isSystemUser = isSystemUser;
    }

    public Boolean isIsEmployee() {
        return isEmployee;
    }

    public void setIsEmployee(Boolean isEmployee) {
        this.isEmployee = isEmployee;
    }

    public Boolean isIsSalesPerson() {
        return isSalesPerson;
    }

    public void setIsSalesPerson(Boolean isSalesPerson) {
        this.isSalesPerson = isSalesPerson;
    }

    public Boolean isIsGuestUser() {
        return isGuestUser;
    }

    public void setIsGuestUser(Boolean isGuestUser) {
        this.isGuestUser = isGuestUser;
    }

    public Boolean isEmailPromotion() {
        return emailPromotion;
    }

    public void setEmailPromotion(Boolean emailPromotion) {
        this.emailPromotion = emailPromotion;
    }

    public String getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(String userPreferences) {
        this.userPreferences = userPreferences;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCustomFields() {
        return customFields;
    }

    public void setCustomFields(String customFields) {
        this.customFields = customFields;
    }

    public String getOtherLanguages() {
        return otherLanguages;
    }

    public void setOtherLanguages(String otherLanguages) {
        this.otherLanguages = otherLanguages;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
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

    public Set<SuppliersDTO> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(Set<SuppliersDTO> suppliers) {
        this.suppliers = suppliers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PeopleDTO)) {
            return false;
        }

        return id != null && id.equals(((PeopleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PeopleDTO{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", preferredName='" + getPreferredName() + "'" +
            ", searchName='" + getSearchName() + "'" +
            ", gender='" + getGender() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", isPermittedToLogon='" + isIsPermittedToLogon() + "'" +
            ", logonName='" + getLogonName() + "'" +
            ", isExternalLogonProvider='" + isIsExternalLogonProvider() + "'" +
            ", isSystemUser='" + isIsSystemUser() + "'" +
            ", isEmployee='" + isIsEmployee() + "'" +
            ", isSalesPerson='" + isIsSalesPerson() + "'" +
            ", isGuestUser='" + isIsGuestUser() + "'" +
            ", emailPromotion='" + isEmailPromotion() + "'" +
            ", userPreferences='" + getUserPreferences() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", customFields='" + getCustomFields() + "'" +
            ", otherLanguages='" + getOtherLanguages() + "'" +
            ", userId='" + getUserId() + "'" +
            ", profilePhoto='" + getProfilePhoto() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", suppliers='" + getSuppliers() + "'" +
            "}";
    }
}

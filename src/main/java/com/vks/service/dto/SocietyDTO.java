package com.vks.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.vks.domain.Society} entity.
 */
public class SocietyDTO implements Serializable {

    private Long id;

    @NotNull
    private String societyName;

    private String address;

    private String village;

    private String tahsil;

    private String state;

    private String district;

    @Lob
    private byte[] logo;

    private String logoContentType;
    private Double registrationNumber;

    private Double gstinNumber;

    private Double panNumber;

    private Double tanNumber;

    private Double phoneNumber;

    private String emailAddress;

    private Integer pinCode;

    private Instant createdOn;

    private String createdBy;

    private String description;

    private Boolean isActivate;

    private Instant lastModified;

    private String lastModifiedBy;

    private SocietyDTO society;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSocietyName() {
        return societyName;
    }

    public void setSocietyName(String societyName) {
        this.societyName = societyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getTahsil() {
        return tahsil;
    }

    public void setTahsil(String tahsil) {
        this.tahsil = tahsil;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public Double getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(Double registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Double getGstinNumber() {
        return gstinNumber;
    }

    public void setGstinNumber(Double gstinNumber) {
        this.gstinNumber = gstinNumber;
    }

    public Double getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(Double panNumber) {
        this.panNumber = panNumber;
    }

    public Double getTanNumber() {
        return tanNumber;
    }

    public void setTanNumber(Double tanNumber) {
        this.tanNumber = tanNumber;
    }

    public Double getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Double phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsActivate() {
        return isActivate;
    }

    public void setIsActivate(Boolean isActivate) {
        this.isActivate = isActivate;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public SocietyDTO getSociety() {
        return society;
    }

    public void setSociety(SocietyDTO society) {
        this.society = society;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SocietyDTO)) {
            return false;
        }

        SocietyDTO societyDTO = (SocietyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, societyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocietyDTO{" +
            "id=" + getId() +
            ", societyName='" + getSocietyName() + "'" +
            ", address='" + getAddress() + "'" +
            ", village='" + getVillage() + "'" +
            ", tahsil='" + getTahsil() + "'" +
            ", state='" + getState() + "'" +
            ", district='" + getDistrict() + "'" +
            ", logo='" + getLogo() + "'" +
            ", registrationNumber=" + getRegistrationNumber() +
            ", gstinNumber=" + getGstinNumber() +
            ", panNumber=" + getPanNumber() +
            ", tanNumber=" + getTanNumber() +
            ", phoneNumber=" + getPhoneNumber() +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", pinCode=" + getPinCode() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", description='" + getDescription() + "'" +
            ", isActivate='" + getIsActivate() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", society=" + getSociety() +
            "}";
    }
}

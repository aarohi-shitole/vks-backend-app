package com.vks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Society.
 */
@Entity
@Table(name = "society")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Society implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "society_name", nullable = false, unique = true)
    private String societyName;

    @Column(name = "address")
    private String address;

    @Column(name = "village")
    private String village;

    @Column(name = "tahsil")
    private String tahsil;

    @Column(name = "state")
    private String state;

    @Column(name = "district")
    private String district;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @Column(name = "registration_number")
    private Double registrationNumber;

    @Column(name = "gstin_number")
    private Double gstinNumber;

    @Column(name = "pan_number")
    private Double panNumber;

    @Column(name = "tan_number")
    private Double tanNumber;

    @Column(name = "phone_number")
    private Double phoneNumber;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "pin_code")
    private Integer pinCode;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "description")
    private String description;

    @Column(name = "is_activate")
    private Boolean isActivate;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = { "society" }, allowSetters = true)
    private Society society;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Society id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSocietyName() {
        return this.societyName;
    }

    public Society societyName(String societyName) {
        this.setSocietyName(societyName);
        return this;
    }

    public void setSocietyName(String societyName) {
        this.societyName = societyName;
    }

    public String getAddress() {
        return this.address;
    }

    public Society address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVillage() {
        return this.village;
    }

    public Society village(String village) {
        this.setVillage(village);
        return this;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getTahsil() {
        return this.tahsil;
    }

    public Society tahsil(String tahsil) {
        this.setTahsil(tahsil);
        return this;
    }

    public void setTahsil(String tahsil) {
        this.tahsil = tahsil;
    }

    public String getState() {
        return this.state;
    }

    public Society state(String state) {
        this.setState(state);
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return this.district;
    }

    public Society district(String district) {
        this.setDistrict(district);
        return this;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public byte[] getLogo() {
        return this.logo;
    }

    public Society logo(byte[] logo) {
        this.setLogo(logo);
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return this.logoContentType;
    }

    public Society logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public Double getRegistrationNumber() {
        return this.registrationNumber;
    }

    public Society registrationNumber(Double registrationNumber) {
        this.setRegistrationNumber(registrationNumber);
        return this;
    }

    public void setRegistrationNumber(Double registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Double getGstinNumber() {
        return this.gstinNumber;
    }

    public Society gstinNumber(Double gstinNumber) {
        this.setGstinNumber(gstinNumber);
        return this;
    }

    public void setGstinNumber(Double gstinNumber) {
        this.gstinNumber = gstinNumber;
    }

    public Double getPanNumber() {
        return this.panNumber;
    }

    public Society panNumber(Double panNumber) {
        this.setPanNumber(panNumber);
        return this;
    }

    public void setPanNumber(Double panNumber) {
        this.panNumber = panNumber;
    }

    public Double getTanNumber() {
        return this.tanNumber;
    }

    public Society tanNumber(Double tanNumber) {
        this.setTanNumber(tanNumber);
        return this;
    }

    public void setTanNumber(Double tanNumber) {
        this.tanNumber = tanNumber;
    }

    public Double getPhoneNumber() {
        return this.phoneNumber;
    }

    public Society phoneNumber(Double phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(Double phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public Society emailAddress(String emailAddress) {
        this.setEmailAddress(emailAddress);
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Integer getPinCode() {
        return this.pinCode;
    }

    public Society pinCode(Integer pinCode) {
        this.setPinCode(pinCode);
        return this;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Society createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Society createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDescription() {
        return this.description;
    }

    public Society description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsActivate() {
        return this.isActivate;
    }

    public Society isActivate(Boolean isActivate) {
        this.setIsActivate(isActivate);
        return this;
    }

    public void setIsActivate(Boolean isActivate) {
        this.isActivate = isActivate;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Society lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Society lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Society getSociety() {
        return this.society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }

    public Society society(Society society) {
        this.setSociety(society);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Society)) {
            return false;
        }
        return id != null && id.equals(((Society) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Society{" +
            "id=" + getId() +
            ", societyName='" + getSocietyName() + "'" +
            ", address='" + getAddress() + "'" +
            ", village='" + getVillage() + "'" +
            ", tahsil='" + getTahsil() + "'" +
            ", state='" + getState() + "'" +
            ", district='" + getDistrict() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
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
            "}";
    }
}

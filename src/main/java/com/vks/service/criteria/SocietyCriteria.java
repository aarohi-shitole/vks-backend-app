package com.vks.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.vks.domain.Society} entity. This class is used
 * in {@link com.vks.web.rest.SocietyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /societies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class SocietyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter societyName;

    private StringFilter address;

    private StringFilter village;

    private StringFilter tahsil;

    private StringFilter state;

    private StringFilter district;

    private DoubleFilter registrationNumber;

    private DoubleFilter gstinNumber;

    private DoubleFilter panNumber;

    private DoubleFilter tanNumber;

    private DoubleFilter phoneNumber;

    private StringFilter emailAddress;

    private IntegerFilter pinCode;

    private InstantFilter createdOn;

    private StringFilter createdBy;

    private StringFilter description;

    private BooleanFilter isActivate;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter societyId;

    private Boolean distinct;

    public SocietyCriteria() {}

    public SocietyCriteria(SocietyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.societyName = other.societyName == null ? null : other.societyName.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.village = other.village == null ? null : other.village.copy();
        this.tahsil = other.tahsil == null ? null : other.tahsil.copy();
        this.state = other.state == null ? null : other.state.copy();
        this.district = other.district == null ? null : other.district.copy();
        this.registrationNumber = other.registrationNumber == null ? null : other.registrationNumber.copy();
        this.gstinNumber = other.gstinNumber == null ? null : other.gstinNumber.copy();
        this.panNumber = other.panNumber == null ? null : other.panNumber.copy();
        this.tanNumber = other.tanNumber == null ? null : other.tanNumber.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.emailAddress = other.emailAddress == null ? null : other.emailAddress.copy();
        this.pinCode = other.pinCode == null ? null : other.pinCode.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.isActivate = other.isActivate == null ? null : other.isActivate.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.societyId = other.societyId == null ? null : other.societyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SocietyCriteria copy() {
        return new SocietyCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSocietyName() {
        return societyName;
    }

    public StringFilter societyName() {
        if (societyName == null) {
            societyName = new StringFilter();
        }
        return societyName;
    }

    public void setSocietyName(StringFilter societyName) {
        this.societyName = societyName;
    }

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getVillage() {
        return village;
    }

    public StringFilter village() {
        if (village == null) {
            village = new StringFilter();
        }
        return village;
    }

    public void setVillage(StringFilter village) {
        this.village = village;
    }

    public StringFilter getTahsil() {
        return tahsil;
    }

    public StringFilter tahsil() {
        if (tahsil == null) {
            tahsil = new StringFilter();
        }
        return tahsil;
    }

    public void setTahsil(StringFilter tahsil) {
        this.tahsil = tahsil;
    }

    public StringFilter getState() {
        return state;
    }

    public StringFilter state() {
        if (state == null) {
            state = new StringFilter();
        }
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }

    public StringFilter getDistrict() {
        return district;
    }

    public StringFilter district() {
        if (district == null) {
            district = new StringFilter();
        }
        return district;
    }

    public void setDistrict(StringFilter district) {
        this.district = district;
    }

    public DoubleFilter getRegistrationNumber() {
        return registrationNumber;
    }

    public DoubleFilter registrationNumber() {
        if (registrationNumber == null) {
            registrationNumber = new DoubleFilter();
        }
        return registrationNumber;
    }

    public void setRegistrationNumber(DoubleFilter registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public DoubleFilter getGstinNumber() {
        return gstinNumber;
    }

    public DoubleFilter gstinNumber() {
        if (gstinNumber == null) {
            gstinNumber = new DoubleFilter();
        }
        return gstinNumber;
    }

    public void setGstinNumber(DoubleFilter gstinNumber) {
        this.gstinNumber = gstinNumber;
    }

    public DoubleFilter getPanNumber() {
        return panNumber;
    }

    public DoubleFilter panNumber() {
        if (panNumber == null) {
            panNumber = new DoubleFilter();
        }
        return panNumber;
    }

    public void setPanNumber(DoubleFilter panNumber) {
        this.panNumber = panNumber;
    }

    public DoubleFilter getTanNumber() {
        return tanNumber;
    }

    public DoubleFilter tanNumber() {
        if (tanNumber == null) {
            tanNumber = new DoubleFilter();
        }
        return tanNumber;
    }

    public void setTanNumber(DoubleFilter tanNumber) {
        this.tanNumber = tanNumber;
    }

    public DoubleFilter getPhoneNumber() {
        return phoneNumber;
    }

    public DoubleFilter phoneNumber() {
        if (phoneNumber == null) {
            phoneNumber = new DoubleFilter();
        }
        return phoneNumber;
    }

    public void setPhoneNumber(DoubleFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public StringFilter getEmailAddress() {
        return emailAddress;
    }

    public StringFilter emailAddress() {
        if (emailAddress == null) {
            emailAddress = new StringFilter();
        }
        return emailAddress;
    }

    public void setEmailAddress(StringFilter emailAddress) {
        this.emailAddress = emailAddress;
    }

    public IntegerFilter getPinCode() {
        return pinCode;
    }

    public IntegerFilter pinCode() {
        if (pinCode == null) {
            pinCode = new IntegerFilter();
        }
        return pinCode;
    }

    public void setPinCode(IntegerFilter pinCode) {
        this.pinCode = pinCode;
    }

    public InstantFilter getCreatedOn() {
        return createdOn;
    }

    public InstantFilter createdOn() {
        if (createdOn == null) {
            createdOn = new InstantFilter();
        }
        return createdOn;
    }

    public void setCreatedOn(InstantFilter createdOn) {
        this.createdOn = createdOn;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            createdBy = new StringFilter();
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public BooleanFilter getIsActivate() {
        return isActivate;
    }

    public BooleanFilter isActivate() {
        if (isActivate == null) {
            isActivate = new BooleanFilter();
        }
        return isActivate;
    }

    public void setIsActivate(BooleanFilter isActivate) {
        this.isActivate = isActivate;
    }

    public InstantFilter getLastModified() {
        return lastModified;
    }

    public InstantFilter lastModified() {
        if (lastModified == null) {
            lastModified = new InstantFilter();
        }
        return lastModified;
    }

    public void setLastModified(InstantFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LongFilter getSocietyId() {
        return societyId;
    }

    public LongFilter societyId() {
        if (societyId == null) {
            societyId = new LongFilter();
        }
        return societyId;
    }

    public void setSocietyId(LongFilter societyId) {
        this.societyId = societyId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SocietyCriteria that = (SocietyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(societyName, that.societyName) &&
            Objects.equals(address, that.address) &&
            Objects.equals(village, that.village) &&
            Objects.equals(tahsil, that.tahsil) &&
            Objects.equals(state, that.state) &&
            Objects.equals(district, that.district) &&
            Objects.equals(registrationNumber, that.registrationNumber) &&
            Objects.equals(gstinNumber, that.gstinNumber) &&
            Objects.equals(panNumber, that.panNumber) &&
            Objects.equals(tanNumber, that.tanNumber) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(emailAddress, that.emailAddress) &&
            Objects.equals(pinCode, that.pinCode) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(description, that.description) &&
            Objects.equals(isActivate, that.isActivate) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(societyId, that.societyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            societyName,
            address,
            village,
            tahsil,
            state,
            district,
            registrationNumber,
            gstinNumber,
            panNumber,
            tanNumber,
            phoneNumber,
            emailAddress,
            pinCode,
            createdOn,
            createdBy,
            description,
            isActivate,
            lastModified,
            lastModifiedBy,
            societyId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocietyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (societyName != null ? "societyName=" + societyName + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (village != null ? "village=" + village + ", " : "") +
            (tahsil != null ? "tahsil=" + tahsil + ", " : "") +
            (state != null ? "state=" + state + ", " : "") +
            (district != null ? "district=" + district + ", " : "") +
            (registrationNumber != null ? "registrationNumber=" + registrationNumber + ", " : "") +
            (gstinNumber != null ? "gstinNumber=" + gstinNumber + ", " : "") +
            (panNumber != null ? "panNumber=" + panNumber + ", " : "") +
            (tanNumber != null ? "tanNumber=" + tanNumber + ", " : "") +
            (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
            (emailAddress != null ? "emailAddress=" + emailAddress + ", " : "") +
            (pinCode != null ? "pinCode=" + pinCode + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (isActivate != null ? "isActivate=" + isActivate + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (societyId != null ? "societyId=" + societyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

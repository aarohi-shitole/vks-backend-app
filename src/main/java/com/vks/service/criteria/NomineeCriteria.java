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
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.vks.domain.Nominee} entity. This class is used
 * in {@link com.vks.web.rest.NomineeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /nominees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class NomineeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter firstName;

    private StringFilter middleName;

    private StringFilter lastName;

    private StringFilter fatherName;

    private StringFilter motherName;

    private StringFilter guardianName;

    private StringFilter gender;

    private LocalDateFilter dob;

    private StringFilter aadharCard;

    private InstantFilter nomineeDeclareDate;

    private StringFilter relation;

    private StringFilter permanentAddr;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private BooleanFilter isDeleted;

    private LongFilter memberId;

    private Boolean distinct;

    public NomineeCriteria() {}

    public NomineeCriteria(NomineeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.middleName = other.middleName == null ? null : other.middleName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.fatherName = other.fatherName == null ? null : other.fatherName.copy();
        this.motherName = other.motherName == null ? null : other.motherName.copy();
        this.guardianName = other.guardianName == null ? null : other.guardianName.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.dob = other.dob == null ? null : other.dob.copy();
        this.aadharCard = other.aadharCard == null ? null : other.aadharCard.copy();
        this.nomineeDeclareDate = other.nomineeDeclareDate == null ? null : other.nomineeDeclareDate.copy();
        this.relation = other.relation == null ? null : other.relation.copy();
        this.permanentAddr = other.permanentAddr == null ? null : other.permanentAddr.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.memberId = other.memberId == null ? null : other.memberId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NomineeCriteria copy() {
        return new NomineeCriteria(this);
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

    public StringFilter getFirstName() {
        return firstName;
    }

    public StringFilter firstName() {
        if (firstName == null) {
            firstName = new StringFilter();
        }
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getMiddleName() {
        return middleName;
    }

    public StringFilter middleName() {
        if (middleName == null) {
            middleName = new StringFilter();
        }
        return middleName;
    }

    public void setMiddleName(StringFilter middleName) {
        this.middleName = middleName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public StringFilter lastName() {
        if (lastName == null) {
            lastName = new StringFilter();
        }
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getFatherName() {
        return fatherName;
    }

    public StringFilter fatherName() {
        if (fatherName == null) {
            fatherName = new StringFilter();
        }
        return fatherName;
    }

    public void setFatherName(StringFilter fatherName) {
        this.fatherName = fatherName;
    }

    public StringFilter getMotherName() {
        return motherName;
    }

    public StringFilter motherName() {
        if (motherName == null) {
            motherName = new StringFilter();
        }
        return motherName;
    }

    public void setMotherName(StringFilter motherName) {
        this.motherName = motherName;
    }

    public StringFilter getGuardianName() {
        return guardianName;
    }

    public StringFilter guardianName() {
        if (guardianName == null) {
            guardianName = new StringFilter();
        }
        return guardianName;
    }

    public void setGuardianName(StringFilter guardianName) {
        this.guardianName = guardianName;
    }

    public StringFilter getGender() {
        return gender;
    }

    public StringFilter gender() {
        if (gender == null) {
            gender = new StringFilter();
        }
        return gender;
    }

    public void setGender(StringFilter gender) {
        this.gender = gender;
    }

    public LocalDateFilter getDob() {
        return dob;
    }

    public LocalDateFilter dob() {
        if (dob == null) {
            dob = new LocalDateFilter();
        }
        return dob;
    }

    public void setDob(LocalDateFilter dob) {
        this.dob = dob;
    }

    public StringFilter getAadharCard() {
        return aadharCard;
    }

    public StringFilter aadharCard() {
        if (aadharCard == null) {
            aadharCard = new StringFilter();
        }
        return aadharCard;
    }

    public void setAadharCard(StringFilter aadharCard) {
        this.aadharCard = aadharCard;
    }

    public InstantFilter getNomineeDeclareDate() {
        return nomineeDeclareDate;
    }

    public InstantFilter nomineeDeclareDate() {
        if (nomineeDeclareDate == null) {
            nomineeDeclareDate = new InstantFilter();
        }
        return nomineeDeclareDate;
    }

    public void setNomineeDeclareDate(InstantFilter nomineeDeclareDate) {
        this.nomineeDeclareDate = nomineeDeclareDate;
    }

    public StringFilter getRelation() {
        return relation;
    }

    public StringFilter relation() {
        if (relation == null) {
            relation = new StringFilter();
        }
        return relation;
    }

    public void setRelation(StringFilter relation) {
        this.relation = relation;
    }

    public StringFilter getPermanentAddr() {
        return permanentAddr;
    }

    public StringFilter permanentAddr() {
        if (permanentAddr == null) {
            permanentAddr = new StringFilter();
        }
        return permanentAddr;
    }

    public void setPermanentAddr(StringFilter permanentAddr) {
        this.permanentAddr = permanentAddr;
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

    public BooleanFilter getIsDeleted() {
        return isDeleted;
    }

    public BooleanFilter isDeleted() {
        if (isDeleted == null) {
            isDeleted = new BooleanFilter();
        }
        return isDeleted;
    }

    public void setIsDeleted(BooleanFilter isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LongFilter getMemberId() {
        return memberId;
    }

    public LongFilter memberId() {
        if (memberId == null) {
            memberId = new LongFilter();
        }
        return memberId;
    }

    public void setMemberId(LongFilter memberId) {
        this.memberId = memberId;
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
        final NomineeCriteria that = (NomineeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(middleName, that.middleName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(fatherName, that.fatherName) &&
            Objects.equals(motherName, that.motherName) &&
            Objects.equals(guardianName, that.guardianName) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(dob, that.dob) &&
            Objects.equals(aadharCard, that.aadharCard) &&
            Objects.equals(nomineeDeclareDate, that.nomineeDeclareDate) &&
            Objects.equals(relation, that.relation) &&
            Objects.equals(permanentAddr, that.permanentAddr) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(memberId, that.memberId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            firstName,
            middleName,
            lastName,
            fatherName,
            motherName,
            guardianName,
            gender,
            dob,
            aadharCard,
            nomineeDeclareDate,
            relation,
            permanentAddr,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            isDeleted,
            memberId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NomineeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (middleName != null ? "middleName=" + middleName + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (fatherName != null ? "fatherName=" + fatherName + ", " : "") +
            (motherName != null ? "motherName=" + motherName + ", " : "") +
            (guardianName != null ? "guardianName=" + guardianName + ", " : "") +
            (gender != null ? "gender=" + gender + ", " : "") +
            (dob != null ? "dob=" + dob + ", " : "") +
            (aadharCard != null ? "aadharCard=" + aadharCard + ", " : "") +
            (nomineeDeclareDate != null ? "nomineeDeclareDate=" + nomineeDeclareDate + ", " : "") +
            (relation != null ? "relation=" + relation + ", " : "") +
            (permanentAddr != null ? "permanentAddr=" + permanentAddr + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (memberId != null ? "memberId=" + memberId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

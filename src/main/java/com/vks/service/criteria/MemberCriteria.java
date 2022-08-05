package com.vks.service.criteria;

import com.vks.domain.enumeration.Gender;
import com.vks.domain.enumeration.LoanStatus;
import com.vks.domain.enumeration.Status;
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
 * Criteria class for the {@link com.vks.domain.Member} entity. This class is used
 * in {@link com.vks.web.rest.MemberResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /members?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class MemberCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Gender
     */
    public static class GenderFilter extends Filter<Gender> {

        public GenderFilter() {}

        public GenderFilter(GenderFilter filter) {
            super(filter);
        }

        @Override
        public GenderFilter copy() {
            return new GenderFilter(this);
        }
    }

    /**
     * Class for filtering Status
     */
    public static class StatusFilter extends Filter<Status> {

        public StatusFilter() {}

        public StatusFilter(StatusFilter filter) {
            super(filter);
        }

        @Override
        public StatusFilter copy() {
            return new StatusFilter(this);
        }
    }

    /**
     * Class for filtering LoanStatus
     */
    public static class LoanStatusFilter extends Filter<LoanStatus> {

        public LoanStatusFilter() {}

        public LoanStatusFilter(LoanStatusFilter filter) {
            super(filter);
        }

        @Override
        public LoanStatusFilter copy() {
            return new LoanStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter firstName;

    private StringFilter middleName;

    private StringFilter lastName;

    private StringFilter memberUniqueId;

    private StringFilter fatherName;

    private StringFilter motherName;

    private GenderFilter gender;

    private LocalDateFilter dob;

    private StringFilter email;

    private StringFilter mobileNo;

    private StringFilter religion;

    private StringFilter category;

    private StringFilter cast;

    private StringFilter aadharCard;

    private StringFilter panCard;

    private StringFilter rationCard;

    private LongFilter familyMemberCount;

    private StringFilter occupation;

    private InstantFilter applicationDate;

    private StatusFilter status;

    private BooleanFilter kmpStatus;

    private StringFilter boardResolutionNo;

    private LocalDateFilter boardResolutionDate;

    private LoanStatusFilter loanStatus;

    private StringFilter memberType;

    private BooleanFilter isactive;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private LongFilter memberBankId;

    private LongFilter societyId;

    private Boolean distinct;

    public MemberCriteria() {}

    public MemberCriteria(MemberCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.middleName = other.middleName == null ? null : other.middleName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.memberUniqueId = other.memberUniqueId == null ? null : other.memberUniqueId.copy();
        this.fatherName = other.fatherName == null ? null : other.fatherName.copy();
        this.motherName = other.motherName == null ? null : other.motherName.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.dob = other.dob == null ? null : other.dob.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.mobileNo = other.mobileNo == null ? null : other.mobileNo.copy();
        this.religion = other.religion == null ? null : other.religion.copy();
        this.category = other.category == null ? null : other.category.copy();
        this.cast = other.cast == null ? null : other.cast.copy();
        this.aadharCard = other.aadharCard == null ? null : other.aadharCard.copy();
        this.panCard = other.panCard == null ? null : other.panCard.copy();
        this.rationCard = other.rationCard == null ? null : other.rationCard.copy();
        this.familyMemberCount = other.familyMemberCount == null ? null : other.familyMemberCount.copy();
        this.occupation = other.occupation == null ? null : other.occupation.copy();
        this.applicationDate = other.applicationDate == null ? null : other.applicationDate.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.kmpStatus = other.kmpStatus == null ? null : other.kmpStatus.copy();
        this.boardResolutionNo = other.boardResolutionNo == null ? null : other.boardResolutionNo.copy();
        this.boardResolutionDate = other.boardResolutionDate == null ? null : other.boardResolutionDate.copy();
        this.loanStatus = other.loanStatus == null ? null : other.loanStatus.copy();
        this.memberType = other.memberType == null ? null : other.memberType.copy();
        this.isactive = other.isactive == null ? null : other.isactive.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.memberBankId = other.memberBankId == null ? null : other.memberBankId.copy();
        this.societyId = other.societyId == null ? null : other.societyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MemberCriteria copy() {
        return new MemberCriteria(this);
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

    public StringFilter getMemberUniqueId() {
        return memberUniqueId;
    }

    public StringFilter memberUniqueId() {
        if (memberUniqueId == null) {
            memberUniqueId = new StringFilter();
        }
        return memberUniqueId;
    }

    public void setMemberUniqueId(StringFilter memberUniqueId) {
        this.memberUniqueId = memberUniqueId;
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

    public GenderFilter getGender() {
        return gender;
    }

    public GenderFilter gender() {
        if (gender == null) {
            gender = new GenderFilter();
        }
        return gender;
    }

    public void setGender(GenderFilter gender) {
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

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getMobileNo() {
        return mobileNo;
    }

    public StringFilter mobileNo() {
        if (mobileNo == null) {
            mobileNo = new StringFilter();
        }
        return mobileNo;
    }

    public void setMobileNo(StringFilter mobileNo) {
        this.mobileNo = mobileNo;
    }

    public StringFilter getReligion() {
        return religion;
    }

    public StringFilter religion() {
        if (religion == null) {
            religion = new StringFilter();
        }
        return religion;
    }

    public void setReligion(StringFilter religion) {
        this.religion = religion;
    }

    public StringFilter getCategory() {
        return category;
    }

    public StringFilter category() {
        if (category == null) {
            category = new StringFilter();
        }
        return category;
    }

    public void setCategory(StringFilter category) {
        this.category = category;
    }

    public StringFilter getCast() {
        return cast;
    }

    public StringFilter cast() {
        if (cast == null) {
            cast = new StringFilter();
        }
        return cast;
    }

    public void setCast(StringFilter cast) {
        this.cast = cast;
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

    public StringFilter getPanCard() {
        return panCard;
    }

    public StringFilter panCard() {
        if (panCard == null) {
            panCard = new StringFilter();
        }
        return panCard;
    }

    public void setPanCard(StringFilter panCard) {
        this.panCard = panCard;
    }

    public StringFilter getRationCard() {
        return rationCard;
    }

    public StringFilter rationCard() {
        if (rationCard == null) {
            rationCard = new StringFilter();
        }
        return rationCard;
    }

    public void setRationCard(StringFilter rationCard) {
        this.rationCard = rationCard;
    }

    public LongFilter getFamilyMemberCount() {
        return familyMemberCount;
    }

    public LongFilter familyMemberCount() {
        if (familyMemberCount == null) {
            familyMemberCount = new LongFilter();
        }
        return familyMemberCount;
    }

    public void setFamilyMemberCount(LongFilter familyMemberCount) {
        this.familyMemberCount = familyMemberCount;
    }

    public StringFilter getOccupation() {
        return occupation;
    }

    public StringFilter occupation() {
        if (occupation == null) {
            occupation = new StringFilter();
        }
        return occupation;
    }

    public void setOccupation(StringFilter occupation) {
        this.occupation = occupation;
    }

    public InstantFilter getApplicationDate() {
        return applicationDate;
    }

    public InstantFilter applicationDate() {
        if (applicationDate == null) {
            applicationDate = new InstantFilter();
        }
        return applicationDate;
    }

    public void setApplicationDate(InstantFilter applicationDate) {
        this.applicationDate = applicationDate;
    }

    public StatusFilter getStatus() {
        return status;
    }

    public StatusFilter status() {
        if (status == null) {
            status = new StatusFilter();
        }
        return status;
    }

    public void setStatus(StatusFilter status) {
        this.status = status;
    }

    public BooleanFilter getKmpStatus() {
        return kmpStatus;
    }

    public BooleanFilter kmpStatus() {
        if (kmpStatus == null) {
            kmpStatus = new BooleanFilter();
        }
        return kmpStatus;
    }

    public void setKmpStatus(BooleanFilter kmpStatus) {
        this.kmpStatus = kmpStatus;
    }

    public StringFilter getBoardResolutionNo() {
        return boardResolutionNo;
    }

    public StringFilter boardResolutionNo() {
        if (boardResolutionNo == null) {
            boardResolutionNo = new StringFilter();
        }
        return boardResolutionNo;
    }

    public void setBoardResolutionNo(StringFilter boardResolutionNo) {
        this.boardResolutionNo = boardResolutionNo;
    }

    public LocalDateFilter getBoardResolutionDate() {
        return boardResolutionDate;
    }

    public LocalDateFilter boardResolutionDate() {
        if (boardResolutionDate == null) {
            boardResolutionDate = new LocalDateFilter();
        }
        return boardResolutionDate;
    }

    public void setBoardResolutionDate(LocalDateFilter boardResolutionDate) {
        this.boardResolutionDate = boardResolutionDate;
    }

    public LoanStatusFilter getLoanStatus() {
        return loanStatus;
    }

    public LoanStatusFilter loanStatus() {
        if (loanStatus == null) {
            loanStatus = new LoanStatusFilter();
        }
        return loanStatus;
    }

    public void setLoanStatus(LoanStatusFilter loanStatus) {
        this.loanStatus = loanStatus;
    }

    public StringFilter getMemberType() {
        return memberType;
    }

    public StringFilter memberType() {
        if (memberType == null) {
            memberType = new StringFilter();
        }
        return memberType;
    }

    public void setMemberType(StringFilter memberType) {
        this.memberType = memberType;
    }

    public BooleanFilter getIsactive() {
        return isactive;
    }

    public BooleanFilter isactive() {
        if (isactive == null) {
            isactive = new BooleanFilter();
        }
        return isactive;
    }

    public void setIsactive(BooleanFilter isactive) {
        this.isactive = isactive;
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

    public LongFilter getMemberBankId() {
        return memberBankId;
    }

    public LongFilter memberBankId() {
        if (memberBankId == null) {
            memberBankId = new LongFilter();
        }
        return memberBankId;
    }

    public void setMemberBankId(LongFilter memberBankId) {
        this.memberBankId = memberBankId;
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
        final MemberCriteria that = (MemberCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(middleName, that.middleName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(memberUniqueId, that.memberUniqueId) &&
            Objects.equals(fatherName, that.fatherName) &&
            Objects.equals(motherName, that.motherName) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(dob, that.dob) &&
            Objects.equals(email, that.email) &&
            Objects.equals(mobileNo, that.mobileNo) &&
            Objects.equals(religion, that.religion) &&
            Objects.equals(category, that.category) &&
            Objects.equals(cast, that.cast) &&
            Objects.equals(aadharCard, that.aadharCard) &&
            Objects.equals(panCard, that.panCard) &&
            Objects.equals(rationCard, that.rationCard) &&
            Objects.equals(familyMemberCount, that.familyMemberCount) &&
            Objects.equals(occupation, that.occupation) &&
            Objects.equals(applicationDate, that.applicationDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(kmpStatus, that.kmpStatus) &&
            Objects.equals(boardResolutionNo, that.boardResolutionNo) &&
            Objects.equals(boardResolutionDate, that.boardResolutionDate) &&
            Objects.equals(loanStatus, that.loanStatus) &&
            Objects.equals(memberType, that.memberType) &&
            Objects.equals(isactive, that.isactive) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(memberBankId, that.memberBankId) &&
            Objects.equals(societyId, that.societyId) &&
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
            memberUniqueId,
            fatherName,
            motherName,
            gender,
            dob,
            email,
            mobileNo,
            religion,
            category,
            cast,
            aadharCard,
            panCard,
            rationCard,
            familyMemberCount,
            occupation,
            applicationDate,
            status,
            kmpStatus,
            boardResolutionNo,
            boardResolutionDate,
            loanStatus,
            memberType,
            isactive,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            memberBankId,
            societyId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (middleName != null ? "middleName=" + middleName + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (memberUniqueId != null ? "memberUniqueId=" + memberUniqueId + ", " : "") +
            (fatherName != null ? "fatherName=" + fatherName + ", " : "") +
            (motherName != null ? "motherName=" + motherName + ", " : "") +
            (gender != null ? "gender=" + gender + ", " : "") +
            (dob != null ? "dob=" + dob + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (mobileNo != null ? "mobileNo=" + mobileNo + ", " : "") +
            (religion != null ? "religion=" + religion + ", " : "") +
            (category != null ? "category=" + category + ", " : "") +
            (cast != null ? "cast=" + cast + ", " : "") +
            (aadharCard != null ? "aadharCard=" + aadharCard + ", " : "") +
            (panCard != null ? "panCard=" + panCard + ", " : "") +
            (rationCard != null ? "rationCard=" + rationCard + ", " : "") +
            (familyMemberCount != null ? "familyMemberCount=" + familyMemberCount + ", " : "") +
            (occupation != null ? "occupation=" + occupation + ", " : "") +
            (applicationDate != null ? "applicationDate=" + applicationDate + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (kmpStatus != null ? "kmpStatus=" + kmpStatus + ", " : "") +
            (boardResolutionNo != null ? "boardResolutionNo=" + boardResolutionNo + ", " : "") +
            (boardResolutionDate != null ? "boardResolutionDate=" + boardResolutionDate + ", " : "") +
            (loanStatus != null ? "loanStatus=" + loanStatus + ", " : "") +
            (memberType != null ? "memberType=" + memberType + ", " : "") +
            (isactive != null ? "isactive=" + isactive + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (memberBankId != null ? "memberBankId=" + memberBankId + ", " : "") +
            (societyId != null ? "societyId=" + societyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

package com.vks.service.dto;

import com.vks.domain.enumeration.Gender;
import com.vks.domain.enumeration.LoanStatus;
import com.vks.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.vks.domain.Member} entity.
 */
public class MemberDTO implements Serializable {

    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String memberUniqueId;

    private String fatherName;

    private String motherName;

    private Gender gender;

    private LocalDate dob;

    private String email;

    private String mobileNo;

    private String religion;

    private String category;

    private String cast;

    private String aadharCard;

    private String panCard;

    private String rationCard;

    private Long familyMemberCount;

    private String occupation;

    private Instant applicationDate;

    private Status status;

    private Boolean kmpStatus;

    private String boardResolutionNo;

    private LocalDate boardResolutionDate;

    private LoanStatus loanStatus;

    private String memberType;

    private Boolean isactive;

    private Instant lastModified;

    private String lastModifiedBy;

    private String createdBy;

    private Instant createdOn;

    private MemberBankDTO memberBank;

    private SocietyDTO society;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMemberUniqueId() {
        return memberUniqueId;
    }

    public void setMemberUniqueId(String memberUniqueId) {
        this.memberUniqueId = memberUniqueId;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getAadharCard() {
        return aadharCard;
    }

    public void setAadharCard(String aadharCard) {
        this.aadharCard = aadharCard;
    }

    public String getPanCard() {
        return panCard;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    public String getRationCard() {
        return rationCard;
    }

    public void setRationCard(String rationCard) {
        this.rationCard = rationCard;
    }

    public Long getFamilyMemberCount() {
        return familyMemberCount;
    }

    public void setFamilyMemberCount(Long familyMemberCount) {
        this.familyMemberCount = familyMemberCount;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Instant getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Instant applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Boolean getKmpStatus() {
        return kmpStatus;
    }

    public void setKmpStatus(Boolean kmpStatus) {
        this.kmpStatus = kmpStatus;
    }

    public String getBoardResolutionNo() {
        return boardResolutionNo;
    }

    public void setBoardResolutionNo(String boardResolutionNo) {
        this.boardResolutionNo = boardResolutionNo;
    }

    public LocalDate getBoardResolutionDate() {
        return boardResolutionDate;
    }

    public void setBoardResolutionDate(LocalDate boardResolutionDate) {
        this.boardResolutionDate = boardResolutionDate;
    }

    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public MemberBankDTO getMemberBank() {
        return memberBank;
    }

    public void setMemberBank(MemberBankDTO memberBank) {
        this.memberBank = memberBank;
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
        if (!(o instanceof MemberDTO)) {
            return false;
        }

        MemberDTO memberDTO = (MemberDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, memberDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", memberUniqueId='" + getMemberUniqueId() + "'" +
            ", fatherName='" + getFatherName() + "'" +
            ", motherName='" + getMotherName() + "'" +
            ", gender='" + getGender() + "'" +
            ", dob='" + getDob() + "'" +
            ", email='" + getEmail() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            ", religion='" + getReligion() + "'" +
            ", category='" + getCategory() + "'" +
            ", cast='" + getCast() + "'" +
            ", aadharCard='" + getAadharCard() + "'" +
            ", panCard='" + getPanCard() + "'" +
            ", rationCard='" + getRationCard() + "'" +
            ", familyMemberCount=" + getFamilyMemberCount() +
            ", occupation='" + getOccupation() + "'" +
            ", applicationDate='" + getApplicationDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", kmpStatus='" + getKmpStatus() + "'" +
            ", boardResolutionNo='" + getBoardResolutionNo() + "'" +
            ", boardResolutionDate='" + getBoardResolutionDate() + "'" +
            ", loanStatus='" + getLoanStatus() + "'" +
            ", memberType='" + getMemberType() + "'" +
            ", isactive='" + getIsactive() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", memberBank=" + getMemberBank() +
            ", society=" + getSociety() +
            "}";
    }
}

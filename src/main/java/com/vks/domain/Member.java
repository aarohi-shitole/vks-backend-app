package com.vks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vks.domain.enumeration.Gender;
import com.vks.domain.enumeration.LoanStatus;
import com.vks.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Member.
 */
@Entity
@Table(name = "member")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "member_unique_id", unique = true)
    private String memberUniqueId;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "mother_name")
    private String motherName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "religion")
    private String religion;

    @Column(name = "category")
    private String category;

    @Column(name = "cast")
    private String cast;

    @Column(name = "aadhar_card", unique = true)
    private String aadharCard;

    @Column(name = "pan_card", unique = true)
    private String panCard;

    @Column(name = "ration_card")
    private String rationCard;

    @Column(name = "family_member_count")
    private Long familyMemberCount;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "application_date")
    private Instant applicationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "kmp_status")
    private Boolean kmpStatus;

    @Column(name = "board_resolution_no")
    private String boardResolutionNo;

    @Column(name = "board_resolution_date")
    private LocalDate boardResolutionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_status")
    private LoanStatus loanStatus;

    @Column(name = "member_type")
    private String memberType;

    @Column(name = "isactive")
    private Boolean isactive;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @JsonIgnoreProperties(value = { "member" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private MemberBank memberBank;

    @ManyToOne
    @JsonIgnoreProperties(value = { "society" }, allowSetters = true)
    private Society society;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Member id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Member firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public Member middleName(String middleName) {
        this.setMiddleName(middleName);
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Member lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMemberUniqueId() {
        return this.memberUniqueId;
    }

    public Member memberUniqueId(String memberUniqueId) {
        this.setMemberUniqueId(memberUniqueId);
        return this;
    }

    public void setMemberUniqueId(String memberUniqueId) {
        this.memberUniqueId = memberUniqueId;
    }

    public String getFatherName() {
        return this.fatherName;
    }

    public Member fatherName(String fatherName) {
        this.setFatherName(fatherName);
        return this;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return this.motherName;
    }

    public Member motherName(String motherName) {
        this.setMotherName(motherName);
        return this;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Member gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return this.dob;
    }

    public Member dob(LocalDate dob) {
        this.setDob(dob);
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return this.email;
    }

    public Member email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public Member mobileNo(String mobileNo) {
        this.setMobileNo(mobileNo);
        return this;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getReligion() {
        return this.religion;
    }

    public Member religion(String religion) {
        this.setReligion(religion);
        return this;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getCategory() {
        return this.category;
    }

    public Member category(String category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCast() {
        return this.cast;
    }

    public Member cast(String cast) {
        this.setCast(cast);
        return this;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getAadharCard() {
        return this.aadharCard;
    }

    public Member aadharCard(String aadharCard) {
        this.setAadharCard(aadharCard);
        return this;
    }

    public void setAadharCard(String aadharCard) {
        this.aadharCard = aadharCard;
    }

    public String getPanCard() {
        return this.panCard;
    }

    public Member panCard(String panCard) {
        this.setPanCard(panCard);
        return this;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    public String getRationCard() {
        return this.rationCard;
    }

    public Member rationCard(String rationCard) {
        this.setRationCard(rationCard);
        return this;
    }

    public void setRationCard(String rationCard) {
        this.rationCard = rationCard;
    }

    public Long getFamilyMemberCount() {
        return this.familyMemberCount;
    }

    public Member familyMemberCount(Long familyMemberCount) {
        this.setFamilyMemberCount(familyMemberCount);
        return this;
    }

    public void setFamilyMemberCount(Long familyMemberCount) {
        this.familyMemberCount = familyMemberCount;
    }

    public String getOccupation() {
        return this.occupation;
    }

    public Member occupation(String occupation) {
        this.setOccupation(occupation);
        return this;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Instant getApplicationDate() {
        return this.applicationDate;
    }

    public Member applicationDate(Instant applicationDate) {
        this.setApplicationDate(applicationDate);
        return this;
    }

    public void setApplicationDate(Instant applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Status getStatus() {
        return this.status;
    }

    public Member status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Boolean getKmpStatus() {
        return this.kmpStatus;
    }

    public Member kmpStatus(Boolean kmpStatus) {
        this.setKmpStatus(kmpStatus);
        return this;
    }

    public void setKmpStatus(Boolean kmpStatus) {
        this.kmpStatus = kmpStatus;
    }

    public String getBoardResolutionNo() {
        return this.boardResolutionNo;
    }

    public Member boardResolutionNo(String boardResolutionNo) {
        this.setBoardResolutionNo(boardResolutionNo);
        return this;
    }

    public void setBoardResolutionNo(String boardResolutionNo) {
        this.boardResolutionNo = boardResolutionNo;
    }

    public LocalDate getBoardResolutionDate() {
        return this.boardResolutionDate;
    }

    public Member boardResolutionDate(LocalDate boardResolutionDate) {
        this.setBoardResolutionDate(boardResolutionDate);
        return this;
    }

    public void setBoardResolutionDate(LocalDate boardResolutionDate) {
        this.boardResolutionDate = boardResolutionDate;
    }

    public LoanStatus getLoanStatus() {
        return this.loanStatus;
    }

    public Member loanStatus(LoanStatus loanStatus) {
        this.setLoanStatus(loanStatus);
        return this;
    }

    public void setLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }

    public String getMemberType() {
        return this.memberType;
    }

    public Member memberType(String memberType) {
        this.setMemberType(memberType);
        return this;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public Boolean getIsactive() {
        return this.isactive;
    }

    public Member isactive(Boolean isactive) {
        this.setIsactive(isactive);
        return this;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Member lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Member lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Member createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Member createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public MemberBank getMemberBank() {
        return this.memberBank;
    }

    public void setMemberBank(MemberBank memberBank) {
        this.memberBank = memberBank;
    }

    public Member memberBank(MemberBank memberBank) {
        this.setMemberBank(memberBank);
        return this;
    }

    public Society getSociety() {
        return this.society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }

    public Member society(Society society) {
        this.setSociety(society);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Member)) {
            return false;
        }
        return id != null && id.equals(((Member) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Member{" +
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
            "}";
    }
}

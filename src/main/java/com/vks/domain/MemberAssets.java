package com.vks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vks.domain.enumeration.AssetType;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MemberAssets.
 */
@Entity
@Table(name = "member_assets")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MemberAssets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "asset_amount")
    private Double assetAmount;

    @Lob
    @Column(name = "other_document_1")
    private byte[] otherDocument1;

    @Column(name = "other_document_1_content_type")
    private String otherDocument1ContentType;

    @Lob
    @Column(name = "other_document_2")
    private byte[] otherDocument2;

    @Column(name = "other_document_2_content_type")
    private String otherDocument2ContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type")
    private AssetType assetType;

    @Column(name = "asset_area")
    private Integer assetArea;

    @Column(name = "asset_address")
    private String assetAddress;

    @Column(name = "number_of_assets")
    private Integer numberOfAssets;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne
    @JsonIgnoreProperties(value = { "memberBank", "society" }, allowSetters = true)
    private Member member;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MemberAssets id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAssetAmount() {
        return this.assetAmount;
    }

    public MemberAssets assetAmount(Double assetAmount) {
        this.setAssetAmount(assetAmount);
        return this;
    }

    public void setAssetAmount(Double assetAmount) {
        this.assetAmount = assetAmount;
    }

    public byte[] getOtherDocument1() {
        return this.otherDocument1;
    }

    public MemberAssets otherDocument1(byte[] otherDocument1) {
        this.setOtherDocument1(otherDocument1);
        return this;
    }

    public void setOtherDocument1(byte[] otherDocument1) {
        this.otherDocument1 = otherDocument1;
    }

    public String getOtherDocument1ContentType() {
        return this.otherDocument1ContentType;
    }

    public MemberAssets otherDocument1ContentType(String otherDocument1ContentType) {
        this.otherDocument1ContentType = otherDocument1ContentType;
        return this;
    }

    public void setOtherDocument1ContentType(String otherDocument1ContentType) {
        this.otherDocument1ContentType = otherDocument1ContentType;
    }

    public byte[] getOtherDocument2() {
        return this.otherDocument2;
    }

    public MemberAssets otherDocument2(byte[] otherDocument2) {
        this.setOtherDocument2(otherDocument2);
        return this;
    }

    public void setOtherDocument2(byte[] otherDocument2) {
        this.otherDocument2 = otherDocument2;
    }

    public String getOtherDocument2ContentType() {
        return this.otherDocument2ContentType;
    }

    public MemberAssets otherDocument2ContentType(String otherDocument2ContentType) {
        this.otherDocument2ContentType = otherDocument2ContentType;
        return this;
    }

    public void setOtherDocument2ContentType(String otherDocument2ContentType) {
        this.otherDocument2ContentType = otherDocument2ContentType;
    }

    public AssetType getAssetType() {
        return this.assetType;
    }

    public MemberAssets assetType(AssetType assetType) {
        this.setAssetType(assetType);
        return this;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public Integer getAssetArea() {
        return this.assetArea;
    }

    public MemberAssets assetArea(Integer assetArea) {
        this.setAssetArea(assetArea);
        return this;
    }

    public void setAssetArea(Integer assetArea) {
        this.assetArea = assetArea;
    }

    public String getAssetAddress() {
        return this.assetAddress;
    }

    public MemberAssets assetAddress(String assetAddress) {
        this.setAssetAddress(assetAddress);
        return this;
    }

    public void setAssetAddress(String assetAddress) {
        this.assetAddress = assetAddress;
    }

    public Integer getNumberOfAssets() {
        return this.numberOfAssets;
    }

    public MemberAssets numberOfAssets(Integer numberOfAssets) {
        this.setNumberOfAssets(numberOfAssets);
        return this;
    }

    public void setNumberOfAssets(Integer numberOfAssets) {
        this.numberOfAssets = numberOfAssets;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public MemberAssets lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public MemberAssets lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public MemberAssets createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public MemberAssets createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public MemberAssets isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Member getMember() {
        return this.member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public MemberAssets member(Member member) {
        this.setMember(member);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemberAssets)) {
            return false;
        }
        return id != null && id.equals(((MemberAssets) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberAssets{" +
            "id=" + getId() +
            ", assetAmount=" + getAssetAmount() +
            ", otherDocument1='" + getOtherDocument1() + "'" +
            ", otherDocument1ContentType='" + getOtherDocument1ContentType() + "'" +
            ", otherDocument2='" + getOtherDocument2() + "'" +
            ", otherDocument2ContentType='" + getOtherDocument2ContentType() + "'" +
            ", assetType='" + getAssetType() + "'" +
            ", assetArea=" + getAssetArea() +
            ", assetAddress='" + getAssetAddress() + "'" +
            ", numberOfAssets=" + getNumberOfAssets() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            "}";
    }
}

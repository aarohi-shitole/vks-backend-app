package com.vks.service.dto;

import com.vks.domain.enumeration.AssetType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vks.domain.MemberAssets} entity.
 */
public class MemberAssetsDTO implements Serializable {

    private Long id;

    private Double assetAmount;

    @Lob
    private byte[] otherDocument1;

    private String otherDocument1ContentType;

    @Lob
    private byte[] otherDocument2;

    private String otherDocument2ContentType;
    private AssetType assetType;

    private Integer assetArea;

    private String assetAddress;

    private Integer numberOfAssets;

    private Instant lastModified;

    private String lastModifiedBy;

    private String createdBy;

    private Instant createdOn;

    private Boolean isDeleted;

    private MemberDTO member;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAssetAmount() {
        return assetAmount;
    }

    public void setAssetAmount(Double assetAmount) {
        this.assetAmount = assetAmount;
    }

    public byte[] getOtherDocument1() {
        return otherDocument1;
    }

    public void setOtherDocument1(byte[] otherDocument1) {
        this.otherDocument1 = otherDocument1;
    }

    public String getOtherDocument1ContentType() {
        return otherDocument1ContentType;
    }

    public void setOtherDocument1ContentType(String otherDocument1ContentType) {
        this.otherDocument1ContentType = otherDocument1ContentType;
    }

    public byte[] getOtherDocument2() {
        return otherDocument2;
    }

    public void setOtherDocument2(byte[] otherDocument2) {
        this.otherDocument2 = otherDocument2;
    }

    public String getOtherDocument2ContentType() {
        return otherDocument2ContentType;
    }

    public void setOtherDocument2ContentType(String otherDocument2ContentType) {
        this.otherDocument2ContentType = otherDocument2ContentType;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public Integer getAssetArea() {
        return assetArea;
    }

    public void setAssetArea(Integer assetArea) {
        this.assetArea = assetArea;
    }

    public String getAssetAddress() {
        return assetAddress;
    }

    public void setAssetAddress(String assetAddress) {
        this.assetAddress = assetAddress;
    }

    public Integer getNumberOfAssets() {
        return numberOfAssets;
    }

    public void setNumberOfAssets(Integer numberOfAssets) {
        this.numberOfAssets = numberOfAssets;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public MemberDTO getMember() {
        return member;
    }

    public void setMember(MemberDTO member) {
        this.member = member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemberAssetsDTO)) {
            return false;
        }

        MemberAssetsDTO memberAssetsDTO = (MemberAssetsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, memberAssetsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberAssetsDTO{" +
            "id=" + getId() +
            ", assetAmount=" + getAssetAmount() +
            ", otherDocument1='" + getOtherDocument1() + "'" +
            ", otherDocument2='" + getOtherDocument2() + "'" +
            ", assetType='" + getAssetType() + "'" +
            ", assetArea=" + getAssetArea() +
            ", assetAddress='" + getAssetAddress() + "'" +
            ", numberOfAssets=" + getNumberOfAssets() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", member=" + getMember() +
            "}";
    }
}

package com.vks.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vks.domain.MemberLandAssets} entity.
 */
public class MemberLandAssetsDTO implements Serializable {

    private Long id;

    private String landType;

    private String landGatNo;

    private Double landAreaInHector;

    private String jindagiPatrakNo;

    private Double jindagiAmount;

    private String assetLandAddress;

    private Double valueOfLand;

    private Boolean assigneeOfLand;

    private Boolean isDeleted;

    private Long mLLoanNo;

    @Lob
    private byte[] jindagiPatrak;

    private String jindagiPatrakContentType;

    @Lob
    private byte[] eightA;

    private String eightAContentType;

    @Lob
    private byte[] saatBara;

    private String saatBaraContentType;
    private Instant lastModified;

    private String lastModifiedBy;

    private String createdBy;

    private Instant createdOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLandType() {
        return landType;
    }

    public void setLandType(String landType) {
        this.landType = landType;
    }

    public String getLandGatNo() {
        return landGatNo;
    }

    public void setLandGatNo(String landGatNo) {
        this.landGatNo = landGatNo;
    }

    public Double getLandAreaInHector() {
        return landAreaInHector;
    }

    public void setLandAreaInHector(Double landAreaInHector) {
        this.landAreaInHector = landAreaInHector;
    }

    public String getJindagiPatrakNo() {
        return jindagiPatrakNo;
    }

    public void setJindagiPatrakNo(String jindagiPatrakNo) {
        this.jindagiPatrakNo = jindagiPatrakNo;
    }

    public Double getJindagiAmount() {
        return jindagiAmount;
    }

    public void setJindagiAmount(Double jindagiAmount) {
        this.jindagiAmount = jindagiAmount;
    }

    public String getAssetLandAddress() {
        return assetLandAddress;
    }

    public void setAssetLandAddress(String assetLandAddress) {
        this.assetLandAddress = assetLandAddress;
    }

    public Double getValueOfLand() {
        return valueOfLand;
    }

    public void setValueOfLand(Double valueOfLand) {
        this.valueOfLand = valueOfLand;
    }

    public Boolean getAssigneeOfLand() {
        return assigneeOfLand;
    }

    public void setAssigneeOfLand(Boolean assigneeOfLand) {
        this.assigneeOfLand = assigneeOfLand;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getmLLoanNo() {
        return mLLoanNo;
    }

    public void setmLLoanNo(Long mLLoanNo) {
        this.mLLoanNo = mLLoanNo;
    }

    public byte[] getJindagiPatrak() {
        return jindagiPatrak;
    }

    public void setJindagiPatrak(byte[] jindagiPatrak) {
        this.jindagiPatrak = jindagiPatrak;
    }

    public String getJindagiPatrakContentType() {
        return jindagiPatrakContentType;
    }

    public void setJindagiPatrakContentType(String jindagiPatrakContentType) {
        this.jindagiPatrakContentType = jindagiPatrakContentType;
    }

    public byte[] getEightA() {
        return eightA;
    }

    public void setEightA(byte[] eightA) {
        this.eightA = eightA;
    }

    public String getEightAContentType() {
        return eightAContentType;
    }

    public void setEightAContentType(String eightAContentType) {
        this.eightAContentType = eightAContentType;
    }

    public byte[] getSaatBara() {
        return saatBara;
    }

    public void setSaatBara(byte[] saatBara) {
        this.saatBara = saatBara;
    }

    public String getSaatBaraContentType() {
        return saatBaraContentType;
    }

    public void setSaatBaraContentType(String saatBaraContentType) {
        this.saatBaraContentType = saatBaraContentType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemberLandAssetsDTO)) {
            return false;
        }

        MemberLandAssetsDTO memberLandAssetsDTO = (MemberLandAssetsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, memberLandAssetsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberLandAssetsDTO{" +
            "id=" + getId() +
            ", landType='" + getLandType() + "'" +
            ", landGatNo='" + getLandGatNo() + "'" +
            ", landAreaInHector=" + getLandAreaInHector() +
            ", jindagiPatrakNo='" + getJindagiPatrakNo() + "'" +
            ", jindagiAmount=" + getJindagiAmount() +
            ", assetLandAddress='" + getAssetLandAddress() + "'" +
            ", valueOfLand=" + getValueOfLand() +
            ", assigneeOfLand='" + getAssigneeOfLand() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", mLLoanNo=" + getmLLoanNo() +
            ", jindagiPatrak='" + getJindagiPatrak() + "'" +
            ", eightA='" + getEightA() + "'" +
            ", saatBara='" + getSaatBara() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            "}";
    }
}

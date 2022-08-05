package com.vks.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MemberLandAssets.
 */
@Entity
@Table(name = "member_land_assets")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MemberLandAssets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "land_type")
    private String landType;

    @Column(name = "land_gat_no")
    private String landGatNo;

    @Column(name = "land_area_in_hector")
    private Double landAreaInHector;

    @Column(name = "jindagi_patrak_no")
    private String jindagiPatrakNo;

    @Column(name = "jindagi_amount")
    private Double jindagiAmount;

    @Column(name = "asset_land_address")
    private String assetLandAddress;

    @Column(name = "value_of_land")
    private Double valueOfLand;

    @Column(name = "assignee_of_land")
    private Boolean assigneeOfLand;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "m_l_loan_no")
    private Long mLLoanNo;

    @Lob
    @Column(name = "jindagi_patrak")
    private byte[] jindagiPatrak;

    @Column(name = "jindagi_patrak_content_type")
    private String jindagiPatrakContentType;

    @Lob
    @Column(name = "eight_a")
    private byte[] eightA;

    @Column(name = "eight_a_content_type")
    private String eightAContentType;

    @Lob
    @Column(name = "saat_bara")
    private byte[] saatBara;

    @Column(name = "saat_bara_content_type")
    private String saatBaraContentType;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MemberLandAssets id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLandType() {
        return this.landType;
    }

    public MemberLandAssets landType(String landType) {
        this.setLandType(landType);
        return this;
    }

    public void setLandType(String landType) {
        this.landType = landType;
    }

    public String getLandGatNo() {
        return this.landGatNo;
    }

    public MemberLandAssets landGatNo(String landGatNo) {
        this.setLandGatNo(landGatNo);
        return this;
    }

    public void setLandGatNo(String landGatNo) {
        this.landGatNo = landGatNo;
    }

    public Double getLandAreaInHector() {
        return this.landAreaInHector;
    }

    public MemberLandAssets landAreaInHector(Double landAreaInHector) {
        this.setLandAreaInHector(landAreaInHector);
        return this;
    }

    public void setLandAreaInHector(Double landAreaInHector) {
        this.landAreaInHector = landAreaInHector;
    }

    public String getJindagiPatrakNo() {
        return this.jindagiPatrakNo;
    }

    public MemberLandAssets jindagiPatrakNo(String jindagiPatrakNo) {
        this.setJindagiPatrakNo(jindagiPatrakNo);
        return this;
    }

    public void setJindagiPatrakNo(String jindagiPatrakNo) {
        this.jindagiPatrakNo = jindagiPatrakNo;
    }

    public Double getJindagiAmount() {
        return this.jindagiAmount;
    }

    public MemberLandAssets jindagiAmount(Double jindagiAmount) {
        this.setJindagiAmount(jindagiAmount);
        return this;
    }

    public void setJindagiAmount(Double jindagiAmount) {
        this.jindagiAmount = jindagiAmount;
    }

    public String getAssetLandAddress() {
        return this.assetLandAddress;
    }

    public MemberLandAssets assetLandAddress(String assetLandAddress) {
        this.setAssetLandAddress(assetLandAddress);
        return this;
    }

    public void setAssetLandAddress(String assetLandAddress) {
        this.assetLandAddress = assetLandAddress;
    }

    public Double getValueOfLand() {
        return this.valueOfLand;
    }

    public MemberLandAssets valueOfLand(Double valueOfLand) {
        this.setValueOfLand(valueOfLand);
        return this;
    }

    public void setValueOfLand(Double valueOfLand) {
        this.valueOfLand = valueOfLand;
    }

    public Boolean getAssigneeOfLand() {
        return this.assigneeOfLand;
    }

    public MemberLandAssets assigneeOfLand(Boolean assigneeOfLand) {
        this.setAssigneeOfLand(assigneeOfLand);
        return this;
    }

    public void setAssigneeOfLand(Boolean assigneeOfLand) {
        this.assigneeOfLand = assigneeOfLand;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public MemberLandAssets isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getmLLoanNo() {
        return this.mLLoanNo;
    }

    public MemberLandAssets mLLoanNo(Long mLLoanNo) {
        this.setmLLoanNo(mLLoanNo);
        return this;
    }

    public void setmLLoanNo(Long mLLoanNo) {
        this.mLLoanNo = mLLoanNo;
    }

    public byte[] getJindagiPatrak() {
        return this.jindagiPatrak;
    }

    public MemberLandAssets jindagiPatrak(byte[] jindagiPatrak) {
        this.setJindagiPatrak(jindagiPatrak);
        return this;
    }

    public void setJindagiPatrak(byte[] jindagiPatrak) {
        this.jindagiPatrak = jindagiPatrak;
    }

    public String getJindagiPatrakContentType() {
        return this.jindagiPatrakContentType;
    }

    public MemberLandAssets jindagiPatrakContentType(String jindagiPatrakContentType) {
        this.jindagiPatrakContentType = jindagiPatrakContentType;
        return this;
    }

    public void setJindagiPatrakContentType(String jindagiPatrakContentType) {
        this.jindagiPatrakContentType = jindagiPatrakContentType;
    }

    public byte[] getEightA() {
        return this.eightA;
    }

    public MemberLandAssets eightA(byte[] eightA) {
        this.setEightA(eightA);
        return this;
    }

    public void setEightA(byte[] eightA) {
        this.eightA = eightA;
    }

    public String getEightAContentType() {
        return this.eightAContentType;
    }

    public MemberLandAssets eightAContentType(String eightAContentType) {
        this.eightAContentType = eightAContentType;
        return this;
    }

    public void setEightAContentType(String eightAContentType) {
        this.eightAContentType = eightAContentType;
    }

    public byte[] getSaatBara() {
        return this.saatBara;
    }

    public MemberLandAssets saatBara(byte[] saatBara) {
        this.setSaatBara(saatBara);
        return this;
    }

    public void setSaatBara(byte[] saatBara) {
        this.saatBara = saatBara;
    }

    public String getSaatBaraContentType() {
        return this.saatBaraContentType;
    }

    public MemberLandAssets saatBaraContentType(String saatBaraContentType) {
        this.saatBaraContentType = saatBaraContentType;
        return this;
    }

    public void setSaatBaraContentType(String saatBaraContentType) {
        this.saatBaraContentType = saatBaraContentType;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public MemberLandAssets lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public MemberLandAssets lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public MemberLandAssets createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public MemberLandAssets createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemberLandAssets)) {
            return false;
        }
        return id != null && id.equals(((MemberLandAssets) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberLandAssets{" +
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
            ", jindagiPatrakContentType='" + getJindagiPatrakContentType() + "'" +
            ", eightA='" + getEightA() + "'" +
            ", eightAContentType='" + getEightAContentType() + "'" +
            ", saatBara='" + getSaatBara() + "'" +
            ", saatBaraContentType='" + getSaatBaraContentType() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            "}";
    }
}

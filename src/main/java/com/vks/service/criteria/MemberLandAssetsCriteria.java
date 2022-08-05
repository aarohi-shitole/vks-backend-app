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
 * Criteria class for the {@link com.vks.domain.MemberLandAssets} entity. This class is used
 * in {@link com.vks.web.rest.MemberLandAssetsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /member-land-assets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class MemberLandAssetsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter landType;

    private StringFilter landGatNo;

    private DoubleFilter landAreaInHector;

    private StringFilter jindagiPatrakNo;

    private DoubleFilter jindagiAmount;

    private StringFilter assetLandAddress;

    private DoubleFilter valueOfLand;

    private BooleanFilter assigneeOfLand;

    private BooleanFilter isDeleted;

    private LongFilter mLLoanNo;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private Boolean distinct;

    public MemberLandAssetsCriteria() {}

    public MemberLandAssetsCriteria(MemberLandAssetsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.landType = other.landType == null ? null : other.landType.copy();
        this.landGatNo = other.landGatNo == null ? null : other.landGatNo.copy();
        this.landAreaInHector = other.landAreaInHector == null ? null : other.landAreaInHector.copy();
        this.jindagiPatrakNo = other.jindagiPatrakNo == null ? null : other.jindagiPatrakNo.copy();
        this.jindagiAmount = other.jindagiAmount == null ? null : other.jindagiAmount.copy();
        this.assetLandAddress = other.assetLandAddress == null ? null : other.assetLandAddress.copy();
        this.valueOfLand = other.valueOfLand == null ? null : other.valueOfLand.copy();
        this.assigneeOfLand = other.assigneeOfLand == null ? null : other.assigneeOfLand.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.mLLoanNo = other.mLLoanNo == null ? null : other.mLLoanNo.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MemberLandAssetsCriteria copy() {
        return new MemberLandAssetsCriteria(this);
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

    public StringFilter getLandType() {
        return landType;
    }

    public StringFilter landType() {
        if (landType == null) {
            landType = new StringFilter();
        }
        return landType;
    }

    public void setLandType(StringFilter landType) {
        this.landType = landType;
    }

    public StringFilter getLandGatNo() {
        return landGatNo;
    }

    public StringFilter landGatNo() {
        if (landGatNo == null) {
            landGatNo = new StringFilter();
        }
        return landGatNo;
    }

    public void setLandGatNo(StringFilter landGatNo) {
        this.landGatNo = landGatNo;
    }

    public DoubleFilter getLandAreaInHector() {
        return landAreaInHector;
    }

    public DoubleFilter landAreaInHector() {
        if (landAreaInHector == null) {
            landAreaInHector = new DoubleFilter();
        }
        return landAreaInHector;
    }

    public void setLandAreaInHector(DoubleFilter landAreaInHector) {
        this.landAreaInHector = landAreaInHector;
    }

    public StringFilter getJindagiPatrakNo() {
        return jindagiPatrakNo;
    }

    public StringFilter jindagiPatrakNo() {
        if (jindagiPatrakNo == null) {
            jindagiPatrakNo = new StringFilter();
        }
        return jindagiPatrakNo;
    }

    public void setJindagiPatrakNo(StringFilter jindagiPatrakNo) {
        this.jindagiPatrakNo = jindagiPatrakNo;
    }

    public DoubleFilter getJindagiAmount() {
        return jindagiAmount;
    }

    public DoubleFilter jindagiAmount() {
        if (jindagiAmount == null) {
            jindagiAmount = new DoubleFilter();
        }
        return jindagiAmount;
    }

    public void setJindagiAmount(DoubleFilter jindagiAmount) {
        this.jindagiAmount = jindagiAmount;
    }

    public StringFilter getAssetLandAddress() {
        return assetLandAddress;
    }

    public StringFilter assetLandAddress() {
        if (assetLandAddress == null) {
            assetLandAddress = new StringFilter();
        }
        return assetLandAddress;
    }

    public void setAssetLandAddress(StringFilter assetLandAddress) {
        this.assetLandAddress = assetLandAddress;
    }

    public DoubleFilter getValueOfLand() {
        return valueOfLand;
    }

    public DoubleFilter valueOfLand() {
        if (valueOfLand == null) {
            valueOfLand = new DoubleFilter();
        }
        return valueOfLand;
    }

    public void setValueOfLand(DoubleFilter valueOfLand) {
        this.valueOfLand = valueOfLand;
    }

    public BooleanFilter getAssigneeOfLand() {
        return assigneeOfLand;
    }

    public BooleanFilter assigneeOfLand() {
        if (assigneeOfLand == null) {
            assigneeOfLand = new BooleanFilter();
        }
        return assigneeOfLand;
    }

    public void setAssigneeOfLand(BooleanFilter assigneeOfLand) {
        this.assigneeOfLand = assigneeOfLand;
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

    public LongFilter getmLLoanNo() {
        return mLLoanNo;
    }

    public LongFilter mLLoanNo() {
        if (mLLoanNo == null) {
            mLLoanNo = new LongFilter();
        }
        return mLLoanNo;
    }

    public void setmLLoanNo(LongFilter mLLoanNo) {
        this.mLLoanNo = mLLoanNo;
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
        final MemberLandAssetsCriteria that = (MemberLandAssetsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(landType, that.landType) &&
            Objects.equals(landGatNo, that.landGatNo) &&
            Objects.equals(landAreaInHector, that.landAreaInHector) &&
            Objects.equals(jindagiPatrakNo, that.jindagiPatrakNo) &&
            Objects.equals(jindagiAmount, that.jindagiAmount) &&
            Objects.equals(assetLandAddress, that.assetLandAddress) &&
            Objects.equals(valueOfLand, that.valueOfLand) &&
            Objects.equals(assigneeOfLand, that.assigneeOfLand) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(mLLoanNo, that.mLLoanNo) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            landType,
            landGatNo,
            landAreaInHector,
            jindagiPatrakNo,
            jindagiAmount,
            assetLandAddress,
            valueOfLand,
            assigneeOfLand,
            isDeleted,
            mLLoanNo,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberLandAssetsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (landType != null ? "landType=" + landType + ", " : "") +
            (landGatNo != null ? "landGatNo=" + landGatNo + ", " : "") +
            (landAreaInHector != null ? "landAreaInHector=" + landAreaInHector + ", " : "") +
            (jindagiPatrakNo != null ? "jindagiPatrakNo=" + jindagiPatrakNo + ", " : "") +
            (jindagiAmount != null ? "jindagiAmount=" + jindagiAmount + ", " : "") +
            (assetLandAddress != null ? "assetLandAddress=" + assetLandAddress + ", " : "") +
            (valueOfLand != null ? "valueOfLand=" + valueOfLand + ", " : "") +
            (assigneeOfLand != null ? "assigneeOfLand=" + assigneeOfLand + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (mLLoanNo != null ? "mLLoanNo=" + mLLoanNo + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

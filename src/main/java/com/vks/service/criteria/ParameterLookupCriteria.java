package com.vks.service.criteria;

import com.vks.domain.enumeration.ParameterType;
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
 * Criteria class for the {@link com.vks.domain.ParameterLookup} entity. This class is used
 * in {@link com.vks.web.rest.ParameterLookupResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /parameter-lookups?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ParameterLookupCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ParameterType
     */
    public static class ParameterTypeFilter extends Filter<ParameterType> {

        public ParameterTypeFilter() {}

        public ParameterTypeFilter(ParameterTypeFilter filter) {
            super(filter);
        }

        @Override
        public ParameterTypeFilter copy() {
            return new ParameterTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ParameterTypeFilter parameterType;

    private StringFilter description;

    private StringFilter displayValue;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private BooleanFilter isDeleted;

    private Boolean distinct;

    public ParameterLookupCriteria() {}

    public ParameterLookupCriteria(ParameterLookupCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.parameterType = other.parameterType == null ? null : other.parameterType.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.displayValue = other.displayValue == null ? null : other.displayValue.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ParameterLookupCriteria copy() {
        return new ParameterLookupCriteria(this);
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

    public ParameterTypeFilter getParameterType() {
        return parameterType;
    }

    public ParameterTypeFilter parameterType() {
        if (parameterType == null) {
            parameterType = new ParameterTypeFilter();
        }
        return parameterType;
    }

    public void setParameterType(ParameterTypeFilter parameterType) {
        this.parameterType = parameterType;
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

    public StringFilter getDisplayValue() {
        return displayValue;
    }

    public StringFilter displayValue() {
        if (displayValue == null) {
            displayValue = new StringFilter();
        }
        return displayValue;
    }

    public void setDisplayValue(StringFilter displayValue) {
        this.displayValue = displayValue;
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
        final ParameterLookupCriteria that = (ParameterLookupCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(parameterType, that.parameterType) &&
            Objects.equals(description, that.description) &&
            Objects.equals(displayValue, that.displayValue) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            parameterType,
            description,
            displayValue,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            isDeleted,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParameterLookupCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (parameterType != null ? "parameterType=" + parameterType + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (displayValue != null ? "displayValue=" + displayValue + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}

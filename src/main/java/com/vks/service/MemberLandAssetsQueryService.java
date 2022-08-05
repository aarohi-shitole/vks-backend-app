package com.vks.service;

import com.vks.domain.*; // for static metamodels
import com.vks.domain.MemberLandAssets;
import com.vks.repository.MemberLandAssetsRepository;
import com.vks.service.criteria.MemberLandAssetsCriteria;
import com.vks.service.dto.MemberLandAssetsDTO;
import com.vks.service.mapper.MemberLandAssetsMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link MemberLandAssets} entities in the database.
 * The main input is a {@link MemberLandAssetsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MemberLandAssetsDTO} or a {@link Page} of {@link MemberLandAssetsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MemberLandAssetsQueryService extends QueryService<MemberLandAssets> {

    private final Logger log = LoggerFactory.getLogger(MemberLandAssetsQueryService.class);

    private final MemberLandAssetsRepository memberLandAssetsRepository;

    private final MemberLandAssetsMapper memberLandAssetsMapper;

    public MemberLandAssetsQueryService(
        MemberLandAssetsRepository memberLandAssetsRepository,
        MemberLandAssetsMapper memberLandAssetsMapper
    ) {
        this.memberLandAssetsRepository = memberLandAssetsRepository;
        this.memberLandAssetsMapper = memberLandAssetsMapper;
    }

    /**
     * Return a {@link List} of {@link MemberLandAssetsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MemberLandAssetsDTO> findByCriteria(MemberLandAssetsCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<MemberLandAssets> specification = createSpecification(criteria);
        return memberLandAssetsMapper.toDto(memberLandAssetsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MemberLandAssetsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MemberLandAssetsDTO> findByCriteria(MemberLandAssetsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<MemberLandAssets> specification = createSpecification(criteria);
        return memberLandAssetsRepository.findAll(specification, page).map(memberLandAssetsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MemberLandAssetsCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<MemberLandAssets> specification = createSpecification(criteria);
        return memberLandAssetsRepository.count(specification);
    }

    /**
     * Function to convert {@link MemberLandAssetsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MemberLandAssets> createSpecification(MemberLandAssetsCriteria criteria) {
        Specification<MemberLandAssets> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MemberLandAssets_.id));
            }
            if (criteria.getLandType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLandType(), MemberLandAssets_.landType));
            }
            if (criteria.getLandGatNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLandGatNo(), MemberLandAssets_.landGatNo));
            }
            if (criteria.getLandAreaInHector() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLandAreaInHector(), MemberLandAssets_.landAreaInHector));
            }
            if (criteria.getJindagiPatrakNo() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getJindagiPatrakNo(), MemberLandAssets_.jindagiPatrakNo));
            }
            if (criteria.getJindagiAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJindagiAmount(), MemberLandAssets_.jindagiAmount));
            }
            if (criteria.getAssetLandAddress() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAssetLandAddress(), MemberLandAssets_.assetLandAddress));
            }
            if (criteria.getValueOfLand() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValueOfLand(), MemberLandAssets_.valueOfLand));
            }
            if (criteria.getAssigneeOfLand() != null) {
                specification = specification.and(buildSpecification(criteria.getAssigneeOfLand(), MemberLandAssets_.assigneeOfLand));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), MemberLandAssets_.isDeleted));
            }
            if (criteria.getmLLoanNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getmLLoanNo(), MemberLandAssets_.mLLoanNo));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), MemberLandAssets_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), MemberLandAssets_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), MemberLandAssets_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), MemberLandAssets_.createdOn));
            }
        }
        return specification;
    }
}

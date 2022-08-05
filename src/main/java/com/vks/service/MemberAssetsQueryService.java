package com.vks.service;

import com.vks.domain.*; // for static metamodels
import com.vks.domain.MemberAssets;
import com.vks.repository.MemberAssetsRepository;
import com.vks.service.criteria.MemberAssetsCriteria;
import com.vks.service.dto.MemberAssetsDTO;
import com.vks.service.mapper.MemberAssetsMapper;
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
 * Service for executing complex queries for {@link MemberAssets} entities in the database.
 * The main input is a {@link MemberAssetsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MemberAssetsDTO} or a {@link Page} of {@link MemberAssetsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MemberAssetsQueryService extends QueryService<MemberAssets> {

    private final Logger log = LoggerFactory.getLogger(MemberAssetsQueryService.class);

    private final MemberAssetsRepository memberAssetsRepository;

    private final MemberAssetsMapper memberAssetsMapper;

    public MemberAssetsQueryService(MemberAssetsRepository memberAssetsRepository, MemberAssetsMapper memberAssetsMapper) {
        this.memberAssetsRepository = memberAssetsRepository;
        this.memberAssetsMapper = memberAssetsMapper;
    }

    /**
     * Return a {@link List} of {@link MemberAssetsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MemberAssetsDTO> findByCriteria(MemberAssetsCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<MemberAssets> specification = createSpecification(criteria);
        return memberAssetsMapper.toDto(memberAssetsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MemberAssetsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MemberAssetsDTO> findByCriteria(MemberAssetsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<MemberAssets> specification = createSpecification(criteria);
        return memberAssetsRepository.findAll(specification, page).map(memberAssetsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MemberAssetsCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<MemberAssets> specification = createSpecification(criteria);
        return memberAssetsRepository.count(specification);
    }

    /**
     * Function to convert {@link MemberAssetsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MemberAssets> createSpecification(MemberAssetsCriteria criteria) {
        Specification<MemberAssets> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MemberAssets_.id));
            }
            if (criteria.getAssetAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssetAmount(), MemberAssets_.assetAmount));
            }
            if (criteria.getAssetType() != null) {
                specification = specification.and(buildSpecification(criteria.getAssetType(), MemberAssets_.assetType));
            }
            if (criteria.getAssetArea() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssetArea(), MemberAssets_.assetArea));
            }
            if (criteria.getAssetAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssetAddress(), MemberAssets_.assetAddress));
            }
            if (criteria.getNumberOfAssets() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfAssets(), MemberAssets_.numberOfAssets));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), MemberAssets_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), MemberAssets_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), MemberAssets_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), MemberAssets_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), MemberAssets_.isDeleted));
            }
            if (criteria.getMemberId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getMemberId(), root -> root.join(MemberAssets_.member, JoinType.LEFT).get(Member_.id))
                    );
            }
        }
        return specification;
    }
}

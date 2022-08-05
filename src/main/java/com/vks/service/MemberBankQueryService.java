package com.vks.service;

import com.vks.domain.*; // for static metamodels
import com.vks.domain.MemberBank;
import com.vks.repository.MemberBankRepository;
import com.vks.service.criteria.MemberBankCriteria;
import com.vks.service.dto.MemberBankDTO;
import com.vks.service.mapper.MemberBankMapper;
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
 * Service for executing complex queries for {@link MemberBank} entities in the database.
 * The main input is a {@link MemberBankCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MemberBankDTO} or a {@link Page} of {@link MemberBankDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MemberBankQueryService extends QueryService<MemberBank> {

    private final Logger log = LoggerFactory.getLogger(MemberBankQueryService.class);

    private final MemberBankRepository memberBankRepository;

    private final MemberBankMapper memberBankMapper;

    public MemberBankQueryService(MemberBankRepository memberBankRepository, MemberBankMapper memberBankMapper) {
        this.memberBankRepository = memberBankRepository;
        this.memberBankMapper = memberBankMapper;
    }

    /**
     * Return a {@link List} of {@link MemberBankDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MemberBankDTO> findByCriteria(MemberBankCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<MemberBank> specification = createSpecification(criteria);
        return memberBankMapper.toDto(memberBankRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MemberBankDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MemberBankDTO> findByCriteria(MemberBankCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<MemberBank> specification = createSpecification(criteria);
        return memberBankRepository.findAll(specification, page).map(memberBankMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MemberBankCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<MemberBank> specification = createSpecification(criteria);
        return memberBankRepository.count(specification);
    }

    /**
     * Function to convert {@link MemberBankCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MemberBank> createSpecification(MemberBankCriteria criteria) {
        Specification<MemberBank> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MemberBank_.id));
            }
            if (criteria.getBankName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankName(), MemberBank_.bankName));
            }
            if (criteria.getBranchName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchName(), MemberBank_.branchName));
            }
            if (criteria.getAccountNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccountNumber(), MemberBank_.accountNumber));
            }
            if (criteria.getIfsccode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIfsccode(), MemberBank_.ifsccode));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), MemberBank_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), MemberBank_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), MemberBank_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), MemberBank_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), MemberBank_.isDeleted));
            }
            if (criteria.getMemberId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getMemberId(), root -> root.join(MemberBank_.member, JoinType.LEFT).get(Member_.id))
                    );
            }
        }
        return specification;
    }
}

package com.vks.service;

import com.vks.domain.*; // for static metamodels
import com.vks.domain.Member;
import com.vks.repository.MemberRepository;
import com.vks.service.criteria.MemberCriteria;
import com.vks.service.dto.MemberDTO;
import com.vks.service.mapper.MemberMapper;
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
 * Service for executing complex queries for {@link Member} entities in the database.
 * The main input is a {@link MemberCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MemberDTO} or a {@link Page} of {@link MemberDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MemberQueryService extends QueryService<Member> {

    private final Logger log = LoggerFactory.getLogger(MemberQueryService.class);

    private final MemberRepository memberRepository;

    private final MemberMapper memberMapper;

    public MemberQueryService(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    /**
     * Return a {@link List} of {@link MemberDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MemberDTO> findByCriteria(MemberCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<Member> specification = createSpecification(criteria);
        return memberMapper.toDto(memberRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MemberDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MemberDTO> findByCriteria(MemberCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<Member> specification = createSpecification(criteria);
        return memberRepository.findAll(specification, page).map(memberMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MemberCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<Member> specification = createSpecification(criteria);
        return memberRepository.count(specification);
    }

    /**
     * Function to convert {@link MemberCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Member> createSpecification(MemberCriteria criteria) {
        Specification<Member> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Member_.id));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Member_.firstName));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleName(), Member_.middleName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Member_.lastName));
            }
            if (criteria.getMemberUniqueId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemberUniqueId(), Member_.memberUniqueId));
            }
            if (criteria.getFatherName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFatherName(), Member_.fatherName));
            }
            if (criteria.getMotherName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMotherName(), Member_.motherName));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildSpecification(criteria.getGender(), Member_.gender));
            }
            if (criteria.getDob() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDob(), Member_.dob));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Member_.email));
            }
            if (criteria.getMobileNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobileNo(), Member_.mobileNo));
            }
            if (criteria.getReligion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReligion(), Member_.religion));
            }
            if (criteria.getCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategory(), Member_.category));
            }
            if (criteria.getCast() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCast(), Member_.cast));
            }
            if (criteria.getAadharCard() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAadharCard(), Member_.aadharCard));
            }
            if (criteria.getPanCard() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPanCard(), Member_.panCard));
            }
            if (criteria.getRationCard() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRationCard(), Member_.rationCard));
            }
            if (criteria.getFamilyMemberCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFamilyMemberCount(), Member_.familyMemberCount));
            }
            if (criteria.getOccupation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOccupation(), Member_.occupation));
            }
            if (criteria.getApplicationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApplicationDate(), Member_.applicationDate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Member_.status));
            }
            if (criteria.getKmpStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getKmpStatus(), Member_.kmpStatus));
            }
            if (criteria.getBoardResolutionNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBoardResolutionNo(), Member_.boardResolutionNo));
            }
            if (criteria.getBoardResolutionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBoardResolutionDate(), Member_.boardResolutionDate));
            }
            if (criteria.getLoanStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getLoanStatus(), Member_.loanStatus));
            }
            if (criteria.getMemberType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemberType(), Member_.memberType));
            }
            if (criteria.getIsactive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsactive(), Member_.isactive));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Member_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Member_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Member_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), Member_.createdOn));
            }
            if (criteria.getMemberBankId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMemberBankId(),
                            root -> root.join(Member_.memberBank, JoinType.LEFT).get(MemberBank_.id)
                        )
                    );
            }
            if (criteria.getSocietyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSocietyId(), root -> root.join(Member_.society, JoinType.LEFT).get(Society_.id))
                    );
            }
        }
        return specification;
    }
}

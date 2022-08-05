package com.vks.service;

import com.vks.domain.*; // for static metamodels
import com.vks.domain.Society;
import com.vks.repository.SocietyRepository;
import com.vks.service.criteria.SocietyCriteria;
import com.vks.service.dto.SocietyDTO;
import com.vks.service.mapper.SocietyMapper;
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
 * Service for executing complex queries for {@link Society} entities in the database.
 * The main input is a {@link SocietyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SocietyDTO} or a {@link Page} of {@link SocietyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SocietyQueryService extends QueryService<Society> {

    private final Logger log = LoggerFactory.getLogger(SocietyQueryService.class);

    private final SocietyRepository societyRepository;

    private final SocietyMapper societyMapper;

    public SocietyQueryService(SocietyRepository societyRepository, SocietyMapper societyMapper) {
        this.societyRepository = societyRepository;
        this.societyMapper = societyMapper;
    }

    /**
     * Return a {@link List} of {@link SocietyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SocietyDTO> findByCriteria(SocietyCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<Society> specification = createSpecification(criteria);
        return societyMapper.toDto(societyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SocietyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SocietyDTO> findByCriteria(SocietyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<Society> specification = createSpecification(criteria);
        return societyRepository.findAll(specification, page).map(societyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SocietyCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<Society> specification = createSpecification(criteria);
        return societyRepository.count(specification);
    }

    /**
     * Function to convert {@link SocietyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Society> createSpecification(SocietyCriteria criteria) {
        Specification<Society> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Society_.id));
            }
            if (criteria.getSocietyName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSocietyName(), Society_.societyName));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Society_.address));
            }
            if (criteria.getVillage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVillage(), Society_.village));
            }
            if (criteria.getTahsil() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTahsil(), Society_.tahsil));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getState(), Society_.state));
            }
            if (criteria.getDistrict() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDistrict(), Society_.district));
            }
            if (criteria.getRegistrationNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegistrationNumber(), Society_.registrationNumber));
            }
            if (criteria.getGstinNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGstinNumber(), Society_.gstinNumber));
            }
            if (criteria.getPanNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPanNumber(), Society_.panNumber));
            }
            if (criteria.getTanNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTanNumber(), Society_.tanNumber));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPhoneNumber(), Society_.phoneNumber));
            }
            if (criteria.getEmailAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailAddress(), Society_.emailAddress));
            }
            if (criteria.getPinCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPinCode(), Society_.pinCode));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), Society_.createdOn));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Society_.createdBy));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Society_.description));
            }
            if (criteria.getIsActivate() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActivate(), Society_.isActivate));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Society_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Society_.lastModifiedBy));
            }
            if (criteria.getSocietyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSocietyId(), root -> root.join(Society_.society, JoinType.LEFT).get(Society_.id))
                    );
            }
        }
        return specification;
    }
}

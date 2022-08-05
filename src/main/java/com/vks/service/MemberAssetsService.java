package com.vks.service;

import com.vks.domain.MemberAssets;
import com.vks.repository.MemberAssetsRepository;
import com.vks.service.dto.MemberAssetsDTO;
import com.vks.service.mapper.MemberAssetsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MemberAssets}.
 */
@Service
@Transactional
public class MemberAssetsService {

    private final Logger log = LoggerFactory.getLogger(MemberAssetsService.class);

    private final MemberAssetsRepository memberAssetsRepository;

    private final MemberAssetsMapper memberAssetsMapper;

    public MemberAssetsService(MemberAssetsRepository memberAssetsRepository, MemberAssetsMapper memberAssetsMapper) {
        this.memberAssetsRepository = memberAssetsRepository;
        this.memberAssetsMapper = memberAssetsMapper;
    }

    /**
     * Save a memberAssets.
     *
     * @param memberAssetsDTO the entity to save.
     * @return the persisted entity.
     */
    public MemberAssetsDTO save(MemberAssetsDTO memberAssetsDTO) {
        log.debug("Request to save MemberAssets : {}", memberAssetsDTO);
        MemberAssets memberAssets = memberAssetsMapper.toEntity(memberAssetsDTO);
        memberAssets = memberAssetsRepository.save(memberAssets);
        return memberAssetsMapper.toDto(memberAssets);
    }

    /**
     * Update a memberAssets.
     *
     * @param memberAssetsDTO the entity to save.
     * @return the persisted entity.
     */
    public MemberAssetsDTO update(MemberAssetsDTO memberAssetsDTO) {
        log.debug("Request to save MemberAssets : {}", memberAssetsDTO);
        MemberAssets memberAssets = memberAssetsMapper.toEntity(memberAssetsDTO);
        memberAssets = memberAssetsRepository.save(memberAssets);
        return memberAssetsMapper.toDto(memberAssets);
    }

    /**
     * Partially update a memberAssets.
     *
     * @param memberAssetsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MemberAssetsDTO> partialUpdate(MemberAssetsDTO memberAssetsDTO) {
        log.debug("Request to partially update MemberAssets : {}", memberAssetsDTO);

        return memberAssetsRepository
            .findById(memberAssetsDTO.getId())
            .map(existingMemberAssets -> {
                memberAssetsMapper.partialUpdate(existingMemberAssets, memberAssetsDTO);

                return existingMemberAssets;
            })
            .map(memberAssetsRepository::save)
            .map(memberAssetsMapper::toDto);
    }

    /**
     * Get all the memberAssets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MemberAssetsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MemberAssets");
        return memberAssetsRepository.findAll(pageable).map(memberAssetsMapper::toDto);
    }

    /**
     * Get one memberAssets by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MemberAssetsDTO> findOne(Long id) {
        log.debug("Request to get MemberAssets : {}", id);
        return memberAssetsRepository.findById(id).map(memberAssetsMapper::toDto);
    }

    /**
     * Delete the memberAssets by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MemberAssets : {}", id);
        memberAssetsRepository.deleteById(id);
    }
}

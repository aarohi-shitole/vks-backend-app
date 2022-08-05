package com.vks.service;

import com.vks.domain.MemberLandAssets;
import com.vks.repository.MemberLandAssetsRepository;
import com.vks.service.dto.MemberLandAssetsDTO;
import com.vks.service.mapper.MemberLandAssetsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MemberLandAssets}.
 */
@Service
@Transactional
public class MemberLandAssetsService {

    private final Logger log = LoggerFactory.getLogger(MemberLandAssetsService.class);

    private final MemberLandAssetsRepository memberLandAssetsRepository;

    private final MemberLandAssetsMapper memberLandAssetsMapper;

    public MemberLandAssetsService(MemberLandAssetsRepository memberLandAssetsRepository, MemberLandAssetsMapper memberLandAssetsMapper) {
        this.memberLandAssetsRepository = memberLandAssetsRepository;
        this.memberLandAssetsMapper = memberLandAssetsMapper;
    }

    /**
     * Save a memberLandAssets.
     *
     * @param memberLandAssetsDTO the entity to save.
     * @return the persisted entity.
     */
    public MemberLandAssetsDTO save(MemberLandAssetsDTO memberLandAssetsDTO) {
        log.debug("Request to save MemberLandAssets : {}", memberLandAssetsDTO);
        MemberLandAssets memberLandAssets = memberLandAssetsMapper.toEntity(memberLandAssetsDTO);
        memberLandAssets = memberLandAssetsRepository.save(memberLandAssets);
        return memberLandAssetsMapper.toDto(memberLandAssets);
    }

    /**
     * Update a memberLandAssets.
     *
     * @param memberLandAssetsDTO the entity to save.
     * @return the persisted entity.
     */
    public MemberLandAssetsDTO update(MemberLandAssetsDTO memberLandAssetsDTO) {
        log.debug("Request to save MemberLandAssets : {}", memberLandAssetsDTO);
        MemberLandAssets memberLandAssets = memberLandAssetsMapper.toEntity(memberLandAssetsDTO);
        memberLandAssets = memberLandAssetsRepository.save(memberLandAssets);
        return memberLandAssetsMapper.toDto(memberLandAssets);
    }

    /**
     * Partially update a memberLandAssets.
     *
     * @param memberLandAssetsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MemberLandAssetsDTO> partialUpdate(MemberLandAssetsDTO memberLandAssetsDTO) {
        log.debug("Request to partially update MemberLandAssets : {}", memberLandAssetsDTO);

        return memberLandAssetsRepository
            .findById(memberLandAssetsDTO.getId())
            .map(existingMemberLandAssets -> {
                memberLandAssetsMapper.partialUpdate(existingMemberLandAssets, memberLandAssetsDTO);

                return existingMemberLandAssets;
            })
            .map(memberLandAssetsRepository::save)
            .map(memberLandAssetsMapper::toDto);
    }

    /**
     * Get all the memberLandAssets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MemberLandAssetsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MemberLandAssets");
        return memberLandAssetsRepository.findAll(pageable).map(memberLandAssetsMapper::toDto);
    }

    /**
     * Get one memberLandAssets by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MemberLandAssetsDTO> findOne(Long id) {
        log.debug("Request to get MemberLandAssets : {}", id);
        return memberLandAssetsRepository.findById(id).map(memberLandAssetsMapper::toDto);
    }

    /**
     * Delete the memberLandAssets by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MemberLandAssets : {}", id);
        memberLandAssetsRepository.deleteById(id);
    }
}

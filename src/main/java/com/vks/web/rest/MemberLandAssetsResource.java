package com.vks.web.rest;

import com.vks.repository.MemberLandAssetsRepository;
import com.vks.service.MemberLandAssetsQueryService;
import com.vks.service.MemberLandAssetsService;
import com.vks.service.criteria.MemberLandAssetsCriteria;
import com.vks.service.dto.MemberLandAssetsDTO;
import com.vks.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.vks.domain.MemberLandAssets}.
 */
@RestController
@RequestMapping("/api")
public class MemberLandAssetsResource {

    private final Logger log = LoggerFactory.getLogger(MemberLandAssetsResource.class);

    private static final String ENTITY_NAME = "memberLandAssets";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MemberLandAssetsService memberLandAssetsService;

    private final MemberLandAssetsRepository memberLandAssetsRepository;

    private final MemberLandAssetsQueryService memberLandAssetsQueryService;

    public MemberLandAssetsResource(
        MemberLandAssetsService memberLandAssetsService,
        MemberLandAssetsRepository memberLandAssetsRepository,
        MemberLandAssetsQueryService memberLandAssetsQueryService
    ) {
        this.memberLandAssetsService = memberLandAssetsService;
        this.memberLandAssetsRepository = memberLandAssetsRepository;
        this.memberLandAssetsQueryService = memberLandAssetsQueryService;
    }

    /**
     * {@code POST  /member-land-assets} : Create a new memberLandAssets.
     *
     * @param memberLandAssetsDTO the memberLandAssetsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new memberLandAssetsDTO, or with status {@code 400 (Bad Request)} if the memberLandAssets has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/member-land-assets")
    public ResponseEntity<MemberLandAssetsDTO> createMemberLandAssets(@RequestBody MemberLandAssetsDTO memberLandAssetsDTO)
        throws URISyntaxException {
        log.debug("REST request to save MemberLandAssets : {}", memberLandAssetsDTO);
        if (memberLandAssetsDTO.getId() != null) {
            throw new BadRequestAlertException("A new memberLandAssets cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MemberLandAssetsDTO result = memberLandAssetsService.save(memberLandAssetsDTO);
        return ResponseEntity
            .created(new URI("/api/member-land-assets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /member-land-assets/:id} : Updates an existing memberLandAssets.
     *
     * @param id the id of the memberLandAssetsDTO to save.
     * @param memberLandAssetsDTO the memberLandAssetsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated memberLandAssetsDTO,
     * or with status {@code 400 (Bad Request)} if the memberLandAssetsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the memberLandAssetsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/member-land-assets/{id}")
    public ResponseEntity<MemberLandAssetsDTO> updateMemberLandAssets(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MemberLandAssetsDTO memberLandAssetsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MemberLandAssets : {}, {}", id, memberLandAssetsDTO);
        if (memberLandAssetsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, memberLandAssetsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memberLandAssetsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MemberLandAssetsDTO result = memberLandAssetsService.update(memberLandAssetsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, memberLandAssetsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /member-land-assets/:id} : Partial updates given fields of an existing memberLandAssets, field will ignore if it is null
     *
     * @param id the id of the memberLandAssetsDTO to save.
     * @param memberLandAssetsDTO the memberLandAssetsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated memberLandAssetsDTO,
     * or with status {@code 400 (Bad Request)} if the memberLandAssetsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the memberLandAssetsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the memberLandAssetsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/member-land-assets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MemberLandAssetsDTO> partialUpdateMemberLandAssets(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MemberLandAssetsDTO memberLandAssetsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MemberLandAssets partially : {}, {}", id, memberLandAssetsDTO);
        if (memberLandAssetsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, memberLandAssetsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memberLandAssetsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MemberLandAssetsDTO> result = memberLandAssetsService.partialUpdate(memberLandAssetsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, memberLandAssetsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /member-land-assets} : get all the memberLandAssets.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of memberLandAssets in body.
     */
    @GetMapping("/member-land-assets")
    public ResponseEntity<List<MemberLandAssetsDTO>> getAllMemberLandAssets(
        MemberLandAssetsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MemberLandAssets by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<MemberLandAssetsDTO> page = memberLandAssetsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /member-land-assets/count} : count all the memberLandAssets.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/member-land-assets/count")
    public ResponseEntity<Long> countMemberLandAssets(MemberLandAssetsCriteria criteria) {
        log.debug("REST request to count MemberLandAssets by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(memberLandAssetsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /member-land-assets/:id} : get the "id" memberLandAssets.
     *
     * @param id the id of the memberLandAssetsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the memberLandAssetsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/member-land-assets/{id}")
    public ResponseEntity<MemberLandAssetsDTO> getMemberLandAssets(@PathVariable Long id) {
        log.debug("REST request to get MemberLandAssets : {}", id);
        Optional<MemberLandAssetsDTO> memberLandAssetsDTO = memberLandAssetsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(memberLandAssetsDTO);
    }

    /**
     * {@code DELETE  /member-land-assets/:id} : delete the "id" memberLandAssets.
     *
     * @param id the id of the memberLandAssetsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/member-land-assets/{id}")
    public ResponseEntity<Void> deleteMemberLandAssets(@PathVariable Long id) {
        log.debug("REST request to delete MemberLandAssets : {}", id);
        memberLandAssetsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

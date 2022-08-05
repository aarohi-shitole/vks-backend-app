package com.vks.web.rest;

import com.vks.repository.MemberBankRepository;
import com.vks.service.MemberBankQueryService;
import com.vks.service.MemberBankService;
import com.vks.service.criteria.MemberBankCriteria;
import com.vks.service.dto.MemberBankDTO;
import com.vks.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.vks.domain.MemberBank}.
 */
@RestController
@RequestMapping("/api")
public class MemberBankResource {

    private final Logger log = LoggerFactory.getLogger(MemberBankResource.class);

    private static final String ENTITY_NAME = "memberBank";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MemberBankService memberBankService;

    private final MemberBankRepository memberBankRepository;

    private final MemberBankQueryService memberBankQueryService;

    public MemberBankResource(
        MemberBankService memberBankService,
        MemberBankRepository memberBankRepository,
        MemberBankQueryService memberBankQueryService
    ) {
        this.memberBankService = memberBankService;
        this.memberBankRepository = memberBankRepository;
        this.memberBankQueryService = memberBankQueryService;
    }

    /**
     * {@code POST  /member-banks} : Create a new memberBank.
     *
     * @param memberBankDTO the memberBankDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new memberBankDTO, or with status {@code 400 (Bad Request)} if the memberBank has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/member-banks")
    public ResponseEntity<MemberBankDTO> createMemberBank(@Valid @RequestBody MemberBankDTO memberBankDTO) throws URISyntaxException {
        log.debug("REST request to save MemberBank : {}", memberBankDTO);
        if (memberBankDTO.getId() != null) {
            throw new BadRequestAlertException("A new memberBank cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MemberBankDTO result = memberBankService.save(memberBankDTO);
        return ResponseEntity
            .created(new URI("/api/member-banks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /member-banks/:id} : Updates an existing memberBank.
     *
     * @param id the id of the memberBankDTO to save.
     * @param memberBankDTO the memberBankDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated memberBankDTO,
     * or with status {@code 400 (Bad Request)} if the memberBankDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the memberBankDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/member-banks/{id}")
    public ResponseEntity<MemberBankDTO> updateMemberBank(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MemberBankDTO memberBankDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MemberBank : {}, {}", id, memberBankDTO);
        if (memberBankDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, memberBankDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memberBankRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MemberBankDTO result = memberBankService.update(memberBankDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, memberBankDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /member-banks/:id} : Partial updates given fields of an existing memberBank, field will ignore if it is null
     *
     * @param id the id of the memberBankDTO to save.
     * @param memberBankDTO the memberBankDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated memberBankDTO,
     * or with status {@code 400 (Bad Request)} if the memberBankDTO is not valid,
     * or with status {@code 404 (Not Found)} if the memberBankDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the memberBankDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/member-banks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MemberBankDTO> partialUpdateMemberBank(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MemberBankDTO memberBankDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MemberBank partially : {}, {}", id, memberBankDTO);
        if (memberBankDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, memberBankDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memberBankRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MemberBankDTO> result = memberBankService.partialUpdate(memberBankDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, memberBankDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /member-banks} : get all the memberBanks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of memberBanks in body.
     */
    @GetMapping("/member-banks")
    public ResponseEntity<List<MemberBankDTO>> getAllMemberBanks(
        MemberBankCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MemberBanks by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<MemberBankDTO> page = memberBankQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /member-banks/count} : count all the memberBanks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/member-banks/count")
    public ResponseEntity<Long> countMemberBanks(MemberBankCriteria criteria) {
        log.debug("REST request to count MemberBanks by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(memberBankQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /member-banks/:id} : get the "id" memberBank.
     *
     * @param id the id of the memberBankDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the memberBankDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/member-banks/{id}")
    public ResponseEntity<MemberBankDTO> getMemberBank(@PathVariable Long id) {
        log.debug("REST request to get MemberBank : {}", id);
        Optional<MemberBankDTO> memberBankDTO = memberBankService.findOne(id);
        return ResponseUtil.wrapOrNotFound(memberBankDTO);
    }

    /**
     * {@code DELETE  /member-banks/:id} : delete the "id" memberBank.
     *
     * @param id the id of the memberBankDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/member-banks/{id}")
    public ResponseEntity<Void> deleteMemberBank(@PathVariable Long id) {
        log.debug("REST request to delete MemberBank : {}", id);
        memberBankService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

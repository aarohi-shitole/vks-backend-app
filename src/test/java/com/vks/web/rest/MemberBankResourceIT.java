package com.vks.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vks.IntegrationTest;
import com.vks.domain.Member;
import com.vks.domain.MemberBank;
import com.vks.repository.MemberBankRepository;
import com.vks.service.criteria.MemberBankCriteria;
import com.vks.service.dto.MemberBankDTO;
import com.vks.service.mapper.MemberBankMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MemberBankResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MemberBankResourceIT {

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_ACCOUNT_NUMBER = 1L;
    private static final Long UPDATED_ACCOUNT_NUMBER = 2L;
    private static final Long SMALLER_ACCOUNT_NUMBER = 1L - 1L;

    private static final String DEFAULT_IFSCCODE = "AAAAAAAAAA";
    private static final String UPDATED_IFSCCODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final String ENTITY_API_URL = "/api/member-banks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MemberBankRepository memberBankRepository;

    @Autowired
    private MemberBankMapper memberBankMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMemberBankMockMvc;

    private MemberBank memberBank;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemberBank createEntity(EntityManager em) {
        MemberBank memberBank = new MemberBank()
            .bankName(DEFAULT_BANK_NAME)
            .branchName(DEFAULT_BRANCH_NAME)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .ifsccode(DEFAULT_IFSCCODE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED);
        return memberBank;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemberBank createUpdatedEntity(EntityManager em) {
        MemberBank memberBank = new MemberBank()
            .bankName(UPDATED_BANK_NAME)
            .branchName(UPDATED_BRANCH_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .ifsccode(UPDATED_IFSCCODE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED);
        return memberBank;
    }

    @BeforeEach
    public void initTest() {
        memberBank = createEntity(em);
    }

    @Test
    @Transactional
    void createMemberBank() throws Exception {
        int databaseSizeBeforeCreate = memberBankRepository.findAll().size();
        // Create the MemberBank
        MemberBankDTO memberBankDTO = memberBankMapper.toDto(memberBank);
        restMemberBankMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberBankDTO)))
            .andExpect(status().isCreated());

        // Validate the MemberBank in the database
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeCreate + 1);
        MemberBank testMemberBank = memberBankList.get(memberBankList.size() - 1);
        assertThat(testMemberBank.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testMemberBank.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testMemberBank.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testMemberBank.getIfsccode()).isEqualTo(DEFAULT_IFSCCODE);
        assertThat(testMemberBank.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testMemberBank.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testMemberBank.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMemberBank.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testMemberBank.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void createMemberBankWithExistingId() throws Exception {
        // Create the MemberBank with an existing ID
        memberBank.setId(1L);
        MemberBankDTO memberBankDTO = memberBankMapper.toDto(memberBank);

        int databaseSizeBeforeCreate = memberBankRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberBankMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberBankDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MemberBank in the database
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMemberBanks() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList
        restMemberBankMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberBank.getId().intValue())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].ifsccode").value(hasItem(DEFAULT_IFSCCODE)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getMemberBank() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get the memberBank
        restMemberBankMockMvc
            .perform(get(ENTITY_API_URL_ID, memberBank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(memberBank.getId().intValue()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.intValue()))
            .andExpect(jsonPath("$.ifsccode").value(DEFAULT_IFSCCODE))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getMemberBanksByIdFiltering() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        Long id = memberBank.getId();

        defaultMemberBankShouldBeFound("id.equals=" + id);
        defaultMemberBankShouldNotBeFound("id.notEquals=" + id);

        defaultMemberBankShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMemberBankShouldNotBeFound("id.greaterThan=" + id);

        defaultMemberBankShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMemberBankShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMemberBanksByBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where bankName equals to DEFAULT_BANK_NAME
        defaultMemberBankShouldBeFound("bankName.equals=" + DEFAULT_BANK_NAME);

        // Get all the memberBankList where bankName equals to UPDATED_BANK_NAME
        defaultMemberBankShouldNotBeFound("bankName.equals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllMemberBanksByBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where bankName in DEFAULT_BANK_NAME or UPDATED_BANK_NAME
        defaultMemberBankShouldBeFound("bankName.in=" + DEFAULT_BANK_NAME + "," + UPDATED_BANK_NAME);

        // Get all the memberBankList where bankName equals to UPDATED_BANK_NAME
        defaultMemberBankShouldNotBeFound("bankName.in=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllMemberBanksByBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where bankName is not null
        defaultMemberBankShouldBeFound("bankName.specified=true");

        // Get all the memberBankList where bankName is null
        defaultMemberBankShouldNotBeFound("bankName.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByBankNameContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where bankName contains DEFAULT_BANK_NAME
        defaultMemberBankShouldBeFound("bankName.contains=" + DEFAULT_BANK_NAME);

        // Get all the memberBankList where bankName contains UPDATED_BANK_NAME
        defaultMemberBankShouldNotBeFound("bankName.contains=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllMemberBanksByBankNameNotContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where bankName does not contain DEFAULT_BANK_NAME
        defaultMemberBankShouldNotBeFound("bankName.doesNotContain=" + DEFAULT_BANK_NAME);

        // Get all the memberBankList where bankName does not contain UPDATED_BANK_NAME
        defaultMemberBankShouldBeFound("bankName.doesNotContain=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllMemberBanksByBranchNameIsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where branchName equals to DEFAULT_BRANCH_NAME
        defaultMemberBankShouldBeFound("branchName.equals=" + DEFAULT_BRANCH_NAME);

        // Get all the memberBankList where branchName equals to UPDATED_BRANCH_NAME
        defaultMemberBankShouldNotBeFound("branchName.equals=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllMemberBanksByBranchNameIsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where branchName in DEFAULT_BRANCH_NAME or UPDATED_BRANCH_NAME
        defaultMemberBankShouldBeFound("branchName.in=" + DEFAULT_BRANCH_NAME + "," + UPDATED_BRANCH_NAME);

        // Get all the memberBankList where branchName equals to UPDATED_BRANCH_NAME
        defaultMemberBankShouldNotBeFound("branchName.in=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllMemberBanksByBranchNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where branchName is not null
        defaultMemberBankShouldBeFound("branchName.specified=true");

        // Get all the memberBankList where branchName is null
        defaultMemberBankShouldNotBeFound("branchName.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByBranchNameContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where branchName contains DEFAULT_BRANCH_NAME
        defaultMemberBankShouldBeFound("branchName.contains=" + DEFAULT_BRANCH_NAME);

        // Get all the memberBankList where branchName contains UPDATED_BRANCH_NAME
        defaultMemberBankShouldNotBeFound("branchName.contains=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllMemberBanksByBranchNameNotContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where branchName does not contain DEFAULT_BRANCH_NAME
        defaultMemberBankShouldNotBeFound("branchName.doesNotContain=" + DEFAULT_BRANCH_NAME);

        // Get all the memberBankList where branchName does not contain UPDATED_BRANCH_NAME
        defaultMemberBankShouldBeFound("branchName.doesNotContain=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllMemberBanksByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultMemberBankShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the memberBankList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultMemberBankShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllMemberBanksByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultMemberBankShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the memberBankList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultMemberBankShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllMemberBanksByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where accountNumber is not null
        defaultMemberBankShouldBeFound("accountNumber.specified=true");

        // Get all the memberBankList where accountNumber is null
        defaultMemberBankShouldNotBeFound("accountNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByAccountNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where accountNumber is greater than or equal to DEFAULT_ACCOUNT_NUMBER
        defaultMemberBankShouldBeFound("accountNumber.greaterThanOrEqual=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the memberBankList where accountNumber is greater than or equal to UPDATED_ACCOUNT_NUMBER
        defaultMemberBankShouldNotBeFound("accountNumber.greaterThanOrEqual=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllMemberBanksByAccountNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where accountNumber is less than or equal to DEFAULT_ACCOUNT_NUMBER
        defaultMemberBankShouldBeFound("accountNumber.lessThanOrEqual=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the memberBankList where accountNumber is less than or equal to SMALLER_ACCOUNT_NUMBER
        defaultMemberBankShouldNotBeFound("accountNumber.lessThanOrEqual=" + SMALLER_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllMemberBanksByAccountNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where accountNumber is less than DEFAULT_ACCOUNT_NUMBER
        defaultMemberBankShouldNotBeFound("accountNumber.lessThan=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the memberBankList where accountNumber is less than UPDATED_ACCOUNT_NUMBER
        defaultMemberBankShouldBeFound("accountNumber.lessThan=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllMemberBanksByAccountNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where accountNumber is greater than DEFAULT_ACCOUNT_NUMBER
        defaultMemberBankShouldNotBeFound("accountNumber.greaterThan=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the memberBankList where accountNumber is greater than SMALLER_ACCOUNT_NUMBER
        defaultMemberBankShouldBeFound("accountNumber.greaterThan=" + SMALLER_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllMemberBanksByIfsccodeIsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where ifsccode equals to DEFAULT_IFSCCODE
        defaultMemberBankShouldBeFound("ifsccode.equals=" + DEFAULT_IFSCCODE);

        // Get all the memberBankList where ifsccode equals to UPDATED_IFSCCODE
        defaultMemberBankShouldNotBeFound("ifsccode.equals=" + UPDATED_IFSCCODE);
    }

    @Test
    @Transactional
    void getAllMemberBanksByIfsccodeIsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where ifsccode in DEFAULT_IFSCCODE or UPDATED_IFSCCODE
        defaultMemberBankShouldBeFound("ifsccode.in=" + DEFAULT_IFSCCODE + "," + UPDATED_IFSCCODE);

        // Get all the memberBankList where ifsccode equals to UPDATED_IFSCCODE
        defaultMemberBankShouldNotBeFound("ifsccode.in=" + UPDATED_IFSCCODE);
    }

    @Test
    @Transactional
    void getAllMemberBanksByIfsccodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where ifsccode is not null
        defaultMemberBankShouldBeFound("ifsccode.specified=true");

        // Get all the memberBankList where ifsccode is null
        defaultMemberBankShouldNotBeFound("ifsccode.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByIfsccodeContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where ifsccode contains DEFAULT_IFSCCODE
        defaultMemberBankShouldBeFound("ifsccode.contains=" + DEFAULT_IFSCCODE);

        // Get all the memberBankList where ifsccode contains UPDATED_IFSCCODE
        defaultMemberBankShouldNotBeFound("ifsccode.contains=" + UPDATED_IFSCCODE);
    }

    @Test
    @Transactional
    void getAllMemberBanksByIfsccodeNotContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where ifsccode does not contain DEFAULT_IFSCCODE
        defaultMemberBankShouldNotBeFound("ifsccode.doesNotContain=" + DEFAULT_IFSCCODE);

        // Get all the memberBankList where ifsccode does not contain UPDATED_IFSCCODE
        defaultMemberBankShouldBeFound("ifsccode.doesNotContain=" + UPDATED_IFSCCODE);
    }

    @Test
    @Transactional
    void getAllMemberBanksByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultMemberBankShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the memberBankList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMemberBankShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMemberBanksByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultMemberBankShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the memberBankList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMemberBankShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMemberBanksByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where lastModified is not null
        defaultMemberBankShouldBeFound("lastModified.specified=true");

        // Get all the memberBankList where lastModified is null
        defaultMemberBankShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultMemberBankShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberBankList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMemberBankShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberBanksByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultMemberBankShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the memberBankList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMemberBankShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberBanksByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where lastModifiedBy is not null
        defaultMemberBankShouldBeFound("lastModifiedBy.specified=true");

        // Get all the memberBankList where lastModifiedBy is null
        defaultMemberBankShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultMemberBankShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberBankList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultMemberBankShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberBanksByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultMemberBankShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberBankList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultMemberBankShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberBanksByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where createdBy equals to DEFAULT_CREATED_BY
        defaultMemberBankShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the memberBankList where createdBy equals to UPDATED_CREATED_BY
        defaultMemberBankShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberBanksByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultMemberBankShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the memberBankList where createdBy equals to UPDATED_CREATED_BY
        defaultMemberBankShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberBanksByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where createdBy is not null
        defaultMemberBankShouldBeFound("createdBy.specified=true");

        // Get all the memberBankList where createdBy is null
        defaultMemberBankShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where createdBy contains DEFAULT_CREATED_BY
        defaultMemberBankShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the memberBankList where createdBy contains UPDATED_CREATED_BY
        defaultMemberBankShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberBanksByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where createdBy does not contain DEFAULT_CREATED_BY
        defaultMemberBankShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the memberBankList where createdBy does not contain UPDATED_CREATED_BY
        defaultMemberBankShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberBanksByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where createdOn equals to DEFAULT_CREATED_ON
        defaultMemberBankShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the memberBankList where createdOn equals to UPDATED_CREATED_ON
        defaultMemberBankShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllMemberBanksByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultMemberBankShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the memberBankList where createdOn equals to UPDATED_CREATED_ON
        defaultMemberBankShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllMemberBanksByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where createdOn is not null
        defaultMemberBankShouldBeFound("createdOn.specified=true");

        // Get all the memberBankList where createdOn is null
        defaultMemberBankShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where isDeleted equals to DEFAULT_IS_DELETED
        defaultMemberBankShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the memberBankList where isDeleted equals to UPDATED_IS_DELETED
        defaultMemberBankShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllMemberBanksByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultMemberBankShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the memberBankList where isDeleted equals to UPDATED_IS_DELETED
        defaultMemberBankShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllMemberBanksByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where isDeleted is not null
        defaultMemberBankShouldBeFound("isDeleted.specified=true");

        // Get all the memberBankList where isDeleted is null
        defaultMemberBankShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByMemberIsEqualToSomething() throws Exception {
        Member member;
        if (TestUtil.findAll(em, Member.class).isEmpty()) {
            memberBankRepository.saveAndFlush(memberBank);
            member = MemberResourceIT.createEntity(em);
        } else {
            member = TestUtil.findAll(em, Member.class).get(0);
        }
        em.persist(member);
        em.flush();
        memberBank.setMember(member);
        member.setMemberBank(memberBank);
        memberBankRepository.saveAndFlush(memberBank);
        Long memberId = member.getId();

        // Get all the memberBankList where member equals to memberId
        defaultMemberBankShouldBeFound("memberId.equals=" + memberId);

        // Get all the memberBankList where member equals to (memberId + 1)
        defaultMemberBankShouldNotBeFound("memberId.equals=" + (memberId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMemberBankShouldBeFound(String filter) throws Exception {
        restMemberBankMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberBank.getId().intValue())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].ifsccode").value(hasItem(DEFAULT_IFSCCODE)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())));

        // Check, that the count call also returns 1
        restMemberBankMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMemberBankShouldNotBeFound(String filter) throws Exception {
        restMemberBankMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMemberBankMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMemberBank() throws Exception {
        // Get the memberBank
        restMemberBankMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMemberBank() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        int databaseSizeBeforeUpdate = memberBankRepository.findAll().size();

        // Update the memberBank
        MemberBank updatedMemberBank = memberBankRepository.findById(memberBank.getId()).get();
        // Disconnect from session so that the updates on updatedMemberBank are not directly saved in db
        em.detach(updatedMemberBank);
        updatedMemberBank
            .bankName(UPDATED_BANK_NAME)
            .branchName(UPDATED_BRANCH_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .ifsccode(UPDATED_IFSCCODE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED);
        MemberBankDTO memberBankDTO = memberBankMapper.toDto(updatedMemberBank);

        restMemberBankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberBankDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberBankDTO))
            )
            .andExpect(status().isOk());

        // Validate the MemberBank in the database
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeUpdate);
        MemberBank testMemberBank = memberBankList.get(memberBankList.size() - 1);
        assertThat(testMemberBank.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testMemberBank.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testMemberBank.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testMemberBank.getIfsccode()).isEqualTo(UPDATED_IFSCCODE);
        assertThat(testMemberBank.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMemberBank.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMemberBank.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMemberBank.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testMemberBank.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingMemberBank() throws Exception {
        int databaseSizeBeforeUpdate = memberBankRepository.findAll().size();
        memberBank.setId(count.incrementAndGet());

        // Create the MemberBank
        MemberBankDTO memberBankDTO = memberBankMapper.toDto(memberBank);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberBankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberBankDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberBankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberBank in the database
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMemberBank() throws Exception {
        int databaseSizeBeforeUpdate = memberBankRepository.findAll().size();
        memberBank.setId(count.incrementAndGet());

        // Create the MemberBank
        MemberBankDTO memberBankDTO = memberBankMapper.toDto(memberBank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberBankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberBankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberBank in the database
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMemberBank() throws Exception {
        int databaseSizeBeforeUpdate = memberBankRepository.findAll().size();
        memberBank.setId(count.incrementAndGet());

        // Create the MemberBank
        MemberBankDTO memberBankDTO = memberBankMapper.toDto(memberBank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberBankMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberBankDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MemberBank in the database
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMemberBankWithPatch() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        int databaseSizeBeforeUpdate = memberBankRepository.findAll().size();

        // Update the memberBank using partial update
        MemberBank partialUpdatedMemberBank = new MemberBank();
        partialUpdatedMemberBank.setId(memberBank.getId());

        partialUpdatedMemberBank
            .bankName(UPDATED_BANK_NAME)
            .branchName(UPDATED_BRANCH_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .ifsccode(UPDATED_IFSCCODE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .isDeleted(UPDATED_IS_DELETED);

        restMemberBankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMemberBank.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMemberBank))
            )
            .andExpect(status().isOk());

        // Validate the MemberBank in the database
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeUpdate);
        MemberBank testMemberBank = memberBankList.get(memberBankList.size() - 1);
        assertThat(testMemberBank.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testMemberBank.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testMemberBank.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testMemberBank.getIfsccode()).isEqualTo(UPDATED_IFSCCODE);
        assertThat(testMemberBank.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMemberBank.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMemberBank.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMemberBank.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testMemberBank.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void fullUpdateMemberBankWithPatch() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        int databaseSizeBeforeUpdate = memberBankRepository.findAll().size();

        // Update the memberBank using partial update
        MemberBank partialUpdatedMemberBank = new MemberBank();
        partialUpdatedMemberBank.setId(memberBank.getId());

        partialUpdatedMemberBank
            .bankName(UPDATED_BANK_NAME)
            .branchName(UPDATED_BRANCH_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .ifsccode(UPDATED_IFSCCODE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED);

        restMemberBankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMemberBank.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMemberBank))
            )
            .andExpect(status().isOk());

        // Validate the MemberBank in the database
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeUpdate);
        MemberBank testMemberBank = memberBankList.get(memberBankList.size() - 1);
        assertThat(testMemberBank.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testMemberBank.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testMemberBank.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testMemberBank.getIfsccode()).isEqualTo(UPDATED_IFSCCODE);
        assertThat(testMemberBank.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMemberBank.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMemberBank.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMemberBank.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testMemberBank.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingMemberBank() throws Exception {
        int databaseSizeBeforeUpdate = memberBankRepository.findAll().size();
        memberBank.setId(count.incrementAndGet());

        // Create the MemberBank
        MemberBankDTO memberBankDTO = memberBankMapper.toDto(memberBank);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberBankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, memberBankDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberBankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberBank in the database
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMemberBank() throws Exception {
        int databaseSizeBeforeUpdate = memberBankRepository.findAll().size();
        memberBank.setId(count.incrementAndGet());

        // Create the MemberBank
        MemberBankDTO memberBankDTO = memberBankMapper.toDto(memberBank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberBankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberBankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberBank in the database
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMemberBank() throws Exception {
        int databaseSizeBeforeUpdate = memberBankRepository.findAll().size();
        memberBank.setId(count.incrementAndGet());

        // Create the MemberBank
        MemberBankDTO memberBankDTO = memberBankMapper.toDto(memberBank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberBankMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(memberBankDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MemberBank in the database
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMemberBank() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        int databaseSizeBeforeDelete = memberBankRepository.findAll().size();

        // Delete the memberBank
        restMemberBankMockMvc
            .perform(delete(ENTITY_API_URL_ID, memberBank.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

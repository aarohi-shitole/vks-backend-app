package com.vks.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vks.IntegrationTest;
import com.vks.domain.Member;
import com.vks.domain.MemberBank;
import com.vks.domain.Society;
import com.vks.domain.enumeration.Gender;
import com.vks.domain.enumeration.LoanStatus;
import com.vks.domain.enumeration.Status;
import com.vks.repository.MemberRepository;
import com.vks.service.criteria.MemberCriteria;
import com.vks.service.dto.MemberDTO;
import com.vks.service.mapper.MemberMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link MemberResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MemberResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MEMBER_UNIQUE_ID = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_UNIQUE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FATHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FATHER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MOTHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MOTHER_NAME = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DOB = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_RELIGION = "AAAAAAAAAA";
    private static final String UPDATED_RELIGION = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_CAST = "AAAAAAAAAA";
    private static final String UPDATED_CAST = "BBBBBBBBBB";

    private static final String DEFAULT_AADHAR_CARD = "AAAAAAAAAA";
    private static final String UPDATED_AADHAR_CARD = "BBBBBBBBBB";

    private static final String DEFAULT_PAN_CARD = "AAAAAAAAAA";
    private static final String UPDATED_PAN_CARD = "BBBBBBBBBB";

    private static final String DEFAULT_RATION_CARD = "AAAAAAAAAA";
    private static final String UPDATED_RATION_CARD = "BBBBBBBBBB";

    private static final Long DEFAULT_FAMILY_MEMBER_COUNT = 1L;
    private static final Long UPDATED_FAMILY_MEMBER_COUNT = 2L;
    private static final Long SMALLER_FAMILY_MEMBER_COUNT = 1L - 1L;

    private static final String DEFAULT_OCCUPATION = "AAAAAAAAAA";
    private static final String UPDATED_OCCUPATION = "BBBBBBBBBB";

    private static final Instant DEFAULT_APPLICATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPLICATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Status DEFAULT_STATUS = Status.CREATED;
    private static final Status UPDATED_STATUS = Status.DOCUMENT_VERIFIED;

    private static final Boolean DEFAULT_KMP_STATUS = false;
    private static final Boolean UPDATED_KMP_STATUS = true;

    private static final String DEFAULT_BOARD_RESOLUTION_NO = "AAAAAAAAAA";
    private static final String UPDATED_BOARD_RESOLUTION_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BOARD_RESOLUTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BOARD_RESOLUTION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_BOARD_RESOLUTION_DATE = LocalDate.ofEpochDay(-1L);

    private static final LoanStatus DEFAULT_LOAN_STATUS = LoanStatus.APPLIED;
    private static final LoanStatus UPDATED_LOAN_STATUS = LoanStatus.PENDING;

    private static final String DEFAULT_MEMBER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/members";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMemberMockMvc;

    private Member member;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Member createEntity(EntityManager em) {
        Member member = new Member()
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .memberUniqueId(DEFAULT_MEMBER_UNIQUE_ID)
            .fatherName(DEFAULT_FATHER_NAME)
            .motherName(DEFAULT_MOTHER_NAME)
            .gender(DEFAULT_GENDER)
            .dob(DEFAULT_DOB)
            .email(DEFAULT_EMAIL)
            .mobileNo(DEFAULT_MOBILE_NO)
            .religion(DEFAULT_RELIGION)
            .category(DEFAULT_CATEGORY)
            .cast(DEFAULT_CAST)
            .aadharCard(DEFAULT_AADHAR_CARD)
            .panCard(DEFAULT_PAN_CARD)
            .rationCard(DEFAULT_RATION_CARD)
            .familyMemberCount(DEFAULT_FAMILY_MEMBER_COUNT)
            .occupation(DEFAULT_OCCUPATION)
            .applicationDate(DEFAULT_APPLICATION_DATE)
            .status(DEFAULT_STATUS)
            .kmpStatus(DEFAULT_KMP_STATUS)
            .boardResolutionNo(DEFAULT_BOARD_RESOLUTION_NO)
            .boardResolutionDate(DEFAULT_BOARD_RESOLUTION_DATE)
            .loanStatus(DEFAULT_LOAN_STATUS)
            .memberType(DEFAULT_MEMBER_TYPE)
            .isactive(DEFAULT_ISACTIVE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON);
        return member;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Member createUpdatedEntity(EntityManager em) {
        Member member = new Member()
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .memberUniqueId(UPDATED_MEMBER_UNIQUE_ID)
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .gender(UPDATED_GENDER)
            .dob(UPDATED_DOB)
            .email(UPDATED_EMAIL)
            .mobileNo(UPDATED_MOBILE_NO)
            .religion(UPDATED_RELIGION)
            .category(UPDATED_CATEGORY)
            .cast(UPDATED_CAST)
            .aadharCard(UPDATED_AADHAR_CARD)
            .panCard(UPDATED_PAN_CARD)
            .rationCard(UPDATED_RATION_CARD)
            .familyMemberCount(UPDATED_FAMILY_MEMBER_COUNT)
            .occupation(UPDATED_OCCUPATION)
            .applicationDate(UPDATED_APPLICATION_DATE)
            .status(UPDATED_STATUS)
            .kmpStatus(UPDATED_KMP_STATUS)
            .boardResolutionNo(UPDATED_BOARD_RESOLUTION_NO)
            .boardResolutionDate(UPDATED_BOARD_RESOLUTION_DATE)
            .loanStatus(UPDATED_LOAN_STATUS)
            .memberType(UPDATED_MEMBER_TYPE)
            .isactive(UPDATED_ISACTIVE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON);
        return member;
    }

    @BeforeEach
    public void initTest() {
        member = createEntity(em);
    }

    @Test
    @Transactional
    void createMember() throws Exception {
        int databaseSizeBeforeCreate = memberRepository.findAll().size();
        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);
        restMemberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isCreated());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate + 1);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testMember.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testMember.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testMember.getMemberUniqueId()).isEqualTo(DEFAULT_MEMBER_UNIQUE_ID);
        assertThat(testMember.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testMember.getMotherName()).isEqualTo(DEFAULT_MOTHER_NAME);
        assertThat(testMember.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testMember.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testMember.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMember.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testMember.getReligion()).isEqualTo(DEFAULT_RELIGION);
        assertThat(testMember.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testMember.getCast()).isEqualTo(DEFAULT_CAST);
        assertThat(testMember.getAadharCard()).isEqualTo(DEFAULT_AADHAR_CARD);
        assertThat(testMember.getPanCard()).isEqualTo(DEFAULT_PAN_CARD);
        assertThat(testMember.getRationCard()).isEqualTo(DEFAULT_RATION_CARD);
        assertThat(testMember.getFamilyMemberCount()).isEqualTo(DEFAULT_FAMILY_MEMBER_COUNT);
        assertThat(testMember.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testMember.getApplicationDate()).isEqualTo(DEFAULT_APPLICATION_DATE);
        assertThat(testMember.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMember.getKmpStatus()).isEqualTo(DEFAULT_KMP_STATUS);
        assertThat(testMember.getBoardResolutionNo()).isEqualTo(DEFAULT_BOARD_RESOLUTION_NO);
        assertThat(testMember.getBoardResolutionDate()).isEqualTo(DEFAULT_BOARD_RESOLUTION_DATE);
        assertThat(testMember.getLoanStatus()).isEqualTo(DEFAULT_LOAN_STATUS);
        assertThat(testMember.getMemberType()).isEqualTo(DEFAULT_MEMBER_TYPE);
        assertThat(testMember.getIsactive()).isEqualTo(DEFAULT_ISACTIVE);
        assertThat(testMember.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testMember.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testMember.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMember.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    void createMemberWithExistingId() throws Exception {
        // Create the Member with an existing ID
        member.setId(1L);
        MemberDTO memberDTO = memberMapper.toDto(member);

        int databaseSizeBeforeCreate = memberRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMembers() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList
        restMemberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(member.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].memberUniqueId").value(hasItem(DEFAULT_MEMBER_UNIQUE_ID)))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME)))
            .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].cast").value(hasItem(DEFAULT_CAST)))
            .andExpect(jsonPath("$.[*].aadharCard").value(hasItem(DEFAULT_AADHAR_CARD)))
            .andExpect(jsonPath("$.[*].panCard").value(hasItem(DEFAULT_PAN_CARD)))
            .andExpect(jsonPath("$.[*].rationCard").value(hasItem(DEFAULT_RATION_CARD)))
            .andExpect(jsonPath("$.[*].familyMemberCount").value(hasItem(DEFAULT_FAMILY_MEMBER_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION)))
            .andExpect(jsonPath("$.[*].applicationDate").value(hasItem(DEFAULT_APPLICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].kmpStatus").value(hasItem(DEFAULT_KMP_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].boardResolutionNo").value(hasItem(DEFAULT_BOARD_RESOLUTION_NO)))
            .andExpect(jsonPath("$.[*].boardResolutionDate").value(hasItem(DEFAULT_BOARD_RESOLUTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanStatus").value(hasItem(DEFAULT_LOAN_STATUS.toString())))
            .andExpect(jsonPath("$.[*].memberType").value(hasItem(DEFAULT_MEMBER_TYPE)))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }

    @Test
    @Transactional
    void getMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get the member
        restMemberMockMvc
            .perform(get(ENTITY_API_URL_ID, member.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(member.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.memberUniqueId").value(DEFAULT_MEMBER_UNIQUE_ID))
            .andExpect(jsonPath("$.fatherName").value(DEFAULT_FATHER_NAME))
            .andExpect(jsonPath("$.motherName").value(DEFAULT_MOTHER_NAME))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO))
            .andExpect(jsonPath("$.religion").value(DEFAULT_RELIGION))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.cast").value(DEFAULT_CAST))
            .andExpect(jsonPath("$.aadharCard").value(DEFAULT_AADHAR_CARD))
            .andExpect(jsonPath("$.panCard").value(DEFAULT_PAN_CARD))
            .andExpect(jsonPath("$.rationCard").value(DEFAULT_RATION_CARD))
            .andExpect(jsonPath("$.familyMemberCount").value(DEFAULT_FAMILY_MEMBER_COUNT.intValue()))
            .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION))
            .andExpect(jsonPath("$.applicationDate").value(DEFAULT_APPLICATION_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.kmpStatus").value(DEFAULT_KMP_STATUS.booleanValue()))
            .andExpect(jsonPath("$.boardResolutionNo").value(DEFAULT_BOARD_RESOLUTION_NO))
            .andExpect(jsonPath("$.boardResolutionDate").value(DEFAULT_BOARD_RESOLUTION_DATE.toString()))
            .andExpect(jsonPath("$.loanStatus").value(DEFAULT_LOAN_STATUS.toString()))
            .andExpect(jsonPath("$.memberType").value(DEFAULT_MEMBER_TYPE))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    void getMembersByIdFiltering() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        Long id = member.getId();

        defaultMemberShouldBeFound("id.equals=" + id);
        defaultMemberShouldNotBeFound("id.notEquals=" + id);

        defaultMemberShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMemberShouldNotBeFound("id.greaterThan=" + id);

        defaultMemberShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMemberShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMembersByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where firstName equals to DEFAULT_FIRST_NAME
        defaultMemberShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the memberList where firstName equals to UPDATED_FIRST_NAME
        defaultMemberShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultMemberShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the memberList where firstName equals to UPDATED_FIRST_NAME
        defaultMemberShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where firstName is not null
        defaultMemberShouldBeFound("firstName.specified=true");

        // Get all the memberList where firstName is null
        defaultMemberShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where firstName contains DEFAULT_FIRST_NAME
        defaultMemberShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the memberList where firstName contains UPDATED_FIRST_NAME
        defaultMemberShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where firstName does not contain DEFAULT_FIRST_NAME
        defaultMemberShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the memberList where firstName does not contain UPDATED_FIRST_NAME
        defaultMemberShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByMiddleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where middleName equals to DEFAULT_MIDDLE_NAME
        defaultMemberShouldBeFound("middleName.equals=" + DEFAULT_MIDDLE_NAME);

        // Get all the memberList where middleName equals to UPDATED_MIDDLE_NAME
        defaultMemberShouldNotBeFound("middleName.equals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByMiddleNameIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where middleName in DEFAULT_MIDDLE_NAME or UPDATED_MIDDLE_NAME
        defaultMemberShouldBeFound("middleName.in=" + DEFAULT_MIDDLE_NAME + "," + UPDATED_MIDDLE_NAME);

        // Get all the memberList where middleName equals to UPDATED_MIDDLE_NAME
        defaultMemberShouldNotBeFound("middleName.in=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByMiddleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where middleName is not null
        defaultMemberShouldBeFound("middleName.specified=true");

        // Get all the memberList where middleName is null
        defaultMemberShouldNotBeFound("middleName.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByMiddleNameContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where middleName contains DEFAULT_MIDDLE_NAME
        defaultMemberShouldBeFound("middleName.contains=" + DEFAULT_MIDDLE_NAME);

        // Get all the memberList where middleName contains UPDATED_MIDDLE_NAME
        defaultMemberShouldNotBeFound("middleName.contains=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByMiddleNameNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where middleName does not contain DEFAULT_MIDDLE_NAME
        defaultMemberShouldNotBeFound("middleName.doesNotContain=" + DEFAULT_MIDDLE_NAME);

        // Get all the memberList where middleName does not contain UPDATED_MIDDLE_NAME
        defaultMemberShouldBeFound("middleName.doesNotContain=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastName equals to DEFAULT_LAST_NAME
        defaultMemberShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the memberList where lastName equals to UPDATED_LAST_NAME
        defaultMemberShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultMemberShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the memberList where lastName equals to UPDATED_LAST_NAME
        defaultMemberShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastName is not null
        defaultMemberShouldBeFound("lastName.specified=true");

        // Get all the memberList where lastName is null
        defaultMemberShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByLastNameContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastName contains DEFAULT_LAST_NAME
        defaultMemberShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the memberList where lastName contains UPDATED_LAST_NAME
        defaultMemberShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastName does not contain DEFAULT_LAST_NAME
        defaultMemberShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the memberList where lastName does not contain UPDATED_LAST_NAME
        defaultMemberShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByMemberUniqueIdIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where memberUniqueId equals to DEFAULT_MEMBER_UNIQUE_ID
        defaultMemberShouldBeFound("memberUniqueId.equals=" + DEFAULT_MEMBER_UNIQUE_ID);

        // Get all the memberList where memberUniqueId equals to UPDATED_MEMBER_UNIQUE_ID
        defaultMemberShouldNotBeFound("memberUniqueId.equals=" + UPDATED_MEMBER_UNIQUE_ID);
    }

    @Test
    @Transactional
    void getAllMembersByMemberUniqueIdIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where memberUniqueId in DEFAULT_MEMBER_UNIQUE_ID or UPDATED_MEMBER_UNIQUE_ID
        defaultMemberShouldBeFound("memberUniqueId.in=" + DEFAULT_MEMBER_UNIQUE_ID + "," + UPDATED_MEMBER_UNIQUE_ID);

        // Get all the memberList where memberUniqueId equals to UPDATED_MEMBER_UNIQUE_ID
        defaultMemberShouldNotBeFound("memberUniqueId.in=" + UPDATED_MEMBER_UNIQUE_ID);
    }

    @Test
    @Transactional
    void getAllMembersByMemberUniqueIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where memberUniqueId is not null
        defaultMemberShouldBeFound("memberUniqueId.specified=true");

        // Get all the memberList where memberUniqueId is null
        defaultMemberShouldNotBeFound("memberUniqueId.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByMemberUniqueIdContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where memberUniqueId contains DEFAULT_MEMBER_UNIQUE_ID
        defaultMemberShouldBeFound("memberUniqueId.contains=" + DEFAULT_MEMBER_UNIQUE_ID);

        // Get all the memberList where memberUniqueId contains UPDATED_MEMBER_UNIQUE_ID
        defaultMemberShouldNotBeFound("memberUniqueId.contains=" + UPDATED_MEMBER_UNIQUE_ID);
    }

    @Test
    @Transactional
    void getAllMembersByMemberUniqueIdNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where memberUniqueId does not contain DEFAULT_MEMBER_UNIQUE_ID
        defaultMemberShouldNotBeFound("memberUniqueId.doesNotContain=" + DEFAULT_MEMBER_UNIQUE_ID);

        // Get all the memberList where memberUniqueId does not contain UPDATED_MEMBER_UNIQUE_ID
        defaultMemberShouldBeFound("memberUniqueId.doesNotContain=" + UPDATED_MEMBER_UNIQUE_ID);
    }

    @Test
    @Transactional
    void getAllMembersByFatherNameIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where fatherName equals to DEFAULT_FATHER_NAME
        defaultMemberShouldBeFound("fatherName.equals=" + DEFAULT_FATHER_NAME);

        // Get all the memberList where fatherName equals to UPDATED_FATHER_NAME
        defaultMemberShouldNotBeFound("fatherName.equals=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByFatherNameIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where fatherName in DEFAULT_FATHER_NAME or UPDATED_FATHER_NAME
        defaultMemberShouldBeFound("fatherName.in=" + DEFAULT_FATHER_NAME + "," + UPDATED_FATHER_NAME);

        // Get all the memberList where fatherName equals to UPDATED_FATHER_NAME
        defaultMemberShouldNotBeFound("fatherName.in=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByFatherNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where fatherName is not null
        defaultMemberShouldBeFound("fatherName.specified=true");

        // Get all the memberList where fatherName is null
        defaultMemberShouldNotBeFound("fatherName.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByFatherNameContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where fatherName contains DEFAULT_FATHER_NAME
        defaultMemberShouldBeFound("fatherName.contains=" + DEFAULT_FATHER_NAME);

        // Get all the memberList where fatherName contains UPDATED_FATHER_NAME
        defaultMemberShouldNotBeFound("fatherName.contains=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByFatherNameNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where fatherName does not contain DEFAULT_FATHER_NAME
        defaultMemberShouldNotBeFound("fatherName.doesNotContain=" + DEFAULT_FATHER_NAME);

        // Get all the memberList where fatherName does not contain UPDATED_FATHER_NAME
        defaultMemberShouldBeFound("fatherName.doesNotContain=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByMotherNameIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where motherName equals to DEFAULT_MOTHER_NAME
        defaultMemberShouldBeFound("motherName.equals=" + DEFAULT_MOTHER_NAME);

        // Get all the memberList where motherName equals to UPDATED_MOTHER_NAME
        defaultMemberShouldNotBeFound("motherName.equals=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByMotherNameIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where motherName in DEFAULT_MOTHER_NAME or UPDATED_MOTHER_NAME
        defaultMemberShouldBeFound("motherName.in=" + DEFAULT_MOTHER_NAME + "," + UPDATED_MOTHER_NAME);

        // Get all the memberList where motherName equals to UPDATED_MOTHER_NAME
        defaultMemberShouldNotBeFound("motherName.in=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByMotherNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where motherName is not null
        defaultMemberShouldBeFound("motherName.specified=true");

        // Get all the memberList where motherName is null
        defaultMemberShouldNotBeFound("motherName.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByMotherNameContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where motherName contains DEFAULT_MOTHER_NAME
        defaultMemberShouldBeFound("motherName.contains=" + DEFAULT_MOTHER_NAME);

        // Get all the memberList where motherName contains UPDATED_MOTHER_NAME
        defaultMemberShouldNotBeFound("motherName.contains=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByMotherNameNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where motherName does not contain DEFAULT_MOTHER_NAME
        defaultMemberShouldNotBeFound("motherName.doesNotContain=" + DEFAULT_MOTHER_NAME);

        // Get all the memberList where motherName does not contain UPDATED_MOTHER_NAME
        defaultMemberShouldBeFound("motherName.doesNotContain=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where gender equals to DEFAULT_GENDER
        defaultMemberShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the memberList where gender equals to UPDATED_GENDER
        defaultMemberShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllMembersByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultMemberShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the memberList where gender equals to UPDATED_GENDER
        defaultMemberShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllMembersByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where gender is not null
        defaultMemberShouldBeFound("gender.specified=true");

        // Get all the memberList where gender is null
        defaultMemberShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByDobIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where dob equals to DEFAULT_DOB
        defaultMemberShouldBeFound("dob.equals=" + DEFAULT_DOB);

        // Get all the memberList where dob equals to UPDATED_DOB
        defaultMemberShouldNotBeFound("dob.equals=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllMembersByDobIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where dob in DEFAULT_DOB or UPDATED_DOB
        defaultMemberShouldBeFound("dob.in=" + DEFAULT_DOB + "," + UPDATED_DOB);

        // Get all the memberList where dob equals to UPDATED_DOB
        defaultMemberShouldNotBeFound("dob.in=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllMembersByDobIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where dob is not null
        defaultMemberShouldBeFound("dob.specified=true");

        // Get all the memberList where dob is null
        defaultMemberShouldNotBeFound("dob.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByDobIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where dob is greater than or equal to DEFAULT_DOB
        defaultMemberShouldBeFound("dob.greaterThanOrEqual=" + DEFAULT_DOB);

        // Get all the memberList where dob is greater than or equal to UPDATED_DOB
        defaultMemberShouldNotBeFound("dob.greaterThanOrEqual=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllMembersByDobIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where dob is less than or equal to DEFAULT_DOB
        defaultMemberShouldBeFound("dob.lessThanOrEqual=" + DEFAULT_DOB);

        // Get all the memberList where dob is less than or equal to SMALLER_DOB
        defaultMemberShouldNotBeFound("dob.lessThanOrEqual=" + SMALLER_DOB);
    }

    @Test
    @Transactional
    void getAllMembersByDobIsLessThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where dob is less than DEFAULT_DOB
        defaultMemberShouldNotBeFound("dob.lessThan=" + DEFAULT_DOB);

        // Get all the memberList where dob is less than UPDATED_DOB
        defaultMemberShouldBeFound("dob.lessThan=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllMembersByDobIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where dob is greater than DEFAULT_DOB
        defaultMemberShouldNotBeFound("dob.greaterThan=" + DEFAULT_DOB);

        // Get all the memberList where dob is greater than SMALLER_DOB
        defaultMemberShouldBeFound("dob.greaterThan=" + SMALLER_DOB);
    }

    @Test
    @Transactional
    void getAllMembersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where email equals to DEFAULT_EMAIL
        defaultMemberShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the memberList where email equals to UPDATED_EMAIL
        defaultMemberShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllMembersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultMemberShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the memberList where email equals to UPDATED_EMAIL
        defaultMemberShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllMembersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where email is not null
        defaultMemberShouldBeFound("email.specified=true");

        // Get all the memberList where email is null
        defaultMemberShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByEmailContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where email contains DEFAULT_EMAIL
        defaultMemberShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the memberList where email contains UPDATED_EMAIL
        defaultMemberShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllMembersByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where email does not contain DEFAULT_EMAIL
        defaultMemberShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the memberList where email does not contain UPDATED_EMAIL
        defaultMemberShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllMembersByMobileNoIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where mobileNo equals to DEFAULT_MOBILE_NO
        defaultMemberShouldBeFound("mobileNo.equals=" + DEFAULT_MOBILE_NO);

        // Get all the memberList where mobileNo equals to UPDATED_MOBILE_NO
        defaultMemberShouldNotBeFound("mobileNo.equals=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllMembersByMobileNoIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where mobileNo in DEFAULT_MOBILE_NO or UPDATED_MOBILE_NO
        defaultMemberShouldBeFound("mobileNo.in=" + DEFAULT_MOBILE_NO + "," + UPDATED_MOBILE_NO);

        // Get all the memberList where mobileNo equals to UPDATED_MOBILE_NO
        defaultMemberShouldNotBeFound("mobileNo.in=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllMembersByMobileNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where mobileNo is not null
        defaultMemberShouldBeFound("mobileNo.specified=true");

        // Get all the memberList where mobileNo is null
        defaultMemberShouldNotBeFound("mobileNo.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByMobileNoContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where mobileNo contains DEFAULT_MOBILE_NO
        defaultMemberShouldBeFound("mobileNo.contains=" + DEFAULT_MOBILE_NO);

        // Get all the memberList where mobileNo contains UPDATED_MOBILE_NO
        defaultMemberShouldNotBeFound("mobileNo.contains=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllMembersByMobileNoNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where mobileNo does not contain DEFAULT_MOBILE_NO
        defaultMemberShouldNotBeFound("mobileNo.doesNotContain=" + DEFAULT_MOBILE_NO);

        // Get all the memberList where mobileNo does not contain UPDATED_MOBILE_NO
        defaultMemberShouldBeFound("mobileNo.doesNotContain=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllMembersByReligionIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where religion equals to DEFAULT_RELIGION
        defaultMemberShouldBeFound("religion.equals=" + DEFAULT_RELIGION);

        // Get all the memberList where religion equals to UPDATED_RELIGION
        defaultMemberShouldNotBeFound("religion.equals=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    void getAllMembersByReligionIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where religion in DEFAULT_RELIGION or UPDATED_RELIGION
        defaultMemberShouldBeFound("religion.in=" + DEFAULT_RELIGION + "," + UPDATED_RELIGION);

        // Get all the memberList where religion equals to UPDATED_RELIGION
        defaultMemberShouldNotBeFound("religion.in=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    void getAllMembersByReligionIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where religion is not null
        defaultMemberShouldBeFound("religion.specified=true");

        // Get all the memberList where religion is null
        defaultMemberShouldNotBeFound("religion.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByReligionContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where religion contains DEFAULT_RELIGION
        defaultMemberShouldBeFound("religion.contains=" + DEFAULT_RELIGION);

        // Get all the memberList where religion contains UPDATED_RELIGION
        defaultMemberShouldNotBeFound("religion.contains=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    void getAllMembersByReligionNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where religion does not contain DEFAULT_RELIGION
        defaultMemberShouldNotBeFound("religion.doesNotContain=" + DEFAULT_RELIGION);

        // Get all the memberList where religion does not contain UPDATED_RELIGION
        defaultMemberShouldBeFound("religion.doesNotContain=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    void getAllMembersByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where category equals to DEFAULT_CATEGORY
        defaultMemberShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the memberList where category equals to UPDATED_CATEGORY
        defaultMemberShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllMembersByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultMemberShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the memberList where category equals to UPDATED_CATEGORY
        defaultMemberShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllMembersByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where category is not null
        defaultMemberShouldBeFound("category.specified=true");

        // Get all the memberList where category is null
        defaultMemberShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByCategoryContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where category contains DEFAULT_CATEGORY
        defaultMemberShouldBeFound("category.contains=" + DEFAULT_CATEGORY);

        // Get all the memberList where category contains UPDATED_CATEGORY
        defaultMemberShouldNotBeFound("category.contains=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllMembersByCategoryNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where category does not contain DEFAULT_CATEGORY
        defaultMemberShouldNotBeFound("category.doesNotContain=" + DEFAULT_CATEGORY);

        // Get all the memberList where category does not contain UPDATED_CATEGORY
        defaultMemberShouldBeFound("category.doesNotContain=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllMembersByCastIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where cast equals to DEFAULT_CAST
        defaultMemberShouldBeFound("cast.equals=" + DEFAULT_CAST);

        // Get all the memberList where cast equals to UPDATED_CAST
        defaultMemberShouldNotBeFound("cast.equals=" + UPDATED_CAST);
    }

    @Test
    @Transactional
    void getAllMembersByCastIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where cast in DEFAULT_CAST or UPDATED_CAST
        defaultMemberShouldBeFound("cast.in=" + DEFAULT_CAST + "," + UPDATED_CAST);

        // Get all the memberList where cast equals to UPDATED_CAST
        defaultMemberShouldNotBeFound("cast.in=" + UPDATED_CAST);
    }

    @Test
    @Transactional
    void getAllMembersByCastIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where cast is not null
        defaultMemberShouldBeFound("cast.specified=true");

        // Get all the memberList where cast is null
        defaultMemberShouldNotBeFound("cast.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByCastContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where cast contains DEFAULT_CAST
        defaultMemberShouldBeFound("cast.contains=" + DEFAULT_CAST);

        // Get all the memberList where cast contains UPDATED_CAST
        defaultMemberShouldNotBeFound("cast.contains=" + UPDATED_CAST);
    }

    @Test
    @Transactional
    void getAllMembersByCastNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where cast does not contain DEFAULT_CAST
        defaultMemberShouldNotBeFound("cast.doesNotContain=" + DEFAULT_CAST);

        // Get all the memberList where cast does not contain UPDATED_CAST
        defaultMemberShouldBeFound("cast.doesNotContain=" + UPDATED_CAST);
    }

    @Test
    @Transactional
    void getAllMembersByAadharCardIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where aadharCard equals to DEFAULT_AADHAR_CARD
        defaultMemberShouldBeFound("aadharCard.equals=" + DEFAULT_AADHAR_CARD);

        // Get all the memberList where aadharCard equals to UPDATED_AADHAR_CARD
        defaultMemberShouldNotBeFound("aadharCard.equals=" + UPDATED_AADHAR_CARD);
    }

    @Test
    @Transactional
    void getAllMembersByAadharCardIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where aadharCard in DEFAULT_AADHAR_CARD or UPDATED_AADHAR_CARD
        defaultMemberShouldBeFound("aadharCard.in=" + DEFAULT_AADHAR_CARD + "," + UPDATED_AADHAR_CARD);

        // Get all the memberList where aadharCard equals to UPDATED_AADHAR_CARD
        defaultMemberShouldNotBeFound("aadharCard.in=" + UPDATED_AADHAR_CARD);
    }

    @Test
    @Transactional
    void getAllMembersByAadharCardIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where aadharCard is not null
        defaultMemberShouldBeFound("aadharCard.specified=true");

        // Get all the memberList where aadharCard is null
        defaultMemberShouldNotBeFound("aadharCard.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByAadharCardContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where aadharCard contains DEFAULT_AADHAR_CARD
        defaultMemberShouldBeFound("aadharCard.contains=" + DEFAULT_AADHAR_CARD);

        // Get all the memberList where aadharCard contains UPDATED_AADHAR_CARD
        defaultMemberShouldNotBeFound("aadharCard.contains=" + UPDATED_AADHAR_CARD);
    }

    @Test
    @Transactional
    void getAllMembersByAadharCardNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where aadharCard does not contain DEFAULT_AADHAR_CARD
        defaultMemberShouldNotBeFound("aadharCard.doesNotContain=" + DEFAULT_AADHAR_CARD);

        // Get all the memberList where aadharCard does not contain UPDATED_AADHAR_CARD
        defaultMemberShouldBeFound("aadharCard.doesNotContain=" + UPDATED_AADHAR_CARD);
    }

    @Test
    @Transactional
    void getAllMembersByPanCardIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where panCard equals to DEFAULT_PAN_CARD
        defaultMemberShouldBeFound("panCard.equals=" + DEFAULT_PAN_CARD);

        // Get all the memberList where panCard equals to UPDATED_PAN_CARD
        defaultMemberShouldNotBeFound("panCard.equals=" + UPDATED_PAN_CARD);
    }

    @Test
    @Transactional
    void getAllMembersByPanCardIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where panCard in DEFAULT_PAN_CARD or UPDATED_PAN_CARD
        defaultMemberShouldBeFound("panCard.in=" + DEFAULT_PAN_CARD + "," + UPDATED_PAN_CARD);

        // Get all the memberList where panCard equals to UPDATED_PAN_CARD
        defaultMemberShouldNotBeFound("panCard.in=" + UPDATED_PAN_CARD);
    }

    @Test
    @Transactional
    void getAllMembersByPanCardIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where panCard is not null
        defaultMemberShouldBeFound("panCard.specified=true");

        // Get all the memberList where panCard is null
        defaultMemberShouldNotBeFound("panCard.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByPanCardContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where panCard contains DEFAULT_PAN_CARD
        defaultMemberShouldBeFound("panCard.contains=" + DEFAULT_PAN_CARD);

        // Get all the memberList where panCard contains UPDATED_PAN_CARD
        defaultMemberShouldNotBeFound("panCard.contains=" + UPDATED_PAN_CARD);
    }

    @Test
    @Transactional
    void getAllMembersByPanCardNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where panCard does not contain DEFAULT_PAN_CARD
        defaultMemberShouldNotBeFound("panCard.doesNotContain=" + DEFAULT_PAN_CARD);

        // Get all the memberList where panCard does not contain UPDATED_PAN_CARD
        defaultMemberShouldBeFound("panCard.doesNotContain=" + UPDATED_PAN_CARD);
    }

    @Test
    @Transactional
    void getAllMembersByRationCardIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where rationCard equals to DEFAULT_RATION_CARD
        defaultMemberShouldBeFound("rationCard.equals=" + DEFAULT_RATION_CARD);

        // Get all the memberList where rationCard equals to UPDATED_RATION_CARD
        defaultMemberShouldNotBeFound("rationCard.equals=" + UPDATED_RATION_CARD);
    }

    @Test
    @Transactional
    void getAllMembersByRationCardIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where rationCard in DEFAULT_RATION_CARD or UPDATED_RATION_CARD
        defaultMemberShouldBeFound("rationCard.in=" + DEFAULT_RATION_CARD + "," + UPDATED_RATION_CARD);

        // Get all the memberList where rationCard equals to UPDATED_RATION_CARD
        defaultMemberShouldNotBeFound("rationCard.in=" + UPDATED_RATION_CARD);
    }

    @Test
    @Transactional
    void getAllMembersByRationCardIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where rationCard is not null
        defaultMemberShouldBeFound("rationCard.specified=true");

        // Get all the memberList where rationCard is null
        defaultMemberShouldNotBeFound("rationCard.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByRationCardContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where rationCard contains DEFAULT_RATION_CARD
        defaultMemberShouldBeFound("rationCard.contains=" + DEFAULT_RATION_CARD);

        // Get all the memberList where rationCard contains UPDATED_RATION_CARD
        defaultMemberShouldNotBeFound("rationCard.contains=" + UPDATED_RATION_CARD);
    }

    @Test
    @Transactional
    void getAllMembersByRationCardNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where rationCard does not contain DEFAULT_RATION_CARD
        defaultMemberShouldNotBeFound("rationCard.doesNotContain=" + DEFAULT_RATION_CARD);

        // Get all the memberList where rationCard does not contain UPDATED_RATION_CARD
        defaultMemberShouldBeFound("rationCard.doesNotContain=" + UPDATED_RATION_CARD);
    }

    @Test
    @Transactional
    void getAllMembersByFamilyMemberCountIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where familyMemberCount equals to DEFAULT_FAMILY_MEMBER_COUNT
        defaultMemberShouldBeFound("familyMemberCount.equals=" + DEFAULT_FAMILY_MEMBER_COUNT);

        // Get all the memberList where familyMemberCount equals to UPDATED_FAMILY_MEMBER_COUNT
        defaultMemberShouldNotBeFound("familyMemberCount.equals=" + UPDATED_FAMILY_MEMBER_COUNT);
    }

    @Test
    @Transactional
    void getAllMembersByFamilyMemberCountIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where familyMemberCount in DEFAULT_FAMILY_MEMBER_COUNT or UPDATED_FAMILY_MEMBER_COUNT
        defaultMemberShouldBeFound("familyMemberCount.in=" + DEFAULT_FAMILY_MEMBER_COUNT + "," + UPDATED_FAMILY_MEMBER_COUNT);

        // Get all the memberList where familyMemberCount equals to UPDATED_FAMILY_MEMBER_COUNT
        defaultMemberShouldNotBeFound("familyMemberCount.in=" + UPDATED_FAMILY_MEMBER_COUNT);
    }

    @Test
    @Transactional
    void getAllMembersByFamilyMemberCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where familyMemberCount is not null
        defaultMemberShouldBeFound("familyMemberCount.specified=true");

        // Get all the memberList where familyMemberCount is null
        defaultMemberShouldNotBeFound("familyMemberCount.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByFamilyMemberCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where familyMemberCount is greater than or equal to DEFAULT_FAMILY_MEMBER_COUNT
        defaultMemberShouldBeFound("familyMemberCount.greaterThanOrEqual=" + DEFAULT_FAMILY_MEMBER_COUNT);

        // Get all the memberList where familyMemberCount is greater than or equal to UPDATED_FAMILY_MEMBER_COUNT
        defaultMemberShouldNotBeFound("familyMemberCount.greaterThanOrEqual=" + UPDATED_FAMILY_MEMBER_COUNT);
    }

    @Test
    @Transactional
    void getAllMembersByFamilyMemberCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where familyMemberCount is less than or equal to DEFAULT_FAMILY_MEMBER_COUNT
        defaultMemberShouldBeFound("familyMemberCount.lessThanOrEqual=" + DEFAULT_FAMILY_MEMBER_COUNT);

        // Get all the memberList where familyMemberCount is less than or equal to SMALLER_FAMILY_MEMBER_COUNT
        defaultMemberShouldNotBeFound("familyMemberCount.lessThanOrEqual=" + SMALLER_FAMILY_MEMBER_COUNT);
    }

    @Test
    @Transactional
    void getAllMembersByFamilyMemberCountIsLessThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where familyMemberCount is less than DEFAULT_FAMILY_MEMBER_COUNT
        defaultMemberShouldNotBeFound("familyMemberCount.lessThan=" + DEFAULT_FAMILY_MEMBER_COUNT);

        // Get all the memberList where familyMemberCount is less than UPDATED_FAMILY_MEMBER_COUNT
        defaultMemberShouldBeFound("familyMemberCount.lessThan=" + UPDATED_FAMILY_MEMBER_COUNT);
    }

    @Test
    @Transactional
    void getAllMembersByFamilyMemberCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where familyMemberCount is greater than DEFAULT_FAMILY_MEMBER_COUNT
        defaultMemberShouldNotBeFound("familyMemberCount.greaterThan=" + DEFAULT_FAMILY_MEMBER_COUNT);

        // Get all the memberList where familyMemberCount is greater than SMALLER_FAMILY_MEMBER_COUNT
        defaultMemberShouldBeFound("familyMemberCount.greaterThan=" + SMALLER_FAMILY_MEMBER_COUNT);
    }

    @Test
    @Transactional
    void getAllMembersByOccupationIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where occupation equals to DEFAULT_OCCUPATION
        defaultMemberShouldBeFound("occupation.equals=" + DEFAULT_OCCUPATION);

        // Get all the memberList where occupation equals to UPDATED_OCCUPATION
        defaultMemberShouldNotBeFound("occupation.equals=" + UPDATED_OCCUPATION);
    }

    @Test
    @Transactional
    void getAllMembersByOccupationIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where occupation in DEFAULT_OCCUPATION or UPDATED_OCCUPATION
        defaultMemberShouldBeFound("occupation.in=" + DEFAULT_OCCUPATION + "," + UPDATED_OCCUPATION);

        // Get all the memberList where occupation equals to UPDATED_OCCUPATION
        defaultMemberShouldNotBeFound("occupation.in=" + UPDATED_OCCUPATION);
    }

    @Test
    @Transactional
    void getAllMembersByOccupationIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where occupation is not null
        defaultMemberShouldBeFound("occupation.specified=true");

        // Get all the memberList where occupation is null
        defaultMemberShouldNotBeFound("occupation.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByOccupationContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where occupation contains DEFAULT_OCCUPATION
        defaultMemberShouldBeFound("occupation.contains=" + DEFAULT_OCCUPATION);

        // Get all the memberList where occupation contains UPDATED_OCCUPATION
        defaultMemberShouldNotBeFound("occupation.contains=" + UPDATED_OCCUPATION);
    }

    @Test
    @Transactional
    void getAllMembersByOccupationNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where occupation does not contain DEFAULT_OCCUPATION
        defaultMemberShouldNotBeFound("occupation.doesNotContain=" + DEFAULT_OCCUPATION);

        // Get all the memberList where occupation does not contain UPDATED_OCCUPATION
        defaultMemberShouldBeFound("occupation.doesNotContain=" + UPDATED_OCCUPATION);
    }

    @Test
    @Transactional
    void getAllMembersByApplicationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where applicationDate equals to DEFAULT_APPLICATION_DATE
        defaultMemberShouldBeFound("applicationDate.equals=" + DEFAULT_APPLICATION_DATE);

        // Get all the memberList where applicationDate equals to UPDATED_APPLICATION_DATE
        defaultMemberShouldNotBeFound("applicationDate.equals=" + UPDATED_APPLICATION_DATE);
    }

    @Test
    @Transactional
    void getAllMembersByApplicationDateIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where applicationDate in DEFAULT_APPLICATION_DATE or UPDATED_APPLICATION_DATE
        defaultMemberShouldBeFound("applicationDate.in=" + DEFAULT_APPLICATION_DATE + "," + UPDATED_APPLICATION_DATE);

        // Get all the memberList where applicationDate equals to UPDATED_APPLICATION_DATE
        defaultMemberShouldNotBeFound("applicationDate.in=" + UPDATED_APPLICATION_DATE);
    }

    @Test
    @Transactional
    void getAllMembersByApplicationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where applicationDate is not null
        defaultMemberShouldBeFound("applicationDate.specified=true");

        // Get all the memberList where applicationDate is null
        defaultMemberShouldNotBeFound("applicationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where status equals to DEFAULT_STATUS
        defaultMemberShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the memberList where status equals to UPDATED_STATUS
        defaultMemberShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMembersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultMemberShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the memberList where status equals to UPDATED_STATUS
        defaultMemberShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMembersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where status is not null
        defaultMemberShouldBeFound("status.specified=true");

        // Get all the memberList where status is null
        defaultMemberShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByKmpStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where kmpStatus equals to DEFAULT_KMP_STATUS
        defaultMemberShouldBeFound("kmpStatus.equals=" + DEFAULT_KMP_STATUS);

        // Get all the memberList where kmpStatus equals to UPDATED_KMP_STATUS
        defaultMemberShouldNotBeFound("kmpStatus.equals=" + UPDATED_KMP_STATUS);
    }

    @Test
    @Transactional
    void getAllMembersByKmpStatusIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where kmpStatus in DEFAULT_KMP_STATUS or UPDATED_KMP_STATUS
        defaultMemberShouldBeFound("kmpStatus.in=" + DEFAULT_KMP_STATUS + "," + UPDATED_KMP_STATUS);

        // Get all the memberList where kmpStatus equals to UPDATED_KMP_STATUS
        defaultMemberShouldNotBeFound("kmpStatus.in=" + UPDATED_KMP_STATUS);
    }

    @Test
    @Transactional
    void getAllMembersByKmpStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where kmpStatus is not null
        defaultMemberShouldBeFound("kmpStatus.specified=true");

        // Get all the memberList where kmpStatus is null
        defaultMemberShouldNotBeFound("kmpStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByBoardResolutionNoIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where boardResolutionNo equals to DEFAULT_BOARD_RESOLUTION_NO
        defaultMemberShouldBeFound("boardResolutionNo.equals=" + DEFAULT_BOARD_RESOLUTION_NO);

        // Get all the memberList where boardResolutionNo equals to UPDATED_BOARD_RESOLUTION_NO
        defaultMemberShouldNotBeFound("boardResolutionNo.equals=" + UPDATED_BOARD_RESOLUTION_NO);
    }

    @Test
    @Transactional
    void getAllMembersByBoardResolutionNoIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where boardResolutionNo in DEFAULT_BOARD_RESOLUTION_NO or UPDATED_BOARD_RESOLUTION_NO
        defaultMemberShouldBeFound("boardResolutionNo.in=" + DEFAULT_BOARD_RESOLUTION_NO + "," + UPDATED_BOARD_RESOLUTION_NO);

        // Get all the memberList where boardResolutionNo equals to UPDATED_BOARD_RESOLUTION_NO
        defaultMemberShouldNotBeFound("boardResolutionNo.in=" + UPDATED_BOARD_RESOLUTION_NO);
    }

    @Test
    @Transactional
    void getAllMembersByBoardResolutionNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where boardResolutionNo is not null
        defaultMemberShouldBeFound("boardResolutionNo.specified=true");

        // Get all the memberList where boardResolutionNo is null
        defaultMemberShouldNotBeFound("boardResolutionNo.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByBoardResolutionNoContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where boardResolutionNo contains DEFAULT_BOARD_RESOLUTION_NO
        defaultMemberShouldBeFound("boardResolutionNo.contains=" + DEFAULT_BOARD_RESOLUTION_NO);

        // Get all the memberList where boardResolutionNo contains UPDATED_BOARD_RESOLUTION_NO
        defaultMemberShouldNotBeFound("boardResolutionNo.contains=" + UPDATED_BOARD_RESOLUTION_NO);
    }

    @Test
    @Transactional
    void getAllMembersByBoardResolutionNoNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where boardResolutionNo does not contain DEFAULT_BOARD_RESOLUTION_NO
        defaultMemberShouldNotBeFound("boardResolutionNo.doesNotContain=" + DEFAULT_BOARD_RESOLUTION_NO);

        // Get all the memberList where boardResolutionNo does not contain UPDATED_BOARD_RESOLUTION_NO
        defaultMemberShouldBeFound("boardResolutionNo.doesNotContain=" + UPDATED_BOARD_RESOLUTION_NO);
    }

    @Test
    @Transactional
    void getAllMembersByBoardResolutionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where boardResolutionDate equals to DEFAULT_BOARD_RESOLUTION_DATE
        defaultMemberShouldBeFound("boardResolutionDate.equals=" + DEFAULT_BOARD_RESOLUTION_DATE);

        // Get all the memberList where boardResolutionDate equals to UPDATED_BOARD_RESOLUTION_DATE
        defaultMemberShouldNotBeFound("boardResolutionDate.equals=" + UPDATED_BOARD_RESOLUTION_DATE);
    }

    @Test
    @Transactional
    void getAllMembersByBoardResolutionDateIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where boardResolutionDate in DEFAULT_BOARD_RESOLUTION_DATE or UPDATED_BOARD_RESOLUTION_DATE
        defaultMemberShouldBeFound("boardResolutionDate.in=" + DEFAULT_BOARD_RESOLUTION_DATE + "," + UPDATED_BOARD_RESOLUTION_DATE);

        // Get all the memberList where boardResolutionDate equals to UPDATED_BOARD_RESOLUTION_DATE
        defaultMemberShouldNotBeFound("boardResolutionDate.in=" + UPDATED_BOARD_RESOLUTION_DATE);
    }

    @Test
    @Transactional
    void getAllMembersByBoardResolutionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where boardResolutionDate is not null
        defaultMemberShouldBeFound("boardResolutionDate.specified=true");

        // Get all the memberList where boardResolutionDate is null
        defaultMemberShouldNotBeFound("boardResolutionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByBoardResolutionDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where boardResolutionDate is greater than or equal to DEFAULT_BOARD_RESOLUTION_DATE
        defaultMemberShouldBeFound("boardResolutionDate.greaterThanOrEqual=" + DEFAULT_BOARD_RESOLUTION_DATE);

        // Get all the memberList where boardResolutionDate is greater than or equal to UPDATED_BOARD_RESOLUTION_DATE
        defaultMemberShouldNotBeFound("boardResolutionDate.greaterThanOrEqual=" + UPDATED_BOARD_RESOLUTION_DATE);
    }

    @Test
    @Transactional
    void getAllMembersByBoardResolutionDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where boardResolutionDate is less than or equal to DEFAULT_BOARD_RESOLUTION_DATE
        defaultMemberShouldBeFound("boardResolutionDate.lessThanOrEqual=" + DEFAULT_BOARD_RESOLUTION_DATE);

        // Get all the memberList where boardResolutionDate is less than or equal to SMALLER_BOARD_RESOLUTION_DATE
        defaultMemberShouldNotBeFound("boardResolutionDate.lessThanOrEqual=" + SMALLER_BOARD_RESOLUTION_DATE);
    }

    @Test
    @Transactional
    void getAllMembersByBoardResolutionDateIsLessThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where boardResolutionDate is less than DEFAULT_BOARD_RESOLUTION_DATE
        defaultMemberShouldNotBeFound("boardResolutionDate.lessThan=" + DEFAULT_BOARD_RESOLUTION_DATE);

        // Get all the memberList where boardResolutionDate is less than UPDATED_BOARD_RESOLUTION_DATE
        defaultMemberShouldBeFound("boardResolutionDate.lessThan=" + UPDATED_BOARD_RESOLUTION_DATE);
    }

    @Test
    @Transactional
    void getAllMembersByBoardResolutionDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where boardResolutionDate is greater than DEFAULT_BOARD_RESOLUTION_DATE
        defaultMemberShouldNotBeFound("boardResolutionDate.greaterThan=" + DEFAULT_BOARD_RESOLUTION_DATE);

        // Get all the memberList where boardResolutionDate is greater than SMALLER_BOARD_RESOLUTION_DATE
        defaultMemberShouldBeFound("boardResolutionDate.greaterThan=" + SMALLER_BOARD_RESOLUTION_DATE);
    }

    @Test
    @Transactional
    void getAllMembersByLoanStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where loanStatus equals to DEFAULT_LOAN_STATUS
        defaultMemberShouldBeFound("loanStatus.equals=" + DEFAULT_LOAN_STATUS);

        // Get all the memberList where loanStatus equals to UPDATED_LOAN_STATUS
        defaultMemberShouldNotBeFound("loanStatus.equals=" + UPDATED_LOAN_STATUS);
    }

    @Test
    @Transactional
    void getAllMembersByLoanStatusIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where loanStatus in DEFAULT_LOAN_STATUS or UPDATED_LOAN_STATUS
        defaultMemberShouldBeFound("loanStatus.in=" + DEFAULT_LOAN_STATUS + "," + UPDATED_LOAN_STATUS);

        // Get all the memberList where loanStatus equals to UPDATED_LOAN_STATUS
        defaultMemberShouldNotBeFound("loanStatus.in=" + UPDATED_LOAN_STATUS);
    }

    @Test
    @Transactional
    void getAllMembersByLoanStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where loanStatus is not null
        defaultMemberShouldBeFound("loanStatus.specified=true");

        // Get all the memberList where loanStatus is null
        defaultMemberShouldNotBeFound("loanStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByMemberTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where memberType equals to DEFAULT_MEMBER_TYPE
        defaultMemberShouldBeFound("memberType.equals=" + DEFAULT_MEMBER_TYPE);

        // Get all the memberList where memberType equals to UPDATED_MEMBER_TYPE
        defaultMemberShouldNotBeFound("memberType.equals=" + UPDATED_MEMBER_TYPE);
    }

    @Test
    @Transactional
    void getAllMembersByMemberTypeIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where memberType in DEFAULT_MEMBER_TYPE or UPDATED_MEMBER_TYPE
        defaultMemberShouldBeFound("memberType.in=" + DEFAULT_MEMBER_TYPE + "," + UPDATED_MEMBER_TYPE);

        // Get all the memberList where memberType equals to UPDATED_MEMBER_TYPE
        defaultMemberShouldNotBeFound("memberType.in=" + UPDATED_MEMBER_TYPE);
    }

    @Test
    @Transactional
    void getAllMembersByMemberTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where memberType is not null
        defaultMemberShouldBeFound("memberType.specified=true");

        // Get all the memberList where memberType is null
        defaultMemberShouldNotBeFound("memberType.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByMemberTypeContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where memberType contains DEFAULT_MEMBER_TYPE
        defaultMemberShouldBeFound("memberType.contains=" + DEFAULT_MEMBER_TYPE);

        // Get all the memberList where memberType contains UPDATED_MEMBER_TYPE
        defaultMemberShouldNotBeFound("memberType.contains=" + UPDATED_MEMBER_TYPE);
    }

    @Test
    @Transactional
    void getAllMembersByMemberTypeNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where memberType does not contain DEFAULT_MEMBER_TYPE
        defaultMemberShouldNotBeFound("memberType.doesNotContain=" + DEFAULT_MEMBER_TYPE);

        // Get all the memberList where memberType does not contain UPDATED_MEMBER_TYPE
        defaultMemberShouldBeFound("memberType.doesNotContain=" + UPDATED_MEMBER_TYPE);
    }

    @Test
    @Transactional
    void getAllMembersByIsactiveIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where isactive equals to DEFAULT_ISACTIVE
        defaultMemberShouldBeFound("isactive.equals=" + DEFAULT_ISACTIVE);

        // Get all the memberList where isactive equals to UPDATED_ISACTIVE
        defaultMemberShouldNotBeFound("isactive.equals=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllMembersByIsactiveIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where isactive in DEFAULT_ISACTIVE or UPDATED_ISACTIVE
        defaultMemberShouldBeFound("isactive.in=" + DEFAULT_ISACTIVE + "," + UPDATED_ISACTIVE);

        // Get all the memberList where isactive equals to UPDATED_ISACTIVE
        defaultMemberShouldNotBeFound("isactive.in=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllMembersByIsactiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where isactive is not null
        defaultMemberShouldBeFound("isactive.specified=true");

        // Get all the memberList where isactive is null
        defaultMemberShouldNotBeFound("isactive.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultMemberShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the memberList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMemberShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultMemberShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the memberList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMemberShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModified is not null
        defaultMemberShouldBeFound("lastModified.specified=true");

        // Get all the memberList where lastModified is null
        defaultMemberShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultMemberShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMemberShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultMemberShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the memberList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMemberShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModifiedBy is not null
        defaultMemberShouldBeFound("lastModifiedBy.specified=true");

        // Get all the memberList where lastModifiedBy is null
        defaultMemberShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultMemberShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultMemberShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultMemberShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultMemberShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMembersByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where createdBy equals to DEFAULT_CREATED_BY
        defaultMemberShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the memberList where createdBy equals to UPDATED_CREATED_BY
        defaultMemberShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMembersByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultMemberShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the memberList where createdBy equals to UPDATED_CREATED_BY
        defaultMemberShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMembersByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where createdBy is not null
        defaultMemberShouldBeFound("createdBy.specified=true");

        // Get all the memberList where createdBy is null
        defaultMemberShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where createdBy contains DEFAULT_CREATED_BY
        defaultMemberShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the memberList where createdBy contains UPDATED_CREATED_BY
        defaultMemberShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMembersByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where createdBy does not contain DEFAULT_CREATED_BY
        defaultMemberShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the memberList where createdBy does not contain UPDATED_CREATED_BY
        defaultMemberShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMembersByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where createdOn equals to DEFAULT_CREATED_ON
        defaultMemberShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the memberList where createdOn equals to UPDATED_CREATED_ON
        defaultMemberShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllMembersByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultMemberShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the memberList where createdOn equals to UPDATED_CREATED_ON
        defaultMemberShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllMembersByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where createdOn is not null
        defaultMemberShouldBeFound("createdOn.specified=true");

        // Get all the memberList where createdOn is null
        defaultMemberShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByMemberBankIsEqualToSomething() throws Exception {
        MemberBank memberBank;
        if (TestUtil.findAll(em, MemberBank.class).isEmpty()) {
            memberRepository.saveAndFlush(member);
            memberBank = MemberBankResourceIT.createEntity(em);
        } else {
            memberBank = TestUtil.findAll(em, MemberBank.class).get(0);
        }
        em.persist(memberBank);
        em.flush();
        member.setMemberBank(memberBank);
        memberRepository.saveAndFlush(member);
        Long memberBankId = memberBank.getId();

        // Get all the memberList where memberBank equals to memberBankId
        defaultMemberShouldBeFound("memberBankId.equals=" + memberBankId);

        // Get all the memberList where memberBank equals to (memberBankId + 1)
        defaultMemberShouldNotBeFound("memberBankId.equals=" + (memberBankId + 1));
    }

    @Test
    @Transactional
    void getAllMembersBySocietyIsEqualToSomething() throws Exception {
        Society society;
        if (TestUtil.findAll(em, Society.class).isEmpty()) {
            memberRepository.saveAndFlush(member);
            society = SocietyResourceIT.createEntity(em);
        } else {
            society = TestUtil.findAll(em, Society.class).get(0);
        }
        em.persist(society);
        em.flush();
        member.setSociety(society);
        memberRepository.saveAndFlush(member);
        Long societyId = society.getId();

        // Get all the memberList where society equals to societyId
        defaultMemberShouldBeFound("societyId.equals=" + societyId);

        // Get all the memberList where society equals to (societyId + 1)
        defaultMemberShouldNotBeFound("societyId.equals=" + (societyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMemberShouldBeFound(String filter) throws Exception {
        restMemberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(member.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].memberUniqueId").value(hasItem(DEFAULT_MEMBER_UNIQUE_ID)))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME)))
            .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].cast").value(hasItem(DEFAULT_CAST)))
            .andExpect(jsonPath("$.[*].aadharCard").value(hasItem(DEFAULT_AADHAR_CARD)))
            .andExpect(jsonPath("$.[*].panCard").value(hasItem(DEFAULT_PAN_CARD)))
            .andExpect(jsonPath("$.[*].rationCard").value(hasItem(DEFAULT_RATION_CARD)))
            .andExpect(jsonPath("$.[*].familyMemberCount").value(hasItem(DEFAULT_FAMILY_MEMBER_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION)))
            .andExpect(jsonPath("$.[*].applicationDate").value(hasItem(DEFAULT_APPLICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].kmpStatus").value(hasItem(DEFAULT_KMP_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].boardResolutionNo").value(hasItem(DEFAULT_BOARD_RESOLUTION_NO)))
            .andExpect(jsonPath("$.[*].boardResolutionDate").value(hasItem(DEFAULT_BOARD_RESOLUTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanStatus").value(hasItem(DEFAULT_LOAN_STATUS.toString())))
            .andExpect(jsonPath("$.[*].memberType").value(hasItem(DEFAULT_MEMBER_TYPE)))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));

        // Check, that the count call also returns 1
        restMemberMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMemberShouldNotBeFound(String filter) throws Exception {
        restMemberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMemberMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMember() throws Exception {
        // Get the member
        restMemberMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member
        Member updatedMember = memberRepository.findById(member.getId()).get();
        // Disconnect from session so that the updates on updatedMember are not directly saved in db
        em.detach(updatedMember);
        updatedMember
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .memberUniqueId(UPDATED_MEMBER_UNIQUE_ID)
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .gender(UPDATED_GENDER)
            .dob(UPDATED_DOB)
            .email(UPDATED_EMAIL)
            .mobileNo(UPDATED_MOBILE_NO)
            .religion(UPDATED_RELIGION)
            .category(UPDATED_CATEGORY)
            .cast(UPDATED_CAST)
            .aadharCard(UPDATED_AADHAR_CARD)
            .panCard(UPDATED_PAN_CARD)
            .rationCard(UPDATED_RATION_CARD)
            .familyMemberCount(UPDATED_FAMILY_MEMBER_COUNT)
            .occupation(UPDATED_OCCUPATION)
            .applicationDate(UPDATED_APPLICATION_DATE)
            .status(UPDATED_STATUS)
            .kmpStatus(UPDATED_KMP_STATUS)
            .boardResolutionNo(UPDATED_BOARD_RESOLUTION_NO)
            .boardResolutionDate(UPDATED_BOARD_RESOLUTION_DATE)
            .loanStatus(UPDATED_LOAN_STATUS)
            .memberType(UPDATED_MEMBER_TYPE)
            .isactive(UPDATED_ISACTIVE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON);
        MemberDTO memberDTO = memberMapper.toDto(updatedMember);

        restMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testMember.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testMember.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testMember.getMemberUniqueId()).isEqualTo(UPDATED_MEMBER_UNIQUE_ID);
        assertThat(testMember.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testMember.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testMember.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testMember.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testMember.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMember.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testMember.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testMember.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testMember.getCast()).isEqualTo(UPDATED_CAST);
        assertThat(testMember.getAadharCard()).isEqualTo(UPDATED_AADHAR_CARD);
        assertThat(testMember.getPanCard()).isEqualTo(UPDATED_PAN_CARD);
        assertThat(testMember.getRationCard()).isEqualTo(UPDATED_RATION_CARD);
        assertThat(testMember.getFamilyMemberCount()).isEqualTo(UPDATED_FAMILY_MEMBER_COUNT);
        assertThat(testMember.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testMember.getApplicationDate()).isEqualTo(UPDATED_APPLICATION_DATE);
        assertThat(testMember.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMember.getKmpStatus()).isEqualTo(UPDATED_KMP_STATUS);
        assertThat(testMember.getBoardResolutionNo()).isEqualTo(UPDATED_BOARD_RESOLUTION_NO);
        assertThat(testMember.getBoardResolutionDate()).isEqualTo(UPDATED_BOARD_RESOLUTION_DATE);
        assertThat(testMember.getLoanStatus()).isEqualTo(UPDATED_LOAN_STATUS);
        assertThat(testMember.getMemberType()).isEqualTo(UPDATED_MEMBER_TYPE);
        assertThat(testMember.getIsactive()).isEqualTo(UPDATED_ISACTIVE);
        assertThat(testMember.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMember.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMember.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMember.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMemberWithPatch() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member using partial update
        Member partialUpdatedMember = new Member();
        partialUpdatedMember.setId(member.getId());

        partialUpdatedMember
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dob(UPDATED_DOB)
            .email(UPDATED_EMAIL)
            .religion(UPDATED_RELIGION)
            .aadharCard(UPDATED_AADHAR_CARD)
            .panCard(UPDATED_PAN_CARD)
            .familyMemberCount(UPDATED_FAMILY_MEMBER_COUNT)
            .boardResolutionNo(UPDATED_BOARD_RESOLUTION_NO)
            .boardResolutionDate(UPDATED_BOARD_RESOLUTION_DATE)
            .memberType(UPDATED_MEMBER_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdOn(UPDATED_CREATED_ON);

        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMember.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMember))
            )
            .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testMember.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testMember.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testMember.getMemberUniqueId()).isEqualTo(DEFAULT_MEMBER_UNIQUE_ID);
        assertThat(testMember.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testMember.getMotherName()).isEqualTo(DEFAULT_MOTHER_NAME);
        assertThat(testMember.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testMember.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testMember.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMember.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testMember.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testMember.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testMember.getCast()).isEqualTo(DEFAULT_CAST);
        assertThat(testMember.getAadharCard()).isEqualTo(UPDATED_AADHAR_CARD);
        assertThat(testMember.getPanCard()).isEqualTo(UPDATED_PAN_CARD);
        assertThat(testMember.getRationCard()).isEqualTo(DEFAULT_RATION_CARD);
        assertThat(testMember.getFamilyMemberCount()).isEqualTo(UPDATED_FAMILY_MEMBER_COUNT);
        assertThat(testMember.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testMember.getApplicationDate()).isEqualTo(DEFAULT_APPLICATION_DATE);
        assertThat(testMember.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMember.getKmpStatus()).isEqualTo(DEFAULT_KMP_STATUS);
        assertThat(testMember.getBoardResolutionNo()).isEqualTo(UPDATED_BOARD_RESOLUTION_NO);
        assertThat(testMember.getBoardResolutionDate()).isEqualTo(UPDATED_BOARD_RESOLUTION_DATE);
        assertThat(testMember.getLoanStatus()).isEqualTo(DEFAULT_LOAN_STATUS);
        assertThat(testMember.getMemberType()).isEqualTo(UPDATED_MEMBER_TYPE);
        assertThat(testMember.getIsactive()).isEqualTo(DEFAULT_ISACTIVE);
        assertThat(testMember.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMember.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMember.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMember.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateMemberWithPatch() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member using partial update
        Member partialUpdatedMember = new Member();
        partialUpdatedMember.setId(member.getId());

        partialUpdatedMember
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .memberUniqueId(UPDATED_MEMBER_UNIQUE_ID)
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .gender(UPDATED_GENDER)
            .dob(UPDATED_DOB)
            .email(UPDATED_EMAIL)
            .mobileNo(UPDATED_MOBILE_NO)
            .religion(UPDATED_RELIGION)
            .category(UPDATED_CATEGORY)
            .cast(UPDATED_CAST)
            .aadharCard(UPDATED_AADHAR_CARD)
            .panCard(UPDATED_PAN_CARD)
            .rationCard(UPDATED_RATION_CARD)
            .familyMemberCount(UPDATED_FAMILY_MEMBER_COUNT)
            .occupation(UPDATED_OCCUPATION)
            .applicationDate(UPDATED_APPLICATION_DATE)
            .status(UPDATED_STATUS)
            .kmpStatus(UPDATED_KMP_STATUS)
            .boardResolutionNo(UPDATED_BOARD_RESOLUTION_NO)
            .boardResolutionDate(UPDATED_BOARD_RESOLUTION_DATE)
            .loanStatus(UPDATED_LOAN_STATUS)
            .memberType(UPDATED_MEMBER_TYPE)
            .isactive(UPDATED_ISACTIVE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON);

        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMember.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMember))
            )
            .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testMember.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testMember.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testMember.getMemberUniqueId()).isEqualTo(UPDATED_MEMBER_UNIQUE_ID);
        assertThat(testMember.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testMember.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testMember.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testMember.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testMember.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMember.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testMember.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testMember.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testMember.getCast()).isEqualTo(UPDATED_CAST);
        assertThat(testMember.getAadharCard()).isEqualTo(UPDATED_AADHAR_CARD);
        assertThat(testMember.getPanCard()).isEqualTo(UPDATED_PAN_CARD);
        assertThat(testMember.getRationCard()).isEqualTo(UPDATED_RATION_CARD);
        assertThat(testMember.getFamilyMemberCount()).isEqualTo(UPDATED_FAMILY_MEMBER_COUNT);
        assertThat(testMember.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testMember.getApplicationDate()).isEqualTo(UPDATED_APPLICATION_DATE);
        assertThat(testMember.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMember.getKmpStatus()).isEqualTo(UPDATED_KMP_STATUS);
        assertThat(testMember.getBoardResolutionNo()).isEqualTo(UPDATED_BOARD_RESOLUTION_NO);
        assertThat(testMember.getBoardResolutionDate()).isEqualTo(UPDATED_BOARD_RESOLUTION_DATE);
        assertThat(testMember.getLoanStatus()).isEqualTo(UPDATED_LOAN_STATUS);
        assertThat(testMember.getMemberType()).isEqualTo(UPDATED_MEMBER_TYPE);
        assertThat(testMember.getIsactive()).isEqualTo(UPDATED_ISACTIVE);
        assertThat(testMember.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMember.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMember.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMember.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, memberDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeDelete = memberRepository.findAll().size();

        // Delete the member
        restMemberMockMvc
            .perform(delete(ENTITY_API_URL_ID, member.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.vks.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vks.IntegrationTest;
import com.vks.domain.Member;
import com.vks.domain.Nominee;
import com.vks.repository.NomineeRepository;
import com.vks.service.criteria.NomineeCriteria;
import com.vks.service.dto.NomineeDTO;
import com.vks.service.mapper.NomineeMapper;
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
 * Integration tests for the {@link NomineeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NomineeResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FATHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FATHER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MOTHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MOTHER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GUARDIAN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GUARDIAN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DOB = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_AADHAR_CARD = "AAAAAAAAAA";
    private static final String UPDATED_AADHAR_CARD = "BBBBBBBBBB";

    private static final Instant DEFAULT_NOMINEE_DECLARE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NOMINEE_DECLARE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RELATION = "AAAAAAAAAA";
    private static final String UPDATED_RELATION = "BBBBBBBBBB";

    private static final String DEFAULT_PERMANENT_ADDR = "AAAAAAAAAA";
    private static final String UPDATED_PERMANENT_ADDR = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/nominees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NomineeRepository nomineeRepository;

    @Autowired
    private NomineeMapper nomineeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNomineeMockMvc;

    private Nominee nominee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nominee createEntity(EntityManager em) {
        Nominee nominee = new Nominee()
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .fatherName(DEFAULT_FATHER_NAME)
            .motherName(DEFAULT_MOTHER_NAME)
            .guardianName(DEFAULT_GUARDIAN_NAME)
            .gender(DEFAULT_GENDER)
            .dob(DEFAULT_DOB)
            .aadharCard(DEFAULT_AADHAR_CARD)
            .nomineeDeclareDate(DEFAULT_NOMINEE_DECLARE_DATE)
            .relation(DEFAULT_RELATION)
            .permanentAddr(DEFAULT_PERMANENT_ADDR)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED);
        return nominee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nominee createUpdatedEntity(EntityManager em) {
        Nominee nominee = new Nominee()
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .guardianName(UPDATED_GUARDIAN_NAME)
            .gender(UPDATED_GENDER)
            .dob(UPDATED_DOB)
            .aadharCard(UPDATED_AADHAR_CARD)
            .nomineeDeclareDate(UPDATED_NOMINEE_DECLARE_DATE)
            .relation(UPDATED_RELATION)
            .permanentAddr(UPDATED_PERMANENT_ADDR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED);
        return nominee;
    }

    @BeforeEach
    public void initTest() {
        nominee = createEntity(em);
    }

    @Test
    @Transactional
    void createNominee() throws Exception {
        int databaseSizeBeforeCreate = nomineeRepository.findAll().size();
        // Create the Nominee
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);
        restNomineeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nomineeDTO)))
            .andExpect(status().isCreated());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeCreate + 1);
        Nominee testNominee = nomineeList.get(nomineeList.size() - 1);
        assertThat(testNominee.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testNominee.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testNominee.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testNominee.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testNominee.getMotherName()).isEqualTo(DEFAULT_MOTHER_NAME);
        assertThat(testNominee.getGuardianName()).isEqualTo(DEFAULT_GUARDIAN_NAME);
        assertThat(testNominee.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testNominee.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testNominee.getAadharCard()).isEqualTo(DEFAULT_AADHAR_CARD);
        assertThat(testNominee.getNomineeDeclareDate()).isEqualTo(DEFAULT_NOMINEE_DECLARE_DATE);
        assertThat(testNominee.getRelation()).isEqualTo(DEFAULT_RELATION);
        assertThat(testNominee.getPermanentAddr()).isEqualTo(DEFAULT_PERMANENT_ADDR);
        assertThat(testNominee.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testNominee.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testNominee.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testNominee.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testNominee.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void createNomineeWithExistingId() throws Exception {
        // Create the Nominee with an existing ID
        nominee.setId(1L);
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);

        int databaseSizeBeforeCreate = nomineeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNomineeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nomineeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNominees() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList
        restNomineeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nominee.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME)))
            .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME)))
            .andExpect(jsonPath("$.[*].guardianName").value(hasItem(DEFAULT_GUARDIAN_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].aadharCard").value(hasItem(DEFAULT_AADHAR_CARD)))
            .andExpect(jsonPath("$.[*].nomineeDeclareDate").value(hasItem(DEFAULT_NOMINEE_DECLARE_DATE.toString())))
            .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION)))
            .andExpect(jsonPath("$.[*].permanentAddr").value(hasItem(DEFAULT_PERMANENT_ADDR)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getNominee() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get the nominee
        restNomineeMockMvc
            .perform(get(ENTITY_API_URL_ID, nominee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nominee.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.fatherName").value(DEFAULT_FATHER_NAME))
            .andExpect(jsonPath("$.motherName").value(DEFAULT_MOTHER_NAME))
            .andExpect(jsonPath("$.guardianName").value(DEFAULT_GUARDIAN_NAME))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.aadharCard").value(DEFAULT_AADHAR_CARD))
            .andExpect(jsonPath("$.nomineeDeclareDate").value(DEFAULT_NOMINEE_DECLARE_DATE.toString()))
            .andExpect(jsonPath("$.relation").value(DEFAULT_RELATION))
            .andExpect(jsonPath("$.permanentAddr").value(DEFAULT_PERMANENT_ADDR))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getNomineesByIdFiltering() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        Long id = nominee.getId();

        defaultNomineeShouldBeFound("id.equals=" + id);
        defaultNomineeShouldNotBeFound("id.notEquals=" + id);

        defaultNomineeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNomineeShouldNotBeFound("id.greaterThan=" + id);

        defaultNomineeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNomineeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNomineesByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where firstName equals to DEFAULT_FIRST_NAME
        defaultNomineeShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the nomineeList where firstName equals to UPDATED_FIRST_NAME
        defaultNomineeShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultNomineeShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the nomineeList where firstName equals to UPDATED_FIRST_NAME
        defaultNomineeShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where firstName is not null
        defaultNomineeShouldBeFound("firstName.specified=true");

        // Get all the nomineeList where firstName is null
        defaultNomineeShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllNomineesByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where firstName contains DEFAULT_FIRST_NAME
        defaultNomineeShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the nomineeList where firstName contains UPDATED_FIRST_NAME
        defaultNomineeShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where firstName does not contain DEFAULT_FIRST_NAME
        defaultNomineeShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the nomineeList where firstName does not contain UPDATED_FIRST_NAME
        defaultNomineeShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByMiddleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where middleName equals to DEFAULT_MIDDLE_NAME
        defaultNomineeShouldBeFound("middleName.equals=" + DEFAULT_MIDDLE_NAME);

        // Get all the nomineeList where middleName equals to UPDATED_MIDDLE_NAME
        defaultNomineeShouldNotBeFound("middleName.equals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByMiddleNameIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where middleName in DEFAULT_MIDDLE_NAME or UPDATED_MIDDLE_NAME
        defaultNomineeShouldBeFound("middleName.in=" + DEFAULT_MIDDLE_NAME + "," + UPDATED_MIDDLE_NAME);

        // Get all the nomineeList where middleName equals to UPDATED_MIDDLE_NAME
        defaultNomineeShouldNotBeFound("middleName.in=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByMiddleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where middleName is not null
        defaultNomineeShouldBeFound("middleName.specified=true");

        // Get all the nomineeList where middleName is null
        defaultNomineeShouldNotBeFound("middleName.specified=false");
    }

    @Test
    @Transactional
    void getAllNomineesByMiddleNameContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where middleName contains DEFAULT_MIDDLE_NAME
        defaultNomineeShouldBeFound("middleName.contains=" + DEFAULT_MIDDLE_NAME);

        // Get all the nomineeList where middleName contains UPDATED_MIDDLE_NAME
        defaultNomineeShouldNotBeFound("middleName.contains=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByMiddleNameNotContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where middleName does not contain DEFAULT_MIDDLE_NAME
        defaultNomineeShouldNotBeFound("middleName.doesNotContain=" + DEFAULT_MIDDLE_NAME);

        // Get all the nomineeList where middleName does not contain UPDATED_MIDDLE_NAME
        defaultNomineeShouldBeFound("middleName.doesNotContain=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastName equals to DEFAULT_LAST_NAME
        defaultNomineeShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the nomineeList where lastName equals to UPDATED_LAST_NAME
        defaultNomineeShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultNomineeShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the nomineeList where lastName equals to UPDATED_LAST_NAME
        defaultNomineeShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastName is not null
        defaultNomineeShouldBeFound("lastName.specified=true");

        // Get all the nomineeList where lastName is null
        defaultNomineeShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllNomineesByLastNameContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastName contains DEFAULT_LAST_NAME
        defaultNomineeShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the nomineeList where lastName contains UPDATED_LAST_NAME
        defaultNomineeShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastName does not contain DEFAULT_LAST_NAME
        defaultNomineeShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the nomineeList where lastName does not contain UPDATED_LAST_NAME
        defaultNomineeShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByFatherNameIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where fatherName equals to DEFAULT_FATHER_NAME
        defaultNomineeShouldBeFound("fatherName.equals=" + DEFAULT_FATHER_NAME);

        // Get all the nomineeList where fatherName equals to UPDATED_FATHER_NAME
        defaultNomineeShouldNotBeFound("fatherName.equals=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByFatherNameIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where fatherName in DEFAULT_FATHER_NAME or UPDATED_FATHER_NAME
        defaultNomineeShouldBeFound("fatherName.in=" + DEFAULT_FATHER_NAME + "," + UPDATED_FATHER_NAME);

        // Get all the nomineeList where fatherName equals to UPDATED_FATHER_NAME
        defaultNomineeShouldNotBeFound("fatherName.in=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByFatherNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where fatherName is not null
        defaultNomineeShouldBeFound("fatherName.specified=true");

        // Get all the nomineeList where fatherName is null
        defaultNomineeShouldNotBeFound("fatherName.specified=false");
    }

    @Test
    @Transactional
    void getAllNomineesByFatherNameContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where fatherName contains DEFAULT_FATHER_NAME
        defaultNomineeShouldBeFound("fatherName.contains=" + DEFAULT_FATHER_NAME);

        // Get all the nomineeList where fatherName contains UPDATED_FATHER_NAME
        defaultNomineeShouldNotBeFound("fatherName.contains=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByFatherNameNotContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where fatherName does not contain DEFAULT_FATHER_NAME
        defaultNomineeShouldNotBeFound("fatherName.doesNotContain=" + DEFAULT_FATHER_NAME);

        // Get all the nomineeList where fatherName does not contain UPDATED_FATHER_NAME
        defaultNomineeShouldBeFound("fatherName.doesNotContain=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByMotherNameIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where motherName equals to DEFAULT_MOTHER_NAME
        defaultNomineeShouldBeFound("motherName.equals=" + DEFAULT_MOTHER_NAME);

        // Get all the nomineeList where motherName equals to UPDATED_MOTHER_NAME
        defaultNomineeShouldNotBeFound("motherName.equals=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByMotherNameIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where motherName in DEFAULT_MOTHER_NAME or UPDATED_MOTHER_NAME
        defaultNomineeShouldBeFound("motherName.in=" + DEFAULT_MOTHER_NAME + "," + UPDATED_MOTHER_NAME);

        // Get all the nomineeList where motherName equals to UPDATED_MOTHER_NAME
        defaultNomineeShouldNotBeFound("motherName.in=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByMotherNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where motherName is not null
        defaultNomineeShouldBeFound("motherName.specified=true");

        // Get all the nomineeList where motherName is null
        defaultNomineeShouldNotBeFound("motherName.specified=false");
    }

    @Test
    @Transactional
    void getAllNomineesByMotherNameContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where motherName contains DEFAULT_MOTHER_NAME
        defaultNomineeShouldBeFound("motherName.contains=" + DEFAULT_MOTHER_NAME);

        // Get all the nomineeList where motherName contains UPDATED_MOTHER_NAME
        defaultNomineeShouldNotBeFound("motherName.contains=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByMotherNameNotContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where motherName does not contain DEFAULT_MOTHER_NAME
        defaultNomineeShouldNotBeFound("motherName.doesNotContain=" + DEFAULT_MOTHER_NAME);

        // Get all the nomineeList where motherName does not contain UPDATED_MOTHER_NAME
        defaultNomineeShouldBeFound("motherName.doesNotContain=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByGuardianNameIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where guardianName equals to DEFAULT_GUARDIAN_NAME
        defaultNomineeShouldBeFound("guardianName.equals=" + DEFAULT_GUARDIAN_NAME);

        // Get all the nomineeList where guardianName equals to UPDATED_GUARDIAN_NAME
        defaultNomineeShouldNotBeFound("guardianName.equals=" + UPDATED_GUARDIAN_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByGuardianNameIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where guardianName in DEFAULT_GUARDIAN_NAME or UPDATED_GUARDIAN_NAME
        defaultNomineeShouldBeFound("guardianName.in=" + DEFAULT_GUARDIAN_NAME + "," + UPDATED_GUARDIAN_NAME);

        // Get all the nomineeList where guardianName equals to UPDATED_GUARDIAN_NAME
        defaultNomineeShouldNotBeFound("guardianName.in=" + UPDATED_GUARDIAN_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByGuardianNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where guardianName is not null
        defaultNomineeShouldBeFound("guardianName.specified=true");

        // Get all the nomineeList where guardianName is null
        defaultNomineeShouldNotBeFound("guardianName.specified=false");
    }

    @Test
    @Transactional
    void getAllNomineesByGuardianNameContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where guardianName contains DEFAULT_GUARDIAN_NAME
        defaultNomineeShouldBeFound("guardianName.contains=" + DEFAULT_GUARDIAN_NAME);

        // Get all the nomineeList where guardianName contains UPDATED_GUARDIAN_NAME
        defaultNomineeShouldNotBeFound("guardianName.contains=" + UPDATED_GUARDIAN_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByGuardianNameNotContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where guardianName does not contain DEFAULT_GUARDIAN_NAME
        defaultNomineeShouldNotBeFound("guardianName.doesNotContain=" + DEFAULT_GUARDIAN_NAME);

        // Get all the nomineeList where guardianName does not contain UPDATED_GUARDIAN_NAME
        defaultNomineeShouldBeFound("guardianName.doesNotContain=" + UPDATED_GUARDIAN_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where gender equals to DEFAULT_GENDER
        defaultNomineeShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the nomineeList where gender equals to UPDATED_GENDER
        defaultNomineeShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllNomineesByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultNomineeShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the nomineeList where gender equals to UPDATED_GENDER
        defaultNomineeShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllNomineesByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where gender is not null
        defaultNomineeShouldBeFound("gender.specified=true");

        // Get all the nomineeList where gender is null
        defaultNomineeShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    void getAllNomineesByGenderContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where gender contains DEFAULT_GENDER
        defaultNomineeShouldBeFound("gender.contains=" + DEFAULT_GENDER);

        // Get all the nomineeList where gender contains UPDATED_GENDER
        defaultNomineeShouldNotBeFound("gender.contains=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllNomineesByGenderNotContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where gender does not contain DEFAULT_GENDER
        defaultNomineeShouldNotBeFound("gender.doesNotContain=" + DEFAULT_GENDER);

        // Get all the nomineeList where gender does not contain UPDATED_GENDER
        defaultNomineeShouldBeFound("gender.doesNotContain=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllNomineesByDobIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where dob equals to DEFAULT_DOB
        defaultNomineeShouldBeFound("dob.equals=" + DEFAULT_DOB);

        // Get all the nomineeList where dob equals to UPDATED_DOB
        defaultNomineeShouldNotBeFound("dob.equals=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllNomineesByDobIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where dob in DEFAULT_DOB or UPDATED_DOB
        defaultNomineeShouldBeFound("dob.in=" + DEFAULT_DOB + "," + UPDATED_DOB);

        // Get all the nomineeList where dob equals to UPDATED_DOB
        defaultNomineeShouldNotBeFound("dob.in=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllNomineesByDobIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where dob is not null
        defaultNomineeShouldBeFound("dob.specified=true");

        // Get all the nomineeList where dob is null
        defaultNomineeShouldNotBeFound("dob.specified=false");
    }

    @Test
    @Transactional
    void getAllNomineesByDobIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where dob is greater than or equal to DEFAULT_DOB
        defaultNomineeShouldBeFound("dob.greaterThanOrEqual=" + DEFAULT_DOB);

        // Get all the nomineeList where dob is greater than or equal to UPDATED_DOB
        defaultNomineeShouldNotBeFound("dob.greaterThanOrEqual=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllNomineesByDobIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where dob is less than or equal to DEFAULT_DOB
        defaultNomineeShouldBeFound("dob.lessThanOrEqual=" + DEFAULT_DOB);

        // Get all the nomineeList where dob is less than or equal to SMALLER_DOB
        defaultNomineeShouldNotBeFound("dob.lessThanOrEqual=" + SMALLER_DOB);
    }

    @Test
    @Transactional
    void getAllNomineesByDobIsLessThanSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where dob is less than DEFAULT_DOB
        defaultNomineeShouldNotBeFound("dob.lessThan=" + DEFAULT_DOB);

        // Get all the nomineeList where dob is less than UPDATED_DOB
        defaultNomineeShouldBeFound("dob.lessThan=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllNomineesByDobIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where dob is greater than DEFAULT_DOB
        defaultNomineeShouldNotBeFound("dob.greaterThan=" + DEFAULT_DOB);

        // Get all the nomineeList where dob is greater than SMALLER_DOB
        defaultNomineeShouldBeFound("dob.greaterThan=" + SMALLER_DOB);
    }

    @Test
    @Transactional
    void getAllNomineesByAadharCardIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where aadharCard equals to DEFAULT_AADHAR_CARD
        defaultNomineeShouldBeFound("aadharCard.equals=" + DEFAULT_AADHAR_CARD);

        // Get all the nomineeList where aadharCard equals to UPDATED_AADHAR_CARD
        defaultNomineeShouldNotBeFound("aadharCard.equals=" + UPDATED_AADHAR_CARD);
    }

    @Test
    @Transactional
    void getAllNomineesByAadharCardIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where aadharCard in DEFAULT_AADHAR_CARD or UPDATED_AADHAR_CARD
        defaultNomineeShouldBeFound("aadharCard.in=" + DEFAULT_AADHAR_CARD + "," + UPDATED_AADHAR_CARD);

        // Get all the nomineeList where aadharCard equals to UPDATED_AADHAR_CARD
        defaultNomineeShouldNotBeFound("aadharCard.in=" + UPDATED_AADHAR_CARD);
    }

    @Test
    @Transactional
    void getAllNomineesByAadharCardIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where aadharCard is not null
        defaultNomineeShouldBeFound("aadharCard.specified=true");

        // Get all the nomineeList where aadharCard is null
        defaultNomineeShouldNotBeFound("aadharCard.specified=false");
    }

    @Test
    @Transactional
    void getAllNomineesByAadharCardContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where aadharCard contains DEFAULT_AADHAR_CARD
        defaultNomineeShouldBeFound("aadharCard.contains=" + DEFAULT_AADHAR_CARD);

        // Get all the nomineeList where aadharCard contains UPDATED_AADHAR_CARD
        defaultNomineeShouldNotBeFound("aadharCard.contains=" + UPDATED_AADHAR_CARD);
    }

    @Test
    @Transactional
    void getAllNomineesByAadharCardNotContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where aadharCard does not contain DEFAULT_AADHAR_CARD
        defaultNomineeShouldNotBeFound("aadharCard.doesNotContain=" + DEFAULT_AADHAR_CARD);

        // Get all the nomineeList where aadharCard does not contain UPDATED_AADHAR_CARD
        defaultNomineeShouldBeFound("aadharCard.doesNotContain=" + UPDATED_AADHAR_CARD);
    }

    @Test
    @Transactional
    void getAllNomineesByNomineeDeclareDateIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where nomineeDeclareDate equals to DEFAULT_NOMINEE_DECLARE_DATE
        defaultNomineeShouldBeFound("nomineeDeclareDate.equals=" + DEFAULT_NOMINEE_DECLARE_DATE);

        // Get all the nomineeList where nomineeDeclareDate equals to UPDATED_NOMINEE_DECLARE_DATE
        defaultNomineeShouldNotBeFound("nomineeDeclareDate.equals=" + UPDATED_NOMINEE_DECLARE_DATE);
    }

    @Test
    @Transactional
    void getAllNomineesByNomineeDeclareDateIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where nomineeDeclareDate in DEFAULT_NOMINEE_DECLARE_DATE or UPDATED_NOMINEE_DECLARE_DATE
        defaultNomineeShouldBeFound("nomineeDeclareDate.in=" + DEFAULT_NOMINEE_DECLARE_DATE + "," + UPDATED_NOMINEE_DECLARE_DATE);

        // Get all the nomineeList where nomineeDeclareDate equals to UPDATED_NOMINEE_DECLARE_DATE
        defaultNomineeShouldNotBeFound("nomineeDeclareDate.in=" + UPDATED_NOMINEE_DECLARE_DATE);
    }

    @Test
    @Transactional
    void getAllNomineesByNomineeDeclareDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where nomineeDeclareDate is not null
        defaultNomineeShouldBeFound("nomineeDeclareDate.specified=true");

        // Get all the nomineeList where nomineeDeclareDate is null
        defaultNomineeShouldNotBeFound("nomineeDeclareDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNomineesByRelationIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where relation equals to DEFAULT_RELATION
        defaultNomineeShouldBeFound("relation.equals=" + DEFAULT_RELATION);

        // Get all the nomineeList where relation equals to UPDATED_RELATION
        defaultNomineeShouldNotBeFound("relation.equals=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    void getAllNomineesByRelationIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where relation in DEFAULT_RELATION or UPDATED_RELATION
        defaultNomineeShouldBeFound("relation.in=" + DEFAULT_RELATION + "," + UPDATED_RELATION);

        // Get all the nomineeList where relation equals to UPDATED_RELATION
        defaultNomineeShouldNotBeFound("relation.in=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    void getAllNomineesByRelationIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where relation is not null
        defaultNomineeShouldBeFound("relation.specified=true");

        // Get all the nomineeList where relation is null
        defaultNomineeShouldNotBeFound("relation.specified=false");
    }

    @Test
    @Transactional
    void getAllNomineesByRelationContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where relation contains DEFAULT_RELATION
        defaultNomineeShouldBeFound("relation.contains=" + DEFAULT_RELATION);

        // Get all the nomineeList where relation contains UPDATED_RELATION
        defaultNomineeShouldNotBeFound("relation.contains=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    void getAllNomineesByRelationNotContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where relation does not contain DEFAULT_RELATION
        defaultNomineeShouldNotBeFound("relation.doesNotContain=" + DEFAULT_RELATION);

        // Get all the nomineeList where relation does not contain UPDATED_RELATION
        defaultNomineeShouldBeFound("relation.doesNotContain=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    void getAllNomineesByPermanentAddrIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where permanentAddr equals to DEFAULT_PERMANENT_ADDR
        defaultNomineeShouldBeFound("permanentAddr.equals=" + DEFAULT_PERMANENT_ADDR);

        // Get all the nomineeList where permanentAddr equals to UPDATED_PERMANENT_ADDR
        defaultNomineeShouldNotBeFound("permanentAddr.equals=" + UPDATED_PERMANENT_ADDR);
    }

    @Test
    @Transactional
    void getAllNomineesByPermanentAddrIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where permanentAddr in DEFAULT_PERMANENT_ADDR or UPDATED_PERMANENT_ADDR
        defaultNomineeShouldBeFound("permanentAddr.in=" + DEFAULT_PERMANENT_ADDR + "," + UPDATED_PERMANENT_ADDR);

        // Get all the nomineeList where permanentAddr equals to UPDATED_PERMANENT_ADDR
        defaultNomineeShouldNotBeFound("permanentAddr.in=" + UPDATED_PERMANENT_ADDR);
    }

    @Test
    @Transactional
    void getAllNomineesByPermanentAddrIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where permanentAddr is not null
        defaultNomineeShouldBeFound("permanentAddr.specified=true");

        // Get all the nomineeList where permanentAddr is null
        defaultNomineeShouldNotBeFound("permanentAddr.specified=false");
    }

    @Test
    @Transactional
    void getAllNomineesByPermanentAddrContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where permanentAddr contains DEFAULT_PERMANENT_ADDR
        defaultNomineeShouldBeFound("permanentAddr.contains=" + DEFAULT_PERMANENT_ADDR);

        // Get all the nomineeList where permanentAddr contains UPDATED_PERMANENT_ADDR
        defaultNomineeShouldNotBeFound("permanentAddr.contains=" + UPDATED_PERMANENT_ADDR);
    }

    @Test
    @Transactional
    void getAllNomineesByPermanentAddrNotContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where permanentAddr does not contain DEFAULT_PERMANENT_ADDR
        defaultNomineeShouldNotBeFound("permanentAddr.doesNotContain=" + DEFAULT_PERMANENT_ADDR);

        // Get all the nomineeList where permanentAddr does not contain UPDATED_PERMANENT_ADDR
        defaultNomineeShouldBeFound("permanentAddr.doesNotContain=" + UPDATED_PERMANENT_ADDR);
    }

    @Test
    @Transactional
    void getAllNomineesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultNomineeShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the nomineeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultNomineeShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllNomineesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultNomineeShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the nomineeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultNomineeShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllNomineesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastModified is not null
        defaultNomineeShouldBeFound("lastModified.specified=true");

        // Get all the nomineeList where lastModified is null
        defaultNomineeShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllNomineesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultNomineeShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the nomineeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultNomineeShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllNomineesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultNomineeShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the nomineeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultNomineeShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllNomineesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastModifiedBy is not null
        defaultNomineeShouldBeFound("lastModifiedBy.specified=true");

        // Get all the nomineeList where lastModifiedBy is null
        defaultNomineeShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNomineesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultNomineeShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the nomineeList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultNomineeShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllNomineesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultNomineeShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the nomineeList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultNomineeShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllNomineesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where createdBy equals to DEFAULT_CREATED_BY
        defaultNomineeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the nomineeList where createdBy equals to UPDATED_CREATED_BY
        defaultNomineeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNomineesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultNomineeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the nomineeList where createdBy equals to UPDATED_CREATED_BY
        defaultNomineeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNomineesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where createdBy is not null
        defaultNomineeShouldBeFound("createdBy.specified=true");

        // Get all the nomineeList where createdBy is null
        defaultNomineeShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNomineesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where createdBy contains DEFAULT_CREATED_BY
        defaultNomineeShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the nomineeList where createdBy contains UPDATED_CREATED_BY
        defaultNomineeShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNomineesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where createdBy does not contain DEFAULT_CREATED_BY
        defaultNomineeShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the nomineeList where createdBy does not contain UPDATED_CREATED_BY
        defaultNomineeShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNomineesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where createdOn equals to DEFAULT_CREATED_ON
        defaultNomineeShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the nomineeList where createdOn equals to UPDATED_CREATED_ON
        defaultNomineeShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllNomineesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultNomineeShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the nomineeList where createdOn equals to UPDATED_CREATED_ON
        defaultNomineeShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllNomineesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where createdOn is not null
        defaultNomineeShouldBeFound("createdOn.specified=true");

        // Get all the nomineeList where createdOn is null
        defaultNomineeShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllNomineesByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where isDeleted equals to DEFAULT_IS_DELETED
        defaultNomineeShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the nomineeList where isDeleted equals to UPDATED_IS_DELETED
        defaultNomineeShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllNomineesByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultNomineeShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the nomineeList where isDeleted equals to UPDATED_IS_DELETED
        defaultNomineeShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllNomineesByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where isDeleted is not null
        defaultNomineeShouldBeFound("isDeleted.specified=true");

        // Get all the nomineeList where isDeleted is null
        defaultNomineeShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllNomineesByMemberIsEqualToSomething() throws Exception {
        Member member;
        if (TestUtil.findAll(em, Member.class).isEmpty()) {
            nomineeRepository.saveAndFlush(nominee);
            member = MemberResourceIT.createEntity(em);
        } else {
            member = TestUtil.findAll(em, Member.class).get(0);
        }
        em.persist(member);
        em.flush();
        nominee.setMember(member);
        nomineeRepository.saveAndFlush(nominee);
        Long memberId = member.getId();

        // Get all the nomineeList where member equals to memberId
        defaultNomineeShouldBeFound("memberId.equals=" + memberId);

        // Get all the nomineeList where member equals to (memberId + 1)
        defaultNomineeShouldNotBeFound("memberId.equals=" + (memberId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNomineeShouldBeFound(String filter) throws Exception {
        restNomineeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nominee.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME)))
            .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME)))
            .andExpect(jsonPath("$.[*].guardianName").value(hasItem(DEFAULT_GUARDIAN_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].aadharCard").value(hasItem(DEFAULT_AADHAR_CARD)))
            .andExpect(jsonPath("$.[*].nomineeDeclareDate").value(hasItem(DEFAULT_NOMINEE_DECLARE_DATE.toString())))
            .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION)))
            .andExpect(jsonPath("$.[*].permanentAddr").value(hasItem(DEFAULT_PERMANENT_ADDR)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())));

        // Check, that the count call also returns 1
        restNomineeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNomineeShouldNotBeFound(String filter) throws Exception {
        restNomineeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNomineeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNominee() throws Exception {
        // Get the nominee
        restNomineeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNominee() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        int databaseSizeBeforeUpdate = nomineeRepository.findAll().size();

        // Update the nominee
        Nominee updatedNominee = nomineeRepository.findById(nominee.getId()).get();
        // Disconnect from session so that the updates on updatedNominee are not directly saved in db
        em.detach(updatedNominee);
        updatedNominee
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .guardianName(UPDATED_GUARDIAN_NAME)
            .gender(UPDATED_GENDER)
            .dob(UPDATED_DOB)
            .aadharCard(UPDATED_AADHAR_CARD)
            .nomineeDeclareDate(UPDATED_NOMINEE_DECLARE_DATE)
            .relation(UPDATED_RELATION)
            .permanentAddr(UPDATED_PERMANENT_ADDR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED);
        NomineeDTO nomineeDTO = nomineeMapper.toDto(updatedNominee);

        restNomineeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nomineeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nomineeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeUpdate);
        Nominee testNominee = nomineeList.get(nomineeList.size() - 1);
        assertThat(testNominee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testNominee.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testNominee.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testNominee.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testNominee.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testNominee.getGuardianName()).isEqualTo(UPDATED_GUARDIAN_NAME);
        assertThat(testNominee.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testNominee.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testNominee.getAadharCard()).isEqualTo(UPDATED_AADHAR_CARD);
        assertThat(testNominee.getNomineeDeclareDate()).isEqualTo(UPDATED_NOMINEE_DECLARE_DATE);
        assertThat(testNominee.getRelation()).isEqualTo(UPDATED_RELATION);
        assertThat(testNominee.getPermanentAddr()).isEqualTo(UPDATED_PERMANENT_ADDR);
        assertThat(testNominee.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testNominee.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testNominee.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testNominee.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testNominee.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingNominee() throws Exception {
        int databaseSizeBeforeUpdate = nomineeRepository.findAll().size();
        nominee.setId(count.incrementAndGet());

        // Create the Nominee
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNomineeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nomineeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nomineeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNominee() throws Exception {
        int databaseSizeBeforeUpdate = nomineeRepository.findAll().size();
        nominee.setId(count.incrementAndGet());

        // Create the Nominee
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNomineeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nomineeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNominee() throws Exception {
        int databaseSizeBeforeUpdate = nomineeRepository.findAll().size();
        nominee.setId(count.incrementAndGet());

        // Create the Nominee
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNomineeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nomineeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNomineeWithPatch() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        int databaseSizeBeforeUpdate = nomineeRepository.findAll().size();

        // Update the nominee using partial update
        Nominee partialUpdatedNominee = new Nominee();
        partialUpdatedNominee.setId(nominee.getId());

        partialUpdatedNominee
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .fatherName(UPDATED_FATHER_NAME)
            .dob(UPDATED_DOB)
            .aadharCard(UPDATED_AADHAR_CARD)
            .nomineeDeclareDate(UPDATED_NOMINEE_DECLARE_DATE)
            .relation(UPDATED_RELATION)
            .permanentAddr(UPDATED_PERMANENT_ADDR)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED);

        restNomineeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNominee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNominee))
            )
            .andExpect(status().isOk());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeUpdate);
        Nominee testNominee = nomineeList.get(nomineeList.size() - 1);
        assertThat(testNominee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testNominee.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testNominee.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testNominee.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testNominee.getMotherName()).isEqualTo(DEFAULT_MOTHER_NAME);
        assertThat(testNominee.getGuardianName()).isEqualTo(DEFAULT_GUARDIAN_NAME);
        assertThat(testNominee.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testNominee.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testNominee.getAadharCard()).isEqualTo(UPDATED_AADHAR_CARD);
        assertThat(testNominee.getNomineeDeclareDate()).isEqualTo(UPDATED_NOMINEE_DECLARE_DATE);
        assertThat(testNominee.getRelation()).isEqualTo(UPDATED_RELATION);
        assertThat(testNominee.getPermanentAddr()).isEqualTo(UPDATED_PERMANENT_ADDR);
        assertThat(testNominee.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testNominee.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testNominee.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testNominee.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testNominee.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void fullUpdateNomineeWithPatch() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        int databaseSizeBeforeUpdate = nomineeRepository.findAll().size();

        // Update the nominee using partial update
        Nominee partialUpdatedNominee = new Nominee();
        partialUpdatedNominee.setId(nominee.getId());

        partialUpdatedNominee
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .guardianName(UPDATED_GUARDIAN_NAME)
            .gender(UPDATED_GENDER)
            .dob(UPDATED_DOB)
            .aadharCard(UPDATED_AADHAR_CARD)
            .nomineeDeclareDate(UPDATED_NOMINEE_DECLARE_DATE)
            .relation(UPDATED_RELATION)
            .permanentAddr(UPDATED_PERMANENT_ADDR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED);

        restNomineeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNominee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNominee))
            )
            .andExpect(status().isOk());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeUpdate);
        Nominee testNominee = nomineeList.get(nomineeList.size() - 1);
        assertThat(testNominee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testNominee.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testNominee.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testNominee.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testNominee.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testNominee.getGuardianName()).isEqualTo(UPDATED_GUARDIAN_NAME);
        assertThat(testNominee.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testNominee.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testNominee.getAadharCard()).isEqualTo(UPDATED_AADHAR_CARD);
        assertThat(testNominee.getNomineeDeclareDate()).isEqualTo(UPDATED_NOMINEE_DECLARE_DATE);
        assertThat(testNominee.getRelation()).isEqualTo(UPDATED_RELATION);
        assertThat(testNominee.getPermanentAddr()).isEqualTo(UPDATED_PERMANENT_ADDR);
        assertThat(testNominee.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testNominee.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testNominee.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testNominee.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testNominee.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingNominee() throws Exception {
        int databaseSizeBeforeUpdate = nomineeRepository.findAll().size();
        nominee.setId(count.incrementAndGet());

        // Create the Nominee
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNomineeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nomineeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nomineeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNominee() throws Exception {
        int databaseSizeBeforeUpdate = nomineeRepository.findAll().size();
        nominee.setId(count.incrementAndGet());

        // Create the Nominee
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNomineeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nomineeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNominee() throws Exception {
        int databaseSizeBeforeUpdate = nomineeRepository.findAll().size();
        nominee.setId(count.incrementAndGet());

        // Create the Nominee
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNomineeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nomineeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNominee() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        int databaseSizeBeforeDelete = nomineeRepository.findAll().size();

        // Delete the nominee
        restNomineeMockMvc
            .perform(delete(ENTITY_API_URL_ID, nominee.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

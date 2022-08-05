package com.vks.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vks.IntegrationTest;
import com.vks.domain.ParameterLookup;
import com.vks.domain.enumeration.ParameterType;
import com.vks.repository.ParameterLookupRepository;
import com.vks.service.criteria.ParameterLookupCriteria;
import com.vks.service.dto.ParameterLookupDTO;
import com.vks.service.mapper.ParameterLookupMapper;
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
 * Integration tests for the {@link ParameterLookupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParameterLookupResourceIT {

    private static final ParameterType DEFAULT_PARAMETER_TYPE = ParameterType.ACCOUNT;
    private static final ParameterType UPDATED_PARAMETER_TYPE = ParameterType.RELIGION;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_VALUE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/parameter-lookups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ParameterLookupRepository parameterLookupRepository;

    @Autowired
    private ParameterLookupMapper parameterLookupMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParameterLookupMockMvc;

    private ParameterLookup parameterLookup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParameterLookup createEntity(EntityManager em) {
        ParameterLookup parameterLookup = new ParameterLookup()
            .parameterType(DEFAULT_PARAMETER_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .displayValue(DEFAULT_DISPLAY_VALUE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED);
        return parameterLookup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParameterLookup createUpdatedEntity(EntityManager em) {
        ParameterLookup parameterLookup = new ParameterLookup()
            .parameterType(UPDATED_PARAMETER_TYPE)
            .description(UPDATED_DESCRIPTION)
            .displayValue(UPDATED_DISPLAY_VALUE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED);
        return parameterLookup;
    }

    @BeforeEach
    public void initTest() {
        parameterLookup = createEntity(em);
    }

    @Test
    @Transactional
    void createParameterLookup() throws Exception {
        int databaseSizeBeforeCreate = parameterLookupRepository.findAll().size();
        // Create the ParameterLookup
        ParameterLookupDTO parameterLookupDTO = parameterLookupMapper.toDto(parameterLookup);
        restParameterLookupMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parameterLookupDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ParameterLookup in the database
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeCreate + 1);
        ParameterLookup testParameterLookup = parameterLookupList.get(parameterLookupList.size() - 1);
        assertThat(testParameterLookup.getParameterType()).isEqualTo(DEFAULT_PARAMETER_TYPE);
        assertThat(testParameterLookup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testParameterLookup.getDisplayValue()).isEqualTo(DEFAULT_DISPLAY_VALUE);
        assertThat(testParameterLookup.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testParameterLookup.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testParameterLookup.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testParameterLookup.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testParameterLookup.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void createParameterLookupWithExistingId() throws Exception {
        // Create the ParameterLookup with an existing ID
        parameterLookup.setId(1L);
        ParameterLookupDTO parameterLookupDTO = parameterLookupMapper.toDto(parameterLookup);

        int databaseSizeBeforeCreate = parameterLookupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParameterLookupMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parameterLookupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParameterLookup in the database
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllParameterLookups() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList
        restParameterLookupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parameterLookup.getId().intValue())))
            .andExpect(jsonPath("$.[*].parameterType").value(hasItem(DEFAULT_PARAMETER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].displayValue").value(hasItem(DEFAULT_DISPLAY_VALUE)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getParameterLookup() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get the parameterLookup
        restParameterLookupMockMvc
            .perform(get(ENTITY_API_URL_ID, parameterLookup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parameterLookup.getId().intValue()))
            .andExpect(jsonPath("$.parameterType").value(DEFAULT_PARAMETER_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.displayValue").value(DEFAULT_DISPLAY_VALUE))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getParameterLookupsByIdFiltering() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        Long id = parameterLookup.getId();

        defaultParameterLookupShouldBeFound("id.equals=" + id);
        defaultParameterLookupShouldNotBeFound("id.notEquals=" + id);

        defaultParameterLookupShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultParameterLookupShouldNotBeFound("id.greaterThan=" + id);

        defaultParameterLookupShouldBeFound("id.lessThanOrEqual=" + id);
        defaultParameterLookupShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByParameterTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where parameterType equals to DEFAULT_PARAMETER_TYPE
        defaultParameterLookupShouldBeFound("parameterType.equals=" + DEFAULT_PARAMETER_TYPE);

        // Get all the parameterLookupList where parameterType equals to UPDATED_PARAMETER_TYPE
        defaultParameterLookupShouldNotBeFound("parameterType.equals=" + UPDATED_PARAMETER_TYPE);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByParameterTypeIsInShouldWork() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where parameterType in DEFAULT_PARAMETER_TYPE or UPDATED_PARAMETER_TYPE
        defaultParameterLookupShouldBeFound("parameterType.in=" + DEFAULT_PARAMETER_TYPE + "," + UPDATED_PARAMETER_TYPE);

        // Get all the parameterLookupList where parameterType equals to UPDATED_PARAMETER_TYPE
        defaultParameterLookupShouldNotBeFound("parameterType.in=" + UPDATED_PARAMETER_TYPE);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByParameterTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where parameterType is not null
        defaultParameterLookupShouldBeFound("parameterType.specified=true");

        // Get all the parameterLookupList where parameterType is null
        defaultParameterLookupShouldNotBeFound("parameterType.specified=false");
    }

    @Test
    @Transactional
    void getAllParameterLookupsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where description equals to DEFAULT_DESCRIPTION
        defaultParameterLookupShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the parameterLookupList where description equals to UPDATED_DESCRIPTION
        defaultParameterLookupShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultParameterLookupShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the parameterLookupList where description equals to UPDATED_DESCRIPTION
        defaultParameterLookupShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where description is not null
        defaultParameterLookupShouldBeFound("description.specified=true");

        // Get all the parameterLookupList where description is null
        defaultParameterLookupShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllParameterLookupsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where description contains DEFAULT_DESCRIPTION
        defaultParameterLookupShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the parameterLookupList where description contains UPDATED_DESCRIPTION
        defaultParameterLookupShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where description does not contain DEFAULT_DESCRIPTION
        defaultParameterLookupShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the parameterLookupList where description does not contain UPDATED_DESCRIPTION
        defaultParameterLookupShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByDisplayValueIsEqualToSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where displayValue equals to DEFAULT_DISPLAY_VALUE
        defaultParameterLookupShouldBeFound("displayValue.equals=" + DEFAULT_DISPLAY_VALUE);

        // Get all the parameterLookupList where displayValue equals to UPDATED_DISPLAY_VALUE
        defaultParameterLookupShouldNotBeFound("displayValue.equals=" + UPDATED_DISPLAY_VALUE);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByDisplayValueIsInShouldWork() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where displayValue in DEFAULT_DISPLAY_VALUE or UPDATED_DISPLAY_VALUE
        defaultParameterLookupShouldBeFound("displayValue.in=" + DEFAULT_DISPLAY_VALUE + "," + UPDATED_DISPLAY_VALUE);

        // Get all the parameterLookupList where displayValue equals to UPDATED_DISPLAY_VALUE
        defaultParameterLookupShouldNotBeFound("displayValue.in=" + UPDATED_DISPLAY_VALUE);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByDisplayValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where displayValue is not null
        defaultParameterLookupShouldBeFound("displayValue.specified=true");

        // Get all the parameterLookupList where displayValue is null
        defaultParameterLookupShouldNotBeFound("displayValue.specified=false");
    }

    @Test
    @Transactional
    void getAllParameterLookupsByDisplayValueContainsSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where displayValue contains DEFAULT_DISPLAY_VALUE
        defaultParameterLookupShouldBeFound("displayValue.contains=" + DEFAULT_DISPLAY_VALUE);

        // Get all the parameterLookupList where displayValue contains UPDATED_DISPLAY_VALUE
        defaultParameterLookupShouldNotBeFound("displayValue.contains=" + UPDATED_DISPLAY_VALUE);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByDisplayValueNotContainsSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where displayValue does not contain DEFAULT_DISPLAY_VALUE
        defaultParameterLookupShouldNotBeFound("displayValue.doesNotContain=" + DEFAULT_DISPLAY_VALUE);

        // Get all the parameterLookupList where displayValue does not contain UPDATED_DISPLAY_VALUE
        defaultParameterLookupShouldBeFound("displayValue.doesNotContain=" + UPDATED_DISPLAY_VALUE);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultParameterLookupShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the parameterLookupList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultParameterLookupShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultParameterLookupShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the parameterLookupList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultParameterLookupShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where lastModified is not null
        defaultParameterLookupShouldBeFound("lastModified.specified=true");

        // Get all the parameterLookupList where lastModified is null
        defaultParameterLookupShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllParameterLookupsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultParameterLookupShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the parameterLookupList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultParameterLookupShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultParameterLookupShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the parameterLookupList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultParameterLookupShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where lastModifiedBy is not null
        defaultParameterLookupShouldBeFound("lastModifiedBy.specified=true");

        // Get all the parameterLookupList where lastModifiedBy is null
        defaultParameterLookupShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllParameterLookupsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultParameterLookupShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the parameterLookupList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultParameterLookupShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultParameterLookupShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the parameterLookupList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultParameterLookupShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where createdBy equals to DEFAULT_CREATED_BY
        defaultParameterLookupShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the parameterLookupList where createdBy equals to UPDATED_CREATED_BY
        defaultParameterLookupShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultParameterLookupShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the parameterLookupList where createdBy equals to UPDATED_CREATED_BY
        defaultParameterLookupShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where createdBy is not null
        defaultParameterLookupShouldBeFound("createdBy.specified=true");

        // Get all the parameterLookupList where createdBy is null
        defaultParameterLookupShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllParameterLookupsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where createdBy contains DEFAULT_CREATED_BY
        defaultParameterLookupShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the parameterLookupList where createdBy contains UPDATED_CREATED_BY
        defaultParameterLookupShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where createdBy does not contain DEFAULT_CREATED_BY
        defaultParameterLookupShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the parameterLookupList where createdBy does not contain UPDATED_CREATED_BY
        defaultParameterLookupShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where createdOn equals to DEFAULT_CREATED_ON
        defaultParameterLookupShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the parameterLookupList where createdOn equals to UPDATED_CREATED_ON
        defaultParameterLookupShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultParameterLookupShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the parameterLookupList where createdOn equals to UPDATED_CREATED_ON
        defaultParameterLookupShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where createdOn is not null
        defaultParameterLookupShouldBeFound("createdOn.specified=true");

        // Get all the parameterLookupList where createdOn is null
        defaultParameterLookupShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllParameterLookupsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where isDeleted equals to DEFAULT_IS_DELETED
        defaultParameterLookupShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the parameterLookupList where isDeleted equals to UPDATED_IS_DELETED
        defaultParameterLookupShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultParameterLookupShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the parameterLookupList where isDeleted equals to UPDATED_IS_DELETED
        defaultParameterLookupShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where isDeleted is not null
        defaultParameterLookupShouldBeFound("isDeleted.specified=true");

        // Get all the parameterLookupList where isDeleted is null
        defaultParameterLookupShouldNotBeFound("isDeleted.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultParameterLookupShouldBeFound(String filter) throws Exception {
        restParameterLookupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parameterLookup.getId().intValue())))
            .andExpect(jsonPath("$.[*].parameterType").value(hasItem(DEFAULT_PARAMETER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].displayValue").value(hasItem(DEFAULT_DISPLAY_VALUE)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())));

        // Check, that the count call also returns 1
        restParameterLookupMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultParameterLookupShouldNotBeFound(String filter) throws Exception {
        restParameterLookupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParameterLookupMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingParameterLookup() throws Exception {
        // Get the parameterLookup
        restParameterLookupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewParameterLookup() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        int databaseSizeBeforeUpdate = parameterLookupRepository.findAll().size();

        // Update the parameterLookup
        ParameterLookup updatedParameterLookup = parameterLookupRepository.findById(parameterLookup.getId()).get();
        // Disconnect from session so that the updates on updatedParameterLookup are not directly saved in db
        em.detach(updatedParameterLookup);
        updatedParameterLookup
            .parameterType(UPDATED_PARAMETER_TYPE)
            .description(UPDATED_DESCRIPTION)
            .displayValue(UPDATED_DISPLAY_VALUE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED);
        ParameterLookupDTO parameterLookupDTO = parameterLookupMapper.toDto(updatedParameterLookup);

        restParameterLookupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parameterLookupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parameterLookupDTO))
            )
            .andExpect(status().isOk());

        // Validate the ParameterLookup in the database
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeUpdate);
        ParameterLookup testParameterLookup = parameterLookupList.get(parameterLookupList.size() - 1);
        assertThat(testParameterLookup.getParameterType()).isEqualTo(UPDATED_PARAMETER_TYPE);
        assertThat(testParameterLookup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testParameterLookup.getDisplayValue()).isEqualTo(UPDATED_DISPLAY_VALUE);
        assertThat(testParameterLookup.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testParameterLookup.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testParameterLookup.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testParameterLookup.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testParameterLookup.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingParameterLookup() throws Exception {
        int databaseSizeBeforeUpdate = parameterLookupRepository.findAll().size();
        parameterLookup.setId(count.incrementAndGet());

        // Create the ParameterLookup
        ParameterLookupDTO parameterLookupDTO = parameterLookupMapper.toDto(parameterLookup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParameterLookupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parameterLookupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parameterLookupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParameterLookup in the database
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParameterLookup() throws Exception {
        int databaseSizeBeforeUpdate = parameterLookupRepository.findAll().size();
        parameterLookup.setId(count.incrementAndGet());

        // Create the ParameterLookup
        ParameterLookupDTO parameterLookupDTO = parameterLookupMapper.toDto(parameterLookup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParameterLookupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parameterLookupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParameterLookup in the database
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParameterLookup() throws Exception {
        int databaseSizeBeforeUpdate = parameterLookupRepository.findAll().size();
        parameterLookup.setId(count.incrementAndGet());

        // Create the ParameterLookup
        ParameterLookupDTO parameterLookupDTO = parameterLookupMapper.toDto(parameterLookup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParameterLookupMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parameterLookupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParameterLookup in the database
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParameterLookupWithPatch() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        int databaseSizeBeforeUpdate = parameterLookupRepository.findAll().size();

        // Update the parameterLookup using partial update
        ParameterLookup partialUpdatedParameterLookup = new ParameterLookup();
        partialUpdatedParameterLookup.setId(parameterLookup.getId());

        partialUpdatedParameterLookup.lastModified(UPDATED_LAST_MODIFIED).lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restParameterLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParameterLookup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParameterLookup))
            )
            .andExpect(status().isOk());

        // Validate the ParameterLookup in the database
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeUpdate);
        ParameterLookup testParameterLookup = parameterLookupList.get(parameterLookupList.size() - 1);
        assertThat(testParameterLookup.getParameterType()).isEqualTo(DEFAULT_PARAMETER_TYPE);
        assertThat(testParameterLookup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testParameterLookup.getDisplayValue()).isEqualTo(DEFAULT_DISPLAY_VALUE);
        assertThat(testParameterLookup.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testParameterLookup.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testParameterLookup.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testParameterLookup.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testParameterLookup.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void fullUpdateParameterLookupWithPatch() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        int databaseSizeBeforeUpdate = parameterLookupRepository.findAll().size();

        // Update the parameterLookup using partial update
        ParameterLookup partialUpdatedParameterLookup = new ParameterLookup();
        partialUpdatedParameterLookup.setId(parameterLookup.getId());

        partialUpdatedParameterLookup
            .parameterType(UPDATED_PARAMETER_TYPE)
            .description(UPDATED_DESCRIPTION)
            .displayValue(UPDATED_DISPLAY_VALUE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED);

        restParameterLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParameterLookup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParameterLookup))
            )
            .andExpect(status().isOk());

        // Validate the ParameterLookup in the database
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeUpdate);
        ParameterLookup testParameterLookup = parameterLookupList.get(parameterLookupList.size() - 1);
        assertThat(testParameterLookup.getParameterType()).isEqualTo(UPDATED_PARAMETER_TYPE);
        assertThat(testParameterLookup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testParameterLookup.getDisplayValue()).isEqualTo(UPDATED_DISPLAY_VALUE);
        assertThat(testParameterLookup.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testParameterLookup.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testParameterLookup.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testParameterLookup.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testParameterLookup.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingParameterLookup() throws Exception {
        int databaseSizeBeforeUpdate = parameterLookupRepository.findAll().size();
        parameterLookup.setId(count.incrementAndGet());

        // Create the ParameterLookup
        ParameterLookupDTO parameterLookupDTO = parameterLookupMapper.toDto(parameterLookup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParameterLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, parameterLookupDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parameterLookupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParameterLookup in the database
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParameterLookup() throws Exception {
        int databaseSizeBeforeUpdate = parameterLookupRepository.findAll().size();
        parameterLookup.setId(count.incrementAndGet());

        // Create the ParameterLookup
        ParameterLookupDTO parameterLookupDTO = parameterLookupMapper.toDto(parameterLookup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParameterLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parameterLookupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParameterLookup in the database
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParameterLookup() throws Exception {
        int databaseSizeBeforeUpdate = parameterLookupRepository.findAll().size();
        parameterLookup.setId(count.incrementAndGet());

        // Create the ParameterLookup
        ParameterLookupDTO parameterLookupDTO = parameterLookupMapper.toDto(parameterLookup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParameterLookupMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parameterLookupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParameterLookup in the database
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParameterLookup() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        int databaseSizeBeforeDelete = parameterLookupRepository.findAll().size();

        // Delete the parameterLookup
        restParameterLookupMockMvc
            .perform(delete(ENTITY_API_URL_ID, parameterLookup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

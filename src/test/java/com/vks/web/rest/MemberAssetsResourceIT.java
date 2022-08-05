package com.vks.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vks.IntegrationTest;
import com.vks.domain.Member;
import com.vks.domain.MemberAssets;
import com.vks.domain.enumeration.AssetType;
import com.vks.repository.MemberAssetsRepository;
import com.vks.service.criteria.MemberAssetsCriteria;
import com.vks.service.dto.MemberAssetsDTO;
import com.vks.service.mapper.MemberAssetsMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link MemberAssetsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MemberAssetsResourceIT {

    private static final Double DEFAULT_ASSET_AMOUNT = 1D;
    private static final Double UPDATED_ASSET_AMOUNT = 2D;
    private static final Double SMALLER_ASSET_AMOUNT = 1D - 1D;

    private static final byte[] DEFAULT_OTHER_DOCUMENT_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_OTHER_DOCUMENT_1 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_OTHER_DOCUMENT_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_OTHER_DOCUMENT_1_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_OTHER_DOCUMENT_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_OTHER_DOCUMENT_2 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_OTHER_DOCUMENT_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_OTHER_DOCUMENT_2_CONTENT_TYPE = "image/png";

    private static final AssetType DEFAULT_ASSET_TYPE = AssetType.FARM_MACHINERY;
    private static final AssetType UPDATED_ASSET_TYPE = AssetType.HOUSE;

    private static final Integer DEFAULT_ASSET_AREA = 1;
    private static final Integer UPDATED_ASSET_AREA = 2;
    private static final Integer SMALLER_ASSET_AREA = 1 - 1;

    private static final String DEFAULT_ASSET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_OF_ASSETS = 1;
    private static final Integer UPDATED_NUMBER_OF_ASSETS = 2;
    private static final Integer SMALLER_NUMBER_OF_ASSETS = 1 - 1;

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

    private static final String ENTITY_API_URL = "/api/member-assets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MemberAssetsRepository memberAssetsRepository;

    @Autowired
    private MemberAssetsMapper memberAssetsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMemberAssetsMockMvc;

    private MemberAssets memberAssets;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemberAssets createEntity(EntityManager em) {
        MemberAssets memberAssets = new MemberAssets()
            .assetAmount(DEFAULT_ASSET_AMOUNT)
            .otherDocument1(DEFAULT_OTHER_DOCUMENT_1)
            .otherDocument1ContentType(DEFAULT_OTHER_DOCUMENT_1_CONTENT_TYPE)
            .otherDocument2(DEFAULT_OTHER_DOCUMENT_2)
            .otherDocument2ContentType(DEFAULT_OTHER_DOCUMENT_2_CONTENT_TYPE)
            .assetType(DEFAULT_ASSET_TYPE)
            .assetArea(DEFAULT_ASSET_AREA)
            .assetAddress(DEFAULT_ASSET_ADDRESS)
            .numberOfAssets(DEFAULT_NUMBER_OF_ASSETS)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED);
        return memberAssets;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemberAssets createUpdatedEntity(EntityManager em) {
        MemberAssets memberAssets = new MemberAssets()
            .assetAmount(UPDATED_ASSET_AMOUNT)
            .otherDocument1(UPDATED_OTHER_DOCUMENT_1)
            .otherDocument1ContentType(UPDATED_OTHER_DOCUMENT_1_CONTENT_TYPE)
            .otherDocument2(UPDATED_OTHER_DOCUMENT_2)
            .otherDocument2ContentType(UPDATED_OTHER_DOCUMENT_2_CONTENT_TYPE)
            .assetType(UPDATED_ASSET_TYPE)
            .assetArea(UPDATED_ASSET_AREA)
            .assetAddress(UPDATED_ASSET_ADDRESS)
            .numberOfAssets(UPDATED_NUMBER_OF_ASSETS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED);
        return memberAssets;
    }

    @BeforeEach
    public void initTest() {
        memberAssets = createEntity(em);
    }

    @Test
    @Transactional
    void createMemberAssets() throws Exception {
        int databaseSizeBeforeCreate = memberAssetsRepository.findAll().size();
        // Create the MemberAssets
        MemberAssetsDTO memberAssetsDTO = memberAssetsMapper.toDto(memberAssets);
        restMemberAssetsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberAssetsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MemberAssets in the database
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeCreate + 1);
        MemberAssets testMemberAssets = memberAssetsList.get(memberAssetsList.size() - 1);
        assertThat(testMemberAssets.getAssetAmount()).isEqualTo(DEFAULT_ASSET_AMOUNT);
        assertThat(testMemberAssets.getOtherDocument1()).isEqualTo(DEFAULT_OTHER_DOCUMENT_1);
        assertThat(testMemberAssets.getOtherDocument1ContentType()).isEqualTo(DEFAULT_OTHER_DOCUMENT_1_CONTENT_TYPE);
        assertThat(testMemberAssets.getOtherDocument2()).isEqualTo(DEFAULT_OTHER_DOCUMENT_2);
        assertThat(testMemberAssets.getOtherDocument2ContentType()).isEqualTo(DEFAULT_OTHER_DOCUMENT_2_CONTENT_TYPE);
        assertThat(testMemberAssets.getAssetType()).isEqualTo(DEFAULT_ASSET_TYPE);
        assertThat(testMemberAssets.getAssetArea()).isEqualTo(DEFAULT_ASSET_AREA);
        assertThat(testMemberAssets.getAssetAddress()).isEqualTo(DEFAULT_ASSET_ADDRESS);
        assertThat(testMemberAssets.getNumberOfAssets()).isEqualTo(DEFAULT_NUMBER_OF_ASSETS);
        assertThat(testMemberAssets.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testMemberAssets.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testMemberAssets.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMemberAssets.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testMemberAssets.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void createMemberAssetsWithExistingId() throws Exception {
        // Create the MemberAssets with an existing ID
        memberAssets.setId(1L);
        MemberAssetsDTO memberAssetsDTO = memberAssetsMapper.toDto(memberAssets);

        int databaseSizeBeforeCreate = memberAssetsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberAssetsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberAssetsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberAssets in the database
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMemberAssets() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList
        restMemberAssetsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberAssets.getId().intValue())))
            .andExpect(jsonPath("$.[*].assetAmount").value(hasItem(DEFAULT_ASSET_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].otherDocument1ContentType").value(hasItem(DEFAULT_OTHER_DOCUMENT_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].otherDocument1").value(hasItem(Base64Utils.encodeToString(DEFAULT_OTHER_DOCUMENT_1))))
            .andExpect(jsonPath("$.[*].otherDocument2ContentType").value(hasItem(DEFAULT_OTHER_DOCUMENT_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].otherDocument2").value(hasItem(Base64Utils.encodeToString(DEFAULT_OTHER_DOCUMENT_2))))
            .andExpect(jsonPath("$.[*].assetType").value(hasItem(DEFAULT_ASSET_TYPE.toString())))
            .andExpect(jsonPath("$.[*].assetArea").value(hasItem(DEFAULT_ASSET_AREA)))
            .andExpect(jsonPath("$.[*].assetAddress").value(hasItem(DEFAULT_ASSET_ADDRESS)))
            .andExpect(jsonPath("$.[*].numberOfAssets").value(hasItem(DEFAULT_NUMBER_OF_ASSETS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getMemberAssets() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get the memberAssets
        restMemberAssetsMockMvc
            .perform(get(ENTITY_API_URL_ID, memberAssets.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(memberAssets.getId().intValue()))
            .andExpect(jsonPath("$.assetAmount").value(DEFAULT_ASSET_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.otherDocument1ContentType").value(DEFAULT_OTHER_DOCUMENT_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.otherDocument1").value(Base64Utils.encodeToString(DEFAULT_OTHER_DOCUMENT_1)))
            .andExpect(jsonPath("$.otherDocument2ContentType").value(DEFAULT_OTHER_DOCUMENT_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.otherDocument2").value(Base64Utils.encodeToString(DEFAULT_OTHER_DOCUMENT_2)))
            .andExpect(jsonPath("$.assetType").value(DEFAULT_ASSET_TYPE.toString()))
            .andExpect(jsonPath("$.assetArea").value(DEFAULT_ASSET_AREA))
            .andExpect(jsonPath("$.assetAddress").value(DEFAULT_ASSET_ADDRESS))
            .andExpect(jsonPath("$.numberOfAssets").value(DEFAULT_NUMBER_OF_ASSETS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getMemberAssetsByIdFiltering() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        Long id = memberAssets.getId();

        defaultMemberAssetsShouldBeFound("id.equals=" + id);
        defaultMemberAssetsShouldNotBeFound("id.notEquals=" + id);

        defaultMemberAssetsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMemberAssetsShouldNotBeFound("id.greaterThan=" + id);

        defaultMemberAssetsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMemberAssetsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetAmount equals to DEFAULT_ASSET_AMOUNT
        defaultMemberAssetsShouldBeFound("assetAmount.equals=" + DEFAULT_ASSET_AMOUNT);

        // Get all the memberAssetsList where assetAmount equals to UPDATED_ASSET_AMOUNT
        defaultMemberAssetsShouldNotBeFound("assetAmount.equals=" + UPDATED_ASSET_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetAmountIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetAmount in DEFAULT_ASSET_AMOUNT or UPDATED_ASSET_AMOUNT
        defaultMemberAssetsShouldBeFound("assetAmount.in=" + DEFAULT_ASSET_AMOUNT + "," + UPDATED_ASSET_AMOUNT);

        // Get all the memberAssetsList where assetAmount equals to UPDATED_ASSET_AMOUNT
        defaultMemberAssetsShouldNotBeFound("assetAmount.in=" + UPDATED_ASSET_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetAmount is not null
        defaultMemberAssetsShouldBeFound("assetAmount.specified=true");

        // Get all the memberAssetsList where assetAmount is null
        defaultMemberAssetsShouldNotBeFound("assetAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetAmount is greater than or equal to DEFAULT_ASSET_AMOUNT
        defaultMemberAssetsShouldBeFound("assetAmount.greaterThanOrEqual=" + DEFAULT_ASSET_AMOUNT);

        // Get all the memberAssetsList where assetAmount is greater than or equal to UPDATED_ASSET_AMOUNT
        defaultMemberAssetsShouldNotBeFound("assetAmount.greaterThanOrEqual=" + UPDATED_ASSET_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetAmount is less than or equal to DEFAULT_ASSET_AMOUNT
        defaultMemberAssetsShouldBeFound("assetAmount.lessThanOrEqual=" + DEFAULT_ASSET_AMOUNT);

        // Get all the memberAssetsList where assetAmount is less than or equal to SMALLER_ASSET_AMOUNT
        defaultMemberAssetsShouldNotBeFound("assetAmount.lessThanOrEqual=" + SMALLER_ASSET_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetAmount is less than DEFAULT_ASSET_AMOUNT
        defaultMemberAssetsShouldNotBeFound("assetAmount.lessThan=" + DEFAULT_ASSET_AMOUNT);

        // Get all the memberAssetsList where assetAmount is less than UPDATED_ASSET_AMOUNT
        defaultMemberAssetsShouldBeFound("assetAmount.lessThan=" + UPDATED_ASSET_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetAmount is greater than DEFAULT_ASSET_AMOUNT
        defaultMemberAssetsShouldNotBeFound("assetAmount.greaterThan=" + DEFAULT_ASSET_AMOUNT);

        // Get all the memberAssetsList where assetAmount is greater than SMALLER_ASSET_AMOUNT
        defaultMemberAssetsShouldBeFound("assetAmount.greaterThan=" + SMALLER_ASSET_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetType equals to DEFAULT_ASSET_TYPE
        defaultMemberAssetsShouldBeFound("assetType.equals=" + DEFAULT_ASSET_TYPE);

        // Get all the memberAssetsList where assetType equals to UPDATED_ASSET_TYPE
        defaultMemberAssetsShouldNotBeFound("assetType.equals=" + UPDATED_ASSET_TYPE);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetTypeIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetType in DEFAULT_ASSET_TYPE or UPDATED_ASSET_TYPE
        defaultMemberAssetsShouldBeFound("assetType.in=" + DEFAULT_ASSET_TYPE + "," + UPDATED_ASSET_TYPE);

        // Get all the memberAssetsList where assetType equals to UPDATED_ASSET_TYPE
        defaultMemberAssetsShouldNotBeFound("assetType.in=" + UPDATED_ASSET_TYPE);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetType is not null
        defaultMemberAssetsShouldBeFound("assetType.specified=true");

        // Get all the memberAssetsList where assetType is null
        defaultMemberAssetsShouldNotBeFound("assetType.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetArea equals to DEFAULT_ASSET_AREA
        defaultMemberAssetsShouldBeFound("assetArea.equals=" + DEFAULT_ASSET_AREA);

        // Get all the memberAssetsList where assetArea equals to UPDATED_ASSET_AREA
        defaultMemberAssetsShouldNotBeFound("assetArea.equals=" + UPDATED_ASSET_AREA);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetAreaIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetArea in DEFAULT_ASSET_AREA or UPDATED_ASSET_AREA
        defaultMemberAssetsShouldBeFound("assetArea.in=" + DEFAULT_ASSET_AREA + "," + UPDATED_ASSET_AREA);

        // Get all the memberAssetsList where assetArea equals to UPDATED_ASSET_AREA
        defaultMemberAssetsShouldNotBeFound("assetArea.in=" + UPDATED_ASSET_AREA);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetArea is not null
        defaultMemberAssetsShouldBeFound("assetArea.specified=true");

        // Get all the memberAssetsList where assetArea is null
        defaultMemberAssetsShouldNotBeFound("assetArea.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetAreaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetArea is greater than or equal to DEFAULT_ASSET_AREA
        defaultMemberAssetsShouldBeFound("assetArea.greaterThanOrEqual=" + DEFAULT_ASSET_AREA);

        // Get all the memberAssetsList where assetArea is greater than or equal to UPDATED_ASSET_AREA
        defaultMemberAssetsShouldNotBeFound("assetArea.greaterThanOrEqual=" + UPDATED_ASSET_AREA);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetAreaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetArea is less than or equal to DEFAULT_ASSET_AREA
        defaultMemberAssetsShouldBeFound("assetArea.lessThanOrEqual=" + DEFAULT_ASSET_AREA);

        // Get all the memberAssetsList where assetArea is less than or equal to SMALLER_ASSET_AREA
        defaultMemberAssetsShouldNotBeFound("assetArea.lessThanOrEqual=" + SMALLER_ASSET_AREA);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetAreaIsLessThanSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetArea is less than DEFAULT_ASSET_AREA
        defaultMemberAssetsShouldNotBeFound("assetArea.lessThan=" + DEFAULT_ASSET_AREA);

        // Get all the memberAssetsList where assetArea is less than UPDATED_ASSET_AREA
        defaultMemberAssetsShouldBeFound("assetArea.lessThan=" + UPDATED_ASSET_AREA);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetAreaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetArea is greater than DEFAULT_ASSET_AREA
        defaultMemberAssetsShouldNotBeFound("assetArea.greaterThan=" + DEFAULT_ASSET_AREA);

        // Get all the memberAssetsList where assetArea is greater than SMALLER_ASSET_AREA
        defaultMemberAssetsShouldBeFound("assetArea.greaterThan=" + SMALLER_ASSET_AREA);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetAddress equals to DEFAULT_ASSET_ADDRESS
        defaultMemberAssetsShouldBeFound("assetAddress.equals=" + DEFAULT_ASSET_ADDRESS);

        // Get all the memberAssetsList where assetAddress equals to UPDATED_ASSET_ADDRESS
        defaultMemberAssetsShouldNotBeFound("assetAddress.equals=" + UPDATED_ASSET_ADDRESS);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetAddressIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetAddress in DEFAULT_ASSET_ADDRESS or UPDATED_ASSET_ADDRESS
        defaultMemberAssetsShouldBeFound("assetAddress.in=" + DEFAULT_ASSET_ADDRESS + "," + UPDATED_ASSET_ADDRESS);

        // Get all the memberAssetsList where assetAddress equals to UPDATED_ASSET_ADDRESS
        defaultMemberAssetsShouldNotBeFound("assetAddress.in=" + UPDATED_ASSET_ADDRESS);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetAddress is not null
        defaultMemberAssetsShouldBeFound("assetAddress.specified=true");

        // Get all the memberAssetsList where assetAddress is null
        defaultMemberAssetsShouldNotBeFound("assetAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetAddressContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetAddress contains DEFAULT_ASSET_ADDRESS
        defaultMemberAssetsShouldBeFound("assetAddress.contains=" + DEFAULT_ASSET_ADDRESS);

        // Get all the memberAssetsList where assetAddress contains UPDATED_ASSET_ADDRESS
        defaultMemberAssetsShouldNotBeFound("assetAddress.contains=" + UPDATED_ASSET_ADDRESS);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetAddressNotContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetAddress does not contain DEFAULT_ASSET_ADDRESS
        defaultMemberAssetsShouldNotBeFound("assetAddress.doesNotContain=" + DEFAULT_ASSET_ADDRESS);

        // Get all the memberAssetsList where assetAddress does not contain UPDATED_ASSET_ADDRESS
        defaultMemberAssetsShouldBeFound("assetAddress.doesNotContain=" + UPDATED_ASSET_ADDRESS);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByNumberOfAssetsIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where numberOfAssets equals to DEFAULT_NUMBER_OF_ASSETS
        defaultMemberAssetsShouldBeFound("numberOfAssets.equals=" + DEFAULT_NUMBER_OF_ASSETS);

        // Get all the memberAssetsList where numberOfAssets equals to UPDATED_NUMBER_OF_ASSETS
        defaultMemberAssetsShouldNotBeFound("numberOfAssets.equals=" + UPDATED_NUMBER_OF_ASSETS);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByNumberOfAssetsIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where numberOfAssets in DEFAULT_NUMBER_OF_ASSETS or UPDATED_NUMBER_OF_ASSETS
        defaultMemberAssetsShouldBeFound("numberOfAssets.in=" + DEFAULT_NUMBER_OF_ASSETS + "," + UPDATED_NUMBER_OF_ASSETS);

        // Get all the memberAssetsList where numberOfAssets equals to UPDATED_NUMBER_OF_ASSETS
        defaultMemberAssetsShouldNotBeFound("numberOfAssets.in=" + UPDATED_NUMBER_OF_ASSETS);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByNumberOfAssetsIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where numberOfAssets is not null
        defaultMemberAssetsShouldBeFound("numberOfAssets.specified=true");

        // Get all the memberAssetsList where numberOfAssets is null
        defaultMemberAssetsShouldNotBeFound("numberOfAssets.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByNumberOfAssetsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where numberOfAssets is greater than or equal to DEFAULT_NUMBER_OF_ASSETS
        defaultMemberAssetsShouldBeFound("numberOfAssets.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_ASSETS);

        // Get all the memberAssetsList where numberOfAssets is greater than or equal to UPDATED_NUMBER_OF_ASSETS
        defaultMemberAssetsShouldNotBeFound("numberOfAssets.greaterThanOrEqual=" + UPDATED_NUMBER_OF_ASSETS);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByNumberOfAssetsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where numberOfAssets is less than or equal to DEFAULT_NUMBER_OF_ASSETS
        defaultMemberAssetsShouldBeFound("numberOfAssets.lessThanOrEqual=" + DEFAULT_NUMBER_OF_ASSETS);

        // Get all the memberAssetsList where numberOfAssets is less than or equal to SMALLER_NUMBER_OF_ASSETS
        defaultMemberAssetsShouldNotBeFound("numberOfAssets.lessThanOrEqual=" + SMALLER_NUMBER_OF_ASSETS);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByNumberOfAssetsIsLessThanSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where numberOfAssets is less than DEFAULT_NUMBER_OF_ASSETS
        defaultMemberAssetsShouldNotBeFound("numberOfAssets.lessThan=" + DEFAULT_NUMBER_OF_ASSETS);

        // Get all the memberAssetsList where numberOfAssets is less than UPDATED_NUMBER_OF_ASSETS
        defaultMemberAssetsShouldBeFound("numberOfAssets.lessThan=" + UPDATED_NUMBER_OF_ASSETS);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByNumberOfAssetsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where numberOfAssets is greater than DEFAULT_NUMBER_OF_ASSETS
        defaultMemberAssetsShouldNotBeFound("numberOfAssets.greaterThan=" + DEFAULT_NUMBER_OF_ASSETS);

        // Get all the memberAssetsList where numberOfAssets is greater than SMALLER_NUMBER_OF_ASSETS
        defaultMemberAssetsShouldBeFound("numberOfAssets.greaterThan=" + SMALLER_NUMBER_OF_ASSETS);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultMemberAssetsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the memberAssetsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMemberAssetsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultMemberAssetsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the memberAssetsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMemberAssetsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where lastModified is not null
        defaultMemberAssetsShouldBeFound("lastModified.specified=true");

        // Get all the memberAssetsList where lastModified is null
        defaultMemberAssetsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultMemberAssetsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberAssetsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMemberAssetsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultMemberAssetsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the memberAssetsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMemberAssetsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where lastModifiedBy is not null
        defaultMemberAssetsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the memberAssetsList where lastModifiedBy is null
        defaultMemberAssetsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultMemberAssetsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberAssetsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultMemberAssetsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultMemberAssetsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberAssetsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultMemberAssetsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where createdBy equals to DEFAULT_CREATED_BY
        defaultMemberAssetsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the memberAssetsList where createdBy equals to UPDATED_CREATED_BY
        defaultMemberAssetsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultMemberAssetsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the memberAssetsList where createdBy equals to UPDATED_CREATED_BY
        defaultMemberAssetsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where createdBy is not null
        defaultMemberAssetsShouldBeFound("createdBy.specified=true");

        // Get all the memberAssetsList where createdBy is null
        defaultMemberAssetsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where createdBy contains DEFAULT_CREATED_BY
        defaultMemberAssetsShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the memberAssetsList where createdBy contains UPDATED_CREATED_BY
        defaultMemberAssetsShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where createdBy does not contain DEFAULT_CREATED_BY
        defaultMemberAssetsShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the memberAssetsList where createdBy does not contain UPDATED_CREATED_BY
        defaultMemberAssetsShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where createdOn equals to DEFAULT_CREATED_ON
        defaultMemberAssetsShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the memberAssetsList where createdOn equals to UPDATED_CREATED_ON
        defaultMemberAssetsShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultMemberAssetsShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the memberAssetsList where createdOn equals to UPDATED_CREATED_ON
        defaultMemberAssetsShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where createdOn is not null
        defaultMemberAssetsShouldBeFound("createdOn.specified=true");

        // Get all the memberAssetsList where createdOn is null
        defaultMemberAssetsShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where isDeleted equals to DEFAULT_IS_DELETED
        defaultMemberAssetsShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the memberAssetsList where isDeleted equals to UPDATED_IS_DELETED
        defaultMemberAssetsShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultMemberAssetsShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the memberAssetsList where isDeleted equals to UPDATED_IS_DELETED
        defaultMemberAssetsShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where isDeleted is not null
        defaultMemberAssetsShouldBeFound("isDeleted.specified=true");

        // Get all the memberAssetsList where isDeleted is null
        defaultMemberAssetsShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByMemberIsEqualToSomething() throws Exception {
        Member member;
        if (TestUtil.findAll(em, Member.class).isEmpty()) {
            memberAssetsRepository.saveAndFlush(memberAssets);
            member = MemberResourceIT.createEntity(em);
        } else {
            member = TestUtil.findAll(em, Member.class).get(0);
        }
        em.persist(member);
        em.flush();
        memberAssets.setMember(member);
        memberAssetsRepository.saveAndFlush(memberAssets);
        Long memberId = member.getId();

        // Get all the memberAssetsList where member equals to memberId
        defaultMemberAssetsShouldBeFound("memberId.equals=" + memberId);

        // Get all the memberAssetsList where member equals to (memberId + 1)
        defaultMemberAssetsShouldNotBeFound("memberId.equals=" + (memberId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMemberAssetsShouldBeFound(String filter) throws Exception {
        restMemberAssetsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberAssets.getId().intValue())))
            .andExpect(jsonPath("$.[*].assetAmount").value(hasItem(DEFAULT_ASSET_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].otherDocument1ContentType").value(hasItem(DEFAULT_OTHER_DOCUMENT_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].otherDocument1").value(hasItem(Base64Utils.encodeToString(DEFAULT_OTHER_DOCUMENT_1))))
            .andExpect(jsonPath("$.[*].otherDocument2ContentType").value(hasItem(DEFAULT_OTHER_DOCUMENT_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].otherDocument2").value(hasItem(Base64Utils.encodeToString(DEFAULT_OTHER_DOCUMENT_2))))
            .andExpect(jsonPath("$.[*].assetType").value(hasItem(DEFAULT_ASSET_TYPE.toString())))
            .andExpect(jsonPath("$.[*].assetArea").value(hasItem(DEFAULT_ASSET_AREA)))
            .andExpect(jsonPath("$.[*].assetAddress").value(hasItem(DEFAULT_ASSET_ADDRESS)))
            .andExpect(jsonPath("$.[*].numberOfAssets").value(hasItem(DEFAULT_NUMBER_OF_ASSETS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())));

        // Check, that the count call also returns 1
        restMemberAssetsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMemberAssetsShouldNotBeFound(String filter) throws Exception {
        restMemberAssetsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMemberAssetsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMemberAssets() throws Exception {
        // Get the memberAssets
        restMemberAssetsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMemberAssets() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        int databaseSizeBeforeUpdate = memberAssetsRepository.findAll().size();

        // Update the memberAssets
        MemberAssets updatedMemberAssets = memberAssetsRepository.findById(memberAssets.getId()).get();
        // Disconnect from session so that the updates on updatedMemberAssets are not directly saved in db
        em.detach(updatedMemberAssets);
        updatedMemberAssets
            .assetAmount(UPDATED_ASSET_AMOUNT)
            .otherDocument1(UPDATED_OTHER_DOCUMENT_1)
            .otherDocument1ContentType(UPDATED_OTHER_DOCUMENT_1_CONTENT_TYPE)
            .otherDocument2(UPDATED_OTHER_DOCUMENT_2)
            .otherDocument2ContentType(UPDATED_OTHER_DOCUMENT_2_CONTENT_TYPE)
            .assetType(UPDATED_ASSET_TYPE)
            .assetArea(UPDATED_ASSET_AREA)
            .assetAddress(UPDATED_ASSET_ADDRESS)
            .numberOfAssets(UPDATED_NUMBER_OF_ASSETS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED);
        MemberAssetsDTO memberAssetsDTO = memberAssetsMapper.toDto(updatedMemberAssets);

        restMemberAssetsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberAssetsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberAssetsDTO))
            )
            .andExpect(status().isOk());

        // Validate the MemberAssets in the database
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeUpdate);
        MemberAssets testMemberAssets = memberAssetsList.get(memberAssetsList.size() - 1);
        assertThat(testMemberAssets.getAssetAmount()).isEqualTo(UPDATED_ASSET_AMOUNT);
        assertThat(testMemberAssets.getOtherDocument1()).isEqualTo(UPDATED_OTHER_DOCUMENT_1);
        assertThat(testMemberAssets.getOtherDocument1ContentType()).isEqualTo(UPDATED_OTHER_DOCUMENT_1_CONTENT_TYPE);
        assertThat(testMemberAssets.getOtherDocument2()).isEqualTo(UPDATED_OTHER_DOCUMENT_2);
        assertThat(testMemberAssets.getOtherDocument2ContentType()).isEqualTo(UPDATED_OTHER_DOCUMENT_2_CONTENT_TYPE);
        assertThat(testMemberAssets.getAssetType()).isEqualTo(UPDATED_ASSET_TYPE);
        assertThat(testMemberAssets.getAssetArea()).isEqualTo(UPDATED_ASSET_AREA);
        assertThat(testMemberAssets.getAssetAddress()).isEqualTo(UPDATED_ASSET_ADDRESS);
        assertThat(testMemberAssets.getNumberOfAssets()).isEqualTo(UPDATED_NUMBER_OF_ASSETS);
        assertThat(testMemberAssets.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMemberAssets.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMemberAssets.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMemberAssets.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testMemberAssets.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingMemberAssets() throws Exception {
        int databaseSizeBeforeUpdate = memberAssetsRepository.findAll().size();
        memberAssets.setId(count.incrementAndGet());

        // Create the MemberAssets
        MemberAssetsDTO memberAssetsDTO = memberAssetsMapper.toDto(memberAssets);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberAssetsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberAssetsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberAssetsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberAssets in the database
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMemberAssets() throws Exception {
        int databaseSizeBeforeUpdate = memberAssetsRepository.findAll().size();
        memberAssets.setId(count.incrementAndGet());

        // Create the MemberAssets
        MemberAssetsDTO memberAssetsDTO = memberAssetsMapper.toDto(memberAssets);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberAssetsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberAssetsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberAssets in the database
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMemberAssets() throws Exception {
        int databaseSizeBeforeUpdate = memberAssetsRepository.findAll().size();
        memberAssets.setId(count.incrementAndGet());

        // Create the MemberAssets
        MemberAssetsDTO memberAssetsDTO = memberAssetsMapper.toDto(memberAssets);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberAssetsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberAssetsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MemberAssets in the database
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMemberAssetsWithPatch() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        int databaseSizeBeforeUpdate = memberAssetsRepository.findAll().size();

        // Update the memberAssets using partial update
        MemberAssets partialUpdatedMemberAssets = new MemberAssets();
        partialUpdatedMemberAssets.setId(memberAssets.getId());

        partialUpdatedMemberAssets
            .assetAmount(UPDATED_ASSET_AMOUNT)
            .otherDocument1(UPDATED_OTHER_DOCUMENT_1)
            .otherDocument1ContentType(UPDATED_OTHER_DOCUMENT_1_CONTENT_TYPE)
            .assetType(UPDATED_ASSET_TYPE)
            .assetAddress(UPDATED_ASSET_ADDRESS)
            .numberOfAssets(UPDATED_NUMBER_OF_ASSETS)
            .lastModified(UPDATED_LAST_MODIFIED);

        restMemberAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMemberAssets.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMemberAssets))
            )
            .andExpect(status().isOk());

        // Validate the MemberAssets in the database
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeUpdate);
        MemberAssets testMemberAssets = memberAssetsList.get(memberAssetsList.size() - 1);
        assertThat(testMemberAssets.getAssetAmount()).isEqualTo(UPDATED_ASSET_AMOUNT);
        assertThat(testMemberAssets.getOtherDocument1()).isEqualTo(UPDATED_OTHER_DOCUMENT_1);
        assertThat(testMemberAssets.getOtherDocument1ContentType()).isEqualTo(UPDATED_OTHER_DOCUMENT_1_CONTENT_TYPE);
        assertThat(testMemberAssets.getOtherDocument2()).isEqualTo(DEFAULT_OTHER_DOCUMENT_2);
        assertThat(testMemberAssets.getOtherDocument2ContentType()).isEqualTo(DEFAULT_OTHER_DOCUMENT_2_CONTENT_TYPE);
        assertThat(testMemberAssets.getAssetType()).isEqualTo(UPDATED_ASSET_TYPE);
        assertThat(testMemberAssets.getAssetArea()).isEqualTo(DEFAULT_ASSET_AREA);
        assertThat(testMemberAssets.getAssetAddress()).isEqualTo(UPDATED_ASSET_ADDRESS);
        assertThat(testMemberAssets.getNumberOfAssets()).isEqualTo(UPDATED_NUMBER_OF_ASSETS);
        assertThat(testMemberAssets.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMemberAssets.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testMemberAssets.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMemberAssets.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testMemberAssets.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void fullUpdateMemberAssetsWithPatch() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        int databaseSizeBeforeUpdate = memberAssetsRepository.findAll().size();

        // Update the memberAssets using partial update
        MemberAssets partialUpdatedMemberAssets = new MemberAssets();
        partialUpdatedMemberAssets.setId(memberAssets.getId());

        partialUpdatedMemberAssets
            .assetAmount(UPDATED_ASSET_AMOUNT)
            .otherDocument1(UPDATED_OTHER_DOCUMENT_1)
            .otherDocument1ContentType(UPDATED_OTHER_DOCUMENT_1_CONTENT_TYPE)
            .otherDocument2(UPDATED_OTHER_DOCUMENT_2)
            .otherDocument2ContentType(UPDATED_OTHER_DOCUMENT_2_CONTENT_TYPE)
            .assetType(UPDATED_ASSET_TYPE)
            .assetArea(UPDATED_ASSET_AREA)
            .assetAddress(UPDATED_ASSET_ADDRESS)
            .numberOfAssets(UPDATED_NUMBER_OF_ASSETS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED);

        restMemberAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMemberAssets.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMemberAssets))
            )
            .andExpect(status().isOk());

        // Validate the MemberAssets in the database
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeUpdate);
        MemberAssets testMemberAssets = memberAssetsList.get(memberAssetsList.size() - 1);
        assertThat(testMemberAssets.getAssetAmount()).isEqualTo(UPDATED_ASSET_AMOUNT);
        assertThat(testMemberAssets.getOtherDocument1()).isEqualTo(UPDATED_OTHER_DOCUMENT_1);
        assertThat(testMemberAssets.getOtherDocument1ContentType()).isEqualTo(UPDATED_OTHER_DOCUMENT_1_CONTENT_TYPE);
        assertThat(testMemberAssets.getOtherDocument2()).isEqualTo(UPDATED_OTHER_DOCUMENT_2);
        assertThat(testMemberAssets.getOtherDocument2ContentType()).isEqualTo(UPDATED_OTHER_DOCUMENT_2_CONTENT_TYPE);
        assertThat(testMemberAssets.getAssetType()).isEqualTo(UPDATED_ASSET_TYPE);
        assertThat(testMemberAssets.getAssetArea()).isEqualTo(UPDATED_ASSET_AREA);
        assertThat(testMemberAssets.getAssetAddress()).isEqualTo(UPDATED_ASSET_ADDRESS);
        assertThat(testMemberAssets.getNumberOfAssets()).isEqualTo(UPDATED_NUMBER_OF_ASSETS);
        assertThat(testMemberAssets.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMemberAssets.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMemberAssets.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMemberAssets.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testMemberAssets.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingMemberAssets() throws Exception {
        int databaseSizeBeforeUpdate = memberAssetsRepository.findAll().size();
        memberAssets.setId(count.incrementAndGet());

        // Create the MemberAssets
        MemberAssetsDTO memberAssetsDTO = memberAssetsMapper.toDto(memberAssets);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, memberAssetsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberAssetsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberAssets in the database
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMemberAssets() throws Exception {
        int databaseSizeBeforeUpdate = memberAssetsRepository.findAll().size();
        memberAssets.setId(count.incrementAndGet());

        // Create the MemberAssets
        MemberAssetsDTO memberAssetsDTO = memberAssetsMapper.toDto(memberAssets);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberAssetsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberAssets in the database
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMemberAssets() throws Exception {
        int databaseSizeBeforeUpdate = memberAssetsRepository.findAll().size();
        memberAssets.setId(count.incrementAndGet());

        // Create the MemberAssets
        MemberAssetsDTO memberAssetsDTO = memberAssetsMapper.toDto(memberAssets);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberAssetsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MemberAssets in the database
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMemberAssets() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        int databaseSizeBeforeDelete = memberAssetsRepository.findAll().size();

        // Delete the memberAssets
        restMemberAssetsMockMvc
            .perform(delete(ENTITY_API_URL_ID, memberAssets.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

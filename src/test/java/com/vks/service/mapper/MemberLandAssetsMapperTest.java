package com.vks.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberLandAssetsMapperTest {

    private MemberLandAssetsMapper memberLandAssetsMapper;

    @BeforeEach
    public void setUp() {
        memberLandAssetsMapper = new MemberLandAssetsMapperImpl();
    }
}

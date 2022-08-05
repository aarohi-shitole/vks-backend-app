package com.vks.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberBankMapperTest {

    private MemberBankMapper memberBankMapper;

    @BeforeEach
    public void setUp() {
        memberBankMapper = new MemberBankMapperImpl();
    }
}

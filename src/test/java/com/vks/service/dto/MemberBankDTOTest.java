package com.vks.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.vks.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MemberBankDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemberBankDTO.class);
        MemberBankDTO memberBankDTO1 = new MemberBankDTO();
        memberBankDTO1.setId(1L);
        MemberBankDTO memberBankDTO2 = new MemberBankDTO();
        assertThat(memberBankDTO1).isNotEqualTo(memberBankDTO2);
        memberBankDTO2.setId(memberBankDTO1.getId());
        assertThat(memberBankDTO1).isEqualTo(memberBankDTO2);
        memberBankDTO2.setId(2L);
        assertThat(memberBankDTO1).isNotEqualTo(memberBankDTO2);
        memberBankDTO1.setId(null);
        assertThat(memberBankDTO1).isNotEqualTo(memberBankDTO2);
    }
}

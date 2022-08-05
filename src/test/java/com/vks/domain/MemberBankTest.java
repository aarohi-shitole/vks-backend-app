package com.vks.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vks.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MemberBankTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemberBank.class);
        MemberBank memberBank1 = new MemberBank();
        memberBank1.setId(1L);
        MemberBank memberBank2 = new MemberBank();
        memberBank2.setId(memberBank1.getId());
        assertThat(memberBank1).isEqualTo(memberBank2);
        memberBank2.setId(2L);
        assertThat(memberBank1).isNotEqualTo(memberBank2);
        memberBank1.setId(null);
        assertThat(memberBank1).isNotEqualTo(memberBank2);
    }
}

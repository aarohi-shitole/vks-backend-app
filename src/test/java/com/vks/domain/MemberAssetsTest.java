package com.vks.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vks.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MemberAssetsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemberAssets.class);
        MemberAssets memberAssets1 = new MemberAssets();
        memberAssets1.setId(1L);
        MemberAssets memberAssets2 = new MemberAssets();
        memberAssets2.setId(memberAssets1.getId());
        assertThat(memberAssets1).isEqualTo(memberAssets2);
        memberAssets2.setId(2L);
        assertThat(memberAssets1).isNotEqualTo(memberAssets2);
        memberAssets1.setId(null);
        assertThat(memberAssets1).isNotEqualTo(memberAssets2);
    }
}

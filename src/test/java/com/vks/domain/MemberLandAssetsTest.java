package com.vks.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vks.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MemberLandAssetsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemberLandAssets.class);
        MemberLandAssets memberLandAssets1 = new MemberLandAssets();
        memberLandAssets1.setId(1L);
        MemberLandAssets memberLandAssets2 = new MemberLandAssets();
        memberLandAssets2.setId(memberLandAssets1.getId());
        assertThat(memberLandAssets1).isEqualTo(memberLandAssets2);
        memberLandAssets2.setId(2L);
        assertThat(memberLandAssets1).isNotEqualTo(memberLandAssets2);
        memberLandAssets1.setId(null);
        assertThat(memberLandAssets1).isNotEqualTo(memberLandAssets2);
    }
}

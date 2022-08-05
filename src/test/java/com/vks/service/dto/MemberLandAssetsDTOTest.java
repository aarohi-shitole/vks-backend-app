package com.vks.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.vks.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MemberLandAssetsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemberLandAssetsDTO.class);
        MemberLandAssetsDTO memberLandAssetsDTO1 = new MemberLandAssetsDTO();
        memberLandAssetsDTO1.setId(1L);
        MemberLandAssetsDTO memberLandAssetsDTO2 = new MemberLandAssetsDTO();
        assertThat(memberLandAssetsDTO1).isNotEqualTo(memberLandAssetsDTO2);
        memberLandAssetsDTO2.setId(memberLandAssetsDTO1.getId());
        assertThat(memberLandAssetsDTO1).isEqualTo(memberLandAssetsDTO2);
        memberLandAssetsDTO2.setId(2L);
        assertThat(memberLandAssetsDTO1).isNotEqualTo(memberLandAssetsDTO2);
        memberLandAssetsDTO1.setId(null);
        assertThat(memberLandAssetsDTO1).isNotEqualTo(memberLandAssetsDTO2);
    }
}

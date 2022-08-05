package com.vks.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.vks.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MemberAssetsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemberAssetsDTO.class);
        MemberAssetsDTO memberAssetsDTO1 = new MemberAssetsDTO();
        memberAssetsDTO1.setId(1L);
        MemberAssetsDTO memberAssetsDTO2 = new MemberAssetsDTO();
        assertThat(memberAssetsDTO1).isNotEqualTo(memberAssetsDTO2);
        memberAssetsDTO2.setId(memberAssetsDTO1.getId());
        assertThat(memberAssetsDTO1).isEqualTo(memberAssetsDTO2);
        memberAssetsDTO2.setId(2L);
        assertThat(memberAssetsDTO1).isNotEqualTo(memberAssetsDTO2);
        memberAssetsDTO1.setId(null);
        assertThat(memberAssetsDTO1).isNotEqualTo(memberAssetsDTO2);
    }
}

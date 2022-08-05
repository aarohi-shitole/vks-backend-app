package com.vks.service.mapper;

import com.vks.domain.Member;
import com.vks.domain.Nominee;
import com.vks.service.dto.MemberDTO;
import com.vks.service.dto.NomineeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Nominee} and its DTO {@link NomineeDTO}.
 */
@Mapper(componentModel = "spring")
public interface NomineeMapper extends EntityMapper<NomineeDTO, Nominee> {
    @Mapping(target = "member", source = "member", qualifiedByName = "memberId")
    NomineeDTO toDto(Nominee s);

    @Named("memberId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MemberDTO toDtoMemberId(Member member);
}

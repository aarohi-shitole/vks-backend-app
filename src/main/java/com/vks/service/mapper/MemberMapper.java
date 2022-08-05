package com.vks.service.mapper;

import com.vks.domain.Member;
import com.vks.domain.MemberBank;
import com.vks.domain.Society;
import com.vks.service.dto.MemberBankDTO;
import com.vks.service.dto.MemberDTO;
import com.vks.service.dto.SocietyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Member} and its DTO {@link MemberDTO}.
 */
@Mapper(componentModel = "spring")
public interface MemberMapper extends EntityMapper<MemberDTO, Member> {
    @Mapping(target = "memberBank", source = "memberBank", qualifiedByName = "memberBankId")
    @Mapping(target = "society", source = "society", qualifiedByName = "societyId")
    MemberDTO toDto(Member s);

    @Named("memberBankId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MemberBankDTO toDtoMemberBankId(MemberBank memberBank);

    @Named("societyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietyDTO toDtoSocietyId(Society society);
}

package com.vks.service.mapper;

import com.vks.domain.MemberBank;
import com.vks.service.dto.MemberBankDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MemberBank} and its DTO {@link MemberBankDTO}.
 */
@Mapper(componentModel = "spring")
public interface MemberBankMapper extends EntityMapper<MemberBankDTO, MemberBank> {}

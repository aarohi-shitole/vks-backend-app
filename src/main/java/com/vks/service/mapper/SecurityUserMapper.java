package com.vks.service.mapper;

import com.vks.domain.SecurityPermission;
import com.vks.domain.SecurityRole;
import com.vks.domain.SecurityUser;
import com.vks.domain.Society;
import com.vks.service.dto.SecurityPermissionDTO;
import com.vks.service.dto.SecurityRoleDTO;
import com.vks.service.dto.SecurityUserDTO;
import com.vks.service.dto.SocietyDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SecurityUser} and its DTO {@link SecurityUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface SecurityUserMapper extends EntityMapper<SecurityUserDTO, SecurityUser> {
    @Mapping(target = "society", source = "society", qualifiedByName = "societyId")
    @Mapping(target = "securityPermissions", source = "securityPermissions", qualifiedByName = "securityPermissionPermissionNameSet")
    @Mapping(target = "securityRoles", source = "securityRoles", qualifiedByName = "securityRoleRoleNameSet")
    SecurityUserDTO toDto(SecurityUser s);

    @Mapping(target = "removeSecurityPermission", ignore = true)
    @Mapping(target = "removeSecurityRole", ignore = true)
    SecurityUser toEntity(SecurityUserDTO securityUserDTO);

    @Named("societyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietyDTO toDtoSocietyId(Society society);

    @Named("securityPermissionPermissionName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "permissionName", source = "permissionName")
    SecurityPermissionDTO toDtoSecurityPermissionPermissionName(SecurityPermission securityPermission);

    @Named("securityPermissionPermissionNameSet")
    default Set<SecurityPermissionDTO> toDtoSecurityPermissionPermissionNameSet(Set<SecurityPermission> securityPermission) {
        return securityPermission.stream().map(this::toDtoSecurityPermissionPermissionName).collect(Collectors.toSet());
    }

    @Named("securityRoleRoleName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "roleName", source = "roleName")
    SecurityRoleDTO toDtoSecurityRoleRoleName(SecurityRole securityRole);

    @Named("securityRoleRoleNameSet")
    default Set<SecurityRoleDTO> toDtoSecurityRoleRoleNameSet(Set<SecurityRole> securityRole) {
        return securityRole.stream().map(this::toDtoSecurityRoleRoleName).collect(Collectors.toSet());
    }
}

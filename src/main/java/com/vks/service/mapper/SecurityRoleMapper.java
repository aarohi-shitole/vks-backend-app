package com.vks.service.mapper;

import com.vks.domain.SecurityPermission;
import com.vks.domain.SecurityRole;
import com.vks.service.dto.SecurityPermissionDTO;
import com.vks.service.dto.SecurityRoleDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SecurityRole} and its DTO {@link SecurityRoleDTO}.
 */
@Mapper(componentModel = "spring")
public interface SecurityRoleMapper extends EntityMapper<SecurityRoleDTO, SecurityRole> {
    @Mapping(target = "securityPermissions", source = "securityPermissions", qualifiedByName = "securityPermissionPermissionNameSet")
    SecurityRoleDTO toDto(SecurityRole s);

    @Mapping(target = "removeSecurityPermission", ignore = true)
    SecurityRole toEntity(SecurityRoleDTO securityRoleDTO);

    @Named("securityPermissionPermissionName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "permissionName", source = "permissionName")
    SecurityPermissionDTO toDtoSecurityPermissionPermissionName(SecurityPermission securityPermission);

    @Named("securityPermissionPermissionNameSet")
    default Set<SecurityPermissionDTO> toDtoSecurityPermissionPermissionNameSet(Set<SecurityPermission> securityPermission) {
        return securityPermission.stream().map(this::toDtoSecurityPermissionPermissionName).collect(Collectors.toSet());
    }
}

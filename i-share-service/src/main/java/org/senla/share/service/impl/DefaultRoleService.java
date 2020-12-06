package org.senla.share.service.impl;

import org.senla.share.dto.RoleDto;
import org.senla.share.entity.Role;
import org.senla.share.mapper.RoleMapper;
import org.senla.share.repository.RoleRepository;
import org.senla.share.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("roleService")
public class DefaultRoleService extends AbstractService<RoleDto, Role, RoleRepository> implements RoleService {

    private RoleRepository roleRepository;

    private RoleMapper roleMapper;

    @Autowired
    public DefaultRoleService(@Qualifier("roleRepository") RoleRepository roleRepository, RoleMapper roleMapper) {
        super(roleRepository, roleMapper);
    }

    @Autowired
    @Qualifier("roleRepository")
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<RoleDto> findByName(String name) {
        Optional<Role> role = roleRepository.findByName(name);
        return role.map(role1 -> roleMapper.entityToDto(role1));
    }
}

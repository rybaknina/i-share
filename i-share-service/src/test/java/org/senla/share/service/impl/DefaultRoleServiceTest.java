package org.senla.share.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.senla.share.dto.RoleDto;
import org.senla.share.entity.Role;
import org.senla.share.enums.UserRole;
import org.senla.share.mapper.RoleMapper;
import org.senla.share.repository.RoleRepository;
import org.senla.share.service.CourseService;
import org.senla.share.service.RoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultRoleServiceTest {
    private final static Logger logger = LogManager.getLogger(DefaultRoleServiceTest.class);
    private static List<Role> roleList = new ArrayList<>();
    private static List<RoleDto> roleDtoList = new ArrayList<>();
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private RoleMapper roleMapper;
    @Mock
    private CourseService courseService;
    @InjectMocks
    private RoleService roleService = new DefaultRoleService(roleRepository, roleMapper);

    @BeforeEach
    void setUp() {
        Role role = new Role();
        role.setId(1);
        role.setName(UserRole.USER.name());
        roleList.add(role);
        RoleDto roleDto = new RoleDto();
        roleDto.setId(1);
        roleDto.setName(UserRole.USER.name());
        roleDtoList.add(roleDto);
    }

    @AfterEach
    void tearDown() {
        roleList.clear();
        roleDtoList.clear();
    }

    @Test
    void saveRoleShouldValidTest() {
        RoleDto roleDto = roleDtoList.get(0);
        when(roleMapper.dtoToEntity(any(RoleDto.class))).thenReturn(roleList.get(0));
        when(roleMapper.entityToDto(any(Role.class))).thenReturn(roleDto);
        when(roleRepository.save(roleMapper.dtoToEntity(roleDto))).thenReturn(Optional.of(roleList.get(0)));
        roleService.save(roleDto);
    }

    @Test
    void saveRoleShouldThrowExceptionWhenAddNullTest() {
        assertThrows(NoSuchElementException.class, () -> roleService.save(null));
    }

    @Test
    void deleteRoleShouldVerifyTest() {
        roleService.delete(roleList.get(0).getId());
        verify(roleRepository, times(1)).delete(anyInt());
    }

    @Test
    void updateRoleShouldValidTest() {
        Role role = roleList.get(0);
        RoleDto roleDto = roleDtoList.get(0);
        when(roleMapper.entityToDto(any(Role.class))).thenReturn(roleDto);
        roleService.update(roleMapper.entityToDto(role));
    }

    @Test
    void getByIdShouldBeNotNullTest() {
        final int id = 0;
        given(roleRepository.getById(id)).willReturn(Optional.of(roleList.get(id)));
        assertNotNull(roleService.getById(id));
    }

    @Test
    void getAllShouldReturnListTest() {
        when(roleRepository.getAll()).thenReturn(roleList);
        List<RoleDto> expected = roleService.getAll();
        assertEquals(roleList.size(), expected.size());
    }

    @Test
    void getAllShouldThrowExceptionWhenEmptyTest() {
        when(roleRepository.getAll()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> roleService.getAll());
    }

    @Test
    void findByNameShouldValidTest() {
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(roleList.get(0)));
        roleService.findByName(UserRole.TRAINER.name());
    }
}
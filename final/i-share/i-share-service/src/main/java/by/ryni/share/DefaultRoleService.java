package by.ryni.share;

import by.ryni.share.dto.RoleDto;
import by.ryni.share.ecxeption.ServiceException;
import by.ryni.share.entity.Role;
import by.ryni.share.mapper.RoleMapper;
import by.ryni.share.repository.RoleRepository;
import by.ryni.share.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("roleService")
public class DefaultRoleService extends AbstractService<RoleDto, Role, RoleRepository> implements RoleService {

    private RoleRepository repository;

    private RoleMapper mapper;

    @Autowired
    public DefaultRoleService(@Qualifier("roleRepository") RoleRepository repository, RoleMapper mapper) {
        super(repository, mapper);
    }

    @Autowired
    @Qualifier("roleRepository")
    public void setRepository(RoleRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setMapper(RoleMapper mapper) {
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    @Override
    public RoleDto findByName(String name) throws ServiceException {
        Optional<Role> role = repository.findByName(name);
        if (role.isPresent()) {
            return mapper.entityToDto(role.get());
        } else {
            throw new ServiceException("Role does not exist");
        }
    }
}

package eu.senla.course.controller;

import eu.senla.course.api.service.IRoleService;
import eu.senla.course.dto.role.RoleDto;
import eu.senla.course.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {

    private IRoleService service;

    @Autowired
    @Qualifier("roleService")
    public void setService(IRoleService service) {
        this.service = service;
    }

    @GetMapping(path = "/roles/{name}")
    public RoleDto loadRoleByName(@PathVariable String name) {
        return service.loadRoleByName(name);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/roles")
    public List<RoleDto> getRoles() {
        return service.getRoles();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/roles")
    public void addRole(@RequestBody RoleDto roleDto) throws ServiceException {
        service.addRole(roleDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/roles")
    public void updateRole(@RequestBody RoleDto roleDto) throws ServiceException {
        service.updateRole(roleDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/roles/{id}")
    public void deleteSpot(@PathVariable int id) {
        service.deleteRole(id);
    }
}

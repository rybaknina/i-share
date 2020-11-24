package by.ryni.share;

import by.ryni.share.dto.RoleDto;
import by.ryni.share.ecxeption.ServiceException;
import by.ryni.share.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleRestController {
    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody RoleDto dto) {
        try {
            roleService.save(dto);
            return ResponseEntity.ok(dto);
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(dto);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        try {
            roleService.delete(id);
            return ResponseEntity.ok(id);
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(id);
        }
    }

    @PatchMapping
    public ResponseEntity<Object> update(@RequestBody RoleDto dto) {
        try {
            roleService.update(dto);
            return ResponseEntity.ok(dto);
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(dto);
        }
    }

    @GetMapping("/{id}")
    public RoleDto getById(@PathVariable int id) {
        return roleService.getById(id).get();
    }

    //TODO: add more security
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public @ResponseBody List<RoleDto> getAll() {
        return roleService.getAll();
    }
}

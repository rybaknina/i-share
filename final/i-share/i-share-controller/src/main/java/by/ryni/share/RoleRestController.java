package by.ryni.share;

import by.ryni.share.dto.RoleDto;
import by.ryni.share.exception.ServiceException;
import by.ryni.share.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
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
            return ResponseEntity.ok("Object deleted successfully");
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

    @GetMapping
    public @ResponseBody List<RoleDto> getAll() {
        return roleService.getAll();
    }
}

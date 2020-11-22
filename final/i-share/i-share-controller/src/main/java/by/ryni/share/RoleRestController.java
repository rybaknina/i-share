package by.ryni.share;

import by.ryni.share.controller.RoleController;
import by.ryni.share.dto.role.RoleDto;
import by.ryni.share.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleRestController extends AbstractRestController<RoleDto, RoleService> implements RoleController {
    //TODO: add more security
    @Override
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'ROLE_ADMIN')")
//    @PreAuthorize("permitAll()")
    public List<RoleDto> getAll() {
        return super.getAll();
    }
}

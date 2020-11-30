package by.ryni.share;

import by.ryni.share.dto.DonateDto;
import by.ryni.share.exception.ServiceException;
import by.ryni.share.handler.ResponseEntityError;
import by.ryni.share.helper.UserHelper;
import by.ryni.share.api.DonateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/donates")
public class DonateRestController {
    private Logger logger = LogManager.getLogger(DonateRestController.class);
    private DonateService donateService;
    private UserHelper userHelper;

    @Autowired
    public void setUserHelper(UserHelper userHelper) {
        this.userHelper = userHelper;
    }

    @Autowired
    public void setDonateService(DonateService donateService) {
        this.donateService = donateService;
    }

    @PostMapping
    public ResponseEntity<Object> donate(@RequestBody DonateDto dto, Principal principal) {
        dto.setUser(userHelper.setCurrentUser(logger, principal));
        try {
            donateService.save(dto);
            return ResponseEntity.ok(dto);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        try {
            donateService.delete(id);
            return ResponseEntity.ok(id);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping
    public ResponseEntity<Object> update(@RequestBody DonateDto dto) {
        try {
            donateService.update(dto);
            return ResponseEntity.ok(dto);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        return ResponseEntity.ok(donateService.getById(id).get());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public @ResponseBody
    List<DonateDto> getAll() {
        return donateService.getAll();
    }
//TODO: add all logic
}

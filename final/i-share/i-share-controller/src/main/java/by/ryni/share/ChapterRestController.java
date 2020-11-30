package by.ryni.share;

import by.ryni.share.api.ChapterService;
import by.ryni.share.dto.ChapterDto;
import by.ryni.share.exception.ServiceException;
import by.ryni.share.handler.ResponseEntityError;
import by.ryni.share.helper.UserHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chapters")
public class ChapterRestController {
    private Logger logger = LogManager.getLogger(ChapterRestController.class);
    private ChapterService chapterService;
    private UserHelper userHelper;

    @Autowired
    public void setChapterService(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @Autowired
    public void setUserHelper(UserHelper userHelper) {
        this.userHelper = userHelper;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody ChapterDto dto, Principal principal) {
        dto.setOwner(userHelper.setCurrentUser(logger, principal));
        try {
            chapterService.save(dto);
            return ResponseEntity.ok(dto);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        try {
            chapterService.delete(id);
            return ResponseEntity.ok(id);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<Object> update(@RequestBody ChapterDto dto, Principal principal) {
        Optional<ChapterDto> chapterDto = chapterService.getById(dto.getId());
        if (!chapterDto.isPresent() ||
                !chapterDto.get().getOwner().getUsername().equals(principal.getName())) {
            return ResponseEntity.badRequest().body("You are not authorize for this operation");
        }
        try {
            chapterService.update(dto);
            return ResponseEntity.ok(dto);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        Optional<ChapterDto> chapterDto = chapterService.getById(id);
        return chapterDto.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body("Chapter does not exists"));
    }

    @GetMapping
    public @ResponseBody List<ChapterDto> getAll() {
        return chapterService.getAll();
    }

    //TODO: add all logic
}

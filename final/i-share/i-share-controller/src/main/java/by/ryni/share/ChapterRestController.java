package by.ryni.share;

import by.ryni.share.dto.ChapterDto;
import by.ryni.share.ecxeption.ServiceException;
import by.ryni.share.entity.User;
import by.ryni.share.handler.ResponseEntityError;
import by.ryni.share.mapper.UserMapper;
import by.ryni.share.service.ChapterService;
import by.ryni.share.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chapters")
public class ChapterRestController {
    private Logger logger = LogManager.getLogger(ChapterRestController.class);
    private ChapterService chapterService;
    private UserService userService;
    private UserMapper userMapper;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setChapterService(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody ChapterDto dto, Authentication authentication) {
        if (authentication == null) {
            logger.error("Authentication is not valid");
            throw new AuthenticationCredentialsNotFoundException("Authentication is not valid");
        }

        Optional<User> user = userService.findByUsername(authentication.getName());
        if (!user.isPresent()) {
            logger.error("Owner is not found");
            throw new UsernameNotFoundException("Owner is not found");
        }
        dto.setOwner(userMapper.entityToDto(user.get()));
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
    public ResponseEntity<Object> update(@RequestBody ChapterDto dto) {
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
        return ResponseEntity.ok(chapterService.getById(id).get());
    }

    //TODO: add all logic

    @PostFilter("filterObject.owner.username == authentication.name or hasRole('ADMIN')")
    // TODO: PostAuthorize etc.. did not work
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public @ResponseBody List<ChapterDto> getAll() {
        return chapterService.getAll();
    }
}

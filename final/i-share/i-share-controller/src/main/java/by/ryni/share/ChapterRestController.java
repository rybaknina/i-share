package by.ryni.share;

import by.ryni.share.controller.ChapterController;
import by.ryni.share.dto.chapter.ChapterDto;
import by.ryni.share.entity.User;
import by.ryni.share.mapper.UserMapper;
import by.ryni.share.service.ChapterService;
import by.ryni.share.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/chapter")
public class ChapterRestController extends AbstractRestController<ChapterDto, ChapterService> implements ChapterController {
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

    //TODO: add all logic

    @Override
    @PostMapping
    public ResponseEntity<Object> save(@RequestBody ChapterDto chapterDto, Authentication authentication) {
        if (authentication == null) {
            throw new AuthenticationCredentialsNotFoundException("Authentication is not valid");
        }
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
        Map<String, Object> attributes = token.getTokenAttributes();

        Optional<User> user = userService.findByUsername(attributes.get("username").toString());
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Owner is not found");
        }
        chapterDto.setOwner(userMapper.entityToShortDto(user.get()));
        return super.save(chapterDto, authentication);
    }
//    @PostFilter("filterObject.owner.username == authentication.name")
    // TODO: PostAuthorize etc.. did not work
//    @PostAuthorize("hasRole('ADMIN')")
//    @Secured("ROLE_ADMIN")
//    @PreAuthorize("permitAll()")
    @GetMapping
    @Override
    public @ResponseBody List<ChapterDto> getAll() {
        return super.getAll();
    }
}

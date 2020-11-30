package by.ryni.share;

import by.ryni.share.api.CourseService;
import by.ryni.share.dto.CourseDto;
import by.ryni.share.exception.ServiceException;
import by.ryni.share.handler.ResponseEntityError;
import by.ryni.share.helper.SearchHelper;
import by.ryni.share.helper.UserHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseRestController {
    private Logger logger = LogManager.getLogger(CourseRestController.class);
    private CourseService courseService;
    private UserHelper userHelper;
    private SearchHelper searchHelper;

    @Autowired
    public void setUserHelper(UserHelper userHelper) {
        this.userHelper = userHelper;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    public void setSearchHelper(SearchHelper searchHelper) {
        this.searchHelper = searchHelper;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody CourseDto dto, Principal principal) {
        dto.setOwner(userHelper.setCurrentUser(logger, principal));
        try {
            courseService.save(dto);
            return ResponseEntity.ok(dto);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        try {
            courseService.delete(id);
            return ResponseEntity.ok(id);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<Object> update(@RequestBody CourseDto dto) {
        try {
            courseService.update(dto);
            return ResponseEntity.ok(dto);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        return ResponseEntity.ok(courseService.getById(id).get());
    }

    @PostFilter("filterObject.owner.username == authentication.name or hasRole('ADMIN')")
    @GetMapping
    public @ResponseBody List<CourseDto> getAll() {
        return courseService.getAll();
    }

    @GetMapping("/search")
    public @ResponseBody List<CourseDto> search(@RequestParam(value = "search", required = false) String search) {
        return courseService.search(searchHelper.getParams(search));
    }

    @GetMapping("/space/{id}")
    public ResponseEntity<Object> availableSpace(@PathVariable int id) {
        return ResponseEntity.ok(courseService.availableSpace(id));
    }

    @PostMapping("/subscribe/{id}")
    public ResponseEntity<Object> subscribe(@PathVariable int id, Principal principal) {
        try {
            courseService.subscribe(id, principal.getName());
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
        return ResponseEntity.ok("You are in");
    }
    //TODO: add all logic
}

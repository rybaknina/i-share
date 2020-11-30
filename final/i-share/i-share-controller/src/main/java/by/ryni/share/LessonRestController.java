package by.ryni.share;

import by.ryni.share.api.LessonService;
import by.ryni.share.dto.LessonDto;
import by.ryni.share.exception.ServiceException;
import by.ryni.share.handler.ResponseEntityError;
import by.ryni.share.helper.SearchHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
public class LessonRestController {
    private Logger logger = LogManager.getLogger(LessonRestController.class);
    private LessonService lessonService;
    private SearchHelper searchHelper;

    @Autowired
    public void setLessonService(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Autowired
    public void setSearchHelper(SearchHelper searchHelper) {
        this.searchHelper = searchHelper;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody LessonDto dto) {
        try {
            lessonService.save(dto);
            return ResponseEntity.ok(dto);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        try {
            lessonService.delete(id);
            return ResponseEntity.ok(id);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<Object> update(@RequestBody LessonDto dto) {
        try {
            lessonService.update(dto);
            return ResponseEntity.ok(dto);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        return ResponseEntity.ok(lessonService.getById(id).get());
    }

    @GetMapping("/search")
    public @ResponseBody List<LessonDto> search(@RequestParam(value = "search", required = false) String search) {
        return lessonService.search(searchHelper.getParams(search));
    }

    @GetMapping
    public @ResponseBody
    List<LessonDto> getAll() {
        return lessonService.getAll();
    }
}

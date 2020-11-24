package by.ryni.share;

import by.ryni.share.dto.ThemeDto;
import by.ryni.share.ecxeption.ServiceException;
import by.ryni.share.handler.ResponseEntityError;
import by.ryni.share.service.ThemeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/themes")
public class ThemeRestController {
    private Logger logger = LogManager.getLogger(ThemeRestController.class);
    private ThemeService themeService;

    @Autowired
    public void setThemeService(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody ThemeDto dto) {
        try {
            themeService.save(dto);
            return ResponseEntity.ok(dto);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        try {
            themeService.delete(id);
            return ResponseEntity.ok(id);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<Object> update(@RequestBody ThemeDto dto) {
        try {
            themeService.update(dto);
            return ResponseEntity.ok(dto);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        return ResponseEntity.ok(themeService.getById(id).get());
    }

    //TODO: add all logic

    @GetMapping
    public @ResponseBody List<ThemeDto> getAll() {
        return themeService.getAll();
    }
}

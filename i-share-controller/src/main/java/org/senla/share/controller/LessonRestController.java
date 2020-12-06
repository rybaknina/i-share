package org.senla.share.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.senla.share.dto.LessonDto;
import org.senla.share.helper.SearchHelper;
import org.senla.share.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @Operation(summary = "Create a new lesson")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Create the lesson",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LessonDto.class))}),
            @ApiResponse(responseCode = "404", description = "Lesson not found",
                    content = @Content)})
    @PutMapping
    public ResponseEntity<Object> save(@RequestHeader(value = "Accept-Language", defaultValue = "ru", required = false) String language, @Valid @RequestBody LessonDto dto) {
        Optional<LessonDto> lessonDto = lessonService.save(dto);
        if (!lessonDto.isPresent()) {
            logger.warn("Lesson not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lessonDto.get(), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a lesson by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete the lesson",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LessonDto.class))})})
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        lessonService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update a lesson")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update the lesson",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LessonDto.class))})})
    @PostMapping
    public ResponseEntity<Object> update(@RequestHeader(value = "Accept-Language", defaultValue = "ru", required = false) String language, @Valid @RequestBody LessonDto dto) {
        return ResponseEntity.ok(lessonService.update(dto).get());
    }

    @Operation(summary = "Get a lesson by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the lesson",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LessonDto.class))}),
            @ApiResponse(responseCode = "404", description = "Lesson not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@Parameter(description = "id of lesson to be searched") @PathVariable int id) {
        Optional<LessonDto> lessonDto = lessonService.getById(id);
        return lessonDto.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all lessons with search and filtering")
    @GetMapping("/search")
    @ResponseBody
    public List<LessonDto> search(@RequestParam(value = "search", required = false) String search) {
        return lessonService.search(searchHelper.getParams(search));
    }

    @Operation(summary = "Get all lessons")
    @GetMapping
    @ResponseBody
    public List<LessonDto> getAll() {
        return lessonService.getAll();
    }

    @Operation(summary = "Get lessons by course")
    @GetMapping("/course/{id}")
    public ResponseEntity<Object> lessonsByCourse(@PathVariable int id) {
        return ResponseEntity.ok(lessonService.lessonsByCourse(id));
    }
}

package org.senla.share.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.senla.share.dto.CourseDto;
import org.senla.share.dto.UserDto;
import org.senla.share.helper.SearchHelper;
import org.senla.share.helper.UserHelper;
import org.senla.share.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Operation(summary = "Create a new course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Create the course",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Course not found",
                    content = @Content)})
    @PutMapping
    public ResponseEntity<Object> save(@RequestHeader(value = "Accept-Language", defaultValue = "ru", required = false) String language, @Valid @RequestBody CourseDto dto, Principal principal) {
        dto.setOwner(userHelper.setCurrentUser(logger, principal));
        Optional<CourseDto> courseDto = courseService.save(dto);
        if (!courseDto.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(courseDto.get(), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a course by its id (just for owner)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete the course",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseDto.class))}),
            @ApiResponse(responseCode = "403", description = "You are not authorize",
                    content = @Content)})
    @PreAuthorize("hasAuthority('TRAINER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id, Principal principal) {
        if (userHelper.userIdByPrincipal(logger, principal) !=
                courseService.getById(id).get().getOwner().getId()) {
            return new ResponseEntity<>("Access deny for this operation", HttpStatus.UNAUTHORIZED);
        }
        courseService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update a course by owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update the course",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseDto.class))}),
            @ApiResponse(responseCode = "403", description = "Only the user that submitted the request can update it",
                    content = @Content(mediaType = "application/json"))})
    @PostMapping
    public ResponseEntity<Object> update(@RequestHeader(value = "Accept-Language", defaultValue = "ru", required = false) String language, @Valid @RequestBody CourseDto dto, Principal principal) {
        if (!(userHelper.userIdByPrincipal(logger, principal) ==
                courseService.getById(dto.getId()).get().getOwner().getId())) {
            return new ResponseEntity<>("Access deny for this operation", HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(courseService.update(dto).get());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the course",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Course not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@Parameter(description = "id of course to be searched") @PathVariable int id) {
        Optional<CourseDto> courseDto = courseService.getById(id);
        return courseDto.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all own courses")
    @PostFilter("filterObject.owner.username == authentication.name or hasAuthority('ADMIN')")
    @GetMapping
    @ResponseBody
    public List<CourseDto> getAll() {
        return courseService.getAll();
    }

    @Operation(summary = "Get all courses with search and filtering")
    @GetMapping("/search")
    @ResponseBody
    public List<CourseDto> search(@RequestParam(value = "search", required = false) String search) {
        return courseService.search(searchHelper.getParams(search));
    }

    @Operation(summary = "Shows the number of available places on the course")
    @GetMapping("/space/{id}")
    public ResponseEntity<Object> availableSpace(@PathVariable int id) {
        return ResponseEntity.ok(courseService.availableSpace(id));
    }

    @Operation(summary = "Subscription to the course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course subscription successful",
                    content = @Content),
            @ApiResponse(responseCode = "406", description = "Course subscription is not acceptable",
                    content = @Content)})
    @PostMapping("/subscribe/{id}")
    public ResponseEntity<Object> subscribe(@PathVariable int id, Principal principal) {
        if (courseService.subscribe(id, principal.getName())) {
            return ResponseEntity.ok("Course subscription successful");
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @Operation(summary = "List of course participants")
    @GetMapping("/members/{id}")
    @ResponseBody
    public Set<UserDto> membersByCourse(@PathVariable int id) {
        return courseService.membersByCourse(id);
    }
}

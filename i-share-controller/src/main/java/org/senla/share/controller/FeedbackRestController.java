package org.senla.share.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.senla.share.dto.FeedbackDto;
import org.senla.share.helper.UserHelper;
import org.senla.share.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackRestController {
    private Logger logger = LogManager.getLogger(FeedbackRestController.class);
    private FeedbackService feedbackService;
    private UserHelper userHelper;

    @Autowired
    public void setUserHelper(UserHelper userHelper) {
        this.userHelper = userHelper;
    }

    @Autowired
    public void setFeedbackService(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Operation(summary = "Create a new feedback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Create the feedback",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeedbackDto.class))}),
            @ApiResponse(responseCode = "404", description = "Feedback not found",
                    content = @Content)})
    @PutMapping
    public ResponseEntity<Object> save(@RequestHeader(value = "Accept-Language", defaultValue = "ru", required = false) String language, @Valid @RequestBody FeedbackDto dto, Principal principal) {
        dto.setUser(userHelper.setCurrentUser(logger, principal));
        Optional<FeedbackDto> feedbackDto = feedbackService.save(dto);
        if (!feedbackDto.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(feedbackDto.get(), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a feedback by its id (for admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete the feedback",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeedbackDto.class))})})
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        feedbackService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update a feedback by owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update the course",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeedbackDto.class))}),
            @ApiResponse(responseCode = "403", description = "Only the user that submitted the request can update it",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<Object> update(@RequestHeader(value = "Accept-Language", defaultValue = "ru", required = false) String language, @Valid @RequestBody FeedbackDto dto, Principal principal) {
        Optional<FeedbackDto> feedbackDto = feedbackService.getById(dto.getId());
        if (!feedbackDto.isPresent() ||
                !feedbackDto.get().getUser().getUsername().equals(principal.getName())) {
            return new ResponseEntity<>("You are not authorize for this operation", HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(feedbackService.update(dto).get());
    }

    @Operation(summary = "Get a feedback by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the feedback",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeedbackDto.class))}),
            @ApiResponse(responseCode = "404", description = "Feedback not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@Parameter(description = "id of feedback to be searched") @PathVariable int id) {
        Optional<FeedbackDto> feedbackDto = feedbackService.getById(id);
        return feedbackDto.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all own feedbacks or all feedbacks for admin")
    @PostFilter("filterObject.user.username == authentication.name or hasAuthority('ADMIN')")
    @GetMapping
    @ResponseBody
    public List<FeedbackDto> getAll() {
        return feedbackService.getAll();
    }

    @Operation(summary = "Get all feedbacks by course")
    @GetMapping("/course/{id}")
    @ResponseBody
    public Set<FeedbackDto> feedbacksByCourse(@PathVariable int id) {
        return feedbackService.getAll().stream()
                .filter(e -> Objects.nonNull(e.getCourse()))
                .filter(e -> e.getCourse().getId() == id)
                .collect(Collectors.toSet());
    }

    @Operation(summary = "Get all feedbacks by lesson")
    @GetMapping("/lesson/{id}")
    @ResponseBody
    public List<FeedbackDto> feedbacksByLesson(@PathVariable int id) {
        return feedbackService.getAll().stream()
                .filter(e -> Objects.nonNull(e.getLesson()))
                .filter(e -> e.getLesson().getId() == id)
                .collect(Collectors.toList());
    }
}

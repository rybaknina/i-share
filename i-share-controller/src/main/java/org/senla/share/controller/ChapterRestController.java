package org.senla.share.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.senla.share.dto.ChapterDto;
import org.senla.share.helper.UserHelper;
import org.senla.share.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Operation(summary = "Create a new chapter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Create the chapter",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChapterDto.class))}),
            @ApiResponse(responseCode = "404", description = "Chapter not found",
                    content = @Content)})
    @PutMapping
    public ResponseEntity<Object> save(@RequestHeader(value = "Accept-Language", defaultValue = "ru", required = false) String language, @Valid @RequestBody ChapterDto dto, Principal principal) {
        dto.setOwner(userHelper.setCurrentUser(logger, principal));
        Optional<ChapterDto> chapterDto = chapterService.save(dto);
        if (!chapterDto.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(chapterDto.get(), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a chapter by its id (for admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete the chapter",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChapterDto.class))})})
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@Parameter(description = "id of chapter to be deleted") @PathVariable int id) {
        chapterService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update a chapter by owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update the chapter",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChapterDto.class))}),
            @ApiResponse(responseCode = "403", description = "Only the user that submitted the request can update it",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<Object> update(@RequestHeader(value = "Accept-Language", defaultValue = "ru", required = false) String language, @Valid @RequestBody ChapterDto dto, Principal principal) {
        Optional<ChapterDto> chapterDto = chapterService.getById(dto.getId());
        if (!chapterDto.isPresent() ||
                !chapterDto.get().getOwner().getUsername().equals(principal.getName())) {
            return new ResponseEntity<>("You are not authorize for this operation", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(chapterService.update(dto).get());
    }

    @Operation(summary = "Get a chapter by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the chapter",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChapterDto.class))}),
            @ApiResponse(responseCode = "404", description = "Chapter not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@Parameter(description = "id of chapter to be searched") @PathVariable int id) {
        Optional<ChapterDto> chapterDto = chapterService.getById(id);
        return chapterDto.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all chapters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found chapters",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChapterDto.class))})})
    @GetMapping
    @ResponseBody
    public List<ChapterDto> getAll() {
        return chapterService.getAll();
    }
}

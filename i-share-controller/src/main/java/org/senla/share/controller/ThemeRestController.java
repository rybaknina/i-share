package org.senla.share.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.senla.share.dto.ThemeDto;
import org.senla.share.helper.UserHelper;
import org.senla.share.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/themes")
public class ThemeRestController {
    private Logger logger = LogManager.getLogger(ThemeRestController.class);
    private ThemeService themeService;
    private UserHelper userHelper;

    @Autowired
    public void setThemeService(ThemeService themeService) {
        this.themeService = themeService;
    }

    @Autowired
    public void setUserHelper(UserHelper userHelper) {
        this.userHelper = userHelper;
    }

    @Operation(summary = "Create a new theme")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Create the theme",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ThemeDto.class))}),
            @ApiResponse(responseCode = "404", description = "Theme not found",
                    content = @Content)})
    @PutMapping
    public ResponseEntity<Object> save(@RequestHeader(value = "Accept-Language", defaultValue = "ru", required = false) String language, @Valid @RequestBody ThemeDto dto, Principal principal) {
        dto.setOwner(userHelper.setCurrentUser(logger, principal));
        Optional<ThemeDto> themeDto = themeService.save(dto);
        if (!themeDto.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(themeDto.get(), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a theme by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete the theme",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ThemeDto.class))})})
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        themeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update a theme")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update the theme",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ThemeDto.class))})})
    @PostMapping
    public ResponseEntity<Object> update(@RequestHeader(value = "Accept-Language", defaultValue = "ru", required = false) String language, @Valid @RequestBody ThemeDto dto) {
        return ResponseEntity.ok(themeService.update(dto).get());
    }

    @Operation(summary = "Get a theme by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the theme",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ThemeDto.class))}),
            @ApiResponse(responseCode = "404", description = "Theme not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@Parameter(description = "id of theme to be searched") @PathVariable int id) {
        Optional<ThemeDto> themeDto = themeService.getById(id);
        return themeDto.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all themes")
    @GetMapping
    @ResponseBody
    public List<ThemeDto> getAll() {
        return themeService.getAll();
    }
}

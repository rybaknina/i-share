package org.senla.share.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.senla.share.dto.ProfileDto;
import org.senla.share.dto.UserDto;
import org.senla.share.helper.UserHelper;
import org.senla.share.service.UserService;
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
@RequestMapping("/users")
public class UserRestController {
    private Logger logger = LogManager.getLogger(UserRestController.class);
    private UserService userService;
    private UserHelper userHelper;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserHelper(UserHelper userHelper) {
        this.userHelper = userHelper;
    }

    @Operation(summary = "Create a new user (for admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Create the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public ResponseEntity<Object> save(@RequestHeader(value = "Accept-Language", defaultValue = "ru", required = false) String language, @Valid @RequestBody UserDto dto) {
        Optional<UserDto> userDto = userService.save(dto);
        if (!userDto.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDto.get(), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a user by its id (for admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))})})
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id, Principal principal) {
        if (userHelper.userIdByPrincipal(logger, principal) == id) {
            return new ResponseEntity<>("Access deny for this operation", HttpStatus.UNAUTHORIZED);
        }
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update a user (for admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))})})
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Object> update(@RequestHeader(value = "Accept-Language", defaultValue = "ru", required = false) String language, @Valid @RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.update(dto).get());
    }

    @Operation(summary = "Get a user by its id (for admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@Parameter(description = "id of user to be searched") @PathVariable int id) {
        Optional<UserDto> userDto = userService.getById(id);
        return userDto.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all users (for admin)")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    @ResponseBody
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @Operation(summary = "Editing a user profile")
    @PostMapping("/profile")
    public ResponseEntity<Object> profile(@RequestHeader(value = "Accept-Language", defaultValue = "ru", required = false) String language, @Valid @RequestBody ProfileDto dto, Principal principal) {
        return ResponseEntity.ok(userService.updateProfile(dto, principal.getName()).get());
    }

    @Operation(summary = "Get a user profile")
    @GetMapping("profile")
    public ResponseEntity<Object> profile(Principal principal) {
        return ResponseEntity.ok(userService.getProfile(principal.getName()).get());
    }
}

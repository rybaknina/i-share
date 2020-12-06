package org.senla.share.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.senla.share.dto.RoleDto;
import org.senla.share.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping("/roles")
public class RoleRestController {
    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(summary = "Create a new role (for admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Create the role",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoleDto.class))}),
            @ApiResponse(responseCode = "404", description = "Role not found",
                    content = @Content)})
    @PutMapping
    public ResponseEntity save(@RequestHeader(value = "Accept-Language", defaultValue = "ru", required = false) String language, @Valid @RequestBody RoleDto dto) {
        Optional<RoleDto> roleDto = roleService.save(dto);
        if (!roleDto.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(roleDto.get(), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a role by its id (for admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete the role",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoleDto.class))})})
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        roleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update a role (for admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update the role",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoleDto.class))})})
    @PostMapping
    public ResponseEntity<Object> update(@RequestHeader(value = "Accept-Language", defaultValue = "ru", required = false) String language, @Valid @RequestBody RoleDto dto) {
        return ResponseEntity.ok(roleService.update(dto).get());
    }

    @Operation(summary = "Get a role by its id (for admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the role",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoleDto.class))}),
            @ApiResponse(responseCode = "404", description = "Role not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@Parameter(description = "id of role to be searched") @PathVariable int id) {
        Optional<RoleDto> roleDto = roleService.getById(id);
        return roleDto.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all roles (for admin)")
    @GetMapping
    @ResponseBody
    public List<RoleDto> getAll() {
        return roleService.getAll();
    }
}

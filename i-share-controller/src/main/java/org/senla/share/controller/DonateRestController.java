package org.senla.share.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.senla.share.dto.DonateDto;
import org.senla.share.helper.UserHelper;
import org.senla.share.service.DonateService;
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
@RequestMapping("/donates")
public class DonateRestController {
    private Logger logger = LogManager.getLogger(DonateRestController.class);
    private DonateService donateService;
    private UserHelper userHelper;

    @Autowired
    public void setUserHelper(UserHelper userHelper) {
        this.userHelper = userHelper;
    }

    @Autowired
    public void setDonateService(DonateService donateService) {
        this.donateService = donateService;
    }

    @Operation(summary = "Create a new Donate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Create the Donate",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DonateDto.class))}),
            @ApiResponse(responseCode = "404", description = "Donate not found",
                    content = @Content)})
    @PutMapping
    public ResponseEntity<Object> save(@RequestHeader(value = "Accept-Language", defaultValue = "ru", required = false) String language, @Valid @RequestBody DonateDto dto, Principal principal) {
        dto.setUser(userHelper.setCurrentUser(logger, principal));
        Optional<DonateDto> donateDto = donateService.save(dto);
        if (!donateDto.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(donateDto.get(), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a Donate by its id (for admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete the Donate",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DonateDto.class))})})
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        donateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update a Donate by its id (for admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update the Donate",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DonateDto.class))})})
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Object> update(@RequestHeader(value = "Accept-Language", defaultValue = "ru", required = false) String language, @Valid @RequestBody DonateDto dto) {
        return ResponseEntity.ok(donateService.update(dto).get());
    }

    @Operation(summary = "Get a Donate by its id (for admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Donate",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DonateDto.class))}),
            @ApiResponse(responseCode = "404", description = "Donate not found",
                    content = @Content)})
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@Parameter(description = "id of Donate to be searched") @PathVariable int id) {
        Optional<DonateDto> donateDto = donateService.getById(id);
        return donateDto.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all donates (for admin)")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    @ResponseBody
    public List<DonateDto> getAll() {
        return donateService.getAll();
    }
}

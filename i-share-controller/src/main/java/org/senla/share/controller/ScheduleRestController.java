package org.senla.share.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.senla.share.dto.ScheduleDto;
import org.senla.share.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schedules")
public class ScheduleRestController {
    private Logger logger = LogManager.getLogger(ScheduleRestController.class);
    private ScheduleService scheduleService;

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Operation(summary = "Create a new schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Create the schedule",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ScheduleDto.class))}),
            @ApiResponse(responseCode = "404", description = "Schedule not found",
                    content = @Content)})
    @PutMapping
    public ResponseEntity<Object> save(@RequestHeader(value = "Accept-Language", defaultValue = "ru", required = false) String language, @Valid @RequestBody ScheduleDto dto) {
        Optional<ScheduleDto> scheduleDto = scheduleService.save(dto);
        if (!scheduleDto.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(scheduleDto.get(), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a schedule by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete the schedule",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ScheduleDto.class))})})
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        scheduleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update a schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update the schedule",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ScheduleDto.class))})})
    @PostMapping
    public ResponseEntity<Object> update(@RequestHeader(value = "Accept-Language", defaultValue = "ru", required = false) String language, @Valid @RequestBody ScheduleDto dto) {
        return ResponseEntity.ok(scheduleService.update(dto).get());
    }

    @Operation(summary = "Get a schedule by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the schedule",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ScheduleDto.class))}),
            @ApiResponse(responseCode = "404", description = "Schedule not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@Parameter(description = "id of schedule to be searched") @PathVariable int id) {
        Optional<ScheduleDto> scheduleDto = scheduleService.getById(id);
        return scheduleDto.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get whole schedule")
    @GetMapping
    @ResponseBody
    public List<ScheduleDto> getAll() {
        return scheduleService.getAll();
    }

    @Operation(summary = "Simple lesson schedule")
    @GetMapping("/lesson/{id}")
    public ResponseEntity<Object> scheduleByLesson(@PathVariable int id) {
        return ResponseEntity.ok(scheduleService.scheduleByLesson(id));
    }
}

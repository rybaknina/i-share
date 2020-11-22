package by.ryni.share.controller;

import by.ryni.share.dto.base.AbstractDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface GenericController<D extends AbstractDto> {
    @PostMapping
    ResponseEntity<Object> save(@RequestBody D dto, Authentication authentication);
    @DeleteMapping("/{id}")
    ResponseEntity<Object> delete(@PathVariable int id);
    @PatchMapping
    ResponseEntity<Object> update(@RequestBody D dto);
    @GetMapping("/{id}")
    ResponseEntity<Object> getById(@PathVariable int id);
    @GetMapping
    @ResponseBody List<D> getAll();
}

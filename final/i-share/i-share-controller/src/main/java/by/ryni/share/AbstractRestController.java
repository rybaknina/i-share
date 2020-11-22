package by.ryni.share;

import by.ryni.share.controller.GenericController;
import by.ryni.share.dto.base.AbstractDto;
import by.ryni.share.ecxeption.ServiceException;
import by.ryni.share.handler.ApiError;
import by.ryni.share.service.GenericService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public class AbstractRestController<D extends AbstractDto, S extends GenericService<D>> implements GenericController<D> {

    private Logger logger = LogManager.getLogger(getClass());

    private S service;

    public AbstractRestController() {
    }

    @Autowired
    public void setService(S service) {
        this.service = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<Object> save(@RequestBody D dto, Authentication authentication) {

        try {
            service.save(dto);
            logger.info("New entity created");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServiceException e) {
            return getObjectResponseEntity("Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @Override
    //@PostAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        try {
            service.delete(id);
        } catch (ServiceException e) {
            return getObjectResponseEntity("Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @PatchMapping
    public ResponseEntity<Object> update(@RequestBody D dto) {

        try {
            Optional<D> updatedDto = service.update(dto);
            logger.info("New entity created: " + updatedDto.get());
            return ResponseEntity.ok(updatedDto.get());
        } catch (ServiceException e) {
            return getObjectResponseEntity("Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        Optional<D> dto = service.getById(id);
        return ResponseEntity.ok(dto.get());
    }

    @Override
    @GetMapping
    public @ResponseBody List<D> getAll() {
        return service.getAll();
    }

    ResponseEntity<Object> getObjectResponseEntity(String error, String localizedMessage) {
        logger.error(error);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, localizedMessage, error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}

package by.ryni.share;

import by.ryni.share.dto.DonateDto;
import by.ryni.share.ecxeption.ServiceException;
import by.ryni.share.handler.ResponseEntityError;
import by.ryni.share.service.DonateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donates")
public class DonateRestController {
    private Logger logger = LogManager.getLogger(DonateRestController.class);
    private DonateService donateService;

    @Autowired
    public void setDonateService(DonateService donateService) {
        this.donateService = donateService;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody DonateDto dto) {
        try {
            donateService.save(dto);
            return ResponseEntity.ok(dto);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        try {
            donateService.delete(id);
            return ResponseEntity.ok(id);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<Object> update(@RequestBody DonateDto dto) {
        try {
            donateService.update(dto);
            return ResponseEntity.ok(dto);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        return ResponseEntity.ok(donateService.getById(id).get());
    }

    @GetMapping
    public @ResponseBody
    List<DonateDto> getAll() {
        return donateService.getAll();
    }
//TODO: add all logic
}

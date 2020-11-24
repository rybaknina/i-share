package by.ryni.share.handler;

import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityError {
    public static ResponseEntity<Object> objectResponseEntity(Logger logger, String error, String localizedMessage) {
        logger.error(error);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, localizedMessage, error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}

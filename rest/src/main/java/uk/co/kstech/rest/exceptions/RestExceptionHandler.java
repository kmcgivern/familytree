package uk.co.kstech.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uk.co.kstech.rest.service.person.RestPersonService;

/**
 * Created by KMcGivern on 02/03/2017.
 */
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(RestPersonService.PersonNotFoundException.class)
    public ResponseEntity<?> personNotFoundException(RestPersonService.PersonNotFoundException ex) {
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
}

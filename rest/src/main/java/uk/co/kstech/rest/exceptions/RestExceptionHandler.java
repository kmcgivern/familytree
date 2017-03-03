package uk.co.kstech.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uk.co.kstech.service.exceptions.InvalidUpdateException;
import uk.co.kstech.service.exceptions.PersonNotFoundException;

/**
 * Created by KMcGivern on 02/03/2017.
 */
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<?> personNotFoundException(PersonNotFoundException ex) {
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidUpdateException.class)
    public ResponseEntity<?> invalidUpdateException(InvalidUpdateException ex) {
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
}

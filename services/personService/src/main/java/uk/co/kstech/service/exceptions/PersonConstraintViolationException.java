package uk.co.kstech.service.exceptions;

/**
 * Created by KMcGivern on 03/03/2017.
 */
public class PersonConstraintViolationException extends RuntimeException {

    public PersonConstraintViolationException(final String message) {
        super(message);
    }

}

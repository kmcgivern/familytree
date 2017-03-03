package uk.co.kstech.service.exceptions;

/**
 * Created by KMcGivern on 03/03/2017.
 */
public class InvalidUpdateException extends RuntimeException {

    public InvalidUpdateException() {
    }

    public InvalidUpdateException(final String error) {
        super(error);
    }
}

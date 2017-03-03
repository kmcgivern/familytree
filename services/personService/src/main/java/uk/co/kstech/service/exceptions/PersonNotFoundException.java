package uk.co.kstech.service.exceptions;

/**
 * Created by KMcGivern on 03/03/2017.
 */
public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException() {
    }

    public PersonNotFoundException(final String error) {
        super(error);
    }

}

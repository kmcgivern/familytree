package uk.co.kstech.service.message;

/**
 * Created by KMcGivern on 11/04/2014.
 */
public class ConstraintError {

    private String property;

    private String value;

    private Object message;

    public ConstraintError(final String property, final Object message, final String value) {
        this.property = property;
        this.value = value;
        this.message = message;
    }


    public String getProperty() {
        return property;
    }

    public void setProperty(final String property) {
        this.property = property;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(final Object message) {
        this.message = message;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public String toString(){
        StringBuilder errors = new StringBuilder();
        errors.append("Property: '");
        errors.append(property);
        errors.append("' ");
        errors.append(message);
        errors.append(". Value was: ");
        errors.append("'");
        errors.append(value);
        errors.append("'");
        return errors.toString();
    }
}

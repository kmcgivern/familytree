package uk.co.kstech.dto;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Created by KMcGivern on 24/04/2014.
 */
public abstract class BaseDTO {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        BaseDTO rhs = (BaseDTO) obj;
        return new EqualsBuilder()
                .append(id, rhs.id)
                .isEquals();
    }

}

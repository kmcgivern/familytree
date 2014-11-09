package uk.co.kstech.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by KMcGivern on 24/03/2014.
 */
@MappedSuperclass()
public abstract class DomainObject implements Serializable {

    protected Long id;

    protected Long version;

    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(final Long version) {
        this.version = version;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

}

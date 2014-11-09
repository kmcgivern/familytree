package uk.co.kstech.model.person;

import org.hibernate.annotations.Type;
import uk.co.kstech.model.DomainObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by kate on 08/11/2014.
 */
@Entity(name = "UNION")
public class Union extends DomainObject{

    @NotNull
    @Column(name = "husband")
    private Person husband;

    @NotNull
    @Column(name = "wife")
    private Person wife;

    @NotNull
    @Column(name = "birthDate")
    @Type(type="date")
    private Date dateOfMarriage;

    @Column(name = "birthDate")
    @Type(type="date")
    private Date dateOfDivorce;

    public Person getHusband() {
        return husband;
    }

    public void setHusband(Person husband) {
        this.husband = husband;
    }

    public Person getWife() {
        return wife;
    }

    public void setWife(Person wife) {
        this.wife = wife;
    }

    public Date getDateOfMarriage() {
        return dateOfMarriage;
    }

    public void setDateOfMarriage(Date dateOfMarriage) {
        this.dateOfMarriage = dateOfMarriage;
    }

    public Date getDateOfDivorce() {
        return dateOfDivorce;
    }

    public void setDateOfDivorce(Date dateOfDivorce) {
        this.dateOfDivorce = dateOfDivorce;
    }
}

package uk.co.kstech.model.person;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import uk.co.kstech.model.DomainObject;

/**
 * Created by KMcGivern on 7/17/2014.
 */
@Entity(name = "PERSON")
@Table(name = "tblPerson")
public class Person extends DomainObject {

    @NotNull
    @Column(name = "firstName")
    private String firstName;

    @Column(name = "middleName")
    private String middleName;

    @NotNull
    @Column(name = "lastName")
    private String lastName;

    @NotNull
    @Column(name = "birthDate")
    @Type(type = "date")
    private LocalDate birthDate;

    @Column(name = "dateDied")
    @Type(type = "date")
    private LocalDate dateDied;

    @NotNull
    @Column(name = "isMale")
    private boolean isMale;

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getDateDied() {
        return dateDied;
    }

    public void setDateDied(LocalDate dateDied) {
        this.dateDied = dateDied;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean isMale) {
        this.isMale = isMale;
    }
}

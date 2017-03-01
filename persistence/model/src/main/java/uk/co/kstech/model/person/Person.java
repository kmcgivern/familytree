package uk.co.kstech.model.person;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

/**
 * Created by KMcGivern on 7/17/2014.
 */
@Entity(name = "PERSON")
@Table(name = "tblPerson")
@Validated
public class Person extends AbstractPersistable<Long> {

    @NotNull
    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "middleName")
    private String middleName;

    @NotNull
    @Column(name = "lastName")
    private String lastName;

    @NotNull
    @Column(name = "birthDate")
    @Temporal(TemporalType.DATE)
    private Calendar birthDate;

    @Column(name = "dateDied")
    @Temporal(TemporalType.DATE)
    private Calendar dateDied;

    @NotNull
    @Column(name = "isMale")
    private boolean isMale;

    public Person() {
        this(null);
    }

    /**
     * Creates a new Person instance.
     */
    public Person(Long id) {
        this.setId(id);
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    public Calendar getDateDied() {
        return dateDied;
    }

    public void setDateDied(Calendar dateDied) {
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

package uk.co.kstech.model.person;

import org.hibernate.annotations.Type;
import uk.co.kstech.model.DomainObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by KMcGivern on 7/17/2014.
 */
@Entity(name = "PERSON")
@Table(name = "Person")
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

    private Set<Person> children = new HashSet<>(2);

    @OneToMany( cascade = CascadeType.ALL)
    public Set<Person> getChildren() {
        return children;
    }

    public void setChildren(Set<Person> parents) {
        this.children = children;
    }

    public void addChild(Person child) {

        children.add(child);
    }

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

    public void setParent(Person parent) {
    }

    public Person getFather() {
        return null;
    }

    public void setFather(Person father) {
    }

    public Person getMother() {
        return null;
    }

    public void setMother(Person mother) {
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean isMale) {
        this.isMale = isMale;
    }
}

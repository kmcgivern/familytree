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

    @OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL, optional = true)
    private Person father;

    @OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL, optional = true)
    private Person mother;


    private Set<Person> children = new HashSet<>(0);

    //these annotations need to be on the getter.
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Person.class)
//    @JoinTable(name = "PERSON_ADDRESS", joinColumns = { @JoinColumn(name = "PERSON_ID") }, inverseJoinColumns = { @JoinColumn(name = "ADDRESS_ID") })
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="parent")
    public Set<Person> getChildren() {
        return children;
    }

    public void setChildren(Set<Person> addresses) {
        this.children = children;
    }

    public void addChild(Person child) {
        if (this.isMale) {
            child.setFather(this);
        } else {
            child.setMother(this);
        }
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

    public Person getFather() {
        return father;
    }

    public void setFather(Person father) {
        this.father = father;
    }

    public Person getMother() {
        return mother;
    }

    public void setMother(Person mother) {
        this.mother = mother;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean isMale) {
        this.isMale = isMale;
    }
}

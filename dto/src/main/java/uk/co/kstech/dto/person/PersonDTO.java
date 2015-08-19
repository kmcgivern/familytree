package uk.co.kstech.dto.person;

import org.hibernate.annotations.Type;
import uk.co.kstech.dto.BaseDTO;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by KMcGivern on 7/17/2014.
 */
public class PersonDTO extends BaseDTO {

    @NotNull
    private String firstName;

    private String middleName;

    @NotNull
    private String lastName;

    @NotNull
    @Type(type="date")
    private Date birthDate;

    private Set<PersonDTO> children = new HashSet<>(0);

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

}

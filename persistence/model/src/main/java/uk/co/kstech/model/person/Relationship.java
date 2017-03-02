package uk.co.kstech.model.person;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

/**
 * Created by KMcGivern on 08/11/2014.
 */
@Entity(name = "RELATIONSHIP")
@Table(name = "tblrelationship")
public class Relationship extends AbstractPersistable<Long> {

    @NotNull
    @Column(name = "personID1")
    private Long personID1;

    @NotNull
    @Column(name = "personID2")
    private Long personID2;

    @NotNull
    @Column(name = "relationshipType")
    @Enumerated(EnumType.STRING)
    private RelationshipType relationshipType;
   
    @Column(name = "dateOfMarriage", nullable = true)
    @Temporal(TemporalType.DATE)
    private Calendar dateOfMarriage;

    public Relationship() {
        this(null);
    }

    /**
     * Creates a new Relationship instance.
     */
    public Relationship(Long id) {
        this.setId(id);
    }

    
    public Long getPersonID1() {
        return personID1;
    }

    public void setPersonID1(Long personID1) {
        this.personID1 = personID1;
    }

    public Long getPersonID2() {
        return personID2;
    }

    public void setPersonID2(Long personID2) {
        this.personID2 = personID2;
    }

    public Calendar getDateOfMarriage() {
        return dateOfMarriage;
    }

    public void setDateOfMarriage(Calendar dateOfMarriage) {
        this.dateOfMarriage = dateOfMarriage;
    }
    
    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
    }
    
    public enum RelationshipType{
    	FATHER, MOTHER, BROTHER,SISTER,MARRIED;
    }

   
}

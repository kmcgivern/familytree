package uk.co.kstech.model.person;

import org.hibernate.annotations.Type;

import uk.co.kstech.model.DomainObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import java.util.Date;

/**
 * Created by KMcGivern on 08/11/2014.
 */
@Entity(name = "RELATIONSHIP")
@Table(name = "tblRelationship")
public class Relationship extends DomainObject{

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
    @Type(type="date")
    private Date dateOfMarriage;

    
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

    public Date getDateOfMarriage() {
        return dateOfMarriage;
    }

    public void setDateOfMarriage(Date dateOfMarriage) {
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

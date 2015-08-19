package uk.co.kstech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.kstech.dao.person.PersonDao;
import uk.co.kstech.dao.person.RelationshipDao;
import uk.co.kstech.model.person.Person;
import uk.co.kstech.model.person.Relationship;
import uk.co.kstech.service.message.ConstraintError;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by KMcGivern on 7/17/2014.
 */
@Service(value ="RelationshipServiceImpl" )
public class RelationshipServiceImpl implements RelationshipService{

    private static Validator validator;

    @PostConstruct
    public static void setUpValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Autowired
    private RelationshipDao relationshipDao;

    @Override
    public Relationship getRelationship(long id) {
            return relationshipDao.findOne(id);
    }
    @Override
    public Relationship createRelationship(Relationship relationship) {
        validate(relationship);
        return relationshipDao.save(relationship);
    }

    @Override
    public Relationship updateRelationship(Relationship relationship) {
        validate(relationship);
        return relationshipDao.save(relationship);
    }

    @Override
    public void deleteRelationship(Relationship relationship) {
        relationshipDao.delete(relationship);
    }

    private void validate(final Relationship relationship) {
        final Set<ConstraintViolation<Relationship>> constraintViolations = validator.validate(relationship);
        if (!constraintViolations.isEmpty()) {
            throw new RelationshipConstraintViolationException(formatViolations(constraintViolations));
        }
    }

    private <E> List<E> makeList(final Iterable<E> iter) {
        final List<E> list = new ArrayList<E>();
        for (E item : iter) {
            list.add(item);
        }
        return list;
    }

    private String formatViolations(final Set<ConstraintViolation<Relationship>> constraintViolations) {
        StringBuilder errors = new StringBuilder();
        errors.append("Relationship Constraint violation. ");
        for (ConstraintViolation<Relationship> cv : constraintViolations) {
            ConstraintError ce = new ConstraintError(cv.getPropertyPath().toString(), cv.getInvalidValue(), cv.getMessage());
            errors.append(ce.toString());
        }
        return errors.toString();
    }

    class RelationshipConstraintViolationException extends RuntimeException {

        public RelationshipConstraintViolationException(final String message) {
            super(message);
        }

    }
}

package uk.co.kstech.dto;

import uk.co.kstech.dto.person.PersonDTO;

import java.util.Date;

/**
 * Created by kate on 07/11/2014.
 */
public class Union extends BaseDTO  {

    private PersonDTO husband;

    private PersonDTO wife;

    private Date dateOfMarriage;

    private Date dateOfDivorce;
}

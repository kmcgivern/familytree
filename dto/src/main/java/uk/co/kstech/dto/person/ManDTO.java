package uk.co.kstech.dto.person;

/**
 * Created by kate on 08/11/2014.
 */
public class ManDTO extends PersonDTO {

    private WomanDTO wife;

    public WomanDTO getWife() {
        return wife;
    }

    public void setWife(WomanDTO wife) {
        this.wife = wife;
    }


}

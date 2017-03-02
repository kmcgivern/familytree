package uk.co.kstech.dto.person;

import javax.validation.constraints.NotNull;

/**
 * Created by KMcGivern on 02/03/2017.
 */
public class DateRepresentation {
    @NotNull
    private int day;

    @NotNull
    private int month;

    @NotNull
    private int year;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

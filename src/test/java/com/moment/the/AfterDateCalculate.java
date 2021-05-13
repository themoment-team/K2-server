package com.moment.the;


import java.time.LocalDate;
import java.time.Period;

public class AfterDateCalculate {
    public static void main(String[] args) {
        LocalDate todayDate = LocalDate.now();
        calculateAfterDate(todayDate);

    }
    public static void calculateAfterDate(LocalDate todayDate){
        LocalDate startTheMoment = LocalDate.of(2021,5,10);

        Period period = startTheMoment.until(todayDate);

        System.out.println(period.getDays());
    }
}

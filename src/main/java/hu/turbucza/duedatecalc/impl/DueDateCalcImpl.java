package hu.turbucza.duedatecalc.impl;

import hu.turbucza.duedatecalc.DueDateCalc;

import java.time.LocalDateTime;

public class DueDateCalcImpl implements DueDateCalc {

    public LocalDateTime calculate(LocalDateTime submitDateTime, Float turnaroundHours) {

        DueDateCalcUtil.checkInputParameters(submitDateTime, turnaroundHours);

        int turnaroundMins = Math.round(turnaroundHours * 60);

        return calculate(submitDateTime, turnaroundMins);
    }

    private LocalDateTime calculate(LocalDateTime submitDateTime, int turnaroundMins) {

        int minsFromStartOfActualDay = submitDateTime.getHour() * 60 - 9 * 60 + submitDateTime.getMinute() + turnaroundMins;

        int days = (minsFromStartOfActualDay - 1) / (8 * 60);
        int weekendDays = getNumOfWeekendDays(submitDateTime, days);

        int sumOfMins = turnaroundMins + days * 16 * 60 + weekendDays * 24 * 60;

        return submitDateTime.plusMinutes(sumOfMins);
    }

    private int getNumOfWeekendDays(LocalDateTime submitDateTime, int offsetDays) {
        int actualDay = submitDateTime.getDayOfWeek().getValue();
        int numOfWeekEnds = (actualDay + offsetDays - 1) / 5;
        return numOfWeekEnds * 2;
    }

}

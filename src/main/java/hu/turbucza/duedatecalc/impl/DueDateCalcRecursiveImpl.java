package hu.turbucza.duedatecalc.impl;

import hu.turbucza.duedatecalc.DueDateCalc;

import java.time.LocalDateTime;

public class DueDateCalcRecursiveImpl implements DueDateCalc {

    public LocalDateTime calculate(LocalDateTime submitDateTime, Float turnaroundHours) {


        DueDateCalcUtil.checkInputParameters(submitDateTime, turnaroundHours);

        long turnaroundMins = (long)(turnaroundHours * 60);

        return calculate(submitDateTime, turnaroundMins);
    }

    private LocalDateTime calculate(LocalDateTime submitDateTime, long turnaroundMins) {

        long minsToEndOfActualWorkingDay = DueDateCalcUtil.calculateMinsToEndOfActualWorkingDay(submitDateTime);
        long minsRemainAfterDay = turnaroundMins - minsToEndOfActualWorkingDay;

        if(minsRemainAfterDay > 0) {
            return calculate(getStartOfNextWorkingDay(submitDateTime), minsRemainAfterDay);
        } else {
            return submitDateTime.plusMinutes(turnaroundMins);
        }
    }

    private LocalDateTime getStartOfNextWorkingDay(LocalDateTime submitDateTime) {
        long daysToAdd = submitDateTime.getDayOfWeek().getValue() == 5 ? 3 : 1;
        return submitDateTime
                .plusDays(daysToAdd)
                .toLocalDate()
                .atTime(9, 0);
    }

}

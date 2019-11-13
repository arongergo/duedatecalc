package hu.turbucza.duedatecalc.impl;

import hu.turbucza.duedatecalc.DueDateCalc;

import java.time.LocalDateTime;

public class DueDateCalcImpl implements DueDateCalc {

    public LocalDateTime calculate(LocalDateTime submitDateTime, Float turnaroundHours) {

        DueDateCalcUtil.checkInputParameters(submitDateTime, turnaroundHours);

        long turnaroundMins = Math.round(turnaroundHours * 60);

        return calculate(submitDateTime, turnaroundMins);
    }

    private LocalDateTime calculate(LocalDateTime submitDateTime, long turnaroundMins) {

        long minsToEndOfActualWorkingDay = DueDateCalcUtil.calculateMinsToEndOfActualWorkingDay(submitDateTime);
        long minsRemainAfterFirstDay = turnaroundMins - minsToEndOfActualWorkingDay;

        if(minsRemainAfterFirstDay > 0) {
            return calculateCarryOverDateTime(submitDateTime, minsRemainAfterFirstDay);
        } else {
            return submitDateTime.plusMinutes(turnaroundMins);
        }
    }

    private LocalDateTime calculateCarryOverDateTime(LocalDateTime submitDateTime, long minsRemainAfterFirstDay) {
        long daysToAdd = (minsRemainAfterFirstDay -1) / (8 * 60);
        long minsRemainForLastDay = minsRemainAfterFirstDay - (daysToAdd * 8 * 60);

        daysToAdd += calculateWeekendOffsetDays(submitDateTime, daysToAdd);
        LocalDateTime calculateFrom = getStartOfNextWorkingDay(submitDateTime);
        return calculateFrom.plusDays(daysToAdd).plusMinutes(minsRemainForLastDay);
    }

    private long calculateWeekendOffsetDays(LocalDateTime submitDateTime, long days) {
        long workingWeeks = (submitDateTime.getDayOfWeek().getValue() + days) / 5;
        return workingWeeks * 2;
    }

    private LocalDateTime getStartOfNextWorkingDay(LocalDateTime submitDateTime) {
        return submitDateTime
                .plusDays(1)
                .toLocalDate()
                .atTime(9, 0);
    }

}

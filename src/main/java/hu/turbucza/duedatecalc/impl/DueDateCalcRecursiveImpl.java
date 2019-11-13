package hu.turbucza.duedatecalc.impl;

import hu.turbucza.duedatecalc.DueDateCalc;

import java.time.LocalDateTime;

public class DueDateCalcRecursiveImpl implements DueDateCalc {

    public LocalDateTime calculate(LocalDateTime submitDateTime, Float turnaroundHours) {

        checkSubmitDateValidity(submitDateTime);
        checkTurnaroundHoursValidity(turnaroundHours);

        long turnaroundMins = (long)(turnaroundHours * 60);

        return calculate(submitDateTime, turnaroundMins);
    }

    private LocalDateTime calculate(LocalDateTime submitDateTime, long turnaroundMins) {

        long minsToEndOfActualWorkingDay = calculateMinsToEndOfActualWorkingDay(submitDateTime);
        long minsRemainAfterDay = turnaroundMins - minsToEndOfActualWorkingDay;

        if(minsRemainAfterDay > 0) {
            return calculate(getStartOfNextWorkingDay(submitDateTime), minsRemainAfterDay);
        } else {
            return submitDateTime.plusMinutes(turnaroundMins);
        }
    }

    private long calculateMinsToEndOfActualWorkingDay(LocalDateTime submitDateTime) {
        long minsFromStartOfDay = submitDateTime.getHour()*60 + submitDateTime.getMinute();
        return 17*60 - minsFromStartOfDay;
    }

    private LocalDateTime getStartOfNextWorkingDay(LocalDateTime submitDateTime) {
        long daysToAdd = submitDateTime.getDayOfWeek().getValue() == 5 ? 3 : 1;
        return submitDateTime
                .plusDays(daysToAdd)
                .toLocalDate()
                .atTime(9, 0);
    }

    private void checkTurnaroundHoursValidity(Float turnaroundHours) {
        if(turnaroundHours == null) {
            throw new IllegalArgumentException("Turnaround hours must not be null!");
        }
        if(turnaroundHours < 0) {
            throw new IllegalArgumentException("Turnaround hours must not be positive!");
        }
    }

    private void checkSubmitDateValidity(LocalDateTime submitDate) {
        if(submitDate == null) {
            throw new IllegalArgumentException("Submit date/time must not be null!");
        }
    }
}

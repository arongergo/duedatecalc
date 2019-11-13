package hu.turbucza.duedatecalc.impl;

import hu.turbucza.duedatecalc.DueDateCalc;

import java.time.LocalDateTime;

public class DueDateCalcImpl implements DueDateCalc {

    public LocalDateTime calculate(LocalDateTime submitDateTime, Float turnaroundHours) {

        checkSubmitDateValidity(submitDateTime);
        checkTurnaroundHoursValidity(turnaroundHours);

        long turnaroundMins = (long)(turnaroundHours * 60);

        return calculate(submitDateTime, turnaroundMins);
    }

    private LocalDateTime calculate(LocalDateTime submitDateTime, long turnaroundMins) {

        long minsToEndOfActualWorkingDay = calculateMinsToEndOfActualWorkingDay(submitDateTime);
        long minsRemainAfterFirstDay = turnaroundMins - minsToEndOfActualWorkingDay;

        if(minsRemainAfterFirstDay > 0) {
            return calculateCarryOverDateTime(submitDateTime, minsRemainAfterFirstDay);
        } else {
            return submitDateTime.plusMinutes(turnaroundMins);
        }
    }

    private LocalDateTime calculateCarryOverDateTime(LocalDateTime submitDateTime, long minsRemainAfterFirstDay) {
        long daysRemainAfterFirstDay = minsRemainAfterFirstDay / (8 * 60);
        long minsRemainForLastDay = minsRemainAfterFirstDay - (daysRemainAfterFirstDay * 8 * 60);

        long daysToAdd = calculateDaysToAdd(submitDateTime, daysRemainAfterFirstDay);
        LocalDateTime startOfNextWorkingDay = getStartOfNextWorkingDay(submitDateTime);
        return startOfNextWorkingDay.plusDays(daysToAdd).plusMinutes(minsRemainForLastDay);
    }

    private long calculateDaysToAdd(LocalDateTime submitDateTime, long days) {
        long workingWeeks = (submitDateTime.getDayOfWeek().getValue() + days) / 5;
        return days + (workingWeeks * 2);
    }

    private LocalDateTime getStartOfNextWorkingDay(LocalDateTime submitDateTime) {
        return submitDateTime
                .plusDays(1)
                .toLocalDate()
                .atTime(9, 0);
    }

    private long calculateMinsToEndOfActualWorkingDay(LocalDateTime submitDateTime) {
        long minsFromStartOfDay = submitDateTime.getHour()*60 + submitDateTime.getMinute();
        return 17*60 - minsFromStartOfDay;
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

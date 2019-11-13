package hu.turbucza.duedatecalc.impl;

import hu.turbucza.duedatecalc.DueDateCalc;

import java.time.LocalDateTime;

public class DueDateCalcRecursiveImpl implements DueDateCalc {

    public LocalDateTime calculate(LocalDateTime submitDateTime, Float turnaroundHours) {

        DueDateCalcUtil.checkInputParameters(submitDateTime, turnaroundHours);

        long turnaroundMins = Math.round(turnaroundHours * 60);

        return calculate(submitDateTime, turnaroundMins);
    }

    private LocalDateTime calculate(LocalDateTime actualDateTime, long remainingTurnaroundMins) {

        long minsToEndOfActualWorkingDay = calculateMinsToEndOfActualWorkingDay(actualDateTime);
        long minsRemainAfterActualDay = remainingTurnaroundMins - minsToEndOfActualWorkingDay;

        if(minsRemainAfterActualDay > 0) {
            return calculate(getStartOfNextWorkingDay(actualDateTime), minsRemainAfterActualDay);
        } else {
            return actualDateTime.plusMinutes(remainingTurnaroundMins);
        }
    }

    private LocalDateTime getStartOfNextWorkingDay(LocalDateTime submitDateTime) {
        long daysToAdd = submitDateTime.getDayOfWeek().getValue() == 5 ? 3 : 1;
        return submitDateTime
                .plusDays(daysToAdd)
                .toLocalDate()
                .atTime(9, 0);
    }

    private long calculateMinsToEndOfActualWorkingDay(LocalDateTime submitDateTime) {
        long minsFromStartOfDay = submitDateTime.getHour()*60 + submitDateTime.getMinute();
        return 17*60 - minsFromStartOfDay;
    }

}

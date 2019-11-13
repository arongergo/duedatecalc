package hu.turbucza;

import java.time.LocalDateTime;

public class DueDateCalc {

    public static LocalDateTime calculate(LocalDateTime submitDateTime, Float turnaroundHours) {

        checkSubmitDateValidity(submitDateTime);
        checkTurnaroundHoursValidity(turnaroundHours);

        long turnaroundMins = (long)(turnaroundHours * 60);

        return calculate(submitDateTime, turnaroundMins);
    }

    private static LocalDateTime calculate(LocalDateTime submitDateTime, long turnaroundMins) {

        long minsToEndOfActualWorkingDay = calculateMinsToEndOfActualWorkingDay(submitDateTime);
        long minsRemainAfterFirstDay = turnaroundMins - minsToEndOfActualWorkingDay;

        if(minsRemainAfterFirstDay > 0) {
            return calculateCarryOverDateTime(submitDateTime, minsRemainAfterFirstDay);
        } else {
            return submitDateTime.plusMinutes(turnaroundMins);
        }
    }

    private static LocalDateTime calculateCarryOverDateTime(LocalDateTime submitDateTime, long minsRemainAfterFirstDay) {
        long daysRemainAfterFirstDay = minsRemainAfterFirstDay / (8 * 60);
        long minsRemainForLastDay = minsRemainAfterFirstDay - (daysRemainAfterFirstDay * 8 * 60);

        long daysToAdd = calculateDaysToAdd(submitDateTime, daysRemainAfterFirstDay);
        LocalDateTime startOfNextWorkingDay = getStartOfNextWorkingDay(submitDateTime);
        return startOfNextWorkingDay.plusDays(daysToAdd).plusMinutes(minsRemainForLastDay);
    }

    private static long calculateDaysToAdd(LocalDateTime submitDateTime, long days) {
        long workingWeeks = (submitDateTime.getDayOfWeek().getValue() + days) / 5;
        return days + (workingWeeks * 2);
    }

    private static LocalDateTime getStartOfNextWorkingDay(LocalDateTime submitDateTime) {
        return submitDateTime
                .plusDays(1)
                .toLocalDate()
                .atTime(9, 0);
    }

    private static long calculateMinsToEndOfActualWorkingDay(LocalDateTime submitDateTime) {
        long minsFromStartOfDay = submitDateTime.getHour()*60 + submitDateTime.getMinute();
        return 17*60 - minsFromStartOfDay;
    }

    private static void checkTurnaroundHoursValidity(Float turnaroundHours) {
        if(turnaroundHours == null) {
            throw new IllegalArgumentException("Turnaround hours must not be null!");
        }
        if(turnaroundHours < 0) {
            throw new IllegalArgumentException("Turnaround hours must not be positive!");
        }
    }

    private static void checkSubmitDateValidity(LocalDateTime submitDate) {
        if(submitDate == null) {
            throw new IllegalArgumentException("Submit date/time must not be null!");
        }
    }
}

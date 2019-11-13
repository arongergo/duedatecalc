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

        long minsToEndOfCurrentWorkingDay = calculateMinsToEndOfCurrentWorkingDay(submitDateTime);
        long minsRemainForNextDays = turnaroundMins - minsToEndOfCurrentWorkingDay;

        if(minsRemainForNextDays > 0) {
            return calculateCarryOverDateTime(submitDateTime, minsRemainForNextDays);
        } else {
            return submitDateTime.plusMinutes(turnaroundMins);
        }
    }

    private static LocalDateTime calculateCarryOverDateTime(LocalDateTime submitDateTime, long minsRemainForNextDays) {
        long days = minsRemainForNextDays / (8 * 60);
        long minsRemainForLastDay = minsRemainForNextDays - (days * 8 * 60);

        LocalDateTime startOfNextWorkingDay = getStartOfNextWorkingDay(submitDateTime);
        return startOfNextWorkingDay.plusDays(days).plusMinutes(minsRemainForLastDay);
    }

    private static LocalDateTime getStartOfNextWorkingDay(LocalDateTime submitDateTime) {
        return submitDateTime
                .plusDays(1)
                .toLocalDate()
                .atTime(9, 0);
    }

    private static long calculateMinsToEndOfCurrentWorkingDay(LocalDateTime submitDateTime) {
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

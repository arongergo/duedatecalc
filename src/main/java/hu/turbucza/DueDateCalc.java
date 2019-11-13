package hu.turbucza;

import java.time.LocalDateTime;

public class DueDateCalc {

    public static LocalDateTime calculate(LocalDateTime submitDate, Float turnaroundHours) {

        checkSubmitDateValidity(submitDate);
        checkTurnaroundHoursValidity(turnaroundHours);

        long turnaroundMins = (long)(turnaroundHours * 60);
        long minsFromStartOfDay = submitDate.getHour()*60 + submitDate.getMinute();
        long minsFromStartOfWorkingHours = minsFromStartOfDay - 9*60;
        long days = (turnaroundMins + minsFromStartOfWorkingHours) / (8 * 60);
        long minsInWorkDay = (turnaroundMins + minsFromStartOfWorkingHours) - (days * 8 * 60);

        return submitDate.plusDays(days).plusMinutes(minsInWorkDay);
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

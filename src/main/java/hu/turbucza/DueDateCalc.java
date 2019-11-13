package hu.turbucza;

import java.time.LocalDateTime;

public class DueDateCalc {

    public static LocalDateTime calculate(LocalDateTime submitDate, Float turnaroundHours) {

        checkSubmitDateValidity(submitDate);
        checkTurnaroundHoursValidity(turnaroundHours);

        long hours = turnaroundHours.longValue();
        long minutes = (long) (60 * (turnaroundHours - hours));

        return submitDate.plusHours(hours).plusMinutes(minutes);
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

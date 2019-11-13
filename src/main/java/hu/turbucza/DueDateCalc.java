package hu.turbucza;

import java.time.LocalDateTime;

public class DueDateCalc {

    public static LocalDateTime calculate(LocalDateTime submitDate, Float turnaroundHours) {
        checkSubmitDate(submitDate);
        checkTurnaroundHours(turnaroundHours);
        return submitDate.plusHours(turnaroundHours.intValue());
    }

    private static void checkTurnaroundHours(Float turnaroundHours) {
        if(turnaroundHours == null) {
            throw new IllegalArgumentException("Turnaround hours must not be null!");
        }
        if(turnaroundHours < 0) {
            throw new IllegalArgumentException("Turnaround hours must not be positive!");
        }
    }

    private static void checkSubmitDate(LocalDateTime submitDate) {
        if(submitDate == null) {
            throw new IllegalArgumentException("Submit date/time must not be null!");
        }
    }
}

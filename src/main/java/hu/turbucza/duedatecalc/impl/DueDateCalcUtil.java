package hu.turbucza.duedatecalc.impl;

import java.time.LocalDateTime;

class DueDateCalcUtil {

    static void checkInputParameters(LocalDateTime submitDateTime, Float turnaroundHours) {
        checkSubmitDateValidity(submitDateTime);
        checkTurnaroundHoursValidity(turnaroundHours);
    }

    private static void checkTurnaroundHoursValidity(Float turnaroundHours) {
        if(turnaroundHours == null) {
            throw new IllegalArgumentException("Turnaround hours must not be null.");
        }
        if(turnaroundHours < 0) {
            throw new IllegalArgumentException("Turnaround hours must be positive.");
        }
    }

    private static void checkSubmitDateValidity(LocalDateTime submitDateTime) {
        if(submitDateTime == null) {
            throw new IllegalArgumentException("Submit date/time must not be null.");
        }
        if(submitDateTime.getHour() < 9
           || submitDateTime.isAfter(getEndOfActualWorkingDay(submitDateTime))) {
            throw new IllegalArgumentException("Submit time must be in working hours.");
        }
        if(submitDateTime.getDayOfWeek().getValue()>5) {
            throw new IllegalArgumentException("Submit date must be working day.");
        }
    }

    static LocalDateTime getEndOfActualWorkingDay(LocalDateTime submitDateTime) {
        return submitDateTime
                .toLocalDate()
                .atTime(17, 0);
    }

    static long calculateMinsToEndOfActualWorkingDay(LocalDateTime submitDateTime) {
        long minsFromStartOfDay = submitDateTime.getHour()*60 + submitDateTime.getMinute();
        return 17*60 - minsFromStartOfDay;
    }

}

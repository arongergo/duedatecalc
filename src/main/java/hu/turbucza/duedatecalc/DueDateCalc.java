package hu.turbucza.duedatecalc;

import java.time.LocalDateTime;

public interface DueDateCalc {
    LocalDateTime calculate(LocalDateTime submitDateTime, Float turnaroundHours);
}

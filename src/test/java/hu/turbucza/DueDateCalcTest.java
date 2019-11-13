package hu.turbucza;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class DueDateCalcTest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Test
    public void calcInWorkday() {
        // given
        String submitDate = "2019-11-03 09:00";

        // when
        LocalDateTime actual = DueDateCalc.calculate(toDate(submitDate), 6f);

        // then
        assertEquals(toDate("2019-11-03 15:00"), actual);
    }

    private LocalDateTime toDate(String parseFrom) {
        return LocalDateTime.parse(parseFrom, formatter);
    }
}
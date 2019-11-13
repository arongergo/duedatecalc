package hu.turbucza.duedatecalc;

import hu.turbucza.duedatecalc.impl.DueDateCalcImpl;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class DueDateCalcTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String SUBMIT_DATE_STRING = "2019-11-13 09:00";
    private static final LocalDateTime SUBMIT_DATE = LocalDateTime.parse(SUBMIT_DATE_STRING, FORMATTER);

    DueDateCalc underTest;


    @Before
    public void setup() {
        underTest = new DueDateCalcImpl();
    }

    @Test(expected=IllegalArgumentException.class)
    public void calcNullDate() {
        underTest.calculate(null, 4f);
    }

    @Test(expected=IllegalArgumentException.class)
    public void calcNullHours() {
        underTest.calculate(SUBMIT_DATE, null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void calcNegativeHours() {
        underTest.calculate(SUBMIT_DATE, -12f);
    }

    @Test
    public void calcInWorkday() {
        // given
        // when
        LocalDateTime actual = underTest.calculate(SUBMIT_DATE, 6f);

        // then
        assertEquals(toDate("2019-11-13 15:00"), actual);
    }

    @Test
    public void calcFragmentHours() {
        // given
        // when
        LocalDateTime actual = underTest.calculate(SUBMIT_DATE, 6.5f);

        // then
        assertEquals(toDate("2019-11-13 15:30"), actual);
    }

    @Test
    public void calcCarryOverDays() {
        // given
        // when
        LocalDateTime actual = underTest.calculate(SUBMIT_DATE, 9.5f);

        // then
        assertEquals(toDate("2019-11-14 10:30"), actual);
    }

    @Test
    public void calcCarryOverMoreDays() {
        // given
        String submitDate = "2019-11-13 15:30";

        // when
        LocalDateTime actual = underTest.calculate(toDate(submitDate), 12.5f);

        // then
        assertEquals(toDate("2019-11-15 12:00"), actual);
    }

    @Test
    public void calcCarryOverWeekend() {
        // given
        String submitDate = "2019-11-15 15:30";

        // when
        LocalDateTime actual = underTest.calculate(toDate(submitDate), 12.5f);

        // then
        assertEquals(toDate("2019-11-19 12:00"), actual);
    }

    private LocalDateTime toDate(String parseFrom) {
        return LocalDateTime.parse(parseFrom, FORMATTER);
    }
}
package hu.turbucza.duedatecalc;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public abstract class DueDateCalcTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String SUBMIT_DATE_STRING = "2019-11-13 09:00";
    private static final LocalDateTime SUBMIT_DATE = LocalDateTime.parse(SUBMIT_DATE_STRING, FORMATTER);

    private DueDateCalc underTest;

    protected abstract DueDateCalc createDueDateCalcInstance();

    @Before
    public void setup() {
        underTest = createDueDateCalcInstance();
    }

    @Test(expected=IllegalArgumentException.class)
    public void calcNullDate() {
        // given
        //when
        underTest.calculate(null, 4f);
        //then
        fail();
    }

    @Test(expected=IllegalArgumentException.class)
    public void calcNullHours() {
        // given
        //when
        underTest.calculate(SUBMIT_DATE, null);
        //then
        fail();
    }

    @Test(expected=IllegalArgumentException.class)
    public void calcNegativeHours() {
        // given
        //when
        underTest.calculate(SUBMIT_DATE, -12f);
        //then
        fail();
    }

    @Test(expected=IllegalArgumentException.class)
    public void calcNonWorkingHours_1() {
        // given
        String submitDate = "2019-11-13 17:30";
        //when
        underTest.calculate(toDate(submitDate), 3f);
        //then
        fail();
    }

    @Test(expected=IllegalArgumentException.class)
    public void calcNonWorkingHours_2() {
        // given
        String submitDate = "2019-11-13 08:59";
        //when
        underTest.calculate(toDate(submitDate), 3f);
        //then
        fail();
    }

    @Test(expected=IllegalArgumentException.class)
    public void calcNonWorkingDays() {
        // given
        String submitDate = "2019-11-16 10:00";
        //when
        underTest.calculate(toDate(submitDate), 3f);
        //then
        fail();
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

    @Test
    public void calcCarryOverMultipleWeekends() {
        // given
        String submitDate = "2019-11-15 15:30";

        // when
        LocalDateTime actual = underTest.calculate(toDate(submitDate), 62.5f);

        // then
        assertEquals(toDate("2019-11-27 14:00"), actual);
    }

    @Test
    public void calcCarryOverWeekendWithSomeHour() {
        // given
        String submitDate = "2019-11-15 15:30";

        // when
        LocalDateTime actual = underTest.calculate(toDate(submitDate), 2.5f);

        // then
        assertEquals(toDate("2019-11-18 10:00"), actual);
    }

    @Test
    public void calcOneExactDay() {
        // given
        // when
        LocalDateTime actual = underTest.calculate(SUBMIT_DATE, 8f);

        // then
        assertEquals(toDate("2019-11-13 17:00"), actual);
    }

    @Test
    public void calcTwoExactDays() {
        // given

        // when
        LocalDateTime actual = underTest.calculate(SUBMIT_DATE, 16f);

        // then
        assertEquals(toDate("2019-11-14 17:00"), actual);
    }

    @Test
    public void calcThreeExactDaysWithWeekend() {
        // given
        String submitDate = "2019-11-15 09:00";

        // when
        LocalDateTime actual = underTest.calculate(toDate(submitDate), 24f);

        // then
        assertEquals(toDate("2019-11-19 17:00"), actual);
    }

    @Test
    public void calcZeroHours() {
        // given
        // when
        LocalDateTime actual = underTest.calculate(SUBMIT_DATE, 0f);

        // then
        assertEquals(SUBMIT_DATE, actual);
    }

    @Test
    public void testPerformance() {
        for(int i = 0; i< 100000; i++) {
            underTest.calculate(SUBMIT_DATE, 800f);
        }
    }

    private LocalDateTime toDate(String parseFrom) {
        return LocalDateTime.parse(parseFrom, FORMATTER);
    }
}
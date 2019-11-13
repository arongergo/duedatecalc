package hu.turbucza.duedatecalc;

import hu.turbucza.duedatecalc.impl.DueDateCalcImpl;

public class DueDateCalcImplTest extends DueDateCalcTest {
    @Override
    protected DueDateCalc createDueDateCalcInstance() {
        return new DueDateCalcImpl();
    }
}

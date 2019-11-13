package hu.turbucza.duedatecalc;

import hu.turbucza.duedatecalc.impl.DueDateCalcRecursiveImpl;

public class DueDateCalcRecursiveImplTest extends DueDateCalcTest {
    @Override
    protected DueDateCalc createDueDateCalcInstance() {
        return new DueDateCalcRecursiveImpl();
    }
}

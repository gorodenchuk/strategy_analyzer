package test;

import stat.ReBalanceStatistic;
import stat.SweepStatistic;
import strategy.elements.BaseStrategy;
import strategy.elements.ReBalance;
import strategy.elements.Sweep;
import utils.FileRoutine;

public class BaseTest {

    protected BaseStrategy baseStrategy = new BaseStrategy();
    protected FileRoutine fileRoutine = new FileRoutine();

    protected Sweep sweep = new Sweep();
    protected ReBalance reBalance = new ReBalance();
    protected ReBalanceStatistic reBalanceStatistic;
    protected SweepStatistic sweepStatistic;
}

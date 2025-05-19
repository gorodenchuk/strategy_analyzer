package test;

import stat.EntryModelsStatistic;
import stat.ReBalanceStatistic;
import stat.SweepStatistic;
import strategy.elements.BaseStrategy;
import strategy.elements.Rebalance;
import strategy.elements.Sweep;
import utils.FileRoutine;

public class BaseTest {

    protected BaseStrategy baseStrategy = new BaseStrategy();
    protected FileRoutine fileRoutine = new FileRoutine();

    protected Sweep sweep = new Sweep();
    protected Rebalance reBalance = new Rebalance();
    protected ReBalanceStatistic reBalanceStatistic;
    protected SweepStatistic sweepStatistic;
    protected EntryModelsStatistic entryModelsStatistic;
}

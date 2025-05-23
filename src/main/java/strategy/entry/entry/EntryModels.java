package strategy.entry.entry;

import helper.CandleHelper;
import pojo.model.candles.Candle;
import strategy.elements.Rebalance;
import strategy.elements.Sweep;
import strategy.elements.Target;

import java.util.ArrayList;
import java.util.List;

public class EntryModels {

    protected Sweep sweep = new Sweep();
    protected Target target = new Target();
    protected Rebalance reBalance = new Rebalance();
    protected CandleHelper candleHelper = new CandleHelper();

    protected List<Candle> fractalsLow;
    protected List<Candle> fractalsHigh;
    protected List<Candle> candles;
    protected Candle candleInvl;
    protected Candle rebalancedFractal;
    protected Candle smrVl;

    public EntryModels(List<Candle> fractalsLow, List<Candle> fractalsHigh, List<Candle> candles, Candle candleInvl, Candle rebalancedFractal, Candle smrVl) {
        this.fractalsLow = fractalsLow;
        this.fractalsHigh = fractalsHigh;
        this.candles = candles;
        this.candleInvl = candleInvl;
        this.rebalancedFractal = rebalancedFractal;
        this.smrVl = smrVl;
    }


  public boolean entryModelLongTest() {
        boolean isSmrTest = false;
        List<Candle> candlesSubList = candleHelper.getCandleSublistEntry(candles, smrVl);

        int indexBosLevel = candleHelper.getHighFractalIndexBeforeSweep(fractalsHigh, candleInvl);

        if (indexBosLevel != -1) {
            Candle bosLevel = fractalsHigh.get(indexBosLevel);

            double candleInvlLow = Double.parseDouble(candleInvl.getMid().getL());
            double bosLevelHigh = Double.parseDouble(bosLevel.getMid().getH());
            int index = candlesSubList.indexOf(smrVl) + 1;

            for (int i = index; i < candlesSubList.size(); i++) {
                Candle candle = candlesSubList.get(i);
                double low = Double.parseDouble(candle.getMid().getL());

                if (low > candleInvlLow && low < bosLevelHigh) {
                    isSmrTest = true;
                    break;
                }
            }
        }
        return isSmrTest;
    }

    public boolean entryModelShortTest() {
        boolean isSmrTest = false;
        List<Candle> candlesSubList = candleHelper.getCandleSublistEntry(candles, smrVl);

        int indexBosLevel = candleHelper.getLowFractalIndexBeforeSweep(fractalsLow, candleInvl);

        if (indexBosLevel != -1) {
            Candle bosLevel = fractalsLow.get(indexBosLevel);

            double candleInvlHigh = Double.parseDouble(candleInvl.getMid().getH());
            double bosLevelLow = Double.parseDouble(bosLevel.getMid().getL());
            int index = candlesSubList.indexOf(smrVl) + 1;

            for (int i = index; i < candlesSubList.size(); i++) {
                Candle candle = candlesSubList.get(i);
                double high = Double.parseDouble(candle.getMid().getH());

                if (high < candleInvlHigh && high > bosLevelLow) {
                    isSmrTest = true;
                    break;
                }
            }
        }
        return isSmrTest;
    }
}

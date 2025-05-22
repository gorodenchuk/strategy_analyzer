package strategy.entry.entry;

import helper.CandleHelper;
import pojo.model.candles.Candle;
import strategy.elements.Rebalance;
import strategy.elements.Sweep;
import strategy.elements.Target;

import java.util.ArrayList;
import java.util.List;

public class EntryModels {

    private final Sweep sweep = new Sweep();
    private final Target target = new Target();
    private final Rebalance reBalance = new Rebalance();
    private final CandleHelper candleHelper = new CandleHelper();

    public boolean entryModelLongTest(List<Candle> candles, List<Candle> fractalsHighMinus2TF, Candle candleInvlMinus2TF, Candle targetValidationCandle) {
        boolean isSnrTest = false;
        List<Candle> candlesSubList = candleHelper.getCandleSublistEntry(candles, targetValidationCandle);

        int indexBosLevel = candleHelper.getHighFractalIndexBeforeSweep(fractalsHighMinus2TF, candleInvlMinus2TF);

        if (indexBosLevel != -1) {
            Candle bosLevel = fractalsHighMinus2TF.get(indexBosLevel);

            double candleInvlLow = Double.parseDouble(candleInvlMinus2TF.getMid().getL());
            double bosLevelHigh = Double.parseDouble(bosLevel.getMid().getH());
            int index = candlesSubList.indexOf(targetValidationCandle) + 1;

            for (int i = index; i < candlesSubList.size(); i++) {
                Candle candle = candlesSubList.get(i);
                double low = Double.parseDouble(candle.getMid().getL());

                if (low > candleInvlLow && low < bosLevelHigh) {
                    isSnrTest = true;
                    break;
                }
            }
        }
        return isSnrTest;
    }

    public boolean entryModelShortTest(List<Candle> candles, List<Candle> fractalsLowMinus2TF, Candle candleInvlMinus2TF, Candle targetValidationCandle) {
        boolean isSnrTest = false;
        List<Candle> candlesSubList = candleHelper.getCandleSublistEntry(candles, targetValidationCandle);

        int indexBosLevel = candleHelper.getLowFractalIndexBeforeSweep(fractalsLowMinus2TF, candleInvlMinus2TF);

        if (indexBosLevel != -1) {
            Candle bosLevel = fractalsLowMinus2TF.get(indexBosLevel);

            double candleInvlHigh = Double.parseDouble(candleInvlMinus2TF.getMid().getH());
            double bosLevelLow = Double.parseDouble(bosLevel.getMid().getL());
            int index = candlesSubList.indexOf(targetValidationCandle) + 1;

            for (int i = index; i < candlesSubList.size(); i++) {
                Candle candle = candlesSubList.get(i);
                double high = Double.parseDouble(candle.getMid().getH());

                if (high < candleInvlHigh && high > bosLevelLow) {
                    isSnrTest = true;
                    break;
                }
            }
        }
        return isSnrTest;
    }
}

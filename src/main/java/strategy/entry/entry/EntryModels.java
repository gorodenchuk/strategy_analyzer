package strategy.entry.entry;

import helper.CandleHelper;
import pojo.model.candles.Candle;
import strategy.elements.Instruments;
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


    public boolean isSnrLong(List<Candle> fractalsLow, List<Candle> fractalsLowMinus2TF, List<Candle> fractalsHighMinus2TF, List<Candle> candlesOnMinus2TF, Candle takeProfitMinus2TF, Candle candleInvl) {
        Candle rebalancedFractalMinus2TF = candleHelper.getLowRebalancedFractalMinus2TF(candlesOnMinus2TF, fractalsLow, candleInvl);

        boolean sweepOnLTF = !sweep.sweepLow(candlesOnMinus2TF, fractalsLowMinus2TF, rebalancedFractalMinus2TF, candleInvl).isEmpty();
        boolean isBosLevelValidated = target.isTargetHighLevelValidated(candlesOnMinus2TF, fractalsHighMinus2TF, rebalancedFractalMinus2TF, candleInvl);
        boolean isFvgPresent = false;

        if (isBosLevelValidated) {
            isFvgPresent = !reBalance.getLongFvgList(candlesOnMinus2TF, fractalsHighMinus2TF, candleInvl).isEmpty();
        }
        return sweepOnLTF && isBosLevelValidated && isFvgPresent;
    }


    public boolean isSnrShort(List<Candle> fractalsHigh, List<Candle> fractalsHighMinus2TF, List<Candle> fractalsLowMinus2TF, List<Candle> candlesOnMinus2TF, Candle takeProfitMinus2TF, Candle candleInvl) {
        Candle rebalancedFractalMinus2TF = candleHelper.getHighRebalancedFractalMinus2TF(candlesOnMinus2TF, fractalsHigh, candleInvl);

        boolean sweepOnLTF = !sweep.sweepHigh(candlesOnMinus2TF, fractalsHighMinus2TF, rebalancedFractalMinus2TF, candleInvl).isEmpty();
        boolean isBosLevelValidated = target.isTargetLowLevelValidated(candlesOnMinus2TF, fractalsLowMinus2TF, rebalancedFractalMinus2TF, candleInvl);
        boolean isFvgPresent = false;

        if (isBosLevelValidated) {
            isFvgPresent = !reBalance.getShortFvgList(candlesOnMinus2TF, fractalsLowMinus2TF, candleInvl).isEmpty();
        }
        return sweepOnLTF && isBosLevelValidated && isFvgPresent;
    }

    public boolean entryModelLongTest(List<Candle> candles, List<Candle> fractalsHigh, Candle candleInvl) {
        boolean isSnrTest = false;
        List<Candle> candlesSubList = new ArrayList<>(candles);

        Candle candleInvlMinus2TF = candleHelper.getCandleByLow(candlesSubList, candleInvl);
        int indexBosLevel = candleHelper.getHighFractalIndexBeforeSweep(fractalsHigh, candleInvlMinus2TF);
        Candle targetValidationCandle = target.getTargetHighLevelValidatedCandle(candlesSubList, fractalsHigh, candleInvl);

        candlesSubList.subList(0, candlesSubList.indexOf(targetValidationCandle)).clear();

        if (indexBosLevel != -1) {
            Candle bosLevel = fractalsHigh.get(indexBosLevel);

            double candleInvlLow = Double.parseDouble(candleInvl.getMid().getL());
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

    public boolean entryModelShortTest(List<Candle> candles, List<Candle> fractalsLow, Candle candleInvl) {
        boolean isSnrTest = false;
        List<Candle> candlesSubList = new ArrayList<>(candles);

        Candle targetValidationCandle = target.getTargetLowLevelValidatedCandle(candlesSubList, fractalsLow, candleInvl);
        Candle candleInvlMinus2TF = candleHelper.getCandleByHigh(candlesSubList, candleInvl);
        int indexBosLevel = candleHelper.getLowFractalIndexBeforeSweep(fractalsLow, candleInvlMinus2TF);

        candlesSubList.subList(0, candlesSubList.indexOf(targetValidationCandle)).clear();


        if (indexBosLevel != -1) {
            Candle bosLevel = fractalsLow.get(indexBosLevel);

            double candleInvlHigh = Double.parseDouble(candleInvl.getMid().getH());
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

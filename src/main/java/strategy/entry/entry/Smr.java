package strategy.entry.entry;

import helper.CandleHelper;
import pojo.model.candles.Candle;
import strategy.elements.Rebalance;
import strategy.elements.Sweep;
import strategy.elements.Target;

import java.util.List;

public class Smr {

    private final List<Candle> fractalsLowMinus2TF;
    private final List<Candle> fractalsHighMinus2TF;
    private final List<Candle> candlesOnMinus2TF;
    private final Candle candleInvlMinus2TF;
    private final Candle rebalancedFractalMinus2TF;
    private final Candle targetLevelValidation;


    private Sweep sweep = new Sweep();                       // need to be replaced
    private Target target = new Target();                    // need to be replaced
    private Rebalance reBalance = new Rebalance();           // need to be replaced

    public Smr(List<Candle> fractalsLowMinus2TF, List<Candle> fractalsHighMinus2TF, List<Candle> candlesOnMinus2TF, Candle candleInvlMinus2TF, Candle rebalancedFractalMinus2TF, Candle targetLevelValidation) {
        this.fractalsLowMinus2TF = fractalsLowMinus2TF;
        this.fractalsHighMinus2TF = fractalsHighMinus2TF;
        this.candlesOnMinus2TF = candlesOnMinus2TF;
        this.candleInvlMinus2TF = candleInvlMinus2TF;
        this.rebalancedFractalMinus2TF = rebalancedFractalMinus2TF;
        this.targetLevelValidation = targetLevelValidation;
    }

    public boolean isSmrLong() {
        boolean sweepOnLTF = !sweep.sweepLow(candlesOnMinus2TF, fractalsLowMinus2TF, rebalancedFractalMinus2TF, candleInvlMinus2TF).isEmpty();
        boolean isBosLevelValidated = target.isTargetHighLevelValidated(candlesOnMinus2TF, fractalsHighMinus2TF, candleInvlMinus2TF);
        boolean isFvgPresent = false;

        if (isBosLevelValidated) {
            isFvgPresent = !reBalance.getLongFvgList(candlesOnMinus2TF, candleInvlMinus2TF, targetLevelValidation).isEmpty();
        }
        return sweepOnLTF && isBosLevelValidated && isFvgPresent;
    }

    public boolean isSmrShort() {
        boolean sweepOnLtf = !sweep.sweepHigh(candlesOnMinus2TF, fractalsHighMinus2TF, rebalancedFractalMinus2TF, candleInvlMinus2TF).isEmpty();
        boolean isBosLevelValidated = target.isTargetLowLevelValidated(candlesOnMinus2TF, fractalsLowMinus2TF, candleInvlMinus2TF);
        boolean isFvgPresent = false;

        if (isBosLevelValidated) {
            isFvgPresent = !reBalance.getShortFvgList(candlesOnMinus2TF, candleInvlMinus2TF, targetLevelValidation).isEmpty();
        }
        return sweepOnLtf && isBosLevelValidated && isFvgPresent;
    }

}
